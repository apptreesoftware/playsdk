@HtmlImport('form.html')
library connector_app.form;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_autonotify/polymer_autonotify.dart';

import 'package:connector_app/models/models.dart';
import 'dart:html';
import 'package:connector_app/form/form_element_display.dart';
import 'package:connector_app/form/form_item/form_text_field_item.dart';
import 'package:connector_app/form/form_item/form_item.dart';

/// [FormTextFieldItem]
@PolymerRegister('at-form')
class Form extends PolymerElement with AutonotifyBehavior, Observable {

  @observable
  @Property()
  String formType;

  @observable
  @Property(observer: 'dataSetAttributesChanged')
  List<ServiceConfigurationAttribute> dataSetAttributes;

  List<FormElementDisplay> formElementDisplays;

  Form.created() : super.created();

  static Form newInstance(List<ServiceConfigurationAttribute> configuration, String formType) {
      Form form = document.createElement("at-form");
      form.dataSetAttributes = configuration;
      form.formType = formType;
      return form;
  }

  @reflectable
  void dataSetAttributesChanged(newConfig, oldConfig) {
    buildFormDisplayElements();
    render();
  }

  void buildFormDisplayElements() {
    var displays = [];
    for ( var attribute in dataSetAttributes ) {
      FormElementDisplay formElementDisplay = new FormElementDisplay();
      formElementDisplay.displayElement = attribute;
      displays.add(formElementDisplay);
    }
    this.formElementDisplays = displays;
  }

  void render() {

    if (formElementDisplays == null || formElementDisplays.isEmpty) {
      return;
    }

    for (var display in formElementDisplays) {
      var element = document.createElement(display.templateName);
      element.formElementDisplay = display;
      DivElement container = $$('#form-item-container');
      container.children.add(element);
    }
  }
}
