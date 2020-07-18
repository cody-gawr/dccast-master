package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.airbnb.lottie.LottieAnimationView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentGroupMemberBinding extends ViewDataBinding {
  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final SwipeMenuRecyclerView recyclerView;

  protected FragmentGroupMemberBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LottieAnimationView dcLoader, SwipeMenuRecyclerView recyclerView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.dcLoader = dcLoader;
    this.recyclerView = recyclerView;
  }

  @NonNull
  public static FragmentGroupMemberBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_group_member, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentGroupMemberBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentGroupMemberBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_group_member, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentGroupMemberBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_group_member, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentGroupMemberBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentGroupMemberBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_group_member, null, false, component);
  }

  public static FragmentGroupMemberBinding bind(@NonNull View view) {
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
  public static FragmentGroupMemberBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentGroupMemberBinding)bind(component, view, notq.dccast.R.layout.fragment_group_member);
  }
}
