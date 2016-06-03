part of sdk_validator.services;

class DatasetService extends BaseService {
  String token;


  DatasetService(http.Client client, Uri coreUrl, this.token)
      : super(client, coreUrl);


  static bool _isExistingImage(String image) {
    if (image == null) return false;
    return image.startsWith('http://') || image.startsWith('https://');
  }

  /// get and clear the attribrute.  Used for file uploads
  static String _getAttribute(DataSetItem item, int attributeIndex) {
    var result = item.valueForAttributeIndex(attributeIndex);
    item.setAttribute('', attributeIndex);
    return result;
  }

  /// Recursively flatten each subItem if it is an attachment
  static List<http.MultipartFile> _flattenAttachments(DataSetItem item) {
    var result = [];
    if (item.subItems.isEmpty) return [];
    for (var subItem in item.subItems) {
      if (subItem.recordTypeString != RecordType.attachment) continue;
      var imageData = _getAttribute(subItem, AttachmentAttributeIndex.image);
      if (imageData != null && imageData.isEmpty && _isExistingImage(imageData)) continue;
      String fileData;
      if (imageData == null || imageData.isEmpty) {
        fileData = _getAttribute(subItem, AttachmentAttributeIndex.file);
      } else {
        fileData = imageData;
      }
      if (fileData == null || fileData.isEmpty) continue;
      var uploadData = imageData ?? fileData;
      var decoded = new String.fromCharCodes(BASE64.decode(uploadData));
      var file = new http.MultipartFile.fromString(subItem.uuid, decoded);
      result.add(file);
      result.addAll(_flattenAttachments(subItem));
    }
    return result;
  }

  Future<DataSetResponse> getDataSet(Uri connectorUri, [Map<String, String> filters]) async {
    var queryParams = filters;
    var uri = connectorUri.replace(queryParameters: queryParams);
    var headers = {'X-AUTH-TOKEN': token};
    var response = await client.get(uri, headers: headers);
    var body = response.body;
    var result = JSON.decode(body);
    var dataSet = new DataSetResponse.fromJson(result);
    return dataSet;
  }

  Future<List<ServiceConfigurationAttribute>> getConfiguration(
      Uri baseUri) async {
    var path = new List.from(baseUri.pathSegments);
    path.add('describe');
    var uri = baseUri.replace(pathSegments: path);
    var headers = {'X-AUTH-TOKEN': token};
    var response = await client.get(uri, headers: headers);
    var json = JSON.decode(response.body);
    var configurationResponse = new DataSetConfigurationResponse.fromJson(json);
    return configurationResponse.attributes;
  }

  Future<List<ListServiceConfigurationAttribute>> getListConfiguration(
      Uri baseUri) async {
    var path = new List.from(baseUri.pathSegments);
    path.add('describe');
    var uri = baseUri.replace(pathSegments: path);
    var headers = {'X-AUTH-TOKEN': token};
    var response = await client.get(uri, headers: headers);
    var json = JSON.decode(response.body);
    var configurationResponse = new ListServiceConfigurationResponse.fromJson(json);
    return configurationResponse.attributes;
  }

  Future<DataSetResponse> sendCreate(Uri baseUrl, DataSetItem item) async {
    var req = new http.MultipartRequest('POST', baseUrl);
    req.files.addAll(_flattenAttachments(item));
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
    req.files.addAll(_flattenAttachments(item));
    req.fields['formJSON'] = JSON.encode(item);
    req.headers['X-AUTH-TOKEN'] = token;
    var response = await client.send(req);
    var responseString = await response.stream.bytesToString();
    var json = JSON.decode(responseString);
    var dataSetResponse = new DataSetResponse.fromJson(json);
    return dataSetResponse;
  }

  Future<DataSetResponse> searchDataSet(
      Uri connectorUri, DataSetItem dataSetItem) async {
    var queryParams = {};
    var pathSegments = new List.from(connectorUri.pathSegments);
    pathSegments.add('search');
    var uri = connectorUri.replace(
        queryParameters: queryParams, pathSegments: pathSegments);

    var headers = {'X-AUTH-TOKEN': token, 'Content-Type': 'application/json',};
    var requestBody = JSON.encode(dataSetItem);
    var response = await client.post(uri, headers: headers, body: requestBody);
    var body = response.body;
    var result = JSON.decode(body);
    var dataSet = new DataSetResponse.fromJson(result);
    return dataSet;
  }

  Future<ListResponse> getList(Uri endpointUri) async {
    var uri = endpointUri.replace(queryParameters: {'json': 'true'});
    var headers = {'X-AUTH-TOKEN': token, 'Content-Type': 'application/json',};
    var response = await client.get(uri, headers: headers);
    var body = response.body;
    var result = JSON.decode(body);
    var dataSet = new ListResponse.fromJson(result);
    return dataSet;
  }

  Future<ListResponse> searchList(Uri endpointUri, SearchListData searchListData) async {
    var pathSegments = new List.from(endpointUri.pathSegments);
    pathSegments.add('search');
    var uri = endpointUri.replace(pathSegments: pathSegments);
    var headers = {'X-AUTH-TOKEN': token, 'Content-Type': 'application/json',};
    var requestBody = JSON.encode(searchListData);
    var response = await client.post(uri, headers: headers, body: requestBody);
    var body = response.body;
    var result = JSON.decode(body);
    var dataSet = new ListResponse.fromJson(result);
    return dataSet;
  }
}

