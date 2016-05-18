part of sdkwebvalidator.models;

@JsonSerializable()
class AppConfig extends Object with _$AppConfigSerializerMixin {
  @JsonKey('core_host')
  final String coreHostString;

  @JsonKey('username')
  final String usernameString;

  Uri get coreHost => Uri.parse(coreHostString);

  AppConfig(this.coreHostString, this.usernameString);

  factory AppConfig.fromJson(json) => _$AppConfigFromJson(json);
}

