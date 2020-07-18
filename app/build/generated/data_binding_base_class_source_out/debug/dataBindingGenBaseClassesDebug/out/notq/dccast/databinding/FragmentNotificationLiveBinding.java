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
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentNotificationLiveBinding extends ViewDataBinding {
  @NonNull
  public final View backgroundDim;

  @NonNull
  public final FrameLayout containerForNotificationVideo;

  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final LinearLayout layoutNoData;

  @NonNull
  public final MotionLayout mainMotionLayout;

  @NonNull
  public final TextView noData;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final SwipeRefreshLayout refresher;

  protected FragmentNotificationLiveBinding(Object _bindingComponent, View _root,
      int _localFieldCount, View backgroundDim, FrameLayout containerForNotificationVideo,
      LottieAnimationView dcLoader, LinearLayout layoutNoData, MotionLayout mainMotionLayout,
      TextView noData, RecyclerView recyclerView, SwipeRefreshLayout refresher) {
    super(_bindingComponent, _root, _localFieldCount);
    this.backgroundDim = backgroundDim;
    this.containerForNotificationVideo = containerForNotificationVideo;
    this.dcLoader = dcLoader;
    this.layoutNoData = layoutNoData;
    this.mainMotionLayout = mainMotionLayout;
    this.noData = noData;
    this.recyclerView = recyclerView;
    this.refresher = refresher;
  }

  @NonNull
  public static FragmentNotificationLiveBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_notification_live, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentNotificationLiveBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentNotificationLiveBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_notification_live, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentNotificationLiveBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_notification_live, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentNotificationLiveBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentNotificationLiveBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_notification_live, null, false, component);
  }

  public static FragmentNotificationLiveBinding bind(@NonNull View view) {
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
  public static FragmentNotificationLiveBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (FragmentNotificationLiveBinding)bind(component, view, notq.dccast.R.layout.fragment_notification_live);
  }
}
