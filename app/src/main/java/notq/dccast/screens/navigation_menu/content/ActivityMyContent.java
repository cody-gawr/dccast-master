package notq.dccast.screens.navigation_menu.content;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.databinding.ActivityMyContentBinding;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelVideo;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.home.interfaces.HomeChildFragmentListener;
import notq.dccast.screens.navigation_menu.content.my_channel.ActivityMyChannel;
import notq.dccast.screens.navigation_menu.content.recent.ActivityRecent;
import notq.dccast.screens.navigation_menu.content.subscribe.ActivitySubscribe;
import notq.dccast.screens.navigation_menu.favorite.ActivityFavorite;
import notq.dccast.util.Constants;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityMyContent extends BaseActivity implements HomeChildFragmentListener {

  private Context mContext = this;
  private ActivityMyContentBinding binding;
  private RecentListFragment recentListFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_my_content);

    initToolbar();
    init();

    recentListFragment = new RecentListFragment();
    setCurrentFragment(recentListFragment);
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_my_content));
    binding.header.backButton.setOnClickListener(v -> {
      finish();
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == Constants.EDIT_VOD && resultCode == Activity.RESULT_OK) {
      if (data != null && data.getExtras() != null) {
        Bundle bundle = data.getExtras();
        ModelVideo modelVideo = (ModelVideo) bundle.getSerializable(Constants.EDIT_VOD_RESPONSE);

        if (modelVideo != null) {
          if (recentListFragment != null) {
            recentListFragment.updateVideo(modelVideo);
          }
        }
      }
    }
  }

  private void init() {
    binding.itemMyChannel.setOnClickListener(v -> {
      Intent channelIntent = new Intent(mContext, ActivityMyChannel.class);
      channelIntent.putExtra(Constants.CHANNEL_DETAIL_IS_ME, true);
      startActivity(channelIntent);
    });

    binding.itemFavorite.setOnClickListener(v -> {
      Intent favoriteIntent = new Intent(mContext, ActivityFavorite.class);
      startActivity(favoriteIntent);
    });

    binding.itemSubscribe.setOnClickListener(v -> {
      Intent subscribeIntent = new Intent(mContext, ActivitySubscribe.class);
      startActivity(subscribeIntent);
    });

    binding.itemRecent.setOnClickListener(v -> {
      Intent recentIntent = new Intent(mContext, ActivityRecent.class);
      startActivity(recentIntent);
    });
  }

  private void setCurrentFragment(Fragment fragment) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.container, fragment);
    fragmentTransaction.commit();
  }

  @Override
  public void fragmentCollapsed() {
    Objects.requireNonNull(getSupportActionBar()).show();
    binding.optionLayout.setVisibility(View.VISIBLE);
  }

  @Override
  public void fragmentExpanded() {
    Objects.requireNonNull(getSupportActionBar()).hide();
    binding.optionLayout.setVisibility(View.GONE);
  }

  @Override
  public void fragmentClosed() {
    Objects.requireNonNull(getSupportActionBar()).show();
    binding.optionLayout.setVisibility(View.VISIBLE);
  }

  @Override public void onLikeDislikeUpdated(ModelVideo video) {
    if (recentListFragment.videoAdapter.getVideos().size() > 0) {
      ArrayList<ModelVideo> videos = recentListFragment.videoAdapter.getVideos();

      for (int i = 0; i < videos.size(); i++) {
        ModelVideo video_ = videos.get(i);

        if (video_.getId() == video.getId()) {
          recentListFragment.videoAdapter.getVideos().set(i, video);
          recentListFragment.videoAdapter.notifyItemChanged(i);
        }
      }
    }
  }

  @Override public void onLikeDislikeUpdated(ModelListHeader header) {

  }

  @Override public void onLeftMenuTabChanged(int tabPosition) {

  }

  @Override public void onMediaDeleted(ModelVideo modelVideo) {
    recentListFragment.videoAdapter.removeVideo(modelVideo);
  }

  @Override public void onMediaRemovedFromFavorite(ModelVideo modelVideo) {

  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
