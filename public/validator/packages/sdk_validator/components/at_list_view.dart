@HtmlImport('at_list_view.html')
library sdk_validator.components.at_list_view;

import 'dart:html';
import 'dart:async';

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_button.dart';
import 'package:polymer_elements/iron_list.dart';
import 'package:polymer_elements/iron_flex_layout.dart';

import 'package:sdk_validator/app_context.dart';
import 'package:sdk_validator/model.dart';
import 'package:sdk_validator/view_models.dart';
import 'package:sdk_validator/components/ui/at_card.dart';
import 'package:sdk_validator/components/list/at_list_list.dart';


class ListItemDisplay extends JsProxy {
  @reflectable
  ListItem wrapped;

  ListItemDisplay(this.wrapped);

  @reflectable
  List<String> get values {
    var result = [];
    for (var i = 0; i < 11; i++) {
      result.add(wrapped.valueForAttributeIndex(i));
    }
    return result;
  }
}

class ListServiceConfigurationAttributeDisplay extends JsProxy {
  ListServiceConfigurationAttribute wrapped;
  ListServiceConfigurationAttributeDisplay(this.wrapped);

  @reflectable
  String get name => wrapped.label ?? "[no label]";
}

const String _tag = 'at-list-view';
@PolymerRegister(_tag)
class AtListView extends PolymerElement {

  AppContext _context;
  EndpointDisplay _endpoint;
  List<ListServiceConfigurationAttribute> _attributes;

  @property String status;

  AtListView.created() : super.created();

  factory AtListView(AppContext context, List<ListServiceConfigurationAttribute> attributes, EndpointDisplay endpoint) {
    var elem = document.createElement(_tag) as AtListView;
    elem._context = context;
    elem._endpoint = endpoint;
    elem._attributes = attributes;
    return elem;
  }

  attached() {
    var listList = $$('#list-list') as AtListList;
    listList.set('serviceAttributes', _attributes.map((a) => new ListServiceConfigurationAttributeDisplay(a)));
    set('status', 'nothing data loaded yet');
  }

  @reflectable
  Future handleSubmit(_, __) async {
    set('status', 'loading...');
    await _loadData();
  }

  Future _loadData() async {
    var result = await _context.datasetService.getList(_endpoint.url);
    if (result.records == null && result.message != null) {
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

