@HtmlImport('at_connector_app.html')
library connector_app.at_connector_app;

import 'package:http/browser_client.dart';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';

import 'package:polymer_elements/paper_drawer_panel.dart';
import 'package:polymer_elements/paper_header_panel.dart';

import 'package:connector_app/models/models.dart';
import 'package:connector_app/services/services.dart';
import 'package:connector_app/components/at_endpoints.dart';
import 'package:connector_app/components/at_data_set.dart';

/// [PaperDrawerPanel]
/// [PaperHeaderPanel]
/// [EndpointsElement]
/// [EndpointTestingElement]
@PolymerRegister('at-connector-app')
class ConnectorAppElement extends PolymerElement {

  EndpointService endpointService;

  @Property()
  List<Endpoint> endpoints;

  @Property()
  Endpoint selectedEndpoint;

  ConnectorAppElement.created() : super.created();

  ready() async {
    var browserClient = new BrowserClient();
    var configService = new ConfigService();
    var config = await configService.loadConfig(browserClient);
    endpointService = new EndpointService(browserClient, config.connectorHost);
    var endpoints = await endpointService.getEndpoints();
    set('endpoints', endpoints);


    // It is not possible to inject a DatasetService unless it implements JsProxy
    // querying the AtEndpoint element directly allows the service to be set without
    // using a template, which means we don't need to use the JsProxy annotation
    // on the service.
    //
    // Using a singleton for the services might make the most sense.  This would
    // allow different components to use the same services.
    var datasetService = new DatasetService(browserClient, config.connectorHost);
    EndpointTestingElement dataSetElement = $$('#endpoint');
    dataSetElement.datasetService = datasetService;

  }

  @reflectable
  handleSelectedEndpointChanged(event, Endpoint detail) {
    set('selectedEndpoint', detail);
  }
}
