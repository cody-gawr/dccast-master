package notq.dccast.screens.navigation_menu.cast.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.databinding.FragmentFriendsBinding;
import notq.dccast.model.friends.ModelFriendRequest;
import notq.dccast.model.friends.ModelFriendRequestResult;
import notq.dccast.model.friends.ModelFriendRequestWrapper;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.navigation_menu.cast.FragmentCast;
import notq.dccast.screens.navigation_menu.cast.adapter.AdapterFriends;
import notq.dccast.screens.navigation_menu.cast.adapter.AdapterRecentFriends;
import notq.dccast.screens.navigation_menu.cast.interfaces.FollowersListener;
import notq.dccast.screens.navigation_menu.cast.interfaces.FriendsListener;
import notq.dccast.screens.navigation_menu.cast.search_friend.ActivitySearchFriend;
import notq.dccast.screens.navigation_menu.content.my_channel.ActivityMyChannel;
import notq.dccast.util.Constants;
import notq.dccast.util.DialogHelper;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsFragment extends Fragment {

  private final int SEARCH_FRIEND = 9231;
  private FragmentFriendsBinding binding;
  private AdapterFriends adapterFriends;
  private AdapterRecentFriends adapterRecent;
  private ProgressDialog progressDialog;
  private int pageIndex = 1;
  private boolean isLoading = false, hasNextPage = false;

  private ArrayList<ModelFriendRequest> friends = new ArrayList<>();
  private ArrayList<ModelFriendRequest> recentFriends = new ArrayList<>();

  private FragmentCast rootFragment;

  private long ONE_DAY = 24 * 60 * 60 * 1000;
  @SuppressLint("SimpleDateFormat")
  private SimpleDateFormat fromServer =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
  @SuppressLint("SimpleDateFormat")
  private SimpleDateFormat simpleDateFormat =
      new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

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
      ModelFriendRequest user = adapterFriends.getItem(position);

      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser == null) {
        return;
      }

      DialogHelper.showAlertDialog(getActivity(),
          getString(R.string.delete_friend_confirm), "", getString(R.string.confirm_yes),
          new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
              deleteFriend(user.getFromUser().getId(), user.getToUser().getId(), false, position);
            }
          }, getString(R.string.confirm_no), null);
    }
  };

  private SwipeMenuItemClickListener mRecentMenuItemClickListener = (menuBridge, position) -> {
    menuBridge.closeMenu();

    int direction = menuBridge.getDirection();

    if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
      ModelFriendRequest user = adapterRecent.getItem(position);

      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser == null) {
        return;
      }

      DialogHelper.showAlertDialog(getActivity(),
          getString(R.string.delete_friend_confirm), "", getString(R.string.confirm_yes),
          new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
              deleteFriend(user.getFromUser().getId(), user.getToUser().getId(), true, position);
            }
          }, getString(R.string.confirm_no), null);
    }
  };

  private void deleteFriend(int fromUser, int toUser, boolean isRecent, int position) {
    progressDialog.show();

    CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);
    Call<ModelFriendRequestResult> call = apiInterface.deleteFriend(fromUser, toUser);
    call.enqueue(new Callback<ModelFriendRequestResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelFriendRequestResult> call,
          @NonNull Response<ModelFriendRequestResult> response) {
        progressDialog.dismiss();

        ModelFriendRequestResult result = response.body();
        if (result == null || !result.result) {
          return;
        }

        if (isRecent) {
          ModelUser user = adapterRecent.getItem(position).getUser();
          user.setFollowing(false);
          adapterRecent.removeRecent(position);
        } else {
          ModelUser user = adapterFriends.getItem(position).getUser();
          user.setFollowing(false);
          adapterFriends.removeFriends(position);
        }

        initListCount();
      }

      @Override
      public void onFailure(@NonNull Call<ModelFriendRequestResult> call, @NonNull Throwable t) {
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
    }
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (getParentFragment() instanceof FragmentCast) {
      rootFragment = (FragmentCast) getParentFragment();
    }
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_friends, container, false);

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
    binding.layoutFab.setOnClickListener(v -> {
      Intent addFriendsIntent = new Intent(getActivity(), ActivitySearchFriend.class);
      startActivityForResult(addFriendsIntent, SEARCH_FRIEND);
    });
  }

  @Override public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == SEARCH_FRIEND) {
      refreshData();
    }
  }

  private void initRecyclerView() {
    adapterFriends = new AdapterFriends(getActivity(), new FriendsListener() {
      @Override
      public void onItemSelected(int position) {
        ModelUser user = adapterFriends.getItem(position).getUser();

        Intent channelIntent = new Intent(getActivity(), ActivityMyChannel.class);
        channelIntent.putExtra(Constants.CHANNEL_DETAIL_IS_ME, false);
        channelIntent.putExtra(Constants.CHANNEL_DETAIL_ID, user.id);
        startActivity(channelIntent);
      }
    });
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.setNestedScrollingEnabled(false);
    binding.recyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
    binding.recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
    binding.recyclerView.setAdapter(adapterFriends);

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
              getFriendsList();
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

    adapterRecent = new AdapterRecentFriends(getActivity(), new FollowersListener() {
      @Override
      public void onItemSelected(int position) {
        ModelUser user = adapterRecent.getItem(position).getUser();

        Intent channelIntent = new Intent(getActivity(), ActivityMyChannel.class);
        channelIntent.putExtra(Constants.CHANNEL_DETAIL_IS_ME, false);
        channelIntent.putExtra(Constants.CHANNEL_DETAIL_ID, user.getId());
        startActivity(channelIntent);
      }

      @Override
      public void onFollow(int position) {
      }
    });
    LinearLayoutManager recentLayoutManager = new LinearLayoutManager(getActivity());
    binding.recyclerViewRecent.setLayoutManager(recentLayoutManager);
    binding.recyclerViewRecent.setNestedScrollingEnabled(false);
    binding.recyclerViewRecent.setSwipeMenuCreator(mSwipeMenuCreator);
    binding.recyclerViewRecent.setSwipeMenuItemClickListener(mRecentMenuItemClickListener);
    binding.recyclerViewRecent.setAdapter(adapterRecent);
  }

  private void refreshData() {
    showLoader();
    prepareRecyclerViewForNewItems();
    getFriendsList();
    getFriendRequestsCount();
  }

  private void prepareRecyclerViewForNewItems() {
    adapterFriends.removeFriends();
    adapterRecent.removeRecent();

    isLoading = false;
    pageIndex = 1;
  }

  private void getFriendsList() {
    friends = new ArrayList<>();
    recentFriends = new ArrayList<>();

    binding.lblFriendsCount.setText(getString(R.string.friends_count, "0"));
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    adapterFriends.showLoadMoreLoader();

    hasNextPage = false;

    Call<ModelFriendRequestWrapper> call =
        APIClient.getClient()
            .create(CastListAPIInterface.class)
            .getFriends(loginUser.getId(), pageIndex);

    call.enqueue(new Callback<ModelFriendRequestWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelFriendRequestWrapper> call,
          @NonNull Response<ModelFriendRequestWrapper> response) {
        ModelFriendRequestWrapper friendsWrapper = response.body();

        if (friendsWrapper != null && friendsWrapper.getFriends() != null) {
          hasNextPage = friendsWrapper.next != null && !friendsWrapper.next.isEmpty();
          for (int i = 0; i < friendsWrapper.getFriends().size(); i++) {
            ModelFriendRequest itemFriends = friendsWrapper.getFriends().get(i);
            if (itemFriends == null
                || itemFriends.getFromUser() == null
                || itemFriends.getToUser() == null
                || !itemFriends.accepted) {
              continue;
            }

            if (itemFriends.getDatetime() != null) {
              try {
                fromServer.setTimeZone(TimeZone.getTimeZone("ISO"));
                simpleDateFormat.setTimeZone(TimeZone.getDefault());

                Date followerDate = fromServer.parse(itemFriends.getDatetime());
                Calendar cFollower = Calendar.getInstance();
                cFollower.setTime(followerDate);

                Calendar now = Calendar.getInstance();

                if (now.getTimeInMillis() - cFollower.getTimeInMillis() < 7 * ONE_DAY) {
                  recentFriends.add(itemFriends);
                  continue;
                }
              } catch (ParseException ignored) {
                friends.add(itemFriends);
              }
            }

            friends.add(itemFriends);
          }
        }

        for (ModelFriendRequest recentFriend : recentFriends) {
          adapterRecent.addRecent(recentFriend);
        }

        for (ModelFriendRequest friend : friends) {
          adapterFriends.addFriend(friend);
        }

        initListCount();

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelFriendRequestWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders();
      }
    });
  }

  private void getFriendRequestsCount() {
    binding.lblFriendRequestCount.setVisibility(View.GONE);
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    Call<ModelFriendRequestWrapper> call =
        APIClient.getClient()
            .create(CastListAPIInterface.class)
            .getReceivedFriendRequest(loginUser.getId(), false);

    call.enqueue(new Callback<ModelFriendRequestWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelFriendRequestWrapper> call,
          @NonNull Response<ModelFriendRequestWrapper> response) {
        ModelFriendRequestWrapper friendsWrapper = response.body();

        if (friendsWrapper != null) {
          int count = friendsWrapper.getCount();
          if (count > 0) {
            binding.lblFriendRequestCount.setText(count > 9 ? "9+" : String.valueOf(count));
            binding.lblFriendRequestCount.setVisibility(View.VISIBLE);
          }

          if (rootFragment != null) {
            rootFragment.setBadgeVisible(count > 0);
          }
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelFriendRequestWrapper> call, @NonNull Throwable t) {
        call.cancel();
      }
    });
  }

  private void initListCount() {
    binding.layoutRecent.setVisibility(
        adapterRecent.getRecentCount() == 0 ? View.GONE : View.VISIBLE);

    String formattedCount = Util.getFormattedNumber(adapterFriends.getFriendsCount());
    binding.lblFriendsCount.setText(getString(R.string.friends_count, formattedCount));

    binding.layoutItem.setVisibility(
        adapterFriends.getFriendsCount() == 0 ? View.GONE : View.VISIBLE);

    if (adapterRecent.getRecentCount() + adapterFriends.getFriendsCount() == 0) {
      showNoDataView();
    } else {
      hideNoDataView();
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

    isLoading = false;

    adapterFriends.hideLoadMoreLoader();
    adapterFriends.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);
    adapterRecent.hideLoadMoreLoader();
    adapterRecent.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);
  }

  private void hideAllLoaders() {
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

    hideLoaders();
  }
}
