package sdk.converter;

import sdk.annotations.Attribute;
import sdk.annotations.PrimaryKey;
import sdk.annotations.PrimaryValue;
import sdk.annotations.Relationship;
import sdk.utils.ClassUtils;

import java.lang.reflect.*;

import static sdk.utils.ClassUtils.Null;

/**
 * Created by Orozco on 8/2/17.
 */
public class AttributeProxy {
    private Field currentField;
    private Method currentMethod;
    public boolean isField;
    public boolean isWrappedClass;
    PrimaryValue primaryValue;
    PrimaryKey primaryKey;
    Attribute attribute;
    Relationship relationship;

    public AttributeProxy(Method currentMethod) {
        isField = false;
        this.currentMethod = currentMethod;
        isWrappedClass = checkIfIsWrappedType((Class) currentMethod.getReturnType());
        hydrateAnnotations(currentMethod);
    }

    public AttributeProxy(Field currentField) {
        isField = true;
        this.currentField = currentField;
        isWrappedClass = checkIfIsWrappedType((Class) currentField.getType());
        hydrateAnnotations(currentField);
    }

    public Attribute getAttributeAnnotation() {
        return attribute;
    }


    public Relationship getRelationshipAnnotation() {
        return relationship;
    }

    public PrimaryKey getPrimaryKeyAnnotation() {
        return primaryKey;
    }


    public PrimaryValue getValueAnnotation() {
        return primaryValue;
    }


    public int getIndex() {
        if (isRelationship()) {
            return relationship.index();
        }
        return !Null(attribute) ?
                attribute.index() :
                0;
    }

    public boolean isAttribute() {
        return !Null(attribute);
    }

    public boolean isPrimaryKey() {
        return !Null(primaryKey);
    }

    public boolean isPrimaryValue() {
        return !Null(primaryValue);
    }

    public boolean useSetterAndGetter() {
        if (isRelationship()) {
            return relationship.useGetterAndSetter();
        }
        return !Null(attribute) && attribute.useGetterAndSetter();
    }

    public boolean excludeFromList(){
        if(isRelationship()) {
            return true;
        }
        return !Null(attribute) && attribute.excludeFromList();
    }


    public boolean useLazyLoad(){
        if(isRelationship()) {
            return !relationship.eager();
        }
        return false;
    }

    public <T> Object getValue(T sourceObject) throws IllegalAccessException, InvocationTargetException {
        if (isField) {
            if(!currentField.isAccessible()) {
                currentField.setAccessible(true);
            }
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

    public boolean isRelationship() {
        return (relationship != null);
    }

    private boolean checkIfIsWrappedType(Class clazz) {
        return ClassUtils.isParameterizedType(clazz);
    }


    private void hydrateAnnotations(AccessibleObject object) {
        if(Null(object)) return;
        primaryKey = object.getAnnotation(PrimaryKey.class);
        primaryValue = object.getAnnotation(PrimaryValue.class);
        attribute = object.getAnnotation(Attribute.class);
        relationship = object.getAnnotation(Relationship.class);
    }

}
