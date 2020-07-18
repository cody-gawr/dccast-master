package notq.dccast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VhNavMenuBinding extends ViewDataBinding {
  @NonNull
  public final TextView castListText;

  @NonNull
  public final TextView favoriteText;

  @NonNull
  public final TextView homeText;

  @NonNull
  public final ImageView imgCast;

  @NonNull
  public final ImageView imgCastSelected;

  @NonNull
  public final ImageView imgFavorite;

  @NonNull
  public final ImageView imgFavoriteSelected;

  @NonNull
  public final ImageView imgHome;

  @NonNull
  public final ImageView imgHomeSelected;

  @NonNull
  public final ImageView imgMyContent;

  @NonNull
  public final ImageView imgMyContentSelected;

  @NonNull
  public final LinearLayout itemCast;

  @NonNull
  public final FrameLayout itemCastContainer;

  @NonNull
  public final LinearLayout itemFavorite;

  @NonNull
  public final FrameLayout itemFavoriteContainer;

  @NonNull
  public final LinearLayout itemHome;

  @NonNull
  public final FrameLayout itemHomeContainer;

  @NonNull
  public final LinearLayout itemMyContent;

  @NonNull
  public final FrameLayout itemMyContentContainer;

  @NonNull
  public final TextView myContentText;

  protected VhNavMenuBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView castListText, TextView favoriteText, TextView homeText, ImageView imgCast,
      ImageView imgCastSelected, ImageView imgFavorite, ImageView imgFavoriteSelected,
      ImageView imgHome, ImageView imgHomeSelected, ImageView imgMyContent,
      ImageView imgMyContentSelected, LinearLayout itemCast, FrameLayout itemCastContainer,
      LinearLayout itemFavorite, FrameLayout itemFavoriteContainer, LinearLayout itemHome,
      FrameLayout itemHomeContainer, LinearLayout itemMyContent, FrameLayout itemMyContentContainer,
      TextView myContentText) {
    super(_bindingComponent, _root, _localFieldCount);
    this.castListText = castListText;
    this.favoriteText = favoriteText;
    this.homeText = homeText;
    this.imgCast = imgCast;
    this.imgCastSelected = imgCastSelected;
    this.imgFavorite = imgFavorite;
    this.imgFavoriteSelected = imgFavoriteSelected;
    this.imgHome = imgHome;
    this.imgHomeSelected = imgHomeSelected;
    this.imgMyContent = imgMyContent;
    this.imgMyContentSelected = imgMyContentSelected;
    this.itemCast = itemCast;
    this.itemCastContainer = itemCastContainer;
    this.itemFavorite = itemFavorite;
    this.itemFavoriteContainer = itemFavoriteContainer;
    this.itemHome = itemHome;
    this.itemHomeContainer = itemHomeContainer;
    this.itemMyContent = itemMyContent;
    this.itemMyContentContainer = itemMyContentContainer;
    this.myContentText = myContentText;
  }

  @NonNull
  public static VhNavMenuBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_nav_menu, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VhNavMenuBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VhNavMenuBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_nav_menu, root, attachToRoot, component);
  }

  @NonNull
  public static VhNavMenuBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.vh_nav_menu, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VhNavMenuBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VhNavMenuBinding>inflateInternal(inflater, notq.dccast.R.layout.vh_nav_menu, null, false, component);
  }

  public static VhNavMenuBinding bind(@NonNull View view) {
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
  public static VhNavMenuBinding bind(@NonNull View view, @Nullable Object component) {
    return (VhNavMenuBinding)bind(component, view, notq.dccast.R.layout.vh_nav_menu);
  }
}
