package sdk.data;

import sdk.models.AttributeType;

/**
 * Created by Orozco on 7/21/17.
 */
public class AttributeMeta {
    private AttributeType attributeType;
    private Integer attributeIndex;

    public AttributeMeta(AttributeType attributeType, Integer attributeIndex) {
        this.setAttributeType(attributeType);
        this.setAttributeIndex(attributeIndex);
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public Integer getAttributeIndex() {
        return attributeIndex;
    }

    public void setAttributeIndex(Integer attributeIndex) {
        this.attributeIndex = attributeIndex;
    }
}
