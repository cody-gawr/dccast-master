package notq.dccast.screens.navigation_menu.live;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.wowza.gocoder.sdk.api.WowzaGoCoder;
import com.wowza.gocoder.sdk.api.configuration.WOWZMediaConfig;
import com.wowza.gocoder.sdk.api.devices.WOWZAudioDevice;
import com.wowza.gocoder.sdk.api.devices.WOWZCamera;
import com.wowza.gocoder.sdk.api.devices.WOWZCameraView;
import com.wowza.gocoder.sdk.api.errors.WOWZError;
import com.wowza.gocoder.sdk.api.errors.WOWZStreamingError;
import com.wowza.gocoder.sdk.api.geometry.WOWZSize;
import com.wowza.gocoder.sdk.api.graphics.WOWZColor;
import com.wowza.gocoder.sdk.api.logging.WOWZLog;
import com.wowza.gocoder.sdk.api.status.WOWZStatus;
import java.util.Arrays;
import notq.dccast.R;
import timber.log.Timber;

@SuppressLint("Registered") public class ActivityCameraBase extends GoCoderSDKActivityBase
    implements WOWZCameraView.PreviewStatusListener {
  private final static String[] CAMERA_CONFIG_PREFS_SORTED =
      new String[] {"wz_video_enabled", "wz_video_frame_size", "wz_video_preset"};

  // UI controls
  protected Button mBtnBroadcast = null;

  // The GoCoder SDK camera preview display view
  protected WOWZCameraView mWZCameraView = null;
  protected WOWZAudioDevice mWZAudioDevice = null;
  protected boolean videoEnabled = true;
  private boolean mDevicesInitialized = false;
  private boolean mUIInitialized = false;
  private SharedPreferences.OnSharedPreferenceChangeListener mPrefsChangeListener = null;

  /**
   * Android Activity lifecycle methods
   */
  @Override protected void onResume() {
    super.onResume();

    if (!mUIInitialized) {
      initUIControls();
    }
    if (!mDevicesInitialized) {
      initGoCoderDevices();
    }

    this.hasDevicePermissionToAccess(result -> {
      if (!mDevicesInitialized || result) {
        initGoCoderDevices();
      }
    });

    if (sGoCoderSDK != null && this.hasDevicePermissionToAccess(Manifest.permission.CAMERA)) {
      final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
      mWZCameraView.setCameraConfig(getBroadcastConfig());
      mWZCameraView.setScaleMode(GoCoderSDKPrefs.getScaleMode(sharedPrefs));
      mWZCameraView.setVideoBackgroundColor(WOWZColor.DARKGREY);

      mWZCameraView.setVideoEnabled(videoEnabled);

      // Setup up a shared preferences change listener to update the camera preview
      // as the related preference values change
      mPrefsChangeListener = (sharedPreferences, prefsKey) -> {
        sharedPreferences.getBoolean("wz_video_enabled", videoEnabled);
        if (mWZCameraView != null
            && Arrays.binarySearch(CAMERA_CONFIG_PREFS_SORTED, prefsKey) != -1) {

          if (prefsKey.equalsIgnoreCase("wz_video_framerate")) {
            String currentFrameRate = String.valueOf(mWZCameraView.getFramerate());
            String frameRate = sharedPreferences.getString("wz_video_framerate", currentFrameRate);
            getBroadcastConfig().setVideoFramerate(Integer.parseInt(frameRate));
            mWZBroadcastConfig.setVideoFramerate(Integer.parseInt(frameRate));
            mWZBroadcastConfig.getVideoSourceConfig()
                .setVideoFramerate(Integer.parseInt(frameRate));
          }

          if (prefsKey.equalsIgnoreCase("wz_video_bitrate")) {
            String currentBitrate = String.valueOf(mWZCameraView.getFramerate());
            String bitrate = sharedPreferences.getString("wz_video_bitrate", currentBitrate);
            getBroadcastConfig().setVideoBitRate(Integer.parseInt(bitrate));
            mWZBroadcastConfig.setVideoFramerate(Integer.parseInt(bitrate));
            mWZBroadcastConfig.getVideoSourceConfig().setVideoFramerate(Integer.parseInt(bitrate));
          }

          // Update the camera preview display frame size
          if (prefsKey.equalsIgnoreCase("wz_video_frame_width") || prefsKey.equalsIgnoreCase(
              "wz_video_frame_height")) {
            WOWZSize currentFrameSize = mWZCameraView.getFrameSize();
            int prefsFrameWidth =
                sharedPreferences.getInt("wz_video_frame_width", currentFrameSize.getWidth());
            int prefsFrameHeight =
                sharedPreferences.getInt("wz_video_frame_height", currentFrameSize.getHeight());
            WOWZSize prefsFrameSize = new WOWZSize(prefsFrameWidth, prefsFrameHeight);
            if (!prefsFrameSize.equals(currentFrameSize)) {
              mWZCameraView.setFrameSize(prefsFrameSize);
            }
          }
          if (prefsKey.equalsIgnoreCase("wz_video_resize_to_aspect")) {
            boolean scaleMode = sharedPreferences.getBoolean("wz_video_resize_to_aspect", false);
            if (scaleMode) {
              mWZCameraView.setScaleMode(WOWZMediaConfig.RESIZE_TO_ASPECT);
            } else {
              mWZCameraView.setScaleMode(WOWZMediaConfig.FILL_VIEW);
            }
          }

          mWZCameraView.setCameraConfig(mWZBroadcastConfig);
          // Toggle the camera preview on or off
          boolean videoEnabled =
              sharedPreferences.getBoolean("wz_video_enabled", mWZBroadcastConfig.isVideoEnabled());
          if (videoEnabled && !mWZCameraView.isPreviewing()) {
            mWZCameraView.startPreview();
          } else if (!videoEnabled && mWZCameraView.isPreviewing()) {
            mWZCameraView.setVideoBackgroundColor(WOWZColor.BLACK);
          }
          mWZCameraView.clearView();
          mWZCameraView.stopPreview();
        }
      };

      sharedPrefs.registerOnSharedPreferenceChangeListener(mPrefsChangeListener);

      final ActivityCameraBase ref = this;
      final Handler handler = new Handler();
      handler.postDelayed(() -> {
        mWZBroadcastConfig.setVideoEnabled(videoEnabled);
        if (videoEnabled && !mWZCameraView.isPreviewing()) {
          mWZCameraView.startPreview(getBroadcastConfig(), ref);
        } else {
          mWZCameraView.stopPreview();
          Toast.makeText(ref, getString(R.string.video_turned_off), Toast.LENGTH_LONG).show();
        }
      }, 300);
    }
    syncUIControlState();
  }

  @Override public void onWZCameraPreviewStarted(WOWZCamera wzCamera, WOWZSize wzSize, int i) {
    // Briefly display the video configuration
    Toast.makeText(this, getBroadcastConfig().getLabel(true, true, false, true), Toast.LENGTH_LONG)
        .show();
  }

  @Override public void onWZCameraPreviewStopped(int cameraId) {
  }

  @Override public void onWZCameraPreviewError(WOWZCamera wzCamera, WOWZError wzError) {
    displayErrorDialog(wzError);
  }

  @Override protected void onPause() {
    super.onPause();

    if (mPrefsChangeListener != null) {
      SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
      sharedPrefs.unregisterOnSharedPreferenceChangeListener(mPrefsChangeListener);
    }

    if (mWZCameraView != null && mWZCameraView.isPreviewing()) {
      mWZCameraView.stopPreview();
    }
  }

  /**
   * WOWZStatusCallback interface methods
   */
  @Override public void onWZStatus(final WOWZStatus goCoderStatus) {

    new Handler(Looper.getMainLooper()).post(() -> {
      if (goCoderStatus.isRunning()) {
        // Keep the screen on while we are broadcasting
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Since we have successfully opened up the server connection, store the connection info for auto complete
        GoCoderSDKPrefs.storeHostConfig(
            PreferenceManager.getDefaultSharedPreferences(ActivityCameraBase.this),
            mWZBroadcastConfig);
      } else if (goCoderStatus.isIdle()) {
        // Clear the "keep screen on" flag
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
      }

      syncUIControlState();
    });
  }

  @Override public void onWZError(final WOWZStatus goCoderStatus) {
    new Handler(Looper.getMainLooper()).post(this::syncUIControlState);
  }

  /**
   * Click handler for the broadcast button
   */
  protected void onToggleBroadcast(boolean videoEnabled) {
    if (getBroadcast() == null) return;

    if (getBroadcast().getStatus().isIdle()) {
      mWZBroadcastConfig.setVideoEnabled(videoEnabled);
      if (!mWZBroadcastConfig.isVideoEnabled() && !mWZBroadcastConfig.isAudioEnabled()) {
        Toast.makeText(this, getString(R.string.video_audio_turned_off),
            Toast.LENGTH_LONG).show();
      } else {
        WOWZLog.debug("Scale Mode: -> " + mWZCameraView.getScaleMode());

        if (!mWZBroadcastConfig.isAudioEnabled()) {
          Toast.makeText(this, getString(R.string.audio_turned_off), Toast.LENGTH_LONG)
              .show();
        }

        if (!mWZBroadcastConfig.isVideoEnabled()) {
          Toast.makeText(this, getString(R.string.video_turned_off), Toast.LENGTH_LONG)
              .show();
        }

        WOWZStreamingError configError = startBroadcast(videoEnabled);
        if (configError != null) {
          Toast.makeText(getApplicationContext(), configError.getErrorDescription(),
              Toast.LENGTH_SHORT).show();
        }
      }
    } else {
      endBroadcast();
    }
  }

  protected void onToggleFlash(boolean flashEnabled) {
    WOWZCamera availableCameras[] = mWZCameraView.getCameras();
    if (availableCameras.length > 0) {
      for (WOWZCamera availableCamera : availableCameras) {
        availableCamera.setTorchOn(flashEnabled);
      }
    }
  }

  protected void onToggleAudio(boolean audioEnabled) {
    if (getBroadcast() == null) return;

    mWZBroadcastConfig.setAudioEnabled(audioEnabled);
    //if (audioEnabled) {
    //  sGoCoderSDK.muteAudio();
    //} else {
    //  sGoCoderSDK.unmuteAudio();
    //}

    AudioManager audioManager =
        (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
    audioManager.setMode(AudioManager.MODE_IN_CALL);
    if (audioManager.isMicrophoneMute() == false) {
      audioManager.setMicrophoneMute(true);
      Timber.e("MICROPHONE- MUTE");
    } else {
      audioManager.setMicrophoneMute(false);
      Timber.e("MICROPHONE- UNMUTE");
    }
  }

  protected void initGoCoderDevices() {
    if (sGoCoderSDK != null) {
      boolean videoIsInitialized = false;
      boolean audioIsInitialized = false;

      // Initialize the camera preview
      if (this.hasDevicePermissionToAccess(Manifest.permission.CAMERA)) {
        if (mWZCameraView != null) {
          WOWZCamera availableCameras[] = mWZCameraView.getCameras();
          // Ensure we can access to at least one camera
          if (availableCameras.length > 0) {
            // Set the video broadcaster in the broadcast config
            getBroadcastConfig().setVideoBroadcaster(mWZCameraView);
            videoIsInitialized = true;
          } else {
            Toast.makeText(getApplicationContext(),
                "Could not detect or gain access to any cameras on this device", Toast.LENGTH_SHORT)
                .show();
            getBroadcastConfig().setVideoEnabled(false);
          }
        }
      }

      if (this.hasDevicePermissionToAccess(Manifest.permission.RECORD_AUDIO)) {
        // Initialize the audio input device interface
        mWZAudioDevice = new WOWZAudioDevice();

        // Set the audio broadcaster in the broadcast config
        getBroadcastConfig().setAudioBroadcaster(mWZAudioDevice);
        audioIsInitialized = true;
      }

      if (videoIsInitialized && audioIsInitialized) mDevicesInitialized = true;
    }
  }

  @SuppressLint("SetTextI18n") protected void initUIControls() {
    // Initialize the UI controls
    mBtnBroadcast = findViewById(R.id.btn_start_live);
    mWZCameraView = findViewById(R.id.camera_preview);

    mUIInitialized = true;
    mBtnBroadcast.setText(getString(R.string.go_to_live));

    if (sGoCoderSDK == null) {
      Toast.makeText(getApplicationContext(), WowzaGoCoder.getLastError().getErrorDescription(),
          Toast.LENGTH_SHORT).show();
    }
  }

  protected boolean syncUIControlState() {
    boolean disableControls = (getBroadcast() == null || !(getBroadcast().getStatus().isIdle()
        || getBroadcast().getStatus().isRunning()));
    if (disableControls) {
      if (mBtnBroadcast != null) {
        mBtnBroadcast.setText(getString(R.string.stop_live));
      }
    } else {
      if (mBtnBroadcast != null) {
        mBtnBroadcast.setText(getString(R.string.go_to_live));
      }
    }

    return disableControls;
  }

  //define callback interface
  interface PermissionCallbackInterface {

    void onPermissionResult(boolean result);
  }
}
