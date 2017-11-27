package sdk.utils.branding;

import akka.stream.Materializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.net.util.Base64;
import org.apache.http.client.utils.URIBuilder;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import play.Application;
import play.api.libs.ws.WSClientConfig;
import play.api.libs.ws.ahc.AhcConfigBuilder;
import play.api.libs.ws.ahc.AhcWSClientConfig;
import play.api.libs.ws.ahc.AhcWSClientConfigFactory;
import play.api.libs.ws.ssl.SSLConfigFactory;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.mvc.Http;
import scala.concurrent.duration.Duration;
import sdk.utils.Constants;
import sdk.utils.JsonUtils;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

/**
 * Author: Karis Sponsler
 * Date: 11/22/17
 */
public class BrandingManager {
    private static WSClient wsClient;
    @Inject
    private static Materializer materializer;

    public static CompletionStage<Branding> getBranding(String appId, String version) {
        return getBranding(appId, version, Constants.BRANDING_ROUTER_URL);
    }

    public static CompletionStage<Branding> getBranding(String appId, String version, String routerUrl) {
        WSRequest request;
        try {
            request = proxylessClient().url(
                new URIBuilder(routerUrl)
                    .setPath("/client/1/config/brandingConfig")
                    .build()
                    .toString()).setHeader("X-APPTREE-APPLICATION-ID", appId).setHeader("X-APPTREE-VERSION", version);
        } catch (URISyntaxException e) {
            return CompletableFuture.supplyAsync(() -> null);
        }
        return request
            .get()
            .thenApply(wsResponse -> {
                // Use default branding (baseline page styles) if request failed
                if (wsResponse.getStatus() != Http.Status.OK) {
                    return null;
                }

                JsonNode json = wsResponse.asJson();
                return Optional.ofNullable(JsonUtils.fromJson(json, Branding.class)).orElse(null);
            })
            .thenCompose(branding -> {
                if (branding == null) {
                    return CompletableFuture.supplyAsync(() -> null);
                }

                // Get the branding logo data URI
                WSRequest logoUrlRequest;
                try {
                    logoUrlRequest = proxylessClient().url(
                        new URIBuilder(routerUrl).setPath(
                            String.format("/1/cache/branding/logo/%s", branding.configuration.logoName)
                        ).addParameter("appid", appId).build().toString()
                    );
                } catch (URISyntaxException e) {
                    return CompletableFuture.supplyAsync(() -> branding);
                }

                return logoUrlRequest
                    .get()
                    .thenApply(wsResponse -> {
                        // Use unchanged branding (baseline page styles) if request failed
                        if (wsResponse.getStatus() == Http.Status.OK) {
                            String logoData =
                                Base64.encodeBase64String(wsResponse.asByteArray());
                            branding.configuration.logoURL = String.format("data:image/png;base64,%s", logoData);
                        }
                        return branding;
                    });
            })
            .exceptionally(throwable -> null);
    }

    public static WSClient proxylessClient() {
        if ( wsClient != null ) {
            return wsClient;
        }

        // Set up the client config (you can also use a parser here):
        scala.Option<String> noneString = scala.None$.empty();
        WSClientConfig wsClientConfig = new WSClientConfig(
            Duration.apply(120, TimeUnit.SECONDS), // connectionTimeout
            Duration.apply(120, TimeUnit.SECONDS), // idleTimeout
            Duration.apply(120, TimeUnit.SECONDS), // requestTimeout
            true, // followRedirects
            false, // useProxyProperties
            noneString, // userAgent
            true, // compressionEnabled / enforced
            SSLConfigFactory.defaultConfig());

        AhcWSClientConfig clientConfig = AhcWSClientConfigFactory.forClientConfig(wsClientConfig);

        AhcConfigBuilder builder = new AhcConfigBuilder(clientConfig);
        DefaultAsyncHttpClientConfig.Builder ahcBuilder = builder.configure();
        AsyncHttpClientConfig.AdditionalChannelInitializer logging = channel ->
            channel.pipeline().addFirst("log", new io.netty.handler.logging.LoggingHandler("debug"));
        ahcBuilder.setHttpAdditionalChannelInitializer(logging);

        wsClient = new play.libs.ws.ahc.AhcWSClient(ahcBuilder.build(), materializer);

        return wsClient;
    }

    public static class Branding {
        @JsonProperty("brandingConfig")
        public BrandingConfiguration configuration;
    }
}
