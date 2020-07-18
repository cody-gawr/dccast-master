package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityHomeToolbarBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appBar;

  @NonNull
  public final FrameLayout homeButton;

  @NonNull
  public final ImageView homeButtonImage;

  @NonNull
  public final FrameLayout searchButton;

  @NonNull
  public final ImageView searchButtonImage;

  @NonNull
  public final TabLayout tabLayout;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final TextView toolbarTitle;

  protected ActivityHomeToolbarBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AppBarLayout appBar, FrameLayout homeButton, ImageView homeButtonImage,
      FrameLayout searchButton, ImageView searchButtonImage, TabLayout tabLayout, Toolbar toolbar,
      TextView toolbarTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appBar = appBar;
    this.homeButton = homeButton;
    this.homeButtonImage = homeButtonImage;
    this.searchButton = searchButton;
    this.searchButtonImage = searchButtonImage;
    this.tabLayout = tabLayout;
    this.toolbar = toolbar;
    this.toolbarTitle = toolbarTitle;
  }

  @NonNull
  public static ActivityHomeToolbarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_home_toolbar, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityHomeToolbarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityHomeToolbarBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_home_toolbar, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityHomeToolbarBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_home_toolbar, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityHomeToolbarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityHomeToolbarBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_home_toolbar, null, false, component);
  }

  public static ActivityHomeToolbarBinding bind(@NonNull View view) {
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
  public static ActivityHomeToolbarBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityHomeToolbarBinding)bind(component, view, notq.dccast.R.layout.activity_home_toolbar);
  }
}
