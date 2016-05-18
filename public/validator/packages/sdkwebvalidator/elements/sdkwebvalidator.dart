@HtmlImport('sdkwebvalidator.html')
library sdkwebvalidator.at_sdkwebvalidator;

import 'dart:async';

import 'package:http/browser_client.dart';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';

import 'package:polymer_elements/paper_toolbar.dart';
import 'package:polymer_elements/paper_drawer_panel.dart';
import 'package:polymer_elements/paper_header_panel.dart';
import 'package:polymer_elements/paper_menu_button.dart';
import 'package:polymer_elements/paper_menu.dart';
import 'package:polymer_elements/paper_item.dart';
import 'package:polymer_elements/paper_button.dart';

import 'package:sdkwebvalidator/models/models.dart';
import 'package:sdkwebvalidator/services/services.dart';
import 'package:sdkwebvalidator/elements/endpoints.dart';
import 'package:sdkwebvalidator/elements/endpoint_tester.dart';
import 'package:sdkwebvalidator/mock/mock_dataset_service.dart';
import 'package:sdkwebvalidator/mock/mock_endpoint_service.dart';

/// [PaperToolbar]
/// [PaperDrawerPanel]
/// [PaperHeaderPanel]
/// [EndpointsElement]
/// [EndpointTestElement]
/// [PaperMenuButton]
@PolymerRegister('sdk-web-validator')
class ConnectorAppElement extends PolymerElement {

  BrowserClient _client;

  EndpointService endpointService;

  @Property(reflectToAttribute: true)
  bool useMock = false;

  @Property()
  List<Endpoint> endpoints;

  @Property()
  Endpoint selectedEndpoint;

  @property ValidatorSettings settings;

  AppConfig _config;

  ConnectorAppElement.created() : super.created();

  ready() async {

    _client = new BrowserClient();
    var configService = new ConfigService();
    _config = await configService.loadConfig(_client);

    var settings = new ValidatorSettings(_config.coreHost.toString(), _config.usernameString);
    set('settings', settings);

    await _reloadEndpointService();

    var token = await _loadToken();
    _reloadDatasetService(token);
  }

  Future _reloadEndpointService() async {
    if (useMock) {
      endpointService = new MockEndpointService();
    } else {
      endpointService = new EndpointService(_client, _config.coreHost);
    }
    var endpoints = await endpointService.getEndpoints();
    set('endpoints', endpoints);
  }

  _reloadDatasetService(String token) {
    // It is not possible to inject a DatasetService unless it implements JsProxy
    // querying the AtEndpoint element directly allows the service to be set without
    // using a template, which means we don't need to use the JsProxy annotation
    // on the service.
    //
    // Using a singleton for the services might make the most sense.  This would
    // allow different elements to use the same services.
    DatasetService datasetService;
    if (useMock) {
      datasetService = new MockDatasetService();
    } else {
      datasetService = new DatasetService(_client, settings.hostUri, token);
    }
    EndpointTestElement dataSetElement = $$('#endpoint');
    dataSetElement.datasetService = datasetService;
  }

  @reflectable
  handleSelectedEndpointChanged(event, Endpoint detail) {
    set('selectedEndpoint', detail);
  }

  @reflectable
  saveSettings(_, __) async {
    if (_config == null) return;
    var token = await _loadToken();
    _reloadEndpointService();
    _reloadDatasetService(token);
  }

  Future<String> _loadToken() async {
    var service = new AuthService(new BrowserClient(), settings.hostUri);
    return await service.getToken(settings.username);
  }
}


class ValidatorSettings extends JsProxy {
  @reflectable
  String host;

  @reflectable
  String username;

  Uri get hostUri => Uri.parse(host);

  ValidatorSettings(this.host, this.username);
}