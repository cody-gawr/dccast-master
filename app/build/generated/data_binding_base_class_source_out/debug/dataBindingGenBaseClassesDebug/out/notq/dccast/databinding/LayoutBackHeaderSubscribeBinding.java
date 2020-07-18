package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutBackHeaderSubscribeBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout backButton;

  @NonNull
  public final FrameLayout btnSubscribe;

  @NonNull
  public final ImageView iconBackHeader;

  @NonNull
  public final ImageView ivActionBtn;

  @NonNull
  public final FrameLayout layoutAction;

  @NonNull
  public final TextView lblHeader;

  @NonNull
  public final TextView subscribe;

  @NonNull
  public final FrameLayout subscribeWrapper;

  @NonNull
  public final Toolbar toolbar;

  protected LayoutBackHeaderSubscribeBinding(Object _bindingComponent, View _root,
      int _localFieldCount, FrameLayout backButton, FrameLayout btnSubscribe,
      ImageView iconBackHeader, ImageView ivActionBtn, FrameLayout layoutAction, TextView lblHeader,
      TextView subscribe, FrameLayout subscribeWrapper, Toolbar toolbar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.backButton = backButton;
    this.btnSubscribe = btnSubscribe;
    this.iconBackHeader = iconBackHeader;
    this.ivActionBtn = ivActionBtn;
    this.layoutAction = layoutAction;
    this.lblHeader = lblHeader;
    this.subscribe = subscribe;
    this.subscribeWrapper = subscribeWrapper;
    this.toolbar = toolbar;
  }

  @NonNull
  public static LayoutBackHeaderSubscribeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_back_header_subscribe, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutBackHeaderSubscribeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutBackHeaderSubscribeBinding>inflateInternal(inflater, notq.dccast.R.layout.layout_back_header_subscribe, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutBackHeaderSubscribeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_back_header_subscribe, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutBackHeaderSubscribeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutBackHeaderSubscribeBinding>inflateInternal(inflater, notq.dccast.R.layout.layout_back_header_subscribe, null, false, component);
  }

  public static LayoutBackHeaderSubscribeBinding bind(@NonNull View view) {
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
  public static LayoutBackHeaderSubscribeBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (LayoutBackHeaderSubscribeBinding)bind(component, view, notq.dccast.R.layout.layout_back_header_subscribe);
  }
}
