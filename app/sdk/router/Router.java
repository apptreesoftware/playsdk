package sdk.router;

import akka.stream.Materializer;
import com.google.inject.Inject;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import play.Configuration;
import play.api.libs.ws.WSClientConfig;
import play.api.libs.ws.ahc.AhcConfigBuilder;
import play.api.libs.ws.ahc.AhcWSClientConfig;
import play.api.libs.ws.ahc.AhcWSClientConfigFactory;
import play.api.libs.ws.ssl.SSLConfigFactory;
import play.libs.ws.WSClient;
import scala.concurrent.duration.Duration;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Singleton
public class Router {
    private Materializer materializer;

    @Inject
    public Router(Materializer materializer) {
        this.materializer = materializer;
    }

    private WSClient proxylessClient;
    public WSClient getProxylessClient() {
        if ( proxylessClient != null ) {
            return proxylessClient;
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
        AsyncHttpClientConfig.AdditionalChannelInitializer logging = new AsyncHttpClientConfig.AdditionalChannelInitializer() {
            @Override
            public void initChannel(io.netty.channel.Channel channel) throws IOException {
                channel.pipeline().addFirst("log", new io.netty.handler.logging.LoggingHandler("debug"));
            }
        };
        ahcBuilder.setHttpAdditionalChannelInitializer(logging);
        proxylessClient = new play.libs.ws.ahc.AhcWSClient(ahcBuilder.build(), materializer);
        return proxylessClient;
    }
}
