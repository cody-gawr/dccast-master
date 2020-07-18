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

public abstract class VhSubscribeBinding extends ViewDataBinding {
  @NonNull
  public final ImageView ivItemSubscribeUser;

  @NonNull
  public final LinearLayout layoutItem;

  @NonNull
  public final FrameLayout layoutLoad;

  @NonNull
  public final FrameLayout layoutStatLoad;

  @NonNull
  public final TextView lblItemSubscribeDescription;

  @NonNull
  public final TextView lblItemSubscribeUsername;

  @NonNull
  public final TextView lblOnAir;

  protected VhSubscribeBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView ivItemSubscribeUser, LinearLayout layoutItem, FrameLayout layoutLoad,
      FrameLayout layoutStatLoad, TextView lblItemSubscribeDescription,
      TextView lblItemSubscribeUsername, TextView lblOnAir) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ivItemSubscribeUser = ivItemSubscribeUser;
    this.layoutItem = layoutItem;
    this.layoutLoad = layoutLoad;
    this.layoutStatLoad = layoutStatLoad;
    this.lblItemSubscribeDescription = lblItemSubscribeDescription;
    this.lblItemSubscribeUsername = lblItemSubscribeUsername;
    this.lblOnAir = lblOnAir;
  }

  @NonNull
  public static VhSubscribeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_subscribe, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhSubscribeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhSubscribeBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_subscribe, root, attachToRoot, component);
  }

  @NonNull
  public static VhSubscribeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_subscribe, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhSubscribeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhSubscribeBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_subscribe, null, false, component);
  }

  public static VhSubscribeBinding bind(@NonNull View view) {
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
  public static VhSubscribeBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhSubscribeBinding)bind(component, view, notq.dccast.R.layout.vh_subscribe);
  }
}
