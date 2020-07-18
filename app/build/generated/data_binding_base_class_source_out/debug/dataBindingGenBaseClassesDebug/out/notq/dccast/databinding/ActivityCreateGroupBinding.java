package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityCreateGroupBinding extends ViewDataBinding {
  @NonNull
  public final Button createGroupBtn;

  @NonNull
  public final TextInputLayout descriptionInputLayout;

  @NonNull
  public final TextInputEditText descriptionInputText;

  @NonNull
  public final LayoutBackHeaderBinding header;

  @NonNull
  public final CardView ivChange;

  @NonNull
  public final CircleImageView ivProfileImg;

  @NonNull
  public final FrameLayout layoutLoadImage;

  @NonNull
  public final TextView lblAddPeople;

  @NonNull
  public final LinearLayout leaveGroup;

  @NonNull
  public final TextInputLayout nameInputLayout;

  @NonNull
  public final TextInputEditText nameInputText;

  @NonNull
  public final RecyclerView recyclerView;

  protected ActivityCreateGroupBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button createGroupBtn, TextInputLayout descriptionInputLayout,
      TextInputEditText descriptionInputText, LayoutBackHeaderBinding header, CardView ivChange,
      CircleImageView ivProfileImg, FrameLayout layoutLoadImage, TextView lblAddPeople,
      LinearLayout leaveGroup, TextInputLayout nameInputLayout, TextInputEditText nameInputText,
      RecyclerView recyclerView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.createGroupBtn = createGroupBtn;
    this.descriptionInputLayout = descriptionInputLayout;
    this.descriptionInputText = descriptionInputText;
    this.header = header;
    setContainedBinding(this.header);;
    this.ivChange = ivChange;
    this.ivProfileImg = ivProfileImg;
    this.layoutLoadImage = layoutLoadImage;
    this.lblAddPeople = lblAddPeople;
    this.leaveGroup = leaveGroup;
    this.nameInputLayout = nameInputLayout;
    this.nameInputText = nameInputText;
    this.recyclerView = recyclerView;
  }

  @NonNull
  public static ActivityCreateGroupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_create_group, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityCreateGroupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityCreateGroupBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_create_group, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityCreateGroupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_create_group, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityCreateGroupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityCreateGroupBinding>inflateInternal(inflater, notq.dccast.R.layout.activity_create_group, null, false, component);
  }

  public static ActivityCreateGroupBinding bind(@NonNull View view) {
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
  public static ActivityCreateGroupBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityCreateGroupBinding)bind(component, view, notq.dccast.R.layout.activity_create_group);
  }
}
