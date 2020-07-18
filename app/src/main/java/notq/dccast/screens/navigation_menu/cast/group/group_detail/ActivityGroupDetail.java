package notq.dccast.screens.navigation_menu.cast.group.group_detail;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import com.bumptech.glide.Glide;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import life.knowledge4.videotrimmer.utils.FileUtils;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.api.home.HomeAPIInterface;
import notq.dccast.databinding.ActivityGroupDetailBinding;
import notq.dccast.model.ModelBlockDivisionResult;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.group.ModelGroup;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.home.interfaces.HomeChildFragmentListener;
import notq.dccast.screens.home.video_detail.FragmentVideo;
import notq.dccast.screens.login.ActivityLogin;
import notq.dccast.screens.navigation_menu.cast.group.create_group.ActivityCreateGroup;
import notq.dccast.screens.navigation_menu.live.ActivityLiveSettings;
import notq.dccast.screens.video_trimmer.ActivityTrimmer;
import notq.dccast.util.Constants;
import notq.dccast.util.LoginService;
import notq.dccast.util.SuccessDialog;
import notq.dccast.util.Util;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static notq.dccast.screens.home.ActivityHome.EXTRA_VIDEO_PATH;

public class ActivityGroupDetail extends BaseActivity
    implements HomeChildFragmentListener {
  private static final int REQUEST_VIDEO_TRIMMER = 0x01;
  private static final int VIDEO_CAPTURE = 1029;
  private final int REQUEST_EDIT_GROUP = 2002;
  private final int REQUEST_VOD_UPLOAD = 2003;
  private final int REQUEST_LIVE = 2004;
  private Context mContext = this;
  private ActivityGroupDetailBinding binding;
  private ModelGroup modelGroup;
  private AdapterGroupDetail pagerAdapter;
  private ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_group_detail);

    modelGroup = (ModelGroup) getIntent().getSerializableExtra(Constants.GROUP_DETAIL);

    if (modelGroup == null) {
      Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
      finish();
      return;
    }

    progressDialog = new ProgressDialog(mContext);

    initToolbar();
    init();

    displayGroup();
  }

  private void displayGroup() {
    String name = modelGroup.getName();
    String description = modelGroup.getMessage();

    if (name != null) {
      binding.lblGroupName.setText(name);
      binding.header.lblHeader.setText(name);
    }

    if (description != null) {
      binding.lblGroupDescription.setText(description);
    }

    if (modelGroup.getProfileImg() != null && !modelGroup.getProfileImg().isEmpty()) {
      Glide.with(mContext)
          .load(Util.getPhotoUrl(modelGroup.getProfileImg()))
          .placeholder(R.drawable.ic_group_placeholder)
          .error(R.drawable.ic_group_placeholder)
          .into(binding.ivGroupProfileImg);
    }
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.backButton.setOnClickListener(v -> finish());

    ModelUser loginUser = LoginService.getLoginUser();

    //&& modelGroup.getContactPerson() == loginUser.getId()
    if (loginUser != null) {
      binding.header.ivActionBtn.setVisibility(View.VISIBLE);
      binding.header.layoutAction.setOnClickListener(v -> {
        Intent groupIntent = new Intent(mContext, ActivityCreateGroup.class);
        groupIntent.putExtra(Constants.GROUP_DETAIL, modelGroup);
        startActivityForResult(groupIntent, REQUEST_EDIT_GROUP);
      });
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    binding.dim.setVisibility(View.GONE);
  }

  private void init() {
    pagerAdapter =
        new AdapterGroupDetail(mContext, modelGroup.getId(), getSupportFragmentManager());
    binding.viewPager.setOffscreenPageLimit(3);
    binding.viewPager.setAdapter(pagerAdapter);
    binding.tabLayout.setupWithViewPager(binding.viewPager);

    binding.layoutStartLive.setOnClickListener(v -> {
      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser == null) {
        Toast.makeText(ActivityGroupDetail.this, getString(R.string.login_required),
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

      if (!Util.isNetworkConnected(mContext)) {
        Toast.makeText(mContext, getString(R.string.no_internet_connection), Toast.LENGTH_LONG)
            .show();
        return;
      }

      checkBlockedFromAdmin(true);
    });

    binding.layoutUploadVod.setOnClickListener(v -> {

      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser == null) {
        Toast.makeText(ActivityGroupDetail.this, getString(R.string.login_required),
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

      if (!Util.isNetworkConnected(mContext)) {
        Toast.makeText(mContext, getString(R.string.no_internet_connection), Toast.LENGTH_LONG)
            .show();
        return;
      }

      checkBlockedFromAdmin(false);
    });
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
                Toast.makeText(mContext, getString(R.string.blocked_from_admin), Toast.LENGTH_LONG)
                    .show();
                return;
              }
            } catch (Exception ex) {
              Timber.e("Error: " + ex.getMessage());
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
          liveSettingsIntent.putExtra("groupId", modelGroup.getId());
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

              String[] readPermissions = { Manifest.permission.READ_EXTERNAL_STORAGE };
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
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case REQUEST_EDIT_GROUP: {
          if (data == null) {
            return;
          }
          modelGroup = (ModelGroup) data.getSerializableExtra(Constants.GROUP_DETAIL);

          if (modelGroup == null) {
            Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
            finish();
            return;
          }

          displayGroup();

          if (pagerAdapter != null) {
            Fragment fragment = pagerAdapter.getItem(0);
            if (fragment instanceof GroupMemberFragment) {
              ((GroupMemberFragment) fragment).refresh();
            }
          }

          break;
        }

        case REQUEST_VIDEO_TRIMMER: {
          if (data == null) {
            Toast.makeText(getApplicationContext(), R.string.toast_cannot_retrieve_selected_video,
                Toast.LENGTH_SHORT).show();
            return;
          }
          final Uri selectedUri = data.getData();
          if (selectedUri != null) {
            Intent intent = new Intent(this, ActivityTrimmer.class);
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_VIDEO_PATH, FileUtils.getPath(this, selectedUri));
            bundle.putInt("groupId", modelGroup.id);
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
            Toast.makeText(getApplicationContext(), R.string.toast_cannot_retrieve_selected_video,
                Toast.LENGTH_SHORT).show();
            return;
          }
          Uri selectedUri = data.getData();
          if (selectedUri != null) {
            Intent intent = new Intent(this, ActivityTrimmer.class);
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_VIDEO_PATH, FileUtils.getPath(this, selectedUri));
            bundle.putInt("groupId", modelGroup.id);
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
              for (int i = 0; i < pagerAdapter.getCount(); i++) {
                if (pagerAdapter.getItem(i) instanceof GroupVideoFragment) {
                  GroupVideoFragment myChannelFragment =
                      (GroupVideoFragment) pagerAdapter.getItem(i);
                  if (myChannelFragment != null) {
                    myChannelFragment.updateVideo(modelVideo);
                  }
                }
              }
            }
          }
          break;
        }
      }
    } else if (resultCode == Constants.GROUP_LEAVED) {
      setResult(resultCode);
      finish();
    }
  }

  private void openVideo(ModelVideo video) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.addToBackStack(null);
    ft.setCustomAnimations(R.anim.slide_in_up, android.R.anim.slide_out_right);

    FragmentVideo fragmentVideo = new FragmentVideo();
    Bundle bundle = new Bundle();
    bundle.putSerializable("data", video);
    bundle.putSerializable("header_data", null);
    fragmentVideo.setArguments(bundle);

    ft.replace(R.id.container_for_group_video, fragmentVideo).commit();
  }

  @Override
  public void fragmentCollapsed() {
    Objects.requireNonNull(getSupportActionBar()).show();
    binding.appBar.setVisibility(View.VISIBLE);
    binding.layoutHeader.setVisibility(View.VISIBLE);
  }

  @Override
  public void fragmentExpanded() {
    Objects.requireNonNull(getSupportActionBar()).hide();
    binding.appBar.setVisibility(View.GONE);
    binding.layoutHeader.setVisibility(View.GONE);
  }

  @Override
  public void fragmentClosed() {
    Objects.requireNonNull(getSupportActionBar()).show();
    binding.appBar.setVisibility(View.VISIBLE);
    binding.layoutHeader.setVisibility(View.VISIBLE);
  }

  @Override public void onLikeDislikeUpdated(ModelVideo video) {
    if (binding.viewPager.getCurrentItem() > 0) {
      GroupVideoFragment liveListFragment =
          (GroupVideoFragment) ((AdapterGroupDetail) binding.viewPager.getAdapter()).myChannelPages.get(
              binding.viewPager.getCurrentItem());

      ArrayList<ModelVideo> videos = liveListFragment.videoAdapter.getVideos();

      for (int i = 0; i < videos.size(); i++) {
        ModelVideo video_ = videos.get(i);

        if (video_.getId() == video.getId()) {
          liveListFragment.videoAdapter.getVideos().set(i, video);
          liveListFragment.videoAdapter.notifyItemChanged(i);
        }
      }
    }
  }

  @Override public void onLikeDislikeUpdated(ModelListHeader header) {

  }

  @Override public void onLeftMenuTabChanged(int tabPosition) {

  }

  @Override public void onMediaDeleted(ModelVideo modelVideo) {
    for (Fragment myChannelPage : pagerAdapter.getMyChannelPages()) {
      if (myChannelPage instanceof GroupVideoFragment) {
        ((GroupVideoFragment) myChannelPage).videoAdapter.removeVideo(modelVideo);
      }
    }
  }

  @Override public void onMediaRemovedFromFavorite(ModelVideo modelVideo) {

  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  private class AdapterGroupDetail extends FragmentStatePagerAdapter {
    private List<Fragment> myChannelPages;
    private Context context;

    AdapterGroupDetail(Context context, int groupId, @NonNull FragmentManager fm) {
      super(fm);
      this.context = context;
      myChannelPages = new ArrayList<>();
      myChannelPages.add(GroupMemberFragment.newInstance(groupId));
      myChannelPages.add(GroupVideoFragment.newInstance("live_list", groupId));
      myChannelPages.add(GroupVideoFragment.newInstance("vod_list", groupId));
    }

    public List<Fragment> getMyChannelPages() {
      return myChannelPages;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
      return myChannelPages.get(position);
    }

    @Override
    public int getCount() {
      return myChannelPages.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
      switch (position) {
        case 0: {
          return context.getString(R.string.group_tab_member);
        }
        case 1: {
          return context.getString(R.string.group_tab_live);
        }
        case 2: {
          return context.getString(R.string.group_tab_vod);
        }
        default:
          return "";
      }
    }
  }
}

