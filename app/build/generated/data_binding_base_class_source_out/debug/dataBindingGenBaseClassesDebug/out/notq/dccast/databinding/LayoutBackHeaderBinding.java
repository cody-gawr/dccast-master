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

public abstract class LayoutBackHeaderBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout backButton;

  @NonNull
  public final ImageView iconBackHeader;

  @NonNull
  public final ImageView iconSearchHeader;

  @NonNull
  public final ImageView ivActionBtn;

  @NonNull
  public final FrameLayout layoutAction;

  @NonNull
  public final TextView lblActionBtn;

  @NonNull
  public final TextView lblHeader;

  @NonNull
  public final FrameLayout searchButton;

  @NonNull
  public final Toolbar toolbar;

  protected LayoutBackHeaderBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout backButton, ImageView iconBackHeader, ImageView iconSearchHeader,
      ImageView ivActionBtn, FrameLayout layoutAction, TextView lblActionBtn, TextView lblHeader,
      FrameLayout searchButton, Toolbar toolbar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.backButton = backButton;
    this.iconBackHeader = iconBackHeader;
    this.iconSearchHeader = iconSearchHeader;
    this.ivActionBtn = ivActionBtn;
    this.layoutAction = layoutAction;
    this.lblActionBtn = lblActionBtn;
    this.lblHeader = lblHeader;
    this.searchButton = searchButton;
    this.toolbar = toolbar;
  }

  @NonNull
  public static LayoutBackHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_back_header, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutBackHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutBackHeaderBinding>inflateInternal(inflater, notq.dccast.R.layout.layout_back_header, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutBackHeaderBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_back_header, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutBackHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutBackHeaderBinding>inflateInternal(inflater, notq.dccast.R.layout.layout_back_header, null, false, component);
  }

  public static LayoutBackHeaderBinding bind(@NonNull View view) {
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
  public static LayoutBackHeaderBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutBackHeaderBinding)bind(component, view, notq.dccast.R.layout.layout_back_header);
  }
}
