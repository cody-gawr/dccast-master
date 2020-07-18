package notq.dccast.screens.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.databinding.VhCommentBodyBinding;
import notq.dccast.model.comment.ModelComment;
import notq.dccast.screens.home.interfaces.ReplyButtonListener;
import notq.dccast.util.Util;

public class AdapterSubComments extends RecyclerView.Adapter {
  public List<ModelComment> comments;
  private Context context;
  private ReplyButtonListener replyButtonListener;
  private boolean isComment;
  private int parentId = -1;

  public AdapterSubComments(Context context, ReplyButtonListener replyButtonListener,
      boolean isComment) {
    this.context = context;
    this.replyButtonListener = replyButtonListener;
    this.isComment = isComment;
    comments = new ArrayList<>();
  }

  public boolean isComment() {
    return isComment;
  }

  public void setComments(List<ModelComment> comments) {
    this.comments = comments;
    notifyDataSetChanged();
  }

  public void addComment(int index, ModelComment comment) {
    if (!this.comments.contains(comment)) {
      if (index >= 0) {
        this.comments.add(index, comment);
      } else {
        this.comments.add(comment);
      }
      notifyDataSetChanged();
    }
  }

  @NonNull @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);

    VhCommentBodyBinding bodyBinding =
        DataBindingUtil.inflate(layoutInflater, R.layout.vh_comment_body, parent, false);

    return new ViewHolderBody(bodyBinding);
  }

  @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    ((ViewHolderBody) holder).setData(position);
  }

  @Override public int getItemCount() {
    return comments == null ? 0 : comments.size();
  }

  public void clearComments() {
    comments = new ArrayList<>();
    notifyDataSetChanged();
  }

  private class ViewHolderBody extends RecyclerView.ViewHolder implements View.OnClickListener {
    private VhCommentBodyBinding binding;
    private ModelComment modelComment;
    private int position;

    ViewHolderBody(VhCommentBodyBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(int position) {
      this.position = position;
      binding.report.setOnClickListener(this);
      binding.commentLikeBtn.setOnClickListener(this);
      binding.commentDislikeBtn.setOnClickListener(this);

      if (this.position == comments.size()) {
        binding.space.setVisibility(View.VISIBLE);
      } else {
        binding.space.setVisibility(View.GONE);
      }

      if (!comments.isEmpty()) {
        modelComment = comments.get(this.position);
        if (modelComment.getDcconUrl() != null) {
          binding.layoutSticker.setVisibility(View.VISIBLE);
          binding.commentBody.setVisibility(View.GONE);

          Glide.with(context)
              .load(Util.getValidateUrl(modelComment.getDcconUrl()))
              .into(binding.ivSticker);
        } else {
          binding.layoutSticker.setVisibility(View.GONE);
          binding.commentBody.setVisibility(View.VISIBLE);
          binding.commentBody.setText(modelComment.getText());
        }
        binding.profileName.setText(modelComment.getUser().nickName);

        if (modelComment.getCreated().length() > 3) {
          binding.time.setText(modelComment.getCreated().substring(0, 10));
        } else {
          binding.time.setText(modelComment.getCreated());
        }

        binding.viewReply.setVisibility(View.GONE);
        binding.layoutReply.setVisibility(View.GONE);

        int profileSize = DCCastApplication.utils.pxFromDp(32);
        Glide.with(context)
            .load(Util.getValidateUrl(modelComment.getUser().getProfileImage()))
            .apply(
                new RequestOptions()
                    .override(profileSize, profileSize)
                    .placeholder(R.drawable.ic_profile_placeholder)
                    .centerCrop())
            .into(binding.profileImage);

        binding.likeCount.setText(String.valueOf(modelComment.getLike()));
        binding.dislikeCount.setText(String.valueOf(modelComment.getDisLike()));

        if (modelComment.isLiked()) {
          binding.likeCount.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        } else {
          binding.likeCount.setTextColor(
              ContextCompat.getColor(context, R.color.bottom_sheet_default));
        }

        if (modelComment.isDisliked()) {
          binding.dislikeCount.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        } else {
          binding.dislikeCount.setTextColor(
              ContextCompat.getColor(context, R.color.bottom_sheet_default));
        }

        binding.commentLikeImage.setColorFilter(ContextCompat.getColor(context,
            modelComment.isLiked() ? R.color.colorPrimary : R.color.bottom_sheet_default),
            android.graphics.PorterDuff.Mode.MULTIPLY);

        binding.commentDislikeImage.setColorFilter(ContextCompat.getColor(context,
            modelComment.isDisliked() ? R.color.colorPrimary : R.color.bottom_sheet_default),
            android.graphics.PorterDuff.Mode.MULTIPLY);
      }
    }

    @Override public void onClick(View view) {
      switch (view.getId()) {
        case R.id.view_reply: {
          //replyButtonListener.onClickReply(position, modelComment);
          break;
        }

        case R.id.report: {
          replyButtonListener.onClickMore(isComment, modelComment);
          break;
        }

        case R.id.comment_like_btn: {
          modelComment.setLiked(!modelComment.isLiked());

          if (modelComment.isLiked()) {
            modelComment.setLike(modelComment.getLike() + 1);
          } else if (modelComment.getLike() > 0) {
            modelComment.setLike(modelComment.getLike() - 1);
          }

          if (modelComment.isLiked() && modelComment.isDisliked()) {
            binding.commentDislikeBtn.performClick();
          }

          replyButtonListener.onCommentLikeClicked(modelComment);
          notifyItemChanged(position);
          break;
        }

        case R.id.comment_dislike_btn: {
          modelComment.setDisliked(!modelComment.isDisliked());

          if (modelComment.isDisliked()) {
            modelComment.setDisLike(modelComment.getDisLike() + 1);
          } else if (modelComment.getDisLike() > 0) {
            modelComment.setDisLike(modelComment.getDisLike() - 1);
          }

          if (modelComment.isLiked() && modelComment.isDisliked()) {
            binding.commentLikeBtn.performClick();
          }

          replyButtonListener.onCommentDislikeClicked(modelComment);
          notifyItemChanged(position);
          break;
        }
      }
    }
  }
}