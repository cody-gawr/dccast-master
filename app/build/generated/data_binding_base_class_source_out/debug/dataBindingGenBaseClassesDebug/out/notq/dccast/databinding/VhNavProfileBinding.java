package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhNavProfileBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout btnLoginOrNotification;

  @NonNull
  public final FrameLayout btnSettings;

  @NonNull
  public final ImageView imgLoginOrNotification;

  @NonNull
  public final ImageView ivProfileImage;

  @NonNull
  public final LinearLayout layoutMandu;

  @NonNull
  public final TextView lblManduCount;

  @NonNull
  public final TextView lblPointCount;

  @NonNull
  public final TextView lblUsername;

  @NonNull
  public final FrameLayout manduLoading;

  @NonNull
  public final LinearLayout profileInfo;

  @NonNull
  public final LinearLayout userInfoLayout;

  protected VhNavProfileBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout btnLoginOrNotification, FrameLayout btnSettings, ImageView imgLoginOrNotification,
      ImageView ivProfileImage, LinearLayout layoutMandu, TextView lblManduCount,
      TextView lblPointCount, TextView lblUsername, FrameLayout manduLoading,
      LinearLayout profileInfo, LinearLayout userInfoLayout) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnLoginOrNotification = btnLoginOrNotification;
    this.btnSettings = btnSettings;
    this.imgLoginOrNotification = imgLoginOrNotification;
    this.ivProfileImage = ivProfileImage;
    this.layoutMandu = layoutMandu;
    this.lblManduCount = lblManduCount;
    this.lblPointCount = lblPointCount;
    this.lblUsername = lblUsername;
    this.manduLoading = manduLoading;
    this.profileInfo = profileInfo;
    this.userInfoLayout = userInfoLayout;
  }

  @NonNull
  public static VhNavProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_nav_profile, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhNavProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhNavProfileBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_nav_profile, root, attachToRoot, component);
  }

  @NonNull
  public static VhNavProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_nav_profile, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhNavProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhNavProfileBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_nav_profile, null, false, component);
  }

  public static VhNavProfileBinding bind(@NonNull View view) {
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
  public static VhNavProfileBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhNavProfileBinding)bind(component, view, notq.dccast.R.layout.vh_nav_profile);
  }
}
