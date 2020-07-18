package notq.dccast.model.user.profile;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ModelProfileStat implements Serializable {
  @SerializedName("subscribers")
  public int subscribers;
  @SerializedName("friends")
  public int friends;
  @SerializedName("following")
  public int following;
  @SerializedName("followers")
  public int followers;

  public ModelProfileStat() {
  }

  public int getSubscribers() {
    return subscribers;
  }

  public void setSubscribers(int subscribers) {
    this.subscribers = subscribers;
  }

  public int getFriends() {
    return friends;
  }

  public void setFriends(int friends) {
    this.friends = friends;
  }

  public int getFollowing() {
    return following;
  }

  public void setFollowing(int following) {
    this.following = following;
  }

  public int getFollowers() {
    return followers;
  }

  public void setFollowers(int followers) {
    this.followers = followers;
  }
}
