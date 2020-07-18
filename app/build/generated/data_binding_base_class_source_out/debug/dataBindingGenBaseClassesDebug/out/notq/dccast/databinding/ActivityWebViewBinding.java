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

public abstract class ActivityWebViewBinding extends ViewDataBinding {
  @NonNull
  public final LayoutHeaderLoadingBinding header;

  @NonNull
  public final WebView webView;

  protected ActivityWebViewBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LayoutHeaderLoadingBinding header, WebView webView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.header = header;
    setContainedBinding(this.header);;
    this.webView = webView;
  }

  @NonNull
  public static ActivityWebViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_web_view, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityWebViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityWebViewBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_web_view, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityWebViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_web_view, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityWebViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityWebViewBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_web_view, null, false, component);
  }

  public static ActivityWebViewBinding bind(@NonNull View view) {
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
  public static ActivityWebViewBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityWebViewBinding)bind(component, view, notq.dccast.R.layout.activity_web_view);
  }
}
