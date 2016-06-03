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

@PolymerRegister('at-attachment')
class AtAttachment extends PolymerElement with AtFormItemBehavior {
  @Property()
  String title;

  @Property()
  String note;

  @Property()
  String url;

  @Property()
  String upload;
  bool _uploadIsImage = false;
  @property String uploadStatus = 'no uploads happening';

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
    datasetItem.removeSubItemAt(index);
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

    if (title != null && title != '') {
      currentlyEditingSubItem.setAttribute(
          title, AttachmentAttributeIndex.title);
    }

    if (note != null && note != '') {
      currentlyEditingSubItem.setAttribute(note, AttachmentAttributeIndex.text);
    } else if (url != null && url != '') {
      currentlyEditingSubItem.setAttribute(url, AttachmentAttributeIndex.link);
    } else if (upload != null && upload != '') {
      if (_uploadIsImage) {
        currentlyEditingSubItem.setAttribute(
            upload, AttachmentAttributeIndex.image);
      } else {
        currentlyEditingSubItem.setAttribute(
            upload, AttachmentAttributeIndex.file);
      }
    }

    newAttachmentLabel = title;
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

  static List<String> _createAttachments(DataSetItem item) {
    var result = [];
    for (var subItem in item.subItems) {
      result.add(_attachmentValue(subItem));
    }
    return result;
  }

  static bool _isImage(String filename) {
    var regexp = new RegExp(r"\.(jpe?g|png|gif|bmp)$");
    return regexp.hasMatch(filename);
  }

  static String _attachmentValue(DataSetItem item) {
    return item.valueForAttributeIndex(AttachmentAttributeIndex.title) ??
        item.valueForAttributeIndex(AttachmentAttributeIndex.text) ??
        item.valueForAttributeIndex(AttachmentAttributeIndex.link) ??
        item.valueForAttributeIndex(AttachmentAttributeIndex.file);
  }

  void _populateFormValues() {
    if (currentlyEditingSubItem == null) return;
    set(
        'title',
        currentlyEditingSubItem
            .valueForAttributeIndex(AttachmentAttributeIndex.title));
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
    var upload = $$('#upload') as InputElement;
    upload.value = '';
    set('title', null);
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
        _uploadIsImage = _isImage(file.name);
        set('uploadStatus', 'upload complete');
      });
      set('uploadStatus', 'uploading...');
      reader.readAsDataUrl(file);
    }
  }
}
