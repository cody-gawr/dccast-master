package notq.dccast.screens.navigation_menu.cast.group.add_people;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.databinding.ActivityAddPeopleBinding;
import notq.dccast.model.friends.ModelFriendRequest;
import notq.dccast.model.friends.ModelFriendRequestWrapper;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.navigation_menu.cast.interfaces.AddPeopleListener;
import notq.dccast.util.Constants;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityAddPeople extends BaseActivity {

  private Context mContext = this;
  private ActivityAddPeopleBinding binding;
  private AdapterAddPeople adapterAddPeople;
  private HashMap<Integer, TextView> addedPeople = new HashMap<>();

  private int pageIndex = 1;
  private boolean isLoading = false, hasNextPage = false;

  private HashMap<Integer, ModelUser> addedMembersMap = new HashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_add_people);

    initToolbar();
    initRecyclerView();

    ArrayList<ModelUser> addedMembers =
        (ArrayList<ModelUser>) getIntent().getSerializableExtra(Constants.GROUP_MEMBERS);

    if (addedMembers == null) {
      addedMembers = new ArrayList<>();
    }

    for (ModelUser addedMember : addedMembers) {
      addedMembersMap.put(addedMember.getId(), addedMember);
      addToSelected(addedMember);
    }

    showLoader();
    getFriends();
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_add_people));
    binding.header.backButton.setOnClickListener(v -> {
      onBackPressed();
    });

    binding.header.lblActionBtn.setVisibility(View.VISIBLE);
    binding.header.lblActionBtn.setText(getString(R.string.create_group_add));
    binding.header.lblActionBtn.setOnClickListener(v -> {
      Intent returnIntent = getIntent();
      ArrayList<ModelUser> members = new ArrayList<>();
      if (addedMembersMap != null) {
        for (ModelUser profile : addedMembersMap.values()) {
          profile.setNeedFetchProfile(true);
          members.add(profile);
        }
      }
      returnIntent.putExtra(Constants.GROUP_ADDED_MEMBERS, members);
      setResult(RESULT_OK, returnIntent);
      finish();
    });
  }

  private void initRecyclerView() {
    adapterAddPeople = new AdapterAddPeople(mContext, new AddPeopleListener() {
      @Override
      public void onItemSelected(int position) {
        ModelUser selectedItem = adapterAddPeople.getItem(position);
        if (selectedItem.isSelected()) {
          removeFromSelected(selectedItem);

          addedMembersMap.remove(selectedItem.getId());
        } else {
          addToSelected(selectedItem);

          if (!addedMembersMap.containsKey(selectedItem.getId())) {
            addedMembersMap.put(selectedItem.getId(), selectedItem);
          }
        }
      }
    });

    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.setAdapter(adapterAddPeople);

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
              getFriends();
            }
          }
        }
      }
    });
  }

  private void getFriends() {
    hasNextPage = false;

    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      hideAllLoaders();
      return;
    }

    adapterAddPeople.showLoadMoreLoader();

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
                || itemFriends.getUser() == null
                || itemFriends.getUser().getId() == loginUser.getId()) {
              continue;
            }

            if (addedMembersMap.containsKey(itemFriends.getId())) {
              itemFriends.getUser().setSelected(true);
            }
            adapterAddPeople.addUser(itemFriends.getUser());
          }
        }

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelFriendRequestWrapper> call, @NonNull Throwable t) {
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

    adapterAddPeople.hideLoadMoreLoader();
    adapterAddPeople.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);

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

  private void addToSelected(ModelUser item) {
    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    TextView lblItem = (TextView) inflater.inflate(R.layout.vh_add_people_tag, null);
    LinearLayout.LayoutParams params =
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    int rightMargin = getResources().getDimensionPixelSize(R.dimen.margin_size_large);
    int topMargin = getResources().getDimensionPixelSize(R.dimen.margin_size_medium);
    params.setMargins(0, topMargin, rightMargin, topMargin);
    lblItem.setLayoutParams(params);
    lblItem.setId(item.getId());
    lblItem.setText(item.getNickName());

    lblItem.setOnClickListener(v -> {
      addedPeople.remove(item.getId());
      binding.flexLayout.removeView(lblItem);

      addedMembersMap.remove(item.getId());

      adapterAddPeople.updateItemSelected(item.getId(), false);
    });

    binding.flexLayout.addView(lblItem, addedPeople.size());
    addedPeople.put(item.getId(), lblItem);

    binding.etSearchUsername.setText("");

    adapterAddPeople.updateItemSelected(item.getId(), true);
  }

  private void removeFromSelected(ModelUser item) {
    if (!addedPeople.containsKey(item.getId())) {
      return;
    }
    TextView selected = addedPeople.get(item.getId());
    addedPeople.remove(item.getId());
    binding.flexLayout.removeView(selected);

    adapterAddPeople.updateItemSelected(item.getId(), false);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
