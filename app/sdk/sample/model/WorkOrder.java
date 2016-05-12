package sdk.sample.model;

import com.avaje.ebean.Model;
import sdk.datax.DataSetItem;

import javax.persistence.*;
import java.util.List;

/**
 * Created by matthew on 5/5/16.
 */

@Entity
public class WorkOrder extends Model {
    @Id @GeneratedValue public long id;
    public String number;
    public String description;
    public String assignedTo;
    public String requestorId;
    public String priority;


    @OneToMany(cascade = CascadeType.ALL)
    public List<Note> notes;

    public void copyIntoDataSetItem(DataSetItem dataSetItem) {
        dataSetItem.setPrimaryKey(id + "");
        dataSetItem.setStringForAttributeIndex(id + "", IDIndex);
        dataSetItem.setStringForAttributeIndex(number, NumberIndex);
        dataSetItem.setStringForAttributeIndex(description, DescriptionIndex);
        dataSetItem.setStringForAttributeIndex(assignedTo, AssignedIndex);
        dataSetItem.setStringForAttributeIndex(requestorId, RequestorIndex);
    }

    public void copyFromDataSetItem(DataSetItem dataSetItem) {
        number = dataSetItem.getStringAttributeAtIndex(NumberIndex);
        description = dataSetItem.getStringAttributeAtIndex(DescriptionIndex);
        assignedTo = dataSetItem.getStringAttributeAtIndex(AssignedIndex);
        requestorId = dataSetItem.getStringAttributeAtIndex(RequestorIndex);
    }

    public static int IDIndex = 0;
    public static int NumberIndex = 1;
    public static int DescriptionIndex = 2;
    public static int AssignedIndex = 3;
    public static int RequestorIndex = 4;

}
