library sdk_validator.form_controller;
import 'dart:async';
import 'package:sdk_validator/app_context.dart';
import 'package:sdk_validator/model.dart';
import 'package:sdk_validator/components/at_dataset_form.dart';

class FormController implements FormDelegate {
  AppContext _context;
  List<ServiceConfigurationAttribute> _attributes;
  FormType _formType;
  Endpoint _endpoint;
  DataSetItem datasetItem;
  StreamController _onSearchCtrl = new StreamController.broadcast();
  Stream<DataSetItem> get onSearch => _onSearchCtrl.stream;

  FormController(this._context, this._attributes, this._formType,
      this._endpoint,
      {DataSetItem itemToUpdate}) {
    datasetItem = itemToUpdate ?? new DataSetItem();
  }

  void handleSubmit() {
    datasetItem.statusString = formTypeToStatusString(_formType);
    if (_formType == FormType.create) {
      _context.datasetService.sendCreate(_endpoint.url, datasetItem);
    } else if (_formType == FormType.update) {
      _context.datasetService.sendUpdate(_endpoint.url, datasetItem);
    } else if (_formType == FormType.search){
      _onSearchCtrl.add(datasetItem);
    }
  }

  List<ServiceConfigurationAttribute> get serviceConfigAttributesForDisplay {
    if (_attributes == null || _attributes.isEmpty) return [];
    List<ServiceConfigurationAttribute> result;
    switch (_formType) {
      case FormType.create:
        result = _attributes.where((at) => at.canCreate == true).toList();
        break;
      case FormType.update:
        result = _attributes.where((at) => at.canUpdate == true).toList();
        break;
      case FormType.search:
        result = _attributes.where((at) => at.canSearch == true).toList();
        break;
    }
    return result;
  }
}
