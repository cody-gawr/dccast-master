package notq.dccast.screens.navigation_menu.settings;

import android.annotation.SuppressLint;
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

public class BottomSheetSetLock extends BottomSheetDialogFragment {
  @SuppressLint("StaticFieldLeak")
  private FragmentBottomSetLockBinding binding;
  private String pin;
  private boolean isInitial = false;
  private EnterPinListener pinListener;

  private String verifiedPin = "";

  public static synchronized BottomSheetSetLock getInstance(String pin) {
    BottomSheetSetLock instance = new BottomSheetSetLock();

    Bundle args = new Bundle();
    args.putString("pin", pin);
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
    }

    isInitial = (pin == null || pin.isEmpty());
  }

  @SuppressLint("SetTextI18n")
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_set_lock, container, false);

    if (pin != null && !pin.isEmpty()) {
      binding.lblPin.setText(getString(R.string.verify_set_lock_password));
    }

    if (isInitial) {
      binding.btnEnter.setVisibility(View.INVISIBLE);
      binding.btnCancel.setVisibility(View.INVISIBLE);
    }

    binding.layoutEnter.setVisibility(View.VISIBLE);
    binding.btnEnter.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (verifiedPin == null || verifiedPin.isEmpty()) {
          binding.lblPinError.setVisibility(View.VISIBLE);
          return;
        }
        if (pinListener != null) {
          pinListener.pinVerified(verifiedPin);
        }
      }
    });

    binding.btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (pinListener != null) {
          pinListener.close();
        }
      }
    });

    binding.layoutScroll.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        binding.etPin.requestFocus();
        showKeyboard();
      }
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
        verifiedPin = null;
        if (s.length() == 4) {
          if (isInitial) {
            //if (pinListener != null) {
            //  pinListener.initialPinEntered(s.toString());
            //}
            pin = s.toString();
            isInitial = false;
            binding.etPin.setText("");
          } else {
            if (s.toString().equalsIgnoreCase(pin)) {
              binding.lblPinError.setVisibility(View.INVISIBLE);
              verifiedPin = s.toString();

              binding.btnEnter.setVisibility(View.VISIBLE);
              binding.btnCancel.setVisibility(View.VISIBLE);
            } else {
              binding.lblPinError.setVisibility(View.VISIBLE);

              binding.btnEnter.setVisibility(View.INVISIBLE);
              binding.btnCancel.setVisibility(View.INVISIBLE);
            }
          }
        } else {
          binding.btnEnter.setVisibility(View.INVISIBLE);
          binding.btnCancel.setVisibility(View.INVISIBLE);
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

  public interface EnterPinListener {
    void initialPinEntered(String pin);

    void pinVerified(String pin);

    void close();
  }
}
