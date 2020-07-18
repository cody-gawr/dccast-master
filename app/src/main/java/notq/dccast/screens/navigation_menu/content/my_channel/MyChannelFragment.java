package notq.dccast.screens.navigation_menu.content.my_channel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.ProfileAPIInterface;
import notq.dccast.api.login.LoginAPIInterface;
import notq.dccast.api.my_content.MyContentAPIInterface;
import notq.dccast.databinding.FragmentMyChannelBinding;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.ModelVideoWrapper;
import notq.dccast.model.user.ModelAdultCertification;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.profile.ModelProfile;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.home.video_detail.FragmentVideo;
import notq.dccast.screens.home.view_holders.VHVideo;
import notq.dccast.screens.login.ActivityLogin;
import notq.dccast.screens.splash.BottomSheetValidatePassCode;
import notq.dccast.util.AdultConfirmDialog;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyChannelFragment extends Fragment {

  public AdapterVideos videoAdapter;
  private FragmentMyChannelBinding binding;
  private int userId;
  private int pageIndex = 1;
  private int currentSort = -1;
  private boolean isLoading = false, hasNextPage = false;
  private boolean isMe = false;

  private ArrayList<ModelVideo> channelVideos = new ArrayList<>();
  private ProgressDialog progressDialog;

  public static MyChannelFragment newInstance(int userId) {
    MyChannelFragment myFragment = new MyChannelFragment();

    Bundle args = new Bundle();
    args.putInt("userId", userId);
    myFragment.setArguments(args);

    return myFragment;
  }

  public void updateVideo(ModelVideo modelVideo) {
    if (getActivity() == null) {
      return;
    }
    getActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        if (videoAdapter != null) {
          videoAdapter.updateVideo(modelVideo);
        }
      }
    });
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      userId = getArguments().getInt("userId");
      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser != null && loginUser.getId() == userId) {
        isMe = true;
      }
    }

    progressDialog = new ProgressDialog(getActivity());
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_channel, container, false);

    init();

    showLoader();
    binding.lblVodCount.setText(getString(R.string.vod_count, 0));
    getChannelVideos();

    return binding.getRoot();
  }

  private void init() {
    videoAdapter = new AdapterVideos(this, false);
    videoAdapter.setViewType(VHVideo.TYPE_CHANNEL);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.setNestedScrollingEnabled(false);
    binding.recyclerView.setAdapter(videoAdapter);

    if (isMe) {
      binding.layoutLive.setVisibility(View.GONE);
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
                getChannelVideos();
              }
            }
          }
        }
      });
    } else {
      binding.layoutLive.setVisibility(View.VISIBLE);
      getLastLiveVideo();
    }

    binding.refresher.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
    binding.refresher.setOnRefreshListener(() -> {
      binding.refresher.setRefreshing(false);
      prepareRecyclerViewForNewItems();
      showLoader();
      getChannelVideos();
    });
  }

  @SuppressLint("SetTextI18n") private void setLiveVideo(ModelVideo video) {
    if (video == null) {
      binding.lblNoVideo.setVisibility(View.VISIBLE);
      binding.container.setVisibility(View.GONE);
      return;
    }
    binding.lblNoVideo.setVisibility(View.GONE);
    binding.container.setVisibility(View.VISIBLE);
    DisplayMetrics metrics = new DisplayMetrics();
    int profileImageSize = DCCastApplication.utils.pxFromDp(24);

    if (getActivity() != null) {
      getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    Glide.with(getActivity())
        .load(Util.getValidateUrl(video.getMediaThumbnail()))
        .apply(
            new RequestOptions()
                .override(metrics.widthPixels, DCCastApplication.utils.pxFromDp(
                    (int) getResources().getDimension(R.dimen.home_viewpager_height)))
                .placeholder(R.drawable.ic_placeholder_header)
                .centerInside())
        .into(binding.thumbnail);

    Glide.with(getActivity())
        .load(Util.getValidateUrl(video.getUser().getProfileImage()))
        .apply(
            new RequestOptions()
                .override(profileImageSize, profileImageSize)
                .placeholder(R.drawable.ic_profile_placeholder)
                .centerInside())
        .into(binding.profileImage);

    binding.profileName.setText(video.getUser().getNickName());
    binding.liveTitle.setText(video.getTitle());
    binding.status.setText("인기 LIVE");

    binding.container.setOnClickListener(view -> {

      if (video == null) {
        return;
      }

      checkIsAdult(R.id.container_for_channel_video, video);
    });
  }

  public void getLastLiveVideo() {
    MyContentAPIInterface apiInterface = APIClient.getClient().create(MyContentAPIInterface.class);
    apiInterface.getMyChannelLastLive(userId, "-createdat", "Live", 1)
        .enqueue(new Callback<ModelVideoWrapper>() {
          @Override
          public void onResponse(@NonNull Call<ModelVideoWrapper> call,
              @NonNull Response<ModelVideoWrapper> response) {
            ModelVideoWrapper videoWrapper = response.body();

            if (videoWrapper != null
                && videoWrapper.videoList != null
                && videoWrapper.videoList.size() > 0) {

              ModelVideo video = videoWrapper.videoList.get(0);
              if (video.getLiveLastUpdate() != null
                  && Util.getTimeDiffStamp(video.getLiveLastUpdate().toString()) <= 32) {
                setLiveVideo(video);
                return;
              }
            }

            setLiveVideo(null);
          }

          @Override
          public void onFailure(@NonNull Call<ModelVideoWrapper> call, @NonNull Throwable t) {
            call.cancel();

            setLiveVideo(null);
          }
        });
  }

  public void search(String keyword) {
    searchRequest(keyword);
  }

  public void sort(String keyword, int sort, boolean force) {
    if (sort == currentSort && !force) {
      return;
    }
    currentSort = sort;
    searchRequest(keyword);
  }

  private void prepareRecyclerViewForNewItems() {
    if (videoAdapter != null) {
      videoAdapter.removeVideoItems();
    }
    channelVideos = new ArrayList<>();
    isLoading = false;
    hasNextPage = false;
    pageIndex = 1;
  }

  private void getChannelVideos() {
    if (videoAdapter != null) {
      videoAdapter.showLoadMoreLoader();
    }

    hasNextPage = false;

    Call<ModelVideoWrapper> call =
        APIClient.getClient()
            .create(MyContentAPIInterface.class)
            .getMyChannelVideo(userId, "VOD", 20, pageIndex);

    call.enqueue(new Callback<ModelVideoWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelVideoWrapper> call,
          @NonNull Response<ModelVideoWrapper> response) {
        ModelVideoWrapper videoWrapper = response.body();

        if (videoWrapper != null && videoWrapper.videoList != null) {
          hasNextPage = videoWrapper.next != null && !videoWrapper.next.isEmpty();
          for (int i = 0; i < videoWrapper.videoList.size(); i++) {
            ModelVideo video = videoWrapper.videoList.get(i);
            channelVideos.add(video);
          }
        }

        videoAdapter.setVideos(channelVideos);

        binding.lblVodCount.setText(getString(R.string.vod_count, videoWrapper.getCount()));

        if (videoAdapter.getVideoCount() > 0) {
          hideNoDataView();
        } else {
          showNoDataView();
        }

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelVideoWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders();
      }
    });
  }

  private void searchRequest(String key) {
    prepareRecyclerViewForNewItems();
    showLoader();

    String ordering = "-createdat";
    switch (currentSort) {
      case 0: {
        ordering = "-createdat";
        break;
      }
      case 1: {
        ordering = "-like";
        break;
      }
      case 2: {
        ordering = "-views";
        break;
      }
    }

    Call<ModelVideoWrapper> call =
        APIClient.getClient()
            .create(MyContentAPIInterface.class)
            .searchRequest(userId, key, ordering, "VOD", 20, pageIndex);

    call.enqueue(new Callback<ModelVideoWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelVideoWrapper> call,
          @NonNull Response<ModelVideoWrapper> response) {
        ModelVideoWrapper videoWrapper = response.body();

        if (videoWrapper != null && videoWrapper.videoList != null) {
          hasNextPage = videoWrapper.next != null && !videoWrapper.next.isEmpty();
          for (int i = 0; i < videoWrapper.videoList.size(); i++) {
            ModelVideo video = videoWrapper.videoList.get(i);
            channelVideos.add(video);
          }
        }

        videoAdapter.setVideos(channelVideos);

        if (videoAdapter.getVideoCount() > 0) {
          hideNoDataView();
        } else {
          showNoDataView();
        }

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelVideoWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders();
      }
    });
  }

  private void showLoader() {
    if (!binding.dcLoader.isAnimating() && !binding.dcLoader.isShown()) {
      binding.dcLoader.playAnimation();
      binding.dcLoader.setVisibility(View.VISIBLE);
    }
  }

  private void showNoDataView() {
    binding.layoutNoData.setVisibility(View.VISIBLE);
  }

  private void hideNoDataView() {
    binding.layoutNoData.setVisibility(View.GONE);
  }

  private void hideLoaders() {
    if (binding.dcLoader.isShown() && binding.dcLoader.isAnimating()) {
      binding.dcLoader.setVisibility(View.GONE);
      binding.dcLoader.cancelAnimation();
    }

    if (binding.refresher.isRefreshing()) {
      binding.refresher.setRefreshing(false);
    }

    videoAdapter.hideLoadMoreLoader();
    videoAdapter.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);

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
          AdultConfirmDialog confirmDialog = new AdultConfirmDialog(getActivity(),
              new AdultConfirmDialog.ConfirmListener() {
                @Override public void onConfirm() {
                  //checkIsLiveAndOpen(containerViewId, modelVideo);
                  checkAdultCertification(loginUser.getId(), containerViewId, modelVideo);
                }
              });
          confirmDialog.showDialog();
        }
      } else {
        Toast.makeText(getActivity(), getString(R.string.login_required),
            Toast.LENGTH_SHORT).show();
        Intent loginIntent = new Intent(getActivity(), ActivityLogin.class);
        startActivity(loginIntent);
      }
    } else {
      checkIsLiveAndOpen(containerViewId, modelVideo);
    }
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

  private void showLoading() {
    progressDialog.show();
  }

  private void dismissLoading() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
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
            Toast.makeText(getActivity(), "Not verified", Toast.LENGTH_LONG).show();
            return;
          }
        }

        dismissLoading();
        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
      }

      @Override public void onFailure(@NonNull Call<ModelAdultCertification> call,
          @NonNull Throwable t) {
        call.cancel();

        dismissLoading();
        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
      }
    });
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
        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
      }

      @Override
      public void onFailure(@NonNull Call<ModelProfile> call, @NonNull Throwable t) {
        call.cancel();

        dismissLoading();
        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
      }
    });
  }

  private void openVideoFragment(int containerViewId, ModelVideo modelVideo) {
    FragmentTransaction ft = getFragmentManager().beginTransaction();
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

    bottomSheetLock.show(getFragmentManager(), "Custom Bottom Sheet");
  }

  private void closeBottomSheet() {
    hideKeyboard();
  }

  private void hideKeyboard() {
    InputMethodManager imm =
        (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
    Objects.requireNonNull(imm).hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
  }
}
