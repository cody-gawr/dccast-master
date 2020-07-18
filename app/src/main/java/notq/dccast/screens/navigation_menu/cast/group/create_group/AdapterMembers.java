package notq.dccast.screens.navigation_menu.cast.group.create_group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.my_content.MyContentAPIInterface;
import notq.dccast.databinding.VhGroupMemberBinding;
import notq.dccast.databinding.VhHomeLoadMoreBinding;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.profile.ModelUserProfileWrapper;
import notq.dccast.screens.navigation_menu.cast.interfaces.MemberListener;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterMembers
    extends RecyclerView.Adapter {

  private static final int VIEW_TYPE_ITEM = 4;
  private static final int VIEW_TYPE_LOAD_MORE = 5;
  private List<ModelUser> members;
  private Context context;
  private MemberListener adapterListener;

  private VHLoadMore loadMore;
  private boolean isNeedShowLoader = true;

  public AdapterMembers(Context context, MemberListener adapterListener) {
    this.context = context;
    this.adapterListener = adapterListener;
    members = new ArrayList<>();
  }

  public void setNeedShowLoader(boolean needShowLoader) {
    isNeedShowLoader = needShowLoader;
  }

  public List<ModelUser> getMembers() {
    return members;
  }

  public void setMembers(List<ModelUser> members) {
    this.members = members;
    notifyDataSetChanged();
  }

  public int getMembersCount() {
    return members == null ? 0 : members.size();
  }

  public void removeMembers() {
    if (!members.isEmpty()) {
      notifyItemRangeRemoved(0, members.size());
      members.clear();
    }
  }

  public void removeMember(int position) {
    if (!members.isEmpty()) {
      members.remove(position);
      notifyItemRemoved(position);
      notifyItemRangeChanged(position, members.size());
    }
  }

  public boolean containsId(int memberId) {
    if (members != null) {
      for (ModelUser member : members) {
        if (member.getId() == memberId) {
          return true;
        }
      }
    }
    return false;
  }

  public void addMember(ModelUser item) {
    members.add(item);
    notifyItemInserted(getItemCount() - 1);
  }

  public void updateMember(int position, ModelUser item) {
    members.set(position, item);
    notifyItemChanged(position);
  }

  public void showLoadMoreLoader() {
    if (loadMore != null) {
      isNeedShowLoader = true;
      notifyItemChanged(getItemCount() - 1);
    }
  }

  public void hideLoadMoreLoader() {
    if (loadMore != null) {
      isNeedShowLoader = false;
      notifyItemChanged(getItemCount() - 1);
    }
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    switch (viewType) {
      case VIEW_TYPE_ITEM: {
        VhGroupMemberBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.vh_group_member, parent, false);
        return new ViewHolderCastMember(binding);
      }
      case VIEW_TYPE_LOAD_MORE: {
        VhHomeLoadMoreBinding loadMoreBinding =
            DataBindingUtil.inflate(inflater, R.layout.vh_home_load_more, parent, false);
        loadMore = new VHLoadMore(loadMoreBinding);

        return loadMore;
      }
    }

    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    switch (getItemViewType(position)) {
      case VIEW_TYPE_ITEM: {
        ((ViewHolderCastMember) holder).setData(members.get(position), position);
        break;
      }

      case VIEW_TYPE_LOAD_MORE: {
        ((VHLoadMore) holder).notifyLoaderStatusChanged(isNeedShowLoader);
        break;
      }
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (position == getItemCount() - 1) {
      return VIEW_TYPE_LOAD_MORE;
    } else {
      return VIEW_TYPE_ITEM;
    }
  }

  public ModelUser getItem(int position) {
    return members.get(position);
  }

  @Override
  public int getItemCount() {
    return members.size() + 1;
  }

  class ViewHolderCastMember extends RecyclerView.ViewHolder implements View.OnClickListener {
    private VhGroupMemberBinding binding;
    private int position;
    private ModelUser item;

    ViewHolderCastMember(@NonNull VhGroupMemberBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(ModelUser profileItem, int position) {
      this.position = position;
      this.item = profileItem;
      binding.layoutItem.setOnClickListener(this);

      if (item.getUser() == null) {
        binding.layoutLoad.setVisibility(View.VISIBLE);

        Call<ModelUserProfileWrapper> call =
            APIClient.getClient().create(MyContentAPIInterface.class).getProfile(item.getId());

        call.enqueue(new Callback<ModelUserProfileWrapper>() {
          @Override
          public void onResponse(@NonNull Call<ModelUserProfileWrapper> call,
              @NonNull Response<ModelUserProfileWrapper> response) {
            ModelUserProfileWrapper profile = response.body();

            binding.layoutLoad.setVisibility(View.GONE);

            if (profile == null || profile.users == null || profile.users.size() == 0) {
              return;
            }

            ModelUser user = profile.users.get(0);
            item = user;
            updateMember(position, user);
          }

          @Override
          public void onFailure(@NonNull Call<ModelUserProfileWrapper> call, @NonNull Throwable t) {
            call.cancel();

            binding.layoutLoad.setVisibility(View.GONE);
          }
        });
      } else {
        binding.layoutLoad.setVisibility(View.GONE);

        displayUser();
      }
    }

    void displayUser() {
      binding.lblName.setText(this.item.getNickName());
      Glide.with(context).load(Util.getValidateUrl(item.getProfileImage()))
          .placeholder(R.drawable.ic_notification_placeholder).into(binding.ivUser);
    }

    @Override
    public void onClick(View view) {
      switch (view.getId()) {
        case R.id.layout_item: {
          adapterListener.onItemSelected(position);
          break;
        }
      }
    }
  }

  class VHLoadMore extends RecyclerView.ViewHolder {
    private VhHomeLoadMoreBinding binding;

    VHLoadMore(VhHomeLoadMoreBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void notifyLoaderStatusChanged(boolean isNeedShowLoadMore) {
      binding.progressBar.setVisibility(isNeedShowLoadMore ? View.VISIBLE : View.GONE);
    }
  }
}
