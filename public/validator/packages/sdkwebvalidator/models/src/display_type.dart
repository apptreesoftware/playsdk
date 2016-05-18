part of sdkwebvalidator.models;

enum DisplayType {
  TextField,
  TextArea,
  Password,
  RadioButton,
  SelectList,
  MapSelectList,
  Checkbox,
  Integer,
  Float,
  Date,
  Time,
  DateTime,
  DateRange,
  DateTimeRange,
  Distance,
  Money,
  Image,
  Email,
  Coordinate,
  Notes,
  MapLatitude,
  MapLongitude,
  MapTitle,
  MapSubtitle,
  MapIcon,
  Attachment,
  KeyValues,
  BarcodeScanner,
  BarcodeListScanner,
  Badge,
  Unread,
  PrimaryKey,
  ActionID,
  QRCode,
  TrackingEnabled,
  Hidden,
  Relationship,
  Signature,
  Duration,
  Color,
  StarRating,
  HtmlField,
  Survey,
  Toggle
}

DisplayType DisplayTypeFromString(String rawValue) {
  return DisplayType.values.firstWhere((dt) => dt.toString().toUpperCase() == "DISPLAYTYPE." + rawValue);
}

String DisplayTypeAsString(DisplayType displayType) {
  return displayType.toString().split('.')[1].toUpperCase();
}