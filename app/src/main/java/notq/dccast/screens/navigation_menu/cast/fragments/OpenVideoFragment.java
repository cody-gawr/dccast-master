package notq.dccast.screens.navigation_menu.cast.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import java.util.HashMap;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.HomeAPIInterface;
import notq.dccast.api.home.ProfileAPIInterface;
import notq.dccast.api.login.LoginAPIInterface;
import notq.dccast.api.my_content.MyContentAPIInterface;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.ModelVideoWrapper;
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

public class OpenVideoFragment extends Fragment {

  protected ProgressDialog progressDialog;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    progressDialog = new ProgressDialog(getActivity());
    progressDialog.setCancelable(false);
  }

  protected boolean isAdultVideo(ModelVideo modelVideo) {
    return (modelVideo.getKinds() != null && modelVideo.getKinds()
        .equalsIgnoreCase(getString(R.string.value_share_type_19)));
  }

  protected void checkIsAdult(int containerViewId, ModelVideo modelVideo) {
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

  protected void checkAdultCertification(int userId, int containerViewId, ModelVideo modelVideo) {
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

  protected void showLoading() {
    progressDialog.show();
  }

  protected void dismissLoading() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  protected void updateProfile(int userId, String expireDate, int containerViewId,
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

  public void getLastLiveVideo(int containerId, int userId) {
    showLoading();
    MyContentAPIInterface apiInterface = APIClient.getClient().create(MyContentAPIInterface.class);
    apiInterface.getMyChannelLastLive(userId, "-createdat", "Live", 1)
        .enqueue(new Callback<ModelVideoWrapper>() {
          @Override
          public void onResponse(@NonNull Call<ModelVideoWrapper> call,
              @NonNull Response<ModelVideoWrapper> response) {
            ModelVideoWrapper videoWrapper = response.body();

            dismissLoading();

            if (videoWrapper != null
                && videoWrapper.videoList != null
                && videoWrapper.videoList.size() > 0) {

              ModelVideo video = videoWrapper.videoList.get(0);
              if (video.getLiveLastUpdate() != null
                  && Util.getTimeDiffStamp(video.getLiveLastUpdate().toString()) <= 12) {

                if (video != null) {
                  checkIsAdult(containerId, video);
                }
                return;
              }
            }
          }

          @Override
          public void onFailure(@NonNull Call<ModelVideoWrapper> call, @NonNull Throwable t) {
            call.cancel();

            dismissLoading();
          }
        });
  }

  protected void getMediaById(int containerId, int openVideoId) {
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
          Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
          return;
        }

        ModelVideo openVideo = headerWrapper.getVideoList().get(0);

        if (openVideo != null) {
          checkIsAdult(containerId, openVideo);
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelVideoWrapper> call, @NonNull Throwable t) {
        call.cancel();

        dismissLoading();
        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
      }
    });
  }

  protected void checkIsLiveAndOpen(int containerViewId, ModelVideo modelVideo) {
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

  protected void openVideoFragment(int containerViewId, ModelVideo modelVideo) {
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

  protected void openBottomSheetLock(String pin, int containerViewId, ModelVideo modelVideo) {
    BottomSheetValidatePassCode bottomSheetLock =
        BottomSheetValidatePassCode.getInstance(BottomSheetValidatePassCode.LIVE_LOCK, pin);
    bottomSheetLock.setCancelable(true);
    bottomSheetLock.setPinListener(pin1 -> {
      hideKeyboard();
      bottomSheetLock.dismiss();
      openVideoFragment(containerViewId, modelVideo);
    });

    bottomSheetLock.show(getChildFragmentManager(), "Custom Bottom Sheet");
  }

  protected void hideKeyboard() {

  }
}
