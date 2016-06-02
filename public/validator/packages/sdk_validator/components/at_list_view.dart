@HtmlImport('at_list_view.html')
library sdk_validator.components.at_list_view;

import 'dart:html';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_button.dart';

import 'package:sdk_validator/app_context.dart';
import 'package:sdk_validator/model.dart';
import 'package:sdk_validator/view_models.dart';
import 'dart:async';

const String _tag = 'at-list-view';
@PolymerRegister(_tag)
class AtListView extends PolymerElement {

  AppContext _context;
  EndpointDisplay _endpoint;
  List<ServiceConfigurationAttribute> _serviceAttributes;

  AtListView.created() : super.created();

  factory AtListView(AppContext context, List<ServiceConfigurationAttribute> attributes, EndpointDisplay endpoint) {
    var elem = document.createElement(_tag) as AtListView;
    elem._context = context;
    elem._serviceAttributes = attributes;
    elem._endpoint = endpoint;
    return elem;
  }

  @reflectable
  Future handleSubmit(_, __) async {
    await _loadData();
  }

  Future _loadData() async {
    var result = await _context.datasetService.getList(_endpoint.url, _endpoint.name);
//    _showListItems(result.records);
  }
}

