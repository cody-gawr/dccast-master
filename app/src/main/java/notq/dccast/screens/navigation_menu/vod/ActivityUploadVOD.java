package notq.dccast.screens.navigation_menu.vod;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.vod.VodAPIInterface;
import notq.dccast.databinding.ActivityUploadVodBinding;
import notq.dccast.model.ModelGroupVideo;
import notq.dccast.model.ModelUploadVideoResponse;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.category.CategoryItem;
import notq.dccast.model.vod.GalleryResponse;
import notq.dccast.model.vod.VodResponse;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.video_trimmer.ActivityTrimmer;
import notq.dccast.util.FileUploader;
import notq.dccast.util.LoginService;
import notq.dccast.util.UploadingDialog;
import notq.dccast.util.Url;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityUploadVOD extends BaseActivity {
  private Context mContext = this;
  private ActivityUploadVodBinding binding;

  private Bitmap uploadThumbnail;
  private long timeInMillisec;
  private int groupId = -1;
  private String mediaThumbUrl;
  private String share;
  private Uri uploadUri;

  private CategoryItem selectedCategory;
  private VodAPIInterface vodAPIInterface;
  private ModelVideo modelVideo;
  private VodResponse vodResponse;
  private ArrayList<String> shareTypeList;
  private GalleryResponse galleryResponse;

  private UploadingDialog uploadingDialog;
  private long currentUploadedPercent = 0L;
  private String orientation = "landscape";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_upload_vod);

    vodAPIInterface =
        APIClient.getVodClient().create(VodAPIInterface.class);

    binding.titleInputText.setError(null);
    binding.descriptionInputText.setError(null);

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      modelVideo = (ModelVideo) bundle.getSerializable("video");
      groupId = bundle.getInt("groupId", -1);

      if (bundle.getString(ActivityTrimmer.UPLOAD_IMAGE_URI) != null) {
        uploadUri = Uri.parse(bundle.getString(ActivityTrimmer.UPLOAD_IMAGE_URI));
      }
    }

    if (uploadUri != null) {
      try {
        uploadThumbnail = ThumbnailUtils.createVideoThumbnail(uploadUri.getPath(),
            MediaStore.Images.Thumbnails.MINI_KIND);
        binding.thumbnail.setImageBitmap(uploadThumbnail);
      } catch (Throwable throwable) {
        throwable.printStackTrace();
      }
    }

    if (modelVideo != null) {
      galleryResponse = new GalleryResponse(modelVideo.dcGalleryName, modelVideo.dcGallery);
      binding.thumbnail.setVisibility(View.GONE);
    } else {
      binding.thumbnail.setVisibility(View.VISIBLE);
    }

    initToolbar();
    if (groupId != -1) {
      binding.dropDownLayout.setVisibility(View.GONE);
    } else {
      initDropdown();
    }
    init();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 9999 && resultCode == 9998) {
      if (data != null) {
        galleryResponse =
            (GalleryResponse) data.getExtras().getSerializable("gallery");
        binding.gallery.setText(galleryResponse.getTitle());
      }
    }
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(
        getString(modelVideo == null ? R.string.activity_upload_vod : R.string.activity_edit_vod));
    binding.header.backButton.setOnClickListener(v -> onBackPressed());
  }

  private void init() {
    binding.titleInputText.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @SuppressLint("SetTextI18n") @Override public void afterTextChanged(Editable s) {
        binding.lblCharacterCount.setText(s.length() + "/40");
      }
    });

    binding.postBtn.setText(modelVideo == null ? getString(R.string.vod_upload_post)
        : getString(R.string.vod_upload_update));
    binding.postBtn.setOnClickListener(v -> {
      if (isValid()) {
        if (modelVideo == null) {
          requestVodUpload();
        } else {
          requestUpdate();
        }
      }
    });

    if (modelVideo != null) {
      binding.titleInputText.setText(modelVideo.getTitle());
      binding.descriptionInputText.setText(modelVideo.getExplanation());
      binding.gallery.setText(modelVideo.dcGalleryName);

      if (modelVideo.getMediaCategory() != null) {
        for (int i = 0; i < DCCastApplication.listCategoryItems.size() - 1; i++) {
          if (DCCastApplication.listCategoryItems.get(i)
              .getName()
              .equals(modelVideo.getMediaCategory().getName())) {
            binding.spinnerCategory.setSelection(i);
          }
        }
      }

      if (modelVideo.getKinds() != null && shareTypeList != null) {
        for (int j = 0; j < shareTypeList.size() - 1; j++) {
          String selectedShareType = shareTypeList.get(j);

          if (modelVideo.getKinds().equals(selectedShareType)) {
            binding.spinnerShare.setSelection(j);
          }
        }
      }
    }

    uploadingDialog = new UploadingDialog(mContext, "",
        new UploadingDialog.UploadCancelInterface() {
          @Override public void cancelVideo() {

          }
        });
  }

  private void initDropdown() {
    ArrayAdapter adapterCategory = new ArrayAdapter<>(this, R.layout.sort_spinner_text_view,
        DCCastApplication.listCategoryItems);
    adapterCategory.setDropDownViewResource(R.layout.sort_spinner_drop_text_view);
    binding.spinnerCategory.setAdapter(adapterCategory);
    binding.spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedCategory = DCCastApplication.listCategoryItems.get(i);
      }

      @Override public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });
    binding.spinnerCategory.setSelection(1);

    shareTypeList = new ArrayList<>();
    shareTypeList.add(getString(R.string.vod_share_public));
    shareTypeList.add(getString(R.string.vod_share_private));
    shareTypeList.add(getString(R.string.vod_share_under_19));

    ArrayAdapter adapterShare =
        new ArrayAdapter<>(this, R.layout.sort_spinner_text_view, shareTypeList);
    adapterShare.setDropDownViewResource(R.layout.sort_spinner_drop_text_view);
    binding.spinnerShare.setAdapter(adapterShare);
    binding.spinnerShare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        share = getString(R.string.value_share_type_public);
        switch (i) {
          case 0: {
            share = getString(R.string.value_share_type_public);
            break;
          }
          case 1: {
            share = getString(R.string.value_share_type_private);
            break;
          }
          case 2: {
            share = getString(R.string.value_share_type_19);
            break;
          }
        }

        if (i == 0) {
          binding.layoutGallery.setVisibility(View.VISIBLE);
        } else {
          binding.layoutGallery.setVisibility(View.GONE);
        }
      }

      @Override public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });

    binding.btnSelectGallery.setOnClickListener(view -> {
      Intent intent = new Intent(ActivityUploadVOD.this, ActivitySelectGallery.class);
      startActivityForResult(intent, 9999);
    });
  }

  private boolean isValid() {
    if (Objects.requireNonNull(binding.titleInputText.getText()).toString().isEmpty()) {
      String title = getString(R.string.validate_vod_upload_title);
      binding.titleInputText.setError(title);
      return false;
    } else {
      binding.titleInputText.setError(null);
    }

    //if (Objects.requireNonNull(binding.descriptionInputText.getText()).toString().isEmpty()) {
    //  String vodDescription = getString(R.string.validate_vod_description_title);
    //  binding.descriptionInputText.setError(vodDescription);
    //  return false;
    //}

    if (groupId == -1) {
      if (binding.layoutGallery.getVisibility() == View.VISIBLE) {
        if (galleryResponse == null) {
          Toast.makeText(mContext, getString(R.string.validate_vod_gallery), Toast.LENGTH_SHORT)
              .show();
          return false;
        }
      }
    }

    return true;
  }

  private String getRealPathFromURIPath(Uri contentURI) {
    @SuppressLint("Recycle") Cursor cursor =
        getContentResolver().query(contentURI, null, null, null, null);
    if (cursor == null) {
      return contentURI.getPath();
    } else {
      cursor.moveToFirst();
      int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
      return cursor.getString(idx);
    }
  }

  private void requestUpdate() {
    String update = getString(R.string.progress_updating);
    showLoading(update, 0f);

    JSONObject requestBodyJson = new JSONObject();
    try {
      requestBodyJson.put("category", "VOD");
      if (galleryResponse != null) {
        requestBodyJson.put("dc_gallery", galleryResponse.getId());
        requestBodyJson.put("dc_gallery_name", galleryResponse.getTitle());
      }
      requestBodyJson.put("explanation", binding.descriptionInputText.getText().toString());
      requestBodyJson.put("kinds", share);
      if (selectedCategory != null) {
        if (DCCastApplication.listCategoryItems != null) {
          for (CategoryItem listCategoryItem : DCCastApplication.listCategoryItems) {
            if (listCategoryItem.getId() == selectedCategory.getId()) {
              requestBodyJson.put("media_category", listCategoryItem.getId());
              break;
            }
          }
        }
      } else {
        requestBodyJson.put("media_category", DCCastApplication.listCategoryItems.get(1).getId());
      }
      requestBodyJson.put("title", binding.titleInputText.getText().toString());
      requestBodyJson.put("user", LoginService.getLoginUser().getId());
    } catch (JSONException e) {
      e.printStackTrace();
    }

    RequestBody updateMediaRequestBody =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            requestBodyJson.toString());

    Call<JsonObject> call = APIClient.getClient()
        .create(VodAPIInterface.class)
        .updateMedia(modelVideo.id, updateMediaRequestBody);

    call.enqueue(
        new Callback<JsonObject>() {
          @Override public void onResponse(@NonNull Call<JsonObject> call,
              @NonNull Response<JsonObject> response) {
            hideLoading();
            if (response.body() != null) {
              Intent returnIntent = getIntent();

              Bundle mBundle = new Bundle();
              mBundle.putBoolean("isUpdate", true);

              try {
                String jsonBody = response.body().toString();
                ModelUploadVideoResponse uploadVideoResponse =
                    new Gson().fromJson(jsonBody, ModelUploadVideoResponse.class);
                ModelVideo uploadedVideo = new ModelVideo(uploadVideoResponse);
                mBundle.putSerializable("modelVideo", uploadedVideo);
                returnIntent.putExtras(mBundle);
                setResult(RESULT_OK, returnIntent);
                finish();
              } catch (Exception ignored) {
                Log.e("ActivityUploadVOD", "Error: " + ignored.getMessage());
                Toast.makeText(mContext, R.string.error, Toast.LENGTH_LONG).show();
              }
            }
          }

          @Override
          public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
            hideLoading();
            Toast.makeText(ActivityUploadVOD.this, t.getMessage(), Toast.LENGTH_SHORT).show();
          }
        });

    setUploadDismissListener(new DialogInterface.OnDismissListener() {
      @Override public void onDismiss(DialogInterface dialog) {
        Toast.makeText(mContext, getString(R.string.request_canceled), Toast.LENGTH_SHORT).show();
        call.cancel();
      }
    });
  }

  private void requestVodUpload() {
    if (uploadUri == null) {
      Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
      return;
    }

    showLoading(getString(R.string.preparing), 0f);

    //DownloadProgressListener listener = new DownloadProgressListener() {
    //  @Override
    //  public void update(long bytesRead, long contentLength, boolean done) {
    //    if (done) {
    //      Timber.e("DONE");
    //    } else {
    //      int progress = (int) ((bytesRead * 100) / contentLength);
    //      Timber.e("PROGRESS: " + progress);
    //    }
    //  }
    //};

    //VodAPIInterface vodAPIInterface =
    //    APIClient.getVodClientWithListener(listener).create(VodAPIInterface.class);

    //RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), vodFile);

    File vodFile = new File(Objects.requireNonNull(getRealPathFromURIPath(uploadUri)));

    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
    retriever.setDataSource(ActivityUploadVOD.this, Uri.fromFile(vodFile));
    String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
    String metaOrientation =
        retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
    if (metaOrientation != null) {
      if (metaOrientation.equalsIgnoreCase("90") || metaOrientation.equalsIgnoreCase("270")) {
        orientation = "portrait";
      } else {
        orientation = "landscape";
      }
    }
    timeInMillisec = Long.parseLong(time);
    retriever.release();

    FileUploader fileUploader = new FileUploader();
    File[] filesToUpload = new File[1];
    filesToUpload[0] = vodFile;

    fileUploader.uploadFiles(Url.hostUrl + "/upload", "avatar",
        filesToUpload,
        new FileUploader.FileUploaderCallback() {
          @Override
          public void onError() {
            Timber.e("OnERror:");

            Timber.e("requestVodUpload error: ");

            runOnUiThread(new Runnable() {
              @Override public void run() {
                Toast.makeText(mContext, getString(R.string.request_canceled), Toast.LENGTH_SHORT)
                    .show();

                hideLoading();
              }
            });

            fileUploader.cancelRequest();
          }

          @Override
          public void onFinish(VodResponse[] responses) {
            Timber.e("onFinish:");
            for (int i = 0; i < responses.length; i++) {
              vodResponse = responses[i];
              Timber.e("requestVodUpload onResponse");
              showLoading(getString(R.string.creating_media), 0f);
              requestThumbUpload(vodResponse);

              break;
            }
          }

          @Override
          public void onProgressUpdate(long currentPercent, long totalpercent, long filenumber) {
            //if (started) {
            //  if (beforePosition != currentPercent) {
            if (currentUploadedPercent != currentPercent) {
              Timber.e("current: " + currentPercent + " total: " + totalpercent);
              if (uploadingDialog != null && uploadingDialog.isShowing()) {
                showLoading(getString(R.string.uploading), currentPercent);
              }
            }

            currentUploadedPercent = currentPercent;
            //  }
            //
            //  beforePosition = currentPercent;
            //}
            //if (currentPercent == 99 && !started) {
            //  started = true;
            //}
          }
        });

    //MultipartBody.Part body =
    //    MultipartBody.Part.createFormData("avatar", vodFile.getName(), requestFile);
    //
    //Call<VodResponse> call = vodAPIInterface.uploadVOD(body);
    //call.enqueue(new Callback<VodResponse>() {
    //  @Override public void onResponse(@NonNull Call<VodResponse> call,
    //      @NonNull Response<VodResponse> response) {
    //    VodResponse vodResponse = response.body();
    //    Timber.e("requestVodUpload onResponse");
    //    if (vodResponse != null) {
    //      showLoading(getString(R.string.creating_media), 0f);
    //      requestThumbUpload(vodResponse);
    //    } else {
    //      hideLoading();
    //    }
    //  }
    //
    //  @Override public void onFailure(@NonNull Call<VodResponse> call, @NonNull Throwable t) {
    //    Timber.e("requestVodUpload error: " + t.getMessage());
    //    Toast.makeText(ActivityUploadVOD.this, t.getMessage(), Toast.LENGTH_SHORT).show();
    //    hideLoading();
    //  }
    //});
    //
    setUploadDismissListener(new DialogInterface.OnDismissListener() {
      @Override public void onDismiss(DialogInterface dialog) {
        fileUploader.cancelRequest();
        Toast.makeText(mContext, getString(R.string.request_canceled), Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override protected void onDestroy() {
    hideLoading();
    super.onDestroy();
  }

  private void requestThumbUpload(VodResponse vodResponse) {
    this.vodResponse = vodResponse;
    File thumbFile = persistImage(uploadThumbnail, vodResponse.getOriginalname());
    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), thumbFile);
    String fileName = thumbFile.getName() + "_" + System.currentTimeMillis();
    String name = Base64.encodeToString(fileName.getBytes(), Base64.NO_WRAP);
    MultipartBody.Part thumbImage =
        MultipartBody.Part.createFormData("thumbnail", name, requestFile);

    Call<JsonObject> call = vodAPIInterface.uploadThumbnail(thumbImage);
    call.enqueue(new Callback<JsonObject>() {
      @Override public void onResponse(@NonNull Call<JsonObject> call,
          @NonNull Response<JsonObject> response) {
        JsonObject jsonObject = response.body();
        Timber.e("requestThumbUpload onResponse");
        if (jsonObject != null) {
          if (jsonObject.has("path")) {
            mediaThumbUrl = Url.nodeUrl + jsonObject.get("path").getAsString().substring(6);
          }
        }
        try {
          requestCreateMedia(orientation);
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }

      @Override public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
        Timber.e("requestThumbUpload error: " + t.getMessage());
        Toast.makeText(ActivityUploadVOD.this, t.getMessage(), Toast.LENGTH_SHORT).show();
        hideLoading();
      }
    });

    setUploadDismissListener(new DialogInterface.OnDismissListener() {
      @Override public void onDismiss(DialogInterface dialog) {
        Toast.makeText(mContext, getString(R.string.request_canceled), Toast.LENGTH_SHORT).show();
        call.cancel();
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

  private void requestCreateMedia(String orientation) throws JSONException {
    if (orientation == null || orientation.isEmpty()) {
      orientation = "landscape";
    }
    showLoading(getString(R.string.creating_media), 0f);
    JSONObject requestBodyJson = new JSONObject();
    requestBodyJson.put("category", "VOD");
    if (galleryResponse != null) {
      requestBodyJson.put("dc_gallery", galleryResponse.getId());
      requestBodyJson.put("dc_gallery_name", galleryResponse.getTitle());
    }
    requestBodyJson.put("duration", (int) (timeInMillisec / 1000) % 60);
    requestBodyJson.put("explanation", binding.descriptionInputText.getText().toString());
    requestBodyJson.put("kinds", share);
    requestBodyJson.put("orientation", orientation);
    if (selectedCategory != null) {
      if (DCCastApplication.listCategoryItems != null) {
        for (CategoryItem listCategoryItem : DCCastApplication.listCategoryItems) {
          if (listCategoryItem.getId() == selectedCategory.getId()) {
            requestBodyJson.put("media_category", listCategoryItem.getId());
            break;
          }
        }
      }
    } else {
      requestBodyJson.put("media_category", DCCastApplication.listCategoryItems.get(1).getId());
    }
    requestBodyJson.put("media_id", vodResponse.getFilename());
    requestBodyJson.put("media_thumbnail", mediaThumbUrl);
    requestBodyJson.put("title", binding.titleInputText.getText().toString());
    requestBodyJson.put("user", LoginService.getLoginUser().getId());

    RequestBody createMediaRequestBody =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            requestBodyJson.toString());

    Call<JsonObject> call =
        APIClient.getClient().create(VodAPIInterface.class).createMedia(createMediaRequestBody);

    call.enqueue(
        new Callback<JsonObject>() {
          @Override public void onResponse(@NonNull Call<JsonObject> call,
              @NonNull Response<JsonObject> response) {
            if (response.body() != null) {
              if (groupId != -1) {
                try {
                  requestUploadToGroup(groupId, response.body());
                } catch (JSONException e) {
                  e.printStackTrace();
                }
              } else {
                hideLoading();
                Intent returnIntent = getIntent();

                Bundle mBundle = new Bundle();
                mBundle.putBoolean("isUpdate", false);

                try {
                  String jsonBody = response.body().toString();
                  ModelUploadVideoResponse uploadVideoResponse =
                      new Gson().fromJson(jsonBody, ModelUploadVideoResponse.class);
                  ModelVideo uploadedVideo = new ModelVideo(uploadVideoResponse);
                  mBundle.putSerializable("modelVideo", uploadedVideo);

                  returnIntent.putExtras(mBundle);
                  setResult(RESULT_OK, returnIntent);
                  finish();
                } catch (Exception ignored) {
                  Log.e("ActivityUploadVOD", "Error: " + ignored.getMessage());
                  Toast.makeText(mContext, R.string.error, Toast.LENGTH_LONG).show();
                }
              }
            }
          }

          @Override public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
            hideLoading();
            Toast.makeText(ActivityUploadVOD.this, t.getMessage(), Toast.LENGTH_SHORT).show();
          }
        });

    setUploadDismissListener(new DialogInterface.OnDismissListener() {
      @Override public void onDismiss(DialogInterface dialog) {
        Toast.makeText(mContext, getString(R.string.request_canceled), Toast.LENGTH_SHORT).show();
        call.cancel();
      }
    });
  }

  private void requestUploadToGroup(int groupId, JsonObject previousBodyJson)
      throws JSONException {
    JSONObject requestBodyJson = new JSONObject();
    requestBodyJson.put("media_id", previousBodyJson.get("id").getAsInt());
    requestBodyJson.put("category", previousBodyJson.get("category").getAsString());
    requestBodyJson.put("chat_lock", 0);
    requestBodyJson.put("vod_rating", 0);

    RequestBody uploadToGroupRequestBody =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            requestBodyJson.toString());

    APIClient.getClient().create(VodAPIInterface.class).uploadToGroup(groupId,
        uploadToGroupRequestBody).enqueue(new Callback<ModelGroupVideo>() {
      @Override public void onResponse(@NonNull Call<ModelGroupVideo> call,
          @NonNull Response<ModelGroupVideo> response) {
        hideLoading();

        if (response != null && response.body() != null) {
          ModelGroupVideo result = response.body();
          Intent returnIntent = getIntent();

          Bundle mBundle = new Bundle();
          mBundle.putBoolean("isUpdate", false);
          if (result.getMedia() != null) {
            mBundle.putSerializable("modelVideo", result.getMedia());
          }
          returnIntent.putExtras(mBundle);
          setResult(RESULT_OK, returnIntent);
          finish();
        } else {
          Toast.makeText(ActivityUploadVOD.this, getString(R.string.error), Toast.LENGTH_SHORT)
              .show();
        }
      }

      @Override public void onFailure(@NonNull Call<ModelGroupVideo> call, @NonNull Throwable t) {
        hideLoading();
        Toast.makeText(ActivityUploadVOD.this, t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void showLoading(String title, float percent) {
    if (uploadingDialog != null && uploadingDialog.isShowing()) {
      uploadingDialog.setTitle(title);
      uploadingDialog.setUploadingProgress(percent);
      return;
    }

    uploadingDialog.setTitle(title);
    uploadingDialog.setUploadingProgress(percent);
    uploadingDialog.showDialog();
  }

  private void setUploadDismissListener(DialogInterface.OnDismissListener listener) {
    if (uploadingDialog != null) {
      uploadingDialog.setOnDismissListener(listener);
    }
  }

  private void hideLoading() {
    if (uploadingDialog != null) {
      uploadingDialog.dismiss();
    }
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
