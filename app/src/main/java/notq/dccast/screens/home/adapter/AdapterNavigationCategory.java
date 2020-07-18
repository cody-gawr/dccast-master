package notq.dccast.screens.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.databinding.VhNavCategoryItemBinding;
import notq.dccast.model.category.CategoryItem;

public class AdapterNavigationCategory
    extends RecyclerView.Adapter<AdapterNavigationCategory.ViewHolderCategory> {
  private Context context;

  public AdapterNavigationCategory(Context context) {
    this.context = context;
  }

  @NonNull @Override
  public ViewHolderCategory onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    VhNavCategoryItemBinding binding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.vh_nav_category_item,
            viewGroup, false);
    return new ViewHolderCategory(binding);
  }

  @Override public void onBindViewHolder(@NonNull ViewHolderCategory viewHolderCategory, int i) {
    viewHolderCategory.binding.setCategory(DCCastApplication.listCategoryItems.get(i));
    viewHolderCategory.setData(i);
    View rootView = viewHolderCategory.binding.getRoot();

    rootView.setTag("category" + i);
    rootView.setOnClickListener((View.OnClickListener) context);
  }

  @Override public int getItemCount() {
    return DCCastApplication.listCategoryItems.size();
  }

  class ViewHolderCategory extends RecyclerView.ViewHolder {
    VhNavCategoryItemBinding binding;

    ViewHolderCategory(VhNavCategoryItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(int position) {
      CategoryItem categoryItem = DCCastApplication.listCategoryItems.get(position);
      switch (categoryItem.id) {
        case 15: {
          //sport
          binding.categoryImg.setImageResource(R.drawable.ic_category_sport);
          break;
        }
        case 6: {
          //travel
          binding.categoryImg.setImageResource(R.drawable.ic_category_travel);
          break;
        }
        case 5: {
          //stock
          binding.categoryImg.setImageResource(R.drawable.ic_category_stock);
          break;
        }
        case 4: {
          //beauty
          binding.categoryImg.setImageResource(R.drawable.ic_category_beauty);
          break;
        }
        case 3: {
          //food
          binding.categoryImg.setImageResource(R.drawable.ic_category_food);
          break;
        }
        case 2: {
          //entertainment
          binding.categoryImg.setImageResource(R.drawable.ic_category_entertainment);
          break;
        }

        case 1: {
          //game
          binding.categoryImg.setImageResource(R.drawable.ic_category_game);
          break;
        }
        default: {
          binding.categoryImg.setImageResource(R.drawable.ic_category_food);
          break;
        }
      }
    }
  }
}
