package sdk.sample.model;

import com.avaje.ebean.Model;

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

    @OneToMany(cascade = CascadeType.ALL)
    public List<Note> notes;

    public static Finder<String, WorkOrder> find = new Finder<>(WorkOrder.class);

}
