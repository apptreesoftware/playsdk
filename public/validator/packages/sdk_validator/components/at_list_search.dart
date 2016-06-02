@HtmlImport('at_list_search.html')
library sdk_validator.at_list_search;

import 'dart:html';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_input.dart';
import 'package:polymer_elements/paper_toggle_button.dart';
import 'package:polymer_elements/paper_icon_button.dart';
import 'package:polymer_elements/iron_icon.dart';

import 'package:sdk_validator/app_context.dart';
import 'package:sdk_validator/view_models.dart';
import 'package:sdk_validator/model.dart';
import 'package:sdk_validator/components/search/at_list_search_context.dart';
import 'package:sdk_validator/components/list/at_list_list.dart';
import 'dart:async';
import 'package:sdk_validator/components/at_list_view.dart';

const String _tag=  'at-list-search';

@PolymerRegister(_tag)
class AtListSearch extends PolymerElement {

  AppContext _context;
  EndpointDisplay _endpoint;
  List<ListServiceConfigurationAttribute> _attributes;

  @property List<ListSearchContext> searchContexts = [];
  @property String searchText;
  @property bool barcode;
  @property String status;

  AtListSearch.created() : super.created();

  factory AtListSearch(AppContext context, List<ListServiceConfigurationAttribute> attributes, EndpointDisplay endpoint) {
    var elem = document.createElement(_tag) as AtListSearch;
    elem._context = context;
    elem._attributes = attributes;
    elem._endpoint = endpoint;
    return elem;
  }

  static Map<String, String> _searchListContextsToMap(List<ListSearchContext> contexts) {
    var result = new Map<String, String>();
    for (var c in contexts) {
      result[c.key] = c.value;
    }
    return result;
  }

  Map<String, String> get _searchListContextsMap => _searchListContextsToMap(searchContexts);

  attached() {
    var listList = $$('#list-list') as AtListList;
    listList.set('serviceAttributes', _attributes.map((a) => new ListServiceConfigurationAttributeDisplay(a)));
    set('status', 'nothing loaded yet');
  }

  @reflectable
  addListSearchContext(e, d) {
    var context = new ListSearchContext();
    var newList = new List.from(searchContexts)..add(context);
    set('searchContexts', newList);
  }

  @reflectable
  removeListSearchContext(e, ListSearchContext context) {
    var newList = new List.from(searchContexts)..remove(context);
    set('searchContexts', newList);
  }

  @reflectable
  submitForm(e, d) async {
    set('status', 'loading...');
    await _loadData();
  }

  Future _loadData() async {
    var data = new SearchListData(searchText, barcode, _searchListContextsMap);
    var result = await _context.datasetService.searchList(_endpoint.url, data);
    if (result.records == null) {
      set('status', result.message);
      return;
    }
    set('status', 'done loading');
    _showListItems(result.records);
  }

  _showListItems(List<ListItem> items) {
    var listList = $$('#list-list') as AtListList;
    listList.set('listItems', items.map((item) => new ListItemDisplay(item)));
  }
}
