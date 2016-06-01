@HtmlImport('at_attachment_item.html')
library sdk_validator.at_attachment_item;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_icon_button.dart';
import 'package:polymer_elements/iron_icons.dart';

@PolymerRegister('at-attachment-item')
class AtAttachmentItem extends PolymerElement {
  @property String attachment;
  @property int index;
  AtAttachmentItem.created() : super.created();

  @reflectable
  handleDelete(e, d) {
    fire('delete', detail: index);
  }

  @reflectable
  handleEdit(e, d) {
    fire('edit', detail: index);
  }
}
