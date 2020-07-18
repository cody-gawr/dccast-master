package notq.dccast.model.comment;

import com.google.gson.annotations.SerializedName;

public class ModelCommentActionResponse {
  @SerializedName("id") int id;
  @SerializedName("is_like") boolean isLike;
  @SerializedName("is_dislike") boolean isDislike;
  @SerializedName("comment") int commentId;
  @SerializedName("user") int userId;
  @SerializedName("created") String createdDate;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isLike() {
    return isLike;
  }

  public void setLike(boolean like) {
    isLike = like;
  }

  public boolean isDislike() {
    return isDislike;
  }

  public void setDislike(boolean dislike) {
    isDislike = dislike;
  }

  public int getCommentId() {
    return commentId;
  }

  public void setCommentId(int commentId) {
    this.commentId = commentId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }
}
