part of sdkwebvalidator.models;

class ListItem {
    String id;
    Map<int, String> _attributeMap = {};
    String attribute01;
    String attribute02;
    String attribute03;
    String attribute04;
    String attribute05;
    String attribute06;
    String attribute07;
    String attribute08;
    String attribute09;
    String attribute10;
    String value;
    double latitude;
    double longitude;
    String parentID;

    ListItem(String value) {
        id = value;
        this.value = value;
    }
    
    ListItem.fromJson(Map<String, dynamic> json) {
        this.value = json["value"].toString();
        this.attribute01 = json["attribute01"];
        this.attribute02 = json["attribute02"];
        this.attribute03 = json["attribute03"];
        this.attribute04 = json["attribute04"];
        this.attribute05 = json["attribute05"];
        this.attribute06 = json["attribute06"];
        this.attribute07 = json["attribute07"];
        this.attribute08 = json["attribute08"];
        this.attribute09 = json["attribute09"];
        this.attribute10 = json["attribute10"];
        this.id = json["id"];
        this.latitude = json["latitude"];
        this.longitude = json["latitude"];
        this.parentID = json["parentID"];
    }
    
    String stringValueForKey(String key) {
        if (key == null) return null;
        var index = int.parse(key);
        if (index != null) {
            return valueForAttributeIndex(index);
        }
        return null;
    }
    
    String valueForAttributeIndex(int index) {
        String attributeValue;
        switch(index) {
        case 0:
            attributeValue = value;
            break;
        case 1:
            attributeValue = attribute01;
            break;
        case 2:
            attributeValue = attribute02;
            break;
        case 3:
            attributeValue = attribute03;
            break;
        case 4:
            attributeValue = attribute04;
            break;
        case 5:
            attributeValue = attribute05;
            break;
        case 6:
            attributeValue = attribute06;
            break;
        case 7:
            attributeValue = attribute07;
            break;
        case 8:
            attributeValue = attribute08;
            break;
        case 9:
            attributeValue = attribute09;
            break;
        case 10:
            attributeValue = attribute10;
            break;
        default:
            attributeValue = /*coreUserInfo?.valueForAttributeIndex(index) ??*/ "";
        }
        return attributeValue ?? "";
    }

    void setValueForAttributeIndex(int index, String value) {
        switch(index) {
            case 0:
                this.value = value;
                break;
            case 1:
                this.attribute01 = value;
                break;
            case 2:
                this.attribute02 = value;
                break;
            case 3:
                this.attribute03 = value;
                break;
            case 4:
                this.attribute04 = value;
                break;
            case 5:
                this.attribute05 = value;
                break;
            case 6:
                this.attribute06 = value;
                break;
            case 7:
                this.attribute07 = value;
                break;
            case 8:
                this.attribute08 = value;
                break;
            case 9:
                this.attribute09 = value;
                break;
            case 10:
                this.attribute10 = value;
                break;
            default:
                break;
        }
    }
    
    Map<String, dynamic> toJson() {
        var dictionary = {};
        dictionary["value"] = value;
        dictionary["attribute01"] = attribute01 ?? null;
        dictionary["attribute02"] = attribute02 ?? null;
        dictionary["attribute03"] = attribute03 ?? null;
        dictionary["attribute04"] = attribute04 ?? null;
        dictionary["attribute05"] = attribute05 ?? null;
        dictionary["attribute06"] = attribute06 ?? null;
        dictionary["attribute07"] = attribute07 ?? null;
        dictionary["attribute08"] = attribute08 ?? null;
        dictionary["attribute09"] = attribute09 ?? null;
        dictionary["attribute10"] = attribute10 ?? null;
        dictionary["latitude"] = "$latitude" ?? null;
        dictionary["longitude"] = "$longitude" ?? null;
        dictionary["parentID"] = parentID ?? null;
        dictionary["id"] = this.id ?? "";
        return dictionary;
    }
}

