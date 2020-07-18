package notq.dccast.screens.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.cast_list.CastListAPIInterface;
import notq.dccast.databinding.VhHomeVideoBinding;
import notq.dccast.databinding.VhRelatedVideoHeaderBinding;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.user.ModelResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.profile.ModelUserProfileWrapper;
import notq.dccast.screens.home.interfaces.LikeDislikeListener;
import notq.dccast.screens.home.interfaces.RelatedItemListener;
import notq.dccast.screens.home.view_holders.VHVideo;
import notq.dccast.screens.navigation_menu.content.my_channel.ActivityMyChannel;
import notq.dccast.util.Constants;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRelatedVideos extends RecyclerView.Adapter {
  public static final int VIEW_TYPE_VIDEO_DETAIL_HEADER = 0;
  private static final int VIEW_TYPE_VIDEO_DETAIL_ITEM = 1;
  private RelatedItemListener clickListener;
  private LikeDislikeListener likeDislikeListener;
  private List<ModelVideo> relatedVideoItems;
  private OnAddFriendListener onAddFriendListener;
  private ModelVideo videoItem;
  private ModelListHeader headerVideoItem;
  private Fragment fragment;
  private int currentPosition = -1;
  private int colorFollowing, colorFollow;
  private int bgFollowing, bgFollow;

  public AdapterRelatedVideos(Fragment fragment, LikeDislikeListener likeDislikeListener,
      RelatedItemListener clickListener,
      ModelVideo videoItem) {
    this.fragment = fragment;
    this.likeDislikeListener = likeDislikeListener;
    this.clickListener = clickListener;
    this.videoItem = videoItem;
    this.videoItem.getUser().setNeedFetchFriend(true);
    relatedVideoItems = new ArrayList<>();

    colorFollow = ContextCompat.getColor(fragment.getContext(), R.color.colorPrimary);
    colorFollowing = ContextCompat.getColor(fragment.getContext(), R.color.white);
    bgFollow = R.drawable.cast_list_followers_follow;
    bgFollowing = R.drawable.cast_list_followers_following;
  }

  public AdapterRelatedVideos(Fragment fragment, LikeDislikeListener likeDislikeListener,
      RelatedItemListener clickListener,
      ModelListHeader headerVideoItem) {
    this.fragment = fragment;
    this.likeDislikeListener = likeDislikeListener;
    this.clickListener = clickListener;
    this.headerVideoItem = headerVideoItem;
    this.headerVideoItem.getUser().setNeedFetchFriend(true);
    relatedVideoItems = new ArrayList<>();

    colorFollow = ContextCompat.getColor(fragment.getContext(), R.color.colorPrimary);
    colorFollowing = ContextCompat.getColor(fragment.getContext(), R.color.white);
    bgFollow = R.drawable.cast_list_followers_follow;
    bgFollowing = R.drawable.cast_list_followers_following;
  }

  public void setOnAddFriendListener(
      OnAddFriendListener onAddFriendListener) {
    this.onAddFriendListener = onAddFriendListener;
  }

  public ModelVideo getNextVideo() {
    if (relatedVideoItems.size() > 0) {
      if (currentPosition < relatedVideoItems.size() - 1) {
        currentPosition++;
        return relatedVideoItems.get(currentPosition);
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  public void clearRelatedVideoItems() {
    this.relatedVideoItems = new ArrayList<>();
    notifyDataSetChanged();
  }

  public ModelVideo getPreVideo() {
    if (relatedVideoItems.size() > 0) {
      if (currentPosition > 0) {
        currentPosition--;
        return relatedVideoItems.get(currentPosition);
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  public void addVideo(ModelVideo modelVideo) {
    relatedVideoItems.add(modelVideo);
    notifyItemInserted(getItemCount());
  }

  public void setHeaderData(ModelVideo videoItem) {
    this.videoItem = videoItem;
    this.headerVideoItem = null;
    notifyItemChanged(0);
  }

  public void setHeaderDataUserIsFriends(boolean isFriends) {
    if (this.videoItem != null) {
      this.videoItem.getUser().setFriend(isFriends);
    } else {
      if (headerVideoItem != null) {
        this.headerVideoItem.getUser().setFriend(isFriends);
      }
    }
    notifyItemChanged(0);
  }

  public void setFriendRequestSent() {
    if (this.videoItem != null) {
      this.videoItem.getUser().setNeedFetchFriend(false);
      this.videoItem.getUser().setFriendRequestSend(true);
    } else {
      if (headerVideoItem != null) {
        this.headerVideoItem.getUser().setNeedFetchFriend(false);
        this.headerVideoItem.getUser().setFriendRequestSend(true);
      }
    }
    notifyItemChanged(0);
  }

  public void setHeaderData(ModelListHeader headerVideoItem) {
    this.headerVideoItem = headerVideoItem;
    this.videoItem = null;
    notifyItemChanged(0);
  }

  public ModelVideo getVideoItem() {
    return videoItem;
  }

  public ModelListHeader getHeaderVideoItem() {
    return headerVideoItem;
  }

  public ModelUser getVideoUser() {
    return videoItem != null ? videoItem.getUser() : headerVideoItem.getUser();
  }

  @NonNull @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(fragment.getContext());
    if (viewType == VIEW_TYPE_VIDEO_DETAIL_HEADER) {
      VhRelatedVideoHeaderBinding binding =
          DataBindingUtil.inflate(inflater, R.layout.vh_related_video_header, parent, false);
      return new ViewHolderHeader(binding);
    } else {
      VhHomeVideoBinding binding =
          DataBindingUtil.inflate(inflater, R.layout.vh_home_video, parent, false);
      return new VHVideo(fragment.getContext(), fragment.getFragmentManager(), binding);
    }
  }

  @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if (getItemViewType(position) == VIEW_TYPE_VIDEO_DETAIL_HEADER) {
      if (videoItem != null) {
        ((ViewHolderHeader) holder).setHeaderData(videoItem);
      } else {
        ((ViewHolderHeader) holder).setHeaderData(headerVideoItem);
      }
    } else {
      if (relatedVideoItems != null) {
        ((VHVideo) holder).setVideoItem(VHVideo.TYPE_RELATED, relatedVideoItems.get(position - 1),
            clickListener);
      }
    }
  }

  @Override public int getItemCount() {
    return relatedVideoItems.size() + 1;
  }

  @Override public int getItemViewType(int position) {
    if (position == 0) {
      return VIEW_TYPE_VIDEO_DETAIL_HEADER;
    } else {
      return VIEW_TYPE_VIDEO_DETAIL_ITEM;
    }
  }

  public interface OnAddFriendListener {
    void addFriendClicked();
  }

  private class ViewHolderHeader extends RecyclerView.ViewHolder {
    int profileImageSize;
    private VhRelatedVideoHeaderBinding binding;
    private boolean isLiked;
    private boolean isDisliked;

    ViewHolderHeader(@NonNull VhRelatedVideoHeaderBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      binding.btnExpandDescription.setOnClickListener(view -> toggle());
      profileImageSize = DCCastApplication.utils.pxFromDp(36);

      binding.profileImage.setOnClickListener(v -> navigateToChannel());
      binding.profileName.setOnClickListener(v -> navigateToChannel());

      binding.lblFollow.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (onAddFriendListener != null) {
            onAddFriendListener.addFriendClicked();
          }
        }
      });
    }

    void checkIsFriends(int userId) {
      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser == null || userId == -1) {
        return;
      }
      Call<ModelResult> call =
          APIClient.getClient()
              .create(CastListAPIInterface.class)
              .checkIsFriend(loginUser.getId(), userId);

      call.enqueue(new Callback<ModelResult>() {
        @Override
        public void onResponse(@NonNull Call<ModelResult> call,
            @NonNull Response<ModelResult> response) {
          ModelResult stat = response.body();
          if (stat == null) {
            return;
          }
          if (videoItem != null) {
            videoItem.getUser().setFriend(stat.result);
          }

          if (headerVideoItem != null) {
            headerVideoItem.getUser().setFriend(stat.result);
          }

          if (stat.result) {
            setFriends(binding.lblFollow);
          } else {
            setCanAddFriend(binding.lblFollow);
          }
        }

        @Override
        public void onFailure(@NonNull Call<ModelResult> call, @NonNull Throwable t) {
          call.cancel();
        }
      });
    }

    void setCanAddFriend(TextView textView) {
      textView.setTextColor(colorFollow);
      textView.setBackgroundResource(bgFollow);
      textView.setText(R.string.add_friend_add_friend);
    }

    void setFriends(TextView textView) {
      textView.setTextColor(colorFollowing);
      textView.setBackgroundResource(bgFollowing);
      textView.setText(R.string.add_friend_friends);
    }

    void setRequestSent(TextView textView) {
      textView.setTextColor(colorFollowing);
      textView.setBackgroundResource(bgFollowing);
      textView.setText(R.string.add_friend_request_sent);
    }

    void fetchUserById(int userId) {
      Call<ModelUserProfileWrapper> call =
          APIClient.getClient()
              .create(CastListAPIInterface.class)
              .getProfile(userId);

      call.enqueue(new Callback<ModelUserProfileWrapper>() {
        @Override
        public void onResponse(@NonNull Call<ModelUserProfileWrapper> call,
            @NonNull Response<ModelUserProfileWrapper> response) {
          ModelUserProfileWrapper stat = response.body();
          if (stat == null || stat.users == null || stat.users.isEmpty()) {
            return;
          }
          ModelUser user = stat.users.get(0);
          if (videoItem != null) {
            videoItem.setNeedFetchUser(false);
            videoItem.setUser(user);
          }

          if (headerVideoItem != null) {
            headerVideoItem.setNeedFetchUser(false);
            headerVideoItem.setUser(user);
          }

          notifyItemChanged(VIEW_TYPE_VIDEO_DETAIL_HEADER);
        }

        @Override
        public void onFailure(@NonNull Call<ModelUserProfileWrapper> call, @NonNull Throwable t) {
          call.cancel();
        }
      });
    }

    void displayUser(ModelUser user) {
      Glide.with(fragment.getContext())
          .load(Util.getValidateUrl(user.getProfileImage()))
          .apply(
              new RequestOptions()
                  .override(profileImageSize, profileImageSize)
                  .placeholder(R.drawable.ic_profile_placeholder)
                  .centerInside())
          .into(binding.profileImage);
      binding.profileName.setText(user.getNickName());

      if (user.isVip && user.isVipActive) {
        binding.itemCrown.setVisibility(View.VISIBLE);
      } else {
        binding.itemCrown.setVisibility(View.GONE);
      }
    }

    @SuppressLint("SetTextI18n") void setHeaderData(ModelVideo videoItem) {
      if (videoItem != null) {
        isLiked = videoItem.isLiked();
        isDisliked = videoItem.isDisliked();

        if (videoItem.needFetchUser) {
          fetchUserById(videoItem.getUserId());
        } else {
          displayUser(videoItem.getUser());
        }

        ModelUser loginUser = LoginService.getLoginUser();
        int userId = videoItem != null ? videoItem.getUserId() : -1;
        if (loginUser != null && loginUser.getId() == userId && userId != -1) {
          binding.lblFollow.setVisibility(View.GONE);
        } else {
          binding.lblFollow.setVisibility(View.VISIBLE);
        }

        binding.title.setText(videoItem.getTitle());
        binding.viewCount.setText(
            fragment.getString(R.string.vod_views) + " " + videoItem.getViews());
        binding.description.setText(videoItem.getExplanation());
        binding.likeCount.setText(String.valueOf(videoItem.getLike()));
        binding.dislikeCount.setText(String.valueOf(videoItem.getDisLike()));

        if (isLiked) {
          binding.btnLikeImage.setColorFilter(
              ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                  R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else if (isDisliked) {
          binding.btnLikeImage.setColorFilter(
              ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                  R.color.colorGrey), android.graphics.PorterDuff.Mode.MULTIPLY);

          binding.btnDislikeImage.setColorFilter(
              ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                  R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
          binding.btnLikeImage.setColorFilter(
              ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                  R.color.colorGrey), android.graphics.PorterDuff.Mode.MULTIPLY);

          binding.btnDislikeImage.setColorFilter(
              ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                  R.color.colorGrey), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        binding.btnLike.setOnClickListener(view -> {
          if (LoginService.getLoginUser() == null) {
            likeDislikeListener.onLikeClicked();
            return;
          }
          int likeCount = videoItem.like;

          if (isLiked) {
            binding.btnLikeImage.setColorFilter(
                ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                    R.color.colorGrey), android.graphics.PorterDuff.Mode.MULTIPLY);

            if (likeCount > 0) {
              likeCount = likeCount - 1;
            }
          } else {
            binding.btnLikeImage.setColorFilter(
                ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                    R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);

            likeCount = likeCount + 1;
          }

          binding.likeCount.setText(String.valueOf(likeCount));
          isLiked = !isLiked;

          if (isLiked && isDisliked) {
            binding.btnDislike.performClick();
          }

          videoItem.setLiked(isLiked);
          videoItem.like = likeCount;
          likeDislikeListener.onLikeClicked();
        });

        binding.btnDislike.setOnClickListener(view -> {
          if (LoginService.getLoginUser() == null) {
            likeDislikeListener.onDislikeClicked();
            return;
          }

          int dislikeCount = videoItem.disLike;

          if (isDisliked) {
            binding.btnDislikeImage.setColorFilter(
                ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                    R.color.colorGrey), android.graphics.PorterDuff.Mode.MULTIPLY);

            if (dislikeCount > 0) {
              dislikeCount = dislikeCount - 1;
            }
          } else {
            binding.btnDislikeImage.setColorFilter(
                ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                    R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);

            dislikeCount = dislikeCount + 1;
          }

          binding.dislikeCount.setText(String.valueOf(dislikeCount));
          isDisliked = !isDisliked;

          if (isDisliked && isLiked) {
            binding.btnLike.performClick();
          }

          videoItem.setDisliked(isDisliked);
          videoItem.disLike = dislikeCount;
          likeDislikeListener.onDislikeClicked();
        });

        if (videoItem.getMediaCategory() != null) {
          binding.category.setText(
              "카테고리: " + Objects.requireNonNull(videoItem).getMediaCategory().getName());
        }
        if (videoItem.getCreated() != null) {
          binding.date.setText(videoItem.getCreated().substring(0, 10));
        }

        if (videoItem.getUser().isNeedFetchFriend()) {
          checkIsFriends(videoItem.getUser().getId());
        } else {
          if (videoItem.getUser().isFriendRequestSend()) {
            setRequestSent(binding.lblFollow);
          } else if (videoItem.getUser().isFriend()) {
            setFriends(binding.lblFollow);
          } else {
            setCanAddFriend(binding.lblFollow);
          }
        }
      }
    }

    @SuppressLint("SetTextI18n") void setHeaderData(ModelListHeader videoHeaderItem) {
      if (videoHeaderItem != null) {
        isLiked = videoHeaderItem.isLiked();
        isDisliked = videoHeaderItem.isDisliked();

        if (videoHeaderItem.needFetchUser) {
          fetchUserById(videoHeaderItem.getUserId());
        } else {
          displayUser(videoHeaderItem.getUser());
        }

        ModelUser loginUser = LoginService.getLoginUser();
        int userId = videoItem != null ? videoHeaderItem.getUserId() : -1;
        if (loginUser != null && loginUser.getId() == userId && userId != -1) {
          binding.lblFollow.setVisibility(View.GONE);
        } else {
          binding.lblFollow.setVisibility(View.VISIBLE);
        }

        binding.title.setText(videoHeaderItem.getTitle());
        binding.viewCount.setText(videoHeaderItem.getViews() + " views");
        binding.description.setText(videoHeaderItem.getExplanation());
        binding.likeCount.setText(String.valueOf(videoHeaderItem.getLike()));
        binding.dislikeCount.setText(String.valueOf(videoHeaderItem.getDisLike()));

        if (isLiked) {
          binding.btnLikeImage.setColorFilter(
              ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                  R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else if (isDisliked) {
          binding.btnLikeImage.setColorFilter(
              ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                  R.color.colorGrey), android.graphics.PorterDuff.Mode.MULTIPLY);

          binding.btnDislikeImage.setColorFilter(
              ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                  R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
          binding.btnLikeImage.setColorFilter(
              ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                  R.color.colorGrey), android.graphics.PorterDuff.Mode.MULTIPLY);

          binding.btnDislikeImage.setColorFilter(
              ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                  R.color.colorGrey), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        binding.btnLike.setOnClickListener(view -> {
          if (LoginService.getLoginUser() == null) {
            likeDislikeListener.onLikeClicked();
            return;
          }
          int likeCount = videoHeaderItem.like;

          if (isLiked) {
            binding.btnLikeImage.setColorFilter(
                ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                    R.color.colorGrey), android.graphics.PorterDuff.Mode.MULTIPLY);

            if (likeCount > 0) {
              likeCount = likeCount - 1;
            }
          } else {
            binding.btnLikeImage.setColorFilter(
                ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                    R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);

            likeCount = likeCount + 1;
          }

          binding.likeCount.setText(String.valueOf(likeCount));
          isLiked = !isLiked;

          if (isLiked && isDisliked) {
            binding.btnDislike.performClick();
          }

          videoHeaderItem.setLiked(isLiked);
          videoHeaderItem.like = likeCount;
          likeDislikeListener.onLikeClicked();
        });

        binding.btnDislike.setOnClickListener(view -> {
          if (LoginService.getLoginUser() == null) {
            likeDislikeListener.onLikeClicked();
            return;
          }
          int dislikeCount = videoHeaderItem.disLike;

          if (isDisliked) {
            binding.btnDislikeImage.setColorFilter(
                ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                    R.color.colorGrey), android.graphics.PorterDuff.Mode.MULTIPLY);

            if (dislikeCount > 0) {
              dislikeCount = dislikeCount - 1;
            }
          } else {
            binding.btnDislikeImage.setColorFilter(
                ContextCompat.getColor(Objects.requireNonNull(fragment.getContext()),
                    R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);

            dislikeCount = dislikeCount + 1;
          }
          videoHeaderItem.setDisLike(dislikeCount);

          binding.dislikeCount.setText(String.valueOf(dislikeCount));
          isDisliked = !isDisliked;

          if (isDisliked && isLiked) {
            binding.btnLike.performClick();
          }

          videoHeaderItem.setDisliked(isDisliked);
          videoHeaderItem.disLike = dislikeCount;
          likeDislikeListener.onDislikeClicked();
        });

        if (videoHeaderItem.getMediaCategory() != null) {
          binding.category.setText(
              "Category: " + Objects.requireNonNull(videoHeaderItem).getMediaCategory().name);
        }
        if (videoHeaderItem.getCreated() != null) {
          binding.date.setText(videoHeaderItem.getCreated().substring(0, 10));
        }

        if (videoHeaderItem.getUser().isNeedFetchFriend()) {
          checkIsFriends(videoHeaderItem.getUser().getId());
        } else {
          if (videoHeaderItem.getUser().isFriendRequestSend()) {
            setRequestSent(binding.lblFollow);
          } else if (videoHeaderItem.getUser().isFriend()) {
            setFriends(binding.lblFollow);
          } else {
            setCanAddFriend(binding.lblFollow);
          }
        }
      }
    }

    private void navigateToChannel() {
      Context context = fragment.getContext();
      Intent channelIntent = new Intent(context, ActivityMyChannel.class);
      channelIntent.putExtra(Constants.CHANNEL_DETAIL_ID,
          videoItem != null ? videoItem.getUser().getId() : headerVideoItem.getUser().getId());

      if (LoginService.getLoginUser() != null) {
        channelIntent.putExtra(Constants.CHANNEL_DETAIL_IS_ME,
            videoItem != null ? videoItem.getUser().getId() == LoginService.getLoginUser().id
                : headerVideoItem.getUser().getId() == LoginService.getLoginUser().id);
      } else {
        channelIntent.putExtra(Constants.CHANNEL_DETAIL_IS_ME, false);
      }

      context.startActivity(channelIntent);
    }

    private void toggle() {
      if (binding.descriptionContainer.isShown()) {
        binding.descriptionContainer.setVisibility(View.GONE);
        binding.arrow.animate().rotation(0).start();
      } else {
        binding.descriptionContainer.setVisibility(View.VISIBLE);
        binding.arrow.animate().rotation(180).start();
      }
    }
  }
}
