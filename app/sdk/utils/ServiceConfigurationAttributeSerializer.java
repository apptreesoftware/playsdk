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
import sdk.models.AttributeType;

import java.io.IOException;

/**
 * Created by Orozco on 8/15/17.
 */


public class ServiceConfigurationAttributeSerializer extends JsonSerializer<ServiceConfigurationAttribute> {
    @Override
    public void serialize(ServiceConfigurationAttribute value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        boolean isListItem = value.isListItemConfiguration();
        ObjectNode node = Json.newObject();
        if (isListItem) {
            node.put((isListItem && !value.getAttributeType().equals(AttributeType.ListItem))?"label":"name", value.getName());
            node.put("attributeIndex", value.getAttributeIndex());
            node.put("attributeType", value.getAttributeType().toString());
            node.put("relatedListServiceConfiguration", Json.toJson(value.getRelatedListServiceConfiguration()));
            node.put("create", value.create);
            node.put("createRequired", value.createRequired);
            node.put("update", value.update);
            node.put("updateRequired", value.updateRequired);
            node.put("search", value.search);
            node.put("searchRequired", value.searchRequired);
        } else {
            node.put("name", value.getName());
            node.put("relatedService", Json.toJson(value.getRelatedService()));
            node.put("attributeIndex", value.getAttributeIndex());
            node.put("attributeType", value.getAttributeType().toString());
            node.put("create", value.create);
            node.put("createRequired", value.createRequired);
            node.put("update", value.update);
            node.put("updateRequired", value.updateRequired);
            node.put("search", value.search);
            node.put("searchRequired", value.searchRequired);
        }
        mapper.writeTree(gen, node);
    }
}
