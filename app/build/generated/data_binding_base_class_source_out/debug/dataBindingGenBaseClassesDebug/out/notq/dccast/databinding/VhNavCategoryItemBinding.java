package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;
import notq.dccast.model.category.CategoryItem;

public abstract class VhNavCategoryItemBinding extends ViewDataBinding {
  @NonNull
  public final ImageView categoryImg;

  @NonNull
  public final TextView categoryTitle;

  @Bindable
  protected CategoryItem mCategory;

  protected VhNavCategoryItemBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView categoryImg, TextView categoryTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.categoryImg = categoryImg;
    this.categoryTitle = categoryTitle;
  }

  public abstract void setCategory(@Nullable CategoryItem category);

  @Nullable
  public CategoryItem getCategory() {
    return mCategory;
  }

  @NonNull
  public static VhNavCategoryItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_nav_category_item, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhNavCategoryItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhNavCategoryItemBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_nav_category_item, root, attachToRoot, component);
  }

  @NonNull
  public static VhNavCategoryItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_nav_category_item, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhNavCategoryItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhNavCategoryItemBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_nav_category_item, null, false, component);
  }

  public static VhNavCategoryItemBinding bind(@NonNull View view) {
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
  public static VhNavCategoryItemBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhNavCategoryItemBinding)bind(component, view, notq.dccast.R.layout.vh_nav_category_item);
  }
}
