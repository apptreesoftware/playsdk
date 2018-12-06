package sdk.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Relationship {
    String value() default "";

    boolean eager() default true;

    int index();

    String name() default "";

    boolean useGetterAndSetter() default true;

    boolean canCreate() default true;

    boolean canCreateAndRequired() default false;

    boolean canUpdate() default true;

    boolean canUpdateAndRequired() default false;

    boolean canSearch() default true;

    boolean canSearchAndRequired() default false;

    // ignore read ignores this relationship when reading
    // from a data set to our model object
    boolean ignoreRead() default false;

    // ignore write ignore writing this relationship when writing our model
    // object to a data set item
    boolean ignoreWrite() default false;
}
