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
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhPopularItemBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout container;

  @NonNull
  public final TextView liveTitle;

  @NonNull
  public final ImageView profileImage;

  @NonNull
  public final TextView profileName;

  @NonNull
  public final TextView status;

  @NonNull
  public final LinearLayout statusContainer;

  @NonNull
  public final ImageView thumbnail;

  @NonNull
  public final CardView vodCrown;

  protected VhPopularItemBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout container, TextView liveTitle, ImageView profileImage, TextView profileName,
      TextView status, LinearLayout statusContainer, ImageView thumbnail, CardView vodCrown) {
    super(_bindingComponent, _root, _localFieldCount);
    this.container = container;
    this.liveTitle = liveTitle;
    this.profileImage = profileImage;
    this.profileName = profileName;
    this.status = status;
    this.statusContainer = statusContainer;
    this.thumbnail = thumbnail;
    this.vodCrown = vodCrown;
  }

  @NonNull
  public static VhPopularItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_popular_item, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhPopularItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhPopularItemBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_popular_item, root, attachToRoot, component);
  }

  @NonNull
  public static VhPopularItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_popular_item, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhPopularItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhPopularItemBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_popular_item, null, false, component);
  }

  public static VhPopularItemBinding bind(@NonNull View view) {
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
  public static VhPopularItemBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhPopularItemBinding)bind(component, view, notq.dccast.R.layout.vh_popular_item);
  }
}
