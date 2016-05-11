@HtmlImport('form.html')
library connector_app.form;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:polymer_autonotify/polymer_autonotify.dart';

import 'package:connector_app/models/models.dart';
import 'dart:html';

@PolymerRegister('at-form')
class Form extends PolymerElement with AutonotifyBehavior, Observable {

  @observable
  @Property()
  String formType;

  @observable
  @Property(observer: 'configChanged')
  List<DataSetAttribute> dataSetAttributes;

  Form.created() : super.created();

  static Form newInstance(List<DataSetAttribute> configuration, String formType) {
      Form form = document.createElement("at-form");
      form.dataSetAttributes = configuration;
      form.formType = formType;
      return form;
  }

  @reflectable
  void configChanged(newConfig, oldConfig) {
    print(dataSetAttributes.length);
  }
}
