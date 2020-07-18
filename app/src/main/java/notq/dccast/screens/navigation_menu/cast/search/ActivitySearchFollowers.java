package notq.dccast.screens.navigation_menu.cast.search;

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
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.databinding.ActivitySearchFollowersBinding;
import notq.dccast.model.user.ModelResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.ModelUserWrapper;
import notq.dccast.model.user.follower.ModelFollowResult;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.navigation_menu.cast.adapter.AdapterFollowers;
import notq.dccast.screens.navigation_menu.cast.interfaces.FollowersListener;
import notq.dccast.screens.navigation_menu.content.my_channel.ActivityMyChannel;
import notq.dccast.util.Constants;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivitySearchFollowers extends BaseActivity {

  private Context mContext = this;
  private ActivitySearchFollowersBinding binding;

  private AdapterFollowers adapterFollowers;
  private int pageIndex = 1;
  private boolean isLoading = false, hasNextPage = false;
  private ProgressDialog progressDialog;

  private boolean didAction = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_search_followers);

    initToolbar();
    initRecyclerView();
    init();
  }

  private void init() {
    progressDialog = new ProgressDialog(mContext);
    progressDialog.setCancelable(false);
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_search_followers));
    binding.header.backButton.setOnClickListener(v -> {
      onBackPressed();
    });
  }

  private void initRecyclerView() {
    adapterFollowers = new AdapterFollowers(mContext, false, new FollowersListener() {
      @Override
      public void onItemSelected(int position) {
        ModelUser user = adapterFollowers.getItem(position);

        Intent channelIntent = new Intent(mContext, ActivityMyChannel.class);
        channelIntent.putExtra(Constants.CHANNEL_DETAIL_IS_ME, false);
        channelIntent.putExtra(Constants.CHANNEL_DETAIL_ID, user.id);
        startActivity(channelIntent);
      }

      @Override
      public void onFollow(int position) {
        ModelUser user = adapterFollowers.getItem(position);

        ModelUser loginUser = LoginService.getLoginUser();
        if (loginUser == null) {
          return;
        }
        if (user.isFollowing()) {
          unFollowUser(position, user.getId(), loginUser.getId());
        } else {
          followUser(position, user.getId(), loginUser.getId());
        }
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
              searchRequest(Objects.requireNonNull(binding.etSearch.getText())
                  .toString());
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
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    hasNextPage = false;
    adapterFollowers.showLoadMoreLoader();

    Call<ModelUserWrapper> call =
        APIClient.getClient()
            .create(CastListAPIInterface.class)
            .searchFromFollowers(loginUser.getId(), keyword, pageIndex);

    call.enqueue(new Callback<ModelUserWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelUserWrapper> call,
          @NonNull Response<ModelUserWrapper> response) {
        ModelUserWrapper userWrapper = response.body();

        if (userWrapper != null && userWrapper.users != null) {
          hasNextPage = userWrapper.next != null && !userWrapper.next.isEmpty();
          for (int i = 0; i < userWrapper.users.size(); i++) {
            adapterFollowers.addFollower(userWrapper.users.get(i));
          }
        }

        if (adapterFollowers.getFollowersCount() == 0) {
          showNoData();
        } else {
          hideNoData();
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

  private void showNoData() {
    binding.layoutNoData.setVisibility(View.VISIBLE);
  }

  private void hideNoData() {
    binding.layoutNoData.setVisibility(View.GONE);
  }

  private void followUser(int position, int userId, int followerId) {
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

        didAction = true;

        adapterFollowers.getItem(position).setFollowing(true);
        adapterFollowers.notifyItemChanged(position);
      }

      @Override
      public void onFailure(@NonNull Call<ModelFollowResult> call, @NonNull Throwable t) {
        call.cancel();

        progressDialog.dismiss();
      }
    });
  }

  private void unFollowUser(int position, int userId, int followerId) {
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

        didAction = true;
        adapterFollowers.getItem(position).setFollowing(false);
        adapterFollowers.notifyItemChanged(position);
      }

      @Override
      public void onFailure(@NonNull Call<ModelResult> call, @NonNull Throwable t) {
        call.cancel();

        progressDialog.dismiss();
      }
    });
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
