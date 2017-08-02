package sdk.converter;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import sdk.annotations.Attribute;
import sdk.annotations.PrimaryKey;
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


    public static <T> void copyFromRecord(Record dataSetItem, T destination) {
        if (destination == null) return;
        mapMethodsFromSource(destination);
        for (Field field : destination.getClass().getDeclaredFields()) {
            try {
                copyToField(field, dataSetItem, destination);
            } catch (UnsupportedAttributeException | IllegalAccessException | UnableToWriteException e) {
                e.printStackTrace();
            }
        }
    }

    private static <T> void copyToField(Field field, Record record, T destination) throws UnsupportedAttributeException, IllegalAccessException, UnableToWriteException {
        Attribute attribute = field.getAnnotation(Attribute.class);
        if (attribute == null) {
            return;
        }
        int index = attribute.index();
        Class metaClass = attribute.relationshipClass();
        Class fieldClass = field.getType();
        AttributeMeta attributeMeta = record.getAttributeMeta(index);
        boolean userSetterAndGetter = attribute.useGetterAndSetter();
        if (attributeMeta == null) {
            attributeMeta = inferMetaData(index, fieldClass);
        }
        if (!isFieldClassSupportedForType(fieldClass, attributeMeta.getAttributeType())) {
            throw new UnsupportedAttributeException(fieldClass, attributeMeta.getAttributeType());
        }
        readDataSetItemData(field, attributeMeta, destination, record, metaClass, userSetterAndGetter);
    }

    private static <T> void readDataSetItemData(Field field, AttributeMeta attributeMeta, T destination, Record dataSetItem, Class metaClass, boolean useSetterAndGetter) throws UnableToWriteException {
        switch (attributeMeta.getAttributeType()) {
            case String:
                writeStringData(field, destination, dataSetItem, attributeMeta.getAttributeIndex(), useSetterAndGetter);
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
                writeStringData(field, destination, dataSetItem, attributeMeta.getAttributeIndex(), useSetterAndGetter);
                break;
        }
    }

    private static <T> void writeStringData(Field field, T destination, Record dataSetItem, Integer index, boolean useSetterAndGetter) throws UnableToWriteException {
        String value = dataSetItem.getString(index);
        try {
            if (fieldHasSetter(field, destination)) {
                useSetter(destination, field, value);
            } else field.set(destination, value);
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(field.getClass().getName(), index, AttributeType.String.toString(), e.getMessage());
        }
    }

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

    private static <T> void writeDateData(Field field, T destination, Record dataSetItem, Integer index) throws UnableToWriteException {
        DateTime value = dataSetItem.getDate(index);
        try {
            setDateValueFromField(field, value, destination);
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(field.getClass().getName(), index, AttributeType.Date.toString(), e.getMessage());
        }
    }

    private static <T> void writeDateTimeData(Field field, T destination, Record dataSetItem, Integer index) throws UnableToWriteException {
        DateTime value = dataSetItem.getDateTime(index);
        try {
            setDateValueFromField(field, value, destination);
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(field.getClass().getName(), index, AttributeType.DateTime.toString(), e.getMessage());
        }
    }

    private static boolean fieldIsFloat(Field field) {
        return (field.getType().getName().contains("float") || field.getType().getName().contains("Float"));
    }

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ie) {
            throw new UnableToWriteException(classValue.getName(), index, AttributeType.ListItem.toString(), ie.getMessage());
        }
    }

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ie) {
            throw new UnableToWriteException(classValue.getName(), index, AttributeType.ListItem.toString(), ie.getMessage());
        }
    }

    private static <T> void writeRelationshipData(Field field, T destination, Record dataSetItem, Integer index, Class metaClass) throws UnableToWriteException {
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
            field.set(destination, tempList);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ie) {
            throw new UnableToWriteException(classValue.getName(), index, AttributeType.ListItem.toString(), ie.getMessage());
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
        return supportedTypeMap;
    }

    public static <T> void copyToRecord(Record dataSetItem, T source) {
        if (source == null) return;
        mapMethodsFromSource(source);
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                copyFromField(field, dataSetItem, source);
            } catch (UnsupportedAttributeException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static <T> void copyFromField(Field field, Record dataSetItem, T source) throws UnsupportedAttributeException, IllegalAccessException, InvocationTargetException {
        Attribute attributeAnnotation = field.getAnnotation(Attribute.class);
        PrimaryKey primaryKeyAnnotation = field.getAnnotation(PrimaryKey.class);
        boolean primaryKey = false;
        if (primaryKeyAnnotation != null) {
            primaryKey = true;
        }
        if (primaryKey && attributeAnnotation == null) {
            dataSetItem.setPrimaryKey(field.get(source).toString());
        }
        if (attributeAnnotation == null) {
            return;
        }
        int index = attributeAnnotation.index();
        Class fieldClass = field.getType();
        boolean useGetterAndSetter = attributeAnnotation.useGetterAndSetter();
        AttributeMeta attributeMeta = dataSetItem.getAttributeMeta(index);
        if (attributeMeta == null) {
            attributeMeta = new AttributeMeta(inferDataType(field).getAttributeType(), index);
        }
        if (!isFieldClassSupportedForType(fieldClass, attributeMeta.getAttributeType())) {
            throw new UnsupportedAttributeException(fieldClass, attributeMeta.getAttributeType());
        }
        readObjectData(field, attributeMeta, source, dataSetItem, primaryKey, useGetterAndSetter);
    }

    private static <T> void readObjectData(Field field, AttributeMeta attributeMeta, T object, Record dataSetItem, boolean primaryKey, boolean useGetterAndSetter) throws IllegalAccessException, InvocationTargetException {
        switch (attributeMeta.getAttributeType()) {
            case String:
                readStringData(field, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter);
                break;
            case Int:
                readIntegerData(field, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter);
                break;
            case Double:
                readDoubleData(field, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter);
                break;
            case Boolean:
                readBoolData(field, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter);
                break;
            case Date:
                readDateData(field, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter);
                break;
            case DateTime:
                readDateTimeData(field, object, dataSetItem, attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter);
                break;
            case ListItem:
                readListItemData(field, object, dataSetItem, attributeMeta.getAttributeIndex(), useGetterAndSetter);
                break;
            case Relation:
                readRelationshipData(field, object, dataSetItem, attributeMeta.getAttributeIndex(), useGetterAndSetter);
                break;
            case SingleRelationship:
                readSingleRelationshipData(field, object, dataSetItem, attributeMeta.getAttributeIndex(), useGetterAndSetter);
                break;
            default:
                break;
        }
    }

    private static <T> void readStringData(Field field, T object, Record record, int index, boolean primaryKey, boolean useGetterAndSetter) throws IllegalAccessException, InvocationTargetException {
        Object fieldData = null;
        if (fieldHasGetter(field, object) && useGetterAndSetter) fieldData = useGetter(field, object);
        else fieldData = field.get(object);
        record.setString(fieldData != null ? fieldData.toString() : null, index);
        if (primaryKey) {
            record.setPrimaryKey(fieldData.toString());
        }
    }

    private static <T> void readIntegerData(Field field, T object, Record record, int index, boolean primaryKey, boolean useGetterAndSetter) throws IllegalAccessException {
        Integer fieldData = null;
        if (fieldHasGetter(field, object) && useGetterAndSetter) {
            fieldData = (Integer) useGetter(field, object);
        } else fieldData = (Integer) field.get(object);
        record.setInt(fieldData, index);
        if (primaryKey) {
            record.setPrimaryKey(fieldData.toString());
        }
    }

    private static <T> void readDoubleData(Field field, T object, Record record, int index, boolean primaryKey, boolean useGetterAndSetter) throws IllegalAccessException {
        String fieldName = field.getType().getName();
        Double fieldData = null;
        if (fieldName.contains("Float") || fieldName.contains("float")) {
            Float floatValue = null;
            if (fieldHasGetter(field, object) && useGetterAndSetter) {
                floatValue = (Float) useGetter(field, object);
            } else floatValue = (Float) field.get(object);
            fieldData = new Double(floatValue);
        } else {
            if (fieldHasGetter(field, object) && useGetterAndSetter) {
                fieldData = (Double) useGetter(field, object);
            } else fieldData = (Double) field.get(object);
        }
        record.setDouble(fieldData, index);
        if (primaryKey) {
            record.setPrimaryKey(fieldData.toString());
        }
    }

    private static <T> void readBoolData(Field field, T object, Record record, int index, boolean primaryKey, boolean useGetterAndSetter) throws IllegalAccessException {
        Boolean fieldData = null;
        if (fieldHasGetter(field, object) && useGetterAndSetter) {
            fieldData = (Boolean) useGetter(field, object);
        } else fieldData = (Boolean) field.get(object);
        record.setBool(fieldData, index);
        if (primaryKey) {
            record.setPrimaryKey(fieldData.toString());
        }
    }

    private static <T> void readDateData(Field field, T object, Record record, int index, boolean primaryKey, boolean useGetterAndSetter) throws IllegalAccessException {
        DateTime dateTime = getDateValueFromObject(field, object, useGetterAndSetter);
        record.setDate(dateTime, index);
        if (primaryKey) {
            record.setPrimaryKey(dateTime.toString());
        }
    }

    private static <T> void readDateTimeData(Field field, T object, Record record, int index, boolean primaryKey, boolean useGetterAndSetter) throws IllegalAccessException {
        DateTime dateTime = getDateValueFromObject(field, object, useGetterAndSetter);
        record.setDateTime(dateTime, index);
        if (primaryKey) {
            record.setPrimaryKey(dateTime.toString());
        }
    }

    private static <T> DateTime getDateValueFromObject(Field field, T object, boolean useGetterAndSetter) throws IllegalAccessException {
        List<Class> supportedClasses = getSupportedTypeMap().get(AttributeType.Date);
        if (supportedClasses == null) {
            return new DateTime();
        }
        for (Class clazz : supportedClasses) {
            if (clazz == org.joda.time.DateTime.class && field.getType() == clazz) {
                if (fieldHasGetter(field, object) && useGetterAndSetter) {
                    return (DateTime) useGetter(field, object);
                } else return (DateTime) field.get(object);
            }
            if (clazz == java.util.Date.class && field.getType() == clazz) {
                if (fieldHasGetter(field, object) && useGetterAndSetter) {
                    return new DateTime(((java.util.Date) useGetter(field, object)).getTime());
                } else return new DateTime((Date) field.get(object));
            }
            if (clazz == java.sql.Date.class && field.getType() == clazz) {
                if (fieldHasGetter(field, object) && useGetterAndSetter) {
                    return new DateTime(((java.sql.Date) useGetter(field, object)).getTime());
                } else return new DateTime((java.sql.Date) field.get(object));
            }
        }

        return new DateTime();
    }

    private static <T> void readListItemData(Field field, T object, Record dataSetItem, int index, boolean useGetterAndSetter) throws IllegalAccessException {
        Object listItemObject = null;
        if (useGetterAndSetter) {
            if (fieldHasGetter(field, object)) {
                listItemObject = useGetter(field, object);
            }
        } else listItemObject = field.get(object);
        ListItem listItem = new ListItem();
        copyToRecord(listItem, listItemObject);
        dataSetItem.setListItem(listItem, index);
    }

    private static <T> void readSingleRelationshipData(Field field, T object, Record dataSetItem, int index, boolean useGetterAndSetter) throws IllegalAccessException {
        Object relationship = null;
        if (fieldHasGetter(field, object) && useGetterAndSetter) {
            relationship = useGetter(field, object);
        } else relationship = field.get(object);
        DataSetItem tempItem = dataSetItem.addNewDataSetItem(index);
        copyToRecord(tempItem, relationship);
    }

    private static <T> void readRelationshipData(Field field, T object, Record dataSetItem, int index, boolean useGetterAndSetter) throws IllegalAccessException {
        List<Object> relationship = null;
        if (fieldHasGetter(field, object) && useGetterAndSetter) {
            relationship = (List<Object>) useGetter(field, object);
        } else relationship = (List<Object>) field.get(object);
        for (Object obj : relationship) {
            DataSetItem tempItem = dataSetItem.addNewDataSetItem(index);
            copyToRecord(tempItem, obj);
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
        Class relationShipClass = attribute.relationshipClass();
        if (StringUtils.isEmpty(name)) {
            name = inferName(field);
        }
        AttributeType attributeType = attribute.dataType();
        ConverterAttributeType converterAttributeType;
        if (attributeType.equals(AttributeType.None)) {
            if (attribute.relationshipClass() == Class.class) {
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

    private static <T> boolean fieldHasGetter(Field field, T object) {
        Map<String, Method> tempMap = getMethodMap().get(object.getClass().getName());
        if (tempMap == null) return false;
        return (tempMap.containsKey(getterMethodName(field.getName())));
    }

    private static <T> boolean fieldHasSetter(Field field, T object) {
        Map<String, Method> tempMap = getMethodMap().get(object.getClass().getName());
        if (tempMap == null) return false;
        return (tempMap.containsKey(setterMethodName(field.getName())));
    }

    private static <T> void useSetter(T destination, Field field, Object value) {
        Map<String, Method> tempMethodMap = getMethodMap().get(destination.getClass().getName());
        if (tempMethodMap == null) return;
        Method setterMethod = tempMethodMap.get(setterMethodName(field.getName()));
        try {
            setterMethod.invoke(destination, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static <T> Object useGetter(Field field, T object) {
        Map<String, Method> tempMethodMap = getMethodMap().get(object.getClass().getName());
        if (tempMethodMap == null) return null;
        Method getterMethod = tempMethodMap.get(getterMethodName(field.getName()));
        try {
            return getterMethod.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getterMethodName(String name) {
        return new StringBuilder("get")
                .append(name).toString().toLowerCase();
    }

    private static String setterMethodName(String name) {
        return new StringBuilder("set")
                .append(name).toString().toLowerCase();
    }

    private static <T> void mapMethodsFromSource(T sourceObject) {
        if (sourceObject == null) return;
        String className = sourceObject.getClass().getName();
        Map<String, Method> methodMap = Arrays.stream(sourceObject.getClass().getDeclaredMethods()).distinct().collect(Collectors.toMap(method -> method.getName().toLowerCase(), method -> method, ((method, method2) ->  method)));
        getMethodMap().put(className, methodMap);
    }

    public static Map<String, Map<String, Method>> getMethodMap() {
        if (methodMap == null) {
            methodMap = new HashMap<>();
        }
        return methodMap;
    }

    public static void setMethodMap(Map<String, Map<String, Method>> tempMethodMap) {
        methodMap = tempMethodMap;
    }
}
