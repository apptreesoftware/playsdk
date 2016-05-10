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
class DataSetResponse extends Object with _$DataSetResponseSerializerMixin, JsProxy {

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

  DataSetResponse(this.success, this.message, this.showMessageAlert, this.authorizationError, this.dataSet);
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
  List<DataSetItem> dataSetItems;

  DataSet(this.totalRecords, this.numberOfRecords, this.moreRecordsAvailable, this.dataSetItems);
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
  List<String> attributes;

  DataSetItem(this.primaryKey, this.crudStatus, this.clientKey, this.recordType, this.dataCollectionStatus, this.workFlowState, this.attributes);
  factory DataSetItem.fromJson(json) => _$DataSetItemFromJson(json);
}
