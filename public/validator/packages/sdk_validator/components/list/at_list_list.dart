@HtmlImport('at_list_list.html')
library sdk_validator.at_list_list;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:sdk_validator/components/at_list_view.dart';
import 'package:sdk_validator/view_models.dart';
import 'package:polymer_elements/iron_list.dart';

@PolymerRegister('at-list-list')
class AtListList extends PolymerElement {

  @Property(observer: 'listItemsChanged')
  List<ListItemDisplay> listItems;
  @property List<ServiceConfigurationAttributeDisplay> serviceAttributes;

  AtListList.created() : super.created();

  @reflectable
  listItemValue(ListItemDisplay item, int index) {
    return item.values[index];
  }

  @reflectable
  listItemsChanged(oldVal, newVal) {
    _redrawIronList();
  }

  _redrawIronList() {
    var ironList = $$('#iron-list') as IronList;
    if (ironList == null) return;
    ironList.fire('iron-resize');
  }
}
