package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentBottomLiveTitleBinding extends ViewDataBinding {
  @NonNull
  public final Button btnCancel;

  @NonNull
  public final Button btnEnter;

  @NonNull
  public final FrameLayout btnTitleClose;

  @NonNull
  public final EditText etLiveTitle;

  @NonNull
  public final LinearLayout layoutEnter;

  @NonNull
  public final LinearLayout layoutScroll;

  protected FragmentBottomLiveTitleBinding(Object _bindingComponent, View _root,
      int _localFieldCount, Button btnCancel, Button btnEnter, FrameLayout btnTitleClose,
      EditText etLiveTitle, LinearLayout layoutEnter, LinearLayout layoutScroll) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnCancel = btnCancel;
    this.btnEnter = btnEnter;
    this.btnTitleClose = btnTitleClose;
    this.etLiveTitle = etLiveTitle;
    this.layoutEnter = layoutEnter;
    this.layoutScroll = layoutScroll;
  }

  @NonNull
  public static FragmentBottomLiveTitleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_live_title, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomLiveTitleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomLiveTitleBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_live_title, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentBottomLiveTitleBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_live_title, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomLiveTitleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomLiveTitleBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_live_title, null, false, component);
  }

  public static FragmentBottomLiveTitleBinding bind(@NonNull View view) {
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
  public static FragmentBottomLiveTitleBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (FragmentBottomLiveTitleBinding)bind(component, view, notq.dccast.R.layout.fragment_bottom_live_title);
  }
}
