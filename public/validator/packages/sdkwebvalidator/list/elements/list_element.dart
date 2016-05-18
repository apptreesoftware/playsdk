@HtmlImport('list_element.html')
library sdkwebvalidator.list.list_element;

import 'dart:html';
import 'dart:async';

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/iron_list.dart';
import 'package:sdkwebvalidator/models/models.dart';
import 'package:sdkwebvalidator/services/services.dart';

class ListItem<T extends JsProxy> extends JsProxy {
  @reflectable int index;
  @reflectable T item;
  ListItem(this.index, this.item);
}

/// [IronList]
@PolymerRegister('at-list')
class ListElement extends PolymerElement {
  List<ServiceConfigurationAttribute> dataSetAttributes = [];

  @Property()
  List<ListItem<DataSetItem>> dataSetItems;

  Uri connectorUri;

  DatasetService service;

  ListElement.created() : super.created();

  factory ListElement(List<ServiceConfigurationAttribute> attributes,
      DatasetService service, Uri connectorUri) {
    ListElement el = document.createElement('at-list');
    el.dataSetAttributes = attributes;
    el.service = service;
    el.connectorUri = connectorUri;
    return el;
  }

  attached() async {
    await _loadDataSet();
  }

  _loadDataSet() async {
    await _hackForIronList();
    var result = await service.getDataSet(connectorUri);
    var listItems = [];
    for (var i = 0; i < result.dataSetItems.length; i++) {
      listItems.add(new ListItem(i, result.dataSetItems[i]));
    }
    set('dataSetItems', listItems);
  }

  /// Provides <iron-list> some dummy data
  /// before rendering the real data.
  ///
  /// I haven't found reliable steps to reproduce yet,
  /// If this isn't called the iron-list component will only
  Future _hackForIronList() async {
    var items = [
      new ListItem(0, null),
    ];
    set('dataSetItems', items);
    await new Future((){});
  }
}
