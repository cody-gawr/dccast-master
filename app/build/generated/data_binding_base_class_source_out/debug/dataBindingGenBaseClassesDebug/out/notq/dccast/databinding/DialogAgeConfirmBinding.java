package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DialogAgeConfirmBinding extends ViewDataBinding {
  @NonNull
  public final Button cancelConfirm;

  @NonNull
  public final Button confirmConfirm;

  protected DialogAgeConfirmBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button cancelConfirm, Button confirmConfirm) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cancelConfirm = cancelConfirm;
    this.confirmConfirm = confirmConfirm;
  }

  @NonNull
  public static DialogAgeConfirmBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_age_confirm, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogAgeConfirmBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogAgeConfirmBinding>inflateInternal(inflater, notq.dccast.R.layout.dialog_age_confirm, root, attachToRoot, component);
  }

  @NonNull
  public static DialogAgeConfirmBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_age_confirm, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogAgeConfirmBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogAgeConfirmBinding>inflateInternal(inflater, notq.dccast.R.layout.dialog_age_confirm, null, false, component);
  }

  public static DialogAgeConfirmBinding bind(@NonNull View view) {
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
  public static DialogAgeConfirmBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogAgeConfirmBinding)bind(component, view, notq.dccast.R.layout.dialog_age_confirm);
  }
}
