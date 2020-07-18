package notq.dccast.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import notq.dccast.model.user.ModelUser;

public class ModelRecentVideoInside implements Serializable {
  @SerializedName("media")
  public ModelVideo video;
  @SerializedName("user")
  public ModelUser user;

  public ModelVideo getVideo() {
    return video;
  }

  public void setVideo(ModelVideo video) {
    this.video = video;
  }

  public ModelUser getUser() {
    return user;
  }

  public void setUser(ModelUser user) {
    this.user = user;
  }
}
