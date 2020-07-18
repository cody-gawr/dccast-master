package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhStickerTabBinding extends ViewDataBinding {
  @NonNull
  public final ImageView ivStickerTab;

  @NonNull
  public final LinearLayout layoutItem;

  @NonNull
  public final FrameLayout layoutLoad;

  protected VhStickerTabBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView ivStickerTab, LinearLayout layoutItem, FrameLayout layoutLoad) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ivStickerTab = ivStickerTab;
    this.layoutItem = layoutItem;
    this.layoutLoad = layoutLoad;
  }

  @NonNull
  public static VhStickerTabBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_sticker_tab, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhStickerTabBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhStickerTabBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_sticker_tab, root, attachToRoot, component);
  }

  @NonNull
  public static VhStickerTabBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_sticker_tab, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhStickerTabBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhStickerTabBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_sticker_tab, null, false, component);
  }

  public static VhStickerTabBinding bind(@NonNull View view) {
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
  public static VhStickerTabBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhStickerTabBinding)bind(component, view, notq.dccast.R.layout.vh_sticker_tab);
  }
}
