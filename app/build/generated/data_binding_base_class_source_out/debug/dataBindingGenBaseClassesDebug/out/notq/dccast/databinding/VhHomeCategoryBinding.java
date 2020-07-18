package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.tabs.TabLayout;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhHomeCategoryBinding extends ViewDataBinding {
  @NonNull
  public final TabLayout tabLayoutCategories;

  protected VhHomeCategoryBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TabLayout tabLayoutCategories) {
    super(_bindingComponent, _root, _localFieldCount);
    this.tabLayoutCategories = tabLayoutCategories;
  }

  @NonNull
  public static VhHomeCategoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_home_category, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhHomeCategoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhHomeCategoryBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_home_category, root, attachToRoot, component);
  }

  @NonNull
  public static VhHomeCategoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_home_category, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhHomeCategoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhHomeCategoryBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_home_category, null, false, component);
  }

  public static VhHomeCategoryBinding bind(@NonNull View view) {
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
  public static VhHomeCategoryBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhHomeCategoryBinding)bind(component, view, notq.dccast.R.layout.vh_home_category);
  }
}
