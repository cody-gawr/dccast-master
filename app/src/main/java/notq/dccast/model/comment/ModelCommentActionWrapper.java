package notq.dccast.model.comment;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ModelCommentActionWrapper {
  @SerializedName("count") public int totalComment;
  @SerializedName("results") public List<ModelComment> commentList;

  public int getTotalComment() {
    return totalComment;
  }

  public void setTotalComment(int totalComment) {
    this.totalComment = totalComment;
  }

  public List<ModelComment> getCommentList() {
    return commentList;
  }

  public void setCommentList(List<ModelComment> commentList) {
    this.commentList = commentList;
  }

  public int getTotalCommentCount() {
    int replyCount = 0;
    if (commentList != null) {
      for (ModelComment modelComment : commentList) {
        if (modelComment.getReplies() != null) {
          replyCount += modelComment.getReplies().size();
        }
      }
    }
    return totalComment + replyCount;
  }
}
