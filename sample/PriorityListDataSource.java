package sdk.sample;

import com.fasterxml.jackson.databind.JsonNode;
import sdk.list.*;
import sdk.list.List;
import sdk.sample.model.Priority;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alexis on 5/10/16.
 */
public class PriorityListDataSource implements CacheableList, SearchableList {

    public Set<ListServiceConfigurationAttribute> getListServiceAttributes() {
        HashSet<ListServiceConfigurationAttribute> attributes = new HashSet<>();
        attributes.add(new ListServiceConfigurationAttribute.Builder(0).name("Name").build());
        attributes.add(new ListServiceConfigurationAttribute.Builder(1).name("Name").build());
        return attributes;
    }

    @Override
    public List queryList(String queryText, boolean barcodeSearch, JsonNode context, AuthenticationInfo authenticationInfo, Parameters params) {
        List list = new List();
        Priority.priorities.stream().filter(priority -> (priority.id + "").contains(queryText) || priority.name.toLowerCase().contains(queryText.toLowerCase()) || priority.description.toLowerCase().contains(queryText.toLowerCase())).forEach(priority -> {
            list.addListItem(priority.toListItem());
        });
        return list;
    }

    @Override
    public List getList(AuthenticationInfo authenticationInfo, Parameters params) {
        List list = new List();
        for ( Priority priority : Priority.priorities ) {
            list.addListItem(priority.toListItem());
        }
        return list;
    }

    @Override
    public boolean isListContentGlobal() {
        return true;
    }

    @Override
    public String getServiceName() {
        return "Priorities";
    }
}
