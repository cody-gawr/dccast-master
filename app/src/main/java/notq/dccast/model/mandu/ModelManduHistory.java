package notq.dccast.model.mandu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.user.ModelUser;

public class ModelManduHistory {
  @SerializedName("id")
  @Expose
  public int id;
  @SerializedName("from_user")
  @Expose
  public ModelUser fromUser;
  @SerializedName("to_user")
  @Expose
  public ModelUser toUser;
  @SerializedName("transaction_type")
  @Expose
  public String transactionType;

  @SerializedName("quantity")
  @Expose
  public int quantity;

  @SerializedName("media")
  @Expose
  public ModelVideo media;

  @SerializedName("created")
  @Expose
  public String created;

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

  public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
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
