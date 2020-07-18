package notq.dccast.screens.navigation_menu.cast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.databinding.VhCastSearchFriendBinding;
import notq.dccast.databinding.VhHomeLoadMoreBinding;
import notq.dccast.model.user.ModelResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.navigation_menu.cast.interfaces.SearchFriendListener;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterSearchFriends extends RecyclerView.Adapter {

  private static final int VIEW_TYPE_ITEM = 4;
  private static final int VIEW_TYPE_LOAD_MORE = 5;
  private List<ModelUser> followers = new ArrayList<>();
  private Context context;
  private SearchFriendListener adapterListener;
  private int addedFriend;
  private int addFriend;

  private int colorFollowing, colorFollow, colorFriends;
  private int bgFollowing, bgFollow, bgWhite;

  private VHLoadMore loadMore;
  private boolean isNeedShowLoader = true;

  public AdapterSearchFriends(Context context, SearchFriendListener adapterListener) {
    this.context = context;
    this.adapterListener = adapterListener;
    followers = new ArrayList<>();

    addedFriend = ContextCompat.getColor(context, R.color.added_friend);
    addFriend = ContextCompat.getColor(context, R.color.add_friend);

    colorFollow = ContextCompat.getColor(context, R.color.colorPrimary);
    colorFollowing = ContextCompat.getColor(context, R.color.white);
    colorFriends = ContextCompat.getColor(context, R.color.cast_list_no_item_text);
    bgFollow = R.drawable.cast_list_followers_follow;
    bgFollowing = R.drawable.cast_list_followers_following;
    bgWhite = R.drawable.cast_list_followers_white;
  }

  public void setFollowers(List<ModelUser> followers) {
    this.followers = followers;
    notifyDataSetChanged();
  }

  public int getFollowersCount() {
    return followers == null ? 0 : followers.size();
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

  public void removeFollowers() {
    if (!followers.isEmpty()) {
      notifyItemRangeRemoved(0, followers.size());
      followers.clear();
    }
  }

  public void removeFollower(int position) {
    if (!followers.isEmpty()) {
      followers.remove(position);
      notifyItemRemoved(position);
      notifyItemRangeChanged(position, followers.size());
    }
  }

  public void removeFollower(ModelUser modelUser) {
    if (!followers.isEmpty()) {
      int position = -1;
      for (int i = 0; i < followers.size(); i++) {
        if (followers.get(i).getId() == modelUser.getId()) {
          position = i;
          break;
        }
      }

      if (position >= 0) {
        removeFollower(position);
      }
    }
  }

  public void updateFriend(ModelUser item, int position) {
    followers.set(position, item);
    notifyItemChanged(position);
  }

  public void setFriendRequestSent(int position) {
    ModelUser item = getItem(position);
    item.setNeedFetchFriend(false);
    item.setFriendRequestSend(true);
    followers.set(position, item);
    notifyItemChanged(position);
  }

  public void addFollower(ModelUser item) {
    followers.add(item);
    notifyItemInserted(getItemCount() - 1);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    switch (viewType) {
      case VIEW_TYPE_ITEM: {
        VhCastSearchFriendBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.vh_cast_search_friend, parent, false);
        return new ViewHolderSearchFriend(binding);
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

  void setRequestSent(TextView textView) {
    textView.setTextColor(colorFollowing);
    textView.setBackgroundResource(bgFollowing);
    textView.setText(R.string.add_friend_request_sent);
  }

  void setAddFriend(TextView textView) {
    textView.setTextColor(colorFollow);
    textView.setBackgroundResource(bgFollow);
    textView.setText(R.string.cast_list_add_friend);
  }

  void setFriendsBg(TextView textView) {
    textView.setTextColor(colorFriends);
    textView.setBackgroundResource(bgWhite);
    textView.setText(R.string.add_friend_friends);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    switch (getItemViewType(position)) {
      case VIEW_TYPE_ITEM: {
        ((ViewHolderSearchFriend) holder).setData(followers.get(position), position);
        break;
      }

      case VIEW_TYPE_LOAD_MORE: {
        ((VHLoadMore) holder).notifyLoaderStatusChanged(isNeedShowLoader);
        break;
      }
    }
  }

  public ModelUser getItem(int position) {
    return followers.get(position);
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
    return followers.size() + 1;
  }

  class ViewHolderSearchFriend extends RecyclerView.ViewHolder implements View.OnClickListener {
    private VhCastSearchFriendBinding binding;
    private int position;
    private ModelUser modelUser;

    ViewHolderSearchFriend(@NonNull VhCastSearchFriendBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(ModelUser item, int position) {
      this.position = position;
      this.modelUser = item;
      binding.layoutItem.setOnClickListener(this);
      binding.layoutAddFriend.setOnClickListener(this);

      binding.layoutLoad.setVisibility(View.GONE);
      displayUser();
    }

    void displayUser() {
      binding.lblItemFollowerUsername.setText(modelUser.getNickName());

      Glide.with(context).load(Util.getValidateUrl(modelUser.getProfileImage()))
          .placeholder(R.drawable.ic_notification_placeholder).into(binding.ivItemFollowerUser);

      ModelUser loginUser = LoginService.getLoginUser();
      if (modelUser.getId() == loginUser.getId()) {
        binding.layoutAddFriend.setVisibility(View.GONE);
      } else {
        binding.layoutAddFriend.setVisibility(View.VISIBLE);
        if (modelUser.isNeedFetchFriend()) {
          checkCanAdd();
        } else {
          setFriends(modelUser);
        }
      }
    }

    void checkCanAdd() {
      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser == null) {
        return;
      }
      binding.layoutCheck.setVisibility(View.VISIBLE);
      Call<ModelResult> call =
          APIClient.getClient()
              .create(CastListAPIInterface.class)
              .checkIsFriend(loginUser.getId(), modelUser.getId());

      call.enqueue(new Callback<ModelResult>() {
        @Override
        public void onResponse(@NonNull Call<ModelResult> call,
            @NonNull Response<ModelResult> response) {
          binding.layoutCheck.setVisibility(View.GONE);

          ModelResult stat = response.body();
          if (stat == null) {
            return;
          }
          modelUser.setNeedFetchFriend(false);
          modelUser.setFriend(stat.isResult());

          updateFriend(modelUser, position);
        }

        @Override
        public void onFailure(@NonNull Call<ModelResult> call, @NonNull Throwable t) {
          call.cancel();

          binding.layoutCheck.setVisibility(View.GONE);
        }
      });
    }

    void setFriends(ModelUser user) {
      binding.layoutCheck.setVisibility(View.GONE);
      if (user.isFriendRequestSend()) {
        binding.layoutAddFriend.setVisibility(View.GONE);
        binding.lblFriends.setVisibility(View.VISIBLE);
        binding.lblFriends.setText(context.getString(R.string.add_friend_request_sent));
        setRequestSent(binding.lblFriends);
      } else if (user.isFriend()) {
        binding.layoutAddFriend.setVisibility(View.GONE);
        binding.lblFriends.setVisibility(View.VISIBLE);
        binding.lblFriends.setText(context.getString(R.string.add_friend_friends));
        setFriendsBg(binding.lblFriends);
      } else {
        binding.layoutAddFriend.setVisibility(View.VISIBLE);
        binding.lblFriends.setVisibility(View.GONE);
        setAddFriend(binding.lblFriends);
      }
    }

    @Override
    public void onClick(View view) {
      switch (view.getId()) {
        case R.id.layout_item: {
          adapterListener.onItemSelected(position);
          break;
        }
        case R.id.layout_add_friend: {
          adapterListener.addFriend(position);
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
