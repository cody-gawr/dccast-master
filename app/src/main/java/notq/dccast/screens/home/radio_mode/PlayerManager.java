package notq.dccast.screens.home.radio_mode;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PlayerManager {

  private static PlayerManager instance = null;
  private static PlayerService service;

  private Context context;

  private boolean serviceBound;
  private ServiceConnection serviceConnection = new ServiceConnection() {

    @Override
    public void onServiceConnected(ComponentName arg0, IBinder binder) {
      service = ((PlayerService.PlayerBinder) binder).getService();
      serviceBound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName arg0) {
      serviceBound = false;
    }
  };

  private PlayerManager(Context context) {
    this.context = context;
    serviceBound = false;
  }

  public static PlayerManager with(Context context) {
    if (instance == null) {
      instance = new PlayerManager(context);
    }

    return instance;
  }

  public static PlayerService getService() {
    return service;
  }

  public void playOrPause(String streamUrl) {
    service.playOrPause(streamUrl);
  }

  public boolean isPlaying() {
    return service.isPlaying();
  }

  public boolean isServiceBound() {
    return serviceBound;
  }

  public void bind() {

    getApplicationContext().bindService(new Intent(context, PlayerService.class), serviceConnection,
        Context.BIND_AUTO_CREATE);

    if (service != null) {
      EventBus.getDefault().post(service.getStatus());
    }
  }

  public void unbind() {
    if (serviceBound) {
      try {
        context.unbindService(serviceConnection);
      } catch (Exception ex) {
        Timber.e("service unbind error: " + ex.getMessage());
      }
      serviceBound = false;
    }
  }
}
