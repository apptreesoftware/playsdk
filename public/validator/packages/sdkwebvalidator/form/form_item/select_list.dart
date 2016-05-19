@HtmlImport('select_list.html')
library sdkwebvalidator.form_item.select_list;

import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_input.dart';
import 'package:polymer_elements/paper_card.dart';
import 'package:polymer_elements/paper_button.dart';
import 'package:polymer_elements/paper_toggle_button.dart';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'form_item.dart';
import 'package:sdkwebvalidator/models/models.dart';
import 'dart:convert';


class SelectDisplayElement extends JsProxy {
  @reflectable String label;
  @reflectable String value;
  int attributeIndex;

  SelectDisplayElement(this.label, this.value, this.attributeIndex);
}

/// [PaperInput]
/// [PaperCard]
/// [PaperButton]
/// [PaperToggleButton]
@PolymerRegister('at-select-list')
class SelectList extends PolymerElement with FormItem {

  @property String listName;
  @property List<SelectDisplayElement> displayElements = [];
  @property String formId;
  @property String formValue;
  @property bool expand;

  SelectList.created() : super.created();

  attached() {
    set('listName', formElementDisplay.title);
    // make a SelectDisplayElement for each attribute.
    // the value of the input will be bound to the SelectDisplayElement.
    var attributes = formElementDisplay.formElement.relatedListServiceConfiguration.attributes;
    var displays = [];

    var existingValue = formElementDisplay.value;
    ListItem listItem;
    if (existingValue != '') {
      try {
        var json = JSON.decode(existingValue);
        listItem = new ListItem.fromJson(json);
        set('formId', listItem.id);
        set('formValue', listItem.value);
      } catch(e) {
        print('Failed to convert JSON attribute to ListItem');
        print(existingValue);
      }
    }

    for (var i = 0; i < attributes.length; i++) {
      var attribute = attributes[i];
      var value = listItem?.valueForAttributeIndex(i) ?? null;
      displays.add(new SelectDisplayElement(attribute.label, value, attribute.attributeIndex));
    }
    set('displayElements', displays);
  }

  String get encodedListItems {
    // create a list item, serialize it, and set the form element display value.
    var listItem = new ListItem(formValue);
    listItem.id = formId;
    for (var element in displayElements) {
      // 'value' is the 0th attribute in a ListItem.  The '+1' converts
      // the configuration index to a ListItem (Dataset) index.
      listItem.setValueForAttributeIndex(element.attributeIndex + 1, element.value);
    }
    return JSON.encode(listItem);
  }
}
