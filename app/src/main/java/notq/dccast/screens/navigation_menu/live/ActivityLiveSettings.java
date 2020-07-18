package notq.dccast.screens.navigation_menu.live;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.pixplicity.easyprefs.library.Prefs;
import java.util.HashMap;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.live.LiveAPIInterface;
import notq.dccast.databinding.ActivityLiveSettingsBinding;
import notq.dccast.model.live.FollowerStatResponse;
import notq.dccast.model.live.ModelOptions;
import notq.dccast.model.live.StatResponse;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.BaseActivity;
import notq.dccast.util.Constants;
import notq.dccast.util.LockableBottomSheetBehavior;
import notq.dccast.util.LoginService;
import notq.dccast.util.SuccessDialog;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityLiveSettings extends BaseActivity {
  public static final int ITEM_DISTRIBUTE = 1;
  public static final int ITEM_LIVE_TYPE = 2;
  public static final int ITEM_CATEGORY = 3;
  public static final int ITEM_SET_LOCK = 4;
  public static final int ITEM_BROADCAST = 5;
  public static final int ITEM_ORIENTATION = 6;
  public static final int ITEM_BLOCK_USER = 7;

  public static final int LIVE_TYPE_CAMERA = 1;
  public static final int LIVE_TYPE_ALBUM = 2;
  public static final int LIVE_TYPE_RADIO_MODE = 3;
  private static final int CAMERA_REQUEST = 1888;
  private static final int GALLERY_REQUEST = 1999;
  private final int REQUEST_LIVE = 2952;
  private String mCurrentPhotoPath;
  private Context mContext = this;
  private int selectedCategoryId = -1;
  private int selectedOrientation = 0;
  private int selectedLiveType = LIVE_TYPE_CAMERA;
  private int groupId = -1;
  private int setMaxUser = 100;
  private String liveLockPin;
  private ActivityLiveSettingsBinding binding;
  private BottomSheetBehavior sheetBehavior;
  @SuppressLint("UseSparseArrays") private HashMap<Integer, BottomSheetOptions>
      bottomSheetOptionsHashMap = new HashMap<>();

  private LiveAPIInterface apiService;

  private int subscriber = 0, follower = 0, secondFollower = 0, thirdFollower = 0;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_live_settings);

    groupId = getIntent().getIntExtra("groupId", -1);

    initApi();
    initToolbar();
    initBottomSheet();
    init();
  }

  private void initApi() {
    apiService = APIClient.getClient().create(LiveAPIInterface.class);
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_live_settings));
    binding.header.backButton.setOnClickListener(v -> {
      onBackPressed();
    });
    binding.header.lblActionBtn.setVisibility(View.GONE);
  }

  private void getStat() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    Call<StatResponse> call = apiService.getStat(loginUser.getId());

    call.enqueue(new Callback<StatResponse>() {
      @Override
      public void onResponse(@NonNull Call<StatResponse> call,
          @NonNull Response<StatResponse> response) {
        StatResponse statResponse = response.body();
        if (statResponse == null) {
          return;
        }

        subscriber = statResponse.getSubscribers();
      }

      @Override
      public void onFailure(@NonNull Call<StatResponse> call, @NonNull Throwable t) {
        call.cancel();
      }
    });
  }

  private void getFollowerStat() {
    ModelUser loginUser = LoginService.getLoginUser();

    if (loginUser == null) {
      return;
    }

    Call<FollowerStatResponse> call = apiService.getFollowerStat(loginUser.getId());

    call.enqueue(new Callback<FollowerStatResponse>() {
      @Override
      public void onResponse(@NonNull Call<FollowerStatResponse> call,
          @NonNull Response<FollowerStatResponse> response) {
        FollowerStatResponse followerStatResponse = response.body();
        if (followerStatResponse == null) {
          return;
        }

        follower = followerStatResponse.getFollowers();
        secondFollower = followerStatResponse.getSecondFollowers();
        thirdFollower = followerStatResponse.getThirdFollowers();
      }

      @Override
      public void onFailure(@NonNull Call<FollowerStatResponse> call, @NonNull Throwable t) {
        call.cancel();
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

    binding.dim.setOnClickListener(v -> closeBottomSheet());

    closeBottomSheet();
  }

  private void init() {
    binding.itemLiveLock.getSwitchItem().setOnCheckedChangeListener((view, isChecked) -> {
      if (isChecked) {
        openBottomSheetLock(null);
      } else {
        liveLockPin = null;
        Toast.makeText(mContext, getString(R.string.password_not_set), Toast.LENGTH_LONG)
            .show();
      }
    });

    binding.itemRestricted.setOnClickListener(v -> binding.itemRestricted.toggleChecked());
    binding.itemLiveLock.setOnClickListener(view -> binding.itemLiveLock.toggleChecked());
    binding.itemChatLock.setOnClickListener(v -> binding.itemChatLock.toggleChecked());
    binding.startLiveBtn.setOnClickListener(v -> {
      if (isValid()) {
        Intent intent = new Intent(ActivityLiveSettings.this, ActivityThumbnailChooser.class);

        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();

        JSONObject params = new JSONObject();
        ModelUser user = LoginService.getLoginUser();
        String username = Prefs.getString(Constants.LOGIN_USERNAME, "");

        try {
          params.put("user", user.getId());
          params.put("title", binding.title.getText().toString());
          params.put("live_deploy", binding.itemLiveDistribute.getValueText());
          params.put("category", "LIVE");
          params.put("media_category", selectedCategoryId);
          params.put("live_member", setMaxUser);
          params.put("live_resolution", binding.itemBroadcastQuality.getValueText());
          if (binding.itemRestricted.getSwitchItem().isChecked()) {
            params.put("kinds", getString(R.string.value_share_type_19));
          }
          params.put("orientation", selectedOrientation == 0 ? "portrait" : "landscape");
          params.put("live_chat_disable",
              binding.itemChatLock.getSwitchItem().isChecked() ? "1" : "0");
          params.put("live_password", liveLockPin);
          params.put("live_setpass", binding.itemLiveLock.getSwitchItem().isChecked() ? 1 : 0);
          params.put("media_id", username + "_" + ts);
          params.put("stream_name", username + "_" + ts);
          params.put("title", binding.title.getText().toString());
        } catch (JSONException e) {
          e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putString("params", params.toString());
        if (groupId != -1) {
          bundle.putInt("groupId", groupId);
        }
        bundle.putInt("liveType", selectedLiveType);
        intent.putExtras(bundle);

        startActivityForResult(intent, REQUEST_LIVE);
      }
    });

    //if (groupId != -1) {
    getStat();
    getFollowerStat();

    binding.itemLiveDistribute.setVisibility(View.VISIBLE);
    binding.itemLiveDistribute.setValue(getString(R.string.item_distribute_subscriber));
    binding.itemLiveDistribute.setItemClickListener(new LiveSettingsItem.ItemClickListener() {
      @Override public void rightArrowClicked() {
        if (binding.itemLiveDistribute.isDescriptionShowing()) {
          binding.itemLiveDistribute.hideDescriptionLayout();
        } else {
          if (binding.itemLiveDistribute.isSubscriber()) {
            binding.itemLiveDistribute.showDescriptionLayout(subscriber);
          } else {
            binding.itemLiveDistribute.showDescriptionLayout(follower, secondFollower,
                thirdFollower);
          }
        }
      }

      @Override public void valueItemClicked() {
        openBottomSheet(ITEM_DISTRIBUTE);
      }
    });
    //} else {
    //  binding.itemLiveDistribute.setVisibility(View.GONE);
    //}

    binding.itemLiveType.setOnClickListener(v -> openBottomSheet(ITEM_LIVE_TYPE));
    binding.itemCategorySetting.setOnClickListener(v -> openBottomSheet(ITEM_CATEGORY));
    binding.itemBroadcastOrientation.setOnClickListener(v -> openBottomSheet(ITEM_ORIENTATION));
    binding.itemBroadcastQuality.setOnClickListener(v -> openBottomSheet(ITEM_BROADCAST));
    binding.itemSetLock.setOnClickListener(v -> openBottomSheet(ITEM_SET_LOCK));
  }

  private void openBottomSheetLock(String pin) {
    FragmentTransaction fragmentTransaction =
        Objects.requireNonNull(getSupportFragmentManager()).beginTransaction();

    BottomSheetLock bottomSheetLock = BottomSheetLock.getInstance(pin);
    bottomSheetLock.setPinListener(new BottomSheetLock.EnterPinListener() {
      @Override public void initialPinEntered(String pin) {
        closeBottomSheet();

        new Handler().postDelayed(() -> runOnUiThread(() -> openBottomSheetLock(pin)), 300);
      }

      @Override public void pinVerified(String pin) {
        liveLockPin = pin;
        closeBottomSheet();
        showVerifiedDialog();
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

  private void openBottomSheet(int type) {
    FragmentTransaction fragmentTransaction =
        Objects.requireNonNull(getSupportFragmentManager()).beginTransaction();

    BottomSheetOptions bottomSheetOptions;
    if (bottomSheetOptionsHashMap.containsKey(type)) {
      bottomSheetOptions = bottomSheetOptionsHashMap.get(type);
    } else {
      bottomSheetOptions = BottomSheetOptions.getInstance(type);
      bottomSheetOptionsHashMap.put(type, bottomSheetOptions);
    }

    if (bottomSheetOptions == null) {
      bottomSheetOptions = BottomSheetOptions.getInstance(type);
    }

    bottomSheetOptions.setOnItemSelectedListener(new BottomSheetOptions.OnItemSelectedListener() {
      @Override public void onItemSelected(ModelOptions item) {
        switch (type) {
          case ITEM_DISTRIBUTE: {
            binding.itemLiveDistribute.setValue(item.getTitle());
            break;
          }
          case ITEM_LIVE_TYPE: {
            selectedLiveType = item.getId();
            binding.itemLiveType.setValue(item.getTitle());
            break;
          }
          case ITEM_CATEGORY: {
            selectedCategoryId = item.getId();
            binding.itemCategorySetting.setValue(item.getTitle());
            break;
          }
          case ITEM_BLOCK_USER: {
            setMaxUser = Integer.parseInt(item.getTitle());
            binding.itemSetLock.setValue(item.getTitle());
            break;
          }
          case ITEM_BROADCAST: {
            binding.itemBroadcastQuality.setValue(item.getTitle());
            break;
          }
          case ITEM_ORIENTATION: {
            selectedOrientation = item.getId();
            binding.itemBroadcastOrientation.setValue(item.getTitle());
            break;
          }
        }
        closeBottomSheet();
      }

      @Override public void close() {
        closeBottomSheet();
      }
    });

    fragmentTransaction.replace(R.id.bottom_sheet, bottomSheetOptions);
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

  private void showVerifiedDialog() {
    SuccessDialog successDialog = new SuccessDialog(mContext);
    successDialog.showDialog();
  }

  @Override public void onBackPressed() {
    if (sheetBehavior != null && sheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
      closeBottomSheet();
      return;
    }

    super.onBackPressed();
  }

  private void hideKeyboard() {
    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
    Objects.requireNonNull(imm).hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
  }

  private boolean isValid() {
    if (binding.title.getText().toString().isEmpty()) {
      String title = getString(R.string.validate_live_title);
      binding.title.setError(title);
      return false;
    }

    if (selectedCategoryId == -1) {
      Toast.makeText(getApplicationContext(), getString(R.string.validate_live_category),
          Toast.LENGTH_SHORT).show();
      return false;
    }

    return true;
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_LIVE) {
      if (resultCode == ActivityThumbnailChooser.THUMBNAIL_DISMISSED) {
        return;
      }
      setResult(resultCode, data);
      finish();
    }
  }
}
