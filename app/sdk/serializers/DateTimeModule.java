package sdk.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.joda.time.DateTime;
import sdk.data.DataSet;
import sdk.utils.DateUtil;

import java.io.IOException;

/**
 * Created by mellissatort on 5/25/16.
 */
public class DateTimeModule extends SimpleModule {
    private class DateTimeSerializer extends JsonDeserializer<DateTime> implements ContextualDeserializer {

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
            return new DateTimeSerializer();
        }
    }

    public DateTimeModule() {
        addDeserializer(DateTime.class, new DateTimeSerializer());
    }
}
