package notq.dccast.screens.home;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import life.knowledge4.videotrimmer.utils.FileUtils;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.HomeAPIInterface;
import notq.dccast.databinding.ActivityHomeBinding;
import notq.dccast.model.ModelBlockDivisionResult;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.category_detail.FragmentCategoryDetail;
import notq.dccast.screens.home.adapter.AdapterNavigationDrawer;
import notq.dccast.screens.home.home_list.HomeListFragment;
import notq.dccast.screens.home.interfaces.HomeChildFragmentListener;
import notq.dccast.screens.home.mandu.ActivityManduHistoryWebView;
import notq.dccast.screens.home.radio_mode.PlayerManager;
import notq.dccast.screens.home.search.ActivitySearch;
import notq.dccast.screens.home.video_detail.BottomSheetAddComment;
import notq.dccast.screens.home.video_detail.BottomSheetBuuz;
import notq.dccast.screens.home.video_detail.BottomSheetComment;
import notq.dccast.screens.home.video_detail.BottomSheetSticker;
import notq.dccast.screens.home.video_detail.FragmentVideo;
import notq.dccast.screens.login.ActivityLogin;
import notq.dccast.screens.navigation_menu.cast.FragmentCast;
import notq.dccast.screens.navigation_menu.cast.search.ActivitySearchFollowers;
import notq.dccast.screens.navigation_menu.cast.search.ActivitySearchGroups;
import notq.dccast.screens.navigation_menu.cast.search_friend.ActivitySearchFriend;
import notq.dccast.screens.navigation_menu.cast.user_follow.ActivityUserFollow;
import notq.dccast.screens.navigation_menu.content.ActivityMyContent;
import notq.dccast.screens.navigation_menu.content.my_channel.ActivityMyChannel;
import notq.dccast.screens.navigation_menu.favorite.ActivityFavorite;
import notq.dccast.screens.navigation_menu.live.ActivityLiveSettings;
import notq.dccast.screens.navigation_menu.notification.ActivityNotification;
import notq.dccast.screens.navigation_menu.settings.ActivitySettings;
import notq.dccast.screens.video_trimmer.ActivityTrimmer;
import notq.dccast.util.Constants;
import notq.dccast.util.DialogHelper;
import notq.dccast.util.FullScreenDialog;
import notq.dccast.util.LoginService;
import notq.dccast.util.SuccessDialog;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityHome extends BaseActivity
    implements View.OnClickListener, HomeChildFragmentListener {
  public static final String EXTRA_VIDEO_PATH = "EXTRA_VIDEO_PATH";
  public static final int FRAGMENT_TYPE_CATEGORY_DETAIL = 0;
  private static final int FRAGMENT_TYPE_HOME_LIST = 1;
  private static final int FRAGMENT_TYPE_OTHER_MENU = 2;
  private static final int REQUEST_VIDEO_TRIMMER = 0x01;
  private static final int VIDEO_CAPTURE = 1029;
  private final int REQUEST_VOD_UPLOAD = 2003;
  private final int REQUEST_LIVE = 2004;

  private Context mContext = this;
  private ActivityHomeBinding binding;
  private Intent tempIntent;
  private Fragment tempFragment;
  private Fragment currentFragment;
  private int currentTabPosition = -1;
  private AdapterNavigationDrawer adapterNavigationDrawer;
  private PlayerManager playerManager;
  private ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

    int openVideoId = getIntent().getIntExtra(Constants.OPEN_VIDEO_ID, -1);

    playerManager = PlayerManager.with(this);
    playerManager.bind();

    progressDialog = new ProgressDialog(mContext);

    init();
    initNavigationDrawer();
    setCurrentFragment(HomeListFragment.newInstance(openVideoId));
  }

  @Override protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    int openVideoId = intent.getIntExtra(Constants.OPEN_VIDEO_ID, -1);

    if (openVideoId > 0 && currentFragment != null && currentFragment instanceof HomeListFragment) {
      HomeListFragment fragment = (HomeListFragment) currentFragment;
      fragment.getMediaById(openVideoId);
    }
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

  private void init() {
    setSupportActionBar(binding.toolbarContainer.toolbar);
    Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(false);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
    binding.toolbarContainer.searchButton.setOnClickListener(this);
    binding.toolbarContainer.homeButton.setOnClickListener(this);
    binding.toolbarContainer.tabLayout.addOnTabSelectedListener(
        new TabLayout.OnTabSelectedListener() {
          @Override
          public void onTabSelected(TabLayout.Tab tab) {
            if (tab.getPosition() != currentTabPosition) {
              DCCastApplication.utils.VIDEO_CHOOSE_TYPE = tab.getPosition() == 0 ? "LIVE" : "VOD";

              if (currentFragment != null && currentFragment instanceof HomeListFragment) {
                ((HomeListFragment) currentFragment).toolbarTabChanged();
              }

              currentTabPosition = tab.getPosition();

              try {
                if (adapterNavigationDrawer != null) {
                  if (adapterNavigationDrawer.category != null) {
                    adapterNavigationDrawer.category.selectTab(currentTabPosition);
                  }
                }
              } catch (Exception ex) {
                Timber.e("ERROR:1111 " + ex.getMessage());
              }
            }
          }

          @Override
          public void onTabUnselected(TabLayout.Tab tab) {

          }

          @Override
          public void onTabReselected(TabLayout.Tab tab) {

          }
        });

    Objects.requireNonNull(binding.toolbarContainer.tabLayout.getTabAt(1)).select();
  }

  @Override
  public void onBackPressed() {
    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
      binding.drawerLayout.closeDrawer(GravityCompat.START);
    } else if (!(currentFragment != null && currentFragment instanceof HomeListFragment)) {
      setCurrentFragment(new HomeListFragment());
      adapterNavigationDrawer.setSelection(0);
    } else {
      if (currentFragment != null && currentFragment instanceof HomeListFragment) {
        HomeListFragment homeListFragment = ((HomeListFragment) currentFragment);
        List<Fragment> fragments = homeListFragment.getChildFragmentManager().getFragments();
        List<Fragment> childFragments = homeListFragment.getFragmentManager().getFragments();

        try {
          if (fragments != null && childFragments != null) {
            fragments.addAll(childFragments);
          }
        } catch (Exception ignored) {

        }

        if (fragments != null) {
          boolean isFoundFragmentVideo = false;
          FragmentVideo fragmentVideo = null;

          for (Fragment fragment : fragments) {
            if (fragment instanceof FragmentVideo) {
              fragmentVideo = ((FragmentVideo) fragment);
              continue;
            }

            if (fragment instanceof BottomSheetAddComment
                || fragment instanceof BottomSheetComment
                || fragment instanceof BottomSheetBuuz
                || fragment instanceof BottomSheetSticker) {
              isFoundFragmentVideo = true;
            }
          }

          if (fragmentVideo != null && isFoundFragmentVideo) {
            fragmentVideo.onBackPressed();
            return;
          }
        }
      }
      super.onBackPressed();
    }
  }

  private void showVODUploadOptionsDialog() {
    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
    builder.setTitle(R.string.vod_upload_select_action)
        .setItems(R.array.video_picker_choose, (dialog, which) -> {
          switch (which) {
            case 0:
              String[] permissions = { Manifest.permission.CAMERA };
              Permissions.Options options = new Permissions.Options()
                  .setSettingsDialogMessage(
                      mContext.getString(R.string.need_camera_permission))
                  .setSettingsDialogTitle(mContext.getString(R.string.permission_warning));

              Permissions.check(mContext, permissions, null, options, new PermissionHandler() {
                @Override
                public void onGranted() {
                  openVideoRecordIntent();
                }
              });
              break;
            case 1:

              String[] readPermissions = {
                  Manifest.permission.READ_EXTERNAL_STORAGE,
                  Manifest.permission.WRITE_EXTERNAL_STORAGE
              };
              Permissions.Options readOptions = new Permissions.Options()
                  .setSettingsDialogMessage(
                      mContext.getString(R.string.need_read_storage_permission))
                  .setSettingsDialogTitle(mContext.getString(R.string.permission_warning));

              Permissions.check(mContext, readPermissions, null, readOptions,
                  new PermissionHandler() {
                    @Override
                    public void onGranted() {
                      openVideoSelector();
                    }
                  });
              break;
            default:
              break;
          }
        });

    builder.create().show();
  }

  private void openVideoRecordIntent() {
    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
    startActivityForResult(intent, VIDEO_CAPTURE);
  }

  private void openVideoSelector() {
    Intent intent = new Intent();
    intent.setTypeAndNormalize("video/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    startActivityForResult(
        Intent.createChooser(intent, getString(R.string.label_select_video)),
        REQUEST_VIDEO_TRIMMER);
  }

  @Override
  public void onClick(View view) {
    String idStr = String.valueOf(view.getTag());
    if (idStr.contains("category")) {
      if (currentFragment instanceof HomeListFragment) {
        int position = Integer.parseInt(String.valueOf(idStr.charAt(idStr.length() - 1)));
        changeToolbar(FRAGMENT_TYPE_CATEGORY_DETAIL);

        runOnUiThread(() -> new Handler().postDelayed(
            () -> ((HomeListFragment) currentFragment).videoAdapter.moveCurrentTab(position),
            1500));
      } else {
        setCurrentFragment(new HomeListFragment());

        int position = Integer.parseInt(String.valueOf(idStr.charAt(idStr.length() - 1)));
        changeToolbar(FRAGMENT_TYPE_CATEGORY_DETAIL);

        runOnUiThread(() -> new Handler().postDelayed(
            () -> ((HomeListFragment) currentFragment).videoAdapter.moveCurrentTab(position),
            1500));
      }

      binding.drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      switch (view.getId()) {
        case R.id.btn_login_or_notification: {
          binding.drawerLayout.closeDrawer(GravityCompat.START);

          ModelUser loginUser = LoginService.getLoginUser();
          if (loginUser != null) {
            Intent notificationIntent =
                new Intent(getApplicationContext(), ActivityNotification.class);
            startActivity(notificationIntent);
          } else {
            Intent loginIntent = new Intent(getApplicationContext(), ActivityLogin.class);
            startActivity(loginIntent);
          }

          break;
        }

        case R.id.item_home: {
          binding.drawerLayout.closeDrawer(GravityCompat.START);

          if (!(currentFragment instanceof HomeListFragment)) {
            tempFragment = new HomeListFragment();
            adapterNavigationDrawer.setSelection(0);
          }
          break;
        }

        case R.id.item_my_content: {
          binding.drawerLayout.closeDrawer(GravityCompat.START);
          ModelUser loginUser = LoginService.getLoginUser();
          if (loginUser != null) {
            Intent settingsIntent = new Intent(getApplicationContext(), ActivityMyContent.class);
            startActivity(settingsIntent);
          } else {
            Toast.makeText(ActivityHome.this, getString(R.string.login_required),
                Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(getApplicationContext(), ActivityLogin.class);
            startActivity(loginIntent);
          }
          break;
        }

        case R.id.item_live: {
          binding.drawerLayout.closeDrawer(GravityCompat.START);

          ModelUser loginUser = LoginService.getLoginUser();
          if (loginUser == null) {
            Toast.makeText(ActivityHome.this, getString(R.string.login_required),
                Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(getApplicationContext(), ActivityLogin.class);
            startActivity(loginIntent);
            return;
          }

          if (loginUser != null && loginUser.getLimitMobileData() && Util.checkNetworkStatus(
              mContext)
              .equalsIgnoreCase("mobileData")) {
            Toast.makeText(mContext, getString(R.string.user_data_using), Toast.LENGTH_LONG)
                .show();
            return;
          }

          checkBlockedFromAdmin(true);
          break;
        }

        case R.id.item_vod: {
          binding.drawerLayout.closeDrawer(GravityCompat.START);

          ModelUser loginUser = LoginService.getLoginUser();
          if (loginUser == null) {
            Toast.makeText(ActivityHome.this, getString(R.string.login_required),
                Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(getApplicationContext(), ActivityLogin.class);
            startActivity(loginIntent);
            return;
          }

          if (loginUser != null && loginUser.getLimitMobileData() && Util.checkNetworkStatus(
              mContext)
              .equalsIgnoreCase("mobileData")) {
            Toast.makeText(mContext, getString(R.string.user_data_using), Toast.LENGTH_LONG)
                .show();
            return;
          }

          checkBlockedFromAdmin(false);

          break;
        }

        case R.id.item_favorite: {
          binding.drawerLayout.closeDrawer(GravityCompat.START);

          ModelUser loginUser = LoginService.getLoginUser();
          if (loginUser != null) {
            Intent favoriteIntent = new Intent(getApplicationContext(), ActivityFavorite.class);
            startActivity(favoriteIntent);
          } else {
            Toast.makeText(ActivityHome.this, getString(R.string.login_required),
                Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(getApplicationContext(), ActivityLogin.class);
            startActivity(loginIntent);
          }

          break;
        }

        case R.id.item_cast: {
          binding.drawerLayout.closeDrawer(GravityCompat.START);

          ModelUser loginUser = LoginService.getLoginUser();
          if (loginUser != null) {
            if (!(currentFragment instanceof FragmentCast)) {
              tempFragment = new FragmentCast();
              adapterNavigationDrawer.setSelection(3);
              binding.toolbarContainer.toolbarTitle.setText(getString(R.string.fragment_cast_list));
            }
          } else {
            Toast.makeText(ActivityHome.this, getString(R.string.login_required),
                Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(getApplicationContext(), ActivityLogin.class);
            startActivity(loginIntent);
          }

          break;
        }

        case R.id.btn_see_all: {
          if (currentFragment instanceof HomeListFragment) {
            changeToolbar(FRAGMENT_TYPE_CATEGORY_DETAIL);
          }

          break;
        }

        case R.id.home_button: {
          if (currentFragment instanceof HomeListFragment) {
            if (binding.toolbarContainer.tabLayout.isShown()) {
              binding.drawerLayout.openDrawer(GravityCompat.START);
            } else {
              changeToolbar(FRAGMENT_TYPE_HOME_LIST);
            }
            ((HomeListFragment) currentFragment).selectFirstTab();
          } else {
            binding.drawerLayout.openDrawer(GravityCompat.START);
          }

          break;
        }

        case R.id.btn_settings: {
          binding.drawerLayout.closeDrawer(GravityCompat.START);
          Intent settingsIntent = new Intent(getApplicationContext(), ActivitySettings.class);
          startActivity(settingsIntent);

          break;
        }

        case R.id.lbl_username: {
          binding.drawerLayout.closeDrawer(GravityCompat.START);
          ModelUser loginUser = LoginService.getLoginUser();
          if (loginUser != null) {
            Intent myChannelIntent = new Intent(getApplicationContext(), ActivityMyChannel.class);
            myChannelIntent.putExtra(Constants.CHANNEL_DETAIL_IS_ME, true);
            startActivity(myChannelIntent);
          } else {
            Toast.makeText(ActivityHome.this, getString(R.string.login_required),
                Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(getApplicationContext(), ActivityLogin.class);
            startActivity(loginIntent);
          }
          break;
        }
        case R.id.iv_profile_image: {
          ModelUser loginUser = LoginService.getLoginUser();
          if (loginUser != null && loginUser.getProfileImage() != null) {
            if (loginUser.getProfileImage() == null) {
              FullScreenDialog dialog = FullScreenDialog.newInstanceDefault();
              FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
              dialog.show(ft, FullScreenDialog.TAG);
            } else {
              FullScreenDialog dialog = FullScreenDialog.newInstance(loginUser.getProfileImage());
              FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
              dialog.show(ft, FullScreenDialog.TAG);
            }
          }
          break;
        }

        case R.id.layout_mandu: {
          Intent intent = new Intent(getApplicationContext(), ActivityManduHistoryWebView.class);
          startActivity(intent);
          break;
        }

        case R.id.search_button: {
          if (currentFragment instanceof FragmentCast) {
            int selectedIndex = ((FragmentCast) currentFragment).getSelectedTab();
            switch (selectedIndex) {
              case 0: {
                startActivity(new Intent(getApplicationContext(), ActivitySearchFollowers.class));
                break;
              }
              case 1: {
                startActivity(new Intent(getApplicationContext(), ActivityUserFollow.class));
                break;
              }
              case 2: {
                startActivity(new Intent(getApplicationContext(), ActivitySearchFriend.class));
                break;
              }
              case 3: {
                startActivity(new Intent(getApplicationContext(), ActivitySearchGroups.class));
                break;
              }
            }
          } else {
            startActivity(new Intent(getApplicationContext(), ActivitySearch.class));
          }
          break;
        }
      }
    }
  }

  private void showLoader() {
    if (progressDialog != null) {
      progressDialog.show();
    }
  }

  private void hideLoader() {
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
  }

  private void checkBlockedFromAdmin(boolean isLive) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    showLoader();
    HomeAPIInterface apiInterface = APIClient.getClient().create(HomeAPIInterface.class);
    Call<ModelBlockDivisionResult> call =
        apiInterface.checkBlockedFromAdmin("check_block_user", loginUser.getId(),
            isLive ? "Live" : "VOD");
    call.enqueue(new Callback<ModelBlockDivisionResult>() {
      @Override public void onResponse(@NonNull Call<ModelBlockDivisionResult> call,
          @NonNull Response<ModelBlockDivisionResult> response) {
        ModelBlockDivisionResult result = response.body();
        hideLoader();
        if (result != null && result.isBlock) {
          if (result.getBlockEndDate() != null && !result.getBlockEndDate().isEmpty()) {
            try {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
              Date date = sdf.parse(result.getBlockEndDate());
              long endTime = date.getTime() + TimeZone.getDefault().getRawOffset();

              long now = new Date().getTime();

              if (endTime > now) {
                String msg = getString(R.string.blocked_from_admin);
                if (result.getBlockMessage() != null && !result.getBlockMessage().isEmpty()) {
                  msg = result.getBlockMessage();
                }

                SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
                String dateFormatted = format.format(date);
                msg += getString(R.string.block_end_date) + dateFormatted;
                Toast.makeText(mContext, msg, Toast.LENGTH_LONG)
                    .show();
                return;
              }
            } catch (Exception ex) {
              Timber.e("Error: " + ex.getMessage());

              SimpleDateFormat sdfSecond =
                  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
              try {
                Date date = sdfSecond.parse(result.getBlockEndDate());
                long endTime = date.getTime() + TimeZone.getDefault().getRawOffset();

                long now = new Date().getTime();

                if (endTime > now) {
                  String msg = getString(R.string.blocked_from_admin);
                  if (result.getBlockMessage() != null && !result.getBlockMessage().isEmpty()) {
                    msg = result.getBlockMessage();
                    SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
                    String dateFormatted = format.format(date);
                    msg += getString(R.string.block_end_date) + dateFormatted;
                  }
                  Toast.makeText(mContext, msg, Toast.LENGTH_LONG)
                      .show();
                  return;
                }
              } catch (ParseException e) {
                e.printStackTrace();
              }
            }
          } else {
            Toast.makeText(mContext, getString(R.string.blocked_from_admin), Toast.LENGTH_LONG)
                .show();
            return;
          }
        }

        if (isLive) {
          Intent liveSettingsIntent =
              new Intent(getApplicationContext(), ActivityLiveSettings.class);
          startActivityForResult(liveSettingsIntent, REQUEST_LIVE);
        } else {
          String[] permissions = { Manifest.permission.READ_EXTERNAL_STORAGE };
          Permissions.Options options = new Permissions.Options()
              .setSettingsDialogMessage(
                  getString(R.string.need_read_storage_permission))
              .setSettingsDialogTitle(mContext.getString(R.string.permission_warning));

          Permissions.check(mContext, permissions, null, options, new PermissionHandler() {
            @Override
            public void onGranted() {
              showVODUploadOptionsDialog();
            }
          });
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelBlockDivisionResult> call, @NonNull Throwable t) {
        call.cancel();
        Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
        hideLoader();
      }
    });
  }

  @Override protected void onResume() {
    super.onResume();

    if (adapterNavigationDrawer != null && adapterNavigationDrawer.getItemCount() > 0) {
      adapterNavigationDrawer.notifyItemChanged(0);
    }

    if (LoginService.getLoginUser() == null
        && (currentFragment instanceof FragmentCast)
        && adapterNavigationDrawer != null) {
      tempFragment = new HomeListFragment();
      setCurrentFragment(tempFragment);
      adapterNavigationDrawer.setSelection(0);
    }
  }

  public void updateViewCount(ModelVideo modelVideo) {
    if (currentFragment != null && currentFragment instanceof HomeListFragment) {
      ((HomeListFragment) currentFragment).updateViewCount(modelVideo);
    }
  }

  public void updateViewCount(ModelListHeader modelVideo) {
    if (currentFragment != null && currentFragment instanceof HomeListFragment) {
      ((HomeListFragment) currentFragment).updateViewCount(modelVideo);
    }
  }

  @Override
  public void fragmentCollapsed() {
    Objects.requireNonNull(getSupportActionBar()).show();

    if (currentFragment != null && currentFragment instanceof FragmentCast) {
      ((FragmentCast) currentFragment).showTab();
    } else if (currentFragment != null && currentFragment instanceof HomeListFragment) {
      ((HomeListFragment) currentFragment).showAdsView();
    }
  }

  @Override
  public void fragmentExpanded() {
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }

    if (currentFragment != null && currentFragment instanceof FragmentCast) {
      ((FragmentCast) currentFragment).hideTab();
    } else if (currentFragment != null && currentFragment instanceof HomeListFragment) {
      ((HomeListFragment) currentFragment).hideAdsView();
    }
  }

  @Override
  public void fragmentClosed() {
    Objects.requireNonNull(getSupportActionBar()).show();

    if (currentFragment != null && currentFragment instanceof FragmentCast) {
      ((FragmentCast) currentFragment).showTab();
    } else if (currentFragment != null && currentFragment instanceof HomeListFragment) {
      ((HomeListFragment) currentFragment).showAdsView();
    }
  }

  @Override public void onLikeDislikeUpdated(ModelVideo video) {
    if (currentFragment instanceof HomeListFragment) {
      ArrayList<ModelVideo> videoArrayList =
          ((HomeListFragment) currentFragment).videoAdapter.getVideos();
      for (int i = 0; i < videoArrayList.size(); i++) {
        ModelVideo modelVideo = videoArrayList.get(i);
        if (modelVideo.getId() == video.getId()) {
          videoArrayList.set(i, video);
          ((HomeListFragment) currentFragment).videoAdapter.setVideos(videoArrayList);
        }
      }
      ArrayList<ModelVideo> popularVideoList =
          ((HomeListFragment) currentFragment).videoAdapter.getPopularVideos();
      for (int i = 0; i < popularVideoList.size(); i++) {
        ModelVideo modelVideo = popularVideoList.get(i);
        if (modelVideo.getId() == video.getId()) {
          popularVideoList.set(i, video);
          ((HomeListFragment) currentFragment).videoAdapter.setPopularVideos(popularVideoList);
        }
      }

      ModelListHeader converted = new ModelListHeader(video);
      List<ModelListHeader> headerVideoList =
          ((HomeListFragment) currentFragment).videoAdapter.getHeaders();
      for (int i = 0; i < headerVideoList.size(); i++) {
        ModelListHeader modelVideo = headerVideoList.get(i);
        if (modelVideo.getId() == video.getId()) {

          headerVideoList.set(i, converted);
          ((HomeListFragment) currentFragment).videoAdapter.setHeaders(headerVideoList);
        }
      }
    }
  }

  @Override public void onLikeDislikeUpdated(ModelListHeader header) {
    if (currentFragment instanceof HomeListFragment) {
      List<ModelListHeader> headers =
          ((HomeListFragment) currentFragment).videoAdapter.getHeaders();
      for (int i = 0; i < headers.size(); i++) {
        ModelListHeader h = headers.get(i);

        if (h.getId() == header.getId()) {
          headers.set(i, header);
          ((HomeListFragment) currentFragment).videoAdapter.setHeaders(headers);
        }
      }
      ModelVideo converted = new ModelVideo(header);

      ArrayList<ModelVideo> videoArrayList =
          ((HomeListFragment) currentFragment).videoAdapter.getVideos();
      for (int i = 0; i < videoArrayList.size(); i++) {
        ModelVideo modelVideo = videoArrayList.get(i);
        if (modelVideo.getId() == header.getId()) {
          videoArrayList.set(i, converted);
          ((HomeListFragment) currentFragment).videoAdapter.setVideos(videoArrayList);
        }
      }

      ArrayList<ModelVideo> popularVideoList =
          ((HomeListFragment) currentFragment).videoAdapter.getPopularVideos();
      for (int i = 0; i < popularVideoList.size(); i++) {
        ModelVideo modelVideo = popularVideoList.get(i);
        if (modelVideo.getId() == header.getId()) {
          popularVideoList.set(i, converted);
          ((HomeListFragment) currentFragment).videoAdapter.setPopularVideos(popularVideoList);
        }
      }
    }
  }

  @Override public void onLeftMenuTabChanged(int currentTabPosition) {
    if (this.currentTabPosition != currentTabPosition) {
      binding.toolbarContainer.tabLayout.getTabAt(currentTabPosition).select();
    }
  }

  @Override public void onMediaDeleted(ModelVideo modelVideo) {
    ((HomeListFragment) currentFragment).videoAdapter.removeVideo(modelVideo);
  }

  @Override public void onMediaRemovedFromFavorite(ModelVideo modelVideo) {
    //((HomeListFragment) currentFragment).videoAdapter.removeVideo(modelVideo);
  }

  public void changeToolbar(int fragmentType) {
    Drawable drawable = null;
    switch (fragmentType) {
      case FRAGMENT_TYPE_CATEGORY_DETAIL: {
        drawable =
            ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_arrow, getTheme());
        binding.toolbarContainer.toolbarTitle.setVisibility(View.VISIBLE);
        binding.toolbarContainer.toolbarTitle.setText(DCCastApplication.utils.VIDEO_CHOOSE_TYPE);
        binding.toolbarContainer.tabLayout.setVisibility(View.GONE);

        if (((HomeListFragment) currentFragment).videoAdapter != null) {
          ((HomeListFragment) currentFragment).videoAdapter.hideHeaderPager();
          ((HomeListFragment) currentFragment).videoAdapter.hideVideosHeader();
        }

        ((HomeListFragment) currentFragment).enablePaging();

        break;
      }

      case FRAGMENT_TYPE_HOME_LIST: {
        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu, getTheme());
        binding.toolbarContainer.toolbarTitle.setVisibility(View.GONE);
        binding.toolbarContainer.tabLayout.setVisibility(View.VISIBLE);

        if (((HomeListFragment) currentFragment).videoAdapter != null) {
          ((HomeListFragment) currentFragment).videoAdapter.showHeaderPager();
          ((HomeListFragment) currentFragment).videoAdapter.showVideosHeader();
        }

        ((HomeListFragment) currentFragment).disablePaging();

        break;
      }

      case FRAGMENT_TYPE_OTHER_MENU: {
        binding.toolbarContainer.toolbarTitle.setVisibility(View.VISIBLE);
        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu, getTheme());
        binding.toolbarContainer.tabLayout.setVisibility(View.GONE);

        break;
      }
    }

    if (drawable != null) {
      binding.toolbarContainer.homeButtonImage.setImageDrawable(drawable);
    }
  }

  private void initNavigationDrawer() {
    ActionBarDrawerToggle mDrawerToggle =
        new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbarContainer.toolbar,
            R.string.open, R.string.close) {
          public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);

            if (tempIntent != null) {
              startActivity(tempIntent);
              tempIntent = null;
            }

            if (tempFragment != null) {
              setCurrentFragment(tempFragment);
              tempFragment = null;
            }
          }

          public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
          }
        };

    binding.drawerLayout.addDrawerListener(mDrawerToggle);
    binding.navigationDrawerRecyclerview.setLayoutManager(
        new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
    adapterNavigationDrawer = new AdapterNavigationDrawer(this);
    binding.navigationDrawerRecyclerview.setAdapter(adapterNavigationDrawer);
  }

  private void setCurrentFragment(Fragment fragment) {
    currentFragment = fragment;
    int fragmentType;

    if (fragment instanceof HomeListFragment) {
      fragmentType = FRAGMENT_TYPE_HOME_LIST;
    } else if (fragment instanceof FragmentCategoryDetail) {
      fragmentType = FRAGMENT_TYPE_CATEGORY_DETAIL;
    } else {
      fragmentType = FRAGMENT_TYPE_OTHER_MENU;
    }

    List<Fragment> fragments = getSupportFragmentManager().getFragments();
    if (fragments != null) {
      int lastIndex = fragments.size() - 1;

      if (lastIndex > 0) {
        Fragment lastFragment = fragments.get(lastIndex);
        if (lastFragment instanceof FragmentVideo) {
          Timber.e("CLEAR LAST FRAGMETN VIDEO");
          getSupportFragmentManager().popBackStackImmediate();
        }
      }
    }

    changeToolbar(fragmentType);

    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.container, fragment);
    fragmentTransaction.commit();

    getSupportFragmentManager().executePendingTransactions();
  }

  @Override protected void onPause() {
    super.onPause();

    Timber.e("onPause MainActivity");
    if (currentFragment != null && currentFragment instanceof FragmentVideo) {
      Timber.e("currentFragment: FragmentVideo");
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case REQUEST_VIDEO_TRIMMER: {
          final Uri selectedUri = data.getData();
          if (selectedUri != null) {
            Intent intent = new Intent(this, ActivityTrimmer.class);
            Bundle bundle = new Bundle();

            bundle.putString(EXTRA_VIDEO_PATH, FileUtils.getPath(this, selectedUri));
            bundle.putInt("groupId", -1);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_VOD_UPLOAD);
          } else {
            Toast.makeText(getApplicationContext(), R.string.toast_cannot_retrieve_selected_video,
                Toast.LENGTH_SHORT).show();
          }
          break;
        }

        case VIDEO_CAPTURE: {
          if (data == null) {
            return;
          }
          Uri selectedUri = data.getData();
          if (selectedUri != null) {
            Intent intent = new Intent(this, ActivityTrimmer.class);
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_VIDEO_PATH, FileUtils.getPath(this, selectedUri));
            bundle.putInt("groupId", -1);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_VOD_UPLOAD);
          } else {
            Toast.makeText(getApplicationContext(), R.string.toast_cannot_retrieve_selected_video,
                Toast.LENGTH_SHORT).show();
          }
          break;
        }

        case REQUEST_VOD_UPLOAD: {
          if (data != null && data.getExtras() != null) {
            Bundle bundle = data.getExtras();
            boolean isUpdate = bundle.getBoolean("isUpdate", false);
            String title = isUpdate ? getString(R.string.vod_edit_success)
                : getString(R.string.vod_upload_success);

            ModelVideo modelVideo = (ModelVideo) bundle.getSerializable("modelVideo");
            SuccessDialog successDialog = new SuccessDialog(mContext, title,
                modelVideo != null ? new SuccessDialog.OpenVideoInterface() {
                  @Override public void openVideoClicked() {
                    openVideo(modelVideo);
                  }
                } : null);
            successDialog.showDialog();
          }
          break;
        }

        case REQUEST_LIVE: {
          SuccessDialog successDialog =
              new SuccessDialog(mContext, getString(R.string.live_stop_success),
                  null);
          successDialog.showDialog();
          break;
        }

        case Constants.EDIT_VOD: {
          if (data != null && data.getExtras() != null) {
            Bundle bundle = data.getExtras();
            ModelVideo modelVideo =
                (ModelVideo) bundle.getSerializable(Constants.EDIT_VOD_RESPONSE);

            if (modelVideo != null) {
              if (currentFragment != null && currentFragment instanceof HomeListFragment) {
                HomeListFragment homeListFragment = (HomeListFragment) currentFragment;
                homeListFragment.updateVideo(modelVideo);
              }
            }
          }
          break;
        }
      }
    }

    if (resultCode == Constants.REQUEST_BLOCKED_ADMIN && requestCode == REQUEST_LIVE) {
      DialogHelper.showAlertDialog(mContext, false,
          null,
          getString(R.string.admin_blocked_title),
          "OK",
          (dialog, which) -> {

          }, null, null, null);
    }
  }

  private void openVideo(ModelVideo video) {
    if (currentFragment instanceof HomeListFragment) {
      ((HomeListFragment) currentFragment).openVideo(video);
    } else {
      Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
    }
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}