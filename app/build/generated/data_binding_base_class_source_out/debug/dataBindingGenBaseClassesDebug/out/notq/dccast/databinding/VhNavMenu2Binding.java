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

public abstract class VhNavMenu2Binding extends ViewDataBinding {
  @NonNull
  public final LinearLayout itemLive;

  @NonNull
  public final FrameLayout itemLiveContainer;

  @NonNull
  public final LinearLayout itemVod;

  @NonNull
  public final FrameLayout itemVodContainer;

  protected VhNavMenu2Binding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout itemLive, FrameLayout itemLiveContainer, LinearLayout itemVod,
      FrameLayout itemVodContainer) {
    super(_bindingComponent, _root, _localFieldCount);
    this.itemLive = itemLive;
    this.itemLiveContainer = itemLiveContainer;
    this.itemVod = itemVod;
    this.itemVodContainer = itemVodContainer;
  }

  @NonNull
  public static VhNavMenu2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_nav_menu_2, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhNavMenu2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhNavMenu2Binding>inflateInternal(inflater, notq.dccast.R.layout.vh_nav_menu_2, root, attachToRoot, component);
  }

  @NonNull
  public static VhNavMenu2Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_nav_menu_2, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhNavMenu2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhNavMenu2Binding>inflateInternal(inflater, notq.dccast.R.layout.vh_nav_menu_2, null, false, component);
  }

  public static VhNavMenu2Binding bind(@NonNull View view) {
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
  public static VhNavMenu2Binding bind(@NonNull View view, @Nullable Object component) {
    return (VhNavMenu2Binding)bind(component, view, notq.dccast.R.layout.vh_nav_menu_2);
  }
}
