@HtmlImport('form.html')
library sdkwebvalidator.form;

import 'dart:html';

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_button.dart';

import 'package:sdkwebvalidator/models/models.dart';
import 'package:sdkwebvalidator/form/form_element_display.dart';
import 'package:sdkwebvalidator/form/form_item/form_text_field_item.dart';
import 'package:sdkwebvalidator/form/form_item/relationship.dart';
import 'package:sdkwebvalidator/form/form_item/select_list.dart';
import 'package:sdkwebvalidator/form/form_item/form_item.dart';
import 'package:sdkwebvalidator/form/form_item/attachment.dart';

class FormType {
  static const String create = 'CREATE';
  static const String update = 'UPDATE';
}

/// [FormTextFieldItem]
/// [FormItem]
/// [PaperButton]
/// [Relationship]
/// [SelectList]
/// Takes a list of [ServiceConfigurationAttribute]s and renders
/// a [FormItem] for each.
@PolymerRegister('at-form')
class Form extends PolymerElement {

  DataSetItem dataSetItem;
  @property String formType;

  @Property(observer: 'dataSetAttributesChanged')
  List<ServiceConfigurationAttribute> dataSetAttributes = [];

  /// A programmatic description of a form element
  List<FormElementDisplay> formElementDisplays = [];

  Form.created() : super.created();

  factory Form(
      List<ServiceConfigurationAttribute> attributes, String formType, {DataSetItem dataSetItem}) {
    Form form = document.createElement("at-form");
    form.dataSetItem = dataSetItem;
    form.set('formType', formType);
    form.set('dataSetAttributes', attributes);
    return form;
  }

  @reflectable
  dataSetAttributesChanged(newConfig, oldConfig) {
    _buildFormDisplayElements();
    _updateUI();
    _fillData();
  }

  @reflectable
  handleSubmitButtonClicked(_, __) {
    var container = $$('#form-item-container');

    // compute the encoded result for each SelectList component
    // before submitting
    for (var element in container.children) {
      if (element is SelectList) {
        element.formElementDisplay.value = element.encodedListItems;
      }
    }

    fire('form-submit', detail: formElementDisplays);
  }

  void _buildFormDisplayElements() {
    if(formType == null) return;
    var displays = [];
    var attributes = [];
    if (formType == FormType.create) {
      attributes = dataSetAttributes.where((at) => at.createRequired == true).toList();
    } else if (formType == FormType.update) {
      attributes = dataSetAttributes.where((at) => at.updateRequired == true).toList();
    } else {
      throw('unrecognized formType $formType');
    }

    // Create a new FormElementDisplay that wraps ServiceConfigurationAttribute.
    // Since ServiceConfigurationAttribute implements DisplayElement,
    // it can be used to describe how it should be displayed in a form.
    for (var attribute in attributes) {
      var formElementDisplay = new FormElementDisplay(attribute);
      displays.add(formElementDisplay);
    }
    this.formElementDisplays = displays;
  }

  void _updateUI() {
    if (formElementDisplays == null || formElementDisplays.isEmpty) {
      return;
    }

    // create each form element
    for (var display in formElementDisplays) {
      var element =
          document.createElement(display.templateName) as PolymerElement;
      element.set('formElementDisplay', display);
      DivElement container = $$('#form-item-container');
      container.children.add(element);
    }
  }

  void _fillData() {
    if (dataSetItem == null) return;
    var container = $$('#form-item-container');
    for(var i = 0; i < container.children.length; i++) {
      var element = container.children[i];
      if (element is FormItem) {
        element.formElementDisplay.value = dataSetItem.valueForAttributeIndex(i);
      }
    }
  }
}
