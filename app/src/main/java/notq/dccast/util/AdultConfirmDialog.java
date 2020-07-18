package notq.dccast.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import androidx.annotation.NonNull;
import notq.dccast.R;

public class AdultConfirmDialog extends Dialog {

  private Dialog dialog;
  private Context context;

  private ConfirmListener confirmListener;

  public AdultConfirmDialog(@NonNull Context context,
      ConfirmListener confirmListener) {
    super(context);
    dialog = new Dialog(context);
    this.context = context;
    this.confirmListener = confirmListener;
    initDialog();
  }

  private void initDialog() {
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(true);
    dialog.setContentView(R.layout.dialog_age_confirm);

    Button cancel = dialog.findViewById(R.id.cancel_confirm);
    Button confirm = dialog.findViewById(R.id.confirm_confirm);

    cancel.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        dialog.dismiss();
      }
    });

    confirm.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (confirmListener != null) {
          dialog.dismiss();
          confirmListener.onConfirm();
        }
      }
    });

    int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.9);

    Window window = dialog.getWindow();
    if (window != null) {
      window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
  }

  public void setOnDismissListener(OnDismissListener listener) {
    dialog.setOnDismissListener(listener);
  }

  public void showDialog() {
    if (dialog != null) {
      dialog.show();
    }
  }

  public void hideDialog() {
    if (dialog != null) {
      dialog.dismiss();
    }
  }

  public interface ConfirmListener {
    void onConfirm();
  }
}
