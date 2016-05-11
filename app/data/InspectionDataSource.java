package data;

import sdk.data.*;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;
import sdk.utils.Response;
import sdk.utils.ServiceParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexis on 5/4/16.
 */
public class InspectionDataSource implements DataSource {
    @Override
    public DataSet getDataSet(AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException {
        return null;
    }

    @Override
    public DataSet getDataSetItem(AuthenticationInfo authenticationInfo, String id, Parameters params) throws InvalidAttributeValueException {
        return null;
    }

    @Override
    public DataSet queryDataSet(DataSetItem queryDataItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException {
        return null;
    }

    @Override
    public DataSet createDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException {
        return null;
    }

    @Override
    public DataSet updateDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException {
        return null;
    }

    @Override
    public Response deleteDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException {
        return null;
    }

    @Override
    public String getServiceDescription() {
        return "Inspections";
    }

    @Override
    public List<ServiceConfigurationAttribute> getDataSetAttributes(AuthenticationInfo authenticationInfo, Parameters params) {
        List<ServiceConfigurationAttribute> attributes = new ArrayList<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(0).name("ID").asInt().canUpdateAndRequired().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(1).name("Name").canUpdateAndRequired().canSearchAndRequired().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(2).name("Inspection Class").canSearchAndRequired().canUpdateAndRequired().canCreateAndRequired().build());
        return attributes;
    }

    @Override
    public List<ServiceParameter> getServiceFilterParameters() {
        return null;
    }

    @Override
    public Response updateEventForDataSetItem(String dataSetItemID, Event event, AuthenticationInfo authenticationInfo, Parameters params) {
        return null;
    }

    @Override
    public List<String> getDependentLists() {
        return null;
    }

    @Override
    public DataSet bulkUpdateDataSetItems(List<String> primaryKeys, DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException {
        return null;
    }
}
