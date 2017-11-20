package sdk.converter;

import sdk.models.AttributeType;

/**
 * Created by Orozco on 7/21/17.
 */
public class ConverterAttributeType {
    private AttributeType attributeType;
    private boolean isOptional;

    public ConverterAttributeType(AttributeType attributeType, boolean isOptional) {
        this.setAttributeType(attributeType);
        this.setOptional(isOptional);
    }


    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public void setOptional(boolean optional) {
        isOptional = optional;
    }
}
