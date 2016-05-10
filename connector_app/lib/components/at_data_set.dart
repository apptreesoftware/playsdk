@HtmlImport('at_data_set.html')
library connector_app.at_data_set;

import 'dart:async';

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';

import 'package:connector_app/services/services.dart';
import 'package:connector_app/models/models.dart';

@PolymerRegister('at-data-set')
class AtDataSet extends PolymerElement {

  DatasetService _datasetService;

  set datasetService(DatasetService service) {
    _datasetService = service;
    _loadDataset();
  }

  @Property()
  Endpoint endpoint;

  AtDataSet.created() : super.created();

  Future _loadDataset() async {
    var dataset = await _datasetService.getDataset(endpoint.url);
    print("got dataset ${dataset.toJson()}");
  }
}