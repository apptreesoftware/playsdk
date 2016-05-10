package data;

import sdk.sample.model.Priority;
import sdk.list.*;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

/**
 * Created by Matthew Smith on 5/4/16.
 * Copyright AppTree Software, Inc.
 */
public class PriorityListDataSource extends ListDataSource {
    @Override
    public ListServiceConfiguration getDescription() {
        ListServiceConfigurationAttribute attribute1 = new ListServiceConfigurationAttribute.Builder("Priority").build();
        ListServiceConfigurationAttribute attribute2 = new ListServiceConfigurationAttribute.Builder("Description").build();
        ListServiceConfiguration configuration = new ListServiceConfiguration("Priorities");
        configuration.setAttribute1(attribute1);
        configuration.setAttribute2(attribute2);
        return configuration;
    }

    @Override
    public List queryList(String queryText, boolean barcodeSearch, AuthenticationInfo authenticationInfo, Parameters params) {
        return null;
    }

    @Override
    public List getList(AuthenticationInfo authenticationInfo, Parameters params) {
        List list = new List();
        ListItem item;

        for ( Priority priority : Priority.priorities ) {
            list.addListItem(priority.toListItem());
        }

        return list;
    }

    @Override
    public boolean canCache() {
        return false;
    }

    @Override
    public boolean canSearch() {
        return false;
    }

    @Override
    public String getServiceName() {
        return "Priority";
    }
}
