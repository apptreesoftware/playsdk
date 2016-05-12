@HtmlImport('form_text_field_item.html')
library connector_app.form_item.form_text_field_item;

import 'package:polymer/polymer.dart';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'form_item.dart';

/// @observable
@PolymerRegister('at-textfield')
class FormTextFieldItem extends PolymerElement with FormItem {
  FormTextFieldItem.created() : super.created();
}
