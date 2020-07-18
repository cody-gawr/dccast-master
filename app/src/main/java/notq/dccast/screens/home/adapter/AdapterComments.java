package notq.dccast.screens.home.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.CommentAPIInterface;
import notq.dccast.databinding.VhCommentBodyBinding;
import notq.dccast.databinding.VhCommentNoDataBinding;
import notq.dccast.databinding.VhCommentWriteBinding;
import notq.dccast.databinding.VhHomeLoadMoreBinding;
import notq.dccast.model.comment.ModelComment;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.interfaces.CommentListener;
import notq.dccast.screens.home.interfaces.ReplyButtonListener;
import notq.dccast.screens.home.view_holders.VHCommentNoData;
import notq.dccast.screens.home.view_holders.VHLoadMore;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterComments extends RecyclerView.Adapter {
  private final int VIEW_TYPE_WRITE = 1;
  private final int VIEW_TYPE_COMMENT = 2;
  private final int VIEW_TYPE_NO_COMMENT = 3;
  private final int VIEW_TYPE_LOADER = 4;
  public List<ModelComment> comments;
  private Context context;
  private boolean isNeedShowLoader = false;
  private VHCommentNoData vhNoData;
  private CommentListener commentListener;
  private ReplyButtonListener replyButtonListener;
  private boolean isComment;
  private int parentId = -1;
  private Activity activity;

  public AdapterComments(Activity activity, Context context,
      ReplyButtonListener replyButtonListener,
      CommentListener commentListener, boolean isComment) {
    this.context = context;
    this.activity = activity;
    this.replyButtonListener = replyButtonListener;
    this.commentListener = commentListener;
    this.isComment = isComment;
    comments = new ArrayList<>();
  }

  public int getParentId() {
    return parentId;
  }

  public void setParentId(int id) {
    this.parentId = id;
  }

  public boolean isComment() {
    return isComment;
  }

  public void setComments(List<ModelComment> comments) {
    this.comments = comments;
    notifyDataSetChanged();
  }

  public void addComment(ModelComment comment) {
    this.comments.add(0, comment);
    notifyItemInserted(0);
    notifyItemChanged(1);
  }

  public void updateComment(ModelComment comment) {
    int index = -1;
    for (int i = 0; i < this.comments.size(); i++) {
      if (this.comments.get(i).getId() == comment.getId()) {
        index = i;
        this.comments.set(index, comment);
        break;
      }

      List<ModelComment> replies = this.comments.get(i).getReplies();
      if (replies != null) {
        for (int j = 0; j < replies.size(); j++) {
          if (replies.get(j).getId() == comment.getId()) {
            this.comments.get(i).getReplies().set(j, comment);
            break;
          }
        }
      }
    }
    notifyDataSetChanged();
  }

  public void deleteComment(ModelComment comment) {
    int index = -1;
    for (int i = 0; i < this.comments.size(); i++) {
      if (index != -1) {
        break;
      }
      if (this.comments.get(i).getId() == comment.getId()) {
        index = i;
        this.comments.remove(index);
        break;
      }

      List<ModelComment> replies = this.comments.get(i).getReplies();
      if (replies != null) {
        for (int j = 0; j < replies.size(); j++) {
          if (replies.get(j).getId() == comment.getId()) {
            this.comments.get(i).getReplies().remove(j);
            index = i;
            break;
          }
        }
      }
    }
  }

  public void showNoDataViewHolder() {
    if (vhNoData != null) {
      vhNoData.showWithText(isComment ? context.getString(R.string.comment_no_data)
          : context.getString(R.string.reply_no_data));
      notifyItemChanged(1);
    }
  }

  public void hideNoDataViewHolder() {
    if (vhNoData != null) {
      vhNoData.hide();
      notifyItemChanged(1);
    }
  }

  public void showLoadFirst() {
    isNeedShowLoader = true;
    notifyItemChanged(2);
  }

  public void hideLoadFirst() {
    isNeedShowLoader = false;
    notifyItemChanged(2);
  }

  @NonNull @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);

    switch (viewType) {
      case VIEW_TYPE_WRITE: {
        VhCommentWriteBinding writeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.vh_comment_write, parent, false);

        return new ViewHolderWriteComment(writeBinding);
      }

      case VIEW_TYPE_LOADER: {
        VhHomeLoadMoreBinding loadMoreBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.vh_home_load_more, parent, false);
        return new VHLoadMore(loadMoreBinding);
      }

      case VIEW_TYPE_NO_COMMENT: {
        VhCommentNoDataBinding noDataBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.vh_comment_no_data, parent, false);
        vhNoData = new VHCommentNoData(noDataBinding);
        return vhNoData;
      }

      case VIEW_TYPE_COMMENT: {
        VhCommentBodyBinding bodyBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.vh_comment_body, parent, false);

        return new ViewHolderBody(bodyBinding);
      }
    }

    return null;
  }

  @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    switch (getItemViewType(position)) {
      case VIEW_TYPE_COMMENT: {
        ((ViewHolderBody) holder).setData(position);
        break;
      }

      case VIEW_TYPE_LOADER: {
        ((VHLoadMore) holder).notifyLoaderStatusChanged(isNeedShowLoader);
        break;
      }

      case VIEW_TYPE_NO_COMMENT: {
        if (comments.size() > 0) {
          ((VHCommentNoData) holder).hide();
        } else {
          ((VHCommentNoData) holder).show();
        }
        break;
      }
    }
  }

  public int getCommentCount() {
    return comments == null ? 0 : comments.size();
  }

  @Override public int getItemCount() {
    return comments.size() + 3;
  }

  @Override public int getItemViewType(int position) {
    switch (position) {
      case 0: {
        return VIEW_TYPE_WRITE;
      }

      case 1: {
        return VIEW_TYPE_NO_COMMENT;
      }

      case 2: {
        return VIEW_TYPE_LOADER;
      }

      default: {
        return VIEW_TYPE_COMMENT;
      }
    }
  }

  public void clearComments() {
    comments = new ArrayList<>();
    notifyDataSetChanged();
  }

  private void checkCanLikeRequestSend(int commentId, boolean isLike,
      CommentLikeInterface commentLikeInterface) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    Call<JsonObject> call
        = APIClient.getClient().create(CommentAPIInterface.class)
        .checkRequestSend(isLike ? "avail_like" : "avail_dislike", loginUser.getId(), commentId);
    call.enqueue(new Callback<JsonObject>() {
      @Override
      public void onResponse(@NonNull Call<JsonObject> call,
          @NonNull Response<JsonObject> response) {
        JsonObject result = response.body();
        if (result != null && result.has("status")) {
          String status = result.get("status").getAsString();

          if (status.equalsIgnoreCase("yes")) {
            commentLikeInterface.canRequestSend();
          } else {
            Toast.makeText(context, isLike ? context.getString(R.string.comment_already_liked)
                : context.getString(R.string.comment_already_disliked), Toast.LENGTH_LONG).show();
          }
        } else {
          Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_LONG).show();
        }
      }

      @Override public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
        call.cancel();
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  public interface CommentLikeInterface {
    void canRequestSend();
  }

  private class ViewHolderWriteComment extends RecyclerView.ViewHolder {
    @SuppressLint("ClickableViewAccessibility") ViewHolderWriteComment(
        VhCommentWriteBinding binding) {
      super(binding.getRoot());

      binding.layoutAddComment.setOnClickListener(v -> commentListener.onCommentLayoutClicked());

      binding.addComment.setHint(isComment ? context.getString(R.string.add_public_comment)
          : context.getString(R.string.add_public_reply));
    }
  }

  private class ViewHolderBody extends RecyclerView.ViewHolder implements View.OnClickListener {
    private VhCommentBodyBinding binding;
    private ModelComment modelComment;
    private AdapterSubComments adapterReplies;
    private int position;

    ViewHolderBody(VhCommentBodyBinding binding) {
      super(binding.getRoot());
      this.binding = binding;

      binding.replyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
      adapterReplies = new AdapterSubComments(context, replyButtonListener, false);
      binding.replyRecyclerView.setAdapter(adapterReplies);
    }

    void setData(int position) {
      this.position = position;
      int visibledCount = 0;
      if (adapterReplies.getItemCount() > 0) {
        visibledCount = adapterReplies.getItemCount();
      } else {
        adapterReplies.clearComments();
      }

      adapterReplies.notifyDataSetChanged();

      binding.viewReply.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (modelComment.getReplies() != null) {
            List<ModelComment> replies = modelComment.getReplies();
            binding.replyRecyclerView.setVisibility(View.VISIBLE);

            int showedCount = adapterReplies.getItemCount();
            showedCount += 5;
            if (replies.size() < showedCount) {
              showedCount = replies.size();
            }

            for (int i = 0; i < showedCount; i++) {
              adapterReplies.addComment(-1, replies.get(i));
            }

            if (showedCount < replies.size()) {
              binding.viewReply.setVisibility(View.VISIBLE);
              int sizeOfReply_ = replies.size() - showedCount;
              binding.viewReply.setText(
                  String.valueOf(
                      "View " + sizeOfReply_ + " repl" + (sizeOfReply_ > 1 ? "ies" : "y")));
            } else {
              binding.viewReply.setVisibility(View.GONE);
            }
          }
        }
      });
      binding.report.setOnClickListener(this);
      binding.commentLikeBtn.setOnClickListener(this);
      binding.commentDislikeBtn.setOnClickListener(this);

      if (this.position - 3 == comments.size() - 1) {
        binding.space.setVisibility(View.VISIBLE);
      } else {
        binding.space.setVisibility(View.GONE);
      }

      if (!comments.isEmpty()) {
        modelComment = comments.get(this.position - 3);
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

        if (modelComment.getReplies() != null) {
          binding.viewReply.setVisibility(View.VISIBLE);
          binding.replyCount.setText(
              String.valueOf(modelComment.getReplies().size()));

          if (modelComment.getReplies().size() > 0) {
            int endIndex = 5;
            if (modelComment.getReplies().size() < 5) {
              endIndex = modelComment.getReplies().size();
            }

            if (endIndex > 0) {
              binding.replyRecyclerView.setVisibility(View.VISIBLE);
              List<ModelComment> comments = new ArrayList<>();
              for (int i = 0; i < endIndex; i++) {
                comments.add(modelComment.getReplies().get(i));
              }
              adapterReplies.setComments(comments);
            }

            visibledCount = endIndex;
          } else {
            adapterReplies.clearComments();
          }

          if (modelComment.getReplies().size() - visibledCount > 0) {
            binding.viewReply.setVisibility(View.VISIBLE);
            int sizeOfReply_ = modelComment.getReplies().size() - visibledCount;
            binding.viewReply.setText(
                String.valueOf(
                    "View " + sizeOfReply_ + " repl" + (sizeOfReply_ > 1 ? "ies" : "y")));
          } else {
            binding.viewReply.setVisibility(View.GONE);
          }
        } else {
          binding.viewReply.setVisibility(View.GONE);
          binding.replyCount.setText("0");
        }

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
          binding.dislikeCount.setTextColor(
              ContextCompat.getColor(context, R.color.colorPrimary));
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
        case R.id.report: {
          replyButtonListener.onClickMore(isComment, modelComment);
          break;
        }

        case R.id.comment_like_btn: {
          checkCanLikeRequestSend(modelComment.getId(), true, new CommentLikeInterface() {
            @Override public void canRequestSend() {
              modelComment.setLiked(!modelComment.isLiked());

              if (modelComment.isLiked()) {
                modelComment.setLike(modelComment.getLike() + 1);
                modelComment.setDisliked(false);
                int dislike = modelComment.getDisLike() - 1;
                if (dislike < 0) {
                  dislike = 0;
                }
                modelComment.setDisLike(dislike);
              } else if (modelComment.getLike() > 0) {
                int like = modelComment.getLike() - 1;
                if (like < 0) {
                  like = 0;
                }
                modelComment.setLike(like);
              }

              //if (modelComment.isLiked() && modelComment.isDisliked()) {
              //  binding.commentDislikeBtn.performClick();
              //}

              replyButtonListener.onCommentLikeClicked(modelComment);
              notifyItemChanged(position);
            }
          });
          break;
        }

        case R.id.comment_dislike_btn: {
          checkCanLikeRequestSend(modelComment.getId(), false, new CommentLikeInterface() {
            @Override public void canRequestSend() {
              modelComment.setDisliked(!modelComment.isDisliked());

              if (modelComment.isDisliked()) {
                modelComment.setDisLike(modelComment.getDisLike() + 1);
                modelComment.setLiked(false);
                int like = modelComment.getLike() - 1;
                if (like < 0) {
                  like = 0;
                }
                modelComment.setLike(like);
              } else if (modelComment.getDisLike() > 0) {
                int dislike = modelComment.getDisLike() - 1;
                if (dislike < 0) {
                  dislike = 0;
                }
                modelComment.setDisLike(dislike);
              }

              //if (modelComment.isLiked() && modelComment.isDisliked()) {
              //  binding.commentLikeBtn.performClick();
              //}

              replyButtonListener.onCommentDislikeClicked(modelComment);
              notifyItemChanged(position);
            }
          });
          break;
        }
      }
    }
  }
}