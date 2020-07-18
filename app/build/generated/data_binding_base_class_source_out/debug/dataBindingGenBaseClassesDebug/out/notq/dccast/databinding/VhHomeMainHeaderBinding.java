package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.rd.PageIndicatorView;
import java.lang.Deprecated;
import java.lang.Object;
import notq.dccast.custom_view.CustomViewPager;

public abstract class VhHomeMainHeaderBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout layoutPopular;

  @NonNull
  public final PageIndicatorView pageIndicatorView;

  @NonNull
  public final FrameLayout pagerContainer;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final TextView videosType;

  @NonNull
  public final CustomViewPager viewPager;

  protected VhHomeMainHeaderBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout layoutPopular, PageIndicatorView pageIndicatorView, FrameLayout pagerContainer,
      RecyclerView recyclerView, TextView videosType, CustomViewPager viewPager) {
    super(_bindingComponent, _root, _localFieldCount);
    this.layoutPopular = layoutPopular;
    this.pageIndicatorView = pageIndicatorView;
    this.pagerContainer = pagerContainer;
    this.recyclerView = recyclerView;
    this.videosType = videosType;
    this.viewPager = viewPager;
  }

  @NonNull
  public static VhHomeMainHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_home_main_header, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhHomeMainHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhHomeMainHeaderBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_home_main_header, root, attachToRoot, component);
  }

  @NonNull
  public static VhHomeMainHeaderBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_home_main_header, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhHomeMainHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhHomeMainHeaderBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_home_main_header, null, false, component);
  }

  public static VhHomeMainHeaderBinding bind(@NonNull View view) {
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
  public static VhHomeMainHeaderBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhHomeMainHeaderBinding)bind(component, view, notq.dccast.R.layout.vh_home_main_header);
  }
}
