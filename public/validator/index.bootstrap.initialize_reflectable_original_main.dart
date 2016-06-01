import 'package:initialize/src/static_loader.dart';
import 'package:initialize/initialize.dart';
import 'index.bootstrap.dart' as i0;
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
import 'package:polymer_elements/iron_resizable_behavior.dart' as i12;
import 'package:polymer_elements/iron_media_query.dart' as i13;
import 'package:polymer_elements/iron_selection.dart' as i14;
import 'package:polymer_elements/iron_selectable.dart' as i15;
import 'package:polymer_elements/iron_multi_selectable.dart' as i16;
import 'package:polymer_elements/iron_selector.dart' as i17;
import 'package:polymer_elements/paper_drawer_panel.dart' as i18;
import 'package:polymer_elements/iron_flex_layout.dart' as i19;
import 'package:polymer_elements/paper_header_panel.dart' as i20;
import 'package:polymer_elements/default_theme.dart' as i21;
import 'package:polymer_elements/roboto.dart' as i22;
import 'package:polymer_elements/typography.dart' as i23;
import 'package:polymer_elements/paper_toolbar.dart' as i24;
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
import 'package:polymer_elements/iron_button_state.dart' as i43;
import 'package:polymer_elements/paper_ripple.dart' as i44;
import 'package:polymer_elements/paper_ripple_behavior.dart' as i45;
import 'package:polymer_elements/paper_inky_focus_behavior.dart' as i46;
import 'package:polymer_elements/iron_icon.dart' as i47;
import 'package:polymer_elements/paper_button_behavior.dart' as i48;
import 'package:polymer_elements/paper_icon_button.dart' as i49;
import 'package:polymer_elements/paper_button.dart' as i50;
import 'package:polymer_elements/iron_iconset_svg.dart' as i51;
import 'package:polymer_elements/iron_icons.dart' as i52;
import 'package:polymer_elements/iron_menu_behavior.dart' as i53;
import 'package:polymer_elements/color.dart' as i54;
import 'package:polymer_elements/paper_menu_shared_styles.dart' as i55;
import 'package:polymer_elements/paper_menu.dart' as i56;
import 'package:polymer_elements/iron_form_element_behavior.dart' as i57;
import 'package:polymer_elements/paper_input_behavior.dart' as i58;
import 'package:polymer_elements/iron_validatable_behavior.dart' as i59;
import 'package:polymer_elements/iron_input.dart' as i60;
import 'package:polymer_elements/paper_input_addon_behavior.dart' as i61;
import 'package:polymer_elements/paper_input_char_counter.dart' as i62;
import 'package:polymer_elements/paper_input_container.dart' as i63;
import 'package:polymer_elements/paper_input_error.dart' as i64;
import 'package:polymer_elements/paper_input.dart' as i65;
import 'package:sdk_validator/components/at_settings_button.dart' as i66;
import 'package:polymer/src/common/polymer_register.dart' as i67;
import 'package:polymer_elements/paper_listbox.dart' as i68;
import 'package:polymer_elements/paper_item_behavior.dart' as i69;
import 'package:polymer_elements/paper_item_shared_styles.dart' as i70;
import 'package:polymer_elements/paper_item.dart' as i71;
import 'package:polymer_elements/paper_item_body.dart' as i72;
import 'package:sdk_validator/components/at_endpoint_list.dart' as i73;
import 'package:polymer_elements/iron_menubar_behavior.dart' as i74;
import 'package:polymer_elements/paper_tabs_icons.dart' as i75;
import 'package:polymer_elements/paper_tab.dart' as i76;
import 'package:polymer_elements/paper_tabs.dart' as i77;
import 'package:polymer_elements/iron_image.dart' as i78;
import 'package:polymer_elements/paper_card.dart' as i79;
import 'package:polymer_elements/iron_checked_element_behavior.dart' as i80;
import 'package:polymer_elements/paper_checked_element_behavior.dart' as i81;
import 'package:polymer_elements/paper_toggle_button.dart' as i82;
import 'package:sdk_validator/components/ui/at_card.dart' as i83;
import 'package:sdk_validator/components/form/at_select_list.dart' as i84;
import 'package:sdk_validator/components/form/at_relationship.dart' as i85;
import 'package:sdk_validator/components/form/at_form_text_field_item.dart'
    as i86;
import 'package:polymer_elements/paper_dialog_behavior.dart' as i87;
import 'package:polymer_elements/paper_dialog_shared_styles.dart' as i88;
import 'package:polymer_elements/paper_dialog.dart' as i89;
import 'package:sdk_validator/components/form/at_attachment_item.dart' as i90;
import 'package:sdk_validator/components/form/at_attachment.dart' as i91;
import 'package:sdk_validator/components/at_dataset_form.dart' as i92;
import 'package:polymer_elements/iron_list.dart' as i93;
import 'package:sdk_validator/components/list/at_list_item.dart' as i94;
import 'package:sdk_validator/components/list/at_list_filter.dart' as i95;
import 'package:sdk_validator/components/list/at_list_filters.dart' as i96;
import 'package:sdk_validator/components/list/at_list.dart' as i97;
import 'package:sdk_validator/components/at_dataset_view.dart' as i98;
import 'package:sdk_validator/components/at_dataset_search.dart' as i99;
import 'package:sdk_validator/components/at_list_view.dart' as i100;
import 'package:sdk_validator/components/at_list_search.dart' as i101;
import 'package:sdk_validator/components/at_endpoint.dart' as i102;
import 'package:sdk_validator/components/at_validator.dart' as i103;

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
        const i7.CustomElementProxy('iron-media-query'), i13.IronMediaQuery),
    new InitEntry(
        const i7.CustomElementProxy('iron-selector'), i17.IronSelector),
    new InitEntry(const i7.CustomElementProxy('paper-drawer-panel'),
        i18.PaperDrawerPanel),
    new InitEntry(const i7.CustomElementProxy('paper-header-panel'),
        i20.PaperHeaderPanel),
    new InitEntry(
        const i7.CustomElementProxy('paper-toolbar'), i24.PaperToolbar),
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
    new InitEntry(const i7.CustomElementProxy('paper-ripple'), i44.PaperRipple),
    new InitEntry(const i7.CustomElementProxy('iron-icon'), i47.IronIcon),
    new InitEntry(
        const i7.CustomElementProxy('paper-icon-button'), i49.PaperIconButton),
    new InitEntry(const i7.CustomElementProxy('paper-button'), i50.PaperButton),
    new InitEntry(
        const i7.CustomElementProxy('iron-iconset-svg'), i51.IronIconsetSvg),
    new InitEntry(const i7.CustomElementProxy('paper-menu'), i56.PaperMenu),
    new InitEntry(
        const i7.CustomElementProxy('iron-input', extendsTag: 'input'),
        i60.IronInput),
    new InitEntry(const i7.CustomElementProxy('paper-input-char-counter'),
        i62.PaperInputCharCounter),
    new InitEntry(const i7.CustomElementProxy('paper-input-container'),
        i63.PaperInputContainer),
    new InitEntry(
        const i7.CustomElementProxy('paper-input-error'), i64.PaperInputError),
    new InitEntry(const i7.CustomElementProxy('paper-input'), i65.PaperInput),
    new InitEntry(
        const i67.PolymerRegister('at-settings-button'), i66.AtSettingsButton),
    new InitEntry(
        const i7.CustomElementProxy('paper-listbox'), i68.PaperListbox),
    new InitEntry(const i7.CustomElementProxy('paper-item'), i71.PaperItem),
    new InitEntry(
        const i7.CustomElementProxy('paper-item-body'), i72.PaperItemBody),
    new InitEntry(
        const i67.PolymerRegister('at-endpoint-list'), i73.AtEndpointList),
    new InitEntry(const i7.CustomElementProxy('paper-tab'), i76.PaperTab),
    new InitEntry(const i7.CustomElementProxy('paper-tabs'), i77.PaperTabs),
    new InitEntry(const i7.CustomElementProxy('iron-image'), i78.IronImage),
    new InitEntry(const i7.CustomElementProxy('paper-card'), i79.PaperCard),
    new InitEntry(const i7.CustomElementProxy('paper-toggle-button'),
        i82.PaperToggleButton),
    new InitEntry(const i67.PolymerRegister('at-card'), i83.AtCard),
    new InitEntry(const i67.PolymerRegister('at-select-list'), i84.SelectList),
    new InitEntry(
        const i67.PolymerRegister('at-relationship'), i85.Relationship),
    new InitEntry(
        const i67.PolymerRegister('at-textfield'), i86.AtFormTextFieldItem),
    new InitEntry(const i7.CustomElementProxy('paper-dialog'), i89.PaperDialog),
    new InitEntry(
        const i67.PolymerRegister('at-attachment-item'), i90.AtAttachmentItem),
    new InitEntry(const i67.PolymerRegister('at-attachment'), i91.AtAttachment),
    new InitEntry(
        const i67.PolymerRegister('at-dataset-form'), i92.AtDatasetForm),
    new InitEntry(const i7.CustomElementProxy('iron-list'), i93.IronList),
    new InitEntry(const i67.PolymerRegister('at-list-item'), i94.AtListItem),
    new InitEntry(
        const i67.PolymerRegister('at-list-filter'), i95.AtListFilter),
    new InitEntry(
        const i67.PolymerRegister('at-list-filters'), i96.AtListFilters),
    new InitEntry(const i67.PolymerRegister('at-list'), i97.AtList),
    new InitEntry(
        const i67.PolymerRegister('at-dataset-view'), i98.AtDatasetView),
    new InitEntry(
        const i67.PolymerRegister('at-dataset-search'), i99.AtDatasetSearch),
    new InitEntry(const i67.PolymerRegister('at-list-view'), i100.AtListView),
    new InitEntry(
        const i67.PolymerRegister('at-list-search'), i101.AtListSearch),
    new InitEntry(const i67.PolymerRegister('at-endpoint'), i102.AtEndpoint),
    new InitEntry(const i67.PolymerRegister('at-validator'), i103.AtValidator),
  ]);

  return i0.main();
}
