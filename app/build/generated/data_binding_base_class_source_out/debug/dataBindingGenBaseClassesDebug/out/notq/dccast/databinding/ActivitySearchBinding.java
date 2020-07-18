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
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import java.lang.Deprecated;
import java.lang.Object;
import notq.dccast.custom_view.DMotionLayout;

public abstract class ActivitySearchBinding extends ViewDataBinding {
  @NonNull
  public final View backgroundDim;

  @NonNull
  public final FrameLayout containerForVideoSearch;

  @NonNull
  public final DMotionLayout mainMotionLayout;

  @NonNull
  public final RecyclerView searchHistoryRecyclerView;

  @NonNull
  public final ViewPager searchPager;

  @NonNull
  public final TabLayout tabLayout;

  @NonNull
  public final ActivitySearchToolbarBinding toolbarContainer;

  protected ActivitySearchBinding(Object _bindingComponent, View _root, int _localFieldCount,
      View backgroundDim, FrameLayout containerForVideoSearch, DMotionLayout mainMotionLayout,
      RecyclerView searchHistoryRecyclerView, ViewPager searchPager, TabLayout tabLayout,
      ActivitySearchToolbarBinding toolbarContainer) {
    super(_bindingComponent, _root, _localFieldCount);
    this.backgroundDim = backgroundDim;
    this.containerForVideoSearch = containerForVideoSearch;
    this.mainMotionLayout = mainMotionLayout;
    this.searchHistoryRecyclerView = searchHistoryRecyclerView;
    this.searchPager = searchPager;
    this.tabLayout = tabLayout;
    this.toolbarContainer = toolbarContainer;
    setContainedBinding(this.toolbarContainer);;
  }

  @NonNull
  public static ActivitySearchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_search, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivitySearchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivitySearchBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_search, root, attachToRoot, component);
  }

  @NonNull
  public static ActivitySearchBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_search, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivitySearchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivitySearchBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_search, null, false, component);
  }

  public static ActivitySearchBinding bind(@NonNull View view) {
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
  public static ActivitySearchBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivitySearchBinding)bind(component, view, notq.dccast.R.layout.activity_search);
  }
}
