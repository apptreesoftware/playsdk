@HtmlImport('relationship.html')
library sdkwebvalidator.form_item.relationship;

import 'package:polymer/polymer.dart';
import 'package:web_components/web_components.dart' show HtmlImport;
import 'form_item.dart';

@PolymerRegister('at-relationship')
class Relationship extends PolymerElement with FormItem {
  Relationship.created() : super.created();
}
