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

public abstract class FragmentFriendsBinding extends ViewDataBinding {
  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final CardView layoutFab;

  @NonNull
  public final LinearLayout layoutItem;

  @NonNull
  public final LinearLayout layoutNoData;

  @NonNull
  public final LinearLayout layoutRecent;

  @NonNull
  public final TextView lblFriendRequestCount;

  @NonNull
  public final TextView lblFriendsCount;

  @NonNull
  public final TextView noData;

  @NonNull
  public final SwipeMenuRecyclerView recyclerView;

  @NonNull
  public final SwipeMenuRecyclerView recyclerViewRecent;

  @NonNull
  public final SwipeRefreshLayout refresher;

  protected FragmentFriendsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LottieAnimationView dcLoader, CardView layoutFab, LinearLayout layoutItem,
      LinearLayout layoutNoData, LinearLayout layoutRecent, TextView lblFriendRequestCount,
      TextView lblFriendsCount, TextView noData, SwipeMenuRecyclerView recyclerView,
      SwipeMenuRecyclerView recyclerViewRecent, SwipeRefreshLayout refresher) {
    super(_bindingComponent, _root, _localFieldCount);
    this.dcLoader = dcLoader;
    this.layoutFab = layoutFab;
    this.layoutItem = layoutItem;
    this.layoutNoData = layoutNoData;
    this.layoutRecent = layoutRecent;
    this.lblFriendRequestCount = lblFriendRequestCount;
    this.lblFriendsCount = lblFriendsCount;
    this.noData = noData;
    this.recyclerView = recyclerView;
    this.recyclerViewRecent = recyclerViewRecent;
    this.refresher = refresher;
  }

  @NonNull
  public static FragmentFriendsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_friends, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentFriendsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentFriendsBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_friends, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentFriendsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_friends, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentFriendsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentFriendsBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_friends, null, false, component);
  }

  public static FragmentFriendsBinding bind(@NonNull View view) {
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
  public static FragmentFriendsBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentFriendsBinding)bind(component, view, notq.dccast.R.layout.fragment_friends);
  }
}
