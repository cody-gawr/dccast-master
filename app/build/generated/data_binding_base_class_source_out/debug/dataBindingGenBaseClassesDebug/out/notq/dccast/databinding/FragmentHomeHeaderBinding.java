package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentHomeHeaderBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout container;

  @NonNull
  public final TextView liveTitle;

  @NonNull
  public final CircleImageView profileImage;

  @NonNull
  public final TextView profileName;

  @NonNull
  public final TextView status;

  @NonNull
  public final LinearLayout statusContainer;

  @NonNull
  public final ImageView thumbnail;

  protected FragmentHomeHeaderBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout container, TextView liveTitle, CircleImageView profileImage, TextView profileName,
      TextView status, LinearLayout statusContainer, ImageView thumbnail) {
    super(_bindingComponent, _root, _localFieldCount);
    this.container = container;
    this.liveTitle = liveTitle;
    this.profileImage = profileImage;
    this.profileName = profileName;
    this.status = status;
    this.statusContainer = statusContainer;
    this.thumbnail = thumbnail;
  }

  @NonNull
  public static FragmentHomeHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_home_header, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentHomeHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentHomeHeaderBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_home_header, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentHomeHeaderBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_home_header, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentHomeHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentHomeHeaderBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_home_header, null, false, component);
  }

  public static FragmentHomeHeaderBinding bind(@NonNull View view) {
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
  public static FragmentHomeHeaderBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentHomeHeaderBinding)bind(component, view, notq.dccast.R.layout.fragment_home_header);
  }
}
