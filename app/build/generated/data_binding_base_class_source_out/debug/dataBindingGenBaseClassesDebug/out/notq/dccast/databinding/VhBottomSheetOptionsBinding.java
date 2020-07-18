package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhBottomSheetOptionsBinding extends ViewDataBinding {
  @NonNull
  public final ImageView ivSelected;

  @NonNull
  public final LinearLayout layoutItem;

  @NonNull
  public final TextView lblItem;

  protected VhBottomSheetOptionsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView ivSelected, LinearLayout layoutItem, TextView lblItem) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ivSelected = ivSelected;
    this.layoutItem = layoutItem;
    this.lblItem = lblItem;
  }

  @NonNull
  public static VhBottomSheetOptionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_bottom_sheet_options, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhBottomSheetOptionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhBottomSheetOptionsBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_bottom_sheet_options, root, attachToRoot, component);
  }

  @NonNull
  public static VhBottomSheetOptionsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_bottom_sheet_options, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhBottomSheetOptionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhBottomSheetOptionsBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_bottom_sheet_options, null, false, component);
  }

  public static VhBottomSheetOptionsBinding bind(@NonNull View view) {
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
  public static VhBottomSheetOptionsBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhBottomSheetOptionsBinding)bind(component, view, notq.dccast.R.layout.vh_bottom_sheet_options);
  }
}
