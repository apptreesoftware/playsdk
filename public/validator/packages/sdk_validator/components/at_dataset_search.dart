@HtmlImport('at_dataset_search.html')
library sdk_validator.at_dataset_search;

import 'dart:html';
import 'dart:async';
import 'dart:convert';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';

import 'package:sdk_validator/app_context.dart';
import 'package:sdk_validator/view_models.dart';
import 'package:sdk_validator/model.dart';
import 'package:sdk_validator/components/at_dataset_form.dart';
import 'list/at_list.dart';
import 'package:sdk_validator/controllers/form_controller.dart';

const String _tag = 'at-dataset-search';

@PolymerRegister(_tag)
class AtDatasetSearch extends PolymerElement {
  AppContext _appContext;
  List<ServiceConfigurationAttribute> _attributes;
  Endpoint _endpoint;

  PolymerElement _form;
  Set<StreamSubscription> _subscriptions = new Set();

  AtDatasetSearch.created() : super.created();

  factory AtDatasetSearch(AppContext context,
      List<ServiceConfigurationAttribute> attributes, Endpoint endpoint) {
    var elem = document.createElement(_tag) as AtDatasetSearch;
    elem._appContext = context;
    elem._attributes = attributes;
    elem._endpoint = endpoint;
    return elem;
  }

  attached() {
    var container = $$('#form-container');
    var formController = new FormController(_appContext, _attributes, FormType.search, _endpoint);
    _form = new AtDatasetForm(formController);
    var sub = formController.onSearch.listen(handleSearch);
    _subscriptions.add(sub);
    listen(_form, 'search', 'handleSearch');
    container.children.clear();
    container.children.add(_form);
  }

  detached() {
    for(var sub in _subscriptions) {
      sub.cancel();
    }
  }

  @reflectable
  handleSearch(DataSetItem dataSetItem) async {
    _showLoading();
    var result = await _appContext.datasetService
        .searchDataSet(_endpoint.url, dataSetItem);
    if (result.success == false) {
      set('dataSetItems', []);
      _showFailureMessage(result);
      return;
    }
    _showDataSetItems(result.dataSetItems);
  }

  @reflectable
  void handleEditListItem(event, detail) {
    fire('edit-list-item', detail: detail);
  }

  _showFailureMessage(DataSetResponse r) =>
      _showText("Failed to load dataset:\n${JSON.encode(r)}");

  _showDataSetItems(List<DataSetItem> ds) {
    var list = new AtList(_attributes, ds);
    listen(list, 'on-edit-list-item', 'handleEditListItem');
    _showElement(list);
  }

  _showLoading() => _showText("Loading...");

  _showText(String text) {
    _showElement(new ParagraphElement()..text = text);
  }

  _showElement(Element elem) {
    var container = $$('#result-container');
    if (container.children.length > 0 && container.children.first is AtList) {
      unlisten(container.children.first, 'on-edit-list-item', 'handleEditListItem');
    }
    container.children.clear();
    container.children.add(elem);
  }
}
