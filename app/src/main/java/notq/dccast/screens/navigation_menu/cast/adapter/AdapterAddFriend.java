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
import notq.dccast.databinding.VhCastAddFriendBinding;
import notq.dccast.databinding.VhHomeLoadMoreBinding;
import notq.dccast.model.friends.ModelFriendRequest;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.navigation_menu.cast.interfaces.AddFriendListener;
import notq.dccast.util.Util;

public class AdapterAddFriend extends RecyclerView.Adapter {

  private static final int VIEW_TYPE_ITEM = 4;
  private static final int VIEW_TYPE_LOAD_MORE = 5;
  private List<ModelFriendRequest> requests = new ArrayList<>();
  private Context context;
  private AddFriendListener adapterListener;

  private VHLoadMore loadMore;
  private boolean isNeedShowLoader = true;

  public AdapterAddFriend(Context context, AddFriendListener adapterListener) {
    this.context = context;
    this.adapterListener = adapterListener;
    requests = new ArrayList<>();
  }

  public void setFriendRequests(List<ModelFriendRequest> requests) {
    this.requests = requests;
    notifyDataSetChanged();
  }

  public int getFriendRequestsCount() {
    return requests == null ? 0 : requests.size();
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

  public void removeRequests() {
    if (!requests.isEmpty()) {
      notifyItemRangeRemoved(0, requests.size());
      requests.clear();
    }
  }

  public void removeFriendRequest(int position) {
    if (!requests.isEmpty()) {
      requests.remove(position);
      notifyItemRemoved(position);
      notifyItemRangeChanged(position, requests.size());
    }
  }

  public void updateFriendRequest(ModelFriendRequest request, int position) {
    requests.set(position, request);
    notifyItemChanged(position);
  }

  public void addFriendRequest(ModelFriendRequest item) {
    requests.add(item);
    notifyItemInserted(getItemCount() - 1);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    switch (viewType) {
      case VIEW_TYPE_ITEM: {
        VhCastAddFriendBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.vh_cast_add_friend, parent, false);
        return new ViewHolderAddFriend(binding);
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
        ((ViewHolderAddFriend) holder).setData(requests.get(position), position);
        break;
      }

      case VIEW_TYPE_LOAD_MORE: {
        ((VHLoadMore) holder).notifyLoaderStatusChanged(isNeedShowLoader);
        break;
      }
    }
  }

  public ModelFriendRequest getItem(int position) {
    return requests.get(position);
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
    return requests.size() + 1;
  }

  class ViewHolderAddFriend extends RecyclerView.ViewHolder implements View.OnClickListener {
    private VhCastAddFriendBinding binding;
    private int position;
    private ModelFriendRequest modelUser;

    ViewHolderAddFriend(@NonNull VhCastAddFriendBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(ModelFriendRequest item, int position) {
      this.position = position;
      this.modelUser = item;
      binding.layoutItem.setOnClickListener(this);
      binding.layoutConfirm.setOnClickListener(this);

      binding.layoutLoad.setVisibility(View.GONE);
      displayUser();
    }

    void displayUser() {
      ModelUser user = modelUser.getUser();
      binding.lblItemFollowerUsername.setText(user.getNickName());

      Glide.with(context).load(Util.getValidateUrl(user.getProfileImage()))
          .placeholder(R.drawable.ic_notification_placeholder)
          .into(binding.ivItemFollowerUser);

      if (modelUser.isAccepted()) {
        binding.lblConfirm.setVisibility(View.GONE);
        binding.lblFriends.setVisibility(View.VISIBLE);
      } else {
        binding.lblConfirm.setVisibility(View.VISIBLE);
        binding.lblFriends.setVisibility(View.GONE);
      }
    }

    @Override
    public void onClick(View view) {
      switch (view.getId()) {
        case R.id.layout_item: {
          adapterListener.onItemSelected(position);
          break;
        }
        case R.id.layout_confirm: {
          adapterListener.onConfirm(position);
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
