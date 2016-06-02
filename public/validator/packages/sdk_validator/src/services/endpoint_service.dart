part of sdk_validator.services;

class EndpointService extends BaseService {
  EndpointService(http.Client client, Uri coreUrl) : super(client, coreUrl);

  Future<List<Endpoint>> getEndpoints() async {
    var response = await client.get(coreUrl);
    var json = JSON.decode(response.body);
    var endpointResponse = new EndpointResponse.fromJson(json);
    return endpointResponse.records;
  }
}
