/**
 * This is sample code provided by Wowza Media Systems, LLC.  All sample code is intended to be a
 * reference for the
 * purpose of educating developers, and is not intended to be used in any production environment.
 *
 * IN NO EVENT SHALL WOWZA MEDIA SYSTEMS, LLC BE LIABLE TO YOU OR ANY PARTY FOR DIRECT, INDIRECT,
 * SPECIAL, INCIDENTAL,
 * OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS
 * DOCUMENTATION,
 * EVEN IF WOWZA MEDIA SYSTEMS, LLC HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * WOWZA MEDIA SYSTEMS, LLC SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. ALL CODE PROVIDED HEREUNDER IS PROVIDED
 * "AS IS".
 * WOWZA MEDIA SYSTEMS, LLC HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 * ENHANCEMENTS, OR MODIFICATIONS.
 *
 * © 2015 – 2019 Wowza Media Systems, LLC. All rights reserved.
 */

package notq.dccast.screens.navigation_menu.live;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import com.wowza.gocoder.sdk.api.configuration.WOWZMediaConfig;
import com.wowza.gocoder.sdk.api.configuration.WOWZStreamConfig;
import com.wowza.gocoder.sdk.api.configuration.WowzaConfig;
import com.wowza.gocoder.sdk.api.h264.WOWZProfileLevel;
import notq.dccast.util.Url;

import static android.content.Context.MODE_PRIVATE;

class GoCoderSDKPrefs {
  private static String getPrefString(SharedPreferences sharedPrefs, String key,
      String defaultValue) {
    String value = sharedPrefs.getString(key, defaultValue);
    if (value.isEmpty()) {
      return defaultValue;
    }
    return value;
  }

  private static void updateConfigFromPrefs(SharedPreferences sharedPrefs,
      WOWZMediaConfig mediaConfig) {
    // video settings
    mediaConfig.setVideoEnabled(sharedPrefs.getBoolean("wz_video_enabled", true));

    mediaConfig.setVideoFrameWidth(
        sharedPrefs.getInt("wz_video_frame_width", WOWZMediaConfig.DEFAULT_VIDEO_FRAME_WIDTH));
    mediaConfig.setVideoFrameHeight(
        sharedPrefs.getInt("wz_video_frame_height", WOWZMediaConfig.DEFAULT_VIDEO_FRAME_HEIGHT));
    mediaConfig.setVideoFramerate(WOWZMediaConfig.DEFAULT_VIDEO_FRAME_RATE);
    mediaConfig.setVideoKeyFrameInterval(Integer.parseInt(
        getPrefString(sharedPrefs, "wz_video_keyframe_interval",
            String.valueOf(WOWZMediaConfig.DEFAULT_VIDEO_KEYFRAME_INTERVAL))));
    mediaConfig.setVideoBitRate(Integer.parseInt(getPrefString(sharedPrefs, "wz_video_bitrate",
        String.valueOf(WOWZMediaConfig.DEFAULT_VIDEO_BITRATE))));
    mediaConfig.setABREnabled(sharedPrefs.getBoolean("wz_video_use_abr", true));
    mediaConfig.setHLSEnabled(sharedPrefs.getBoolean("wz_use_hls", false));
    mediaConfig.setHLSBackupURL(sharedPrefs.getString("wz_hls_failover", null));

    int profile = sharedPrefs.getInt("wz_video_profile_level_profile", -1);
    int level = sharedPrefs.getInt("wz_video_profile_level_level", -1);
    if (profile != -1 && level != -1) {
      WOWZProfileLevel profileLevel = new WOWZProfileLevel(profile, level);
      if (profileLevel.validate()) {
        mediaConfig.setVideoProfileLevel(profileLevel);
      }
    } else {
      mediaConfig.setVideoProfileLevel(null);
    }

    // audio settings
    mediaConfig.setAudioEnabled(sharedPrefs.getBoolean("wz_audio_enabled", true));

    mediaConfig.setAudioSampleRate(Integer.parseInt(
        getPrefString(sharedPrefs, "wz_audio_samplerate",
            String.valueOf(WOWZMediaConfig.DEFAULT_AUDIO_SAMPLE_RATE))));
    mediaConfig.setAudioChannels(
        sharedPrefs.getBoolean("wz_audio_stereo", true) ? WOWZMediaConfig.AUDIO_CHANNELS_STEREO
            : WOWZMediaConfig.AUDIO_CHANNELS_MONO);
    mediaConfig.setAudioBitRate(Integer.parseInt(getPrefString(sharedPrefs, "wz_audio_bitrate",
        String.valueOf(WOWZMediaConfig.DEFAULT_AUDIO_BITRATE))));
  }

  public static String getApplicationName(Context context) {
    ApplicationInfo applicationInfo = context.getApplicationInfo();
    int stringId = applicationInfo.labelRes;
    return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString()
        : context.getString(stringId);
  }

  static void updateConfigFromPrefs(Context context, SharedPreferences sharedPrefs,
      WOWZStreamConfig streamConfig) {
    // connection settings
    streamConfig.setHostAddress(Url.wowzaUrl);
    streamConfig.setPortNumber(Integer.parseInt(
        sharedPrefs.getString("wz_live_port_number", String.valueOf(WowzaConfig.DEFAULT_PORT))));
    //streamConfig.setUseSSL(sharedPrefs.getBoolean("wz_live_use_ssl", false));
    streamConfig.setApplicationName(
        sharedPrefs.getString("wz_live_app_name", WowzaConfig.DEFAULT_APP));
    Log.e("app name", sharedPrefs.getString("wz_live_app_name", WowzaConfig.DEFAULT_APP));
    SharedPreferences prefs = context.getSharedPreferences("DCCAST", MODE_PRIVATE);
    Log.e("stream name", prefs.getString("stream_name", WowzaConfig.DEFAULT_STREAM));
    streamConfig.setStreamName(
        prefs.getString("stream_name", WowzaConfig.DEFAULT_STREAM));

    streamConfig.setIsPlayback(false);
    updateConfigFromPrefs(sharedPrefs, (WOWZMediaConfig) streamConfig);
  }

  static int getScaleMode(SharedPreferences sharedPrefs) {
    return sharedPrefs.getBoolean("wz_video_resize_to_aspect", false)
        ? WOWZMediaConfig.RESIZE_TO_ASPECT : WOWZMediaConfig.FILL_VIEW;
  }

  static void storeHostConfig(SharedPreferences sharedPrefs, WOWZStreamConfig streamConfig) {
    String hostAddress = streamConfig.getHostAddress();
    if (hostAddress == null || hostAddress.trim().length() == 0) return;

    AutoCompletePreference.storeAutoCompleteHostConfig(sharedPrefs, streamConfig);
  }
}
