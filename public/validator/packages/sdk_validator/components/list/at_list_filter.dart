@HtmlImport('at_list_filter.html')
library sdkwebvalidator.at_list_filter;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_icon_button.dart';
import 'package:polymer_elements/iron_icons.dart';

@PolymerRegister('at-list-filter')
class AtListFilter extends PolymerElement {
  @property FilterVO filter;
  AtListFilter.created() : super.created();

  @reflectable
  void handleRemoveClicked(e, detail) {
    fire('remove', detail: filter);
  }
}

class FilterVO extends JsProxy {
  @reflectable String key;
  @reflectable String value;
  FilterVO(this.key, this.value);
}
