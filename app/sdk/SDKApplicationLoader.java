package sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import play.Logger;
import play.api.inject.guice.GuiceApplicationBuilder;
import play.api.inject.guice.GuiceApplicationLoader;
import play.libs.Json;
import sdkmodels.serializers.DataSetModule;
import sdkmodels.serializers.DateTimeModule;

/**
 * Created by Matthew on 5/31/2016.
 */
public class SDKApplicationLoader extends GuiceApplicationLoader {
    @Override
    public GuiceApplicationBuilder builder(Context context) {
        Logger.debug("Loading SDKApplicationLoader");
        ObjectMapper mapper = Json.newDefaultMapper();
        mapper.registerModule(new DataSetModule());
        mapper.registerModule(new DateTimeModule());
        Json.setObjectMapper(mapper);
        return super.builder(context);
    }
}
