@HtmlImport('at_endpoint.html')
library sdk_validator.components.at_endpoint;

import 'dart:html';
import 'dart:async';

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:sdk_validator/view_models.dart';
import 'package:route_hierarchical/client.dart';

import 'package:polymer_elements/paper_tabs.dart';
import 'package:polymer_elements/paper_tab.dart';

import 'package:sdk_validator/app_context.dart';
import 'at_dataset_form.dart';
import 'at_dataset_view.dart';
import 'at_dataset_search.dart';
import 'package:sdk_validator/model.dart';
import 'package:sdk_validator/components/at_list_view.dart';
import 'package:sdk_validator/components/at_list_search.dart';
import 'package:sdk_validator/controllers/form_controller.dart';

enum DisplayType { create, update, view, search }

@PolymerRegister('at-endpoint')
class AtEndpoint extends PolymerElement implements Routable {
  @Property()
  AppContext appContext;

  @Property(observer: 'selectedEndpointChanged')
  EndpointDisplay endpoint;

  @Property(observer: "selectedTabChanged")
  int selectedTabIndex;

  List<ServiceConfigurationAttribute> _serviceConfigurationAttributes;
  List<ListServiceConfigurationAttribute> _listServiceConfigurationAttributes;

  Route _route;
  PolymerElement _formElement;
  DataSetItemDisplay _dataSetItemForUpdate;

  AtEndpoint.created() : super.created();

  void configureRoute(Route route) {
    _route = route;
    _route.addRoute(
        name: 'method',
        path: '/:methodName',
        enter: _methodChangedByUrl);
  }

  @reflectable
  selectedEndpointChanged(_, __) {
    _loadServiceConfigurationAttributes();
  }

  @reflectable
  void selectedTabChanged(_, __) {
    _changeRouterUrlToCurrentTab();
    updateUI();
  }

  void updateUI() {
    if (_formElement != null) {
      unlisten(_formElement, 'edit-list-item', 'handleListItemEdit');
    }

    var container = $$('#form-container') as DivElement;
    container.children.clear();

    if (selectedTabIndex == null) return;
    var displayType = DisplayType.values[selectedTabIndex];

    if (_serviceConfigurationAttributes == null && _listServiceConfigurationAttributes == null) {
      var loadingElem = new ParagraphElement()..text = 'loading...';
      container.append(loadingElem);
      return;
    }

    switch (displayType) {
      case DisplayType.create:
        var controller = new FormController(appContext, _serviceConfigurationAttributes,
            FormType.create, endpoint.wrapped);
        _formElement = new AtDatasetForm(controller);
        break;
      case DisplayType.update:
        if (_dataSetItemForUpdate == null) return;
        var controller = new FormController(appContext, _serviceConfigurationAttributes,
            FormType.create, endpoint.wrapped, itemToUpdate: _dataSetItemForUpdate.wrapped);
        _formElement = new AtDatasetForm(controller);
        break;
      case DisplayType.view:
        if (endpoint.type == 'data') {
          _formElement = new AtDatasetView(
              appContext, _serviceConfigurationAttributes, endpoint);
        } else if (endpoint.type == 'list') {
          _formElement = new AtListView(
              appContext, _listServiceConfigurationAttributes, endpoint);
        }

        // might be cleaner if done using the @Listen annotation
        listen(_formElement, 'edit-list-item', 'handleListItemEdit');
        break;
      case DisplayType.search:
        if (endpoint.type == 'data') {
          _formElement = new AtDatasetSearch(
              appContext, _serviceConfigurationAttributes, endpoint.wrapped);
        } else if (endpoint.type == 'list') {
          _formElement = new AtListSearch(
              appContext, _listServiceConfigurationAttributes, endpoint);
        }

        listen(_formElement, 'edit-list-item', 'handleListItemEdit');
        break;
    }
    container.append(_formElement);
  }

  @reflectable
  handleListItemEdit(event, detail) {
    if (detail is DataSetItemDisplay) {
      _dataSetItemForUpdate = detail;
      var path = _displayTypeToPath(DisplayType.update);
      appContext.router.go('method', {'methodName': path}, startingFrom: _route);
    }
  }

  Future _loadServiceConfigurationAttributes() async {
    if (endpoint == null) return;
    if (endpoint.type == 'data') {
      var response =
      await appContext.datasetService.getConfiguration(endpoint.url);
      _serviceConfigurationAttributes = response;
    } else if (endpoint.type == 'list') {
      var response =
      await appContext.datasetService.getListConfiguration(endpoint.url);
      _listServiceConfigurationAttributes = response;
    }
    updateUI();
  }

  void  _methodChangedByUrl(RouteEvent event) {
    var method = (event.parameters["methodName"] as String).toLowerCase();
    var displayType = _pathToDisplayType(method);
    set('selectedTabIndex', displayType.index);
    updateUI();
  }

  void _changeRouterUrlToCurrentTab() {
    if (appContext == null) return;
    var displayType = DisplayType.values[selectedTabIndex];
    String routerPath = _displayTypeToPath(displayType);
    appContext.router.go('method', {'methodName': routerPath}, startingFrom: _route);
  }
}

String _displayTypeToPath(DisplayType dt) {
  switch(dt) {
    case DisplayType.create:
      return 'create';
    case DisplayType.update:
      return 'update';
    case DisplayType.view:
      return 'view';
    case DisplayType.search:
      return 'search';
  }
}

DisplayType _pathToDisplayType(String path) {
  switch(path) {
    case 'create':
      return DisplayType.create;
    case 'update':
      return DisplayType.update;
    case 'view':
      return DisplayType.view;
    case 'search':
      return DisplayType.search;
    default:
      return DisplayType.create;
  }
}
