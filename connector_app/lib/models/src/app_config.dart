part of connector_app.models;

@JsonSerializable()
class AppConfig extends Object with _$AppConfigSerializerMixin {
  @JsonKey('connector_host')
  String connectorHost;

  AppConfig(this.connectorHost);

  factory AppConfig.fromJson(json) => _$AppConfigFromJson(json);
}

