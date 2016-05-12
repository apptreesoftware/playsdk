package sdk.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import sdk.data.DataSet;

import java.io.IOException;

/**
 * Created by matthew on 5/5/16.
 */
public class DataSetModule extends SimpleModule {
    private class DataSetJsonSerializer extends JsonSerializer<DataSet> implements ContextualDeserializer {

        @Override
        public void serialize(DataSet value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeRawValue(value.toJSON().toString());
        }

        @Override
        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
            return null;
        }
    }

    public DataSetModule() {
        addSerializer(DataSet.class, new DataSetJsonSerializer());
    }
}