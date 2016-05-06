package sdk.sample.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by matthew on 5/5/16.
 */

@Entity
public class Note {

    @Id public String id;
    public String text;
    public String createdBy;
}
