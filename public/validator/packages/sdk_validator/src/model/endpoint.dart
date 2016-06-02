part of sdk_validator.model;

@JsonSerializable()
class EndpointResponse extends Object with _$EndpointResponseSerializerMixin {
  final bool success;
  List<Endpoint> records;

  EndpointResponse(this.success, this.records);

  factory EndpointResponse.fromJson(json) => _$EndpointResponseFromJson(json);
}

@JsonSerializable()
class Endpoint extends Object with _$EndpointSerializerMixin {
  final String endpoint;
  final String name;
  final String type;
  @JsonKey('url')
  final String urlString;

  Uri get url => Uri.parse(urlString);

  Endpoint(this.endpoint, this.name, this.type, this.urlString);

  factory Endpoint.fromJson(json) => _$EndpointFromJson(json);
}
