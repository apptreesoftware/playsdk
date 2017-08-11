package sdk.converter;

import org.joda.time.DateTime;
import sdk.annotations.Attribute;
import sdk.annotations.CustomLocation;
import sdk.annotations.PrimaryKey;
import sdk.annotations.PrimaryValue;
import sdk.data.AttributeMeta;
import sdk.data.DataSetItem;
import sdk.data.Record;
import sdk.exceptions.UnableToWriteException;
import sdk.exceptions.UnsupportedAttributeException;
import sdk.list.ListItem;
import sdk.models.AttributeType;
import sdk.models.Color;
import sdk.models.Location;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Orozco on 7/19/17.
 */
public class ObjectConverter extends ConfigurationManager {

    public ObjectConverter() { }

    /**
     * Method accepts an object that implements the `Record` interface and an object's members annotated with Attribute annotation
     * This function iterates the annotated members of the "destination" object
     * then copies the value from record object into the annotated field of your "destination" object
     * The link is created by index
     *
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
            case Location:
                routeWriteLocationData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex(), metaClass);
                break;
            case Color:
                writeColorData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex(), metaClass);
                break;
            default:
                writeStringData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex(), useSetterAndGetter);
                break;
        }
    }

    /**
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
            case Location:
                readLocationData(attributeProxy, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter, value);
                break;
            case Color:
                readColorData(attributeProxy, object, dataSetItem, attributeMeta.getAttributeIndex(), useGetterAndSetter);
                break;
            default:
                break;
        }
    }

    /**
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
//        try {
//            classValue = Class.forName(fieldType.getTypeName());
//            Object object = classValue.newInstance();
//            copyFromRecord(listItem, object);
//            useSetterIfExists(proxy, destination, object);
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ie) {
//            throw new UnableToWriteException(classValue.getName(), index, AttributeType.ListItem.toString(), ie.getMessage());
//        }
        copyFromRecordRecursive(proxy, fieldType, classValue, listItem, destination, index);
    }

    /**
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
        copyFromRecordRecursive(proxy, fieldType, classValue, newDataSetItem, destination, index);
    }

    private static <T> void copyFromRecordRecursive(AttributeProxy proxy, Type fieldType, Class classValue, Record record, T destination, Integer index)
    throws InvocationTargetException, UnableToWriteException {
        try {
            classValue = Class.forName(fieldType.getTypeName());
            Object object = classValue.newInstance();
            copyFromRecord(record, object);
            useSetterIfExists(proxy, destination, object);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ie) {
            throw new UnableToWriteException(classValue.getName(), index, AttributeType.ListItem.toString(), ie.getMessage());
        }
    }

    /**
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

    private static <T> void routeWriteLocationData(AttributeProxy proxy, T destination, Record dataSetItem, Integer index, Class metaClass)
            throws UnableToWriteException, InvocationTargetException {
        if (CustomLocation.class.isAssignableFrom(destination.getClass()))
            writeCustomLocationData(proxy, destination, dataSetItem, index, metaClass);
        else writeLocationData(proxy, destination, dataSetItem, index, metaClass);
    }

    private static <T, C extends CustomLocation> void writeCustomLocationData(AttributeProxy proxy, T destination, Record dataSetItem, Integer index, Class metaClass)
            throws UnableToWriteException, InvocationTargetException {
        Location location = dataSetItem.getLocation(index);
        if (location == null) return;
        try {
            Field field = destination.getClass().getDeclaredField(proxy.getName());
            Class<C> clazz = (Class<C>) Class.forName(field.getType().getName());
            C newInstance = clazz.newInstance();
            newInstance.fromLocation(location, newInstance);
            useSetterIfExists(proxy, destination, newInstance);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    private static <T> void writeLocationData(AttributeProxy proxy, T destination, Record dataSetItem, Integer index, Class metaClass)
            throws UnableToWriteException, InvocationTargetException {
        Location location = dataSetItem.getLocation(index);
        if (location == null) return;
        try {
            useSetterIfExists(proxy, destination, location);
        } catch (Exception error) {
            throw new RuntimeException("Unable to write Location data for field: " + proxy.getName());
        }
    }

    private static <T> void writeColorData(AttributeProxy proxy, T destination, Record record, Integer index, Class metaClass) {
        Color color = record.getColor(index);
        if (color == null) return;
        try {
            useSetterIfExists(proxy, destination, color);
        } catch (Exception error) {
            throw new RuntimeException("Unable to write Color data for field: " + proxy.getName());
        }
    }

    /**
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

    private static <T> void readLocationData(AttributeProxy proxy, T object, Record record, int index, boolean primaryKey, boolean useGetterAndSetter, boolean value) throws IllegalAccessException, InvocationTargetException {
        Location location = getLocationValueFromObject(proxy, object, useGetterAndSetter);
        record.setLocation(location, index);
        if (value) {
            record.setValue(location.toString());
        }
        if (primaryKey) {
            record.setPrimaryKey(location.toString());
            if (!record.isValueSet()) {
                record.setValue(location.toString());
            }
        }
    }

    private static <T> void readColorData(AttributeProxy proxy, T object, Record record, int index, boolean useGetterAndSetter) {
        Color color = getColorValueFromObject(proxy, object, useGetterAndSetter);
        record.setColor(color, index);
    }

    /**
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

    private static <T, C extends CustomLocation> Location getLocationValueFromObject(AttributeProxy proxy, T object, boolean useGetterAndSetter)
            throws IllegalAccessException, InvocationTargetException {
        List<Class> supportedClasses = getSupportedTypeMap().get(AttributeType.Location);
        if (supportedClasses == null) return new Location();
        if (proxy.getType() == Location.class) {
            if (fieldHasGetter(proxy, object) && useGetterAndSetter) return (Location) useGetter(proxy, object);
            Location location = (Location) proxy.getValue(object);
            if (location == null) return new Location();
            Location retLocation = new Location(location.getLatitude(), location.getLongitude());
            retLocation.setAccuracy(location.getAccuracy());
            retLocation.setBearing(location.getBearing());
            retLocation.setElevation(location.getElevation());
            retLocation.setSpeed(location.getSpeed());
            retLocation.setTimestamp(location.getTimestamp());
            return retLocation;
        } else if (CustomLocation.class.isAssignableFrom(proxy.getType())) {
            Location location = new Location();
            C src = (C) proxy.getValue(object);
            if (src == null) return new Location();
            location.setLatitude(src.getLatitude());
            location.setLongitude(src.getLongitude());
            location.setBearing(src.getBearing());
            location.setElevation(src.getElevation());
            location.setSpeed(src.getSpeed());
            location.setAccuracy(src.getAccuracy());
            location.setTimestamp(src.getTimestamp());
            return location;
        }
        return new Location();
    }

    private static <T> Color getColorValueFromObject(AttributeProxy proxy, T object, boolean useGetterAndSetter) {
        if (getSupportedTypeMap().get(AttributeType.Color) == null) return new Color();
        if (proxy.getType() == Color.class) {
            try {
                if (fieldHasGetter(proxy, object) && useGetterAndSetter) return (Color) useGetter(proxy, object);
                Color objColor = (Color) proxy.getValue(object);
                return new Color(objColor.getR(), objColor.getG(), objColor.getB(), objColor.getA());
            } catch (Exception error) {
            }
        }
        return new Color();
    }
}