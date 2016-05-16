package sdk.list;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by alexis on 5/3/16.
 */
public class List {
    public ArrayList<ListItem> listItems;

    /**
     * Creates a list
     */
    public List() {
        listItems = new ArrayList<ListItem>();
    }

    public List(java.util.List<ListItem> listItems) {
        this.listItems = new ArrayList<>(listItems);
    }

    /**
     * Adds a list item
     * @param listItem The list item to add
     */
    public void addListItem(ListItem listItem) {
        if ( listItem != null ) {
            listItems.add(listItem);
        }
    }

    /**
     * Adds a collection of list items
     * @param listItems The collection of list items to add
     */
    public void addListItems(Collection<ListItem> listItems) {
        this.listItems.addAll(listItems);
    }

    /**
     * removes a list items
     * @param listItem The list item to remove
     */
    public void removeListItem(List listItem) {
        listItems.remove(listItem);
    }
}
