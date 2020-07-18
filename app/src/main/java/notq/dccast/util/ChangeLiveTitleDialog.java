package notq.dccast.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.live.LiveAPIInterface;
import notq.dccast.model.user.ModelResult;
import notq.dccast.model.user.ModelUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeLiveTitleDialog extends Dialog {

  private Dialog dialog;
  private Context context;

  private OnItemSelectedListener onItemSelectedListener;

  public ChangeLiveTitleDialog(@NonNull Context context,
      OnItemSelectedListener onItemSelectedListener) {
    super(context);
    dialog = new Dialog(context);
    this.context = context;
    this.onItemSelectedListener = onItemSelectedListener;
    initDialog();
  }

  private void initDialog() {
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(true);
    dialog.setContentView(R.layout.fragment_bottom_live_title);

    EditText etLiveTitle = dialog.findViewById(R.id.et_live_title);
    FrameLayout btnTitleClose = dialog.findViewById(R.id.btn_title_close);
    Button btnEnter = dialog.findViewById(R.id.btn_enter);
    Button btnCancel = dialog.findViewById(R.id.btn_cancel);

    btnTitleClose.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (onItemSelectedListener != null) {
          onItemSelectedListener.close();
        }
      }
    });

    btnEnter.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String liveTitle = etLiveTitle.getText().toString();

        if (liveTitle == null || liveTitle.isEmpty()) {
          Toast.makeText(getContext(), context.getString(R.string.validate_live_title), Toast.LENGTH_LONG)
              .show();
          return;
        }

        if (onItemSelectedListener != null) {
          onItemSelectedListener.onTitleEntered(liveTitle);
          onItemSelectedListener.close();
        }
      }
    });

    btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (onItemSelectedListener != null) {
          onItemSelectedListener.close();
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

  public interface OnItemSelectedListener {
    void onTitleEntered(String title);

    void close();
  }
}
