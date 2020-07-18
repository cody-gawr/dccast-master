package notq.dccast.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.exoplayer2.ui.PlayerView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomPlayerView extends PlayerView {
  public CustomPlayerView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
    super(context, attributeSet);
  }

  public final void setEndPadding(float value) {
    this.setPadding(0, 0, (int) value, 0);
  }
}
