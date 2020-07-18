package notq.dccast.screens.home.radio_mode;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

  protected PlayerManager playerManager;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    playerManager = PlayerManager.with(getActivity());
  }

  @Override public void onStart() {
    super.onStart();
  }

  @Override public void onResume() {
    super.onResume();

    playerManager.bind();
  }

  @Override public void onStop() {
    super.onStop();
  }
}
