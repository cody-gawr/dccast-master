package notq.dccast.screens.navigation_menu.live;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.JsonObject;
import com.wowza.gocoder.sdk.api.devices.WOWZCamera;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.live.LiveAPIInterface;
import notq.dccast.databinding.ActivityLiveStreamBinding;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.ModelVideoWrapper;
import notq.dccast.model.live.ModelBlockResult;
import notq.dccast.model.live.ModelOptions;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.adapter.LiveChatAdapter;
import notq.dccast.screens.home.mandu.ManduService;
import notq.dccast.screens.home.video_detail.LiveChatHelper;
import notq.dccast.util.ChangeLiveTitleDialog;
import notq.dccast.util.Constants;
import notq.dccast.util.DialogHelper;
import notq.dccast.util.LockableBottomSheetBehavior;
import notq.dccast.util.LoginService;
import notq.dccast.util.ShareService;
import notq.dccast.util.Util;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class ActivityLiveStream extends ActivityCameraBase implements View.OnClickListener,
    LiveChatHelper.onReceivedMemberCount {
  protected ImageView mBtnSwitchCamera = null;
  protected TimerView mTimerView = null;
  protected boolean audioEnabled = true;
  protected boolean flashEnabled = false;
  private ActivityLiveStreamBinding binding;
  private int selectedOrientation = -1;
  private int videoId;
  private Context mContext = this;
  private LiveChatAdapter liveChatAdapter;
  private LiveChatHelper liveChatHelper;
  private ShareService shareService;
  private int LIVE_TYPE = ActivityLiveSettings.LIVE_TYPE_CAMERA;
  private String mediaThumbnail = "";
  private LiveAPIInterface apiInterface;
  private ProgressDialog progressDialog;

  private BottomSheetBehavior sheetBehavior;
  private BottomSheetBehavior sheetBehaviorBlock;

  private long lastDuration = 0L;
  private int lastMemberCount = 0;

  private int orientation = 0;

  private ModelVideo modelVideo;
  private ChangeLiveTitleDialog changeLiveTitleDialog;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_live_stream);
    videoId = getIntent().getIntExtra("videoId", 0);
    orientation = getIntent().getIntExtra("orientation", 0);
    LIVE_TYPE = getIntent().getIntExtra("liveType", ActivityLiveSettings.LIVE_TYPE_CAMERA);
    mediaThumbnail = getIntent().getStringExtra("mediaThumbnail");
    videoEnabled = (LIVE_TYPE == ActivityLiveSettings.LIVE_TYPE_CAMERA);

    progressDialog = new ProgressDialog(mContext);
    progressDialog.setCancelable(false);

    init();
    initBottomSheet();

    getMediaById(videoId);
    initLiveChat();
  }

  public void getMediaById(int openVideoId) {
    showLoading();
    Call<ModelVideoWrapper> call = apiInterface.getMediaById(openVideoId);
    call.enqueue(new Callback<ModelVideoWrapper>() {
      @Override public void onResponse(@NonNull Call<ModelVideoWrapper> call,
          @NonNull Response<ModelVideoWrapper> response) {
        ModelVideoWrapper headerWrapper = response.body();
        hideLoading();
        if (headerWrapper == null
            || headerWrapper.getVideoList() == null
            || headerWrapper.getVideoList().size() == 0) {
          Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
          return;
        }

        modelVideo = headerWrapper.getVideoList().get(0);

        if (modelVideo != null && modelVideo.getTitle() != null) {
          binding.liveTitle.setText(modelVideo.getTitle());
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelVideoWrapper> call, @NonNull Throwable t) {
        call.cancel();
        hideLoading();
        Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
      }
    });
  }

  private void initBottomSheet() {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    getWindowManager()
        .getDefaultDisplay()
        .getMetrics(displayMetrics);
    int displayHeight = displayMetrics.heightPixels;
    int bottomSheetHeight = displayHeight;

    sheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);
    sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override public void onStateChanged(@NonNull View bottomSheet, int newState) {
        bottomSheet.post(() -> {
          bottomSheet.requestLayout();
          bottomSheet.invalidate();
        });

        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
          binding.dim.setVisibility(View.GONE);
        }
        if (sheetBehavior instanceof LockableBottomSheetBehavior) {
          ((LockableBottomSheetBehavior) sheetBehavior).setLocked(true);
        }
      }

      @Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {
      }
    });

    sheetBehaviorBlock = BottomSheetBehavior.from(binding.bottomSheetBlock);
    sheetBehaviorBlock.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override public void onStateChanged(@NonNull View bottomSheet, int newState) {
        bottomSheet.post(() -> {
          bottomSheet.requestLayout();
          bottomSheet.invalidate();
        });

        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
          binding.dim.setVisibility(View.GONE);
        }
        if (sheetBehaviorBlock instanceof LockableBottomSheetBehavior) {
          ((LockableBottomSheetBehavior) sheetBehaviorBlock).setLocked(true);
        }
      }

      @Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {
      }
    });

    binding.dim.setOnClickListener(v -> closeBottomSheet());

    binding.bottomSheet.getLayoutParams().height = bottomSheetHeight;
    binding.bottomSheet.requestLayout();

    closeBottomSheet();
  }

  private void openBottomSheetBlockUser(int userId, String userNickName) {
    FragmentTransaction fragmentTransaction =
        Objects.requireNonNull(getSupportFragmentManager()).beginTransaction();

    BottomSheetOptions bottomSheetOptions;
    bottomSheetOptions = BottomSheetOptions.getInstance(ActivityLiveSettings.ITEM_BLOCK_USER);

    bottomSheetOptions.setOnItemSelectedListener(new BottomSheetOptions.OnItemSelectedListener() {
      @Override public void onItemSelected(ModelOptions item) {
        closeBottomSheet();
        if (item.getId() == 0) {
          liveChatHelper.sendMessageBlocked(userNickName);
          sendBlockRequest("3MINUTES", userId);
        } else {
          liveChatHelper.sendUserBlocked(userNickName);
          sendBlockRequest("FOREVER", userId);
        }
      }

      @Override public void close() {
        closeBottomSheet();
      }
    });

    fragmentTransaction.replace(R.id.bottom_sheet_block, bottomSheetOptions);
    fragmentTransaction.commit();

    new Handler().postDelayed(() -> runOnUiThread(() -> {
      sheetBehaviorBlock.setState(BottomSheetBehavior.STATE_EXPANDED);
      if (binding.dim.getVisibility() != View.VISIBLE) {
        binding.dim.setVisibility(View.VISIBLE);
      }
    }), 100);
  }

  private void openChangeTitleDialog() {
    changeLiveTitleDialog = new ChangeLiveTitleDialog(mContext,
        new ChangeLiveTitleDialog.OnItemSelectedListener() {
          @Override public void onTitleEntered(String title) {
            JSONObject params = new JSONObject();
            try {
              params.put("title", title);
            } catch (JSONException e) {
              e.printStackTrace();
            }

            updateMedia(params);

            binding.liveTitle.setText(title);

            changeLiveTitleDialog.hideDialog();
          }

          @Override public void close() {
            changeLiveTitleDialog.hideDialog();
          }
        });

    changeLiveTitleDialog.showDialog();
  }

  private void openBottomSheetSettings() {
    FragmentTransaction fragmentTransaction =
        Objects.requireNonNull(getSupportFragmentManager()).beginTransaction();

    BottomSheetLiveSettings bottomSheetLiveSettings;
    bottomSheetLiveSettings = BottomSheetLiveSettings.getInstance(modelVideo);
    bottomSheetLiveSettings.setOnItemSelectedListener(
        new BottomSheetLiveSettings.OnItemSelectedListener() {
          @Override public void onItemSelected(ModelOptions item) {

          }

          @Override public void close() {
            closeBottomSheet();
          }
        });
    bottomSheetLiveSettings.setUpdateLiveInterface(
        new BottomSheetLiveSettings.UpdateLiveInterface() {
          @Override public void updateLiveLock(String pin) {

            JSONObject params = new JSONObject();
            try {
              if (pin == null || pin.isEmpty()) {
                params.put("live_setpass", false);
                params.put("live_password", "");
              } else {
                params.put("live_setpass", true);
                params.put("live_password", pin);
              }
            } catch (JSONException e) {
              e.printStackTrace();
            }

            updateMedia(params);
          }

          @Override public void updateChatLock(boolean enabled) {
            JSONObject params = new JSONObject();
            try {
              params.put("live_chat_disable", enabled);
            } catch (JSONException e) {
              e.printStackTrace();
            }

            updateMedia(params);
          }

          @Override public void updateRestricted(boolean enabled) {
            JSONObject params = new JSONObject();
            try {
              params.put("kinds", enabled ? getString(R.string.value_share_type_19)
                  : getString(R.string.value_share_type_public));
            } catch (JSONException e) {
              e.printStackTrace();
            }

            updateMedia(params);
          }

          @Override public void updateCategoryId(int mediaCategoryId) {
            JSONObject params = new JSONObject();
            try {
              params.put("media_category", mediaCategoryId);
            } catch (JSONException e) {
              e.printStackTrace();
            }

            updateMedia(params);
          }

          @Override public void updateLiveMember(int count) {
            JSONObject params = new JSONObject();
            try {
              params.put("live_member", count);
            } catch (JSONException e) {
              e.printStackTrace();
            }

            updateMedia(params);
          }

          @Override public void updateResolution(String resolution) {
            JSONObject params = new JSONObject();
            try {
              params.put("live_resolution", resolution);
            } catch (JSONException e) {
              e.printStackTrace();
            }

            updateMedia(params);
          }

          @Override public void updateMeshType(String meshType) {
            JSONObject params = new JSONObject();
            try {
              params.put("live_deploy", meshType);
            } catch (JSONException e) {
              e.printStackTrace();
            }

            updateMedia(params);
          }
        });

    fragmentTransaction.replace(R.id.bottom_sheet, bottomSheetLiveSettings);
    fragmentTransaction.commit();

    new Handler().postDelayed(() -> runOnUiThread(() -> {
      sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
      if (binding.dim.getVisibility() != View.VISIBLE) {
        binding.dim.setVisibility(View.VISIBLE);
      }
    }), 100);
  }

  private void updateMedia(JSONObject params) {
    RequestBody updateLiveVideoRequest =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            String.valueOf(params));

    showLoading();

    Call<JsonObject> call =
        apiInterface.updateLive(videoId, updateLiveVideoRequest);

    call.enqueue(new Callback<JsonObject>() {
      @Override
      public void onResponse(@NonNull Call<JsonObject> call,
          @NonNull Response<JsonObject> response) {
        hideLoading();
        JsonObject result = response.body();
        if (result != null) {
          Timber.e("SUCCESS UPDATE");
        } else {
          Timber.e("FAILED UPDATED");
        }
      }

      @Override
      public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
        call.cancel();
        hideLoading();
        Toast.makeText(mContext, getString(R.string.error_with, t.getMessage()), Toast.LENGTH_LONG)
            .show();
      }
    });
  }

  private void showLoading() {
    if (progressDialog != null) {
      progressDialog.show();
    }
  }

  private void hideLoading() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  private void sendBlockRequest(String type, int userId) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    Call<ModelBlockResult> call =
        apiInterface.sendBlockRequest(type, "LIVE", videoId, userId, loginUser.getId());

    call.enqueue(new Callback<ModelBlockResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelBlockResult> call,
          @NonNull Response<ModelBlockResult> response) {

        ModelBlockResult result = response.body();
        if (result != null) {
          Timber.e("SUCCESS BLOCKED");
        } else {
          Timber.e("FAILED BLOCKED");
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelBlockResult> call, @NonNull Throwable t) {
        call.cancel();
        Toast.makeText(mContext, getString(R.string.error_with, t.getMessage()), Toast.LENGTH_LONG)
            .show();
      }
    });
  }

  private void closeBottomSheet() {
    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    sheetBehaviorBlock.setState(BottomSheetBehavior.STATE_COLLAPSED);
  }

  @Override protected void onResume() {
    super.onResume();
    if (this.hasDevicePermissionToAccess() && sGoCoderSDK != null && mWZCameraView != null) {
      WOWZCamera activeCamera = mWZCameraView.getCamera();
      if (activeCamera != null && activeCamera.hasCapability(WOWZCamera.FOCUS_MODE_CONTINUOUS)) {
        activeCamera.setFocusMode(WOWZCamera.FOCUS_MODE_CONTINUOUS);
      }
    }

    if (mBtnBroadcast == null) {
      initUIControls();
    }

    mBtnBroadcast.setOnClickListener(this);
  }

  private void init() {
    mRequiredPermissions = new String[] {
        Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO
    };

    mBtnSwitchCamera = findViewById(R.id.btn_switch);
    mTimerView = findViewById(R.id.live_timer);

    mBtnSwitchCamera.setOnClickListener(this);
    binding.containerLandscape.setOnClickListener(this);
    binding.containerPortrait.setOnClickListener(this);
    binding.btnClose.setOnClickListener(this);
    binding.ivShare.setOnClickListener(this);

    binding.layoutVolume.setOnClickListener(this);
    binding.layoutFlash.setOnClickListener(this);
    binding.layoutSettings.setOnClickListener(this);

    if (videoEnabled) {
      binding.layoutThumbnail.setVisibility(View.GONE);
      binding.layoutSwitch.setVisibility(View.VISIBLE);
      binding.cameraPreview.setVideoEnabled(true);
    } else {
      binding.layoutThumbnail.setVisibility(View.VISIBLE);
      binding.layoutSwitch.setVisibility(View.INVISIBLE);
      if (mediaThumbnail != null) {
        runOnUiThread(new Runnable() {
          @Override public void run() {
            Glide.with(mContext)
                .load(Util.getValidateUrl(mediaThumbnail))
                .into(binding.ivMediaThumbnail);
          }
        });
      }
      binding.cameraPreview.setVideoEnabled(false);
      binding.cameraPreview.stopPreview();
    }

    apiInterface = APIClient.getClient().create(LiveAPIInterface.class);

    if (orientation == 0) {
      binding.containerPortrait.performClick();
    } else {
      binding.containerLandscape.performClick();
    }

    if (selectedOrientation != -1) {
      if (selectedOrientation == SCREEN_ORIENTATION_LANDSCAPE) {
        setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
      } else {
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
      }
    }

    binding.orientationChooserLayout.setVisibility(View.GONE);

    binding.liveTitle.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        openChangeTitleDialog();
      }
    });
  }

  @Override protected void onDestroy() {
    if (liveChatHelper != null) {
      liveChatHelper.stopGetMembersTimer();
    }
    super.onDestroy();
  }

  private void initLiveChat() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(RecyclerView.VERTICAL);
    layoutManager.setReverseLayout(false);

    binding.liveChatRecyclerView.setLayoutManager(layoutManager);
    liveChatAdapter = new LiveChatAdapter(this, binding.liveChatRecyclerView);
    liveChatAdapter.setTextColor(Color.WHITE);
    liveChatAdapter.setOnBlockUserMessage(new LiveChatAdapter.OnBlockUserMessage() {
      @Override public void showBlockOptions(int userId, String userNickName) {
        openBottomSheetBlockUser(userId, userNickName);
      }
    });
    binding.liveChatRecyclerView.setAdapter(liveChatAdapter);

    liveChatHelper = new LiveChatHelper(this, videoId, liveChatAdapter);
    liveChatHelper.setOnReceivedMemberCount(this);

    ManduService service = new ManduService(mContext, new ManduService.ManduCallback() {
      @Override public void onError(String error) {
        binding.manduCount.setText("0");
        if (error != null) {
          Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
        }
      }

      @Override public void onComplete(double mandu) {
        binding.manduCount.setText(Util.getFormattedNumber(mandu));
      }
    });
    service.getUserMandu();
  }

  public void onSwitchCamera() {
    if (mWZCameraView == null) return;
    WOWZCamera newCamera = mWZCameraView.switchCamera();
    if (newCamera != null) {
      if (newCamera.hasCapability(WOWZCamera.FOCUS_MODE_CONTINUOUS)) {
        newCamera.setFocusMode(WOWZCamera.FOCUS_MODE_CONTINUOUS);
      }
    }
  }

  /**
   * Update the state of the UI controls
   */
  @Override protected boolean syncUIControlState() {
    boolean disableControls = super.syncUIControlState();

    if (disableControls) {
      mBtnSwitchCamera.setEnabled(false);
    } else {
      boolean isDisplayingVideo = (this.hasDevicePermissionToAccess(Manifest.permission.CAMERA)
          && getBroadcastConfig().isVideoEnabled()
          && mWZCameraView.getCameras().length > 0);
      boolean isStreaming = getBroadcast().getStatus().isRunning();

      if (isDisplayingVideo) {
        mBtnSwitchCamera.setEnabled(mWZCameraView.getCameras().length > 0);
      } else {
        mBtnSwitchCamera.setEnabled(false);
      }

      if (isStreaming && !mTimerView.isRunning()) {
        mTimerView.startTimer();
      } else if (getBroadcast().getStatus().isIdle() && mTimerView.isRunning()) {
        mTimerView.stopTimer();
      } else if (!isStreaming) {
        mTimerView.setVisibility(View.GONE);
      }
    }

    return disableControls;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode,
      @androidx.annotation.Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data == null) {
      Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
      return;
    }
    if (shareService != null) {
      shareService.callbackOnActivityResult(requestCode, resultCode, data);
    }
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_switch: {
        onSwitchCamera();
        break;
      }

      case R.id.layout_flash: {
        flashEnabled = !flashEnabled;
        binding.ivFlash.setImageResource(
            flashEnabled ? R.drawable.ic_flash : R.drawable.ic_flash_on);
        onToggleFlash(flashEnabled);
        break;
      }

      case R.id.layout_volume: {
        audioEnabled = !audioEnabled;
        binding.ivVolume.setImageResource(
            audioEnabled ? R.drawable.ic_volume : R.drawable.ic_muted);
        Toast.makeText(mContext,
            audioEnabled ? getString(R.string.audio_enabled) : getString(R.string.audio_disabled),
            Toast.LENGTH_LONG).show();
        onToggleAudio(audioEnabled);
        break;
      }

      case R.id.layout_settings: {
        openBottomSheetSettings();
        break;
      }

      case R.id.iv_share: {
        shareService = new ShareService(ActivityLiveStream.this, videoId);
        shareService.showDialog(null);
        break;
      }

      case R.id.btn_close: {
        DialogHelper.showAlertDialog(mContext, getString(R.string.stop_live_confirm_title),
            getString(
                R.string.stop_live_confirm_body),
            getString(R.string.stop_live_confirm_yes),
            (dialog, which) -> {
              stopBroadcast();
              setResult(RESULT_OK);
              finish();
            }, getString(R.string.stop_live_confirm_no), null);
        break;
      }

      case R.id.btn_start_live: {
        if (selectedOrientation == -1 && videoEnabled) {
          Toast.makeText(getApplicationContext(), getString(R.string.choose_orientation),
              Toast.LENGTH_SHORT)
              .show();
        } else {
          binding.counter.setVisibility(View.VISIBLE);
          binding.counterDim.setVisibility(View.VISIBLE);

          new CountDownTimer(4000, 1000) {
            @Override public void onTick(long millisUntilFinished) {
              binding.counter.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override public void onFinish() {
              binding.container.setVisibility(View.GONE);
              mBtnBroadcast.setVisibility(View.GONE);
              onToggleBroadcast(videoEnabled);
              liveChatHelper.onSocket();
            }
          }.start();
        }

        break;
      }

      case R.id.container_portrait: {
        selectedOrientation = SCREEN_ORIENTATION_PORTRAIT;
        binding.textPortrait.setTextColor(Color.WHITE);
        binding.textLandscape.setTextColor(Color.parseColor("#808B94"));
        binding.imgPortrait.setColorFilter(
            ContextCompat.getColor(getApplicationContext(), R.color.white),
            android.graphics.PorterDuff.Mode.MULTIPLY);
        binding.imgLandscape.setColorFilter(
            ContextCompat.getColor(getApplicationContext(), R.color.colorUnselectedOrientation),
            android.graphics.PorterDuff.Mode.MULTIPLY);
        break;
      }

      case R.id.container_landscape: {
        selectedOrientation = SCREEN_ORIENTATION_LANDSCAPE;
        binding.textLandscape.setTextColor(Color.WHITE);
        binding.textPortrait.setTextColor(Color.parseColor("#808B94"));
        binding.imgLandscape.setColorFilter(
            ContextCompat.getColor(getApplicationContext(), R.color.white),
            android.graphics.PorterDuff.Mode.MULTIPLY);
        binding.imgPortrait.setColorFilter(
            ContextCompat.getColor(getApplicationContext(), R.color.colorUnselectedOrientation),
            android.graphics.PorterDuff.Mode.MULTIPLY);
        break;
      }
    }
  }

  @Override public void onReceivedMemberCount(int memberCount) {
    runOnUiThread(new Runnable() {
      @Override public void run() {
        int count = memberCount > 0 ? memberCount - 1 : memberCount;
        binding.memberCount.setText(String.valueOf(count));
      }
    });

    lastMemberCount = memberCount;
    lastDuration = binding.liveTimer.getDuration();

    if (lastDuration <= 0) {
      return;
    }

    Call<JsonObject> call =
        apiInterface.updateLive("lastupdate_media", videoId, false, lastDuration, memberCount);

    call.enqueue(new Callback<JsonObject>() {
      @Override
      public void onResponse(@NonNull Call<JsonObject> call,
          @NonNull Response<JsonObject> response) {

        JsonObject result = response.body();
        boolean isBlock = false;
        if (result != null) {
          Timber.e("LIVE RESPO: " + result.toString());
          Timber.e("-------------------------------");
          if (result.has("user")) {
            JsonObject user = result.get("user").getAsJsonObject();
            if (user.has("is_block")) {
              isBlock = user.get("is_block").getAsBoolean();
            }
          }

          if (isBlock) {
            runOnUiThread(new Runnable() {
              @Override public void run() {
                stopBroadcast();

                setResult(Constants.REQUEST_BLOCKED_ADMIN);
                finish();
              }
            });
          }
        }
      }

      @Override
      public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
        call.cancel();
        Toast.makeText(mContext, getString(R.string.error_with, t.getMessage()), Toast.LENGTH_LONG)
            .show();
      }
    });
  }

  private void stopBroadcast() {
    endBroadcastForce();

    if (liveChatHelper != null) {
      liveChatHelper.stopGetMembersTimer();
    }

    binding.liveTimer.stopTimer();

    Call<JsonObject> call =
        apiInterface.updateLive("lastupdate_media", videoId, true);

    call.enqueue(new Callback<JsonObject>() {
      @Override
      public void onResponse(@NonNull Call<JsonObject> call,
          @NonNull Response<JsonObject> response) {

      }

      @Override
      public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
        call.cancel();
      }
    });
  }
}
