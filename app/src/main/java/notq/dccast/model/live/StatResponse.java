package notq.dccast.model.live;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatResponse {

  @SerializedName("following") @Expose private int following;
  @SerializedName("friends") @Expose private int friends;
  @SerializedName("followers") @Expose private int followers;
  @SerializedName("subscribers") @Expose private int subscribers;

  public int getFollowing() {
    return following;
  }

  public void setFollowing(int following) {
    this.following = following;
  }

  public int getFriends() {
    return friends;
  }

  public void setFriends(int friends) {
    this.friends = friends;
  }

  public int getFollowers() {
    return followers;
  }

  public void setFollowers(int followers) {
    this.followers = followers;
  }

  public int getSubscribers() {
    return subscribers;
  }

  public void setSubscribers(int subscribers) {
    this.subscribers = subscribers;
  }
}