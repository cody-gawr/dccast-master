package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public abstract class ActivityUploadVodBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout btnSelectGallery;

  @NonNull
  public final TextInputLayout descriptionInputLayout;

  @NonNull
  public final TextInputEditText descriptionInputText;

  @NonNull
  public final LinearLayout dropDownLayout;

  @NonNull
  public final TextView gallery;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final LinearLayout layoutGallery;

  @NonNull
  public final LinearLayout layoutTitle;

  @NonNull
  public final TextView lblCharacterCount;

  @NonNull
  public final Button postBtn;

  @NonNull
  public final AppCompatSpinner spinnerCategory;

  @NonNull
  public final AppCompatSpinner spinnerShare;

  @NonNull
  public final ImageView thumbnail;

  @NonNull
  public final TextInputLayout titleInputLayout;

  @NonNull
  public final TextInputEditText titleInputText;

  protected ActivityUploadVodBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout btnSelectGallery, TextInputLayout descriptionInputLayout,
      TextInputEditText descriptionInputText, LinearLayout dropDownLayout, TextView gallery,
      LayoutBackHeaderBinding header, LinearLayout layoutGallery, LinearLayout layoutTitle,
      TextView lblCharacterCount, Button postBtn, AppCompatSpinner spinnerCategory,
      AppCompatSpinner spinnerShare, ImageView thumbnail, TextInputLayout titleInputLayout,
      TextInputEditText titleInputText) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnSelectGallery = btnSelectGallery;
    this.descriptionInputLayout = descriptionInputLayout;
    this.descriptionInputText = descriptionInputText;
    this.dropDownLayout = dropDownLayout;
    this.gallery = gallery;
    this.header = header;
    setContainedBinding(this.header);;
    this.layoutGallery = layoutGallery;
    this.layoutTitle = layoutTitle;
    this.lblCharacterCount = lblCharacterCount;
    this.postBtn = postBtn;
    this.spinnerCategory = spinnerCategory;
    this.spinnerShare = spinnerShare;
    this.thumbnail = thumbnail;
    this.titleInputLayout = titleInputLayout;
    this.titleInputText = titleInputText;
  }

  @NonNull
  public static ActivityUploadVodBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_upload_vod, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityUploadVodBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityUploadVodBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_upload_vod, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityUploadVodBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_upload_vod, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityUploadVodBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityUploadVodBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_upload_vod, null, false, component);
  }

  public static ActivityUploadVodBinding bind(@NonNull View view) {
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
  public static ActivityUploadVodBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityUploadVodBinding)bind(component, view, notq.dccast.R.layout.activity_upload_vod);
  }
}
