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
import com.airbnb.lottie.LottieAnimationView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityAddFriendBinding extends ViewDataBinding {
  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final LinearLayout layoutItem;

  @NonNull
  public final LinearLayout layoutNoData;

  @NonNull
  public final TextView lblResultCount;

  @NonNull
  public final TextView noData;

  @NonNull
  public final SwipeMenuRecyclerView recyclerView;

  protected ActivityAddFriendBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LottieAnimationView dcLoader, LayoutBackHeaderBinding header, LinearLayout layoutItem,
      LinearLayout layoutNoData, TextView lblResultCount, TextView noData,
      SwipeMenuRecyclerView recyclerView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.dcLoader = dcLoader;
    this.header = header;
    setContainedBinding(this.header);;
    this.layoutItem = layoutItem;
    this.layoutNoData = layoutNoData;
    this.lblResultCount = lblResultCount;
    this.noData = noData;
    this.recyclerView = recyclerView;
  }

  @NonNull
  public static ActivityAddFriendBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_add_friend, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityAddFriendBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityAddFriendBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_add_friend, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityAddFriendBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_add_friend, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityAddFriendBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityAddFriendBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_add_friend, null, false, component);
  }

  public static ActivityAddFriendBinding bind(@NonNull View view) {
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
  public static ActivityAddFriendBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityAddFriendBinding)bind(component, view, notq.dccast.R.layout.activity_add_friend);
  }
}
