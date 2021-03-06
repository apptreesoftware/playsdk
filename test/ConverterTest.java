import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import sdk.annotations.Attribute;
import sdk.annotations.PrimaryKey;
import sdk.converter.AttributeProxy;
import sdk.converter.ConfigurationManager;
import sdk.converter.ObjectConverter;
import sdk.converter.TypeManager;
import sdk.data.DataSetItem;
import sdk.data.Record;
import sdk.data.ServiceConfiguration;
import sdk.exceptions.UnableToWriteException;
import sdk.exceptions.UnsupportedAttributeException;
import sdk.list.ListItem;
import sdk.models.Color;
import sdk.models.Image;
import sdk.models.Location;

import javax.validation.constraints.AssertTrue;
import java.lang.reflect.Method;
import java.util.*;

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

    @SuppressWarnings("unchecked")
    public <T extends List<?>> T hydrateSampleObjectProxies() {
        try {
            Method method = TypeManager.class
                .getDeclaredMethod("getMethodAndFieldAnnotationsForClass", Class.class);
            method.setAccessible(true);
            Object[] args = {SampleObject.class};
            ObjectConverter converter = new ObjectConverter();
            return (T) method.invoke(converter, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private DataSetItem getHydratedDataSetItemFromSampleObject(DataSetItem dataSetItem,
                                                               SampleObject sampleObject) {
        dataSetItem.setPrimaryKey(sampleObject.woNumber);
        dataSetItem.setString(sampleObject.woNumber, 0);
        dataSetItem.setInt(sampleObject.testInt, 1);
        if (sampleObject.testIntObject != null) dataSetItem.setInt(sampleObject.testIntObject, 2);
        dataSetItem.setDouble(sampleObject.testFloat, 3);
        if (sampleObject.testFloatObject != null)
            dataSetItem.setDouble(sampleObject.testFloatObject, 4);
        dataSetItem.setDouble(sampleObject.testDouble, 5);
        if (sampleObject.testDoubleObject != null)
            dataSetItem.setDouble(sampleObject.testDoubleObject, 6);
        dataSetItem.setDateTime(sampleObject.testJodaTimeDate, 7);
        dataSetItem.setDateTime(sampleObject.testJodaTimeDate, 8);
        dataSetItem.setDateTime(sampleObject.testJodaTimeDate, 9);
        dataSetItem.setListItem(getSampleListItem(), 10);
        DataSetItem singleRelationShip = dataSetItem.addNewDataSetItemForAttributeIndex(11);
        copyDataToSingleRelationship(singleRelationShip);
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


    private void copyDataToSingleRelationship(DataSetItem dataSetItem) {
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
        ListItem listItem = new ListItem("1234");
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
        Image image = new Image();
        image.imageURL = "myurl.com/attachmentImage.jpg";
        listItem.setImage(image, 10);
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
        object.image = new Image();
        object.image.imageURL = "myurl.com/attachmentImage.jpg";
        return object;
    }

    private SampleLazyLoadObj getNewLazyObj() {
        SampleLazyLoadObj obj = new SampleLazyLoadObj();
        List<SampleLazyLoadObj.LazilyLoadedObj> objList = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            SampleLazyLoadObj.LazilyLoadedObj childObj = new SampleLazyLoadObj.LazilyLoadedObj();
            childObj.pk = i;
            childObj.name = "test" + i;
            objList.add(childObj);
        }
        obj.objList = objList;
        return obj;
    }


    @Test
    public void testLazyLoad() {
        SampleLazyLoadObj obj = getNewLazyObj();
        SampleLazyLoadObj finalObj = new SampleLazyLoadObj();
        DataSetItem item = new DataSetItem(
            ObjectConverter.generateConfigurationAttributes(SampleLazyLoadObj.class));
        ObjectConverter.copyToRecord(item, obj);
        ObjectConverter.copyFromRecord(item, finalObj, false);
        assert (finalObj.objList == null);

        List<Integer> loadProps = Arrays.asList(0, 1);
        ObjectConverter.copyToRecord(item, obj, loadProps);
        ObjectConverter.copyFromRecord(item, finalObj, false);
        assert (finalObj.objList != null);
    }

    @Test
    public void testObjectWithPrimitiveTypes() {
        SamplePrimitivesObject obj = new SamplePrimitivesObject();
        DataSetItem item = new DataSetItem(
            ObjectConverter.generateConfigurationAttributes(SamplePrimitivesObject.class));
        try {
            ObjectConverter.copyToRecord(item, obj);
            DataSetItem newItem = new DataSetItem(
                ObjectConverter.generateConfigurationAttributes(SamplePrimitivesObject.class));
            ObjectConverter.copyFromRecord(newItem, obj, false);
            assert (obj.equivalent(item, obj));
            assert (obj.equivalent(newItem, new SamplePrimitivesObject()));
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


    @Test
    public void testSetImageToRecord() {
        SampleListItem object = getNewSampleListObject();
        Record record = new ListItem();
        ObjectConverter.copyToRecord(record, object);
        assert (object.image.imageURL.equals(record.getImage(10).imageURL));
    }


    public static class ObjectWithMethodPk {
        @Attribute(index = 0)
        public String name = "object with method primary key";
        private String pk = "primary key";

        @PrimaryKey
        public String getPk() {
            return pk;
        }

        public void setPk(String pk) {
            this.pk = pk;
        }
    }

    @Test
    public void testConvertObjWithMethodPrimaryKey() {
        ObjectWithMethodPk obj = new ObjectWithMethodPk();
        DataSetItem item = new DataSetItem(
            ObjectConverter.generateConfigurationAttributes(ObjectWithMethodPk.class));
        ObjectConverter.copyToRecord(item, obj);
        assert (item.getPrimaryKey().equals(obj.getPk()));
        item.setPrimaryKey("different key");
        ObjectConverter.copyFromRecord(item, obj, false);
        assert (obj.getPk().equals("different key"));
    }


    @Test
    public void testSetImageToObject() {
        SampleListItem object = new SampleListItem();
        Record record =
            new ListItem(ObjectConverter.generateListConfiguration(SampleListItem.class, "config"));
        Image image = new Image();
        image.imageURL = "testUrl.com";
        record.setImage(image, 10);
        ObjectConverter.copyFromRecord(record, object, false);
        assert (record.getImage(10).imageURL.equals(object.image.imageURL));
    }


    @Test
    public void testGenerateConfigAcrossHierarchy() {
        assert (
            ObjectConverter.generateConfigurationAttributes(SampleInheritanceTree.class).size() ==
            1);
        assert (
            ObjectConverter.generateConfigurationAttributes(SampleInheritanceTree.ChildObject.class)
                           .size() == 2);
        assert (ObjectConverter
                    .generateConfigurationAttributes(SampleInheritanceTree.GrandChildObject.class)
                    .size() == 3);
    }


    @Test
    public void testSetAttributesUpHierarchy() {
        SampleInheritanceTree.ChildObject.GrandChildObject obj =
            new SampleInheritanceTree.ChildObject.GrandChildObject();
        DataSetItem attrs = new DataSetItem(ObjectConverter.generateConfigurationAttributes(
            SampleInheritanceTree.GrandChildObject.class));
        attrs.setStringForAttributeIndex("first", 0);
        attrs.setStringForAttributeIndex("second", 1);
        attrs.setStringForAttributeIndex("third", 2);
        ObjectConverter.copyFromRecord(attrs, obj, false);
        assert (attrs.getStringAttributeAtIndex(0).equals(obj.first));
        assert (attrs.getStringAttributeAtIndex(1).equals(obj.second));
        assert (attrs.getStringAttributeAtIndex(2).equals(obj.third));
    }


    @Test
    public void testPrimaryKeyOnParent() {
        SampleInheritanceTree.ChildObject.GrandChildObject obj =
            new SampleInheritanceTree.ChildObject.GrandChildObject();
        obj.first = "first";
        obj.second = "second";
        obj.third = "third";
        DataSetItem attrs = new DataSetItem(ObjectConverter.generateConfigurationAttributes(
            SampleInheritanceTree.GrandChildObject.class));
        ObjectConverter.copyToRecord(attrs, obj);
        assert (attrs.getPrimaryKey().equals(obj.first));
    }


    @Test
    public void testRedefinePrimaryKey() {
        try {
            ObjectConverter.generateConfigurationAttributes(
                SampleInheritanceTree.GrandChildRedefinesPK.class);
            assert (false);
        } catch (RuntimeException e) {
            assert (e.getMessage().contains("redefines a primary key"));
        }
    }


    @Test
    public void testDuplicateIndex() {
        try {
            ObjectConverter.generateConfigurationAttributes(
                SampleInheritanceTree.GrandChildDuplicateIndex.class);
            assert (false);
        } catch (RuntimeException e) {
            assert (e.getMessage().contains("shares an index"));
        }
    }


    @Test
    public void testConfigGeneration() {
        ServiceConfiguration sampleConf = SampleObject.getServiceConfiguration();
        SampleObject sampleObject = new SampleObject();
        ServiceConfiguration generatedConfig =
            ObjectConverter.generateConfiguration(sampleObject.getClass());
        Assert.assertTrue(generatedConfig.equals(sampleConf));
    }


    @Test
    public void testCopyFromDataSetItem() throws UnsupportedAttributeException,
                                                 IllegalAccessException, UnableToWriteException {
        ServiceConfiguration sampleConf = ObjectConverter.generateConfiguration(SampleObject.class);
        SampleObject sampleObject = new SampleObject();
        DataSetItem dataSetItem = new DataSetItem(sampleConf.getAttributes());
        DataSetItem testDataSetItem = new DataSetItem(sampleConf.getAttributes());
        dataSetItem = getHydratedDataSetItemFromSampleObject(dataSetItem, getSampleObject());
        ObjectConverter.copyFromRecord(dataSetItem, sampleObject, false);
        ObjectConverter.copyToRecord(testDataSetItem, sampleObject);
        Assert.assertTrue(dataSetItem.equals(testDataSetItem));
    }

    @Test
    public void testSetPrimaryKeyAndValue() {
        ListItem item = new ListItem();
        SampleListItem object = getNewSampleListObject();
        ObjectConverter.copyToRecord(item, object);
        assert (item.id.equals(object.woNumber) && item.value.equals(object.woNumber));
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
            Method method = ConfigurationManager.class.getDeclaredMethod("inferName", String.class);
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
            error.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void testGetLocationValueFromObjectSDK() {
        SampleObject object = getSampleObject();
        List<AttributeProxy> attributeProxies = hydrateSampleObjectProxies();
        AttributeProxy argProxy = null;
        for (AttributeProxy proxy : attributeProxies) {
            if (proxy.getName().equalsIgnoreCase("sdklocation")) argProxy = proxy;
        }
        ObjectConverter converter = new ObjectConverter();
        try {
            Method method = ObjectConverter.class
                .getDeclaredMethod("getLocationValueFromObject", AttributeProxy.class, Object.class,
                                   boolean.class);
            method.setAccessible(true);
            Object[] args = new Object[3];
            args[0] = argProxy;
            args[1] = object;
            args[2] = false; // Use getter/setter
            Location testFillObj = (Location) method.invoke(converter, args);
            assert (testFillObj.getLatLngString().equals(object.sdkLocation.getLatLngString()));
            assert (testFillObj.getAccuracy() == object.sdkLocation.getAccuracy());
            assert (testFillObj.getBearing() == object.sdkLocation.getBearing());
            assert (testFillObj.getElevation() == object.sdkLocation.getElevation());
            assert (testFillObj.getSpeed() == object.sdkLocation.getSpeed());
        } catch (Exception error) {
            error.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void testGetLocationValueFromObjectCustom() {
        SampleObject object = getSampleObject();
        List<AttributeProxy> attributeProxies = hydrateSampleObjectProxies();
        AttributeProxy argProxy = null;
        for (AttributeProxy proxy : attributeProxies) {
            if (proxy.getName().equalsIgnoreCase("customLocation")) argProxy = proxy;
        }
        ObjectConverter converter = new ObjectConverter();
        try {
            Method method = converter.getClass().getDeclaredMethod("getLocationValueFromObject",
                                                                   AttributeProxy.class,
                                                                   Object.class, boolean.class);
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
            error.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void testReadLocationData() {
        SampleObject object = getSampleObject();
        List<AttributeProxy> attributeProxies = hydrateSampleObjectProxies();
        AttributeProxy argProxy = null;
        for (AttributeProxy proxy : attributeProxies) {
            if (proxy.getName().equalsIgnoreCase("sdklocation")) argProxy = proxy;
        }
        ObjectConverter converter = new ObjectConverter();
        try {
            Method method = converter.getClass()
                                     .getDeclaredMethod("readLocationData", AttributeProxy.class,
                                                        Object.class, Record.class, int.class,
                                                        boolean.class, boolean.class,
                                                        boolean.class);
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
            error.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void testWriteLocationDataSDK() {
        SampleObject object = getSampleObject();
        ObjectConverter converter = new ObjectConverter();
        List<AttributeProxy> attributeProxies = hydrateSampleObjectProxies();
        AttributeProxy argProxy = null;
        for (AttributeProxy proxy : attributeProxies) {
            if (proxy.getName().equalsIgnoreCase("sdklocation")) argProxy = proxy;
        }
        DataSetItem dataSetItem = new DataSetItem(SampleObject.getServiceConfigurationAttributes());
        ObjectConverter.copyToRecord(dataSetItem, object);
        Location src = dataSetItem.getLocation(argProxy.getAttributeAnnotation().index());
        SampleObject dest = new SampleObject();
        dest.customLocation = null;
        try {
            Method method = ObjectConverter.class
                .getDeclaredMethod("writeLocationData", AttributeProxy.class, Object.class,
                                   Record.class, Integer.class);
            method.setAccessible(true);
            Object[] args = new Object[4];
            args[0] = argProxy;
            args[1] = dest; // destination
            args[2] = dataSetItem;
            args[3] = argProxy.getAttributeAnnotation().index();
            method.invoke(converter, args);
            assert (dest.sdkLocation.getLatLngString().equals(src.getLatLngString()));
            assert (dest.sdkLocation.getAccuracy() == src.getAccuracy());
            assert (dest.sdkLocation.getSpeed() == src.getSpeed());
            assert (dest.sdkLocation.getBearing() == src.getBearing());
            assert (dest.sdkLocation.getElevation() == src.getElevation());
        } catch (Exception error) {
            error.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void testWriteCustomLocation() {
        SampleObject object = getSampleObject();
        ObjectConverter converter = new ObjectConverter();
        List<AttributeProxy> attributeProxies = hydrateSampleObjectProxies();
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
            Method method = ObjectConverter.class
                .getDeclaredMethod("writeCustomLocationData", AttributeProxy.class, Object.class,
                                   Record.class, Integer.class);
            method.setAccessible(true);
            Object[] args = new Object[4];
            args[0] = argProxy;
            args[1] = dest; // destination
            args[2] = dataSetItem;
            args[3] = argProxy.getAttributeAnnotation().index();
            method.invoke(converter, args);
            assert (dest.customLocation.getLatitude() == src.getLatitude());
            assert (dest.customLocation.getLongitude() == src.getLongitude());
            assert (dest.customLocation.getAccuracy() == src.getAccuracy());
            assert (dest.customLocation.getSpeed() == src.getSpeed());
            assert (dest.customLocation.getBearing() == src.getBearing());
            assert (dest.customLocation.getElevation() == src.getElevation());
        } catch (Exception error) {
            error.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void testReadColorData() {
        SampleObject object = getSampleObject();
        DataSetItem dataSetItem = new DataSetItem(object.getServiceConfigurationAttributes());
        List<AttributeProxy> proxies = hydrateSampleObjectProxies();
        AttributeProxy argProxy = null;
        for (AttributeProxy proxy : proxies) {
            if (proxy.getName().equalsIgnoreCase("color")) argProxy = proxy;
        }
        try {
            Method method = ObjectConverter.class
                .getDeclaredMethod("readColorData", AttributeProxy.class, Object.class,
                                   Record.class, int.class, boolean.class);
            method.setAccessible(true);
            Object[] args =
                {argProxy, object, dataSetItem, argProxy.getAttributeAnnotation().index(), false};
            method.invoke(null, args);
            Color responseColor = dataSetItem.getColor(argProxy.getAttributeAnnotation().index());
            assert (responseColor.getA() == object.color.getA());
            assert (responseColor.getB() == object.color.getB());
            assert (responseColor.getG() == object.color.getG());
            assert (responseColor.getR() == object.color.getR());
        } catch (Exception error) {
            error.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void testWriteColor() {
        SampleObject object = getSampleObject();
        DataSetItem dataSetItem = new DataSetItem(object.getServiceConfigurationAttributes());
        List<AttributeProxy> proxies = hydrateSampleObjectProxies();
        getHydratedDataSetItemFromSampleObject(dataSetItem, object);
        AttributeProxy argProxy = null;
        object.color = null;
        for (AttributeProxy proxy : proxies) {
            if (proxy.getName().equalsIgnoreCase("color")) argProxy = proxy;
        }
        try {
            Method method = ObjectConverter.class
                .getDeclaredMethod("writeColorData", AttributeProxy.class, Object.class,
                                   Record.class, Integer.class);
            method.setAccessible(true);
            Object[] args =
                {argProxy, object, dataSetItem, argProxy.getAttributeAnnotation().index()};
            method.invoke(null, args);
            Color responseColor = object.color;
            assert (responseColor.getA() == object.color.getA());
            assert (responseColor.getB() == object.color.getB());
            assert (responseColor.getG() == object.color.getG());
            assert (responseColor.getR() == object.color.getR());
        } catch (Exception error) {
            error.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void testGetColorValueFromObject() {
        SampleObject object = getSampleObject();
        DataSetItem dataSetItem = new DataSetItem(object.getServiceConfigurationAttributes());
        List<AttributeProxy> proxies = hydrateSampleObjectProxies();
        getHydratedDataSetItemFromSampleObject(dataSetItem, object);
        AttributeProxy argProxy = null;
        for (AttributeProxy proxy : proxies) {
            if (proxy.getName().equalsIgnoreCase("color")) argProxy = proxy;
        }
        try {
            Method method = ObjectConverter.class
                .getDeclaredMethod("getColorValueFromObject", AttributeProxy.class, Object.class,
                                   boolean.class);
            method.setAccessible(true);
            Object[] args = {argProxy, object, false};
            Color responseColor = (Color) method.invoke(null, args);
            assert (responseColor.getA() == object.color.getA());
            assert (responseColor.getB() == object.color.getB());
            assert (responseColor.getG() == object.color.getG());
            assert (responseColor.getR() == object.color.getR());
        } catch (Exception error) {
            error.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void generateAttachmentConfiguration() {
        ServiceConfiguration sampleConf = SampleAttachment.getServiceConfiguration();
        ServiceConfiguration generateConfig =
            ObjectConverter.generateConfiguration(SampleAttachmentWrapper.class);
        Assert.assertTrue(sampleConf.equals(generateConfig));
    }


    @Test
    public void testIfConfigurationsAreEqual() {
        ServiceConfiguration sampleConf = SampleObject.getServiceConfiguration();
        ServiceConfiguration generatedConfg =
            ObjectConverter.generateConfiguration(SampleObject.class);
        Assert.assertTrue(sampleConf.equals(generatedConfg));
    }


    @Test
    public void toDataSetItem() {
        SampleObject sampleObject = getSampleObject();
        DataSetItem dataSetItem =
            new DataSetItem(ObjectConverter.generateConfigurationAttributes(SampleObject.class));
        ObjectConverter.copyToRecord(dataSetItem, sampleObject);
    }


    @Test
    public void fromDataSetItem() {
        SampleObject sampleObject = getSampleObject();
        DataSetItem dataSetItem =
            new DataSetItem(ObjectConverter.generateConfigurationAttributes(SampleObject.class));
        ObjectConverter.copyToRecord(dataSetItem, sampleObject);
        SampleObject testSampleObject = new SampleObject();
        ObjectConverter.copyFromRecord(dataSetItem, testSampleObject, false);
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
        ServiceConfiguration serviceConfiguration =
            ObjectConverter.generateConfiguration(ExcludeFromList.class);
        Assert.assertTrue((serviceConfiguration.getAttributes().size() - 1) ==
                          serviceConfiguration.getAttributeWithIndex(3)
                                              .getRelatedListServiceConfiguration().getAttributes()
                                              .size());
    }


    @Test
    public void testExcludeFromListCopyToRecord() {
        ExcludeFromList excludeFromList = ExcludeFromList.getExcludeFromListObject();
        DataSetItem dataSetItem =
            new DataSetItem(ObjectConverter.generateConfigurationAttributes(ExcludeFromList.class));
        ObjectConverter.copyToRecord(dataSetItem, excludeFromList);
        DataSetItem testDataSetItem = ExcludeFromList.getTestDataSetItem();
        Assert.assertTrue(dataSetItem.equals(testDataSetItem));
    }


    @Test
    public void testExcludeFromListCopyFromRecord() {
        ExcludeFromList excludeFromList = new ExcludeFromList();
        DataSetItem testDataSetItem = ExcludeFromList.getTestDataSetItem();
        ObjectConverter.copyFromRecord(testDataSetItem, excludeFromList, false);
        DataSetItem dataSetItem =
            new DataSetItem(ObjectConverter.generateConfigurationAttributes(ExcludeFromList.class));
        ObjectConverter.copyToRecord(dataSetItem, excludeFromList);
        Assert.assertTrue(dataSetItem.equals(testDataSetItem));
    }


    //THIS test is supposed to make sure that a primary key without an attribute annotation
    // won't have an index of zero
    @Test
    public void testPrimaryKeyWithNoIndex() {
        DataSetItem dataSetItem =
            new DataSetItem(ObjectConverter.generateConfigurationAttributes(TestObject.class));
        TestObject testObject = new TestObject();
        testObject.id = 1;
        testObject.name = "testing";
        ObjectConverter.copyToRecord(dataSetItem, testObject);

    }


    @Test
    public void testAnnotatedMethodConversion() {
        TestObject testObject = new TestObject();
        testObject.setId(1);
        testObject.setName("Sam");

        DataSetItem dataSetItem =
            new DataSetItem(ObjectConverter.generateConfigurationAttributes(TestObject.class));
        ObjectConverter.copyToRecord(dataSetItem, testObject);
        Assert.assertTrue(dataSetItem.getString(1).equals("Test Name"));
        TestObject testObject1 = new TestObject();
        ObjectConverter.copyFromRecord(dataSetItem, testObject1, false);
        Assert.assertTrue(testObject.equals(testObject1));
    }


    public class TestObject {
        @PrimaryKey
        private
        int id;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestObject that = (TestObject) o;
            return id == that.id &&
                   Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {

            return Objects.hash(id, name);
        }

        @Attribute(index = 0)
        private String name;

        @Attribute(index = 1)
        private String billingName() {
            return "Test Name";
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }


}