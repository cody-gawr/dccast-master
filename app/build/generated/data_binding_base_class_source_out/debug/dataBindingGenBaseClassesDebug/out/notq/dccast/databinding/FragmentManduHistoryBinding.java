package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentManduHistoryBinding extends ViewDataBinding {
  @NonNull
  public final FloatingActionButton btnNext;

  @NonNull
  public final FloatingActionButton btnPrev;

  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final LinearLayout layoutNoData;

  @NonNull
  public final TextView lblPageIndex;

  @NonNull
  public final TextView noData;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final SwipeRefreshLayout refresher;

  protected FragmentManduHistoryBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FloatingActionButton btnNext, FloatingActionButton btnPrev, LottieAnimationView dcLoader,
      LinearLayout layoutNoData, TextView lblPageIndex, TextView noData, RecyclerView recyclerView,
      SwipeRefreshLayout refresher) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnNext = btnNext;
    this.btnPrev = btnPrev;
    this.dcLoader = dcLoader;
    this.layoutNoData = layoutNoData;
    this.lblPageIndex = lblPageIndex;
    this.noData = noData;
    this.recyclerView = recyclerView;
    this.refresher = refresher;
  }

  @NonNull
  public static FragmentManduHistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_mandu_history, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentManduHistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentManduHistoryBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_mandu_history, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentManduHistoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_mandu_history, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentManduHistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentManduHistoryBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_mandu_history, null, false, component);
  }

  public static FragmentManduHistoryBinding bind(@NonNull View view) {
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
  public static FragmentManduHistoryBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentManduHistoryBinding)bind(component, view, notq.dccast.R.layout.fragment_mandu_history);
  }
}
