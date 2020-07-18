package notq.dccast.screens.navigation_menu.content.my_channel;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import io.realm.Realm;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.ProfileAPIInterface;
import notq.dccast.api.vod.VodAPIInterface;
import notq.dccast.databinding.ActivityEditProfileBinding;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.profile.ModelProfile;
import notq.dccast.screens.BaseActivity;
import notq.dccast.util.LoginService;
import notq.dccast.util.Url;
import notq.dccast.util.Util;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityEditProfile extends BaseActivity {

  private static final int CAMERA_REQUEST = 1888;
  private static final int GALLERY_REQUEST = 1999;
  private Bitmap uploadFile;
  private Context mContext = this;
  private ActivityEditProfileBinding binding;
  private ProgressDialog progressDialog;
  private Realm realm;
  private String mCurrentPhotoPath;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);

    initToolbar();
    init();

    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
      finish();
      return;
    }

    displayUser(loginUser);
  }

  private void displayUser(ModelUser loginUser) {
    binding.etNickname.setText(loginUser.getNickName());
    binding.etStatus.setText(loginUser.getStateMessage());

    if (loginUser.getUser() != null) {
      binding.etId.setText(String.valueOf(loginUser.getUser().getUsername()));
    }
    binding.etEmail.setText(loginUser.getUser().getEmail());
    binding.etPhone.setText(loginUser.getPhone());

    if (loginUser.getProfileImage() != null) {
      Glide.with(mContext)
          .load(Util.getValidateUrl(loginUser.getProfileImage()))
          .into(binding.ivUser);
    }
  }

  private boolean validateNickname() {
    if (binding.etNickname.getText().toString().isEmpty()) {
      binding.layoutNickname.setErrorEnabled(true);
      String nickName = getString(R.string.validate_nickname);
      binding.etNickname.setError(nickName);
      return false;
    }

    binding.layoutNickname.setErrorEnabled(false);
    binding.etNickname.setError(null);
    return true;
  }

  private boolean validateStatus() {
    if (binding.etStatus.getText().toString().isEmpty()) {
      binding.layoutStatus.setErrorEnabled(true);
      String status = getString(R.string.validate_enter_status);
      binding.etStatus.setError(status);
      return false;
    }

    binding.layoutStatus.setErrorEnabled(false);
    binding.etStatus.setError(null);
    return true;
  }

  private boolean validatePhone() {
    if (binding.etPhone.getText().toString().isEmpty()) {
      binding.layoutPhone.setErrorEnabled(true);
      String phoneNumber = getString(R.string.validate_phone_number);
      binding.etPhone.setError(phoneNumber);
      return false;
    }

    binding.layoutPhone.setErrorEnabled(false);
    binding.etPhone.setError(null);
    return true;
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_edit_profile));
    binding.header.backButton.setOnClickListener(v -> {
      finish();
    });
  }

  private void requestThumbUpload() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    progressDialog.setMessage(getString(R.string.uploading_thumbnail));
    progressDialog.show();

    File thumbFile = persistImage(uploadFile, loginUser.getNickName());
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
        String mediaThumbUrl = null;
        if (jsonObject != null) {
          if (jsonObject.has("path")) {
            mediaThumbUrl = Url.nodeUrl + jsonObject.get("path").getAsString().substring(6);
          }
        }

        saveProfile(mediaThumbUrl);
      }

      @Override public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
        Toast.makeText(ActivityEditProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
        hideLoading();
      }
    });
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

  private void saveProfile(String imgUrl) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    progressDialog.setMessage(getString(R.string.updating_profile));
    showLoading();

    HashMap<String, Object> updateValues = new HashMap<>();
    updateValues.put("user", loginUser.getId());
    updateValues.put("nick_name", binding.etNickname.getText().toString());
    updateValues.put("state_message", binding.etStatus.getText().toString());
    updateValues.put("phone", binding.etPhone.getText().toString());

    updateValues.put("name_certification", loginUser.getNameCertification());
    updateValues.put("auto_login", loginUser.getAutoLogin());
    if (loginUser.getLastNameCertification() != null) {
      updateValues.put("last_name", loginUser.getLastNameCertification());
    }
    if (loginUser.getEmailCertification() != null) {
      updateValues.put("email_certification", loginUser.getEmailCertification());
    }

    updateValues.put("notice_live", loginUser.getNoticeLive());
    updateValues.put("notice_vod", loginUser.getNoticeVod());
    updateValues.put("notice_chat", loginUser.getNoticeChat());
    updateValues.put("notice_notice", loginUser.getNoticeNotice());
    updateValues.put("notice_sound", loginUser.getNoticeSound());
    updateValues.put("notice_vibration", loginUser.getNoticeVibration());
    updateValues.put("phone_certificatioon", loginUser.getPhoneCertification());
    if (loginUser.getPhoneCertificationDate() != null) {
      updateValues.put("phone_certification_date", loginUser.getPhoneCertificationDate());
    }
    updateValues.put("limit_mobile_data", loginUser.getLimitMobileData());
    updateValues.put("stop_recent_view", loginUser.getStopRecentView());
    updateValues.put("stop_recent_search", loginUser.getStopRecentSearch());
    if (loginUser.getSetPass() && loginUser.getPassString() != null) {
      updateValues.put("set_pass", true);
      updateValues.put("pass_string", loginUser.getPassString());
    } else {
      updateValues.put("set_pass", false);
      updateValues.put("pass_string", "");
    }
    updateValues.put("adult_certification", loginUser.getAdultCertification());
    if (loginUser.getAdultCertificationDate() != null) {
      updateValues.put("adult_certification_date", loginUser.getAdultCertificationDate());
    }
    updateValues.put("is_vip", loginUser.getVip());
    updateValues.put("is_vip_active", loginUser.getVipActive());
    if (loginUser.getVipCreateDate() != null) {
      updateValues.put("vip_create_date", loginUser.getVipCreateDate());
    }
    if (imgUrl != null) {
      updateValues.put("profile_image", imgUrl);
    } else if (loginUser.getProfileImage() != null) {
      updateValues.put("profile_image", loginUser.getProfileImage());
    }
    if (loginUser.getProfileOriginalImage() != null) {
      updateValues.put("profile_original_image", loginUser.getProfileOriginalImage());
    }

    ProfileAPIInterface profileAPIInterface =
        APIClient.getClient().create(ProfileAPIInterface.class);
    Call<ModelProfile> call = profileAPIInterface.updateProfile(loginUser.getId(), updateValues);
    call.enqueue(new Callback<ModelProfile>() {
      @Override
      public void onResponse(@NonNull Call<ModelProfile> call,
          @NonNull Response<ModelProfile> response) {
        hideLoading();

        ModelProfile updatedProfile = response.body();

        if (updatedProfile == null) {
          return;
        }

        if (!realm.isInTransaction()) {
          realm.beginTransaction();
        }
        loginUser.setProfile(updatedProfile);
        realm.copyToRealmOrUpdate(loginUser);

        realm.commitTransaction();

        displayUser(loginUser);

        Toast.makeText(mContext, getString(R.string.successfully_updated), Toast.LENGTH_LONG)
            .show();
      }

      @Override
      public void onFailure(@NonNull Call<ModelProfile> call, @NonNull Throwable t) {
        call.cancel();
        Log.e("ActivitySettings", "Error: " + t.getMessage());
        hideLoading();
      }
    });
  }

  private void showLoading() {
    if (progressDialog != null) {
      progressDialog.show();
    }
  }

  private void hideLoading() {
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
  }

  private void init() {
    progressDialog = new ProgressDialog(mContext);
    progressDialog.setCancelable(false);

    realm = Realm.getDefaultInstance();

    binding.saveBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!validateNickname() || !validateStatus() || !validatePhone()) {
          return;
        }

        if (uploadFile != null) {
          requestThumbUpload();
        } else {
          saveProfile(null);
        }
      }
    });

    binding.etNickname.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        validateNickname();
      }
    });

    binding.etStatus.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        validateStatus();
      }
    });

    //binding.etEmail.addTextChangedListener(new TextWatcher() {
    //  @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    //
    //  }
    //
    //  @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
    //
    //  }
    //
    //  @Override public void afterTextChanged(Editable s) {
    //    validateEmail();
    //  }
    //});

    binding.etPhone.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        validatePhone();
      }
    });

    binding.ivChange.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Permissions.Options options = new Permissions.Options()
            .setSettingsDialogMessage(
                mContext.getString(R.string.need_storage_permission))
            .setSettingsDialogTitle(mContext.getString(R.string.permission_warning));

        Permissions.check(mContext, permissions, null, options, new PermissionHandler() {
          @Override
          public void onGranted() {
            showOptionsDialog();
          }
        });
      }
    });

    binding.ivUser.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Permissions.Options options = new Permissions.Options()
            .setSettingsDialogMessage(
                mContext.getString(R.string.need_storage_permission))
            .setSettingsDialogTitle(mContext.getString(R.string.permission_warning));

        Permissions.check(mContext, permissions, null, options, new PermissionHandler() {
          @Override
          public void onGranted() {
            showOptionsDialog();
          }
        });
      }
    });
  }

  private void showOptionsDialog() {
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

              Permissions.check(mContext, permissions, null, options, new PermissionHandler() {
                @Override
                public void onGranted() {
                  dispatchTakePictureIntent();
                }
              });
              break;
            case 1:
              Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
              startActivityForResult(pickPhoto, GALLERY_REQUEST);
              break;
            default:
              break;
          }
        });

    builder.create().show();
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

  private void dispatchTakePictureIntent() {
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
        startActivityForResult(takePictureIntent, CAMERA_REQUEST);
      }
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    Bitmap photo;
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case CAMERA_REQUEST: {
          int targetWidth = 600;
          int targetHeight = 600;
          Bitmap imageBitmap =
              Util.setReducedImageSize(targetWidth, targetHeight, mCurrentPhotoPath);

          if (imageBitmap == null && data != null && data.getExtras() != null) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
          }

          Bitmap rotatedBitmap = null;
          try {
            rotatedBitmap = Util.modifyOrientation(imageBitmap, mCurrentPhotoPath);
          } catch (IOException e) {
            e.printStackTrace();
          }
          loadImage(rotatedBitmap);

          String imageName = String.valueOf(System.currentTimeMillis());
          Util.saveImage(this, rotatedBitmap == null ? imageBitmap : rotatedBitmap,
              imageName, "jpg");

          uploadFile = rotatedBitmap;

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

            uploadFile = rotatedBitmap;

            loadImage(selectedImageUri);
          } catch (IOException e) {
            e.printStackTrace();
          }
          break;
        }
      }
    }
  }

  private void loadImage(Uri uri) {
    Glide.with(mContext).load(uri).into(binding.ivUser);
  }

  private void loadImage(Bitmap bitmap) {
    Glide.with(mContext).load(bitmap).into(binding.ivUser);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
