package notq.dccast.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import notq.dccast.R;

public class UploadingDialog extends Dialog {

  private Dialog dialog;
  private Context context;

  private UploadCancelInterface uploadCancelInterface;

  private ProgressBar uploadingProgress;
  private TextView uploadTitle;
  private TextView uploadCancel;
  private TextView uploadPercent;

  public UploadingDialog(@NonNull Context context, String title,
      UploadCancelInterface uploadCancelInterface) {
    super(context);
    dialog = new Dialog(context);
    this.context = context;
    this.uploadCancelInterface = uploadCancelInterface;
    initDialog(title);
  }

  private void initDialog(String title) {
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.dialog_uploading);

    uploadTitle = dialog.findViewById(R.id.upload_title);
    uploadCancel = dialog.findViewById(R.id.upload_cancel);
    uploadPercent = dialog.findViewById(R.id.upload_percent);
    uploadingProgress = dialog.findViewById(R.id.upload_load);

    if (title != null) {
      uploadTitle.setText(title);
    }

    if (this.uploadCancelInterface != null) {
      uploadCancel.setVisibility(View.VISIBLE);
      uploadCancel.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          uploadCancelInterface.cancelVideo();
          dialog.dismiss();
        }
      });
    }

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
    if (dialog != null && !dialog.isShowing()) {
      dialog.show();
    }

    if (uploadingProgress != null) {
      uploadingProgress.setVisibility(View.VISIBLE);
    }
  }

  public boolean isShowing() {
    return dialog.isShowing();
  }

  public void hideDialog() {
    if (dialog != null) {
      dialog.dismiss();
    }
  }

  public void setTitle(String title) {
    if (uploadTitle != null) {
      uploadTitle.setText(title);
    }
  }

  public void setUploadingProgress(float progress) {
    if (uploadPercent != null) {
      if (progress == 0) {
        uploadPercent.setVisibility(View.GONE);
      } else {
        uploadPercent.setVisibility(View.VISIBLE);
        uploadPercent.setText(String.format("%.1f", progress) + "%");
      }
    }
  }

  public interface UploadCancelInterface {
    void cancelVideo();
  }
}
