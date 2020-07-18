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

public abstract class VhGroupMemberBinding extends ViewDataBinding {
  @NonNull
  public final CircleImageView ivUser;

  @NonNull
  public final LinearLayout layoutItem;

  @NonNull
  public final FrameLayout layoutLoad;

  @NonNull
  public final TextView lblName;

  protected VhGroupMemberBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CircleImageView ivUser, LinearLayout layoutItem, FrameLayout layoutLoad, TextView lblName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ivUser = ivUser;
    this.layoutItem = layoutItem;
    this.layoutLoad = layoutLoad;
    this.lblName = lblName;
  }

  @NonNull
  public static VhGroupMemberBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_group_member, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhGroupMemberBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhGroupMemberBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_group_member, root, attachToRoot, component);
  }

  @NonNull
  public static VhGroupMemberBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_group_member, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhGroupMemberBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhGroupMemberBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_group_member, null, false, component);
  }

  public static VhGroupMemberBinding bind(@NonNull View view) {
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
  public static VhGroupMemberBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhGroupMemberBinding)bind(component, view, notq.dccast.R.layout.vh_group_member);
  }
}
