part of sdk_validator.view_models;

class EndpointDisplay extends JsProxy {
  Endpoint wrapped;

  @reflectable
  String get name => wrapped.name;

  @reflectable
  String get endpoint => wrapped.endpoint;

  @reflectable
  String get type => wrapped.type;

  @reflectable
  Uri get url => wrapped.url;

  EndpointDisplay(this.wrapped);
}
