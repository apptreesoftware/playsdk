package sdk.list;

import sdk.datasources.base.CacheableList;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by matthew on 5/24/16.
 */
public class SimpleListDataSource implements CacheableList {
    private java.util.List<String> items;
    private String serviceName;

    public SimpleListDataSource(String serviceName, String... items) {
        this.items = Arrays.asList(items);
        this.serviceName = serviceName;
    }

    @Override
    public List getList(AuthenticationInfo authenticationInfo, Parameters params) {
        List list = new List();
        for ( String item : items ) {
            list.addListItem(new ListItem(item));
        }
        return list;
    }

    @Override
    public boolean isListContentGlobal() {
        return true;
    }

    @Override
    public Set<ListServiceConfigurationAttribute> getListServiceAttributes() {
        return new HashSet<ListServiceConfigurationAttribute>();
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }
}
