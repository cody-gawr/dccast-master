package notq.dccast.screens.navigation_menu.favorite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.databinding.ActivityFavoriteBinding;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelVideo;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.home.interfaces.HomeChildFragmentListener;
import notq.dccast.util.Constants;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityFavorite extends BaseActivity implements HomeChildFragmentListener {
  private ActivityFavoriteBinding binding;
  private FavoriteListFragment fragment;
  private Context mContext = this;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite);

    initToolbar();
    setFavoriteCount(0);
    setCurrentFragment(new FavoriteListFragment());
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_favorite));
    binding.header.backButton.setOnClickListener(v -> finish());
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == Constants.EDIT_VOD && resultCode == Activity.RESULT_OK) {
      if (data != null && data.getExtras() != null) {
        Bundle bundle = data.getExtras();
        ModelVideo modelVideo = (ModelVideo) bundle.getSerializable(Constants.EDIT_VOD_RESPONSE);

        if (modelVideo != null) {
          if (fragment != null) {
            fragment.updateVideo(modelVideo);
          }
        }
      }
    }
  }

  private void setCurrentFragment(FavoriteListFragment fragment) {
    this.fragment = fragment;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.container, this.fragment);
    fragmentTransaction.commit();
  }

  @Override
  public void fragmentCollapsed() {
    Objects.requireNonNull(getSupportActionBar()).show();
    binding.layoutFavourite.setVisibility(View.VISIBLE);
  }

  @Override
  public void fragmentExpanded() {
    Objects.requireNonNull(getSupportActionBar()).hide();
    binding.layoutFavourite.setVisibility(View.GONE);
  }

  @Override
  public void fragmentClosed() {
    Objects.requireNonNull(getSupportActionBar()).show();
    binding.layoutFavourite.setVisibility(View.VISIBLE);
  }

  @Override public void onLikeDislikeUpdated(ModelVideo video) {
    if (fragment.videoAdapter.getVideos().size() > 0) {
      ArrayList<ModelVideo> videos = fragment.videoAdapter.getVideos();

      for (int i = 0; i < videos.size(); i++) {
        ModelVideo video_ = videos.get(i);

        if (video_.getId() == video.getId()) {
          fragment.videoAdapter.getVideos().set(i, video);
          fragment.videoAdapter.notifyItemChanged(i);
        }
      }
    }
  }

  @Override public void onLikeDislikeUpdated(ModelListHeader header) {
  }

  @Override public void onLeftMenuTabChanged(int tabPosition) {

  }

  @Override public void onMediaDeleted(ModelVideo modelVideo) {
    fragment.onMediaDeleted(modelVideo);
  }

  @Override public void onMediaRemovedFromFavorite(ModelVideo modelVideo) {
    fragment.onMediaRemovedFromFavorite(modelVideo);
  }

  public void setFavoriteCount(int count) {
    binding.lblFavorite.setText(getString(R.string.favorite_video_count, count));
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
