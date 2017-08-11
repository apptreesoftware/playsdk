package sdk.utils;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Orozco on 8/10/17.
 */
public class ClassUtils {
    private static Set<String> parameterizedTypeSet = new HashSet<>();

    static {
        parameterizedTypeSet.add("List");
    }


    public static <T> boolean Null(T valueToCheck) {
        return valueToCheck == null;
    }


    public static <T> boolean Null(T... valuesToCheck) {
        for (T value : valuesToCheck) {
            if (value == null) return true;
        }
        return false;
    }


    public static boolean isParameterizedType(Class clazz) {
        String simpleName = clazz.getSimpleName();
        return parameterizedTypeSet.contains(simpleName);
    }

    public static Class getParameterizedType(ParameterizedType type) {
        Class<?> wrappedClass = (Class<?>) type.getActualTypeArguments()[0];
        return wrappedClass;
    }


}
