part of sdkwebvalidator.models;

@JsonSerializable()
class DataSetConfigurationResponse extends Object
    with _$DataSetConfigurationResponseSerializerMixin, JsProxy {
  @reflectable
  final bool success;

  @reflectable
  final String message;

  @reflectable
  final bool showMessageAsAlert;

  @reflectable
  final bool authorizationError;

  @reflectable
  final String name;

  @reflectable
  List<ServiceConfigurationAttribute> attributes;

  List<String> dependentLists;

  DataSetConfigurationResponse(
      this.success,
      this.message,
      this.showMessageAsAlert,
      this.authorizationError,
      this.name,
      this.attributes,
      this.dependentLists);

  factory DataSetConfigurationResponse.fromJson(json) =>
      _$DataSetConfigurationResponseFromJson(json);
}

@JsonSerializable()

class ServiceConfigurationAttribute extends Object
    with _$ServiceConfigurationAttributeSerializerMixin, JsProxy
    implements DisplayElement {
  @reflectable
  final String name;

  @reflectable
  final RelatedServiceConfiguration relatedService;

  @reflectable
  final String attributeType;

  @reflectable
  final bool createRequired;

  @reflectable
  final bool updateRequired;

  @reflectable
  final bool searchRequired;

  @reflectable
  final int attributeIndex;

  @reflectable
  @JsonKey('create')
  final bool canCreate;

  @reflectable
  @JsonKey('update')
  final bool canUpdate;

  @reflectable
  @JsonKey('search')
  final bool canSearch;

  @reflectable
  final ListServiceConfiguration relatedListServiceConfiguration;

  ServiceConfigurationAttribute(
      this.name,
      this.relatedService,
      this.attributeType,
      this.createRequired,
      this.updateRequired,
      this.searchRequired,
      this.attributeIndex,
      this.canCreate,
      this.canUpdate,
      this.canSearch,
      this.relatedListServiceConfiguration);

  factory ServiceConfigurationAttribute.fromJson(json) =>
      _$ServiceConfigurationAttributeFromJson(json);

  @override
  bool get hidden => false;
  @override
  String get title => name;
  @override
  DisplayType get displayType {
    switch (dataType) {
      case DataType.Text:
        return DisplayType.TextField;
      case DataType.ListItem:
        return DisplayType.SelectList;
      case DataType.Relationship:
        return DisplayType.Relationship;
      default:
        return DisplayType.TextField;
    }
  }

  @override
  DataType get dataType => DataTypeFromString(attributeType);
}

@JsonSerializable()
class RelatedServiceConfiguration extends Object
    with _$RelatedServiceConfigurationSerializerMixin, JsProxy {
  final bool success;
  final String message;
  final bool showMessageAsAlert;
  final String name;
  final List<ServiceConfigurationAttribute> attributes;
  final Map<String, ServiceConfigurationAttribute>
      attributeConfigurationForIndexMap;
  List<String> dependentLists;

  RelatedServiceConfiguration(
      this.success,
      this.name,
      this.message,
      this.showMessageAsAlert,
      this.attributes,
      this.attributeConfigurationForIndexMap,
      this.dependentLists);

  factory RelatedServiceConfiguration.fromJson(json) =>
      _$RelatedServiceConfigurationFromJson(json);
}

@JsonSerializable()
class ListServiceConfiguration extends Object
    with _$ListServiceConfigurationSerializerMixin, JsProxy {

  @reflectable
  final String listName;

  final bool includesLocation;
  final bool authenticationRequired;
  final int userIDIndex;
  final bool canCache;
  final bool canSearch;
  List<ListServiceConfigurationAttribute> attributes;
  List<ServiceParameter> serviceFilterParameters;

  ListServiceConfiguration(
      this.listName,
      this.includesLocation,
      this.authenticationRequired,
      this.userIDIndex,
      this.canCache,
      this.canSearch,
      this.attributes);

  factory ListServiceConfiguration.fromJson(json) =>
      _$ListServiceConfigurationFromJson(json);
}

@JsonSerializable()
class ListServiceConfigurationAttribute extends Object
    with _$ListServiceConfigurationAttributeSerializerMixin, JsProxy {
  final int attributeIndex;

  @reflectable
  final String label;

  @reflectable
  final String attributeType;

  @reflectable
  final String relatedListConfiguration;

  ListServiceConfigurationAttribute(this.attributeIndex, this.label,
      this.attributeType, this.relatedListConfiguration);

  factory ListServiceConfigurationAttribute.fromJson(json) =>
      _$ListServiceConfigurationAttributeFromJson(json);
}

@JsonSerializable()
class ServiceParameter extends Object
    with _$ServiceParameterSerializerMixin, JsProxy {
  final String key;
  final String type;
  final List<String> possibleValues;
  final bool availableInQueryBuilder;
  final bool required;

  ServiceParameter(this.key, this.type, this.possibleValues,
      this.availableInQueryBuilder, this.required);

  factory ServiceParameter.fromJson(json) => _$ServiceParameterFromJson(json);
}
