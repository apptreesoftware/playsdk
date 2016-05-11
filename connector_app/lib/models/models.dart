library connector_app.models;

import 'package:source_gen/generators/json_serializable.dart';
import 'package:polymer/polymer.dart' show JsProxy, reflectable;

part 'package:connector_app/models/models.g.dart';

@JsonSerializable()
class AppConfig extends Object with _$AppConfigSerializerMixin {
  @JsonKey('connector_host')
  String connectorHost;

  AppConfig(this.connectorHost);

  factory AppConfig.fromJson(json) => _$AppConfigFromJson(json);
}

@JsonSerializable()
class EndpointResponse extends Object with _$EndpointResponseSerializerMixin {
  final bool success;
  List<Endpoint> records;

  EndpointResponse(this.success, this.records);

  factory EndpointResponse.fromJson(json) => _$EndpointResponseFromJson(json);
}

@JsonSerializable()
class Endpoint extends Object with _$EndpointSerializerMixin, JsProxy {

  @reflectable
  final String endpoint;

  @reflectable
  final String name;

  @reflectable
  final String data;

  @reflectable
  final String url;

  Endpoint(this.endpoint, this.name, this.data, this.url);

  factory Endpoint.fromJson(json) => _$EndpointFromJson(json);
}


@JsonSerializable()
class DataSetResponse extends Object
    with _$DataSetResponseSerializerMixin, JsProxy {

  @reflectable
  final bool success;

  @reflectable
  final String message;

  @reflectable
  final bool showMessageAlert;

  @reflectable
  final bool authorizationError;

  @reflectable
  @JsonKey('records')
  final DataSet dataSet;

  DataSetResponse(this.success, this.message, this.showMessageAlert,
      this.authorizationError, this.dataSet);

  factory DataSetResponse.fromJson(json) => _$DataSetResponseFromJson(json);
}

@JsonSerializable()
class DataSet extends Object with _$DataSetSerializerMixin, JsProxy {
  @reflectable
  final int totalRecords;

  @reflectable
  final int numberOfRecords;

  @reflectable
  final bool moreRecordsAvailable;

  @reflectable
  @JsonKey('records')
  final List<DataSetItem> dataSetItems;

  DataSet(this.totalRecords, this.numberOfRecords, this.moreRecordsAvailable,
      this.dataSetItems);

  factory DataSet.fromJson(json) => _$DataSetFromJson(json);
}

@JsonSerializable()
class DataSetItem extends Object with _$DataSetItemSerializerMixin, JsProxy {
  @reflectable
  final String primaryKey;

  @reflectable
  @JsonKey('CRUDStatus')
  final String crudStatus;

  @reflectable
  final String clientKey;

  @reflectable
  final String recordType;

  @reflectable
  @JsonKey('DataCollectionStatus')
  final String dataCollectionStatus;

  @reflectable
  final String workFlowState;

  @reflectable
  final List<String> attributes;

  DataSetItem(this.primaryKey, this.crudStatus, this.clientKey, this.recordType,
      this.dataCollectionStatus, this.workFlowState, this.attributes);

  factory DataSetItem.fromJson(json) => _$DataSetItemFromJson(json);
}

@JsonSerializable()
class DataSetConfiguration extends Object
    with _$DataSetConfigurationSerializerMixin, JsProxy {

  @reflectable
  final List<DataSetAttribute> attributes;

  @reflectable
  final List<String> dependentLists;

  DataSetConfiguration(this.attributes, this.dependentLists);

  factory DataSetConfiguration.fromJson(json) =>
      _$DataSetConfigurationFromJson(json);
}

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
  @JsonKey('records')
  DataSetConfiguration configuration;

  DataSetConfigurationResponse(this.success, this.message,
      this.showMessageAlert, this.authorizationError, this.configuration);

  factory DataSetConfigurationResponse.fromJson(json) =>
      _$DataSetConfigurationResponseFromJson(json);
}

@JsonSerializable()
class DataSetAttribute extends Object
    with _$DataSetAttributeSerializerMixin, JsProxy {

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
  // TODO
  final Map relatedListServiceConfiguration;

  @reflectable
  final bool canSearch;

  DataSetAttribute(this.name, this.relatedService, this.attributeType, this.createRequired, this.updateRequired, this.searchRequired, this.attributeIndex, this.canUpdate, this.canCreate, this.relatedListServiceConfiguration, this.canSearch);

  factory DataSetAttribute.fromJson(json) => _$DataSetAttributeFromJson(json);
}

@JsonSerializable()
class ServiceConfiguration extends Object
    with _$ServiceConfigurationSerializerMixin, JsProxy {

  @reflectable
  final String configurationName;

  @reflectable
  final List<ServiceConfigurationAttribute> attributes;

  @reflectable
  final List<String> mDependentListEndpoints;

  @reflectable // TODO
  final List<Map> serviceFilterParameters;

  ServiceConfiguration(this.configurationName, this.attributes,
      this.mDependentListEndpoints, this.serviceFilterParameters);

  factory ServiceConfiguration.fromJson(json) =>
      _$ServiceConfigurationFromJson(json);
}

@JsonSerializable()
class ServiceConfigurationAttribute extends Object
    with _$ServiceConfigurationAttributeSerializerMixin, JsProxy {

  @reflectable
  final String name;

  // TODO

  ServiceConfigurationAttribute(this.name);

  factory ServiceConfigurationAttribute.fromJson(json) =>
      _$ServiceConfigurationAttributeFromJson(json);
}
