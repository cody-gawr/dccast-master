package notq.dccast.screens.navigation_menu.content.subscribe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.HomeAPIInterface;
import notq.dccast.api.home.ProfileAPIInterface;
import notq.dccast.api.login.LoginAPIInterface;
import notq.dccast.api.my_content.MyContentAPIInterface;
import notq.dccast.databinding.ActivitySubscribeBinding;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.ModelVideoWrapper;
import notq.dccast.model.user.ModelAdultCertification;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.profile.ModelProfile;
import notq.dccast.model.user.subscribe.ModelSubscribeUser;
import notq.dccast.model.user.subscribe.ModelSubscribeUserWrapper;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.home.interfaces.HomeChildFragmentListener;
import notq.dccast.screens.home.video_detail.FragmentVideo;
import notq.dccast.screens.login.ActivityLogin;
import notq.dccast.screens.navigation_menu.content.my_channel.BottomSheetSort;
import notq.dccast.screens.splash.BottomSheetValidatePassCode;
import notq.dccast.util.AdultConfirmDialog;
import notq.dccast.util.LockableBottomSheetBehavior;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivitySubscribe extends BaseActivity implements HomeChildFragmentListener {

  private Context mContext = this;
  private ActivitySubscribeBinding binding;
  private AdapterSubscribe adapterSubscribe;

  private int pageIndex = 1, currentSort = -1;
  private boolean isLoading = false, hasNextPage = false;

  private ArrayList<ModelSubscribeUser> subscribers = new ArrayList<>();

  private BottomSheetBehavior sheetBehavior;
  private ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_subscribe);

    initToolbar();
    initBottomSheet();
    init();

    showLoader();
    getSubscribeList();
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_subscribe));
    binding.header.backButton.setOnClickListener(v -> {
      finish();
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
    adapterSubscribe = new AdapterSubscribe(mContext, user -> {
      if (user == null || user.getToUser() == null) {
        return;
      }
      ModelUser toUser = user.getToUser();
      if (toUser.isOnAir()) {
        //ModelVideo openVideo = toUser.getOnAirMedia();
        getMediaById(toUser.getOnAirMedia());
        //checkIsAdult(R.id.container_for_subscribe_video, openVideo);
      }
    });

    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.setAdapter(adapterSubscribe);

    binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) {
          int visibleItemCount = layoutManager.getChildCount();
          int totalItemCount = layoutManager.getItemCount();
          int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

          if (!isLoading && hasNextPage) {
            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
              isLoading = true;
              pageIndex = pageIndex + 1;
              getSubscribeList();
            }
          }
        }
      }
    });

    binding.refresher.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
    binding.refresher.setOnRefreshListener(() -> {
      binding.refresher.setRefreshing(false);
      prepareRecyclerViewForNewItems();
      showLoader();
      getSubscribeList();
    });

    binding.layoutSort.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        openBottomSheet();
      }
    });

    binding.etSearch.addTextChangedListener(new TextWatcher() {
      final int DURATION = 500;
      Timer timer = new Timer();

      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (timer != null) {
          timer.cancel();
        }
      }

      @Override public void afterTextChanged(Editable s) {
        if (timer != null) {
          timer.cancel();
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
          @Override public void run() {
            runOnUiThread(new Runnable() {
              @Override public void run() {
                searchRequest(s.toString());
              }
            });
          }
        }, DURATION);
      }
    });

    progressDialog = new ProgressDialog(mContext);
  }

  private void prepareRecyclerViewForNewItems() {
    adapterSubscribe.removeSubscribers();
    subscribers = new ArrayList<>();
    isLoading = false;
    pageIndex = 1;
  }

  private void openBottomSheet() {
    FragmentTransaction fragmentTransaction =
        Objects.requireNonNull(getSupportFragmentManager()).beginTransaction();

    BottomSheetSort bottomSheetSort;
    bottomSheetSort = BottomSheetSort.getInstance(BottomSheetSort.TYPE_SUBSCRIBE);
    bottomSheetSort.setOnItemSelectedListener(item -> {
      binding.lblSortValue.setText(item.getTitle());
      sort(item.getId(), false);
      closeBottomSheet();
    });

    fragmentTransaction.replace(R.id.bottom_sheet, bottomSheetSort);
    fragmentTransaction.commit();

    new Handler().postDelayed(() -> runOnUiThread(() -> {
      sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
      if (binding.dim.getVisibility() != View.VISIBLE) {
        binding.dim.setVisibility(View.VISIBLE);
      }
    }), 100);
  }

  public void sort(int sort, boolean force) {
    if (sort == currentSort && !force) {
      return;
    }
    currentSort = sort;
    switch (sort) {
      case 0: {
        Collections.sort(subscribers,
            (o1, o2) -> o2.getCreated().compareToIgnoreCase(o1.getCreated()));
        break;
      }

      case 1: {
        Collections.sort(subscribers, (o1, o2) -> {
          if (o1.getLatestLive() == null && o2.getLatestLive() == null) {
            return 0;
          }

          if (o1.getLatestLive() == null) {
            return 1;
          }

          if (o2.getLatestLive() == null) {
            return -1;
          }
          return o2.getLatestLive()
              .getCreated()
              .compareToIgnoreCase(o1.getLatestLive().getCreated());
        });
        break;
      }

      case 2: {
        Collections.sort(subscribers,
            (o1, o2) -> o1.getToUser()
                .getNickName()
                .compareToIgnoreCase(o2.getToUser().getNickName()));
        break;
      }
    }

    adapterSubscribe.setSubscribers(subscribers);
  }

  private void closeBottomSheet() {
    hideKeyboard();
    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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

  private void searchRequest(String key) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    prepareRecyclerViewForNewItems();
    showLoader();

    String ordering = "created";
    switch (currentSort) {
      case 0: {
        ordering = "created";
        break;
      }
      case 1: {
        ordering = "on_air";
        break;
      }
      case 2: {
        ordering = "to_user_nick_name";
        break;
      }
    }

    Call<ModelSubscribeUserWrapper> call =
        APIClient.getClient()
            .create(MyContentAPIInterface.class)
            .getSubscribeList(loginUser.getId(), key, ordering, pageIndex);

    call.enqueue(new Callback<ModelSubscribeUserWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelSubscribeUserWrapper> call,
          @NonNull Response<ModelSubscribeUserWrapper> response) {
        ModelSubscribeUserWrapper subscribeWrapper = response.body();

        if (subscribeWrapper != null && subscribeWrapper.getSubscribers() != null) {
          hasNextPage = subscribeWrapper.next != null && !subscribeWrapper.next.isEmpty();
          for (int i = 0; i < subscribeWrapper.getSubscribers().size(); i++) {
            ModelSubscribeUser subscribeUser = subscribeWrapper.getSubscribers().get(i);
            subscribers.add(subscribeUser);
          }
        }

        adapterSubscribe.setSubscribers(subscribers);

        if (adapterSubscribe.getSubscriptionsCount() > 0) {
          hideNoDataView();
        } else {
          showNoDataView();
        }

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelSubscribeUserWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders();
      }
    });
  }

  private void getSubscribeList() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    adapterSubscribe.showLoadMoreLoader();

    hasNextPage = false;

    Call<ModelSubscribeUserWrapper> call =
        APIClient.getClient()
            .create(MyContentAPIInterface.class)
            .getSubscribeList(loginUser.getId(), pageIndex);

    call.enqueue(new Callback<ModelSubscribeUserWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelSubscribeUserWrapper> call,
          @NonNull Response<ModelSubscribeUserWrapper> response) {
        ModelSubscribeUserWrapper userWrapper = response.body();

        if (userWrapper != null && userWrapper.getSubscribers() != null) {
          hasNextPage = userWrapper.next != null && !userWrapper.next.isEmpty();
          for (int i = 0; i < userWrapper.getSubscribers().size(); i++) {
            ModelSubscribeUser userItem = userWrapper.getSubscribers().get(i);
            if (userItem == null) {
              continue;
            }
            subscribers.add(userItem);
          }
        }

        sort(currentSort != -1 ? currentSort : 0, true);

        if (adapterSubscribe.getSubscriptionsCount() > 0) {
          hideNoDataView();
        } else {
          showNoDataView();
        }

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelSubscribeUserWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders();
      }
    });
  }

  private void showNoDataView() {
    binding.layoutNoData.setVisibility(View.VISIBLE);
  }

  private void hideNoDataView() {
    binding.layoutNoData.setVisibility(View.GONE);
  }

  private void showLoader() {
    if (!binding.dcLoader.isAnimating() && !binding.dcLoader.isShown()) {
      binding.dcLoader.playAnimation();
      binding.dcLoader.setVisibility(View.VISIBLE);
    }
  }

  private void hideLoaders() {
    if (binding.dcLoader.isShown() && binding.dcLoader.isAnimating()) {
      binding.dcLoader.setVisibility(View.GONE);
      binding.dcLoader.cancelAnimation();
    }

    if (binding.refresher.isRefreshing()) {
      binding.refresher.setRefreshing(false);
    }

    adapterSubscribe.hideLoadMoreLoader();
    adapterSubscribe.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);

    isLoading = false;
  }

  private void hideAllLoaders() {
    new CountDownTimer(1500, 1500) {
      @Override
      public void onTick(long l) {

      }

      @Override
      public void onFinish() {
        if (binding.dcLoader.isShown() && binding.dcLoader.isAnimating()) {
          binding.dcLoader.setVisibility(View.GONE);
          binding.dcLoader.cancelAnimation();
        }
      }
    }.start();

    hideLoaders();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @Override
  public void fragmentCollapsed() {
    if (getSupportActionBar() != null) {
      Objects.requireNonNull(getSupportActionBar()).show();
    }
    if (binding != null && binding.layoutSearch != null) {
      binding.layoutSearch.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void fragmentExpanded() {
    if (getSupportActionBar() != null) {
      Objects.requireNonNull(getSupportActionBar()).hide();
    }
    if (binding != null && binding.layoutSearch != null) {
      binding.layoutSearch.setVisibility(View.GONE);
    }
  }

  @Override
  public void fragmentClosed() {
    if (getSupportActionBar() != null) {
      Objects.requireNonNull(getSupportActionBar()).show();
    }
    if (binding != null && binding.layoutSearch != null) {
      binding.layoutSearch.setVisibility(View.VISIBLE);
    }
  }

  @Override public void onLikeDislikeUpdated(ModelVideo video) {

  }

  @Override public void onLikeDislikeUpdated(ModelListHeader header) {
  }

  @Override public void onLeftMenuTabChanged(int tabPosition) {

  }

  @Override public void onMediaDeleted(ModelVideo modelVideo) {
  }

  @Override public void onMediaRemovedFromFavorite(ModelVideo modelVideo) {
  }

  private boolean isAdultVideo(ModelVideo modelVideo) {
    return (modelVideo.getKinds() != null && modelVideo.getKinds()
        .equalsIgnoreCase(getString(R.string.value_share_type_19)));
  }

  private void checkIsAdult(int containerViewId, ModelVideo modelVideo) {
    if (isAdultVideo(modelVideo)) {

      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser != null) {
        if (loginUser.getAdultCertification()) {
          checkIsLiveAndOpen(containerViewId, modelVideo);
        } else {
          AdultConfirmDialog confirmDialog = new AdultConfirmDialog(mContext,
              new AdultConfirmDialog.ConfirmListener() {
                @Override public void onConfirm() {
                  //checkIsLiveAndOpen(containerViewId, modelVideo);
                  checkAdultCertification(loginUser.getId(), containerViewId, modelVideo);
                }
              });
          confirmDialog.showDialog();
        }
      } else {
        Toast.makeText(mContext, getString(R.string.login_required),
            Toast.LENGTH_SHORT).show();
        Intent loginIntent = new Intent(mContext, ActivityLogin.class);
        startActivity(loginIntent);
      }
    } else {
      checkIsLiveAndOpen(containerViewId, modelVideo);
    }
  }

  private void checkAdultCertification(int userId, int containerViewId, ModelVideo modelVideo) {
    showLoading();
    LoginAPIInterface apiInterface = APIClient.getMDCIdClient().create(LoginAPIInterface.class);

    Call<ModelAdultCertification> call =
        apiInterface.checkAdultCertification("A96", DCCastApplication.userId);
    call.enqueue(new Callback<ModelAdultCertification>() {
      @Override public void onResponse(@NonNull Call<ModelAdultCertification> call,
          @NonNull Response<ModelAdultCertification> response) {

        if (response.body() != null) {
          ModelAdultCertification result = response.body();
          if (result.getAdultCert() != null && result.getAdultCert().equalsIgnoreCase("y")) {
            String expireDate = result.getExpireDate();
            updateProfile(userId, expireDate, containerViewId, modelVideo);
            return;
          } else {
            dismissLoading();
            Toast.makeText(mContext, "Not verified", Toast.LENGTH_LONG).show();
            return;
          }
        }

        dismissLoading();
        Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
      }

      @Override public void onFailure(@NonNull Call<ModelAdultCertification> call,
          @NonNull Throwable t) {
        call.cancel();

        dismissLoading();
        Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
      }
    });
  }

  private void showLoading() {
    progressDialog.show();
  }

  private void dismissLoading() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  private void updateProfile(int userId, String expireDate, int containerViewId,
      ModelVideo modelVideo) {
    ProfileAPIInterface profileAPIInterface =
        APIClient.getClient().create(ProfileAPIInterface.class);

    HashMap<String, Object> updateValues = new HashMap<>();

    updateValues.put("user", userId);
    updateValues.put("adult_certification", true);
    updateValues.put("adult_certification_date", expireDate);

    Call<ModelProfile> call = profileAPIInterface.updateProfile(userId, updateValues);
    call.enqueue(new Callback<ModelProfile>() {
      @Override
      public void onResponse(@NonNull Call<ModelProfile> call,
          @NonNull Response<ModelProfile> response) {

        ModelProfile updatedProfile = response.body();

        if (updatedProfile != null && updatedProfile.getAdultCertification()) {
          checkIsLiveAndOpen(containerViewId, modelVideo);
          return;
        }

        dismissLoading();
        Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
      }

      @Override
      public void onFailure(@NonNull Call<ModelProfile> call, @NonNull Throwable t) {
        call.cancel();

        dismissLoading();
        Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
      }
    });
  }

  public void getMediaById(int openVideoId) {
    showLoading();
    HomeAPIInterface apiInterface = APIClient.getClient().create(HomeAPIInterface.class);
    Call<ModelVideoWrapper> call = apiInterface.getMediaById(openVideoId);
    call.enqueue(new Callback<ModelVideoWrapper>() {
      @Override public void onResponse(@NonNull Call<ModelVideoWrapper> call,
          @NonNull Response<ModelVideoWrapper> response) {
        ModelVideoWrapper headerWrapper = response.body();

        dismissLoading();

        if (headerWrapper == null
            || headerWrapper.getVideoList() == null
            || headerWrapper.getVideoList().size() == 0) {
          Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
          return;
        }

        ModelVideo openVideo = headerWrapper.getVideoList().get(0);

        if (openVideo != null) {
          checkIsAdult(R.id.container_for_subscribe_video, openVideo);
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelVideoWrapper> call, @NonNull Throwable t) {
        call.cancel();

        dismissLoading();
        Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
      }
    });
  }

  private void checkIsLiveAndOpen(int containerViewId, ModelVideo modelVideo) {
    if (modelVideo == null) {
      return;
    }

    if (modelVideo.getCategory().equalsIgnoreCase("LIVE")
        && modelVideo.getLivePassword() != null && !modelVideo.getLivePassword().isEmpty()) {
      openBottomSheetLock(modelVideo.getLivePassword(), containerViewId, modelVideo);
    } else {
      openVideoFragment(containerViewId, modelVideo);
    }
  }

  private void openVideoFragment(int containerViewId, ModelVideo modelVideo) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.addToBackStack(null);
    ft.setCustomAnimations(R.anim.slide_in_up, android.R.anim.slide_out_right);

    FragmentVideo fragmentVideo = new FragmentVideo();
    Bundle bundle = new Bundle();
    bundle.putSerializable("data", modelVideo);
    bundle.putSerializable("header_data", null);
    fragmentVideo.setArguments(bundle);

    ft.replace(containerViewId, fragmentVideo).commit();
  }

  private void openBottomSheetLock(String pin, int containerViewId, ModelVideo modelVideo) {
    BottomSheetValidatePassCode bottomSheetLock =
        BottomSheetValidatePassCode.getInstance(BottomSheetValidatePassCode.LIVE_LOCK, pin);
    bottomSheetLock.setCancelable(true);
    bottomSheetLock.setPinListener(pin1 -> {
      closeBottomSheet();
      bottomSheetLock.dismiss();
      openVideoFragment(containerViewId, modelVideo);
    });

    bottomSheetLock.show(getSupportFragmentManager(), "Custom Bottom Sheet");
  }
}
