package notq.dccast.screens.navigation_menu.notification.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.notification.NotificationAPIInterface;
import notq.dccast.databinding.FragmentNotificationNoticeBinding;
import notq.dccast.model.notification.ModelNotification;
import notq.dccast.model.notification.ModelNotificationWrapper;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.navigation_menu.cast.fragments.OpenVideoFragment;
import notq.dccast.screens.navigation_menu.notification.ActivityNotification;
import notq.dccast.screens.navigation_menu.notification.adapter.AdapterNotificationNotice;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNotificationNotice extends OpenVideoFragment {

  private FragmentNotificationNoticeBinding binding;
  private AdapterNotificationNotice adapterNotificationNotice;

  private ActivityNotification activity;

  private int pageIndex = 1;
  private boolean isLoading = false, hasNextPage = false;
  private NotificationAPIInterface apiInterface;
  private ArrayList<ModelNotification> notifications;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    activity = (ActivityNotification) getActivity();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_notification_notice, container, false);
    init();

    showLoader();
    getNotifications();
    return binding.getRoot();
  }

  private void init() {
    apiInterface = APIClient.getClient().create(NotificationAPIInterface.class);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    binding.recyclerView.setLayoutManager(layoutManager);

    adapterNotificationNotice = new AdapterNotificationNotice(getActivity(), position -> {

    });
    binding.recyclerView.setAdapter(adapterNotificationNotice);

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
              getNotifications();
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
      getNotifications();
    });
  }

  private void prepareRecyclerViewForNewItems() {
    adapterNotificationNotice.removeNotifications();
    isLoading = false;
    pageIndex = 1;
  }

  private void getNotifications() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    adapterNotificationNotice.showLoadMoreLoader();

    Call<ModelNotificationWrapper> call =
        apiInterface.getNotificationList("notice", loginUser.getId(), pageIndex);

    call.enqueue(new Callback<ModelNotificationWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelNotificationWrapper> call,
          @NonNull Response<ModelNotificationWrapper> response) {
        ModelNotificationWrapper mediaWrapper = response.body();

        for (ModelNotification notification : mediaWrapper.notifications) {
          adapterNotificationNotice.addNotification(notification);
        }

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelNotificationWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders();
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

    isLoading = false;

    adapterNotificationNotice.hideLoadMoreLoader();
    adapterNotificationNotice.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);
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
