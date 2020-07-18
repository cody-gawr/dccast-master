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
import notq.dccast.databinding.VhCastFriendBinding;
import notq.dccast.databinding.VhHomeLoadMoreBinding;
import notq.dccast.model.friends.ModelFriendRequest;
import notq.dccast.screens.navigation_menu.cast.interfaces.FriendsListener;
import notq.dccast.util.Util;

public class AdapterFriends
    extends RecyclerView.Adapter {

  private static final int VIEW_TYPE_ITEM = 4;
  private static final int VIEW_TYPE_LOAD_MORE = 5;
  private List<ModelFriendRequest> friends = new ArrayList<>();
  private Context context;
  private FriendsListener adapterListener;
  private VHLoadMore loadMore;
  private boolean isNeedShowLoader = true;

  public AdapterFriends(Context context, FriendsListener adapterListener) {
    this.context = context;
    this.adapterListener = adapterListener;
    friends = new ArrayList<>();
  }

  public void setFriends(List<ModelFriendRequest> friends) {
    this.friends = friends;
    notifyDataSetChanged();
  }

  public int getFriendsCount() {
    return friends == null ? 0 : friends.size();
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

  public void removeFriends() {
    if (!friends.isEmpty()) {
      notifyItemRangeRemoved(0, friends.size());
      friends.clear();
    }
  }

  public void removeFriends(int position) {
    if (!friends.isEmpty()) {
      friends.remove(position);
      notifyItemRemoved(position);
      notifyItemRangeChanged(position, friends.size());
    }
  }

  public void addFriend(ModelFriendRequest item) {
    friends.add(item);
    notifyItemInserted(getItemCount() - 1);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    switch (viewType) {
      case VIEW_TYPE_ITEM: {
        VhCastFriendBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.vh_cast_friend, parent, false);
        return new ViewHolderCastFriend(binding);
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
        ((ViewHolderCastFriend) holder).setData(friends.get(position), position);
        break;
      }

      case VIEW_TYPE_LOAD_MORE: {
        ((VHLoadMore) holder).notifyLoaderStatusChanged(isNeedShowLoader);
        break;
      }
    }
  }

  public ModelFriendRequest getItem(int position) {
    return friends.get(position);
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
    return friends.size() + 1;
  }

  class ViewHolderCastFriend extends RecyclerView.ViewHolder implements View.OnClickListener {
    private VhCastFriendBinding binding;
    private int position;
    private ModelFriendRequest modelUser;

    ViewHolderCastFriend(@NonNull VhCastFriendBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(ModelFriendRequest item, int position) {
      this.position = position;
      this.modelUser = item;
      binding.layoutItem.setOnClickListener(this);

      binding.layoutLoad.setVisibility(View.GONE);
      displayUser();
    }

    void displayUser() {
      binding.lblUsername.setText(modelUser.getUser().getNickName());
      if (modelUser.getUser().getStateMessage() != null) {
        binding.lblStatus.setText(modelUser.getUser().getStateMessage());
        binding.lblStatus.setVisibility(View.VISIBLE);
      } else {
        binding.lblStatus.setText("");
        binding.lblStatus.setVisibility(View.GONE);
      }

      Glide.with(context).load(Util.getValidateUrl(modelUser.getUser().getProfileImage()))
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
