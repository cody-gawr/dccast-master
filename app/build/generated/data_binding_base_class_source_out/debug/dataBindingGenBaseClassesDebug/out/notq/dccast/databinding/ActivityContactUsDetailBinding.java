package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityContactUsDetailBinding extends ViewDataBinding {
  @NonNull
  public final TextView answer;

  @NonNull
  public final TextView contactDate;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final TextView information;

  @NonNull
  public final TextView status;

  @NonNull
  public final TextView title;

  @NonNull
  public final TextView type;

  protected ActivityContactUsDetailBinding(Object _bindingComponent, View _root,
      int _localFieldCount, TextView answer, TextView contactDate, LayoutBackHeaderBinding header,
      TextView information, TextView status, TextView title, TextView type) {
    super(_bindingComponent, _root, _localFieldCount);
    this.answer = answer;
    this.contactDate = contactDate;
    this.header = header;
    setContainedBinding(this.header);;
    this.information = information;
    this.status = status;
    this.title = title;
    this.type = type;
  }

  @NonNull
  public static ActivityContactUsDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_contact_us_detail, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityContactUsDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityContactUsDetailBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_contact_us_detail, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityContactUsDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_contact_us_detail, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityContactUsDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityContactUsDetailBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_contact_us_detail, null, false, component);
  }

  public static ActivityContactUsDetailBinding bind(@NonNull View view) {
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
  public static ActivityContactUsDetailBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (ActivityContactUsDetailBinding)bind(component, view, notq.dccast.R.layout.activity_contact_us_detail);
  }
}
