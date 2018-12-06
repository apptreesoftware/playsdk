package sdk.annotations;


import sdk.models.AttributeType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Supplier;

/**
 * Created by Orozco on 7/19/17.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute {
    int index();

    String name() default "";

    AttributeType dataType() default AttributeType.None;

    Class relationshipClass() default Class.class;

    boolean excludeFromList() default false;

    boolean useGetterAndSetter() default true;

    boolean canCreate() default true;

    boolean canCreateAndRequired() default false;

    boolean canUpdate() default true;

    boolean canUpdateAndRequired() default false;

    boolean canSearch() default true;

    boolean canSearchAndRequired() default false;

    // ignore read ignores this attribute when reading
    // from a data set to our model object
    boolean ignoreRead() default false;

    // ignore write ignore writing this attribute when writing our model
    // object to a data set item
    boolean ignoreWrite() default false;
}

