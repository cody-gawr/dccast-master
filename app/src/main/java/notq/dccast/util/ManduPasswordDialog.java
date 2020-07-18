package notq.dccast.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import notq.dccast.R;

public class ManduPasswordDialog extends Dialog {

  private Dialog dialog;
  private Context context;

  private SendManduInterface sendManduInterface;

  public ManduPasswordDialog(@NonNull Context context,
      SendManduInterface sendManduInterface) {
    super(context);
    dialog = new Dialog(context);
    this.context = context;
    this.sendManduInterface = sendManduInterface;
    initDialog();
  }

  private void initDialog() {
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(true);
    dialog.setContentView(R.layout.dialog_mandu_password);

    AppCompatEditText etPassword = dialog.findViewById(R.id.et_password);
    Button cancelMandu = dialog.findViewById(R.id.cancel_mandu);
    Button sendMandu = dialog.findViewById(R.id.send_mandu);

    cancelMandu.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        dialog.dismiss();
      }
    });

    sendMandu.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String password = etPassword.getText().toString();
        if (password.isEmpty()) {
          Toast.makeText(getContext(), context.getString(R.string.validate_mandu_password),
              Toast.LENGTH_SHORT).show();
          return;
        }

        if (sendManduInterface != null) {
          dialog.dismiss();
          sendManduInterface.sendMandu(password);
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

  public interface SendManduInterface {
    void sendMandu(String password);
  }
}
