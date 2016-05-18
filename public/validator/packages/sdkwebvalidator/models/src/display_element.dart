part of sdkwebvalidator.models;

/// Describes how an element should be displayed in a form.
abstract class DisplayElement {
  DataType get dataType;
  DisplayType get displayType;
  String get title;
  bool get hidden;
  ListServiceConfiguration get relatedListServiceConfiguration;
}