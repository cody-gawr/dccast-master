package notq.dccast.model.friends;

import com.google.gson.annotations.SerializedName;
import notq.dccast.model.user.ModelUser;

public class ModelFriendResult {
  @SerializedName("id")
  public Integer id;
  @SerializedName("from_user")
  public ModelUser fromUser;
  @SerializedName("to_user")
  public ModelUser toUser;
  @SerializedName("datetime")
  public String dateTime;
  @SerializedName("accepted")
  public boolean accepted;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }
}
