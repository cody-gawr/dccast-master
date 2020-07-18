package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentBottomCommentBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout btnCommentClose;

  @NonNull
  public final TextView btnDelete;

  @NonNull
  public final TextView btnEdit;

  @NonNull
  public final FrameLayout btnRefresh;

  @NonNull
  public final TextView btnReply;

  @NonNull
  public final FrameLayout btnReplyClose;

  @NonNull
  public final TextView btnReport;

  @NonNull
  public final RecyclerView commentsRecyclerView;

  @NonNull
  public final View dim;

  @NonNull
  public final CoordinatorLayout mainLayout;

  @NonNull
  public final ProgressBar progressBarVod;

  @NonNull
  public final FrameLayout replyBottomSheet;

  @NonNull
  public final RecyclerView replyRecyclerView;

  @NonNull
  public final LinearLayout reportBottomSheet;

  @NonNull
  public final TextView totalComments;

  @NonNull
  public final TextView totalReplies;

  protected FragmentBottomCommentBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout btnCommentClose, TextView btnDelete, TextView btnEdit, FrameLayout btnRefresh,
      TextView btnReply, FrameLayout btnReplyClose, TextView btnReport,
      RecyclerView commentsRecyclerView, View dim, CoordinatorLayout mainLayout,
      ProgressBar progressBarVod, FrameLayout replyBottomSheet, RecyclerView replyRecyclerView,
      LinearLayout reportBottomSheet, TextView totalComments, TextView totalReplies) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnCommentClose = btnCommentClose;
    this.btnDelete = btnDelete;
    this.btnEdit = btnEdit;
    this.btnRefresh = btnRefresh;
    this.btnReply = btnReply;
    this.btnReplyClose = btnReplyClose;
    this.btnReport = btnReport;
    this.commentsRecyclerView = commentsRecyclerView;
    this.dim = dim;
    this.mainLayout = mainLayout;
    this.progressBarVod = progressBarVod;
    this.replyBottomSheet = replyBottomSheet;
    this.replyRecyclerView = replyRecyclerView;
    this.reportBottomSheet = reportBottomSheet;
    this.totalComments = totalComments;
    this.totalReplies = totalReplies;
  }

  @NonNull
  public static FragmentBottomCommentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_comment, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomCommentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomCommentBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_comment, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentBottomCommentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_comment, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomCommentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomCommentBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_comment, null, false, component);
  }

  public static FragmentBottomCommentBinding bind(@NonNull View view) {
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
  public static FragmentBottomCommentBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentBottomCommentBinding)bind(component, view, notq.dccast.R.layout.fragment_bottom_comment);
  }
}
