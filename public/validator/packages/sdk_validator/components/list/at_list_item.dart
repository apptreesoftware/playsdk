@HtmlImport('at_list_item.html')
library sdk_validator.at_list_item;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';

import 'package:polymer_elements/paper_button.dart';
import 'package:polymer_elements/iron_icon.dart';

import 'package:sdk_validator/view_models.dart';

@PolymerRegister('at-list-item')
class AtListItem extends PolymerElement {
  @Property(observer: 'itemUpdated')
  IronListHelper<DatasetAndAttributes> item;

  @property
  List<AttributeValue> attributesWithValues;

  @Property(observer: 'notifyExpand')
  bool expand = false;

  AtListItem.created() : super.created();

  @reflectable
  itemUpdated(_, __) {
    set('attributesWithValues', item.item.attributesWithValues);
  }

  @reflectable
  editItem(event, detail) {
    fire('edit', detail: item.item.datasetItem);
  }

  @reflectable
  showAttribute(int index, expand) {

    // Only display three attributes
    if (index <=2) return true;
    if (expand) return true;
    return false;
  }

  @reflectable
  void notifyExpand(bool newValue, bool oldValue) {
    // Check if this change event is from the element getting attached
    if(oldValue == null) return;

    fire('expand');
  }
}
