// GENERATED CODE - DO NOT MODIFY BY HAND

part of sdkwebvalidator.models;

// **************************************************************************
// Generator: JsonSerializableGenerator
// Target: class AppConfig
// **************************************************************************

AppConfig _$AppConfigFromJson(Map json) =>
    new AppConfig(json['core_host'], json['username']);

abstract class _$AppConfigSerializerMixin {
  String get coreHostString;
  String get usernameString;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'core_host': coreHostString,
        'username': usernameString
      };
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
  String get urlString;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'endpoint': endpoint,
        'name': name,
        'data': data,
        'url': urlString
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
        json['showMessageAsAlert'],
        json['authorizationError'],
        json['name'],
        (json['attributes'] as List)
            ?.map((v0) => v0 == null
                ? null
                : new ServiceConfigurationAttribute.fromJson(v0))
            ?.toList(),
        (json['dependentLists'] as List)?.map((v0) => v0)?.toList());

abstract class _$DataSetConfigurationResponseSerializerMixin {
  bool get success;
  String get message;
  bool get showMessageAsAlert;
  bool get authorizationError;
  String get name;
  List get attributes;
  List get dependentLists;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'success': success,
        'message': message,
        'showMessageAsAlert': showMessageAsAlert,
        'authorizationError': authorizationError,
        'name': name,
        'attributes': attributes,
        'dependentLists': dependentLists
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
            : new RelatedServiceConfiguration.fromJson(json['relatedService']),
        json['attributeType'],
        json['createRequired'],
        json['updateRequired'],
        json['searchRequired'],
        json['attributeIndex'],
        json['canCreate'],
        json['canUpdate'],
        json['canSearch'],
        json['relatedListServiceConfiguration'] == null
            ? null
            : new ListServiceConfiguration.fromJson(
                json['relatedListServiceConfiguration']));

abstract class _$ServiceConfigurationAttributeSerializerMixin {
  String get name;
  RelatedServiceConfiguration get relatedService;
  String get attributeType;
  bool get createRequired;
  bool get updateRequired;
  bool get searchRequired;
  int get attributeIndex;
  bool get canCreate;
  bool get canUpdate;
  bool get canSearch;
  ListServiceConfiguration get relatedListServiceConfiguration;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'name': name,
        'relatedService': relatedService,
        'attributeType': attributeType,
        'createRequired': createRequired,
        'updateRequired': updateRequired,
        'searchRequired': searchRequired,
        'attributeIndex': attributeIndex,
        'canCreate': canCreate,
        'canUpdate': canUpdate,
        'canSearch': canSearch,
        'relatedListServiceConfiguration': relatedListServiceConfiguration
      };
}

// **************************************************************************
// Generator: JsonSerializableGenerator
// Target: class RelatedServiceConfiguration
// **************************************************************************

RelatedServiceConfiguration _$RelatedServiceConfigurationFromJson(Map json) =>
    new RelatedServiceConfiguration(
        json['success'],
        json['name'],
        json['message'],
        json['showMessageAsAlert'],
        (json['attributes'] as List)
            ?.map((v0) => v0 == null
                ? null
                : new ServiceConfigurationAttribute.fromJson(v0))
            ?.toList(),
        json['attributeConfigurationForIndexMap'],
        (json['dependentLists'] as List)?.map((v0) => v0)?.toList());

abstract class _$RelatedServiceConfigurationSerializerMixin {
  bool get success;
  String get message;
  bool get showMessageAsAlert;
  String get name;
  List get attributes;
  Map get attributeConfigurationForIndexMap;
  List get dependentLists;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'success': success,
        'message': message,
        'showMessageAsAlert': showMessageAsAlert,
        'name': name,
        'attributes': attributes,
        'attributeConfigurationForIndexMap': attributeConfigurationForIndexMap,
        'dependentLists': dependentLists
      };
}

// **************************************************************************
// Generator: JsonSerializableGenerator
// Target: class ListServiceConfiguration
// **************************************************************************

ListServiceConfiguration _$ListServiceConfigurationFromJson(Map json) =>
    new ListServiceConfiguration(
        json['listName'],
        json['includesLocation'],
        json['authenticationRequired'],
        json['userIDIndex'],
        json['canCache'],
        json['canSearch'],
        (json['attributes'] as List)
            ?.map((v0) => v0 == null
                ? null
                : new ListServiceConfigurationAttribute.fromJson(v0))
            ?.toList())
      ..serviceFilterParameters = (json['serviceFilterParameters'] as List)
          ?.map((v0) => v0 == null ? null : new ServiceParameter.fromJson(v0))
          ?.toList();

abstract class _$ListServiceConfigurationSerializerMixin {
  String get listName;
  bool get includesLocation;
  bool get authenticationRequired;
  int get userIDIndex;
  bool get canCache;
  bool get canSearch;
  List get attributes;
  List get serviceFilterParameters;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'listName': listName,
        'includesLocation': includesLocation,
        'authenticationRequired': authenticationRequired,
        'userIDIndex': userIDIndex,
        'canCache': canCache,
        'canSearch': canSearch,
        'attributes': attributes,
        'serviceFilterParameters': serviceFilterParameters
      };
}

// **************************************************************************
// Generator: JsonSerializableGenerator
// Target: class ListServiceConfigurationAttribute
// **************************************************************************

ListServiceConfigurationAttribute _$ListServiceConfigurationAttributeFromJson(
        Map json) =>
    new ListServiceConfigurationAttribute(json['attributeIndex'], json['label'],
        json['attributeType'], json['relatedListConfiguration']);

abstract class _$ListServiceConfigurationAttributeSerializerMixin {
  int get attributeIndex;
  String get label;
  String get attributeType;
  String get relatedListConfiguration;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'attributeIndex': attributeIndex,
        'label': label,
        'attributeType': attributeType,
        'relatedListConfiguration': relatedListConfiguration
      };
}

// **************************************************************************
// Generator: JsonSerializableGenerator
// Target: class ServiceParameter
// **************************************************************************

ServiceParameter _$ServiceParameterFromJson(Map json) => new ServiceParameter(
    json['key'],
    json['type'],
    (json['possibleValues'] as List)?.map((v0) => v0)?.toList(),
    json['availableInQueryBuilder'],
    json['required']);

abstract class _$ServiceParameterSerializerMixin {
  String get key;
  String get type;
  List get possibleValues;
  bool get availableInQueryBuilder;
  bool get required;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'key': key,
        'type': type,
        'possibleValues': possibleValues,
        'availableInQueryBuilder': availableInQueryBuilder,
        'required': required
      };
}
