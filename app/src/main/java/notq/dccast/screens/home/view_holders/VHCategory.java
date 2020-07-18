package notq.dccast.screens.home.view_holders;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import com.google.android.material.tabs.TabLayout;
import java.util.List;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.HomeAPIInterface;
import notq.dccast.databinding.VhHomeCategoryBinding;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.ModelVideoWrapper;
import notq.dccast.model.category.CategoryItem;
import notq.dccast.screens.home.adapter.AdapterVideos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VHCategory extends RecyclerView.ViewHolder {
  private VhHomeCategoryBinding binding;
  private List<CategoryItem> categoryItems;

  public VHCategory(Fragment fragment,
      TabLayout.OnTabSelectedListener tabSelectedListener,
      @NonNull VhHomeCategoryBinding binding) {
    super(binding.getRoot());
    this.binding = binding;
    binding.tabLayoutCategories.addOnTabSelectedListener(tabSelectedListener);
  }

  public void setCategories(List<CategoryItem> categoryItems) {
    this.categoryItems = categoryItems;

    for (CategoryItem categoryItem : categoryItems) {
      binding.tabLayoutCategories.addTab(
          binding.tabLayoutCategories.newTab().setText(categoryItem.getName()));
    }
  }

  public List<CategoryItem> getCategoryItems() {
    return categoryItems;
  }

  public void setCurrentPosition(int performClickPosition) {
    if (binding.tabLayoutCategories.getSelectedTabPosition() != performClickPosition) {
      Objects.requireNonNull(binding.tabLayoutCategories.getTabAt(performClickPosition)).select();
    }
  }
}
