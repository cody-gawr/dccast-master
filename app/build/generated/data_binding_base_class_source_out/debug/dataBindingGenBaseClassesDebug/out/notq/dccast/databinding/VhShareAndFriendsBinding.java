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

public abstract class VhShareAndFriendsBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout container;

  @NonNull
  public final ImageView img;

  @NonNull
  public final TextView name;

  protected VhShareAndFriendsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout container, ImageView img, TextView name) {
    super(_bindingComponent, _root, _localFieldCount);
    this.container = container;
    this.img = img;
    this.name = name;
  }

  @NonNull
  public static VhShareAndFriendsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_share_and_friends, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhShareAndFriendsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhShareAndFriendsBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_share_and_friends, root, attachToRoot, component);
  }

  @NonNull
  public static VhShareAndFriendsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_share_and_friends, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhShareAndFriendsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhShareAndFriendsBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_share_and_friends, null, false, component);
  }

  public static VhShareAndFriendsBinding bind(@NonNull View view) {
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
  public static VhShareAndFriendsBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhShareAndFriendsBinding)bind(component, view, notq.dccast.R.layout.vh_share_and_friends);
  }
}
