package notq.dccast.model.user.follower;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelFollowResult {
  @SerializedName("id")
  @Expose
  public int id;

  @SerializedName("user_id")
  @Expose
  public int userId;

  @SerializedName("follower_id")
  @Expose
  public int followerId;
  @SerializedName("follower_date")
  @Expose
  public String followerDate;
  @SerializedName("following")
  @Expose
  public boolean following;

  @SerializedName("results")
  public boolean result;
  @SerializedName("success")
  public boolean success;

  public boolean isResult() {
    return result;
  }

  public void setResult(boolean result) {
    this.result = result;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

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

  public String getFollowerDate() {
    return followerDate;
  }

  public void setFollowerDate(String followerDate) {
    this.followerDate = followerDate;
  }

  public boolean isFollowing() {
    return following;
  }

  public void setFollowing(boolean following) {
    this.following = following;
  }
}
