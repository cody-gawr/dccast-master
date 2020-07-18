package notq.dccast.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomFrameLayout extends FrameLayout {
  public CustomFrameLayout(@NotNull Context context, @Nullable AttributeSet attributeSet) {
    super(context, attributeSet);
  }

  public final void setEndPadding(float value) {
    this.setPadding(0, 0, (int) value, 0);
  }
}
