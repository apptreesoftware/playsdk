package sdk.utils;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by Orozco on 8/31/17.
 */
public class ValidationUtils {

    public static<T> Collection<T> safe(Collection<T> collection){
        return (collection == null)? Collections.EMPTY_LIST:collection;
    }


}
