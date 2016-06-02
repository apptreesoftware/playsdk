part of sdk_validator.view_models;

class DataSetItemDisplay extends JsProxy {
  DataSetItem wrapped;

  @reflectable
  String get uuid => wrapped.uuid;

  @reflectable
  String get serverPrimaryKey => wrapped.serverPrimaryKey;

  @reflectable
  String get recordTypeString => wrapped.recordTypeString;

  @reflectable
  int get serverListOrder => wrapped.serverListOrder;

  @reflectable
  String get statusString => wrapped.statusString;

  @reflectable
  int get subItemOfAttributeIndex => wrapped.subItemOfAttributeIndex;

  @reflectable
  int subItemOrderBy = 0;

  @reflectable
  String get dataCollectionStatusString => wrapped.dataCollectionStatusString;

  @reflectable
  String get workFlowState => wrapped.workFlowState;

  @reflectable
  DateTime get retrievalDate => wrapped.retrievalDate;

  @reflectable
  String get typedAttributeStorage => wrapped.typedAttributeStorage;

  DataSetItemDisplay(this.wrapped);
}
