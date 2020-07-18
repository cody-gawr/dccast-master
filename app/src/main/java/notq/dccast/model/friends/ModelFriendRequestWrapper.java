package notq.dccast.model.friends;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ModelFriendRequestWrapper {
  @SerializedName("results") public List<ModelFriendRequest> friends;
  @SerializedName("next")
  public String next;
  @SerializedName("count")
  public int count = 0;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public List<ModelFriendRequest> getFriends() {
    return friends;
  }

  public void setFriends(List<ModelFriendRequest> friends) {
    this.friends = friends;
  }

  public String getNext() {
    return next;
  }

  public void setNext(String next) {
    this.next = next;
  }
}
