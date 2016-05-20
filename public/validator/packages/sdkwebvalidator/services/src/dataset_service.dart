part of sdkwebvalidator.services;

class DatasetService extends BaseService {
  String token;

  DatasetService(http.Client client, Uri coreUrl, this.token) : super(client, coreUrl);

  Future<DataSetResponse> getDataSet(Uri connectorUri) async {
    var queryParams = {'MINE': 'true'};
    var uri = connectorUri.replace(queryParameters: queryParams);
    var headers = {
      'X-AUTH-TOKEN': token
    };
    var response = await client.get(uri, headers: headers);
    var body = response.body;
    var result = JSON.decode(body);
    var dataSet = new DataSetResponse.fromJson(result);
    return dataSet;
  }

  Observable<List<ServiceConfigurationAttribute>> getConfiguration(Uri baseUri) {
    var path = new List.from(baseUri.pathSegments);
    path.add('describe');
    var uri = baseUri.replace(pathSegments: path);
    var headers = {
      'X-AUTH-TOKEN': token
    };
    var responseFuture = client.get(uri, headers: headers);
    return new Observable<http.Response>.fromFuture(responseFuture)
        .map((http.Response response) => JSON.decode(response.body))
        .map((Map json) => new DataSetConfigurationResponse.fromJson(json))
        .map((DataSetConfigurationResponse r) => r.attributes);
  }

  Future<DataSetResponse> sendCreate(Uri baseUrl, DataSetItem item) async {
    var req = new http.MultipartRequest('POST', baseUrl);
    req.fields['formJSON'] = JSON.encode(item);
    req.headers['X-AUTH-TOKEN'] = token;
    var response = await client.send(req);
    var responseString = await response.stream.bytesToString();
    var json = JSON.decode(responseString);
    var dataSetResponse = new DataSetResponse.fromJson(json);
    return dataSetResponse;
  }

  Future<DataSetResponse> sendUpdate(Uri baseUrl, DataSetItem item) async {
    var req = new http.MultipartRequest('PUT', baseUrl);
    req.fields['formJSON'] = JSON.encode(item);
    req.headers['X-AUTH-TOKEN'] = token;
    var response = await client.send(req);
    var responseString = await response.stream.bytesToString();
    var json = JSON.decode(responseString);
    var dataSetResponse = new DataSetResponse.fromJson(json);
    return dataSetResponse;
  }

  Future<DataSetResponse> searchDataSet(Uri connectorUri, DataSetItem dataSetItem) async {
    var queryParams = {};
    var pathSegments = new List.from(connectorUri.pathSegments);
    pathSegments.add('search');
    var uri = connectorUri.replace(queryParameters: queryParams, pathSegments: pathSegments);

    var headers = {
      'X-AUTH-TOKEN': token,
      'Content-Type': 'application/json',
    };
    var requestBody = JSON.encode(dataSetItem);
    var response = await client.post(uri, headers: headers, body: requestBody);
    var body = response.body;
    var result = JSON.decode(body);
    var dataSet = new DataSetResponse.fromJson(result);
    return dataSet;
  }
}
