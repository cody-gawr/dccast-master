package notq.dccast.screens.home.interfaces;

import notq.dccast.model.comment.ModelComment;

public interface ReplyButtonListener {
  void onClickReply(int position, ModelComment modelComment);

  void onClickMore(boolean isComment, ModelComment modelComment);

  void onCommentLikeClicked(ModelComment modelComment);

  void onCommentDislikeClicked(ModelComment modelComment);
}
