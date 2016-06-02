library sdk_validator.app_context;

import 'dart:async';
import 'package:polymer/polymer.dart' show JsProxy;
import 'package:http/http.dart' as http;
import 'package:http/browser_client.dart';
import 'package:route_hierarchical/client.dart';

import 'package:sdk_validator/services.dart';
import 'package:sdk_validator/mock_services.dart';
import 'package:sdk_validator/view_models.dart';
import 'package:sdk_validator/model.dart';

class AppContext extends JsProxy {
  ConfigService configService;
  AuthService authService;
  EndpointService endpointService;
  DatasetService datasetService;
  Router router;
  AppConfig appConfig;

  http.Client _client;
  String _authToken;

  AppContext() {
    _client = new BrowserClient();
    router = new Router(useFragment: true);
  }

  Future init() async {
    configService = new ConfigService(_client);
    appConfig = await configService.loadConfig();

    if (appConfig.mock) {
      initMock();
      router.listen();
      return;
    }

    await recreateServices();
    router.listen();
  }

  initMock() async {
    endpointService = new MockEndpointService();
    datasetService = new MockDatasetService();
  }

  Future recreateServices() async {
    authService = new AuthService(_client, appConfig.coreHost);
    endpointService = new EndpointService(_client, appConfig.coreHost);
    _authToken = await authService.getToken(appConfig.usernameString);
    datasetService = new DatasetService(_client, appConfig.coreHost, _authToken);
  }
}
