package notq.dccast.screens.splash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.databinding.FragmentBottomSetLockBinding;

public class BottomSheetValidatePassCode extends BottomSheetDialogFragment {
  public static final int SET_LOCK = 1, LIVE_LOCK = 2;
  @SuppressLint("StaticFieldLeak")
  private FragmentBottomSetLockBinding binding;
  private String pin;
  private EnterPinListener pinListener;
  private int type = SET_LOCK;

  public static synchronized BottomSheetValidatePassCode getInstance(int type, String pin) {
    BottomSheetValidatePassCode instance = new BottomSheetValidatePassCode();

    Bundle args = new Bundle();
    args.putString("pin", pin);
    args.putInt("type", type);
    instance.setArguments(args);

    return instance;
  }

  public void setPinListener(EnterPinListener pinListener) {
    this.pinListener = pinListener;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      pin = getArguments().getString("pin");
      type = getArguments().getInt("type");
    }
  }

  @SuppressLint("SetTextI18n")
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_set_lock, container, false);

    if (pin != null && !pin.isEmpty()) {
      binding.lblPin.setText(
          type == SET_LOCK ? getString(R.string.set_lock_password)
              : getString(R.string.live_lock_password));
    }
    binding.layoutEnter.setVisibility(View.GONE);

    binding.layoutScroll.setOnClickListener(v -> {
      binding.etPin.requestFocus();
      showKeyboard();
    });

    binding.etPin.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        if (s.length() == 4) {
          if (s.toString().equalsIgnoreCase(pin)) {
            if (pinListener != null) {
              hideKeyboard();
              pinListener.pinVerified(s.toString());
            }

            binding.lblPinError.setVisibility(View.GONE);
          } else {
            binding.lblPinError.setText(getString(R.string.password_not_verified));
            binding.lblPinError.setVisibility(View.VISIBLE);
          }
        }
      }
    });

    binding.etPin.requestFocus();
    showKeyboard();

    return binding.getRoot();
  }

  private void showKeyboard() {
    if (getActivity() == null) {
      return;
    }
    InputMethodManager imm =
        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
  }

  private void hideKeyboard() {
    InputMethodManager imm =
        (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
    Objects.requireNonNull(imm).hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
  }

  public interface EnterPinListener {
    void pinVerified(String pin);
  }
}
