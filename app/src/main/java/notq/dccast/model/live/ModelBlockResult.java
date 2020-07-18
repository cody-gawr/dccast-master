package notq.dccast.model.live;

import com.google.gson.annotations.SerializedName;

public class ModelBlockResult {
  @SerializedName("id")
  public int id;
  @SerializedName("type")
  public String type;
  @SerializedName("category")
  public String category;
  @SerializedName("media")
  public int media;
  @SerializedName("forced_user")
  public int forced_user;
  @SerializedName("forcing_user")
  public int forcing_user;
  @SerializedName("created")
  public String created;
  @SerializedName("start_timestamp")
  public String start_timestamp;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getMedia() {
    return media;
  }

  public void setMedia(int media) {
    this.media = media;
  }

  public int getForced_user() {
    return forced_user;
  }

  public void setForced_user(int forced_user) {
    this.forced_user = forced_user;
  }

  public int getForcing_user() {
    return forcing_user;
  }

  public void setForcing_user(int forcing_user) {
    this.forcing_user = forcing_user;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getStart_timestamp() {
    return start_timestamp;
  }

  public void setStart_timestamp(String start_timestamp) {
    this.start_timestamp = start_timestamp;
  }
}
