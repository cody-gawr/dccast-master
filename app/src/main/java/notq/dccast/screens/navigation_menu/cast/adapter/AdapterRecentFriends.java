package notq.dccast.screens.navigation_menu.cast.adapter;

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
import notq.dccast.databinding.VhCastRecentFollowerBinding;
import notq.dccast.databinding.VhHomeLoadMoreBinding;
import notq.dccast.model.friends.ModelFriendRequest;
import notq.dccast.screens.navigation_menu.cast.interfaces.FollowersListener;
import notq.dccast.util.Util;

public class AdapterRecentFriends
    extends RecyclerView.Adapter {

  private static final int VIEW_TYPE_ITEM = 4;
  private static final int VIEW_TYPE_LOAD_MORE = 5;
  private List<ModelFriendRequest> recent = new ArrayList<>();
  private Context context;
  private FollowersListener adapterListener;
  private VHLoadMore loadMore;
  private boolean isNeedShowLoader = true;

  public AdapterRecentFriends(Context context, FollowersListener adapterListener) {
    this.context = context;
    this.adapterListener = adapterListener;
    recent = new ArrayList<>();
  }

  public void setRecent(List<ModelFriendRequest> notificationNotices) {
    this.recent = notificationNotices;
    notifyDataSetChanged();
  }

  public int getRecentCount() {
    return recent == null ? 0 : recent.size();
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

  public void removeRecent() {
    if (!recent.isEmpty()) {
      notifyItemRangeRemoved(0, recent.size());
      recent.clear();
    }
  }

  public void removeRecent(int position) {
    if (!recent.isEmpty()) {
      recent.remove(position);
      notifyItemRemoved(position);
      notifyItemRangeChanged(position, recent.size());
    }
  }

  public void removeRecent(ModelFriendRequest modelUser) {
    if (!recent.isEmpty()) {
      int position = -1;
      for (int i = 0; i < recent.size(); i++) {
        if (recent.get(i).getId() == modelUser.getId()) {
          position = i;
          break;
        }
      }

      if (position >= 0) {
        removeRecent(position);
      }
    }
  }

  public void addRecent(ModelFriendRequest item) {
    recent.add(item);
    notifyItemInserted(getItemCount() - 1);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    switch (viewType) {
      case VIEW_TYPE_ITEM: {
        VhCastRecentFollowerBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.vh_cast_recent_follower, parent, false);
        return new ViewHolderCastRecentFollower(binding);
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
        ((ViewHolderCastRecentFollower) holder).setData(recent.get(position), position);
        break;
      }

      case VIEW_TYPE_LOAD_MORE: {
        ((VHLoadMore) holder).notifyLoaderStatusChanged(isNeedShowLoader);
        break;
      }
    }
  }

  public ModelFriendRequest getItem(int position) {
    return recent.get(position);
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
    return recent.size() + 1;
  }

  class ViewHolderCastRecentFollower extends RecyclerView.ViewHolder
      implements View.OnClickListener {
    private VhCastRecentFollowerBinding binding;
    private int position;
    private ModelFriendRequest modelUser;

    ViewHolderCastRecentFollower(@NonNull VhCastRecentFollowerBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(ModelFriendRequest item, int position) {
      this.position = position;
      this.modelUser = item;

      this.binding.lblFollow.setOnClickListener(this);
      binding.layoutItem.setOnClickListener(this);

      binding.layoutLoad.setVisibility(View.GONE);
      displayUser();
    }

    void displayUser() {
      binding.lblUsername.setText(modelUser.getUser().getNickName());
      if (modelUser.getUser().getStateMessage() != null) {
        binding.lblItemSubscribeDescription.setText(modelUser.getUser().getStateMessage());
        binding.lblItemSubscribeDescription.setVisibility(View.VISIBLE);
      } else {
        binding.lblItemSubscribeDescription.setText("");
        binding.lblItemSubscribeDescription.setVisibility(View.GONE);
      }
      Glide.with(context).load(Util.getValidateUrl(modelUser.getUser().getProfileImage()))
          .placeholder(R.drawable.ic_notification_placeholder).into(binding.ivUser);

      binding.lblFollow.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
      switch (view.getId()) {
        case R.id.layout_item: {
          adapterListener.onItemSelected(position);
          break;
        }
        case R.id.lbl_follow: {
          adapterListener.onFollow(position);
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
