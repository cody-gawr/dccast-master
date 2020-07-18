package notq.dccast.screens;

import android.os.Bundle;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
    //  getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
    //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN);
    //}
  }

  //@Override
  //public void onWindowFocusChanged(boolean hasFocus) {
  //  super.onWindowFocusChanged(hasFocus);
  //  if (hasFocus) {
  //    getWindow().getDecorView().setSystemUiVisibility(
  //        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
  //            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
  //            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
  //            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
  //            | View.SYSTEM_UI_FLAG_FULLSCREEN
  //            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
  //  }
  //}
}
