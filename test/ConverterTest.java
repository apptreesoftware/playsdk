import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import sdk.annotations.Attribute;
import sdk.converter.AttributeProxy;
import sdk.converter.ObjectConverter;
import sdk.data.DataSetItem;
import sdk.data.Record;
import sdk.data.ServiceConfiguration;
import sdk.exceptions.UnableToWriteException;
import sdk.exceptions.UnsupportedAttributeException;
import sdk.list.ListItem;
import sdk.models.Color;
import sdk.models.Location;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Orozco on 7/19/17.
 */
public class ConverterTest {

    public SampleObject getSampleObject() {
        SampleObject sampleObject = new SampleObject();
        sampleObject.woNumber = "1234";
        sampleObject.setTestInt(1);
        sampleObject.setTestIntObject(1);
        sampleObject.setTestFloat(1.0f);
        sampleObject.setTestFloatObject(1.0f);
        sampleObject.setTestDouble(1.0);
        sampleObject.setTestDoubleObject(1.0);
        sampleObject.setTestDate(new Date(100));
        sampleObject.setTestJodaTimeDate(new DateTime(100));
        sampleObject.setTestSqlDate(new java.sql.Date(100));
        sampleObject.setSampleListItem(new SampleListItem());
        sampleObject.sampleListItem.woNumber = "1234";
        sampleObject.sampleListItem.testInt = 1;
        sampleObject.sampleListItem.testIntObject = 1;
        sampleObject.sampleListItem.testFloat = 1.0f;
        sampleObject.sampleListItem.testFloatObject = 1.0f;
        sampleObject.sampleListItem.testDouble = 1.0;
        sampleObject.sampleListItem.testDoubleObject = 1.0;
        sampleObject.sampleListItem.testDate = new Date(100);
        sampleObject.sampleListItem.testJodaTimeDate = new DateTime(100);
        sampleObject.sampleListItem.testSqlDate = new java.sql.Date(100);
        sampleObject.color = new Color(255, 155, 55, 5);
        sampleObject.sampleAttachment = new ArrayList<>();
        SampleAttachment sampleAttachment = new SampleAttachment();
        sampleAttachment.setAttachmentURL("http://testattachment.url");
        sampleAttachment.setMimeType("test/mimetype");
        sampleAttachment.setTitle("First Attachment");
        SampleAttachment sampleAttachment2 = new SampleAttachment();
        sampleAttachment2.setAttachmentURL("http://testattachment.url");
        sampleAttachment2.setMimeType("test/mimetype");
        sampleAttachment2.setTitle("second Attachment");
        sampleObject.sampleAttachment.add(sampleAttachment);
        sampleObject.sampleAttachment.add(sampleAttachment2);
        sampleObject.sampleSingleAttachment = sampleAttachment2;
        sampleObject.customLocation = getCustomLocation();
        sampleObject.sdkLocation = getSDKLocation();

        return sampleObject;
    }


    private SampleLocation getCustomLocation() {
        SampleLocation location = new SampleLocation();
        location.setLatitude(11.0);
        location.setLongitude(232323.0);
        return location;
    }


    private Location getSDKLocation() {
        Location location = new Location();
        location.setLatitude(1122.0);
        location.setLongitude(12121.0);
        return location;
    }


    public void hydrateSampleObjectProxies(List<AttributeProxy> attributeProxies) {
        Field[] fields = SampleObject.class.getFields();
        Method[] methods = SampleObject.class.getMethods();
        attributeProxies.addAll(Arrays.stream(fields)
                .filter(field -> field.getAnnotation(Attribute.class) != null)
                .map(field -> new AttributeProxy(field))
                .collect(Collectors.toList()));
        attributeProxies.addAll(Arrays.stream(methods)
                .filter(method -> method.getAnnotation(Attribute.class) != null)
                .map(field -> new AttributeProxy(field))
                .collect(Collectors.toList()));
    }

    private DataSetItem getHydratedDataSetItemFromSampleObject(DataSetItem dataSetItem, SampleObject sampleObject) {
        dataSetItem.setString(sampleObject.woNumber, 0);
        dataSetItem.setInt(sampleObject.testInt, 1);
        if (sampleObject.testIntObject != null) dataSetItem.setInt(sampleObject.testIntObject, 2);
        dataSetItem.setDouble(sampleObject.testFloat, 3);
        if (sampleObject.testFloatObject != null) dataSetItem.setDouble(sampleObject.testFloatObject, 4);
        dataSetItem.setDouble(sampleObject.testDouble, 5);
        if (sampleObject.testDoubleObject != null) dataSetItem.setDouble(sampleObject.testDoubleObject, 6);
        dataSetItem.setDateTime(sampleObject.testJodaTimeDate, 7);
        dataSetItem.setDateTime(sampleObject.testJodaTimeDate, 8);
        dataSetItem.setDateTime(sampleObject.testJodaTimeDate, 9);
        dataSetItem.setListItem(getSampleListItem(), 10);
        DataSetItem singleRelationShip = dataSetItem.addNewDataSetItemForAttributeIndex(11);
        copyDataToSingleListItem(singleRelationShip);
        List<SampleRelationship> relationshipList = getSampleRelationshipList();
        for (SampleRelationship sampleRelationship : relationshipList) {
            DataSetItem tempDataSetItem = dataSetItem.addNewDataSetItemForAttributeIndex(12);
            sampleRelationship.copyToDataSetItem(tempDataSetItem);
        }
        dataSetItem.setColor(sampleObject.color, 17);
        dataSetItem.setLocation(sampleObject.sdkLocation, 18);
        dataSetItem.setLocation(sampleObject.sdkLocation, 19);
        return dataSetItem;
    }


    private List<SampleRelationship> getSampleRelationshipList() {
        int max = 10;
        int cnt = 0;
        List<SampleRelationship> tempList = new ArrayList<>();
        while (cnt <= max) {
            tempList.add(getSampleRelationship());
            cnt++;
        }

        return tempList;
    }


    private SampleRelationship getSampleRelationship() {
        SampleRelationship sampleRelationship = new SampleRelationship();
        sampleRelationship.woNumber = "1234";
        sampleRelationship.testInt = 1;
        sampleRelationship.testIntObject = 1;
        sampleRelationship.testFloat = 1.0f;
        sampleRelationship.testFloatObject = 1.0f;
        sampleRelationship.testDouble = 1.0;
        sampleRelationship.testDoubleObject = 1.0;
        sampleRelationship.testDate = new Date(100);
        sampleRelationship.testJodaTimeDate = new DateTime(100);
        sampleRelationship.testSqlDate = new java.sql.Date(100);
        sampleRelationship.sampleListItem = new SampleListItem();
        sampleRelationship.sampleListItem.woNumber = "1234";
        sampleRelationship.sampleListItem.testInt = 1;
        sampleRelationship.sampleListItem.testIntObject = 1;
        sampleRelationship.sampleListItem.testFloat = 1.0f;
        sampleRelationship.sampleListItem.testFloatObject = 1.0f;
        sampleRelationship.sampleListItem.testDouble = 1.0;
        sampleRelationship.sampleListItem.testDoubleObject = 1.0;
        sampleRelationship.sampleListItem.testDate = new Date(100);
        sampleRelationship.sampleListItem.testJodaTimeDate = new DateTime(100);
        sampleRelationship.sampleListItem.testSqlDate = new java.sql.Date(100);
        return sampleRelationship;
    }


    private void copyDataToSingleListItem(DataSetItem dataSetItem) {
        dataSetItem.setString("1234", 0);
        dataSetItem.setInt(1, 1);
        dataSetItem.setInt(1, 2);
        dataSetItem.setDouble(1.0, 3);
        dataSetItem.setDouble(1.0, 4);
        dataSetItem.setDouble(1.0, 5);
        dataSetItem.setDouble(1.0, 6);
        dataSetItem.setDateTime(new DateTime(100), 7);
        dataSetItem.setDateTime(new DateTime(100), 8);
        dataSetItem.setDateTime(new DateTime(100), 9);
        dataSetItem.setListItem(getSampleListItem(), 10);
    }


    private ListItem getSampleListItem() {
        ListItem listItem = new ListItem();
        listItem.setString("1234", 0);
        listItem.setInt(1, 1);
        listItem.setInt(1, 2);
        listItem.setDouble(1.0f, 3);
        listItem.setDouble(1.0f, 4);
        listItem.setDouble(1.0, 5);
        listItem.setDouble(1.0, 6);
        listItem.setDateTime(new DateTime(100), 7);
        listItem.setDateTime(new DateTime(100), 8);
        listItem.setDateTime(new DateTime(100), 9);
        return listItem;
    }

    private SampleListItem getNewSampleListObject() {
        SampleListItem object = new SampleListItem();
        object.woNumber = "1234";
        object.testDate = new Date();
        object.testDouble = 1.0;
        object.testDoubleObject = 1.5;
        object.testFloat = 2.0f;
        object.testFloatObject = 2.5f;
        object.testInt = 3;
        object.testIntObject = 4;
        object.testJodaTimeDate = new DateTime();
        object.testSqlDate = new java.sql.Date(100);
        return object;
    }


    @Test
    public void testConfigGeneration() {
        ServiceConfiguration sampleConf = SampleObject.getServiceConfiguration();
        SampleObject sampleObject = new SampleObject();
        ServiceConfiguration generatedConfig = ObjectConverter.generateConfiguration(sampleObject.getClass());
        Assert.assertTrue(generatedConfig.equals(sampleConf));
    }


    @Test
    public void testCopyFromDataSetItem() throws UnsupportedAttributeException, IllegalAccessException, UnableToWriteException {
        ServiceConfiguration sampleConf = ObjectConverter.generateConfiguration(SampleObject.class);
        SampleObject sampleObject = new SampleObject();
        DataSetItem dataSetItem = new DataSetItem(sampleConf.getAttributes());
        DataSetItem testDataSetItem = new DataSetItem(sampleConf.getAttributes());
        dataSetItem = getHydratedDataSetItemFromSampleObject(dataSetItem, getSampleObject());
        ObjectConverter.copyFromRecord(dataSetItem, sampleObject);
        ObjectConverter.copyToRecord(testDataSetItem, sampleObject);
        Assert.assertTrue(dataSetItem.equals(testDataSetItem));
    }

    @Test
    public void testSetPrimaryKeyAndValue() {
        ListItem item = new ListItem();
        SampleListItem object = getNewSampleListObject();
        ObjectConverter.copyToRecord(item, object);
        assert(item.id.equals(object.woNumber) && item.value.equals(object.woNumber));
    }

    @Test
    public void testInferName() {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("aName", "A Name");
        testMap.put("MyVar", "My Var");
        testMap.put("bool123", "Bool 123");
        testMap.put("123bool", "123 Bool");
        testMap.put("snake_case", "Snake Case");
        testMap.put("myURL", "My URL");
        testMap.put("setURL", "URL");
        testMap.put("getName", "Name");

        try {
            ObjectConverter converter = new ObjectConverter();
            Class[] argTypes = new Class[]{String.class};
            Method method = converter.getClass().getDeclaredMethod("inferName", argTypes);
            method.setAccessible(true);
            Object[] args = new Object[1];

            testMap.forEach((key, value) -> {
                args[0] = key;
                String output = "";
                try {
                    output = (String) method.invoke(converter, args);
                } catch (Exception e) {
                    System.out.println("Test failed.");
                }
                System.out.println(output);
                assert (output.equals(value));
            });
        } catch (Exception error) {
            System.out.println("Test failed with error: " + error.getLocalizedMessage());
        }
    }

    @Test
    public void testGetLocationValueFromObjectSDK() {
        SampleObject object = new SampleObject();
        List<AttributeProxy> attributeProxies = new ArrayList<>();
        hydrateSampleObjectProxies(attributeProxies);
        AttributeProxy argProxy = null;
        for (AttributeProxy proxy : attributeProxies) {
            if (proxy.getName().equalsIgnoreCase("customLocation")) argProxy = proxy;
        }
        ObjectConverter converter = new ObjectConverter();
        try {
            Method method = converter.getClass().getDeclaredMethod("getLocationValueFromObject", AttributeProxy.class, Object.class, boolean.class);
            method.setAccessible(true);
            Object[] args = new Object[3];
            args[0] = attributeProxies.get(1);
            args[1] = object;
            args[2] = false; // Use getter/setter
            Location testFillObj = (Location) method.invoke(converter, args);
            assert (testFillObj.getLatLngString().equals(object.sdkLocation.getLatLngString()));
            assert (testFillObj.getAccuracy() == object.sdkLocation.getAccuracy());
            assert (testFillObj.getBearing() == object.sdkLocation.getBearing());
            assert (testFillObj.getElevation() == object.sdkLocation.getElevation());
            assert (testFillObj.getSpeed() == object.sdkLocation.getSpeed());
        } catch (Exception error) {
            System.out.println("Test failed with error: " + error.getLocalizedMessage());
        }
    }

    @Test
    public void testGetLocationValueFromObjectCustom() {
        SampleObject object = new SampleObject();
        List<AttributeProxy> attributeProxies = new ArrayList<>();
        hydrateSampleObjectProxies(attributeProxies);
        AttributeProxy argProxy = null;
        for (AttributeProxy proxy : attributeProxies) {
            if (proxy.getName().equalsIgnoreCase("customLocation")) argProxy = proxy;
        }
        ObjectConverter converter = new ObjectConverter();
        try {
            Method method = converter.getClass().getDeclaredMethod("getLocationValueFromObject", AttributeProxy.class, Object.class, boolean.class);
            method.setAccessible(true);
            Object[] args = new Object[3];
            args[0] = argProxy;
            args[1] = object;
            args[2] = false; // Use getter/setter
            Location testFillObj = (Location) method.invoke(converter, args);
            assert (testFillObj.getLatitude() == object.customLocation.getLatitude());
            assert (testFillObj.getLongitude() == object.customLocation.getLongitude());
            assert (testFillObj.getAccuracy() == object.customLocation.getAccuracy());
            assert (testFillObj.getBearing() == object.customLocation.getBearing());
            assert (testFillObj.getElevation() == object.customLocation.getElevation());
            assert (testFillObj.getSpeed() == object.customLocation.getSpeed());
        } catch (Exception error) {
            System.out.println("Test failed with error: " + error.getLocalizedMessage());
        }
    }

    @Test
    public void testReadLocationData() {
        SampleObject object = new SampleObject();
        List<AttributeProxy> attributeProxies = new ArrayList<>();
        hydrateSampleObjectProxies(attributeProxies);
        AttributeProxy argProxy = null;
        for (AttributeProxy proxy : attributeProxies) {
            if (proxy.getName().equalsIgnoreCase("customLocation")) argProxy = proxy;
        }
        ObjectConverter converter = new ObjectConverter();
        try {
            Method method = converter.getClass().getDeclaredMethod("readLocationData", AttributeProxy.class, Object.class, Record.class, int.class, boolean.class, boolean.class, boolean.class);
            method.setAccessible(true);
            Object[] args = new Object[7];
            args[0] = argProxy;
            args[1] = object;
            args[2] = new DataSetItem(SampleObject.getServiceConfigurationAttributes());
            args[3] = argProxy.getAttributeAnnotation().index(); // index
            args[4] = false; // Primary key
            args[5] = false; // use getter/setter
            args[6] = true; // value
            method.invoke(converter, args);
            Location testFillObj = ((DataSetItem) args[2]).getLocation((Integer) args[3]);
            assert (testFillObj.getLatLngString().equals(object.sdkLocation.getLatLngString()));
            assert (testFillObj.getAccuracy() == object.sdkLocation.getAccuracy());
            assert (testFillObj.getBearing() == object.sdkLocation.getBearing());
            assert (testFillObj.getElevation() == object.sdkLocation.getElevation());
            assert (testFillObj.getSpeed() == object.sdkLocation.getSpeed());
        } catch (Exception error) {
            System.out.println("Test failed with error: " + error.getLocalizedMessage());
        }
    }

    @Test
    public void testWriteLocationDataSDK() {
        SampleObject object = new SampleObject();
        ObjectConverter converter = new ObjectConverter();
        List<AttributeProxy> attributeProxies = new ArrayList<>();
        hydrateSampleObjectProxies(attributeProxies);
        AttributeProxy argProxy = null;
        for (AttributeProxy proxy : attributeProxies) {
            if (proxy.getName().equalsIgnoreCase("customLocation")) argProxy = proxy;
        }
        DataSetItem dataSetItem = new DataSetItem(SampleObject.getServiceConfigurationAttributes());
        getHydratedDataSetItemFromSampleObject(dataSetItem, object);
        Location src = dataSetItem.getLocation(argProxy.getAttributeAnnotation().index());
        SampleObject dest = new SampleObject();
        dest.customLocation = null;
        try {
            Method method = ObjectConverter.class.getDeclaredMethod("writeLocationData", AttributeProxy.class, Object.class, Record.class, Integer.class, Class.class);
            method.setAccessible(true);
            Object[] args = new Object[5];
            args[0] = argProxy;
            args[1] = dest; // destination
            args[2] = dataSetItem;
            args[3] = argProxy.getAttributeAnnotation().index();
            args[4] = Class.class;
            method.invoke(converter, args);
            assert (dest.sdkLocation.getLatLngString().equals(src.getLatLngString()));
            assert (dest.sdkLocation.getAccuracy() == src.getAccuracy());
            assert (dest.sdkLocation.getSpeed() == src.getSpeed());
            assert (dest.sdkLocation.getBearing() == src.getBearing());
            assert (dest.sdkLocation.getElevation() == src.getElevation());
        } catch (Exception error) {
            System.out.println("Test failed with error: " + error.getLocalizedMessage());
        }
    }

    @Test
    public void testWriteCustomLocation() {
        SampleObject object = new SampleObject();
        ObjectConverter converter = new ObjectConverter();
        List<AttributeProxy> attributeProxies = new ArrayList<>();
        hydrateSampleObjectProxies(attributeProxies);
        AttributeProxy argProxy = null;
        for (AttributeProxy proxy : attributeProxies) {
            if (proxy.getName().equalsIgnoreCase("customLocation")) argProxy = proxy;
        }
        DataSetItem dataSetItem = new DataSetItem(object.getServiceConfigurationAttributes());
        getHydratedDataSetItemFromSampleObject(dataSetItem, object);
        Location src = dataSetItem.getLocation(argProxy.getAttributeAnnotation().index());
        SampleObject dest = new SampleObject();
        dest.customLocation = null;
        try {
            Method method = ObjectConverter.class.getDeclaredMethod("writeCustomLocationData", AttributeProxy.class, Object.class, Record.class, Integer.class, Class.class);
            method.setAccessible(true);
            Object[] args = new Object[5];
            args[0] = argProxy;
            args[1] = dest; // destination
            args[2] = dataSetItem;
            args[3] = argProxy.getAttributeAnnotation().index();
            args[4] = Class.class;
            method.invoke(converter, args);
            assert (dest.customLocation.getLatitude() == src.getLatitude());
            assert (dest.customLocation.getLongitude() == src.getLongitude());
            assert (dest.customLocation.getAccuracy() == src.getAccuracy());
            assert (dest.customLocation.getSpeed() == src.getSpeed());
            assert (dest.customLocation.getBearing() == src.getBearing());
            assert (dest.customLocation.getElevation() == src.getElevation());
        } catch (Exception error) {
            System.out.println("Test failed with error: " + error.getLocalizedMessage());
        }
    }

    @Test
    public void testReadColorData() {
        SampleObject object = getSampleObject();
        DataSetItem dataSetItem = new DataSetItem(object.getServiceConfigurationAttributes());
        List<AttributeProxy> proxies = new ArrayList<>();
        hydrateSampleObjectProxies(proxies);
        AttributeProxy argProxy = null;
        for (AttributeProxy proxy : proxies) {
            if (proxy.getName().equalsIgnoreCase("color")) argProxy = proxy;
        }
        try {
            Method method = ObjectConverter.class.getDeclaredMethod("readColorData", AttributeProxy.class, Object.class, Record.class, int.class, boolean.class);
            method.setAccessible(true);
            Object[] args = {argProxy, object, dataSetItem, argProxy.getAttributeAnnotation().index(), false};
            method.invoke(null, args);
            Color responseColor = dataSetItem.getColor(argProxy.getAttributeAnnotation().index());
            assert (responseColor.getA() == object.color.getA());
            assert (responseColor.getB() == object.color.getB());
            assert (responseColor.getG() == object.color.getG());
            assert (responseColor.getR() == object.color.getR());
        } catch (Exception error) {
            System.out.println("Test failed with error: " + error.getLocalizedMessage());
        }
    }

    @Test
    public void testWriteColor() {
        SampleObject object = getSampleObject();
        DataSetItem dataSetItem = new DataSetItem(object.getServiceConfigurationAttributes());
        List<AttributeProxy> proxies = new ArrayList<>();
        getHydratedDataSetItemFromSampleObject(dataSetItem, object);
        hydrateSampleObjectProxies(proxies);
        AttributeProxy argProxy = null;
        object.color = null;
        for (AttributeProxy proxy : proxies) {
            if (proxy.getName().equalsIgnoreCase("color")) argProxy = proxy;
        }
        try {
            Method method = ObjectConverter.class.getDeclaredMethod("writeColorData", AttributeProxy.class, Object.class, Record.class, Integer.class, Class.class);
            method.setAccessible(true);
            Object[] args = {argProxy, object, dataSetItem, argProxy.getAttributeAnnotation().index(), Class.class};
            method.invoke(null, args);
            Color responseColor = object.color;
            assert (responseColor.getA() == object.color.getA());
            assert (responseColor.getB() == object.color.getB());
            assert (responseColor.getG() == object.color.getG());
            assert (responseColor.getR() == object.color.getR());
        } catch (Exception error) {
            System.out.println("Test failed with error: " + error.getLocalizedMessage());
        }
    }

    @Test
    public void testGetColorValueFromObject() {
        SampleObject object = getSampleObject();
        DataSetItem dataSetItem = new DataSetItem(object.getServiceConfigurationAttributes());
        List<AttributeProxy> proxies = new ArrayList<>();
        getHydratedDataSetItemFromSampleObject(dataSetItem, object);
        hydrateSampleObjectProxies(proxies);
        AttributeProxy argProxy = null;
        for (AttributeProxy proxy : proxies) {
            if (proxy.getName().equalsIgnoreCase("color")) argProxy = proxy;
        }
        try {
            Method method = ObjectConverter.class.getDeclaredMethod("getColorValueFromObject", AttributeProxy.class, Object.class, boolean.class);
            method.setAccessible(true);
            Object[] args = {argProxy, object, false};
            Color responseColor = (Color) method.invoke(null, args);
            assert (responseColor.getA() == object.color.getA());
            assert (responseColor.getB() == object.color.getB());
            assert (responseColor.getG() == object.color.getG());
            assert (responseColor.getR() == object.color.getR());
        } catch (Exception error) {
            System.out.println("Test failed with error: " + error.getLocalizedMessage());
        }
    }

    @Test
    public void generateAttachmentConfiguration() {
        ServiceConfiguration sampleConf = SampleAttachment.getServiceConfiguration();
        ServiceConfiguration generateConfig = ObjectConverter.generateConfiguration(SampleAttachmentWrapper.class);
        Assert.assertTrue(sampleConf.equals(generateConfig));
    }


    @Test
    public void testIfConfigurationsAreEqual() {
        ServiceConfiguration sampleConf = SampleObject.getServiceConfiguration();
        ServiceConfiguration generatedConfg = ObjectConverter.generateConfiguration(SampleObject.class);
        Assert.assertTrue(sampleConf.equals(generatedConfg));
    }


    @Test
    public void toDataSetItem() {
        SampleObject sampleObject = getSampleObject();
        DataSetItem dataSetItem = new DataSetItem(ObjectConverter.generateConfigurationAttributes(SampleObject.class));
        ObjectConverter.copyToRecord(dataSetItem, sampleObject);
    }


    @Test
    public void fromDataSetItem() {
        SampleObject sampleObject = getSampleObject();
        DataSetItem dataSetItem = new DataSetItem(ObjectConverter.generateConfigurationAttributes(SampleObject.class));
        ObjectConverter.copyToRecord(dataSetItem, sampleObject);
        SampleObject testSampleObject = new SampleObject();
        ObjectConverter.copyFromRecord(dataSetItem, testSampleObject);
    }


    @Test
    public void compareConfiguration() {
        String originalConfiguration = "{\n" +
                "  \"success\": true,\n" +
                "  \"message\": null,\n" +
                "  \"showMessageAsAlert\": false,\n" +
                "  \"async\": false,\n" +
                "  \"listName\": \"Issue Status\",\n" +
                "  \"includesLocation\": false,\n" +
                "  \"authenticationRequired\": false,\n" +
                "  \"userIDIndex\": -1,\n" +
                "  \"canCache\": true,\n" +
                "  \"canSearch\": true,\n" +
                "  \"attributes\": [\n" +
                "    {\n" +
                "      \"attributeIndex\": 0,\n" +
                "      \"label\": \"Status\",\n" +
                "      \"attributeType\": \"Text\",\n" +
                "    }\n" +
                "  ],\n" +
                "  \"serviceFilterParameters\": [\n" +
                "    \n" +
                "  ]\n" +
                "}";
        // TODO: talk to Matt about writing a test for this
    }


    @Test
    public void testExcludeFromListConfiguration() {
        ServiceConfiguration serviceConfiguration = ObjectConverter.generateConfiguration(ExcludeFromList.class);
        Assert.assertTrue((serviceConfiguration.getAttributes().size() - 1) == serviceConfiguration.getAttributeWithIndex(3).getRelatedListServiceConfiguration().getAttributes().size());
    }


    @Test
    public void testExcludeFromListCopyToRecord() {
        ExcludeFromList excludeFromList = ExcludeFromList.getExcludeFromListObject();
        DataSetItem dataSetItem = new DataSetItem(ObjectConverter.generateConfigurationAttributes(ExcludeFromList.class));
        ObjectConverter.copyToRecord(dataSetItem, excludeFromList);
        DataSetItem testDataSetItem = ExcludeFromList.getTestDataSetItem();
        Assert.assertTrue(dataSetItem.equals(testDataSetItem));
    }


    @Test
    public void testExcludeFromListCopyFromRecord() {
        ExcludeFromList excludeFromList = new ExcludeFromList();
        DataSetItem testDataSetItem = ExcludeFromList.getTestDataSetItem();
        ObjectConverter.copyFromRecord(testDataSetItem, excludeFromList);
        DataSetItem dataSetItem = new DataSetItem(ObjectConverter.generateConfigurationAttributes(ExcludeFromList.class));
        ObjectConverter.copyToRecord(dataSetItem, excludeFromList);
        Assert.assertTrue(dataSetItem.equals(testDataSetItem));
    }









}