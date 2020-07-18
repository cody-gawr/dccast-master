package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentBottomAddCommentBinding extends ViewDataBinding {
  @NonNull
  public final AppCompatEditText addComment;

  @NonNull
  public final FrameLayout btnEditCommentClose;

  @NonNull
  public final FrameLayout btnSendComment;

  @NonNull
  public final FrameLayout btnSendSticker;

  @NonNull
  public final FrameLayout editCommentBottomSheet;

  @NonNull
  public final ImageView ivEditUser;

  @NonNull
  public final TextView lblTitle;

  @NonNull
  public final FrameLayout rootLayout;

  protected FragmentBottomAddCommentBinding(Object _bindingComponent, View _root,
      int _localFieldCount, AppCompatEditText addComment, FrameLayout btnEditCommentClose,
      FrameLayout btnSendComment, FrameLayout btnSendSticker, FrameLayout editCommentBottomSheet,
      ImageView ivEditUser, TextView lblTitle, FrameLayout rootLayout) {
    super(_bindingComponent, _root, _localFieldCount);
    this.addComment = addComment;
    this.btnEditCommentClose = btnEditCommentClose;
    this.btnSendComment = btnSendComment;
    this.btnSendSticker = btnSendSticker;
    this.editCommentBottomSheet = editCommentBottomSheet;
    this.ivEditUser = ivEditUser;
    this.lblTitle = lblTitle;
    this.rootLayout = rootLayout;
  }

  @NonNull
  public static FragmentBottomAddCommentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_add_comment, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomAddCommentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomAddCommentBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_add_comment, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentBottomAddCommentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_add_comment, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomAddCommentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomAddCommentBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_add_comment, null, false, component);
  }

  public static FragmentBottomAddCommentBinding bind(@NonNull View view) {
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
  public static FragmentBottomAddCommentBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (FragmentBottomAddCommentBinding)bind(component, view, notq.dccast.R.layout.fragment_bottom_add_comment);
  }
}
