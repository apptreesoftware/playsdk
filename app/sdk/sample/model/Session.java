package sdk.sample.model;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by matthew on 5/12/16.
 */
@Entity
public class Session extends Model {
    public String token;
    public String username;
    public Date loginDate;
}
