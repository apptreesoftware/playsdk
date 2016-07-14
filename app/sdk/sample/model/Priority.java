package sdk.sample.model;

import sdk.list.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexis on 5/4/16.
 */
public class Priority {
    public int id;
    public String name;
    public String description;

    public static int idCounter = 1;

    public static List<Priority> priorities;

    static {
        priorities = new ArrayList<>();
        Priority priority;

        priority = new Priority();
        priority.id = idCounter;
        priority.name = "P1";
        priority.description = "Emergency";
        idCounter++;
        priorities.add(priority);

        priority = new Priority();
        priority.id = idCounter;
        priority.name = "P2";
        priority.description = "Urgent/Expidited";
        idCounter++;
        priorities.add(priority);

        priority = new Priority();
        priority.id = idCounter;
        priority.name = "P3";
        priority.description = "Routine Type I";
        idCounter++;
        priorities.add(priority);

        priority = new Priority();
        priority.id = idCounter;
        priority.name = "P4";
        priority.description = "Routine Type II";
        idCounter++;
        priorities.add(priority);

        priority = new Priority();
        priority.id = idCounter;
        priority.name = "P5";
        priority.description = "Renos/Projects/Events";
        idCounter++;
        priorities.add(priority);

        priority = new Priority();
        priority.id = idCounter;
        priority.name = "P6";
        priority.description = "Deferred";
        idCounter++;
        priorities.add(priority);
    }

    public Priority() {}

    public Priority(ListItem listItem) {
        if ( listItem != null ) {
            id = Integer.parseInt(listItem.id);
            name = listItem.getAttributeForIndex(0).getStringValue();
            description = listItem.getAttributeForIndex(1).getStringValue();
        }
    }

    public ListItem toListItem() {
        ListItem listItem = new ListItem(String.valueOf(id));
        listItem.setAttributeForIndex(name, 0);
        listItem.setAttributeForIndex(description, 1);
        return listItem;
    }
}
