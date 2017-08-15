import sdk.annotations.Attribute;
import sdk.list.ListItem;
import sdk.list.ListServiceConfigurationAttribute;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by matthew on 6/9/17.
 */
public class IssueStatus {


    @Attribute(index = 0)
    public String status;

    public IssueStatus(String status) {
        this.status = status;
    }

    public static Set<ListServiceConfigurationAttribute> getListAttributes() {
        HashSet<ListServiceConfigurationAttribute> set = new HashSet<>();
        set.add(new ListServiceConfigurationAttribute.Builder(0).name("Status").build());
        return set;
    }

    public ListItem toListItem() {
        ListItem listItem = new ListItem(status);
        listItem.setAttributeForIndex(status, 0);
        return listItem;
    }

    public static List<IssueStatus> getAllStatus() {
        ArrayList<IssueStatus> all = new ArrayList<>();
        all.add(new IssueStatus("Open"));
        all.add(new IssueStatus("In Progress"));
        all.add(new IssueStatus("Fixed"));
        all.add(new IssueStatus("Closed"));
        return all;
    }

}
