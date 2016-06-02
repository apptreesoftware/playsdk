part of sdk_validator.model;

@JsonSerializable()
class ListResponse extends Object with _$ListResponseSerializerMixin {
  final bool success;
  final String message;
  final bool showMessageAsAlert;
  List<ListItem> records;

  ListResponse(this.success, this.message, this.showMessageAsAlert, this.records);

  factory ListResponse.fromJson(json) => _$ListResponseFromJson(json);
}