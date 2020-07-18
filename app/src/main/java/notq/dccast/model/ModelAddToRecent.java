package notq.dccast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import notq.dccast.model.user.ModelUser;

public class ModelAddToRecent implements Serializable {
  @SerializedName("id") @Expose public Integer id;
  @SerializedName("user") @Expose public ModelUser user;
  @SerializedName("media") @Expose public ModelVideo media;
  @SerializedName("created") @Expose public String created;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ModelUser getUser() {
    return user;
  }

  public void setUser(ModelUser user) {
    this.user = user;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public ModelVideo getMedia() {
    return media;
  }

  public void setMedia(ModelVideo media) {
    this.media = media;
  }
}
