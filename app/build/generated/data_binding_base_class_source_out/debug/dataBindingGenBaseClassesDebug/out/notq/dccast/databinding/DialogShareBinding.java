package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DialogShareBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout btnShareClose;

  @NonNull
  public final RecyclerView friends;

  @NonNull
  public final RecyclerView shareOptions;

  @NonNull
  public final TextView titleFriends;

  protected DialogShareBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout btnShareClose, RecyclerView friends, RecyclerView shareOptions,
      TextView titleFriends) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnShareClose = btnShareClose;
    this.friends = friends;
    this.shareOptions = shareOptions;
    this.titleFriends = titleFriends;
  }

  @NonNull
  public static DialogShareBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_share, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogShareBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogShareBinding>inflateInternal(inflater, notq.dccast.R.layout.dialog_share, root, attachToRoot, component);
  }

  @NonNull
  public static DialogShareBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_share, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogShareBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogShareBinding>inflateInternal(inflater, notq.dccast.R.layout.dialog_share, null, false, component);
  }

  public static DialogShareBinding bind(@NonNull View view) {
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
  public static DialogShareBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogShareBinding)bind(component, view, notq.dccast.R.layout.dialog_share);
  }
}
