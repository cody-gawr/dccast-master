package notq.dccast.model.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import notq.dccast.model.user.ModelUser;

public class ModelComment implements Serializable {
  @SerializedName("id") @Expose private int id;
  @SerializedName("media") @Expose private Integer media;
  @SerializedName("user") @Expose private ModelUser user;
  @SerializedName("text") @Expose private String text;
  @SerializedName("created") @Expose private String created;
  @SerializedName("parent") @Expose private int parent = -1;
  @SerializedName("replies") @Expose private List<ModelComment> replies;
  @SerializedName("like") @Expose private Integer like;
  @SerializedName("dis_like") @Expose private Integer disLike;
  @SerializedName("dccon_url") @Expose private String dcconUrl;
  @SerializedName("liked") @Expose private boolean isLiked;
  @SerializedName("disliked") @Expose private boolean isDisliked;
  private int level = 0;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Integer getMedia() {
    return media;
  }

  public void setMedia(Integer media) {
    this.media = media;
  }

  public ModelUser getUser() {
    return user;
  }

  public void setUser(ModelUser user) {
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

  public int getParent() {
    return parent;
  }

  public void setParent(int parent) {
    this.parent = parent;
  }

  public List<ModelComment> getReplies() {
    return replies;
  }

  public void setReplies(List<ModelComment> replies) {
    this.replies = replies;
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

  public boolean isLiked() {
    return isLiked;
  }

  public void setLiked(boolean liked) {
    isLiked = liked;
  }

  public boolean isDisliked() {
    return isDisliked;
  }

  public void setDisliked(boolean disliked) {
    isDisliked = disliked;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }
}
