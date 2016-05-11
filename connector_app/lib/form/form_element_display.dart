import 'package:connector_app/models/models.dart';

class FormElementDisplay {
  DisplayElement displayElement;
  String value;

  int intValue() {
    return int.parse(value);
  }

  void setIntValue(int intValue) {
    value = intValue.toString();
  }

  bool boolValue() {
    return value?.toLowerCase() == "Y" ?? false;
  }

  void setBoolValue(bool boolValue) {
    this.value = boolValue ? "Y" : "N";
  }

  bool get hidden  {
    return displayElement.hidden;
  }

  String get templateName {
    if ( displayElement.hidden ) {
      return "at-hidden";
    }
    switch(displayElement.displayType) {
      case DisplayType.TextField:
        return "at-textfield";
      case DisplayType.SelectList:
        return "at-select-list";
      default:
        return "at-hidden";
    }
  }
}
