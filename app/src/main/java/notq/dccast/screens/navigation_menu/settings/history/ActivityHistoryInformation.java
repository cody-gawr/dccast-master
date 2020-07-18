package notq.dccast.screens.navigation_menu.settings.history;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import io.realm.Realm;
import java.util.HashMap;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.ProfileAPIInterface;
import notq.dccast.databinding.ActivityHistoryInformationBinding;
import notq.dccast.model.ModelSearchHistory;
import notq.dccast.model.user.ModelResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.profile.ModelProfile;
import notq.dccast.screens.BaseActivity;
import notq.dccast.util.DialogHelper;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityHistoryInformation extends BaseActivity {

  private Context mContext = this;
  private ActivityHistoryInformationBinding binding;
  private ModelUser loginUser;
  private ProfileAPIInterface profileAPIInterface;
  private ProgressDialog progressDialog;
  private Realm realm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_history_information);

    initToolbar();
    initApi();

    loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
      finish();
      return;
    }

    displayUser();
    init();
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_history_information));
    binding.header.backButton.setOnClickListener(v -> {
      onBackPressed();
    });
  }

  private void initApi() {
    profileAPIInterface = APIClient.getClient().create(ProfileAPIInterface.class);
    realm = Realm.getDefaultInstance();

    progressDialog = new ProgressDialog(mContext);
    progressDialog.setCancelable(false);
  }

  private void init() {
    binding.clearLiveHistory.setOnClickListener(v -> {
      DialogHelper.showAlertDialog(mContext, getString(R.string.clear_live_history_title),
          getString(R.string.clear_live_history_body), getString(R.string.clear_live_history_yes),
          (dialog, which) -> {

            progressDialog.show();
            Call<ModelResult> call =
                profileAPIInterface.deleteRecentHistory(loginUser.getId(), "Live");
            call.enqueue(new Callback<ModelResult>() {
              @Override
              public void onResponse(@NonNull Call<ModelResult> call,
                  @NonNull Response<ModelResult> response) {
                progressDialog.dismiss();

                ModelResult result = response.body();

                if (result == null || !result.result) {
                  return;
                }

                Toast.makeText(mContext, getString(R.string.live_history_cleared),
                    Toast.LENGTH_LONG).show();
              }

              @Override
              public void onFailure(@NonNull Call<ModelResult> call, @NonNull Throwable t) {
                call.cancel();
                Log.e("ActivityHistory", "Error: " + t.getMessage());
                progressDialog.dismiss();
              }
            });
          }, getString(R.string.clear_live_history_no), null);
    });

    binding.clearVodHistory.setOnClickListener(v -> {
      DialogHelper.showAlertDialog(mContext, getString(R.string.clear_vod_history_title),
          getString(R.string.clear_vod_history_body), getString(R.string.clear_vod_history_yes),
          (dialog, which) -> {
            progressDialog.show();
            Call<ModelResult> call =
                profileAPIInterface.deleteRecentHistory(loginUser.getId(), "VOD");
            call.enqueue(new Callback<ModelResult>() {
              @Override
              public void onResponse(@NonNull Call<ModelResult> call,
                  @NonNull Response<ModelResult> response) {
                progressDialog.dismiss();

                ModelResult result = response.body();

                if (result == null || !result.result) {
                  return;
                }

                Toast.makeText(mContext, getString(R.string.vod_history_cleared), Toast.LENGTH_LONG)
                    .show();
              }

              @Override
              public void onFailure(@NonNull Call<ModelResult> call, @NonNull Throwable t) {
                call.cancel();
                Log.e("ActivityHistory", "Error: " + t.getMessage());
                progressDialog.dismiss();
              }
            });
          }, getString(R.string.clear_vod_history_no), null);
    });

    binding.clearSearchHistory.setOnClickListener(v -> {
      DialogHelper.showAlertDialog(mContext, getString(R.string.clear_search_history_title),
          getString(R.string.clear_search_history_body),
          getString(R.string.clear_search_history_yes),
          (dialog, which) -> {
            if (!realm.isInTransaction()) {
              realm.beginTransaction();
            }

            realm.delete(ModelSearchHistory.class);

            realm.commitTransaction();

            Toast.makeText(mContext, getString(R.string.search_history_cleared), Toast.LENGTH_LONG)
                .show();
          }, getString(R.string.clear_search_history_no), null);
    });

    binding.switchRecentHistory.setOnClickListener(v -> {
      binding.switchRecentHistory.toggleChecked();
    });

    binding.switchRecentHistory.setCheckedChangeListener((view, isChecked) -> {
      if (!realm.isInTransaction()) {
        realm.beginTransaction();
      }
      loginUser.setStopRecentSearch(isChecked);
      realm.commitTransaction();

      updateProfile();
    });

    binding.switchRecentView.setOnClickListener(v -> {
      binding.switchRecentView.toggleChecked();
    });

    binding.switchRecentView.setCheckedChangeListener((view, isChecked) -> {
      if (!realm.isInTransaction()) {
        realm.beginTransaction();
      }
      loginUser.setStopRecentView(isChecked);
      realm.commitTransaction();

      updateProfile();
    });
  }

  private void displayUser() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser != null) {
      binding.switchRecentHistory.setChecked(loginUser.getStopRecentSearch());
      binding.switchRecentView.setChecked(loginUser.getStopRecentView());
    }
  }

  private void updateProfile() {
    if (loginUser == null) {
      return;
    }
    progressDialog.show();

    HashMap<String, Object> updateValues = new HashMap<>();
    updateValues.put("user", loginUser.getId());
    updateValues.put("name_certification", loginUser.getNameCertification());
    updateValues.put("auto_login", loginUser.getAutoLogin());
    if (loginUser.getNickName() != null) {
      updateValues.put("nick_name", loginUser.getNickName());
    }
    if (loginUser.getLastNameCertification() != null) {
      updateValues.put("last_name", loginUser.getLastNameCertification());
    }
    if (loginUser.getEmailCertification() != null) {
      updateValues.put("email_certification", loginUser.getEmailCertification());
    }
    if (loginUser.getStateMessage() != null) {
      updateValues.put("state_message", loginUser.getStateMessage());
    }

    updateValues.put("notice_live", loginUser.getNoticeLive());
    updateValues.put("notice_vod", loginUser.getNoticeVod());
    updateValues.put("notice_chat", loginUser.getNoticeChat());
    updateValues.put("notice_notice", loginUser.getNoticeNotice());
    updateValues.put("notice_sound", loginUser.getNoticeSound());
    updateValues.put("notice_vibration", loginUser.getNoticeVibration());
    if (loginUser.getPhone() != null) {
      updateValues.put("phone", loginUser.getPhone());
    }
    updateValues.put("phone_certificatioon", loginUser.getPhoneCertification());
    if (loginUser.getPhoneCertificationDate() != null) {
      updateValues.put("phone_certification_date", loginUser.getPhoneCertificationDate());
    }
    updateValues.put("limit_mobile_data", loginUser.getLimitMobileData());
    updateValues.put("stop_recent_view", loginUser.getStopRecentView());
    updateValues.put("stop_recent_search", loginUser.getStopRecentSearch());
    updateValues.put("set_pass", loginUser.getSetPass());
    if (loginUser.getPassString() != null) {
      updateValues.put("pass_string", loginUser.getPassString());
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
    if (loginUser.getProfileImage() != null) {
      updateValues.put("profile_image", loginUser.getProfileImage());
    }
    if (loginUser.getProfileOriginalImage() != null) {
      updateValues.put("profile_original_image", loginUser.getProfileOriginalImage());
    }

    Call<ModelProfile> call = profileAPIInterface.updateProfile(loginUser.getId(), updateValues);
    call.enqueue(new Callback<ModelProfile>() {
      @Override
      public void onResponse(@NonNull Call<ModelProfile> call,
          @NonNull Response<ModelProfile> response) {
        progressDialog.dismiss();

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

        displayUser();
      }

      @Override
      public void onFailure(@NonNull Call<ModelProfile> call, @NonNull Throwable t) {
        call.cancel();
        Log.e("ActivitySettings", "Error: " + t.getMessage());
        progressDialog.dismiss();
      }
    });
  }

  @Override
  protected void onDestroy() {
    if (!isFinishing() && progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
    super.onDestroy();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
