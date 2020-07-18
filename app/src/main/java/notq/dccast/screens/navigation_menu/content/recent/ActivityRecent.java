package notq.dccast.screens.navigation_menu.content.recent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import java.util.ArrayList;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.databinding.ActivityRecentBinding;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelVideo;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.home.interfaces.HomeChildFragmentListener;
import notq.dccast.util.Constants;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityRecent extends BaseActivity implements HomeChildFragmentListener {
  private Context mContext = this;
  private ActivityRecentBinding binding;
  private AdapterRecent pagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_recent);
    initToolbar();
    init();
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_recent));
    binding.header.backButton.setOnClickListener(v -> finish());
  }

  private void init() {
    pagerAdapter = new AdapterRecent(mContext, getSupportFragmentManager());
    binding.viewPager.setOffscreenPageLimit(3);
    binding.viewPager.setAdapter(pagerAdapter);
    binding.tabLayout.setupWithViewPager(binding.viewPager);

    binding.viewPager.setCurrentItem(1);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == Constants.EDIT_VOD && resultCode == Activity.RESULT_OK) {
      if (data != null && data.getExtras() != null) {
        Bundle bundle = data.getExtras();
        ModelVideo modelVideo = (ModelVideo) bundle.getSerializable(Constants.EDIT_VOD_RESPONSE);

        if (modelVideo != null) {
          RecentFragment myChannelFragment = pagerAdapter.getItem(0);
          if (myChannelFragment != null) {
            myChannelFragment.updateVideo(modelVideo);
          }
        }
      }
    }
  }

  @Override
  public void fragmentCollapsed() {
    Objects.requireNonNull(getSupportActionBar()).show();
    binding.tabLayout.setVisibility(View.VISIBLE);
  }

  @Override
  public void fragmentExpanded() {
    Objects.requireNonNull(getSupportActionBar()).hide();
    binding.tabLayout.setVisibility(View.GONE);
  }

  @Override
  public void fragmentClosed() {
    Objects.requireNonNull(getSupportActionBar()).show();
    binding.tabLayout.setVisibility(View.VISIBLE);
  }

  @Override public void onLikeDislikeUpdated(ModelVideo video) {
    RecentFragment recentFragment =
        pagerAdapter.myChannelPages.get(binding.viewPager.getCurrentItem());

    if (recentFragment.videoAdapter.getVideos().size() > 0) {
      ArrayList<ModelVideo> videos = recentFragment.videoAdapter.getVideos();

      for (int i = 0; i < videos.size(); i++) {
        ModelVideo video_ = videos.get(i);

        if (video_.getId() == video.getId()) {
          recentFragment.videoAdapter.getVideos().set(i, video);
          recentFragment.videoAdapter.notifyItemChanged(i);
        }
      }
    }
  }

  @Override public void onLikeDislikeUpdated(ModelListHeader header) {

  }

  @Override public void onLeftMenuTabChanged(int tabPosition) {

  }

  @Override public void onMediaDeleted(ModelVideo modelVideo) {
    for (RecentFragment myChannelPage : pagerAdapter.getMyChannelPages()) {
      myChannelPage.videoAdapter.removeVideo(modelVideo);
    }
  }

  @Override public void onMediaRemovedFromFavorite(ModelVideo modelVideo) {

  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
