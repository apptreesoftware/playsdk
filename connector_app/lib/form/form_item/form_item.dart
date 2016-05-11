import 'package:connector_app/form/form_element_display.dart';

abstract class FormItem {
  FormElementDisplay formElementDisplay;

  void updateUI();

}