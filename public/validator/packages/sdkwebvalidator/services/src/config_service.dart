part of sdkwebvalidator.services;

class ConfigService {
  Future<AppConfig> loadConfig(http.Client client) async {
    var response = await client.get('./config.yaml');
    var body = response.body;
    var yamlMap = loadYaml(body);
    return new AppConfig.fromJson(yamlMap);
  }
}
