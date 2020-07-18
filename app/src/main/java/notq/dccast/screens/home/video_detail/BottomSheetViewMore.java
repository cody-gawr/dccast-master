package notq.dccast.screens.home.video_detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.VideoDetailAPIInterface;
import notq.dccast.api.vod.VodAPIInterface;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.favorite.ModelCreateFavorite;
import notq.dccast.model.favorite.ModelFavorite;
import notq.dccast.model.favorite.ModelFavoriteWrapper;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.interfaces.HomeChildFragmentListener;
import notq.dccast.screens.login.ActivityLogin;
import notq.dccast.screens.navigation_menu.content.my_channel.ActivityMyChannel;
import notq.dccast.screens.navigation_menu.vod.ActivityUploadVOD;
import notq.dccast.util.Constants;
import notq.dccast.util.DialogHelper;
import notq.dccast.util.LoginService;
import notq.dccast.util.ShareService;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class BottomSheetViewMore extends BottomSheetDialogFragment implements View.OnClickListener {

  public static Context context;
  private boolean isVod;
  private boolean isMe;
  private ModelVideo video;
  private boolean isFavSelected;
  private TextView btnEdit;
  private TextView btnGoToChannel;
  private TextView btnDelete;
  private TextView btnFavorite;
  private TextView btnShare;
  private ModelCreateFavorite modelCreateFavorite;
  private ModelFavorite modelFavorite;
  private ShareService shareService;
  private boolean isAlwaysRemoveFavorite = false;
  private boolean ignoreMyChannel = false;
  private boolean ignoreEdit = false;

  public static void setContext(Context context) {
    BottomSheetViewMore.context = context;
  }

  public static BottomSheetViewMore getInstance(boolean isVod, boolean isMe, ModelVideo video) {
    BottomSheetViewMore instance = new BottomSheetViewMore();
    Bundle bundle = new Bundle();
    bundle.putBoolean("isVod", isVod);
    bundle.putBoolean("isMe", isMe);
    bundle.putSerializable("video", video);
    instance.setArguments(bundle);
    return instance;
  }

  public void setAlwaysRemoveFavorite(boolean alwaysRemoveFavorite) {
    isAlwaysRemoveFavorite = alwaysRemoveFavorite;
  }

  public void setIgnoreMyChannel(boolean ignoreMyChannel) {
    this.ignoreMyChannel = ignoreMyChannel;
  }

  public void setIgnoreEdit(boolean ignoreEdit) {
    this.ignoreEdit = ignoreEdit;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.bottom_sheet_view_more, container, false);

    isVod = Objects.requireNonNull(getArguments()).getBoolean("isVod");
    isMe = Objects.requireNonNull(getArguments()).getBoolean("isMe");
    video = (ModelVideo) Objects.requireNonNull(getArguments()).getSerializable("video");

    btnEdit = view.findViewById(R.id.btn_edit);
    btnDelete = view.findViewById(R.id.btn_delete);
    btnFavorite = view.findViewById(R.id.btn_favorite);
    btnShare = view.findViewById(R.id.btn_share);
    btnGoToChannel = view.findViewById(R.id.btn_go_to_channel);

    if (isMe) {
      if (isVod) {
        btnEdit.setVisibility(View.VISIBLE);
      } else {
        btnEdit.setVisibility(View.GONE);
      }
      btnDelete.setVisibility(View.VISIBLE);
      btnFavorite.setVisibility(View.GONE);
    } else {
      btnEdit.setVisibility(View.GONE);
      btnDelete.setVisibility(View.GONE);
      btnFavorite.setVisibility(View.VISIBLE);
    }

    if (isAlwaysRemoveFavorite) {
      btnFavorite.setVisibility(View.VISIBLE);
    }

    if (LoginService.getLoginUser() == null) {
      btnFavorite.setVisibility(View.GONE);
    }

    if (ignoreMyChannel) {
      btnGoToChannel.setVisibility(View.GONE);
      btnEdit.setVisibility(View.GONE);
    }

    if (ignoreEdit) {
      btnEdit.setVisibility(View.GONE);
    }

    btnGoToChannel.setOnClickListener(this);
    btnEdit.setOnClickListener(this);
    btnDelete.setOnClickListener(this);
    btnFavorite.setOnClickListener(this);

    if (isVod) {
      btnShare.setVisibility(View.VISIBLE);
      btnShare.setOnClickListener(this);
    } else {
      btnShare.setVisibility(View.GONE);
    }

    getIsFavoriteRequest();

    return view;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_edit: {
        if (video != null) {
          Intent intent = new Intent(context, ActivityUploadVOD.class);
          Bundle bundle = new Bundle();
          bundle.putSerializable("video", video);
          intent.putExtras(bundle);
          if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, Constants.EDIT_VOD);
          } else {
            startActivity(intent);
          }
        }

        this.dismiss();
        break;
      }

      case R.id.btn_delete: {
        DialogHelper.showAlertDialog(context, "",
            getString(R.string.delete_video_confirm_body),
            getString(R.string.delete_video_confirm_yes),
            (dialog, which) -> {
              deleteMediaRequest();
              ((HomeChildFragmentListener) context).onMediaDeleted(video);
            }, getString(R.string.delete_video_confirm_no), null);
        break;
      }

      case R.id.btn_share: {
        this.dismiss();
        showShareDialog();
        break;
      }

      case R.id.btn_favorite: {
        if (btnFavorite.getText().toString().equals(getString(R.string.view_more_favorite))) {
          createFavoriteRequest();
        } else {
          deleteFavoriteRequest();

          ((HomeChildFragmentListener) context).onMediaRemovedFromFavorite(video);
        }
        break;
      }

      case R.id.btn_go_to_channel: {
        if (video.getUser() == null) {
          return;
        }
        this.dismiss();
        ModelUser loginUser = LoginService.getLoginUser();
        if (loginUser == null) {
          Intent intent = new Intent(getActivity(), ActivityLogin.class);
          startActivity(intent);
          return;
        }
        goToChannel(video.getUser().getId());
        break;
      }
    }
  }

  private void goToChannel(int userId) {
    Intent channelIntent = new Intent(getActivity(), ActivityMyChannel.class);
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser != null && loginUser.getId() == userId) {
      channelIntent.putExtra(Constants.CHANNEL_DETAIL_IS_ME, true);
    } else {
      channelIntent.putExtra(Constants.CHANNEL_DETAIL_IS_ME, false);
      channelIntent.putExtra(Constants.CHANNEL_DETAIL_ID, userId);
    }
    startActivity(channelIntent);
  }

  private void showShareDialog() {
    if (video == null) {
      return;
    }
    int videoId = video.getId();
    shareService = new ShareService(getActivity(), videoId);
    shareService.showDialog(null);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode,
      @androidx.annotation.Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data == null) {
      Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
      return;
    }
    if (shareService != null) {
      shareService.callbackOnActivityResult(requestCode, resultCode, data);
    }
  }

  private void getIsFavoriteRequest() {
    if (LoginService.getLoginUser() != null) {
      Call<ModelFavoriteWrapper> call2 = APIClient.getClient()
          .create(VideoDetailAPIInterface.class)
          .getFavorite(LoginService.getLoginUser().id, video.id);
      call2.enqueue(new Callback<ModelFavoriteWrapper>() {
        @SuppressLint("SetTextI18n") @Override
        public void onResponse(@NonNull Call<ModelFavoriteWrapper> call,
            @NonNull Response<ModelFavoriteWrapper> response) {
          ModelFavoriteWrapper wrapper = response.body();
          if (wrapper.favoriteList.size() > 0) {
            int k = 0;
            for (int i = 0; i < wrapper.favoriteList.size(); i++) {
              modelFavorite = wrapper.favoriteList.get(i);
              if (modelFavorite.getMedia().id == video.id) {
                k++;
              }
            }

            isFavSelected = k > 0;

            if (isFavSelected) {
              btnFavorite.setText(getString(R.string.remove_from_favorite));
            } else {
              btnFavorite.setText(getString(R.string.add_to_favorite));
            }
          }
        }

        @Override
        public void onFailure(@NonNull Call<ModelFavoriteWrapper> call, @NonNull Throwable t) {
          Log.e("isFavorite", t.getMessage());
        }
      });
    }
  }

  private void createFavoriteRequest() {
    JSONObject createFavJSON = new JSONObject();
    try {
      createFavJSON.put("user", String.valueOf(LoginService.getLoginUser().getId()));
      createFavJSON.put("media",
          String.valueOf(video.getId()));
    } catch (JSONException e) {
      e.printStackTrace();
    }

    RequestBody favRequestBody =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            (createFavJSON).toString());

    Call<ModelCreateFavorite> call =
        APIClient.getClient().create(VideoDetailAPIInterface.class).createFavorite(favRequestBody);

    call.enqueue(new Callback<ModelCreateFavorite>() {
      @Override
      public void onResponse(@NonNull Call<ModelCreateFavorite> call,
          @NonNull Response<ModelCreateFavorite> response) {
        if (response.body() != null) {
          modelCreateFavorite = response.body();
          btnFavorite.setText(getString(R.string.remove_from_favorite));
          Toast.makeText(getActivity(), getString(R.string.create_favorite), Toast.LENGTH_LONG)
              .show();
          dismiss();
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelCreateFavorite> call, @NonNull Throwable t) {
        Log.e("create fav error", t.getMessage());
        call.cancel();
        dismiss();
      }
    });
  }

  private void deleteFavoriteRequest() {
    if (isFavSelected) {
      int id = -1;

      if (modelCreateFavorite != null) {
        id = modelCreateFavorite.id;
      } else if (modelFavorite != null) {
        id = modelFavorite.getId();
      }

      if (id != -1) {
        Call<Void> call
            = APIClient.getClient()
            .create(VideoDetailAPIInterface.class)
            .removeFavorite(id);
        call.enqueue(new Callback<Void>() {
          @Override
          public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
            btnFavorite.setText(getString(R.string.add_to_favorite));
            Toast.makeText(getActivity(), getString(R.string.delete_favorite), Toast.LENGTH_LONG)
                .show();
            dismiss();
          }

          @Override public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
            call.cancel();
            dismiss();
          }
        });
      }
    }
  }

  private void deleteMediaRequest() {
    Call<Void> call
        = APIClient.getClient()
        .create(VodAPIInterface.class)
        .deleteMedia(video.id);

    call.enqueue(new Callback<Void>() {
      @Override
      public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
        Log.e("BottomSheetViewMore", "Delete Request");
        Toast.makeText(context, context.getString(R.string.vod_delete_success), Toast.LENGTH_LONG)
            .show();
        dismiss();
      }

      @Override public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
        call.cancel();
        Log.e("BottomSheetViewMore", "Delete Request Error: " + t.getMessage());
        dismiss();
      }
    });
  }
}
