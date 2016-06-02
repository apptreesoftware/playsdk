@HtmlImport('at_list_search.html')
library sdk_validator.at_list_search;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:sdk_validator/app_context.dart';
import 'package:sdk_validator/view_models.dart';
import 'package:sdk_validator/model.dart';
import 'dart:html';

const String _tag=  'at-list-search';

@PolymerRegister(_tag)
class AtListSearch extends PolymerElement {

  AppContext _context;
  EndpointDisplay _endpoint;
  List<ServiceConfigurationAttribute> _serviceAttributes;

  AtListSearch.created() : super.created();

  factory AtListSearch(AppContext context, List<ServiceConfigurationAttribute> attributes, EndpointDisplay endpoint) {
    var elem = document.createElement(_tag) as AtListSearch;
    elem._context = context;
    elem._serviceAttributes = attributes;
    elem._endpoint = endpoint;
    return elem;
  }
}
