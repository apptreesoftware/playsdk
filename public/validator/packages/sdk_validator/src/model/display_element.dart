part of sdk_validator.model;

/// Describes how an element should be displayed in a form.
abstract class DisplayElement {
  DataType get dataType;
  DisplayType get displayType;
  String get title;
  bool get hidden;
  int get attributeIndex;
  ListServiceConfiguration get relatedListServiceConfiguration;
}
