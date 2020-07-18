package notq.dccast.model.subscribe;

import com.google.gson.annotations.SerializedName;
import notq.dccast.model.user.ModelUser;

public class ResponseSubscribe {
  @SerializedName("id") private int id;
  @SerializedName("from_user") private ModelUser fromUser;
  @SerializedName("to_user") private ModelUser toUser;
  @SerializedName("created") private String createdDate;

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

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }
}
