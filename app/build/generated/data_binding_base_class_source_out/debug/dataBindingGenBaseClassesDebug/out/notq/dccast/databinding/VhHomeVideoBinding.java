package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhHomeVideoBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout btnLiveMore;

  @NonNull
  public final FrameLayout btnVodMore;

  @NonNull
  public final FrameLayout layoutLoad;

  @NonNull
  public final CardView live18;

  @NonNull
  public final TextView liveCategory;

  @NonNull
  public final TextView liveChannel;

  @NonNull
  public final LinearLayout liveContent;

  @NonNull
  public final CardView liveCrown;

  @NonNull
  public final TextView liveDuration;

  @NonNull
  public final FrameLayout liveDurationContainer;

  @NonNull
  public final TextView liveHorizontalTitle;

  @NonNull
  public final LinearLayout liveLayout;

  @NonNull
  public final TextView liveStatus;

  @NonNull
  public final ImageView liveThumbnail;

  @NonNull
  public final TextView liveTitle;

  @NonNull
  public final FrameLayout rootLayout;

  @NonNull
  public final CardView vod18;

  @NonNull
  public final TextView vodCategory;

  @NonNull
  public final TextView vodChannel;

  @NonNull
  public final CardView vodCrown;

  @NonNull
  public final TextView vodDuration;

  @NonNull
  public final FrameLayout vodDurationContainer;

  @NonNull
  public final LinearLayout vodLayout;

  @NonNull
  public final CardView vodLock;

  @NonNull
  public final CardView vodLockInlive;

  @NonNull
  public final ProgressBar vodProgress;

  @NonNull
  public final ImageView vodThumbnail;

  @NonNull
  public final TextView vodTitle;

  @NonNull
  public final TextView vodViewCount;

  protected VhHomeVideoBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout btnLiveMore, FrameLayout btnVodMore, FrameLayout layoutLoad, CardView live18,
      TextView liveCategory, TextView liveChannel, LinearLayout liveContent, CardView liveCrown,
      TextView liveDuration, FrameLayout liveDurationContainer, TextView liveHorizontalTitle,
      LinearLayout liveLayout, TextView liveStatus, ImageView liveThumbnail, TextView liveTitle,
      FrameLayout rootLayout, CardView vod18, TextView vodCategory, TextView vodChannel,
      CardView vodCrown, TextView vodDuration, FrameLayout vodDurationContainer,
      LinearLayout vodLayout, CardView vodLock, CardView vodLockInlive, ProgressBar vodProgress,
      ImageView vodThumbnail, TextView vodTitle, TextView vodViewCount) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnLiveMore = btnLiveMore;
    this.btnVodMore = btnVodMore;
    this.layoutLoad = layoutLoad;
    this.live18 = live18;
    this.liveCategory = liveCategory;
    this.liveChannel = liveChannel;
    this.liveContent = liveContent;
    this.liveCrown = liveCrown;
    this.liveDuration = liveDuration;
    this.liveDurationContainer = liveDurationContainer;
    this.liveHorizontalTitle = liveHorizontalTitle;
    this.liveLayout = liveLayout;
    this.liveStatus = liveStatus;
    this.liveThumbnail = liveThumbnail;
    this.liveTitle = liveTitle;
    this.rootLayout = rootLayout;
    this.vod18 = vod18;
    this.vodCategory = vodCategory;
    this.vodChannel = vodChannel;
    this.vodCrown = vodCrown;
    this.vodDuration = vodDuration;
    this.vodDurationContainer = vodDurationContainer;
    this.vodLayout = vodLayout;
    this.vodLock = vodLock;
    this.vodLockInlive = vodLockInlive;
    this.vodProgress = vodProgress;
    this.vodThumbnail = vodThumbnail;
    this.vodTitle = vodTitle;
    this.vodViewCount = vodViewCount;
  }

  @NonNull
  public static VhHomeVideoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_home_video, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhHomeVideoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhHomeVideoBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_home_video, root, attachToRoot, component);
  }

  @NonNull
  public static VhHomeVideoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_home_video, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhHomeVideoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhHomeVideoBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_home_video, null, false, component);
  }

  public static VhHomeVideoBinding bind(@NonNull View view) {
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
  public static VhHomeVideoBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhHomeVideoBinding)bind(component, view, notq.dccast.R.layout.vh_home_video);
  }
}
