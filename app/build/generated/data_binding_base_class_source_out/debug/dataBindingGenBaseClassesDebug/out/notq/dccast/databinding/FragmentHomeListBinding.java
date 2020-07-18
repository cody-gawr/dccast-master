package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

public abstract class FragmentHomeListBinding extends ViewDataBinding {
  @NonNull
  public final LayoutAdsBinding ads;

  @NonNull
  public final View backgroundDim;

  @NonNull
  public final FrameLayout containerForVideo;

  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final MotionLayout mainMotionLayout;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final SwipeRefreshLayout refresher;

  protected FragmentHomeListBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LayoutAdsBinding ads, View backgroundDim, FrameLayout containerForVideo,
      LottieAnimationView dcLoader, MotionLayout mainMotionLayout, RecyclerView recyclerView,
      SwipeRefreshLayout refresher) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ads = ads;
    setContainedBinding(this.ads);;
    this.backgroundDim = backgroundDim;
    this.containerForVideo = containerForVideo;
    this.dcLoader = dcLoader;
    this.mainMotionLayout = mainMotionLayout;
    this.recyclerView = recyclerView;
    this.refresher = refresher;
  }

  @NonNull
  public static FragmentHomeListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_home_list, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentHomeListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentHomeListBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_home_list, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentHomeListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_home_list, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentHomeListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentHomeListBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_home_list, null, false, component);
  }

  public static FragmentHomeListBinding bind(@NonNull View view) {
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
  public static FragmentHomeListBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentHomeListBinding)bind(component, view, notq.dccast.R.layout.fragment_home_list);
  }
}
