package notq.dccast.screens.home.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.HomeAPIInterface;
import notq.dccast.databinding.FragmentSearchResultPageBinding;
import notq.dccast.model.ModelVideoWrapper;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.home.view_holders.VHVideo;
import notq.dccast.util.LoginService;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultPage extends Fragment {
  public AdapterVideos videoAdapter;
  private FragmentSearchResultPageBinding binding;
  private int pastVisiblesItems, visibleItemCount, totalItemCount, pageIndex = 1;
  private boolean loading = true;
  private String division = "";
  private String keyword = "";
  private boolean firstTime = true;

  public SearchResultPage() {
    // Required empty public constructor
  }

  @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_search_result_page, container, false);

    return binding.getRoot();
  }

  private void init() {
    firstTime = false;
    binding.loader.setVisibility(View.VISIBLE);
    videoAdapter = new AdapterVideos(this, division.equals("LIVE"));
    videoAdapter.setViewType(VHVideo.TYPE_SEARCH);

    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
    gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
    gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int position) {
        return 2;
      }
    });

    binding.recyclerView.setLayoutManager(gridLayoutManager);
    binding.recyclerView.setAdapter(videoAdapter);

    binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) {
          visibleItemCount = gridLayoutManager.getChildCount();
          totalItemCount = gridLayoutManager.getItemCount();
          pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition();

          if (loading) {
            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
              loading = false;
              pageIndex = pageIndex + 1;
              searchRequest(division, keyword, false);
            }
          }
        }
      }
    });
  }

  void searchRequest(String division, String keyword, boolean clearList) {
    if (firstTime) {
      this.division = division;
      this.keyword = keyword;
      init();
    }

    if (clearList || !keyword.equalsIgnoreCase(this.keyword)) {
      videoAdapter.removeVideoItems();
    }

    JSONObject searchBody = new JSONObject();
    try {
      searchBody.put("kind", "search_in_title_username");
      searchBody.put("division", division);
      searchBody.put("keyword", keyword);
      searchBody.put("page", pageIndex);
      if (LoginService.getLoginUser() != null) {
        searchBody.put("user_id", LoginService.getLoginUser().getId());
      }
    } catch (JSONException e) {
      e.printStackTrace();
    } finally {
      this.keyword = keyword;
      this.division = division;
      this.loading = true;
      videoAdapter.showLoadMoreLoader();
      binding.placeholder.setVisibility(View.GONE);

      RequestBody searchRequestBody =
          RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
              (searchBody).toString());

      Call<ModelVideoWrapper> call =
          APIClient.getClient().create(HomeAPIInterface.class).search(searchRequestBody);

      call.enqueue(new Callback<ModelVideoWrapper>() {
        @Override public void onResponse(@NonNull Call<ModelVideoWrapper> call,
            @NonNull Response<ModelVideoWrapper> response) {
          ModelVideoWrapper videoWrapper = response.body();

          if (videoWrapper != null && videoWrapper.videoList != null) {
            for (int i = 0; i < videoWrapper.videoList.size(); i++) {
              videoAdapter.addVideo(videoWrapper.videoList.get(i));
            }

            binding.loader.setVisibility(View.GONE);
            binding.loader.cancelAnimation();

            if (videoAdapter.getItemCount() == 1) {
              binding.placeholder.setVisibility(View.VISIBLE);
              binding.searchResult.setText(
                  keyword.isEmpty() ? getString(R.string.video_search_no_data_empty)
                      : getString(R.string.video_search_no_data, keyword));
            }
          }

          videoAdapter.hideLoadMoreLoader();
        }

        @Override
        public void onFailure(@NonNull Call<ModelVideoWrapper> call, @NonNull Throwable t) {
          call.cancel();
          binding.loader.setVisibility(View.GONE);
          binding.loader.cancelAnimation();

          if (videoAdapter.getItemCount() == 1) {
            binding.placeholder.setVisibility(View.VISIBLE);
            binding.searchResult.setText(getString(R.string.error));
          }

          videoAdapter.hideLoadMoreLoader();
        }
      });
    }
  }
}
