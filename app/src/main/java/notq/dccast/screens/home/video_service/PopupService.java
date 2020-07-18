package notq.dccast.screens.home.video_service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RemoteViews;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import notq.dccast.R;
import notq.dccast.screens.splash.ActivitySplash;
import notq.dccast.util.Constants;
import notq.dccast.util.PopUpUtil;
import notq.dccast.util.Util;

/**
 * Created by Martin on 1/16/2018
 */

public class PopupService extends Service {

  public static final int FOREGROUND_SERVICE = 103;

  public static final String CHANNEL_ID = "DCCast_Notification";
  public static final String CHANNEL_NAME = "DCCast_Notification Name";
  public static final String ACTION_START_FOREGROUND = "ACTION_START_FOREGROUND";
  public static final String ACTION_STOP_FOREGROUND = "ACTION_STOP_FOREGROUND";
  public static final String ACTION_EXPAND = "ACTION_EXPAND";
  public static final String ACTION_PLAY = "ACTION_PLAY";
  public static boolean isStop = false;
  public static int live_id = -1;
  int playerWidth, playerHeight;
  WindowManager.LayoutParams paramPlayer;
  Notification status;
  private RemoteViews bigViews;
  private RemoteViews views;
  private String type = "popup";
  private ExoPlayer mPlayer;
  private WindowManager windowManager;
  private boolean isPlay = false;
  private boolean isStartService = false;
  private BroadcastReceiver myReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if (action != null) {
        switch (action) {
          case ACTION_STOP_FOREGROUND:
            destroyService();
            break;
        }
      }
    }
  };
  private Runnable timeViewRunnable = new Runnable() {
    @Override
    public void run() {
      timeView_proc();
      if (isPlay == true) {
        Util.getHandler().postDelayed(this, 200);
      }
    }
  };

  private void resumeToApp() {
    PopUpUtil.setOpenVideoId(mPlayer.getVideoItem().getId());
    Intent intent = new Intent(PopupService.this, ActivitySplash.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);

    destroyService();
  }

  public void destroyService() {
    isStartService = false;
    Log.i("Trying To Destroy ", "...");
    stopForeground(true);
    stopSelf();
    stopService(new Intent(getApplicationContext(), PopupService.class));
  }

  private void registerReceiver() {
    IntentFilter filter = new IntentFilter();
    filter.addAction(ACTION_STOP_FOREGROUND);
    registerReceiver(myReceiver, filter);
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {

    String action = intent.getAction();
    if (action == null) {
      action = ACTION_START_FOREGROUND;
    }
    switch (action) {
      case ACTION_START_FOREGROUND:

        initService(intent);
        showNotification();
        registerReceiver();
        break;

      case ACTION_EXPAND: {
        Util.getHandler().removeCallbacks(timeViewRunnable);

        collpasePanel(this);
        resumeToApp();
      }
      break;

      case ACTION_STOP_FOREGROUND:
        destroyService();
        break;
      case ACTION_PLAY: {
        if (isStop == true) {
          return START_NOT_STICKY;
        }
        isPlay = !isPlay;
        if (isPlay == true) {
          views.setImageViewResource(R.id.btn_play,
              R.drawable.ic_notification_pause);
          bigViews.setImageViewResource(R.id.btn_play,
              R.drawable.ic_notification_pause);
          mPlayer.resumeVideo();
          Util.getHandler().post(timeViewRunnable);
        } else {
          views.setImageViewResource(R.id.btn_play,
              R.drawable.ic_notification_play);
          bigViews.setImageViewResource(R.id.btn_play,
              R.drawable.ic_notification_play);
          mPlayer.pauseVideo();
          Util.getHandler().removeCallbacks(timeViewRunnable);
        }

        NotificationManager notificationManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
          notificationManager = getSystemService(NotificationManager.class);
        }
        if (notificationManager != null) {
          notificationManager.notify(FOREGROUND_SERVICE, status);
        }
      }
      break;
    }

    return START_NOT_STICKY;
  }

  @Override
  public void onDestroy() {
    unregisterReceiver(myReceiver);
    if (type.equals("popup")) {
      windowManager.removeView(mPlayer.mContent);
    }
    mPlayer.releasePlayer();
    Util.getHandler().removeCallbacks(timeViewRunnable);

    super.onDestroy();
  }

  private void collpasePanel(Context _context) {
    try {
      Object sbservice = _context.getSystemService("statusbar");
      Class<?> statusbarManager;
      statusbarManager = Class.forName("android.app.StatusBarManager");
      Method showsb;
      if (Build.VERSION.SDK_INT >= 17) {
        showsb = statusbarManager.getMethod("collapsePanels");
      } else {
        showsb = statusbarManager.getMethod("collapse");
      }
      showsb.invoke(sbservice);
    } catch (ClassNotFoundException _e) {
      _e.printStackTrace();
    } catch (NoSuchMethodException _e) {
      _e.printStackTrace();
    } catch (IllegalArgumentException _e) {
      _e.printStackTrace();
    } catch (IllegalAccessException _e) {
      _e.printStackTrace();
    } catch (InvocationTargetException _e) {
      _e.printStackTrace();
    }
  }

  private void initService(Intent intent) {
    playerWidth = getPixelFromDp(280);
    playerHeight = getPixelFromDp(175);
    Bundle data = intent.getExtras();

    mPlayer = new ExoPlayer();
    if (data != null) {
      mPlayer.setVideoItem(PopUpUtil.getPopUpVideo());
      type = data.getString("type");
    }

    mPlayer.initVideoPlayer(getApplicationContext());
    //if (type != null) {
    //  mPlayer.setType(type);
    //}
    mPlayer.resumeVideo();
    isPlay = true;

    if (type.equals("popup")) {
      initView();
    }
    isStartService = true;
    isStop = false;
  }

  private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      CharSequence name = CHANNEL_NAME;
      String description = "This is dccast channel";
      int importance = NotificationManager.IMPORTANCE_DEFAULT;
      NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
      channel.setDescription(description);

      NotificationManager notificationManager = getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(channel);
    }
  }

  private void showNotification() {
    createNotificationChannel();
    views = new RemoteViews(getPackageName(),
        R.layout.notification_view);
    bigViews = new RemoteViews(getPackageName(),
        R.layout.notification_expanded_view);

    Intent notificationIntent = new Intent(this, PopupService.class);
    notificationIntent.setAction(Constants.MAIN_ACTION);
    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
        notificationIntent, 0);

    Intent playIntent = new Intent(this, PopupService.class);
    playIntent.setAction(ACTION_PLAY);
    PendingIntent pendingPlayIntent = PendingIntent.getService(this, 0,
        playIntent, 0);

    Intent resumeIntent = new Intent(this, PopupService.class);
    resumeIntent.setAction(ACTION_EXPAND);
    PendingIntent presumeIntent = PendingIntent.getService(this, 0,
        resumeIntent, 0);

    views.setOnClickPendingIntent(R.id.btn_play, pendingPlayIntent);
    bigViews.setOnClickPendingIntent(R.id.btn_play, pendingPlayIntent);

    views.setOnClickPendingIntent(R.id.btn_resume, presumeIntent);
    bigViews.setOnClickPendingIntent(R.id.btn_resume, presumeIntent);

    views.setImageViewResource(R.id.btn_play,
        R.drawable.ic_notification_pause);
    bigViews.setImageViewResource(R.id.btn_play,
        R.drawable.ic_notification_pause);

    views.setTextViewText(R.id.txt_live_title, mPlayer.getVideoItem().getTitle());
    bigViews.setTextViewText(R.id.txt_live_title, mPlayer.getVideoItem().getTitle());

    views.setTextViewText(R.id.txt_live_time, "");
    bigViews.setTextViewText(R.id.txt_live_time, "");

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      status = new Notification.Builder(this, CHANNEL_ID)
          .setAutoCancel(true)
          .build();
    } else {
      status = new NotificationCompat.Builder(this, CHANNEL_ID)
          .setAutoCancel(true)
          .build();
    }
    status.contentView = views;
    status.bigContentView = bigViews;
    status.flags = Notification.FLAG_ONGOING_EVENT;
    status.icon = R.drawable.ic_dccast;
    status.contentIntent = pendingIntent;

    startForeground(FOREGROUND_SERVICE, status);

    Util.getHandler().post(timeViewRunnable);
  }

  private void initView() {
    windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
      paramPlayer = new WindowManager.LayoutParams(
          playerWidth, playerHeight,
          WindowManager.LayoutParams.TYPE_PHONE,
          WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
              | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
              | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
          PixelFormat.TRANSLUCENT);
    } else {
      paramPlayer = new WindowManager.LayoutParams(
          playerWidth, playerHeight,
          WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
          WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
              | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
              | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
          PixelFormat.TRANSLUCENT);
    }
    paramPlayer.gravity = Gravity.TOP | Gravity.START;
    Point size = getDeviceSize();
    paramPlayer.x = (size.x - playerWidth) / 2;
    paramPlayer.y = (size.y - playerHeight) / 2;

    windowManager.addView(mPlayer.mContent, paramPlayer);

    mPlayer.mContent.findViewById(R.id.btn_expand).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        resumeToApp();
      }
    });
    mPlayer.mContent.findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        destroyService();
      }
    });
    mPlayer.mContent.findViewById(R.id.txt_live_title)
        .setOnTouchListener(new View.OnTouchListener() {
          int statusbarHeight = getStatusBarHeight();
          private int initialX, initialY;
          private float initialTouchX, initialTouchY;

          @Override
          public boolean onTouch(View v, final MotionEvent event) {

            Point deviceSize = getDeviceSize();

            switch (event.getAction()) {
              case MotionEvent.ACTION_DOWN:
                initialX = paramPlayer.x;
                initialY = paramPlayer.y;
                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();
                return true;

              case MotionEvent.ACTION_UP:
                return true;

              case MotionEvent.ACTION_MOVE:
                int newX, newY;
                newX = initialX + (int) (event.getRawX() - initialTouchX);
                newY = initialY + (int) (event.getRawY() - initialTouchY);

                if (newX < 0) {
                  paramPlayer.x = 0;
                } else if (playerWidth + newX > deviceSize.x) {
                  paramPlayer.x = deviceSize.x - playerWidth;
                } else {
                  paramPlayer.x = newX;
                }

                if (newY < 0) {
                  paramPlayer.y = 0;
                } else if (playerHeight + newY > deviceSize.y - statusbarHeight) {
                  paramPlayer.y = deviceSize.y - statusbarHeight - playerHeight;
                } else {
                  paramPlayer.y = newY;
                }

                windowManager.updateViewLayout(mPlayer.mContent, paramPlayer);
                return true;
            }
            return false;
          }
        });

    Util.getHandler().post(timeViewRunnable);
  }

  private Point getDeviceSize() {
    Display display = windowManager.getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    return size;
  }

  private int getStatusBarHeight() {
    return getPixelFromDp(25);
  }

  private int getPixelFromDp(float dp) {
    return (int) (dp * getApplicationContext().getResources().getDisplayMetrics().density);
  }

  private void timeView_proc() {
    if (!type.equals("popup")) {
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      long currentTime = timestamp.getTime();
      int duration = (int) ((currentTime - mPlayer.getVideoItem().getDuration()) / 1000);
      int hours = duration / 3600;
      int mins = (duration % 3600) / 60;
      int secs = ((duration % 3600) % 60);
      String durationString = String.format("%02d:%02d:%02d", hours, mins, secs);

      Date date = new Date(currentTime - mPlayer.getVideoItem().getDuration());
      DateFormat formatter = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
      String dateFormatted = formatter.format(date);
      views.setTextViewText(R.id.txt_live_time, durationString);
      bigViews.setTextViewText(R.id.txt_live_time, durationString);

      if (isPlay == true && isStop == true && live_id == mPlayer.getVideoItem().getId()) {
        Util.getHandler().removeCallbacks(timeViewRunnable);

        collpasePanel(this);
        resumeToApp();
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.cancel(FOREGROUND_SERVICE);
        return;
      }
      NotificationManager notificationManager = getSystemService(NotificationManager.class);
      notificationManager.notify(FOREGROUND_SERVICE, status);
    } else {
      if (isPlay == true && isStop == true && live_id == mPlayer.getVideoItem().getId()) {
        Util.getHandler().removeCallbacks(timeViewRunnable);

        collpasePanel(this);
        resumeToApp();
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.cancel(FOREGROUND_SERVICE);
        return;
      }
    }
  }
}
