package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhContactUsBinding extends ViewDataBinding {
  @NonNull
  public final TextView date;

  @NonNull
  public final LinearLayout layoutItem;

  @NonNull
  public final TextView state;

  @NonNull
  public final TextView title;

  protected VhContactUsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView date, LinearLayout layoutItem, TextView state, TextView title) {
    super(_bindingComponent, _root, _localFieldCount);
    this.date = date;
    this.layoutItem = layoutItem;
    this.state = state;
    this.title = title;
  }

  @NonNull
  public static VhContactUsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_contact_us, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhContactUsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhContactUsBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_contact_us, root, attachToRoot, component);
  }

  @NonNull
  public static VhContactUsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_contact_us, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhContactUsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhContactUsBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_contact_us, null, false, component);
  }

  public static VhContactUsBinding bind(@NonNull View view) {
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
  public static VhContactUsBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhContactUsBinding)bind(component, view, notq.dccast.R.layout.vh_contact_us);
  }
}
