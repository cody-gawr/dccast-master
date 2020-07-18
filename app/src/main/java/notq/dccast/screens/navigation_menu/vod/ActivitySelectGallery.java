package notq.dccast.screens.navigation_menu.vod;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.vod.VodAPIInterface;
import notq.dccast.databinding.ActivitySelectGalleryBinding;
import notq.dccast.model.vod.GalleryResponse;
import notq.dccast.screens.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivitySelectGallery extends BaseActivity
    implements View.OnClickListener, GalleryCallback {
  private ActivitySelectGalleryBinding binding;
  private ProgressDialog mProgressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_select_gallery);
    init();
  }

  private void init() {
    binding.toolbarContainer.btnBack.setOnClickListener(this);
    binding.toolbarContainer.searchField.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override public void afterTextChanged(Editable editable) {
        if (editable.length() > 0) {
          if (Objects.requireNonNull(binding.toolbarContainer.searchField.getText()).length() > 0) {
            binding.toolbarContainer.btnClear.setVisibility(View.VISIBLE);
          } else {
            binding.toolbarContainer.btnClear.setVisibility(View.GONE);
          }
        }
      }
    });

    binding.toolbarContainer.btnClear.setOnClickListener(this);
    binding.toolbarContainer.searchField.setOnEditorActionListener(
        (textView, actionId, keyEvent) -> {
          if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (isValid()) {
              searchGalleryRequest();
            }

            return true;
          }

          return false;
        });

    binding.galleryRecyclerview.setLayoutManager(
        new LinearLayoutManager(ActivitySelectGallery.this, RecyclerView.VERTICAL, false));
  }

  private void searchGalleryRequest() {
    if (mProgressDialog == null) {
      mProgressDialog = new ProgressDialog(this);
      mProgressDialog.setCancelable(false);
      mProgressDialog.setMessage(getString(R.string.please_wait));
      mProgressDialog.setOnDismissListener(
          dialogInterface -> DCCastApplication.utils.hideKeyboard(ActivitySelectGallery.this));
    }

    mProgressDialog.show();

    APIClient.getMDCIdClient()
        .create(VodAPIInterface.class)
        .searchGallery(DCCastApplication.appId,
            binding.toolbarContainer.searchField.getText().toString(), "gall_name")
        .enqueue(new Callback<JsonObject>() {
          @Override public void onResponse(@NonNull Call<JsonObject> call,
              @NonNull Response<JsonObject> response) {
            JsonObject jsonObject = response.body();
            GalleryItemWrapper mainGalleryItemWrapper = null;
            GalleryItemWrapper minorGalleryItemWrapper = null;
            List<GalleryResponse> mainGalleryItemList;
            List<GalleryResponse> minorGalleryItemList;

            if (jsonObject != null) {
              if (jsonObject.has("main_gall")) {
                JsonArray mainGalleryList = jsonObject.get("main_gall").getAsJsonArray();

                if (mainGalleryList != null && mainGalleryList.size() > 0) {
                  mainGalleryItemWrapper = new GalleryItemWrapper();
                  mainGalleryItemList = new ArrayList<>();
                  for (int i = 0; i < mainGalleryList.size(); i++) {
                    JsonObject galleryItem = mainGalleryList.get(i).getAsJsonObject();
                    GalleryResponse gallery =
                        new GalleryResponse(galleryItem.get("title").getAsString(),
                            galleryItem.get("id").getAsString());
                    mainGalleryItemList.add(gallery);
                  }

                  mainGalleryItemWrapper.setSectionTitle("갤러리");
                  mainGalleryItemWrapper.setItemList(mainGalleryItemList);
                }
              }

              if (jsonObject.has("minor_gall")) {
                JsonArray minorGalleryList = jsonObject.get("minor_gall").getAsJsonArray();

                if (minorGalleryList != null && minorGalleryList.size() > 0) {
                  minorGalleryItemWrapper = new GalleryItemWrapper();
                  minorGalleryItemList = new ArrayList<>();
                  for (int i = 0; i < minorGalleryList.size(); i++) {
                    JsonObject galleryItem = minorGalleryList.get(i).getAsJsonObject();
                    GalleryResponse gallery =
                        new GalleryResponse(galleryItem.get("title").getAsString(),
                            galleryItem.get("id").getAsString());
                    minorGalleryItemList.add(gallery);
                  }

                  minorGalleryItemWrapper.setSectionTitle("마이너 갤러리");
                  minorGalleryItemWrapper.setItemList(minorGalleryItemList);
                }
              }
            }

            if (mainGalleryItemWrapper == null && minorGalleryItemWrapper == null) {

            } else {
              GalleryAdapter adapter =
                  new GalleryAdapter(ActivitySelectGallery.this, mainGalleryItemWrapper,
                      minorGalleryItemWrapper);
              binding.galleryRecyclerview.setAdapter(adapter);
            }
            mProgressDialog.dismiss();
          }

          @Override public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
            Toast.makeText(ActivitySelectGallery.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            mProgressDialog.dismiss();
          }
        });
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_clear: {
        binding.toolbarContainer.searchField.getText().clear();
        break;
      }
      case R.id.btn_back: {
        onBackPressed();
        break;
      }
    }
  }

  private boolean isValid() {
    if (Objects.requireNonNull(binding.toolbarContainer.searchField.getText()).length() == 0) {
      Toast.makeText(getApplicationContext(), getString(R.string.validate_search_value),
          Toast.LENGTH_SHORT).show();
      return false;
    } else {
      return true;
    }
  }

  @Override public void onGalleryItemClicked(GalleryResponse gallery) {
    Intent intent = new Intent();
    Bundle bundle = new Bundle();
    bundle.putSerializable("gallery", gallery);
    intent.putExtras(bundle);

    setResult(9998, intent);
    finish();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
