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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.airbnb.lottie.LottieAnimationView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentMyChannelBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout container;

  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final LinearLayout layoutLive;

  @NonNull
  public final LinearLayout layoutNoData;

  @NonNull
  public final TextView lblNoVideo;

  @NonNull
  public final TextView lblVodCount;

  @NonNull
  public final TextView liveTitle;

  @NonNull
  public final FrameLayout liveVideo;

  @NonNull
  public final TextView noData;

  @NonNull
  public final ImageView profileImage;

  @NonNull
  public final TextView profileName;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final SwipeRefreshLayout refresher;

  @NonNull
  public final TextView status;

  @NonNull
  public final LinearLayout statusContainer;

  @NonNull
  public final ImageView thumbnail;

  protected FragmentMyChannelBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout container, LottieAnimationView dcLoader, LinearLayout layoutLive,
      LinearLayout layoutNoData, TextView lblNoVideo, TextView lblVodCount, TextView liveTitle,
      FrameLayout liveVideo, TextView noData, ImageView profileImage, TextView profileName,
      RecyclerView recyclerView, SwipeRefreshLayout refresher, TextView status,
      LinearLayout statusContainer, ImageView thumbnail) {
    super(_bindingComponent, _root, _localFieldCount);
    this.container = container;
    this.dcLoader = dcLoader;
    this.layoutLive = layoutLive;
    this.layoutNoData = layoutNoData;
    this.lblNoVideo = lblNoVideo;
    this.lblVodCount = lblVodCount;
    this.liveTitle = liveTitle;
    this.liveVideo = liveVideo;
    this.noData = noData;
    this.profileImage = profileImage;
    this.profileName = profileName;
    this.recyclerView = recyclerView;
    this.refresher = refresher;
    this.status = status;
    this.statusContainer = statusContainer;
    this.thumbnail = thumbnail;
  }

  @NonNull
  public static FragmentMyChannelBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_my_channel, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentMyChannelBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentMyChannelBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_my_channel, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentMyChannelBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_my_channel, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentMyChannelBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentMyChannelBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_my_channel, null, false, component);
  }

  public static FragmentMyChannelBinding bind(@NonNull View view) {
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
  public static FragmentMyChannelBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentMyChannelBinding)bind(component, view, notq.dccast.R.layout.fragment_my_channel);
  }
}
