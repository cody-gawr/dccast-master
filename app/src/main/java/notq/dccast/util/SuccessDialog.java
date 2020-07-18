package notq.dccast.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.airbnb.lottie.LottieAnimationView;
import notq.dccast.R;

public class SuccessDialog extends Dialog {

  private Dialog dialog;
  private Context context;
  private LottieAnimationView successAnimation;

  private OpenVideoInterface openVideoInterface;

  public SuccessDialog(@NonNull Context context) {
    super(context);
    dialog = new Dialog(context);
    this.context = context;
    initDialog(null);
  }

  public SuccessDialog(@NonNull Context context, String title) {
    super(context);
    dialog = new Dialog(context);
    this.context = context;
    initDialog(title);
  }

  public SuccessDialog(@NonNull Context context, String title,
      OpenVideoInterface openVideoInterface) {
    super(context);
    dialog = new Dialog(context);
    this.context = context;
    this.openVideoInterface = openVideoInterface;
    initDialog(title);
  }

  private void initDialog(String title) {
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(true);
    dialog.setContentView(R.layout.dialog_verified_success);

    TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
    TextView goToVideo = dialog.findViewById(R.id.go_to_video);

    if (title != null) {
      dialogTitle.setText(title);
    }

    if (this.openVideoInterface != null) {
      goToVideo.setVisibility(View.VISIBLE);
      goToVideo.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          openVideoInterface.openVideoClicked();
          dialog.dismiss();
        }
      });
    }

    int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.9);

    Window window = dialog.getWindow();
    if (window != null) {
      window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    successAnimation = dialog.findViewById(R.id.success_animation);
  }

  public void setOnDismissListener(OnDismissListener listener) {
    dialog.setOnDismissListener(listener);
  }

  public void showDialog() {
    if (dialog != null) {
      dialog.show();
    }

    if (successAnimation != null) {
      if (!successAnimation.isAnimating()) {
        successAnimation.playAnimation();
      }
    }
  }

  public void hideDialog() {
    if (dialog != null) {
      dialog.dismiss();
    }
  }

  public interface OpenVideoInterface {
    void openVideoClicked();
  }
}
