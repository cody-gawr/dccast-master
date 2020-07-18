package notq.dccast.screens.video_trimmer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnK4LVideoListener;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;
import notq.dccast.R;
import notq.dccast.databinding.ActivityTrimmerBinding;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.home.ActivityHome;
import notq.dccast.screens.navigation_menu.vod.ActivityUploadVOD;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityTrimmer extends BaseActivity
    implements OnTrimVideoListener, OnK4LVideoListener {
  public static final String UPLOAD_IMAGE_URI = "UPLOAD_IMAGE_URI";
  private final int REQUEST_VOD_UPLOAD = 4923;
  private ActivityTrimmerBinding binding;
  private K4LVideoTrimmer mVideoTrimmer;
  private ProgressDialog mProgressDialog;
  private int groupId;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_trimmer);
    String path;
    if (getIntent().getExtras() != null) {
      groupId = getIntent().getExtras().getInt("groupId");
      path = getIntent().getExtras().getString(ActivityHome.EXTRA_VIDEO_PATH);
    } else {
      groupId = -1;
      path = null;
    }
    if (path != null) {
      Log.e("path", path);
      init(path);
    } else {
      Log.e("path ni null baina", "ok");
    }
  }

  private void init(String path) {
    mProgressDialog = new ProgressDialog(this);
    mProgressDialog.setCancelable(false);
    mProgressDialog.setMessage(getString(R.string.trimming_video));
    mVideoTrimmer = binding.timeLine;
    if (mVideoTrimmer != null) {
      mVideoTrimmer.setMaxDuration(10 * 60 * 60);
      mVideoTrimmer.setOnTrimVideoListener(this);
      mVideoTrimmer.setOnK4LVideoListener(this);
      mVideoTrimmer.setVideoURI(Uri.parse(path));
      mVideoTrimmer.setVideoInformationVisibility(true);
    }
  }

  @Override public void onTrimStarted() {
    mProgressDialog.show();
  }

  @Override public void getResult(Uri uri) {
    mProgressDialog.cancel();
    Intent uploadIntent = new Intent(ActivityTrimmer.this, ActivityUploadVOD.class);
    Bundle bundle = new Bundle();
    bundle.putString(UPLOAD_IMAGE_URI, uri.toString());
    bundle.putInt("groupId", groupId);
    uploadIntent.putExtras(bundle);
    startActivityForResult(uploadIntent, REQUEST_VOD_UPLOAD);
    //finish();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_VOD_UPLOAD) {
      setResult(resultCode, data);
      finish();
    }
  }

  @Override public void cancelAction() {
    mProgressDialog.cancel();
    mVideoTrimmer.destroy();
    finish();
  }

  @Override public void onError(String message) {
    mProgressDialog.cancel();

    runOnUiThread(
        () -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
  }

  @Override public void onVideoPrepared() {

  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
