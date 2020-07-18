package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Deprecated;
import java.lang.Object;
import notq.dccast.custom_view.DMotionLayout;

public abstract class ActivityGroupDetailBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appBar;

  @NonNull
  public final View backgroundDim;

  @NonNull
  public final FrameLayout bottomSheet;

  @NonNull
  public final FrameLayout containerForGroupVideo;

  @NonNull
  public final LinearLayout descriptionLayout;

  @NonNull
  public final View dim;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final CircleImageView ivGroupProfileImg;

  @NonNull
  public final LinearLayout layoutHeader;

  @NonNull
  public final LinearLayout layoutStartLive;

  @NonNull
  public final LinearLayout layoutUploadVod;

  @NonNull
  public final TextView lblGroupDescription;

  @NonNull
  public final TextView lblGroupName;

  @NonNull
  public final DMotionLayout mainMotionLayout;

  @NonNull
  public final TabLayout tabLayout;

  @NonNull
  public final ViewPager viewPager;

  protected ActivityGroupDetailBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AppBarLayout appBar, View backgroundDim, FrameLayout bottomSheet,
      FrameLayout containerForGroupVideo, LinearLayout descriptionLayout, View dim,
      LayoutBackHeaderBinding header, CircleImageView ivGroupProfileImg, LinearLayout layoutHeader,
      LinearLayout layoutStartLive, LinearLayout layoutUploadVod, TextView lblGroupDescription,
      TextView lblGroupName, DMotionLayout mainMotionLayout, TabLayout tabLayout,
      ViewPager viewPager) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appBar = appBar;
    this.backgroundDim = backgroundDim;
    this.bottomSheet = bottomSheet;
    this.containerForGroupVideo = containerForGroupVideo;
    this.descriptionLayout = descriptionLayout;
    this.dim = dim;
    this.header = header;
    setContainedBinding(this.header);;
    this.ivGroupProfileImg = ivGroupProfileImg;
    this.layoutHeader = layoutHeader;
    this.layoutStartLive = layoutStartLive;
    this.layoutUploadVod = layoutUploadVod;
    this.lblGroupDescription = lblGroupDescription;
    this.lblGroupName = lblGroupName;
    this.mainMotionLayout = mainMotionLayout;
    this.tabLayout = tabLayout;
    this.viewPager = viewPager;
  }

  @NonNull
  public static ActivityGroupDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_group_detail, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityGroupDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityGroupDetailBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_group_detail, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityGroupDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_group_detail, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityGroupDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityGroupDetailBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_group_detail, null, false, component);
  }

  public static ActivityGroupDetailBinding bind(@NonNull View view) {
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
  public static ActivityGroupDetailBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityGroupDetailBinding)bind(component, view, notq.dccast.R.layout.activity_group_detail);
  }
}
