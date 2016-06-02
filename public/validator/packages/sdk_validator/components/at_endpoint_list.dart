@HtmlImport('at_endpoint_list.html')
library sdk_validator.components.at_endpoint_list;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:route_hierarchical/client.dart';

import 'package:polymer_elements/paper_listbox.dart';
import 'package:polymer_elements/paper_item.dart';
import 'package:polymer_elements/paper_item_body.dart';

import 'package:sdk_validator/view_models.dart';
import 'package:sdk_validator/app_context.dart';

@PolymerRegister('at-endpoint-list')
class AtEndpointList extends PolymerElement implements Routable {
  // bound to parent list
  @property
  List<EndpointDisplay> endpoints;

  // one-way binding from <my-app>
  @Property(notify: true, observer: 'selectedEndpointChanged')
  EndpointDisplay selectedEndpoint;

  // one-way binding to <paper-listbox>
  @property
  int selectedIndex;

  AtEndpointList.created() : super.created();

  // handle clicking the list item
  @reflectable
  selectListIndexChanged(event, detail) {
    var index = detail['value'];
    if (index < 0) return;
    var endpoint = endpoints.elementAt(index);
    fire('endpoint-selected', detail: endpoint);
  }

  // handle the endpoint changing via the URL
  @reflectable
  void selectedEndpointChanged(
      EndpointDisplay newEndpoint, EndpointDisplay oldEndpoint) {
    if (endpoints == null) return;

    // avoid setting the index if nothing changed
    if (newEndpoint != null &&
        oldEndpoint != null &&
        newEndpoint.wrapped == oldEndpoint.wrapped) {
      return;
    }

    // set the index on <paper-listbox>
    var endpointList = endpoints.toList();
    var endpoint = endpointList.firstWhere(
        (e) => e.wrapped == newEndpoint.wrapped,
        orElse: () => null);
    if (endpoint != null) {
      set('selectedIndex', endpointList.indexOf(endpoint));
    }
  }
}
