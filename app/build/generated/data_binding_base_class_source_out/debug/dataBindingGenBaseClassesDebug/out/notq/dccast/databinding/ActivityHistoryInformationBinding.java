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

public abstract class ActivityHistoryInformationBinding extends ViewDataBinding {
  @NonNull
  public final SettingsItem clearLiveHistory;

  @NonNull
  public final SettingsItem clearSearchHistory;

  @NonNull
  public final SettingsItem clearVodHistory;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final SettingsItem switchRecentHistory;

  @NonNull
  public final SettingsItem switchRecentView;

  protected ActivityHistoryInformationBinding(Object _bindingComponent, View _root,
      int _localFieldCount, SettingsItem clearLiveHistory, SettingsItem clearSearchHistory,
      SettingsItem clearVodHistory, LayoutBackHeaderBinding header,
      SettingsItem switchRecentHistory, SettingsItem switchRecentView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.clearLiveHistory = clearLiveHistory;
    this.clearSearchHistory = clearSearchHistory;
    this.clearVodHistory = clearVodHistory;
    this.header = header;
    setContainedBinding(this.header);;
    this.switchRecentHistory = switchRecentHistory;
    this.switchRecentView = switchRecentView;
  }

  @NonNull
  public static ActivityHistoryInformationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_history_information, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityHistoryInformationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityHistoryInformationBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_history_information, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityHistoryInformationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_history_information, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityHistoryInformationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityHistoryInformationBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_history_information, null, false, component);
  }

  public static ActivityHistoryInformationBinding bind(@NonNull View view) {
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
  public static ActivityHistoryInformationBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (ActivityHistoryInformationBinding)bind(component, view, notq.dccast.R.layout.activity_history_information);
  }
}
