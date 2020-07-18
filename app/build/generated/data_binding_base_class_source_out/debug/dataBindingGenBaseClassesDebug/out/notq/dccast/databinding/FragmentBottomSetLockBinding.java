package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;
import notq.dccast.custom_view.PinEntryEditText;

public abstract class FragmentBottomSetLockBinding extends ViewDataBinding {
  @NonNull
  public final Button btnCancel;

  @NonNull
  public final Button btnEnter;

  @NonNull
  public final PinEntryEditText etPin;

  @NonNull
  public final LinearLayout layoutEnter;

  @NonNull
  public final LinearLayout layoutScroll;

  @NonNull
  public final TextView lblPin;

  @NonNull
  public final TextView lblPinError;

  protected FragmentBottomSetLockBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button btnCancel, Button btnEnter, PinEntryEditText etPin, LinearLayout layoutEnter,
      LinearLayout layoutScroll, TextView lblPin, TextView lblPinError) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnCancel = btnCancel;
    this.btnEnter = btnEnter;
    this.etPin = etPin;
    this.layoutEnter = layoutEnter;
    this.layoutScroll = layoutScroll;
    this.lblPin = lblPin;
    this.lblPinError = lblPinError;
  }

  @NonNull
  public static FragmentBottomSetLockBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_set_lock, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomSetLockBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomSetLockBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_set_lock, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentBottomSetLockBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_set_lock, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomSetLockBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomSetLockBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_set_lock, null, false, component);
  }

  public static FragmentBottomSetLockBinding bind(@NonNull View view) {
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
  public static FragmentBottomSetLockBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentBottomSetLockBinding)bind(component, view, notq.dccast.R.layout.fragment_bottom_set_lock);
  }
}
