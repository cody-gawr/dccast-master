package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhCommentWriteBinding extends ViewDataBinding {
  @NonNull
  public final TextView addComment;

  @NonNull
  public final FrameLayout btnSendComment;

  @NonNull
  public final FrameLayout btnSendSticker;

  @NonNull
  public final ImageView ivUser;

  @NonNull
  public final LinearLayout layoutAddComment;

  protected VhCommentWriteBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView addComment, FrameLayout btnSendComment, FrameLayout btnSendSticker, ImageView ivUser,
      LinearLayout layoutAddComment) {
    super(_bindingComponent, _root, _localFieldCount);
    this.addComment = addComment;
    this.btnSendComment = btnSendComment;
    this.btnSendSticker = btnSendSticker;
    this.ivUser = ivUser;
    this.layoutAddComment = layoutAddComment;
  }

  @NonNull
  public static VhCommentWriteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_comment_write, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhCommentWriteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhCommentWriteBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_comment_write, root, attachToRoot, component);
  }

  @NonNull
  public static VhCommentWriteBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_comment_write, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhCommentWriteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhCommentWriteBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_comment_write, null, false, component);
  }

  public static VhCommentWriteBinding bind(@NonNull View view) {
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
  public static VhCommentWriteBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhCommentWriteBinding)bind(component, view, notq.dccast.R.layout.vh_comment_write);
  }
}
