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
import notq.dccast.databinding.FragmentNotificationVodBinding;
import notq.dccast.model.notification.ModelNotification;
import notq.dccast.model.notification.ModelNotificationWrapper;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.navigation_menu.cast.fragments.OpenVideoFragment;
import notq.dccast.screens.navigation_menu.notification.ActivityNotification;
import notq.dccast.screens.navigation_menu.notification.adapter.AdapterNotificationVOD;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNotificationVOD extends OpenVideoFragment {

  private FragmentNotificationVodBinding binding;
  private AdapterNotificationVOD adapterNotificationVOD;

  private ActivityNotification activity;

  private int pageIndex = 1;
  private boolean isLoading = false, hasNextPage = false;
  private NotificationAPIInterface apiInterface;
  private ArrayList<ModelNotification> notifications;
  private boolean firstTime = true;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    activity = (ActivityNotification) getActivity();
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (firstTime && isVisibleToUser) {
      showLoader();
      getNotifications();

      firstTime = false;
    }
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_notification_vod, container, false);
    init();

    return binding.getRoot();
  }

  private void init() {
    apiInterface = APIClient.getClient().create(NotificationAPIInterface.class);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    binding.recyclerView.setLayoutManager(layoutManager);

    adapterNotificationVOD = new AdapterNotificationVOD(getActivity(), position -> {

    });
    binding.recyclerView.setAdapter(adapterNotificationVOD);

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
    adapterNotificationVOD.removeNotifications();
    isLoading = false;
    pageIndex = 1;
  }

  private void getNotifications() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    adapterNotificationVOD.showLoadMoreLoader();

    Call<ModelNotificationWrapper> call =
        apiInterface.getNotificationList("vod", loginUser.getId(), pageIndex);

    call.enqueue(new Callback<ModelNotificationWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelNotificationWrapper> call,
          @NonNull Response<ModelNotificationWrapper> response) {
        ModelNotificationWrapper mediaWrapper = response.body();

        for (ModelNotification notification : mediaWrapper.notifications) {
          adapterNotificationVOD.addNotification(notification);
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

    adapterNotificationVOD.hideLoadMoreLoader();
    adapterNotificationVOD.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);
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
