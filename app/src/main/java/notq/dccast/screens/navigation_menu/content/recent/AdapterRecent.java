package notq.dccast.screens.navigation_menu.content.recent;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.R;
import notq.dccast.util.Constants;

public class AdapterRecent extends FragmentStatePagerAdapter {
  public List<RecentFragment> myChannelPages;
  private Context context;

  AdapterRecent(Context context, @NonNull FragmentManager fm) {
    super(fm);
    this.context = context;
    myChannelPages = new ArrayList<>();
    myChannelPages.add(RecentFragment.newInstance(Constants.DIVISION_LIVE));
    myChannelPages.add(RecentFragment.newInstance(Constants.DIVISION_VOD));
  }

  public List<RecentFragment> getMyChannelPages() {
    return myChannelPages;
  }

  @NonNull
  @Override
  public RecentFragment getItem(int position) {
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
        return context.getString(R.string.recent_tab_live);
      }
      case 1: {
        return context.getString(R.string.recent_tab_vod);
      }
      default:
        return "";
    }
  }
}
