package notq.dccast.screens.home.mandu;

import android.content.Context;
import androidx.annotation.NonNull;
import com.pixplicity.easyprefs.library.Prefs;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import notq.dccast.BuildConfig;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.ManduAPIInterface;
import notq.dccast.api.login.LoginAPIInterface;
import notq.dccast.model.ModelPublicKeyResult;
import notq.dccast.model.mandu.ModelManduDCInside;
import notq.dccast.model.user.ModelDCInsideLoginResult;
import notq.dccast.util.Constants;
import notq.dccast.util.Util;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManduService {

  private Context context;
  private ManduAPIInterface apiInterface;
  private LoginAPIInterface dcApiLoginInterface;
  private ManduCallback callback;
  private ManduHistoryCallback historyCallback;

  public ManduService(Context context, ManduCallback callback) {
    this.context = context;
    this.callback = callback;
    apiInterface = APIClient.getMDCIdClient().create(ManduAPIInterface.class);

    dcApiLoginInterface =
        APIClient.getDCIdClient().create(LoginAPIInterface.class);
  }

  public ManduService(Context context, ManduHistoryCallback callback) {
    this.context = context;
    this.historyCallback = callback;
    apiInterface = APIClient.getMDCIdClient().create(ManduAPIInterface.class);

    dcApiLoginInterface =
        APIClient.getDCIdClient().create(LoginAPIInterface.class);
  }

  public void getUserMandu() {
    if (DCCastApplication.appId == null || DCCastApplication.appId.isEmpty()) {
      getAppId();
      return;
    }

    if (DCCastApplication.userId == null || DCCastApplication.userId.isEmpty()) {
      getUserId();
      return;
    }

    getManduRequest();
  }

  public void getManduHistory() {
    if (DCCastApplication.appId == null || DCCastApplication.appId.isEmpty()) {
      getAppId();
      return;
    }

    if (DCCastApplication.userId == null || DCCastApplication.userId.isEmpty()) {
      getUserId();
      return;
    }

    getManduHistoryWebView();
  }

  private void getAppId() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH", Locale.ENGLISH);
    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
    String date = sdf.format(new Date());
    String valueTokenString = "dcCastchk_" + date;
    String valueToken = "";

    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      digest.update(valueTokenString.getBytes());
      valueToken = Util.bytesToHexString(digest.digest()).toLowerCase();
    } catch (NoSuchAlgorithmException ignored) {
    }

    Call<List<ModelPublicKeyResult>> call =
        dcApiLoginInterface.getPublicKey(valueToken, Constants.SIGNATURE,
            BuildConfig.APPLICATION_ID, BuildConfig.VERSION_NAME);
    call.enqueue(new Callback<List<ModelPublicKeyResult>>() {
      @Override public void onResponse(@NonNull Call<List<ModelPublicKeyResult>> call,
          @NonNull Response<List<ModelPublicKeyResult>> response) {
        List<ModelPublicKeyResult> result = response.body();
        if (result != null && result.size() > 0) {
          ModelPublicKeyResult publicKeyResult = result.get(0);

          if (publicKeyResult != null
              && publicKeyResult.isResult()
              && publicKeyResult.getAppId() != null) {
            DCCastApplication.appId = publicKeyResult.getAppId();

            if (DCCastApplication.userId == null || DCCastApplication.userId.isEmpty()) {
              getUserId();
              return;
            }

            getManduRequest();
          }
        }
      }

      @Override
      public void onFailure(@NonNull Call<List<ModelPublicKeyResult>> call, @NonNull Throwable t) {
        call.cancel();

        callback.onError(t.getMessage());
      }
    });
  }

  private void getUserId() {
    String username = Prefs.getString(Constants.LOGIN_USERNAME, "");
    String password = Prefs.getString(Constants.LOGIN_PASSWORD, "");

    if (username.isEmpty() || password.isEmpty()) {
      callback.onError(null);
      return;
    }

    Call<List<ModelDCInsideLoginResult>> call =
        dcApiLoginInterface.dcInsideLogin(username, password);
    call.enqueue(new Callback<List<ModelDCInsideLoginResult>>() {
      @Override public void onResponse(@NonNull Call<List<ModelDCInsideLoginResult>> call,
          @NonNull Response<List<ModelDCInsideLoginResult>> response) {
        List<ModelDCInsideLoginResult> loginResult = response.body();
        if (loginResult != null && loginResult.size() > 0) {
          ModelDCInsideLoginResult insideLoginResult = loginResult.get(0);
          if (insideLoginResult != null && insideLoginResult.isResult()) {
            DCCastApplication.userId = insideLoginResult.getUserId();
            getManduRequest();
            return;
          }
        }

        if (callback != null) {
          callback.onError(null);
        }
      }

      @Override public void onFailure(@NonNull Call<List<ModelDCInsideLoginResult>> call,
          @NonNull Throwable t) {
        call.cancel();

        if (callback != null) {
          callback.onError(t.getMessage());
        }
      }
    });
  }

  private void getManduRequest() {
    Call<List<ModelManduDCInside>> call =
        apiInterface.getManduFromDcInside(DCCastApplication.userId, DCCastApplication.appId);
    call.enqueue(new Callback<List<ModelManduDCInside>>() {
      @Override
      public void onResponse(@NonNull Call<List<ModelManduDCInside>> call,
          @NonNull Response<List<ModelManduDCInside>> response) {

        List<ModelManduDCInside> result = response.body();
        if (result == null || result.size() == 0) {
          callback.onError(context.getString(R.string.error));
          return;
        }

        double manduCount = 0;

        for (ModelManduDCInside modelManduDCInside : result) {
          if (modelManduDCInside.getResult().equalsIgnoreCase("success")) {
            manduCount = modelManduDCInside.getManduCount();
            break;
          }
        }

        callback.onComplete(manduCount);
      }

      @Override
      public void onFailure(@NonNull Call<List<ModelManduDCInside>> call, @NonNull Throwable t) {
        call.cancel();

        callback.onError(t.getMessage());
      }
    });
  }

  private void getManduHistoryWebView() {
    Call<ResponseBody> call =
        apiInterface.getManduHistoryWebView(DCCastApplication.userId, DCCastApplication.appId);
    call.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(@NonNull Call<ResponseBody> call,
          @NonNull Response<ResponseBody> response) {

        ResponseBody result = response.body();
        String resultString = "";
        try {
          resultString = result.string();
        } catch (IOException e) {
          e.printStackTrace();

          historyCallback.onError(context.getString(R.string.error));
          return;
        }
        if (result == null) {
          historyCallback.onError(context.getString(R.string.error));
          return;
        }

        historyCallback.onComplete(resultString);
      }

      @Override
      public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
        call.cancel();

        historyCallback.onError(t.getMessage());
      }
    });
  }

  public interface ManduCallback {
    void onError(String error);

    void onComplete(double mandu);
  }

  public interface ManduHistoryCallback {
    void onError(String error);

    void onComplete(String content);
  }
}
