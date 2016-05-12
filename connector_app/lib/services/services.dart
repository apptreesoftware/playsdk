library connector_app.services;

import 'dart:async';
import 'dart:convert';
import 'package:rxdart/rxdart.dart';
import 'package:yaml/yaml.dart';
import 'package:http/http.dart' as http;
import 'package:connector_app/models/models.dart';

class ConfigService {
  Future<AppConfig> loadConfig(http.Client client) async {
    var response = await client.get('/config.yaml');
    var body = response.body;
    var yamlMap = loadYaml(body);
    return new AppConfig.fromJson(yamlMap);
  }
}

class BaseService {
  http.Client client;
  String baseUrl;

  BaseService(this.client, this.baseUrl);
}

class EndpointService extends BaseService {
  EndpointService(http.Client client, String baseUrl) : super(client, baseUrl);

  Future<List<Endpoint>> getEndpoints() async {
    var response = await client.get('$baseUrl');
    var body = response.body;
    var result = JSON.decode(body);
    var endpointResponse = new EndpointResponse.fromJson(result);
    return endpointResponse.records;
  }
}

class DatasetService extends BaseService {
  DatasetService(http.Client client, String baseUrl) : super(client, baseUrl);

  Future<DataSet> getDataSet(String url) async {
    var response = await client.get('$url');
    var body = response.body;
    var result = JSON.decode(body);
    var dataSet = new DataSet.fromJson(result);
    return dataSet;
  }

  Observable<List<ServiceConfigurationAttribute>> getConfiguration(String baseUrl) {
    return new Observable<http.Response>.fromFuture(client.get('$baseUrl/describe'))
        .map((http.Response response) => JSON.decode(response.body))
        .map((Map json) => new DataSetConfigurationResponse.fromJson(json))
        .map((DataSetConfigurationResponse r) => r.attributes);
  }
}
