package notq.dccast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ModelAddToRecentResult implements Serializable {
  @SerializedName("id") @Expose public int id;
  @SerializedName("user") @Expose public int user;
  @SerializedName("media") @Expose public int media;
  @SerializedName("created") @Expose public String created;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUser() {
    return user;
  }

  public void setUser(int user) {
    this.user = user;
  }

  public int getMedia() {
    return media;
  }

  public void setMedia(int media) {
    this.media = media;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }
}
