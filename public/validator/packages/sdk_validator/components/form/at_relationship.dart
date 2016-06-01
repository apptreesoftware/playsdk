@HtmlImport('at_relationship.html')
library sdk_validator.at_relationship;

import 'package:polymer/polymer.dart';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'at_form_item_behavior.dart';

@PolymerRegister('at-relationship')
class Relationship extends PolymerElement with AtFormItemBehavior {
  Relationship.created() : super.created();
}
