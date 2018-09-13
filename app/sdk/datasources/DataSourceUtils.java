package sdk.datasources;

import sdk.utils.AuthenticationInfo;
import sdk.utils.Constants;

import java.lang.reflect.ParameterizedType;

public class DataSourceUtils {


    /**
     * The class passed to this function must be a Typed class
     * This method is meant to get the parameter type of a parameterized class
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Class<T> getDataSourceType(Class clazz) {
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }


    /**
     * returns a new instance of the given class
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getNewInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Gets appId string from request headers
     *
     * @param info
     * @return
     */
    public static String getAppId(AuthenticationInfo info) {
        return info.getCustomAuthenticationParameter(Constants.APP_ID_HEADER);
    }
}
