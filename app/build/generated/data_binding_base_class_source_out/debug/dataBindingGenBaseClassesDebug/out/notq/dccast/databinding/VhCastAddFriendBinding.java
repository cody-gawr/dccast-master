package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhCastAddFriendBinding extends ViewDataBinding {
  @NonNull
  public final CircleImageView ivItemFollowerUser;

  @NonNull
  public final FrameLayout layoutConfirm;

  @NonNull
  public final LinearLayout layoutItem;

  @NonNull
  public final FrameLayout layoutLoad;

  @NonNull
  public final TextView lblConfirm;

  @NonNull
  public final TextView lblFriends;

  @NonNull
  public final TextView lblItemFollowerUsername;

  protected VhCastAddFriendBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CircleImageView ivItemFollowerUser, FrameLayout layoutConfirm, LinearLayout layoutItem,
      FrameLayout layoutLoad, TextView lblConfirm, TextView lblFriends,
      TextView lblItemFollowerUsername) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ivItemFollowerUser = ivItemFollowerUser;
    this.layoutConfirm = layoutConfirm;
    this.layoutItem = layoutItem;
    this.layoutLoad = layoutLoad;
    this.lblConfirm = lblConfirm;
    this.lblFriends = lblFriends;
    this.lblItemFollowerUsername = lblItemFollowerUsername;
  }

  @NonNull
  public static VhCastAddFriendBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_cast_add_friend, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhCastAddFriendBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhCastAddFriendBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_cast_add_friend, root, attachToRoot, component);
  }

  @NonNull
  public static VhCastAddFriendBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_cast_add_friend, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhCastAddFriendBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhCastAddFriendBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_cast_add_friend, null, false, component);
  }

  public static VhCastAddFriendBinding bind(@NonNull View view) {
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
  public static VhCastAddFriendBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhCastAddFriendBinding)bind(component, view, notq.dccast.R.layout.vh_cast_add_friend);
  }
}
