package sdk.converter;

import sdk.annotations.*;
import sdk.utils.ClassUtils;

import java.lang.reflect.*;
import java.util.Optional;

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
    ParentValue parentValue;

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

    public ParentValue getParentValueAnnotation() {
        return parentValue;
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


    public Integer getIndex() {
        if (isRelationship()) {
            return relationship.index();
        }
        return !Null(attribute) ?
                attribute.index() :
                null;
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

    public boolean isParentValue() {
        return !Null(parentValue);
    }

    public boolean useSetterAndGetter() {
        if (isRelationship()) {
            return relationship.useGetterAndSetter();
        }
        return !Null(attribute) && attribute.useGetterAndSetter();
    }

    public boolean excludeFromList() {
        if (isRelationship()) {
            return true;
        }
        return !Null(attribute) && attribute.excludeFromList();
    }


    public boolean useLazyLoad() {
        if (isRelationship()) {
            return !relationship.eager();
        }
        return false;
    }

    public <T> Object getValue(T sourceObject) throws IllegalAccessException, InvocationTargetException {
        if (isField) {
            if (!currentField.isAccessible()) {
                currentField.setAccessible(true);
            }
            return currentField.get(sourceObject);
        } else {
            if (!currentMethod.isAccessible()) {
                currentMethod.setAccessible(true);
            }
            return currentMethod.invoke(sourceObject);
        }
    }


    public <T> void setValue(T destination, Object value) throws InvocationTargetException, IllegalAccessException {
        if (isField) {
            if (!currentField.isAccessible()) {
                currentField.setAccessible(true);
            }
            currentField.set(destination, value);
        } else {
            currentMethod.invoke(destination, value);
        }
    }


    public <T> void setPrimaryKeyOrValue(T destination, String value) throws IllegalAccessException, InvocationTargetException {
        //if the primary key or value is null we don't want to set it
        if (value == null) return;

        if (isField) {
            Class clazz = currentField.getType();
            if (String.class == clazz) {
                if (!currentField.isAccessible()) {
                    currentField.setAccessible(true);
                }
                currentField.set(destination, value);
            } else if (clazz == Integer.class || clazz == int.class) {
                if (!currentField.isAccessible()) {
                    currentField.setAccessible(true);
                }
                int intValue = 0;
                try {
                    intValue = Integer.parseInt(value);
                } catch (Exception e) {
                    intValue = 0;
                }
                currentField.set(destination, intValue);
            } else if (clazz == Long.class || clazz == long.class) {
                if (!currentField.isAccessible()) {
                    currentField.setAccessible(true);
                }
                long longValue = 0;
                try {
                    longValue = Long.parseLong(value);
                } catch (Exception e) {
                    longValue = 0;
                }
                currentField.set(destination, longValue);
            } else {
                throw new RuntimeException("Primary Key Must be an Integer, Long or String data type");
            }
        } else {
            Method method = TypeManager.getSetterForAttributeProxy(this, destination);
            if (method == null) return;
            currentMethod = method;
            Class clazz = currentMethod.getParameters()[0].getType();

            if (String.class == clazz) {
                if (!currentMethod.isAccessible()) {
                    currentMethod.setAccessible(true);
                }
                currentMethod.invoke(destination, value);
            } else if (clazz == Integer.class || clazz == int.class) {
                if (!currentMethod.isAccessible()) {
                    currentMethod.setAccessible(true);
                }
                Integer intValue = Integer.parseInt(value);
                currentMethod.invoke(destination, intValue);
            } else if (clazz == Long.class || clazz == long.class) {
                if (!currentMethod.isAccessible()) {
                    currentMethod.setAccessible(true);
                }
                Long longValue = Long.parseLong(value);
                currentMethod.invoke(destination, longValue);
            } else {
                throw new RuntimeException("Primary Key Must be an Integer, Long or String data type");
            }
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
        if (Null(object)) return;
        primaryKey = object.getAnnotation(PrimaryKey.class);
        primaryValue = object.getAnnotation(PrimaryValue.class);
        attribute = object.getAnnotation(Attribute.class);
        relationship = object.getAnnotation(Relationship.class);
        parentValue = object.getAnnotation(ParentValue.class);
    }

}
