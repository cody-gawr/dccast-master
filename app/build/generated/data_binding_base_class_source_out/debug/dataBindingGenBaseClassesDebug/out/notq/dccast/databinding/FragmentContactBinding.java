package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentContactBinding extends ViewDataBinding {
  @NonNull
  public final Button btnChooseImage;

  @NonNull
  public final Button btnSend;

  @NonNull
  public final TextInputEditText etInformation;

  @NonNull
  public final TextInputEditText etTitle;

  @NonNull
  public final TextInputLayout layoutInformation;

  @NonNull
  public final TextInputLayout layoutTitle;

  @NonNull
  public final TextView lblImg;

  @NonNull
  public final AppCompatSpinner spinnerType;

  protected FragmentContactBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button btnChooseImage, Button btnSend, TextInputEditText etInformation,
      TextInputEditText etTitle, TextInputLayout layoutInformation, TextInputLayout layoutTitle,
      TextView lblImg, AppCompatSpinner spinnerType) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnChooseImage = btnChooseImage;
    this.btnSend = btnSend;
    this.etInformation = etInformation;
    this.etTitle = etTitle;
    this.layoutInformation = layoutInformation;
    this.layoutTitle = layoutTitle;
    this.lblImg = lblImg;
    this.spinnerType = spinnerType;
  }

  @NonNull
  public static FragmentContactBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_contact, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentContactBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentContactBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_contact, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentContactBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_contact, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentContactBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentContactBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_contact, null, false, component);
  }

  public static FragmentContactBinding bind(@NonNull View view) {
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
  public static FragmentContactBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentContactBinding)bind(component, view, notq.dccast.R.layout.fragment_contact);
  }
}
