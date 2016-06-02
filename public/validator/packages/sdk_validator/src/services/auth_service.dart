part of sdk_validator.services;

class AuthService extends BaseService {
  AuthService(http.Client client, Uri coreUrl) : super(client, coreUrl);

  Future<String> getToken(String username) async {
    var uri = coreUrl.replace(pathSegments: ['auth', 'login']);
    var headers = {'Content-Type': 'application/json'};
    var body = {'username': username};
    var response =
        await client.post(uri, headers: headers, body: JSON.encode(body));
    var responseJson = JSON.decode(response.body);
    return responseJson['token'];
  }
}
