package notq.dccast.custom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import notq.dccast.R;
import org.jetbrains.annotations.NotNull;

public class DMotionLayout extends MotionLayout {
  private final Lazy viewToDetect;
  private final Rect viewRect;
  private boolean touchStarted;
  private OnTouchListener onTouchListener;
  private boolean isCanDrag = true;

  public DMotionLayout(@NotNull Context context, @Nullable AttributeSet attributeSet) {
    super(context, attributeSet);
    this.viewRect = new Rect();

    this.viewToDetect =
        LazyKt.lazy((Function0) () -> DMotionLayout.this.findViewById(R.id.videoViewContainer));
  }

  public void disable() {
    isCanDrag = false;
  }

  public void enable() {
    isCanDrag = true;
  }

  public void _setOnTouchListener(OnTouchListener onTouchListener) {
    this.onTouchListener = onTouchListener;
  }

  private View getViewToDetectTouch() {
    return (View) this.viewToDetect.getValue();
  }

  private long lastTouchDown;

  @SuppressLint("ClickableViewAccessibility")
  public boolean onTouchEvent(@NotNull MotionEvent event) {
    Intrinsics.checkParameterIsNotNull(event, "event");
    int CLICK_ACTION_THRESHOLD = 50;
    switch (event.getActionMasked()) {
      case 1:
      case 3: {
        if (onTouchListener != null) {
          onTouchListener.onTouched();
        }

        if (isCanDrag) {
          this.touchStarted = false;
          return super.onTouchEvent(event);
        } else {
          switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
              lastTouchDown = System.currentTimeMillis();
              break;
            case MotionEvent.ACTION_UP:
              if (System.currentTimeMillis() - lastTouchDown < CLICK_ACTION_THRESHOLD) {
                if (onTouchListener != null) {
                  onTouchListener.onTouched();
                }
              }
              break;
          }
          return true;
        }
      }

      case 2:
      default: {
        if (isCanDrag) {
          if (!this.touchStarted) {
            if (this.getViewToDetectTouch() != null) {
              this.getViewToDetectTouch().getHitRect(this.viewRect);
              this.touchStarted = this.viewRect.contains((int) event.getX(), (int) event.getY());
            }
          }

          return this.touchStarted && super.onTouchEvent(event);
        } else {
          switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
              lastTouchDown = System.currentTimeMillis();
              break;
            case MotionEvent.ACTION_UP:
              if (System.currentTimeMillis() - lastTouchDown < CLICK_ACTION_THRESHOLD) {
                if (onTouchListener != null) {
                  onTouchListener.onTouched();
                }
              }
              break;
          }
          return true;
        }
      }
    }
  }

  public interface OnTouchListener {
    void onTouched();
  }
}
