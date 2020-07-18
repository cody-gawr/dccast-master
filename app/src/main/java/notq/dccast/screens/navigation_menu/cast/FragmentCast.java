package notq.dccast.screens.navigation_menu.cast;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.databinding.FragmentCastBinding;
import notq.dccast.model.friends.ModelFriendRequestWrapper;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.navigation_menu.cast.fragments.FollowersFragment;
import notq.dccast.screens.navigation_menu.cast.fragments.FollowingFragment;
import notq.dccast.screens.navigation_menu.cast.fragments.FriendsFragment;
import notq.dccast.screens.navigation_menu.cast.group.GroupsFragment;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCast extends Fragment {

  private FragmentCastBinding binding;
  private AdapterCastList pagerAdapter;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cast, container, false);

    init();

    return binding.getRoot();
  }

  public void hideTab() {
    if (binding.tabLayout != null) {
      binding.tabLayout.setVisibility(View.GONE);
    }
  }

  public void showTab() {
    if (binding.tabLayout != null) {
      binding.tabLayout.setVisibility(View.VISIBLE);
    }
  }

  public int getSelectedTab() {
    if (binding.tabLayout != null) {
      return binding.tabLayout.getSelectedTabPosition();
    }
    return -1;
  }

  private void init() {
    pagerAdapter = new AdapterCastList(getActivity(), getFragmentManager());
    binding.viewPager.setOffscreenPageLimit(4);
    binding.viewPager.setAdapter(pagerAdapter);
    binding.tabLayout.setupWithViewPager(binding.viewPager);

    setBadgeVisible(false);
  }

  public void setBadgeVisible(boolean visible) {
    if (binding.tabLayout == null || getActivity() == null) {
      return;
    }
    for (int i = 0; i < 4; i++) {
      TabLayout.Tab tab = binding.tabLayout.getTabAt(i);
      if (tab != null) {
        View tabItem =
            LayoutInflater.from(getActivity()).inflate(R.layout.tab_item_cast_list_friends, null);

        TextView lblItem = tabItem.findViewById(R.id.lbl_item);
        lblItem.setText(pagerAdapter.getPageTitle(i));
        View badgeDot = tabItem.findViewById(R.id.badge_dot);
        badgeDot.setVisibility(i == 2 && visible ? View.VISIBLE : View.GONE);

        tabItem.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
        tab.setCustomView(null);
        tab.setCustomView(tabItem);
      }
    }
  }

  @Override public void onResume() {
    super.onResume();

    if (binding.tabLayout != null) {
      getFriendRequestsCountBackground();
    }
  }

  private void getFriendRequestsCountBackground() {
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
          setBadgeVisible(count > 0);
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelFriendRequestWrapper> call, @NonNull Throwable t) {
        call.cancel();
      }
    });
  }

  public class AdapterCastList extends FragmentStatePagerAdapter {
    private List<Fragment> myChannelPages;
    private Context context;

    public AdapterCastList(Context context, @NonNull FragmentManager fm) {
      super(fm);
      this.context = context;
      myChannelPages = new ArrayList<>();
      myChannelPages.add(FollowersFragment.newInstance());
      myChannelPages.add(FollowingFragment.newInstance());
      myChannelPages.add(new FriendsFragment());
      myChannelPages.add(new GroupsFragment());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
      return myChannelPages.get(position);
    }

    @Override
    public int getCount() {
      return myChannelPages.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
      switch (position) {
        case 0: {
          return context.getString(R.string.cast_list_tab_followers);
        }
        case 1: {
          return context.getString(R.string.cast_list_tab_following);
        }
        case 2: {
          return context.getString(R.string.cast_list_tab_friends);
        }
        case 3: {
          return context.getString(R.string.cast_list_tab_groups);
        }
        default:
          return "";
      }
    }
  }
}
