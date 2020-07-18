package notq.dccast.screens.navigation_menu.settings.contact;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.ProfileAPIInterface;
import notq.dccast.databinding.FragmentContactListBinding;
import notq.dccast.model.ModelContactUs;
import notq.dccast.model.ModelContactUsWrapper;
import notq.dccast.model.user.ModelUser;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactListFragment extends Fragment {

  private FragmentContactListBinding binding;
  private ContactUsAdapter adapter;
  private int pageIndex = 1;
  private boolean isLoading = false, hasNextPage = false;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_list, container, false);

    init();

    showLoader();
    getContactUsList();

    return binding.getRoot();
  }

  private void init() {
    adapter = new ContactUsAdapter(getActivity(), new ContactUsAdapter.ContactUsInterface() {
      @Override public void contactUsClicked(ModelContactUs contactUs) {
        Intent intent = new Intent(getActivity(), ActivityContactUsDetail.class);
        intent.putExtra("contactUs", contactUs);
        startActivity(intent);
      }
    });

    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.setAdapter(adapter);

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
              getContactUsList();
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
      getContactUsList();
    });
  }

  private void getContactUsList() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    hasNextPage = false;

    Call<ModelContactUsWrapper> callVod =
        APIClient.getClient()
            .create(ProfileAPIInterface.class)
            .getContactUsList(loginUser.getId(), 25, pageIndex);

    callVod.enqueue(new Callback<ModelContactUsWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelContactUsWrapper> call,
          @NonNull Response<ModelContactUsWrapper> response) {
        ModelContactUsWrapper contactUsWrapper = response.body();

        if (contactUsWrapper != null && contactUsWrapper.getContactList() != null) {
          hasNextPage = contactUsWrapper.next != null && !contactUsWrapper.next.isEmpty();
          for (int i = 0; i < contactUsWrapper.getContactList().size(); i++) {
            ModelContactUs contactUs = contactUsWrapper.getContactList().get(i);
            adapter.addContactUs(contactUs);
          }
        }

        if (adapter.getContactUsCount() > 0) {
          hideNoDataView();
        } else {
          showNoDataView();
        }

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelContactUsWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders();
      }
    });
  }

  private void prepareRecyclerViewForNewItems() {
    adapter.removeContactUs();
    isLoading = false;
    pageIndex = 1;
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
