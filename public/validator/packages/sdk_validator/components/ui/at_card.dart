@HtmlImport('at_card.html')
library sdk_validator.components.at_card;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_card.dart';
import 'package:polymer_elements/color.dart';
import 'package:polymer_elements/typography.dart';

@PolymerRegister('at-card')
class AtCard extends PolymerElement {

  @property String heading;
  @Property(computed: 'computeHasHeading(heading)')
  bool hasHeading = false;

  AtCard.created() : super.created();

  @reflectable
  bool computeHasHeading(String heading) => heading != null;
}
