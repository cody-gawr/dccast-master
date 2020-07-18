package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutAdsBinding extends ViewDataBinding {
  @NonNull
  public final ImageView adsImage;

  @NonNull
  public final WebView adsWebView;

  @NonNull
  public final LinearLayout layoutBanner;

  protected LayoutAdsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView adsImage, WebView adsWebView, LinearLayout layoutBanner) {
    super(_bindingComponent, _root, _localFieldCount);
    this.adsImage = adsImage;
    this.adsWebView = adsWebView;
    this.layoutBanner = layoutBanner;
  }

  @NonNull
  public static LayoutAdsBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_ads, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutAdsBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutAdsBinding>inflateInternal(inflater, notq.dccast.R.layout.layout_ads, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutAdsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_ads, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutAdsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutAdsBinding>inflateInternal(inflater, notq.dccast.R.layout.layout_ads, null, false, component);
  }

  public static LayoutAdsBinding bind(@NonNull View view) {
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
  public static LayoutAdsBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutAdsBinding)bind(component, view, notq.dccast.R.layout.layout_ads);
  }
}
