@HtmlImport('endpoint_tester.html')
library sdkwebvalidator.at_data_set;

import 'dart:html';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_tabs.dart';
import 'package:polymer_elements/paper_tab.dart';

import 'package:sdkwebvalidator/form/form.dart';
import 'package:sdkwebvalidator/services/services.dart';
import 'package:sdkwebvalidator/models/models.dart';
import 'package:sdkwebvalidator/list/elements/list_element.dart';

enum DisplayType { CreateForm, UpdateForm, ViewData, Search }

@PolymerRegister('at-endpoint-tester')

/// [PaperTab]
/// [PaperTabs]
/// Displays Create, Update, List, and Filter actions for a given list of [ServiceConfigurationAttribute]s
class EndpointTestElement extends PolymerElement {
  DatasetService _datasetService;
  set datasetService(DatasetService service) {
    _datasetService = service;
    _loadDataSet();
  }
  DatasetService get datasetService => _datasetService;

  @Property(observer: "endpointChanged")
  Endpoint endpoint;

  @Property()
  List<ServiceConfigurationAttribute> dataSetAttributes = [];

  @Property(observer: "selectedTabChanged")
  int selectedTabIndex = 0;

  EndpointTestElement.created() : super.created();

  void updateUI() {
    DisplayType displayType = DisplayType.values[selectedTabIndex];
    DivElement container = $$('#content-container');
    container.children.clear();
    Element element;
    switch (displayType) {
      case DisplayType.CreateForm:
        element = new Form(dataSetAttributes, "CREATE");
        break;
      case DisplayType.UpdateForm:
        element = new Form(dataSetAttributes, "UPDATE");
        break;
      case DisplayType.ViewData:
        element = new ListElement(dataSetAttributes, _datasetService, endpoint.url);
        break;
      case DisplayType.Search:
        break;
    }
    if (element != null) {
      element.on['form-submit'].listen((Event event) {
        if (event.target is Form) {
          _sendDataSetItem(event.target);
        }
      });
      container.append(element);
    }
  }

  void _sendDataSetItem(Form form) {
    var formType = form.formType;
    var elementDisplays = form.formElementDisplays;
    var dataSetItem = new DataSetItem.fromFormElementDisplays(elementDisplays);
    dataSetItem.statusString = formType;
    if (formType == "CREATE") {
      _datasetService.sendCreate(endpoint.url, dataSetItem);
    } else if (formType == "UPDATE") {
      _datasetService.sendUpdate(endpoint.url, dataSetItem);
    }
  }

  @reflectable
  void endpointChanged(_, __) {
    _loadDataSet();
  }

  _loadDataSet() {
    if (endpoint == null) return;
    if (_datasetService == null) return;
    _datasetService.getConfiguration(endpoint.url).listen((data) {
      dataSetAttributes = data;
      updateUI();
    }, onError: (error) => print(error));
  }

  @reflectable
  void selectedTabChanged(_, __) {
    updateUI();
  }
}
