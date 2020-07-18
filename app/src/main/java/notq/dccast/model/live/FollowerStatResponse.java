package notq.dccast.model.live;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowerStatResponse {

  @SerializedName("followers") @Expose private int followers;
  @SerializedName("second_followers") @Expose private int secondFollowers;
  @SerializedName("third_followers") @Expose private int thirdFollowers;

  public int getFollowers() {
    return followers;
  }

  public void setFollowers(int followers) {
    this.followers = followers;
  }

  public int getSecondFollowers() {
    return secondFollowers;
  }

  public void setSecondFollowers(int secondFollowers) {
    this.secondFollowers = secondFollowers;
  }

  public int getThirdFollowers() {
    return thirdFollowers;
  }

  public void setThirdFollowers(int thirdFollowers) {
    this.thirdFollowers = thirdFollowers;
  }
}