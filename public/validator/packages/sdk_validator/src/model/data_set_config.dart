part of sdk_validator.model;

@JsonSerializable()
class DataSetConfigurationResponse extends Object
    with _$DataSetConfigurationResponseSerializerMixin {
  final bool success;
  final String message;
  final bool showMessageAsAlert;
  final bool authorizationError;
  final String name;
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
    with _$ServiceConfigurationAttributeSerializerMixin
    implements DisplayElement {
  final String name;
  final RelatedServiceConfiguration relatedService;
  final String attributeType;
  final bool createRequired;
  final bool updateRequired;
  final bool searchRequired;
  final int attributeIndex;
  @JsonKey('create')
  final bool canCreate;
  @JsonKey('update')
  final bool canUpdate;
  @JsonKey('search')
  final bool canSearch;
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

  bool get hidden => false;
  String get title => name;
  DisplayType get displayType {
    switch (dataType) {
      case DataType.Text:
        return DisplayType.TextField;
      case DataType.ListItem:
        return DisplayType.SelectList;
      case DataType.Attachments:
        return DisplayType.Attachment;
      default:
        return DisplayType.TextField;
    }
  }
  DataType get dataType => DataTypeFromString(attributeType);
}

@JsonSerializable()
class RelatedServiceConfiguration extends Object
    with _$RelatedServiceConfigurationSerializerMixin {
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
    with _$ListServiceConfigurationSerializerMixin {
  final String listName;

  final bool includesLocation;
  final bool authenticationRequired;
  final int userIDIndex;
  final bool canCache;

  @JsonKey('search')
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
class ListServiceConfigurationResponse extends Object with _$ListServiceConfigurationResponseSerializerMixin {
  final bool success;
  final List<ListServiceConfigurationAttribute> attributes;
  ListServiceConfigurationResponse(this.success, this.attributes);
  factory ListServiceConfigurationResponse.fromJson(json) =>
      _$ListServiceConfigurationResponseFromJson(json);
}

@JsonSerializable()
class ListServiceConfigurationAttribute extends Object
    with _$ListServiceConfigurationAttributeSerializerMixin {
  final int attributeIndex;

  final String label;
  final String attributeType;
  final String relatedListConfiguration;

  ListServiceConfigurationAttribute(this.attributeIndex, this.label,
      this.attributeType, this.relatedListConfiguration);

  factory ListServiceConfigurationAttribute.fromJson(json) =>
      _$ListServiceConfigurationAttributeFromJson(json);
}

@JsonSerializable()
class ServiceParameter extends Object with _$ServiceParameterSerializerMixin {
  final String key;
  final String type;
  final List<String> possibleValues;
  final bool availableInQueryBuilder;
  final bool required;

  ServiceParameter(this.key, this.type, this.possibleValues,
      this.availableInQueryBuilder, this.required);

  factory ServiceParameter.fromJson(json) => _$ServiceParameterFromJson(json);
}
