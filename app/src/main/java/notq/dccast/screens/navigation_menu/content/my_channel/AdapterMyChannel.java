package notq.dccast.screens.navigation_menu.content.my_channel;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.R;

public class AdapterMyChannel extends FragmentStatePagerAdapter {
  public List<MyChannelFragment> myChannelPages;
  private Context context;

  public AdapterMyChannel(Context context, int userId, @NonNull FragmentManager fm) {
    super(fm);
    this.context = context;
    myChannelPages = new ArrayList<>();
    myChannelPages.add(MyChannelFragment.newInstance(userId));
  }

  public List<MyChannelFragment> getMyChannelPages() {
    return myChannelPages;
  }

  @NonNull
  @Override
  public MyChannelFragment getItem(int position) {
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
        return context.getString(R.string.my_channel_tab_live);
      }
      case 1: {
        return context.getString(R.string.my_channel_tab_vod);
      }
      default:
        return "";
    }
  }
}
