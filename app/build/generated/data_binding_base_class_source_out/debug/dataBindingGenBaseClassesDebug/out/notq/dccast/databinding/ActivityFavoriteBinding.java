package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityFavoriteBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout container;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final LinearLayout layoutFavourite;

  @NonNull
  public final TextView lblFavorite;

  protected ActivityFavoriteBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout container, LayoutBackHeaderBinding header, LinearLayout layoutFavourite,
      TextView lblFavorite) {
    super(_bindingComponent, _root, _localFieldCount);
    this.container = container;
    this.header = header;
    setContainedBinding(this.header);;
    this.layoutFavourite = layoutFavourite;
    this.lblFavorite = lblFavorite;
  }

  @NonNull
  public static ActivityFavoriteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_favorite, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityFavoriteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityFavoriteBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_favorite, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityFavoriteBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_favorite, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityFavoriteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityFavoriteBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_favorite, null, false, component);
  }

  public static ActivityFavoriteBinding bind(@NonNull View view) {
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
  public static ActivityFavoriteBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityFavoriteBinding)bind(component, view, notq.dccast.R.layout.activity_favorite);
  }
}
