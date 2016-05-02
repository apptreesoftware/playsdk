package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Matthew Smith on 4/25/16.
 * Copyright AppTree Software, Inc.
 */
@Entity
@Table(name = "at_inspection_configuration_attribute")
public class InspectionConfigurationAttribute {

    @Id public long id;
    public String name;
    public int index;
    public String dataType;

    public static Model.Finder<Long, InspectionConfigurationAttribute> find = new Model.Finder<>(InspectionConfigurationAttribute.class);

}
