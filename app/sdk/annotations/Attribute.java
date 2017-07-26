package sdk.annotations;


import sdk.models.AttributeType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Orozco on 7/19/17.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute {
    int index();
    String name() default "";
    AttributeType dataType() default AttributeType.None;
    Class relationShipClass() default Class.class;
    boolean primaryKey() default false;
    boolean canCreate() default true;
    boolean canCreateAndRequired() default false;
    boolean canUpdate() default true;
    boolean canUpdateAndRequired() default false;
    boolean canSearch() default true;
    boolean canSearchAndRequired() default false;
}

