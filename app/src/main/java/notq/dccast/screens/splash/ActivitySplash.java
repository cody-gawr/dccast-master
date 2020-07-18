package notq.dccast.screens.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.pixplicity.easyprefs.library.Prefs;
import io.realm.Realm;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import notq.dccast.BuildConfig;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.HomeAPIInterface;
import notq.dccast.api.home.ProfileAPIInterface;
import notq.dccast.api.login.LoginAPIInterface;
import notq.dccast.databinding.ActivitySplashBinding;
import notq.dccast.model.ModelPublicKeyResult;
import notq.dccast.model.category.CategoryItem;
import notq.dccast.model.category.ModelCategory;
import notq.dccast.model.user.ModelChildUser;
import notq.dccast.model.user.ModelDCInsideLoginResult;
import notq.dccast.model.user.ModelLoginUserWrapper;
import notq.dccast.model.user.ModelSignUpResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.ModelUserWrapper;
import notq.dccast.model.user.profile.ModelProfile;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.home.ActivityHome;
import notq.dccast.util.Constants;
import notq.dccast.util.LockableBottomSheetBehavior;
import notq.dccast.util.LoginService;
import notq.dccast.util.PopUpUtil;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivitySplash extends BaseActivity {
  private Context mContext = this;
  private HomeAPIInterface apiInterface;
  private LoginAPIInterface loginApiInterface;
  private LoginAPIInterface dcApiLoginInterface;
  private Realm realm;
  private ActivitySplashBinding binding;
  private boolean waitLogin = false, waitCategory = false;
  private BottomSheetBehavior<FrameLayout> sheetBehavior;

  private int openVideoId = -1;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
    init();
    initBottomSheet();

    Prefs.putBoolean("stopped", false);

    int notificationVideoId = getIntent().getIntExtra(Constants.OPEN_VIDEO_ID, -1);
    if (notificationVideoId > 0) {
      openVideoId = notificationVideoId;
    } else {
      openVideoId = PopUpUtil.getOpenVideoId();
      if (openVideoId > 0) {
        PopUpUtil.setOpenVideoId(-1);
      }
    }

    getPublicKey();

    getCategoryList();

    checkLogin();
  }

  private void checkLogin() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser != null && loginUser.getAutoLogin()) {
      waitLogin = true;
      if (loginUser.getSetPass()
          && loginUser.getPassString() != null
          && loginUser.getPassString().length() == 4) {
        openBottomSheetLock(loginUser.getPassString());
        Log.e("ActivitySplash", "ASK PASS");
      } else {
        dcInsideLogin();
        Log.e("ActivitySplash", "DC INSIDE LOGIN");
      }
    } else {
      Log.e("ActivitySplash", "LOGIN USER NULL");
      deleteUser();
    }
  }

  @Override protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    if (intent == null) {
      return;
    }

    int notificationVideoId = intent.getIntExtra(Constants.OPEN_VIDEO_ID, -1);
    if (notificationVideoId > 0) {
      openVideoId = notificationVideoId;
    } else {
      openVideoId = PopUpUtil.getOpenVideoId();
      if (openVideoId > 0) {
        PopUpUtil.setOpenVideoId(-1);
      }
    }
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

    BottomSheetValidatePassCode bottomSheetLock =
        BottomSheetValidatePassCode.getInstance(BottomSheetValidatePassCode.SET_LOCK, pin);
    bottomSheetLock.setCancelable(false);
    bottomSheetLock.setPinListener(pin1 -> {
      closeBottomSheet();
      dcInsideLogin();
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

  private void init() {
    realm = Realm.getDefaultInstance();
    apiInterface = APIClient.getClient().create(HomeAPIInterface.class);
    loginApiInterface = APIClient.getClient().create(LoginAPIInterface.class);
    dcApiLoginInterface = APIClient.getDCIdClient().create(LoginAPIInterface.class);
  }

  private void saveUserNo(ModelUser loginUser) {
    HashMap<String, Object> updateValues = new HashMap<>();
    updateValues.put("user", loginUser.getId());
    updateValues.put("nick_name", loginUser.getNickName());
    updateValues.put("state_message", loginUser.getStateMessage());
    updateValues.put("phone", loginUser.getPhone());
    updateValues.put("user_no", DCCastApplication.userNo);

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
          waitLogin = false;
          navigateToHomeActivity();
          return;
        }

        loginUser.setProfile(updatedProfile);
        saveUser(loginUser);
        navigateToHomeActivity();
      }

      @Override
      public void onFailure(@NonNull Call<ModelProfile> call, @NonNull Throwable t) {
        call.cancel();
        waitLogin = false;
        navigateToHomeActivity();
        Log.e("ActivitySplash", "Error: " + t.getMessage());
      }
    });
  }

  private void navigateToHomeActivity() {
    if (waitLogin || waitCategory) {
      return;
    }
    new Handler().postDelayed(() -> {

      Intent homeIntent = new Intent(getApplicationContext(), ActivityHome.class);
      if (openVideoId > 0) {
        homeIntent.putExtra(Constants.OPEN_VIDEO_ID, openVideoId);
      }
      startActivity(homeIntent);
      finish();
    }, 1000);
  }

  public void onErrorCondition() {
    deleteUser();
    waitLogin = false;
    navigateToHomeActivity();
  }

  private void getPublicKey() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH", Locale.ENGLISH);
    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
    String date = sdf.format(new Date());
    String valueTokenString = "dcCastchk_" + date;
    String valueToken = "";

    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      digest.update(valueTokenString.getBytes());
      valueToken = Util.bytesToHexString(digest.digest()).toLowerCase();
      Log.e("ERROR", "ValueToken: " + valueToken);
    } catch (NoSuchAlgorithmException e) {
      Log.e("ERROR", "hash:NoSuchAlgorithm: " + e.getMessage());
    }

    Call<List<ModelPublicKeyResult>> call =
        dcApiLoginInterface.getPublicKey(valueToken, Constants.SIGNATURE,
            BuildConfig.APPLICATION_ID, BuildConfig.VERSION_NAME);
    call.enqueue(new Callback<List<ModelPublicKeyResult>>() {
      @Override public void onResponse(@NonNull Call<List<ModelPublicKeyResult>> call,
          @NonNull Response<List<ModelPublicKeyResult>> response) {
        List<ModelPublicKeyResult> result = response.body();
        if (result != null && result.size() > 0) {
          ModelPublicKeyResult publicKeyResult = result.get(0);

          if (publicKeyResult != null
              && publicKeyResult.isResult()
              && publicKeyResult.getAppId() != null) {
            String appId = publicKeyResult.getAppId();
            Log.e("ActivitySplash", "APP ID: " + appId);
            DCCastApplication.appId = appId;
            return;
          }
        }

        Log.e("ActivitySplash", "Handle app_id error");
      }

      @Override
      public void onFailure(@NonNull Call<List<ModelPublicKeyResult>> call, @NonNull Throwable t) {
        call.cancel();

        Log.e("ActivitySplash", "Get Public Key Error: " + t.getMessage());
      }
    });
  }

  private void dcInsideLogin() {
    String username = Prefs.getString(Constants.LOGIN_USERNAME, "");
    String password = Prefs.getString(Constants.LOGIN_PASSWORD, "");

    if (username.isEmpty() || password.isEmpty()) {
      Log.e("ActivitySplash", "SHARED EMPTY");
      waitLogin = false;
      return;
    }

    Call<List<ModelDCInsideLoginResult>> call =
        dcApiLoginInterface.dcInsideLogin(username, password);
    call.enqueue(new Callback<List<ModelDCInsideLoginResult>>() {
      @Override public void onResponse(@NonNull Call<List<ModelDCInsideLoginResult>> call,
          @NonNull Response<List<ModelDCInsideLoginResult>> response) {
        List<ModelDCInsideLoginResult> loginResult = response.body();
        if (loginResult != null && loginResult.size() > 0) {
          Log.e("ActivitySplash", "DCInside Login Success!");
          ModelDCInsideLoginResult insideLoginResult = loginResult.get(0);
          if (insideLoginResult != null && insideLoginResult.isResult()) {
            DCCastApplication.userId = insideLoginResult.getUserId();
            DCCastApplication.userNo = insideLoginResult.getUserNo();
            Log.e("ActivitySplash", "USER ID:" + DCCastApplication.userId);
            checkUserExists(username, password);
            return;
          }
        }

        Log.e("ActivitySplash", "DCInside Login Error!");
        onErrorCondition();
      }

      @Override public void onFailure(@NonNull Call<List<ModelDCInsideLoginResult>> call,
          @NonNull Throwable t) {
        call.cancel();

        onErrorCondition();
        Log.e("ActivitySplash", "DCInside Login Error: " + t.getMessage());
      }
    });
  }

  private void checkUserExists(String username, String password) {
    String password_const = "password123";
    Call<ModelUserWrapper> call = loginApiInterface.checkUserExists(username, password);
    call.enqueue(new Callback<ModelUserWrapper>() {
      @Override public void onResponse(@NonNull Call<ModelUserWrapper> call,
          @NonNull Response<ModelUserWrapper> response) {
        ModelUserWrapper userWrapper = response.body();
        if (userWrapper == null || userWrapper.getUsers() == null || userWrapper.getUsers()
            .isEmpty()) {
          dcSignUp(username, password_const);
          return;
        }

        Log.e("ActivitySplash", "Check User Success!");
        dcCastLogin(username, password_const);
      }

      @Override public void onFailure(@NonNull Call<ModelUserWrapper> call, @NonNull Throwable t) {
        call.cancel();

        onErrorCondition();
        Log.e("ActivitySplash", "Check User Error: " + t.getMessage());
      }
    });
  }

  private void dcCastLogin(String username, String password) {
    Call<ModelLoginUserWrapper> call = loginApiInterface.login(username, password);
    call.enqueue(new Callback<ModelLoginUserWrapper>() {
      @Override public void onResponse(@NonNull Call<ModelLoginUserWrapper> call,
          @NonNull Response<ModelLoginUserWrapper> response) {
        waitLogin = false;
        ModelLoginUserWrapper userWrapper = response.body();
        if (userWrapper == null || userWrapper.getUser() == null) {
          String errorMessage =
              response.message() != null ? "Error: " + response.message() : "Error";
          onErrorCondition();
          Log.e("ActivitySplash", errorMessage);
          return;
        }
        Log.e("ActivitySplash", "DCCast Login Success!");
        ModelUser user = userWrapper.getUser();

        saveUserNo(user);
      }

      @Override
      public void onFailure(@NonNull Call<ModelLoginUserWrapper> call, @NonNull Throwable t) {
        call.cancel();

        waitLogin = false;
        navigateToHomeActivity();

        Log.e("ActivitySplash", "DCCast Login Error: " + t.getMessage());
      }
    });
  }

  private void dcSignUp(String username, String password) {
    Call<ModelSignUpResult> call = loginApiInterface.signUp(username, password, "");
    call.enqueue(new Callback<ModelSignUpResult>() {
      @Override public void onResponse(@NonNull Call<ModelSignUpResult> call,
          @NonNull Response<ModelSignUpResult> response) {
        ModelSignUpResult result = response.body();
        if (result != null && result.getUsername() != null) {
          Log.e("ActivitySplash", "SignUp Success!");
          dcCastLogin(username, password);
          return;
        }

        Log.e("ActivitySplash", "SignUp Error!");
        onErrorCondition();
      }

      @Override public void onFailure(@NonNull Call<ModelSignUpResult> call, @NonNull Throwable t) {
        call.cancel();

        onErrorCondition();
        Log.e("ActivitySplash", "Sign Up Error: " + t.getMessage());
      }
    });
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

  private void getCategoryList() {
    Call<ModelCategory> call = apiInterface.getCategoryList();
    call.enqueue(new Callback<ModelCategory>() {
      @Override public void onResponse(@NonNull Call<ModelCategory> call,
          @NonNull Response<ModelCategory> response) {
        ModelCategory category = response.body();
        if (category != null) {
          saveCategories(Objects.requireNonNull(category).categoryItems);
        }
        waitCategory = false;
        navigateToHomeActivity();
      }

      @Override public void onFailure(@NonNull Call<ModelCategory> call, @NonNull Throwable t) {
        call.cancel();
        getCategories();
        waitCategory = false;
        navigateToHomeActivity();
      }
    });
  }

  private void saveCategories(List<CategoryItem> categoryItemList) {
    if (!realm.isInTransaction()) {
      realm.beginTransaction();
    }

    realm.delete(CategoryItem.class);
    CategoryItem all = new CategoryItem();
    all.setId(-1);
    all.setName(getString(R.string.category_all));
    categoryItemList.add(0, all);

    for (int i = 0; i < categoryItemList.size(); i++) {
      realm.copyToRealm(categoryItemList.get(i));
    }

    if (realm.isInTransaction()) {
      realm.commitTransaction();
    }

    DCCastApplication.listCategoryItems = categoryItemList;
  }

  private void getCategories() {
    if (!realm.isInTransaction()) {
      realm.beginTransaction();
    }

    DCCastApplication.listCategoryItems = realm.where(CategoryItem.class).findAll();

    if (realm.isInTransaction()) {
      realm.commitTransaction();
    }
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}