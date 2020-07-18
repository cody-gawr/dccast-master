package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhHomeSubHeaderBinding extends ViewDataBinding {
  @NonNull
  public final TextView btnSeeAll;

  @NonNull
  public final LinearLayout container;

  @NonNull
  public final TextView videosType;

  protected VhHomeSubHeaderBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView btnSeeAll, LinearLayout container, TextView videosType) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnSeeAll = btnSeeAll;
    this.container = container;
    this.videosType = videosType;
  }

  @NonNull
  public static VhHomeSubHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_home_sub_header, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhHomeSubHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhHomeSubHeaderBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_home_sub_header, root, attachToRoot, component);
  }

  @NonNull
  public static VhHomeSubHeaderBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_home_sub_header, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhHomeSubHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhHomeSubHeaderBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_home_sub_header, null, false, component);
  }

  public static VhHomeSubHeaderBinding bind(@NonNull View view) {
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
  public static VhHomeSubHeaderBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhHomeSubHeaderBinding)bind(component, view, notq.dccast.R.layout.vh_home_sub_header);
  }
}
