package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentGroupVideoBinding extends ViewDataBinding {
  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final LinearLayout layoutNoData;

  @NonNull
  public final TextView noData;

  @NonNull
  public final RecyclerView recyclerView;

  protected FragmentGroupVideoBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LottieAnimationView dcLoader, LinearLayout layoutNoData, TextView noData,
      RecyclerView recyclerView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.dcLoader = dcLoader;
    this.layoutNoData = layoutNoData;
    this.noData = noData;
    this.recyclerView = recyclerView;
  }

  @NonNull
  public static FragmentGroupVideoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_group_video, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentGroupVideoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentGroupVideoBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_group_video, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentGroupVideoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_group_video, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentGroupVideoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentGroupVideoBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_group_video, null, false, component);
  }

  public static FragmentGroupVideoBinding bind(@NonNull View view) {
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
  public static FragmentGroupVideoBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentGroupVideoBinding)bind(component, view, notq.dccast.R.layout.fragment_group_video);
  }
}
