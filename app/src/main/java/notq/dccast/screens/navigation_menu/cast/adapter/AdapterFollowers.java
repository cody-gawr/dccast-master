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
import notq.dccast.databinding.VhCastFollowerBinding;
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

public class AdapterFollowers extends RecyclerView.Adapter {

  private static final int VIEW_TYPE_ITEM = 4;
  private static final int VIEW_TYPE_LOAD_MORE = 5;
  private List<ModelUser> followers = new ArrayList<>();
  private Context context;
  private FollowersListener adapterListener;

  private int colorFollowing, colorFollow;
  private int bgFollowing, bgFollow;

  private boolean isFollowing = false;

  private VHLoadMore loadMore;
  private boolean isNeedShowLoader = true;

  private String live, onAir;

  public AdapterFollowers(Context context, boolean isFollowing, FollowersListener adapterListener) {
    this.context = context;
    this.adapterListener = adapterListener;
    followers = new ArrayList<>();
    this.isFollowing = isFollowing;

    colorFollow = ContextCompat.getColor(context, R.color.colorPrimary);
    colorFollowing = ContextCompat.getColor(context, R.color.white);
    bgFollow = R.drawable.cast_list_followers_follow;
    bgFollowing = R.drawable.cast_list_followers_following;

    live = context.getString(R.string.live);
    onAir = context.getString(R.string.on_air);
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
        VhCastFollowerBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.vh_cast_follower, parent, false);
        return new ViewHolderCastFollower(binding);
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
        ((ViewHolderCastFollower) holder).setData(followers.get(position), position);
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

  class ViewHolderCastFollower extends RecyclerView.ViewHolder implements View.OnClickListener {
    private VhCastFollowerBinding binding;
    private int position;
    private ModelUser modelUser;

    ViewHolderCastFollower(@NonNull VhCastFollowerBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(ModelUser item, int position) {
      this.position = position;
      this.modelUser = item;
      binding.layoutItem.setOnClickListener(this);
      binding.lblFollow.setOnClickListener(this);

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
            followers.set(position, modelUser);
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
      binding.lblItemFollowerUsername.setText(modelUser.getNickName());

      Glide.with(context).load(Util.getValidateUrl(modelUser.getProfileImage()))
          .placeholder(R.drawable.ic_notification_placeholder)
          .into(binding.ivItemFollowerUser);

      if (modelUser.getStateMessage() != null) {
        binding.lblItemSubscribeDescription.setText(modelUser.getStateMessage());
        binding.lblItemSubscribeDescription.setVisibility(View.VISIBLE);
      } else {
        binding.lblItemSubscribeDescription.setText("");
        binding.lblItemSubscribeDescription.setVisibility(View.GONE);
      }

      if (modelUser.isOnAir()) {
        binding.lblOnAir.setVisibility(View.VISIBLE);
      } else {
        binding.lblOnAir.setVisibility(View.GONE);
      }

      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser != null && loginUser.getId() == modelUser.getId()) {
        binding.lblFollow.setVisibility(View.GONE);
      } else {
        binding.lblFollow.setVisibility(View.VISIBLE);
        checkCanFollow();
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
