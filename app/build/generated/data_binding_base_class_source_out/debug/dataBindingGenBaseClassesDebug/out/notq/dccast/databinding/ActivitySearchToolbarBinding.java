package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivitySearchToolbarBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout btnBack;

  @NonNull
  public final FrameLayout btnClear;

  @NonNull
  public final AppCompatEditText searchField;

  @NonNull
  public final TextView searchedField;

  @NonNull
  public final Toolbar toolbar;

  protected ActivitySearchToolbarBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout btnBack, FrameLayout btnClear, AppCompatEditText searchField,
      TextView searchedField, Toolbar toolbar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnBack = btnBack;
    this.btnClear = btnClear;
    this.searchField = searchField;
    this.searchedField = searchedField;
    this.toolbar = toolbar;
  }

  @NonNull
  public static ActivitySearchToolbarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_search_toolbar, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivitySearchToolbarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivitySearchToolbarBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_search_toolbar, root, attachToRoot, component);
  }

  @NonNull
  public static ActivitySearchToolbarBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_search_toolbar, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivitySearchToolbarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivitySearchToolbarBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_search_toolbar, null, false, component);
  }

  public static ActivitySearchToolbarBinding bind(@NonNull View view) {
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
  public static ActivitySearchToolbarBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivitySearchToolbarBinding)bind(component, view, notq.dccast.R.layout.activity_search_toolbar);
  }
}
