package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentBottomOptionsBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout btnOptionsClose;

  @NonNull
  public final RecyclerView recyclerView;

  protected FragmentBottomOptionsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout btnOptionsClose, RecyclerView recyclerView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnOptionsClose = btnOptionsClose;
    this.recyclerView = recyclerView;
  }

  @NonNull
  public static FragmentBottomOptionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_options, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomOptionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomOptionsBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_options, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentBottomOptionsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_options, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomOptionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomOptionsBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_options, null, false, component);
  }

  public static FragmentBottomOptionsBinding bind(@NonNull View view) {
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
  public static FragmentBottomOptionsBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentBottomOptionsBinding)bind(component, view, notq.dccast.R.layout.fragment_bottom_options);
  }
}
