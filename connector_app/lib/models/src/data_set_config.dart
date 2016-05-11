part of connector_app.models;

@JsonSerializable()
class DataSetConfigurationResponse extends Object
    with _$DataSetConfigurationResponseSerializerMixin, JsProxy {

  @reflectable
  final bool success;

  @reflectable
  final String message;

  @reflectable
  final bool showMessageAlert;

  @reflectable
  final bool authorizationError;

  @reflectable
  List<ServiceConfigurationAttribute> attributes;

  DataSetConfigurationResponse(this.success, this.message,
      this.showMessageAlert, this.authorizationError, this.attributes);

  factory DataSetConfigurationResponse.fromJson(json) =>
      _$DataSetConfigurationResponseFromJson(json);
}

@JsonSerializable()
class ServiceConfigurationAttribute extends Object
    with _$ServiceConfigurationAttributeSerializerMixin, JsProxy implements DisplayElement {

  @reflectable
  final String name;

  @reflectable
  final ServiceConfiguration relatedService;

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
  final bool canUpdate;

  @reflectable
  final bool canCreate;

  @reflectable
  /// INCOMPLETE
  final Map relatedListServiceConfiguration;

  @reflectable
  final bool canSearch;

  ServiceConfigurationAttribute(this.name, this.relatedService, this.attributeType, this.createRequired, this.updateRequired, this.searchRequired, this.attributeIndex, this.canUpdate, this.canCreate, this.relatedListServiceConfiguration, this.canSearch);

  factory ServiceConfigurationAttribute.fromJson(json) => _$ServiceConfigurationAttributeFromJson(json);

  @override
  bool get hidden => false;
  @override
  String get title => name;
  @override
  DisplayType get displayType {
    switch(dataType) {
      case DataType.Text:
        return DisplayType.TextField;
      default:
        return DisplayType.TextField;
    }
  }

  @override
  DataType get dataType => DataTypeFromString(attributeType);

}

/// INCOMPLETE
@JsonSerializable()
class ServiceConfiguration extends Object
    with _$ServiceConfigurationSerializerMixin, JsProxy {

  @reflectable
  final String configurationName;

  @reflectable
  final List<ServiceConfigurationAttribute> attributes;

  @reflectable
  final List<String> mDependentListEndpoints;

  @reflectable
  final List<Map> serviceFilterParameters;

  ServiceConfiguration(this.configurationName, this.attributes,
      this.mDependentListEndpoints, this.serviceFilterParameters);

  factory ServiceConfiguration.fromJson(json) =>
      _$ServiceConfigurationFromJson(json);
}
