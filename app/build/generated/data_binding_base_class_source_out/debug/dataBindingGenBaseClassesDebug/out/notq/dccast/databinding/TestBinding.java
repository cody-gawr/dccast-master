package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
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

public abstract class TestBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout bottomSheet;

  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final View dim;

  @NonNull
  public final EditText etSearch;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final LinearLayout layoutNoData;

  @NonNull
  public final LinearLayout layoutSort;

  @NonNull
  public final TextView lblSortValue;

  @NonNull
  public final TextView noData;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final SwipeRefreshLayout refresher;

  protected TestBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout bottomSheet, LottieAnimationView dcLoader, View dim, EditText etSearch,
      LayoutBackHeaderBinding header, LinearLayout layoutNoData, LinearLayout layoutSort,
      TextView lblSortValue, TextView noData, RecyclerView recyclerView,
      SwipeRefreshLayout refresher) {
    super(_bindingComponent, _root, _localFieldCount);
    this.bottomSheet = bottomSheet;
    this.dcLoader = dcLoader;
    this.dim = dim;
    this.etSearch = etSearch;
    this.header = header;
    setContainedBinding(this.header);;
    this.layoutNoData = layoutNoData;
    this.layoutSort = layoutSort;
    this.lblSortValue = lblSortValue;
    this.noData = noData;
    this.recyclerView = recyclerView;
    this.refresher = refresher;
  }

  @NonNull
  public static TestBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.test, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static TestBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<TestBinding>inflateInternal(inflater, notq.dccast.R.layout.test, root, attachToRoot, component);
  }

  @NonNull
  public static TestBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.test, null, false, component)
   */
  @NonNull
  @Deprecated
  public static TestBinding inflate(@NonNull LayoutInflater inflater, @Nullable Object component) {
    return ViewDataBinding.<TestBinding>inflateInternal(inflater, notq.dccast.R.layout.test, null, false, component);
  }

  public static TestBinding bind(@NonNull View view) {
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
  public static TestBinding bind(@NonNull View view, @Nullable Object component) {
    return (TestBinding)bind(component, view, notq.dccast.R.layout.test);
  }
}
