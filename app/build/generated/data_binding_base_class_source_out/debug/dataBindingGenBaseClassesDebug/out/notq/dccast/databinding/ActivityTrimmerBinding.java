package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;
import life.knowledge4.videotrimmer.K4LVideoTrimmer;

public abstract class ActivityTrimmerBinding extends ViewDataBinding {
  @NonNull
  public final K4LVideoTrimmer timeLine;

  protected ActivityTrimmerBinding(Object _bindingComponent, View _root, int _localFieldCount,
      K4LVideoTrimmer timeLine) {
    super(_bindingComponent, _root, _localFieldCount);
    this.timeLine = timeLine;
  }

  @NonNull
  public static ActivityTrimmerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_trimmer, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityTrimmerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityTrimmerBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_trimmer, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityTrimmerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_trimmer, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityTrimmerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityTrimmerBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_trimmer, null, false, component);
  }

  public static ActivityTrimmerBinding bind(@NonNull View view) {
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
  public static ActivityTrimmerBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityTrimmerBinding)bind(component, view, notq.dccast.R.layout.activity_trimmer);
  }
}
