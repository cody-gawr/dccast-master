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

public abstract class VhCastSearchFriendBinding extends ViewDataBinding {
  @NonNull
  public final CircleImageView ivItemFollowerUser;

  @NonNull
  public final FrameLayout layoutAddFriend;

  @NonNull
  public final FrameLayout layoutCheck;

  @NonNull
  public final LinearLayout layoutItem;

  @NonNull
  public final FrameLayout layoutLoad;

  @NonNull
  public final TextView lblAddFriend;

  @NonNull
  public final TextView lblFriends;

  @NonNull
  public final TextView lblItemFollowerUsername;

  protected VhCastSearchFriendBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CircleImageView ivItemFollowerUser, FrameLayout layoutAddFriend, FrameLayout layoutCheck,
      LinearLayout layoutItem, FrameLayout layoutLoad, TextView lblAddFriend, TextView lblFriends,
      TextView lblItemFollowerUsername) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ivItemFollowerUser = ivItemFollowerUser;
    this.layoutAddFriend = layoutAddFriend;
    this.layoutCheck = layoutCheck;
    this.layoutItem = layoutItem;
    this.layoutLoad = layoutLoad;
    this.lblAddFriend = lblAddFriend;
    this.lblFriends = lblFriends;
    this.lblItemFollowerUsername = lblItemFollowerUsername;
  }

  @NonNull
  public static VhCastSearchFriendBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_cast_search_friend, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhCastSearchFriendBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhCastSearchFriendBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_cast_search_friend, root, attachToRoot, component);
  }

  @NonNull
  public static VhCastSearchFriendBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_cast_search_friend, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhCastSearchFriendBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhCastSearchFriendBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_cast_search_friend, null, false, component);
  }

  public static VhCastSearchFriendBinding bind(@NonNull View view) {
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
  public static VhCastSearchFriendBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhCastSearchFriendBinding)bind(component, view, notq.dccast.R.layout.vh_cast_search_friend);
  }
}
