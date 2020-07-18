package notq.dccast.screens.home.view_holders;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.HomeAPIInterface;
import notq.dccast.databinding.VhHomeMainHeaderBinding;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.ModelVideoWrapper;
import notq.dccast.screens.home.adapter.AdapterHomeHeader;
import notq.dccast.screens.home.adapter.AdapterVideos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class VHMainHeader extends RecyclerView.ViewHolder {
  private AdapterVideos videoAdapter;
  private AdapterHomeHeader headerAdapter;
  private VhHomeMainHeaderBinding binding;
  private Context context;
  private Fragment fragment;
  private Call<ModelVideoWrapper> videoRequest;

  public VHMainHeader(Fragment fragment,
      @NonNull VhHomeMainHeaderBinding binding) {
    super(binding.getRoot());
    this.binding = binding;
    this.fragment = fragment;
    this.context = fragment.getContext();
    try {
      headerAdapter = new AdapterHomeHeader(fragment.getChildFragmentManager());
      binding.viewPager.setAdapter(headerAdapter);
      binding.viewPager.setOffscreenPageLimit(5);

      videoAdapter = new AdapterVideos(fragment, false);
      videoAdapter.setViewType(VHVideo.TYPE_POPULAR);
      videoAdapter.setNeedShowLoader(false);

      binding.recyclerView.setLayoutManager(
          new LinearLayoutManager(fragment.getContext()));
      binding.recyclerView.setAdapter(videoAdapter);

      SnapHelper helper = new LinearSnapHelper();
      helper.attachToRecyclerView(binding.recyclerView);

      //if(isLive != lastCalled) {
      toggleLive(DCCastApplication.isLive);
      //}
    } catch (Exception ex) {
      Timber.e("ERROR MAIN HEADER: " + ex.getMessage());
    }
  }

  public boolean deleteVideo(ModelVideo deleteVideo) {
    if (videoAdapter.getVideos() != null) {
      for (ModelVideo video : videoAdapter.getVideos()) {
        if (video.getId() == deleteVideo.getId()) {
          videoAdapter.removeVideo(deleteVideo);
          return true;
        }
      }
    }

    return false;
  }

  public void updateHeaderVideo(ModelListHeader updateVideo) {
    if (headerAdapter.getHeaderItems() != null) {
      List<ModelListHeader> header = headerAdapter.getHeaderItems();
      for (int i = 0; i < header.size() ; i++) {
        if(header.get(i).getId() == updateVideo.getId()) {
          headerAdapter.updateItem(i, updateVideo);
          break;
        }
      }
    }
  }

  public boolean updateVideo(ModelVideo updateVideo) {
    boolean returnValue = false;
    if (headerAdapter.getHeaderItems() != null) {
      List<ModelListHeader> header = headerAdapter.getHeaderItems();
      for (int i = 0; i < header.size() ; i++) {
        if(header.get(i).getId() == updateVideo.getId()) {
          headerAdapter.updateItem(i, new ModelListHeader(updateVideo));
          returnValue = true;
          break;
        }
      }
    }

    //POPULAR VIDEO-ноос хайх
    if (videoAdapter.getVideos() != null) {
      for (ModelVideo video : videoAdapter.getVideos()) {
        if (video.getId() == updateVideo.getId()) {
          videoAdapter.updateVideo(updateVideo);
          returnValue = true;
          break;
        }
      }
    }

    return returnValue;
  }

  public void toggleLive(boolean isLive) {
    if (videoRequest != null) {
      videoRequest.cancel();
    }
    binding.videosType.setText(
        context.getString(R.string.popular) + " " + (isLive ? context.getString(R.string.tab_live)
            : context.getString(R.string.tab_vod)));
    videoAdapter.removeVideoItems();

    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        fragment.getActivity().runOnUiThread(new Runnable() {
          @Override public void run() {
            getVideos(isLive);
          }
        });
      }
    }, 500);
  }

  public void getVideos(boolean isLive) {
    HomeAPIInterface apiInterface = APIClient.getClient().create(HomeAPIInterface.class);
    videoRequest = apiInterface.getPopularVideo(isLive ? "Live" : "VOD", 10, "-views");
    videoRequest.enqueue(new Callback<ModelVideoWrapper>() {
      @Override public void onResponse(@NonNull Call<ModelVideoWrapper> call,
          @NonNull Response<ModelVideoWrapper> response) {
        ModelVideoWrapper videoWrapper = response.body();

        if (videoWrapper != null && videoWrapper.videoList != null) {
          for (ModelVideo video : videoWrapper.videoList) {
            videoAdapter.addVideo(video);
          }
        }
      }

      @Override public void onFailure
          (@NonNull Call<ModelVideoWrapper> call, @NonNull Throwable t) {
        call.cancel();
      }
    });
  }

  public ArrayList<ModelVideo> getPopularVideos() {
    if (videoAdapter == null || videoAdapter.getVideos() == null) {
      return new ArrayList<>();
    }
    return videoAdapter.getVideos();
  }

  public void setPopularVideos(ArrayList<ModelVideo> popularVideos) {
    videoAdapter.setVideos(popularVideos);
  }

  public void hidePopularVideos() {
    binding.layoutPopular.setVisibility(View.GONE);
    binding.pagerContainer.setVisibility(View.GONE);
  }

  public void showPopularVideos() {
    binding.layoutPopular.setVisibility(View.VISIBLE);
    binding.pagerContainer.setVisibility(View.VISIBLE);
  }

  public void setHeaders(List<ModelListHeader> headerItems) {
    headerAdapter.setHeaderItems(headerItems);

    if (headerAdapter.getCount() > 0) {
      binding.pageIndicatorView.setVisibility(View.VISIBLE);
    } else {
      binding.pageIndicatorView.setVisibility(View.GONE);
    }
  }

  public void show() {
    binding.pagerContainer.setVisibility(View.VISIBLE);
    binding.layoutPopular.setVisibility(View.VISIBLE);
  }

  public void hide() {
    binding.pagerContainer.setVisibility(View.GONE);
    binding.layoutPopular.setVisibility(View.GONE);
  }
}
