package notq.dccast.screens.navigation_menu.settings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;
import io.realm.Realm;
import java.util.HashMap;
import java.util.Objects;
import notq.dccast.BuildConfig;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.ProfileAPIInterface;
import notq.dccast.api.login.LoginAPIInterface;
import notq.dccast.databinding.ActivitySettingsBinding;
import notq.dccast.model.user.ModelChildUser;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.profile.ModelProfile;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.login.ActivityLogin;
import notq.dccast.screens.navigation_menu.settings.contact.ActivityContactUs;
import notq.dccast.screens.navigation_menu.settings.history.ActivityHistoryInformation;
import notq.dccast.screens.navigation_menu.settings.notification.ActivityNotificationSettings;
import notq.dccast.util.Constants;
import notq.dccast.util.DialogHelper;
import notq.dccast.util.LockableBottomSheetBehavior;
import notq.dccast.util.LoginService;
import notq.dccast.util.SuccessDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivitySettings extends BaseActivity {

  private final int LOGIN_REQUEST = 102;
  ActivitySettingsBinding binding;
  private Context mContext = this;
  private ProfileAPIInterface profileAPIInterface;
  private LoginAPIInterface loginAPIInterface;
  private ProgressDialog progressDialog;
  private Realm realm;
  private ModelUser loginUser;
  private BottomSheetBehavior sheetBehavior;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

    initToolbar();
    initBottomSheet();
    initApi();

    loginUser = LoginService.getLoginUser();

    binding.itemVersion.setTitle(
        getString(R.string.settings_item_version, BuildConfig.VERSION_NAME));

    displayUser();
    init();
  }

  private void initApi() {
    profileAPIInterface = APIClient.getClient().create(ProfileAPIInterface.class);
    loginAPIInterface = APIClient.getClient().create(LoginAPIInterface.class);
    realm = Realm.getDefaultInstance();

    progressDialog = new ProgressDialog(mContext);
    progressDialog.setCancelable(false);
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_settings));
    binding.header.backButton.setOnClickListener(v -> {
      finish();
    });
  }

  private void init() {
    binding.itemMobileData.setOnClickListener(v -> {
      binding.itemMobileData.toggleChecked();
    });

    binding.itemMobileData.setCheckedChangeListener((view, isChecked) -> {
      if (loginUser == null) {
        Prefs.putBoolean("mobile_data", isChecked);
      } else {
        if (!realm.isInTransaction()) {
          realm.beginTransaction();
        }
        loginUser.setLimitMobileData(isChecked);
        realm.commitTransaction();

        updateProfile(false, false);
      }

      Toast.makeText(mContext, isChecked ? getString(R.string.settings_mobile_data_on)
          : getString(R.string.settings_mobile_data_off), Toast.LENGTH_LONG).show();
    });

    binding.itemLicense.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(mContext, ActivityOpenSource.class);
        startActivity(intent);
      }
    });

    binding.itemPrivacy.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(mContext, ActivityWebView.class);
        intent.putExtra("url", getString(R.string.url_privacy));
        intent.putExtra("title", getString(R.string.settings_privacy));
        startActivity(intent);
      }
    });

    binding.itemTerms.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(mContext, ActivityWebView.class);
        intent.putExtra("url", getString(R.string.url_policy));
        intent.putExtra("title", getString(R.string.settings_terms_of_condition));
        startActivity(intent);
      }
    });

    binding.itemInformation.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ModelUser loginUser = LoginService.getLoginUser();
        if (loginUser == null) {
          Intent loginIntent = new Intent(mContext, ActivityLogin.class);
          startActivityForResult(loginIntent, LOGIN_REQUEST);
          return;
        }
        Intent intent = new Intent(mContext, ActivityContactUs.class);
        startActivity(intent);
      }
    });

    if (loginUser == null) {
      binding.itemAutoLogin.setOnClickListener(v -> {
        Intent loginIntent = new Intent(mContext, ActivityLogin.class);
        startActivityForResult(loginIntent, LOGIN_REQUEST);
      });
      binding.itemAutoLogin.disableSwitch();
      binding.itemSecurity.disableSwitch();

      binding.itemNotification.setClickable(false);
      binding.itemNotification.setEnabled(false);

      binding.itemHistory.setClickable(false);
      binding.itemHistory.setEnabled(false);

      binding.itemSecurity.setClickable(false);
      binding.itemSecurity.setEnabled(false);

      binding.layoutLogOut.setVisibility(View.GONE);
    } else {
      binding.itemAutoLogin.enableSwitch();
      binding.itemSecurity.enableSwitch();

      binding.itemAutoLogin.setOnClickListener(v -> {
        binding.itemAutoLogin.toggleChecked();
      });

      binding.itemNotification.setClickable(true);
      binding.itemNotification.setEnabled(true);

      binding.itemHistory.setClickable(true);
      binding.itemHistory.setEnabled(true);

      binding.itemSecurity.setClickable(true);
      binding.itemSecurity.setEnabled(true);

      binding.layoutLogOut.setVisibility(View.VISIBLE);
      binding.itemAutoLogin.setCheckedChangeListener((view, isChecked) -> {
        if (loginUser.getAutoLogin() != isChecked) {
          if (!realm.isInTransaction()) {
            realm.beginTransaction();
          }
          loginUser.setAutoLogin(isChecked);
          realm.commitTransaction();

          updateProfile(false, false);

          Toast.makeText(mContext, isChecked ? getString(R.string.settings_auto_login_on)
              : getString(R.string.settings_auto_login_off), Toast.LENGTH_LONG).show();
        }
      });

      binding.itemNotification.setOnClickListener(v -> {
        Intent notificationIntent = new Intent(mContext, ActivityNotificationSettings.class);
        startActivity(notificationIntent);
      });

      binding.itemHistory.setOnClickListener(v -> {
        Intent historyIntent = new Intent(mContext, ActivityHistoryInformation.class);
        startActivity(historyIntent);
      });

      binding.itemSecurity.setOnClickListener(v -> {
        binding.itemSecurity.toggleChecked();
      });

      binding.itemSecurity.setCheckedChangeListener((view, isChecked) -> {
        if (isChecked) {
          openBottomSheetLock(null);
        } else {
          if (loginUser.getSetPass() != isChecked) {
            if (!realm.isInTransaction()) {
              realm.beginTransaction();
            }
            loginUser.setSetPass(false);
            loginUser.setPassString(null);
            realm.commitTransaction();
            updateProfile(true, false);
          }
        }
      });

      binding.layoutLogOut.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          DialogHelper.showAlertDialog(mContext, getString(R.string.logout_confirm_title),
              getString(
                  R.string.logout_confirm_body),
              getString(R.string.logout_confirm_yes),
              (dialog, which) -> {
                Call<JsonObject> call = loginAPIInterface.logout();
                call.enqueue(new Callback<JsonObject>() {
                  @Override
                  public void onResponse(@NonNull Call<JsonObject> call,
                      @NonNull Response<JsonObject> response) {
                    deleteUser();

                    setResult(RESULT_OK);
                    finish();
                  }

                  @Override
                  public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    deleteUser();

                    setResult(RESULT_OK);
                    finish();
                  }
                });
              }, getString(R.string.logout_confirm_no), null);
        }
      });
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode == LOGIN_REQUEST) {
      loginUser = LoginService.getLoginUser();

      binding.itemVersion.setTitle(
          getString(R.string.settings_item_version, BuildConfig.VERSION_NAME));

      displayUser();
      init();
    }
  }

  private void checkAppVersion() {
    if (profileAPIInterface == null) {
      return;
    }
    Call<JsonObject> call = profileAPIInterface.getAppVersion();
    call.enqueue(new Callback<JsonObject>() {
      @Override
      public void onResponse(@NonNull Call<JsonObject> call,
          @NonNull Response<JsonObject> response) {
        JsonObject updatedProfile = response.body();

        if (updatedProfile == null || !updatedProfile.has("version")) {
          return;
        }

        String version = updatedProfile.get("version").getAsString();
        if (BuildConfig.VERSION_NAME.compareToIgnoreCase(version) > 0) {
          binding.itemVersion.showUpdate(new SettingsItem.UpdateClickListener() {
            @Override public void updateClick() {
              final String appPackageName =
                  getPackageName();
              try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackageName)));
              } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                    "https://play.google.com/store/apps/details?id=" + appPackageName)));
              }
            }
          });
        } else {
          binding.itemVersion.hideUpdate();
        }
      }

      @Override
      public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
        call.cancel();
        Log.e("ActivitySettings", "getAppVersion Error: " + t.getMessage());
      }
    });
  }

  private void initBottomSheet() {
    sheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);
    sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override public void onStateChanged(@NonNull View bottomSheet, int newState) {
        bottomSheet.post(() -> {
          bottomSheet.requestLayout();
          bottomSheet.invalidate();
        });

        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
          binding.dim.setVisibility(View.GONE);
        }
        if (sheetBehavior instanceof LockableBottomSheetBehavior) {
          ((LockableBottomSheetBehavior) sheetBehavior).setLocked(true);
        }
      }

      @Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {
      }
    });

    binding.dim.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

      }
    });

    closeBottomSheet();
  }

  private void openBottomSheetLock(String pin) {
    FragmentTransaction fragmentTransaction =
        Objects.requireNonNull(getSupportFragmentManager()).beginTransaction();

    BottomSheetSetLock bottomSheetLock = BottomSheetSetLock.getInstance(pin);
    bottomSheetLock.setCancelable(false);
    bottomSheetLock.setPinListener(new BottomSheetSetLock.EnterPinListener() {
      @Override public void initialPinEntered(String pin) {
        closeBottomSheet();

        new Handler().postDelayed(() -> runOnUiThread(() -> openBottomSheetLock(pin)), 300);
      }

      @Override public void pinVerified(String pin) {
        closeBottomSheet();

        if (!realm.isInTransaction()) {
          realm.beginTransaction();
        }
        loginUser.setSetPass(true);
        loginUser.setPassString(pin);
        realm.commitTransaction();

        updateProfile(true, true);
      }

      @Override public void close() {
        closeBottomSheet();
      }
    });

    fragmentTransaction.replace(R.id.bottom_sheet, bottomSheetLock);
    fragmentTransaction.commit();

    new Handler().postDelayed(() -> runOnUiThread(() -> {
      sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

      if (binding.dim.getVisibility() != View.VISIBLE) {
        binding.dim.setVisibility(View.VISIBLE);
      }
    }), 100);
  }

  private void closeBottomSheet() {
    hideKeyboard();
    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
  }

  private void hideKeyboard() {
    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
    Objects.requireNonNull(imm).hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
  }

  private void showVerifiedDialog() {
    SuccessDialog successDialog =
        new SuccessDialog(mContext, getString(R.string.settings_verified_success));
    successDialog.showDialog();
  }

  @Override public void onBackPressed() {
    if (sheetBehavior != null && sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
      binding.itemSecurity.setChecked(false);
      closeBottomSheet();
      return;
    }

    super.onBackPressed();
  }

  private void deleteUser() {
    Prefs.putString(Constants.LOGIN_USERNAME, "");
    Prefs.putString(Constants.LOGIN_PASSWORD, "");

    if (!realm.isInTransaction()) {
      realm.beginTransaction();
    }
    realm.delete(ModelUser.class);
    realm.delete(ModelChildUser.class);

    if (realm.isInTransaction()) {
      realm.commitTransaction();
    }
  }

  private void displayUser() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser != null) {
      //SECURITY LOCK
      binding.itemAutoLogin.setChecked(loginUser.getAutoLogin());
      binding.itemMobileData.setChecked(loginUser.getLimitMobileData());
      binding.itemSecurity.setChecked(
          loginUser.getSetPass() && loginUser.getPassString() != null && !loginUser.getPassString()
              .isEmpty());
    }
  }

  @Override protected void onResume() {
    super.onResume();

    checkAppVersion();
  }

  private void updateProfile(boolean showToast, boolean isSetLock) {
    if (isFinishing()) {
      return;
    }
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
    if (loginUser.getAutoLogin() && loginUser.getSetPass() && loginUser.getPassString() != null) {
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
    if (loginUser.getProfileImage() != null) {
      updateValues.put("profile_image", loginUser.getProfileImage());
    }

    if (loginUser.getProfileOriginalImage() != null) {
      updateValues.put("profile_original_image", loginUser.getProfileOriginalImage());
    } else {
      if (loginUser.getProfileImage() != null) {
        updateValues.put("profile_original_image", loginUser.getProfileImage());
      }
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

        if (isSetLock) {
          if (updatedProfile.getSetPass()) {
            showVerifiedDialog();

            if (showToast) {
              Toast.makeText(mContext, getString(R.string.password_set), Toast.LENGTH_LONG).show();
            }
          } else {
            if (showToast) {
              Toast.makeText(mContext, getString(R.string.password_not_set), Toast.LENGTH_LONG)
                  .show();
            }
          }
        } else {
          if (showToast) {
            Toast.makeText(mContext, getString(R.string.password_not_set), Toast.LENGTH_LONG)
                .show();
          }
        }
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
