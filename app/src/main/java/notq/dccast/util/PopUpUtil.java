package notq.dccast.util;

import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;
import notq.dccast.model.ModelVideo;
import timber.log.Timber;

public class PopUpUtil {
  public static ModelVideo getPopUpVideo() {
    Gson gson = new Gson();
    String json = Prefs.getString("serviceMedia", "");
    ModelVideo media = null;
    try {
      media = gson.fromJson(json, ModelVideo.class);
    } catch (Exception error) {
      Timber.e("ERROR: " + error.getMessage());
    }
    return media;
  }

  public static void setPopUpVideo(ModelVideo media) {
    Gson gson = new Gson();
    String json = gson.toJson(media);
    Prefs.putString("serviceMedia", json);
  }

  public static int getOpenVideoId() {
    return Prefs.getInt("openVideoId", -1);
  }

  public static void setOpenVideoId(int videoId) {
    Prefs.putInt("openVideoId", videoId);
  }
}
