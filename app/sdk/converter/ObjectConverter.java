package sdk.converter;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import sdk.annotations.Attribute;
import sdk.data.*;
import sdk.exceptions.UnableToWriteException;
import sdk.exceptions.UnsupportedAttributeException;
import sdk.list.ListItem;
import sdk.list.ListServiceConfiguration;
import sdk.list.ListServiceConfigurationAttribute;
import sdk.models.AttributeType;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Orozco on 7/19/17.
 */
public class ObjectConverter {
    private static final Map<AttributeType, List<Class>> supportedTypeMap;
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
     *
     * @param dataSetItem
     * @param destination
     * @param <T>
     * @throws UnsupportedAttributeException
     * @throws IllegalAccessException
     */
    public static <T> void copyFromRecord(Record dataSetItem, T destination) throws UnableToWriteException, UnsupportedAttributeException, IllegalAccessException {
        for (Field field : destination.getClass().getDeclaredFields()) {
            copyToField(field, dataSetItem, destination);
        }
    }


    /**
     *
     * @param field
     * @param record
     * @param destination
     * @param <T>
     * @throws UnsupportedAttributeException
     * @throws IllegalAccessException
     */
    private static <T> void copyToField(Field field, Record record, T destination) throws UnsupportedAttributeException, IllegalAccessException, UnableToWriteException {
        Attribute attribute = field.getAnnotation(Attribute.class);
        if (attribute == null) {
            return;
        }
        int index = attribute.index();
        Class metaClass = attribute.relationShipClass();
        Class fieldClass = field.getType();
        AttributeMeta attributeMeta = record.getAttributeMeta(index);
        if (attributeMeta == null) {
            attributeMeta = inferMetaData(index, fieldClass);
        }
        if (!isFieldClassSupportedForType(fieldClass, attributeMeta.getAttributeType())) {
            throw new UnsupportedAttributeException(fieldClass, attributeMeta.getAttributeType());
        }
        readDataSetItemData(field, attributeMeta, destination, record, metaClass);
    }


    /**
     * @param field
     * @param attributeMeta
     * @param destination
     * @param dataSetItem
     * @param metaClass
     * @param <T>
     * @throws UnableToWriteException
     */
    private static <T> void readDataSetItemData(Field field, AttributeMeta attributeMeta, T destination, Record dataSetItem, Class metaClass) throws UnableToWriteException {
        switch (attributeMeta.getAttributeType()) {
            case String:
                writeStringData(field, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Int:
                writeIntegerData(field, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Double:
                writeDoubleData(field, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Boolean:
                writeBoolData(field, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Date:
                writeDateData(field, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case DateTime:
                writeDateTimeData(field, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case ListItem:
                writeListItemData(field, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case SingleRelationship:
                writeSingleRelationshipData(field, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Relation:
                writeRelationshipData(field, destination, dataSetItem, attributeMeta.getAttributeIndex(), metaClass);
                break;
            default:
                writeStringData(field, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
        }
    }


    /**
     * @param field
     * @param destination
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws UnableToWriteException
     */
    private static <T> void writeStringData(Field field, T destination, Record dataSetItem, Integer index) throws UnableToWriteException {
        String value = dataSetItem.getString(index);
        try {
            field.set(destination, value);
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(field.getClass().getName(), index, AttributeType.String.toString(), e.getMessage());
        }
    }


    /**
     * @param field
     * @param destination
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws UnableToWriteException
     */
    private static <T> void writeIntegerData(Field field, T destination, Record dataSetItem, Integer index) throws UnableToWriteException {
        Optional<Integer> value = dataSetItem.getOptionalInt(index);
        try {
            if (value.isPresent()) {
                field.set(destination, value.get());
            } else {
                ConverterAttributeType converterAttributeType = inferDataType(field);
                field.set(destination, (converterAttributeType.isOptional()) ? null : 0);
            }
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(field.getClass().getName(), index, AttributeType.Int.toString(), e.getMessage());
        }
    }


    /**
     * @param field
     * @param destination
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws UnableToWriteException
     */
    private static <T> void writeDoubleData(Field field, T destination, Record dataSetItem, Integer index) throws UnableToWriteException {
        Optional<Double> value = dataSetItem.getOptionalDouble(index);
        boolean floatValue = fieldIsFloat(field);
        try {
            if (value.isPresent()) {
                if (floatValue) {
                    field.set(destination, value.get().floatValue());
                } else {
                    field.set(destination, value.get());
                }

            } else {
                ConverterAttributeType converterAttributeType = inferDataType(field);
                if (converterAttributeType.isOptional()) {
                    field.set(destination, null);
                } else {
                    if (floatValue)
                        field.set(destination, 0.0f);
                    else field.set(destination, 0.0f);
                }
            }
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(field.getClass().getName(), index, AttributeType.Double.toString(), e.getMessage());
        }
    }


    /**
     * @param field
     * @param destination
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws UnableToWriteException
     */
    private static <T> void writeBoolData(Field field, T destination, Record dataSetItem, Integer index) throws UnableToWriteException {
        Optional<Boolean> value = dataSetItem.getOptionalBoolean(index);
        try {
            if (value.isPresent()) {
                field.set(destination, value.get());
            } else {
                ConverterAttributeType converterAttributeType = inferDataType(field);
                field.set(destination, (converterAttributeType.isOptional()) ? null : false);
            }
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(field.getClass().getName(), index, AttributeType.Boolean.toString(), e.getMessage());
        }
    }

    /**
     * @param field
     * @param destination
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throw UnableToWriteException
     */
    private static <T> void writeDateData(Field field, T destination, Record dataSetItem, Integer index) throws UnableToWriteException {
        DateTime value = dataSetItem.getDate(index);
        try {
            setDateValueFromField(field, value, destination);
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(field.getClass().getName(), index, AttributeType.Date.toString(), e.getMessage());
        }
    }

    /**
     * @param field
     * @param destination
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws UnableToWriteException
     */
    private static <T> void writeDateTimeData(Field field, T destination, Record dataSetItem, Integer index) throws UnableToWriteException {
        DateTime value = dataSetItem.getDateTime(index);
        try {
            setDateValueFromField(field, value, destination);
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(field.getClass().getName(), index, AttributeType.DateTime.toString(), e.getMessage());
        }
    }

    /**
     * @param field
     * @return
     */
    private static boolean fieldIsFloat(Field field) {
        return (field.getType().getName().contains("float") || field.getType().getName().contains("Float"));
    }


    /**
     * @param field
     * @param destination
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws UnableToWriteException
     */
    private static <T> void writeListItemData(Field field, T destination, Record dataSetItem, Integer index) throws UnableToWriteException {
        ListItem listItem = dataSetItem.getListItem(index);
        if (listItem == null) return;
        Type fieldType = field.getType();
        Class classValue = null;
        try {
            classValue = Class.forName(fieldType.getTypeName());
            Object object = classValue.newInstance();
            copyFromRecord(listItem, object);
            field.set(destination, object);
        } catch (ClassNotFoundException | InstantiationException | UnsupportedAttributeException | IllegalAccessException ie) {
            throw new UnableToWriteException(classValue.getName(), index, AttributeType.ListItem.toString(), ie.getMessage());
        }
    }


    /**
     * @param field
     * @param destination
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws UnableToWriteException
     */

    private static <T> void writeSingleRelationshipData(Field field, T destination, Record dataSetItem, Integer index) throws UnableToWriteException {
        DataSetItem newDataSetItem = dataSetItem.getDataSetItem(index);
        if (newDataSetItem == null) return;
        Type fieldType = field.getType();
        Class classValue = null;
        try {
            classValue = Class.forName(fieldType.getTypeName());
            Object object = classValue.newInstance();
            copyFromRecord(newDataSetItem, object);
            field.set(destination, object);
        } catch (ClassNotFoundException | InstantiationException | UnsupportedAttributeException | IllegalAccessException ie) {
            throw new UnableToWriteException(classValue.getName(), index, AttributeType.ListItem.toString(), ie.getMessage());
        }
    }


    /**
     * @param field
     * @param destination
     * @param dataSetItem
     * @param index
     * @param metaClass
     * @param <T>
     * @throws UnableToWriteException
     */
    private static <T> void writeRelationshipData(Field field, T destination, Record dataSetItem, Integer index, Class metaClass) throws UnableToWriteException {
        List<DataSetItem> dataSetItems = dataSetItem.getDataSetItems(index);
        if (dataSetItems == null) return;
        Class classValue = null;
        try {
            classValue = Class.forName(metaClass.getName());
            Object object = classValue.newInstance();
            ArrayList<Object> tempList = new ArrayList<>();
            for (DataSetItem dataSetItem1 : dataSetItems) {
                copyFromRecord(dataSetItem1, object);
                tempList.add(object);
            }
            field.set(destination, tempList);
        } catch (ClassNotFoundException | InstantiationException | UnsupportedAttributeException | IllegalAccessException ie) {
            throw new UnableToWriteException(classValue.getName(), index, AttributeType.ListItem.toString(), ie.getMessage());
        }
    }


    /**
     * @param field
     * @param datetime
     * @param destination
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void setDateValueFromField(Field field, DateTime datetime, T destination) throws IllegalAccessException {
        Class clazz = field.getType();
        if (clazz == org.joda.time.DateTime.class) {
            field.set(destination, datetime);
        }
        if (clazz == java.util.Date.class) {
            field.set(destination, new java.util.Date(datetime.getMillis()));
        }
        if (clazz == java.sql.Date.class) {
            field.set(destination, new java.sql.Date(datetime.getMillis()));
        }
    }

    /**
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
     * @return
     */
    private static Map<AttributeType, List<Class>> getSupportedTypeMap() {
        return supportedTypeMap;
    }

    //Copy into a data set item from a model object

    /**
     * @param dataSetItem
     * @param source
     * @param <T>
     * @throws UnsupportedAttributeException
     * @throws IllegalAccessException
     */
    public static <T> void copyToRecord(Record dataSetItem, T source) throws UnsupportedAttributeException, IllegalAccessException {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            copyFromField(field, dataSetItem, source);
        }
    }

    /**
     * @param field
     * @param dataSetItem
     * @param source
     * @param <T>
     * @throws UnsupportedAttributeException
     * @throws IllegalAccessException
     */
    private static <T> void copyFromField(Field field, Record dataSetItem, T source) throws UnsupportedAttributeException, IllegalAccessException {
        Attribute attributeAnnotation = field.getAnnotation(Attribute.class);
        if (attributeAnnotation == null) {
            return;
        }
        int index = attributeAnnotation.index();
        Class fieldClass = field.getType();
        AttributeMeta attributeMeta = dataSetItem.getAttributeMeta(index);
        boolean primaryKey = attributeAnnotation.primaryKey();
        if (attributeMeta == null) {
            attributeMeta = new AttributeMeta(inferDataType(field).getAttributeType(), index);
        }
        if (!isFieldClassSupportedForType(fieldClass, attributeMeta.getAttributeType())) {
            throw new UnsupportedAttributeException(fieldClass, attributeMeta.getAttributeType());
        }
        readObjectData(field, attributeMeta, source, dataSetItem, primaryKey);
    }


    /**
     * @param field
     * @param attributeMeta
     * @param object
     * @param dataSetItem
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void readObjectData(Field field, AttributeMeta attributeMeta, T object, Record dataSetItem, boolean primaryKey) throws IllegalAccessException {
        switch (attributeMeta.getAttributeType()) {
            case String:
                readStringData(field, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey);
                break;
            case Int:
                readIntegerData(field, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey);
                break;
            case Double:
                readDoubleData(field, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey);
                break;
            case Boolean:
                readBoolData(field, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey);
                break;
            case Date:
                readDateData(field, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey);
                break;
            case DateTime:
                readDateTimeData(field, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey);
                break;
            case ListItem:
                readListItemData(field, object, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Relation:
                readRelationshipData(field, object, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case SingleRelationship:
                readSingleRelationshipData(field, object, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            default:
                break;
        }
    }


    /**
     * @param field
     * @param object
     * @param record
     * @param index
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void readStringData(Field field, T object, Record record, int index, boolean primaryKey) throws IllegalAccessException {
        Object fieldData = field.get(object);
        record.setString(fieldData.toString(), index);
        if(primaryKey){
            record.setPrimaryKey(fieldData.toString());
        }
    }


    /**
     * @param field
     * @param object
     * @param record
     * @param index
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void readIntegerData(Field field, T object, Record record, int index, boolean primaryKey) throws IllegalAccessException {
        Integer fieldData = (Integer) field.get(object);
        record.setInt(fieldData, index);
        if(primaryKey){
            record.setPrimaryKey(fieldData.toString());
        }
    }

    /**
     * @param field
     * @param object
     * @param record
     * @param index
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void readDoubleData(Field field, T object, Record record, int index, boolean primaryKey) throws IllegalAccessException {
        String fieldName = field.getType().getName();
        Double fieldData = null;
        if (fieldName.contains("Float") || fieldName.contains("float")) {
            Float floatValue = (Float) field.get(object);
            fieldData = new Double(floatValue);
        } else {
            fieldData = (Double) field.get(object);
        }
        record.setDouble(fieldData, index);
        if(primaryKey){
            record.setPrimaryKey(fieldData.toString());
        }
    }


    /**
     * @param field
     * @param object
     * @param record
     * @param index
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void readBoolData(Field field, T object, Record record, int index, boolean primaryKey) throws IllegalAccessException {
        Boolean fieldData = (Boolean) field.get(object);
        record.setBool(fieldData, index);
        if(primaryKey){
            record.setPrimaryKey(fieldData.toString());
        }
    }

    /**
     * @param field
     * @param object
     * @param record
     * @param index
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void readDateData(Field field, T object, Record record, int index, boolean primaryKey) throws IllegalAccessException {
        DateTime dateTime = getDateValueFromObject(field, object);
        record.setDate(dateTime, index);
        if(primaryKey){
            record.setPrimaryKey(dateTime.toString());
        }
    }


    /**
     * @param field
     * @param object
     * @param record
     * @param index
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void readDateTimeData(Field field, T object, Record record, int index, boolean primaryKey) throws IllegalAccessException {
        DateTime dateTime = getDateValueFromObject(field, object);
        record.setDateTime(dateTime, index);
        if(primaryKey){
            record.setPrimaryKey(dateTime.toString());
        }
    }


    /**
     * @param field
     * @param object
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    private static <T> DateTime getDateValueFromObject(Field field, T object) throws IllegalAccessException {
        List<Class> supportedClasses = getSupportedTypeMap().get(AttributeType.Date);
        if (supportedClasses == null) {
            return new DateTime();
        }
        for (Class clazz : supportedClasses) {
            if (clazz == org.joda.time.DateTime.class && field.getType() == clazz) {
                return (DateTime) field.get(object);
            }
            if (clazz == java.util.Date.class && field.getType() == clazz) {
                return new DateTime(((java.util.Date) field.get(object)).getTime());
            }
            if (clazz == java.sql.Date.class && field.getType() == clazz) {
                return new DateTime((java.sql.Date) field.get(object));
            }
        }

        return new DateTime();
    }


    /**
     * @param field
     * @param object
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void readListItemData(Field field, T object, Record dataSetItem, int index) throws IllegalAccessException {
        Object listItemObject = field.get(object);
        ListItem listItem = new ListItem();
        try {
            copyToRecord(listItem, listItemObject);
        } catch (UnsupportedAttributeException e) {
            e.printStackTrace();
        }
        dataSetItem.setListItem(listItem, index);
    }


    /**
     * @param field
     * @param object
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void readSingleRelationshipData(Field field, T object, Record dataSetItem, int index) throws IllegalAccessException {
        Object relationship = field.get(object);
        try {
            DataSetItem tempItem = dataSetItem.addNewDataSetItem(index);
            copyToRecord(tempItem, relationship);
        } catch (UnsupportedAttributeException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param field
     * @param object
     * @param dataSetItem
     * @param index
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void readRelationshipData(Field field, T object, Record dataSetItem, int index) throws IllegalAccessException {
        List<Object> relationship = (List<Object>) field.get(object);
        try {
            for (Object obj : relationship) {
                DataSetItem tempItem = dataSetItem.addNewDataSetItem(index);
                copyToRecord(tempItem, relationship);
            }
        } catch (UnsupportedAttributeException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param someClass
     * @param <T>
     * @return
     */
    public static <T> ServiceConfiguration generateConfiguration(Class<T> someClass) {
        return new ServiceConfiguration.Builder(someClass.getName()).withAttributes(generateConfigurationAttributes(someClass)).build();
    }


    /**
     * @param someClass
     * @param configName
     * @param <T>
     * @return
     */
    public static <T> ListServiceConfiguration generateListConfiguration(Class<T> someClass, String configName) {
        ListServiceConfiguration listServiceConfiguration = new ListServiceConfiguration(configName);
        listServiceConfiguration.getAttributes().addAll(generateListConfigurationAttributes(someClass));
        return listServiceConfiguration;
    }


    /**
     * @param someClass
     * @param <T>
     * @return
     */
    public static <T> Collection<ListServiceConfigurationAttribute> generateListConfigurationAttributes(Class<T> someClass) {
        Field[] fields = someClass.getDeclaredFields();
        Collection<ListServiceConfigurationAttribute> attributes = new ArrayList<>();
        for (Field field : fields) {
            Attribute attribute = field.getAnnotation(Attribute.class);
            if (attribute != null) {
                attributes.add(getListServiceConfigurationAttributeFromField(field, attribute));
            }
        }
        return attributes;
    }


    /**
     * @param field
     * @param attribute
     * @return
     */
    private static ListServiceConfigurationAttribute getListServiceConfigurationAttributeFromField(Field field, Attribute attribute) {
        int index = attribute.index();
        String name = attribute.name();
        if (StringUtils.isEmpty(name)) {
            name = inferName(field);
        }
        ConverterAttributeType converterAttributeType;
        AttributeType attributeType = attribute.dataType();
        if (attributeType.equals(AttributeType.None)) {
            converterAttributeType = inferDataType(field);
        }
        ListServiceConfigurationAttribute listServiceConfigurationAttribute = new ListServiceConfigurationAttribute();
        listServiceConfigurationAttribute.setAttributeIndex(index);
        listServiceConfigurationAttribute.setLabel(name);
        listServiceConfigurationAttribute.setAttributeType(attributeType);
        return listServiceConfigurationAttribute;
    }


    /**
     * @param someClass
     * @param <T>
     * @return
     */
    public static <T> Collection<ServiceConfigurationAttribute> generateConfigurationAttributes(Class<T> someClass) {
        Field[] fields = someClass.getDeclaredFields();
        Collection<ServiceConfigurationAttribute> attributes = new ArrayList<>();
        for (Field field : fields) {
            Attribute attribute = field.getAnnotation(Attribute.class);
            if (attribute != null) {
                attributes.add(getServiceConfigurationAttributeFromField(field, attribute));
            }
        }
        return attributes;
    }

    /**
     * @param field
     * @param attribute
     * @return
     */
    private static ServiceConfigurationAttribute getServiceConfigurationAttributeFromField(Field field, Attribute attribute) {
        int index = attribute.index();
        String name = attribute.name();
        Class relationShipClass = attribute.relationShipClass();
        if (StringUtils.isEmpty(name)) {
            name = inferName(field);
        }
        AttributeType attributeType = attribute.dataType();
        ConverterAttributeType converterAttributeType;
        if (attributeType.equals(AttributeType.None)) {
            if (attribute.relationShipClass() == Class.class) {
                converterAttributeType = inferDataType(field);
                attributeType = converterAttributeType.getAttributeType();
            } else {
                converterAttributeType = inferDataType(field);
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
            setRelatedServiceConfiguration(attributeType, serviceConfigurationAttribute, name, field.getType(), relationShipClass);
        }
        return serviceConfigurationAttribute;
    }


    /**
     * @param attributeType
     * @return
     */
    private static boolean requiresRelatedServiceConfiguration(AttributeType attributeType) {
        return (attributeType.equals(AttributeType.ListItem) || attributeType.equals(AttributeType.Relation) || attributeType.equals(AttributeType.SingleRelationship));
    }


    /**
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
            serviceConfigurationAttribute.setRelatedService(new RelatedServiceConfiguration(configName, generateConfiguration(clazz1).getAttributes()));
        }
    }

    /**
     * @param field
     * @return
     */
    private static String inferName(Field field) {
        String name = field.getName();
        //TODO: clean up camel Case Variable names
        return name;
    }


    /**
     * @param field
     * @return
     */
    private static ConverterAttributeType inferDataType(Field field) {
        String simpleName = field.getType().getSimpleName();
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
     * @param index
     * @param clazz
     * @return
     */
    private static AttributeMeta inferMetaData(int index, Class clazz) {
        return new AttributeMeta(inferDataType(clazz).getAttributeType(), index);
    }


    /**
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


}
