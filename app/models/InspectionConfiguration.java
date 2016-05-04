package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew Smith on 4/25/16.
 * Copyright AppTree Software, Inc.
 */
@Entity
@Table(name = "at_inspection_configuration")
public class InspectionConfiguration extends Model {

    @Id public long id;
    @Constraints.Required
    public String inspectionName;
    public String inspectionClass;

    @OneToMany(cascade= CascadeType.ALL)
    public List<InspectionConfigurationAttribute> attributes = new ArrayList<>();

    public static Finder<Long, InspectionConfiguration> find = new Finder<>(InspectionConfiguration.class);

}
