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
import com.airbnb.lottie.LottieAnimationView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentSearchResultPageBinding extends ViewDataBinding {
  @NonNull
  public final LottieAnimationView loader;

  @NonNull
  public final LinearLayout placeholder;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final TextView searchResult;

  protected FragmentSearchResultPageBinding(Object _bindingComponent, View _root,
      int _localFieldCount, LottieAnimationView loader, LinearLayout placeholder,
      RecyclerView recyclerView, TextView searchResult) {
    super(_bindingComponent, _root, _localFieldCount);
    this.loader = loader;
    this.placeholder = placeholder;
    this.recyclerView = recyclerView;
    this.searchResult = searchResult;
  }

  @NonNull
  public static FragmentSearchResultPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_search_result_page, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentSearchResultPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentSearchResultPageBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_search_result_page, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentSearchResultPageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_search_result_page, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentSearchResultPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentSearchResultPageBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_search_result_page, null, false, component);
  }

  public static FragmentSearchResultPageBinding bind(@NonNull View view) {
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
  public static FragmentSearchResultPageBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (FragmentSearchResultPageBinding)bind(component, view, notq.dccast.R.layout.fragment_search_result_page);
  }
}
