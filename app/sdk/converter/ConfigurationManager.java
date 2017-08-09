package sdk.converter;

import sdk.annotations.CustomLocation;
import sdk.models.AttributeType;

/**
 * Author: Karis Sponsler
 * Date: 8/8/17
 */
public class ConfigurationManager {
    public static ConverterAttributeType getConverterAttributeType(Class clazz) {
        switch(clazz.getSimpleName()) {
            case "String":
                return new ConverterAttributeType(AttributeType.String, true);
            case "Double":
            case "Float":
                return new ConverterAttributeType(AttributeType.Double, true);
            case "float":
            case "double":
                return new ConverterAttributeType(AttributeType.Double, false);
            case "Integer":
                return new ConverterAttributeType(AttributeType.Int, true);
            case "int":
                return new ConverterAttributeType(AttributeType.Int, false);
            case "Date":
            case "DateTime":
                return new ConverterAttributeType(AttributeType.DateTime, true);
            case "Boolean":
                return new ConverterAttributeType(AttributeType.Boolean, true);
            case "boolean":
                return new ConverterAttributeType(AttributeType.Boolean, false);
            case "ArrayList":
            case "List":
                return new ConverterAttributeType(AttributeType.Relation, true);
            case "Location":
                return new ConverterAttributeType(AttributeType.Location, true);
        }
        if(CustomLocation.class.isAssignableFrom(clazz)) return new ConverterAttributeType(AttributeType.Location, true);
        return new ConverterAttributeType(AttributeType.ListItem, true);
    }
}
