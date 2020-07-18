package notq.dccast.screens.home.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.HashMap;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.ProfileAPIInterface;
import notq.dccast.api.login.LoginAPIInterface;
import notq.dccast.databinding.VhPopularItemBinding;
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

public class AdapterPopularVideos
    extends RecyclerView.Adapter<AdapterPopularVideos.VHPopularItems> {

  private ArrayList<ModelVideo> videos;
  private Fragment fragment;
  private Context context;
  private FragmentManager fragmentManager;
  private ProgressDialog progressDialog;

  public AdapterPopularVideos(Fragment fragment) {
    this.fragment = fragment;
    this.context = fragment.getContext();
    this.fragmentManager = fragment.getChildFragmentManager();
    videos = new ArrayList<>();
  }

  public void addVideo(ModelVideo modelVideo) {
    videos.add(modelVideo);
    notifyItemInserted(getItemCount() - 1);
  }

  public void removeAllItems() {
    if (videos != null) {
      videos.clear();
    }

    notifyDataSetChanged();
  }

  public int getVideoCount() {
    return videos == null ? 0 : videos.size();
  }

  public ArrayList<ModelVideo> getVideos() {
    return videos;
  }

  public void setVideos(ArrayList<ModelVideo> videos) {
    this.videos = videos;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public VHPopularItems onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(fragment.getContext());

    VhPopularItemBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.vh_popular_item, parent, false);
    return new VHPopularItems(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull VHPopularItems holder, int position) {
    holder.setData(videos.get(position), position);
  }

  @Override
  public int getItemCount() {
    return videos == null ? 0 : videos.size();
  }

  private boolean isAdultVideo(ModelVideo modelVideo) {
    return (modelVideo.getKinds() != null && modelVideo.getKinds()
        .equalsIgnoreCase(context.getString(R.string.value_share_type_19)));
  }

  private void checkIsAdult(int containerViewId, ModelVideo modelVideo) {
    if (isAdultVideo(modelVideo)) {

      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser != null) {
        if (loginUser.getAdultCertification()) {
          checkIsLiveAndOpen(containerViewId, modelVideo);
        } else {
          AdultConfirmDialog confirmDialog = new AdultConfirmDialog(context,
              new AdultConfirmDialog.ConfirmListener() {
                @Override public void onConfirm() {
                  //checkIsLiveAndOpen(containerViewId, modelVideo);
                  checkAdultCertification(loginUser.getId(), containerViewId, modelVideo);
                }
              });
          confirmDialog.showDialog();
        }
      } else {
        Toast.makeText(context, context.getString(R.string.login_required),
            Toast.LENGTH_SHORT).show();
        Intent loginIntent = new Intent(context, ActivityLogin.class);
        context.startActivity(loginIntent);
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
            Toast.makeText(context, "Not verified", Toast.LENGTH_LONG).show();
            return;
          }
        }

        dismissLoading();
        Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_LONG).show();
      }

      @Override public void onFailure(@NonNull Call<ModelAdultCertification> call,
          @NonNull Throwable t) {
        call.cancel();

        dismissLoading();
        Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_LONG).show();
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
        Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_LONG).show();
      }

      @Override
      public void onFailure(@NonNull Call<ModelProfile> call, @NonNull Throwable t) {
        call.cancel();

        dismissLoading();
        Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_LONG).show();
      }
    });
  }

  private void openVideoFragment(int containerViewId, ModelVideo modelVideo) {
    FragmentTransaction ft = fragmentManager.beginTransaction();
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
      bottomSheetLock.dismiss();
      openVideoFragment(containerViewId, modelVideo);
    });

    bottomSheetLock.show(fragmentManager, "Custom Bottom Sheet");
  }

  class VHPopularItems extends RecyclerView.ViewHolder {

    private VhPopularItemBinding binding;

    public VHPopularItems(@NonNull VhPopularItemBinding binding) {
      super(binding.getRoot());

      this.binding = binding;

      DisplayMetrics displayMetrics = new DisplayMetrics();
      fragment.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
      int width = displayMetrics.widthPixels;
      int height = displayMetrics.heightPixels;

      itemView.setLayoutParams(new RecyclerView.LayoutParams((int) (width * 0.8),
          RecyclerView.LayoutParams.WRAP_CONTENT));
    }

    void setData(ModelVideo video, int position) {
      DisplayMetrics metrics = new DisplayMetrics();
      int profileImageSize = DCCastApplication.utils.pxFromDp(24);

      if (fragment.getActivity() != null) {
        fragment.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
      }

      Glide.with(fragment.getContext())
          .load(Util.getValidateUrl(video.getMediaThumbnail()))
          .apply(
              new RequestOptions()
                  .override(metrics.widthPixels, DCCastApplication.utils.pxFromDp(
                      (int) fragment.getResources().getDimension(R.dimen.home_popular_height)))
                  .placeholder(R.drawable.ic_placeholder_header)
                  .centerInside())
          .into(binding.thumbnail);

      Glide.with(fragment.getContext())
          .load(Util.getValidateUrl(video.getUser().getProfileImage()))
          .apply(
              new RequestOptions()
                  .override(profileImageSize, profileImageSize)
                  .placeholder(R.drawable.ic_profile_placeholder)
                  .centerInside())
          .into(binding.profileImage);

      if (video.getUser().isVip && video.getUser().isVipActive) {
        binding.vodCrown.setVisibility(View.VISIBLE);
      } else {
        binding.vodCrown.setVisibility(View.GONE);
      }

      binding.profileName.setText(video.getUser().nickName);
      binding.liveTitle.setText(video.getTitle());
      binding.status.setText("인기 " + DCCastApplication.utils.VIDEO_CHOOSE_TYPE);

      binding.container.setOnClickListener(view -> {

        if (video == null) {
          return;
        }

        checkIsAdult(R.id.container_for_video, video);
      });
    }
  }
}
