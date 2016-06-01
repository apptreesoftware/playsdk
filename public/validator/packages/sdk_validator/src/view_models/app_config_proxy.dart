part of sdk_validator.view_models;

class AppConfigProxy {
  AppConfig wrapped;

  @reflectable
  String coreHostString;

  @reflectable
  String username;

  AppConfigProxy(this.wrapped) {

  }
}