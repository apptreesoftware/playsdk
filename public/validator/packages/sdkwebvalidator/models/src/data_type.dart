part of sdkwebvalidator.models;

enum DataType {
  Text,
  Date,
  DateTime,
  DateRange,
  DateTimeRange,
  TimeInterval,
  Integer,
  Float,
  Boolean,
  Image,
  Location,
  Attachments,
  ListItem,
  Relationship,
  Color
}

DataType DataTypeFromString(String rawValue) {
  return DataType.values.firstWhere((dt) => dt.toString().toUpperCase() == "DATATYPE." + rawValue.toUpperCase());
}

String DataTypeAsString(DataType dataType) {
  return dataType.toString().split(".")[1].toUpperCase();
}
