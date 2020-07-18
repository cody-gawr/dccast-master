package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import java.lang.Deprecated;
import java.lang.Object;
import notq.dccast.custom_view.DMotionLayout;

public abstract class ActivityRecentBinding extends ViewDataBinding {
  @NonNull
  public final View backgroundDim;

  @NonNull
  public final FrameLayout containerForRecentVideo;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final DMotionLayout mainMotionLayout;

  @NonNull
  public final TabLayout tabLayout;

  @NonNull
  public final ViewPager viewPager;

  protected ActivityRecentBinding(Object _bindingComponent, View _root, int _localFieldCount,
      View backgroundDim, FrameLayout containerForRecentVideo, LayoutBackHeaderBinding header,
      DMotionLayout mainMotionLayout, TabLayout tabLayout, ViewPager viewPager) {
    super(_bindingComponent, _root, _localFieldCount);
    this.backgroundDim = backgroundDim;
    this.containerForRecentVideo = containerForRecentVideo;
    this.header = header;
    setContainedBinding(this.header);;
    this.mainMotionLayout = mainMotionLayout;
    this.tabLayout = tabLayout;
    this.viewPager = viewPager;
  }

  @NonNull
  public static ActivityRecentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_recent, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityRecentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityRecentBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_recent, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityRecentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_recent, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityRecentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityRecentBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_recent, null, false, component);
  }

  public static ActivityRecentBinding bind(@NonNull View view) {
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
  public static ActivityRecentBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityRecentBinding)bind(component, view, notq.dccast.R.layout.activity_recent);
  }
}
