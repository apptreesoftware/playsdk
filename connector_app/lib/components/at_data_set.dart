@HtmlImport('at_data_set.html')
library connector_app.at_data_set;

import 'dart:html';
import 'dart:async';
import 'dart:convert';

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:rxdart/rxdart.dart' as rx;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_tabs.dart';
import 'package:polymer_elements/paper_tab.dart';

import 'package:connector_app/form/form.dart';
import 'package:connector_app/services/services.dart';
import 'package:connector_app/models/models.dart';
import 'package:autonotify_observe/autonotify_observe.dart';
import 'package:polymer_autonotify/polymer_autonotify.dart';

/// [PaperTab]
/// [PaperTabs]
@PolymerRegister('at-data-set')
class EndpointTestingElement extends PolymerElement with Observable {
  DatasetService _datasetService;

  set datasetService(DatasetService service) {
    _datasetService = service;
  }

  rx.Observable<PropertyChangeRecord> get _propertyChanges =>
      new rx.Observable.fromStream(this.changes)
          .flatMap((List<ChangeRecord> changes) =>
              new rx.Observable.fromIterable(changes))
          .where((ChangeRecord record) => record is PropertyChangeRecord);

  rx.Observable<PropertyChangeRecord> _propertyChangesOfType(String type) =>
      _propertyChanges.where((record) => record.name == type);

  ready() async {
    await for (var record in _propertyChangesOfType("endpoint")) {
      _loadDataset();
    }
  }

  @observable
  @Property()
  Endpoint endpoint;

  @observable
  @Property()
  DataSetConfiguration configuration;

  @Property(observer: 'handleSelectedTabChanged')
  int selectedTabIndex = 0;

  EndpointTestingElement.created() : super.created();

  _loadDataset() {
    if (endpoint == null) {
      return;
    }
    _datasetService.getConfiguration(endpoint.url).listen((data) {
      configuration = data;
      updateUI();
    }, onError: (error) => print(error));
  }

  @reflectable
  void handleSelectedTabChanged(int newIndex, int oldIndex) {
    updateUI();
  }

  void updateUI() {
    DisplayType displayType = DisplayType.values[selectedTabIndex];
    DivElement container = $$('#content-container');
    container.children.clear();
    Element element;
    switch (displayType) {
      case DisplayType.CreateForm:
        element = Form.newInstance(configuration, "CREATE");
        break;
      case DisplayType.UpdateForm:
        element = Form.newInstance(configuration, "UPDATE");
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
}

enum DisplayType { CreateForm, UpdateForm, ViewData, Search }
