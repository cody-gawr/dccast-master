package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.appbar.AppBarLayout;
import java.lang.Deprecated;
import java.lang.Object;
import notq.dccast.custom_view.DMotionLayout;

public abstract class ActivityMyChannelBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appBar;

  @NonNull
  public final View backgroundDim;

  @NonNull
  public final FrameLayout bottomSheet;

  @NonNull
  public final FrameLayout containerForChannelVideo;

  @NonNull
  public final View dim;

  @NonNull
  public final EditText etSearch;

  @NonNull
  public final LayoutBackHeaderSubscribeBinding header;

  @NonNull
  public final ImageView ivChannelUser;

  @NonNull
  public final LinearLayout layoutSort;

  @NonNull
  public final LinearLayout layoutUserInfo;

  @NonNull
  public final TextView lblChannelUser;

  @NonNull
  public final TextView lblChannelUserDescription;

  @NonNull
  public final TextView lblFollowers;

  @NonNull
  public final TextView lblFollowing;

  @NonNull
  public final TextView lblSortValue;

  @NonNull
  public final TextView lblSubscriber;

  @NonNull
  public final DMotionLayout mainMotionLayout;

  @NonNull
  public final ProgressBar statLoader;

  @NonNull
  public final ViewPager viewPager;

  protected ActivityMyChannelBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AppBarLayout appBar, View backgroundDim, FrameLayout bottomSheet,
      FrameLayout containerForChannelVideo, View dim, EditText etSearch,
      LayoutBackHeaderSubscribeBinding header, ImageView ivChannelUser, LinearLayout layoutSort,
      LinearLayout layoutUserInfo, TextView lblChannelUser, TextView lblChannelUserDescription,
      TextView lblFollowers, TextView lblFollowing, TextView lblSortValue, TextView lblSubscriber,
      DMotionLayout mainMotionLayout, ProgressBar statLoader, ViewPager viewPager) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appBar = appBar;
    this.backgroundDim = backgroundDim;
    this.bottomSheet = bottomSheet;
    this.containerForChannelVideo = containerForChannelVideo;
    this.dim = dim;
    this.etSearch = etSearch;
    this.header = header;
    setContainedBinding(this.header);;
    this.ivChannelUser = ivChannelUser;
    this.layoutSort = layoutSort;
    this.layoutUserInfo = layoutUserInfo;
    this.lblChannelUser = lblChannelUser;
    this.lblChannelUserDescription = lblChannelUserDescription;
    this.lblFollowers = lblFollowers;
    this.lblFollowing = lblFollowing;
    this.lblSortValue = lblSortValue;
    this.lblSubscriber = lblSubscriber;
    this.mainMotionLayout = mainMotionLayout;
    this.statLoader = statLoader;
    this.viewPager = viewPager;
  }

  @NonNull
  public static ActivityMyChannelBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_my_channel, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMyChannelBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityMyChannelBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_my_channel, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityMyChannelBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_my_channel, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMyChannelBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityMyChannelBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_my_channel, null, false, component);
  }

  public static ActivityMyChannelBinding bind(@NonNull View view) {
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
  public static ActivityMyChannelBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityMyChannelBinding)bind(component, view, notq.dccast.R.layout.activity_my_channel);
  }
}
