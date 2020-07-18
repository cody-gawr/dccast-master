package notq.dccast.model.subscribe;

import com.google.gson.annotations.SerializedName;

public class ResponseCreateSubscribe {
  @SerializedName("id") private int id;
  @SerializedName("from_user")private int fromUserId;
  @SerializedName("to_user")private int toUserId;
  @SerializedName("created") private String createdDate;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getFromUserId() {
    return fromUserId;
  }

  public void setFromUserId(int fromUserId) {
    this.fromUserId = fromUserId;
  }

  public int getToUserId() {
    return toUserId;
  }

  public void setToUserId(int toUserId) {
    this.toUserId = toUserId;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }
}
