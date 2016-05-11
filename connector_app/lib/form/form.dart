@HtmlImport('form.html')
library connector_app.form;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_autonotify/polymer_autonotify.dart';

import 'package:connector_app/models/models.dart';
import 'dart:html';
import 'package:connector_app/form/form_element_display.dart';

@PolymerRegister('at-form')
class Form extends PolymerElement with AutonotifyBehavior, Observable {

  @observable
  @Property()
  String formType;

  @observable
  @Property(observer: 'configChanged')
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
  void configChanged(newConfig, oldConfig) {
    buildFormDisplayElements();
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
    for (var display in formElementDisplays) {
      Element element = document.createElement(display.templateName);
      this.children.add(element);
    }
  }

}
