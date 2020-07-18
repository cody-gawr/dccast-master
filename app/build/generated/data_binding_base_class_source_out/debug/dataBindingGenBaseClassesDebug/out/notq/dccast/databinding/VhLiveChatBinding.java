package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhLiveChatBinding extends ViewDataBinding {
  @NonNull
  public final ImageView sticker;

  @NonNull
  public final TextView text;

  @NonNull
  public final TextView user;

  @NonNull
  public final LinearLayout wrapper;

  protected VhLiveChatBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView sticker, TextView text, TextView user, LinearLayout wrapper) {
    super(_bindingComponent, _root, _localFieldCount);
    this.sticker = sticker;
    this.text = text;
    this.user = user;
    this.wrapper = wrapper;
  }

  @NonNull
  public static VhLiveChatBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_live_chat, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhLiveChatBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhLiveChatBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_live_chat, root, attachToRoot, component);
  }

  @NonNull
  public static VhLiveChatBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_live_chat, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhLiveChatBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhLiveChatBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_live_chat, null, false, component);
  }

  public static VhLiveChatBinding bind(@NonNull View view) {
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
  public static VhLiveChatBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhLiveChatBinding)bind(component, view, notq.dccast.R.layout.vh_live_chat);
  }
}
