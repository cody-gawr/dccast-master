package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.radiobutton.MaterialRadioButton;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DialogVideoSettingsBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout changeNickname;

  @NonNull
  public final LinearLayout popUp;

  @NonNull
  public final TextView report;

  @NonNull
  public final MaterialRadioButton resolution0;

  @NonNull
  public final MaterialRadioButton resolution1;

  @NonNull
  public final MaterialRadioButton resolution2;

  @NonNull
  public final MaterialRadioButton resolution3;

  @NonNull
  public final MaterialRadioButton resolution4;

  @NonNull
  public final RadioGroup resolutionGroup;

  @NonNull
  public final TextView resolutionTitle;

  @NonNull
  public final TextView viewAsPopUp;

  @NonNull
  public final TextView viewAsRadio;

  protected DialogVideoSettingsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout changeNickname, LinearLayout popUp, TextView report,
      MaterialRadioButton resolution0, MaterialRadioButton resolution1,
      MaterialRadioButton resolution2, MaterialRadioButton resolution3,
      MaterialRadioButton resolution4, RadioGroup resolutionGroup, TextView resolutionTitle,
      TextView viewAsPopUp, TextView viewAsRadio) {
    super(_bindingComponent, _root, _localFieldCount);
    this.changeNickname = changeNickname;
    this.popUp = popUp;
    this.report = report;
    this.resolution0 = resolution0;
    this.resolution1 = resolution1;
    this.resolution2 = resolution2;
    this.resolution3 = resolution3;
    this.resolution4 = resolution4;
    this.resolutionGroup = resolutionGroup;
    this.resolutionTitle = resolutionTitle;
    this.viewAsPopUp = viewAsPopUp;
    this.viewAsRadio = viewAsRadio;
  }

  @NonNull
  public static DialogVideoSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_video_settings, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogVideoSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogVideoSettingsBinding>inflateInternal(inflater, notq.dccast.R.layout.dialog_video_settings, root, attachToRoot, component);
  }

  @NonNull
  public static DialogVideoSettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_video_settings, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogVideoSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogVideoSettingsBinding>inflateInternal(inflater, notq.dccast.R.layout.dialog_video_settings, null, false, component);
  }

  public static DialogVideoSettingsBinding bind(@NonNull View view) {
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
  public static DialogVideoSettingsBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogVideoSettingsBinding)bind(component, view, notq.dccast.R.layout.dialog_video_settings);
  }
}
