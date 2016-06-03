@HtmlImport('at_list_search_context.html')
library sdk_validator.at_list_search_context;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';

class ListSearchContext extends JsProxy {

  @reflectable
  String key;

  @reflectable
  String value;
}

@PolymerRegister('at-list-search-context')
class AtListSearchContext extends PolymerElement {
  @property ListSearchContext context;

  AtListSearchContext.created() : super.created();

  @reflectable
  handleRemoveClicked(_,__) {
    fire('remove', detail: context);
  }
}
