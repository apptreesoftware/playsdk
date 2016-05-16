package sdk.sample.model;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by matthew on 5/12/16.
 */
@Entity
public class User extends Model {
    @Id
    @GeneratedValue
    public long id;
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;

    //"extra" user info
    public String employeeID;
    public long supervisorID;
}
