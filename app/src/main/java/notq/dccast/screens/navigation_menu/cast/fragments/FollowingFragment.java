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
import java.util.HashMap;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.databinding.FragmentFollowingBinding;
import notq.dccast.model.user.ModelResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.follower.ModelFollowResult;
import notq.dccast.model.user.follower.ModelFollower;
import notq.dccast.model.user.follower.ModelFollowerWrapper;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.navigation_menu.cast.adapter.AdapterFollowers;
import notq.dccast.screens.navigation_menu.cast.adapter.AdapterRecentFollowers;
import notq.dccast.screens.navigation_menu.cast.interfaces.FollowersListener;
import notq.dccast.screens.navigation_menu.cast.user_follow.ActivityUserFollow;
import notq.dccast.util.DialogHelper;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class FollowingFragment extends OpenVideoFragment {

  private final int ADD_FOLLOW_REQUEST = 3012;
  private final int RECENT = 1;
  private final int FOLLOW = 2;
  private FragmentFollowingBinding binding;
  private AdapterFollowers adapterUnFollowing;
  private AdapterRecentFollowers adapterFollowing;
  private ProgressDialog progressDialog;

  private int unFollowingPageIndex = 1, followingPageIndex = 1;
  private boolean isLoading = false, isLoadingFollowing = false, hasNextPage = false,
      hasFollowingNextPage = false;

  private ArrayList<ModelUser> recentFollowers;
  private ArrayList<ModelUser> followers;
  private boolean firstTime = true;

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
      ModelUser user = adapterUnFollowing.getItem(position);

      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser == null || user.getFollowingId() == 0) {
        return;
      }

      DialogHelper.showAlertDialog(getActivity(),
          "", getString(R.string.delete_unfollowing_confirm, user.getNickName()),
          getString(R.string.confirm_yes),
          new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
              deleteUnfollow(user.getFollowingId(), position);
            }
          }, getString(R.string.confirm_no), null);
    }
  };

  public static FollowingFragment newInstance() {
    return new FollowingFragment();
  }

  private void deleteUnfollow(int followingId, int position) {
    progressDialog.show();

    CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);
    Call<ModelFollowResult> call = apiInterface.deleteUnfollow(followingId);
    call.enqueue(new Callback<ModelFollowResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelFollowResult> call,
          @NonNull Response<ModelFollowResult> response) {
        progressDialog.dismiss();

        ModelUser user = adapterUnFollowing.getItem(position);
        user.setFollowing(false);
        adapterUnFollowing.removeFollower(position);

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
    if (isVisibleToUser) {
      refreshData();

      firstTime = false;
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
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_following, container, false);

    initRecyclerView();
    initButtons();
    init();

    return binding.getRoot();
  }

  private void init() {
    progressDialog = new ProgressDialog(getActivity());
    progressDialog.setCancelable(false);
  }

  private void initButtons() {
    binding.layoutFab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent addFollowIntent = new Intent(getActivity(), ActivityUserFollow.class);
        startActivityForResult(addFollowIntent, ADD_FOLLOW_REQUEST);
      }
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case ADD_FOLLOW_REQUEST: {
          refreshData();
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

        if (fromRecent) {
          adapterFollowing.getItem(position).setFollowing(true);
          adapterFollowing.notifyItemChanged(position);
        } else {
          ModelUser user = adapterUnFollowing.getItem(position);
          user.setFollowing(true);
          adapterFollowing.addRecent(user);

          adapterUnFollowing.removeFollower(position);
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
          ModelUser user = adapterFollowing.getItem(position);
          user.setFollowing(false);
          adapterUnFollowing.addFollower(user);

          adapterFollowing.removeRecent(position);
        } else {
          adapterUnFollowing.getItem(position).setFollowing(false);
          adapterUnFollowing.notifyItemChanged(position);
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
    adapterUnFollowing = new AdapterFollowers(getActivity(), true, new FollowersListener() {
      @Override
      public void onItemSelected(int position) {
        ModelUser user = adapterUnFollowing.getItem(position);
        if (user.isOnAir()) {
          getLastLiveVideo(R.id.container_for_following_video, user.getId());
        }
      }

      @Override
      public void onFollow(int position) {
        ModelUser user = adapterUnFollowing.getItem(position);

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
    binding.recyclerView.setAdapter(adapterUnFollowing);

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
              unFollowingPageIndex = unFollowingPageIndex + 1;
              getUnFollowingList();
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

    adapterFollowing = new AdapterRecentFollowers(getActivity(), true, new FollowersListener() {
      @Override
      public void onItemSelected(int position) {
        ModelUser user = adapterFollowing.getItem(position);
        if (user == null) {
          return;
        }
        if (user.isOnAir()) {
          getLastLiveVideo(R.id.container_for_following_video, user.getId());
          //checkIsAdult(R.id.container_for_cast_list_video, user.getOnAirMedia());
        }
      }

      @Override
      public void onFollow(int position) {
        ModelUser user = adapterFollowing.getItem(position);

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
    binding.recyclerViewRecent.setLayoutManager(recentLayoutManager);
    binding.recyclerViewRecent.setNestedScrollingEnabled(false);
    binding.recyclerViewRecent.setAdapter(adapterFollowing);

    binding.recyclerViewRecent.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) {
          int visibleItemCount = recentLayoutManager.getChildCount();
          int totalItemCount = recentLayoutManager.getItemCount();
          int pastVisiblesItems = recentLayoutManager.findFirstVisibleItemPosition();

          if (!isLoadingFollowing && hasFollowingNextPage) {
            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
              isLoadingFollowing = true;
              followingPageIndex = followingPageIndex + 1;
              getFollowingList();
            }
          }
        }
      }
    });

    binding.lblFollowingCount.setText(getString(R.string.following_count, "0"));
    binding.lblUnfollowingCount.setText(getString(R.string.unfollowing_count, "0"));
  }

  private void showNoDataView() {
    binding.layoutNoData.setVisibility(View.VISIBLE);

    if (!binding.dcNoData.isAnimating() && !binding.dcNoData.isShown()) {
      binding.dcNoData.playAnimation();
      binding.dcNoData.setVisibility(View.VISIBLE);
    }
  }

  private void hideNoDataView() {
    binding.layoutNoData.setVisibility(View.GONE);

    if (binding.dcNoData.isShown() && binding.dcNoData.isAnimating()) {
      binding.dcNoData.setVisibility(View.GONE);
      binding.dcNoData.cancelAnimation();
    }
  }

  private void prepareRecyclerViewForNewItems() {
    adapterUnFollowing.removeFollowers();
    adapterFollowing.removeRecent();

    isLoading = false;
    isLoadingFollowing = false;
    unFollowingPageIndex = 1;
    followingPageIndex = 1;
  }

  private void refreshData() {
    showLoader();
    prepareRecyclerViewForNewItems();

    getFollowingList();
    getUnFollowingList();
  }

  private void getFollowingList() {
    recentFollowers = new ArrayList<>();

    binding.lblFollowingCount.setText(getString(R.string.following_count, "0"));
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    adapterFollowing.showLoadMoreLoader();

    isLoadingFollowing = true;
    hasFollowingNextPage = false;

    CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);

    Call<ModelFollowerWrapper> call =
        apiInterface.getFollowing(loginUser.getId(), followingPageIndex);
    call.enqueue(new Callback<ModelFollowerWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelFollowerWrapper> call,
          @NonNull Response<ModelFollowerWrapper> response) {
        ModelFollowerWrapper userWrapper = response.body();

        if (userWrapper != null && userWrapper.followers != null) {
          hasFollowingNextPage = userWrapper.next != null && !userWrapper.next.isEmpty();
          for (int i = 0; i < userWrapper.followers.size(); i++) {
            ModelFollower profile = userWrapper.followers.get(i);
            if (profile == null || profile.getUser() == null) {
              continue;
            }
            ModelUser user = profile.getUser();
            user.setFollowingId(profile.getId());
            recentFollowers.add(profile.getUser());
          }
        }

        hideAllLoaders(RECENT);
      }

      @Override
      public void onFailure(@NonNull Call<ModelFollowerWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders(RECENT);
      }
    });
  }

  private void getUnFollowingList() {
    followers = new ArrayList<>();

    binding.lblUnfollowingCount.setText(getString(R.string.unfollowing_count, "0"));
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    adapterUnFollowing.showLoadMoreLoader();

    isLoading = true;
    hasNextPage = false;

    CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);

    Call<ModelFollowerWrapper> call =
        apiInterface.getUnfollowing(loginUser.getId(), unFollowingPageIndex);

    call.enqueue(new Callback<ModelFollowerWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelFollowerWrapper> call,
          @NonNull Response<ModelFollowerWrapper> response) {
        ModelFollowerWrapper userWrapper = response.body();

        if (userWrapper != null && userWrapper.followers != null) {
          hasNextPage = userWrapper.next != null && !userWrapper.next.isEmpty();
          for (int i = 0; i < userWrapper.followers.size(); i++) {
            ModelFollower follower = userWrapper.followers.get(i);
            if (follower == null || follower.getUser() == null) {
              continue;
            }
            ModelUser user = follower.getUser();
            user.setFollowingId(follower.getId());
            followers.add(user);
          }
        }

        hideAllLoaders(FOLLOW);
      }

      @Override
      public void onFailure(@NonNull Call<ModelFollowerWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders(FOLLOW);
      }
    });
  }

  private void showLoader() {
    if (!binding.dcLoader.isAnimating() && !binding.dcLoader.isShown()) {
      binding.dcLoader.playAnimation();
      binding.dcLoader.setVisibility(View.VISIBLE);
    }
  }

  private void hideLoaders(int listType) {
    if (binding.dcLoader.isShown() && binding.dcLoader.isAnimating()) {
      binding.dcLoader.setVisibility(View.GONE);
      binding.dcLoader.cancelAnimation();
    }

    if (binding.refresher.isRefreshing()) {
      binding.refresher.setRefreshing(false);
    }

    if (listType == FOLLOW) {
      isLoading = false;
    } else {
      isLoadingFollowing = false;
    }

    if (!isLoading && !isLoadingFollowing) {
      HashMap<Integer, ModelUser> recentFollowersMap = new HashMap<>();
      for (ModelUser recentFollower : recentFollowers) {
        adapterFollowing.addRecent(recentFollower);
        recentFollowersMap.put(recentFollower.getId(), recentFollower);
      }

      for (ModelUser follower : followers) {
        if (!recentFollowersMap.containsKey(follower.getId())) {
          adapterUnFollowing.addFollower(follower);
        }
      }

      initListCount();
    }

    if (listType == FOLLOW) {
      adapterUnFollowing.hideLoadMoreLoader();
      adapterUnFollowing.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);
    } else {
      adapterFollowing.hideLoadMoreLoader();
      adapterFollowing.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);
    }
  }

  private void initListCount() {
    String followingCount = Util.getFormattedNumber(adapterFollowing.getRecentCount());
    binding.lblFollowingCount.setText(getString(R.string.following_count, followingCount));

    binding.layoutRecent.setVisibility(
        adapterFollowing.getRecentCount() == 0 ? View.GONE : View.VISIBLE);

    String unFollowingCount = Util.getFormattedNumber(adapterUnFollowing.getFollowersCount());
    binding.lblUnfollowingCount.setText(getString(R.string.unfollowing_count, unFollowingCount));

    binding.layoutItem.setVisibility(
        adapterUnFollowing.getFollowersCount() == 0 ? View.GONE : View.VISIBLE);

    if (adapterFollowing.getRecentCount() + adapterUnFollowing.getFollowersCount() == 0) {
      showNoDataView();
    } else {
      hideNoDataView();
    }
  }

  private void hideAllLoaders(int listType) {
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

    hideLoaders(listType);
  }

  @Override
  protected void hideKeyboard() {
    InputMethodManager imm =
        (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
    Objects.requireNonNull(imm).hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
  }
}
