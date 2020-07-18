package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.wowza.gocoder.sdk.api.devices.WOWZCameraView;
import java.lang.Deprecated;
import java.lang.Object;
import notq.dccast.screens.navigation_menu.live.TimerView;

public abstract class ActivityLiveStreamBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout bottomSheet;

  @NonNull
  public final FrameLayout bottomSheetBlock;

  @NonNull
  public final FrameLayout btnClose;

  @NonNull
  public final Button btnStartLive;

  @NonNull
  public final ImageView btnSwitch;

  @NonNull
  public final WOWZCameraView cameraPreview;

  @NonNull
  public final FrameLayout container;

  @NonNull
  public final LinearLayout containerLandscape;

  @NonNull
  public final LinearLayout containerPortrait;

  @NonNull
  public final TextView counter;

  @NonNull
  public final View counterDim;

  @NonNull
  public final View dim;

  @NonNull
  public final ImageView imgLandscape;

  @NonNull
  public final ImageView imgPortrait;

  @NonNull
  public final ImageView ivFlash;

  @NonNull
  public final ImageView ivMediaThumbnail;

  @NonNull
  public final ImageView ivSettings;

  @NonNull
  public final ImageView ivShare;

  @NonNull
  public final ImageView ivVolume;

  @NonNull
  public final FrameLayout layoutFlash;

  @NonNull
  public final FrameLayout layoutSettings;

  @NonNull
  public final FrameLayout layoutSwitch;

  @NonNull
  public final FrameLayout layoutThumbnail;

  @NonNull
  public final FrameLayout layoutVolume;

  @NonNull
  public final RecyclerView liveChatRecyclerView;

  @NonNull
  public final TimerView liveTimer;

  @NonNull
  public final TextView liveTitle;

  @NonNull
  public final TextView manduCount;

  @NonNull
  public final TextView memberCount;

  @NonNull
  public final LinearLayout orientationChooserLayout;

  @NonNull
  public final TextView textLandscape;

  @NonNull
  public final TextView textPortrait;

  protected ActivityLiveStreamBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout bottomSheet, FrameLayout bottomSheetBlock, FrameLayout btnClose,
      Button btnStartLive, ImageView btnSwitch, WOWZCameraView cameraPreview, FrameLayout container,
      LinearLayout containerLandscape, LinearLayout containerPortrait, TextView counter,
      View counterDim, View dim, ImageView imgLandscape, ImageView imgPortrait, ImageView ivFlash,
      ImageView ivMediaThumbnail, ImageView ivSettings, ImageView ivShare, ImageView ivVolume,
      FrameLayout layoutFlash, FrameLayout layoutSettings, FrameLayout layoutSwitch,
      FrameLayout layoutThumbnail, FrameLayout layoutVolume, RecyclerView liveChatRecyclerView,
      TimerView liveTimer, TextView liveTitle, TextView manduCount, TextView memberCount,
      LinearLayout orientationChooserLayout, TextView textLandscape, TextView textPortrait) {
    super(_bindingComponent, _root, _localFieldCount);
    this.bottomSheet = bottomSheet;
    this.bottomSheetBlock = bottomSheetBlock;
    this.btnClose = btnClose;
    this.btnStartLive = btnStartLive;
    this.btnSwitch = btnSwitch;
    this.cameraPreview = cameraPreview;
    this.container = container;
    this.containerLandscape = containerLandscape;
    this.containerPortrait = containerPortrait;
    this.counter = counter;
    this.counterDim = counterDim;
    this.dim = dim;
    this.imgLandscape = imgLandscape;
    this.imgPortrait = imgPortrait;
    this.ivFlash = ivFlash;
    this.ivMediaThumbnail = ivMediaThumbnail;
    this.ivSettings = ivSettings;
    this.ivShare = ivShare;
    this.ivVolume = ivVolume;
    this.layoutFlash = layoutFlash;
    this.layoutSettings = layoutSettings;
    this.layoutSwitch = layoutSwitch;
    this.layoutThumbnail = layoutThumbnail;
    this.layoutVolume = layoutVolume;
    this.liveChatRecyclerView = liveChatRecyclerView;
    this.liveTimer = liveTimer;
    this.liveTitle = liveTitle;
    this.manduCount = manduCount;
    this.memberCount = memberCount;
    this.orientationChooserLayout = orientationChooserLayout;
    this.textLandscape = textLandscape;
    this.textPortrait = textPortrait;
  }

  @NonNull
  public static ActivityLiveStreamBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_live_stream, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityLiveStreamBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityLiveStreamBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_live_stream, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityLiveStreamBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_live_stream, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityLiveStreamBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityLiveStreamBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_live_stream, null, false, component);
  }

  public static ActivityLiveStreamBinding bind(@NonNull View view) {
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
  public static ActivityLiveStreamBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityLiveStreamBinding)bind(component, view, notq.dccast.R.layout.activity_live_stream);
  }
}
