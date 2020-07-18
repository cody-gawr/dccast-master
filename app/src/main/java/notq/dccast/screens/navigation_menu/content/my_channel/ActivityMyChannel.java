package notq.dccast.screens.navigation_menu.content.my_channel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.VideoDetailAPIInterface;
import notq.dccast.api.my_content.MyContentAPIInterface;
import notq.dccast.databinding.ActivityMyChannelBinding;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.subscribe.ResponseCreateSubscribe;
import notq.dccast.model.subscribe.ResponseSubscribe;
import notq.dccast.model.subscribe.ResponseSubscribeWrapper;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.profile.ModelProfileStat;
import notq.dccast.model.user.profile.ModelUserProfileWrapper;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.home.interfaces.HomeChildFragmentListener;
import notq.dccast.screens.login.ActivityLogin;
import notq.dccast.util.Constants;
import notq.dccast.util.FullScreenDialog;
import notq.dccast.util.LockableBottomSheetBehavior;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityMyChannel extends BaseActivity implements HomeChildFragmentListener {
  private static final int LOGIN_REQUEST = 1023;
  private final int EDIT_PROFILE = 1203;
  private Context mContext = this;
  private ActivityMyChannelBinding binding;
  private AdapterMyChannel pagerAdapter;
  private int userId = -1;
  private boolean isMe = false;

  private ProgressDialog progressDialog;
  private BottomSheetBehavior sheetBehavior;
  private int subscribeId = -1;
  private String profileImage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_my_channel);

    userId = getIntent().getIntExtra(Constants.CHANNEL_DETAIL_ID, -1);
    isMe = getIntent().getBooleanExtra(Constants.CHANNEL_DETAIL_IS_ME, false);

    ModelUser loginUser = LoginService.getLoginUser();
    if (isMe && loginUser != null) {
      userId = loginUser.getId();

      profileImage = loginUser.getProfileImage();
    }

    initToolbar();
    initBottomSheet();
    init();

    initUser();
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_my_channel));
    binding.header.backButton.setOnClickListener(v -> {
      finish();
    });
  }

  public void setHeaderName(String name) {
    binding.header.lblHeader.setText(getString(R.string.channel_name, name));
  }

  private void initUser() {
    if (isMe) {
      binding.header.layoutAction.setVisibility(View.VISIBLE);
      binding.header.btnSubscribe.setVisibility(View.GONE);
      binding.header.ivActionBtn.setOnClickListener(v -> {
        Intent editProfileIntent = new Intent(mContext, ActivityEditProfile.class);
        startActivityForResult(editProfileIntent, EDIT_PROFILE);
      });

      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser != null) {
        displayUser(loginUser);
      }
    } else {
      binding.header.layoutAction.setVisibility(View.GONE);
      binding.header.btnSubscribe.setVisibility(View.VISIBLE);
      binding.header.btnSubscribe.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (LoginService.getLoginUser() != null) {
            toggleSubscribe();
          } else {
            Toast.makeText(mContext, getString(R.string.login_required), Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(mContext, ActivityLogin.class);
            startActivityForResult(loginIntent, LOGIN_REQUEST);
          }
        }
      });
      getProfile(userId);
      getIsSubscribedRequest();
    }

    binding.header.ivActionBtn.setVisibility(isMe ? View.VISIBLE : View.GONE);
    binding.lblChannelUser.setPaintFlags(
        binding.lblChannelUser.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == EDIT_PROFILE) {
      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser != null) {
        displayUser(loginUser);
      }
    }

    if (requestCode == Constants.EDIT_VOD && resultCode == Activity.RESULT_OK) {
      if (data != null && data.getExtras() != null) {
        Bundle bundle = data.getExtras();
        ModelVideo modelVideo = (ModelVideo) bundle.getSerializable(Constants.EDIT_VOD_RESPONSE);

        if (modelVideo != null) {
          MyChannelFragment myChannelFragment = pagerAdapter.getItem(0);
          if (myChannelFragment != null) {
            myChannelFragment.updateVideo(modelVideo);
          }
        }
      }
    }

    if (requestCode == LOGIN_REQUEST && resultCode == RESULT_OK) {
      if (!isMe) {
        getIsSubscribedRequest();
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

    binding.dim.setOnClickListener(v -> closeBottomSheet());

    closeBottomSheet();
  }

  private void init() {
    pagerAdapter = new AdapterMyChannel(mContext, userId, getSupportFragmentManager());
    binding.viewPager.setOffscreenPageLimit(1);
    binding.viewPager.setAdapter(pagerAdapter);

    progressDialog = new ProgressDialog(mContext);
    progressDialog.setCancelable(false);

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
                pagerAdapter.getItem(0).search(s.toString());
              }
            });
          }
        }, DURATION);
      }
    });

    binding.ivChannelUser.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ModelUser loginUser = LoginService.getLoginUser();
        if (loginUser != null && loginUser.getProfileImage() != null) {
          FullScreenDialog dialog = FullScreenDialog.newInstance(profileImage);
          FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
          dialog.show(ft, FullScreenDialog.TAG);
        }
      }
    });
  }

  public void moveToBackground() {
    boolean sentAppToBackground = moveTaskToBack(true);

    if (!sentAppToBackground) {
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_MAIN);
      intent.addCategory(Intent.CATEGORY_HOME);
      startActivity(intent);
    }
  }

  private void openBottomSheet() {
    FragmentTransaction fragmentTransaction =
        Objects.requireNonNull(getSupportFragmentManager()).beginTransaction();

    BottomSheetSort bottomSheetSort;
    bottomSheetSort = BottomSheetSort.getInstance(BottomSheetSort.TYPE_MY_CHANNEL);
    bottomSheetSort.setOnItemSelectedListener(item -> {
      binding.lblSortValue.setText(item.getTitle());
      String key = binding.etSearch.getText().toString();
      pagerAdapter.getItem(0).sort(key, item.getId(), false);
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

  private void displayUser(ModelUser user) {
    if (user != null) {
      binding.lblChannelUser.setText(user.getNickName());
      if (user.getStateMessage() != null) {
        binding.lblChannelUserDescription.setText(user.getStateMessage());
        binding.lblChannelUserDescription.setVisibility(View.VISIBLE);
      } else {
        binding.lblChannelUserDescription.setText("");
        binding.lblChannelUserDescription.setVisibility(View.GONE);
      }

      profileImage = user.getProfileImage();

      int width = getResources().getDimensionPixelSize(R.dimen.my_channel_user_image_size);
      Glide.with(mContext).load(Util.getValidateUrl(user.getProfileImage()))
          .apply(
              new RequestOptions()
                  .override(width, width)
                  .placeholder(R.drawable.ic_profile_placeholder)
                  .centerCrop()).into(binding.ivChannelUser);

      getProfileStat(user.getId());
    }
  }

  private void getProfileStat(int userId) {
    binding.statLoader.setVisibility(View.VISIBLE);

    Call<ModelProfileStat> call =
        APIClient.getClient().create(MyContentAPIInterface.class).getProfileStat(userId);

    call.enqueue(new Callback<ModelProfileStat>() {
      @Override
      public void onResponse(@NonNull Call<ModelProfileStat> call,
          @NonNull Response<ModelProfileStat> response) {
        ModelProfileStat stat = response.body();

        binding.statLoader.setVisibility(View.GONE);

        if (stat != null) {
          binding.lblSubscriber.setText(Util.getFormattedNumber(stat.subscribers));
          binding.lblFollowers.setText(Util.getFormattedNumber(stat.followers));
          binding.lblFollowing.setText(Util.getFormattedNumber(stat.following));
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelProfileStat> call, @NonNull Throwable t) {
        call.cancel();

        binding.statLoader.setVisibility(View.GONE);
      }
    });
  }

  private void getProfile(int userId) {
    progressDialog.show();

    Call<ModelUserProfileWrapper> call =
        APIClient.getClient().create(MyContentAPIInterface.class).getProfile(userId);

    call.enqueue(new Callback<ModelUserProfileWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelUserProfileWrapper> call,
          @NonNull Response<ModelUserProfileWrapper> response) {
        ModelUserProfileWrapper stat = response.body();

        progressDialog.dismiss();

        if (stat == null || stat.users == null || stat.users.size() == 0) {
          Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
          finish();
          return;
        }

        ModelUser user = stat.users.get(0);
        displayUser(user);

        setHeaderName(user.nickName);
      }

      @Override
      public void onFailure(@NonNull Call<ModelUserProfileWrapper> call, @NonNull Throwable t) {
        call.cancel();

        progressDialog.dismiss();

        Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
        finish();
      }
    });
  }

  @Override
  public void fragmentCollapsed() {
    Objects.requireNonNull(getSupportActionBar()).show();
    binding.appBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void fragmentExpanded() {
    Objects.requireNonNull(getSupportActionBar()).hide();
    binding.appBar.setVisibility(View.GONE);
  }

  @Override
  public void fragmentClosed() {
    Objects.requireNonNull(getSupportActionBar()).show();
    binding.appBar.setVisibility(View.VISIBLE);
  }

  @Override public void onLikeDislikeUpdated(ModelVideo video) {
    MyChannelFragment myChannelFragment =
        pagerAdapter.myChannelPages.get(binding.viewPager.getCurrentItem());

    if (myChannelFragment.videoAdapter.getVideos().size() > 0) {
      ArrayList<ModelVideo> videos = myChannelFragment.videoAdapter.getVideos();

      for (int i = 0; i < videos.size(); i++) {
        ModelVideo video_ = videos.get(i);

        if (video_.getId() == video.getId()) {
          myChannelFragment.videoAdapter.getVideos().set(i, video);
          myChannelFragment.videoAdapter.notifyItemChanged(i);
        }
      }
    }
  }

  @Override public void onLikeDislikeUpdated(ModelListHeader header) {

  }

  @Override public void onLeftMenuTabChanged(int tabPosition) {

  }

  @Override public void onMediaDeleted(ModelVideo modelVideo) {
    MyChannelFragment currentFragment = pagerAdapter.getItem(0);
    currentFragment.videoAdapter.removeVideo(modelVideo);
  }

  @Override public void onMediaRemovedFromFavorite(ModelVideo modelVideo) {

  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @SuppressLint("SetTextI18n") private void toggleSubscribe() {
    if (binding.header.subscribe.getText()
        .toString()
        .equals(getString(R.string.video_user_subscribed))) {
      setSubscribe();
      deleteSubscribeRequest();
    } else {
      setSubscribed();
      createSubscribeRequest();
    }
  }

  private void createSubscribeRequest() {
    JSONObject createSubscribe = new JSONObject();
    try {
      createSubscribe.put("from_user", String.valueOf(LoginService.getLoginUser().getId()));
      createSubscribe.put("to_user",
          String.valueOf(userId));
    } catch (JSONException e) {
      e.printStackTrace();
    }

    RequestBody subscribeRequestBody =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            (createSubscribe).toString());

    Call<ResponseCreateSubscribe> call =
        APIClient.getClient().create(VideoDetailAPIInterface.class)
            .createSubscribe(subscribeRequestBody);

    call.enqueue(new Callback<ResponseCreateSubscribe>() {
      @Override public void onResponse(@NonNull Call<ResponseCreateSubscribe> call,
          @NonNull Response<ResponseCreateSubscribe> response) {
        ResponseCreateSubscribe createSubscribeResponse = response.body();
        if (createSubscribeResponse != null) {
          subscribeId = createSubscribeResponse.getId();

          Toast.makeText(mContext, getString(R.string.subscribed), Toast.LENGTH_LONG).show();
        } else {
          subscribeId = -1;
        }
      }

      @Override public void onFailure(@NonNull Call<ResponseCreateSubscribe> call, @NonNull
          Throwable t) {
        subscribeId = -1;
        call.cancel();
        Toast.makeText(mContext, getString(R.string.error_with, t.getMessage()),
            Toast.LENGTH_LONG).show();
      }
    });
  }

  private void deleteSubscribeRequest() {
    if (subscribeId != -1) {
      Call<Void> call = APIClient.getClient().create(VideoDetailAPIInterface.class)
          .deleteSubscribe(subscribeId);
      call.enqueue(new Callback<Void>() {
        @Override
        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
          Toast.makeText(mContext, getString(R.string.subscribe_cancel), Toast.LENGTH_LONG)
              .show();
        }

        @Override public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
          call.cancel();
          Toast.makeText(mContext, getString(R.string.error_with, t.getMessage()),
              Toast.LENGTH_LONG);
        }
      });
    }
  }

  private void getIsSubscribedRequest() {
    if (LoginService.getLoginUser() != null) {
      Call<ResponseSubscribeWrapper> call =
          APIClient.getClient()
              .create(VideoDetailAPIInterface.class)
              .getSubscribe(LoginService.getLoginUser().id, userId);

      call.enqueue(new Callback<ResponseSubscribeWrapper>() {
        @Override public void onResponse(@NonNull Call<ResponseSubscribeWrapper> call,
            @NonNull Response<ResponseSubscribeWrapper> response) {
          ResponseSubscribeWrapper subscribeWrapper = response.body();
          boolean isSubscribed = false;
          if (subscribeWrapper != null && subscribeWrapper.subscribeList != null) {
            for (int i = 0; i < subscribeWrapper.subscribeList.size(); i++) {
              ResponseSubscribe subscribe = subscribeWrapper.subscribeList.get(i);
              if (subscribe.getToUser().id == userId) {
                isSubscribed = true;
                subscribeId = subscribe.getId();
              }
            }
          }

          if (isSubscribed) {
            setSubscribed();
          } else {
            subscribeId = -1;
            setSubscribe();
          }
        }

        @Override public void onFailure(
            @NonNull Call<ResponseSubscribeWrapper> call, @NonNull Throwable t) {
          subscribeId = -1;
          setSubscribe();
        }
      });
    } else {
      binding.header.subscribe.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
      binding.header.subscribe.setTextColor(Color.WHITE);
    }
  }

  private void setSubscribe() {
    binding.header.subscribe.setText(getString(R.string.video_user_subscribe));
    binding.header.subscribeWrapper.setBackground(
        ContextCompat.getDrawable(mContext, R.drawable.channel_item_subscribe));
    binding.header.subscribe.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    binding.header.subscribe.setTextColor(Color.WHITE);
  }

  private void setSubscribed() {
    binding.header.subscribe.setText(getString(R.string.video_user_subscribed));
    binding.header.subscribeWrapper.setBackground(
        ContextCompat.getDrawable(mContext, R.drawable.channel_item_subscribed));
    binding.header.subscribe.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_subscribed, 0,
        0, 0);
    binding.header.subscribe.setTextColor(Color.BLACK);
  }
}
