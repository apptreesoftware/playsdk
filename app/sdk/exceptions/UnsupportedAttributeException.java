package sdk.exceptions;

import sdk.models.AttributeType;

/**
 * Created by Orozco on 7/19/17.
 */
public class UnsupportedAttributeException extends Exception {
    private Class classType;
    private AttributeType attributeType;

    public UnsupportedAttributeException(Class classType, AttributeType attributeType) {
        super(String.format("Class type of %s is not supported for attribute type %s", classType.toString(), attributeType.toString()));
        this.classType = classType;
        this.attributeType = attributeType;
    }

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }
}
