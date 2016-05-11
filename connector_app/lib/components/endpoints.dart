@HtmlImport('endpoints.html')
library connector_app.at_endpoints;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_listbox.dart';
import 'package:polymer_elements/paper_item.dart';

import 'package:connector_app/models/models.dart';

/// [PaperListbox]
/// [PaperItem]
@PolymerRegister('at-endpoints')
class EndpointsElement extends PolymerElement {

  @Property(observer: 'endpointsChanged')
  List<Endpoint> endpoints = [];

  EndpointsElement.created() : super.created();

  @reflectable
  void endpointsChanged(List newEndpoints, List oldEndpoints) {
    if (endpoints.isEmpty) throw('not ready!');
    var firstEndpoint = endpoints[0];
    fire('selected-endpoint-changed', detail: firstEndpoint);
  }

  @reflectable
  selectedChanged(CustomEventWrapper event, detail) {
    int index = detail['value'];
    var selected = endpoints[index];
    fire('selected-endpoint-changed', detail: selected);
  }
}
