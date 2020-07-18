package notq.dccast.model.user;

import com.google.gson.annotations.SerializedName;

public class ModelFriend {
  @SerializedName("id") private int id;

  @SerializedName("user_id") private int userId;

  @SerializedName("follower_id") private int followerId;

  @SerializedName("follow_data") private String followDate;

  @SerializedName("follower") private ModelUser follower;

  @SerializedName("user") private ModelUser user;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getFollowerId() {
    return followerId;
  }

  public void setFollowerId(int followerId) {
    this.followerId = followerId;
  }

  public String getFollowDate() {
    return followDate;
  }

  public void setFollowDate(String followDate) {
    this.followDate = followDate;
  }

  public ModelUser getFollower() {
    return follower;
  }

  public void setFollower(ModelUser follower) {
    this.follower = follower;
  }

  public ModelUser getUser() {
    return user;
  }

  public void setUser(ModelUser user) {
    this.user = user;
  }
}
