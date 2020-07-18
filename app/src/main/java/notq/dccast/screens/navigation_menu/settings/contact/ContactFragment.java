package notq.dccast.screens.navigation_menu.settings.contact;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.google.gson.JsonObject;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.ProfileAPIInterface;
import notq.dccast.api.vod.VodAPIInterface;
import notq.dccast.databinding.FragmentContactBinding;
import notq.dccast.model.user.ModelUser;
import notq.dccast.util.DialogHelper;
import notq.dccast.util.LoginService;
import notq.dccast.util.Url;
import notq.dccast.util.Util;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

public class ContactFragment extends Fragment {

  private static final int CAMERA_REQUEST = 1888;
  private static final int GALLERY_REQUEST = 1999;
  private FragmentContactBinding binding;
  private String type = "";
  private String mCurrentPhotoPath;
  private Bitmap uploadFile;
  private ProgressDialog progressDialog;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);

    init();

    return binding.getRoot();
  }

  private void init() {
    progressDialog = new ProgressDialog(getActivity());
    progressDialog.setCancelable(false);

    ArrayList<String> typeList = new ArrayList<>();

    typeList.add(getString(R.string.contact_type_1));
    typeList.add(getString(R.string.contact_type_2));
    typeList.add(getString(R.string.contact_type_3));
    typeList.add(getString(R.string.contact_type_4));
    typeList.add(getString(R.string.contact_type_5));

    ArrayAdapter adapterShare =
        new ArrayAdapter<>(getActivity(), R.layout.sort_spinner_text_view, typeList);
    adapterShare.setDropDownViewResource(R.layout.sort_spinner_drop_text_view);
    binding.spinnerType.setAdapter(adapterShare);
    binding.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        type = typeList.get(i);
      }

      @Override public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });

    binding.spinnerType.setSelection(0);

    binding.btnChooseImage.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Permissions.Options options = new Permissions.Options()
            .setSettingsDialogMessage(
                getString(R.string.need_storage_permission))
            .setSettingsDialogTitle(getString(R.string.permission_warning));

        Permissions.check(getActivity(), permissions, null, options, new PermissionHandler() {
          @Override
          public void onGranted() {
            showOptionsDialog();
          }
        });
      }
    });

    binding.btnSend.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!validateTitle() || !validateType() || !validateInformation()) {
          return;
        }

        DialogHelper.showAlertDialog(getActivity(), getString(R.string.dialog_contact_confirm), "",
            getString(
                R.string.contact_confirm_yes),
            new DialogInterface.OnClickListener() {
              @Override public void onClick(DialogInterface dialog, int which) {
                if (uploadFile != null) {
                  requestThumbUpload();
                } else {
                  sendRequest(null);
                }
              }
            }, getString(R.string.contact_confirm_no), null);
      }
    });
  }

  private void requestThumbUpload() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    progressDialog.setMessage(getString(R.string.uploading_thumbnail));
    showLoading();

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

        sendRequest(mediaThumbUrl);
      }

      @Override public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
        call.cancel();
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
        hideLoading();
      }
    });
  }

  private void sendRequest(String imgPath) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    String title = binding.etTitle.getText().toString();
    String information = binding.etInformation.getText().toString();

    APIClient.getClient()
        .create(ProfileAPIInterface.class)
        .sendContact(title, type, information, loginUser.getId(), "", imgPath)
        .enqueue(new Callback<JsonObject>() {
          @Override public void onResponse(@NonNull Call<JsonObject> call,
              @NonNull Response<JsonObject> response) {
            hideLoading();

            if (response != null && response.body() != null) {
              JsonObject contactUs = response.body();
              Toast.makeText(getActivity(), getString(R.string.contact_success),
                  Toast.LENGTH_LONG).show();
            } else {
              Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT)
                  .show();
            }
          }

          @Override
          public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
            hideLoading();
            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
          }
        });
  }

  private File persistImage(Bitmap bitmap, String name) {
    File filesDir = getActivity().getFilesDir();
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

  private void showOptionsDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle(R.string.create_group_select_action)
        .setItems(R.array.image_picker_choose, (dialog, which) -> {
          switch (which) {
            case 0:
              String[] permissions = {Manifest.permission.CAMERA};
              Permissions.Options options = new Permissions.Options()
                  .setSettingsDialogMessage(
                      getString(R.string.need_camera_permission))
                  .setSettingsDialogTitle(getString(R.string.permission_warning));

              Permissions.check(getActivity(), permissions, null, options, new PermissionHandler() {
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
    File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
      File photoFile = null;
      try {
        photoFile = createImageFile();
      } catch (IOException ignored) {
      }
      if (photoFile != null) {
        Uri photoURI = FileProvider.getUriForFile(getActivity(),
            "notq.dccast.fileprovider",
            photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(takePictureIntent, CAMERA_REQUEST);
      }
    }
  }

  @Override public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Bitmap photo;
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

          String imageName = String.valueOf(System.currentTimeMillis());
          Util.saveImage(getActivity(), rotatedBitmap == null ? imageBitmap : rotatedBitmap,
              imageName, "jpg");

          uploadFile = rotatedBitmap;
          File file = new File(mCurrentPhotoPath);
          binding.lblImg.setText(file.getPath());

          break;
        }
        case GALLERY_REQUEST: {
          try {
            Uri selectedImageUri = data.getData();
            String selectedImagePath = Util.getPath(getActivity(), selectedImageUri);
            photo = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                data.getData());
            Bitmap rotatedBitmap = Util.modifyOrientation(photo, selectedImagePath);
            String imageName = String.valueOf(System.currentTimeMillis());
            Util.saveImage(getActivity(), rotatedBitmap, imageName, "jpg");

            uploadFile = rotatedBitmap;
            File file = new File(selectedImagePath);
            binding.lblImg.setText(file.getPath());
          } catch (IOException e) {
            e.printStackTrace();
          }
          break;
        }
      }
    }
  }

  private boolean validateTitle() {
    if (binding.etTitle.getText().toString().isEmpty()) {
      binding.layoutTitle.setErrorEnabled(true);
      String title = getString(R.string.validate_contact_title);
      binding.etTitle.setError(title);
      return false;
    }

    binding.layoutTitle.setErrorEnabled(false);
    binding.etTitle.setError(null);
    return true;
  }

  private boolean validateInformation() {
    if (binding.etInformation.getText().toString().isEmpty()) {
      binding.layoutInformation.setErrorEnabled(true);
      String title = getString(R.string.validate_contact_information);
      binding.etInformation.setError(title);
      return false;
    }

    binding.layoutInformation.setErrorEnabled(false);
    binding.etInformation.setError(null);
    return true;
  }

  private boolean validateType() {
    if (type == null || type.isEmpty()) {
      Toast.makeText(getActivity(), getString(R.string.validate_contact_type), Toast.LENGTH_LONG)
          .show();
      return false;
    }
    return true;
  }
}
