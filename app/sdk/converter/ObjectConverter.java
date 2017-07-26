package sdk.converter;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import sdk.annotations.Attribute;
import sdk.data.*;
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
    public ObjectConverter() {
    }

    public static <T> void copyFromRecord(Record dataSetItem, T destination) throws UnsupportedAttributeException, IllegalAccessException {
        for (Field field : destination.getClass().getDeclaredFields()) {
            copyToField(field, dataSetItem, destination);
        }
    }

    private static <T> void copyToField(Field field, Record record, T destination) throws UnsupportedAttributeException, IllegalAccessException {
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

    private static <T> void readDataSetItemData(Field field, AttributeMeta attributeMeta, T destination, Record dataSetItem, Class metaClass) throws IllegalAccessException {
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
                break;
        }
    }

    private static <T> void writeStringData(Field field, T destination, Record dataSetItem, Integer index) throws IllegalAccessException {
        String value = dataSetItem.getString(index);
        field.set(destination, value);
    }

    private static <T> void writeIntegerData(Field field, T destination, Record dataSetItem, Integer index) throws IllegalAccessException {
        Optional<Integer> value = dataSetItem.getOptionalInt(index);
        if (value.isPresent()) {
            field.set(destination, value.get());
        } else {
            ConverterAttributeType converterAttributeType = inferDataType(field);
            field.set(destination, (converterAttributeType.isOptional()) ? null : 0);
        }
    }

    private static <T> void writeDoubleData(Field field, T destination, Record dataSetItem, Integer index) throws IllegalAccessException {
        Optional<Double> value = dataSetItem.getOptionalDouble(index);
        boolean floatValue = fieldIsFloat(field);
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
    }

    private static <T> void writeBoolData(Field field, T destination, Record dataSetItem, Integer index) throws IllegalAccessException {
        Optional<Boolean> value = dataSetItem.getOptionalBoolean(index);
        if (value.isPresent()) {
            field.set(destination, value.get());
        } else {
            ConverterAttributeType converterAttributeType = inferDataType(field);
            field.set(destination, (converterAttributeType.isOptional()) ? null : false);
        }
    }

    private static <T> void writeDateData(Field field, T destination, Record dataSetItem, Integer index) throws IllegalAccessException {
        DateTime value = dataSetItem.getDate(index);
        setDateValueFromField(field, value, destination);
    }

    private static <T> void writeDateTimeData(Field field, T destination, Record dataSetItem, Integer index) throws IllegalAccessException {
        DateTime value = dataSetItem.getDateTime(index);
        setDateValueFromField(field, value, destination);
    }

    private static boolean fieldIsFloat(Field field) {
        return (field.getType().getName().contains("float") || field.getType().getName().contains("Float"));
    }


    private static <T> void writeListItemData(Field field, T destination, Record dataSetItem, Integer index) throws IllegalAccessException {
        ListItem listItem = dataSetItem.getListItem(index);
        if (listItem == null) return;
        Type fieldType = field.getType();
        Class classValue;
        try {
            classValue = Class.forName(fieldType.getTypeName());
            Object object = classValue.newInstance();
            try {
                copyFromRecord(listItem, object);
                field.set(destination, object);
            } catch (UnsupportedAttributeException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {

        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static <T> void writeSingleRelationshipData(Field field, T destination, Record dataSetItem, Integer index) throws IllegalAccessException {
        DataSetItem newDataSetItem = dataSetItem.getDataSetItem(index);
        if (newDataSetItem == null) return;
        Type fieldType = field.getType();
        Class classValue;
        try {
            classValue = Class.forName(fieldType.getTypeName());
            Object object = classValue.newInstance();
            try {
                copyFromRecord(newDataSetItem, object);
                field.set(destination, object);
            } catch (UnsupportedAttributeException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {

        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static <T> void writeRelationshipData(Field field, T destination, Record dataSetItem, Integer index, Class metaClass) throws IllegalAccessException {
        List<DataSetItem> dataSetItems = dataSetItem.getDataSetItems(index);
        if (dataSetItems == null) return;
        Class classValue;
        try {
            classValue = Class.forName(metaClass.getName());
            Object object = classValue.newInstance();
            ArrayList<Object> tempList = new ArrayList<>();
            try {
                for (DataSetItem dataSetItem1 : dataSetItems) {
                    copyFromRecord(dataSetItem1, object);
                    tempList.add(object);
                }
                field.set(destination, tempList);
            } catch (UnsupportedAttributeException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {

        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


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


    private static boolean isFieldClassSupportedForType(Class<?> classType, AttributeType type) {
        if ((type.equals(AttributeType.ListItem) || type.equals(AttributeType.Relation) || type.equals(AttributeType.SingleRelationship)) && !isPrimitiveDataTypeOrWrapper(classType)) {
            return true;
        }
        List<Class> classList = getSupportedTypeMap().get(type);
        return classList != null && classList.contains(classType);
    }

    private static Map<AttributeType, List<Class>> getSupportedTypeMap() {
        //TODO: find a better way to do this
        HashMap<AttributeType, List<Class>> map = new HashMap<>();
        ArrayList<Class> classes = new ArrayList<>();
        classes.add(String.class);
        classes.add(Integer.class);
        classes.add(Float.class);
        map.put(AttributeType.String, classes);
        ArrayList<Class> integerClasses = new ArrayList<>();
        integerClasses.add(Integer.class);
        integerClasses.add(int.class);
        map.put(AttributeType.Int, integerClasses);
        ArrayList<Class> doubleClasses = new ArrayList<>();
        doubleClasses.add(Double.class);
        doubleClasses.add(double.class);
        doubleClasses.add(Float.class);
        doubleClasses.add(float.class);
        doubleClasses.add(Integer.class);
        doubleClasses.add(int.class);
        map.put(AttributeType.Double, doubleClasses);
        ArrayList<Class> boolClasses = new ArrayList<>();
        boolClasses.add(Boolean.class);
        boolClasses.add(boolean.class);
        map.put(AttributeType.Boolean, boolClasses);
        ArrayList<Class> dateClasses = new ArrayList<>();
        dateClasses.add(java.util.Date.class);
        dateClasses.add(org.joda.time.DateTime.class);
        dateClasses.add(java.sql.Date.class);
        map.put(AttributeType.Date, dateClasses);
        ArrayList<Class> dateTimeClasses = new ArrayList<>();
        dateTimeClasses.add(java.util.Date.class);
        dateTimeClasses.add(org.joda.time.DateTime.class);
        dateTimeClasses.add(java.sql.Date.class);
        map.put(AttributeType.DateTime, dateTimeClasses);

        return map;
    }

    //Copy into a data set item from a model object

    public static <T> void copyToRecord(Record dataSetItem, T source) throws UnsupportedAttributeException, IllegalAccessException {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            copyFromField(field, dataSetItem, source);
        }
    }

    private static <T> void copyFromField(Field field, Record dataSetItem, T source) throws UnsupportedAttributeException, IllegalAccessException {
        Attribute attributeAnnotation = field.getAnnotation(Attribute.class);
        if (attributeAnnotation == null) {
            return;
        }
        int index = attributeAnnotation.index();
        Class fieldClass = field.getType();
        AttributeMeta attributeMeta = dataSetItem.getAttributeMeta(index);
        if (attributeMeta == null) {
            attributeMeta = new AttributeMeta(inferDataType(field).getAttributeType(), index);
        }
        if (!isFieldClassSupportedForType(fieldClass, attributeMeta.getAttributeType())) {
            throw new UnsupportedAttributeException(fieldClass, attributeMeta.getAttributeType());
        }
        readObjectData(field, attributeMeta, source, dataSetItem);
    }

    private static <T> void readObjectData(Field field, AttributeMeta attributeMeta, T object, Record dataSetItem) throws IllegalAccessException {
        switch (attributeMeta.getAttributeType()) {
            case String:
                readStringData(field, object, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Int:
                readIntegerData(field, object, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Double:
                readDoubleData(field, object, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Boolean:
                readBoolData(field, object, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Date:
                readDateData(field, object, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case DateTime:
                readDateTimeData(field, object, dataSetItem, attributeMeta.getAttributeIndex());
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

    private static <T> void readStringData(Field field, T object, Record dataSetItem, int index) throws IllegalAccessException {
        Object fieldData = field.get(object);
        dataSetItem.setString(fieldData.toString(), index);
    }


    private static <T> void readIntegerData(Field field, T object, Record dataSetItem, int index) throws IllegalAccessException {
        Integer fieldData = (Integer) field.get(object);
        dataSetItem.setInt(fieldData, index);
    }

    private static <T> void readDoubleData(Field field, T object, Record dataSetItem, int index) throws IllegalAccessException {
        String fieldName = field.getType().getName();
        Double fieldData = null;
        if (fieldName.contains("Float") || fieldName.contains("float")) {
            Float floatValue = (Float) field.get(object);
            fieldData = new Double(floatValue);
        } else {
            fieldData = (Double) field.get(object);
        }
        dataSetItem.setDouble(fieldData, index);
    }

    private static <T> void readBoolData(Field field, T object, Record dataSetItem, int index) throws IllegalAccessException {
        boolean fieldData = (Boolean) field.get(object);
        dataSetItem.setBool(fieldData, index);
    }

    private static <T> void readDateData(Field field, T object, Record dataSetItem, int index) throws IllegalAccessException {
        DateTime dateTime = getDateValueFromObject(field, object);
        dataSetItem.setDate(dateTime, index);
    }

    private static <T> void readDateTimeData(Field field, T object, Record dataSetItem, int index) throws IllegalAccessException {
        DateTime dateTime = getDateValueFromObject(field, object);
        dataSetItem.setDateTime(dateTime, index);
    }

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


    private static <T> void readSingleRelationshipData(Field field, T object, Record dataSetItem, int index) throws IllegalAccessException {
        Object relationship = field.get(object);
        try {
            DataSetItem tempItem = dataSetItem.addNewDataSetItem(index);
            copyToRecord(tempItem, relationship);
        } catch (UnsupportedAttributeException e) {
            e.printStackTrace();
        }
    }


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


    private static void copyFieldToListItem(Field listItemField, ListItem item, Object listItemObject) throws IllegalAccessException {
        Attribute attribute = listItemField.getAnnotation(Attribute.class);
        int index = attribute.index();
        ConverterAttributeType converterAttributeType;
        AttributeType attributeType = attribute.dataType();
        if (attributeType.equals(AttributeType.None)) {
            converterAttributeType = inferDataType(listItemField);
        }
        switch (attributeType) {
            case Int:
                item.setInt((Integer) listItemField.get(listItemObject), index);
                break;
            case Double:
                item.setInt((Integer) listItemField.get(listItemObject), index);
                break;
            case String:
                item.setString((String) listItemField.get(listItemObject), index);
                break;
            case Boolean:
                item.setBool((Boolean) listItemField.get(listItemObject), index);
                break;
        }
    }


    public static <T> ServiceConfiguration generateConfiguration(Class<T> someClass) {
        return new ServiceConfiguration.Builder(someClass.getName()).withAttributes(generateConfigurationAttributes(someClass)).build();
    }


    public static <T> ListServiceConfiguration generateListConfiguration(Class<T> someClass, String configName) {
        ListServiceConfiguration listServiceConfiguration = new ListServiceConfiguration(configName);
        listServiceConfiguration.getAttributes().addAll(generateListConfigurationAttributes(someClass));
        return listServiceConfiguration;
    }

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
        ServiceConfigurationAttribute serviceConfigurationAttribute = new ServiceConfigurationAttribute();
        serviceConfigurationAttribute.setAttributeIndex(index);
        serviceConfigurationAttribute.setAttributeType(attributeType);
        serviceConfigurationAttribute.setName(name);
        if (requiresRelatedServiceConfiguration(attributeType)) {
            setRelatedServiceConfiguration(attributeType, serviceConfigurationAttribute, name, field.getType(), relationShipClass);
        }
        return serviceConfigurationAttribute;
    }


    private static boolean requiresRelatedServiceConfiguration(AttributeType attributeType) {
        return (attributeType.equals(AttributeType.ListItem) || attributeType.equals(AttributeType.Relation) || attributeType.equals(AttributeType.SingleRelationship));
    }


    private static void setRelatedServiceConfiguration(AttributeType attributeType, ServiceConfigurationAttribute serviceConfigurationAttribute, String configName, Class<?> clazz, Class<?> clazz1) {
        if (attributeType.equals(AttributeType.ListItem)) {
            serviceConfigurationAttribute.setRelatedListServiceConfiguration(generateListConfiguration(clazz, configName));
        } else if (attributeType.equals(AttributeType.Relation)) {
            serviceConfigurationAttribute.setRelatedService(new RelatedServiceConfiguration(configName, generateConfiguration(clazz1).getAttributes()));
        } else {
            serviceConfigurationAttribute.setRelatedService(new RelatedServiceConfiguration(configName, generateConfiguration(clazz1).getAttributes()));
        }
    }

    private static String inferName(Field field) {
        String name = field.getName();
        //TODO: clean up camel Case Variable names
        return name;
    }


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

    private static AttributeMeta inferMetaData(int index, Class clazz) {
        return new AttributeMeta(inferDataType(clazz).getAttributeType(), index);
    }


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
