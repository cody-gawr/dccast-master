package notq.dccast.screens.home.video_detail;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.JsonObject;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.ManduAPIInterface;
import notq.dccast.databinding.FragmentBottomBuuzBinding;
import notq.dccast.model.user.ModelResult;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.mandu.ManduService;
import notq.dccast.util.LoginService;
import notq.dccast.util.ManduPasswordDialog;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetBuuz extends BottomSheetDialogFragment
    implements View.OnClickListener, View.OnLongClickListener {
  @SuppressLint("StaticFieldLeak") private static BottomSheetBuuz instance;
  private FragmentBottomBuuzBinding binding;
  private View.OnClickListener onClickListenerForParent;
  private int sendBuuzCount = 1;
  private double totalBuuzCount = 0;

  private ProgressDialog progressDialog;

  private int videoUserId = -1;
  private String videoUserNo = "-1";

  static synchronized BottomSheetBuuz getInstance(int videoUserId, String videoUserNo) {
    //if (instance == null) {
    //  instance = new BottomSheetBuuz();
    //}

    instance = new BottomSheetBuuz();

    Bundle args = new Bundle();
    if (instance.getArguments() != null) {
      Bundle oldArguments = instance.getArguments();
      if (oldArguments.containsKey("videoUserId")) {
        args.remove("videoUserId");
        args.putInt("videoUserId", videoUserId);
      }

      if (oldArguments.containsKey("videoUserNo")) {
        args.remove("videoUserNo");
        args.putString("videoUserNo", videoUserNo);
      }
    } else {
      args.putInt("videoUserId", videoUserId);
      args.putString("videoUserNo", videoUserNo);
    }
    instance.setArguments(args);

    return instance;
  }

  void setOnClickListenerForParent(View.OnClickListener onClickListenerForParent) {
    this.onClickListenerForParent = onClickListenerForParent;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      videoUserId = getArguments().getInt("videoUserId", -1);
      videoUserNo = getArguments().getString("videoUserNo", "-1");
    }
  }

  @SuppressLint("SetTextI18n") @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_buuz, container, false);

    progressDialog = new ProgressDialog(getActivity());
    progressDialog.setCancelable(false);

    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser != null) {
      binding.manduYourDumpling.setText(
          getString(R.string.mandu_your_dumpling, loginUser.getNickName()));
    } else {
      binding.manduYourDumpling.setText(
          getString(R.string.mandu_your_dumpling, ""));
    }

    getBuuzInfoRequest();
    updateBuuzCountText();
    initClickListeners();
    return binding.getRoot();
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_minus: {
        minusBuuz();
        break;
      }

      case R.id.btn_add: {
        addBuuz();
        break;
      }

      case R.id.lbl_buuz_buy: {
        Toast.makeText(getActivity(), "Buy Mandu", Toast.LENGTH_LONG).show();
        break;
      }

      case R.id.btn_send_buuz: {
        if (!validate()) {
          return;
        }

        ManduPasswordDialog manduPasswordDialog = new ManduPasswordDialog(getActivity(),
            new ManduPasswordDialog.SendManduInterface() {
              @Override public void sendMandu(String password) {

                //TODO USE PASSWORD
                ManduAPIInterface apiInterface =
                    APIClient.getMDCIdClient().create(ManduAPIInterface.class);

                ModelUser loginUser = LoginService.getLoginUser();
                if (DCCastApplication.appId == null
                    || DCCastApplication.userId == null
                    || loginUser == null) {
                  return;
                }

                progressDialog.show();
                Call<JsonObject> call =
                    apiInterface.sendManduToDcInside("A96", DCCastApplication.userId,
                        DCCastApplication.appId,
                        sendBuuzCount, videoUserNo);
                call.enqueue(new Callback<JsonObject>() {
                  @Override
                  public void onResponse(@NonNull Call<JsonObject> call,
                      @NonNull Response<JsonObject> response) {
                    JsonObject result = response.body();
                    Toast.makeText(getActivity(), result.get("cause").getAsString(),
                        Toast.LENGTH_LONG)
                        .show();
                    if (response.isSuccessful()) {
                      checkManduSent(loginUser.getId(), videoUserId, sendBuuzCount);
                    }
                  }

                  @Override
                  public void onFailure(@NonNull Call<JsonObject> call,
                      @NonNull Throwable t) {
                    call.cancel();

                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG)
                        .show();
                  }
                });
              }
            });

        manduPasswordDialog.showDialog();

        break;
      }
    }
  }

  private void checkManduSent(int fromUser, int toUser, int quantity) {
    progressDialog.show();
    ManduAPIInterface apiInterface = APIClient.getClient().create(ManduAPIInterface.class);
    Call<ModelResult> call =
        apiInterface.checkManduSent("mandu_gift", fromUser, toUser, "gift", quantity);
    call.enqueue(new Callback<ModelResult>() {
      @Override
      public void onResponse(@NonNull Call<ModelResult> call,
          @NonNull Response<ModelResult> response) {
        progressDialog.dismiss();
        ModelResult result = response.body();

        if (response.isSuccessful() && result != null && result.isResult()) {
          totalBuuzCount -= quantity;
          binding.buuzPoint.setText(Util.getFormattedNumber(totalBuuzCount));
          return;
        }

        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
      }

      @Override
      public void onFailure(@NonNull Call<ModelResult> call, @NonNull Throwable t) {
        call.cancel();

        progressDialog.dismiss();
        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
      }
    });
  }

  private boolean validate() {
    if (sendBuuzCount <= totalBuuzCount) {
      return true;
    }

    Toast.makeText(getActivity(), getString(R.string.buuz_max), Toast.LENGTH_LONG).show();
    return false;
  }

  @Override public boolean onLongClick(View view) {
    switch (view.getId()) {
      case R.id.btn_minus: {
        if (!binding.buuzAnimation.isAnimating()) {
          binding.buuzAnimation.playAnimation();
        }

        break;
      }

      case R.id.btn_add: {
        if (!binding.buuzAnimation.isAnimating()) {
          binding.buuzAnimation.playAnimation();
        }

        break;
      }
    }

    return false;
  }

  private void initClickListeners() {
    if (onClickListenerForParent != null) {
      binding.btnMinus.setOnClickListener(this);
      binding.btnAdd.setOnClickListener(this);
      binding.btnCloseBs.setOnClickListener(onClickListenerForParent);
      binding.btnSendBuuz.setOnClickListener(this);
      binding.lblBuuzBuy.setOnClickListener(this);

      binding.btnMinus.setOnLongClickListener(this);
      binding.btnAdd.setOnLongClickListener(this);
    }
  }

  @SuppressLint("SetTextI18n") private void addBuuz() {
    if (sendBuuzCount < 200) {
      if (!binding.buuzAnimation.isAnimating()) {
        binding.buuzAnimation.playAnimation();
      }

      sendBuuzCount++;

      updateBuuzCountText();
    } else {
      Toast.makeText(getContext(), getString(R.string.mandu_send_reach_limit), Toast.LENGTH_SHORT)
          .show();
    }
  }

  @SuppressLint("SetTextI18n") private void minusBuuz() {
    if (sendBuuzCount > 1) {
      if (!binding.buuzAnimation.isAnimating()) {
        binding.buuzAnimation.playAnimation();
      }

      sendBuuzCount--;

      updateBuuzCountText();
    }
  }

  @SuppressLint("SetTextI18n") private void updateBuuzCountText() {
    binding.buuzCountField.setText(sendBuuzCount + " " + getString(R.string.mandu_unit));
  }

  private void getBuuzInfoRequest() {
    totalBuuzCount = 0;
    if (DCCastApplication.userId != null && DCCastApplication.appId != null) {
      binding.manduLoading.setVisibility(View.VISIBLE);
      ManduService service = new ManduService(getActivity(), new ManduService.ManduCallback() {
        @Override public void onError(String error) {
          binding.manduLoading.setVisibility(View.GONE);
          binding.buuzPoint.setText("0");
          if (error != null) {
            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
          }
        }

        @Override public void onComplete(double mandu) {
          binding.manduLoading.setVisibility(View.GONE);
          binding.buuzPoint.setText(Util.getFormattedNumber(mandu));

          totalBuuzCount = mandu;
        }
      });
      service.getUserMandu();
    } else {
      binding.manduLoading.setVisibility(View.GONE);
      binding.buuzPoint.setText(0);
    }
  }
}
