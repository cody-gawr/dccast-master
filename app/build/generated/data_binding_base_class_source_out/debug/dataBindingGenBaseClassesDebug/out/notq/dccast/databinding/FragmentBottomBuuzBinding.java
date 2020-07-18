package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.airbnb.lottie.LottieAnimationView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentBottomBuuzBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout btnAdd;

  @NonNull
  public final FrameLayout btnCloseBs;

  @NonNull
  public final FrameLayout btnMinus;

  @NonNull
  public final Button btnSendBuuz;

  @NonNull
  public final LottieAnimationView buuzAnimation;

  @NonNull
  public final EditText buuzCountField;

  @NonNull
  public final TextView buuzPoint;

  @NonNull
  public final LinearLayout layoutManduCount;

  @NonNull
  public final TextView lblBuuzBuy;

  @NonNull
  public final ProgressBar manduLoading;

  @NonNull
  public final TextView manduYourDumpling;

  protected FragmentBottomBuuzBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout btnAdd, FrameLayout btnCloseBs, FrameLayout btnMinus, Button btnSendBuuz,
      LottieAnimationView buuzAnimation, EditText buuzCountField, TextView buuzPoint,
      LinearLayout layoutManduCount, TextView lblBuuzBuy, ProgressBar manduLoading,
      TextView manduYourDumpling) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnAdd = btnAdd;
    this.btnCloseBs = btnCloseBs;
    this.btnMinus = btnMinus;
    this.btnSendBuuz = btnSendBuuz;
    this.buuzAnimation = buuzAnimation;
    this.buuzCountField = buuzCountField;
    this.buuzPoint = buuzPoint;
    this.layoutManduCount = layoutManduCount;
    this.lblBuuzBuy = lblBuuzBuy;
    this.manduLoading = manduLoading;
    this.manduYourDumpling = manduYourDumpling;
  }

  @NonNull
  public static FragmentBottomBuuzBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_buuz, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomBuuzBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomBuuzBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_buuz, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentBottomBuuzBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_buuz, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBottomBuuzBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentBottomBuuzBinding>inflateInternal(inflater, notq.dccast.R.layout.fragment_bottom_buuz, null, false, component);
  }

  public static FragmentBottomBuuzBinding bind(@NonNull View view) {
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
  public static FragmentBottomBuuzBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentBottomBuuzBinding)bind(component, view, notq.dccast.R.layout.fragment_bottom_buuz);
  }
}
