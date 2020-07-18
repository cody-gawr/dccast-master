package notq.dccast.screens.home.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.model.ModelListHeader;
import notq.dccast.screens.home.header.HomeHeader;

public class AdapterHomeHeader extends FragmentStatePagerAdapter {
  private List<ModelListHeader> headerItems = new ArrayList<>();

  public AdapterHomeHeader(@NonNull FragmentManager fm) {
    super(fm);
    headerItems = new ArrayList<>();
  }

  public List<ModelListHeader> getHeaderItems() {
    return headerItems;
  }

  public void setHeaderItems(List<ModelListHeader> headerItems) {
    this.headerItems = headerItems;
    notifyDataSetChanged();
  }

  public void updateItem(int position , ModelListHeader video) {
    this.headerItems.set(position, video);
    notifyDataSetChanged();
  }

  @Override
  public int getItemPosition(Object object) {
    return POSITION_NONE;
  }

  @NonNull @Override public Fragment getItem(int position) {
    return HomeHeader.getInstance(headerItems.get(position));
  }

  @Override public int getCount() {
    return headerItems == null ? 0 : headerItems.size();
  }
}