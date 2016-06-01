part of sdk_validator.model;

@JsonSerializable()
class AppConfig extends Object with _$AppConfigSerializerMixin {
  @JsonKey('core_host')
  final String coreHostString;

  @JsonKey('username')
  final String usernameString;

  @JsonKey('mock')
  final bool mock;

  Uri get coreHost => Uri.parse(coreHostString);

  AppConfig(this.coreHostString, this.usernameString, this.mock);

  factory AppConfig.fromJson(json) => _$AppConfigFromJson(json);
}
