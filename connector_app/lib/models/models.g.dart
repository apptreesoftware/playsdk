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
// Target: class DataSet
// **************************************************************************

DataSet _$DataSetFromJson(Map json) => new DataSet(
    json['success'],
    json['message'],
    json['showMessageAlert'],
    json['totalRecords'],
    json['numberOfRecords'],
    json['moreRecordsAvailable'],
    (json['records'] as List)
        ?.map((v0) => v0 == null ? null : new DataSetItem.fromJson(v0))
        ?.toList());

abstract class _$DataSetSerializerMixin {
  bool get success;
  String get message;
  bool get showMessageAlert;
  int get totalRecords;
  int get numberOfRecords;
  bool get moreRecordsAvailable;
  List get dataSetItems;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'success': success,
        'message': message,
        'showMessageAlert': showMessageAlert,
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

// **************************************************************************
// Generator: JsonSerializableGenerator
// Target: class DataSetConfigurationResponse
// **************************************************************************

DataSetConfigurationResponse _$DataSetConfigurationResponseFromJson(Map json) =>
    new DataSetConfigurationResponse(
        json['success'],
        json['message'],
        json['showMessageAlert'],
        json['authorizationError'],
        (json['attributes'] as List)
            ?.map((v0) => v0 == null
                ? null
                : new ServiceConfigurationAttribute.fromJson(v0))
            ?.toList());

abstract class _$DataSetConfigurationResponseSerializerMixin {
  bool get success;
  String get message;
  bool get showMessageAlert;
  bool get authorizationError;
  List get attributes;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'success': success,
        'message': message,
        'showMessageAlert': showMessageAlert,
        'authorizationError': authorizationError,
        'attributes': attributes
      };
}

// **************************************************************************
// Generator: JsonSerializableGenerator
// Target: class ServiceConfigurationAttribute
// **************************************************************************

ServiceConfigurationAttribute _$ServiceConfigurationAttributeFromJson(
        Map json) =>
    new ServiceConfigurationAttribute(
        json['name'],
        json['relatedService'] == null
            ? null
            : new ServiceConfiguration.fromJson(json['relatedService']),
        json['attributeType'],
        json['createRequired'],
        json['updateRequired'],
        json['searchRequired'],
        json['attributeIndex'],
        json['canUpdate'],
        json['canCreate'],
        json['relatedListServiceConfiguration'],
        json['canSearch']);

abstract class _$ServiceConfigurationAttributeSerializerMixin {
  String get name;
  ServiceConfiguration get relatedService;
  String get attributeType;
  bool get createRequired;
  bool get updateRequired;
  bool get searchRequired;
  int get attributeIndex;
  bool get canUpdate;
  bool get canCreate;
  Map get relatedListServiceConfiguration;
  bool get canSearch;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'name': name,
        'relatedService': relatedService,
        'attributeType': attributeType,
        'createRequired': createRequired,
        'updateRequired': updateRequired,
        'searchRequired': searchRequired,
        'attributeIndex': attributeIndex,
        'canUpdate': canUpdate,
        'canCreate': canCreate,
        'relatedListServiceConfiguration': relatedListServiceConfiguration,
        'canSearch': canSearch
      };
}

// **************************************************************************
// Generator: JsonSerializableGenerator
// Target: class ServiceConfiguration
// **************************************************************************

ServiceConfiguration _$ServiceConfigurationFromJson(Map json) =>
    new ServiceConfiguration(
        json['configurationName'],
        (json['attributes'] as List)
            ?.map((v0) => v0 == null
                ? null
                : new ServiceConfigurationAttribute.fromJson(v0))
            ?.toList(),
        (json['mDependentListEndpoints'] as List)?.map((v0) => v0)?.toList(),
        (json['serviceFilterParameters'] as List)?.map((v0) => v0)?.toList());

abstract class _$ServiceConfigurationSerializerMixin {
  String get configurationName;
  List get attributes;
  List get mDependentListEndpoints;
  List get serviceFilterParameters;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'configurationName': configurationName,
        'attributes': attributes,
        'mDependentListEndpoints': mDependentListEndpoints,
        'serviceFilterParameters': serviceFilterParameters
      };
}
