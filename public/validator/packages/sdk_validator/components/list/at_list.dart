@HtmlImport('at_list.html')
library sdk_validator.list.at_list;

import 'dart:html';
import 'dart:async';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/iron_list.dart';

import 'package:sdk_validator/model.dart';
import 'package:sdk_validator/view_models.dart';

@PolymerRegister('at-list')
class AtList extends PolymerElement {

  @Property(computed: 'hasDataFn(dataSetItems)')
  bool hasData;
  @reflectable
  hasDataFn(List<DatasetAndAttributes> d) => d != null && !d.isEmpty;

  AtList.created() : super.created();

  factory AtList(List<ServiceConfigurationAttribute> attributes, List<DataSetItem> items) {
    var list = document.createElement('at-list') as AtList;
    list.render(attributes, items);
    return list;
  }

  @reflectable
  redrawIronList(_, __) {
    var ironList = $$('#iron-list') as IronList;
    if (ironList == null) return;
    ironList.fire('iron-resize');
  }

  void render(List<ServiceConfigurationAttribute> attributes, List<DataSetItem> items) {
    var dataSetItemsWithAttributes = [];
    var attributeDisplays = attributes.map((a) => new ServiceConfigurationAttributeDisplay(a));
    for (var i = 0; i < items.length; i++) {
      var dataSetItemDisplay = new DataSetItemDisplay(items[i]);
      var dataSetItemWithAttributes = new DatasetAndAttributes(dataSetItemDisplay, attributeDisplays);
      dataSetItemsWithAttributes.add(new IronListHelper(i, dataSetItemWithAttributes));
    }
    set('dataSetItems', dataSetItemsWithAttributes);
  }

  @reflectable
  void handleEditListItem(event, detail) {
    fire('edit-list-item', detail: detail);
  }
}
