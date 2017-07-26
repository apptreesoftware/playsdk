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
}

