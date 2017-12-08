package sdk.utils;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by Orozco on 8/31/17.
 */
public class ValidationUtils {

    public static <T> Collection<T> safe(Collection<T> collection) {
        return (collection == null) ? Collections.EMPTY_LIST : collection;
    }

    public static <T> boolean    NullOrEmpty(Collection<T> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean NullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static <T> boolean anyNull(T... t) {
        for (T tempT : t) {
            if (tempT == null) return true;
        }
        return false;
    }




}
