package notq.dccast.screens.home.home_list;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Objects;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.HomeAPIInterface;
import notq.dccast.databinding.FragmentHomeListBinding;
import notq.dccast.model.ModelAdsWrapper;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelListHeaderWrapper;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.ModelVideoWrapper;
import notq.dccast.model.ads.ModelAds;
import notq.dccast.model.category.CategoryItem;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.ActivityHome;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.home.video_detail.FragmentVideo;
import notq.dccast.screens.home.view_holders.VHVideo;
import notq.dccast.util.Constants;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static android.view.View.OnClickListener;
import static com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import static com.google.android.material.tabs.TabLayout.Tab;

public class HomeListFragment extends Fragment implements OnTabSelectedListener {
  public AdapterVideos videoAdapter;
  private FragmentHomeListBinding binding;
  private HomeAPIInterface apiInterface;
  private boolean loading = true;
  private boolean pagingEnabled = false;
  private int pastVisiblesItems, visibleItemCount, totalItemCount, selectedTabPosition = 0,
      pageIndex = 1;

  private Call<ModelListHeaderWrapper> getHeaderListCall;

  private Call<ModelVideoWrapper> getVideoListCall;

  private int openVideoId = -1;

  private ModelAds currentAds;

  public static HomeListFragment newInstance(int openVideoId) {
    HomeListFragment myFragment = new HomeListFragment();

    Bundle args = new Bundle();
    args.putInt(Constants.OPEN_VIDEO_ID, openVideoId);
    myFragment.setArguments(args);

    return myFragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      openVideoId = getArguments().getInt(Constants.OPEN_VIDEO_ID, -1);
    }
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_list, container, false);
    initApi();
    init();

    if (openVideoId > 0) {
      getMediaById(openVideoId);
    }

    initAds();

    return binding.getRoot();
  }

  public void openVideo(ModelVideo video) {
    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
    ft.addToBackStack(null);
    ft.setCustomAnimations(R.anim.slide_in_up, android.R.anim.slide_out_right);

    FragmentVideo fragmentVideo = new FragmentVideo();
    Bundle bundle = new Bundle();
    bundle.putSerializable("data", video);
    bundle.putSerializable("header_data", null);
    fragmentVideo.setArguments(bundle);

    ft.replace(R.id.container_for_video, fragmentVideo).commit();
  }

  @Override public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    HomeListFragment homeListFragment = this;
    OnTabSelectedListener tabSelectedListener = this;
    OnClickListener clickListener = (OnClickListener) context;

    videoAdapter = new AdapterVideos(homeListFragment, tabSelectedListener, clickListener);
    videoAdapter.setViewType(VHVideo.TYPE_HOME);
  }

  private void initApi() {
    apiInterface = APIClient.getClient().create(HomeAPIInterface.class);
  }

  private void init() {
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
    gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
    gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int position) {
        switch (videoAdapter.getItemViewType(position)) {
          case AdapterVideos.VIEW_TYPE_MAIN_HEADER:
          case AdapterVideos.VIEW_TYPE_CATEGORY:
          case AdapterVideos.VIEW_TYPE_SUB_HEADER:
          case AdapterVideos.VIEW_TYPE_LOAD_MORE:
          case AdapterVideos.VIEW_TYPE_NO_DATA:
            return 2;

          case AdapterVideos.VIEW_TYPE_ITEM: {
            if (DCCastApplication.utils.VIDEO_CHOOSE_TYPE.equals("LIVE")) {
              return 2;
            } else {
              return 1;
            }
          }
        }

        return 0;
      }
    });

    binding.recyclerView.setLayoutManager(gridLayoutManager);
    binding.recyclerView.setNestedScrollingEnabled(false);
    binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) {
          visibleItemCount = gridLayoutManager.getChildCount();
          totalItemCount = gridLayoutManager.getItemCount();
          pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition();

          if (loading) {
            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
              if (!pagingEnabled) {
                return;
              }
              //if (videoAdapter.getVideoCount() >= 20) {
              //  return;
              //}
              loading = false;
              pageIndex = pageIndex + 1;
              getVideosRequest();
            }
          }
        }
      }
    });

    binding.refresher.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

    binding.refresher.setOnRefreshListener(() -> {
      binding.refresher.setRefreshing(true);
      prepareRecyclerViewForNewItems();
      videoAdapter.refreshCategoryVideo();
      getVideosRequest();
    });

    toolbarTabChanged();
  }

  public void enablePaging() {
    pagingEnabled = true;
  }

  public void disablePaging() {
    pagingEnabled = false;
  }

  private void prepareRecyclerViewForNewItems() {
    try {
      videoAdapter.removeVideoItems();
    } catch (IllegalStateException ex) {
      Timber.e("ERROR2: " + ex.getMessage());
    } catch (Exception ex) {
      Timber.e("ERROR2: " + ex.getMessage());
    }
    loading = true;
    pageIndex = 1;
  }

  public void selectFirstTab() {
    if (videoAdapter != null && videoAdapter.category != null) {
      videoAdapter.category.setCurrentPosition(0);
    }
  }

  /**
   * Category Tab Listener
   */
  @Override public void onTabSelected(Tab tab) {
    if (selectedTabPosition != tab.getPosition()) {
      prepareRecyclerViewForNewItems();
    }

    selectedTabPosition = tab.getPosition();
    if (tab.getPosition() == 0) {
      videoAdapter.showPopularVideos();
    } else {
      videoAdapter.hidePopularVideos();

      ((ActivityHome) getActivity()).changeToolbar(ActivityHome.FRAGMENT_TYPE_CATEGORY_DETAIL);
    }

    getVideosRequest();

    showAds();
  }

  @Override public void onTabUnselected(Tab tab) {

  }

  @Override public void onTabReselected(Tab tab) {

  }

  /**
   * Activity Home - Toolbar Tab select хийхэд дуудна
   */
  public void toolbarTabChanged() {
    requestVideoCalls();
    if (videoAdapter != null) {
      prepareRecyclerViewForNewItems();
      DCCastApplication.isLive = DCCastApplication.utils.VIDEO_CHOOSE_TYPE.equals("LIVE");
      videoAdapter.toggleLiveVod(DCCastApplication.utils.VIDEO_CHOOSE_TYPE.equals("LIVE"));
      videoAdapter.showLoadMoreLoader();
      getHeaderHitRequest();
    }

    if (DCCastApplication.adsList == null || DCCastApplication.adsList.isEmpty()) {
      getAds();
    } else {
      showAds();
    }
  }

  @Override public void onDestroy() {
    requestVideoCalls();
    super.onDestroy();
  }

  private void requestVideoCalls() {
    if (getHeaderListCall != null) {
      getHeaderListCall.cancel();
    }

    if (getVideoListCall != null) {
      getVideoListCall.cancel();
    }
  }

  private void hideAds() {
    currentAds = null;
    removeMarginContainer();

    binding.ads.layoutBanner.setVisibility(View.GONE);
  }

  private void showAds(ModelAds ads) {
    currentAds = ads;
    addMarginToContainer();
    binding.ads.layoutBanner.setVisibility(View.VISIBLE);

    if (ads.getAdverType().equalsIgnoreCase("SCRIPT")) {
      binding.ads.adsWebView.setVisibility(View.VISIBLE);
      binding.ads.adsWebView.loadData(ads.getContent(), "text/html", "UTF-8");

      binding.ads.adsWebView.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
          if (ads.getAffil() != null && ads.getAffil().getSite() != null) {
            Uri url = Uri.parse(ads.getAffil().getSite());
            Intent intent = new Intent(Intent.ACTION_VIEW, url);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
              startActivity(intent);
            }
          }
          return false;
        }
      });
    } else if (ads.getAdverType().equalsIgnoreCase("IMAGE") || ads.getAdverType()
        .equalsIgnoreCase("FILE")) {
      binding.ads.adsImage.setVisibility(View.VISIBLE);
      if (ads.getFile() != null) {
        if (getActivity() != null) {
          Glide.with(getActivity()).load(ads.getFile()).into(binding.ads.adsImage);
        }
      }
    }

    binding.ads.layoutBanner.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (ads.getAffil() != null && ads.getAffil().getSite() != null) {
          Uri url = Uri.parse(ads.getAffil().getSite());
          Intent intent = new Intent(Intent.ACTION_VIEW, url);
          if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
          }
        }
      }
    });
  }

  private void addMarginToContainer() {
    try {
      MotionLayout.LayoutParams params =
          (MotionLayout.LayoutParams) binding.containerForVideo.getLayoutParams();
      params.setMargins(0, 0, 0, (int) Util.convertDpToPixel(55, getActivity()));

      FrameLayout.LayoutParams refresherParams =
          (FrameLayout.LayoutParams) binding.refresher.getLayoutParams();
      refresherParams.setMargins(0, 0, 0, (int) Util.convertDpToPixel(55, getActivity()));
    } catch (Exception ex) {
      Timber.e("ERR:" + ex.getMessage());
    }
  }

  private void removeMarginContainer() {
    MotionLayout.LayoutParams params =
        (MotionLayout.LayoutParams) binding.containerForVideo.getLayoutParams();
    params.setMargins(0, 0, 0, 0);

    FrameLayout.LayoutParams refresherParams =
        (FrameLayout.LayoutParams) binding.refresher.getLayoutParams();
    refresherParams.setMargins(0, 0, 0, 0);
  }

  public void hideAdsView() {
    binding.ads.layoutBanner.setVisibility(View.GONE);

    removeMarginContainer();
  }

  public void showAdsView() {
    if (currentAds != null) {
      binding.ads.layoutBanner.setVisibility(View.VISIBLE);
      addMarginToContainer();
    }
  }

  public void initAds() {
    binding.ads.adsWebView.getSettings().setJavaScriptEnabled(true);
  }

  public void getAds() {
    Call<ModelAdsWrapper> call = apiInterface.getAds();

    call.enqueue(new Callback<ModelAdsWrapper>() {
      @Override public void onResponse(@NonNull Call<ModelAdsWrapper> call,
          @NonNull Response<ModelAdsWrapper> response) {
        ModelAdsWrapper headerWrapper = response.body();

        if (headerWrapper == null
            || headerWrapper.getAds() == null
            || headerWrapper.getAds().size() == 0) {
          return;
        }

        DCCastApplication.adsList = new ArrayList<>(headerWrapper.getAds());

        showAds();
      }

      @Override
      public void onFailure(@NonNull Call<ModelAdsWrapper> call, @NonNull Throwable t) {
        call.cancel();
      }
    });
  }

  private void showAds() {
    boolean isLive = DCCastApplication.utils.VIDEO_CHOOSE_TYPE.equalsIgnoreCase("LIVE");

    String locateId = isLive ? "Live_banner" : "VOD_banner";
    ModelAds ads = Util.getAds(locateId);
    if (selectedTabPosition > 0) {
      if (DCCastApplication.listCategoryItems != null
          && DCCastApplication.listCategoryItems.size() >= selectedTabPosition) {
        CategoryItem selectedCategory =
            DCCastApplication.listCategoryItems.get(selectedTabPosition);
        ads = Util.getAds(locateId, selectedCategory.getId());
      }
    }

    if (ads != null) {
      showAds(ads);
      return;
    }

    hideAds();
  }

  public void getMediaById(int openVideoId) {
    showLoader();
    Call<ModelVideoWrapper> call = apiInterface.getMediaById(openVideoId);
    call.enqueue(new Callback<ModelVideoWrapper>() {
      @Override public void onResponse(@NonNull Call<ModelVideoWrapper> call,
          @NonNull Response<ModelVideoWrapper> response) {
        ModelVideoWrapper headerWrapper = response.body();

        hideLoader();

        if (headerWrapper == null
            || headerWrapper.getVideoList() == null
            || headerWrapper.getVideoList().size() == 0) {
          Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
          return;
        }

        ModelVideo openVideo = headerWrapper.getVideoList().get(0);

        if (openVideo != null) {
          openVideo(openVideo);
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelVideoWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideLoader();
        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
      }
    });
  }

  private void getHeaderHitRequest() {
    boolean isLive = DCCastApplication.utils.VIDEO_CHOOSE_TYPE.equalsIgnoreCase("LIVE");

    showLoader();

    if (getHeaderListCall != null) {
      getHeaderListCall.cancel();
    }

    getHeaderListCall =
        apiInterface.getHeaderVodList();
    if (isLive) {
      getHeaderListCall = apiInterface.getHeaderLiveList();
    }

    getHeaderListCall.enqueue(new Callback<ModelListHeaderWrapper>() {
      @Override public void onResponse(@NonNull Call<ModelListHeaderWrapper> call,
          @NonNull Response<ModelListHeaderWrapper> response) {
        ModelListHeaderWrapper headerWrapper = response.body();

        binding.refresher.setRefreshing(false);

        try {
          if (headerWrapper == null
              || headerWrapper.getHeaderList() == null
              || headerWrapper.getHeaderList().size() == 0) {
            getHeaderRequest();
            return;
          }

          if (headerWrapper != null) {
            videoAdapter.resetHeaderDatas();
            videoAdapter.setHeaderDatas(Objects.requireNonNull(headerWrapper).headerList);
          }

          if (videoAdapter.categories.isEmpty()) {
            binding.recyclerView.setAdapter(videoAdapter);
            videoAdapter.setCategoryDatas(DCCastApplication.listCategoryItems);
          } else {
            getVideosRequest();
          }
        } catch (IllegalStateException ex) {
          Timber.e("ERROR HERE ill: " + ex.getMessage());
        } catch (Exception ex) {
          Timber.e("ERROR HERE: " + ex.getMessage());
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelListHeaderWrapper> call, @NonNull Throwable t) {
        call.cancel();

        binding.refresher.setRefreshing(false);
        hideAllLoaders();
      }
    });
  }

  private void getHeaderRequest() {
    getHeaderListCall =
        apiInterface.getHeaderList("-views", true, DCCastApplication.utils.VIDEO_CHOOSE_TYPE, 5);

    if (LoginService.getLoginUser() != null) {
      ModelUser loginUser = LoginService.getLoginUser();
      getHeaderListCall =
          apiInterface.getHeaderListWithUserId("-views", true,
              DCCastApplication.utils.VIDEO_CHOOSE_TYPE, 5, loginUser.getId());
    }

    getHeaderListCall.enqueue(new Callback<ModelListHeaderWrapper>() {
      @Override public void onResponse(@NonNull Call<ModelListHeaderWrapper> call,
          @NonNull Response<ModelListHeaderWrapper> response) {
        ModelListHeaderWrapper headerWrapper = response.body();

        try {
          videoAdapter.resetHeaderDatas();
          if (headerWrapper != null) {
            videoAdapter.setHeaderDatas(Objects.requireNonNull(headerWrapper).headerList);
          }

          if (videoAdapter.categories.isEmpty()) {
            binding.recyclerView.setAdapter(videoAdapter);
            videoAdapter.setCategoryDatas(DCCastApplication.listCategoryItems);
          } else {
            getVideosRequest();
          }
        } catch (IllegalStateException ex) {
          Timber.e("ERROR HERE ill: " + ex.getMessage());
        } catch (Exception ex) {
          Timber.e("NO ERROR HERE: " + ex.getMessage());
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelListHeaderWrapper> call, @NonNull Throwable t) {
        call.cancel();

        Timber.e("ANOTHER FAILURE");

        getVideosRequest();
      }
    });
  }

  public void updateVideo(ModelVideo modelVideo) {
    if (videoAdapter != null) {
      videoAdapter.updateVideo(modelVideo);
    }
  }

  public void updateViewCount(ModelVideo modelVideo) {
    if (videoAdapter != null) {
      videoAdapter.updateViewCount(modelVideo);
    }
  }

  public void updateViewCount(ModelListHeader modelVideo) {
    if (videoAdapter != null) {
      videoAdapter.updateViewCount(modelVideo);
    }
  }

  private void getVideosRequest() {
    videoAdapter.showLoadMoreLoader();
    CategoryItem selectedCategory = DCCastApplication.listCategoryItems.get(selectedTabPosition);

    ModelUser loginUser = LoginService.getLoginUser();
    String ordering = "-views";
    if (DCCastApplication.utils.VIDEO_CHOOSE_TYPE.equalsIgnoreCase("LIVE")) {
      ordering = "-created";
    }
    if (loginUser != null) {
      if (selectedCategory.getId() == -1) {
        getVideoListCall =
            apiInterface.getAllVideoListWithUserId(loginUser.getId(),
                DCCastApplication.utils.VIDEO_CHOOSE_TYPE, ordering, 20,
                pageIndex, 0, 0);
      } else {
        getVideoListCall =
            apiInterface.getVideoListWithUserId(loginUser.getId(),
                DCCastApplication.utils.VIDEO_CHOOSE_TYPE, selectedCategory.id,
                ordering, 20, pageIndex, 0, 0);
      }
    } else {
      if (selectedCategory.getId() == -1) {
        getVideoListCall =
            apiInterface.getAllVideoList(DCCastApplication.utils.VIDEO_CHOOSE_TYPE, ordering, 20,
                pageIndex, 0, 0);
      } else {
        getVideoListCall =
            apiInterface.getVideoList(DCCastApplication.utils.VIDEO_CHOOSE_TYPE,
                selectedCategory.id,
                ordering, 20, pageIndex, 0, 0);
      }
    }

    getVideoListCall.enqueue(new Callback<ModelVideoWrapper>() {
      @Override public void onResponse(@NonNull Call<ModelVideoWrapper> call,
          @NonNull Response<ModelVideoWrapper> response) {
        ModelVideoWrapper videoWrapper = response.body();

        try {
          if (videoWrapper != null && videoWrapper.videoList != null) {
            for (int i = 0; i < videoWrapper.videoList.size(); i++) {
              videoAdapter.addVideo(videoWrapper.videoList.get(i));
            }
          }
        } catch (IllegalStateException ex) {
          Timber.e("ERROR HERE ill: " + ex.getMessage());
        } catch (Exception ex) {
          Timber.e("NO NO NO ERROR: " + ex.getMessage());
        }

        hideAllLoaders();
      }

      @Override public void onFailure(@NonNull Call<ModelVideoWrapper> call, @NonNull Throwable t) {
        hideAllLoaders();
      }
    });
  }

  private void showLoader() {
    if (!binding.dcLoader.isShown() || !binding.dcLoader.isAnimating()) {
      binding.dcLoader.setVisibility(View.VISIBLE);
      binding.dcLoader.playAnimation();
    }
  }

  private void hideLoader() {
    if (binding.dcLoader.isShown() && binding.dcLoader.isAnimating()) {
      binding.dcLoader.setVisibility(View.GONE);
      binding.dcLoader.cancelAnimation();
    }
  }

  private void hideLoaders() {
    hideLoader();

    if (binding.refresher.isRefreshing()) {
      binding.refresher.setRefreshing(false);
    }

    videoAdapter.hideLoadMoreLoader();
    videoAdapter.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);
  }

  private void hideAllLoaders() {
    new CountDownTimer(1500, 1500) {
      @Override public void onTick(long l) {

      }

      @Override public void onFinish() {
        if (binding.dcLoader.isShown() && binding.dcLoader.isAnimating()) {
          binding.dcLoader.setVisibility(View.GONE);
          binding.dcLoader.cancelAnimation();
        }
      }
    }.start();

    hideLoaders();
  }
}
