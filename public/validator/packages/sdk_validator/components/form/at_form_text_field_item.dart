@HtmlImport('at_form_text_field_item.html')
library sdkwebvalidator.form_item.at_form_text_field_item;

import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_input.dart';
import 'package:web_components/web_components.dart' show HtmlImport;

import 'package:sdk_validator/components/ui/at_card.dart';
import 'at_form_item_behavior.dart';

/// [PaperInput]
@PolymerRegister('at-textfield')
class AtFormTextFieldItem extends PolymerElement with AtFormItemBehavior {
  @Property(observer: 'inputValueChanged')
  String inputValue;

  @Property()
  String label;

  AtFormTextFieldItem.created() : super.created();

  @reflectable
  inputValueChanged(String newValue, String oldValue) {
    formElementDisplay?.value = newValue;
  }

  attached() {
    // if we are viewing the 'update' tab we want to display existing values
    set('inputValue', formElementDisplay.value);
    set('label', formElementDisplay.title);
  }
}
