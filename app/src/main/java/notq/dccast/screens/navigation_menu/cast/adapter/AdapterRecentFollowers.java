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
import notq.dccast.api.my_content.MyContentAPIInterface;
import notq.dccast.databinding.VhCastRecentFollowerBinding;
import notq.dccast.databinding.VhHomeLoadMoreBinding;
import notq.dccast.model.user.ModelResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.profile.ModelUserProfileWrapper;
import notq.dccast.screens.navigation_menu.cast.interfaces.FollowersListener;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRecentFollowers
    extends RecyclerView.Adapter {

  private static final int VIEW_TYPE_ITEM = 4;
  private static final int VIEW_TYPE_LOAD_MORE = 5;
  private List<ModelUser> recent = new ArrayList<>();
  private Context context;
  private FollowersListener adapterListener;
  private VHLoadMore loadMore;
  private boolean isNeedShowLoader = true;
  private boolean isFriendsRecent = false;
  private int colorFollowing, colorFollow;
  private int bgFollowing, bgFollow;

  private boolean isFollowing = false;
  private String live, onAir;

  public AdapterRecentFollowers(Context context, boolean isFollowing,
      FollowersListener adapterListener) {
    this.context = context;
    this.adapterListener = adapterListener;
    this.isFollowing = isFollowing;
    recent = new ArrayList<>();

    colorFollow = ContextCompat.getColor(context, R.color.colorPrimary);
    colorFollowing = ContextCompat.getColor(context, R.color.white);
    bgFollow = R.drawable.cast_list_followers_follow;
    bgFollowing = R.drawable.cast_list_followers_following;

    live = context.getString(R.string.live);
    onAir = context.getString(R.string.on_air);
  }

  public void setFriendsRecent(boolean friendsRecent) {
    isFriendsRecent = friendsRecent;
  }

  public void setRecent(List<ModelUser> notificationNotices) {
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

  public void removeRecent(ModelUser modelUser) {
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

  public void addRecent(ModelUser item) {
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

  public ModelUser getItem(int position) {
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
    private ModelUser modelUser;

    ViewHolderCastRecentFollower(@NonNull VhCastRecentFollowerBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(ModelUser item, int position) {
      this.position = position;
      this.modelUser = item;

      this.binding.lblFollow.setOnClickListener(this);
      binding.layoutItem.setOnClickListener(this);

      binding.lblOnAir.setText(isFollowing ? live : onAir);

      if (modelUser.getUser() == null) {
        binding.layoutLoad.setVisibility(View.VISIBLE);

        Call<ModelUserProfileWrapper> call =
            APIClient.getClient().create(MyContentAPIInterface.class).getProfile(modelUser.getId());

        call.enqueue(new Callback<ModelUserProfileWrapper>() {
          @Override
          public void onResponse(@NonNull Call<ModelUserProfileWrapper> call,
              @NonNull Response<ModelUserProfileWrapper> response) {
            ModelUserProfileWrapper stat = response.body();

            binding.layoutLoad.setVisibility(View.GONE);

            if (stat == null || stat.users == null || stat.users.size() == 0) {
              return;
            }

            modelUser = stat.users.get(0);
            recent.set(position, modelUser);
            displayUser();
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
      binding.lblUsername.setText(modelUser.getNickName());
      if (modelUser.getStateMessage() != null) {
        binding.lblItemSubscribeDescription.setText(modelUser.getStateMessage());
        binding.lblItemSubscribeDescription.setVisibility(View.VISIBLE);
      } else {
        binding.lblItemSubscribeDescription.setText("");
        binding.lblItemSubscribeDescription.setVisibility(View.GONE);
      }
      Glide.with(context).load(Util.getValidateUrl(modelUser.getProfileImage()))
          .placeholder(R.drawable.ic_notification_placeholder).into(binding.ivUser);

      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser != null && loginUser.getId() == modelUser.getId()) {
        binding.lblFollow.setVisibility(View.GONE);
      } else {
        binding.lblFollow.setVisibility(View.VISIBLE);
        if (!isFriendsRecent) {
          checkCanFollow();
        } else {
          binding.lblFollow.setVisibility(View.GONE);
        }
      }

      if (modelUser.isOnAir()) {
        binding.lblOnAir.setVisibility(View.VISIBLE);
      } else {
        binding.lblOnAir.setVisibility(View.GONE);
      }
    }

    void checkCanFollow() {
      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser == null) {
        return;
      }
      Call<ModelResult> call =
          APIClient.getClient()
              .create(CastListAPIInterface.class)
              .checkFollow("avail_follow", loginUser.getId(), modelUser.getId());

      call.enqueue(new Callback<ModelResult>() {
        @Override
        public void onResponse(@NonNull Call<ModelResult> call,
            @NonNull Response<ModelResult> response) {
          ModelResult stat = response.body();
          if (stat == null) {
            return;
          }
          modelUser.setFollowing(!stat.result);
          if (modelUser.isFollowing()) {
            setFollowing(binding.lblFollow);
          } else {
            setFollow(binding.lblFollow);
          }
        }

        @Override
        public void onFailure(@NonNull Call<ModelResult> call, @NonNull Throwable t) {
          call.cancel();
        }
      });
    }

    void setFollow(TextView textView) {
      textView.setTextColor(colorFollow);
      textView.setBackgroundResource(bgFollow);
      textView.setText(R.string.cast_list_follow);
    }

    void setFollowing(TextView textView) {
      textView.setTextColor(colorFollowing);
      textView.setBackgroundResource(bgFollowing);
      textView.setText(R.string.cast_list_following);
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
