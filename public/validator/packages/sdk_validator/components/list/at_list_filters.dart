@HtmlImport('at_list_filters.html')
library sdk_validator.at_list_filters;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_elements/paper_button.dart';

import 'package:sdk_validator/components/list/at_list_filter.dart';
import 'package:sdk_validator/components/ui/at_card.dart';

@PolymerRegister('at-list-filters')
class AtListFilters extends PolymerElement {
  @property List<FilterVO> filters = [
    new FilterVO('MINE', 'true')
  ];

  AtListFilters.created() : super.created();

  @reflectable
  void handleAddFilter(event, detail) {
    var newFilters = new List.from(filters)
      ..add(new FilterVO('', ''));
    set('filters', newFilters);
  }

  @reflectable
  void handleRemoveFilter(event, detail) {
    var removed = detail;
    var newFilters = new List.from(filters)
      ..remove(removed);
    set('filters', newFilters);
  }

  @reflectable
  void handleFilterSubmit(event, detail) {
    var filterMap = {};
    for (var filter in filters) {
      filterMap[filter.key] = filter.value;
    }
    fire('submit', detail: filterMap);
  }
}
