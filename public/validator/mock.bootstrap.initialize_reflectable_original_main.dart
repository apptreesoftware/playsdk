import 'package:initialize/src/static_loader.dart';
import 'package:initialize/initialize.dart';
import 'mock.bootstrap.dart' as i0;
import 'package:polymer_interop/src/convert.dart' as i1;
import 'package:web_components/html_import_annotation.dart' as i2;
import 'package:polymer_interop/polymer_interop.dart' as i3;
import 'package:polymer/polymer_micro.dart' as i4;
import 'package:polymer/polymer_mini.dart' as i5;
import 'package:polymer/src/template/array_selector.dart' as i6;
import 'package:web_components/custom_element_proxy.dart' as i7;
import 'package:polymer/src/template/dom_bind.dart' as i8;
import 'package:polymer/src/template/dom_if.dart' as i9;
import 'package:polymer/src/template/dom_repeat.dart' as i10;
import 'package:polymer/polymer.dart' as i11;
import 'package:polymer_elements/default_theme.dart' as i12;
import 'package:polymer_elements/roboto.dart' as i13;
import 'package:polymer_elements/typography.dart' as i14;
import 'package:polymer_elements/iron_flex_layout.dart' as i15;
import 'package:polymer_elements/paper_toolbar.dart' as i16;
import 'package:polymer_elements/iron_resizable_behavior.dart' as i17;
import 'package:polymer_elements/iron_media_query.dart' as i18;
import 'package:polymer_elements/iron_selection.dart' as i19;
import 'package:polymer_elements/iron_selectable.dart' as i20;
import 'package:polymer_elements/iron_multi_selectable.dart' as i21;
import 'package:polymer_elements/iron_selector.dart' as i22;
import 'package:polymer_elements/paper_drawer_panel.dart' as i23;
import 'package:polymer_elements/paper_header_panel.dart' as i24;
import 'package:polymer_elements/iron_a11y_keys_behavior.dart' as i25;
import 'package:polymer_elements/iron_control_state.dart' as i26;
import 'package:polymer_elements/iron_fit_behavior.dart' as i27;
import 'package:polymer_elements/iron_overlay_backdrop.dart' as i28;
import 'package:polymer_elements/iron_overlay_behavior.dart' as i29;
import 'package:polymer_elements/iron_meta.dart' as i30;
import 'package:polymer_elements/neon_animation_behavior.dart' as i31;
import 'package:polymer_elements/neon_animation/animations/opaque_animation.dart'
    as i32;
import 'package:polymer_elements/neon_animatable_behavior.dart' as i33;
import 'package:polymer_elements/neon_animation_runner_behavior.dart' as i34;
import 'package:polymer_elements/iron_dropdown.dart' as i35;
import 'package:polymer_elements/shadow.dart' as i36;
import 'package:polymer_elements/paper_material_shared_styles.dart' as i37;
import 'package:polymer_elements/paper_material.dart' as i38;
import 'package:polymer_elements/neon_animation/animations/fade_in_animation.dart'
    as i39;
import 'package:polymer_elements/neon_animation/animations/fade_out_animation.dart'
    as i40;
import 'package:polymer_elements/paper_menu_button_animations.dart' as i41;
import 'package:polymer_elements/paper_menu_button.dart' as i42;
import 'package:polymer_elements/iron_menu_behavior.dart' as i43;
import 'package:polymer_elements/color.dart' as i44;
import 'package:polymer_elements/paper_menu_shared_styles.dart' as i45;
import 'package:polymer_elements/paper_menu.dart' as i46;
import 'package:polymer_elements/iron_button_state.dart' as i47;
import 'package:polymer_elements/paper_item_behavior.dart' as i48;
import 'package:polymer_elements/paper_item_shared_styles.dart' as i49;
import 'package:polymer_elements/paper_item.dart' as i50;
import 'package:polymer_elements/paper_ripple.dart' as i51;
import 'package:polymer_elements/paper_ripple_behavior.dart' as i52;
import 'package:polymer_elements/paper_button_behavior.dart' as i53;
import 'package:polymer_elements/paper_button.dart' as i54;
import 'package:polymer_elements/paper_listbox.dart' as i55;
import 'package:sdkwebvalidator/elements/endpoints.dart' as i56;
import 'package:polymer/src/common/polymer_register.dart' as i57;
import 'package:polymer_elements/iron_menubar_behavior.dart' as i58;
import 'package:polymer_elements/iron_icon.dart' as i59;
import 'package:polymer_elements/paper_inky_focus_behavior.dart' as i60;
import 'package:polymer_elements/paper_icon_button.dart' as i61;
import 'package:polymer_elements/iron_iconset_svg.dart' as i62;
import 'package:polymer_elements/paper_tabs_icons.dart' as i63;
import 'package:polymer_elements/paper_tab.dart' as i64;
import 'package:polymer_elements/paper_tabs.dart' as i65;
import 'package:polymer_elements/iron_form_element_behavior.dart' as i66;
import 'package:polymer_elements/paper_input_behavior.dart' as i67;
import 'package:polymer_elements/iron_validatable_behavior.dart' as i68;
import 'package:polymer_elements/iron_input.dart' as i69;
import 'package:polymer_elements/paper_input_addon_behavior.dart' as i70;
import 'package:polymer_elements/paper_input_char_counter.dart' as i71;
import 'package:polymer_elements/paper_input_container.dart' as i72;
import 'package:polymer_elements/paper_input_error.dart' as i73;
import 'package:polymer_elements/paper_input.dart' as i74;
import 'package:sdkwebvalidator/form/form_item/form_text_field_item.dart'
    as i75;
import 'package:sdkwebvalidator/form/form_item/relationship.dart' as i76;
import 'package:polymer_elements/iron_image.dart' as i77;
import 'package:polymer_elements/paper_card.dart' as i78;
import 'package:sdkwebvalidator/form/form_item/select_list.dart' as i79;
import 'package:sdkwebvalidator/form/form.dart' as i80;
import 'package:polymer_elements/iron_list.dart' as i81;
import 'package:sdkwebvalidator/list/elements/list_element.dart' as i82;
import 'package:sdkwebvalidator/elements/endpoint_tester.dart' as i83;
import 'package:sdkwebvalidator/elements/sdkwebvalidator.dart' as i84;

main() {
  initializers.addAll([
    new InitEntry(
        const i7.CustomElementProxy('array-selector'), i6.ArraySelector),
    new InitEntry(
        const i7.CustomElementProxy('dom-bind', extendsTag: 'template'),
        i8.DomBind),
    new InitEntry(const i7.CustomElementProxy('dom-if', extendsTag: 'template'),
        i9.DomIf),
    new InitEntry(
        const i7.CustomElementProxy('dom-repeat', extendsTag: 'template'),
        i10.DomRepeat),
    new InitEntry(
        const i7.CustomElementProxy('paper-toolbar'), i16.PaperToolbar),
    new InitEntry(
        const i7.CustomElementProxy('iron-media-query'), i18.IronMediaQuery),
    new InitEntry(
        const i7.CustomElementProxy('iron-selector'), i22.IronSelector),
    new InitEntry(const i7.CustomElementProxy('paper-drawer-panel'),
        i23.PaperDrawerPanel),
    new InitEntry(const i7.CustomElementProxy('paper-header-panel'),
        i24.PaperHeaderPanel),
    new InitEntry(const i7.CustomElementProxy('iron-overlay-backdrop'),
        i28.IronOverlayBackdrop),
    new InitEntry(const i7.CustomElementProxy('iron-meta'), i30.IronMeta),
    new InitEntry(
        const i7.CustomElementProxy('iron-meta-query'), i30.IronMetaQuery),
    new InitEntry(
        const i7.CustomElementProxy('opaque-animation'), i32.OpaqueAnimation),
    new InitEntry(
        const i7.CustomElementProxy('iron-dropdown'), i35.IronDropdown),
    new InitEntry(
        const i7.CustomElementProxy('paper-material'), i38.PaperMaterial),
    new InitEntry(
        const i7.CustomElementProxy('fade-in-animation'), i39.FadeInAnimation),
    new InitEntry(const i7.CustomElementProxy('fade-out-animation'),
        i40.FadeOutAnimation),
    new InitEntry(
        const i7.CustomElementProxy('paper-menu-grow-height-animation'),
        i41.PaperMenuGrowHeightAnimation),
    new InitEntry(
        const i7.CustomElementProxy('paper-menu-grow-width-animation'),
        i41.PaperMenuGrowWidthAnimation),
    new InitEntry(
        const i7.CustomElementProxy('paper-menu-shrink-width-animation'),
        i41.PaperMenuShrinkWidthAnimation),
    new InitEntry(
        const i7.CustomElementProxy('paper-menu-shrink-height-animation'),
        i41.PaperMenuShrinkHeightAnimation),
    new InitEntry(
        const i7.CustomElementProxy('paper-menu-button'), i42.PaperMenuButton),
    new InitEntry(const i7.CustomElementProxy('paper-menu'), i46.PaperMenu),
    new InitEntry(const i7.CustomElementProxy('paper-item'), i50.PaperItem),
    new InitEntry(const i7.CustomElementProxy('paper-ripple'), i51.PaperRipple),
    new InitEntry(const i7.CustomElementProxy('paper-button'), i54.PaperButton),
    new InitEntry(
        const i7.CustomElementProxy('paper-listbox'), i55.PaperListbox),
    new InitEntry(
        const i57.PolymerRegister('at-endpoints'), i56.EndpointsElement),
    new InitEntry(const i7.CustomElementProxy('iron-icon'), i59.IronIcon),
    new InitEntry(
        const i7.CustomElementProxy('paper-icon-button'), i61.PaperIconButton),
    new InitEntry(
        const i7.CustomElementProxy('iron-iconset-svg'), i62.IronIconsetSvg),
    new InitEntry(const i7.CustomElementProxy('paper-tab'), i64.PaperTab),
    new InitEntry(const i7.CustomElementProxy('paper-tabs'), i65.PaperTabs),
    new InitEntry(
        const i7.CustomElementProxy('iron-input', extendsTag: 'input'),
        i69.IronInput),
    new InitEntry(const i7.CustomElementProxy('paper-input-char-counter'),
        i71.PaperInputCharCounter),
    new InitEntry(const i7.CustomElementProxy('paper-input-container'),
        i72.PaperInputContainer),
    new InitEntry(
        const i7.CustomElementProxy('paper-input-error'), i73.PaperInputError),
    new InitEntry(const i7.CustomElementProxy('paper-input'), i74.PaperInput),
    new InitEntry(
        const i57.PolymerRegister('at-textfield'), i75.FormTextFieldItem),
    new InitEntry(
        const i57.PolymerRegister('at-relationship'), i76.Relationship),
    new InitEntry(const i7.CustomElementProxy('iron-image'), i77.IronImage),
    new InitEntry(const i7.CustomElementProxy('paper-card'), i78.PaperCard),
    new InitEntry(const i57.PolymerRegister('at-select-list'), i79.SelectList),
    new InitEntry(const i57.PolymerRegister('at-form'), i80.Form),
    new InitEntry(const i7.CustomElementProxy('iron-list'), i81.IronList),
    new InitEntry(const i57.PolymerRegister('at-list'), i82.ListElement),
    new InitEntry(const i57.PolymerRegister('at-endpoint-tester'),
        i83.EndpointTestElement),
    new InitEntry(const i57.PolymerRegister('sdk-web-validator'),
        i84.ConnectorAppElement),
  ]);

  return i0.main();
}
