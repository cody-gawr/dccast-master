package notq.dccast.model.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelCreateCommentResponse {
  @SerializedName("id") @Expose private Integer id;
  @SerializedName("media") @Expose private Integer media;
  @SerializedName("user") @Expose private Integer user;
  @SerializedName("text") @Expose private String text;
  @SerializedName("created") @Expose private String created;
  @SerializedName("parent") @Expose private Integer parent;
  @SerializedName("like") @Expose private Integer like;
  @SerializedName("dis_like") @Expose private Integer disLike;
  @SerializedName("dccon_url") @Expose private String dcconUrl;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getMedia() {
    return media;
  }

  public void setMedia(Integer media) {
    this.media = media;
  }

  public Integer getUser() {
    return user;
  }

  public void setUser(Integer user) {
    this.user = user;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public Integer getParent() {
    return parent;
  }

  public void setParent(Integer parent) {
    this.parent = parent;
  }

  public Integer getLike() {
    return like;
  }

  public void setLike(Integer like) {
    this.like = like;
  }

  public Integer getDisLike() {
    return disLike;
  }

  public void setDisLike(Integer disLike) {
    this.disLike = disLike;
  }

  public String getDcconUrl() {
    return dcconUrl;
  }

  public void setDcconUrl(String dcconUrl) {
    this.dcconUrl = dcconUrl;
  }
}
