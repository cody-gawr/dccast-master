package notq.dccast.screens.navigation_menu.cast.group;

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
import notq.dccast.databinding.VhCastGroupBinding;
import notq.dccast.databinding.VhHomeLoadMoreBinding;
import notq.dccast.model.group.ModelGroup;
import notq.dccast.screens.navigation_menu.cast.interfaces.FriendsListener;
import notq.dccast.util.Util;

public class AdapterGroups
    extends RecyclerView.Adapter {

  private static final int VIEW_TYPE_ITEM = 4;
  private static final int VIEW_TYPE_LOAD_MORE = 5;
  private List<ModelGroup> groups = new ArrayList<>();
  private Context context;
  private FriendsListener adapterListener;

  private VHLoadMore loadMore;
  private boolean isNeedShowLoader = true;

  public AdapterGroups(Context context, FriendsListener adapterListener) {
    this.context = context;
    this.adapterListener = adapterListener;
    groups = new ArrayList<>();
  }

  public void setGroups(List<ModelGroup> listGroups) {
    this.groups = listGroups;
    notifyDataSetChanged();
  }

  public int getGroupsCount() {
    return groups == null ? 0 : groups.size();
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

  public void removeGroups() {
    if (!groups.isEmpty()) {
      notifyItemRangeRemoved(0, groups.size());
      groups.clear();
    }
  }

  public void removeGroup(int position) {
    if(!groups.isEmpty()) {
      groups.remove(position);
      notifyDataSetChanged();
    }
  }

  public void addGroup(ModelGroup item) {
    groups.add(item);
    notifyItemInserted(getItemCount() - 1);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    switch (viewType) {
      case VIEW_TYPE_ITEM: {
        VhCastGroupBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.vh_cast_group, parent, false);
        return new ViewHolderCastGroup(binding);
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
        ((ViewHolderCastGroup) holder).setData(groups.get(position), position);
        break;
      }

      case VIEW_TYPE_LOAD_MORE: {
        ((VHLoadMore) holder).notifyLoaderStatusChanged(isNeedShowLoader);
        break;
      }
    }
  }

  public ModelGroup getItem(int position) {
    return groups.get(position);
  }

  @Override
  public int getItemViewType(int position) {
    if (position == getItemCount() - 1) {
      return VIEW_TYPE_LOAD_MORE;
    } else {
      return VIEW_TYPE_ITEM;
    }
  }

  @Override
  public int getItemCount() {
    return groups.size() + 1;
  }

  class ViewHolderCastGroup extends RecyclerView.ViewHolder implements View.OnClickListener {
    private VhCastGroupBinding binding;
    private int position;
    private ModelGroup modelGroup;

    ViewHolderCastGroup(@NonNull VhCastGroupBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(ModelGroup item, int position) {
      this.position = position;
      this.modelGroup = item;

      String groupName = modelGroup.getName();

      if (modelGroup.getMembers() != null) {
        groupName += "( " + modelGroup.getMembers().size() + ")";
      }

      binding.lblGroupName.setText(groupName);
      binding.layoutItem.setOnClickListener(this);

      if (modelGroup.getMessage() != null) {
        binding.lblGroupDescription.setText(modelGroup.getMessage());
        binding.lblGroupDescription.setVisibility(View.VISIBLE);
      } else {
        binding.lblGroupDescription.setVisibility(View.GONE);
      }

      Glide.with(context).load(Util.getPhotoUrl(modelGroup.getProfileImg()))
          .placeholder(R.drawable.ic_notification_placeholder).into(binding.ivGroup);
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
