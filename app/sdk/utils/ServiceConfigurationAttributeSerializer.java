package sdk.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import sdk.data.ServiceConfigurationAttribute;

import java.io.IOException;

/**
 * Created by Orozco on 8/15/17.
 */


public class ServiceConfigurationAttributeSerializer extends JsonSerializer<ServiceConfigurationAttribute> {
    @Override
    public void serialize(ServiceConfigurationAttribute value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        boolean isListItem = value.isListItemConfiguration();
        JsonNode node = Json.newObject();
        if (isListItem) {
            ((ObjectNode) node).put("label", value.getName());
            ((ObjectNode) node).put("attributeIndex", value.getAttributeIndex());
            ((ObjectNode) node).put("attributeType", value.getAttributeType().toString());
        } else {
            ((ObjectNode) node).put("name", value.getName());
            ((ObjectNode) node).put("attributeIndex", value.getAttributeIndex());
            ((ObjectNode) node).put("attributeType", value.getAttributeType().toString());
            ((ObjectNode) node).put("create", value.create);
            ((ObjectNode) node).put("createRequired", value.createRequired);
            ((ObjectNode) node).put("update", value.update);
            ((ObjectNode) node).put("updateRequired", value.updateRequired);
            ((ObjectNode) node).put("search", value.search);
            ((ObjectNode) node).put("searchRequired", value.searchRequired);
        }
        mapper.writeTree(gen, node);
    }
}
