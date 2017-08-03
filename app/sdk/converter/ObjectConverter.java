package sdk.converter;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import scala.annotation.meta.field;
import sdk.annotations.Attribute;
import sdk.annotations.PrimaryKey;
import sdk.annotations.PrimaryValue;
import sdk.data.*;
import sdk.exceptions.UnableToWriteException;
import sdk.exceptions.UnsupportedAttributeException;
import sdk.list.ListItem;
import sdk.list.ListServiceConfiguration;
import sdk.list.ListServiceConfigurationAttribute;
import sdk.models.AttributeType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Orozco on 7/19/17.
 */
public class ObjectConverter {
    private static final Map<AttributeType, List<Class>> supportedTypeMap;
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
        }};
    }

    public ObjectConverter() {
    }


    /**
     * Method accepts an object that implements the `Record` interface and an object's members annotated with Attribute annotation
     * This function iterates the annotated members of the "destination" object
     * then copies the value from record object into the annotated field of your "destination" object
     * The link is created by index
     * @param dataSetItem
     * @param destination
     * @param <T>
     */
    public static <T> void copyFromRecord(Record dataSetItem, T destination) {
        if (destination == null) return;
        mapMethodsFromSource(destination);
        for (AttributeProxy proxy : getMethodAndFieldAnnotationsForClass(destination.getClass())) {
            try {
                copyToField(proxy, dataSetItem, destination);
            } catch (UnsupportedAttributeException | IllegalAccessException | UnableToWriteException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     *
     * @param proxy
     * @param record
     * @param destination
     * @param <T>
     * @throws UnsupportedAttributeException
     * @throws IllegalAccessException
     * @throws UnableToWriteException
     * @throws InvocationTargetException
     */
    private static <T> void copyToField(AttributeProxy proxy, Record record, T destination) throws UnsupportedAttributeException, IllegalAccessException, UnableToWriteException, InvocationTargetException {
        Attribute attribute = proxy.getAttributeAnnotation();
        if (attribute == null) {
            return;
        }
        int index = attribute.index();
        Class metaClass = attribute.relationshipClass();
        Class fieldClass = proxy.getType();
        AttributeMeta attributeMeta = record.getAttributeMeta(index);
        boolean userSetterAndGetter = attribute.useGetterAndSetter();
        if (attributeMeta == null) {
            attributeMeta = inferMetaData(index, fieldClass);
        }
        if (!isFieldClassSupportedForType(fieldClass, attributeMeta.getAttributeType())) {
            throw new UnsupportedAttributeException(fieldClass, attributeMeta.getAttributeType());
        }
        readDataSetItemData(proxy, attributeMeta, destination, record, metaClass, userSetterAndGetter);
    }


    /**
     *
     * @param proxy
     * @param attributeMeta
     * @param destination
     * @param dataSetItem
     * @param metaClass
     * @param useSetterAndGetter
     * @param <T>
     * @throws UnableToWriteException
     * @throws InvocationTargetException
     */
    private static <T> void readDataSetItemData(AttributeProxy proxy, AttributeMeta attributeMeta, T destination, Record dataSetItem, Class metaClass, boolean useSetterAndGetter) throws UnableToWriteException, InvocationTargetException {
        switch (attributeMeta.getAttributeType()) {
            case String:
                writeStringData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex(), useSetterAndGetter);
                break;
            case Int:
                writeIntegerData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Double:
                writeDoubleData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Boolean:
                writeBoolData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Date:
                writeDateData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case DateTime:
                writeDateTimeData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case ListItem:
                writeListItemData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case SingleRelationship:
                writeSingleRelationshipData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Relation:
                writeRelationshipData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex(), metaClass);
                break;
            default:
                writeStringData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex(), useSetterAndGetter);
                break;
        }
    }


    /**
     *
     * @param proxy
     * @param destination
     * @param dataSetItem
     * @param index
     * @param useSetterAndGetter
     * @param <T>
     * @throws UnableToWriteException
     * @throws InvocationTargetException
     */
    private static <T> void writeStringData(AttributeProxy proxy, T destination, Record dataSetItem, Integer index, boolean useSetterAndGetter) throws UnableToWriteException, InvocationTargetException {
        String value = dataSetItem.getString(index);
        try {
            useSetterIfExists(proxy, destination, value);
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(proxy.getType().getClass().getName(), index, AttributeType.String.toString(), e.getMessage());
        }
    }

    private static <T> void writeIntegerData(AttributeProxy proxy, T destination, Record dataSetItem, Integer index) throws UnableToWriteException {
        Optional<Integer> value = dataSetItem.getOptionalInt(index);
        Integer intValue = 0;
        try {
            if (value.isPresent()) {
                intValue = value.get();
            } else {
                ConverterAttributeType converterAttributeType = inferDataType(proxy.getType().getSimpleName());
                intValue = (converterAttributeType.isOptional()) ? null : 0;
            }
            useSetterIfExists(proxy, destination, intValue);
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(proxy.getType().getName(), index, AttributeType.Int.toString(), e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param proxy
     * @param destination
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws UnableToWriteException
     * @throws InvocationTargetException
     */
    private static <T> void writeDoubleData(AttributeProxy proxy, T destination, Record dataSetItem, Integer index) throws UnableToWriteException, InvocationTargetException {
        Optional<Double> value = dataSetItem.getOptionalDouble(index);
        boolean isFloatValue = fieldIsFloat(proxy.getType());
        try {
            if (value.isPresent()) {
                if (isFloatValue) {
                    useSetterIfExists(proxy, destination, value.get().floatValue());
                } else {
                    useSetterIfExists(proxy, destination, value.get());
                }

            } else {
                ConverterAttributeType converterAttributeType = inferDataType(proxy.getType().getSimpleName());
                if (converterAttributeType.isOptional()) {
                    proxy.setValue(destination, value.get());
                } else {
                    if (isFloatValue)
                        useSetterIfExists(proxy, destination, 0.0f);
                    else useSetterIfExists(proxy, destination, 0.0);
                }
            }
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(proxy.getType().getName(), index, AttributeType.Double.toString(), e.getMessage());
        }
    }

    /**
     *
     * @param proxy
     * @param destination
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws UnableToWriteException
     * @throws InvocationTargetException
     */
    private static <T> void writeBoolData(AttributeProxy proxy, T destination, Record dataSetItem, Integer index) throws UnableToWriteException, InvocationTargetException {
        Optional<Boolean> value = dataSetItem.getOptionalBoolean(index);
        try {
            if (value.isPresent()) {
                useSetterIfExists(proxy, destination, value.get());
            } else {
                ConverterAttributeType converterAttributeType = inferDataType(proxy.getType().getSimpleName());
                useSetterIfExists(proxy, destination, (converterAttributeType.isOptional()) ? null : false);
            }
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(proxy.getType().getName(), index, AttributeType.Boolean.toString(), e.getMessage());
        }
    }

    /**
     *
     * @param proxy
     * @param destination
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws UnableToWriteException
     * @throws InvocationTargetException
     */
    private static <T> void writeDateData(AttributeProxy proxy, T destination, Record dataSetItem, Integer index) throws UnableToWriteException, InvocationTargetException {
        DateTime value = dataSetItem.getDate(index);
        try {
            setDateValueFromField(proxy, value, destination);
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(proxy.getType().getName(), index, AttributeType.Date.toString(), e.getMessage());
        }
    }

    /**
     *
     * @param proxy
     * @param destination
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws UnableToWriteException
     * @throws InvocationTargetException
     */
    private static <T> void writeDateTimeData(AttributeProxy proxy, T destination, Record dataSetItem, Integer index) throws UnableToWriteException, InvocationTargetException {
        DateTime value = dataSetItem.getDateTime(index);
        try {
            setDateValueFromField(proxy, value, destination);
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(proxy.getType().getName(), index, AttributeType.DateTime.toString(), e.getMessage());
        }
    }

    /**
     *
     * @param clazz
     * @return
     */
    private static boolean fieldIsFloat(Class clazz) {
        return (clazz.getName().contains("float") || clazz.getName().contains("Float"));
    }

    /**
     *
     * @param proxy
     * @param destination
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws UnableToWriteException
     * @throws InvocationTargetException
     */
    private static <T> void writeListItemData(AttributeProxy proxy, T destination, Record dataSetItem, Integer index) throws UnableToWriteException, InvocationTargetException {
        ListItem listItem = dataSetItem.getListItem(index);
        if (listItem == null) return;
        Type fieldType = proxy.getType();
        Class classValue = null;
        try {
            classValue = Class.forName(fieldType.getTypeName());
            Object object = classValue.newInstance();
            copyFromRecord(listItem, object);
            useSetterIfExists(proxy, destination, object);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ie) {
            throw new UnableToWriteException(classValue.getName(), index, AttributeType.ListItem.toString(), ie.getMessage());
        }
    }

    /**
     *
     * @param proxy
     * @param destination
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws UnableToWriteException
     * @throws InvocationTargetException
     */
    private static <T> void writeSingleRelationshipData(AttributeProxy proxy, T destination, Record dataSetItem, Integer index) throws UnableToWriteException, InvocationTargetException {
        DataSetItem newDataSetItem = dataSetItem.getDataSetItem(index);
        if (newDataSetItem == null) return;
        Type fieldType = proxy.getType();
        Class classValue = null;
        try {
            classValue = Class.forName(fieldType.getTypeName());
            Object object = classValue.newInstance();
            copyFromRecord(newDataSetItem, object);
            useSetterIfExists(proxy, destination, object);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ie) {
            throw new UnableToWriteException(classValue.getName(), index, AttributeType.ListItem.toString(), ie.getMessage());
        }
    }

    /**
     *
     * @param proxy
     * @param destination
     * @param dataSetItem
     * @param index
     * @param metaClass
     * @param <T>
     * @throws UnableToWriteException
     * @throws InvocationTargetException
     */
    private static <T> void writeRelationshipData(AttributeProxy proxy, T destination, Record dataSetItem, Integer index, Class metaClass) throws UnableToWriteException, InvocationTargetException {
        List<DataSetItem> dataSetItems = dataSetItem.getDataSetItems(index);
        if (dataSetItems == null) return;
        Class classValue = null;
        try {
            classValue = Class.forName(metaClass.getName());
            ArrayList<Object> tempList = new ArrayList<>();
            for (DataSetItem dataSetItem1 : dataSetItems) {
                Object object = classValue.newInstance();
                copyFromRecord(dataSetItem1, object);
                tempList.add(object);
            }
            useSetterIfExists(proxy, destination, tempList);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ie) {
            throw new UnableToWriteException(classValue.getName(), index, AttributeType.ListItem.toString(), ie.getMessage());
        }
    }

    /**
     *
     * @param proxy
     * @param datetime
     * @param destination
     * @param <T>
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> void setDateValueFromField(AttributeProxy proxy, DateTime datetime, T destination) throws IllegalAccessException, InvocationTargetException {
        Class clazz = proxy.getType();
        if (clazz == org.joda.time.DateTime.class) {
            useSetterIfExists(proxy, destination, datetime);
        }
        if (clazz == java.util.Date.class) {
            useSetterIfExists(proxy, destination, new java.util.Date(datetime.getMillis()));
        }
        if (clazz == java.sql.Date.class) {
            useSetterIfExists(proxy, destination, new java.sql.Date(datetime.getMillis()));
        }
    }

    /**
     *
     * @param classType
     * @param type
     * @return
     */
    private static boolean isFieldClassSupportedForType(Class<?> classType, AttributeType type) {
        if ((type.equals(AttributeType.ListItem) || type.equals(AttributeType.Relation) || type.equals(AttributeType.SingleRelationship)) && !isPrimitiveDataTypeOrWrapper(classType)) {
            return true;
        }
        List<Class> classList = getSupportedTypeMap().get(type);
        return classList != null && classList.contains(classType);
    }

    /**
     *
     * @return
     */
    private static Map<AttributeType, List<Class>> getSupportedTypeMap() {
        return supportedTypeMap;
    }

    /**
     *
     * @param dataSetItem
     * @param source
     * @param <T>
     */
    public static <T> void copyToRecord(Record dataSetItem, T source) {
        if (source == null) return;
        mapMethodsFromSource(source);
        for (AttributeProxy attributeProxy : getMethodAndFieldAnnotationsForClass(source.getClass())) {
            try {
                copyFromField(attributeProxy, dataSetItem, source);
            } catch (UnsupportedAttributeException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param clazz
     * @return
     */
    private static List<AttributeProxy> getMethodAndFieldAnnotationsForClass(Class clazz) {
        Field[] fields = clazz.getFields();
        Method[] methods = clazz.getMethods();
        List<AttributeProxy> attributeProxies = new ArrayList<>();
        attributeProxies.addAll(Arrays.stream(fields)
                .filter(field -> field.getAnnotation(Attribute.class) != null)
                .map(field -> new AttributeProxy(field))
                .collect(Collectors.toList()));

        attributeProxies.addAll(Arrays.stream(methods)
                .filter(method -> method.getAnnotation(Attribute.class) != null)
                .map(field -> new AttributeProxy(field))
                .collect(Collectors.toList()));
        return attributeProxies;
    }

    /**
     *
     * @param attributeProxy
     * @param dataSetItem
     * @param source
     * @param <T>
     * @throws UnsupportedAttributeException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> void copyFromField(AttributeProxy attributeProxy, Record dataSetItem, T source) throws UnsupportedAttributeException, IllegalAccessException, InvocationTargetException {
        Attribute attributeAnnotation = attributeProxy.getAttributeAnnotation();
        PrimaryKey primaryKeyAnnotation = attributeProxy.getPrimaryKeyAnnotation();
        PrimaryValue primaryValueAnnotation = attributeProxy.getValueAnnotation();
        boolean primaryKey = false;
        boolean value = false;
        if (primaryKeyAnnotation != null) {
            primaryKey = true;
        }
        if (primaryValueAnnotation != null) {
            value = true;
        }
        if (primaryKey && attributeAnnotation == null) {
            dataSetItem.setPrimaryKey(attributeProxy.getValue(source).toString());
        }
        if (attributeAnnotation == null) {
            return;
        }
        int index = attributeAnnotation.index();
        Class fieldClass = attributeProxy.getType();
        boolean useGetterAndSetter = attributeAnnotation.useGetterAndSetter();
        AttributeMeta attributeMeta = dataSetItem.getAttributeMeta(index);
        if (attributeMeta == null) {
            attributeMeta = new AttributeMeta(inferDataType(attributeProxy.getType().getSimpleName()).getAttributeType(), index);
        }
        if (!isFieldClassSupportedForType(fieldClass, attributeMeta.getAttributeType())) {
            throw new UnsupportedAttributeException(fieldClass, attributeMeta.getAttributeType());
        }
        readObjectData(attributeProxy, attributeMeta, source, dataSetItem, primaryKey, useGetterAndSetter, value);
    }

    /**
     *
     * @param attributeProxy
     * @param attributeMeta
     * @param object
     * @param dataSetItem
     * @param primaryKey
     * @param useGetterAndSetter
     * @param value
     * @param <T>
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> void readObjectData(AttributeProxy attributeProxy, AttributeMeta attributeMeta, T object, Record dataSetItem, boolean primaryKey, boolean useGetterAndSetter, boolean value) throws IllegalAccessException, InvocationTargetException {
        switch (attributeMeta.getAttributeType()) {
            case String:
                readStringData(attributeProxy, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter, value);
                break;
            case Int:
                readIntegerData(attributeProxy, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter, value);
                break;
            case Double:
                readDoubleData(attributeProxy, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter, value);
                break;
            case Boolean:
                readBoolData(attributeProxy, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter, value);
                break;
            case Date:
                readDateData(attributeProxy, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter, value);
                break;
            case DateTime:
                readDateTimeData(attributeProxy, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter, value);
                break;
            case ListItem:
                readListItemData(attributeProxy, object, dataSetItem, attributeMeta.getAttributeIndex(), useGetterAndSetter);
                break;
            case Relation:
                readRelationshipData(attributeProxy, object, dataSetItem, attributeMeta.getAttributeIndex(), useGetterAndSetter);
                break;
            case SingleRelationship:
                readSingleRelationshipData(attributeProxy, object, dataSetItem, attributeMeta.getAttributeIndex(), useGetterAndSetter);
                break;
            default:
                break;
        }
    }

    /**
     *
     * @param attributeProxy
     * @param object
     * @param record
     * @param index
     * @param primaryKey
     * @param useGetterAndSetter
     * @param value
     * @param <T>
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> void readStringData(AttributeProxy attributeProxy, T object, Record record, int index, boolean primaryKey, boolean useGetterAndSetter, boolean value) throws IllegalAccessException, InvocationTargetException {
        Object fieldData = null;
        if (fieldHasGetter(attributeProxy, object) && useGetterAndSetter) fieldData = useGetter(attributeProxy, object);
        else fieldData = attributeProxy.getValue(object);
        record.setString(fieldData != null ? fieldData.toString() : null, index);
        if (value) {
            record.setValue(fieldData.toString());
        }
        if (primaryKey) {
            record.setPrimaryKey(fieldData.toString());
            if (!record.isValueSet()) {
                record.setValue(fieldData.toString());
            }
        }
    }

    /**
     *
     * @param attributeProxy
     * @param object
     * @param record
     * @param index
     * @param primaryKey
     * @param useGetterAndSetter
     * @param value
     * @param <T>
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> void readIntegerData(AttributeProxy attributeProxy, T object, Record record, int index, boolean primaryKey, boolean useGetterAndSetter, boolean value) throws IllegalAccessException, InvocationTargetException {
        Integer fieldData = null;
        if (fieldHasGetter(attributeProxy, object) && useGetterAndSetter) {
            fieldData = (Integer) useGetter(attributeProxy, object);
        } else fieldData = (Integer) attributeProxy.getValue(object);
        record.setInt(fieldData, index);
        if (value) {
            record.setValue(fieldData.toString());
        }
        if (primaryKey) {
            record.setPrimaryKey(fieldData.toString());
            if (!record.isValueSet()) {
                record.setValue(fieldData.toString());
            }
        }
    }

    /**
     *
     * @param attributeProxy
     * @param object
     * @param record
     * @param index
     * @param primaryKey
     * @param useGetterAndSetter
     * @param value
     * @param <T>
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> void readDoubleData(AttributeProxy attributeProxy, T object, Record record, int index, boolean primaryKey, boolean useGetterAndSetter, boolean value) throws IllegalAccessException, InvocationTargetException {
        String fieldName = attributeProxy.getDataTypeName();
        Double fieldData = null;
        if (fieldName.contains("Float") || fieldName.contains("float")) {
            Float floatValue = null;
            if (fieldHasGetter(attributeProxy, object) && useGetterAndSetter) {
                floatValue = (Float) useGetter(attributeProxy, object);
            } else floatValue = (Float) attributeProxy.getValue(object);
            fieldData = new Double(floatValue);
        } else {
            if (fieldHasGetter(attributeProxy, object) && useGetterAndSetter) {
                fieldData = (Double) useGetter(attributeProxy, object);
            } else fieldData = (Double) attributeProxy.getValue(object);
        }
        record.setDouble(fieldData, index);
        if (value) {
            record.setValue(fieldData.toString());
        }
        if (primaryKey) {
            record.setPrimaryKey(fieldData.toString());
            if (!record.isValueSet()) {
                record.setValue(fieldData.toString());
            }
        }
    }

    /**
     *
     * @param attributeProxy
     * @param object
     * @param record
     * @param index
     * @param primaryKey
     * @param useGetterAndSetter
     * @param value
     * @param <T>
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> void readBoolData(AttributeProxy attributeProxy, T object, Record record, int index, boolean primaryKey, boolean useGetterAndSetter, boolean value) throws IllegalAccessException, InvocationTargetException {
        Boolean fieldData = null;
        if (fieldHasGetter(attributeProxy, object) && useGetterAndSetter) {
            fieldData = (Boolean) useGetter(attributeProxy, object);
        } else fieldData = (Boolean) attributeProxy.getValue(object);
        record.setBool(fieldData, index);
        if (value) {
            record.setValue(fieldData.toString());
        }
        if (primaryKey) {
            record.setPrimaryKey(fieldData.toString());
            if (!record.isValueSet()) {
                record.setValue(fieldData.toString());
            }
        }
    }

    /**
     *
     * @param attributeProxy
     * @param object
     * @param record
     * @param index
     * @param primaryKey
     * @param useGetterAndSetter
     * @param value
     * @param <T>
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> void readDateData(AttributeProxy attributeProxy, T object, Record record, int index, boolean primaryKey, boolean useGetterAndSetter, boolean value) throws IllegalAccessException, InvocationTargetException {
        DateTime dateTime = getDateValueFromObject(attributeProxy, object, useGetterAndSetter);
        record.setDate(dateTime, index);
        if (value) {
            record.setValue(dateTime.toString());
        }
        if (primaryKey) {
            record.setPrimaryKey(dateTime.toString());
            if (!record.isValueSet()) {
                record.setValue(dateTime.toString());
            }
        }
    }

    /**
     *
     * @param attributeProxy
     * @param object
     * @param record
     * @param index
     * @param primaryKey
     * @param useGetterAndSetter
     * @param value
     * @param <T>
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> void readDateTimeData(AttributeProxy attributeProxy, T object, Record record, int index, boolean primaryKey, boolean useGetterAndSetter, boolean value) throws IllegalAccessException, InvocationTargetException {
        DateTime dateTime = getDateValueFromObject(attributeProxy, object, useGetterAndSetter);
        record.setDateTime(dateTime, index);
        if (value) {
            record.setValue(dateTime.toString());
        }
        if (primaryKey) {
            record.setPrimaryKey(dateTime.toString());
            if (!record.isValueSet()) {
                record.setValue(dateTime.toString());
            }
        }
    }

    /**
     *
     * @param attributeProxy
     * @param object
     * @param useGetterAndSetter
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> DateTime getDateValueFromObject(AttributeProxy attributeProxy, T object, boolean useGetterAndSetter) throws IllegalAccessException, InvocationTargetException {
        List<Class> supportedClasses = getSupportedTypeMap().get(AttributeType.Date);
        if (supportedClasses == null) {
            return new DateTime();
        }
        for (Class clazz : supportedClasses) {
            if (clazz == org.joda.time.DateTime.class && attributeProxy.getType() == clazz) {
                if (fieldHasGetter(attributeProxy, object) && useGetterAndSetter) {
                    return (DateTime) useGetter(attributeProxy, object);
                } else return (DateTime) attributeProxy.getValue(object);
            }
            if (clazz == java.util.Date.class && attributeProxy.getType() == clazz) {
                if (fieldHasGetter(attributeProxy, object) && useGetterAndSetter) {
                    return new DateTime(((java.util.Date) useGetter(attributeProxy, object)).getTime());
                } else return new DateTime((Date) attributeProxy.getValue(object));
            }
            if (clazz == java.sql.Date.class && attributeProxy.getType() == clazz) {
                if (fieldHasGetter(attributeProxy, object) && useGetterAndSetter) {
                    return new DateTime(((java.sql.Date) useGetter(attributeProxy, object)).getTime());
                } else return new DateTime((java.sql.Date) attributeProxy.getValue(object));
            }
        }

        return new DateTime();
    }

    /**
     *
     * @param attributeProxy
     * @param object
     * @param dataSetItem
     * @param index
     * @param useGetterAndSetter
     * @param <T>
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> void readListItemData(AttributeProxy attributeProxy, T object, Record dataSetItem, int index, boolean useGetterAndSetter) throws IllegalAccessException, InvocationTargetException {
        Object listItemObject = null;
        if (useGetterAndSetter) {
            if (fieldHasGetter(attributeProxy, object)) {
                listItemObject = useGetter(attributeProxy, object);
            }
        } else listItemObject = attributeProxy.getValue(object);
        ListItem listItem = new ListItem();
        copyToRecord(listItem, listItemObject);
        dataSetItem.setListItem(listItem, index);
    }

    /**
     *
     * @param attributeProxy
     * @param object
     * @param dataSetItem
     * @param index
     * @param useGetterAndSetter
     * @param <T>
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> void readSingleRelationshipData(AttributeProxy attributeProxy, T object, Record dataSetItem, int index, boolean useGetterAndSetter) throws IllegalAccessException, InvocationTargetException {
        Object relationship = null;
        if (fieldHasGetter(attributeProxy, object) && useGetterAndSetter) {
            relationship = useGetter(attributeProxy, object);
        } else relationship = attributeProxy.getValue(object);
        DataSetItem tempItem = dataSetItem.addNewDataSetItem(index);
        copyToRecord(tempItem, relationship);
    }

    /**
     *
     * @param attributeProxy
     * @param object
     * @param dataSetItem
     * @param index
     * @param useGetterAndSetter
     * @param <T>
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> void readRelationshipData(AttributeProxy attributeProxy, T object, Record dataSetItem, int index, boolean useGetterAndSetter) throws IllegalAccessException, InvocationTargetException {
        List<Object> relationship = null;
        if (fieldHasGetter(attributeProxy, object) && useGetterAndSetter) {
            relationship = (List<Object>) useGetter(attributeProxy, object);
        } else relationship = (List<Object>) attributeProxy.getValue(object);
        for (Object obj : relationship) {
            DataSetItem tempItem = dataSetItem.addNewDataSetItem(index);
            copyToRecord(tempItem, obj);
        }
    }

    /**
     *
     * @param someClass
     * @param <T>
     * @return
     */
    public static <T> ServiceConfiguration generateConfiguration(Class<T> someClass) {
        return new ServiceConfiguration.Builder(someClass.getName()).withAttributes(generateConfigurationAttributes(someClass)).build();
    }

    public static <T> ListServiceConfiguration generateListConfiguration(Class<T> someClass, String configName) {
        ListServiceConfiguration listServiceConfiguration = new ListServiceConfiguration(configName);
        listServiceConfiguration.getAttributes().addAll(generateListConfigurationAttributes(someClass));
        return listServiceConfiguration;
    }

    /**
     *
     * @param someClass
     * @param <T>
     * @return
     */
    public static <T> Set<ListServiceConfigurationAttribute> generateListConfigurationAttributes(Class<T> someClass) {
        Field[] fields = someClass.getDeclaredFields();
        Set<ListServiceConfigurationAttribute> attributes = new HashSet<>();
        for (Field field : fields) {
            Attribute attribute = field.getAnnotation(Attribute.class);
            if (attribute != null) {
                attributes.add(getListServiceConfigurationAttributeFromField(field, attribute));
            }
        }
        return attributes;
    }

    /**
     *
     * @param field
     * @param attribute
     * @return
     */
    private static ListServiceConfigurationAttribute getListServiceConfigurationAttributeFromField(Field field, Attribute attribute) {
        int index = attribute.index();
        String name = attribute.name();
        if (StringUtils.isEmpty(name)) {
            name = inferName(field.getName());
        }
        ConverterAttributeType converterAttributeType = null;
        AttributeType attributeType = attribute.dataType();
        if (attributeType.equals(AttributeType.None)) {
            converterAttributeType = inferDataType(field.getClass().getSimpleName());
        }
        ListServiceConfigurationAttribute listServiceConfigurationAttribute = new ListServiceConfigurationAttribute();
        listServiceConfigurationAttribute.setAttributeIndex(index);
        listServiceConfigurationAttribute.setLabel(name);
        listServiceConfigurationAttribute.setAttributeType(converterAttributeType.getAttributeType());
        return listServiceConfigurationAttribute;
    }

    /**
     *
     * @param someClass
     * @param <T>
     * @return
     */
    public static <T> Collection<ServiceConfigurationAttribute> generateConfigurationAttributes(Class<T> someClass) {
        Field[] fields = someClass.getDeclaredFields();
        Method[] methods = someClass.getDeclaredMethods();
        Collection<ServiceConfigurationAttribute> attributes = new ArrayList<>();
        for (AttributeProxy proxy : getMethodAndFieldAnnotationsForClass(someClass)) {
            Attribute attribute = proxy.getAttributeAnnotation();
            if (attribute != null) {
                attributes.add(getServiceConfigurationAttributeFromField(new ConfigurationWrapper(proxy), attribute));
            }
        }
        return attributes;
    }

    /**
     *
     * @param configurationWrapper
     * @param attribute
     * @return
     */
    private static ServiceConfigurationAttribute getServiceConfigurationAttributeFromField(ConfigurationWrapper configurationWrapper, Attribute attribute) {
        int index = attribute.index();
        String name = attribute.name();
        Class relationShipClass = attribute.relationshipClass();
        if (StringUtils.isEmpty(name)) {
            name = inferName(configurationWrapper.varName);
        }
        AttributeType attributeType = attribute.dataType();
        ConverterAttributeType converterAttributeType;
        if (attributeType.equals(AttributeType.None)) {
            if (attribute.relationshipClass() == Class.class) {
                converterAttributeType = inferDataType(configurationWrapper.dataTypeName);
                attributeType = converterAttributeType.getAttributeType();
            } else {
                converterAttributeType = inferDataType(configurationWrapper.dataTypeName);
                if (!converterAttributeType.getAttributeType().equals(AttributeType.Relation)) {
                    converterAttributeType = new ConverterAttributeType(AttributeType.SingleRelationship, true);
                    attributeType = converterAttributeType.getAttributeType();
                } else {
                    converterAttributeType = new ConverterAttributeType(AttributeType.Relation, true);
                    attributeType = converterAttributeType.getAttributeType();
                }
            }
        }

        boolean canCreate = attribute.canCreate();
        boolean canCreateAndRequired = attribute.canCreateAndRequired();
        boolean canUpdate = attribute.canUpdate();
        boolean canUpdateAndRequired = attribute.canUpdateAndRequired();
        boolean canSearch = attribute.canSearch();
        boolean canSearchAndRequired = attribute.canSearchAndRequired();


        ServiceConfigurationAttribute serviceConfigurationAttribute = new ServiceConfigurationAttribute();
        serviceConfigurationAttribute.setAttributeIndex(index);
        serviceConfigurationAttribute.setAttributeType(attributeType);
        serviceConfigurationAttribute.setName(name);
        serviceConfigurationAttribute.create = canCreate;
        serviceConfigurationAttribute.createRequired = canCreateAndRequired;
        serviceConfigurationAttribute.update = canUpdate;
        serviceConfigurationAttribute.updateRequired = canUpdateAndRequired;
        serviceConfigurationAttribute.search = canSearch;
        serviceConfigurationAttribute.searchRequired = canSearchAndRequired;
        if (requiresRelatedServiceConfiguration(attributeType)) {
            setRelatedServiceConfiguration(attributeType, serviceConfigurationAttribute, name, configurationWrapper.clazz, relationShipClass);
        }
        return serviceConfigurationAttribute;
    }

    /**
     *
     * @param attributeType
     * @return
     */
    private static boolean requiresRelatedServiceConfiguration(AttributeType attributeType) {
        return (attributeType.equals(AttributeType.ListItem) || attributeType.equals(AttributeType.Relation) || attributeType.equals(AttributeType.SingleRelationship));
    }

    /**
     *
     * @param attributeType
     * @param serviceConfigurationAttribute
     * @param configName
     * @param clazz
     * @param clazz1
     */
    private static void setRelatedServiceConfiguration(AttributeType attributeType, ServiceConfigurationAttribute serviceConfigurationAttribute, String configName, Class<?> clazz, Class<?> clazz1) {
        if (attributeType.equals(AttributeType.ListItem)) {
            serviceConfigurationAttribute.setRelatedListServiceConfiguration(generateListConfiguration(clazz, configName));
        } else if (attributeType.equals(AttributeType.Relation)) {
            serviceConfigurationAttribute.setRelatedService(new RelatedServiceConfiguration(configName, generateConfiguration(clazz1).getAttributes()));
        } else {
            serviceConfigurationAttribute.setRelatedService(new RelatedServiceConfiguration(configName, generateConfiguration(clazz).getAttributes()));
        }
    }

    /**
     *
     * @param name
     * @return
     */
    private static String inferName(String name) {
        //TODO: clean up camel Case Variable names
        return name;
    }

    /**
     *
     * @param dataTypeName
     * @return
     */
    private static ConverterAttributeType inferDataType(String dataTypeName) {
        switch (dataTypeName) {
            case "String":
                return new ConverterAttributeType(AttributeType.String, true);
            case "Double":
            case "Float":
                return new ConverterAttributeType(AttributeType.Double, true);
            case "float":
            case "double":
                return new ConverterAttributeType(AttributeType.Double, false);
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
            default:
                return new ConverterAttributeType(AttributeType.ListItem, true);

        }
    }

    /**
     *
     * @param clazz
     * @return
     */
    private static ConverterAttributeType inferDataType(Class clazz) {
        String simpleName = clazz.getSimpleName();
        switch (simpleName) {
            case "String":
                return new ConverterAttributeType(AttributeType.String, true);
            case "Double":
            case "Float":
                return new ConverterAttributeType(AttributeType.Double, true);
            case "float":
            case "double":
                return new ConverterAttributeType(AttributeType.Double, false);
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
            default:
                return new ConverterAttributeType(AttributeType.ListItem, true);

        }
    }

    /**
     *
     * @param index
     * @param clazz
     * @return
     */
    private static AttributeMeta inferMetaData(int index, Class clazz) {
        return new AttributeMeta(inferDataType(clazz).getAttributeType(), index);
    }

    /**
     *
     * @param clazz
     * @return
     */
    private static boolean isPrimitiveDataTypeOrWrapper(Class clazz) {
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
     *
     * @param attributeProxy
     * @param object
     * @param <T>
     * @return
     */
    private static <T> boolean fieldHasGetter(AttributeProxy attributeProxy, T object) {
        if (!attributeProxy.isField) return false;
        Map<String, Method> tempMap = getMethodMap().get(object.getClass().getName());
        if (tempMap == null) return false;
        return (tempMap.containsKey(getterMethodName(attributeProxy.getName())));
    }

    /**
     *
     * @param proxy
     * @param object
     * @param <T>
     * @return
     */
    private static <T> boolean fieldHasSetter(AttributeProxy proxy, T object) {
        Map<String, Method> tempMap = getMethodMap().get(object.getClass().getName());
        if (tempMap == null) return false;
        return (tempMap.containsKey(setterMethodName(proxy)));
    }

    /**
     *
     * @param destination
     * @param proxy
     * @param value
     * @param <T>
     */
    private static <T> void useSetter(T destination, AttributeProxy proxy, Object value) {
        Map<String, Method> tempMethodMap = getMethodMap().get(destination.getClass().getName());
        if (tempMethodMap == null) return;
        Method setterMethod = tempMethodMap.get(setterMethodName(proxy));
        try {
            setterMethod.invoke(destination, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param attributeProxy
     * @param object
     * @param <T>
     * @return
     */
    private static <T> Object useGetter(AttributeProxy attributeProxy, T object) {
        Map<String, Method> tempMethodMap = getMethodMap().get(object.getClass().getName());
        if (tempMethodMap == null) return null;
        Method getterMethod = tempMethodMap.get(getterMethodName(attributeProxy.getName()));
        try {
            return getterMethod.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param name
     * @return
     */
    private static String getterMethodName(String name) {
        return new StringBuilder("get")
                .append(name).toString().toLowerCase();
    }

    /**
     *
     * @param proxy
     * @return
     */
    private static String setterMethodName(AttributeProxy proxy) {
        if (proxy.isField) {
            return new StringBuilder("set")
                    .append(proxy.getName()).toString().toLowerCase();
        } else {
            return proxy.getName().replace("get", "set");
        }
    }

    /**
     *
     * @param sourceObject
     * @param <T>
     */
    private static <T> void mapMethodsFromSource(T sourceObject) {
        if (sourceObject == null) return;
        String className = sourceObject.getClass().getName();
        Map<String, Method> methodMap = Arrays.stream(sourceObject.getClass().getDeclaredMethods()).distinct().collect(Collectors.toMap(method -> method.getName().toLowerCase(), method -> method, ((method, method2) -> method)));
        getMethodMap().put(className, methodMap);
    }

    /**
     *
     * @return
     */
    public static Map<String, Map<String, Method>> getMethodMap() {
        if (methodMap == null) {
            methodMap = new HashMap<>();
        }
        return methodMap;
    }

    /**
     *
     * @param tempMethodMap
     */
    public static void setMethodMap(Map<String, Map<String, Method>> tempMethodMap) {
        methodMap = tempMethodMap;
    }

    /**
     *
     * @param attributeProxy
     * @param destination
     * @param value
     * @param <T>
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static <T> void useSetterIfExists(AttributeProxy attributeProxy, T destination, Object value) throws InvocationTargetException, IllegalAccessException {
        if (fieldHasSetter(attributeProxy, destination)) {
            useSetter(destination, attributeProxy, value);
        } else {
            if (attributeProxy.isField) {
                attributeProxy.setValue(destination, value);
            }
        }
    }
}
