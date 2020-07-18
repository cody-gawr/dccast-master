package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityNotificationBinding extends ViewDataBinding {
  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final TabLayout tabLayout;

  @NonNull
  public final ViewPager viewPager;

  protected ActivityNotificationBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LayoutBackHeaderBinding header, TabLayout tabLayout, ViewPager viewPager) {
    super(_bindingComponent, _root, _localFieldCount);
    this.header = header;
    setContainedBinding(this.header);;
    this.tabLayout = tabLayout;
    this.viewPager = viewPager;
  }

  @NonNull
  public static ActivityNotificationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_notification, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityNotificationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityNotificationBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_notification, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityNotificationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_notification, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityNotificationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityNotificationBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_notification, null, false, component);
  }

  public static ActivityNotificationBinding bind(@NonNull View view) {
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
  public static ActivityNotificationBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityNotificationBinding)bind(component, view, notq.dccast.R.layout.activity_notification);
  }
}
