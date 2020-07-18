package notq.dccast.screens.navigation_menu.cast.search_friend;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.databinding.ActivitySearchFriendBinding;
import notq.dccast.model.friends.ModelFriendRequestWrapper;
import notq.dccast.model.friends.ModelFriendResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.ModelUserWrapper;
import notq.dccast.model.user.follower.ModelFollower;
import notq.dccast.model.user.follower.ModelFollowerWrapper;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.navigation_menu.cast.adapter.AdapterSearchFriends;
import notq.dccast.screens.navigation_menu.cast.interfaces.SearchFriendListener;
import notq.dccast.screens.navigation_menu.content.my_channel.ActivityMyChannel;
import notq.dccast.util.Constants;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivitySearchFriend extends BaseActivity {

  private final int ADD_FRIEND = 2302;
  private Context mContext = this;
  private ActivitySearchFriendBinding binding;

  private AdapterSearchFriends adapterFollowers;
  private int pageIndex = 1;
  private boolean isLoading = false, hasNextPage = false;
  private ProgressDialog progressDialog;

  private boolean didAction = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_search_friend);

    initToolbar();
    initRecyclerView();
    init();

    refreshData();
  }

  @SuppressLint("SetTextI18n") private void refreshData() {
    getFriendRequestsCount();

    binding.header.lblActionBtn.setText(getString(R.string.search_friend_request, 0));
  }

  private void init() {
    progressDialog = new ProgressDialog(mContext);
    progressDialog.setCancelable(false);

    binding.lblResultCount.setText(getString(R.string.search_friend_result, 0));
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_search_friend));
    binding.header.backButton.setOnClickListener(v -> {
      onBackPressed();
    });
    binding.header.lblActionBtn.setText(getString(R.string.search_friend_request, 0));
    binding.header.lblActionBtn.setVisibility(View.VISIBLE);
    binding.header.lblActionBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(mContext, ActivityAddFriend.class);
        startActivityForResult(intent, ADD_FRIEND);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == ADD_FRIEND) {
      refreshData();
    }
  }

  private void initRecyclerView() {
    adapterFollowers = new AdapterSearchFriends(mContext, new SearchFriendListener() {
      @Override
      public void onItemSelected(int position) {
        ModelUser user = adapterFollowers.getItem(position);

        Intent channelIntent = new Intent(mContext, ActivityMyChannel.class);
        channelIntent.putExtra(Constants.CHANNEL_DETAIL_IS_ME, false);
        channelIntent.putExtra(Constants.CHANNEL_DETAIL_ID, user.id);
        startActivity(channelIntent);
      }

      @Override
      public void addFriend(int position) {
        ModelUser user = adapterFollowers.getItem(position);

        ModelUser loginUser = LoginService.getLoginUser();
        if (loginUser == null || user.getId() == loginUser.getId()) {
          return;
        }

        sendFriendRequest(loginUser.getId(), user.getId(), position);
      }
    });
    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.setNestedScrollingEnabled(false);
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

    binding.lblCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        binding.etSearch.setText("");
      }
    });

    binding.etSearch.setOnEditorActionListener(
        (textView, actionId, keyEvent) -> {
          if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (isValid()) {
              prepareRecyclerViewForNewItems();
              searchRequest(Objects.requireNonNull(binding.etSearch.getText())
                  .toString());
            }

            return true;
          }
          return false;
        });

    binding.etSearch.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        binding.lblCancel.setVisibility(s.length() == 0 ? View.GONE : View.VISIBLE);
      }
    });
  }

  private boolean isValid() {
    if (Objects.requireNonNull(binding.etSearch.getText()).length() == 0) {
      Toast.makeText(getApplicationContext(), getString(R.string.validate_search_value),
          Toast.LENGTH_SHORT).show();
      return false;
    } else {
      return true;
    }
  }

  private void prepareRecyclerViewForNewItems() {
    adapterFollowers.removeFollowers();

    isLoading = false;
    pageIndex = 1;
  }

  void searchRequest(String keyword) {
    prepareRecyclerViewForNewItems();
    adapterFollowers.showLoadMoreLoader();
    hasNextPage = false;

    Call<ModelUserWrapper> call =
        APIClient.getClient().create(CastListAPIInterface.class).search(keyword, pageIndex);

    call.enqueue(new Callback<ModelUserWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelUserWrapper> call,
          @NonNull Response<ModelUserWrapper> response) {
        ModelUserWrapper userWrapper = response.body();

        if (userWrapper != null && userWrapper.users != null) {
          hasNextPage = userWrapper.next != null && !userWrapper.next.isEmpty();
          for (int i = 0; i < userWrapper.users.size(); i++) {
            ModelUser user = userWrapper.users.get(i);
            user.setNeedFetchFriend(true);
            adapterFollowers.addFollower(user);
          }
        }

        if (userWrapper.getCount() == 0) {
          showNoDataView();
        } else {
          binding.lblResultCount.setText(
              getString(R.string.search_friend_result, userWrapper.getCount()));

          hideNoDataView();
        }

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelUserWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders();
      }
    });
  }

  private void sendFriendRequest(int followerId, int userId, int position) {
    progressDialog.show();

    CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);
    Call<ModelFriendResult> call = apiInterface.sendFriendRequest(followerId, userId);
    call.enqueue(new Callback<ModelFriendResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelFriendResult> call,
          @NonNull Response<ModelFriendResult> response) {
        progressDialog.dismiss();

        ModelFriendResult result = response.body();
        if (result == null) {
          return;
        }

        adapterFollowers.setFriendRequestSent(position);
      }

      @Override
      public void onFailure(@NonNull Call<ModelFriendResult> call, @NonNull Throwable t) {
        call.cancel();

        progressDialog.dismiss();
      }
    });
  }

  private void getFollowersList() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    adapterFollowers.showLoadMoreLoader();

    hasNextPage = false;

    Call<ModelFollowerWrapper> call =
        APIClient.getClient()
            .create(CastListAPIInterface.class)
            .getFollowers(loginUser.getId(), pageIndex);

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
            adapterFollowers.addFollower(profile.getFollower());
          }
        }

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelFollowerWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders();
      }
    });
  }

  private void getFriendRequestsCount() {
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
            binding.header.lblActionBtn.setText(getString(R.string.search_friend_request, count));
          }
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelFriendRequestWrapper> call, @NonNull Throwable t) {
        call.cancel();
      }
    });
  }

  private void showNoDataView() {
    binding.layoutItem.setVisibility(View.GONE);
    binding.layoutNoData.setVisibility(View.VISIBLE);
  }

  private void hideNoDataView() {
    binding.layoutItem.setVisibility(View.VISIBLE);
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

    //        if (binding.refresher.isRefreshing()) {
    //            binding.refresher.setRefreshing(false);
    //        }

    adapterFollowers.hideLoadMoreLoader();
    adapterFollowers.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);

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
          binding.dcLoader.cancelAnimation();
        }

        binding.dcLoader.setVisibility(View.GONE);
      }
    }.start();

    hideLoaders();
  }

  @Override
  public void onBackPressed() {
    if (didAction) {
      setResult(RESULT_OK);
      finish();
      return;
    }
    super.onBackPressed();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
