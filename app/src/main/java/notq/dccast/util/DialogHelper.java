package notq.dccast.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import notq.dccast.R;

public class DialogHelper {
  public static void showAlertDialog(Context context, String title, String description) {
    showAlertDialog(context, true, title, description, null, null, null, null, null);
  }

  public static void showAlertDialog(Context context, String title, String description,
      String yesButton, DialogInterface.OnClickListener yesClickListener, String noButton,
      DialogInterface.OnClickListener noClickListener) {
    showAlertDialog(context, true, title, description, yesButton, yesClickListener, noButton,
        noClickListener, null);
  }

  public static void showAlertDialog(Context context, String title, String description,
      String yesButton, DialogInterface.OnClickListener yesClickListener) {
    showAlertDialog(context, true, title, description, yesButton, yesClickListener, null,
        null, null);
  }

  public static void showAlertDialog(Context context, boolean cancelable, String title,
      String description, String yesButton, DialogInterface.OnClickListener yesClickListener,
      String noButton, DialogInterface.OnClickListener noClickListener,
      DialogInterface.OnDismissListener dismissListener) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);

    boolean hasTitle = false;
    if (title != null && !title.isEmpty()) {
      builder.setTitle(title);
      hasTitle = true;
    }
    builder.setMessage(description);
    builder.setCancelable(cancelable);

    if (yesButton != null || yesClickListener == null) {
      builder.setPositiveButton(
          yesButton != null ? yesButton : context.getString(R.string.logout_confirm_yes),
          yesClickListener != null ? yesClickListener : new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dialog.cancel();
            }
          });
    }

    if (noButton != null || noClickListener == null) {
      builder.setNegativeButton(
          noButton != null ? noButton : context.getString(R.string.logout_confirm_no),
          noClickListener != null ? noClickListener : new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dialog.cancel();
            }
          });
    }

    AlertDialog alert = builder.create();
    if (!hasTitle) {
      alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    if (dismissListener != null) {
      alert.setOnDismissListener(dismissListener);
    }
    alert.show();
  }
}
