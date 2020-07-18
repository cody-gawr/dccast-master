package notq.dccast.screens.navigation_menu.cast.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.util.ArrayList;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.databinding.FragmentFollowersBinding;
import notq.dccast.model.user.ModelResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.follower.ModelFollowResult;
import notq.dccast.model.user.follower.ModelFollower;
import notq.dccast.model.user.follower.ModelFollowerWrapper;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.navigation_menu.cast.adapter.AdapterFollowers;
import notq.dccast.screens.navigation_menu.cast.adapter.AdapterRecentFollowers;
import notq.dccast.screens.navigation_menu.cast.interfaces.FollowersListener;
import notq.dccast.screens.navigation_menu.my_live_history.ActivityMyLiveHistory;
import notq.dccast.util.Constants;
import notq.dccast.util.DialogHelper;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class FollowersFragment extends OpenVideoFragment {

  private final int ADD_FOLLOW_REQUEST = 3012;
  private FragmentFollowersBinding binding;
  private AdapterFollowers adapterFollowers;
  private AdapterRecentFollowers adapterRecent;

  private int FOLLOWERS = 1203, MY_LIVE_HISTORY = 1204;

  private int pageIndex = 1, pageIndexMyLive = 1;
  private boolean isLoading = false, isLoadingMyLive = false, hasNextPage = false,
      hasNextPageMyLive = false;

  private ArrayList<ModelUser> followers;
  private ArrayList<ModelUser> myLiveHistory;

  private boolean viewInitialized = false;
  private boolean isVisible = false;

  private SwipeMenuCreator mSwipeMenuCreator = (swipeLeftMenu, swipeRightMenu, viewType) -> {
    int width = getResources().getDimensionPixelSize(R.dimen.swipe_menu_width);
    int height = ViewGroup.LayoutParams.MATCH_PARENT;

    SwipeMenuItem deleteItem = new SwipeMenuItem(getContext()).setBackgroundColor(
        ContextCompat.getColor(getActivity(), R.color.swipe_red_background))
        .setText(getString(R.string.delete))
        .setTextSize(12)
        .setTextColor(ContextCompat.getColor(getActivity(), R.color.white))
        .setWidth(width)
        .setHeight(height);
    swipeRightMenu.addMenuItem(deleteItem);
  };

  private SwipeMenuItemClickListener mMenuItemClickListener = (menuBridge, position) -> {
    menuBridge.closeMenu();

    int direction = menuBridge.getDirection();

    if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
      ModelUser user = adapterFollowers.getItem(position);

      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser == null || user.getNickName() == null) {
        return;
      }

      DialogHelper.showAlertDialog(getActivity(), "",
          getString(R.string.delete_follower_confirm, user.getNickName()),
          getString(R.string.confirm_yes),
          new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
              deleteFollower(loginUser.getId(), user.getId(), position);
            }
          }, getString(R.string.confirm_no), null);
    }
  };

  public static FollowersFragment newInstance() {
    return new FollowersFragment();
  }

  private void deleteFollower(int fromUser, int toUser, int position) {
    progressDialog.show();

    CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);
    Call<ModelFollowResult> call = apiInterface.deleteFollower(fromUser, toUser);
    call.enqueue(new Callback<ModelFollowResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelFollowResult> call,
          @NonNull Response<ModelFollowResult> response) {
        progressDialog.dismiss();

        ModelFollowResult result = response.body();
        if (result == null || !result.success) {
          return;
        }

        ModelUser user = adapterFollowers.getItem(position);
        user.setFollowing(false);
        adapterFollowers.removeFollower(position);

        initListCount();
      }

      @Override
      public void onFailure(@NonNull Call<ModelFollowResult> call, @NonNull Throwable t) {
        call.cancel();

        progressDialog.dismiss();
      }
    });
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    this.isVisible = isVisibleToUser;

    if (isVisibleToUser && viewInitialized) {
      refreshData();
    }
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_followers, container, false);

    initRecyclerView();
    init();

    if (isVisible) {
      refreshData();
    }

    viewInitialized = true;

    return binding.getRoot();
  }

  private void init() {
    progressDialog = new ProgressDialog(getActivity());
    progressDialog.setCancelable(false);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case ADD_FOLLOW_REQUEST: {
          break;
        }
      }
    }
  }

  private void followUser(boolean fromRecent, int position, int userId, int followerId) {
    progressDialog.show();

    CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);
    Call<ModelFollowResult> call = apiInterface.follow(userId, followerId);
    call.enqueue(new Callback<ModelFollowResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelFollowResult> call,
          @NonNull Response<ModelFollowResult> response) {
        progressDialog.dismiss();

        ModelFollowResult result = response.body();
        if (result == null || (!result.isFollowing() && !result.isSuccess())) {
          return;
        }

        if (getActivity() != null) {
          getActivity().runOnUiThread(new Runnable() {
            @Override public void run() {
              if (fromRecent) {
                adapterRecent.getItem(position).setFollowing(true);
                adapterRecent.notifyItemChanged(position);
              } else {
                ModelUser user = adapterFollowers.getItem(position);
                user.setFollowing(true);
                adapterRecent.addRecent(user);

                adapterFollowers.removeFollower(position);
              }
            }
          });
        }

        initListCount();
      }

      @Override
      public void onFailure(@NonNull Call<ModelFollowResult> call, @NonNull Throwable t) {
        call.cancel();

        progressDialog.dismiss();
      }
    });
  }

  private void unFollowUser(boolean fromRecent, int position, int userId, int followerId) {
    progressDialog.show();

    CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);
    Call<ModelResult> call = apiInterface.unFollow(userId, followerId);
    call.enqueue(new Callback<ModelResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelResult> call,
          @NonNull Response<ModelResult> response) {
        progressDialog.dismiss();

        ModelResult result = response.body();
        if (result == null || !result.isSuccess()) {
          return;
        }

        if (fromRecent) {
          ModelUser user = adapterRecent.getItem(position);
          user.setFollowing(false);
          adapterFollowers.addFollower(user);

          adapterRecent.removeRecent(position);
        } else {
          adapterFollowers.getItem(position).setFollowing(false);
          adapterFollowers.notifyItemChanged(position);
        }

        initListCount();
      }

      @Override
      public void onFailure(@NonNull Call<ModelResult> call, @NonNull Throwable t) {
        call.cancel();

        progressDialog.dismiss();
      }
    });
  }

  private void initRecyclerView() {
    adapterFollowers = new AdapterFollowers(getActivity(), false, new FollowersListener() {
      @Override
      public void onItemSelected(int position) {
        ModelUser user = adapterFollowers.getItem(position);
        if (user == null) {
          return;
        }
        if (user.isOnAir()) {
          getLastLiveVideo(R.id.container_for_cast_list_video, user.getId());
          //checkIsAdult(R.id.container_for_cast_list_video, user.getOnAirMedia());
        }
      }

      @Override
      public void onFollow(int position) {
        ModelUser user = adapterFollowers.getItem(position);

        ModelUser loginUser = LoginService.getLoginUser();
        if (loginUser == null) {
          return;
        }
        if (user.isFollowing()) {
          unFollowUser(false, position, user.getId(), loginUser.getId());
        } else {
          followUser(false, position, user.getId(), loginUser.getId());
        }
      }
    });
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.setNestedScrollingEnabled(false);
    binding.recyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
    binding.recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
    binding.recyclerView.setAdapter(adapterFollowers);

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
              getFollowersList();
            }
          }
        }
      }
    });

    binding.refresher.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
    binding.refresher.setOnRefreshListener(() -> {
      binding.refresher.setRefreshing(false);
      refreshData();
    });

    adapterRecent = new AdapterRecentFollowers(getActivity(), false, new FollowersListener() {
      @Override
      public void onItemSelected(int position) {
        ModelUser user = adapterRecent.getItem(position);

        Intent channelIntent = new Intent(getActivity(), ActivityMyLiveHistory.class);
        channelIntent.putExtra(Constants.USER_ID, user.getId());
        startActivity(channelIntent);
      }

      @Override
      public void onFollow(int position) {
        ModelUser user = adapterRecent.getItem(position);

        ModelUser loginUser = LoginService.getLoginUser();
        if (loginUser == null) {
          return;
        }
        if (user.isFollowing()) {
          unFollowUser(true, position, user.getId(), loginUser.getId());
        } else {
          followUser(true, position, user.getId(), loginUser.getId());
        }
      }
    });
    LinearLayoutManager recentLayoutManager = new LinearLayoutManager(getActivity());
    binding.recyclerViewMyLive.setLayoutManager(recentLayoutManager);
    binding.recyclerViewMyLive.setNestedScrollingEnabled(false);
    binding.recyclerViewMyLive.setAdapter(adapterRecent);

    binding.recyclerViewMyLive.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) {
          int visibleItemCount = recentLayoutManager.getChildCount();
          int totalItemCount = recentLayoutManager.getItemCount();
          int pastVisiblesItems = recentLayoutManager.findFirstVisibleItemPosition();

          if (!isLoadingMyLive && hasNextPageMyLive) {
            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
              isLoadingMyLive = true;
              pageIndexMyLive = pageIndexMyLive + 1;
              getMyLiveHistory();
            }
          }
        }
      }
    });
  }

  private void showNoDataView() {
    binding.layoutNoData.setVisibility(View.VISIBLE);
  }

  private void hideNoDataView() {
    binding.layoutNoData.setVisibility(View.GONE);
  }

  private void prepareRecyclerViewForNewItems() {
    adapterFollowers.removeFollowers();
    adapterRecent.removeRecent();

    isLoading = false;
    pageIndex = 1;

    isLoadingMyLive = false;
    pageIndexMyLive = 1;
  }

  private void refreshData() {
    showLoader();
    prepareRecyclerViewForNewItems();

    getFollowersList();
    getMyLiveHistory();
  }

  private void getFollowersList() {
    followers = new ArrayList<>();

    binding.lblFollowerCount.setText(getString(R.string.followers_count, "0"));

    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    adapterFollowers.showLoadMoreLoader();

    isLoading = true;
    hasNextPage = false;

    CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);

    Call<ModelFollowerWrapper> call = apiInterface.getFollowers(loginUser.getId(), pageIndex);

    call.enqueue(new Callback<ModelFollowerWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelFollowerWrapper> call,
          @NonNull Response<ModelFollowerWrapper> response) {
        ModelFollowerWrapper userWrapper = response.body();

        if (userWrapper != null && userWrapper.followers != null) {
          hasNextPage = userWrapper.next != null && !userWrapper.next.isEmpty();
          for (int i = 0; i < userWrapper.followers.size(); i++) {
            ModelFollower profile = userWrapper.followers.get(i);
            if (profile == null || profile.getFollower() == null) {
              continue;
            }

            if (profile.getFollower().getId() == (loginUser.getId())) {
              continue;
            }

            followers.add(profile.getFollower());
          }
        }

        for (ModelUser follower : followers) {
          adapterFollowers.addFollower(follower);
        }

        hideAllLoaders(FOLLOWERS);
      }

      @Override
      public void onFailure(@NonNull Call<ModelFollowerWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders(FOLLOWERS);
      }
    });
  }

  private void getMyLiveHistory() {
    myLiveHistory = new ArrayList<>();

    binding.lblMyLiveHistory.setText(getString(R.string.my_live_history, "0"));

    adapterRecent.hideLoadMoreLoader();
    adapterRecent.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);

    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    isLoadingMyLive = true;
    hasNextPageMyLive = false;

    CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);

    Call<ModelFollowerWrapper> call =
        apiInterface.getMyLiveHistory(loginUser.getId(), pageIndexMyLive);

    call.enqueue(new Callback<ModelFollowerWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelFollowerWrapper> call,
          @NonNull Response<ModelFollowerWrapper> response) {
        ModelFollowerWrapper userWrapper = response.body();

        if (userWrapper != null && userWrapper.followers != null) {
          hasNextPageMyLive = userWrapper.next != null && !userWrapper.next.isEmpty();
          for (int i = 0; i < userWrapper.followers.size(); i++) {
            ModelFollower profile = userWrapper.followers.get(i);
            if (profile == null || profile.getFollower() == null) {
              continue;
            }

            if (profile.getFollower().getId() == (loginUser.getId())) {
              continue;
            }

            myLiveHistory.add(profile.getFollower());
          }
        }

        for (ModelUser follower : myLiveHistory) {
          adapterRecent.addRecent(follower);
        }

        hideAllLoaders(MY_LIVE_HISTORY);
      }

      @Override
      public void onFailure(@NonNull Call<ModelFollowerWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders(MY_LIVE_HISTORY);
      }
    });
  }

  private void showLoader() {
    if (!binding.dcLoader.isAnimating() && !binding.dcLoader.isShown()) {
      binding.dcLoader.playAnimation();
      binding.dcLoader.setVisibility(View.VISIBLE);
    }
  }

  private void hideLoaders(int type) {
    if (binding.refresher.isRefreshing()) {
      binding.refresher.setRefreshing(false);
    }

    if (type == FOLLOWERS) {
      isLoading = false;

      adapterFollowers.hideLoadMoreLoader();
      adapterFollowers.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);
    } else {

      isLoadingMyLive = false;
      adapterRecent.hideLoadMoreLoader();
      adapterRecent.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);
    }

    if (!isLoading && !isLoadingMyLive) {
      initListCount();

      if (binding.dcLoader.isShown() && binding.dcLoader.isAnimating()) {
        binding.dcLoader.setVisibility(View.GONE);
        binding.dcLoader.cancelAnimation();
      }
    }
  }

  private void initListCount() {
    binding.layoutMyLiveHistory.setVisibility(
        adapterRecent.getRecentCount() == 0 ? View.GONE : View.VISIBLE);

    String recentCount = Util.getFormattedNumber(adapterRecent.getRecentCount());
    binding.lblMyLiveHistory.setText(getString(R.string.my_live_history, recentCount));

    String formattedCount = Util.getFormattedNumber(adapterFollowers.getFollowersCount());
    binding.lblFollowerCount.setText(getString(R.string.followers_count, formattedCount));

    binding.layoutItem.setVisibility(
        adapterFollowers.getFollowersCount() == 0 ? View.GONE : View.VISIBLE);

    if (adapterRecent.getRecentCount() + adapterFollowers.getFollowersCount() == 0) {
      showNoDataView();
    } else {
      hideNoDataView();
    }
  }

  private void hideAllLoaders(int type) {
    new CountDownTimer(1500, 1500) {
      @Override
      public void onTick(long l) {

      }

      @Override
      public void onFinish() {
        if (binding.dcLoader.isShown() && binding.dcLoader.isAnimating()) {
          binding.dcLoader.cancelAnimation();
        }

        binding.dcLoader.setVisibility(View.GONE);
      }
    }.start();

    hideLoaders(type);
  }

  @Override
  protected void hideKeyboard() {
    InputMethodManager imm =
        (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
    Objects.requireNonNull(imm).hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
  }
}
