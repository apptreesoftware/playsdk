@HtmlImport('list_item.html')
library sdkwebvalidator.list_item;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:sdkwebvalidator/models/models.dart';
import 'package:sdkwebvalidator/list/elements/list_element.dart';

@PolymerRegister('list-item')
class ListItemElement extends PolymerElement {

  @property ListItemVO item;

  ListItemElement.created() : super.created();

  @reflectable
  editItem(event, detail) {
    fire('edit', detail: item);
  }
}
