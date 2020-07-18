package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.rd.PageIndicatorView;
import java.lang.Deprecated;
import java.lang.Object;
import notq.dccast.custom_view.CustomViewPager;

public abstract class FragmentBottomStickerBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout btnStickerClose;

  @NonNull
  public final View dim3;

  @NonNull
  public final PageIndicatorView pageIndicatorView;

  @NonNull
  public final FrameLayout pagerContainer;

  @NonNull
  public final ProgressBar progressBarSticker;

  @NonNull
  public final FrameLayout stickerBottomSheet;

  @NonNull
  public final RecyclerView tabRecyclerView;

  @NonNull
  public final TextView totalStickers;

  @NonNull
  public final CustomViewPager viewPager;

  protected FragmentBottomStickerBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout btnStickerClose, View dim3, PageIndicatorView pageIndicatorView,
      FrameLayout pagerContainer, ProgressBar progressBarSticker, FrameLayout stickerBottomSheet,
      RecyclerView tabRecyclerView, TextView totalStickers, CustomViewPager viewPager) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnStickerClose = btnStickerClose;
    this.dim3 = dim3;
    this.pageIndicatorView = pageIndicatorView;
    this.pagerContainer = pagerContainer;
    this.progressBarSticker = progressBarSticker;
    this.stickerBottomSheet = stickerBottomSheet;
    this.tabRecyclerView = tabRecyclerView;
    this.totalStickers = totalStickers;
    this.viewPager = viewPager;
  }

  @NonNull
  public static FragmentBottomStickerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sticker, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomStickerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomStickerBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_sticker, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentBottomStickerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sticker, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomStickerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomStickerBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_sticker, null, false, component);
  }

  public static FragmentBottomStickerBinding bind(@NonNull View view) {
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
  public static FragmentBottomStickerBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentBottomStickerBinding)bind(component, view, notq.dccast.R.layout.fragment_bottom_sticker);
  }
}
