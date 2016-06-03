part of sdk_validator.view_models;

class ServiceConfigurationAttributeDisplay extends JsProxy {
  ServiceConfigurationAttribute wrapped;
  ServiceConfigurationAttributeDisplay(this.wrapped);

  @reflectable
  int get attributeIndex => wrapped.attributeIndex;

  @reflectable
  String get name => wrapped.name ?? "[no name]";
}
