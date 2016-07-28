package sdk.models;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by alexis on 5/3/16.
 */
public enum AttributeType {
    String("Text"),
    Int("Integer"),
    TimeInterval("TimeInterval"),
    Double("Float"),
    Boolean("Boolean"),
    Date("Date"),
    DateTime("DateTime"),
    DateRange("DateRange"),
    DateTimeRange("DateTimeRange"),
    Image("Image"),
    Location("Location"),
    Attachments("Attachments"),
    Relation("Relationship"),
    SingleRelationship("SingleRelationship"),
    ListItem("ListItem"),
    Color("Color"),
    None("None");

    private String stringValue;
    private AttributeType(String stringValue) {
        this.stringValue = stringValue;
    }

    @JsonValue
    public String toString() {
        return stringValue;
    }

    /**
     *
     * @param string the string attribute type
     * @return an Attributetype
     */
    static public AttributeType fromString(String string) {
        if ( string.equalsIgnoreCase(String.stringValue) ) {
            return String;
        } else if ( string.equalsIgnoreCase(Int.stringValue) ) {
            return Int;
        } else if ( string.equalsIgnoreCase(Boolean.stringValue) ) {
            return Boolean;
        } else if ( string.equalsIgnoreCase(Date.stringValue) ) {
            return Date;
        } else if ( string.equalsIgnoreCase(DateTime.stringValue) ) {
            return DateTime;
        } else if ( string.equalsIgnoreCase(DateRange.stringValue) ) {
            return DateRange;
        } else if ( string.equalsIgnoreCase(DateTimeRange.stringValue) ) {
            return DateTimeRange;
        } else  if ( string.equalsIgnoreCase(Image.stringValue) ) {
            return Image;
        } else if ( string.equalsIgnoreCase(Location.stringValue) ) {
            return Location;
        } else if ( string.equalsIgnoreCase(Attachments.stringValue) ) {
            return Attachments;
        } else if ( string.equalsIgnoreCase(Relation.stringValue) ) {
            return Relation;
        } else if ( string.equalsIgnoreCase(SingleRelationship.stringValue) ) {
            return SingleRelationship;
        } else if ( string.equalsIgnoreCase(ListItem.stringValue) ) {
            return ListItem;
        } else if ( string.equalsIgnoreCase(Color.stringValue) ) {
            return Color;
        } else if ( string.equalsIgnoreCase(TimeInterval.stringValue) ) {
            return TimeInterval;
        } else {
            return None;
        }
    }
}
