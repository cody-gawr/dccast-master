package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentFollowingBinding extends ViewDataBinding {
  @NonNull
  public final View backgroundDim;

  @NonNull
  public final FrameLayout containerForFollowingVideo;

  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final LottieAnimationView dcNoData;

  @NonNull
  public final CardView layoutFab;

  @NonNull
  public final LinearLayout layoutItem;

  @NonNull
  public final LinearLayout layoutNoData;

  @NonNull
  public final LinearLayout layoutRecent;

  @NonNull
  public final TextView lblFollowingCount;

  @NonNull
  public final TextView lblUnfollowingCount;

  @NonNull
  public final MotionLayout mainMotionLayout;

  @NonNull
  public final SwipeMenuRecyclerView recyclerView;

  @NonNull
  public final RecyclerView recyclerViewRecent;

  @NonNull
  public final SwipeRefreshLayout refresher;

  protected FragmentFollowingBinding(Object _bindingComponent, View _root, int _localFieldCount,
      View backgroundDim, FrameLayout containerForFollowingVideo, LottieAnimationView dcLoader,
      LottieAnimationView dcNoData, CardView layoutFab, LinearLayout layoutItem,
      LinearLayout layoutNoData, LinearLayout layoutRecent, TextView lblFollowingCount,
      TextView lblUnfollowingCount, MotionLayout mainMotionLayout,
      SwipeMenuRecyclerView recyclerView, RecyclerView recyclerViewRecent,
      SwipeRefreshLayout refresher) {
    super(_bindingComponent, _root, _localFieldCount);
    this.backgroundDim = backgroundDim;
    this.containerForFollowingVideo = containerForFollowingVideo;
    this.dcLoader = dcLoader;
    this.dcNoData = dcNoData;
    this.layoutFab = layoutFab;
    this.layoutItem = layoutItem;
    this.layoutNoData = layoutNoData;
    this.layoutRecent = layoutRecent;
    this.lblFollowingCount = lblFollowingCount;
    this.lblUnfollowingCount = lblUnfollowingCount;
    this.mainMotionLayout = mainMotionLayout;
    this.recyclerView = recyclerView;
    this.recyclerViewRecent = recyclerViewRecent;
    this.refresher = refresher;
  }

  @NonNull
  public static FragmentFollowingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_following, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentFollowingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentFollowingBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_following, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentFollowingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_following, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentFollowingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentFollowingBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_following, null, false, component);
  }

  public static FragmentFollowingBinding bind(@NonNull View view) {
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
  public static FragmentFollowingBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentFollowingBinding)bind(component, view, notq.dccast.R.layout.fragment_following);
  }
}
