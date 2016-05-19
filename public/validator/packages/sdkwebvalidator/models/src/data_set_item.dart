part of sdkwebvalidator.models;

class DataSetItem extends JsProxy {

  @reflectable
  String uuid = "";

  @reflectable
  String serverPrimaryKey = "";

  @reflectable
  String recordTypeString = "RECORD";

  @reflectable
  int serverListOrder = 0;

  @reflectable
  String statusString = "READ";

  @reflectable
  int subItemOfAttributeIndex = -1;

  @reflectable
  int subItemOrderBy = 0;

  @reflectable
  String dataCollectionStatusString = "NONE";

  @reflectable
  String workFlowState = "NONE";

  @reflectable
  DateTime retrievalDate = new DateTime.now();

  @reflectable
  String typedAttributeStorage = "{}";

  DataSetItem parentItem;
  List<DataSetItem> subItems = [];
  DataSet dataSet = new DataSet();

  DataSetItem();

  factory DataSetItem.fromFormElementDisplays(List<FormElementDisplay> displays) {
    var result = new DataSetItem();
    for (var i = 0; i < displays.length; i++) {
      var display = displays[i];
      result.setAttribute(display.value, i);
    }
    return result;
  }

  void updateFromJson(Map<String, Object> json, String primaryKeyPrefix,
      [bool asSubItem = false]) {

    try {
      assert(dataSet != null);
    } catch (e) {
      throw ("A data set is required before updateFromJSON is called");
    }

    serverPrimaryKey = json["primaryKey"].toString();
    if (uuid.isEmpty) {
      uuid = "$primaryKeyPrefix-$serverPrimaryKey";
    }
    if (!asSubItem) {
      retrievalDate = new DateTime.now();
    }
    recordTypeString = json["recordType"].toString() ?? "RECORD";
    statusString = json["CRUDStatus"].toString() ?? "READ";
    dataCollectionStatusString =
        json["DataCollectionStatus"].toString() ?? "NONE";
    workFlowState = json["workFlowState"].toString() ?? "NONE";
    var subItemIDCache = new Set<String>();
    var attributes = json["attributes"] ?? [];
    for (var index = 0; index < attributes.length; index++) {
      Object attribute = attributes[index];
      if (attribute == null) {
        setAttribute("", index);
      } else if (attribute is List) {
        var subItems = attribute;
        var subItemIndex = 0;
        for (var subRecord in subItems) {
          var subItem = new DataSetItem();
          subItem.subItemOfAttributeIndex = index;
          subItem.dataSet = this.dataSet;
          subItem.subItemOrderBy = subItemIndex;
          subItem.retrievalDate = retrievalDate;
          subItem = new DataSetItem()..updateFromJson(subRecord, uuid);
          if (!subItemIDCache.contains(subItem.uuid)) {
            addSubItem(subItem);
            subItemIndex += 1;
            subItemIDCache.add(subItem.uuid);
          }
        }
      } else {
        var stringValue = attribute.toString();
        var listItem = new ListItem(stringValue);
        if (listItem != null) {
          setListItem(listItem, index, updateTypeStorage: false);
        } else {
          setAttribute(attribute.toString(), index);
        }
      }
    }
    saveTypedAttributeMap();
  }

  setListItem(ListItem listItem, int index, {bool updateTypeStorage: true}) {
    if (listItem != null) {
      var listItem2 = listItem;
      this.setAttribute(listItem2.value, index);
      typedAttributeMap["$index"] = listItem.toJson();
    } else {
      this.setAttribute("", index);
      typedAttributeMap["$index"] = null;
    }
    if (updateTypeStorage) {
      saveTypedAttributeMap();
    }
  }

  Map<String, dynamic> get typedAttributeMap {
    var json = JSON.decode(this.typedAttributeStorage);
    if (json != null) {
      return json;
    } else {
      return {};
    }
  }

  String typedAttributeStringAtIndex(int index) {
    return typedAttributeMap["$index"];
  }

  void saveTypedAttributeMap() {
    try {
      var jsonData = typedAttributeMap;
      var jsonString = JSON.encode(jsonData);
      if (jsonString != null) {
        typedAttributeStorage = jsonString;
      }
    } catch (error) {
      print("Could not write JSON: ${error}");
    }
  }

  Map<String, dynamic> toJson() {
    var dictionary = new Map<String, dynamic>();
    dictionary["primaryKey"] = this.serverPrimaryKey;
    dictionary["clientKey"] = this.uuid;
    dictionary["CRUDStatus"] = this.statusString;
    dictionary["recordType"] = this.recordTypeString;
    dictionary["DataCollectionStatus"] = this.dataCollectionStatusString;
    dictionary["workFlowState"] = this.workFlowState;
    var attributes = [];
    var subItemMap = subItemsForAttributeMap();
    var firstNullIndex = -1;
    for (var i = 0; i <= 80; i++) {
      var subItems = subSubmissionArrayForAttributeIndex(i, subItemMap);
      if (subItems != null) {
        attributes.add(subItems);
        firstNullIndex = -1;
      } else {
        String value;
        var typedValue = this.typedAttributeMap["$i"];
        if (typedValue != null) {
          value = typedValue;
        } else {
          value = this.valueForAttributeIndex(i);
        }
        if (value != null && !value.isEmpty) {
          attributes.add(value);
          firstNullIndex = -1;
        } else {
          attributes.add(null);
          if (firstNullIndex == -1) {
            firstNullIndex = i;
          }
        }
      }
    }
    if (firstNullIndex > 0) {
      attributes = attributes.sublist(0, firstNullIndex);
    }
    dictionary["attributes"] = attributes;
    return dictionary;
  }

  List<Map<String, dynamic>> subSubmissionArrayForAttributeIndex(
      int index, Map<int, dynamic> subItemMap) {
    var items = subItemMap[index];
    if (items != null) {
      var subItems = new List<Map<String, dynamic>>();
      for (var subItem in items) {
        subItems.add(subItem.toJson());
      }
      return subItems;
    }
    return null;
  }

  Map<int, List<DataSetItem>> subItemsForAttributeMap() {
    this.subItems.sort((dsi1, dsi2) {
      // sort with in order of priority 1. subItemOfAttributeIndex 2. subItemOrderBy
      var difference =
          dsi1.subItemOfAttributeIndex - dsi2.subItemOfAttributeIndex;
      if (difference == 0) {
        var orderByDifference = dsi1.subItemOrderBy - dsi2.subItemOrderBy;
        return orderByDifference;
      }
      return difference;
    });
    var map = new Map<int, List<DataSetItem>>();
    var itemsForIndex = new List<DataSetItem>();
    for (var subItem in subItems) {
      var index = subItem.subItemOfAttributeIndex;
      if (map.containsKey(index)) {
        itemsForIndex = map[index];
        itemsForIndex?.add(subItem);
        map[index] = itemsForIndex;
      } else {
        itemsForIndex = [subItem];
        map[index] = itemsForIndex;
      }
    }
    return map;
  }

  DataSetItem get rootDataSetItem {
    var parent = this;
    while (parent.parentItem != null) {
      parent = parent.parentItem;
    }
    return parent;
  }

  Map get criteriaDict {
    var dictionary = {};
    for (var i = 0; i <= 80; i++) {
      var value = this.valueForAttributeIndex(i);
      dictionary[i] = value;
    }
    return dictionary;
  }

  void addSubItem(DataSetItem subItem) {
    subItem.parentItem = this;
    subItems.add(subItem);
  }

  String get status {
    var crudStatus = this.statusString;
    if (crudStatus != null) {
      return crudStatus;
    }
    return "READ"; // verify CRUDStatus.Read is "READ"
  }

  void set status(String newCrudStatus) {
    this.statusString = newCrudStatus;
  }

  List<DataSetItem> subItemsForAttributeIndex(int index) {
    return new List.from(subItems.where((subItem) {
      return subItem.subItemOfAttributeIndex == index;
    }));
  }

  ListItem listItemForAttributeIndex(int index) {
    var jsonString = typedAttributeMap["$index"];
    var json = JSON.decode(jsonString);
    if (json != null) {
      var listItem = new ListItem.fromJson(json);
      return listItem;
    }
    return null;
  }

  Map<int, String> _attributeMap = {};

  @reflectable
  String valueForAttributeIndex(int index) {
    return _attributeMap[index];
  }

  void setAttribute(String value, int index) {
    _attributeMap[index] = value;
  }
}
