package notq.dccast.screens.navigation_menu.cast.group;

import android.app.Activity;
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
import java.util.ArrayList;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.databinding.FragmentGroupsBinding;
import notq.dccast.model.group.ModelGroup;
import notq.dccast.model.group.ModelGroupWrapper;
import notq.dccast.model.user.ModelResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.navigation_menu.cast.group.create_group.ActivityCreateGroup;
import notq.dccast.screens.navigation_menu.cast.group.group_detail.ActivityGroupDetail;
import notq.dccast.util.Constants;
import notq.dccast.util.DialogHelper;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupsFragment extends Fragment {

  private final int REQUEST_CREATE_GROUP = 4921, REQUEST_GROUP_DETAIL = 4922;
  private FragmentGroupsBinding binding;
  private AdapterGroups adapterGroups;

  private int pageIndex = 1;
  private int totalMyGroupListCount = 0;
  private boolean isLoading = true, hasNextPage = false;

  private ArrayList<ModelGroup> myGroups = new ArrayList<>();
  private ProgressDialog progressDialog;

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
      ModelGroup group = adapterGroups.getItem(position);

      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser == null || group == null) {
        return;
      }

      DialogHelper.showAlertDialog(getActivity(),
          getString(R.string.leave_group_confirm_body), "",
          getString(R.string.confirm_yes),
          new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
              leaveGroup(group.getId(), loginUser.getId(), position);
            }
          }, getString(R.string.confirm_no), null);
    }
  };

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (isVisibleToUser) {
      refreshData();
    }
  }

  private void leaveGroup(int groupId, int memberId, int position) {
    progressDialog.show();

    Call<ModelResult> call =
        APIClient.getClient().create(CastListAPIInterface.class).leaveGroup(groupId, memberId);
    call.enqueue(new Callback<ModelResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelResult> call,
          @NonNull Response<ModelResult> response) {
        progressDialog.dismiss();

        ModelResult result = response.body();
        if (result == null || !result.result) {
          return;
        }

        if (result.isResult()) {
          adapterGroups.removeGroup(position);
          initListCount();
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelResult> call, @NonNull Throwable t) {
        call.cancel();

        progressDialog.dismiss();
      }
    });
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    progressDialog = new ProgressDialog(getActivity());
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_groups, container, false);

    initRecyclerView();
    initButtons();

    return binding.getRoot();
  }

  private void initButtons() {
    binding.btnAdd.setOnClickListener(v -> {
      Intent groupIntent = new Intent(getActivity(), ActivityCreateGroup.class);
      startActivityForResult(groupIntent, REQUEST_CREATE_GROUP);
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CREATE_GROUP) {
      if (resultCode == Activity.RESULT_OK) {
        refreshData();
      }
    } else if (requestCode == REQUEST_GROUP_DETAIL) {
      refreshData();
    }
  }

  private void initRecyclerView() {
    adapterGroups = new AdapterGroups(getActivity(), position -> {
      ModelGroup group = adapterGroups.getItem(position);
      Intent groupIntent = new Intent(getActivity(), ActivityGroupDetail.class);
      groupIntent.putExtra(Constants.GROUP_DETAIL, group);
      startActivityForResult(groupIntent, REQUEST_GROUP_DETAIL);
    });
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.setNestedScrollingEnabled(false);
    binding.recyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
    binding.recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
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
              getGroups();
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
  }

  private void refreshData() {
    showLoader();
    prepareRecyclerViewForNewItems();

    getGroups();
  }

  private void prepareRecyclerViewForNewItems() {
    adapterGroups.removeGroups();

    myGroups = new ArrayList<>();

    isLoading = true;
    pageIndex = 1;
  }

  private void getGroups() {
    binding.lblGroupCount.setText(getString(R.string.groups_count, "0"));
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    adapterGroups.showLoadMoreLoader();

    hasNextPage = false;

    Call<ModelGroupWrapper> call =
        APIClient.getClient()
            .create(CastListAPIInterface.class)
            .getGroups(loginUser.getId(), pageIndex);

    call.enqueue(new Callback<ModelGroupWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelGroupWrapper> call,
          @NonNull Response<ModelGroupWrapper> response) {
        ModelGroupWrapper groupWrapper = response.body();

        if (groupWrapper != null && groupWrapper.groups != null) {
          totalMyGroupListCount = groupWrapper.getCount();
          hasNextPage = groupWrapper.next != null && !groupWrapper.next.isEmpty();
          for (int i = 0; i < groupWrapper.groups.size(); i++) {
            ModelGroup group = groupWrapper.groups.get(i);
            if (group == null || group.getMembers() == null || !group.getMembers()
                .contains(loginUser.getId())) {
              continue;
            }
            myGroups.add(group);
          }
        }

        for (ModelGroup myGroup : myGroups) {
          adapterGroups.addGroup(myGroup);
        }

        initListCount();

        hideAllLoaders();
      }

      @Override
      public void onFailure(@NonNull Call<ModelGroupWrapper> call, @NonNull Throwable t) {
        call.cancel();
        hideAllLoaders();
      }
    });
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

    adapterGroups.hideLoadMoreLoader();
    adapterGroups.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);
  }

  private void initListCount() {
    String myGroupCount = Util.getFormattedNumber(adapterGroups.getGroupsCount());
    binding.lblGroupCount.setText(getString(R.string.groups_count, myGroupCount));

    binding.layoutMyGroup.setVisibility(
        adapterGroups.getGroupsCount() > 0 ? View.VISIBLE : View.GONE);

    if (totalMyGroupListCount == 0) {
      showNoDataView();
    } else {
      hideNoDataView();
    }
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

  @Override public void onDestroy() {
    super.onDestroy();
  }
}
