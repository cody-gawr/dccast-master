package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.flexbox.FlexboxLayout;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityAddPeopleBinding extends ViewDataBinding {
  @NonNull
  public final LottieAnimationView dcLoader;

  @NonNull
  public final EditText etSearchUsername;

  @NonNull
  public final FlexboxLayout flexLayout;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final FrameLayout layoutMemberLoad;

  @NonNull
  public final RecyclerView recyclerView;

  protected ActivityAddPeopleBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LottieAnimationView dcLoader, EditText etSearchUsername, FlexboxLayout flexLayout,
      LayoutBackHeaderBinding header, FrameLayout layoutMemberLoad, RecyclerView recyclerView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.dcLoader = dcLoader;
    this.etSearchUsername = etSearchUsername;
    this.flexLayout = flexLayout;
    this.header = header;
    setContainedBinding(this.header);;
    this.layoutMemberLoad = layoutMemberLoad;
    this.recyclerView = recyclerView;
  }

  @NonNull
  public static ActivityAddPeopleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_add_people, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityAddPeopleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityAddPeopleBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_add_people, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityAddPeopleBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_add_people, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityAddPeopleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityAddPeopleBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_add_people, null, false, component);
  }

  public static ActivityAddPeopleBinding bind(@NonNull View view) {
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
  public static ActivityAddPeopleBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityAddPeopleBinding)bind(component, view, notq.dccast.R.layout.activity_add_people);
  }
}
