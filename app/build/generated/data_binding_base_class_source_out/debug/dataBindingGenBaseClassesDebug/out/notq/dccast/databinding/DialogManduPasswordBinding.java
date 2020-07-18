package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DialogManduPasswordBinding extends ViewDataBinding {
  @NonNull
  public final Button cancelMandu;

  @NonNull
  public final AppCompatEditText etPassword;

  @NonNull
  public final Button sendMandu;

  protected DialogManduPasswordBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button cancelMandu, AppCompatEditText etPassword, Button sendMandu) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cancelMandu = cancelMandu;
    this.etPassword = etPassword;
    this.sendMandu = sendMandu;
  }

  @NonNull
  public static DialogManduPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_mandu_password, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogManduPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogManduPasswordBinding>inflateInternal(inflater, notq.dccast.R.layout.dialog_mandu_password, root, attachToRoot, component);
  }

  @NonNull
  public static DialogManduPasswordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_mandu_password, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogManduPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogManduPasswordBinding>inflateInternal(inflater, notq.dccast.R.layout.dialog_mandu_password, null, false, component);
  }

  public static DialogManduPasswordBinding bind(@NonNull View view) {
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
  public static DialogManduPasswordBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogManduPasswordBinding)bind(component, view, notq.dccast.R.layout.dialog_mandu_password);
  }
}
