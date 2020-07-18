package notq.dccast.screens.navigation_menu.cast.group.group_detail;

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
import notq.dccast.databinding.FragmentGroupVideoBinding;
import notq.dccast.model.ModelGroupVideo;
import notq.dccast.model.ModelGroupVideoWrapper;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.home.view_holders.VHVideo;
import notq.dccast.util.Constants;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupVideoFragment extends Fragment {

  public AdapterVideos videoAdapter;
  private FragmentGroupVideoBinding binding;
  private String division;
  private int groupId;
  private int pageIndex = 1;
  private boolean isLoading = false, hasNextPage = false;

  public static GroupVideoFragment newInstance(String division, int userId) {
    GroupVideoFragment myFragment = new GroupVideoFragment();

    Bundle args = new Bundle();
    args.putString(Constants.DIVISION, division);
    args.putInt(Constants.GROUP_ID, userId);
    myFragment.setArguments(args);

    return myFragment;
  }

  @Override public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);

    if (isVisibleToUser) {
      refreshData();
    }
  }

  public void updateVideo(ModelVideo modelVideo) {
    if (videoAdapter != null) {
      videoAdapter.updateVideo(modelVideo);
    }
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      division = getArguments().getString(Constants.DIVISION);
      groupId = getArguments().getInt(Constants.GROUP_ID);
    }
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group_video, container, false);

    init();

    return binding.getRoot();
  }

  private void refreshData() {
    prepareRecyclerViewForNewItems();
    showLoader();
    getGroupVideos();
  }

  private void init() {
    videoAdapter = new AdapterVideos(this, false);
    videoAdapter.setViewType(VHVideo.TYPE_GROUP);

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
              pageIndex = pageIndex + 1;
              getGroupVideos();
            }
          }
        }
      }
    });
  }

  private void prepareRecyclerViewForNewItems() {
    videoAdapter.removeVideoItems();

    isLoading = false;
    pageIndex = 1;
  }

  private void getGroupVideos() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    videoAdapter.showLoadMoreLoader();

    hasNextPage = false;

    Call<ModelGroupVideoWrapper> call =
        APIClient.getClient()
            .create(CastListAPIInterface.class)
            .getGroupVideos(groupId, division, loginUser.getId(), pageIndex);

    call.enqueue(new Callback<ModelGroupVideoWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelGroupVideoWrapper> call,
          @NonNull Response<ModelGroupVideoWrapper> response) {
        ModelGroupVideoWrapper mediaWrapper = response.body();

        if (mediaWrapper != null && mediaWrapper.mediaList != null) {
          hasNextPage = mediaWrapper.next != null && !mediaWrapper.next.isEmpty();
          for (int i = 0; i < mediaWrapper.mediaList.size(); i++) {
            ModelGroupVideo groupVideo = mediaWrapper.mediaList.get(i);
            if (groupVideo.getMedia() != null) {
              ModelVideo video = groupVideo.getMedia();
              videoAdapter.addVideo(video);
            }
          }
        }

        if (videoAdapter.getVideoCount() > 0) {
          hideNoDataView();
        } else {
          showNoDataView();
        }

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelGroupVideoWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders();
      }
    });
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
