package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhNotificationNoticeItemBinding extends ViewDataBinding {
  @NonNull
  public final ImageView ivCheck;

  @NonNull
  public final ImageView ivItem;

  @NonNull
  public final CircleImageView ivUser;

  @NonNull
  public final LinearLayout layoutItem;

  @NonNull
  public final TextView lblBody;

  @NonNull
  public final TextView lblDate;

  @NonNull
  public final TextView lblTitle;

  protected VhNotificationNoticeItemBinding(Object _bindingComponent, View _root,
      int _localFieldCount, ImageView ivCheck, ImageView ivItem, CircleImageView ivUser,
      LinearLayout layoutItem, TextView lblBody, TextView lblDate, TextView lblTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ivCheck = ivCheck;
    this.ivItem = ivItem;
    this.ivUser = ivUser;
    this.layoutItem = layoutItem;
    this.lblBody = lblBody;
    this.lblDate = lblDate;
    this.lblTitle = lblTitle;
  }

  @NonNull
  public static VhNotificationNoticeItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_notification_notice_item, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhNotificationNoticeItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhNotificationNoticeItemBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_notification_notice_item, root, attachToRoot, component);
  }

  @NonNull
  public static VhNotificationNoticeItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_notification_notice_item, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhNotificationNoticeItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhNotificationNoticeItemBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_notification_notice_item, null, false, component);
  }

  public static VhNotificationNoticeItemBinding bind(@NonNull View view) {
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
  public static VhNotificationNoticeItemBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (VhNotificationNoticeItemBinding)bind(component, view, notq.dccast.R.layout.vh_notification_notice_item);
  }
}
