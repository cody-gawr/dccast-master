package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.AppBarLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Deprecated;
import java.lang.Object;
import notq.dccast.custom_view.CustomFrameLayout;
import notq.dccast.custom_view.CustomPlayerView;
import notq.dccast.custom_view.CustomProgressView;
import notq.dccast.custom_view.DMotionLayout;

public abstract class FragmentVideoBinding extends ViewDataBinding {
  @NonNull
  public final LayoutAdsBinding ads;

  @NonNull
  public final AppBarLayout appBar;

  @NonNull
  public final FrameLayout bottomSheet;

  @NonNull
  public final FrameLayout bottomSheetAddComment;

  @NonNull
  public final FrameLayout bottomSheetBuuz;

  @NonNull
  public final FrameLayout bottomSheetBuuzFull;

  @NonNull
  public final FrameLayout bottomSheetSticker;

  @NonNull
  public final FrameLayout bottomSheetStickerFull;

  @NonNull
  public final FrameLayout btnBuuz;

  @NonNull
  public final FrameLayout btnBuuzFull;

  @NonNull
  public final FrameLayout btnChat;

  @NonNull
  public final AppCompatImageView btnClose;

  @NonNull
  public final FrameLayout btnClose1;

  @NonNull
  public final FrameLayout btnCollapse;

  @NonNull
  public final FrameLayout btnFav;

  @NonNull
  public final FrameLayout btnFavFull;

  @NonNull
  public final ImageView btnFavImage;

  @NonNull
  public final ImageView btnFavImageFull;

  @NonNull
  public final ImageView btnFavImageSelected;

  @NonNull
  public final ImageView btnFavImageSelectedFull;

  @NonNull
  public final TextView btnLiveFollow;

  @NonNull
  public final TextView btnLiveFollowFull;

  @NonNull
  public final ImageView btnNext;

  @NonNull
  public final AppCompatImageView btnPlay;

  @NonNull
  public final ImageView btnPlayBig;

  @NonNull
  public final ImageView btnPre;

  @NonNull
  public final FrameLayout btnScreenSize;

  @NonNull
  public final FrameLayout btnScreenSizeFull;

  @NonNull
  public final FrameLayout btnSettings;

  @NonNull
  public final FrameLayout btnShare;

  @NonNull
  public final FrameLayout btnShareFull;

  @NonNull
  public final FrameLayout btnSubscribe;

  @NonNull
  public final FrameLayout btnSubscribeFull;

  @NonNull
  public final FrameLayout btnVolumeToggle;

  @NonNull
  public final FrameLayout btnVolumeToggleFull;

  @NonNull
  public final LinearLayout chatLayout;

  @NonNull
  public final LinearLayout chatLayoutFull;

  @NonNull
  public final TextView chatMessageField;

  @NonNull
  public final EditText chatMessageFieldFull;

  @NonNull
  public final FrameLayout chatMessageSend;

  @NonNull
  public final FrameLayout chatMessageSendFull;

  @NonNull
  public final FrameLayout chatMessageSticker;

  @NonNull
  public final FrameLayout chatMessageStickerFull;

  @NonNull
  public final TextView collapsedChannel;

  @NonNull
  public final LinearLayout collapsedControlLayout;

  @NonNull
  public final View collapsedDivider;

  @NonNull
  public final TextView collapsedTitle;

  @NonNull
  public final LinearLayout containerRecyclerview;

  @NonNull
  public final CoordinatorLayout content;

  @NonNull
  public final FrameLayout contentContainer;

  @NonNull
  public final CustomFrameLayout controllers;

  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final View dim;

  @NonNull
  public final TextView duration;

  @NonNull
  public final CircleImageView ivLiveUser;

  @NonNull
  public final CircleImageView ivLiveUserFull;

  @NonNull
  public final FrameLayout layoutVideoAds;

  @NonNull
  public final CardView live18;

  @NonNull
  public final CardView live18Full;

  @NonNull
  public final FrameLayout liveBtnClose1;

  @NonNull
  public final FrameLayout liveBtnSettings;

  @NonNull
  public final RecyclerView liveChatRecyclerview;

  @NonNull
  public final LinearLayout liveFollowLayout;

  @NonNull
  public final CoordinatorLayout liveFullScreenControllers;

  @NonNull
  public final TextView liveFullScreenTitle;

  @NonNull
  public final LinearLayout liveHeader;

  @NonNull
  public final TextView liveScreenTitle;

  @NonNull
  public final ImageView muted;

  @NonNull
  public final ImageView mutedFull;

  @NonNull
  public final ImageView notMuted;

  @NonNull
  public final ImageView notMutedFull;

  @NonNull
  public final AppCompatSeekBar playerSeekBar;

  @NonNull
  public final CustomProgressView progressBarVod;

  @NonNull
  public final FrameLayout relatedLayout;

  @NonNull
  public final RecyclerView relatedRecyclerview;

  @NonNull
  public final FrameLayout rootLayout;

  @NonNull
  public final HorizontalScrollView scrollView;

  @NonNull
  public final LinearLayout skipAds;

  @NonNull
  public final TextView skipTimer;

  @NonNull
  public final TextView start;

  @NonNull
  public final View stateDetector;

  @NonNull
  public final TextView subscribe;

  @NonNull
  public final TextView subscribeFull;

  @NonNull
  public final FrameLayout subscribeWrapper;

  @NonNull
  public final FrameLayout subscribeWrapperFull;

  @NonNull
  public final TextView tvLiveUserName;

  @NonNull
  public final TextView tvLiveUserNameFull;

  @NonNull
  public final TextView tvLiveViewCount;

  @NonNull
  public final TextView tvLiveViewCountFull;

  @NonNull
  public final DMotionLayout videoMotionLayout;

  @NonNull
  public final CustomPlayerView videoView;

  @NonNull
  public final CustomPlayerView videoViewAds;

  @NonNull
  public final CardView videoViewContainer;

  protected FragmentVideoBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LayoutAdsBinding ads, AppBarLayout appBar, FrameLayout bottomSheet,
      FrameLayout bottomSheetAddComment, FrameLayout bottomSheetBuuz,
      FrameLayout bottomSheetBuuzFull, FrameLayout bottomSheetSticker,
      FrameLayout bottomSheetStickerFull, FrameLayout btnBuuz, FrameLayout btnBuuzFull,
      FrameLayout btnChat, AppCompatImageView btnClose, FrameLayout btnClose1,
      FrameLayout btnCollapse, FrameLayout btnFav, FrameLayout btnFavFull, ImageView btnFavImage,
      ImageView btnFavImageFull, ImageView btnFavImageSelected, ImageView btnFavImageSelectedFull,
      TextView btnLiveFollow, TextView btnLiveFollowFull, ImageView btnNext,
      AppCompatImageView btnPlay, ImageView btnPlayBig, ImageView btnPre, FrameLayout btnScreenSize,
      FrameLayout btnScreenSizeFull, FrameLayout btnSettings, FrameLayout btnShare,
      FrameLayout btnShareFull, FrameLayout btnSubscribe, FrameLayout btnSubscribeFull,
      FrameLayout btnVolumeToggle, FrameLayout btnVolumeToggleFull, LinearLayout chatLayout,
      LinearLayout chatLayoutFull, TextView chatMessageField, EditText chatMessageFieldFull,
      FrameLayout chatMessageSend, FrameLayout chatMessageSendFull, FrameLayout chatMessageSticker,
      FrameLayout chatMessageStickerFull, TextView collapsedChannel,
      LinearLayout collapsedControlLayout, View collapsedDivider, TextView collapsedTitle,
      LinearLayout containerRecyclerview, CoordinatorLayout content, FrameLayout contentContainer,
      CustomFrameLayout controllers, LottieAnimationView dcLoader, View dim, TextView duration,
      CircleImageView ivLiveUser, CircleImageView ivLiveUserFull, FrameLayout layoutVideoAds,
      CardView live18, CardView live18Full, FrameLayout liveBtnClose1, FrameLayout liveBtnSettings,
      RecyclerView liveChatRecyclerview, LinearLayout liveFollowLayout,
      CoordinatorLayout liveFullScreenControllers, TextView liveFullScreenTitle,
      LinearLayout liveHeader, TextView liveScreenTitle, ImageView muted, ImageView mutedFull,
      ImageView notMuted, ImageView notMutedFull, AppCompatSeekBar playerSeekBar,
      CustomProgressView progressBarVod, FrameLayout relatedLayout,
      RecyclerView relatedRecyclerview, FrameLayout rootLayout, HorizontalScrollView scrollView,
      LinearLayout skipAds, TextView skipTimer, TextView start, View stateDetector,
      TextView subscribe, TextView subscribeFull, FrameLayout subscribeWrapper,
      FrameLayout subscribeWrapperFull, TextView tvLiveUserName, TextView tvLiveUserNameFull,
      TextView tvLiveViewCount, TextView tvLiveViewCountFull, DMotionLayout videoMotionLayout,
      CustomPlayerView videoView, CustomPlayerView videoViewAds, CardView videoViewContainer) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ads = ads;
    setContainedBinding(this.ads);;
    this.appBar = appBar;
    this.bottomSheet = bottomSheet;
    this.bottomSheetAddComment = bottomSheetAddComment;
    this.bottomSheetBuuz = bottomSheetBuuz;
    this.bottomSheetBuuzFull = bottomSheetBuuzFull;
    this.bottomSheetSticker = bottomSheetSticker;
    this.bottomSheetStickerFull = bottomSheetStickerFull;
    this.btnBuuz = btnBuuz;
    this.btnBuuzFull = btnBuuzFull;
    this.btnChat = btnChat;
    this.btnClose = btnClose;
    this.btnClose1 = btnClose1;
    this.btnCollapse = btnCollapse;
    this.btnFav = btnFav;
    this.btnFavFull = btnFavFull;
    this.btnFavImage = btnFavImage;
    this.btnFavImageFull = btnFavImageFull;
    this.btnFavImageSelected = btnFavImageSelected;
    this.btnFavImageSelectedFull = btnFavImageSelectedFull;
    this.btnLiveFollow = btnLiveFollow;
    this.btnLiveFollowFull = btnLiveFollowFull;
    this.btnNext = btnNext;
    this.btnPlay = btnPlay;
    this.btnPlayBig = btnPlayBig;
    this.btnPre = btnPre;
    this.btnScreenSize = btnScreenSize;
    this.btnScreenSizeFull = btnScreenSizeFull;
    this.btnSettings = btnSettings;
    this.btnShare = btnShare;
    this.btnShareFull = btnShareFull;
    this.btnSubscribe = btnSubscribe;
    this.btnSubscribeFull = btnSubscribeFull;
    this.btnVolumeToggle = btnVolumeToggle;
    this.btnVolumeToggleFull = btnVolumeToggleFull;
    this.chatLayout = chatLayout;
    this.chatLayoutFull = chatLayoutFull;
    this.chatMessageField = chatMessageField;
    this.chatMessageFieldFull = chatMessageFieldFull;
    this.chatMessageSend = chatMessageSend;
    this.chatMessageSendFull = chatMessageSendFull;
    this.chatMessageSticker = chatMessageSticker;
    this.chatMessageStickerFull = chatMessageStickerFull;
    this.collapsedChannel = collapsedChannel;
    this.collapsedControlLayout = collapsedControlLayout;
    this.collapsedDivider = collapsedDivider;
    this.collapsedTitle = collapsedTitle;
    this.containerRecyclerview = containerRecyclerview;
    this.content = content;
    this.contentContainer = contentContainer;
    this.controllers = controllers;
    this.dcLoader = dcLoader;
    this.dim = dim;
    this.duration = duration;
    this.ivLiveUser = ivLiveUser;
    this.ivLiveUserFull = ivLiveUserFull;
    this.layoutVideoAds = layoutVideoAds;
    this.live18 = live18;
    this.live18Full = live18Full;
    this.liveBtnClose1 = liveBtnClose1;
    this.liveBtnSettings = liveBtnSettings;
    this.liveChatRecyclerview = liveChatRecyclerview;
    this.liveFollowLayout = liveFollowLayout;
    this.liveFullScreenControllers = liveFullScreenControllers;
    this.liveFullScreenTitle = liveFullScreenTitle;
    this.liveHeader = liveHeader;
    this.liveScreenTitle = liveScreenTitle;
    this.muted = muted;
    this.mutedFull = mutedFull;
    this.notMuted = notMuted;
    this.notMutedFull = notMutedFull;
    this.playerSeekBar = playerSeekBar;
    this.progressBarVod = progressBarVod;
    this.relatedLayout = relatedLayout;
    this.relatedRecyclerview = relatedRecyclerview;
    this.rootLayout = rootLayout;
    this.scrollView = scrollView;
    this.skipAds = skipAds;
    this.skipTimer = skipTimer;
    this.start = start;
    this.stateDetector = stateDetector;
    this.subscribe = subscribe;
    this.subscribeFull = subscribeFull;
    this.subscribeWrapper = subscribeWrapper;
    this.subscribeWrapperFull = subscribeWrapperFull;
    this.tvLiveUserName = tvLiveUserName;
    this.tvLiveUserNameFull = tvLiveUserNameFull;
    this.tvLiveViewCount = tvLiveViewCount;
    this.tvLiveViewCountFull = tvLiveViewCountFull;
    this.videoMotionLayout = videoMotionLayout;
    this.videoView = videoView;
    this.videoViewAds = videoViewAds;
    this.videoViewContainer = videoViewContainer;
  }

  @NonNull
  public static FragmentVideoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_video, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentVideoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentVideoBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_video, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentVideoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_video, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentVideoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentVideoBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_video, null, false, component);
  }

  public static FragmentVideoBinding bind(@NonNull View view) {
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
  public static FragmentVideoBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentVideoBinding)bind(component, view, notq.dccast.R.layout.fragment_video);
  }
}
