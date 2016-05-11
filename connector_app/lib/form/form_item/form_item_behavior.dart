import 'package:polymer/polymer.dart';
import 'package:connector_app/form/form_element_display.dart';
import 'package:polymer_autonotify/polymer_autonotify.dart';

@behavior
abstract class FormItemBehavior {

  @property
  @observable
  FormElementDisplay formElementDisplay;

  void updateUI();
}
