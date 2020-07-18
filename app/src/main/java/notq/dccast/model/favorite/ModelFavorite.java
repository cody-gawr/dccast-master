package notq.dccast.model.favorite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.user.ModelUser;

public class ModelFavorite {
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("user")
  @Expose
  private ModelUser user;
  @SerializedName("media")
  @Expose
  private ModelVideo media;
  @SerializedName("created")
  @Expose
  private String created;

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

  public ModelVideo getMedia() {
    return media;
  }

  public void setMedia(ModelVideo media) {
    this.media = media;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }
}
