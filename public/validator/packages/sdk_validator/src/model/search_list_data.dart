part of sdk_validator.model;

@JsonSerializable()
class SearchListData extends Object with _$SearchListDataSerializerMixin {
  String searchTerm;
  bool barcodeSearch;
  Map<String, String> context;
  SearchListData(this.searchTerm, this.barcodeSearch, this.context);
  factory SearchListData.fromJson(json) => _$SearchListDataFromJson(json);
}