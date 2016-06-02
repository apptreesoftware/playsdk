@HtmlImport('at_attachment.html')
library sdk_validator.at_form_item.attachment;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_input.dart';
import 'package:polymer_elements/paper_dialog.dart';
import 'package:sdk_validator/components/form/at_form_item_behavior.dart';
import 'dart:html';
import 'dart:async';
import 'package:sdk_validator/model.dart';
import 'at_attachment_item.dart';


// CRUDStatus
// 1. attachment was not changed => READ
// 2. attachment was created => CREATE
// 3. attachment was updated => UPDATE

@PolymerRegister('at-attachment')
class AtAttachment extends PolymerElement with AtFormItemBehavior {
  @Property()
  String image;

  @Property()
  String note;

  @Property()
  String url;

  @Property()
  String upload;

  @Property()
  List<String> attachments = [];

  DataSetItem datasetItem;

  DataSetItem currentlyEditingSubItem;

  Set<StreamSubscription> _listeners = new Set();

  AtAttachment.created() : super.created();

  attached() {
    set('label', formElementDisplay.formElement.title);
    _populateFormValues();

    var uploadElement = $$('#upload');
    var sub = uploadElement.onChange.listen(fileUploaded);
    _listeners.add(sub);
  }

  detached() {
    for (var listener in _listeners) {
      listener.cancel();
    }
  }

  @Listen('tap')
  handleTap(e, d) {
    var button = e.target as Element;

    if (button.id == 'cancel-attachment') {
      _clearFormValues();
    }

    if (button.id == 'save-attachment') {
      _saveAttachment();
      _clearFormValues();
    }

    if (button.attributes.containsKey('data-dialog')) {
      var id = button.attributes['data-dialog'];
      var dialog = $$('#$id') as PaperDialog;
      dialog.open();
      return;
    }
  }

  @reflectable
  deleteAttachment(CustomEvent e, int index) {
    set('attachments', new List.from(attachments)..removeAt(index));
  }

  @reflectable
  editAttachment(CustomEvent e, int index) {
    var subItems = datasetItem.subItems;
    currentlyEditingSubItem = subItems[index];
    _populateFormValues();
    var dialog = $$('#modal') as PaperDialog;
    dialog.open();
  }

  @reflectable
  handleAddAttachment(e, d) {
    currentlyEditingSubItem = new DataSetItem()
      ..subItemOfAttributeIndex = formElementDisplay.formElement.attributeIndex
      ..recordTypeString = RecordType.attachment
      ..status = "CREATE";
  }

  _saveAttachment() {
    String newAttachmentLabel;
    if (image != null && image != '') {
      _clearAttachments(currentlyEditingSubItem);
      newAttachmentLabel = image;
      currentlyEditingSubItem.setAttribute(
          image, AttachmentAttributeIndex.image);
    } else if (note != null && note != '') {
      _clearAttachments(currentlyEditingSubItem);
      newAttachmentLabel = note;
      currentlyEditingSubItem.setAttribute(note, AttachmentAttributeIndex.text);
    } else if (url != null && url != '') {
      _clearAttachments(currentlyEditingSubItem);
      newAttachmentLabel = url;
      currentlyEditingSubItem.setAttribute(url, AttachmentAttributeIndex.link);
    } else if (upload != null && upload != '') {
      _clearAttachments(currentlyEditingSubItem);
      newAttachmentLabel = 'file';
      currentlyEditingSubItem.setAttribute(
          upload, AttachmentAttributeIndex.file);
    }
    if (newAttachmentLabel != null) {
      if (!datasetItem.subItems.contains(currentlyEditingSubItem)) {
        var newAttachments = new List.from(attachments)
          ..add(newAttachmentLabel);
        set('attachments', newAttachments);
        datasetItem.addSubItem(currentlyEditingSubItem);
      } else {
        set('attachments', _createAttachments(datasetItem));
        if (currentlyEditingSubItem.status == 'READ') {
          currentlyEditingSubItem.status = 'UPDATE';
        }
      }
    }
  }

  static void _clearAttachments(DataSetItem item) {
    item.setAttribute(null, AttachmentAttributeIndex.image);
    item.setAttribute(null, AttachmentAttributeIndex.text);
    item.setAttribute(null, AttachmentAttributeIndex.link);
    item.setAttribute(null, AttachmentAttributeIndex.file);
  }

  static List<String> _createAttachments(DataSetItem item) {
    var result = [];
    for (var subItem in item.subItems) {
      result.add(_attachmentValue(subItem));
    }
    return result;
  }

  static String _attachmentValue(DataSetItem item) {
    return item.valueForAttributeIndex(AttachmentAttributeIndex.image) ??
        item.valueForAttributeIndex(AttachmentAttributeIndex.text) ??
        item.valueForAttributeIndex(AttachmentAttributeIndex.link) ??
        item.valueForAttributeIndex(AttachmentAttributeIndex.file);
  }

  void _populateFormValues() {
    if (currentlyEditingSubItem == null) return;
    set(
        'image',
        currentlyEditingSubItem
            .valueForAttributeIndex(AttachmentAttributeIndex.image));
    set(
        'note',
        currentlyEditingSubItem
            .valueForAttributeIndex(AttachmentAttributeIndex.text));
    set(
        'url',
        currentlyEditingSubItem
            .valueForAttributeIndex(AttachmentAttributeIndex.link));
    set(
        'upload',
        currentlyEditingSubItem
            .valueForAttributeIndex(AttachmentAttributeIndex.file));
  }

  void _clearFormValues() {
    set('image', null);
    set('note', null);
    set('url', null);
    set('upload', null);
  }

  @reflectable
  fileUploaded(_) {
    var uploadInput = $$('#upload') as InputElement;
    final files = uploadInput.files;
    if (files.length == 1) {
      final file = files[0];
      final reader = new FileReader();
      reader.onLoadEnd.listen((e) {
        // result format is: data:text/plain;base64,{base64 data}
        // ',' is not in the set of base64 characters
        upload = reader.result.toString().split(',').elementAt(1);
      });
      reader.readAsDataUrl(file);
    }
  }
}
