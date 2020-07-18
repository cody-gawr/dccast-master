package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;
import notq.dccast.screens.navigation_menu.live.LiveSettingsItem;

public abstract class FragmentBottomLiveSettingsBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout bottomSheet;

  @NonNull
  public final FrameLayout btnSettingsClose;

  @NonNull
  public final View dim;

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
  public final LiveSettingsItem itemRestricted;

  @NonNull
  public final LiveSettingsItem itemSetLock;

  protected FragmentBottomLiveSettingsBinding(Object _bindingComponent, View _root,
      int _localFieldCount, FrameLayout bottomSheet, FrameLayout btnSettingsClose, View dim,
      LiveSettingsItem itemBroadcastQuality, LiveSettingsItem itemCategorySetting,
      LiveSettingsItem itemChatLock, LiveSettingsItem itemLiveDistribute,
      LiveSettingsItem itemLiveLock, LiveSettingsItem itemRestricted,
      LiveSettingsItem itemSetLock) {
    super(_bindingComponent, _root, _localFieldCount);
    this.bottomSheet = bottomSheet;
    this.btnSettingsClose = btnSettingsClose;
    this.dim = dim;
    this.itemBroadcastQuality = itemBroadcastQuality;
    this.itemCategorySetting = itemCategorySetting;
    this.itemChatLock = itemChatLock;
    this.itemLiveDistribute = itemLiveDistribute;
    this.itemLiveLock = itemLiveLock;
    this.itemRestricted = itemRestricted;
    this.itemSetLock = itemSetLock;
  }

  @NonNull
  public static FragmentBottomLiveSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_live_settings, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomLiveSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomLiveSettingsBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_live_settings, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentBottomLiveSettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_live_settings, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomLiveSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomLiveSettingsBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_live_settings, null, false, component);
  }

  public static FragmentBottomLiveSettingsBinding bind(@NonNull View view) {
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
  public static FragmentBottomLiveSettingsBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (FragmentBottomLiveSettingsBinding)bind(component, view, notq.dccast.R.layout.fragment_bottom_live_settings);
  }
}
