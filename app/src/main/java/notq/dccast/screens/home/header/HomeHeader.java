package notq.dccast.screens.home.header;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.HashMap;
import java.util.Objects;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.ProfileAPIInterface;
import notq.dccast.api.login.LoginAPIInterface;
import notq.dccast.databinding.FragmentHomeHeaderBinding;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.user.ModelAdultCertification;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.profile.ModelProfile;
import notq.dccast.screens.home.video_detail.FragmentVideo;
import notq.dccast.screens.login.ActivityLogin;
import notq.dccast.screens.splash.BottomSheetValidatePassCode;
import notq.dccast.util.AdultConfirmDialog;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeHeader extends Fragment {
  private ModelListHeader headerItem;
  private FragmentHomeHeaderBinding binding;
  private ProgressDialog progressDialog;

  public HomeHeader() {
    // Required empty public constructor
  }

  public static HomeHeader getInstance(ModelListHeader listHeaderItem) {
    HomeHeader instance = new HomeHeader();
    Bundle headerData = new Bundle();
    headerData.putSerializable("headerData", listHeaderItem);
    instance.setArguments(headerData);
    return instance;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      Bundle bundle = getArguments();
      headerItem = (ModelListHeader) bundle.getSerializable("headerData");
    }
    progressDialog = new ProgressDialog(getActivity());
  }

  @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_header, container, false);
    setDatas();
    return binding.getRoot();
  }

  @SuppressLint("SetTextI18n") private void setDatas() {
    DisplayMetrics metrics = new DisplayMetrics();
    int profileImageSize = DCCastApplication.utils.pxFromDp(24);

    if (getActivity() != null) {
      getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    Glide.with(getActivity())
        .load(Util.getValidateUrl(headerItem.getMediaThumbnail()))
        .apply(
            new RequestOptions()
                .override(metrics.widthPixels, DCCastApplication.utils.pxFromDp(
                    (int) getResources().getDimension(R.dimen.home_viewpager_height)))
                .placeholder(R.drawable.ic_placeholder_header)
                .centerInside())
        .into(binding.thumbnail);

    Glide.with(getActivity())
        .load(Util.getValidateUrl(headerItem.getUser().getProfileImage()))
        .apply(
            new RequestOptions()
                .override(profileImageSize, profileImageSize)
                .placeholder(R.drawable.ic_profile_placeholder)
                .centerInside())
        .into(binding.profileImage);

    binding.profileName.setText(headerItem.getUser().nickName);

    binding.liveTitle.setText(
        DCCastApplication.utils.VIDEO_CHOOSE_TYPE.equalsIgnoreCase("Live") ? "Hit live"
            : "Hit VOD");

    if (DCCastApplication.utils.VIDEO_CHOOSE_TYPE.equalsIgnoreCase("Live")) {
      if (headerItem.getLiveLastUpdate() != null
          && Util.getTimeDiffStamp(headerItem.getLiveLastUpdate().toString()) <= 12) {
        binding.status.setVisibility(View.VISIBLE);
      } else {
        binding.status.setVisibility(View.GONE);
      }
    } else {
      binding.status.setVisibility(View.VISIBLE);
    }
    binding.status.setText("인기 " + DCCastApplication.utils.VIDEO_CHOOSE_TYPE);

    binding.container.setOnClickListener(view -> {

      if (headerItem == null) {
        return;
      }
      ModelVideo videoItem = new ModelVideo(headerItem);
      checkIsAdult(R.id.container_for_video, videoItem);
    });
  }

  private boolean isAdultVideo(ModelVideo modelVideo) {
    return (modelVideo.getKinds() != null && modelVideo.getKinds()
        .equalsIgnoreCase(getString(R.string.value_share_type_19)));
  }

  private void checkIsAdult(int containerViewId, ModelVideo modelVideo) {
    if (getActivity() == null) {
      return;
    }
    if (!Util.isNetworkConnected(getActivity())) {
      Toast.makeText(getActivity(), getString(R.string.no_internet_connection), Toast.LENGTH_LONG)
          .show();
      return;
    }
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser != null && loginUser.getLimitMobileData() && Util.checkNetworkStatus(
        getActivity())
        .equalsIgnoreCase("mobileData")) {
      Toast.makeText(getActivity(), getString(R.string.user_data_using), Toast.LENGTH_LONG)
          .show();
      return;
    }

    if (isAdultVideo(modelVideo)) {
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

  //private void openBottomSheetLock(String pin) {
  //  BottomSheetValidatePassCode bottomSheetLock =
  //      BottomSheetValidatePassCode.getInstance(BottomSheetValidatePassCode.LIVE_LOCK, pin);
  //  bottomSheetLock.setCancelable(true);
  //  bottomSheetLock.setPinListener(pin1 -> {
  //    bottomSheetLock.dismiss();
  //    openVideoFragment();
  //  });
  //
  //  bottomSheetLock.show(getFragmentManager(), "Custom Bottom Sheet");
  //}
  //
  //private void openVideoFragment() {
  //  FragmentTransaction ft = Objects.requireNonNull(getFragmentManager()).beginTransaction();
  //  ft.addToBackStack(null);
  //  ft.setCustomAnimations(R.anim.slide_in_up, android.R.anim.slide_out_right);
  //
  //  FragmentVideo fragmentVideo = new FragmentVideo();
  //  Bundle bundle = new Bundle();
  //  bundle.putSerializable("data", null);
  //  bundle.putSerializable("header_data", headerItem);
  //  fragmentVideo.setArguments(bundle);
  //
  //  ft.replace(R.id.container_for_video, fragmentVideo).commit();
  //}
}
