package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivitySearchFollowersBinding extends ViewDataBinding {
  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final EditText etSearch;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final LinearLayout layoutNoData;

  @NonNull
  public final TextView lblCancel;

  @NonNull
  public final TextView noData;

  @NonNull
  public final RecyclerView recyclerView;

  protected ActivitySearchFollowersBinding(Object _bindingComponent, View _root,
      int _localFieldCount, LottieAnimationView dcLoader, EditText etSearch,
      LayoutBackHeaderBinding header, LinearLayout layoutNoData, TextView lblCancel,
      TextView noData, RecyclerView recyclerView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.dcLoader = dcLoader;
    this.etSearch = etSearch;
    this.header = header;
    setContainedBinding(this.header);;
    this.layoutNoData = layoutNoData;
    this.lblCancel = lblCancel;
    this.noData = noData;
    this.recyclerView = recyclerView;
  }

  @NonNull
  public static ActivitySearchFollowersBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_search_followers, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivitySearchFollowersBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivitySearchFollowersBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_search_followers, root, attachToRoot, component);
  }

  @NonNull
  public static ActivitySearchFollowersBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_search_followers, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivitySearchFollowersBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivitySearchFollowersBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_search_followers, null, false, component);
  }

  public static ActivitySearchFollowersBinding bind(@NonNull View view) {
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
  public static ActivitySearchFollowersBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (ActivitySearchFollowersBinding)bind(component, view, notq.dccast.R.layout.activity_search_followers);
  }
}
