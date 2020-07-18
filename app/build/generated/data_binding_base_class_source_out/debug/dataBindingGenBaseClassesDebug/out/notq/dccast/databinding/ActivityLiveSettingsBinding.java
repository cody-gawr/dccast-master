package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;
import notq.dccast.screens.navigation_menu.live.LiveSettingsItem;

public abstract class ActivityLiveSettingsBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout bottomSheet;

  @NonNull
  public final View dim;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final LiveSettingsItem itemBroadcastOrientation;

  @NonNull
  public final LiveSettingsItem itemBroadcastQuality;

  @NonNull
  public final LiveSettingsItem itemCategorySetting;

  @NonNull
  public final LiveSettingsItem itemChatLock;

  @NonNull
  public final LiveSettingsItem itemLiveDistribute;

  @NonNull
  public final LiveSettingsItem itemLiveLock;

  @NonNull
  public final LinearLayout itemLiveTitle;

  @NonNull
  public final LiveSettingsItem itemLiveType;

  @NonNull
  public final LiveSettingsItem itemRestricted;

  @NonNull
  public final LiveSettingsItem itemSetLock;

  @NonNull
  public final Button startLiveBtn;

  @NonNull
  public final AppCompatEditText title;

  protected ActivityLiveSettingsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout bottomSheet, View dim, LayoutBackHeaderBinding header,
      LiveSettingsItem itemBroadcastOrientation, LiveSettingsItem itemBroadcastQuality,
      LiveSettingsItem itemCategorySetting, LiveSettingsItem itemChatLock,
      LiveSettingsItem itemLiveDistribute, LiveSettingsItem itemLiveLock,
      LinearLayout itemLiveTitle, LiveSettingsItem itemLiveType, LiveSettingsItem itemRestricted,
      LiveSettingsItem itemSetLock, Button startLiveBtn, AppCompatEditText title) {
    super(_bindingComponent, _root, _localFieldCount);
    this.bottomSheet = bottomSheet;
    this.dim = dim;
    this.header = header;
    setContainedBinding(this.header);;
    this.itemBroadcastOrientation = itemBroadcastOrientation;
    this.itemBroadcastQuality = itemBroadcastQuality;
    this.itemCategorySetting = itemCategorySetting;
    this.itemChatLock = itemChatLock;
    this.itemLiveDistribute = itemLiveDistribute;
    this.itemLiveLock = itemLiveLock;
    this.itemLiveTitle = itemLiveTitle;
    this.itemLiveType = itemLiveType;
    this.itemRestricted = itemRestricted;
    this.itemSetLock = itemSetLock;
    this.startLiveBtn = startLiveBtn;
    this.title = title;
  }

  @NonNull
  public static ActivityLiveSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_live_settings, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityLiveSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityLiveSettingsBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_live_settings, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityLiveSettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_live_settings, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityLiveSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityLiveSettingsBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_live_settings, null, false, component);
  }

  public static ActivityLiveSettingsBinding bind(@NonNull View view) {
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
  public static ActivityLiveSettingsBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityLiveSettingsBinding)bind(component, view, notq.dccast.R.layout.activity_live_settings);
  }
}
