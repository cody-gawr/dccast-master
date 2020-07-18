package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import java.lang.Deprecated;
import java.lang.Object;
import notq.dccast.custom_view.DisableSwipeViewPager;

public abstract class FragmentCastBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appBar;

  @NonNull
  public final TabLayout tabLayout;

  @NonNull
  public final DisableSwipeViewPager viewPager;

  protected FragmentCastBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AppBarLayout appBar, TabLayout tabLayout, DisableSwipeViewPager viewPager) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appBar = appBar;
    this.tabLayout = tabLayout;
    this.viewPager = viewPager;
  }

  @NonNull
  public static FragmentCastBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_cast, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentCastBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentCastBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_cast, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentCastBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_cast, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentCastBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentCastBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_cast, null, false, component);
  }

  public static FragmentCastBinding bind(@NonNull View view) {
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
  public static FragmentCastBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentCastBinding)bind(component, view, notq.dccast.R.layout.fragment_cast);
  }
}
