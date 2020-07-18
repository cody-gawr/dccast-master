package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityMyContentBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout container;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final LinearLayout itemFavorite;

  @NonNull
  public final LinearLayout itemMyChannel;

  @NonNull
  public final LinearLayout itemRecent;

  @NonNull
  public final LinearLayout itemSubscribe;

  @NonNull
  public final LinearLayout optionLayout;

  protected ActivityMyContentBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout container, LayoutBackHeaderBinding header, LinearLayout itemFavorite,
      LinearLayout itemMyChannel, LinearLayout itemRecent, LinearLayout itemSubscribe,
      LinearLayout optionLayout) {
    super(_bindingComponent, _root, _localFieldCount);
    this.container = container;
    this.header = header;
    setContainedBinding(this.header);;
    this.itemFavorite = itemFavorite;
    this.itemMyChannel = itemMyChannel;
    this.itemRecent = itemRecent;
    this.itemSubscribe = itemSubscribe;
    this.optionLayout = optionLayout;
  }

  @NonNull
  public static ActivityMyContentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_my_content, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMyContentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityMyContentBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_my_content, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityMyContentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_my_content, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMyContentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityMyContentBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_my_content, null, false, component);
  }

  public static ActivityMyContentBinding bind(@NonNull View view) {
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
  public static ActivityMyContentBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityMyContentBinding)bind(component, view, notq.dccast.R.layout.activity_my_content);
  }
}
