package sdk.converter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Orozco on 8/2/17.
 */
public class ConfigurationWrapper{
    String varName;
    String dataTypeName;
    Class clazz;
    boolean isWrappedClass;

    public ConfigurationWrapper(String varName, String dataTypeName, Class clazz) {
        this.varName = varName;
        this.dataTypeName = dataTypeName;
        this.clazz = clazz;
    }

    public ConfigurationWrapper(Field field){
        this.varName = field.getName();
        this.dataTypeName = field.getType().getSimpleName();
        this.clazz = field.getClass();
    }

    public ConfigurationWrapper(Method method){
        this.varName = method.getName();
        this.dataTypeName = method.getReturnType().getSimpleName();
        this.clazz = method.getReturnType();
    }

    public ConfigurationWrapper(AttributeProxy attributeProxy){
        this.varName = attributeProxy.getName();
        this.dataTypeName = attributeProxy.getType().getSimpleName();
        this.clazz = attributeProxy.getType();
        this.isWrappedClass = attributeProxy.isWrappedClass;
    }


}