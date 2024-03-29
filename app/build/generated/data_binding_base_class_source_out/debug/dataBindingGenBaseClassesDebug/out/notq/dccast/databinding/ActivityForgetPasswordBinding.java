package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityForgetPasswordBinding extends ViewDataBinding {
  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final WebView webView;

  protected ActivityForgetPasswordBinding(Object _bindingComponent, View _root,
      int _localFieldCount, LayoutBackHeaderBinding header, WebView webView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.header = header;
    setContainedBinding(this.header);;
    this.webView = webView;
  }

  @NonNull
  public static ActivityForgetPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_forget_password, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityForgetPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityForgetPasswordBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_forget_password, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityForgetPasswordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_forget_password, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityForgetPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityForgetPasswordBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_forget_password, null, false, component);
  }

  public static ActivityForgetPasswordBinding bind(@NonNull View view) {
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
  public static ActivityForgetPasswordBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityForgetPasswordBinding)bind(component, view, notq.dccast.R.layout.activity_forget_password);
  }
}
