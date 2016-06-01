part of sdk_validator.view_models;

class Settings extends JsProxy {
  @reflectable
  String host;

  @reflectable
  String username;

  Uri get hostUri => Uri.parse(host);

  Settings(this.host, this.username);
}
