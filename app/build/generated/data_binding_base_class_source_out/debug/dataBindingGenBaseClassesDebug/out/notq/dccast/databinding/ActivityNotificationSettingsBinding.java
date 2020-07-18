package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;
import notq.dccast.screens.navigation_menu.settings.SettingsItem;

public abstract class ActivityNotificationSettingsBinding extends ViewDataBinding {
  @NonNull
  public final SettingsItem allNotification;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final SettingsItem liveNotification;

  @NonNull
  public final SettingsItem vodNotification;

  protected ActivityNotificationSettingsBinding(Object _bindingComponent, View _root,
      int _localFieldCount, SettingsItem allNotification, LayoutBackHeaderBinding header,
      SettingsItem liveNotification, SettingsItem vodNotification) {
    super(_bindingComponent, _root, _localFieldCount);
    this.allNotification = allNotification;
    this.header = header;
    setContainedBinding(this.header);;
    this.liveNotification = liveNotification;
    this.vodNotification = vodNotification;
  }

  @NonNull
  public static ActivityNotificationSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_notification_settings, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityNotificationSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityNotificationSettingsBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_notification_settings, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityNotificationSettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_notification_settings, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityNotificationSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityNotificationSettingsBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_notification_settings, null, false, component);
  }

  public static ActivityNotificationSettingsBinding bind(@NonNull View view) {
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
  public static ActivityNotificationSettingsBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (ActivityNotificationSettingsBinding)bind(component, view, notq.dccast.R.layout.activity_notification_settings);
  }
}
