part of sdk_validator.services;

class ConfigService {
  http.Client _client;
  ConfigService(this._client);

  Future<AppConfig> loadConfig() async {
    var response = await _client.get('./config.yaml');
    var body = response.body;
    var yamlMap = loadYaml(body);
    return new AppConfig.fromJson(yamlMap);
  }
}
