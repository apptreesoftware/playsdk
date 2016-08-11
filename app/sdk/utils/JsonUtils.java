package sdk.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import sdk.serializers.DataSetModule;
import sdk.serializers.DateTimeModule;

import java.util.Optional;

/**
 * Created by Matthew Smith on 5/10/16.
 * Copyright AppTree Software, Inc.
 */
public class JsonUtils {
    public static Optional<String> string(ObjectNode json, String fieldName) {
        JsonNode node = json.findValue(fieldName);
        if ( node != null ) {
            return Optional.ofNullable(node.textValue());
        }
        return Optional.empty();
    }

    public static Optional<JsonNode> parseOptional(String src) {
        if ( src == null ) return Optional.empty();
        try {
            return Optional.of(Json.parse(src));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    private static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if ( objectMapper == null ) {
            objectMapper = Json.newDefaultMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.registerModule(new DateTimeModule());
            objectMapper.registerModule(new DataSetModule());
        }
        return objectMapper;
    }

    public static <A> A fromJson(JsonNode json, Class<A> clazz) {
        try {
            return getObjectMapper().treeToValue(json, clazz);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode toJson(final Object data) {
        try {
            return getObjectMapper().valueToTree(data);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
