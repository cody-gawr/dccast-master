package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityThumbnailChooserBinding extends ViewDataBinding {
  @NonNull
  public final Button btnNext;

  @NonNull
  public final CropImageView cropImageView;

  @NonNull
  public final FrameLayout homeButton;

  @NonNull
  public final ImageView homeButtonImage;

  @NonNull
  public final Toolbar toolbar;

  protected ActivityThumbnailChooserBinding(Object _bindingComponent, View _root,
      int _localFieldCount, Button btnNext, CropImageView cropImageView, FrameLayout homeButton,
      ImageView homeButtonImage, Toolbar toolbar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnNext = btnNext;
    this.cropImageView = cropImageView;
    this.homeButton = homeButton;
    this.homeButtonImage = homeButtonImage;
    this.toolbar = toolbar;
  }

  @NonNull
  public static ActivityThumbnailChooserBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_thumbnail_chooser, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityThumbnailChooserBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityThumbnailChooserBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_thumbnail_chooser, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityThumbnailChooserBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_thumbnail_chooser, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityThumbnailChooserBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityThumbnailChooserBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_thumbnail_chooser, null, false, component);
  }

  public static ActivityThumbnailChooserBinding bind(@NonNull View view) {
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
  public static ActivityThumbnailChooserBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (ActivityThumbnailChooserBinding)bind(component, view, notq.dccast.R.layout.activity_thumbnail_chooser);
  }
}
