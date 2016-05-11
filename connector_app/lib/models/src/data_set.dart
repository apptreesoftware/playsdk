part of connector_app.models;

@JsonSerializable()
class DataSetResponse extends Object
    with _$DataSetResponseSerializerMixin, JsProxy {

  @reflectable
  final bool success;

  @reflectable
  final String message;

  @reflectable
  final bool showMessageAlert;

  @reflectable
  final bool authorizationError;

  @reflectable
  @JsonKey('records')
  final DataSet dataSet;

  DataSetResponse(this.success, this.message, this.showMessageAlert,
      this.authorizationError, this.dataSet);

  factory DataSetResponse.fromJson(json) => _$DataSetResponseFromJson(json);
}

@JsonSerializable()
class DataSet extends Object with _$DataSetSerializerMixin, JsProxy {
  @reflectable
  final int totalRecords;

  @reflectable
  final int numberOfRecords;

  @reflectable
  final bool moreRecordsAvailable;

  @reflectable
  @JsonKey('records')
  final List<DataSetItem> dataSetItems;

  DataSet(this.totalRecords, this.numberOfRecords, this.moreRecordsAvailable,
      this.dataSetItems);

  factory DataSet.fromJson(json) => _$DataSetFromJson(json);
}

@JsonSerializable()
class DataSetItem extends Object with _$DataSetItemSerializerMixin, JsProxy {
  @reflectable
  final String primaryKey;

  @reflectable
  @JsonKey('CRUDStatus')
  final String crudStatus;

  @reflectable
  final String clientKey;

  @reflectable
  final String recordType;

  @reflectable
  @JsonKey('DataCollectionStatus')
  final String dataCollectionStatus;

  @reflectable
  final String workFlowState;

  @reflectable
  final List<String> attributes;

  DataSetItem(this.primaryKey, this.crudStatus, this.clientKey, this.recordType,
      this.dataCollectionStatus, this.workFlowState, this.attributes);

  factory DataSetItem.fromJson(json) => _$DataSetItemFromJson(json);
}
