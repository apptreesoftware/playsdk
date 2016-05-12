@HtmlImport('form_text_field_item.html')
library connector_app.form_item.form_text_field_item;

import 'package:polymer/polymer.dart';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'form_item.dart';
import 'package:polymer_autonotify/polymer_autonotify.dart';
import 'package:connector_app/utils/utils.dart';

/// @observable
@PolymerRegister('at-textfield')
class FormTextFieldItem extends PolymerElement with Observable, AutonotifyBehavior, FormItem, ObservableHelpers {
  FormTextFieldItem.created() : super.created();
}
