package notq.dccast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import notq.dccast.model.group.ModelGroup;

public class ModelGroupVideo implements Serializable {
  @SerializedName("id")
  @Expose
  public int id;
  @SerializedName("group")
  @Expose
  public ModelGroup group;
  @SerializedName("media")
  @Expose
  public ModelVideo media;
  @SerializedName("timestamp")
  @Expose
  public String timestamp;
  @SerializedName("category")
  @Expose
  public String category;
  @SerializedName("orientation")
  @Expose
  public String orientation;
  @SerializedName("live_share")
  @Expose
  public Object liveShare;
  @SerializedName("live_type")
  @Expose
  public Object liveType;
  @SerializedName("live_lock")
  @Expose
  public boolean liveLock;
  @SerializedName("chat_lock")
  @Expose
  public int chatLock;
  @SerializedName("vod_rating")
  @Expose
  public int vodRating;

  public String getOrientation() {
    if (orientation == null) {
      return "";
    }
    return orientation;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public ModelGroup getGroup() {
    return group;
  }

  public void setGroup(ModelGroup group) {
    this.group = group;
  }

  public ModelVideo getMedia() {
    return media;
  }

  public void setMedia(ModelVideo media) {
    this.media = media;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Object getLiveShare() {
    return liveShare;
  }

  public void setLiveShare(Object liveShare) {
    this.liveShare = liveShare;
  }

  public Object getLiveType() {
    return liveType;
  }

  public void setLiveType(Object liveType) {
    this.liveType = liveType;
  }

  public boolean isLiveLock() {
    return liveLock;
  }

  public void setLiveLock(boolean liveLock) {
    this.liveLock = liveLock;
  }

  public int getChatLock() {
    return chatLock;
  }

  public void setChatLock(int chatLock) {
    this.chatLock = chatLock;
  }

  public int getVodRating() {
    return vodRating;
  }

  public void setVodRating(int vodRating) {
    this.vodRating = vodRating;
  }
}
