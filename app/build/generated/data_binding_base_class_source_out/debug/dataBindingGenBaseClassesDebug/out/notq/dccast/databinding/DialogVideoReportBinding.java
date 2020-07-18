package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DialogVideoReportBinding extends ViewDataBinding {
  @NonNull
  public final Button cancelReport;

  @NonNull
  public final AppCompatRadioButton report1;

  @NonNull
  public final AppCompatRadioButton report2;

  @NonNull
  public final AppCompatRadioButton report3;

  @NonNull
  public final AppCompatRadioButton report4;

  @NonNull
  public final AppCompatEditText reportField;

  @NonNull
  public final TextView reportTitle;

  @NonNull
  public final RadioGroup reportTypes;

  @NonNull
  public final Button sendReport;

  protected DialogVideoReportBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button cancelReport, AppCompatRadioButton report1, AppCompatRadioButton report2,
      AppCompatRadioButton report3, AppCompatRadioButton report4, AppCompatEditText reportField,
      TextView reportTitle, RadioGroup reportTypes, Button sendReport) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cancelReport = cancelReport;
    this.report1 = report1;
    this.report2 = report2;
    this.report3 = report3;
    this.report4 = report4;
    this.reportField = reportField;
    this.reportTitle = reportTitle;
    this.reportTypes = reportTypes;
    this.sendReport = sendReport;
  }

  @NonNull
  public static DialogVideoReportBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_video_report, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogVideoReportBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogVideoReportBinding>inflateInternal(inflater, notq.dccast.R.layout.dialog_video_report, root, attachToRoot, component);
  }

  @NonNull
  public static DialogVideoReportBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_video_report, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogVideoReportBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogVideoReportBinding>inflateInternal(inflater, notq.dccast.R.layout.dialog_video_report, null, false, component);
  }

  public static DialogVideoReportBinding bind(@NonNull View view) {
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
  public static DialogVideoReportBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogVideoReportBinding)bind(component, view, notq.dccast.R.layout.dialog_video_report);
  }
}
