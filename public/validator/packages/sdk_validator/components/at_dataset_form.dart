@HtmlImport('at_dataset_form.html')
library sdk_validator.at_dataset_form;

import 'dart:html';

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';

import 'package:sdk_validator/model.dart';
import 'package:sdk_validator/form.dart';
import 'package:sdk_validator/components/form/at_form_item_behavior.dart';

import 'package:polymer_elements/paper_button.dart';

import 'form/at_select_list.dart';
import 'form/at_relationship.dart';
import 'form/at_form_text_field_item.dart';
import 'form/at_attachment.dart';

enum FormType { create, update, search }
String formTypeToStatusString(FormType ft) {
  switch (ft) {
    case FormType.create:
      return "CREATE";
    case FormType.update:
      return "UPDATE";
    case FormType.search:
      return "SEARCH";
  }
}

abstract class FormDelegate {
  void handleSubmit();
  List<ServiceConfigurationAttribute> get serviceConfigAttributesForDisplay;
  DataSetItem get datasetItem;
}

const String _tag = 'at-dataset-form';

@PolymerRegister(_tag)
class AtDatasetForm extends PolymerElement {
  FormDelegate _delegate;

  AtDatasetForm.created() : super.created();

  factory AtDatasetForm(FormDelegate delegate) {
    var form = document.createElement(_tag) as AtDatasetForm;
    form._delegate = delegate;
    return form;
  }

  attached() {
    _updateUI();
  }

  @reflectable
  handleSubmitButtonClicked(_, __) {
    var container = $$('#form-item-container');

    // compute the encoded result for each SelectList component
    // before submitting
    List<FormElementDisplay> displays = [];
    for (var element in container.children) {
      if (element is! AtFormItemBehavior) continue;
      element = element as AtFormItemBehavior;
      displays.add(element.formElementDisplay);
      if (element is SelectList) {
        element.formElementDisplay.value = element.encodedListItems;
      }
    }
    _delegate.handleSubmit();
  }

  void _updateUI() {
    List<ServiceConfigurationAttribute> attributes = _delegate.serviceConfigAttributesForDisplay;

    DivElement container = $$('#form-item-container');
    for (var attribute in attributes) {
      var display = new FormElementDisplay(attribute);
      var element =
          document.createElement(display.templateName) as PolymerElement;
      element.set('formElementDisplay', display);

      if (element is AtAttachment) {
        element.datasetItem = _delegate.datasetItem;
      }

      // set the value if there is a DataSetItem to update
      if (_delegate.datasetItem != null && element is AtFormItemBehavior) {
        element.formElementDisplay.value =
            _delegate.datasetItem.valueForAttributeIndex(attribute.attributeIndex);
      }

      container.children.add(element);
    }
  }
}
