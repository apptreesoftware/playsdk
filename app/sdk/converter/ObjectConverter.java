package sdk.converter;

import org.joda.time.DateTime;
import play.Logger;
import sdk.annotations.CustomLocation;
import sdk.converter.attachment.ApptreeAttachment;
import sdk.data.*;
import sdk.exceptions.DestinationInvalidException;
import sdk.exceptions.UnableToWriteException;
import sdk.exceptions.UnsupportedAttributeException;
import sdk.list.ListItem;
import sdk.models.*;
import sdk.utils.RecordUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;

import static sdk.utils.ClassUtils.Null;
import static sdk.utils.ValidationUtils.NullOrEmpty;

/**
 * Created by Orozco on 7/19/17.
 */
public class ObjectConverter extends ConfigurationManager {
    private static ParserContext parserContext;


    public ObjectConverter() {
    }


    public static <T> DataSet getDataSetFromObject(T t,
                                                   Collection<ServiceConfigurationAttribute> attributes) {
        DataSetItem dataSetItem = new DataSetItem(attributes);
        copyToRecord(dataSetItem, t);
        return new DataSet(dataSetItem);
    }


    /**
     * @param objects
     * @param attributes
     * @param <T>
     * @return
     */
    public static <T> InspectionDataSet getInspectionDataSetFromCollection(Collection<T> objects,
                                                                           Collection<ServiceConfigurationAttribute> attributes) {
        InspectionDataSet dataSet = new InspectionDataSet(attributes);
        if (NullOrEmpty(objects)) return dataSet;
        for (T object : objects) {
            DataSetItem dataSetItem = dataSet.addNewDataSetItem();
            copyToRecord(dataSetItem, object);
        }

        if (objects instanceof PagedCollection) {
            int totalRecords = ((PagedCollection) objects).getTotalAvailableRecords();
            int pageSize = ((PagedCollection) objects).getPageSize();
            dataSet.setTotalRecords(totalRecords);
            dataSet.setMoreRecordsAvailable(totalRecords > pageSize);
        }
        return dataSet;
    }

    public static <T> Collection<T> getCollectionFromDataSet(DataSet dataSet,
                                                             Class<T> clazz,
                                                             ParserContext context) throws
                                                                                    IllegalAccessException,
                                                                                    InstantiationException {
        List<DataSetItem> dataSetItems = dataSet.getDataSetItems();
        Collection<T> result = new ArrayList<>();
        if (NullOrEmpty(dataSetItems)) return Collections.emptyList();
        for (DataSetItem item : dataSetItems) {
            T temp = clazz.newInstance();
            ObjectConverter.copyFromRecord(item, temp, false, context);
            result.add(temp);
        }
        return result;
    }


    /**
     * @param objects
     * @param attributes
     * @param <T>
     * @return
     */
    public static <T> DataSet getDataSetFromCollection(Collection<T> objects,
                                                       Collection<ServiceConfigurationAttribute> attributes) {
        DataSet dataSet = new DataSet(attributes);
        for (T object : objects) {
            DataSetItem dataSetItem = dataSet.addNewDataSetItem();
            copyToRecord(dataSetItem, object);
        }

        if (objects instanceof PagedCollection) {
            int totalRecords = ((PagedCollection) objects).getTotalAvailableRecords();
            int pageSize = ((PagedCollection) objects).getPageSize();
            dataSet.setTotalRecords(totalRecords);
            dataSet.setMoreRecordsAvailable(totalRecords > pageSize);
        }
        return dataSet;
    }

    /**
     * @param objects
     * @param attributes
     * @param <T>
     * @param relationshipsToLoad
     * @return
     */
    public static <T> DataSet getDataSetFromCollection(Collection<T> objects,
                                                       Collection<ServiceConfigurationAttribute> attributes,
                                                       List<Integer> relationshipsToLoad) {
        DataSet dataSet = new DataSet(attributes);
        for (T object : objects) {
            DataSetItem dataSetItem = dataSet.addNewDataSetItem();
            copyToRecord(dataSetItem, object, relationshipsToLoad);
        }

        if (objects instanceof PagedCollection) {
            int totalRecords = ((PagedCollection) objects).getTotalAvailableRecords();
            int pageSize = ((PagedCollection) objects).getPageSize();
            dataSet.setTotalRecords(totalRecords);
            dataSet.setMoreRecordsAvailable(totalRecords > pageSize);
        }
        return dataSet;
    }


    public static <T> ParserContext copyFromRecord(Record record, T destination,
                                                   boolean isSearchForm) {
        return copyFromRecord(record, destination, isSearchForm, null);
    }


    /**
     * Method accepts an object that implements the `Record` interface and an object's members annotated with Attribute annotation
     * This function iterates the annotated members of the "destination" object
     * then copies the value from record object into the annotated field of your "destination" object
     * The link is created by index
     *
     * @param record
     * @param destination
     * @param <T>
     */
    public static <T> ParserContext copyFromRecord(Record record,
                                                   T destination,
                                                   boolean isSearchForm,
                                                   ParserContext parserContext) {
        if (parserContext == null) {
            parserContext = getParserContext();
        }
        parserContext.setSearchForm(isSearchForm);
        mapMethodsFromSource(destination);
        if (destination == null) {
            throw new DestinationInvalidException();
        }
        if (record.supportsCRUDStatus()) {
            parserContext.setItemStatus(destination, record.getCRUDStatus());
        }
        for (AttributeProxy proxy : getMethodAndFieldAnnotationsForClass(destination.getClass())) {
            try {

                copyToField(proxy, record, destination, parserContext);
                //if the dataSetItem is coming from a search form the values in the primary key/value
                // will always be null.
                //If NOT coming from a search form we want the given primary key/value values to
                //always over write what was copied in the object copy because primary key/value should not be editable
                if (!isSearchForm) {
                    if (proxy.isPrimaryKey())
                        proxy.setPrimaryKeyOrValue(destination, record.getPrimaryKey());
                    if (proxy.isPrimaryValue())
                        proxy.setPrimaryKeyOrValue(destination, record.getValue());
                    if (proxy.isStatus())
                        proxy.setPrimaryKeyOrValue(destination, record.getStatus().toString());

                }
            } catch (UnsupportedAttributeException | IllegalAccessException | UnableToWriteException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return parserContext;
    }


    private static ParserContext getParserContext() {
        return new ParserContext();
    }


    /**
     * @param record
     * @param source
     * @param <T>
     */
    public static <T> void copyToRecord(Record record, T source) {
        if (source == null) return;
        mapMethodsFromSource(source);
        for (AttributeProxy attributeProxy : getMethodAndFieldAnnotationsForClass(
            source.getClass())) {
            try {
                copyFromField(attributeProxy, record, source);
            } catch (UnsupportedAttributeException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param record
     * @param source
     * @param <T>
     * @param loadRelationshipIndexes
     */
    public static <T> void copyToRecord(Record record, T source,
                                        List<Integer> loadRelationshipIndexes) {
        if (source == null) return;
        Set<Integer> relationshipsToLoad = new HashSet<>(loadRelationshipIndexes);
        mapMethodsFromSource(source);
        for (AttributeProxy attributeProxy : getMethodAndFieldAnnotationsForClass(
            source.getClass())) {
            try {
                attributeProxy.setLoadRelationshipData(
                    relationshipsToLoad.contains(attributeProxy.getIndex()));
                copyFromField(attributeProxy, record, source);
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
     * @throws UnableToWriteException
     * @throws InvocationTargetException
     */
    private static <T> void copyToField(AttributeProxy proxy, Record record, T destination,
                                        ParserContext parserContext) throws
                                                                     UnsupportedAttributeException,
                                                                     UnableToWriteException,
                                                                     InvocationTargetException {
        if (!proxy.isAttribute() && !proxy.isRelationship() && !proxy.isStatus()) return;
        if (record.isListItem() && proxy.excludeFromList()) return;

        int index = proxy.getIndex();
        Class fieldClass = proxy.getType();
        AttributeMeta attributeMeta = record.getAttributeMeta(index);
        boolean userSetterAndGetter = proxy.useSetterAndGetter();
        if (attributeMeta == null) {
            attributeMeta = inferMetaData(index, fieldClass);
        }
        if (!isFieldClassSupportedForType(fieldClass, attributeMeta.getAttributeType())) {
            throw new UnsupportedAttributeException(fieldClass, attributeMeta.getAttributeType());
        }
        readDataSetItemData(proxy, attributeMeta, destination, record, userSetterAndGetter,
                            parserContext);
    }

    /**
     * @param attributeProxy
     * @param record
     * @param source
     * @param <T>
     * @throws UnsupportedAttributeException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> void copyFromField(AttributeProxy attributeProxy, Record record,
                                          T source) throws UnsupportedAttributeException,
                                                           IllegalAccessException,
                                                           InvocationTargetException {

        if (record.isListItem() && attributeProxy.excludeFromList()) return;

        boolean primaryKey = attributeProxy.isPrimaryKey();
        boolean value = attributeProxy.isPrimaryValue();
        boolean parentValue = attributeProxy.isParentValue();
        boolean status = attributeProxy.isStatus();

        // setting primary key on data set rather than
        // setting the value of the data set item attribute
        if (primaryKey) {
            Object val = useGetterIfExists(attributeProxy, source);
            if (val == null) throw new RuntimeException(
                "Primary key is null on " + source.getClass().getSimpleName());
            record.setPrimaryKey(val.toString());
            if (record.getValue() == null) record.setValue(val.toString());
            if (!attributeProxy.isAttribute()) return;
        }

        // setting status
        if (status) {
            Object val = useGetterIfExists(attributeProxy, source);
            if (val == null) return;
            record.setStatus(val.toString());
            if (!attributeProxy.isAttribute()) return;
        }

        int index = attributeProxy.getIndex();
        Class fieldClass = attributeProxy.getType();
        boolean useGetterAndSetter = attributeProxy.useSetterAndGetter();
        AttributeMeta attributeMeta = record.getAttributeMeta(index);
        if (attributeMeta == null) {
            attributeMeta = new AttributeMeta(
                inferDataType(attributeProxy.getType().getSimpleName()).getAttributeType(), index);
        }
        if (!isFieldClassSupportedForType(fieldClass, attributeMeta.getAttributeType())) {
            throw new UnsupportedAttributeException(fieldClass, attributeMeta.getAttributeType());
        }
        readObjectData(attributeProxy, attributeMeta, source, record, primaryKey,
                       useGetterAndSetter, value, parentValue);
    }

    /**
     * @param proxy
     * @param attributeMeta
     * @param destination
     * @param dataSetItem
     * @param useSetterAndGetter
     * @param <T>
     * @throws UnableToWriteException
     * @throws InvocationTargetException
     */
    private static <T> void readDataSetItemData(AttributeProxy proxy, AttributeMeta attributeMeta,
                                                T destination, Record dataSetItem,
                                                boolean useSetterAndGetter,
                                                ParserContext parserContext) throws
                                                                             UnableToWriteException,
                                                                             InvocationTargetException {
        switch (attributeMeta.getAttributeType()) {
            case String:
                writeStringData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex(),
                                useSetterAndGetter);
                break;
            case Int:
                writeIntegerData(proxy, destination, dataSetItem,
                                 attributeMeta.getAttributeIndex());
                break;
            case Double:
                writeDoubleData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Boolean:
                writeBoolData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case Date:
                writeDateData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex(),
                              parserContext);
                break;
            case DateTime:
                writeDateTimeData(proxy, destination, dataSetItem,
                                  attributeMeta.getAttributeIndex(), parserContext);
                break;
            case ListItem:
                writeListItemData(proxy, destination, dataSetItem,
                                  attributeMeta.getAttributeIndex(), parserContext);
                break;
            case SingleRelationship:
                writeSingleRelationshipData(proxy, destination, dataSetItem,
                                            attributeMeta.getAttributeIndex(), parserContext);
                break;
            case Relation:
                writeRelationshipData(proxy, destination, dataSetItem,
                                      attributeMeta.getAttributeIndex(), parserContext);
                break;
            case Attachments:
                writeAttachmentData(proxy, destination, dataSetItem,
                                    attributeMeta.getAttributeIndex(), parserContext);
                break;
            case Location:
                routeWriteLocationData(proxy, destination, dataSetItem,
                                       attributeMeta.getAttributeIndex());
                break;
            case Color:
                writeColorData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            case TimeInterval:
                writeTimeIntervalData(proxy, destination, dataSetItem,
                                      attributeMeta.getAttributeIndex());
                break;
            case Image:
                writeImageData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex());
                break;
            default:
                writeStringData(proxy, destination, dataSetItem, attributeMeta.getAttributeIndex(),
                                useSetterAndGetter);
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
    private static <T> void readObjectData(AttributeProxy attributeProxy,
                                           AttributeMeta attributeMeta, T object,
                                           Record dataSetItem, boolean primaryKey,
                                           boolean useGetterAndSetter, boolean value,
                                           boolean parentValue) throws IllegalAccessException,
                                                                       InvocationTargetException {
        switch (attributeMeta.getAttributeType()) {
            case String:
                readStringData(attributeProxy, object, dataSetItem,
                               attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter,
                               value, parentValue);
                break;
            case Int:
                readIntegerData(attributeProxy, object, dataSetItem,
                                attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter,
                                value, parentValue);
                break;
            case Double:
                readDoubleData(attributeProxy, object, dataSetItem,
                               attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter,
                               value);
                break;
            case Boolean:
                readBoolData(attributeProxy, object, dataSetItem, attributeMeta.getAttributeIndex(),
                             primaryKey, useGetterAndSetter, value);
                break;
            case Date:
                readDateData(attributeProxy, object, dataSetItem, attributeMeta.getAttributeIndex(),
                             primaryKey, useGetterAndSetter, value);
                break;
            case DateTime:
                readDateTimeData(attributeProxy, object, dataSetItem,
                                 attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter,
                                 value);
                break;
            case ListItem:
                readListItemData(attributeProxy, object, dataSetItem,
                                 attributeMeta.getAttributeIndex(), useGetterAndSetter);
                break;
            case Relation:
                readRelationshipData(attributeProxy, object, dataSetItem,
                                     attributeMeta.getAttributeIndex(), useGetterAndSetter);
                break;
            case SingleRelationship:
                readSingleRelationshipData(attributeProxy, object, dataSetItem,
                                           attributeMeta.getAttributeIndex(), useGetterAndSetter);
                break;
            case Attachments:
                readAttachmentData(attributeProxy, object, dataSetItem,
                                   attributeMeta.getAttributeIndex(), useGetterAndSetter);
                break;
            case Location:
                readLocationData(attributeProxy, object, dataSetItem,
                                 attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter,
                                 value);
                break;
            case TimeInterval:
                readTimeIntervalData(attributeProxy, object, dataSetItem,
                                     attributeMeta.getAttributeIndex(), primaryKey,
                                     useGetterAndSetter);
                break;
            case Color:
                readColorData(attributeProxy, object, dataSetItem,
                              attributeMeta.getAttributeIndex(), useGetterAndSetter);
                break;
            case Image:
                readImageData(attributeProxy, object, dataSetItem,
                              attributeMeta.getAttributeIndex(), primaryKey, useGetterAndSetter);
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
    private static <T> void writeStringData(AttributeProxy proxy, T destination, Record dataSetItem,
                                            Integer index, boolean useSetterAndGetter) throws
                                                                                       UnableToWriteException,
                                                                                       InvocationTargetException {
        String value = dataSetItem.getString(index);
        try {
            useSetterIfExists(proxy, destination, value);
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(proxy.getType().getClass().getName(), index,
                                             AttributeType.String.toString(), e.getMessage());
        }
    }

    private static <T> void writeIntegerData(AttributeProxy proxy, T destination,
                                             Record dataSetItem, Integer index) throws
                                                                                UnableToWriteException {
        Optional<Integer> value = dataSetItem.getOptionalInt(index);
        Integer intValue = 0;
        try {
            if (value.isPresent()) {
                intValue = value.get();
            } else {
                ConverterAttributeType converterAttributeType =
                    inferDataType(proxy.getType().getSimpleName());
                intValue = (converterAttributeType.isOptional()) ? null : 0;
            }
            useSetterIfExists(proxy, destination, intValue);
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(proxy.getType().getName(), index,
                                             AttributeType.Int.toString(), e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    private static <T> void writeTimeIntervalData(AttributeProxy proxy, T destination,
                                                  Record record, Integer index) throws
                                                                                UnableToWriteException {
        Optional<Long> value = record.getOptionalTimeInterval(index);
        Long longValue = 0L;
        try {
            if (value.isPresent()) {
                longValue = value.get();
            } else {
                ConverterAttributeType converterAttributeType =
                    inferDataType(proxy.getType().getSimpleName());
                longValue = (converterAttributeType.isOptional()) ? null : 0L;
            }
            useSetterIfExists(proxy, destination, longValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnableToWriteException(proxy.getType().getName(), index,
                                             AttributeType.Int.toString(), e.getMessage());
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
    private static <T> void writeDoubleData(AttributeProxy proxy, T destination, Record dataSetItem,
                                            Integer index) throws UnableToWriteException,
                                                                  InvocationTargetException {
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
                ConverterAttributeType converterAttributeType =
                    inferDataType(proxy.getType().getSimpleName());
                if (converterAttributeType.isOptional()) {
                    useSetterIfExists(proxy, destination, null);
                } else {
                    if (isFloatValue)
                        useSetterIfExists(proxy, destination, 0.0f);
                    else useSetterIfExists(proxy, destination, 0.0);
                }
            }
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(proxy.getType().getName(), index,
                                             AttributeType.Double.toString(), e.getMessage());
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
    private static <T> void writeBoolData(AttributeProxy proxy, T destination, Record dataSetItem,
                                          Integer index) throws UnableToWriteException,
                                                                InvocationTargetException {
        Optional<Boolean> value = dataSetItem.getOptionalBoolean(index);
        try {
            if (value.isPresent()) {
                useSetterIfExists(proxy, destination, value.get());
            } else {
                ConverterAttributeType converterAttributeType =
                    inferDataType(proxy.getType().getSimpleName());
                useSetterIfExists(proxy, destination,
                                  (converterAttributeType.isOptional()) ? null : false);
            }
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(proxy.getType().getName(), index,
                                             AttributeType.Boolean.toString(), e.getMessage());
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
    private static <T> void writeDateData(AttributeProxy proxy, T destination, Record dataSetItem,
                                          Integer index, ParserContext parserContext) throws
                                                                                      UnableToWriteException,
                                                                                      InvocationTargetException {
        DateTime value = dataSetItem.getDate(index);
        if (Null(
            value)) { // if value is null we want to check and see if there is a date time range avail. at that index
            DateRange dateRange = dataSetItem.getDateRange(index);
            if (!Null(dateRange)) {
                parserContext.putDateTimeRange(index, proxy.getName(), dateRange);
            }
        }
        try {
            setDateValueFromField(proxy, value, destination);
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(proxy.getType().getName(), index,
                                             AttributeType.Date.toString(), e.getMessage());
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
    private static <T> void writeDateTimeData(AttributeProxy proxy, T destination,
                                              Record dataSetItem, Integer index,
                                              ParserContext parserContext) throws
                                                                           UnableToWriteException,
                                                                           InvocationTargetException {
        DateTime value = dataSetItem.getDateTime(index);
        if (Null(
            value)) { // if value is null we want to check and see if there is a date time range avail. at that index
            DateTimeRange dateTimeRange = dataSetItem.getDateTimeRange(index);
            if (!Null(dateTimeRange)) {
                parserContext.putDateTimeRange(index, proxy.getName(), dateTimeRange);
            }
        }
        try {
            setDateValueFromField(proxy, value, destination);
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(proxy.getType().getName(), index,
                                             AttributeType.DateTime.toString(), e.getMessage());
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
    private static <T> void writeListItemData(AttributeProxy proxy, T destination,
                                              Record dataSetItem, Integer index,
                                              ParserContext parserContext) throws
                                                                           UnableToWriteException,
                                                                           InvocationTargetException {
        ListItem listItem = dataSetItem.getListItem(index);
        if (listItem == null) return;
        Type fieldType = proxy.getType();
        Class classValue = null;
        copyFromRecordRecursive(proxy,
                                fieldType,
                                classValue,
                                listItem,
                                destination,
                                index,
                                parserContext);
    }

    /**
     * @param proxy
     * @param destination
     * @param record
     * @param index
     * @param <T>
     * @throws UnableToWriteException
     * @throws InvocationTargetException
     */
    private static <T> void writeSingleRelationshipData(AttributeProxy proxy, T destination,
                                                        Record record, Integer index,
                                                        ParserContext parserContext) throws
                                                                                     UnableToWriteException,
                                                                                     InvocationTargetException {
        parserContext.setItemStatus(destination, record.getCRUDStatus());
        DataSetItem newDataSetItem = record.getDataSetItem(index);
        if (newDataSetItem == null) return;
        Type fieldType = proxy.getType();
        Class classValue = null;
        copyFromRecordRecursive(proxy,
                                fieldType,
                                classValue,
                                newDataSetItem,
                                destination,
                                index,
                                parserContext);
    }

    private static <T> void writeImageData(AttributeProxy proxy, T destination,
                                           Record record, Integer index) throws
                                                                         UnableToWriteException,
                                                                         InvocationTargetException {
        Image value = record.getImage(index);
        try {
            if (value != null) useSetterIfExists(proxy, destination, value);
        } catch (IllegalAccessException e) {
            throw new UnableToWriteException(proxy.getType().getName(), index,
                                             AttributeType.Boolean.toString(), e.getMessage());
        }
    }

    private static <T> void copyFromRecordRecursive(AttributeProxy proxy, Type fieldType,
                                                    Class classValue, Record record, T destination,
                                                    Integer index, ParserContext parserContext)
        throws InvocationTargetException, UnableToWriteException {
        try {
            if (!findTypeOnSimpleName(fieldType.getTypeName()).isOptional())
                classValue = primitiveToWrapper(fieldType.getTypeName());
            else classValue = Class.forName(fieldType.getTypeName());
            Object object = classValue.newInstance();
            copyFromRecord(record,
                           object,
                           parserContext.isSearchForm(),
                           parserContext);
            useSetterIfExists(proxy, destination, object);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ie) {
            throw new UnableToWriteException(classValue.getName(), index,
                                             AttributeType.ListItem.toString(), ie.getMessage());
        }
    }

    /**
     * @param proxy
     * @param destination
     * @param record
     * @param index
     * @param <T>
     * @throws UnableToWriteException
     * @throws InvocationTargetException
     */
    private static <T> void writeRelationshipData(AttributeProxy proxy, T destination,
                                                  Record record, Integer index,
                                                  ParserContext parserContext) throws
                                                                               UnableToWriteException,
                                                                               InvocationTargetException {
        List<DataSetItem> dataSetItems = record.getDataSetItems(index);
        parserContext.setItemStatus(destination, record.getCRUDStatus());
        if (dataSetItems == null) return;
        Class classValue = null;
        try {
            classValue = Class.forName(proxy.getType().getName());
            ArrayList<Object> tempList = new ArrayList<>();
            for (DataSetItem dataSetItem1 : dataSetItems) {
                Object object = classValue.newInstance();
                copyFromRecord(dataSetItem1, object, parserContext.isSearchForm(), parserContext);
                tempList.add(object);
            }
            useSetterIfExists(proxy, destination, tempList);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ie) {
            throw new UnableToWriteException(classValue.getName(), index,
                                             AttributeType.ListItem.toString(), ie.getMessage());
        }
    }

    private static <T> void routeWriteLocationData(AttributeProxy proxy, T destination,
                                                   Record dataSetItem, Integer index)
        throws UnableToWriteException, InvocationTargetException {
        if (CustomLocation.class.isAssignableFrom(proxy.getType()))
            writeCustomLocationData(proxy, destination, dataSetItem, index);
        else writeLocationData(proxy, destination, dataSetItem, index);
    }

    private static <T, C extends CustomLocation> void writeCustomLocationData(AttributeProxy proxy,
                                                                              T destination,
                                                                              Record dataSetItem,
                                                                              Integer index)
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

    private static <T> void writeLocationData(AttributeProxy proxy, T destination,
                                              Record dataSetItem, Integer index)
        throws UnableToWriteException, InvocationTargetException {
        Location location = dataSetItem.getLocation(index);
        if (location == null) return;
        try {
            useSetterIfExists(proxy, destination, location);
        } catch (Exception error) {
            throw new RuntimeException(
                "Unable to write Location data for field: " + proxy.getName());
        }
    }

    private static <T> void writeColorData(AttributeProxy proxy, T destination, Record record,
                                           Integer index) {
        Color color = record.getColor(index);
        if (color == null) return;
        try {
            useSetterIfExists(proxy, destination, color);
        } catch (Exception error) {
            throw new RuntimeException("Unable to write Color data for field: " + proxy.getName());
        }
    }


    private static <T> void writeAttachmentData(AttributeProxy proxy, T destination, Record record,
                                                Integer index, ParserContext parserContext) throws
                                                                                            UnableToWriteException,
                                                                                            InvocationTargetException {
        List<DataSetItemAttachment> attachmentItems = record.getAttachmentItemsForIndex(index);
        parserContext.setItemStatus(destination, record.getCRUDStatus());
        if (attachmentItems == null) return;
        try {
            ApptreeAttachment singleAttachment = (ApptreeAttachment) proxy.getType().newInstance();
            if (proxy.isWrappedClass()) {
                Collection<ApptreeAttachment> attachmentList =
                    RecordUtils.copyListOfAttachmentsFromRecordForIndex(attachmentItems, proxy);
                useSetterIfExists(proxy, destination, attachmentList);
            } else {
                RecordUtils.copyAttachmentFromRecordForIndex(attachmentItems, singleAttachment);
                useSetterIfExists(proxy, destination, singleAttachment);
            }
        } catch (IllegalAccessException ie) {
            ie.printStackTrace();
            throw new UnableToWriteException(proxy.getType().getName(), index,
                                             AttributeType.ListItem.toString(), ie.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
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
    private static <T> void setDateValueFromField(AttributeProxy proxy, DateTime datetime,
                                                  T destination) throws IllegalAccessException,
                                                                        InvocationTargetException {
        if (Null(datetime)) return;
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

    public static void copyToAttachment(DataSetItemAttachment attachmentItem, Object object) {
        ApptreeAttachment apptreeAttachment = (ApptreeAttachment) object;
        ObjectConverter.copyToRecord(attachmentItem, apptreeAttachment);
        attachmentItem.setMimeType(apptreeAttachment.getMimeType());
        attachmentItem.setTitle(apptreeAttachment.getTitle());

        // We are clearing the opposite file url index
        // based on the one we set because the above line:
        // `ObjectConverter.copyToRecord(attachmentItem, apptreeAttachment)`
        // might set a value for one of those indexes and cause the client to think it is an image/file
        // when it's not.
        if (isImageMimeType(apptreeAttachment.getMimeType())) {
            attachmentItem.setImageAttachmentURL(apptreeAttachment.getAttachmentURL());
            attachmentItem.setFileAttachmentURL(null);
        } else {
            attachmentItem.setFileAttachmentURL(apptreeAttachment.getAttachmentURL());
            attachmentItem.setImageAttachmentURL(null);
        }

    }


    public static void copyFromAttachment(DataSetItemAttachment attachmentItem,
                                          ApptreeAttachment object) {
        object.setAttachmentURL(attachmentItem.getFileAttachmentURL());
        object.setMimeType(attachmentItem.getMimeType());
        object.setTitle(attachmentItem.getTitle());
        if (attachmentItem.getAttachmentBytes() != null) {
            InputStream byteArrayInputStream =
                new ByteArrayInputStream(attachmentItem.getAttachmentBytes());
            object.setInputStream(byteArrayInputStream);
        } else if (attachmentItem.getCRUDStatus() == DataSetItem.CRUDStatus.Create) {
            throw new RuntimeException("Attachment uploaded without any data.");
        }
    }


    /**
     * This function takes a mime type string and determines if it
     * is an image mime type or not. It checks if the mimeType string contains "image" || "Image"
     * <p>
     * This function will default to false if the mimetype is empty or null
     *
     * @param mimeType
     * @returnx
     */
    private static boolean isImageMimeType(String mimeType) {
        if (NullOrEmpty(mimeType)) return false;
        return mimeType.contains("image") || mimeType.contains("IMAGE");
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
    private static <T> void readStringData(AttributeProxy attributeProxy, T object, Record record,
                                           int index, boolean primaryKey,
                                           boolean useGetterAndSetter, boolean value,
                                           boolean parent) throws IllegalAccessException,
                                                                  InvocationTargetException {
        Object fieldData;
        if (useGetterAndSetter) fieldData = useGetterIfExists(attributeProxy, object);
        else fieldData = attributeProxy.getValue(object);
        boolean isFieldDataNull = fieldData == null;
        record.setString((!isFieldDataNull) ? fieldData.toString() : null, index);
        if (parent) {
            if (isFieldDataNull) nullAttrWarning(attributeProxy.getName(), "parent");
            else {
                // Data set items can't have parent values so we want to make sure we are
                // setting a parent on a list item
                if (record.isListItem()) {
                    record.setParentValue(fieldData.toString());
                }
            }
        }
        if (value) {
            if (isFieldDataNull) nullAttrWarning(attributeProxy.getName(), "value");
            else record.setValue(fieldData.toString());
        }
        if (primaryKey) {
            if (isFieldDataNull) nullAttrWarning(attributeProxy.getName(), "primary key");
            else {
                record.setPrimaryKey(fieldData.toString());
                if (!record.isValueSet()) {
                    record.setValue(fieldData.toString());
                }
            }
        }
    }


    private static <T> void readTimeIntervalData(AttributeProxy attributeProxy, T object,
                                                 Record record, int index, boolean primaryKey,
                                                 boolean useGetterAndSetter) throws
                                                                             IllegalAccessException,
                                                                             InvocationTargetException {
        Object fieldData;
        if (useGetterAndSetter) fieldData = useGetterIfExists(attributeProxy, object);
        else fieldData = attributeProxy.getValue(object);
        record.setTimeInterval(fieldData != null ? (long) fieldData : 0L, index);
        if (fieldData == null && primaryKey) {
            nullAttrWarning(attributeProxy.getName(), "primary key");
        } else {
            if (primaryKey) {
                record.setPrimaryKey(fieldData.toString());
                if (!record.isValueSet()) {
                    record.setValue(fieldData.toString());
                }
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
    private static <T> void readIntegerData(AttributeProxy attributeProxy, T object, Record record,
                                            int index, boolean primaryKey,
                                            boolean useGetterAndSetter, boolean value,
                                            boolean parent) throws IllegalAccessException,
                                                                   InvocationTargetException {
        Integer fieldData;
        if (useGetterAndSetter) {
            fieldData = (Integer) useGetterIfExists(attributeProxy, object);
        } else fieldData = (Integer) attributeProxy.getValue(object);
        boolean isFieldDataNull = fieldData == null;
        fieldData = (isFieldDataNull) ? 0 : fieldData;
        if (!isFieldDataNull) record.setInt(fieldData, index);
        if (parent) {
            // Data set items can't have parent values so we want to make sure we are
            // setting a parent on a list item
            if (record.isListItem()) {
                record.setParentValue(fieldData.toString());
            }
        }
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
    private static <T> void readDoubleData(AttributeProxy attributeProxy, T object, Record record,
                                           int index, boolean primaryKey,
                                           boolean useGetterAndSetter, boolean value) throws
                                                                                      IllegalAccessException,
                                                                                      InvocationTargetException {
        String fieldName = attributeProxy.getDataTypeName();
        Double fieldData;
        if (fieldName.contains("Float") || fieldName.contains("float")) {
            Float floatValue;
            if (useGetterAndSetter) {
                floatValue = (Float) useGetterIfExists(attributeProxy, object);
            } else floatValue = (Float) attributeProxy.getValue(object);
            if (floatValue == null || floatValue.isNaN()) {
                fieldData = 0.0;
            } else {
                fieldData = new Double(floatValue);
            }
        } else {
            if (useGetterAndSetter) {
                fieldData = (Double) useGetterIfExists(attributeProxy, object);
            } else fieldData = (Double) attributeProxy.getValue(object);
        }
        boolean isFieldDataNull = fieldData == null;
        fieldData = (fieldData == null) ? 0.0 : fieldData;
        if (!isFieldDataNull) record.setDouble(fieldData, index);
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
    private static <T> void readBoolData(AttributeProxy attributeProxy, T object, Record record,
                                         int index, boolean primaryKey, boolean useGetterAndSetter,
                                         boolean value) throws IllegalAccessException,
                                                               InvocationTargetException {
        Boolean fieldData;
        if (useGetterAndSetter) {
            fieldData = (Boolean) useGetterIfExists(attributeProxy, object);
        } else fieldData = (Boolean) attributeProxy.getValue(object);
        fieldData = (fieldData != null) ? fieldData : false;
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
    private static <T> void readDateData(AttributeProxy attributeProxy, T object, Record record,
                                         int index, boolean primaryKey, boolean useGetterAndSetter,
                                         boolean value) throws IllegalAccessException,
                                                               InvocationTargetException {
        DateTime dateTime = getDateValueFromObject(attributeProxy, object, useGetterAndSetter);
        record.setDate(dateTime, index);
        boolean isFieldDataNull = dateTime == null;
        if (value) {
            if (isFieldDataNull) nullAttrWarning(attributeProxy.getName(), "value");
            else record.setValue(dateTime.toString());
        }
        if (primaryKey) {
            if (isFieldDataNull) nullAttrWarning(attributeProxy.getName(), "primary key");
            else {
                record.setPrimaryKey(dateTime.toString());
                if (!record.isValueSet()) {
                    record.setValue(dateTime.toString());
                }
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
    private static <T> void readDateTimeData(AttributeProxy attributeProxy, T object, Record record,
                                             int index, boolean primaryKey,
                                             boolean useGetterAndSetter, boolean value) throws
                                                                                        IllegalAccessException,
                                                                                        InvocationTargetException {
        DateTime dateTime = getDateValueFromObject(attributeProxy, object, useGetterAndSetter);
        record.setDateTime(dateTime, index);
        boolean isFieldDataNull = dateTime == null;
        if (value) {
            if (isFieldDataNull) nullAttrWarning(attributeProxy.getName(), "value");
            else record.setValue(dateTime.toString());
        }
        if (primaryKey) {
            if (isFieldDataNull) nullAttrWarning(attributeProxy.getName(), "primary key");
            else {
                record.setPrimaryKey(dateTime.toString());
                if (!record.isValueSet()) {
                    record.setValue(dateTime.toString());
                }
            }
        }
    }

    private static <T> void readLocationData(AttributeProxy proxy, T object, Record record,
                                             int index, boolean primaryKey,
                                             boolean useGetterAndSetter, boolean value) throws
                                                                                        IllegalAccessException,
                                                                                        InvocationTargetException {
        Location location = getLocationValueFromObject(proxy, object, useGetterAndSetter);
        record.setLocation(location, index);
        boolean isFieldDataNull = location == null;
        if (value) {
            if (isFieldDataNull) nullAttrWarning(proxy.getName(), "value");
            else record.setValue(location.toString());
        }
        if (primaryKey) {
            if (isFieldDataNull) nullAttrWarning(proxy.getName(), "primary key");
            else {
                record.setPrimaryKey(location.toString());
                if (!record.isValueSet()) {
                    record.setValue(location.toString());
                }
            }
        }
    }

    private static <T> void readImageData(AttributeProxy proxy, T object, Record record,
                                          int index, boolean primaryKey,
                                          boolean useGetterAndSetter) throws
                                                                      IllegalAccessException,
                                                                      InvocationTargetException {
        Image foundImage;
        if (fieldHasGetter(proxy, object) && useGetterAndSetter) {
            foundImage = (Image) useGetterIfExists(proxy, object);
        } else foundImage = (Image) proxy.getValue(object);
        record.setImage(foundImage, index);
        if (primaryKey) {
            if (foundImage == null) nullAttrWarning(proxy.getName(), "primary key");
            else {
                record.setPrimaryKey(foundImage.imageURL);
                if (!record.isValueSet()) record.setValue(foundImage.imageURL);
            }
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
    private static <T> DateTime getDateValueFromObject(AttributeProxy attributeProxy, T object,
                                                       boolean useGetterAndSetter) throws
                                                                                   IllegalAccessException,
                                                                                   InvocationTargetException {
        List<Class> supportedClasses = getSupportedTypeMap().get(AttributeType.Date);
        if (supportedClasses == null) {
            return new DateTime();
        }
        for (Class clazz : supportedClasses) {
            if (clazz == org.joda.time.DateTime.class && attributeProxy.getType() == clazz) {
                if (fieldHasGetter(attributeProxy, object) && useGetterAndSetter) {
                    return (DateTime) useGetterIfExists(attributeProxy, object);
                } else return (DateTime) attributeProxy.getValue(object);
            }
            if (clazz == java.util.Date.class && attributeProxy.getType() == clazz) {
                if (fieldHasGetter(attributeProxy, object) && useGetterAndSetter) {
                    return new DateTime(
                        ((java.util.Date) useGetterIfExists(attributeProxy, object)).getTime());
                } else return new DateTime((Date) attributeProxy.getValue(object));
            }
            if (clazz == java.sql.Date.class && attributeProxy.getType() == clazz) {
                if (fieldHasGetter(attributeProxy, object) && useGetterAndSetter) {
                    return new DateTime(
                        ((java.sql.Date) useGetterIfExists(attributeProxy, object)).getTime());
                } else return new DateTime((java.sql.Date) attributeProxy.getValue(object));
            }
        }

        return new DateTime();
    }

    private static <T> void readColorData(AttributeProxy proxy, T object, Record record, int index,
                                          boolean useGetterAndSetter) {
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
    private static <T> void readListItemData(AttributeProxy attributeProxy, T object,
                                             Record dataSetItem, int index,
                                             boolean useGetterAndSetter) throws
                                                                         IllegalAccessException,
                                                                         InvocationTargetException {
        Object listItemObject;
        if (useGetterAndSetter) {
            listItemObject = useGetterIfExists(attributeProxy, object);
        } else listItemObject = attributeProxy.getValue(object);
        ListItem listItem = new ListItem();
        copyToRecord(listItem, listItemObject);
        if (!dataSetItem.isListItem()) {
            dataSetItem.setListItem(listItem, index);
        }
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
    private static <T> void readSingleRelationshipData(AttributeProxy attributeProxy, T object,
                                                       Record dataSetItem, int index,
                                                       boolean useGetterAndSetter) throws
                                                                                   IllegalAccessException,
                                                                                   InvocationTargetException {
        Object relationship;
        if (useGetterAndSetter) {
            relationship = useGetterIfExists(attributeProxy, object);
        } else relationship = attributeProxy.getValue(object);
        if (relationship == null) {
            return;
        }
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
    private static <T> void readAttachmentData(AttributeProxy attributeProxy, T object,
                                               Record dataSetItem, int index,
                                               boolean useGetterAndSetter) throws
                                                                           IllegalAccessException,
                                                                           InvocationTargetException {
        Object relationship = null;
        List<Object> relationships = null;

        if (useGetterAndSetter) {
            if (attributeProxy.isWrappedClass()) {
                relationships = (List<Object>) useGetterIfExists(attributeProxy, object);
            } else {
                relationship = useGetterIfExists(attributeProxy, object);
            }
        } else {
            if (attributeProxy.isWrappedClass()) {
                relationships = (List<Object>) attributeProxy.getValue(object);
            } else {
                relationship = attributeProxy.getValue(object);
            }
        }

        if (Null(relationship) && !Null(relationships)) {
            RecordUtils.copyListOfAttachmentsToRecordForIndex(relationships, dataSetItem, index);
        }

        if (!Null(relationship) && Null(relationships)) {
            RecordUtils.copySingleAttachmentToRecordForIndex(relationship, dataSetItem, index);
        }

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
    private static <T> void readRelationshipData(AttributeProxy attributeProxy, T object,
                                                 Record dataSetItem, int index,
                                                 boolean useGetterAndSetter) throws
                                                                             IllegalAccessException,
                                                                             InvocationTargetException {
        List<Object> relationship;
        if (attributeProxy.useLazyLoad()) {
            if (attributeProxy.hasRelationshipPath()) {
                dataSetItem.useLazyLoad(index, attributeProxy.getPath());
            } else {
                dataSetItem.useLazyLoad(index);
            }
        }
        if (attributeProxy.loadRelationshipData() ||
            attributeProxy.getRelationshipAnnotation().eager()) {
            if (useGetterAndSetter) {
                relationship = (List<Object>) useGetterIfExists(attributeProxy, object);
            } else relationship = (List<Object>) attributeProxy.getValue(object);
            if (!Null(relationship)) {
                for (Object obj : relationship) {
                    DataSetItem tempItem = dataSetItem.addNewDataSetItem(index);
                    copyToRecord(tempItem, obj);
                }
            }
        }
    }

    private static <T, C extends CustomLocation> Location getLocationValueFromObject(
        AttributeProxy proxy, T object, boolean useGetterAndSetter)
        throws IllegalAccessException, InvocationTargetException {
        List<Class> supportedClasses = getSupportedTypeMap().get(AttributeType.Location);
        if (supportedClasses == null) return new Location();
        if (proxy.getType() == Location.class) {
            if (fieldHasGetter(proxy, object) && useGetterAndSetter)
                return (Location) useGetterIfExists(proxy, object);
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

    private static <T> Color getColorValueFromObject(AttributeProxy proxy, T object,
                                                     boolean useGetterAndSetter) {
        if (getSupportedTypeMap().get(AttributeType.Color) == null) return new Color();
        if (proxy.getType() == Color.class) {
            try {
                if (fieldHasGetter(proxy, object) && useGetterAndSetter)
                    return (Color) useGetterIfExists(proxy, object);
                Color objColor = (Color) proxy.getValue(object);
                return new Color(objColor.getR(), objColor.getG(), objColor.getB(),
                                 objColor.getA());
            } catch (Exception error) {
            }
        }
        return new Color();
    }


    private static boolean isChildOfApptreeSpecificClass(Class clazz) {
        return (ApptreeAttachment.class.isAssignableFrom(clazz));
    }

    private static AttributeType getAttributeTypeFromApptreeSpecificClass(Class clazz) {
        if (ApptreeAttachment.class.isAssignableFrom(clazz)) {
            return AttributeType.Attachments;
        }
        return AttributeType.None;
    }

    private static void nullAttrWarning(String objName, String fieldName) {
        Logger.warn(String.format("Setting %s on %s as null.", fieldName, objName));
    }
}
