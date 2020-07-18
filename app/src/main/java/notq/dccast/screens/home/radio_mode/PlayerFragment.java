package notq.dccast.screens.home.radio_mode;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ui.PlayerView;
import notq.dccast.R;

import static notq.dccast.screens.home.radio_mode.PlayerManager.getService;

public class PlayerFragment extends BaseFragment {

  private PlayerView playerView;

  private String videoUrl = "http://mirrors.standaloneinstaller.com/video-sample/lion-sample.mp4";

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_player, container, false);
    initInstances(rootView);
    return rootView;
  }

  private void initInstances(View rootView) {
    playerView = rootView.findViewById(R.id.video_view);

    if (getService() == null) {
      playerManager.bind();
    }

    playerView.setControllerHideOnTouch(false);
    playerView.setControllerShowTimeoutMs(0);
    playerView.showController();
  }

  private void managerBinding() {
    if (playerManager.isServiceBound()) {
      playerManager.playOrPause(videoUrl);
      playerView.setPlayer(getService().exoPlayer);
    }
  }

  @Override public void onResume() {
    super.onResume();

    managerBinding();
  }

  private void hideSystemUiFullScreen() {
    playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
        | View.SYSTEM_UI_FLAG_FULLSCREEN
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
  }

  private void hideSystemUi() {
    playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
  }

  @Override public void onConfigurationChanged(@NonNull Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    int currentOrientation = getResources().getConfiguration().orientation;
    if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
      hideSystemUiFullScreen();
    } else {
      hideSystemUi();
    }
  }
}
