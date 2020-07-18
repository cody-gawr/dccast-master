package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhSearchHistoryBinding extends ViewDataBinding {
  @NonNull
  public final TextView keyword;

  @NonNull
  public final FrameLayout remove;

  protected VhSearchHistoryBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView keyword, FrameLayout remove) {
    super(_bindingComponent, _root, _localFieldCount);
    this.keyword = keyword;
    this.remove = remove;
  }

  @NonNull
  public static VhSearchHistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_search_history, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhSearchHistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhSearchHistoryBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_search_history, root, attachToRoot, component);
  }

  @NonNull
  public static VhSearchHistoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_search_history, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhSearchHistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhSearchHistoryBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_search_history, null, false, component);
  }

  public static VhSearchHistoryBinding bind(@NonNull View view) {
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
  public static VhSearchHistoryBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhSearchHistoryBinding)bind(component, view, notq.dccast.R.layout.vh_search_history);
  }
}
