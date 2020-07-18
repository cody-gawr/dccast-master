package notq.dccast.model.ads;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ModelLocate implements Serializable {
  @SerializedName("id")
  @Expose
  public int id;

  @SerializedName("title")
  @Expose
  public String title;

  @SerializedName("media_type")
  @Expose
  public String mediaType;

  @SerializedName("size_width")
  @Expose
  public String sizeWidth;

  @SerializedName("size_height")
  @Expose
  public String sizeHeight;

  @SerializedName("locate_id")
  @Expose
  public String locateId;

  @SerializedName("create_date")
  @Expose
  public String createDate;

  @SerializedName("user")
  @Expose
  public int user;

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

  public String getMediaType() {
    return mediaType;
  }

  public void setMediaType(String mediaType) {
    this.mediaType = mediaType;
  }

  public String getSizeWidth() {
    return sizeWidth;
  }

  public void setSizeWidth(String sizeWidth) {
    this.sizeWidth = sizeWidth;
  }

  public String getSizeHeight() {
    return sizeHeight;
  }

  public void setSizeHeight(String sizeHeight) {
    this.sizeHeight = sizeHeight;
  }

  public String getLocateId() {
    return locateId;
  }

  public void setLocateId(String locateId) {
    this.locateId = locateId;
  }

  public int getUser() {
    return user;
  }

  public void setUser(int user) {
    this.user = user;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }
}
