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
import notq.dccast.databinding.ActivitySearchGroupsBinding;
import notq.dccast.model.group.ModelGroup;
import notq.dccast.model.group.ModelGroupWrapper;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.navigation_menu.cast.group.AdapterGroups;
import notq.dccast.screens.navigation_menu.cast.group.group_detail.ActivityGroupDetail;
import notq.dccast.util.Constants;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivitySearchGroups extends BaseActivity {

  private Context mContext = this;
  private ActivitySearchGroupsBinding binding;

  private AdapterGroups adapterGroups;
  private int pageIndex = 1;
  private boolean isLoading = false, hasNextPage = false;
  private ProgressDialog progressDialog;

  private boolean didAction = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_search_groups);

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
    binding.header.lblHeader.setText(getString(R.string.activity_search_groups));
    binding.header.backButton.setOnClickListener(v -> {
      onBackPressed();
    });
  }

  private void initRecyclerView() {
    adapterGroups = new AdapterGroups(mContext, position -> {
      ModelGroup group = adapterGroups.getItem(position);
      Intent groupIntent = new Intent(mContext, ActivityGroupDetail.class);
      groupIntent.putExtra(Constants.GROUP_DETAIL, group);
      startActivity(groupIntent);
    });

    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.setNestedScrollingEnabled(false);
    binding.recyclerView.setAdapter(adapterGroups);

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
    adapterGroups.removeGroups();

    isLoading = false;
    pageIndex = 1;
  }

  void searchRequest(String keyword) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    hasNextPage = false;
    adapterGroups.showLoadMoreLoader();

    Call<ModelGroupWrapper> call =
        APIClient.getClient()
            .create(CastListAPIInterface.class)
            .searchFromGroups(loginUser.getId(), keyword, pageIndex);

    call.enqueue(new Callback<ModelGroupWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelGroupWrapper> call,
          @NonNull Response<ModelGroupWrapper> response) {
        ModelGroupWrapper userWrapper = response.body();

        if (userWrapper != null && userWrapper.groups != null) {
          hasNextPage = userWrapper.next != null && !userWrapper.next.isEmpty();
          for (int i = 0; i < userWrapper.groups.size(); i++) {
            adapterGroups.addGroup(userWrapper.groups.get(i));
          }
        }

        if (adapterGroups.getGroupsCount() == 0) {
          showNoData();
        } else {
          hideNoData();
        }

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelGroupWrapper> call, @NonNull Throwable t) {
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

    adapterGroups.hideLoadMoreLoader();
    adapterGroups.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);

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
