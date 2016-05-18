library sdkwebvalidator.mock_endpoint_service;

import 'dart:async';
import 'package:sdkwebvalidator/services/services.dart';
import 'package:sdkwebvalidator/models/models.dart';
import 'dart:convert';
import 'package:sdkwebvalidator/mock/mock_endpoints.dart';

class MockEndpointService implements EndpointService {
  dynamic client;
  Uri coreUrl;

  MockEndpointService() : super();

  Future<List<Endpoint>> getEndpoints() async {
    var json = JSON.decode(mockEndpoints);
    var endpointResponse = new EndpointResponse.fromJson(json);
    return endpointResponse.records;
  }
}