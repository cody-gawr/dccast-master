package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivitySelectGalleryBinding extends ViewDataBinding {
  @NonNull
  public final RecyclerView galleryRecyclerview;

  @NonNull
  public final ActivitySearchToolbarBinding toolbarContainer;

  protected ActivitySelectGalleryBinding(Object _bindingComponent, View _root, int _localFieldCount,
      RecyclerView galleryRecyclerview, ActivitySearchToolbarBinding toolbarContainer) {
    super(_bindingComponent, _root, _localFieldCount);
    this.galleryRecyclerview = galleryRecyclerview;
    this.toolbarContainer = toolbarContainer;
    setContainedBinding(this.toolbarContainer);;
  }

  @NonNull
  public static ActivitySelectGalleryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_select_gallery, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivitySelectGalleryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivitySelectGalleryBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_select_gallery, root, attachToRoot, component);
  }

  @NonNull
  public static ActivitySelectGalleryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_select_gallery, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivitySelectGalleryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivitySelectGalleryBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_select_gallery, null, false, component);
  }

  public static ActivitySelectGalleryBinding bind(@NonNull View view) {
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
  public static ActivitySelectGalleryBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivitySelectGalleryBinding)bind(component, view, notq.dccast.R.layout.activity_select_gallery);
  }
}
