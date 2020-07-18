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
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhCommentNoDataBinding extends ViewDataBinding {
  @NonNull
  public final TextView lblText;

  @NonNull
  public final LinearLayout placeholderNoData;

  protected VhCommentNoDataBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView lblText, LinearLayout placeholderNoData) {
    super(_bindingComponent, _root, _localFieldCount);
    this.lblText = lblText;
    this.placeholderNoData = placeholderNoData;
  }

  @NonNull
  public static VhCommentNoDataBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_comment_no_data, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhCommentNoDataBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhCommentNoDataBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_comment_no_data, root, attachToRoot, component);
  }

  @NonNull
  public static VhCommentNoDataBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_comment_no_data, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhCommentNoDataBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhCommentNoDataBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_comment_no_data, null, false, component);
  }

  public static VhCommentNoDataBinding bind(@NonNull View view) {
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
  public static VhCommentNoDataBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhCommentNoDataBinding)bind(component, view, notq.dccast.R.layout.vh_comment_no_data);
  }
}
