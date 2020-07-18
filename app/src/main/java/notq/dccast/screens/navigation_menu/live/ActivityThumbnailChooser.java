package notq.dccast.screens.navigation_menu.live;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import com.google.gson.JsonObject;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.live.LiveAPIInterface;
import notq.dccast.api.vod.VodAPIInterface;
import notq.dccast.databinding.ActivityThumbnailChooserBinding;
import notq.dccast.model.ModelGroupVideo;
import notq.dccast.model.live.LiveResponse;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.BaseActivity;
import notq.dccast.util.LoginService;
import notq.dccast.util.Url;
import notq.dccast.util.Util;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityThumbnailChooser extends BaseActivity {
  public static final int THUMBNAIL_DISMISSED = 1233;
  private static final int CAMERA_REQUEST = 1888;
  private static final int GALLERY_REQUEST = 1999;

  private static final int ALBUM_CAMERA_REQUEST = 2888;
  private static final int ALBUM_GALLERY_REQUEST = 2999;
  private final int REQUEST_LIVE = 1235;
  private ActivityThumbnailChooserBinding binding;
  private JSONObject params;
  private int groupId = -1;
  private int LIVE_TYPE = ActivityLiveSettings.LIVE_TYPE_CAMERA;
  private Context mContext = this;
  private ProgressDialog progressDialog;
  private boolean dismissIgnore = false;
  private AlertDialog optionsDialog;
  private String mCurrentPhotoPath;
  private String mediaThumbUrl;

  public String getRealPathFromURI(Uri uri) {
    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
    if (cursor == null) {
      return null;
    }
    cursor.moveToFirst();
    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
    return cursor.getString(idx);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_thumbnail_chooser);

    if (getIntent().getExtras() != null) {
      Bundle bundle = getIntent().getExtras();
      try {
        params = new JSONObject(bundle.getString("params"));
        groupId = bundle.getInt("groupId", -1);
        LIVE_TYPE =
            bundle.getInt("liveType", ActivityLiveSettings.LIVE_TYPE_CAMERA);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    init();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    Bitmap photo;
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_LIVE) {
      setResult(resultCode, data);
      finish();
      return;
    }
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case CAMERA_REQUEST: {
          int targetWidth = binding.cropImageView.getWidth();
          int targetHeight = binding.cropImageView.getHeight();
          Bitmap imageBitmap =
              Util.setReducedImageSize(targetWidth, targetHeight, mCurrentPhotoPath);

          if (imageBitmap == null && data != null && data.getExtras() != null) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
          }

          Bitmap rotatedBitmap = Util.rotateImageIfRequired(imageBitmap, mCurrentPhotoPath);
          loadImage(rotatedBitmap);
          break;
        }
        case GALLERY_REQUEST: {
          try {
            Uri selectedImageUri = data.getData();
            String selectedImagePath = Util.getPath(this, selectedImageUri);
            photo = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                data.getData());
            Bitmap rotatedBitmap = Util.modifyOrientation(photo, selectedImagePath);
            String imageName = String.valueOf(System.currentTimeMillis());
            Util.saveImage(this, rotatedBitmap, imageName, "jpg");
            loadImage(selectedImageUri);
          } catch (IOException e) {
            e.printStackTrace();
          }
          break;
        }
        case ALBUM_CAMERA_REQUEST: {
          Uri liveAlbumImage = Uri.parse(mCurrentPhotoPath);
          requestAlbumImageUpload(liveAlbumImage.toString(), mediaThumbUrl);
          break;
        }
        case ALBUM_GALLERY_REQUEST: {
          try {
            Uri selectedImageUri = data.getData();
            String selectedImagePath = Util.getPath(this, selectedImageUri);
            photo = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                data.getData());
            Bitmap rotatedBitmap = Util.modifyOrientation(photo, selectedImagePath);
            String imageName = String.valueOf(System.currentTimeMillis());
            Util.saveImage(this, rotatedBitmap, imageName, "jpg");
            Uri liveAlbumImage = selectedImageUri;
            requestAlbumImageUpload(liveAlbumImage.toString(), mediaThumbUrl);
          } catch (IOException e) {
            e.printStackTrace();
          }
          break;
        }
      }
    } else {
      permissionCheck();
    }
  }

  private void init() {
    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);

    permissionCheck();

    binding.btnNext.setOnClickListener(v -> requestThumbUpload());
    binding.homeButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        onBackPressed();
      }
    });
  }

  @Override public void onBackPressed() {
    if (optionsDialog != null && optionsDialog.isShowing()) {
      optionsDialog.dismiss();
    }
    super.onBackPressed();
  }

  private void permissionCheck() {
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    Permissions.Options options = new Permissions.Options()
        .setSettingsDialogMessage(
            mContext.getString(R.string.need_storage_permission))
        .setSettingsDialogTitle(mContext.getString(R.string.permission_warning));

    Permissions.check(this, permissions, null, options, new PermissionHandler() {
      @Override
      public void onGranted() {
        showOptionsDialog(true);
      }
    });
  }

  private void showOptionsDialog(boolean isThumb) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(R.string.create_group_select_action)
        .setItems(R.array.image_picker_choose, (dialog, which) -> {
          switch (which) {
            case 0:
              String[] permissions = {Manifest.permission.CAMERA};
              Permissions.Options options = new Permissions.Options()
                  .setSettingsDialogMessage(
                      mContext.getString(R.string.need_camera_permission))
                  .setSettingsDialogTitle(mContext.getString(R.string.permission_warning));

              Permissions.check(this, permissions, null, options, new PermissionHandler() {
                @Override
                public void onGranted() {
                  dispatchTakePictureIntent(isThumb);
                }
              });

              dismissIgnore = true;
              break;
            case 1:
              Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
              startActivityForResult(pickPhoto, isThumb ? GALLERY_REQUEST : ALBUM_GALLERY_REQUEST);

              dismissIgnore = true;
              break;
            default:
              break;
          }
        });

    optionsDialog = builder.create();
    optionsDialog.setCancelable(true);
    optionsDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
      @Override public void onDismiss(DialogInterface dialog) {
        if (dismissIgnore) {
          return;
        }

        dismissIgnore = false;

        setResult(THUMBNAIL_DISMISSED);
        finish();
      }
    });
    optionsDialog.show();
  }

  @Override protected void onDestroy() {
    if (optionsDialog != null && optionsDialog.isShowing()) {
      optionsDialog.dismiss();
    }
    super.onDestroy();
  }

  private File createImageFile() throws IOException {
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageFileName = "JPEG_" + timeStamp + "_";
    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    File image = File.createTempFile(
        imageFileName,
        ".jpg",
        storageDir
    );

    mCurrentPhotoPath = image.getAbsolutePath();
    return image;
  }

  private void dispatchTakePictureIntent(boolean isThumb) {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
      File photoFile = null;
      try {
        photoFile = createImageFile();
      } catch (IOException ignored) {
      }
      if (photoFile != null) {
        Uri photoURI = FileProvider.getUriForFile(this,
            "notq.dccast.fileprovider",
            photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(takePictureIntent, isThumb ? CAMERA_REQUEST : ALBUM_CAMERA_REQUEST);
      }
    }
  }

  private void loadImage(Uri uri) {
    binding.cropImageView.setImageUriAsync(uri);
  }

  private void loadImage(Bitmap bitmap) {
    binding.cropImageView.setImageBitmap(bitmap);
    //Glide.with(mContext).load(bitmap).into(binding.fullScreenImage);
  }

  private void requestThumbUpload() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    progressDialog.setMessage(getString(R.string.uploading_thumbnail));
    progressDialog.show();

    File thumbFile = persistImage(binding.cropImageView.getCroppedImage(), loginUser.getNickName());
    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), thumbFile);
    String name = Base64.encodeToString(thumbFile.getName().getBytes(), Base64.NO_WRAP);
    MultipartBody.Part thumbImage =
        MultipartBody.Part.createFormData("thumbnail", name, requestFile);

    VodAPIInterface vodAPIInterface = APIClient.getVodClient().create(VodAPIInterface.class);
    Call<JsonObject> call = vodAPIInterface.uploadThumbnail(thumbImage);
    call.enqueue(new Callback<JsonObject>() {
      @Override public void onResponse(@NonNull Call<JsonObject> call,
          @NonNull Response<JsonObject> response) {
        hideLoading();
        JsonObject jsonObject = response.body();
        if (jsonObject != null) {
          if (jsonObject.has("path")) {
            mediaThumbUrl = Url.nodeUrl + jsonObject.get("path").getAsString().substring(6);
          }
        }

        if (LIVE_TYPE == ActivityLiveSettings.LIVE_TYPE_ALBUM) {
          hideLoading();
          String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
          Permissions.Options options = new Permissions.Options()
              .setSettingsDialogMessage(
                  mContext.getString(R.string.need_storage_permission))
              .setSettingsDialogTitle(mContext.getString(R.string.permission_warning));

          Permissions.check(mContext, permissions, null, options, new PermissionHandler() {
            @Override
            public void onGranted() {
              showOptionsDialog(false);
            }
          });
          return;
        }

        sendCreateMediaRequest("", mediaThumbUrl);
      }

      @Override public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
        Toast.makeText(ActivityThumbnailChooser.this, t.getMessage(), Toast.LENGTH_SHORT).show();
        hideLoading();
      }
    });
  }

  private void requestAlbumImageUpload(String liveAlbumImage, String mediaThumbUrl) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    progressDialog.setMessage(getString(R.string.uploading_thumbnail));
    progressDialog.show();

    File albumFile = null;
    try {
      albumFile = new File(getRealPathFromURI(Uri.parse(liveAlbumImage)));
    } catch (Exception ex) {
      albumFile = new File(liveAlbumImage);
    }

    if (albumFile == null) {
      Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
      return;
    }

    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), albumFile);
    String name = Base64.encodeToString(albumFile.getName().getBytes(), Base64.NO_WRAP);
    MultipartBody.Part thumbImage =
        MultipartBody.Part.createFormData("thumbnail", name, requestFile);

    VodAPIInterface vodAPIInterface = APIClient.getVodClient().create(VodAPIInterface.class);
    Call<JsonObject> call = vodAPIInterface.uploadThumbnail(thumbImage);
    call.enqueue(new Callback<JsonObject>() {
      @Override public void onResponse(@NonNull Call<JsonObject> call,
          @NonNull Response<JsonObject> response) {
        hideLoading();
        JsonObject jsonObject = response.body();
        String albumFileUploadedUrl = null;
        if (jsonObject != null) {
          if (jsonObject.has("path")) {
            albumFileUploadedUrl = Url.nodeUrl + jsonObject.get("path").getAsString().substring(6);
          }
        }

        sendCreateMediaRequest(albumFileUploadedUrl, mediaThumbUrl);
      }

      @Override public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
        Toast.makeText(ActivityThumbnailChooser.this, t.getMessage(), Toast.LENGTH_SHORT).show();
        hideLoading();
      }
    });
  }

  @SuppressLint("CommitPrefEdits")
  private void sendCreateMediaRequest(final String albumFileUrl, String thumbnail) {
    if (params != null) {
      try {
        SharedPreferences.Editor editor = getSharedPreferences("DCCAST", MODE_PRIVATE).edit();
        editor.putString("stream_name", params.getString("stream_name"));
        editor.apply();

        if (thumbnail != null) {
          params.put("media_thumbnail", thumbnail);
        }
        if (albumFileUrl != null) {
          params.put("live_vod_id", albumFileUrl);
        }

        if (LIVE_TYPE == ActivityLiveSettings.LIVE_TYPE_CAMERA) {
          params.put("media_type", getString(R.string.item_live_type_camera));
        } else if (LIVE_TYPE == ActivityLiveSettings.LIVE_TYPE_ALBUM) {
          params.put("media_type", getString(R.string.item_live_type_album));
        } else if (LIVE_TYPE == ActivityLiveSettings.LIVE_TYPE_RADIO_MODE) {
          params.put("media_type", getString(R.string.item_live_type_radio_mode));
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }

      RequestBody createLiveRequestBody =
          RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
              String.valueOf(params));

      Call<LiveResponse> call =
          APIClient.getClient().create(LiveAPIInterface.class).createLive(createLiveRequestBody);

      progressDialog.setMessage(getString(R.string.creating_live_media));
      progressDialog.show();

      call.enqueue(new Callback<LiveResponse>() {
        @Override public void onResponse(@NonNull Call<LiveResponse> call,
            @NonNull Response<LiveResponse> response) {
          if (response.body() != null) {
            LiveResponse liveResponse = response.body();
            if (groupId != -1) {
              try {
                requestUploadToGroup(groupId, response.body());
              } catch (JSONException e) {
                e.printStackTrace();
                hideLoading();
                Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
              }
            } else {
              hideLoading();
              Intent intent = new Intent(ActivityThumbnailChooser.this, ActivityLiveStream.class);
              int orientation = liveResponse.getOrientation().equalsIgnoreCase("landscape") ? 1 : 0;
              if (params.has("orientation")) {
                try {
                  orientation =
                      params.getString("orientation").equalsIgnoreCase("landscape") ? 1 : 0;
                } catch (JSONException e) {
                  e.printStackTrace();
                }
              }
              intent.putExtra("orientation", orientation);
              intent.putExtra("videoId", response.body().getId());
              intent.putExtra("liveType", LIVE_TYPE);
              if (LIVE_TYPE == ActivityLiveSettings.LIVE_TYPE_RADIO_MODE) {
                if (liveResponse.getMediaThumbnail() != null) {
                  intent.putExtra("mediaThumbnail",
                      liveResponse.getMediaThumbnail());
                }
              } else if (LIVE_TYPE == ActivityLiveSettings.LIVE_TYPE_ALBUM) {
                if (liveResponse.getLiveVodId() != null) {
                  intent.putExtra("mediaThumbnail",
                      liveResponse.getLiveVodId());
                }
              }
              startActivityForResult(intent, REQUEST_LIVE);
            }
          } else {
            Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            hideLoading();
          }
        }

        @Override public void onFailure(@NonNull Call<LiveResponse> call, @NonNull Throwable t) {
          Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
          hideLoading();
        }
      });
    }
  }

  private void requestUploadToGroup(final int groupId, LiveResponse previousResponse)
      throws JSONException {
    JSONObject requestBodyJson = new JSONObject();
    requestBodyJson.put("media_id", previousResponse.getId());
    requestBodyJson.put("category", previousResponse.getCategory());
    requestBodyJson.put("chat_lock", previousResponse.getLiveChatDisable() ? 1 : 0);
    requestBodyJson.put("vod_rating", 0);

    RequestBody uploadToGroupRequestBody =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            requestBodyJson.toString());

    APIClient.getClient().create(VodAPIInterface.class).uploadToGroupLive(groupId,
        uploadToGroupRequestBody).enqueue(new Callback<ModelGroupVideo>() {
      @Override public void onResponse(@NonNull Call<ModelGroupVideo> call,
          @NonNull Response<ModelGroupVideo> response) {
        hideLoading();

        if (response != null && response.body() != null) {
          ModelGroupVideo groupVideo = response.body();
          Intent intent = new Intent(ActivityThumbnailChooser.this, ActivityLiveStream.class);
          int orientation = groupVideo.getOrientation().equalsIgnoreCase("landscape") ? 1 : 0;
          if (params.has("orientation")) {
            try {
              orientation = params.getString("orientation").equalsIgnoreCase("landscape") ? 1 : 0;
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
          intent.putExtra("videoId", groupVideo.getId());
          intent.putExtra("orientation", orientation);
          if (groupId != -1) {
            intent.putExtra("groupId", groupId);
          }
          if (groupVideo.getMedia() != null) {
            intent.putExtra("mediaThumbnail", groupVideo.getMedia().getMediaThumbnail());
          }
          intent.putExtra("liveType", LIVE_TYPE);
          startActivityForResult(intent, REQUEST_LIVE);
        } else {
          Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_SHORT)
              .show();
        }
      }

      @Override public void onFailure(@NonNull Call<ModelGroupVideo> call, @NonNull Throwable t) {
        hideLoading();
        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void hideLoading() {
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
  }

  private File persistImage(Bitmap bitmap, String name) {
    File filesDir = getFilesDir();
    File imageFile = new File(filesDir, name + ".jpeg");

    OutputStream os;
    try {
      os = new FileOutputStream(imageFile);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
      os.flush();
      os.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return imageFile;
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
