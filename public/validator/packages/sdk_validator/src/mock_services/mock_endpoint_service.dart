part of sdk_validator.mock_services;

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
