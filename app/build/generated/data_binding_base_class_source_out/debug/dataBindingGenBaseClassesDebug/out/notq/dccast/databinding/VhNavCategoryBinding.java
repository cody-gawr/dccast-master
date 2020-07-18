package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhNavCategoryBinding extends ViewDataBinding {
  @NonNull
  public final RecyclerView categoryRv;

  @NonNull
  public final TabLayout tabLayout;

  protected VhNavCategoryBinding(Object _bindingComponent, View _root, int _localFieldCount,
      RecyclerView categoryRv, TabLayout tabLayout) {
    super(_bindingComponent, _root, _localFieldCount);
    this.categoryRv = categoryRv;
    this.tabLayout = tabLayout;
  }

  @NonNull
  public static VhNavCategoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_nav_category, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhNavCategoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhNavCategoryBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_nav_category, root, attachToRoot, component);
  }

  @NonNull
  public static VhNavCategoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_nav_category, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhNavCategoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhNavCategoryBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_nav_category, null, false, component);
  }

  public static VhNavCategoryBinding bind(@NonNull View view) {
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
  public static VhNavCategoryBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhNavCategoryBinding)bind(component, view, notq.dccast.R.layout.vh_nav_category);
  }
}
