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
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhCastGroupBinding extends ViewDataBinding {
  @NonNull
  public final CircleImageView ivGroup;

  @NonNull
  public final LinearLayout layoutItem;

  @NonNull
  public final TextView lblGroupDescription;

  @NonNull
  public final TextView lblGroupName;

  protected VhCastGroupBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CircleImageView ivGroup, LinearLayout layoutItem, TextView lblGroupDescription,
      TextView lblGroupName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ivGroup = ivGroup;
    this.layoutItem = layoutItem;
    this.lblGroupDescription = lblGroupDescription;
    this.lblGroupName = lblGroupName;
  }

  @NonNull
  public static VhCastGroupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_cast_group, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhCastGroupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhCastGroupBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_cast_group, root, attachToRoot, component);
  }

  @NonNull
  public static VhCastGroupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_cast_group, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhCastGroupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhCastGroupBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_cast_group, null, false, component);
  }

  public static VhCastGroupBinding bind(@NonNull View view) {
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
  public static VhCastGroupBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhCastGroupBinding)bind(component, view, notq.dccast.R.layout.vh_cast_group);
  }
}
