package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentGroupsBinding extends ViewDataBinding {
  @NonNull
  public final CardView btnAdd;

  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final LinearLayout layoutMyGroup;

  @NonNull
  public final LinearLayout layoutNoData;

  @NonNull
  public final TextView lblGroupCount;

  @NonNull
  public final TextView noData;

  @NonNull
  public final SwipeMenuRecyclerView recyclerView;

  @NonNull
  public final SwipeRefreshLayout refresher;

  protected FragmentGroupsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CardView btnAdd, LottieAnimationView dcLoader, LinearLayout layoutMyGroup,
      LinearLayout layoutNoData, TextView lblGroupCount, TextView noData,
      SwipeMenuRecyclerView recyclerView, SwipeRefreshLayout refresher) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnAdd = btnAdd;
    this.dcLoader = dcLoader;
    this.layoutMyGroup = layoutMyGroup;
    this.layoutNoData = layoutNoData;
    this.lblGroupCount = lblGroupCount;
    this.noData = noData;
    this.recyclerView = recyclerView;
    this.refresher = refresher;
  }

  @NonNull
  public static FragmentGroupsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_groups, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentGroupsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentGroupsBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_groups, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentGroupsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_groups, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentGroupsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentGroupsBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_groups, null, false, component);
  }

  public static FragmentGroupsBinding bind(@NonNull View view) {
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
  public static FragmentGroupsBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentGroupsBinding)bind(component, view, notq.dccast.R.layout.fragment_groups);
  }
}
