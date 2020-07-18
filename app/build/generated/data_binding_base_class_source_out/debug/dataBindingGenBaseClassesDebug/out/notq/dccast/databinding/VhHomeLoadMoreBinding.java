package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhHomeLoadMoreBinding extends ViewDataBinding {
  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final FrameLayout progressLayout;

  protected VhHomeLoadMoreBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ProgressBar progressBar, FrameLayout progressLayout) {
    super(_bindingComponent, _root, _localFieldCount);
    this.progressBar = progressBar;
    this.progressLayout = progressLayout;
  }

  @NonNull
  public static VhHomeLoadMoreBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_home_load_more, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhHomeLoadMoreBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhHomeLoadMoreBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_home_load_more, root, attachToRoot, component);
  }

  @NonNull
  public static VhHomeLoadMoreBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_home_load_more, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhHomeLoadMoreBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhHomeLoadMoreBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_home_load_more, null, false, component);
  }

  public static VhHomeLoadMoreBinding bind(@NonNull View view) {
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
  public static VhHomeLoadMoreBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhHomeLoadMoreBinding)bind(component, view, notq.dccast.R.layout.vh_home_load_more);
  }
}
