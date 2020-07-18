package notq.dccast.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import io.realm.Realm;
import java.util.HashMap;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.ProfileAPIInterface;
import notq.dccast.api.my_content.MyContentAPIInterface;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.profile.ModelProfile;
import notq.dccast.model.user.profile.ModelUserProfileWrapper;
import notq.dccast.screens.home.ActivityHome;
import notq.dccast.util.Constants;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

  @Override public void onNewToken(String newToken) {
    super.onNewToken(newToken);

    updateProfile(newToken);
  }

  private void updateProfile(String token) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    int userId = loginUser.getId();

    HashMap<String, Object> updateValues = new HashMap<>();
    updateValues.put("user", userId);
    if (token != null) {
      updateValues.put("client_token", token);
    }

    updateValues.put("auto_login", loginUser.getAutoLogin());
    updateValues.put("notice_live", loginUser.getNoticeLive());
    updateValues.put("notice_vod", loginUser.getNoticeVod());
    updateValues.put("notice_chat", loginUser.getNoticeChat());
    updateValues.put("notice_notice", loginUser.getNoticeNotice());

    ProfileAPIInterface profileAPIInterface =
        APIClient.getClient().create(ProfileAPIInterface.class);
    Call<ModelProfile> call = profileAPIInterface.updateProfile(userId, updateValues);

    call.enqueue(new Callback<ModelProfile>() {
      @Override
      public void onResponse(@NonNull Call<ModelProfile> call,
          @NonNull Response<ModelProfile> response) {

        ModelProfile updatedProfile = response.body();

        if (updatedProfile == null) {
          return;
        }

        Realm backgroundRealm = Realm.getDefaultInstance();
        backgroundRealm.executeTransactionAsync(realm -> {
          ModelUser loginUser1 = LoginService.getLoginUser();
          if (loginUser1 != null) {
            loginUser1.setProfile(updatedProfile);
          }
        });

        Timber.e("SAVE TOKEN SUCCESS!");
      }

      @Override
      public void onFailure(@NonNull Call<ModelProfile> call, @NonNull Throwable t) {
        call.cancel();
        Timber.e("Save firebase messaging service: " + t.getMessage());
      }
    });
  }

  @Override public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);

    if (remoteMessage.getData() != null) {
      String title = remoteMessage.getData().get("title");
      String body = remoteMessage.getData().get("body");
      String type = remoteMessage.getData().get("type");
      String user_id = remoteMessage.getData().get("user_id");
      String content_id = remoteMessage.getData().get("content_id");

      Timber.e("onMessageReceived: " + remoteMessage.getData().toString());

      getUser(title, body, type, user_id, content_id);
    }
  }

  private void getUser(final String messageTitle, final String messageBody,
      final String type, final String user_id, final String content_id) {
    int userId = -1;
    try {
      userId = Integer.parseInt(user_id);
    } catch (Exception ignored) {

    }

    if (userId == -1) {
      return;
    }

    Call<ModelUserProfileWrapper> call =
        APIClient.getClient().create(MyContentAPIInterface.class).getProfile(userId);

    int finalUserId = userId;
    call.enqueue(new Callback<ModelUserProfileWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelUserProfileWrapper> call,
          @NonNull Response<ModelUserProfileWrapper> response) {
        ModelUserProfileWrapper stat = response.body();

        if (stat == null || stat.users == null || stat.users.size() == 0) {
          return;
        }

        ModelUser modelUser = stat.users.get(0);
        sendNotification(modelUser, messageTitle, messageBody, type, finalUserId, content_id);
      }

      @Override
      public void onFailure(@NonNull Call<ModelUserProfileWrapper> call, @NonNull Throwable t) {
        call.cancel();

        Timber.e("Get user error: " + t.getMessage());
      }
    });
  }

  private void sendNotification(ModelUser modelUser, final String messageTitle,
      final String messageBody,
      final String type, final int user_id, final String content_id) {

    boolean notice_live = modelUser.getNoticeLive();
    boolean notice_vod = modelUser.getNoticeVod();
    boolean notice_notice = modelUser.getNoticeNotice();
    boolean notice_sound = modelUser.getNoticeSound();
    boolean notice_vibration = modelUser.getNoticeVibration();

    int videoId = -1;

    if (type != null) {
      if (type.equals("live_hit") || type.equals("live_subscribe")) {
        if (!notice_live) {
          return;
        }
        videoId = Integer.parseInt(content_id);
      } else if (type.equals("vod_hit") || type.equals("vod_subscribe") || type.equals(
          "vod_comment")) {
        if (!notice_vod) {
          return;
        }
        videoId = Integer.parseInt(content_id);
      }
    }

    Intent intent = new Intent(MyFirebaseMessagingService.this, ActivityHome.class);
    if (videoId > 0) {
      intent.putExtra(Constants.OPEN_VIDEO_ID, videoId);
    }
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
        Intent.FLAG_ACTIVITY_SINGLE_TOP);
    PendingIntent pendingIntent =
        PendingIntent.getActivity(MyFirebaseMessagingService.this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT);

    String channelId = getString(R.string.default_notification_channel_id);
    NotificationCompat.Builder notificationBuilder = null;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      notificationBuilder =
          new NotificationCompat.Builder(MyFirebaseMessagingService.this, channelId)
              .setSmallIcon(R.drawable.ic_dccast)
              .setContentTitle(messageTitle)
              .setContentText(messageBody)
              .setAutoCancel(true)
              .setDefaults(Notification.DEFAULT_ALL)
              .setPriority(NotificationManager.IMPORTANCE_HIGH)
              .setContentIntent(pendingIntent);
    } else {
      notificationBuilder =
          new NotificationCompat.Builder(MyFirebaseMessagingService.this, channelId)
              .setSmallIcon(R.drawable.ic_dccast)
              .setContentTitle(messageTitle)
              .setContentText(messageBody)
              .setAutoCancel(true)
              .setContentIntent(pendingIntent);
    }
    //
    int defaults = 0;
    if (notice_sound) {
      defaults |= Notification.DEFAULT_SOUND;
    }
    if (notice_vibration) {
      defaults |= Notification.DEFAULT_VIBRATE;
    }
    notificationBuilder.setDefaults(defaults);

    // sound
    if (notice_sound) {
      Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
      notificationBuilder.setSound(uri);
    } else {
    }
    // vibration
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
      if (notice_vibration) {
        long[] v = {500, 1000};
        notificationBuilder.setVibrate(v);
      } else {
        //notificationBuilder.setVibrate(new long[]{0L});
      }
    } else {
      if (notice_vibration) {
        long[] v = {500, 1000};
        notificationBuilder.setVibrate(v);
      } else {
        notificationBuilder.setVibrate(new long[] {500, 1000});
      }
    }
    //
    NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    // Since android Oreo data channel is needed.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel channel = new NotificationChannel(channelId,
          "Channel human readable title",
          NotificationManager.IMPORTANCE_HIGH);
      notificationBuilder.setChannelId(channelId);
      // sound
      if (notice_sound == false) {
        channel.setImportance(NotificationManager.IMPORTANCE_LOW);
        channel.setSound(null, null);
      } else {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build();
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        channel.setSound(uri, audioAttributes);
      }
      // vibration
      if (notice_vibration) {
        channel.setVibrationPattern(new long[] {100, 200, 300});
        channel.enableVibration(true);
      } else {
        channel.setVibrationPattern(new long[] {0});
        channel.enableVibration(true);
      }
      notificationManager.createNotificationChannel(channel);
    }

    notificationManager.notify(0 /* ID of data */, notificationBuilder.build());

    Timber.e("NOTIFIED");
  }
}
