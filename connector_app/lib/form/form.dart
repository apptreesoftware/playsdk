@HtmlImport('form.html')
library connector_app.form;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';

import 'package:connector_app/models/models.dart';
import 'dart:html';
import 'package:connector_app/form/form_element_display.dart';
import 'package:connector_app/form/form_item/form_text_field_item.dart';
import 'package:connector_app/form/form_item/form_item.dart';

/// [FormTextFieldItem]
/// [FormItem]
@PolymerRegister('at-form')
class Form extends PolymerElement {

  @Property()
  String formType;

  @Property(observer: 'dataSetAttributesChanged')
  List<ServiceConfigurationAttribute> dataSetAttributes;

  List<FormElementDisplay> formElementDisplays;

  Form.created() : super.created();

  static Form newInstance(List<ServiceConfigurationAttribute> attributes, String formType) {
      Form form = document.createElement("at-form");
      form.set('dataSetAttributes', attributes);
      form.set('formType', formType);
      return form;
  }

  @reflectable
  dataSetAttributesChanged(newConfig, oldConfig) {
    buildFormDisplayElements();
    render();
  }

  void buildFormDisplayElements() {
    var displays = [];
    for (var attribute in dataSetAttributes) {
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
      var element = document.createElement(display.templateName) as PolymerElement;
      element.set('formElementDisplay', display);
      DivElement container = $$('#form-item-container');
      container.children.add(element);
    }
  }
}
