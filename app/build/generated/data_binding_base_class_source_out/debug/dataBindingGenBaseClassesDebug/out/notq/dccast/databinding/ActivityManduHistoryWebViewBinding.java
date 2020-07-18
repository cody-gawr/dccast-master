package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.airbnb.lottie.LottieAnimationView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityManduHistoryWebViewBinding extends ViewDataBinding {
  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final LayoutHeaderLoadingBinding header;

  @NonNull
  public final WebView webView;

  protected ActivityManduHistoryWebViewBinding(Object _bindingComponent, View _root,
      int _localFieldCount, LottieAnimationView dcLoader, LayoutHeaderLoadingBinding header,
      WebView webView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.dcLoader = dcLoader;
    this.header = header;
    setContainedBinding(this.header);;
    this.webView = webView;
  }

  @NonNull
  public static ActivityManduHistoryWebViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_mandu_history_web_view, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityManduHistoryWebViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityManduHistoryWebViewBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_mandu_history_web_view, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityManduHistoryWebViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_mandu_history_web_view, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityManduHistoryWebViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityManduHistoryWebViewBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_mandu_history_web_view, null, false, component);
  }

  public static ActivityManduHistoryWebViewBinding bind(@NonNull View view) {
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
  public static ActivityManduHistoryWebViewBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (ActivityManduHistoryWebViewBinding)bind(component, view, notq.dccast.R.layout.activity_mandu_history_web_view);
  }
}
