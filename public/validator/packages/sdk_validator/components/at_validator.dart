@HtmlImport('at_validator.html')
library sdk_validator.components.at_validator;

import 'dart:async';
import 'dart:html';

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:route_hierarchical/client.dart';

import 'package:polymer_elements/paper_drawer_panel.dart';
import 'package:polymer_elements/paper_header_panel.dart';
import 'package:polymer_elements/paper_toolbar.dart';
import 'package:polymer_elements/paper_menu_button.dart';
import 'package:polymer_elements/paper_icon_button.dart';
import 'package:polymer_elements/paper_button.dart';
import 'package:polymer_elements/iron_icons.dart';
import 'package:polymer_elements/iron_flex_layout.dart';

import 'package:sdk_validator/app_context.dart';
import 'package:sdk_validator/view_models.dart';
import 'at_settings_button.dart';
import 'at_endpoint_list.dart';
import 'at_endpoint.dart';

@PolymerRegister('at-validator')
class AtValidator extends PolymerElement implements Routable {
  @property
  AppContext appContext;

  @Property()
  List<EndpointDisplay> endpoints;

  /// one-way binding to <at-endpoint-list>
  @Property()
  EndpointDisplay selectedEndpoint;

  Route _route;
  String _endpointPathToDisplay;

  AtValidator.created() : super.created();

  ready() async {
    appContext = new AppContext();

    // the router should be configured before appContext.init()
    // since that's when router.listen() is called.
    appContext.router.root
      ..addRoute(
          name: 'home',
          defaultRoute: true,
          path: '/',
          enter: showHome,
          mount: this);

    await appContext.init();

    notifyPath('appContext', appContext);
    await _loadEndpoints();
  }

  void showHome(RouteEvent e) {
    appContext.router.go('home', {});
  }

  void configureRoute(Route route) {
    var endpointElement = $$('#endpoint') as AtEndpoint;
    _route = route;
    _route.addRoute(
        name: 'endpoint',
        path: 'endpoint/:endpointId',
        enter: endpointChangedByUrl,
        mount: endpointElement);
  }

  void endpointChangedByUrl(RouteEvent event) {
    var route = event.parameters["endpointId"] as String;
    // convert the router-friendly path to the original path
    var slashPath = route.replaceAll('-', '/');

    if (endpoints == null) {
      // set a temporary string to select when the data is loaded
      _endpointPathToDisplay = slashPath;
    } else {
      // find the endpoint that matches this path
      var selected = endpoints.firstWhere((e) => e.endpoint == slashPath,
          orElse: () => null);
      set('selectedEndpoint', selected);
    }
  }

  @reflectable
  void endpointSelectedByUser(event, EndpointDisplay display) {
    if (display == null) return;

    // convert the path into a router-friendly path
    var endpointPath = display.endpoint;
    var endpointUrlPath = endpointPath.replaceAll('/', '-');
    // all state changes that are reflected in the router need to go through
    // the router.
    appContext.router
        .go('endpoint', {'endpointId': endpointUrlPath}, startingFrom: _route);
  }

  @reflectable
  Future settingsSaved(event, detail) async {
    await appContext.recreateServices();
    _loadEndpoints();
  }

  Future _loadEndpoints() async {
    var loadedEndpoints = await appContext.endpointService.getEndpoints();
    var endpointDisplays = loadedEndpoints.map((e) => new EndpointDisplay(e));
    set('endpoints', endpointDisplays);

    // When the data is loaded, set the selected endpoint manually
    if (_endpointPathToDisplay != null) {
      var selected = endpoints.firstWhere((e) => e.endpoint == _endpointPathToDisplay,
          orElse: () => null);
      if (selected != null) {
        set('selectedEndpoint', selected);
        _endpointPathToDisplay = null;
      }
    }
  }
}
