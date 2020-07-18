package notq.dccast.screens.home.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.screens.home.search.SearchResultPage;

public class AdapterSearchPager extends FragmentStatePagerAdapter {
  public List<SearchResultPage> searchResultPages;

  public AdapterSearchPager(@NonNull FragmentManager fm) {
    super(fm);
    searchResultPages = new ArrayList<>();
    searchResultPages.add(new SearchResultPage());
    searchResultPages.add(new SearchResultPage());
  }

  public List<SearchResultPage> getSearchResultPages() {
    return searchResultPages;
  }

  @NonNull @Override public Fragment getItem(int position) {
    return searchResultPages.get(position);
  }

  @Override public int getCount() {
    return searchResultPages.size();
  }

  @Nullable @Override public CharSequence getPageTitle(int position) {
    return position == 0 ? "VOD" : "Live";
  }
}
