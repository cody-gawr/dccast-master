package notq.dccast.screens.navigation_menu.my_live_history;

import android.os.Bundle;
import android.os.CountDownTimer;
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
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.databinding.FragmentMyLiveHistoryBinding;
import notq.dccast.model.ModelLiveHistoryVideoResponse;
import notq.dccast.model.ModelMyLiveHistoryWrapper;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.home.view_holders.VHVideo;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyLiveHistoryFragment extends Fragment {
  public AdapterVideos videoAdapter;
  private FragmentMyLiveHistoryBinding binding;
  private ActivityMyLiveHistory activityMyLiveHistory;
  private int pageIndex = 1;
  private boolean isLoading = false, hasNextPage = false;
  private int totalCount = 0;

  private int followerId = -1;

  public static MyLiveHistoryFragment newInstance(int followerId) {
    MyLiveHistoryFragment myFragment = new MyLiveHistoryFragment();

    Bundle args = new Bundle();
    args.putInt("followerId", followerId);
    myFragment.setArguments(args);

    return myFragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityMyLiveHistory = (ActivityMyLiveHistory) getActivity();

    if (getArguments() != null) {
      followerId = getArguments().getInt("followerId");
    }
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_my_live_history, container, false);
    init();
    showLoader();
    getMyLiveHistoryMedia();
    return binding.getRoot();
  }

  public void updateVideo(ModelVideo modelVideo) {
    if (videoAdapter != null) {
      videoAdapter.updateVideo(modelVideo);
    }
  }

  private void init() {
    videoAdapter = new AdapterVideos(this, false);
    videoAdapter.setViewType(VHVideo.TYPE_MY_LIVE);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
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
              getMyLiveHistoryMedia();
            }
          }
        }
      }
    });

    binding.refresher.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
    binding.refresher.setOnRefreshListener(() -> {
      binding.refresher.setRefreshing(false);
      prepareRecyclerViewForNewItems();
      showLoader();
      getMyLiveHistoryMedia();
    });
  }

  private void prepareRecyclerViewForNewItems() {
    videoAdapter.removeVideoItems();
    isLoading = false;
    pageIndex = 1;
  }

  private void getMyLiveHistoryMedia() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    videoAdapter.showLoadMoreLoader();

    Call<ModelMyLiveHistoryWrapper> callVod =
        APIClient.getClient()
            .create(CastListAPIInterface.class)
            .getMyLiveHistoryMedia(loginUser.getId(), followerId, pageIndex);

    callVod.enqueue(new Callback<ModelMyLiveHistoryWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelMyLiveHistoryWrapper> call,
          @NonNull Response<ModelMyLiveHistoryWrapper> response) {
        ModelMyLiveHistoryWrapper videoWrapper = response.body();

        if (videoWrapper != null && videoWrapper.videoWrappers != null) {
          hasNextPage = videoWrapper.next != null && !videoWrapper.next.isEmpty();
          for (int i = 0; i < videoWrapper.videoWrappers.size(); i++) {
            ModelLiveHistoryVideoResponse insideVideo = videoWrapper.videoWrappers.get(i);
            ModelVideo video = new ModelVideo(insideVideo);
            videoAdapter.addVideo(video);
          }
        }

        totalCount = videoWrapper.getCount();
        initCount();

        if (videoAdapter.getVideoCount() > 0) {
          hideNoDataView();
        } else {
          showNoDataView();
        }

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelMyLiveHistoryWrapper> call, @NonNull Throwable t) {
        call.cancel();

        totalCount = 0;
        initCount();
        hideAllLoaders();
      }
    });
  }

  private void initCount() {
    if (activityMyLiveHistory != null) {
      activityMyLiveHistory.setMyLiveHistoryCount(totalCount < 0 ? 0 : totalCount);
    }
  }

  private void showNoDataView() {
    binding.layoutNoData.setVisibility(View.VISIBLE);
  }

  private void hideNoDataView() {
    binding.layoutNoData.setVisibility(View.GONE);
  }

  private void showLoader() {
    if (!binding.dcLoader.isAnimating() && !binding.dcLoader.isShown()) {
      binding.dcLoader.playAnimation();
      binding.dcLoader.setVisibility(View.VISIBLE);
    }
  }

  private void hideLoaders() {
    if (binding.dcLoader.isShown() && binding.dcLoader.isAnimating()) {
      binding.dcLoader.setVisibility(View.GONE);
      binding.dcLoader.cancelAnimation();
    }

    if (binding.refresher.isRefreshing()) {
      binding.refresher.setRefreshing(false);
    }

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

  public void onMediaDeleted(ModelVideo modelVideo) {
    videoAdapter.removeVideo(modelVideo);
  }

  public void onMediaRemovedFromFavorite(ModelVideo modelVideo) {
    videoAdapter.removeVideo(modelVideo);

    totalCount -= 1;
    initCount();
  }
}
