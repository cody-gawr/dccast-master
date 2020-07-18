package notq.dccast.model.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ModelNotification implements Serializable {
  @SerializedName("id")
  @Expose
  public int id;

  @SerializedName("title")
  @Expose
  public String title;

  @SerializedName("text")
  @Expose
  public String text;

  @SerializedName("category")
  @Expose
  public String category;

  @SerializedName("thumbnail")
  @Expose
  public String thumbnail;

  @SerializedName("send_datetime")
  @Expose
  public String sendDateTime;

  @SerializedName("is_read")
  @Expose
  public boolean isRead;
  @SerializedName("to_user")
  @Expose
  public int toUser;
  @SerializedName("content_id")
  @Expose
  public int videoid = 0;

  public int getVideoid() {
    return videoid;
  }

  public void setVideoid(int videoid) {
    this.videoid = videoid;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public String getSendDateTime() {
    return sendDateTime;
  }

  public void setSendDateTime(String sendDateTime) {
    this.sendDateTime = sendDateTime;
  }

  public boolean isRead() {
    return isRead;
  }

  public void setRead(boolean read) {
    isRead = read;
  }

  public int getToUser() {
    return toUser;
  }

  public void setToUser(int toUser) {
    this.toUser = toUser;
  }
}
