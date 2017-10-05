package sdk.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Orozco on 7/26/17.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
    boolean value() default true;
}
