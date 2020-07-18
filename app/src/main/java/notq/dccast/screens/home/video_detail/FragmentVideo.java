package notq.dccast.screens.home.video_detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.realm.Realm;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.api.home.HomeAPIInterface;
import notq.dccast.api.home.ProfileAPIInterface;
import notq.dccast.api.home.VideoDetailAPIInterface;
import notq.dccast.api.live.LiveAPIInterface;
import notq.dccast.databinding.DialogVideoReportBinding;
import notq.dccast.databinding.DialogVideoSettingsBinding;
import notq.dccast.databinding.FragmentVideoBinding;
import notq.dccast.model.ModelAddToRecentResult;
import notq.dccast.model.ModelAddToRecentWrapper;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.ModelVideoWrapper;
import notq.dccast.model.ads.ModelAds;
import notq.dccast.model.comment.ModelComment;
import notq.dccast.model.favorite.ModelCreateFavorite;
import notq.dccast.model.favorite.ModelFavorite;
import notq.dccast.model.favorite.ModelFavoriteWrapper;
import notq.dccast.model.friends.ModelFriendRequestResult;
import notq.dccast.model.friends.ModelFriendResult;
import notq.dccast.model.live.ModelBlockResult;
import notq.dccast.model.live.ModelBlockResultWrapper;
import notq.dccast.model.subscribe.ResponseCreateSubscribe;
import notq.dccast.model.subscribe.ResponseSubscribe;
import notq.dccast.model.subscribe.ResponseSubscribeWrapper;
import notq.dccast.model.user.ModelResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.profile.ModelProfile;
import notq.dccast.screens.home.ActivityHome;
import notq.dccast.screens.home.adapter.AdapterRelatedVideos;
import notq.dccast.screens.home.adapter.LiveChatAdapter;
import notq.dccast.screens.home.interfaces.HomeChildFragmentListener;
import notq.dccast.screens.home.interfaces.LikeDislikeListener;
import notq.dccast.screens.home.interfaces.RelatedItemListener;
import notq.dccast.screens.home.radio_mode.BaseFragment;
import notq.dccast.screens.home.search.ActivitySearch;
import notq.dccast.screens.home.video_service.PopupService;
import notq.dccast.screens.login.ActivityLogin;
import notq.dccast.screens.navigation_menu.content.my_channel.ActivityMyChannel;
import notq.dccast.util.ChangeNicknameDialog;
import notq.dccast.util.DialogHelper;
import notq.dccast.util.KeyboardUtil;
import notq.dccast.util.LockableBottomSheetBehavior;
import notq.dccast.util.LoginService;
import notq.dccast.util.PopUpUtil;
import notq.dccast.util.ShareService;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static notq.dccast.screens.home.radio_mode.PlayerManager.getService;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVideo extends BaseFragment
    implements View.OnClickListener, RelatedItemListener,
    LikeDislikeListener, LiveChatHelper.onReceivedMemberCount, LiveChatHelper.onUserMessageBlocked {
  private static final int BUUZ = 1;
  private static final int SHARE = 2;
  private static final int COMMENT = 3;
  private static final int STICKER = 4;
  private static final int BUUZ_FULL = 5;
  public static int OVERLAY_PERMISSION_REQ = 1234;
  private final int LOGIN_REQUEST = 101;
  private ShareService shareService;
  private FragmentVideoBinding binding;
  private DialogVideoReportBinding reportDialogBinding;

  private boolean homeVideo = true;
  private boolean hasNavigation = false;

  private int initialNavigationBarHeight = 0;

  //REQUESTS
  private Call<ResponseSubscribeWrapper> subscribeRequest;
  private Call<ModelFavoriteWrapper> favoriteRequest;
  private Call<ModelResult> friendRequest;
  private Call<ModelVideoWrapper> relatedVideoRequest;

  private int state;
  private int subscribeId;
  private float currentVolume;
  private boolean isFavSelected = false;
  private boolean isCollapsed = false;

  private ModelVideo videoItem;
  private ModelListHeader headerVideoItem;
  private ModelFavorite favoriteItem;
  private ModelCreateFavorite modelCreateFavorite;

  private AlertDialog settingsDialog;
  private ChangeNicknameDialog changeNicknameDialog;
  private AlertDialog reportVodLive;
  private SimpleExoPlayer player;
  private boolean isPaused = false;
  private SimpleExoPlayer adsPlayer;
  private BottomSheetBehavior sheetBehavior;
  private BottomSheetBehavior sheetBehaviorAddComment;
  private BottomSheetBehavior sheetBehaviorSticker;
  private BottomSheetBehavior sheetBehaviorStickerFull;
  private BottomSheetBehavior sheetBehaviorBuuz;
  private BottomSheetBehavior sheetBehaviorBuuzFull;
  private Handler progressHandler;
  private HomeChildFragmentListener childFragmentListener;
  private AdapterRelatedVideos relatedVideoAdapter;

  private LiveChatHelper liveChatHelper;
  private LiveChatAdapter liveChatAdapter;
  private int colorFollowing, colorFollow;
  private int bgFollowing, bgFollow;

  private boolean isLive;
  private boolean isFullscreen;

  private KeyboardUtil keyboardUtil;

  private int videoId = -1;

  private boolean vodCountUpdated = false;

  private String currentVideoResolution = "";
  private long currentDuration = 0l;

  private Timer liveAdsTimer;
  private Timer skipButtonTimer;
  private Timer vodAdsTimer;
  private int timer = 0;

  private boolean isFirst = true;
  private Runnable runnable = new Runnable() {
    @Override
    public void run() {
      if (runnable != null) {
        progressHandler.postDelayed(runnable, 100);
      }

      if (player != null) {
        binding.start.setText(
            DCCastApplication.utils.milliSecondsToTimer(player.getCurrentPosition()));

        binding.playerSeekBar.setProgress(
            (int) (player.getCurrentPosition() * 100 / player.getDuration()));
      }
    }
  };
  private String videoUrl = "";
  private ProgressDialog progressDialog;

  private long lastDuration = 0L;

  private boolean liveChatBlocked = false;
  private boolean isPortrait = false;

  private Handler messageBlockHandler;
  private Runnable messageBlockRunnable;

  @Override
  public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      Bundle bundle = getArguments();
      videoItem = (ModelVideo) bundle.getSerializable("data");
      headerVideoItem = (ModelListHeader) bundle.getSerializable("header_data");
      homeVideo = bundle.getBoolean("home", false);

      isPortrait =
          videoItem != null ? videoItem.isPortraitVideo() : headerVideoItem.isPortraitVideo();

      if (videoItem != null) {
        videoId = videoItem.getId();
      } else if (headerVideoItem != null) {
        videoId = headerVideoItem.getId();
      }
    }
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    //No call for super(). Bug on API Level > 11.
  }

  @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
  @Nullable
  public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false);

    isLive =
        (videoItem != null && videoItem.getCategory().equalsIgnoreCase("LIVE")) || (headerVideoItem
            != null && headerVideoItem.getCategory().equalsIgnoreCase("LIVE"));

    initListeners();
    initPlayer();
    init();
    initBottomSheet();
    initChangeNicknameDialog();
    initSettingsDialog();
    initReportDialog();

    getAds();

    if (isPortrait) {
      binding.videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
      binding.videoView.requestFocus();
      Timber.e("FIXED_HEIGHT");
    } else {
      binding.videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
      binding.videoView.requestFocus();
      Timber.e("FIXED_WIDTH");
    }

    if (isLive) {
      binding.btnPre.setVisibility(View.GONE);
      binding.btnNext.setVisibility(View.GONE);
      binding.playerSeekBar.setVisibility(View.INVISIBLE);
      binding.start.setVisibility(View.INVISIBLE);
      binding.duration.setVisibility(View.INVISIBLE);
      binding.btnPlayBig.setVisibility(View.INVISIBLE);

      checkLiveMemberFull();

      boolean isChatLock = false;
      boolean isVideoEnabled = true;
      String imageUrl = "";
      if (videoItem != null) {
        isChatLock = videoItem.isLiveChatDisable();
        if (videoItem.getMediaType() != null) {
          String mediaType = videoItem.getMediaType();
          if (mediaType.equalsIgnoreCase(getString(R.string.item_live_type_album))) {
            isVideoEnabled = false;
            imageUrl = videoItem.getLiveVodId();
          } else if (mediaType.equalsIgnoreCase(getString(R.string.item_live_type_radio_mode))) {
            isVideoEnabled = false;
            imageUrl = videoItem.getMediaThumbnail();
          }
        }
      }

      if (headerVideoItem != null) {
        isChatLock = headerVideoItem.getLiveChatDisable();

        if (headerVideoItem.getMediaType() != null) {
          String mediaType = headerVideoItem.getMediaType();
          if (mediaType.equalsIgnoreCase(getString(R.string.item_live_type_album))) {
            isVideoEnabled = false;
            imageUrl = videoItem.getLiveVodId();
          } else if (mediaType.equalsIgnoreCase(getString(R.string.item_live_type_radio_mode))) {
            isVideoEnabled = false;
            imageUrl = videoItem.getMediaThumbnail();
          }
        }
      }

      if (!isVideoEnabled) {
        binding.videoView.setUseArtwork(true);
        Glide.with(getActivity()).load(notq.dccast.util.Util.getValidateUrl(imageUrl)).listener(
            new RequestListener<Drawable>() {
              @Override public boolean onLoadFailed(@androidx.annotation.Nullable GlideException e,
                  Object model, Target<Drawable> target, boolean isFirstResource) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                  getActivity().runOnUiThread(new Runnable() {
                    @Override public void run() {
                      Timber.e("SET DEFAULT LOAD ERROR: " + e.getMessage());
                    }
                  });
                }
                return false;
              }

              @Override public boolean onResourceReady(Drawable resource, Object model,
                  Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource,
                  boolean isFirstResource) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                  getActivity().runOnUiThread(new Runnable() {
                    @Override public void run() {
                      Timber.e("SET DEFAULT LOAD DONE");
                      binding.videoView.setDefaultArtwork(resource);
                    }
                  });
                }
                return false;
              }
            }).submit();
      }

      initLiveChat(isChatLock);

      binding.btnCollapse.setVisibility(View.GONE);
    } else {
      showLoader();

      binding.btnCollapse.setVisibility(View.VISIBLE);

      getLikeAndDislikeRequest(false, true, videoId);
      getLikeAndDislikeRequest(false, false, videoId);

      if (!vodCountUpdated) {
        updateVodCount();
      }

      initRelatedVideoView();
      getRelatedVideosRequest();
    }

    hasNavigation = notq.dccast.util.Util.getNavigationBarSize(getActivity());
    Timber.e("NAVIGATION BAR: " + (hasNavigation ? "TRUE" : "false"));
    if (hasNavigation) {
      setNavigationBarHeight();
    }

    keyboardUtil = new KeyboardUtil(getActivity(), binding.getRoot());
    keyboardUtil.enable();

    initVideoTitle();

    checkRecent();

    return binding.getRoot();
  }

  public void setFullScreenControllersHeight() {
    int height = getNavigationBarHeight();
    if (hasNavigation) {
      if (height > 0) {
        binding.liveFullScreenControllers.setPadding(0, 0, 0,
            height);
      } else if (initialNavigationBarHeight > 0) {
        binding.liveFullScreenControllers.setPadding(0, 0, 0,
            initialNavigationBarHeight);
      }

      initHeight();
    }
  }

  public void removeFullScreenControllersHeight() {
    binding.liveFullScreenControllers.setPadding(0, 0, 0,
        0);
  }

  private void initHeight() {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    Objects.requireNonNull(getActivity())
        .getWindowManager()
        .getDefaultDisplay()
        .getMetrics(displayMetrics);
    int displayHeight = displayMetrics.heightPixels;
    int displayWidth = displayMetrics.widthPixels;
    int bottomSheetHeight =
        displayHeight - DCCastApplication.utils.pxFromDp(203) + getNavigationBarHeight();

    binding.bottomSheetBuuzFull.getLayoutParams().height =
        (isPortrait ? bottomSheetHeight : displayWidth);
    binding.bottomSheetBuuzFull.requestLayout();
  }

  public int getNavigationBarHeight() {
    Resources resources = getResources();
    int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
    int navigationBarHeight = 0;
    if (resourceId > 0) {
      navigationBarHeight = resources.getDimensionPixelSize(resourceId);
    }

    int calculatedHeight = notq.dccast.util.Util.getNavigationDifference(getActivity());

    return calculatedHeight - navigationBarHeight;
  }

  public void setNavigationBarHeight() {
    int height = getNavigationBarHeight();
    if (height > 0) {
      initialNavigationBarHeight = height;
      binding.videoMotionLayout.setPadding(0, 0, 0,
          height);
    } else if (initialNavigationBarHeight > 0) {
      binding.videoMotionLayout.setPadding(0, 0, 0,
          initialNavigationBarHeight);
    }
  }

  public void removeNavigationBarHeight() {
    binding.videoMotionLayout.setPadding(0, 0, 0,
        0);
  }

  private void updateVodCount() {
    vodCountUpdated = true;

    int videoId = videoItem != null ? videoItem.getId() : headerVideoItem.getId();
    int viewsCount = (videoItem != null ? videoItem.getViews() : headerVideoItem.getViews()) + 1;

    JSONObject params = new JSONObject();
    try {
      params.put("views", viewsCount);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    RequestBody updateLiveVideoRequest =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            String.valueOf(params));

    LiveAPIInterface apiInterface = APIClient.getClient().create(LiveAPIInterface.class);
    Call<JsonObject> call =
        apiInterface.updateLive(videoId, updateLiveVideoRequest);

    call.enqueue(new Callback<JsonObject>() {
      @Override
      public void onResponse(@NonNull Call<JsonObject> call,
          @NonNull Response<JsonObject> response) {
        JsonObject result = response.body();
        if (result != null) {
          Timber.e("SUCCESS UPDATE");

          FragmentActivity activity = getActivity();
          if (activity instanceof ActivityHome) {
            if (videoItem != null) {
              videoItem.views = viewsCount;

              ((ActivityHome) activity).updateViewCount(videoItem);
              relatedVideoAdapter.setHeaderData(videoItem);
            } else if (headerVideoItem != null) {
              headerVideoItem.views = viewsCount;

              ((ActivityHome) activity).updateViewCount(headerVideoItem);
              relatedVideoAdapter.setHeaderData(headerVideoItem);
            }
          }
        } else {
          Timber.e("FAILED UPDATED");
        }
      }

      @Override
      public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
        call.cancel();
        Timber.e("ERROR vod count update: " + t.getMessage());
      }
    });
  }

  private void checkLiveMemberFull() {

    int videoId = videoItem != null ? videoItem.getId() : headerVideoItem.getId();
    Call<ModelVideoWrapper> call =
        APIClient.getClient().create(HomeAPIInterface.class).getMediaById(videoId);
    call.enqueue(new Callback<ModelVideoWrapper>() {
      @Override public void onResponse(@NonNull Call<ModelVideoWrapper> call,
          @NonNull Response<ModelVideoWrapper> response) {
        ModelVideoWrapper headerWrapper = response.body();

        hideLoader();

        if (headerWrapper == null
            || headerWrapper.getVideoList() == null
            || headerWrapper.getVideoList().size() == 0) {
          Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
          return;
        }

        ModelVideo openVideo = headerWrapper.getVideoList().get(0);

        if (openVideo != null) {
          int liveMember = 0;
          int views = 0;
          try {
            liveMember = Integer.parseInt(openVideo.getLiveMember());
            views = openVideo.getViews();

            if (views + 1 > liveMember) {
              Toast.makeText(getActivity(), getString(R.string.live_full), Toast.LENGTH_LONG)
                  .show();
              hideFragment();
            }
          } catch (Exception ex) {
            Timber.e("EXCEPTION: " + ex.getMessage());
          }
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelVideoWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideLoader();
        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
      }
    });
  }

  private void checkRecent() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null || loginUser.getStopRecentView()) {
      return;
    }

    int userId = loginUser.getId();

    final int videoId = (videoItem != null) ? videoItem.getId()
        : (headerVideoItem != null ? headerVideoItem.getId() : -1);
    if (videoId == -1) {
      return;
    }
    Call<ModelAddToRecentWrapper> call = APIClient.getClient()
        .create(VideoDetailAPIInterface.class)
        .getRecent(loginUser.getId(), videoId);

    call.enqueue(new Callback<ModelAddToRecentWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelAddToRecentWrapper> call,
          @NonNull Response<ModelAddToRecentWrapper> response) {
        ModelAddToRecentWrapper videoWrapper = response.body();

        if (videoWrapper == null
            || videoWrapper.getRecentMedias() == null
            || videoWrapper.getRecentMedias().size() == 0) {
          addToRecent(userId, videoId);
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelAddToRecentWrapper> call, @NonNull Throwable t) {
        call.cancel();
        Timber.e("Error get recent: %s", t.getMessage());
      }
    });
  }

  private void addToRecent(int userId, int videoId) {
    Call<ModelAddToRecentResult> call = APIClient.getClient()
        .create(VideoDetailAPIInterface.class)
        .createRecent(userId, videoId);

    call.enqueue(new Callback<ModelAddToRecentResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelAddToRecentResult> call,
          @NonNull Response<ModelAddToRecentResult> response) {
        ModelAddToRecentResult result = response.body();

        if (result != null) {
          Timber.e("Successfully added to recent");
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelAddToRecentResult> call, @NonNull Throwable t) {
        call.cancel();
        Timber.e("Error to add recent: %s", t.getMessage());
      }
    });
  }

  private void init() {
    binding.content.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

      }
    });
  }

  @Override public void onResume() {
    super.onResume();
    getIsFavoriteRequest();
    getIsSubscribedRequest();
    checkCanAddFriend();
    checkIs18();

    if (binding.videoView != null && binding.videoView.getPlayer() != null && player != null) {
      binding.videoView.setPlayer(player);
    }
  }

  @Override
  public void onActivityCreated(@androidx.annotation.Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Objects.requireNonNull(getView()).setFocusableInTouchMode(true);

    getView().requestFocus();
    getView().setOnKeyListener((v, keyCode, event) -> {
      if (event.getAction() == KeyEvent.ACTION_DOWN) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

          return checkCanBack();
        }
      }

      return false;
    });
  }

  private boolean checkCanBack() {
    if (sheetBehaviorSticker.getState() == BottomSheetBehavior.STATE_EXPANDED) {
      closeBottomSheetSticker();
      return true;
    }

    if (sheetBehaviorStickerFull.getState() == BottomSheetBehavior.STATE_EXPANDED) {
      closeBottomSheetSticker();
      return true;
    }

    if (sheetBehaviorBuuzFull.getState() == BottomSheetBehavior.STATE_EXPANDED) {
      closeBottomSheetBuuz();
      return true;
    }

    if (sheetBehaviorBuuz.getState() == BottomSheetBehavior.STATE_EXPANDED) {
      closeBottomSheetBuuz();
      return true;
    }

    if (sheetBehaviorAddComment.getState() == BottomSheetBehavior.STATE_EXPANDED) {
      closeBottomSheetAddComment();
      return true;
    }

    if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
      closeBottomSheet();
      return true;
    } else {
      if (isFullscreen) {
        changeScreenOrientation();
        return true;
      } else {
        if (binding.layoutVideoAds.getVisibility() == View.VISIBLE) {
          return true;
        }
        videoToCollapse();
        return true;
      }
    }
  }

  public void onBackPressed() {
    checkCanBack();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode,
      @androidx.annotation.Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == LOGIN_REQUEST && resultCode == Activity.RESULT_OK) {
      if (!isLive) {
        getLikeAndDislikeRequest(false, true, videoId);
        getLikeAndDislikeRequest(false, false, videoId);
      }

      return;
    }
    if (data == null) {
      Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
      return;
    }
    if (shareService != null) {
      shareService.callbackOnActivityResult(requestCode, resultCode, data);
    }
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    if (context instanceof HomeChildFragmentListener) {
      childFragmentListener = (HomeChildFragmentListener) context;
      childFragmentListener.fragmentExpanded();
    }
  }

  @Override public void onDetach() {
    Timber.e("STOP- onDetach fragmentVideo");
    if (player != null && !isPaused) {
      pauseVideo();
    }
    super.onDetach();
  }

  @Override public void onPause() {
    pauseVideo();
    Timber.e("STOP- onPause");

    super.onPause();
  }

  private void cancelAds() {
    if (liveAdsTimer != null) {
      liveAdsTimer.cancel();
      liveAdsTimer.purge();
    }

    if (vodAdsTimer != null) {
      vodAdsTimer.cancel();
      vodAdsTimer.purge();
    }
  }

  @Override public void onDestroyView() {
    Timber.e("onDestroyView");

    super.onDestroyView();

    if (liveChatHelper != null) {
      liveChatHelper.stopGetMembersTimer();
    }
    if (liveChatHelper != null) {
      liveChatHelper.offSocket();
    }

    if (settingsDialog != null && settingsDialog.isShowing()) {
      settingsDialog.dismiss();
    }

    cancelAds();

    if (skipButtonTimer != null) {
      skipButtonTimer.cancel();
      skipButtonTimer.purge();
    }

    hideProgressDialog();

    if (messageBlockHandler != null && messageBlockRunnable != null) {
      messageBlockHandler.removeCallbacks(messageBlockRunnable);
    }

    if (subscribeRequest != null) {
      subscribeRequest.cancel();
    }

    if (favoriteRequest != null) {
      favoriteRequest.cancel();
    }

    if (friendRequest != null) {
      friendRequest.cancel();
    }

    if (relatedVideoRequest != null) {
      relatedVideoRequest.cancel();
    }
  }

  @Override public void onLikeClicked() {
    int mediaId = 0;
    if (videoItem != null) {
      mediaId = videoItem.getId();
    } else if (headerVideoItem != null) {
      mediaId = headerVideoItem.getId();
    }

    if (LoginService.getLoginUser() == null) {
      navigateToLogin();
      return;
    }

    getLikeAndDislikeRequest(true, true, mediaId);
  }

  @Override public void onDislikeClicked() {
    int mediaId = 0;
    if (videoItem != null) {
      mediaId = videoItem.getId();
    } else if (headerVideoItem != null) {
      mediaId = headerVideoItem.getId();
    }

    if (LoginService.getLoginUser() == null) {
      navigateToLogin();
      return;
    }

    getLikeAndDislikeRequest(true, false, mediaId);
  }

  @Override
  public void onRelatedItemClicked(ModelVideo modelVideo) {
    //if (binding.videoViewAds.getVisibility() == View.VISIBLE) {
    //  return;
    //}
    binding.skipAds.performClick();
    cancelAds();

    isFirst = true;
    playVideo(modelVideo);

    getRelatedVideosRequest();
    getAds();
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btnPlay: {
        if (binding.stateDetector.getHeight() > 0) {
          if (isFullscreen) {
            if (isLive) {
              toggleLiveFullScreenControllers();
            } else {
              toggleControllersVisibility();
            }
          } else {
            toggleControllersVisibility();
          }
        } else {
          togglePlayAndPause();
        }

        break;
      }

      case R.id.btnPlayBig: {
        togglePlayAndPause();
        break;
      }

      case R.id.view_as_pop_up: {
        showPopupWindow();
        break;
      }

      case R.id.view_as_radio: {
        if (getService() == null) {
          playerManager.bind();
        }
        managerBinding();
        break;
      }

      case R.id.live_btn_close_1:
      case R.id.btn_close_1: {
        closeItself();
        break;
      }

      case R.id.btnClose: {
        if (binding.stateDetector.getHeight() > 0) {
          if (isFullscreen) {
            if (isLive) {
              toggleLiveFullScreenControllers();
            } else {
              toggleControllersVisibility();
            }
          } else {
            toggleControllersVisibility();
          }
        } else {
          closeItself();
        }

        break;
      }

      case R.id.btn_collapse: {
        showOptionsDialog();
        break;
      }

      case R.id.btn_volume_toggle_full:
      case R.id.btn_volume_toggle: {
        if (isFullscreen) {
          if (player.getVolume() != 0) {
            binding.notMutedFull.setVisibility(View.GONE);
            binding.mutedFull.setVisibility(View.VISIBLE);
            player.setVolume(0f);
          } else {
            binding.notMutedFull.setVisibility(View.VISIBLE);
            binding.mutedFull.setVisibility(View.GONE);
            player.setVolume(currentVolume);
          }
        } else {
          if (player.getVolume() != 0) {
            binding.notMuted.setVisibility(View.GONE);
            binding.muted.setVisibility(View.VISIBLE);
            player.setVolume(0f);
          } else {
            binding.notMuted.setVisibility(View.VISIBLE);
            binding.muted.setVisibility(View.GONE);
            player.setVolume(currentVolume);
          }
        }

        break;
      }

      case R.id.chat_message_sticker_full: {
        if (liveChatBlocked) {
          showMessageDisabled();
          return;
        }
        openBottomSheet(STICKER);
        break;
      }

      case R.id.btn_buuz_full: {
        openBottomSheet(BUUZ_FULL);
        break;
      }

      case R.id.chat_layout: {
        openAddCommentToLive();
        break;
      }

      case R.id.btn_chat: {
        if (liveChatBlocked) {
          showMessageDisabled();
          return;
        }
        openBottomSheet(COMMENT);
        break;
      }

      case R.id.btn_buuz: {
        openBottomSheet(BUUZ);
        break;
      }

      case R.id.btn_fav_full: {
        if (LoginService.getLoginUser() != null) {
          if (isFavSelected) {
            deleteFavoriteRequest();
            binding.btnFavImageFull.setVisibility(View.VISIBLE);
            binding.btnFavImageSelectedFull.setVisibility(View.GONE);
          } else {
            createFavoriteRequest();
            binding.btnFavImageFull.setVisibility(View.GONE);
            binding.btnFavImageSelectedFull.setVisibility(View.VISIBLE);
          }

          isFavSelected = !isFavSelected;
        } else {
          navigateToLogin();
        }
        break;
      }
      case R.id.btn_fav: {
        if (LoginService.getLoginUser() != null) {
          if (isFavSelected) {
            deleteFavoriteRequest();
            binding.btnFavImage.setVisibility(View.VISIBLE);
            binding.btnFavImageSelected.setVisibility(View.GONE);
          } else {
            createFavoriteRequest();
            binding.btnFavImage.setVisibility(View.GONE);
            binding.btnFavImageSelected.setVisibility(View.VISIBLE);
          }

          isFavSelected = !isFavSelected;
        } else {
          navigateToLogin();
        }
        break;
      }

      case R.id.btn_share_full:
      case R.id.btn_share: {
        openBottomSheet(SHARE);
        break;
      }

      case R.id.btn_subscribe_full:
      case R.id.btn_subscribe: {
        if (LoginService.getLoginUser() != null) {
          toggleSubscribe();
        } else {
          navigateToLogin();
        }
        break;
      }

      case R.id.live_btn_settings:
      case R.id.btn_settings: {
        pauseVideo();
        settingsDialog.show();
        break;
      }

      case R.id.btn_screen_size_full:
      case R.id.btn_screen_size: {
        changeScreenOrientation();
        break;
      }

      case R.id.btn_sticker_close: {
        closeBottomSheetSticker();
        break;
      }
      case R.id.btn_comment_close:
      case R.id.btn_close_bs: {
        closeBottomSheet();
        closeBottomSheetBuuz();
        break;
      }

      case R.id.btn_edit_comment_close: {
        closeBottomSheetAddComment();
        break;
      }

      case R.id.btnPre: {
        playVideo(relatedVideoAdapter.getPreVideo());
        break;
      }

      case R.id.btnNext: {
        playVideo(relatedVideoAdapter.getNextVideo());
        break;
      }

      case R.id.report: {
        checkVodLiveIsReported();
        break;
      }

      case R.id.cancel_report: {
        dismissReport();
        break;
      }

      case R.id.change_nickname: {
        if (settingsDialog != null) {
          settingsDialog.dismiss();
        }

        changeNicknameDialog.showDialog();

        break;
      }

      case R.id.send_report: {
        if (reportDialogBinding.reportField.getText().toString().isEmpty()) {
          Toast.makeText(getContext(), getString(R.string.validate_report_reason),
              Toast.LENGTH_SHORT).show();
        } else {
          if (reportDialogBinding.reportTypes.getCheckedRadioButtonId() == -1) {
            return;
          }

          sendReportVideoRequest();
        }
        break;
      }

      case R.id.btn_live_follow_full:
      case R.id.btn_live_follow: {
        if (LoginService.getLoginUser() == null) {
          navigateToLogin();
          return;
        }

        int userId = (videoItem != null ? videoItem.getUser().getId()
            : (headerVideoItem != null ? headerVideoItem.getUser().getId() : -1));
        if (userId == -1) {
          return;
        }
        if (binding.btnLiveFollow.getText()
            .toString()
            .equalsIgnoreCase(getString(R.string.add_friend_add_friend))) {
          addFriend(userId);
        } else {
          removeFriend(userId);
        }
        break;
      }
    }
  }

  private void videoToCollapse() {
    binding.videoMotionLayout.transitionToEnd();
  }

  private void showOptionsDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle(R.string.popup_mode_select)
        .setItems(R.array.popup_mode, (dialog, which) -> {
          switch (which) {
            case 0:
              managerBinding();
              break;
            case 1:
              showPopupWindow();
              break;
            default:
              break;
          }
        });

    builder.create().show();
  }

  @SuppressLint("ClickableViewAccessibility") private void initListeners() {
    binding.btnPlay.setOnClickListener(this);
    binding.btnPlayBig.setOnClickListener(this);
    binding.btnClose.setOnClickListener(this);
    binding.btnCollapse.setOnClickListener(this);
    binding.btnSettings.setOnClickListener(this);
    binding.btnClose1.setOnClickListener(this);
    binding.btnChat.setOnClickListener(this);
    binding.btnBuuz.setOnClickListener(this);
    binding.btnBuuzFull.setOnClickListener(this);
    binding.btnFav.setOnClickListener(this);
    binding.btnFavFull.setOnClickListener(this);
    binding.btnShare.setOnClickListener(this);
    binding.btnShareFull.setOnClickListener(this);
    binding.btnSubscribe.setOnClickListener(this);
    binding.btnSubscribeFull.setOnClickListener(this);
    binding.btnNext.setOnClickListener(this);
    binding.btnPre.setOnClickListener(this);
    binding.btnVolumeToggle.setOnClickListener(this);
    binding.btnVolumeToggleFull.setOnClickListener(this);
    binding.btnLiveFollow.setOnClickListener(this);
    binding.btnLiveFollowFull.setOnClickListener(this);
    binding.liveBtnClose1.setOnClickListener(this);
    binding.liveBtnSettings.setOnClickListener(this);
    binding.videoMotionLayout._setOnTouchListener(() -> {
      if (isFullscreen) {
        if (isLive) {
          toggleLiveFullScreenControllers();
        } else {
          toggleControllersVisibility();
        }
      } else {
        if (!isCollapsed) {
          toggleControllersVisibility();
        }
      }
    });
  }

  @SuppressLint({"ClickableViewAccessibility", "HandlerLeak"})
  private void initPlayer() {
    if (player == null) {
      player = ExoPlayerFactory.newSimpleInstance(getContext());
      currentVolume = player.getVolume();
      player.addListener(new Player.EventListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
          state = playbackState;
          switch (playbackState) {
            case Player.STATE_IDLE: {
              binding.progressBarVod.setVisibility(View.GONE);
              break;
            }

            case Player.STATE_BUFFERING: {
              binding.progressBarVod.setVisibility(View.VISIBLE);
              binding.controllers.setVisibility(View.GONE);
              break;
            }

            case Player.STATE_READY: {
              if (currentDuration > 0) {
                player.seekTo(currentDuration);
                resumeVideo();
                currentDuration = 0;
              }
              binding.progressBarVod.setVisibility(View.GONE);
              if (binding.duration.getText().equals("--:--")) {
                lastDuration = player.getDuration();
                binding.duration.setText(
                    DCCastApplication.utils.milliSecondsToTimer(player.getDuration()));
              } else if (lastDuration != player.getDuration()) {
                lastDuration = player.getDuration();
                binding.duration.setText(
                    DCCastApplication.utils.milliSecondsToTimer(player.getDuration()));
              }

              if (isFirst) {
                if (!playWhenReady && getContext() != null) {
                  togglePlayAndPause();
                }

                isFirst = false;
              }
              break;
            }

            case Player.STATE_ENDED: {
              binding.progressBarVod.setVisibility(View.GONE);
              break;
            }
          }
        }
      });

      binding.videoMotionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
        @Override
        public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

        }

        @Override
        public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

        }

        @Override
        public void onTransitionCompleted(MotionLayout motionLayout, int i) {
          if (binding.stateDetector.getHeight() > 0) {
            childFragmentListener.fragmentExpanded();
            isCollapsed = false;
            binding.collapsedDivider.setVisibility(View.GONE);
            binding.collapsedControlLayout.setVisibility(View.GONE);
          } else {
            childFragmentListener.fragmentCollapsed();
            isCollapsed = true;
            binding.collapsedDivider.setVisibility(View.VISIBLE);
            binding.collapsedControlLayout.setVisibility(View.VISIBLE);

            if (binding.progressBarVod.isShown()) {
              binding.progressBarVod.setVisibility(View.GONE);
            }
          }
        }

        @Override
        public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

        }
      });

      binding.videoView.requestFocus();
      binding.videoView.setUseController(false);
      binding.videoView.setPlayer(player);

      binding.playerSeekBar.getProgressDrawable()
          .setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
      binding.playerSeekBar.getThumb().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
      binding.playerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
          if (progressHandler != null) {
            progressHandler.removeCallbacks(runnable);
            progressHandler = null;
          }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
          resumeVideo();
          int seekTo = (int) ((seekBar.getProgress() * player.getDuration()) / 100);
          if (seekTo < 0) {
            seekTo = 0;
          }
          player.seekTo(seekTo);
        }
      });

      binding.btnScreenSize.setOnClickListener(this);
      binding.btnScreenSizeFull.setOnClickListener(this);
    }

    if (adsPlayer == null) {
      adsPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
      adsPlayer.addListener(new Player.EventListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
          switch (playbackState) {
            case Player.STATE_ENDED: {
              binding.skipAds.performClick();
              break;
            }
          }
        }
      });

      binding.videoViewAds.requestFocus();
      binding.videoViewAds.setUseController(false);
      binding.videoViewAds.setPlayer(adsPlayer);
    }
  }

  private void showVideoAd(ModelAds ads, boolean isLive) {
    if (isLive) {
      scheduleLiveAdsTimer(ads, (int) (1), 1);
    } else {
      //10 * 60
      scheduleVodAdsTimer(ads, (int) (0), 1);
    }
  }

  private void scheduleLiveAdsTimer(ModelAds ads, int duration, int index) {
    if (liveAdsTimer != null) {
      liveAdsTimer.cancel();
    }

    liveAdsTimer = new Timer();
    liveAdsTimer.schedule(new TimerTask() {
      @Override public void run() {
        if (getActivity() == null || !binding.videoMotionLayout.isEnabled()) {
          return;
        }
        getActivity().runOnUiThread(new Runnable() {
          @Override public void run() {
            Timber.e("timerEnd");
            pauseVideo();
            binding.layoutVideoAds.setVisibility(View.VISIBLE);
            binding.videoViewAds.setVisibility(View.VISIBLE);
            binding.videoView.setVisibility(View.INVISIBLE);

            DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(Objects.requireNonNull(getContext()),
                    Util.getUserAgent(getContext(), "DCCast"));

            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory()
                .setConstantBitrateSeekingEnabled(true);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .setExtractorsFactory(extractorsFactory)
                .createMediaSource(Uri.parse(
                    ads.getFile()));

            adsPlayer.prepare(videoSource);
            adsPlayer.setPlayWhenReady(true);

            disableSkipButton();
          }
        });
      }
    }, index == 1 ? 1 : duration);

    binding.skipAds.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        adsPlayer.stop(false);
        binding.layoutVideoAds.setVisibility(View.GONE);
        binding.videoViewAds.setVisibility(View.GONE);
        binding.videoView.setVisibility(View.VISIBLE);

        if (index == 1) {
          playVideoUrl("");
        } else {
          resumeVideo();
        }

        scheduleLiveAdsTimer(ads, (int) (15 * 60 * 1000), 2);
      }
    });
  }

  private void scheduleVodAdsTimer(ModelAds ads, int duration, int index) {
    if (vodAdsTimer != null) {
      vodAdsTimer.cancel();
    }

    vodAdsTimer = new Timer();
    vodAdsTimer.schedule(new TimerTask() {
      @Override public void run() {
        if (getActivity() == null || !binding.videoMotionLayout.isEnabled()) {
          return;
        }
        getActivity().runOnUiThread(new Runnable() {
          @Override public void run() {
            pauseVideo();
            binding.layoutVideoAds.setVisibility(View.VISIBLE);
            binding.videoViewAds.setVisibility(View.VISIBLE);
            binding.videoView.setVisibility(View.INVISIBLE);

            binding.videoMotionLayout.disable();

            DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(Objects.requireNonNull(getContext()),
                    Util.getUserAgent(getContext(), "DCCast"));

            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory()
                .setConstantBitrateSeekingEnabled(true);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .setExtractorsFactory(extractorsFactory)
                .createMediaSource(Uri.parse(
                    ads.getFile()));

            adsPlayer.prepare(videoSource);
            adsPlayer.setPlayWhenReady(true);

            disableSkipButton();
          }
        });
      }
    }, index == 1 ? 1 : duration);

    //if (index == 1) {
    //  playVideoUrl("");
    //} else {
    //  resumeVideo();
    //}

    binding.skipAds.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        adsPlayer.stop(false);
        binding.layoutVideoAds.setVisibility(View.GONE);
        binding.videoViewAds.setVisibility(View.GONE);
        binding.videoView.setVisibility(View.VISIBLE);

        binding.videoMotionLayout.enable();

        if (index == 1) {
          playVideoUrl("");
        } else {
          resumeVideo();
        }

        scheduleVodAdsTimer(ads, (int) (5 * 60 * 1000), 2);
      }
    });
  }

  private void disableSkipButton() {
    binding.skipAds.setClickable(false);
    binding.skipAds.setEnabled(false);

    binding.skipAds.setBackgroundResource(R.drawable.bg_skip_disabled);

    if (skipButtonTimer != null) {
      skipButtonTimer.cancel();
    }

    timer = 0;
    final int defaultAdsTimer =
        adsPlayer.getDuration() > 0 ? (int) adsPlayer.getDuration() / 1000 : 15;
    skipButtonTimer = new Timer();
    skipButtonTimer.schedule(new TimerTask() {
      @Override public void run() {
        timer++;
        if (timer >= defaultAdsTimer) {
          skipButtonTimer.cancel();

          getActivity().runOnUiThread(new Runnable() {
            @Override public void run() {
              binding.skipTimer.setText("Skip");
              binding.skipAds.setBackgroundResource(R.drawable.bg_skip);
              binding.skipAds.setClickable(true);
              binding.skipAds.setEnabled(true);
            }
          });
        } else {
          getActivity().runOnUiThread(new Runnable() {
            @Override public void run() {
              binding.skipTimer.setText((defaultAdsTimer - timer) + " Skip");
            }
          });
        }
      }
    }, 1000, 1000);
  }

  private void playVideoUrl(String quality) {
    if (videoItem != null) {
      videoUrl = videoItem.getVideoUrl(quality);
    } else {
      videoUrl = headerVideoItem.getVideoUrl(quality);
    }

    Timber.e("PLAY VIDEO URL: " + videoUrl);

    DataSource.Factory dataSourceFactory =
        new DefaultDataSourceFactory(Objects.requireNonNull(getContext()),
            Util.getUserAgent(getContext(), "DCCast"));

    MediaSource videoSource =
        new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoUrl));

    DCCastApplication.videoUrl = videoUrl;
    DCCastApplication.videoId = videoItem != null ? videoItem.getId() : headerVideoItem.getId();

    player.stop(false);
    player.prepare(videoSource);
  }

  private void managerBinding() {
    if (playerManager.isServiceBound() && videoUrl != null && !videoUrl.isEmpty()) {
      try {
        playerManager.playOrPause(videoUrl);
        binding.videoView.setPlayer(getService().exoPlayer);
      } catch (Exception ex) {
        Timber.e("managerBinding deer error: " + ex.getMessage());
      }

      if (getActivity() != null) {
        if (getActivity() instanceof ActivityHome) {
          ActivityHome activityHome = (ActivityHome) getActivity();
          activityHome.finish();
          activityHome.moveToBackground();
        } else if (getActivity() instanceof ActivitySearch) {
          ActivitySearch activityHome = (ActivitySearch) getActivity();
          activityHome.finish();
          activityHome.moveToBackground();
        } else if (getActivity() instanceof ActivityMyChannel) {
          ActivityMyChannel activityHome = (ActivityMyChannel) getActivity();
          activityHome.finish();
          activityHome.moveToBackground();
        }
      }
    }
  }

  private void showPopupWindow() {
    if (!checkOverlayPermission()) return;

    String title = videoItem != null ? videoItem.getTitle() : headerVideoItem.getTitle();
    String subString = title;
    if (title.length() > 20) {
      subString = title.substring(0, 20);
      subString = subString + "...";
    }

    Toast.makeText(getActivity(), getString(R.string.popup_change), Toast.LENGTH_LONG).show();

    if (getActivity() == null) {
      return;
    }

    if (getActivity() instanceof ActivityHome) {
      ActivityHome activityHome = (ActivityHome) getActivity();
      Intent intent = new Intent(activityHome, PopupService.class);
      intent.setAction(PopupService.ACTION_START_FOREGROUND);
      ModelVideo popUpVideo = videoItem;
      if (headerVideoItem != null) {
        popUpVideo = new ModelVideo(headerVideoItem);
      }
      PopUpUtil.setPopUpVideo(popUpVideo);
      intent.putExtra("type", "popup");
      activityHome.startService(intent);

      activityHome.finish();
      activityHome.moveToBackground();
    } else if (getActivity() instanceof ActivityMyChannel) {
      ActivityMyChannel activityHome = (ActivityMyChannel) getActivity();
      Intent intent = new Intent(activityHome, PopupService.class);
      intent.setAction(PopupService.ACTION_START_FOREGROUND);
      ModelVideo popUpVideo = videoItem;
      if (headerVideoItem != null) {
        popUpVideo = new ModelVideo(headerVideoItem);
      }
      PopUpUtil.setPopUpVideo(popUpVideo);
      intent.putExtra("type", "popup");
      activityHome.startService(intent);

      activityHome.finish();
      activityHome.moveToBackground();
    } else if (getActivity() instanceof ActivitySearch) {
      ActivitySearch activityHome = (ActivitySearch) getActivity();
      Intent intent = new Intent(activityHome, PopupService.class);
      intent.setAction(PopupService.ACTION_START_FOREGROUND);
      ModelVideo popUpVideo = videoItem;
      if (headerVideoItem != null) {
        popUpVideo = new ModelVideo(headerVideoItem);
      }
      PopUpUtil.setPopUpVideo(popUpVideo);
      intent.putExtra("type", "popup");
      activityHome.startService(intent);

      activityHome.finish();
      activityHome.moveToBackground();
    }
  }

  private boolean checkOverlayPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getContext())) {
      Intent i = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
          Uri.parse("package:" + getActivity().getPackageName()));
      startActivityForResult(i, OVERLAY_PERMISSION_REQ);
      return false;
    }
    return true;
  }

  private void initLiveChat(boolean isChatLock) {
    colorFollow = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
    colorFollowing = ContextCompat.getColor(getActivity(), R.color.white);
    bgFollow = R.drawable.cast_list_followers_follow;
    bgFollowing = R.drawable.cast_list_followers_following;

    binding.liveFollowLayout.setVisibility(View.VISIBLE);
    binding.btnChat.setVisibility(View.GONE);
    binding.chatLayout.setVisibility(View.VISIBLE);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
    layoutManager.setOrientation(RecyclerView.VERTICAL);
    layoutManager.setReverseLayout(false);
    layoutManager2.setOrientation(RecyclerView.VERTICAL);
    layoutManager2.setReverseLayout(false);

    liveChatAdapter = new LiveChatAdapter(getActivity(), binding.relatedRecyclerview,
        binding.liveChatRecyclerview);

    binding.relatedRecyclerview.setLayoutManager(layoutManager);
    binding.relatedRecyclerview.setAdapter(liveChatAdapter);
    binding.liveChatRecyclerview.setLayoutManager(layoutManager2);
    binding.liveChatRecyclerview.setAdapter(liveChatAdapter);

    String liveUserName;
    String liveViewCount;
    String liveTitle;
    String userImage;
    String category = "";

    if (videoItem != null) {
      liveChatHelper = new LiveChatHelper(getActivity(), videoItem, liveChatAdapter);
      liveUserName = videoItem.getUser().getNickName();
      liveViewCount = videoItem.getLiveMember();
      liveTitle = videoItem.getTitle();
      if (videoItem.getMediaCategory() != null && videoItem.getMediaCategory().getName() != null) {
        category = videoItem.getMediaCategory().getName();
      }
      userImage = videoItem.getUser().getProfileImage();
    } else {
      liveChatHelper = new LiveChatHelper(getActivity(), headerVideoItem, liveChatAdapter);
      liveUserName = headerVideoItem.getUser().getNickName();
      liveViewCount = headerVideoItem.getLiveMember();
      liveTitle = headerVideoItem.getTitle();
      if (headerVideoItem.getMediaCategory() != null
          && headerVideoItem.getMediaCategory().getName() != null) {
        category = headerVideoItem.getMediaCategory().getName();
      }
      userImage = headerVideoItem.getUser().getProfileImage();
    }

    liveChatHelper.setOnReceivedMemberCount(this);
    liveChatHelper.setOnUserMessageBlocked(this);

    if (category != null && !category.isEmpty()) {
      liveTitle = "[" + category + "] " + liveTitle;
    }

    binding.tvLiveUserName.setText(liveUserName);
    binding.tvLiveViewCount.setText(liveViewCount);
    binding.tvLiveUserNameFull.setText(liveUserName);
    binding.tvLiveViewCountFull.setText(liveViewCount);
    binding.liveFullScreenTitle.setText(liveTitle);
    binding.liveScreenTitle.setText(liveTitle);
    binding.liveScreenTitle.setVisibility(View.VISIBLE);
    binding.btnCollapse.setVisibility(View.GONE);

    Glide.with(getActivity())
        .load(notq.dccast.util.Util.getValidateUrl(userImage))
        .into(binding.ivLiveUserFull);
    Glide.with(getActivity())
        .load(notq.dccast.util.Util.getValidateUrl(userImage))
        .into(binding.ivLiveUser);

    binding.chatMessageFieldFull.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (LoginService.getLoginUser() == null) {
          navigateToLogin();
        }
      }
    });

    if (isChatLock) {
      binding.chatLayout.setVisibility(View.GONE);
      binding.liveChatRecyclerview.setVisibility(View.GONE);
    } else {
      liveChatHelper.onSocket();

      View.OnClickListener chatMessageSendListener = v -> {
        if (LoginService.getLoginUser() == null) {
          navigateToLogin();
          return;
        }
        if (liveChatBlocked) {
          showMessageDisabled();
          return;
        }
        EditText editText = binding.chatMessageFieldFull;

        if (editText.getText().toString().isEmpty()) {
          Toast.makeText(getContext(), getString(R.string.validate_comment), Toast.LENGTH_SHORT)
              .show();
        } else {
          if (liveChatHelper.isJoined) {
            liveChatHelper.sendMessage(editText.getText().toString());
            editText.getText().clear();
            DCCastApplication.utils.hideKeyboard(getActivity());
          } else {
            Toast.makeText(getActivity(), getString(R.string.live_need_to_join), Toast.LENGTH_SHORT)
                .show();

            if (liveChatHelper != null) {
              liveChatHelper.onSocket();
            }
          }
        }
      };

      binding.chatLayout.setOnClickListener(this);
      binding.chatMessageStickerFull.setOnClickListener(this);
      binding.chatMessageSendFull.setOnClickListener(chatMessageSendListener);
    }

    checkLiveBlocked();
  }

  private void checkLiveBlocked() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    LiveAPIInterface apiInterface = APIClient.getClient().create(LiveAPIInterface.class);
    Call<ModelBlockResultWrapper> call =
        apiInterface.checkBlockRequest(videoId, loginUser.getId());

    call.enqueue(new Callback<ModelBlockResultWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelBlockResultWrapper> call,
          @NonNull Response<ModelBlockResultWrapper> response) {

        ModelBlockResultWrapper result = response.body();

        if (result.getBlockResults() != null) {
          for (ModelBlockResult blockResult : result.getBlockResults()) {
            if (blockResult.type.equalsIgnoreCase("FOREVER")) {
              onUserMessageBlocked(true, 0);
              break;
            } else {
              long blockedTimeStamp = 0;

              try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
                Date date = sdf.parse(blockResult.getStart_timestamp());
                long endTime = date.getTime() + 3 * 60 * 1000;

                long now = new Date().getTime();
                if (endTime > now) {
                  blockedTimeStamp = endTime - now;

                  onUserMessageBlocked(false, blockedTimeStamp);
                }
              } catch (ParseException e) {
                e.printStackTrace();
              }
              break;
            }
          }
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelBlockResultWrapper> call, @NonNull Throwable t) {
        call.cancel();
        Toast.makeText(getActivity(), getString(R.string.error_with, t.getMessage()),
            Toast.LENGTH_LONG)
            .show();
      }
    });
  }

  @Override public void onReceivedMemberCount(int memberCount) {
    getActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        String count = notq.dccast.util.Util.getFormattedNumber(memberCount);
        binding.tvLiveViewCount.setText(count);
        binding.tvLiveViewCountFull.setText(count);
      }
    });
  }

  @Override public void onUserMessageBlocked(boolean permanent, long duration) {
    getActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        if (permanent) {
          liveChatBlocked = true;
          if (messageBlockHandler != null && messageBlockRunnable != null) {
            messageBlockHandler.removeCallbacks(messageBlockRunnable);
          }
          Toast.makeText(getActivity(), getString(R.string.user_blocked_from_live),
              Toast.LENGTH_LONG).show();

          hideFragment();
        } else {
          liveChatBlocked = true;
          if (messageBlockHandler != null && messageBlockRunnable != null) {
            messageBlockHandler.removeCallbacks(messageBlockRunnable);
          }
          messageBlockHandler = new Handler();
          messageBlockRunnable = new Runnable() {
            @Override public void run() {
              liveChatBlocked = false;
            }
          };

          messageBlockHandler.postDelayed(messageBlockRunnable,
              duration == 0 ? 3 * 60 * 1000 : duration);
        }
      }
    });
  }

  private void hideFragment() {
    if (getActivity() != null && getActivity().getSupportFragmentManager() != null) {
      if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
        if (childFragmentListener != null) {
          childFragmentListener.fragmentClosed();
        }
        getActivity().getSupportFragmentManager().popBackStackImmediate();
      }
    }

    if (getParentFragment() != null && getParentFragment().getChildFragmentManager() != null) {
      if (getParentFragment().getChildFragmentManager().getBackStackEntryCount() > 0) {
        if (childFragmentListener != null) {
          childFragmentListener.fragmentClosed();
        }
        getParentFragment().getChildFragmentManager().popBackStackImmediate();
      }
    }
  }

  private void initVideoTitle() {
    String videoTitle;
    String videoKind;

    if (videoItem != null) {
      videoTitle = videoItem.getTitle();
      videoKind = videoItem.getKinds();
    } else {
      videoTitle = headerVideoItem.getTitle();
      videoKind = headerVideoItem.getKinds();
    }

    binding.collapsedTitle.setText(videoTitle);
    binding.collapsedChannel.setText(videoKind);
  }

  private void initRelatedVideoView() {
    binding.btnChat.setVisibility(View.VISIBLE);
    binding.chatLayout.setVisibility(View.GONE);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    layoutManager.setOrientation(RecyclerView.VERTICAL);
    layoutManager.setReverseLayout(false);

    if (videoItem != null) {
      relatedVideoAdapter = new AdapterRelatedVideos(this, this, this, videoItem);
    } else {
      relatedVideoAdapter = new AdapterRelatedVideos(this, this, this, headerVideoItem);
    }

    relatedVideoAdapter.setOnAddFriendListener(new AdapterRelatedVideos.OnAddFriendListener() {
      @Override public void addFriendClicked() {
        ModelUser loginUser = LoginService.getLoginUser();
        if (loginUser == null) {
          navigateToLogin();
          return;
        }
        ModelUser user = relatedVideoAdapter.getVideoUser();

        if (user == null) {
          return;
        }
        if (user.isFriend() || user.isFriendRequestSend()) {
          removeFriend(user.getId());
        } else {
          addFriend(user.getId());
        }
      }
    });

    binding.relatedRecyclerview.setLayoutManager(layoutManager);
    binding.relatedRecyclerview.setAdapter(relatedVideoAdapter);

    if (videoItem != null) {
      relatedVideoAdapter.setHeaderData(videoItem);
    } else {
      relatedVideoAdapter.setHeaderData(headerVideoItem);
    }
  }

  private void initBottomSheet() {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    Objects.requireNonNull(getActivity())
        .getWindowManager()
        .getDefaultDisplay()
        .getMetrics(displayMetrics);
    int displayHeight = displayMetrics.heightPixels;
    int displayWidth = displayMetrics.widthPixels;
    int bottomSheetHeight = displayHeight - DCCastApplication.utils.pxFromDp(203);

    View.OnClickListener emptyClickListener = new View.OnClickListener() {
      @Override public void onClick(View v) {

      }
    };
    binding.bottomSheet.setOnClickListener(emptyClickListener);
    binding.bottomSheetSticker.setOnClickListener(emptyClickListener);
    binding.bottomSheetStickerFull.setOnClickListener(emptyClickListener);
    binding.bottomSheetAddComment.setOnClickListener(emptyClickListener);

    sheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);
    sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override
      public void onStateChanged(@NonNull View bottomSheet, int newState) {
        if (sheetBehavior instanceof LockableBottomSheetBehavior) {
          ((LockableBottomSheetBehavior) sheetBehavior).setLocked(true);
        }
      }

      @Override
      public void onSlide(@NonNull View bottomSheet, float slideOffset) {

      }
    });
    if (sheetBehavior instanceof LockableBottomSheetBehavior) {
      ((LockableBottomSheetBehavior) sheetBehavior).setLocked(true);
    }

    sheetBehaviorAddComment = BottomSheetBehavior.from(binding.bottomSheetAddComment);
    sheetBehaviorAddComment.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override
      public void onStateChanged(@NonNull View bottomSheet, int newState) {
        if (sheetBehaviorAddComment instanceof LockableBottomSheetBehavior) {
          ((LockableBottomSheetBehavior) sheetBehaviorAddComment).setLocked(true);
        }

        if (sheetBehaviorAddComment.getState() == BottomSheetBehavior.STATE_EXPANDED) {
          binding.dim.setVisibility(View.VISIBLE);
        }

        if (sheetBehaviorAddComment.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
          binding.dim.setVisibility(View.GONE);
        }
      }

      @Override
      public void onSlide(@NonNull View bottomSheet, float slideOffset) {

      }
    });
    if (sheetBehaviorAddComment instanceof LockableBottomSheetBehavior) {
      ((LockableBottomSheetBehavior) sheetBehaviorAddComment).setLocked(true);
    }

    binding.dim.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        sheetBehaviorAddComment.setState(BottomSheetBehavior.STATE_COLLAPSED);
      }
    });

    sheetBehaviorSticker = BottomSheetBehavior.from(binding.bottomSheetSticker);
    sheetBehaviorSticker.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override
      public void onStateChanged(@NonNull View bottomSheet, int newState) {
        if (sheetBehaviorSticker instanceof LockableBottomSheetBehavior) {
          ((LockableBottomSheetBehavior) sheetBehaviorSticker).setLocked(true);
        }
      }

      @Override
      public void onSlide(@NonNull View bottomSheet, float slideOffset) {

      }
    });
    if (sheetBehaviorSticker instanceof LockableBottomSheetBehavior) {
      ((LockableBottomSheetBehavior) sheetBehaviorSticker).setLocked(true);
    }

    sheetBehaviorStickerFull = BottomSheetBehavior.from(binding.bottomSheetStickerFull);
    sheetBehaviorStickerFull.setBottomSheetCallback(
        new BottomSheetBehavior.BottomSheetCallback() {
          @Override
          public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (sheetBehaviorStickerFull instanceof LockableBottomSheetBehavior) {
              ((LockableBottomSheetBehavior) sheetBehaviorStickerFull).setLocked(true);
            }
          }

          @Override
          public void onSlide(@NonNull View bottomSheet, float slideOffset) {

          }
        });
    if (sheetBehaviorStickerFull instanceof LockableBottomSheetBehavior) {
      ((LockableBottomSheetBehavior) sheetBehaviorStickerFull).setLocked(true);
    }

    sheetBehaviorBuuzFull = BottomSheetBehavior.from(binding.bottomSheetBuuzFull);
    sheetBehaviorBuuzFull.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override
      public void onStateChanged(@NonNull View bottomSheet, int newState) {
        if (sheetBehaviorBuuzFull instanceof LockableBottomSheetBehavior) {
          ((LockableBottomSheetBehavior) sheetBehaviorBuuzFull).setLocked(true);
        }
      }

      @Override
      public void onSlide(@NonNull View bottomSheet, float slideOffset) {

      }
    });
    if (sheetBehaviorBuuzFull instanceof LockableBottomSheetBehavior) {
      ((LockableBottomSheetBehavior) sheetBehaviorBuuzFull).setLocked(true);
    }
    sheetBehaviorBuuz = BottomSheetBehavior.from(binding.bottomSheetBuuz);
    sheetBehaviorBuuz.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override
      public void onStateChanged(@NonNull View bottomSheet, int newState) {
        if (sheetBehaviorBuuz instanceof LockableBottomSheetBehavior) {
          ((LockableBottomSheetBehavior) sheetBehaviorBuuz).setLocked(true);
        }
      }

      @Override
      public void onSlide(@NonNull View bottomSheet, float slideOffset) {

      }
    });
    if (sheetBehaviorBuuz instanceof LockableBottomSheetBehavior) {
      ((LockableBottomSheetBehavior) sheetBehaviorBuuz).setLocked(true);
    }

    binding.bottomSheet.getLayoutParams().height = bottomSheetHeight;
    binding.bottomSheet.requestLayout();

    binding.bottomSheetBuuz.getLayoutParams().height = bottomSheetHeight;
    binding.bottomSheetBuuz.requestLayout();

    binding.containerRecyclerview.setMinimumHeight(bottomSheetHeight);
    binding.containerRecyclerview.requestLayout();

    int bottomButtonWidth = displayWidth / 5;

    LinearLayout.LayoutParams btnLayoutParams =
        new LinearLayout.LayoutParams(bottomButtonWidth, MATCH_PARENT);
    binding.btnChat.setLayoutParams(btnLayoutParams);
    binding.btnBuuz.setLayoutParams(btnLayoutParams);
    binding.btnFav.setLayoutParams(btnLayoutParams);
    binding.btnShare.setLayoutParams(btnLayoutParams);
  }

  public void getAds() {

    ModelAds adsVideo = null;
    ModelAds adsBanner = null;

    int categoryId = -1;
    if (videoItem != null) {
      if (videoItem.getMediaCategory() != null) {
        categoryId = videoItem.getMediaCategory().getId();
      }
    }

    if (headerVideoItem != null) {
      if (headerVideoItem.getMediaCategory() != null) {
        categoryId = headerVideoItem.getMediaCategory().getId();
      }
    }

    if (isLive) {
      adsVideo = notq.dccast.util.Util.getAds("Live_movie", categoryId);

      adsBanner = notq.dccast.util.Util.getAds("Live_banner", categoryId);
    } else {
      adsVideo = notq.dccast.util.Util.getAds("VOD_movie", categoryId);

      adsBanner = notq.dccast.util.Util.getAds("VOD_banner", categoryId);
    }

    if (adsVideo != null) {
      showVideoAd(adsVideo, isLive);
    } else {
      playVideoUrl("");
    }

    if (adsBanner != null) {
      showAds(adsBanner);
    } else {
      hideAds();
    }
  }

  private void hideAds() {
    //binding.containerRecyclerview.setPadding(0, 0, 0,
    //    (int) notq.dccast.util.Util.convertDpToPixel(62, getActivity()));
    binding.ads.layoutBanner.setVisibility(View.GONE);
  }

  private void showAds(ModelAds ads) {
    //binding.containerRecyclerview.setPadding(0, 0, 0,
    //    (int) notq.dccast.util.Util.convertDpToPixel(62, getActivity()));
    binding.ads.layoutBanner.setVisibility(View.VISIBLE);

    if (ads.getAdverType().equalsIgnoreCase("SCRIPT")) {
      binding.ads.adsWebView.setVisibility(View.VISIBLE);
      binding.ads.adsWebView.loadData(ads.getContent(), "text/html", "UTF-8");

      binding.ads.adsWebView.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
          if (ads.getAffil() != null && ads.getAffil().getSite() != null) {
            Uri url = Uri.parse(ads.getAffil().getSite());
            Intent intent = new Intent(Intent.ACTION_VIEW, url);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
              startActivity(intent);
            }
          }
          return false;
        }
      });
    } else if (ads.getAdverType().equalsIgnoreCase("IMAGE") || ads.getAdverType()
        .equalsIgnoreCase("FILE")) {
      binding.ads.adsImage.setVisibility(View.VISIBLE);
      if (ads.getFile() != null) {
        Glide.with(getActivity())
            .load(ads.getFile())
            .into(binding.ads.adsImage);
      }
    }

    binding.ads.layoutBanner.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (ads.getAffil() != null && ads.getAffil().getSite() != null) {
          Uri url = Uri.parse(ads.getAffil().getSite());
          Intent intent = new Intent(Intent.ACTION_VIEW, url);
          if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
          }
        }
      }
    });
  }

  private void initSettingsDialog() {
    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
    DialogVideoSettingsBinding dialogBinding =
        DataBindingUtil.inflate(layoutInflater, R.layout.dialog_video_settings, null, false);

    settingsDialog =
        new AlertDialog.Builder(getContext()).setView(dialogBinding.getRoot()).create();
    dialogBinding.report.setText(R.string.report_dialog_title);

    if (isLive) {
      dialogBinding.resolutionTitle.setText(getString(R.string.live_quality_title));
      dialogBinding.resolution0.setText(getString(R.string.live_quality_auto));
      dialogBinding.resolution1.setText(getString(R.string.live_quality_240));
      dialogBinding.resolution2.setText(getString(R.string.live_quality_480));
      dialogBinding.resolution3.setText(getString(R.string.live_quality_720));
      dialogBinding.resolution4.setText(getString(R.string.live_quality_1080));
    } else {
      dialogBinding.resolutionTitle.setText(getString(R.string.vod_quality_title));
      dialogBinding.resolution0.setText(getString(R.string.vod_quality_auto));
      dialogBinding.resolution1.setText(getString(R.string.vod_quality_240));
      dialogBinding.resolution2.setText(getString(R.string.vod_quality_480));
      dialogBinding.resolution3.setText(getString(R.string.vod_quality_720));
      dialogBinding.resolution4.setText(getString(R.string.vod_quality_1080));
    }

    dialogBinding.resolutionGroup.setOnCheckedChangeListener(
        new RadioGroup.OnCheckedChangeListener() {
          @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
            View radioButton = dialogBinding.resolutionGroup.findViewById(checkedId);
            int index = dialogBinding.resolutionGroup.indexOfChild(radioButton);

            String resolution = "";
            String toast = getString(R.string.resolution_toast_auto);
            switch (index) {
              case 0: {
                resolution = "";
                toast = getString(R.string.resolution_toast_auto);
                break;
              }
              case 1: {
                resolution = "240p";
                toast = getString(R.string.resolution_toast_240);
                break;
              }
              case 2: {
                resolution = "480p";
                toast = getString(R.string.resolution_toast_480);
                break;
              }
              case 3: {
                resolution = "720p";
                toast = getString(R.string.resolution_toast_720);
                break;
              }
              case 4: {
                resolution = "1080p";
                toast = getString(R.string.resolution_toast_1080);
                break;
              }
            }

            if (!currentVideoResolution.equalsIgnoreCase(resolution)) {
              currentDuration = player.getCurrentPosition();
              currentVideoResolution = resolution;
              playVideoUrl(currentVideoResolution);
            }

            Toast.makeText(getActivity(), getString(R.string.vod_resolution_changed, toast),
                Toast.LENGTH_LONG).show();

            settingsDialog.hide();
          }
        });

    dialogBinding.report.setOnClickListener(this);
    dialogBinding.viewAsPopUp.setOnClickListener(this);
    dialogBinding.viewAsRadio.setOnClickListener(this);

    String category = videoItem != null ? videoItem.getCategory() : headerVideoItem.getCategory();

    if (category != null && category.equalsIgnoreCase("LIVE")) {
      dialogBinding.changeNickname.setVisibility(View.VISIBLE);
      dialogBinding.changeNickname.setOnClickListener(this);
    }
  }

  private void initChangeNicknameDialog() {
    changeNicknameDialog = new ChangeNicknameDialog(getActivity(),
        this::updateProfile);
  }

  private void updateProfile(String nickname) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    showProgressDialog();

    int userId = loginUser.getId();

    HashMap<String, Object> updateValues = new HashMap<>();
    updateValues.put("user", userId);
    updateValues.put("nick_name", nickname);
    updateValues.put("auto_login", loginUser.getAutoLogin());
    updateValues.put("notice_live", loginUser.getNoticeLive());
    updateValues.put("notice_vod", loginUser.getNoticeVod());
    updateValues.put("notice_chat", loginUser.getNoticeChat());
    updateValues.put("notice_notice", loginUser.getNoticeNotice());

    ProfileAPIInterface profileAPIInterface =
        APIClient.getClient().create(ProfileAPIInterface.class);
    Call<ModelProfile> call = profileAPIInterface.updateProfile(userId, updateValues);

    Realm realm = Realm.getDefaultInstance();
    call.enqueue(new Callback<ModelProfile>() {
      @Override
      public void onResponse(@NonNull Call<ModelProfile> call,
          @NonNull Response<ModelProfile> response) {

        hideProgressDialog();

        ModelProfile updatedProfile = response.body();

        if (updatedProfile == null) {
          return;
        }

        if (!realm.isInTransaction()) {
          realm.beginTransaction();
        }
        loginUser.setProfile(updatedProfile);
        realm.copyToRealmOrUpdate(loginUser);

        realm.commitTransaction();

        Toast.makeText(getActivity(), getString(R.string.change_nickname_success, nickname),
            Toast.LENGTH_LONG).show();
      }

      @Override
      public void onFailure(@NonNull Call<ModelProfile> call, @NonNull Throwable t) {
        call.cancel();
        Timber.e("Save nickname: " + t.getMessage());
        hideProgressDialog();
      }
    });
  }

  private void showProgressDialog() {
    if (progressDialog == null) {
      progressDialog = new ProgressDialog(getActivity());
    }

    progressDialog.show();
  }

  private void hideProgressDialog() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  private void initReportDialog() {
    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
    reportDialogBinding =
        DataBindingUtil.inflate(layoutInflater, R.layout.dialog_video_report, null, false);

    reportVodLive = new AlertDialog.Builder(getContext())
        .setView(reportDialogBinding.getRoot()).create();

    reportDialogBinding.reportTitle.setText(
        getString(isLive ? R.string.live_report_title : R.string.vod_report_title));

    reportDialogBinding.report1.setSelected(true);

    reportDialogBinding.cancelReport.setOnClickListener(this);
    reportDialogBinding.sendReport.setOnClickListener(this);
  }

  private void dismissReport() {
    if (reportVodLive != null && reportVodLive.isShowing()) {
      reportVodLive.dismiss();
    }
  }

  private void showLoader() {
    if (!binding.dcLoader.isAnimating() && !binding.dcLoader.isShown()) {
      binding.dcLoader.playAnimation();
      binding.dcLoader.setVisibility(View.VISIBLE);
    }
  }

  private void hideLoader() {
    if (binding.dcLoader.isAnimating() && binding.dcLoader.isShown()) {
      binding.dcLoader.cancelAnimation();
      binding.dcLoader.setVisibility(View.GONE);
    }
  }

  private void updateProgress() {
    if (progressHandler == null) {
      progressHandler = new Handler();
    }

    Objects.requireNonNull(progressHandler).postDelayed(runnable, 100);
  }

  private void playVideo(ModelVideo modelVideo) {
    if (player != null) {
      player.stop(false);
    }
    if (modelVideo != null) {
      this.videoItem = modelVideo;
      this.videoId = modelVideo.getId();
      relatedVideoAdapter.setHeaderData(modelVideo);

      checkRecent();

      isPortrait = this.videoItem.isPortraitVideo();

      if (isPortrait) {
        binding.videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
        binding.videoView.requestFocus();
        Timber.e("FIXED_HEIGHT");
      } else {
        binding.videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
        binding.videoView.requestFocus();
        Timber.e("FIXED_WIDTH");
      }

      DataSource.Factory dataSourceFactory =
          new DefaultDataSourceFactory(Objects.requireNonNull(getContext()),
              Util.getUserAgent(getContext(), "DCCast"));

      MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
          Uri.parse(modelVideo.getVideoUrl(currentVideoResolution)));

      player.prepare(videoSource);
      player.setPlayWhenReady(true);
    }
  }

  private void togglePlayAndPause() {
    if (state == Player.STATE_READY) {
      if (player.getPlayWhenReady()) {
        pauseVideo();
      } else {
        resumeVideo();
      }

      player.getPlaybackState();
      binding.btnPlay.setColorFilter(
          ContextCompat.getColor(Objects.requireNonNull(getContext()), android.R.color.black),
          android.graphics.PorterDuff.Mode.MULTIPLY);
    }
  }

  @SuppressLint("SetTextI18n") private void toggleSubscribe() {
    if (binding.subscribe.getText()
        .toString()
        .equals(getString(R.string.video_user_subscribed))) {
      setSubscribe();
      deleteSubscribeRequest();
    } else {
      setSubscribed();
      createSubscribeRequest();
    }
  }

  private void toggleControllersVisibility() {
    if (binding.controllers.isShown()) {
      binding.controllers.setVisibility(View.GONE);
    } else {
      binding.controllers.setVisibility(View.VISIBLE);
    }

    //binding.liveFullScreenControllers.setVisibility(View.GONE);
  }

  private void toggleLiveFullScreenControllers() {
    if (binding.liveFullScreenControllers.isShown()) {
      binding.liveFullScreenControllers.setVisibility(View.GONE);

      binding.btnVolumeToggleFull.setVisibility(View.VISIBLE);
      binding.btnScreenSizeFull.setVisibility(View.VISIBLE);
    } else {
      binding.liveFullScreenControllers.setVisibility(View.VISIBLE);

      binding.btnVolumeToggleFull.setVisibility(View.VISIBLE);
      binding.btnScreenSizeFull.setVisibility(View.VISIBLE);

      if (isFavSelected) {
        binding.btnFavImageSelectedFull.setVisibility(View.VISIBLE);
        binding.btnFavImageFull.setVisibility(View.GONE);
      } else {
        binding.btnFavImageSelectedFull.setVisibility(View.GONE);
        binding.btnFavImageFull.setVisibility(View.VISIBLE);
      }
    }

    if (isPortrait && isFullscreen) {
      if (hasNavigation) {
        setFullScreenControllersHeight();
      } else {
        removeFullScreenControllersHeight();
      }
    }

    //binding.controllers.setVisibility(View.GONE);
  }

  private void resumeVideo() {
    Timber.e("STOP- onResumeVideo");
    if (player != null) {
      binding.btnPlay.setImageResource(R.drawable.ic_pause);
      binding.btnPlayBig.setImageResource(R.drawable.ic_pause);
      player.setPlayWhenReady(true);
      updateProgress();

      isPaused = false;
    }
  }

  private void pauseVideo() {
    if (player != null) {
      binding.btnPlay.setImageResource(R.drawable.ic_play);
      binding.btnPlayBig.setImageResource(R.drawable.ic_play);
      player.setPlayWhenReady(false);

      isPaused = true;
    }
  }

  private void openBottomSheetBehaviour() {
    binding.bottomSheet.post(() -> sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED));
  }

  private void openBottomSheetAddCommentBehaviour() {
    if (isLive && liveChatBlocked) {
      showMessageDisabled();
      return;
    }
    binding.dim.setVisibility(View.VISIBLE);
    binding.bottomSheetAddComment.post(
        () -> {
          sheetBehaviorAddComment.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
  }

  private void openBottomSheetStickerBehaviour(boolean isFullscreen) {
    if (isFullscreen) {
      binding.bottomSheetStickerFull.post(new Runnable() {
        @Override public void run() {
          sheetBehaviorStickerFull.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
      });
    } else {
      binding.bottomSheetSticker.post(new Runnable() {
        @Override public void run() {
          sheetBehaviorSticker.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
      });
    }
  }

  private void openBottomSheetBuuzFullBehaviour(boolean isFullscreen) {
    if (isFullscreen) {
      binding.bottomSheetBuuzFull.post(new Runnable() {
        @Override public void run() {
          sheetBehaviorBuuzFull.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
      });
    } else {
      binding.bottomSheetBuuz.post(new Runnable() {
        @Override public void run() {
          sheetBehaviorBuuz.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
      });
    }
  }

  private void openStickerBottomSheet(boolean isEdit,
      BottomSheetComment.OnStickerSelectedListenerForComment stickerSelectedListenerForComment,
      BottomSheetSticker.OnDismissListener onDismissListener) {
    if (liveChatBlocked) {
      showMessageDisabled();
      return;
    }
    BottomSheetSticker bottomSheetSticker =
        BottomSheetSticker.getInstance(isEdit, isFullscreen, isPortrait);
    bottomSheetSticker.setStickerListener(stickerSelectedListenerForComment);
    bottomSheetSticker.setOnDismissListener(onDismissListener);
    FragmentTransaction ft =
        Objects.requireNonNull(getFragmentManager()).beginTransaction();
    ft.add(R.id.bottom_sheet_sticker, bottomSheetSticker);
    ft.commit();
    openBottomSheetStickerBehaviour(false);
  }

  private void openAddCommentBottomSheet(boolean isComment, boolean isEdit,
      ModelComment editComment,
      BottomSheetComment.OnStickerSelectedListenerForComment stickerSelectedListenerForComment,
      BottomSheetComment.OnCommentAddListenerForComment onCommentAddListenerForComment) {
    if (liveChatBlocked) {
      showMessageDisabled();
      return;
    }
    FragmentTransaction fragmentTransaction =
        Objects.requireNonNull(getFragmentManager()).beginTransaction();
    BottomSheetAddComment bottomSheetAddComment =
        BottomSheetAddComment.getInstance(isComment, isEdit, editComment);
    bottomSheetAddComment.setOnClickListenerForParent(this);
    bottomSheetAddComment.setOnCommentAddListenerForComment(onCommentAddListenerForComment);
    bottomSheetAddComment.setOnStickerSelect(new BottomSheetAddComment.OnStickerSelect() {
      @Override public void onStickerSelect() {
        openStickerBottomSheet(isEdit, stickerSelectedListenerForComment,
            new BottomSheetSticker.OnDismissListener() {
              @Override public void onDismiss() {
                closeBottomSheetSticker();
                closeBottomSheetAddComment();
              }
            });
      }
    });
    fragmentTransaction.replace(R.id.bottom_sheet_add_comment, bottomSheetAddComment);
    fragmentTransaction.commit();
    openBottomSheetAddCommentBehaviour();
  }

  private void openAddCommentToLive() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      navigateToLogin();
      return;
    }
    FragmentTransaction fragmentTransaction =
        Objects.requireNonNull(getFragmentManager()).beginTransaction();
    if (liveChatBlocked) {
      showMessageDisabled();
      return;
    }
    BottomSheetAddComment bottomSheetAddComment =
        BottomSheetAddComment.getInstance(true, false, null);
    bottomSheetAddComment.setOnClickListenerForParent(this);
    bottomSheetAddComment.setOnCommentAddListenerForComment(
        new BottomSheetComment.OnCommentAddListenerForComment() {
          @Override public void onCommentSelected(boolean isEdit, String comment) {
            if (comment == null || comment.isEmpty()) {
              Toast.makeText(getContext(), getString(R.string.validate_comment),
                  Toast.LENGTH_SHORT)
                  .show();
            } else {
              if (liveChatHelper.isJoined) {
                liveChatHelper.sendMessage(comment);
                DCCastApplication.utils.hideKeyboard(getActivity());
              } else {
                Toast.makeText(getActivity(), getString(R.string.live_need_to_join),
                    Toast.LENGTH_SHORT)
                    .show();

                if (liveChatHelper != null) {
                  liveChatHelper.onSocket();
                }
              }
            }
          }
        });
    bottomSheetAddComment.setOnStickerSelect(new BottomSheetAddComment.OnStickerSelect() {
      @Override public void onStickerSelect() {
        openBottomSheet(STICKER);
      }
    });
    fragmentTransaction.replace(R.id.bottom_sheet_add_comment, bottomSheetAddComment);
    fragmentTransaction.commit();
    openBottomSheetAddCommentBehaviour();
  }

  private void showMessageDisabled() {
    if (getActivity() == null) {
      return;
    }
    DialogHelper.showAlertDialog(getActivity(), "",
        getString(R.string.comment_disabled_body),
        null, null);
  }

  private void openBottomSheet(int fragmentId) {
    FragmentTransaction fragmentTransaction =
        Objects.requireNonNull(getFragmentManager()).beginTransaction();

    if (LoginService.getLoginUser() != null) {
      switch (fragmentId) {
        case STICKER: {
          BottomSheetSticker bottomSheetSticker =
              BottomSheetSticker.getInstance(false, isFullscreen, isPortrait);
          bottomSheetSticker.setLiveChatHelper(liveChatHelper);
          bottomSheetSticker.setOnClickListener(this);
          bottomSheetSticker.setOnLiveStickerSelected(
              new BottomSheetSticker.OnLiveStickerSelected() {
                @Override public void onLiveStickerSelected() {
                  closeBottomSheetSticker();
                  closeBottomSheet();
                }
              });

          FragmentTransaction ft =
              Objects.requireNonNull(getFragmentManager()).beginTransaction();

          ft.add(isFullscreen ? R.id.bottom_sheet_sticker_full : R.id.bottom_sheet_sticker,
              bottomSheetSticker);

          ft.commit();

          openBottomSheetStickerBehaviour(isFullscreen);
          break;
        }

        case COMMENT: {
          int videoId = videoItem != null ? videoItem.getId() : headerVideoItem.getId();
          BottomSheetComment bottomSheetComment = BottomSheetComment.getInstance(videoId);
          bottomSheetComment.setOnClickListenerForParent(this);
          bottomSheetComment.setOnLayoutAddComment(new BottomSheetComment.OnLayoutAddComment() {
            @Override public void onLayoutAddCommentClicked(boolean isComment) {
              openAddCommentBottomSheet(isComment, false, null,
                  bottomSheetComment.getStickerSelectedListenerForComment(),
                  bottomSheetComment.getOnCommentAddListenerForComment());
            }

            @Override
            public void onLayoutEditCommentClicked(boolean isComment, ModelComment comment) {
              openAddCommentBottomSheet(isComment, true, comment,
                  bottomSheetComment.getStickerSelectedListenerForComment(),
                  bottomSheetComment.getOnCommentAddListenerForComment());
            }
          });
          fragmentTransaction.replace(R.id.bottom_sheet, bottomSheetComment);
          fragmentTransaction.commit();
          openBottomSheetBehaviour();
          break;
        }

        case BUUZ: {
          int videoUserId = videoItem != null ? videoItem.getUser().getId()
              : (headerVideoItem != null ? headerVideoItem.getUser().getId() : -1);
          String videoUserNo = videoItem != null ? videoItem.getUser().getUserNo()
              : (headerVideoItem != null ? headerVideoItem.getUser().getUserNo() : "-1");
          BottomSheetBuuz bottomSheetBuuz = BottomSheetBuuz.getInstance(
              videoUserId, videoUserNo);
          bottomSheetBuuz.setOnClickListenerForParent(this);

          FragmentTransaction ft =
              Objects.requireNonNull(getFragmentManager()).beginTransaction();

          ft.replace(isFullscreen ? R.id.bottom_sheet_buuz_full : R.id.bottom_sheet_buuz,
              bottomSheetBuuz);

          ft.commit();

          openBottomSheetBuuzFullBehaviour(false);
          break;
        }

        case BUUZ_FULL: {
          int videoUserId = videoItem != null ? videoItem.getUser().getId()
              : (headerVideoItem != null ? headerVideoItem.getUser().getId() : -1);
          String videoUserNo = videoItem != null ? videoItem.getUser().getUserNo()
              : (headerVideoItem != null ? headerVideoItem.getUser().getUserNo() : "-1");
          BottomSheetBuuz bottomSheetBuuz = BottomSheetBuuz.getInstance(
              videoUserId, videoUserNo);
          bottomSheetBuuz.setOnClickListenerForParent(this);

          FragmentTransaction ft =
              Objects.requireNonNull(getFragmentManager()).beginTransaction();
          ft.replace(isFullscreen ? R.id.bottom_sheet_buuz_full : R.id.bottom_sheet_buuz,
              bottomSheetBuuz);
          ft.commit();

          openBottomSheetBuuzFullBehaviour(isFullscreen);
          break;
        }

        case SHARE: {
          shareService = new ShareService(getActivity(), videoId);
          shareService.showDialog(null);
          break;
        }
      }
    } else {
      navigateToLogin();
    }
  }

  private void closeBottomSheet() {
    InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity())
        .getSystemService(Activity.INPUT_METHOD_SERVICE);
    View view = getActivity().getCurrentFocus();

    if (view == null) {
      view = new View(getContext());
    }

    Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
  }

  private void closeBottomSheetAddComment() {
    InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity())
        .getSystemService(Activity.INPUT_METHOD_SERVICE);
    View view = getActivity().getCurrentFocus();

    if (view == null) {
      view = new View(getContext());
    }

    Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
    sheetBehaviorAddComment.setState(BottomSheetBehavior.STATE_COLLAPSED);
  }

  private void closeBottomSheetSticker() {
    sheetBehaviorSticker.setState(BottomSheetBehavior.STATE_COLLAPSED);
    sheetBehaviorStickerFull.setState(BottomSheetBehavior.STATE_COLLAPSED);
  }

  private void closeBottomSheetBuuz() {
    sheetBehaviorBuuzFull.setState(BottomSheetBehavior.STATE_COLLAPSED);
    sheetBehaviorBuuz.setState(BottomSheetBehavior.STATE_COLLAPSED);
  }

  private void changeScreenOrientation() {
    int orientation =
        Objects.requireNonNull(getActivity()).getResources().getConfiguration().orientation;
    ViewGroup.LayoutParams params = binding.videoViewContainer.getLayoutParams();
    params.width = MATCH_PARENT;

    ViewGroup.LayoutParams adsParams = binding.layoutVideoAds.getLayoutParams();
    adsParams.width = MATCH_PARENT;

    if (isPortrait) {
      binding.videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
      binding.videoView.requestFocus();
      Timber.e("FIXED_HEIGHT");
      isFullscreen = !isFullscreen;

      Objects.requireNonNull(getActivity())
          .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

      if (isFullscreen) {
        params.height = MATCH_PARENT;
        adsParams.height = MATCH_PARENT;
      } else {
        params.height = DCCastApplication.utils.pxFromDp(203);
        adsParams.height = DCCastApplication.utils.pxFromDp(203);
      }

      binding.videoViewContainer.requestLayout();
      binding.layoutVideoAds.requestLayout();

      if (!isLive) {
        binding.btnCollapse.setVisibility(View.VISIBLE);
      }
      if (isFullscreen) {
        binding.btnCollapse.setVisibility(View.GONE);
        binding.videoMotionLayout.disable();
        binding.relatedLayout.setVisibility(View.GONE);

        binding.liveFullScreenTitle.setVisibility(View.INVISIBLE);
      } else {
        binding.videoMotionLayout.enable();
        binding.relatedLayout.setVisibility(View.VISIBLE);

        binding.liveFullScreenTitle.setVisibility(View.VISIBLE);
      }
    } else {
      binding.videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
      binding.videoView.requestFocus();
      Timber.e("FIXED_WIDTH");

      if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        if (hasNavigation) {
          removeNavigationBarHeight();
        }
        Objects.requireNonNull(getActivity())
            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        params.height = MATCH_PARENT;
        adsParams.height = MATCH_PARENT;

        binding.videoViewContainer.requestLayout();
        binding.layoutVideoAds.requestLayout();

        binding.btnCollapse.setVisibility(View.GONE);
        binding.videoMotionLayout.disable();
        binding.relatedLayout.setVisibility(View.GONE);

        if (isLive) {
          binding.liveFullScreenControllers.setVisibility(View.VISIBLE);
          binding.liveChatRecyclerview.setVisibility(View.VISIBLE);
          binding.controllers.setVisibility(View.GONE);
        }

        isFullscreen = true;
      } else {
        if (hasNavigation) {
          setNavigationBarHeight();
        }

        Objects.requireNonNull(getActivity())
            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        params.height = DCCastApplication.utils.pxFromDp(203);
        adsParams.height = DCCastApplication.utils.pxFromDp(203);

        binding.videoViewContainer.requestLayout();
        binding.layoutVideoAds.requestLayout();

        if (!isLive) {
          binding.btnCollapse.setVisibility(View.VISIBLE);
        }
        binding.videoMotionLayout.enable();
        binding.relatedLayout.setVisibility(View.VISIBLE);

        if (isLive) {
          binding.liveFullScreenControllers.setVisibility(View.GONE);
          binding.liveChatRecyclerview.setVisibility(View.GONE);
          binding.controllers.setVisibility(View.VISIBLE);
        }

        isFullscreen = false;
      }
    }

    if (isFullscreen) {
      if (isFavSelected) {
        binding.btnFavImageSelectedFull.setVisibility(View.VISIBLE);
        binding.btnFavImageFull.setVisibility(View.GONE);
      } else {
        binding.btnFavImageSelectedFull.setVisibility(View.GONE);
        binding.btnFavImageFull.setVisibility(View.VISIBLE);
      }
      if (isLive) {
        binding.btnVolumeToggle.setVisibility(View.GONE);
        binding.btnScreenSize.setVisibility(View.GONE);

        liveChatAdapter.setFullScreen(isFullscreen);

        toggleLiveFullScreenControllers();
      } else {
        binding.btnVolumeToggle.setVisibility(View.VISIBLE);
        binding.btnScreenSize.setVisibility(View.VISIBLE);
      }
    } else {
      binding.btnScreenSize.setVisibility(View.VISIBLE);

      if (isLive) {
        binding.btnVolumeToggle.setVisibility(View.VISIBLE);
        binding.liveFullScreenControllers.setVisibility(View.GONE);
      }
      if (isFavSelected) {
        binding.btnFavImageSelected.setVisibility(View.VISIBLE);
        binding.btnFavImage.setVisibility(View.GONE);
      } else {
        binding.btnFavImageSelected.setVisibility(View.GONE);
        binding.btnFavImage.setVisibility(View.VISIBLE);
      }
    }
  }

  private void closeItself() {
    int orientation =
        Objects.requireNonNull(getActivity()).getResources().getConfiguration().orientation;
    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
      changeScreenOrientation();
    }

    childFragmentListener.fragmentClosed();
    pauseVideo();

    if (progressHandler != null && runnable != null) {
      progressHandler.removeCallbacks(runnable);
    }

    if (player != null) {
      player.release();
      player = null;
    }

    if (adsPlayer != null) {
      adsPlayer.release();
      adsPlayer = null;
    }

    Objects.requireNonNull(getActivity())
        .getSupportFragmentManager()
        .beginTransaction()
        .remove(FragmentVideo.this)
        .commit();
  }

  private void checkVodLiveIsReported() {
    if (LoginService.getLoginUser() != null) {
      int userId = LoginService.getLoginUser().id;
      int mediaId = videoItem != null ? videoItem.id : headerVideoItem.id;

      Call<JsonObject> call = APIClient.getClient().create(VideoDetailAPIInterface.class)
          .checkIsLiveVodReported(mediaId, userId);

      call.enqueue(new Callback<JsonObject>() {
        @Override public void onResponse(@NonNull Call<JsonObject> call,
            @NonNull Response<JsonObject> response) {
          JsonObject jsonObject = response.body();
          if (jsonObject.has("count")) {
            int count = jsonObject.get("count").getAsInt();
            if (count == 0) {
              settingsDialog.dismiss();
              reportVodLive.show();
            } else {
              Toast.makeText(getContext(), getString(R.string.already_reported),
                  Toast.LENGTH_SHORT)
                  .show();
            }
          }
        }

        @Override public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
          Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }
      });
    } else {
      navigateToLogin();
    }
  }

  private void getLikeAndDislikeRequest(boolean isActionButtonClicked, boolean isLike,
      int mediaId) {
    if (LoginService.getLoginUser() != null) {
      Call<JsonObject> call2 =
          APIClient.getClient()
              .create(VideoDetailAPIInterface.class)
              .getLikeAndDislikeStatus(mediaId, LoginService.getLoginUser().getId());

      call2.enqueue(new Callback<JsonObject>() {
        @Override public void onResponse(@NonNull Call<JsonObject> call,
            @NonNull Response<JsonObject> response) {
          JsonObject result = response.body();
          if (result.has("results")) {
            JsonArray results = result.getAsJsonArray("results");

            if (results.size() > 0) {
              JsonObject likeJson = results.get(0).getAsJsonObject();
              int likeAndDislikeId = likeJson.get("id").getAsInt();

              if (isActionButtonClicked) {
                if (isLike) {
                  if (likeJson.get("is_like").getAsBoolean()) {
                    deleteLike(likeAndDislikeId);
                  } else {
                    createLikeOrDislike(mediaId, true);

                    if (likeJson.get("is_dislike").getAsBoolean()) {
                      deleteDislike(likeAndDislikeId);
                    }
                  }
                } else {
                  if (likeJson.get("is_dislike").getAsBoolean()) {
                    deleteDislike(likeAndDislikeId);
                  } else {
                    createLikeOrDislike(mediaId, false);

                    if (likeJson.get("is_like").getAsBoolean()) {
                      deleteLike(likeAndDislikeId);
                    }
                  }
                }
              } else {
                if (videoItem != null) {
                  relatedVideoAdapter.getVideoItem()
                      .setLiked(likeJson.get("is_like").getAsBoolean());
                  relatedVideoAdapter.getVideoItem()
                      .setDisliked(likeJson.get("is_dislike").getAsBoolean());
                } else if (headerVideoItem != null) {
                  relatedVideoAdapter.getHeaderVideoItem()
                      .setLiked(likeJson.get("is_like").getAsBoolean());
                  relatedVideoAdapter.getHeaderVideoItem()
                      .setDisliked(likeJson.get("is_dislike").getAsBoolean());
                }

                relatedVideoAdapter.notifyItemChanged(
                    AdapterRelatedVideos.VIEW_TYPE_VIDEO_DETAIL_HEADER);
              }
            } else {
              if (isActionButtonClicked) {
                createLikeOrDislike(mediaId, isLike);
              } else {
                if (videoItem != null) {
                  relatedVideoAdapter.getVideoItem().setLiked(false);
                  relatedVideoAdapter.getVideoItem().setDisliked(false);
                } else if (headerVideoItem != null) {
                  relatedVideoAdapter.getHeaderVideoItem().setLiked(false);
                  relatedVideoAdapter.getHeaderVideoItem().setDisliked(false);
                }

                relatedVideoAdapter.notifyItemChanged(
                    AdapterRelatedVideos.VIEW_TYPE_VIDEO_DETAIL_HEADER);
              }
            }
          }
        }

        @Override public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
          Log.e("onFailure", t.getMessage());
        }
      });
    }
  }

  private void createLikeOrDislike(int mediaId, boolean isLike) {
    JSONObject likeBodyJson = new JSONObject();
    try {
      likeBodyJson.put("user", LoginService.getLoginUser().getId());
      likeBodyJson.put("media", mediaId);
      if (isLike) {
        likeBodyJson.put("is_dislike", false);
        likeBodyJson.put("is_like", true);
      } else {
        likeBodyJson.put("is_dislike", true);
        likeBodyJson.put("is_like", false);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    RequestBody like =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            (likeBodyJson).toString());

    Call<JsonObject> call =
        APIClient.getClient().create(VideoDetailAPIInterface.class).createLike(like);

    call.enqueue(new Callback<JsonObject>() {
      @Override public void onResponse(@NonNull Call<JsonObject> call,
          @NonNull Response<JsonObject> response) {
        if (response.body() != null) {
          if (videoItem != null) {
            childFragmentListener.onLikeDislikeUpdated(relatedVideoAdapter.getVideoItem());
          } else if (headerVideoItem != null) {
            childFragmentListener.onLikeDislikeUpdated(relatedVideoAdapter.getHeaderVideoItem());
          }

          if (isLike) {
            Toast.makeText(getActivity(), getString(R.string.create_like), Toast.LENGTH_LONG)
                .show();
          } else {
            Toast.makeText(getActivity(), getString(R.string.create_dislike), Toast.LENGTH_LONG)
                .show();
          }
        }
      }

      @Override public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
        Log.e("create failure", t.getMessage());
      }
    });
  }

  private void deleteLike(int likeAndDislikeId) {
    Call<Void> call = APIClient.getClient().create(VideoDetailAPIInterface.class)
        .deleteLike(likeAndDislikeId);
    call.enqueue(new Callback<Void>() {
      @Override
      public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
        if (videoItem != null) {
          childFragmentListener.onLikeDislikeUpdated(relatedVideoAdapter.getVideoItem());
        } else if (headerVideoItem != null) {
          childFragmentListener.onLikeDislikeUpdated(relatedVideoAdapter.getHeaderVideoItem());
        }

        Toast.makeText(getActivity(), getString(R.string.delete_like), Toast.LENGTH_LONG).show();
      }

      @Override public void onFailure(@NonNull Call<Void>
          call, @NonNull Throwable t) {
      }
    });
  }

  private void deleteDislike(int likeAndDislikeId) {
    Call<Void> call = APIClient.getClient().create(VideoDetailAPIInterface.class)
        .deleteDislike(likeAndDislikeId);
    call.enqueue(new Callback<Void>() {
      @Override
      public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
        if (videoItem != null) {
          childFragmentListener.onLikeDislikeUpdated(relatedVideoAdapter.getVideoItem());
        } else if (headerVideoItem != null) {
          childFragmentListener.onLikeDislikeUpdated(relatedVideoAdapter.getHeaderVideoItem());
        }

        Toast.makeText(getActivity(), getString(R.string.delete_dislike), Toast.LENGTH_LONG)
            .show();
      }

      @Override public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

      }
    });
  }

  private void createSubscribeRequest() {
    JSONObject createSubscribe = new JSONObject();
    try {
      createSubscribe.put("from_user", String.valueOf(LoginService.getLoginUser().getId()));
      createSubscribe.put("to_user",
          String.valueOf(
              videoItem != null ? videoItem.getUser().getId()
                  : headerVideoItem.getUser().getId()));
    } catch (JSONException e) {
      e.printStackTrace();
    }

    RequestBody subscribeRequestBody =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            (createSubscribe).toString());

    Call<ResponseCreateSubscribe> call =
        APIClient.getClient().create(VideoDetailAPIInterface.class)
            .createSubscribe(subscribeRequestBody);

    call.enqueue(new Callback<ResponseCreateSubscribe>() {
      @Override public void onResponse(@NonNull Call<ResponseCreateSubscribe> call,
          @NonNull Response<ResponseCreateSubscribe> response) {
        ResponseCreateSubscribe createSubscribeResponse = response.body();
        if (createSubscribeResponse != null) {
          subscribeId = createSubscribeResponse.getId();

          Toast.makeText(getActivity(), getString(R.string.subscribed), Toast.LENGTH_LONG).show();
        } else {
          subscribeId = -1;
        }
      }

      @Override public void onFailure(@NonNull Call<ResponseCreateSubscribe> call, @NonNull
          Throwable t) {
        subscribeId = -1;
        call.cancel();
        Toast.makeText(getActivity(), getString(R.string.error_with, t.getMessage()),
            Toast.LENGTH_LONG).show();
      }
    });
  }

  private void deleteSubscribeRequest() {
    if (subscribeId != -1) {
      Call<Void> call = APIClient.getClient().create(VideoDetailAPIInterface.class)
          .deleteSubscribe(subscribeId);
      call.enqueue(new Callback<Void>() {
        @Override
        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
          Toast.makeText(getActivity(), getString(R.string.subscribe_cancel), Toast.LENGTH_LONG)
              .show();
        }

        @Override public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
          call.cancel();
          Toast.makeText(getActivity(), getString(R.string.error_with, t.getMessage()),
              Toast.LENGTH_LONG);
        }
      });
    }
  }

  private void getIsSubscribedRequest() {
    if (LoginService.getLoginUser() != null) {
      subscribeRequest =
          APIClient.getClient()
              .create(VideoDetailAPIInterface.class)
              .getSubscribe(LoginService.getLoginUser().id,
                  (videoItem != null ? videoItem.getUser().id
                      : headerVideoItem.getUser().getId()));

      subscribeRequest.enqueue(new Callback<ResponseSubscribeWrapper>() {
        @Override public void onResponse(@NonNull Call<ResponseSubscribeWrapper> call,
            @NonNull Response<ResponseSubscribeWrapper> response) {
          ResponseSubscribeWrapper subscribeWrapper = response.body();
          boolean isSubscribed = false;
          if (subscribeWrapper != null && subscribeWrapper.subscribeList != null) {
            for (int i = 0; i < subscribeWrapper.subscribeList.size(); i++) {
              ResponseSubscribe subscribe = subscribeWrapper.subscribeList.get(i);
              if (subscribe.getToUser().id == (videoItem != null ? videoItem.getUser().id
                  : headerVideoItem.getUser().getId())) {
                isSubscribed = true;
                subscribeId = subscribe.getId();
              }
            }
          }

          if (isSubscribed) {
            setSubscribed();
          } else {
            subscribeId = -1;
            setSubscribe();
          }
        }

        @Override public void onFailure(
            @NonNull Call<ResponseSubscribeWrapper> call, @NonNull Throwable t) {
          subscribeId = -1;
          setSubscribe();
        }
      });
    } else {
      binding.subscribe.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
      binding.subscribe.setTextColor(Color.WHITE);
    }
  }

  private void setSubscribe() {
    if (getActivity() == null) {
      return;
    }
    binding.subscribe.setText(getString(R.string.video_user_subscribe));
    binding.subscribeWrapper.setBackground(
        ContextCompat.getDrawable(getContext(), R.drawable.bottom_nav_item_subscribe));
    binding.subscribe.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    binding.subscribe.setTextColor(Color.WHITE);

    binding.subscribeFull.setText(getString(R.string.video_user_subscribe));
    binding.subscribeWrapperFull.setBackground(
        ContextCompat.getDrawable(getContext(), R.drawable.live_bottom_full_subscribe));
    binding.subscribeFull.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    binding.subscribeFull.setTextColor(Color.WHITE);
  }

  private void setSubscribed() {
    if (getActivity() == null) {
      return;
    }
    binding.subscribe.setText(getString(R.string.video_user_subscribed));
    binding.subscribeWrapper.setBackground(
        ContextCompat.getDrawable(getContext(), R.drawable.bottom_nav_item_subscribed));
    binding.subscribe.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_subscribed, 0,
        0, 0);
    binding.subscribe.setTextColor(Color.parseColor("#B8B8B8"));

    binding.subscribeFull.setText(getString(R.string.video_user_subscribed));
    binding.subscribeWrapperFull.setBackground(
        ContextCompat.getDrawable(getContext(), R.drawable.live_bottom_full_subscribed));
    binding.subscribeFull.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_subscribed, 0, 0,
        0);
    binding.subscribeFull.setTextColor(Color.WHITE);
  }

  private void getRelatedVideosRequest() {
    relatedVideoAdapter.clearRelatedVideoItems();
    showLoader();

    int userId;

    if (videoItem != null) {
      userId = videoItem.getUser().id;
    } else {
      userId = headerVideoItem.getUser().id;
    }

    int videoId = videoItem != null ? videoItem.id : headerVideoItem.id;

    relatedVideoRequest = APIClient.getClient()
        .create(VideoDetailAPIInterface.class)
        .getRelatedVideoList(userId, DCCastApplication.utils.VIDEO_CHOOSE_TYPE, 100);

    relatedVideoRequest.enqueue(new Callback<ModelVideoWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelVideoWrapper> call,
          @NonNull Response<ModelVideoWrapper> response) {
        ModelVideoWrapper videoWrapper = response.body();

        if (videoWrapper != null && videoWrapper.videoList != null) {
          for (int i = 0; i < videoWrapper.videoList.size(); i++) {
            ModelVideo video = videoWrapper.videoList.get(i);
            if (video.getId() != videoId) {
              relatedVideoAdapter.addVideo(videoWrapper.videoList.get(i));
            }
          }
        }

        hideLoader();
      }

      @Override
      public void onFailure(@NonNull Call<ModelVideoWrapper> call, @NonNull Throwable t) {
        call.cancel();
        hideLoader();
      }
    });
  }

  private void getIsFavoriteRequest() {
    if (LoginService.getLoginUser() != null) {
      favoriteRequest = APIClient.getClient()
          .create(VideoDetailAPIInterface.class)
          .getFavorite(LoginService.getLoginUser().id,
              videoItem != null ? videoItem.getId() : headerVideoItem.getId());
      favoriteRequest.enqueue(new Callback<ModelFavoriteWrapper>() {
        @Override public void onResponse(@NonNull Call<ModelFavoriteWrapper> call,
            @NonNull Response<ModelFavoriteWrapper> response) {
          ModelFavoriteWrapper wrapper = response.body();
          if (wrapper.favoriteList.size() > 0) {
            int k = 0;
            for (int i = 0; i < wrapper.favoriteList.size(); i++) {
              ModelFavorite favorite = wrapper.favoriteList.get(i);
              if (favorite.getMedia().id == (videoItem != null ? videoItem.id
                  : headerVideoItem.id)) {
                k++;
                FragmentVideo.this.favoriteItem = favorite;
              }
            }

            isFavSelected = k > 0;

            if (isFavSelected) {
              binding.btnFavImageSelected.setVisibility(View.VISIBLE);
              binding.btnFavImage.setVisibility(View.GONE);
            } else {
              binding.btnFavImageSelected.setVisibility(View.GONE);
              binding.btnFavImage.setVisibility(View.VISIBLE);
            }
          }
        }

        @Override
        public void onFailure(@NonNull Call<ModelFavoriteWrapper> call, @NonNull Throwable t) {
          Log.e("isFavorite", t.getMessage());
        }
      });
    }
  }

  private void createFavoriteRequest() {
    JSONObject createFavJSON = new JSONObject();
    try {
      createFavJSON.put("user", String.valueOf(LoginService.getLoginUser().getId()));
      createFavJSON.put("media",
          String.valueOf(videoItem != null ? videoItem.getId() : headerVideoItem.getId()));
    } catch (JSONException e) {
      e.printStackTrace();
    }

    RequestBody favRequestBody =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            (createFavJSON).toString());

    Call<ModelCreateFavorite> call =
        APIClient.getClient()
            .create(VideoDetailAPIInterface.class)
            .createFavorite(favRequestBody);

    call.enqueue(new Callback<ModelCreateFavorite>() {
      @Override
      public void onResponse(@NonNull Call<ModelCreateFavorite> call,
          @NonNull Response<ModelCreateFavorite> response) {
        if (response.body() != null) {
          modelCreateFavorite = response.body();
        }

        Toast.makeText(getActivity(), getString(R.string.create_favorite), Toast.LENGTH_LONG)
            .show();
      }

      @Override
      public void onFailure(@NonNull Call<ModelCreateFavorite> call, @NonNull Throwable t) {
        Log.e("create fav error", t.getMessage());
        call.cancel();
      }
    });
  }

  private void deleteFavoriteRequest() {
    if (isFavSelected) {
      int id = -1;

      if (modelCreateFavorite != null) {
        id = modelCreateFavorite.id;
      } else if (favoriteItem != null) {
        id = favoriteItem.getId();
      }

      if (id != -1) {
        Call<Void> call
            = APIClient.getClient()
            .create(VideoDetailAPIInterface.class)
            .removeFavorite(id);
        call.enqueue(new Callback<Void>() {
          @Override
          public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
            Toast.makeText(getActivity(), getString(R.string.delete_favorite), Toast.LENGTH_LONG)
                .show();
          }

          @Override public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

          }
        });
      }
    }
  }

  private void sendReportVideoRequest() {
    JSONObject reportJson = new JSONObject();
    try {
      reportJson.put("user", LoginService.getLoginUser().getId());
      reportJson.put("division", DCCastApplication.utils.VIDEO_CHOOSE_TYPE);
      reportJson.put("content", reportDialogBinding.reportField.getText().toString());
      String kind = "";
      if (reportDialogBinding.report1.isChecked()) {
        kind = reportDialogBinding.report1.getText().toString();
      }

      if (reportDialogBinding.report2.isChecked()) {
        kind = reportDialogBinding.report2.getText().toString();
      }

      if (reportDialogBinding.report3.isChecked()) {
        kind = reportDialogBinding.report3.getText().toString();
      }

      if (reportDialogBinding.report4.isChecked()) {
        kind = reportDialogBinding.report4.getText().toString();
      }

      reportJson.put("kind", kind);
      reportJson.put("media", videoItem != null ? videoItem.getId() : headerVideoItem.getId());
    } catch (JSONException e) {
      e.printStackTrace();
    }

    RequestBody report =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            (reportJson).toString());

    Call<JsonObject> call =
        APIClient.getClient().create(VideoDetailAPIInterface.class).reportVideo(report);

    call.enqueue(new Callback<JsonObject>() {
      @Override public void onResponse(@NonNull Call<JsonObject> call,
          @NonNull Response<JsonObject> response) {
        //SuccessDialog successDialog =
        //    new SuccessDialog(getActivity(), isLive ? getString(R.string.report_success)
        //        : getString(R.string.report_live_success));
        //successDialog.showDialog();
        Toast.makeText(getActivity(), isLive ? getString(R.string.report_success)
            : getString(R.string.report_live_success), Toast.LENGTH_LONG).show();

        dismissReport();
      }

      @Override public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        dismissReport();
      }
    });
  }

  private void checkIs18() {
    ModelUser loginUser = LoginService.getLoginUser();

    if (loginUser == null || !isLive) {
      return;
    }

    boolean isAdult = false;
    if (videoItem != null) {
      if (videoItem.getKinds() != null && videoItem.getKinds()
          .equalsIgnoreCase(getString(R.string.value_share_type_19))) {
        isAdult = true;
      }
    } else if (headerVideoItem != null) {
      if (headerVideoItem.getKinds() != null && headerVideoItem.getKinds()
          .equalsIgnoreCase(getString(R.string.value_share_type_19))) {
        isAdult = true;
      }
    }

    if (isAdult) {
      binding.live18.setVisibility(View.VISIBLE);
      binding.live18Full.setVisibility(View.VISIBLE);
    } else {
      binding.live18.setVisibility(View.GONE);
      binding.live18Full.setVisibility(View.GONE);
    }
  }

  private void checkCanAddFriend() {
    ModelUser loginUser = LoginService.getLoginUser();

    if (loginUser == null) {
      return;
    }

    int userId = (videoItem != null ? videoItem.getUser().id : headerVideoItem.getUser().getId());

    if (loginUser.getId() == userId) {
      return;
    }

    friendRequest =
        APIClient.getClient()
            .create(CastListAPIInterface.class)
            .checkIsFriend(loginUser.getId(), userId);

    friendRequest.enqueue(new Callback<ModelResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelResult> call,
          @NonNull Response<ModelResult> response) {
        ModelResult result = response.body();
        if (result == null) {
          return;
        }

        if (result.isResult()) {
          setFriends();
        } else {
          setAddFriend();
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelResult> call, @NonNull Throwable t) {
        call.cancel();
      }
    });
  }

  private void addFriend(int userId) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null || userId == loginUser.getId()) {
      return;
    }
    CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);
    Call<ModelFriendResult> call =
        apiInterface.sendFriendRequest(LoginService.getLoginUser().id, userId);
    call.enqueue(new Callback<ModelFriendResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelFriendResult> call,
          @NonNull Response<ModelFriendResult> response) {
        ModelFriendResult result = response.body();
        if (result == null) {
          return;
        }

        if (relatedVideoAdapter != null) {
          relatedVideoAdapter.setFriendRequestSent();
        }
        setRequestSent();

        Toast.makeText(getActivity(), getString(R.string.add_friend_request_sent),
            Toast.LENGTH_LONG).show();
      }

      @Override
      public void onFailure(@NonNull Call<ModelFriendResult> call, @NonNull Throwable t) {
        call.cancel();
      }
    });
  }

  private void removeFriend(int userId) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null || userId == -1) {
      return;
    }

    CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);
    Call<ModelFriendRequestResult> call = apiInterface.deleteFriend(loginUser.getId(), userId);
    call.enqueue(new Callback<ModelFriendRequestResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelFriendRequestResult> call,
          @NonNull Response<ModelFriendRequestResult> response) {

        ModelFriendRequestResult result = response.body();
        if (result == null || !result.result) {
          return;
        }

        if (relatedVideoAdapter != null) {
          relatedVideoAdapter.setHeaderDataUserIsFriends(false);
        }
        setAddFriend();
      }

      @Override
      public void onFailure(@NonNull Call<ModelFriendRequestResult> call, @NonNull Throwable t) {
        call.cancel();
      }
    });
  }

  private void setAddFriend() {
    binding.btnLiveFollow.setTextColor(colorFollow);
    binding.btnLiveFollow.setBackgroundResource(bgFollow);
    binding.btnLiveFollow.setText(R.string.add_friend_add_friend);

    if (isLive) {
      binding.btnLiveFollowFull.setTextColor(colorFollow);
      binding.btnLiveFollowFull.setBackgroundResource(bgFollow);
      binding.btnLiveFollowFull.setText(R.string.add_friend_add_friend);
    }
  }

  private void setFriends() {
    binding.btnLiveFollow.setTextColor(colorFollowing);
    binding.btnLiveFollow.setBackgroundResource(bgFollowing);
    binding.btnLiveFollow.setText(R.string.add_friend_friends);

    if (isLive) {
      binding.btnLiveFollowFull.setTextColor(colorFollowing);
      binding.btnLiveFollowFull.setBackgroundResource(bgFollowing);
      binding.btnLiveFollowFull.setText(R.string.add_friend_friends);
    }
  }

  private void setRequestSent() {
    binding.btnLiveFollow.setTextColor(colorFollowing);
    binding.btnLiveFollow.setBackgroundResource(bgFollowing);
    binding.btnLiveFollow.setText(R.string.add_friend_request_sent);

    if (isLive) {
      binding.btnLiveFollowFull.setTextColor(colorFollow);
      binding.btnLiveFollowFull.setBackgroundResource(bgFollow);
      binding.btnLiveFollowFull.setText(R.string.add_friend_request_sent);
    }
  }

  private void navigateToLogin() {
    Toast.makeText(getContext(), getString(R.string.login_required), Toast.LENGTH_SHORT).show();
    Intent loginIntent = new Intent(getContext(), ActivityLogin.class);
    startActivityForResult(loginIntent, LOGIN_REQUEST);
  }
}
