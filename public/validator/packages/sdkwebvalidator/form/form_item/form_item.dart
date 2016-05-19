library sdkwebvalidator.form.form_item;
import 'package:polymer/polymer.dart';
import 'package:sdkwebvalidator/form/form_element_display.dart';

@behavior
abstract class FormItem {

  @property
  FormElementDisplay formElementDisplay;

  void updateUI();
}
