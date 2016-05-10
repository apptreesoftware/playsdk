// GENERATED CODE - DO NOT MODIFY BY HAND

part of connector_app.models;

// **************************************************************************
// Generator: JsonSerializableGenerator
// Target: class AppConfig
// **************************************************************************

AppConfig _$AppConfigFromJson(Map json) =>
    new AppConfig(json['connector_host']);

abstract class _$AppConfigSerializerMixin {
  String get connectorHost;
  Map<String, dynamic> toJson() =>
      <String, dynamic>{'connector_host': connectorHost};
}

// **************************************************************************
// Generator: JsonSerializableGenerator
// Target: class EndpointResponse
// **************************************************************************

EndpointResponse _$EndpointResponseFromJson(Map json) => new EndpointResponse(
    json['success'],
    (json['records'] as List)
        ?.map((v0) => v0 == null ? null : new Endpoint.fromJson(v0))
        ?.toList());

abstract class _$EndpointResponseSerializerMixin {
  bool get success;
  List get records;
  Map<String, dynamic> toJson() =>
      <String, dynamic>{'success': success, 'records': records};
}

// **************************************************************************
// Generator: JsonSerializableGenerator
// Target: class Endpoint
// **************************************************************************

Endpoint _$EndpointFromJson(Map json) =>
    new Endpoint(json['endpoint'], json['name'], json['data'], json['url']);

abstract class _$EndpointSerializerMixin {
  String get endpoint;
  String get name;
  String get data;
  String get url;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'endpoint': endpoint,
        'name': name,
        'data': data,
        'url': url
      };
}

// **************************************************************************
// Generator: JsonSerializableGenerator
// Target: class DataSetResponse
// **************************************************************************

DataSetResponse _$DataSetResponseFromJson(Map json) => new DataSetResponse(
    json['success'],
    json['message'],
    json['showMessageAlert'],
    json['authorizationError'],
    json['records'] == null ? null : new DataSet.fromJson(json['records']));

abstract class _$DataSetResponseSerializerMixin {
  bool get success;
  String get message;
  bool get showMessageAlert;
  bool get authorizationError;
  DataSet get dataSet;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'success': success,
        'message': message,
        'showMessageAlert': showMessageAlert,
        'authorizationError': authorizationError,
        'records': dataSet
      };
}

// **************************************************************************
// Generator: JsonSerializableGenerator
// Target: class DataSet
// **************************************************************************

DataSet _$DataSetFromJson(Map json) => new DataSet(
    json['totalRecords'],
    json['numberOfRecords'],
    json['moreRecordsAvailable'],
    (json['records'] as List)
        ?.map((v0) => v0 == null ? null : new DataSetItem.fromJson(v0))
        ?.toList());

abstract class _$DataSetSerializerMixin {
  int get totalRecords;
  int get numberOfRecords;
  bool get moreRecordsAvailable;
  List get dataSetItems;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'totalRecords': totalRecords,
        'numberOfRecords': numberOfRecords,
        'moreRecordsAvailable': moreRecordsAvailable,
        'records': dataSetItems
      };
}

// **************************************************************************
// Generator: JsonSerializableGenerator
// Target: class DataSetItem
// **************************************************************************

DataSetItem _$DataSetItemFromJson(Map json) => new DataSetItem(
    json['primaryKey'],
    json['CRUDStatus'],
    json['clientKey'],
    json['recordType'],
    json['DataCollectionStatus'],
    json['workFlowState'],
    (json['attributes'] as List)?.map((v0) => v0)?.toList());

abstract class _$DataSetItemSerializerMixin {
  String get primaryKey;
  String get crudStatus;
  String get clientKey;
  String get recordType;
  String get dataCollectionStatus;
  String get workFlowState;
  List get attributes;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'primaryKey': primaryKey,
        'CRUDStatus': crudStatus,
        'clientKey': clientKey,
        'recordType': recordType,
        'DataCollectionStatus': dataCollectionStatus,
        'workFlowState': workFlowState,
        'attributes': attributes
      };
}
