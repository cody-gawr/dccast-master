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
import com.airbnb.lottie.LottieAnimationView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentContentRecentBinding extends ViewDataBinding {
  @NonNull
  public final View backgroundDim;

  @NonNull
  public final FrameLayout containerForContentVideo;

  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final MotionLayout mainMotionLayout;

  @NonNull
  public final RecyclerView recyclerView;

  protected FragmentContentRecentBinding(Object _bindingComponent, View _root, int _localFieldCount,
      View backgroundDim, FrameLayout containerForContentVideo, LottieAnimationView dcLoader,
      MotionLayout mainMotionLayout, RecyclerView recyclerView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.backgroundDim = backgroundDim;
    this.containerForContentVideo = containerForContentVideo;
    this.dcLoader = dcLoader;
    this.mainMotionLayout = mainMotionLayout;
    this.recyclerView = recyclerView;
  }

  @NonNull
  public static FragmentContentRecentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_content_recent, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentContentRecentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentContentRecentBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_content_recent, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentContentRecentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_content_recent, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentContentRecentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentContentRecentBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_content_recent, null, false, component);
  }

  public static FragmentContentRecentBinding bind(@NonNull View view) {
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
  public static FragmentContentRecentBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentContentRecentBinding)bind(component, view, notq.dccast.R.layout.fragment_content_recent);
  }
}
