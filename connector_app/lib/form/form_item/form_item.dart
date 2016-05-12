import 'package:polymer/polymer.dart';
import 'package:connector_app/form/form_element_display.dart';

@behavior
abstract class FormItem {

  @property
  FormElementDisplay formElementDisplay;

  void updateUI();
}
