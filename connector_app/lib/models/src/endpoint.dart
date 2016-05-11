part of connector_app.models;

@JsonSerializable()
class EndpointResponse extends Object with _$EndpointResponseSerializerMixin {
  final bool success;
  List<Endpoint> records;

  EndpointResponse(this.success, this.records);

  factory EndpointResponse.fromJson(json) => _$EndpointResponseFromJson(json);
}

@JsonSerializable()
class Endpoint extends Object with _$EndpointSerializerMixin, JsProxy {

  @reflectable
  final String endpoint;

  @reflectable
  final String name;

  @reflectable
  final String data;

  @reflectable
  final String url;

  Endpoint(this.endpoint, this.name, this.data, this.url);

  factory Endpoint.fromJson(json) => _$EndpointFromJson(json);
}
