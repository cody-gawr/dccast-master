package notq.dccast.screens.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.databinding.VhNavCategoryBinding;
import notq.dccast.databinding.VhNavMenu2Binding;
import notq.dccast.databinding.VhNavMenuBinding;
import notq.dccast.databinding.VhNavProfileBinding;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.interfaces.HomeChildFragmentListener;
import notq.dccast.screens.home.mandu.ManduService;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;

public class AdapterNavigationDrawer extends RecyclerView.Adapter {
  private static final int SECTIONS = 4; //1:profile, 2:menus, 3:menus #2, 4:categories
  public ViewHolderCategory category;
  private Context context;
  private int selectedPosition;

  public AdapterNavigationDrawer(Context context) {
    this.context = context;
  }

  public void setSelection(int selection) {
    this.selectedPosition = selection;

    if (selectedPosition == 0 || selectedPosition == 1 || selectedPosition == 3) {
      notifyItemChanged(1);
    } else if (selectedPosition == 4 || selectedPosition == 5) {
      notifyItemChanged(2);
    }
  }

  @NonNull
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
    LayoutInflater inflater = LayoutInflater.from(context);
    VhNavProfileBinding profileBinding;
    VhNavMenuBinding menuBinding;
    VhNavMenu2Binding menu2Binding;
    VhNavCategoryBinding categoryBinding;

    switch (getItemViewType(position)) {
      case 0: {
        profileBinding =
            DataBindingUtil.inflate(inflater, R.layout.vh_nav_profile, viewGroup, false);
        return new ViewHolderProfile(profileBinding);
      }

      case 1: {
        menuBinding = DataBindingUtil.inflate(inflater, R.layout.vh_nav_menu, viewGroup, false);
        return new ViewHolderMenu(menuBinding);
      }

      case 2: {
        menu2Binding = DataBindingUtil.inflate(inflater, R.layout.vh_nav_menu_2, viewGroup, false);
        return new ViewHolderMenu2(menu2Binding);
      }

      case 3: {
        categoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.vh_nav_category, viewGroup, false);
        category = new ViewHolderCategory(categoryBinding);
        return category;
      }

      default:
        //noinspection ConstantConditions
        return null;
    }
  }

  @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
    switch (getItemViewType(i)) {
      case 0: {
        ((ViewHolderProfile) viewHolder).reload();
        break;
      }
      case 1: {
        ((ViewHolderMenu) viewHolder).reload();
        break;
      }

      case 2: {
        ((ViewHolderMenu2) viewHolder).reload();
        break;
      }

      case 3: {
        ((ViewHolderCategory) viewHolder).reload();
        break;
      }
    }
  }

  @Override public int getItemCount() {
    return SECTIONS;
  }

  @Override public int getItemViewType(int position) {
    return position;
  }

  public class ViewHolderProfile extends RecyclerView.ViewHolder {
    private VhNavProfileBinding profileBinding;

    ViewHolderProfile(VhNavProfileBinding profileBinding) {
      super(profileBinding.getRoot());
      this.profileBinding = profileBinding;
      profileBinding.lblUsername.setOnClickListener((View.OnClickListener) context);
      profileBinding.ivProfileImage.setOnClickListener((View.OnClickListener) context);
      profileBinding.layoutMandu.setOnClickListener((View.OnClickListener) context);
      profileBinding.btnSettings.setOnClickListener((View.OnClickListener) context);
      profileBinding.btnLoginOrNotification.setOnClickListener((View.OnClickListener) context);
    }

    void reload() {
      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser != null) {
        profileBinding.profileInfo.setVisibility(View.VISIBLE);
        profileBinding.imgLoginOrNotification.setImageResource(R.drawable.ic_notification);
        profileBinding.lblUsername.setText(loginUser.getNickName());
        profileBinding.lblUsername.setPaintFlags(
            profileBinding.lblUsername.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        int width =
            context.getResources().getDimensionPixelSize(R.dimen.left_menu_profile_image_size);

        Glide.with(context)
            .load(Util.getValidateUrl(loginUser.getProfileImage()))
            .apply(
                new RequestOptions()
                    .override(width, width)
                    .placeholder(R.drawable.ic_profile_placeholder)
                    .centerCrop())
            .into(profileBinding.ivProfileImage);

        if (DCCastApplication.userId != null && DCCastApplication.appId != null) {
          profileBinding.manduLoading.setVisibility(View.VISIBLE);
          ManduService service = new ManduService(context, new ManduService.ManduCallback() {
            @Override public void onError(String error) {
              profileBinding.manduLoading.setVisibility(View.GONE);
              profileBinding.lblManduCount.setText("0");
              if (error != null) {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show();
              }
            }

            @Override public void onComplete(double mandu) {
              profileBinding.manduLoading.setVisibility(View.GONE);
              profileBinding.lblManduCount.setText(Util.getFormattedNumber(mandu));
            }
          });
          service.getUserMandu();
        } else {
          profileBinding.manduLoading.setVisibility(View.GONE);
        }
      } else {
        profileBinding.profileInfo.setVisibility(View.GONE);
        profileBinding.imgLoginOrNotification.setImageResource(R.drawable.ic_profile_placeholder);
      }
    }
  }

  public class ViewHolderMenu extends RecyclerView.ViewHolder {
    private VhNavMenuBinding menuBinding;

    ViewHolderMenu(VhNavMenuBinding menuBinding) {
      super(menuBinding.getRoot());
      this.menuBinding = menuBinding;
      menuBinding.itemHome.setOnClickListener((View.OnClickListener) context);
      menuBinding.itemMyContent.setOnClickListener((View.OnClickListener) context);
      menuBinding.itemFavorite.setOnClickListener((View.OnClickListener) context);
      menuBinding.itemCast.setOnClickListener((View.OnClickListener) context);
    }

    void reload() {
      menuBinding.imgHome.setVisibility(View.VISIBLE);
      menuBinding.imgHomeSelected.setVisibility(View.GONE);
      menuBinding.imgMyContent.setVisibility(View.VISIBLE);
      menuBinding.imgMyContentSelected.setVisibility(View.GONE);
      menuBinding.imgFavorite.setVisibility(View.VISIBLE);
      menuBinding.imgFavoriteSelected.setVisibility(View.GONE);
      menuBinding.imgCast.setVisibility(View.VISIBLE);
      menuBinding.imgCastSelected.setVisibility(View.GONE);

      menuBinding.homeText.setTextColor(Color.parseColor("#383A40"));
      menuBinding.myContentText.setTextColor(Color.parseColor("#383A40"));
      menuBinding.favoriteText.setTextColor(Color.parseColor("#383A40"));
      menuBinding.castListText.setTextColor(Color.parseColor("#383A40"));

      switch (selectedPosition) {
        case 0: {
          menuBinding.imgHome.setVisibility(View.GONE);
          menuBinding.imgHomeSelected.setVisibility(View.VISIBLE);
          menuBinding.homeText.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
          break;
        }

        case 1: {
          menuBinding.imgMyContent.setVisibility(View.GONE);
          menuBinding.imgMyContentSelected.setVisibility(View.VISIBLE);
          menuBinding.myContentText.setTextColor(
              ContextCompat.getColor(context, R.color.colorPrimary));

          break;
        }

        case 2: {
          menuBinding.imgFavorite.setVisibility(View.GONE);
          menuBinding.imgFavoriteSelected.setVisibility(View.VISIBLE);
          menuBinding.favoriteText.setTextColor(
              ContextCompat.getColor(context, R.color.colorPrimary));

          break;
        }

        case 3: {
          menuBinding.imgCast.setVisibility(View.GONE);
          menuBinding.imgCastSelected.setVisibility(View.VISIBLE);
          menuBinding.castListText.setTextColor(
              ContextCompat.getColor(context, R.color.colorPrimary));

          break;
        }
      }
    }
  }

  public class ViewHolderMenu2 extends RecyclerView.ViewHolder {
    private VhNavMenu2Binding menu2Binding;

    ViewHolderMenu2(@NonNull VhNavMenu2Binding menu2Binding) {
      super(menu2Binding.getRoot());
      this.menu2Binding = menu2Binding;

      menu2Binding.itemLive.setOnClickListener((View.OnClickListener) context);
      menu2Binding.itemVod.setOnClickListener((View.OnClickListener) context);
    }

    void reload() {
      menu2Binding.itemLiveContainer.setBackgroundColor(Color.WHITE);
      menu2Binding.itemVodContainer.setBackgroundColor(Color.WHITE);

      switch (selectedPosition) {
        case 4: {
          menu2Binding.itemLiveContainer.setBackgroundColor(
              ContextCompat.getColor(context, R.color.colorSelected));
          menu2Binding.itemVodContainer.setBackgroundColor(Color.WHITE);

          break;
        }

        case 5: {
          menu2Binding.itemLiveContainer.setBackgroundColor(Color.WHITE);
          menu2Binding.itemVodContainer.setBackgroundColor(
              ContextCompat.getColor(context, R.color.colorSelected));

          break;
        }
      }
    }
  }

  public class ViewHolderCategory extends RecyclerView.ViewHolder {
    private VhNavCategoryBinding categoryBinding;

    ViewHolderCategory(VhNavCategoryBinding categoryBinding) {
      super(categoryBinding.getRoot());
      this.categoryBinding = categoryBinding;
      init();
    }

    private void init() {
      GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
      gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
      categoryBinding.categoryRv.setHasFixedSize(true);
      categoryBinding.categoryRv.setLayoutManager(gridLayoutManager);
      categoryBinding.categoryRv.setAdapter(new AdapterNavigationCategory(context));
      categoryBinding.tabLayout.getTabAt(1).select();
      categoryBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override public void onTabSelected(TabLayout.Tab tab) {
          ((HomeChildFragmentListener) context).onLeftMenuTabChanged(tab.getPosition());
        }

        @Override public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override public void onTabReselected(TabLayout.Tab tab) {

        }
      });
    }

    public void selectTab(int position) {
      if (position != categoryBinding.tabLayout.getSelectedTabPosition()) {
        categoryBinding.tabLayout.getTabAt(position).select();
        notifyItemChanged(position);
      }
    }

    void reload() {

    }
  }
}