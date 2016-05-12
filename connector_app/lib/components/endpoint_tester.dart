@HtmlImport('endpoint_tester.html')
library connector_app.at_data_set;

import 'dart:html';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_tabs.dart';
import 'package:polymer_elements/paper_tab.dart';

import 'package:connector_app/form/form.dart';
import 'package:connector_app/services/services.dart';
import 'package:connector_app/models/models.dart';

enum DisplayType { CreateForm, UpdateForm, ViewData, Search }

/// [PaperTab]
/// [PaperTabs]
@PolymerRegister('at-endpoint-tester')
class EndpointTestElement extends PolymerElement {

  DatasetService _datasetService;
  set datasetService(DatasetService service) {
    print('setting dataset service to $service');
    _datasetService = service;
    _loadDataSet();
  }

  @Property(observer: "endpointChanged")
  Endpoint endpoint;

  @Property()
  List<ServiceConfigurationAttribute> dataSetAttributes;

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
        element = Form.newInstance(dataSetAttributes, "CREATE");
        break;
      case DisplayType.UpdateForm:
        element = Form.newInstance(dataSetAttributes, "UPDATE");
        break;
      case DisplayType.ViewData:
        break;
      case DisplayType.Search:
        break;
    }
    if (element != null) {
      container.append(element);
    }
  }

  @reflectable
  void endpointChanged(_, __) {
    _loadDataSet();
  }

  _loadDataSet() {
    if (endpoint == null) return;
    if (_datasetService == null) return;
    print('loading data set');
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
