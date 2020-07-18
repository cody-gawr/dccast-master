package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityEditProfileBinding extends ViewDataBinding {
  @NonNull
  public final CardView cardImage;

  @NonNull
  public final TextInputEditText etEmail;

  @NonNull
  public final TextInputEditText etId;

  @NonNull
  public final TextInputEditText etNickname;

  @NonNull
  public final TextInputEditText etPhone;

  @NonNull
  public final TextInputEditText etStatus;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final CardView ivChange;

  @NonNull
  public final ImageView ivUser;

  @NonNull
  public final TextInputLayout layoutEmail;

  @NonNull
  public final FrameLayout layoutLoadImage;

  @NonNull
  public final TextInputLayout layoutNickname;

  @NonNull
  public final TextInputLayout layoutPhone;

  @NonNull
  public final TextInputLayout layoutStatus;

  @NonNull
  public final Button saveBtn;

  protected ActivityEditProfileBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CardView cardImage, TextInputEditText etEmail, TextInputEditText etId,
      TextInputEditText etNickname, TextInputEditText etPhone, TextInputEditText etStatus,
      LayoutBackHeaderBinding header, CardView ivChange, ImageView ivUser,
      TextInputLayout layoutEmail, FrameLayout layoutLoadImage, TextInputLayout layoutNickname,
      TextInputLayout layoutPhone, TextInputLayout layoutStatus, Button saveBtn) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cardImage = cardImage;
    this.etEmail = etEmail;
    this.etId = etId;
    this.etNickname = etNickname;
    this.etPhone = etPhone;
    this.etStatus = etStatus;
    this.header = header;
    setContainedBinding(this.header);;
    this.ivChange = ivChange;
    this.ivUser = ivUser;
    this.layoutEmail = layoutEmail;
    this.layoutLoadImage = layoutLoadImage;
    this.layoutNickname = layoutNickname;
    this.layoutPhone = layoutPhone;
    this.layoutStatus = layoutStatus;
    this.saveBtn = saveBtn;
  }

  @NonNull
  public static ActivityEditProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_edit_profile, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityEditProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityEditProfileBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_edit_profile, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityEditProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_edit_profile, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityEditProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityEditProfileBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_edit_profile, null, false, component);
  }

  public static ActivityEditProfileBinding bind(@NonNull View view) {
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
  public static ActivityEditProfileBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityEditProfileBinding)bind(component, view, notq.dccast.R.layout.activity_edit_profile);
  }
}
