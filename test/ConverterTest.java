import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import sdk.converter.ObjectConverter;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfiguration;
import sdk.exceptions.UnableToWriteException;
import sdk.exceptions.UnsupportedAttributeException;
import sdk.list.ListItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public void toDataSetItem(){
        SampleObject sampleObject = getSampleObject();
        DataSetItem dataSetItem = new DataSetItem(ObjectConverter.generateConfigurationAttributes(SampleObject.class));
        ObjectConverter.copyToRecord(dataSetItem, sampleObject);
    }




    @Test
    public void fromDataSetItem(){
        SampleObject sampleObject = getSampleObject();
        DataSetItem dataSetItem = new DataSetItem(ObjectConverter.generateConfigurationAttributes(SampleObject.class));
        ObjectConverter.copyToRecord(dataSetItem, sampleObject);
        SampleObject testSampleObject = new SampleObject();
        ObjectConverter.copyFromRecord(dataSetItem, testSampleObject);
    }



























}