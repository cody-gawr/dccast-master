package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentFollowersBinding extends ViewDataBinding {
  @NonNull
  public final View backgroundDim;

  @NonNull
  public final FrameLayout containerForCastListVideo;

  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final LinearLayout layoutItem;

  @NonNull
  public final LinearLayout layoutMyLiveHistory;

  @NonNull
  public final LinearLayout layoutNoData;

  @NonNull
  public final TextView lblFollowerCount;

  @NonNull
  public final TextView lblMyLiveHistory;

  @NonNull
  public final MotionLayout mainMotionLayout;

  @NonNull
  public final TextView noData;

  @NonNull
  public final SwipeMenuRecyclerView recyclerView;

  @NonNull
  public final RecyclerView recyclerViewMyLive;

  @NonNull
  public final SwipeRefreshLayout refresher;

  protected FragmentFollowersBinding(Object _bindingComponent, View _root, int _localFieldCount,
      View backgroundDim, FrameLayout containerForCastListVideo, LottieAnimationView dcLoader,
      LinearLayout layoutItem, LinearLayout layoutMyLiveHistory, LinearLayout layoutNoData,
      TextView lblFollowerCount, TextView lblMyLiveHistory, MotionLayout mainMotionLayout,
      TextView noData, SwipeMenuRecyclerView recyclerView, RecyclerView recyclerViewMyLive,
      SwipeRefreshLayout refresher) {
    super(_bindingComponent, _root, _localFieldCount);
    this.backgroundDim = backgroundDim;
    this.containerForCastListVideo = containerForCastListVideo;
    this.dcLoader = dcLoader;
    this.layoutItem = layoutItem;
    this.layoutMyLiveHistory = layoutMyLiveHistory;
    this.layoutNoData = layoutNoData;
    this.lblFollowerCount = lblFollowerCount;
    this.lblMyLiveHistory = lblMyLiveHistory;
    this.mainMotionLayout = mainMotionLayout;
    this.noData = noData;
    this.recyclerView = recyclerView;
    this.recyclerViewMyLive = recyclerViewMyLive;
    this.refresher = refresher;
  }

  @NonNull
  public static FragmentFollowersBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_followers, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentFollowersBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentFollowersBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_followers, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentFollowersBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_followers, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentFollowersBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentFollowersBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_followers, null, false, component);
  }

  public static FragmentFollowersBinding bind(@NonNull View view) {
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
  public static FragmentFollowersBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentFollowersBinding)bind(component, view, notq.dccast.R.layout.fragment_followers);
  }
}
