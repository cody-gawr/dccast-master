package notq.dccast.screens.navigation_menu.notification;

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
import notq.dccast.api.notification.NotificationAPIInterface;
import notq.dccast.databinding.ActivityNotificationBinding;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelNotificationNew;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.notification.ModelNotification;
import notq.dccast.model.user.ModelResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.home.interfaces.HomeChildFragmentListener;
import notq.dccast.screens.navigation_menu.notification.fragments.FragmentNotificationLive;
import notq.dccast.screens.navigation_menu.notification.fragments.FragmentNotificationNotice;
import notq.dccast.screens.navigation_menu.notification.fragments.FragmentNotificationVOD;
import notq.dccast.util.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityNotification extends BaseActivity implements HomeChildFragmentListener {

  private Context mContext = this;
  private ActivityNotificationBinding binding;

  private ArrayList<ModelNotification> notifications = new ArrayList<>();
  private AdapterNotification pagerAdapter;
  private NotificationAPIInterface apiInterface;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);

    initToolbar();
    init();
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_notification));
    binding.header.backButton.setOnClickListener(v -> {
      onBackPressed();
    });
  }

  private void init() {
    apiInterface = APIClient.getClient().create(NotificationAPIInterface.class);
    pagerAdapter = new AdapterNotification(mContext, getSupportFragmentManager());
    binding.viewPager.setOffscreenPageLimit(3);
    binding.viewPager.setAdapter(pagerAdapter);
    binding.tabLayout.setupWithViewPager(binding.viewPager);

    binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override public void onTabSelected(TabLayout.Tab tab) {
        updateBadgeShowed(tab.getPosition());
      }

      @Override public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  public void updateBadgeShowed(int position) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    String category = "notice";
    if (position == 1) {
      category = "live";
    } else if (position == 2) {
      category = "vod";
    }
    Call<ModelResult> call =
        apiInterface.setBadgeRead(loginUser.getId(), category);

    call.enqueue(new Callback<ModelResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelResult> call,
          @NonNull Response<ModelResult> response) {
        ModelResult result = response.body();
        if (result != null && result.isResult()) {
          toggleBadge(position, false);
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelResult> call, @NonNull Throwable t) {
        call.cancel();
      }
    });
  }

  public void toggleBadge(int position, boolean show) {
    TabLayout.Tab tab = binding.tabLayout.getTabAt(position);
    if (tab != null) {
      View tabItem = LayoutInflater.from(this).inflate(R.layout.notification_tab_item, null);

      View badgeText = tabItem.findViewById(R.id.lbl_new);
      badgeText.setVisibility(show ? View.VISIBLE : View.GONE);

      TextView lblTabItem = tabItem.findViewById(R.id.lbl_tab_item);
      lblTabItem.setText(pagerAdapter.getPageTitle(position));

      tabItem.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
          ViewGroup.LayoutParams.MATCH_PARENT));
      tab.setCustomView(null);
      tab.setCustomView(tabItem);
    }
  }

  @Override protected void onResume() {
    super.onResume();

    if (apiInterface != null) {
      getNewBadge();
    }
  }

  private void getNewBadge() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    Call<ModelNotificationNew> call =
        apiInterface.getBadgeNew(loginUser.getId());

    call.enqueue(new Callback<ModelNotificationNew>() {
      @Override
      public void onResponse(@NonNull Call<ModelNotificationNew> call,
          @NonNull Response<ModelNotificationNew> response) {
        ModelNotificationNew result = response.body();
        if (result != null) {
          toggleBadge(0, result.isNotice());
          toggleBadge(1, result.isLive());
          toggleBadge(2, result.isVod());
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelNotificationNew> call, @NonNull Throwable t) {
        call.cancel();
      }
    });
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @Override public void fragmentCollapsed() {
    if (getSupportActionBar() != null) {
      getSupportActionBar().show();
    }

    binding.tabLayout.setVisibility(View.VISIBLE);
  }

  @Override public void fragmentExpanded() {
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }

    binding.tabLayout.setVisibility(View.GONE);
  }

  @Override public void fragmentClosed() {
    if (getSupportActionBar() != null) {
      getSupportActionBar().show();
    }

    binding.tabLayout.setVisibility(View.VISIBLE);
  }

  @Override public void onLikeDislikeUpdated(ModelVideo video) {

  }

  @Override public void onLikeDislikeUpdated(ModelListHeader header) {

  }

  @Override public void onLeftMenuTabChanged(int tabPosition) {

  }

  @Override public void onMediaDeleted(ModelVideo modelVideo) {

  }

  @Override public void onMediaRemovedFromFavorite(ModelVideo modelVideo) {

  }

  class AdapterNotification extends FragmentStatePagerAdapter {
    private List<Fragment> myChannelPages;
    private Context context;

    public AdapterNotification(Context context, @NonNull FragmentManager fm) {
      super(fm);
      this.context = context;
      myChannelPages = new ArrayList<>();
      myChannelPages.add(new FragmentNotificationNotice());
      myChannelPages.add(new FragmentNotificationLive());
      myChannelPages.add(new FragmentNotificationVOD());
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
          return context.getString(R.string.notification_tab_notice);
        }
        case 1: {
          return context.getString(R.string.notification_tab_live);
        }
        case 2: {
          return context.getString(R.string.notification_tab_vod);
        }
        default:
          return "";
      }
    }
  }
}
