import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import sdk.annotations.Attribute;
import sdk.annotations.CustomLocation;
import sdk.annotations.PrimaryKey;
import sdk.converter.AttributeProxy;
import sdk.converter.ObjectConverter;
import sdk.data.DataSetItem;
import sdk.data.Record;
import sdk.data.ServiceConfiguration;
import sdk.exceptions.UnableToWriteException;
import sdk.exceptions.UnsupportedAttributeException;
import sdk.list.ListItem;
import sdk.models.Location;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
        return sampleObject;
    }

    private DataSetItem getHydratedDataSetItemFromSampleObject(DataSetItem dataSetItem, SampleObject sampleObject) {
        dataSetItem.setString(sampleObject.woNumber, 0);
        dataSetItem.setInt(sampleObject.testInt, 1);
        dataSetItem.setInt(sampleObject.testIntObject, 2);
        dataSetItem.setDouble(sampleObject.testFloat, 3);
        dataSetItem.setDouble(sampleObject.testFloatObject, 4);
        dataSetItem.setDouble(sampleObject.testDouble, 5);
        dataSetItem.setDouble(sampleObject.testDoubleObject, 6);
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
    public void testInferName() {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("aName", "A Name");
        testMap.put("MyVar", "My Var");
        testMap.put("bool123", "Bool 123");
        testMap.put("123bool", "123 Bool");
        testMap.put("snake_case", "Snake Case");
        testMap.put("myURL", "My URL");

        try {
            ObjectConverter converter = new ObjectConverter();
            Class[] argTypes = new Class[]{String.class};
            Method method = converter.getClass().getDeclaredMethod("inferName", argTypes);
            method.setAccessible(true);
            Object[] args = new Object[1];

            testMap.forEach((key, value) -> {
                args[0] = key;
                String output = "";
                try {output = (String) method.invoke(converter, args);}
                catch(Exception e) {System.out.println("Test failed.");}
                System.out.println(output);
                assert (output.equals(value));
            });
        } catch(Exception error) {
            System.out.println("Test failed with error: " + error.getLocalizedMessage());
        }
    }

    @Test
    public void testGetLocationValueFromObjectSDK() {
        TestClass object = new TestClass();
        List<AttributeProxy> attributeProxies = new ArrayList<>();
        hydrateObjAndProxies(object, attributeProxies);
        ObjectConverter converter = new ObjectConverter();
        try {
            Method method = converter.getClass().getDeclaredMethod("getLocationValueFromObject", AttributeProxy.class, Object.class, boolean.class);
            method.setAccessible(true);
            Object[] args = new Object[3];
            args[0] = attributeProxies.get(1);
            args[1] = object;
            args[2] = false; // Use getter/setter
            Location testFillObj = (Location) method.invoke(converter, args);
            System.out.println(String.format("Initial: %s, Final: %s", object.location.getLatLngString(), testFillObj.getLatLngString()));
            assert(testFillObj.getLatLngString().equals(object.location.getLatLngString()));
            assert(testFillObj.getAccuracy() == object.location.getAccuracy());
            assert(testFillObj.getBearing() == object.location.getBearing());
            assert(testFillObj.getElevation() == object.location.getElevation());
            assert(testFillObj.getSpeed() == object.location.getSpeed());
        } catch(Exception error) {
            System.out.println("Test failed with error: " + error.getLocalizedMessage());
        }
    }

    @Test
    public void testGetLocationValueFromObjectCustom() {
        TestClass object = new TestClass();
        List<AttributeProxy> attributeProxies = new ArrayList<>();
        hydrateObjAndProxies(object, attributeProxies);
        object.customLocation = new TestLocation();
        object.customLocation.fromLocation(object.location, object.customLocation);
        ObjectConverter converter = new ObjectConverter();
        try {
            Method method = converter.getClass().getDeclaredMethod("getLocationValueFromObject", AttributeProxy.class, Object.class, boolean.class);
            method.setAccessible(true);
            Object[] args = new Object[3];
            args[0] = attributeProxies.get(2);
            args[1] = object;
            args[2] = false; // Use getter/setter
            Location testFillObj = (Location) method.invoke(converter, args);
            assert(testFillObj.getLatitude() == object.customLocation.getLatitude());
            assert(testFillObj.getLongitude() == object.customLocation.getLongitude());
            assert(testFillObj.getAccuracy() == object.customLocation.getAccuracy());
            assert(testFillObj.getBearing() == object.customLocation.getBearing());
            assert(testFillObj.getElevation() == object.customLocation.getElevation());
            assert(testFillObj.getSpeed() == object.customLocation.getSpeed());
        } catch(Exception error) {
            System.out.println("Test failed with error: " + error.getLocalizedMessage());
        }
    }

    @Test
    public void testReadLocationData() {
        TestClass object = new TestClass();
        List<AttributeProxy> attributeProxies = new ArrayList<>();
        hydrateObjAndProxies(object, attributeProxies);
        ObjectConverter converter = new ObjectConverter();
        try {
            Method method = converter.getClass().getDeclaredMethod("readLocationData", AttributeProxy.class, Object.class, Record.class, int.class, boolean.class, boolean.class, boolean.class);
            method.setAccessible(true);
            Object[] args = new Object[7];
            args[0] = attributeProxies.get(1);
            args[1] = object;
            args[2] = new DataSetItem(ObjectConverter.generateConfigurationAttributes(TestClass.class));
            args[3] = 1; // index
            args[4] = false; // Primary key
            args[5] = false; // use getter/setter
            args[6] = true; // value
            method.invoke(converter, args);
            Location testFillObj = ((DataSetItem) args[2]).getLocation((Integer) args[3]);
            System.out.println(String.format("Initial: %s, Final: %s", object.location.getLatLngString(), testFillObj.getLatLngString()));
            assert(testFillObj.getLatLngString().equals(object.location.getLatLngString()));
            assert(testFillObj.getAccuracy() == object.location.getAccuracy());
            assert(testFillObj.getBearing() == object.location.getBearing());
            assert(testFillObj.getElevation() == object.location.getElevation());
            assert(testFillObj.getSpeed() == object.location.getSpeed());
        } catch(Exception error) {
            System.out.println("Test failed with error: " + error.getLocalizedMessage());
        }
    }

    @Test
    public void testWriteLocationDataSDK() {
        TestClass object = new TestClass();
        ObjectConverter converter = new ObjectConverter();
        List<AttributeProxy> attributeProxies = new ArrayList<>();
        hydrateObjAndProxies(object, attributeProxies);
        DataSetItem dataSetItem = new DataSetItem(ObjectConverter.generateConfigurationAttributes(TestClass.class));
        dataSetItem.setLocation(object.location, 1);
        Location src = dataSetItem.getLocation(1);
        TestClass dest = new TestClass();
        try {
            Method method = ObjectConverter.class.getDeclaredMethod("writeLocationData", AttributeProxy.class, Object.class, Record.class, Integer.class, Class.class);
            method.setAccessible(true);
            Object[] args = new Object[5];
            args[0] = attributeProxies.get(1);
            args[1] = dest; // destination
            args[2] = dataSetItem;
            args[3] = 1;
            args[4] = Class.class;
            method.invoke(converter, args);
            assert(dest.location.getLatLngString().equals(src.getLatLngString()));
            assert(dest.location.getAccuracy() == src.getAccuracy());
            assert(dest.location.getSpeed() == src.getSpeed());
            assert(dest.location.getBearing() == src.getBearing());
            assert(dest.location.getElevation() == src.getElevation());
        } catch(Exception error) {
            System.out.println("Test failed with error: " + error.getLocalizedMessage());
        }
    }

    @Test
    public void testWriteCustomLocation() {
        TestClass object = new TestClass();
        ObjectConverter converter = new ObjectConverter();
        List<AttributeProxy> attributeProxies = new ArrayList<>();
        hydrateObjAndProxies(object, attributeProxies);
        DataSetItem dataSetItem = new DataSetItem(ObjectConverter.generateConfigurationAttributes(TestClass.class));
        dataSetItem.setLocation(object.location, 2);
        Location src = dataSetItem.getLocation(2);
        TestClass dest = new TestClass();
        try {
            Method method = ObjectConverter.class.getDeclaredMethod("writeCustomLocationData", AttributeProxy.class, Object.class, Record.class, Integer.class, Class.class);
            method.setAccessible(true);
            Object[] args = new Object[5];
            args[0] = attributeProxies.get(2);
            args[1] = dest; // destination
            args[2] = dataSetItem;
            args[3] = 2;
            args[4] = Class.class;
            method.invoke(converter, args);
            assert(dest.customLocation.getLatitude() == src.getLatitude());
            assert(dest.customLocation.getLongitude() == src.getLongitude());
            assert(dest.customLocation.getAccuracy() == src.getAccuracy());
            assert(dest.customLocation.getSpeed() == src.getSpeed());
            assert(dest.customLocation.getBearing() == src.getBearing());
            assert(dest.customLocation.getElevation() == src.getElevation());
        } catch(Exception error) {
            System.out.println("Test failed with error: " + error.getLocalizedMessage());
        }
    }

    private void hydrateObjAndProxies(TestClass object, List<AttributeProxy> attributeProxies) {
        object.location = new Location(1.0, 2.0);
        object.location.setTimestamp(new DateTime());
        object.location.setSpeed(3.0);
        object.location.setElevation(4.0);
        object.location.setBearing(5.0);
        object.location.setAccuracy(6.0);
        Field[] fields = TestClass.class.getFields();
        Method[] methods = TestClass.class.getMethods();
        attributeProxies.addAll(Arrays.stream(fields)
                .filter(field -> field.getAnnotation(Attribute.class) != null)
                .map(field -> new AttributeProxy(field))
                .collect(Collectors.toList()));
        attributeProxies.addAll(Arrays.stream(methods)
                .filter(method -> method.getAnnotation(Attribute.class) != null)
                .map(field -> new AttributeProxy(field))
                .collect(Collectors.toList()));
    }

    public class TestClass {
        @PrimaryKey
        @Attribute(index = 0)
        public int key;

        @Attribute(index = 1)
        public Location location;

        @Attribute(index = 2)
        public TestLocation customLocation;
    }

    public static class TestLocation extends CustomLocation {
        public TestLocation() {}

        @Override
        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        @Override
        public double getLatitude() {
            return this.latitude;
        }

        @Override
        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        @Override
        public double getLongitude() {
            return this.longitude;
        }
    }
}