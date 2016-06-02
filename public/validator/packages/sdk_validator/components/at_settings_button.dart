@HtmlImport('at_settings_button.html')
library sdk_validator.components.at_settings_button;

import 'package:web_components/web_components.dart' show HtmlImport;
import 'package:polymer/polymer.dart';
import 'package:sdk_validator/app_context.dart';

import 'package:polymer_elements/paper_menu_button.dart';
import 'package:polymer_elements/paper_menu.dart';
import 'package:polymer_elements/paper_input.dart';
import 'package:polymer_elements/paper_button.dart';
import 'package:sdk_validator/view_models.dart';
import 'package:sdk_validator/model.dart';

@PolymerRegister('at-settings-button')
class AtSettingsButton extends PolymerElement {
  @Property(observer: 'contextChanged')
  AppContext appContext;

  @Property()
  String coreHost;

  @Property()
  String username;

  AtSettingsButton.created() : super.created();

  @reflectable
  contextChanged(newContext, __) {
    if (newContext == null) return;
    set('coreHost', appContext.appConfig.coreHostString);
    set('username', appContext.appConfig.usernameString);
  }

  @reflectable
  saveSettings(event, detail) {
    ($$('#menu') as PaperMenuButton).close();
    var newConfig = new AppConfig(coreHost, username, appContext.appConfig.mock);
    appContext.appConfig = newConfig;
    fire('save');
  }
}
