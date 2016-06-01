part of sdk_validator.view_models;

/// Combines a dataset with attributes and values
class DatasetAndAttributes extends JsProxy {
  @reflectable
  DataSetItemDisplay datasetItem;

  @reflectable
  List<ServiceConfigurationAttributeDisplay> configAttributes;

  @reflectable
  List<AttributeValue> get attributesWithValues {
    var result = [];
    for (var attribute in configAttributes) {
      var attributeIndex = attribute.wrapped.attributeIndex;
      var key = attribute.wrapped.name;
      var value = datasetItem.wrapped.valueForAttributeIndex(attributeIndex);
      result.add(new AttributeValue(key, value));
    }
    return result;
  }

  DatasetAndAttributes(this.datasetItem, this.configAttributes);
}

class AttributeValue extends JsProxy {
  @reflectable
  String key;

  @reflectable
  String value;

  AttributeValue(this.key, this.value);
}

/// Helper class for <iron-list>
class IronListHelper<T extends JsProxy> extends JsProxy {
  @reflectable
  int index;
  @reflectable
  T item;
  IronListHelper(this.index, this.item);
}