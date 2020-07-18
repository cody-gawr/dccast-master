package notq.dccast.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ModelFriendList {
  @SerializedName("results")
  @Expose List<ModelFriend> friends;

  public List<ModelFriend> getFriends() {
    return friends;
  }

  public void setUsers(List<ModelFriend> friends) {
    this.friends = friends;
  }
}
