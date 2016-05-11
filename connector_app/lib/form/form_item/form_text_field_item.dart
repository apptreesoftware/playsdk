@HtmlImport('form_text_field_item.html')
library connector_app.form_item.form_text_field_item;

import 'package:polymer/polymer.dart';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'form_item.dart';
import 'package:polymer_autonotify/polymer_autonotify.dart';
import 'package:connector_app/form/form_element_display.dart';

@PolymerRegister('at-textfield')
class FormTextFieldItem extends PolymerElement with Observable, AutonotifyBehavior, FormItem {

  @property
  @observable
  FormElementDisplay formElementDisplay;

  FormTextFieldItem.created() : super.created();

}
