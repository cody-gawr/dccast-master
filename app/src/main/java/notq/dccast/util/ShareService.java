package notq.dccast.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import java.util.ArrayList;
import java.util.HashMap;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.VideoDetailAPIInterface;
import notq.dccast.databinding.DialogShareBinding;
import notq.dccast.model.friends.ModelFriendRequest;
import notq.dccast.model.friends.ModelFriendRequestWrapper;
import notq.dccast.model.user.ModelShareResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.adapter.AdapterShareAndFriends;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareService implements View.OnClickListener {

  private Context context;
  private Activity activity;

  private AdapterShareAndFriends friendsAdapter;

  private DialogShareBinding shareBinding;
  private ShareDialog fbShareDialog;
  private CallbackManager callbackManager;

  private AlertDialog shareDialog;

  private String shareUrl = Url.SHARE_URL;
  private int videoId = -1;

  public ShareService(Activity activity, int videoId) {
    this.activity = activity;
    this.context = activity;
    this.videoId = videoId;

    initShareDialog();
  }

  public void showDialog(String shareUrl) {
    this.shareUrl = shareUrl == null ? Url.SHARE_URL : shareUrl;
    this.shareUrl += videoId;
    shareDialog.show();
  }

  private void initShareDialog() {
    LayoutInflater layoutInflater = LayoutInflater.from(context);

    shareBinding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_share, null, false);
    shareDialog = new AlertDialog.Builder(context).setView(shareBinding.getRoot()).create();

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
    linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
    shareBinding.friends.setLayoutManager(linearLayoutManager);

    shareBinding.shareOptions.setLayoutManager(new GridLayoutManager(context, 4));

    friendsAdapter = new AdapterShareAndFriends(context, false, this);
    friendsAdapter.setShareToFriendsListener(new AdapterShareAndFriends.ShareToFriendsListener() {
      @Override public void shareToFriends(ModelFriendRequest friendRequest) {
        share(friendRequest.getUser().getId(), videoId);
      }
    });
    AdapterShareAndFriends shareAdapter = new AdapterShareAndFriends(context, true, this);

    shareBinding.friends.setAdapter(friendsAdapter);
    shareBinding.shareOptions.setAdapter(shareAdapter);
    shareBinding.btnShareClose.setOnClickListener(view -> shareDialog.hide());

    getFriendListRequest();
  }

  private void share(int shareUserId, int videoId) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser != null) {
      ArrayList<Integer> friends = new ArrayList<>();
      friends.add(shareUserId);

      ArrayList<Integer> media = new ArrayList<>();
      media.add(videoId);

      HashMap<String, Object> bodyObject = new HashMap<>();
      bodyObject.put("user_id", loginUser.getId());
      bodyObject.put("media", media);
      bodyObject.put("friends", friends);

      Call<ModelShareResult> call =
          APIClient.getClient()
              .create(VideoDetailAPIInterface.class)
              .shareToFriends(bodyObject);

      call.enqueue(new Callback<ModelShareResult>() {
        @Override
        public void onResponse(@NonNull Call<ModelShareResult> call,
            @NonNull Response<ModelShareResult> response) {
          if (response.body() != null) {
            ModelShareResult result = response.body();

            if (result.isResult()) {
              shareDialog.dismiss();
              Toast.makeText(context, context.getString(R.string.video_shared_success),
                  Toast.LENGTH_LONG).show();
              return;
            }
          }

          Toast.makeText(context, context.getString(R.string.error),
              Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(@NonNull Call<ModelShareResult> call, @NonNull Throwable t) {
          call.cancel();

          Toast.makeText(context, context.getString(R.string.error),
              Toast.LENGTH_LONG).show();
        }
      });
    }
  }

  private void getFriendListRequest() {
    if (LoginService.getLoginUser() != null) {
      Call<ModelFriendRequestWrapper> call =
          APIClient.getClient()
              .create(VideoDetailAPIInterface.class)
              .getFriends(LoginService.getLoginUser().id, 1);

      call.enqueue(new Callback<ModelFriendRequestWrapper>() {
        @Override
        public void onResponse(@NonNull Call<ModelFriendRequestWrapper> call,
            @NonNull Response<ModelFriendRequestWrapper> response) {
          if (response.body() != null) {
            ModelFriendRequestWrapper friendWrapper = response.body();

            for (int i = 0; i < friendWrapper.getFriends().size(); i++) {
              ModelFriendRequest itemFriends = friendWrapper.getFriends().get(i);
              if (itemFriends == null
                  || itemFriends.getFromUser() == null
                  || itemFriends.getToUser() == null
                  || !itemFriends.isAccepted()) {
                continue;
              }

              friendsAdapter.addFriend(itemFriends);
            }

            shareBinding.titleFriends.setVisibility(
                friendsAdapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
            shareBinding.friends.setVisibility(
                friendsAdapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
          } else {
            shareBinding.titleFriends.setVisibility(View.GONE);
            shareBinding.friends.setVisibility(View.GONE);
          }
        }

        @Override
        public void onFailure(@NonNull Call<ModelFriendRequestWrapper> call, @NonNull Throwable t) {
          shareBinding.titleFriends.setVisibility(View.GONE);
          shareBinding.friends.setVisibility(View.GONE);
        }
      });
    }
  }

  public void callbackOnActivityResult(int requestCode, int resultCode,
      @androidx.annotation.Nullable Intent data) {
    if (callbackManager != null) {
      callbackManager.onActivityResult(requestCode, resultCode, data);
    }
  }

  private void shareToFacebook() {
    if (ShareDialog.canShow(ShareLinkContent.class)) {
      final ShareLinkContent linkContent = new ShareLinkContent.Builder()
          .setContentUrl(Uri.parse(shareUrl))
          .build();

      callbackManager = CallbackManager.Factory.create();
      fbShareDialog = new ShareDialog(activity);
      fbShareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
        @Override
        public void onSuccess(Sharer.Result result) {
          shareDialog.dismiss();
          Toast.makeText(activity, context.getString(R.string.video_shared_success),
              Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
          Log.e("FragmentVideo", "Cancel");
        }

        @Override
        public void onError(FacebookException error) {
          if (error != null && error.getMessage().equals("null")) {
            fbShareDialog.show(linkContent, ShareDialog.Mode.WEB);
          }
        }
      });

      fbShareDialog.show(linkContent);
    } else {
      boolean isInstalled =
          isFacebookPackageInstalled(activity.getPackageManager());
      if (!isInstalled) {
        DialogHelper.showAlertDialog(activity, context.getString(R.string.dialog_error),
            context.getString(
                R.string.dont_have_facebook), "OK",
            null);
      } else {
        DialogHelper.showAlertDialog(activity, context.getString(R.string.dialog_error),
            context.getString(
                R.string.cant_share_facebook));
      }
    }
  }

  private void shareToLine() {
    try {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(
          Uri.parse("line://msg/text/" + shareUrl));
      activity.startActivity(intent);
    } catch (Exception e) {
      boolean isInstalled =
          isLinePackageInstalled(activity.getPackageManager());
      if (!isInstalled) {
        DialogHelper.showAlertDialog(activity, context.getString(R.string.dialog_error),
            context.getString(R.string.dont_have_line),
            "OK", null);
      } else {
        DialogHelper.showAlertDialog(activity, context.getString(R.string.dialog_error),
            context.getString(R.string.cant_share_line) + e.getMessage());
      }
    }
  }

  private void shareToKakao() {
    String shareTitle = "";
    String shareThumbnail = "";

    FeedTemplate params = FeedTemplate
        .newBuilder(ContentObject.newBuilder(shareTitle,
            shareThumbnail,
            LinkObject.newBuilder().setWebUrl(shareUrl)
                .setMobileWebUrl(shareUrl).build())
            .setDescrption("")
            .build())
        .addButton(
            new ButtonObject(context.getString(R.string.view_on_the_web), LinkObject.newBuilder()
                .setWebUrl(shareUrl)
                .setMobileWebUrl(shareUrl).build()))
        .build();

    KakaoLinkService.getInstance()
        .sendDefault(activity, params, new ResponseCallback<KakaoLinkResponse>() {
          @Override
          public void onFailure(ErrorResult errorResult) {
            Log.e("FragmentVideo", errorResult.toString());

            boolean isInstalled =
                isKakaoPackageInstalled(activity.getPackageManager());
            if (!isInstalled) {
              DialogHelper.showAlertDialog(activity, context.getString(R.string.dialog_error),
                  context.getString(R.string.dont_have_kakao),
                  "OK", null);
            } else {
              DialogHelper.showAlertDialog(activity, context.getString(R.string.dialog_error),
                  context.getString(R.string.cant_share_kakao) + errorResult.getErrorMessage());
            }
          }

          @Override
          public void onSuccess(KakaoLinkResponse result) {
            Log.e("FragmentVideo", "Share to KAKAO success");
          }
        });
  }

  private boolean isFacebookPackageInstalled(PackageManager packageManager) {
    try {
      return packageManager.getApplicationInfo("com.facebook.katana", 0).enabled;
    } catch (PackageManager.NameNotFoundException e) {
      return false;
    }
  }

  private boolean isLinePackageInstalled(PackageManager packageManager) {
    try {
      return packageManager.getApplicationInfo("jp.naver.line.android", 0).enabled;
    } catch (PackageManager.NameNotFoundException e) {
      return false;
    }
  }

  private boolean isKakaoPackageInstalled(PackageManager packageManager) {
    try {
      return packageManager.getApplicationInfo("com.kakao.talk", 0).enabled;
    } catch (PackageManager.NameNotFoundException e) {
      return false;
    }
  }

  private boolean isTwitterInstalled(PackageManager packageManager) {
    try {
      return packageManager.getApplicationInfo("com.twitter.android", 0).enabled;
    } catch (PackageManager.NameNotFoundException e) {
      return false;
    }
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.container: {
        if (view.getTag().equals(context.getString(R.string.share_facebook))) {
          shareToFacebook();
        } else if (view.getTag().equals(context.getString(R.string.share_line))) {
          shareToLine();
        } else if (view.getTag().equals(context.getString(R.string.share_kakao))) {
          shareToKakao();
        } else if (view.getTag().equals(context.getString(R.string.share_twitter))) {
          boolean isInstalled =
              isTwitterInstalled(activity.getPackageManager());
          if (!isInstalled) {
            DialogHelper.showAlertDialog(activity, context.getString(R.string.dialog_error),
                context.getString(R.string.dont_have_twitter),
                "OK", null);
          } else {
            String url = "http://www.twitter.com/intent/tweet?url=" + shareUrl + "&text=DCCast";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
          }
        } else if (view.getTag().equals(context.getString(R.string.share_more))) {
          Intent sendIntent = new Intent();
          sendIntent.setAction(Intent.ACTION_SEND);
          sendIntent.putExtra(Intent.EXTRA_TEXT, shareUrl);
          sendIntent.setType("text/plain");
          context.startActivity(Intent.createChooser(sendIntent, "Share"));
        }

        break;
      }
    }
  }
}
