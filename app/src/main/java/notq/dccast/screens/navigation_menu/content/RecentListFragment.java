package notq.dccast.screens.navigation_menu.content;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.my_content.MyContentAPIInterface;
import notq.dccast.databinding.FragmentContentRecentBinding;
import notq.dccast.model.ModelRecentVideoInside;
import notq.dccast.model.ModelRecentVideoWrapper;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.home.view_holders.VHVideo;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecentListFragment extends Fragment {

  public AdapterVideos videoAdapter;
  private FragmentContentRecentBinding binding;
  private int pageIndex = 1;
  private boolean isLoading = false, hasNextPage = false;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_recent, container, false);

    init();

    getRecent();

    return binding.getRoot();
  }

  public void updateVideo(ModelVideo modelVideo) {
    if (videoAdapter != null) {
      videoAdapter.updateVideo(modelVideo);
    }
  }

  private void init() {
    videoAdapter = new AdapterVideos(this, false);
    videoAdapter.setViewType(VHVideo.TYPE_CONTENT);
    videoAdapter.setNeedShowLoader(false);

    LinearLayoutManager layoutManager =
        new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.setAdapter(videoAdapter);

    binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) {
          int visibleItemCount = layoutManager.getChildCount();
          int totalItemCount = layoutManager.getItemCount();
          int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

          if (!isLoading && hasNextPage) {
            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
              isLoading = true;
              pageIndex += 1;
              getRecent();
            }
          }
        }
      }
    });
  }

  private void getRecent() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    videoAdapter.showLoadMoreLoader();

    hasNextPage = false;

    Call<ModelRecentVideoWrapper> callVod =
        APIClient.getClient()
            .create(MyContentAPIInterface.class)
            .getRecent(loginUser.getId(), pageIndex);

    callVod.enqueue(new Callback<ModelRecentVideoWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelRecentVideoWrapper> call,
          @NonNull Response<ModelRecentVideoWrapper> response) {
        ModelRecentVideoWrapper videoWrapper = response.body();

        if (videoWrapper != null && videoWrapper.videoWrappers != null) {
          hasNextPage = videoWrapper.next != null && !videoWrapper.next.isEmpty();
          for (int i = 0; i < videoWrapper.videoWrappers.size(); i++) {
            ModelRecentVideoInside insideVideo = videoWrapper.videoWrappers.get(i);
            if (insideVideo.video == null || insideVideo.user == null) {
              continue;
            }
            ModelVideo video = insideVideo.video;
            video.user = insideVideo.user;
            videoAdapter.addVideo(video);
          }
        }

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelRecentVideoWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders();
      }
    });
  }

  private void hideLoaders() {
    if (binding.dcLoader.isShown() && binding.dcLoader.isAnimating()) {
      binding.dcLoader.setVisibility(View.GONE);
      binding.dcLoader.cancelAnimation();
    }

    Log.e("RecentListFragment", "Adapter Item: " + videoAdapter.getItemCount());

    videoAdapter.hideLoadMoreLoader();
    videoAdapter.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);

    isLoading = false;
  }

  private void hideAllLoaders() {
    new CountDownTimer(1500, 1500) {
      @Override
      public void onTick(long l) {

      }

      @Override
      public void onFinish() {
        if (binding.dcLoader.isShown() && binding.dcLoader.isAnimating()) {
          binding.dcLoader.setVisibility(View.GONE);
          binding.dcLoader.cancelAnimation();
        }
      }
    }.start();

    hideLoaders();
  }
}
