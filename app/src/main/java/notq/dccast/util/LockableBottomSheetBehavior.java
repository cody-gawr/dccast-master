package notq.dccast.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class LockableBottomSheetBehavior<V extends View> extends BottomSheetBehavior<V> {
  private boolean mLocked = true;

  public LockableBottomSheetBehavior() {
  }

  public LockableBottomSheetBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void setLocked(boolean locked) {
    mLocked = locked;
  }

  @Override
  public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
    boolean handled = false;

    if (!mLocked) {
      handled = super.onInterceptTouchEvent(parent, child, event);
    }

    return handled;
  }

  @Override public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
    boolean handled = false;

    if (!mLocked) {
      handled = super.onTouchEvent(parent, child, event);
    }

    return handled;
  }

  @Override
  public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child,
      @NonNull View directTargetChild, @NonNull View target, int nestedScrollAxes) {
    boolean handled = false;

    if (!mLocked) {
      handled = super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target,
          nestedScrollAxes);
    }

    return handled;
  }

  @Override
  public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child,
      @NonNull View target, int dx, int dy, @NonNull int[] consumed) {
    if (!mLocked) {
      super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }
  }

  @Override
  public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child,
      @NonNull View target) {
    if (!mLocked) {
      super.onStopNestedScroll(coordinatorLayout, child, target);
    }
  }

  @Override
  public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child,
      @NonNull View target, float velocityX, float velocityY) {
    boolean handled = false;

    if (!mLocked) {
      handled = super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    return handled;
  }
}