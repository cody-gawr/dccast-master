package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityHomeBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout container;

  @NonNull
  public final DrawerLayout drawerLayout;

  @NonNull
  public final RecyclerView navigationDrawerRecyclerview;

  @NonNull
  public final ActivityHomeToolbarBinding toolbarContainer;

  protected ActivityHomeBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout container, DrawerLayout drawerLayout, RecyclerView navigationDrawerRecyclerview,
      ActivityHomeToolbarBinding toolbarContainer) {
    super(_bindingComponent, _root, _localFieldCount);
    this.container = container;
    this.drawerLayout = drawerLayout;
    this.navigationDrawerRecyclerview = navigationDrawerRecyclerview;
    this.toolbarContainer = toolbarContainer;
    setContainedBinding(this.toolbarContainer);;
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_home, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityHomeBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_home, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_home, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityHomeBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_home, null, false, component);
  }

  public static ActivityHomeBinding bind(@NonNull View view) {
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
  public static ActivityHomeBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityHomeBinding)bind(component, view, notq.dccast.R.layout.activity_home);
  }
}
