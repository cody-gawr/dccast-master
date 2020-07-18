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
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhRelatedVideoHeaderBinding extends ViewDataBinding {
  @NonNull
  public final ImageView arrow;

  @NonNull
  public final FrameLayout btnDislike;

  @NonNull
  public final ImageView btnDislikeImage;

  @NonNull
  public final FrameLayout btnExpandDescription;

  @NonNull
  public final FrameLayout btnLike;

  @NonNull
  public final ImageView btnLikeImage;

  @NonNull
  public final TextView category;

  @NonNull
  public final TextView date;

  @NonNull
  public final TextView description;

  @NonNull
  public final LinearLayout descriptionContainer;

  @NonNull
  public final TextView dislikeCount;

  @NonNull
  public final CardView itemCrown;

  @NonNull
  public final TextView lblFollow;

  @NonNull
  public final TextView likeCount;

  @NonNull
  public final CircleImageView profileImage;

  @NonNull
  public final TextView profileName;

  @NonNull
  public final TextView title;

  @NonNull
  public final TextView viewCount;

  protected VhRelatedVideoHeaderBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView arrow, FrameLayout btnDislike, ImageView btnDislikeImage,
      FrameLayout btnExpandDescription, FrameLayout btnLike, ImageView btnLikeImage,
      TextView category, TextView date, TextView description, LinearLayout descriptionContainer,
      TextView dislikeCount, CardView itemCrown, TextView lblFollow, TextView likeCount,
      CircleImageView profileImage, TextView profileName, TextView title, TextView viewCount) {
    super(_bindingComponent, _root, _localFieldCount);
    this.arrow = arrow;
    this.btnDislike = btnDislike;
    this.btnDislikeImage = btnDislikeImage;
    this.btnExpandDescription = btnExpandDescription;
    this.btnLike = btnLike;
    this.btnLikeImage = btnLikeImage;
    this.category = category;
    this.date = date;
    this.description = description;
    this.descriptionContainer = descriptionContainer;
    this.dislikeCount = dislikeCount;
    this.itemCrown = itemCrown;
    this.lblFollow = lblFollow;
    this.likeCount = likeCount;
    this.profileImage = profileImage;
    this.profileName = profileName;
    this.title = title;
    this.viewCount = viewCount;
  }

  @NonNull
  public static VhRelatedVideoHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_related_video_header, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhRelatedVideoHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhRelatedVideoHeaderBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_related_video_header, root, attachToRoot, component);
  }

  @NonNull
  public static VhRelatedVideoHeaderBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_related_video_header, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhRelatedVideoHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhRelatedVideoHeaderBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_related_video_header, null, false, component);
  }

  public static VhRelatedVideoHeaderBinding bind(@NonNull View view) {
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
  public static VhRelatedVideoHeaderBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhRelatedVideoHeaderBinding)bind(component, view, notq.dccast.R.layout.vh_related_video_header);
  }
}
