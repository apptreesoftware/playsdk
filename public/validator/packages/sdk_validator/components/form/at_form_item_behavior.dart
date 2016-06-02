library sdkwebvalidator.at_form_item_behavior;

import 'package:polymer/polymer.dart';
import 'package:sdk_validator/form.dart';

@behavior
abstract class AtFormItemBehavior {
  @property
  FormElementDisplay formElementDisplay;

  void updateUI();
}
