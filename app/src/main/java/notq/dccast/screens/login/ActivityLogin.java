package notq.dccast.screens.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pixplicity.easyprefs.library.Prefs;
import io.realm.Realm;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.ProfileAPIInterface;
import notq.dccast.api.login.LoginAPIInterface;
import notq.dccast.databinding.ActivityLoginBinding;
import notq.dccast.model.user.ModelChildUser;
import notq.dccast.model.user.ModelDCInsideLoginResult;
import notq.dccast.model.user.ModelLoginUserWrapper;
import notq.dccast.model.user.ModelSignUpResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.ModelUserWrapper;
import notq.dccast.model.user.profile.ModelProfile;
import notq.dccast.screens.BaseActivity;
import notq.dccast.util.Constants;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityLogin extends BaseActivity {
  private Context mContext = this;
  private ActivityLoginBinding binding;
  private LoginAPIInterface loginApiInterface;
  private LoginAPIInterface dcApiLoginInterface;
  private Realm realm;
  private ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

    init();
    initApi();
    initListeners();
    initButtons();

    PUT_TEST_VALUES();
  }

  private void init() {
    progressDialog = new ProgressDialog(mContext);
    progressDialog.setCancelable(false);

    realm = Realm.getDefaultInstance();
  }

  private void initApi() {
    loginApiInterface = APIClient.getClient().create(LoginAPIInterface.class);
    dcApiLoginInterface = APIClient.getDCIdClient().create(LoginAPIInterface.class);
  }

  private void initListeners() {
    setTextChangedListener(binding.emailInputLayout, binding.emailInputText);
    setTextChangedListener(binding.passwordInputLayout, binding.passwordInputText);
  }

  void PUT_TEST_VALUES() {
    //binding.emailInputText.setText("kinno19");
    //binding.passwordInputText.setText("dcinside00");

    binding.emailInputText.setText("spinaweb");
    binding.passwordInputText.setText("sp1n@web");
  }

  private void initButtons() {
    binding.loginButton.setOnClickListener(v -> {
      if (!validateEmail() || !validatePassword()) {
        return;
      }
      runOnUiThread(() -> progressDialog.show());
      String username = binding.emailInputText.getText().toString().trim();
      String password = binding.passwordInputText.getText().toString().trim();

      dcInsideLogin(username, password);
      //TODO REMOVE
      //dcCastLogin(username, password, "password123");
    });

    binding.forgetButton.setOnClickListener(v -> {
      Intent forgetIntent = new Intent(mContext, ActivityForgetPassword.class);
      startActivity(forgetIntent);
    });

    binding.registerButton.setOnClickListener(v -> {
      Intent signUpIntent = new Intent(mContext, ActivitySignUp.class);
      startActivity(signUpIntent);
    });
  }

  private void dcInsideLogin(String username, String password) {
    Call<List<ModelDCInsideLoginResult>> call =
        dcApiLoginInterface.dcInsideLogin(username, password);
    call.enqueue(new Callback<List<ModelDCInsideLoginResult>>() {
      @Override
      public void onResponse(@NonNull Call<List<ModelDCInsideLoginResult>> call,
          @NonNull Response<List<ModelDCInsideLoginResult>> response) {
        List<ModelDCInsideLoginResult> loginResult = response.body();

        if (loginResult != null && loginResult.size() > 0) {
          ModelDCInsideLoginResult insideLoginResult = loginResult.get(0);
          if (insideLoginResult != null && insideLoginResult.isResult()) {
            DCCastApplication.userId = insideLoginResult.getUserId();
            DCCastApplication.userNo = insideLoginResult.getUserNo();

            checkUserExists(username, password);
            return;
          }
        }

        Toast.makeText(mContext, getString(R.string.error_login), Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
      }

      @Override
      public void onFailure(@NonNull Call<List<ModelDCInsideLoginResult>> call,
          @NonNull Throwable t) {
        call.cancel();

        Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
        Log.e("ActivityLogin", "DCInside Login Error: " + t.getMessage());
        progressDialog.dismiss();
      }
    });
  }

  private void checkUserExists(String username, String password) {
    String password_const = "password123";
    Call<ModelUserWrapper> call = loginApiInterface.checkUserExists(username, password);
    call.enqueue(new Callback<ModelUserWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelUserWrapper> call,
          @NonNull Response<ModelUserWrapper> response) {
        ModelUserWrapper userWrapper = response.body();
        if (userWrapper == null || userWrapper.getUsers() == null || userWrapper.getUsers()
            .isEmpty()) {
          dcSignUp(username, password, password_const);
          return;
        }

        dcCastLogin(username, password, password_const);
      }

      @Override
      public void onFailure(@NonNull Call<ModelUserWrapper> call, @NonNull Throwable t) {
        call.cancel();

        Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
        Log.e("ActivityLogin", "Check User Error: " + t.getMessage());
        progressDialog.dismiss();
      }
    });
  }

  private void dcCastLogin(String username, String password, String passwordConst) {
    Call<ModelLoginUserWrapper> call = loginApiInterface.login(username, passwordConst);
    call.enqueue(new Callback<ModelLoginUserWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelLoginUserWrapper> call,
          @NonNull Response<ModelLoginUserWrapper> response) {
        ModelLoginUserWrapper userWrapper = response.body();
        if (userWrapper == null || userWrapper.getUser() == null) {
          String errorMessage =
              response.message() != null ? "Error: " + response.message() : "Error";
          Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
          progressDialog.dismiss();
          return;
        }

        ModelUser user = userWrapper.getUser();

        saveUserNo(user, username, password);
      }

      @Override
      public void onFailure(@NonNull Call<ModelLoginUserWrapper> call, @NonNull Throwable t) {
        call.cancel();

        Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
        Log.e("ActivityLogin", "DCCast Login Error: " + t.getMessage());
        progressDialog.dismiss();
      }
    });
  }

  private void dcSignUp(String username, String password, String passwordConst) {
    Call<ModelSignUpResult> call = loginApiInterface.signUp(username, passwordConst, "");
    call.enqueue(new Callback<ModelSignUpResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelSignUpResult> call,
          @NonNull Response<ModelSignUpResult> response) {
        ModelSignUpResult result = response.body();
        if (result != null && result.getUsername() != null) {
          dcCastLogin(username, password, passwordConst);
          return;
        }

        Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
      }

      @Override
      public void onFailure(@NonNull Call<ModelSignUpResult> call, @NonNull Throwable t) {
        call.cancel();

        Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
        Log.e("ActivityLogin", "Sign Up Error: " + t.getMessage());
        progressDialog.dismiss();
      }
    });
  }

  private void saveUserNo(ModelUser loginUser, final String username, final String password) {
    HashMap<String, Object> updateValues = new HashMap<>();
    updateValues.put("user", loginUser.getId());
    //updateValues.put("nick_name", loginUser.getNickName());
    //updateValues.put("state_message", loginUser.getStateMessage());
    //updateValues.put("phone", loginUser.getPhone());
    updateValues.put("user_no", DCCastApplication.userNo);

    //updateValues.put("name_certification", loginUser.getNameCertification());
    //updateValues.put("auto_login", loginUser.getAutoLogin());
    //if (loginUser.getLastNameCertification() != null) {
    //  updateValues.put("last_name", loginUser.getLastNameCertification());
    //}
    //if (loginUser.getEmailCertification() != null) {
    //  updateValues.put("email_certification", loginUser.getEmailCertification());
    //}
    //
    //updateValues.put("notice_live", loginUser.getNoticeLive());
    //updateValues.put("notice_vod", loginUser.getNoticeVod());
    //updateValues.put("notice_chat", loginUser.getNoticeChat());
    //updateValues.put("notice_notice", loginUser.getNoticeNotice());
    //updateValues.put("notice_sound", loginUser.getNoticeSound());
    //updateValues.put("notice_vibration", loginUser.getNoticeVibration());
    //updateValues.put("phone_certificatioon", loginUser.getPhoneCertification());
    //if (loginUser.getPhoneCertificationDate() != null) {
    //  updateValues.put("phone_certification_date", loginUser.getPhoneCertificationDate());
    //}
    //updateValues.put("limit_mobile_data", loginUser.getLimitMobileData());
    //updateValues.put("stop_recent_view", loginUser.getStopRecentView());
    //updateValues.put("stop_recent_search", loginUser.getStopRecentSearch());
    //if (loginUser.getSetPass() && loginUser.getPassString() != null) {
    //  updateValues.put("set_pass", true);
    //  updateValues.put("pass_string", loginUser.getPassString());
    //} else {
    //  updateValues.put("set_pass", false);
    //  updateValues.put("pass_string", "");
    //}
    //updateValues.put("adult_certification", loginUser.getAdultCertification());
    //if (loginUser.getAdultCertificationDate() != null) {
    //  updateValues.put("adult_certification_date", loginUser.getAdultCertificationDate());
    //}
    //updateValues.put("is_vip", loginUser.getVip());
    //updateValues.put("is_vip_active", loginUser.getVipActive());
    //if (loginUser.getVipCreateDate() != null) {
    //  updateValues.put("vip_create_date", loginUser.getVipCreateDate());
    //}

    updateValues.put("auto_login", loginUser.getAutoLogin());
    if (loginUser.getSetPass() && loginUser.getPassString() != null) {
      updateValues.put("set_pass", true);
      updateValues.put("pass_string", loginUser.getPassString());
    } else {
      updateValues.put("set_pass", false);
      updateValues.put("pass_string", "");
    }
    updateValues.put("notice_live", loginUser.getNoticeLive());
    updateValues.put("notice_vod", loginUser.getNoticeVod());
    updateValues.put("notice_chat", loginUser.getNoticeChat());
    updateValues.put("notice_notice", loginUser.getNoticeNotice());

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

    ProfileAPIInterface profileAPIInterface =
        APIClient.getClient().create(ProfileAPIInterface.class);
    Call<ModelProfile> call = profileAPIInterface.updateProfile(loginUser.getId(), updateValues);
    call.enqueue(new Callback<ModelProfile>() {
      @Override
      public void onResponse(@NonNull Call<ModelProfile> call,
          @NonNull Response<ModelProfile> response) {

        ModelProfile updatedProfile = response.body();

        if (updatedProfile == null) {
          progressDialog.dismiss();
          Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
          return;
        }

        loginUser.setProfile(updatedProfile);
        saveUser(loginUser);

        Prefs.putString(Constants.LOGIN_USERNAME, username);
        Prefs.putString(Constants.LOGIN_PASSWORD, password);

        progressDialog.dismiss();

        FirebaseInstanceId.getInstance().getInstanceId()
            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
              @Override
              public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                  Timber.e("getInstanceId failed%s", task.getException().getMessage());
                  return;
                }

                String token = task.getResult().getToken();

                Timber.e("REFRESH TOKEN: " + token);
                updateProfile(token);
              }
            });

        setResult(RESULT_OK);
        finish();
      }

      @Override
      public void onFailure(@NonNull Call<ModelProfile> call, @NonNull Throwable t) {
        call.cancel();
        progressDialog.dismiss();
        Log.e("ActivityLogin", "DCCast Login Error: " + t.getMessage());
        Log.e("ActivitySplash", "Error: " + t.getMessage());
      }
    });
  }

  private void updateProfile(String token) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    int userId = loginUser.getId();

    HashMap<String, Object> updateValues = new HashMap<>();
    updateValues.put("user", userId);
    if (token != null) {
      updateValues.put("client_token", token);
    }

    updateValues.put("auto_login", loginUser.getAutoLogin());
    updateValues.put("notice_live", loginUser.getNoticeLive());
    updateValues.put("notice_vod", loginUser.getNoticeVod());
    updateValues.put("notice_chat", loginUser.getNoticeChat());
    updateValues.put("notice_notice", loginUser.getNoticeNotice());

    ProfileAPIInterface profileAPIInterface =
        APIClient.getClient().create(ProfileAPIInterface.class);
    Call<ModelProfile> call = profileAPIInterface.updateProfile(userId, updateValues);

    Realm realm = Realm.getDefaultInstance();
    call.enqueue(new Callback<ModelProfile>() {
      @Override
      public void onResponse(@NonNull Call<ModelProfile> call,
          @NonNull Response<ModelProfile> response) {

        ModelProfile updatedProfile = response.body();

        if (updatedProfile == null) {
          return;
        }

        if (!realm.isInTransaction()) {
          realm.beginTransaction();
        }
        try {
          loginUser.setProfile(updatedProfile);
        } catch (Exception ignored) {

        }
        realm.copyToRealmOrUpdate(loginUser);

        realm.commitTransaction();

        Timber.e("SAVE TOKEN SUCCESS!");
      }

      @Override
      public void onFailure(@NonNull Call<ModelProfile> call, @NonNull Throwable t) {
        call.cancel();
        Timber.e("Save firebase messaging service: %s", t.getMessage());
      }
    });
  }

  @Override
  protected void onDestroy() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
    super.onDestroy();
  }

  private void saveUser(ModelUser user) {
    if (!realm.isInTransaction()) {
      realm.beginTransaction();
    }
    realm.delete(ModelUser.class);
    realm.delete(ModelChildUser.class);

    realm.copyToRealmOrUpdate(user);

    if (realm.isInTransaction()) {
      realm.commitTransaction();
    }
  }

  private boolean validateEmail() {
    String email = Objects.requireNonNull(binding.emailInputText.getText()).toString();
    if (TextUtils.isEmpty(email)) {
      binding.emailInputLayout.setErrorEnabled(true);
      String error = getString(R.string.validate_enter_username);
      binding.emailInputLayout.setError(error);
      return false;
    }

    binding.emailInputLayout.setError(null);
    binding.emailInputLayout.setErrorEnabled(false);
    return true;
  }

  private boolean validatePassword() {
    String password = Objects.requireNonNull(binding.passwordInputText.getText()).toString();
    boolean validate = !TextUtils.isEmpty(password);
    binding.passwordInputLayout.setErrorEnabled(!validate);
    String validatePassword = getString(R.string.validate_enter_password);
    binding.passwordInputLayout.setError(validate ? null : validatePassword);
    return validate;
  }

  private void setTextChangedListener(TextInputLayout layout, TextInputEditText editText) {
    editText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        layout.setErrorEnabled(false);
        layout.setError(null);
      }
    });
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
