package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhCommentBodyBinding extends ViewDataBinding {
  @NonNull
  public final TextView commentBody;

  @NonNull
  public final FrameLayout commentDislikeBtn;

  @NonNull
  public final ImageView commentDislikeImage;

  @NonNull
  public final FrameLayout commentLikeBtn;

  @NonNull
  public final ImageView commentLikeImage;

  @NonNull
  public final TextView dislikeCount;

  @NonNull
  public final ImageView ivSticker;

  @NonNull
  public final LinearLayout layoutReply;

  @NonNull
  public final LinearLayout layoutSticker;

  @NonNull
  public final TextView likeCount;

  @NonNull
  public final ImageView profileImage;

  @NonNull
  public final TextView profileName;

  @NonNull
  public final TextView replyCount;

  @NonNull
  public final RecyclerView replyRecyclerView;

  @NonNull
  public final FrameLayout report;

  @NonNull
  public final Space space;

  @NonNull
  public final TextView time;

  @NonNull
  public final TextView viewReply;

  protected VhCommentBodyBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView commentBody, FrameLayout commentDislikeBtn, ImageView commentDislikeImage,
      FrameLayout commentLikeBtn, ImageView commentLikeImage, TextView dislikeCount,
      ImageView ivSticker, LinearLayout layoutReply, LinearLayout layoutSticker, TextView likeCount,
      ImageView profileImage, TextView profileName, TextView replyCount,
      RecyclerView replyRecyclerView, FrameLayout report, Space space, TextView time,
      TextView viewReply) {
    super(_bindingComponent, _root, _localFieldCount);
    this.commentBody = commentBody;
    this.commentDislikeBtn = commentDislikeBtn;
    this.commentDislikeImage = commentDislikeImage;
    this.commentLikeBtn = commentLikeBtn;
    this.commentLikeImage = commentLikeImage;
    this.dislikeCount = dislikeCount;
    this.ivSticker = ivSticker;
    this.layoutReply = layoutReply;
    this.layoutSticker = layoutSticker;
    this.likeCount = likeCount;
    this.profileImage = profileImage;
    this.profileName = profileName;
    this.replyCount = replyCount;
    this.replyRecyclerView = replyRecyclerView;
    this.report = report;
    this.space = space;
    this.time = time;
    this.viewReply = viewReply;
  }

  @NonNull
  public static VhCommentBodyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_comment_body, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhCommentBodyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhCommentBodyBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_comment_body, root, attachToRoot, component);
  }

  @NonNull
  public static VhCommentBodyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_comment_body, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhCommentBodyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhCommentBodyBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_comment_body, null, false, component);
  }

  public static VhCommentBodyBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static VhCommentBodyBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhCommentBodyBinding)bind(component, view, notq.dccast.R.layout.vh_comment_body);
  }
}
