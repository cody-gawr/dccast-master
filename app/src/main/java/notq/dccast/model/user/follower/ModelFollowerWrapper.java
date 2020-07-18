package notq.dccast.model.user.follower;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ModelFollowerWrapper {
    @SerializedName("results")
    public List<ModelFollower> followers;
    @SerializedName("next")
    public String next;

  @SerializedName("count")
  public int count;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
