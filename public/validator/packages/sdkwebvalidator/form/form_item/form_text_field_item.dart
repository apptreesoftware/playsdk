@HtmlImport('form_text_field_item.html')
library sdkwebvalidator.form_item.form_text_field_item;

import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_input.dart';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'form_item.dart';

/// [PaperInput]
@PolymerRegister('at-textfield')
class FormTextFieldItem extends PolymerElement with FormItem {

  @Property(observer: 'inputValueChanged')
  String inputValue;

  FormTextFieldItem.created() : super.created();

  @reflectable
  inputValueChanged(String newValue, String oldValue) {
    formElementDisplay?.value = newValue;
  }
}
