package notq.dccast.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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

public class ChangeNicknameDialog extends Dialog {

  private Dialog dialog;
  private Context context;

  private boolean isChecked = false;
  private String checkedName = "";

  private int colorError, colorVerified;

  private ChangeNicknameInterface changeNicknameInterface;

  private TextView statusText;

  private ProgressDialog progressDialog;

  public ChangeNicknameDialog(@NonNull Context context,
      ChangeNicknameInterface changeNicknameInterface) {
    super(context);
    dialog = new Dialog(context);
    this.context = context;
    progressDialog = new ProgressDialog(context);
    this.changeNicknameInterface = changeNicknameInterface;
    initDialog();
  }

  private void initDialog() {
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(true);
    dialog.setContentView(R.layout.dialog_change_nickname);

    colorError = ContextCompat.getColor(context, R.color.badge_text_red);
    colorVerified = ContextCompat.getColor(context, R.color.colorPrimary);

    AppCompatEditText etNickname = dialog.findViewById(R.id.et_nickname);
    TextView statusCheck = dialog.findViewById(R.id.status_check);
    Button cancelChangeNickname = dialog.findViewById(R.id.cancel_change_nickname);
    statusText = dialog.findViewById(R.id.status_text);
    Button changeNickname = dialog.findViewById(R.id.change_nickname);

    statusCheck.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String nickname = etNickname.getText().toString();
        if (nickname == null || nickname.isEmpty()) {
          Toast.makeText(getContext(), context.getString(R.string.validate_change_nickname),
              Toast.LENGTH_SHORT).show();
          return;
        }
        checkUsername(nickname);
      }
    });

    cancelChangeNickname.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        hideDialog();
      }
    });

    changeNickname.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!isChecked || checkedName == null || checkedName.isEmpty()) {
          Toast.makeText(getContext(), context.getString(R.string.validate_change_nickname),
              Toast.LENGTH_SHORT).show();
          return;
        }

        if (changeNicknameInterface != null) {
          changeNicknameInterface.changeNickname(checkedName);
        }

        hideDialog();
      }
    });

    int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.9);

    Window window = dialog.getWindow();
    if (window != null) {
      window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
  }

  private void showProgressDialog() {
    if (progressDialog != null) {
      progressDialog.show();
    }
  }

  private void hideProgressDialog() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  private void checkUsername(String nickname) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    showProgressDialog();
    LiveAPIInterface apiInterface = APIClient.getClient().create(LiveAPIInterface.class);
    Call<ModelResult> call =
        apiInterface.checkNickname("avail_nickname", loginUser.getId(), nickname);
    call.enqueue(new Callback<ModelResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelResult> call,
          @NonNull Response<ModelResult> response) {
        ModelResult result = response.body();
        hideProgressDialog();
        if (result == null || !result.result) {
          checkedName = "";
          isChecked = false;

          statusText.setTextColor(colorError);
          statusText.setText(context.getString(R.string.change_nickname_not_verified));
          return;
        }

        checkedName = nickname;
        isChecked = true;

        statusText.setTextColor(colorVerified);
        statusText.setText(context.getString(R.string.change_nickname_verified));
      }

      @Override
      public void onFailure(@NonNull Call<ModelResult> call, @NonNull Throwable t) {
        call.cancel();
        hideProgressDialog();
      }
    });
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

  public interface ChangeNicknameInterface {
    void changeNickname(String nickname);
  }
}
