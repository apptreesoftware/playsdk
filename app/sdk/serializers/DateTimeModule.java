package sdk.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.joda.time.DateTime;
import sdk.utils.Constants;
import sdk.utils.DateUtil;

import java.io.IOException;

/**
 * Created by mellissatort on 5/25/16.
 */
public class DateTimeModule extends SimpleModule {
    private class DateTimeDeserializer extends JsonDeserializer<DateTime> implements ContextualDeserializer {

        @Override
        public DateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String dateString = p.getValueAsString();
            DateTime date = DateUtil.dateTimeFromString(dateString);
            if ( date == null ) {
                date = DateUtil.dateFromString(dateString);
            }
            return date;
        }

        @Override
        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
            return new DateTimeDeserializer();
        }
    }

    private class DateTimeSerializer extends JsonSerializer<DateTime> implements ContextualSerializer {

        @Override
        public void serialize(DateTime value, JsonGenerator gen, SerializerProvider arg2) throws IOException, JsonProcessingException {
            gen.writeString(Constants.AppTreeDateTimeFormat.print(value));
        }

        @Override
        public JsonSerializer<DateTime> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
            return new DateTimeSerializer();
        }
    }

    public DateTimeModule() {
        addDeserializer(DateTime.class, new DateTimeDeserializer());
        addSerializer(DateTime.class, new DateTimeSerializer());
    }
}
