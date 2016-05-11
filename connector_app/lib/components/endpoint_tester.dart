@HtmlImport('endpoint_tester.html')
library connector_app.at_data_set;

import 'dart:html';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_tabs.dart';
import 'package:polymer_elements/paper_tab.dart';

import 'package:connector_app/utils/utils.dart';
import 'package:connector_app/form/form.dart';
import 'package:connector_app/services/services.dart';
import 'package:connector_app/models/models.dart';
import 'package:autonotify_observe/autonotify_observe.dart';
import 'package:polymer_autonotify/polymer_autonotify.dart';

enum DisplayType { CreateForm, UpdateForm, ViewData, Search }

/// [PaperTab]
/// [PaperTabs]
@PolymerRegister('at-endpoint-tester')
class EndpointTestElement extends PolymerElement
    with Observable, ObservableHelpers {

  DatasetService _datasetService;
  set datasetService(DatasetService service) {
    _datasetService = service;
  }

  @observable
  @property
  Endpoint endpoint;

  @observable
  @property
  List<DataSetAttribute> dataSetAttributes;

  @observable
  @property
  int selectedTabIndex = 0;

  EndpointTestElement.created() : super.created();

  ready() {
    var subs = [
      propertyChangesOfType("endpoint").listen((_) => _loadDataSet()),
      propertyChangesOfType("selectedTabIndex")
          .listen((_) => _handleSelectedTabChanged())
    ];
    subscriptions.addAll(subs);
  }

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

  _loadDataSet() {
    print('loading data set');
    if (endpoint == null) return;
    _datasetService.getConfiguration(endpoint.url).listen((data) {
      dataSetAttributes = data;
      updateUI();
    }, onError: (error) => print(error));
  }

  void _handleSelectedTabChanged() {
    print('selected tab changed');
    updateUI();
  }
}
