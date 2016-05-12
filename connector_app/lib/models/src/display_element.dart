part of connector_app.models;

abstract class DisplayElement {
  DataType get dataType;
  DisplayType get displayType;
  String get title;
  bool get hidden;
}