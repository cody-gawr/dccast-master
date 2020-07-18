package notq.dccast.screens.home.radio_mode;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import notq.dccast.R;
import notq.dccast.screens.home.ActivityHome;

public class PlayerNotificationManager {

  private PlayerService service;

  private int NOTIFICATION_ID = 555;

  private int REQUEST_CODE_PAUSE = 1;
  private int REQUEST_CODE_PLAY = 2;
  private int REQUEST_CODE_STOP = 3;

  private String mAppname;

  private Resources resources;

  public PlayerNotificationManager(PlayerService service) {
    this.service = service;
    resources = service.getResources();

    mAppname = resources.getString(R.string.app_name);
  }

  PendingIntent createAction(String action, int requestCode) {
    Intent intent = new Intent(service, PlayerService.class);
    intent.setAction(action);
    return PendingIntent.getService(service, requestCode, intent, 0);
  }

  void startNotify(String playbackStatus) {

    int icon = R.drawable.ic_notification_pause;

    PendingIntent playPauseAction = createAction(PlayerService.ACTION_PAUSE, REQUEST_CODE_PAUSE);

    if (playbackStatus.equalsIgnoreCase(PlaybackStatus.PAUSED)) {
      icon = R.drawable.ic_notification_play;

      playPauseAction = createAction(PlayerService.ACTION_PLAY, REQUEST_CODE_PLAY);
    }

    PendingIntent stopAction = createAction(PlayerService.ACTION_STOP, REQUEST_CODE_STOP);

    Intent intent = new Intent(service, ActivityHome.class);
    intent.setAction(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_LAUNCHER);
    PendingIntent pendingIntent = PendingIntent.getActivity(service, 0, intent, 0);

    String channelId = "";
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      channelId =
          (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) ? createNotificationChannel("my_service",
              "My Background Service") : "";
    }

    NotificationCompat.Builder builder = new NotificationCompat.Builder(service, channelId)
        .setSmallIcon(R.drawable.exo_edit_mode_logo)
        .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_dccast))
        .setContentTitle(mAppname)
        .setContentText("DCCast, playing background video")
        .setContentIntent(pendingIntent)
        .addAction(icon, "pause", playPauseAction)
        .addAction(R.drawable.exo_icon_stop, "stop", stopAction)
        .setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(
            service.getMediaSession().getSessionToken())
            .setShowActionsInCompactView(0, 1)
            .setShowCancelButton(true)
            .setCancelButtonIntent(stopAction));

    service.startForeground(NOTIFICATION_ID, builder.build());
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private String createNotificationChannel(String channelId, String channelName) {
    NotificationChannel chan = new NotificationChannel(channelId,
        channelName, NotificationManager.IMPORTANCE_NONE);
    chan.setLightColor(Color.BLUE);
    chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
    NotificationManager service =
        (NotificationManager) this.service.getSystemService(Context.NOTIFICATION_SERVICE);
    service.createNotificationChannel(chan);
    return channelId;
  }

  void cancelNotify() {
    service.stopForeground(true);
  }
}
