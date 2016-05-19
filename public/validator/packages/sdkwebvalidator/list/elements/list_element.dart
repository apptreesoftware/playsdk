@HtmlImport('list_element.html')
library sdkwebvalidator.list.list_element;

import 'dart:html';
import 'dart:async';

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/iron_list.dart';
import 'package:polymer_elements/iron_icons.dart';
import 'package:sdkwebvalidator/models/models.dart';
import 'package:sdkwebvalidator/services/services.dart';

import 'package:sdkwebvalidator/list/elements/list_item.dart';

class ListItemVO<T extends JsProxy> extends JsProxy {
  @reflectable int index;
  @reflectable T item;
  ListItemVO(this.index, this.item);
}

/// [IronList]
/// [ListItemElement]
@PolymerRegister('at-list')
class ListElement extends PolymerElement {
  List<ServiceConfigurationAttribute> dataSetAttributes = [];

  @Property()
  List<ListItemVO<DataSetItem>> dataSetItems;

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
      listItems.add(new ListItemVO(i, result.dataSetItems[i]));
    }
    set('dataSetItems', listItems);
  }

  @reflectable
  handleEditListItem(event, detail) {
    var dataSetItem = (detail as ListItemVO).item as DataSetItem;
    fire('item-edit', detail: dataSetItem);
  }

  /// Provides <iron-list> some dummy data
  /// before rendering the real data.
  ///
  /// I haven't found reliable steps to reproduce yet,
  /// If this isn't called the iron-list component will only
  Future _hackForIronList() async {
    var items = [
      new ListItemVO(0, new DataSetItem()..uuid="loading"),
    ];
    set('dataSetItems', items);
    await new Future((){});
  }
}
