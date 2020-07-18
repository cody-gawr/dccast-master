package notq.dccast.screens.navigation_menu.cast.group.create_group;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.databinding.ActivityCreateGroupBinding;
import notq.dccast.model.group.ModelGroup;
import notq.dccast.model.user.ModelResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.navigation_menu.cast.group.add_people.ActivityAddPeople;
import notq.dccast.screens.navigation_menu.cast.interfaces.MemberListener;
import notq.dccast.util.Constants;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityCreateGroup extends BaseActivity {

  private static final int CAMERA_REQUEST = 1888;
  private static final int GALLERY_REQUEST = 1999;
  private final int REQUEST_ADD_PEOPLE = 2001;
  private Context mContext = this;
  private ActivityCreateGroupBinding binding;
  private AdapterMembers adapterMembers;
  private ProgressDialog progressDialog;
  private ModelGroup modelGroup;
  private boolean isUpdate = false;
  private File uploadFile;
  private String mCurrentPhotoPath;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_create_group);

    modelGroup = (ModelGroup) getIntent().getSerializableExtra(Constants.GROUP_DETAIL);

    if (modelGroup != null) {
      isUpdate = true;
    } else {
      modelGroup = new ModelGroup();
    }

    initToolbar();
    initRecyclerView();
    init();

    if (isUpdate) {
      String name = modelGroup.getName();
      String description = modelGroup.getMessage();

      binding.nameInputText.setText(name);
      binding.descriptionInputText.setText(description);

      binding.header.lblHeader.setText(getString(R.string.activity_update_group));
      binding.header.lblActionBtn.setText(getString(R.string.group_detail_save));
      if (modelGroup.getProfileImg() != null && !modelGroup.getProfileImg().isEmpty()) {
        binding.layoutLoadImage.setVisibility(View.VISIBLE);
        Glide.with(mContext)
            .load(Util.getPhotoUrl(modelGroup.getProfileImg()))
            .listener(new RequestListener<Drawable>() {
              @Override public boolean onLoadFailed(@Nullable GlideException e, Object model,
                  Target<Drawable> target, boolean isFirstResource) {
                binding.layoutLoadImage.setVisibility(View.GONE);
                binding.ivProfileImg.setImageResource(R.drawable.ic_upload_image_choose);
                return false;
              }

              @Override public boolean onResourceReady(Drawable resource, Object model,
                  Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                binding.layoutLoadImage.setVisibility(View.GONE);
                return false;
              }
            })
            .into(binding.ivProfileImg);
      }
      binding.header.lblActionBtn.setVisibility(View.VISIBLE);
      binding.header.lblActionBtn.setOnClickListener(v -> binding.createGroupBtn.performClick());
      binding.createGroupBtn.setVisibility(View.GONE);

      if (modelGroup.getMembers() != null) {
        ArrayList<ModelUser> members = new ArrayList<>();
        for (Integer member : modelGroup.getMembers()) {
          ModelUser user = new ModelUser();
          user.setId(member);
          user.setNeedFetchProfile(true);
          members.add(user);
        }
        adapterMembers.setMembers(members);
      }
      binding.leaveGroup.setVisibility(View.VISIBLE);
      binding.leaveGroup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
          builder.setTitle(getString(R.string.leave_group_confirm_title));
          builder.setMessage(getString(R.string.leave_group_confirm_body));
          builder.setCancelable(true);

          builder.setPositiveButton(
              getString(R.string.leave_group_confirm_yes),
              (dialog, id) -> {
                dialog.cancel();

                ModelUser loginUser = LoginService.getLoginUser();
                if (loginUser != null) {
                  leaveGroup(modelGroup.getId(), loginUser.getId());
                }
              });

          builder.setNegativeButton(
              getString(R.string.leave_group_confirm_no),
              (dialog, id) -> dialog.cancel());

          AlertDialog alert = builder.create();
          alert.show();
        }
      });
    } else {
      binding.createGroupBtn.setVisibility(View.VISIBLE);
      binding.leaveGroup.setVisibility(View.INVISIBLE);
    }
  }

  private void leaveGroup(int groupId, int memberId) {
    progressDialog.show();

    Call<ModelResult> call =
        APIClient.getClient().create(CastListAPIInterface.class).leaveGroup(groupId, memberId);
    call.enqueue(new Callback<ModelResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelResult> call,
          @NonNull Response<ModelResult> response) {
        progressDialog.dismiss();

        ModelResult result = response.body();
        if (result == null || !result.result) {
          return;
        }

        if (result.isResult()) {
          setResult(Constants.GROUP_LEAVED);
          finish();
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelResult> call, @NonNull Throwable t) {
        call.cancel();

        progressDialog.dismiss();
      }
    });
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_create_group));
    binding.header.backButton.setOnClickListener(v -> {
      onBackPressed();
    });
  }

  private void initRecyclerView() {
    adapterMembers = new AdapterMembers(mContext, new MemberListener() {
      @Override
      public void onItemSelected(int position) {

      }
    });
    adapterMembers.setNeedShowLoader(false);

    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.setAdapter(adapterMembers);
  }

  private void init() {
    progressDialog = new ProgressDialog(mContext);
    progressDialog.setCancelable(false);

    binding.lblAddPeople.setOnClickListener(v -> {
      Intent addPeopleIntent = new Intent(mContext, ActivityAddPeople.class);
      startActivityForResult(addPeopleIntent, REQUEST_ADD_PEOPLE);
    });

    binding.createGroupBtn.setOnClickListener(v -> {
      if (!validateName()) {
        return;
      }
      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser == null) {
        return;
      }

      progressDialog.show();

      final String name = Objects.requireNonNull(binding.nameInputText.getText()).toString();
      final String description =
          Objects.requireNonNull(binding.descriptionInputText.getText()).toString();

      RequestBody rqName = RequestBody.create(MediaType.parse("text/plain"), name);
      RequestBody rqMessage = RequestBody.create(MediaType.parse("text/plain"), description);
      RequestBody rqContactPerson =
          RequestBody.create(MediaType.parse("text/plain"), String.valueOf(loginUser.getId()));

      ArrayList<Integer> ids = new ArrayList<>();
      for (ModelUser member : adapterMembers.getMembers()) {
        ids.add(member.getId());
      }
      ids.add(loginUser.getId());

      MultipartBody.Part profileImg = null;

      if (uploadFile != null) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), uploadFile);
        profileImg =
            MultipartBody.Part.createFormData("profile_img", uploadFile.getName(), requestFile);
      }

      CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);
      Call<ModelGroup> call = null;

      if (isUpdate) {
        call = apiInterface.updateGroup(modelGroup.getId(), rqName, rqMessage, rqContactPerson, ids,
            profileImg);
      } else {
        call = apiInterface.createGroup(rqName, rqMessage, rqContactPerson, ids, profileImg);
      }

      call.enqueue(new Callback<ModelGroup>() {
        @Override
        public void onResponse(@NonNull Call<ModelGroup> call,
            @NonNull Response<ModelGroup> response) {
          progressDialog.dismiss();

          ModelGroup group = response.body();

          if (group == null) {
            return;
          }

          Intent intent = getIntent();
          intent.putExtra(Constants.GROUP_DETAIL, group);
          setResult(RESULT_OK, intent);
          finish();
        }

        @Override
        public void onFailure(@NonNull Call<ModelGroup> call, @NonNull Throwable t) {
          call.cancel();

          progressDialog.dismiss();
          Toast.makeText(mContext, getString(R.string.error_with, t.getMessage()),
              Toast.LENGTH_LONG)
              .show();
        }
      });
    });

    binding.ivProfileImg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Permissions.Options options = new Permissions.Options()
            .setSettingsDialogMessage(
                mContext.getString(R.string.need_camera_permission))
            .setSettingsDialogTitle(mContext.getString(R.string.permission_warning));

        Permissions.check(mContext, permissions, null, options, new PermissionHandler() {
          @Override
          public void onGranted() {
            showOptionsDialog();
          }
        });
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

  public Uri getImageUri(Context inContext, Bitmap inImage) {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    String path =
        MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
    return Uri.parse(path);
  }

  public String getRealPathFromURI(Uri uri) {
    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
    if (cursor == null) {
      return null;
    }
    cursor.moveToFirst();
    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
    return cursor.getString(idx);
  }

  private boolean validateName() {
    String name = Objects.requireNonNull(binding.nameInputText.getText()).toString();
    if (name.isEmpty()) {
      String groupName = getString(R.string.validate_enter_group_name);
      binding.nameInputLayout.setError(groupName);
      binding.nameInputLayout.setErrorEnabled(true);
      return false;
    }

    binding.nameInputLayout.setError(null);
    binding.nameInputLayout.setErrorEnabled(false);
    return true;
  }

  @Override
  protected void onDestroy() {
    if (progressDialog != null) {
      progressDialog.dismiss();
    }

    super.onDestroy();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    Bitmap photo;
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case REQUEST_ADD_PEOPLE: {
          if (data == null) {
            return;
          }
          ArrayList<ModelUser> allMembers = new ArrayList<>();
          if (adapterMembers.getMembersCount() > 0) {
            allMembers.addAll(adapterMembers.getMembers());
          }

          ArrayList<ModelUser> addedUsers =
              (ArrayList<ModelUser>) data.getSerializableExtra(Constants.GROUP_ADDED_MEMBERS);
          if (addedUsers != null) {
            for (ModelUser addedUser : addedUsers) {
              if (!adapterMembers.containsId(addedUser.getId())) {
                allMembers.add(addedUser);
              }
            }
          }

          adapterMembers.setMembers(allMembers);
          break;
        }
        case CAMERA_REQUEST: {

          int targetWidth = 600;
          int targetHeight = 600;
          Bitmap imageBitmap =
              Util.setReducedImageSize(targetWidth, targetHeight, mCurrentPhotoPath);

          if (imageBitmap == null && data != null && data.getExtras() != null) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
          }

          Bitmap rotatedBitmap = Util.rotateImageIfRequired(imageBitmap, mCurrentPhotoPath);
          loadImage(rotatedBitmap);

          String imageName = String.valueOf(System.currentTimeMillis());
          Util.saveImage(this, rotatedBitmap == null ? imageBitmap : rotatedBitmap,
              imageName, "jpg");
          uploadFile = getFileStreamPath(imageName + ".jpg");
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

            uploadFile = getFileStreamPath(imageName + ".jpg");

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
    Glide.with(mContext).load(uri).into(binding.ivProfileImg);
  }

  private void loadImage(Bitmap bitmap) {
    Glide.with(mContext).load(bitmap).into(binding.ivProfileImg);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
