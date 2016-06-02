part of sdk_validator.mock_services;

class MockDatasetService implements DatasetService {
  String token;
  dynamic client;
  Uri coreUrl;

  MockDatasetService();

  Future<DataSetResponse> getDataSet(Uri connectorUri, [Map<String, String> filters]) async {
    var json = JSON.decode(mockDataset);
    var dataSet = new DataSetResponse.fromJson(json);
    return dataSet;
  }

  Future<List<ServiceConfigurationAttribute>> getConfiguration(
      Uri baseUri) async {
    var json = JSON.decode(mockEndpointConfig);
    var response = new DataSetConfigurationResponse.fromJson(json);
    return response.attributes;
  }

  Future<List<ListServiceConfigurationAttribute>> getListConfiguration(
      Uri baseUri) async {
    var json = JSON.decode(mockListConfiguration);
    var response = new ListServiceConfigurationResponse.fromJson(json);
    return response.attributes;
  }

  Future<DataSetResponse> sendCreate(Uri baseUrl, DataSetItem item) async {
    print('mock create to $baseUrl');
    print(JSON.encode(item));
    return new DataSetResponse(true, '', false, 1, 1, true, []);
  }

  Future<DataSetResponse> sendUpdate(Uri baseUrl, DataSetItem item) async {
    print('mock update to $baseUrl');
    print(JSON.encode(item));
    return new DataSetResponse(true, '', false, 1, 1, true, []);
  }

  Future<DataSetResponse> searchDataSet(Uri baseUrl, DataSetItem item) async {
    print('mock update to $baseUrl');
    print(JSON.encode(item));
    return new DataSetResponse(true, '', false, 1, 1, true, []);
  }

  Future<ListResponse> getList(Uri connectorUri) async {
    var json = JSON.decode('{}');
    var dataSet = new DataSetResponse.fromJson(json);
    return dataSet;
  }

  Future<ListResponse> searchList(Uri endpointUri, SearchListData searchListData) async {
    var json = JSON.decode('{}');
    var dataSet = new DataSetResponse.fromJson(json);
    return dataSet;
  }
}
