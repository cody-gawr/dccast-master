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

public abstract class VhCastFollowerBinding extends ViewDataBinding {
  @NonNull
  public final CircleImageView ivItemFollowerUser;

  @NonNull
  public final LinearLayout layoutItem;

  @NonNull
  public final FrameLayout layoutLoad;

  @NonNull
  public final TextView lblFollow;

  @NonNull
  public final TextView lblItemFollowerUsername;

  @NonNull
  public final TextView lblItemSubscribeDescription;

  @NonNull
  public final TextView lblOnAir;

  protected VhCastFollowerBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CircleImageView ivItemFollowerUser, LinearLayout layoutItem, FrameLayout layoutLoad,
      TextView lblFollow, TextView lblItemFollowerUsername, TextView lblItemSubscribeDescription,
      TextView lblOnAir) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ivItemFollowerUser = ivItemFollowerUser;
    this.layoutItem = layoutItem;
    this.layoutLoad = layoutLoad;
    this.lblFollow = lblFollow;
    this.lblItemFollowerUsername = lblItemFollowerUsername;
    this.lblItemSubscribeDescription = lblItemSubscribeDescription;
    this.lblOnAir = lblOnAir;
  }

  @NonNull
  public static VhCastFollowerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_cast_follower, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhCastFollowerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhCastFollowerBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_cast_follower, root, attachToRoot, component);
  }

  @NonNull
  public static VhCastFollowerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_cast_follower, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhCastFollowerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhCastFollowerBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_cast_follower, null, false, component);
  }

  public static VhCastFollowerBinding bind(@NonNull View view) {
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
  public static VhCastFollowerBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhCastFollowerBinding)bind(component, view, notq.dccast.R.layout.vh_cast_follower);
  }
}
