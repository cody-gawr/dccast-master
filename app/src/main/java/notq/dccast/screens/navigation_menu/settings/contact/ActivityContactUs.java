package notq.dccast.screens.navigation_menu.settings.contact;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.R;
import notq.dccast.databinding.ActivityContactUsBinding;
import notq.dccast.screens.BaseActivity;

public class ActivityContactUs extends BaseActivity {

  private ActivityContactUsBinding binding;
  private AdapterContactPager pagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us);

    initToolbar();
    initViewPager();
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.settings_information));
    binding.header.backButton.setOnClickListener(v -> {
      finish();
    });
  }

  private void initViewPager() {
    pagerAdapter = new AdapterContactPager(getSupportFragmentManager());
    binding.viewPager.setOffscreenPageLimit(2);
    binding.viewPager.setAdapter(pagerAdapter);
    binding.tabLayout.setupWithViewPager(binding.viewPager);
  }

  class AdapterContactPager extends FragmentStatePagerAdapter {
    public List<Fragment> contactPages;

    public AdapterContactPager(@NonNull FragmentManager fm) {
      super(fm);
      contactPages = new ArrayList<>();
      contactPages.add(new ContactFragment());
      contactPages.add(new ContactListFragment());
    }

    public List<Fragment> getContactPages() {
      return contactPages;
    }

    @NonNull @Override public Fragment getItem(int position) {
      return contactPages.get(position);
    }

    @Override public int getCount() {
      return contactPages.size();
    }

    @Nullable @Override public CharSequence getPageTitle(int position) {
      return position == 0 ? getString(R.string.tab_contact_us)
          : getString(R.string.tab_inquery_list);
    }
  }
}
