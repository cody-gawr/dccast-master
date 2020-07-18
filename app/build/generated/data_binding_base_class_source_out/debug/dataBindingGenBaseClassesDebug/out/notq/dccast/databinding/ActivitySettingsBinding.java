package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;
import notq.dccast.screens.navigation_menu.settings.SettingsItem;

public abstract class ActivitySettingsBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout bottomSheet;

  @NonNull
  public final View dim;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final SettingsItem itemAutoLogin;

  @NonNull
  public final SettingsItem itemHistory;

  @NonNull
  public final SettingsItem itemInformation;

  @NonNull
  public final SettingsItem itemLicense;

  @NonNull
  public final SettingsItem itemMobileData;

  @NonNull
  public final SettingsItem itemNotification;

  @NonNull
  public final SettingsItem itemPrivacy;

  @NonNull
  public final SettingsItem itemSecurity;

  @NonNull
  public final SettingsItem itemTerms;

  @NonNull
  public final SettingsItem itemVersion;

  @NonNull
  public final LinearLayout layoutLogOut;

  protected ActivitySettingsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout bottomSheet, View dim, LayoutBackHeaderBinding header, SettingsItem itemAutoLogin,
      SettingsItem itemHistory, SettingsItem itemInformation, SettingsItem itemLicense,
      SettingsItem itemMobileData, SettingsItem itemNotification, SettingsItem itemPrivacy,
      SettingsItem itemSecurity, SettingsItem itemTerms, SettingsItem itemVersion,
      LinearLayout layoutLogOut) {
    super(_bindingComponent, _root, _localFieldCount);
    this.bottomSheet = bottomSheet;
    this.dim = dim;
    this.header = header;
    setContainedBinding(this.header);;
    this.itemAutoLogin = itemAutoLogin;
    this.itemHistory = itemHistory;
    this.itemInformation = itemInformation;
    this.itemLicense = itemLicense;
    this.itemMobileData = itemMobileData;
    this.itemNotification = itemNotification;
    this.itemPrivacy = itemPrivacy;
    this.itemSecurity = itemSecurity;
    this.itemTerms = itemTerms;
    this.itemVersion = itemVersion;
    this.layoutLogOut = layoutLogOut;
  }

  @NonNull
  public static ActivitySettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_settings, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivitySettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivitySettingsBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_settings, root, attachToRoot, component);
  }

  @NonNull
  public static ActivitySettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_settings, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivitySettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivitySettingsBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_settings, null, false, component);
  }

  public static ActivitySettingsBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ActivitySettingsBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivitySettingsBinding)bind(component, view, notq.dccast.R.layout.activity_settings);
  }
}
