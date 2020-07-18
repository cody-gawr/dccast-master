package notq.dccast.screens.home.view_holders;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import java.util.HashMap;
import java.util.Objects;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.api.home.ProfileAPIInterface;
import notq.dccast.api.login.LoginAPIInterface;
import notq.dccast.databinding.VhHomeVideoBinding;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.ModelVideoWrapper;
import notq.dccast.model.user.ModelAdultCertification;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.profile.ModelProfile;
import notq.dccast.screens.home.interfaces.RelatedItemListener;
import notq.dccast.screens.home.video_detail.BottomSheetViewMore;
import notq.dccast.screens.home.video_detail.FragmentVideo;
import notq.dccast.screens.login.ActivityLogin;
import notq.dccast.screens.splash.BottomSheetValidatePassCode;
import notq.dccast.util.AdultConfirmDialog;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VHVideo extends RecyclerView.ViewHolder {
  public static final int TYPE_RELATED = 0;
  public static final int TYPE_HOME = 1;
  public static final int TYPE_SEARCH = 2;
  public static final int TYPE_FAV = 3;
  public static final int TYPE_CHANNEL = 4;
  public static final int TYPE_RECENT = 5;
  public static final int TYPE_CONTENT = 6;
  public static final int TYPE_GROUP = 7;
  public static final int TYPE_MY_LIVE = 8;
  public static final int TYPE_POPULAR = 9;

  private int type;

  private Context context;
  private FragmentManager fragmentManager;
  private VhHomeVideoBinding binding;
  private RelatedItemListener onClickListener;
  private ModelVideo modelVideo;
  private ItemFetchedListener fetchedListener;

  private ProgressDialog progressDialog;

  public VHVideo(Context context, FragmentManager fragmentManager,
      @NonNull VhHomeVideoBinding binding) {
    super(binding.getRoot());
    this.context = context;
    this.fragmentManager = fragmentManager;
    this.binding = binding;

    progressDialog = new ProgressDialog(context);
  }

  public void setVideoItem(int type, ModelVideo item) {
    this.modelVideo = item;
    this.type = type;
    setParams(type, item);
    setItemData(type, item);
  }

  public void setVideoItem(int type, ModelVideo item, RelatedItemListener onClickListener) {
    this.modelVideo = item;
    this.type = type;
    setParams(type, item);
    setItemData(type, item);
    this.onClickListener = onClickListener;
  }

  public void setVideoItem(int type, ModelVideo item, ItemFetchedListener fetchListener) {
    this.modelVideo = item;
    this.type = type;
    setParams(type, item);
    setItemData(type, item);
    this.fetchedListener = fetchListener;
  }

  private void setParams(int type, ModelVideo modelVideo) {
    switch (type) {
      case TYPE_FAV:
      case TYPE_CHANNEL:
      case TYPE_RECENT:
      case TYPE_SEARCH:
      case TYPE_GROUP:
      case TYPE_MY_LIVE:
      case TYPE_POPULAR:
      case TYPE_RELATED: {
        prepareLayoutForSingleColumn();
        break;
      }

      case TYPE_CONTENT: {
        prepareLayoutForHorizontalView();
        break;
      }

      case TYPE_HOME: {
        if (modelVideo.getCategory().equalsIgnoreCase("LIVE")) {
          prepareLayoutForSingleColumn();
        } else {
          prepareLayoutForMultipleColumn();
        }

        break;
      }
    }

    itemView.setOnClickListener(view -> {
      switch (type) {
        case TYPE_SEARCH: {
          checkIsAdult(R.id.container_for_video_search, modelVideo);
          break;
        }

        case TYPE_POPULAR:
        case TYPE_HOME: {
          checkIsAdult(R.id.container_for_video, modelVideo);
          break;
        }

        case TYPE_RELATED: {
          onClickListener.onRelatedItemClicked(modelVideo);
          break;
        }

        case TYPE_FAV: {
          checkIsAdult(R.id.container_for_fav_video, modelVideo);
          break;
        }

        case TYPE_CHANNEL: {
          checkIsAdult(R.id.container_for_channel_video, modelVideo);
          break;
        }

        case TYPE_RECENT: {
          checkIsAdult(R.id.container_for_recent_video, modelVideo);
          break;
        }

        case TYPE_CONTENT: {
          checkIsAdult(R.id.container_for_content_video, modelVideo);
          break;
        }

        case TYPE_GROUP: {
          checkIsAdult(R.id.container_for_group_video, modelVideo);
          break;
        }

        case TYPE_MY_LIVE: {
          checkIsAdult(R.id.container_for_my_live_video, modelVideo);
          break;
        }
      }
    });
  }

  private boolean isAdultVideo(ModelVideo modelVideo) {
    return (modelVideo.getKinds() != null && modelVideo.getKinds()
        .equalsIgnoreCase(context.getString(R.string.value_share_type_19)));
  }

  private void checkIsAdult(int containerViewId, ModelVideo modelVideo) {
    if (!Util.isNetworkConnected(context)) {
      Toast.makeText(context, context.getString(R.string.no_internet_connection), Toast.LENGTH_LONG)
          .show();
      return;
    }
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser != null && loginUser.getLimitMobileData() && Util.checkNetworkStatus(context)
        .equalsIgnoreCase("mobileData")) {
      Toast.makeText(context, context.getString(R.string.user_data_using), Toast.LENGTH_LONG)
          .show();
      return;
    }
    if (isAdultVideo(modelVideo)) {
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
    if (!Util.isNetworkConnected(context)) {
      Toast.makeText(context, context.getString(R.string.no_internet_connection), Toast.LENGTH_LONG)
          .show();
      return;
    }
    FragmentTransaction ft = fragmentManager.beginTransaction();
    ft.setCustomAnimations(R.anim.slide_in_up, android.R.anim.slide_out_right);

    FragmentVideo fragmentVideo = new FragmentVideo();
    Bundle bundle = new Bundle();
    bundle.putSerializable("data", modelVideo);
    bundle.putBoolean("home", this.type == TYPE_HOME);
    bundle.putSerializable("header_data", null);
    fragmentVideo.setArguments(bundle);
    ft.replace(containerViewId, fragmentVideo);
    ft.addToBackStack(null);
    ft.commit();

    fragmentManager.executePendingTransactions();
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

    bottomSheetLock.show(fragmentManager, "Custom Bottom Sheet");
  }

  private void closeBottomSheet() {
    hideKeyboard();
  }

  private void hideKeyboard() {
    InputMethodManager imm =
        (InputMethodManager) this.context.getSystemService(Activity.INPUT_METHOD_SERVICE);
    Objects.requireNonNull(imm).hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
  }

  private void prepareLayoutForHorizontalView() {
    FrameLayout.LayoutParams params =
        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);

    FrameLayout.LayoutParams rootParams =
        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);

    binding.liveLayout.setVisibility(View.VISIBLE);
    binding.liveContent.setVisibility(View.GONE);
    binding.vodLayout.setVisibility(View.GONE);
    binding.liveHorizontalTitle.setVisibility(View.VISIBLE);

    int marginHorizontal = DCCastApplication.utils.pxFromDp(12);
    int marginVertical = DCCastApplication.utils.pxFromDp(8);
    params.setMargins(marginHorizontal, marginVertical, marginHorizontal, marginVertical);

    binding.liveLayout.setLayoutParams(params);
    binding.rootLayout.setLayoutParams(rootParams);
  }

  private void prepareLayoutForSingleColumn() {
    FrameLayout.LayoutParams params =
        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);

    binding.liveLayout.setVisibility(View.VISIBLE);
    binding.vodLayout.setVisibility(View.GONE);

    int marginHorizontal = DCCastApplication.utils.pxFromDp(12);
    int marginVertical = DCCastApplication.utils.pxFromDp(8);
    params.setMargins(marginHorizontal, marginVertical, marginHorizontal, marginVertical);

    binding.liveLayout.setLayoutParams(params);
  }

  private void prepareLayoutForMultipleColumn() {
    FrameLayout.LayoutParams params =
        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);

    binding.liveLayout.setVisibility(View.GONE);
    binding.vodLayout.setVisibility(View.VISIBLE);

    int marginHorizontal = DCCastApplication.utils.pxFromDp(6);
    int marginVertical = DCCastApplication.utils.pxFromDp(8);
    params.setMargins(marginHorizontal, marginVertical, marginHorizontal, marginVertical);

    binding.vodLayout.setLayoutParams(params);
  }

  private void setItemData(int type, ModelVideo item) {
    switch (type) {
      case TYPE_HOME: {
        if (item.getCategory().equalsIgnoreCase("LIVE")) {
          binding.liveThumbnail.setVisibility(View.VISIBLE);

          int width = DCCastApplication.utils.pxFromDp(155);
          int height = DCCastApplication.utils.pxFromDp(100);

          Glide.with(context)
              .load(Util.getValidateUrl(item.getMediaThumbnail()))
              .apply(
                  new RequestOptions()
                      .override(width, height)
                      .placeholder(R.drawable.ic_placeholder_video)
                      .centerCrop())
              .into(binding.liveThumbnail);

          binding.liveDuration.setText(String.valueOf(item.getLiveMember()));
          binding.liveTitle.setText(item.getTitle());
          binding.liveChannel.setText(item.getKinds());
          if (item.getMediaCategory() != null) {
            binding.liveCategory.setText(item.getMediaCategory().getName());
          }

          if (modelVideo.getLivePassword() != null && !modelVideo.getLivePassword().isEmpty()) {
            binding.vodLockInlive.setVisibility(View.VISIBLE);
          } else {
            binding.vodLockInlive.setVisibility(View.GONE);
          }

          if (isAdultVideo(modelVideo)) {
            binding.live18.setVisibility(View.VISIBLE);
          } else {
            binding.live18.setVisibility(View.GONE);
          }

          if (modelVideo.getUser().isVip && modelVideo.getUser().isVipActive) {
            binding.vodCrown.setVisibility(View.VISIBLE);
          } else {
            binding.vodCrown.setVisibility(View.GONE);
          }

          if (modelVideo.getLiveLastUpdate() != null
              && Util.getTimeDiffStamp(modelVideo.getLiveLastUpdate().toString()) <= 12) {
            binding.liveStatus.setVisibility(View.VISIBLE);
          } else {
            binding.liveStatus.setVisibility(View.GONE);
          }

          binding.liveDuration.setText(String.valueOf(item.getViews()));
        } else {
          binding.vodThumbnail.setVisibility(View.VISIBLE);
          DisplayMetrics displayMetrics = new DisplayMetrics();
          ((AppCompatActivity) context).getWindowManager()
              .getDefaultDisplay()
              .getMetrics(displayMetrics);

          int width = displayMetrics.widthPixels;
          int height = DCCastApplication.utils.pxFromDp(100);

          Glide.with(context)
              .load(Util.getValidateUrl(item.getMediaThumbnail()))
              .apply(
                  new RequestOptions()
                      .override(width, height)
                      .placeholder(R.drawable.ic_placeholder_video)
                      .centerCrop())
              .listener(new RequestListener<Drawable>() {
                @Override public boolean onLoadFailed(@Nullable GlideException e, Object model,
                    Target<Drawable> target, boolean isFirstResource) {
                  binding.vodProgress.setVisibility(View.GONE);
                  return false;
                }

                @Override public boolean onResourceReady(Drawable resource, Object model,
                    Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                  binding.vodProgress.setVisibility(View.GONE);
                  return false;
                }
              })
              .into(binding.vodThumbnail);

          if (item.getDuration() > 0) {
            binding.vodDurationContainer.setVisibility(View.VISIBLE);
            binding.vodDuration.setText(DateUtils.formatElapsedTime(item.getDuration()));
          } else {
            binding.vodDurationContainer.setVisibility(View.GONE);
          }

          binding.liveStatus.setVisibility(View.GONE);

          if (modelVideo.getUser().isVip && modelVideo.getUser().isVipActive) {
            binding.vodCrown.setVisibility(View.VISIBLE);
          } else {
            binding.vodCrown.setVisibility(View.GONE);
          }

          if (modelVideo.getLivePassword() != null && !modelVideo.getLivePassword().isEmpty()) {
            binding.vodLock.setVisibility(View.VISIBLE);
          } else {
            binding.vodLock.setVisibility(View.GONE);
          }

          if (isAdultVideo(modelVideo)) {
            binding.vod18.setVisibility(View.VISIBLE);
          } else {
            binding.vod18.setVisibility(View.GONE);
          }

          binding.vodTitle.setText(item.getTitle());
          binding.vodChannel.setText(item.getKinds());
          if (item.getMediaCategory() != null) {
            binding.vodCategory.setText(item.getMediaCategory().getName());
          }
          binding.vodViewCount.setText(String.valueOf(String.valueOf(item.getViews())));
        }
        break;
      }

      case TYPE_FAV:
      case TYPE_CHANNEL:
      case TYPE_RECENT:
      case TYPE_SEARCH:
      case TYPE_GROUP:
      case TYPE_MY_LIVE:
      case TYPE_POPULAR:
      case TYPE_RELATED: {
        if (item.isNeedFetchById()) {
          getVideoById(item.id);
        } else {
          displayVideo();
        }
        break;
      }

      case TYPE_CONTENT: {
        binding.liveThumbnail.setVisibility(View.VISIBLE);

        int width = DCCastApplication.utils.pxFromDp(155);
        int height = DCCastApplication.utils.pxFromDp(100);

        Glide.with(context)
            .load(Util.getValidateUrl(item.getMediaThumbnail()))
            .apply(
                new RequestOptions()
                    .override(width, height)
                    .placeholder(R.drawable.ic_placeholder_video)
                    .centerCrop())
            .into(binding.liveThumbnail);

        if (item.getCategory().equalsIgnoreCase("LIVE")) {
          Drawable img = context.getResources().getDrawable(R.drawable.ic_live_count);
          binding.liveDuration.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
          binding.liveDuration.setText(String.valueOf(item.getLiveMember()));
          if (modelVideo.getLivePassword() != null && !modelVideo.getLivePassword().isEmpty()) {
            binding.vodLockInlive.setVisibility(View.VISIBLE);
          } else {
            binding.vodLockInlive.setVisibility(View.GONE);
          }

          if (modelVideo.getUser().isVip && modelVideo.getUser().isVipActive) {
            binding.liveCrown.setVisibility(View.VISIBLE);
          } else {
            binding.liveCrown.setVisibility(View.GONE);
          }

          if (item.getLiveLastUpdate() != null
              && Util.getTimeDiffStamp(item.getLiveLastUpdate().toString()) <= 12) {
            binding.liveStatus.setVisibility(View.VISIBLE);
          } else {
            binding.liveStatus.setVisibility(View.GONE);
          }
        } else {
          binding.liveDuration.setText(DateUtils.formatElapsedTime(item.getDuration()));
          binding.liveDuration.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
          if (modelVideo.getLivePassword() != null && !modelVideo.getLivePassword().isEmpty()) {
            binding.vodLock.setVisibility(View.VISIBLE);
          } else {
            binding.vodLock.setVisibility(View.GONE);
          }
          binding.liveStatus.setVisibility(View.GONE);

          if (modelVideo.getUser().isVip && modelVideo.getUser().isVipActive) {
            binding.liveCrown.setVisibility(View.VISIBLE);
          } else {
            binding.liveCrown.setVisibility(View.GONE);
          }
        }

        if (isAdultVideo(item)) {
          binding.live18.setVisibility(View.VISIBLE);
        } else {
          binding.live18.setVisibility(View.GONE);
        }

        binding.liveHorizontalTitle.setText(item.getTitle());

        break;
      }
    }

    final boolean isVod =
        (modelVideo.getCategory() != null && modelVideo.getCategory().equalsIgnoreCase("VOD"));

    if (binding.btnLiveMore != null) {
      binding.btnLiveMore.setOnClickListener(
          v -> {
            boolean isMe = false;
            if (LoginService.getLoginUser() != null) {
              if (LoginService.getLoginUser().getId() == modelVideo.getUserId()) {
                isMe = true;
              }
            }

            BottomSheetViewMore.setContext(context);
            BottomSheetViewMore bottomSheetDialog =
                BottomSheetViewMore.getInstance(isVod, isMe, modelVideo);
            if (type == TYPE_CONTENT || type == TYPE_RECENT) {
              bottomSheetDialog.setIgnoreEdit(true);
            }
            if (type == TYPE_FAV) {
              bottomSheetDialog.setAlwaysRemoveFavorite(true);
            }
            if (type == TYPE_CHANNEL) {
              bottomSheetDialog.setIgnoreMyChannel(true);
            }
            bottomSheetDialog.show(fragmentManager, "Custom Bottom Sheet");
          });
    }

    if (binding.btnVodMore != null) {
      binding.btnVodMore.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          boolean isMe = false;
          if (LoginService.getLoginUser() != null) {
            if (LoginService.getLoginUser().getId() == modelVideo.getUserId()) {
              isMe = true;
            }
          }

          BottomSheetViewMore.setContext(context);
          BottomSheetViewMore bottomSheetDialog =
              BottomSheetViewMore.getInstance(isVod, isMe, modelVideo);
          if (type == TYPE_FAV) {
            bottomSheetDialog.setAlwaysRemoveFavorite(true);
          }
          if (type == TYPE_CHANNEL) {
            bottomSheetDialog.setIgnoreMyChannel(true);
          }
          bottomSheetDialog.show(fragmentManager, "Custom Bottom Sheet");
        }
      });
    }
  }

  private void getVideoById(int videoId) {
    binding.layoutLoad.setVisibility(View.VISIBLE);

    Call<ModelVideoWrapper> call =
        APIClient.getClient().create(CastListAPIInterface.class).getVideoById(videoId);

    call.enqueue(new retrofit2.Callback<ModelVideoWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelVideoWrapper> call,
          @NonNull Response<ModelVideoWrapper> response) {
        ModelVideoWrapper mediaWrapper = response.body();

        if (mediaWrapper != null && mediaWrapper.videoList != null) {
          if (mediaWrapper.videoList.size() > 0) {
            modelVideo = mediaWrapper.videoList.get(0);
            if (fetchedListener != null) {
              fetchedListener.itemFetched(modelVideo);
            }
            displayVideo();
          }
        }

        binding.layoutLoad.setVisibility(View.GONE);
      }

      @Override
      public void onFailure(@NonNull Call<ModelVideoWrapper> call, @NonNull Throwable t) {
        call.cancel();

        binding.layoutLoad.setVisibility(View.GONE);
      }
    });
  }

  private void displayVideo() {
    binding.liveThumbnail.setVisibility(View.VISIBLE);

    int width = DCCastApplication.utils.pxFromDp(155);
    int height = DCCastApplication.utils.pxFromDp(100);

    Glide.with(context)
        .load(Util.getValidateUrl(modelVideo.getMediaThumbnail()))
        .apply(
            new RequestOptions()
                .override(width, height)
                .placeholder(R.drawable.ic_placeholder_video)
                .centerCrop())
        .into(binding.liveThumbnail);

    if (modelVideo.getCategory().equalsIgnoreCase("LIVE")) {
      Drawable img = context.getResources().getDrawable(R.drawable.ic_live_count);
      binding.liveDuration.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
      binding.liveDuration.setText(String.valueOf(modelVideo.getLiveMember()));
      if (modelVideo.getLivePassword() != null && !modelVideo.getLivePassword().isEmpty()) {
        binding.vodLockInlive.setVisibility(View.VISIBLE);
      } else {
        binding.vodLockInlive.setVisibility(View.GONE);
      }

      if (modelVideo.getLiveLastUpdate() != null
          && Util.getTimeDiffStamp(modelVideo.getLiveLastUpdate().toString()) <= 12) {
        binding.liveStatus.setVisibility(View.VISIBLE);
      } else {
        binding.liveStatus.setVisibility(View.GONE);
      }

      if (modelVideo.getUser().isVip && modelVideo.getUser().isVipActive) {
        binding.liveCrown.setVisibility(View.VISIBLE);
      } else {
        binding.liveCrown.setVisibility(View.GONE);
      }

      if (isAdultVideo(modelVideo)) {
        binding.live18.setVisibility(View.VISIBLE);
      } else {
        binding.live18.setVisibility(View.GONE);
      }
    } else {
      binding.liveDuration.setText(DateUtils.formatElapsedTime(modelVideo.getDuration()));
      binding.liveDuration.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
      if (modelVideo.getLivePassword() != null && !modelVideo.getLivePassword().isEmpty()) {
        binding.vodLock.setVisibility(View.VISIBLE);
      } else {
        binding.vodLock.setVisibility(View.GONE);
      }
      binding.liveStatus.setVisibility(View.GONE);

      if (modelVideo.getUser().isVip && modelVideo.getUser().isVipActive) {
        if (type == TYPE_POPULAR) {
          binding.liveCrown.setVisibility(View.VISIBLE);
        } else {
          binding.vodCrown.setVisibility(View.VISIBLE);
        }
      } else {
        if (type == TYPE_POPULAR) {
          binding.liveCrown.setVisibility(View.GONE);
        } else {
          binding.vodCrown.setVisibility(View.GONE);
        }
      }

      if (isAdultVideo(modelVideo)) {
        binding.vod18.setVisibility(View.VISIBLE);
      } else {
        binding.vod18.setVisibility(View.GONE);
      }
    }

    binding.liveTitle.setText(modelVideo.getTitle());
    binding.liveChannel.setText(modelVideo.getKinds());
    if (modelVideo.getMediaCategory() != null) {
      binding.liveCategory.setText(modelVideo.getMediaCategory().getName());
    }
    binding.liveDuration.setText(String.valueOf(modelVideo.getViews()));
  }

  public interface ItemFetchedListener {
    void itemFetched(ModelVideo video);
  }
}