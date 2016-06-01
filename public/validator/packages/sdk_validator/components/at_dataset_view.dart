@HtmlImport('at_dataset_view.html')
library sdk_validator.at_dataset_view;

import 'dart:html';
import 'dart:async';
import 'dart:convert';

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/iron_list.dart';

import 'package:sdk_validator/app_context.dart';
import 'package:sdk_validator/view_models.dart';
import 'package:sdk_validator/model.dart';
import 'list/at_list_item.dart';
import 'list/at_list_filters.dart';
import 'list/at_list.dart';

const String _tag = 'at-dataset-view';

@PolymerRegister(_tag)
class AtDatasetView extends PolymerElement {
  AppContext _context;
  EndpointDisplay _endpoint;
  List<ServiceConfigurationAttribute> _serviceAttributes;

  AtDatasetView.created() : super.created();

  factory AtDatasetView(
      AppContext context,
      List<ServiceConfigurationAttribute> attributes,
      EndpointDisplay endpoint) {
    var elem = document.createElement(_tag) as AtDatasetView;
    elem._context = context;
    elem._serviceAttributes = attributes;
    elem._endpoint = endpoint;
    return elem;
  }

  attached() {
    _showNoData();
  }

  @reflectable
  void handleEditListItem(event, detail) {
    fire('edit-list-item', detail: detail);
  }

  @reflectable
  Future handleFilterSubmit(event, Map<String, String> filters) async {
    await _loadDataSet(filters);
  }

  _loadDataSet(Map<String, String> filters) async {
    _showLoading();
    var result =
        await _context.datasetService.getDataSet(_endpoint.url, filters);
    if (result.success == false) {
      set('dataSetItems', []);
      _showFailureMessage(result);
      return;
    }
    _showDataSetItems(result.dataSetItems);
  }

  _showFailureMessage(DataSetResponse r) =>
      _showText("Failed to load dataset:\n${JSON.encode(r)}");

  _showDataSetItems(List<DataSetItem> ds) {
    var list = new AtList(_serviceAttributes, ds);
    listen(list, 'on-edit-list-item', 'handleEditListItem');
    _showElement(list);
  }

  _showLoading() => _showText("Loading...");

  _showNoData() => _showText("No Data");

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
