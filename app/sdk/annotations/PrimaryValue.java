package sdk.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Orozco on 8/2/17.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryValue {
    boolean value() default true;
}
