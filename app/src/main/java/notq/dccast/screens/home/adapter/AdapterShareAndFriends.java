package notq.dccast.screens.home.adapter;

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
import notq.dccast.databinding.VhShareAndFriendsBinding;
import notq.dccast.model.ModelShare;
import notq.dccast.model.friends.ModelFriendRequest;
import notq.dccast.util.Util;

public class AdapterShareAndFriends
    extends RecyclerView.Adapter<AdapterShareAndFriends.ViewHolderSAF> {

  private Context context;
  private boolean isShare;
  private List<ModelFriendRequest> userList;
  private List<ModelShare> shareList;
  private View.OnClickListener clickListener;
  private ShareToFriendsListener shareToFriendsListener;

  public AdapterShareAndFriends(Context context, boolean isShare,
      View.OnClickListener clickListener) {
    this.context = context;
    this.isShare = isShare;
    this.clickListener = clickListener;

    userList = new ArrayList<>();
    shareList = new ArrayList<>();

    if (isShare) {
      ModelShare facebook = new ModelShare();
      facebook.setName(context.getString(R.string.share_facebook));
      facebook.setImgResource(R.drawable.ic_facebook);

      ModelShare line = new ModelShare();
      line.setName(context.getString(R.string.share_line));
      line.setImgResource(R.drawable.ic_line);

      ModelShare kakao = new ModelShare();
      kakao.setName(context.getString(R.string.share_kakao));
      kakao.setImgResource(R.drawable.ic_kakao);

      ModelShare twitter = new ModelShare();
      twitter.setName(context.getString(R.string.share_twitter));
      twitter.setImgResource(R.drawable.ic_twitter);

      ModelShare more = new ModelShare();
      more.setName(context.getString(R.string.share_more));
      more.setImgResource(R.drawable.ic_share_more);

      shareList.add(facebook);
      shareList.add(line);
      shareList.add(kakao);
      shareList.add(twitter);
      shareList.add(more);
    }
  }

  public void setShareToFriendsListener(
      ShareToFriendsListener shareToFriendsListener) {
    this.shareToFriendsListener = shareToFriendsListener;
  }

  public List<ModelShare> getShareList() {
    return shareList;
  }

  public void setUserList(List<ModelFriendRequest> userList) {
    this.userList = userList;
    notifyDataSetChanged();
  }

  public void addFriend(ModelFriendRequest friendRequest) {
    userList.add(friendRequest);
    notifyItemInserted(getItemCount() - 1);
  }

  @NonNull
  @Override
  public ViewHolderSAF onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    VhShareAndFriendsBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.vh_share_and_friends, null, false);

    return new ViewHolderSAF(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolderSAF holder, int position) {
    if (isShare) {
      ModelShare share = shareList.get(position);

      holder.binding.name.setText(share.getName());
      holder.binding.img.setImageResource(share.getImgResource());
      holder.binding.container.setOnClickListener(view -> {
        view.setTag(share.getName());
        clickListener.onClick(view);
      });
    } else {
      ModelFriendRequest friend = userList.get(position);
      holder.binding.name.setText(friend.getUser().getNickName());
      Glide.with(context)
          .load(Util.getValidateUrl(friend.getUser().getProfileImage()))
          .into(holder.binding.img);

      holder.binding.container.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (shareToFriendsListener != null) {
            shareToFriendsListener.shareToFriends(friend);
          }
        }
      });
    }
  }

  @Override
  public int getItemCount() {
    return isShare ? shareList.size() : userList.size();
  }

  public interface ShareToFriendsListener {
    void shareToFriends(ModelFriendRequest friendRequest);
  }

  class ViewHolderSAF extends RecyclerView.ViewHolder {
    private VhShareAndFriendsBinding binding;

    ViewHolderSAF(@NonNull VhShareAndFriendsBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}
