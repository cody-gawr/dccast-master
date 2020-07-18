package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DialogChangeNicknameBinding extends ViewDataBinding {
  @NonNull
  public final Button cancelChangeNickname;

  @NonNull
  public final Button changeNickname;

  @NonNull
  public final AppCompatEditText etNickname;

  @NonNull
  public final TextView statusCheck;

  @NonNull
  public final TextView statusText;

  protected DialogChangeNicknameBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button cancelChangeNickname, Button changeNickname, AppCompatEditText etNickname,
      TextView statusCheck, TextView statusText) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cancelChangeNickname = cancelChangeNickname;
    this.changeNickname = changeNickname;
    this.etNickname = etNickname;
    this.statusCheck = statusCheck;
    this.statusText = statusText;
  }

  @NonNull
  public static DialogChangeNicknameBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_change_nickname, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogChangeNicknameBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogChangeNicknameBinding>inflateInternal(inflater, notq.dccast.R.layout.dialog_change_nickname, root, attachToRoot, component);
  }

  @NonNull
  public static DialogChangeNicknameBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_change_nickname, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogChangeNicknameBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogChangeNicknameBinding>inflateInternal(inflater, notq.dccast.R.layout.dialog_change_nickname, null, false, component);
  }

  public static DialogChangeNicknameBinding bind(@NonNull View view) {
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
  public static DialogChangeNicknameBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogChangeNicknameBinding)bind(component, view, notq.dccast.R.layout.dialog_change_nickname);
  }
}
