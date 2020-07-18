package notq.dccast.screens.navigation_menu.cast.group.group_detail;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
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
import notq.dccast.databinding.FragmentGroupMemberBinding;
import notq.dccast.model.group.ModelGroup;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.profile.ModelProfile;
import notq.dccast.model.user.profile.ModelProfileWrapper;
import notq.dccast.screens.home.adapter.AdapterVideos;
import notq.dccast.screens.navigation_menu.cast.group.create_group.AdapterMembers;
import notq.dccast.screens.navigation_menu.content.my_channel.ActivityMyChannel;
import notq.dccast.util.Constants;
import notq.dccast.util.DialogHelper;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupMemberFragment extends Fragment {

  private FragmentGroupMemberBinding binding;
  private AdapterMembers adapterMembers;

  private int groupId;
  private int pageIndex = 1;
  private boolean isLoading = false, hasNextPage = false;
  private boolean init = false;
  private ActivityGroupDetail activity;
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
      ModelUser user = adapterMembers.getItem(position);
      ModelUser loginUser = LoginService.getLoginUser();
      if (user == null || user.getId() == 0) {
        return;
      }

      if (loginUser != null && loginUser.getId() == user.getId()) {
        return;
      }

      DialogHelper.showAlertDialog(getActivity(), "",
          getString(R.string.delete_group_member_confirm),
          getString(R.string.confirm_yes),
          new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
              updateGroup(user.getId(), position);
            }
          }, getString(R.string.confirm_no), null);
    }
  };

  public static GroupMemberFragment newInstance(int userId) {
    GroupMemberFragment myFragment = new GroupMemberFragment();

    Bundle args = new Bundle();
    args.putInt(Constants.GROUP_ID, userId);
    myFragment.setArguments(args);

    return myFragment;
  }

  private void updateGroup(int removedMemberId, int position) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }

    showLoading();

    ArrayList<Integer> ids = new ArrayList<>();
    for (ModelUser member : adapterMembers.getMembers()) {
      if (member.getId() != removedMemberId) {
        ids.add(member.getId());
      }
    }
    ids.add(loginUser.getId());

    CastListAPIInterface apiInterface = APIClient.getClient().create(CastListAPIInterface.class);
    Call<ModelGroup> call =
        apiInterface.updateGroupMembers(groupId, ids);

    call.enqueue(new Callback<ModelGroup>() {
      @Override
      public void onResponse(@NonNull Call<ModelGroup> call,
          @NonNull Response<ModelGroup> response) {
        hideLoading();

        ModelGroup group = response.body();
        if (group != null) {
          adapterMembers.removeMember(position);
          Toast.makeText(getActivity(), getString(R.string.delete_group_member_success),
              Toast.LENGTH_LONG).show();
        } else {
          Toast.makeText(getActivity(), getString(R.string.error),
              Toast.LENGTH_LONG)
              .show();
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelGroup> call, @NonNull Throwable t) {
        call.cancel();

        hideLoading();
        Toast.makeText(getActivity(), getString(R.string.error),
            Toast.LENGTH_LONG)
            .show();
      }
    });
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      groupId = getArguments().getInt(Constants.GROUP_ID);
    }

    activity = (ActivityGroupDetail) getActivity();
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (isVisibleToUser && init) {
      showLoader();
      getGroupMembers();
    }
  }

  public void refresh() {
    prepareRecyclerViewForNewItems();
    showLoader();
    getGroupMembers();
  }

  private void prepareRecyclerViewForNewItems() {
    if (adapterMembers != null) {
      adapterMembers.removeMembers();
    }

    isLoading = false;
    pageIndex = 1;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group_member, container, false);

    init();

    init = true;

    progressDialog = new ProgressDialog(getActivity());
    progressDialog.setCancelable(false);

    showLoader();
    getGroupMembers();

    return binding.getRoot();
  }

  private void showLoading() {
    if (progressDialog != null) {
      progressDialog.show();
    }
  }

  private void hideLoading() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  private void init() {
    adapterMembers = new AdapterMembers(getActivity(), position -> {
      ModelUser user = adapterMembers.getItem(position);
      Intent channelIntent = new Intent(getActivity(), ActivityMyChannel.class);
      channelIntent.putExtra(Constants.CHANNEL_DETAIL_IS_ME, false);
      channelIntent.putExtra(Constants.CHANNEL_DETAIL_ID, user.id);
      startActivity(channelIntent);
    });

    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
    binding.recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
    binding.recyclerView.setAdapter(adapterMembers);

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
              getGroupMembers();
            }
          }
        }
      }
    });
  }

  private void getGroupMembers() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      hideAllLoaders();
      return;
    }

    hasNextPage = false;
    adapterMembers.showLoadMoreLoader();

    Call<ModelProfileWrapper> call =
        APIClient.getClient()
            .create(CastListAPIInterface.class)
            .getGroupMembers(groupId, pageIndex);

    call.enqueue(new Callback<ModelProfileWrapper>() {
      @Override
      public void onResponse(@NonNull Call<ModelProfileWrapper> call,
          @NonNull Response<ModelProfileWrapper> response) {
        ModelProfileWrapper profileWrapper = response.body();

        hideAllLoaders();

        if (profileWrapper == null || profileWrapper.getProfiles() == null) {
          return;
        }

        hasNextPage = profileWrapper.next != null && !profileWrapper.next.isEmpty();

        ArrayList<ModelUser> members = new ArrayList<>();
        for (ModelProfile profile : profileWrapper.getProfiles()) {
          ModelUser user = new ModelUser(profile.getId());
          user.setProfile(profile);
          members.add(user);
        }

        adapterMembers.setMembers(members);
      }

      @Override
      public void onFailure(@NonNull Call<ModelProfileWrapper> call, @NonNull Throwable t) {
        call.cancel();

        hideAllLoaders();
      }
    });
  }

  private void showLoader() {
    if (binding.dcLoader != null
        && !binding.dcLoader.isAnimating()
        && !binding.dcLoader.isShown()) {
      binding.dcLoader.playAnimation();
      binding.dcLoader.setVisibility(View.VISIBLE);
    }
  }

  private void hideLoaders() {
    if (binding.dcLoader.isShown() && binding.dcLoader.isAnimating()) {
      binding.dcLoader.setVisibility(View.GONE);
      binding.dcLoader.cancelAnimation();
    }

    adapterMembers.hideLoadMoreLoader();
    adapterMembers.notifyItemChanged(AdapterVideos.VIEW_TYPE_NO_DATA);

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
