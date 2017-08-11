package sdk.converter;

import sdk.annotations.Attribute;
import sdk.annotations.PrimaryKey;
import sdk.annotations.PrimaryValue;
import sdk.utils.ClassUtils;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Orozco on 8/2/17.
 */
public class AttributeProxy {
    private Field currentField;
    private Method currentMethod;
    public boolean isField;
    public boolean isWrappedClass;


    public AttributeProxy(Method currentMethod) {
        isField = false;
        this.currentMethod = currentMethod;
        isWrappedClass = checkIfIsWrappedType((Class) currentMethod.getReturnType());
    }

    public AttributeProxy(Field currentField) {
        isField = true;
        this.currentField = currentField;
        isWrappedClass = checkIfIsWrappedType((Class) currentField.getType());
    }

    public Attribute getAttributeAnnotation() {
        if (isField) {
            return currentField.getAnnotation(Attribute.class);
        } else {
            return currentMethod.getAnnotation(Attribute.class);
        }
    }

    public PrimaryKey getPrimaryKeyAnnotation() {
        if (isField) {
            return currentField.getAnnotation(PrimaryKey.class);
        } else {
            return currentMethod.getAnnotation(PrimaryKey.class);
        }
    }


    public PrimaryValue getValueAnnotation() {
        if (isField) {
            return currentField.getAnnotation(PrimaryValue.class);
        } else {
            return currentMethod.getAnnotation(PrimaryValue.class);
        }
    }

    public <T> Object getValue(T sourceObject) throws IllegalAccessException, InvocationTargetException {
        if (isField) {
            return currentField.get(sourceObject);
        } else {
            return currentMethod.invoke(sourceObject);
        }
    }


    public <T> void setValue(T destination, Object value) throws InvocationTargetException, IllegalAccessException {
        if (isField) {
            currentField.set(destination, value);
        } else {
            currentMethod.invoke(destination, value);
        }
    }


    public Class getType() {
        if (isField) {
            return (!isWrappedClass) ?
                    currentField.getType() :
                    ClassUtils.getParameterizedType((ParameterizedType) currentField.getGenericType());
        } else {
            return (!isWrappedClass) ?
                    currentMethod.getReturnType() :
                    ClassUtils.getParameterizedType((ParameterizedType) currentMethod.getGenericReturnType());
        }
    }

    public String getName() {
        if (isField) {
            return currentField.getName();
        } else {
            return currentMethod.getName();
        }
    }

    public String getDataTypeName() {
        if (isField) {
            return currentField.getType().getSimpleName();
        } else {
            return currentMethod.getReturnType().getSimpleName();
        }
    }

    private boolean checkIfIsWrappedType(Class clazz) {
        return ClassUtils.isParameterizedType(clazz);
    }


}
