library mock_dataset_service;

import 'dart:async';
import 'package:sdkwebvalidator/services/services.dart';
import 'package:sdkwebvalidator/models/models.dart';
import 'dart:convert';
import 'package:sdkwebvalidator/mock/mock_dataset.dart';
import 'package:rxdart/rxdart.dart';
import 'package:sdkwebvalidator/mock/mock_endpoint_configuration.dart';

class MockDatasetService implements DatasetService {
  String token;
  dynamic client;
  Uri coreUrl;

  MockDatasetService();

  Future<DataSetResponse> getDataSet(Uri connectorUri) async {
    var json = JSON.decode(mockDataset);
    var dataSet = new DataSetResponse.fromJson(json);
    return dataSet;
  }

  Observable<List<ServiceConfigurationAttribute>> getConfiguration(Uri baseUri) {
    var json = JSON.decode(mockEndpointConfig);
    var response = new DataSetConfigurationResponse.fromJson(json);
    var stream = new Stream.fromIterable([response.attributes]);
    return new Observable.fromStream(stream);
  }

  Future<DataSetResponse> sendCreate(Uri baseUrl, DataSetItem item) async {
    return new DataSetResponse(true, '', false, 1, 1, true, []);
  }

  Future<DataSetResponse> sendUpdate(Uri baseUrl, DataSetItem item) async {
    return new DataSetResponse(true, '', false, 1, 1, true, []);
  }
}