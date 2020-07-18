package notq.dccast.model.user.subscribe;

import com.google.gson.annotations.SerializedName;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.user.ModelUser;

public class ModelSubscribeUser {
  @SerializedName("id")
  public int id;
  @SerializedName("from_user")
  public ModelUser fromUser;
  @SerializedName("to_user")
  public ModelUser toUser;
  @SerializedName("created")
  public String created;
  @SerializedName("latest-vod")
  public ModelVideo latestVod;
  @SerializedName("latest-live")
  public ModelVideo latestLive;

  public ModelVideo getLatestLive() {
    return latestLive;
  }

  public void setLatestLive(ModelVideo latestLive) {
    this.latestLive = latestLive;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public ModelUser getFromUser() {
    return fromUser;
  }

  public void setFromUser(ModelUser fromUser) {
    this.fromUser = fromUser;
  }

  public ModelUser getToUser() {
    return toUser;
  }

  public void setToUser(ModelUser toUser) {
    this.toUser = toUser;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public ModelVideo getLatestVod() {
    return latestVod;
  }

  public void setLatestVod(ModelVideo latestVod) {
    this.latestVod = latestVod;
  }
}
