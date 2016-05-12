package sdk.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import java.util.Optional;

/**
 * Created by Matthew Smith on 5/10/16.
 * Copyright AppTree Software, Inc.
 */
public class JSON {
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
}
