package sdk.user;

import sdk.models.AttributeType;

/**
 * Created by alexis on 9/14/16.
 */
public class UserInfoKey {
    public String name;
    public String id;
    public AttributeType attributeType;

    public UserInfoKey(String name, AttributeType attributeType) {
        this.name = name;
        this.attributeType = attributeType;
    }
}
