package notq.dccast.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomBlockLayout extends FrameLayout {

  public CustomBlockLayout(@NonNull Context context) {
    super(context);
  }

  public CustomBlockLayout(@NonNull Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public CustomBlockLayout(@NonNull Context context, @Nullable AttributeSet attrs,
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public CustomBlockLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    return true;
  }
}
