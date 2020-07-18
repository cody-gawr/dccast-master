package notq.dccast.screens.navigation_menu.cast.search_friend;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.databinding.ActivityAddFriendBinding;
import notq.dccast.model.friends.ModelFriendRequest;
import notq.dccast.model.friends.ModelFriendRequestResult;
import notq.dccast.model.friends.ModelFriendRequestWrapper;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.navigation_menu.cast.adapter.AdapterAddFriend;
import notq.dccast.screens.navigation_menu.cast.interfaces.AddFriendListener;
import notq.dccast.screens.navigation_menu.content.my_channel.ActivityMyChannel;
import notq.dccast.util.Constants;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityAddFriend extends BaseActivity {

  private Context mContext = this;
  private ActivityAddFriendBinding binding;

  private AdapterAddFriend adapterAddFriend;
  private int pageIndex = 1;
  private boolean isLoading = false, hasNextPage = false;
  private ProgressDialog progressDialog;

  private boolean didAction = false;

  private SwipeMenuCreator mSwipeMenuCreator = (swipeLeftMenu, swipeRightMenu, viewType) -> {
    int width = getResources().getDimensionPixelSize(R.dimen.swipe_menu_width);
    int height = ViewGroup.LayoutParams.MATCH_PARENT;

    SwipeMenuItem deleteItem = new SwipeMenuItem(mContext).setBackgroundColor(
        ContextCompat.getColor(mContext, R.color.swipe_red_background))
        .setText(getString(R.string.delete))
        .setTextSize(12)
        .setTextColor(ContextCompat.getColor(mContext, R.color.white))
        .setWidth(width)
        .setHeight(height);
    swipeRightMenu.addMenuItem(deleteItem);
  };

  private SwipeMenuItemClickListener mMenuItemClickListener = (menuBridge, position) -> {
    menuBridge.closeMenu();

    int direction = menuBridge.getDirection();

    if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
      ModelFriendRequest user = adapterAddFriend.getItem(position);

      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser == null) {
        return;
      }

      denyRequest(user, position);
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_add_friend);

    initToolbar();
    initRecyclerView();
    init();

    showLoader();
    prepareRecyclerViewForNewItems();
    getFriendRequests();
  }

  private void init() {
    progressDialog = new ProgressDialog(mContext);
    progressDialog.setCancelable(false);
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_add_friend));
    binding.header.backButton.setOnClickListener(v -> {
      onBackPressed();
    });
  }

  private void initRecyclerView() {
    adapterAddFriend = new AdapterAddFriend(mContext, new AddFriendListener() {
      @Override
      public void onItemSelected(int position) {
        ModelUser user = adapterAddFriend.getItem(position).getUser();

        Intent channelIntent = new Intent(mContext, ActivityMyChannel.class);
        channelIntent.putExtra(Constants.CHANNEL_DETAIL_IS_ME, false);
        channelIntent.putExtra(Constants.CHANNEL_DETAIL_ID, user.id);
        startActivity(channelIntent);
      }

      @Override
      public void onConfirm(int position) {
        ModelFriendRequest request = adapterAddFriend.getItem(position);

        ModelUser loginUser = LoginService.getLoginUser();
        if (loginUser == null) {
          return;
        }

        if (!request.isAccepted()) {
          acceptRequest(request, position);
        }
      }
    });
    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.setNestedScrollingEnabled(false);
    binding.recyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
    binding.recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
    binding.recyclerView.setAdapter(adapterAddFriend);

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
              getFriendRequests();
            }
          }
        }
      }
    });
  }

  private void prepareRecyclerViewForNewItems() {
    adapterAddFriend.removeRequests();

    isLoading = false;
    pageIndex = 1;
  }

  private void acceptRequest(ModelFriendRequest request, int position) {
    CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);
    Call<ModelFriendRequestResult> call =
        apiInterface.acceptFriendRequest(request.getFromUser().getId(),
            request.getToUser().getId());
    call.enqueue(new Callback<ModelFriendRequestResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelFriendRequestResult> call,
          @NonNull Response<ModelFriendRequestResult> response) {
        progressDialog.dismiss();

        ModelFriendRequestResult result = response.body();
        if (result == null || !result.isResult()) {
          return;
        }

        ModelFriendRequest request = adapterAddFriend.getItem(position);
        request.setAccepted(true);
        adapterAddFriend.updateFriendRequest(request, position);
      }

      @Override
      public void onFailure(@NonNull Call<ModelFriendRequestResult> call, @NonNull Throwable t) {
        call.cancel();

        progressDialog.dismiss();
      }
    });
  }

  private void denyRequest(ModelFriendRequest request, int position) {
    CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);
    Call<ModelFriendRequestResult> call =
        apiInterface.deleteFriendRequest(request.getFromUser().getId(),
            request.getToUser().getId());
    call.enqueue(new Callback<ModelFriendRequestResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelFriendRequestResult> call,
          @NonNull Response<ModelFriendRequestResult> response) {
        progressDialog.dismiss();

        ModelFriendRequestResult result = response.body();
        if (result == null || !result.isResult()) {
          Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
          return;
        }

        adapterAddFriend.removeFriendRequest(position);
        initListCount();
      }

      @Override
      public void onFailure(@NonNull Call<ModelFriendRequestResult> call, @NonNull Throwable t) {
        call.cancel();

        progressDialog.dismiss();
      }
    });
  }

  private void getFriendRequests() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    adapterAddFriend.showLoadMoreLoader();

    hasNextPage = false;

    Call<ModelFriendRequestWrapper> call =
        APIClient.getClient()
            .create(CastListAPIInterface.class)
            .getReceivedFriendRequest(loginUser.getId(), false);

    call.enqueue(new Callback<ModelFriendRequestWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelFriendRequestWrapper> call,
          @NonNull Response<ModelFriendRequestWrapper> response) {
        ModelFriendRequestWrapper userWrapper = response.body();

        if (userWrapper != null && userWrapper.getFriends() != null) {
          hasNextPage = userWrapper.next != null && !userWrapper.next.isEmpty();
          for (int i = 0; i < userWrapper.getFriends().size(); i++) {
            ModelFriendRequest request = userWrapper.getFriends().get(i);
            if (request == null
                || request.getFromUser() == null
                || request.getToUser() == null
                || request.isAccepted()) {
              continue;
            }
            adapterAddFriend.addFriendRequest(request);
          }
        }

        initListCount();

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelFriendRequestWrapper> call, @NonNull Throwable t) {
        call.cancel();
      }
    });
  }

  private void initListCount() {
    if (adapterAddFriend.getFriendRequestsCount() > 0) {
      hideNoDataView();
    } else {
      showNoDataView();
    }
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

    adapterAddFriend.hideLoadMoreLoader();
    adapterAddFriend.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);

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
