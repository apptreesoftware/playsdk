package sdk.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Orozco on 9/14/17.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ParentValue {
    boolean value() default true;
}
