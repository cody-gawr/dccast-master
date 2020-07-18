package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentStickerPaginationBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout container;

  @NonNull
  public final RecyclerView recyclerView;

  protected FragmentStickerPaginationBinding(Object _bindingComponent, View _root,
      int _localFieldCount, FrameLayout container, RecyclerView recyclerView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.container = container;
    this.recyclerView = recyclerView;
  }

  @NonNull
  public static FragmentStickerPaginationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_sticker_pagination, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentStickerPaginationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentStickerPaginationBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_sticker_pagination, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentStickerPaginationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_sticker_pagination, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentStickerPaginationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentStickerPaginationBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_sticker_pagination, null, false, component);
  }

  public static FragmentStickerPaginationBinding bind(@NonNull View view) {
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
  public static FragmentStickerPaginationBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (FragmentStickerPaginationBinding)bind(component, view, notq.dccast.R.layout.fragment_sticker_pagination);
  }
}
