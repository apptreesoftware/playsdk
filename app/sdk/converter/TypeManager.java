package sdk.converter;

import org.joda.time.DateTime;
import play.Logger;
import sdk.annotations.*;
import sdk.converter.attachment.ApptreeAttachment;
import sdk.converter.attachment.Attachment;
import sdk.models.AttributeType;
import sdk.models.Color;
import sdk.models.Image;
import sdk.models.Location;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: Karis Sponsler
 * Date: 8/11/17
 */
public class TypeManager {
    private final static Map<AttributeType, List<Class>> supportedTypeMap;
    private static Map<String, Map<String, Method>> methodMap;

    /**
     *  Static map containing AttributeType -> Class supported types
     */
    static {
        supportedTypeMap = new HashMap<AttributeType, List<Class>>() {{
            ArrayList<Class> stringClasses = new ArrayList<>();
            stringClasses.add(String.class);
            stringClasses.add(Integer.class);
            stringClasses.add(Float.class);
            put(AttributeType.String, stringClasses);

            ArrayList<Class> integerClasses = new ArrayList<>();
            integerClasses.add(Integer.class);
            integerClasses.add(int.class);
            put(AttributeType.Int, integerClasses);

            ArrayList<Class> doubleClasses = new ArrayList<>();
            doubleClasses.add(Double.class);
            doubleClasses.add(double.class);
            doubleClasses.add(Float.class);
            doubleClasses.add(float.class);
            doubleClasses.add(Integer.class);
            doubleClasses.add(int.class);
            put(AttributeType.Double, doubleClasses);

            ArrayList<Class> boolClasses = new ArrayList<>();
            boolClasses.add(Boolean.class);
            boolClasses.add(boolean.class);
            put(AttributeType.Boolean, boolClasses);

            ArrayList<Class> dateClasses = new ArrayList<>();
            dateClasses.add(java.util.Date.class);
            dateClasses.add(org.joda.time.DateTime.class);
            dateClasses.add(java.sql.Date.class);
            put(AttributeType.Date, dateClasses);

            ArrayList<Class> dateTimeClasses = new ArrayList<>();
            dateTimeClasses.add(java.util.Date.class);
            dateTimeClasses.add(org.joda.time.DateTime.class);
            dateTimeClasses.add(java.sql.Date.class);
            put(AttributeType.DateTime, dateTimeClasses);

            ArrayList<Class> locationClasses = new ArrayList<>();
            locationClasses.add(Location.class);
            locationClasses.add(CustomLocation.class);
            put(AttributeType.Location, locationClasses);

            ArrayList<Class> colorClasses = new ArrayList();
            colorClasses.add(Color.class);
            put(AttributeType.Color, colorClasses);

            ArrayList<Class> attachmentClasses = new ArrayList<>();
            attachmentClasses.add(Attachment.class);
            attachmentClasses.add(ApptreeAttachment.class);
            put(AttributeType.Attachments, attachmentClasses);

            ArrayList<Class> imageClasses = new ArrayList<>();
            imageClasses.add(Image.class);
            put(AttributeType.Image, imageClasses);

            ArrayList<Class> dateRangeClasses = new ArrayList<>();
            dateRangeClasses.add(Date.class);
            dateRangeClasses.add(DateTime.class);
            put(AttributeType.DateTimeRange, dateRangeClasses);
            put(AttributeType.DateRange, dateRangeClasses);

            ArrayList<Class> timeInterval = new ArrayList<>();
            timeInterval.add(Long.class);
            timeInterval.add(long.class);
            timeInterval.add(Double.class);
            timeInterval.add(double.class);
            timeInterval.add(Integer.class);
            timeInterval.add(int.class);
            put(AttributeType.TimeInterval, timeInterval);

        }};
    }

    /**
     * @param dataTypeName
     * @return
     */
    protected static ConverterAttributeType inferDataType(String dataTypeName) {
        return findTypeOnSimpleName(dataTypeName);
    }

    /**
     * @param clazz
     * @return
     */
    protected static ConverterAttributeType inferDataType(Class clazz) {
        if (CustomLocation.class.isAssignableFrom(clazz))
            return new ConverterAttributeType(AttributeType.Location, true);
        if (ApptreeAttachment.class.isAssignableFrom(clazz))
            return new ConverterAttributeType(AttributeType.Attachments, true);
        return findTypeOnSimpleName(clazz.getSimpleName());
    }

    protected static ConverterAttributeType findTypeOnSimpleName(String name) {
        switch (name) {
            case "String":
                return new ConverterAttributeType(AttributeType.String, true);
            case "Double":
            case "Float":
                return new ConverterAttributeType(AttributeType.Double, true);
            case "float":
            case "double":
                return new ConverterAttributeType(AttributeType.Double, false);
            case "Long":
                return new ConverterAttributeType(AttributeType.Int, true);
            case "long":
                return new ConverterAttributeType(AttributeType.Int, false);
            case "Integer":
                return new ConverterAttributeType(AttributeType.Int, true);
            case "int":
                return new ConverterAttributeType(AttributeType.Int, false);
            case "Date":
            case "DateTime":
                return new ConverterAttributeType(AttributeType.DateTime, true);
            case "Boolean":
                return new ConverterAttributeType(AttributeType.Boolean, true);
            case "boolean":
                return new ConverterAttributeType(AttributeType.Boolean, false);
            case "ArrayList":
            case "List":
                return new ConverterAttributeType(AttributeType.Relation, true);
            case "Location":
                return new ConverterAttributeType(AttributeType.Location, true);
            case "Color":
                return new ConverterAttributeType(AttributeType.Color, true);
            case "Attachments":
                return new ConverterAttributeType(AttributeType.Attachments, true);
            case "Image":
                return new ConverterAttributeType(AttributeType.Image, true);
            default:
                return new ConverterAttributeType(AttributeType.ListItem, true);
        }
    }

    /**
     * @param sourceObject
     * @param <T>
     */
    protected static <T> void mapMethodsFromSource(T sourceObject) {
        if (sourceObject == null) return;
        String className = sourceObject.getClass().getName();
        Map<String, Method> methodMap = Arrays.stream(sourceObject.getClass().getDeclaredMethods()).distinct().collect(Collectors.toMap(method -> method.getName().toLowerCase(), method -> method, ((method, method2) -> method)));
        getMethodMap().put(className, methodMap);
    }

    /**
     * @return
     */
    public static Map<String, Map<String, Method>> getMethodMap() {
        if (methodMap == null) {
            methodMap = new HashMap<>();
        }
        return methodMap;
    }

    /**
     * @return
     */
    protected static Map<AttributeType, List<Class>> getSupportedTypeMap() {
        return supportedTypeMap;
    }

    /**
     * @param tempMethodMap
     */
    public static void setMethodMap(Map<String, Map<String, Method>> tempMethodMap) {
        methodMap = tempMethodMap;
    }

    /**
     * @param clazz
     * @return
     */
    protected static List<AttributeProxy> getMethodAndFieldAnnotationsForClass(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();
        List<AttributeProxy> attributeProxies = new ArrayList<>();
        attributeProxies.addAll(Arrays.stream(fields)
                .filter(field ->
                        (field.getAnnotation(Attribute.class) != null
                                || field.getAnnotation(PrimaryKey.class) != null
                                || field.getAnnotation(PrimaryValue.class) != null)
                                || field.getAnnotation(Relationship.class) != null)
                .map(field -> new AttributeProxy(field))
                .collect(Collectors.toList()));

        attributeProxies.addAll(Arrays.stream(methods)
                .filter(method ->
                        (method.getAnnotation(Attribute.class) != null
                                || method.getAnnotation(PrimaryKey.class) != null
                                || method.getAnnotation(PrimaryValue.class) != null)
                                || method.getAnnotation(Relationship.class) != null)
                .map(field -> new AttributeProxy(field))
                .collect(Collectors.toList()));
        if(clazz.getSuperclass() != null)
            attributeProxies.addAll(getMethodAndFieldAnnotationsForClass(clazz.getSuperclass()));
        Map<Integer, Boolean> findDuplicateIndex = new HashMap<>();
        boolean primaryKeyIsSet = false;
        for(AttributeProxy proxy : attributeProxies) {
            if(proxy.isPrimaryKey()) {
                if(primaryKeyIsSet) throw new RuntimeException("field named '" + proxy.getName() + "' redefines a primary key");
                else primaryKeyIsSet = true;
            }
            if(proxy.isAttribute() && findDuplicateIndex.putIfAbsent(proxy.getIndex(), true) != null) {
                throw new RuntimeException("field named '" + proxy.getName() + "' shares an index with another field in the same model");
            }
        }
        return attributeProxies;
    }

    /**
     * @param classType
     * @param type
     * @return
     */
    protected static boolean isFieldClassSupportedForType(Class<?> classType, AttributeType type) {
        if ((type.equals(AttributeType.ListItem)
                || type.equals(AttributeType.Relation)
                || type.equals(AttributeType.SingleRelationship))
                || type.equals(AttributeType.Attachments)
                || type.equals(AttributeType.Location)
                || type.equals(AttributeType.Image)
                && !isPrimitiveDataTypeOrWrapper(classType)) {
            return true;
        }
        List<Class> classList = getSupportedTypeMap().get(type);
        return classList != null && (classList.contains(classType) || classList.contains(classType.getSuperclass()));
    }

    /**
     * @param clazz
     * @return
     */
    protected static boolean isPrimitiveDataTypeOrWrapper(Class clazz) {
        if (clazz.isPrimitive()) return true;
        switch (clazz.getSimpleName()) {
            case "Integer":
            case "Byte":
            case "Short":
            case "Long":
            case "Boolean":
            case "Character":
            case "Float":
            case "Double":
                return true;
            default:
                return false;
        }
    }

    /**
     * @param clazz
     * @return
     */
    protected static boolean fieldIsFloat(Class clazz) {
        return (clazz.getName().contains("float") || clazz.getName().contains("Float"));
    }

    /**
     * @param attributeProxy
     * @param object
     * @param <T>
     * @return
     */
    protected static <T> boolean fieldHasGetter(AttributeProxy attributeProxy, T object) {
        if (!attributeProxy.isField) return false;
        Map<String, Method> tempMap = getMethodMap().get(object.getClass().getName());
        if (tempMap == null) return false;
        return (tempMap.containsKey(getterMethodName(attributeProxy.getName())));
    }

    /**
     * @param proxy
     * @param object
     * @param <T>
     * @return
     */
    protected static <T> boolean fieldHasSetter(AttributeProxy proxy, T object) {
        Map<String, Method> tempMap = getMethodMap().get(object.getClass().getName());
        if (tempMap == null) return false;
        return (tempMap.containsKey(setterMethodName(proxy)));
    }

    /**
     * @param destination
     * @param proxy
     * @param value
     * @param <T>
     */
    protected static <T> void useSetter(T destination, AttributeProxy proxy, Object value) {
        Map<String, Method> tempMethodMap = getMethodMap().get(destination.getClass().getName());
        if (tempMethodMap == null) return;
        Method setterMethod = tempMethodMap.get(setterMethodName(proxy));
        if (!setterMethod.isAccessible()) {
            setterMethod.setAccessible(true);
        }
        try {
            setterMethod.invoke(destination, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param attributeProxy
     * @param object
     * @param <T>
     * @return
     */
    protected static <T> Object useGetterIfExists(AttributeProxy attributeProxy, T object) {
        Map<String, Method> tempMethodMap = getMethodMap().get(object.getClass().getName());
        if (tempMethodMap == null) return null;
        Method getterMethod = tempMethodMap.get(getterMethodName(attributeProxy.getName()));
        try {
            if (getterMethod == null) return attributeProxy.getValue(object);
            if (!getterMethod.isAccessible()) {
                getterMethod.setAccessible(true);
            }
            return getterMethod.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param name
     * @return
     */
    protected static String getterMethodName(String name) {
        return new StringBuilder("get")
                .append(name).toString().toLowerCase();
    }

    /**
     * @param proxy
     * @return
     */
    protected static String setterMethodName(AttributeProxy proxy) {
        if (proxy.isField) {
            return new StringBuilder("set")
                    .append(proxy.getName()).toString().toLowerCase();
        } else {
            return proxy.getName().replace("get", "set");
        }
    }

    /**
     * @param attributeProxy
     * @param destination
     * @param value
     * @param <T>
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    protected static <T> void useSetterIfExists(AttributeProxy attributeProxy, T destination, Object value) throws InvocationTargetException, IllegalAccessException {
        if (fieldHasSetter(attributeProxy, destination)) {
            useSetter(destination, attributeProxy, value);
        } else {
            if (attributeProxy.isField) {
                attributeProxy.setValue(destination, value);
            }
        }
    }
}
