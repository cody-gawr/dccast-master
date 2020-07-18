package notq.dccast.screens.navigation_menu.live;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.util.HashMap;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.databinding.FragmentBottomLiveSettingsBinding;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.live.ModelOptions;
import notq.dccast.util.DialogHelper;
import notq.dccast.util.LockableBottomSheetBehavior;
import notq.dccast.util.SuccessDialog;

public class BottomSheetLiveSettings extends Fragment {
  public static BottomSheetLiveSettings bottomSheetLiveSettings;
  @SuppressLint("StaticFieldLeak") private FragmentBottomLiveSettingsBinding binding;
  private OnItemSelectedListener onItemSelectedListener;
  private UpdateLiveInterface updateLiveInterface;
  @SuppressLint("UseSparseArrays") private HashMap<Integer, BottomSheetOptions>
      bottomSheetOptionsHashMap = new HashMap<>();
  private BottomSheetBehavior sheetBehavior;
  private ModelVideo videoItem;

  public static synchronized BottomSheetLiveSettings getInstance(ModelVideo modelVideo) {
    if (bottomSheetLiveSettings == null) {
      bottomSheetLiveSettings = new BottomSheetLiveSettings();
    }
    Bundle headerData = new Bundle();
    headerData.putSerializable("data", modelVideo);
    bottomSheetLiveSettings.setArguments(headerData);
    return bottomSheetLiveSettings;
  }

  void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
    this.onItemSelectedListener = onItemSelectedListener;
  }

  public void setUpdateLiveInterface(
      UpdateLiveInterface updateLiveInterface) {
    this.updateLiveInterface = updateLiveInterface;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      Bundle bundle = getArguments();
      videoItem = (ModelVideo) bundle.getSerializable("data");
    }
  }

  @SuppressLint("SetTextI18n") @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_live_settings, container, false);

    initBottomSheet();
    init();
    return binding.getRoot();
  }

  private void initBottomSheet() {
    sheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);
    sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override public void onStateChanged(@NonNull View bottomSheet, int newState) {
        bottomSheet.post(() -> {
          bottomSheet.requestLayout();
          bottomSheet.invalidate();
        });

        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
          binding.dim.setVisibility(View.GONE);
        }
        if (sheetBehavior instanceof LockableBottomSheetBehavior) {
          ((LockableBottomSheetBehavior) sheetBehavior).setLocked(true);
        }
      }

      @Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {
      }
    });

    binding.dim.setOnClickListener(v -> closeBottomSheet());

    closeBottomSheet();
  }

  private void init() {
    binding.btnSettingsClose.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (onItemSelectedListener != null) {
          onItemSelectedListener.close();
        }
      }
    });

    binding.itemCategorySetting.setOnClickListener(
        v -> openBottomSheet(ActivityLiveSettings.ITEM_CATEGORY));
    binding.itemBroadcastQuality.setOnClickListener(
        v -> openBottomSheet(ActivityLiveSettings.ITEM_BROADCAST));
    binding.itemSetLock.setOnClickListener(
        v -> openBottomSheet(ActivityLiveSettings.ITEM_SET_LOCK));

    binding.itemRestricted.setOnClickListener(v -> binding.itemRestricted.toggleChecked());
    binding.itemLiveLock.setOnClickListener(view -> binding.itemLiveLock.toggleChecked());
    binding.itemChatLock.setOnClickListener(v -> binding.itemChatLock.toggleChecked());

    binding.itemLiveDistribute.setValue(getString(R.string.item_distribute_subscriber));
    binding.itemLiveDistribute.setItemClickListener(new LiveSettingsItem.ItemClickListener() {
      @Override public void rightArrowClicked() {

      }

      @Override public void valueItemClicked() {
        openBottomSheet(ActivityLiveSettings.ITEM_DISTRIBUTE);
      }
    });

    if (videoItem != null) {
      if (isAdultVideo(videoItem)) {
        binding.itemRestricted.setChecked(true);
      } else {
        binding.itemRestricted.setChecked(false);
      }

      if (videoItem.isLiveSetpass()
          && videoItem.getLivePassword() != null
          && !videoItem.getLivePassword().isEmpty()) {
        binding.itemLiveLock.setChecked(true);
      } else {
        binding.itemLiveLock.setChecked(false);
      }

      if (videoItem.isLiveChatDisable()) {
        binding.itemChatLock.setChecked(true);
      } else {
        binding.itemChatLock.setChecked(false);
      }

      binding.itemSetLock.setValue(videoItem.getLiveMember());

      if (videoItem.getLiveResolution() != null) {
        binding.itemBroadcastQuality.setValue(videoItem.getLiveResolution());
      }

      if (videoItem.getMediaCategory() != null) {
        binding.itemCategorySetting.setValue(videoItem.getMediaCategory().getName());
      }
    }

    binding.itemRestricted.getSwitchItem().setOnCheckedChangeListener((view, isChecked) -> {
      if (updateLiveInterface != null) {
        updateLiveInterface.updateRestricted(isChecked);
      }
    });

    binding.itemLiveLock.getSwitchItem().setOnCheckedChangeListener((view, isChecked) -> {
      if (isChecked) {
        openBottomSheetLock(null);
      } else {
        if (updateLiveInterface != null) {
          updateLiveInterface.updateLiveLock(null);
        }
      }
    });

    binding.itemChatLock.getSwitchItem().setOnCheckedChangeListener((view, isChecked) -> {
      if (updateLiveInterface != null) {
        updateLiveInterface.updateChatLock(isChecked);
      }
    });
  }

  private boolean isAdultVideo(ModelVideo modelVideo) {
    return (modelVideo.getKinds() != null && modelVideo.getKinds()
        .equalsIgnoreCase(getString(R.string.value_share_type_19)));
  }

  private void openBottomSheetLock(String pin) {
    FragmentTransaction fragmentTransaction =
        Objects.requireNonNull(getChildFragmentManager()).beginTransaction();

    BottomSheetLock bottomSheetLock = BottomSheetLock.getInstance(pin);
    bottomSheetLock.setPinListener(new BottomSheetLock.EnterPinListener() {
      @Override public void initialPinEntered(String pin) {
        closeBottomSheet();

        new Handler().postDelayed(() -> getActivity().runOnUiThread(() -> openBottomSheetLock(pin)),
            300);
      }

      @Override public void pinVerified(String pin) {
        if (updateLiveInterface != null) {
          updateLiveInterface.updateLiveLock(pin);
        }

        closeBottomSheet();
        showVerifiedDialog();
      }

      @Override public void close() {
        closeBottomSheet();
      }
    });

    fragmentTransaction.replace(R.id.bottom_sheet, bottomSheetLock);
    fragmentTransaction.commit();

    new Handler().postDelayed(() -> getActivity().runOnUiThread(() -> {
      sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

      if (binding.dim.getVisibility() != View.VISIBLE) {
        binding.dim.setVisibility(View.VISIBLE);
      }
    }), 100);
  }

  private void openBottomSheet(int type) {
    FragmentTransaction fragmentTransaction =
        Objects.requireNonNull(getChildFragmentManager()).beginTransaction();

    BottomSheetOptions bottomSheetOptions;
    if (bottomSheetOptionsHashMap.containsKey(type)) {
      bottomSheetOptions = bottomSheetOptionsHashMap.get(type);
    } else {
      bottomSheetOptions = BottomSheetOptions.getInstance(type);
      bottomSheetOptionsHashMap.put(type, bottomSheetOptions);
    }

    if (bottomSheetOptions == null) {
      bottomSheetOptions = BottomSheetOptions.getInstance(type);
    }

    bottomSheetOptions.setLiveVideo(videoItem);

    bottomSheetOptions.setOnItemSelectedListener(new BottomSheetOptions.OnItemSelectedListener() {
      @Override public void onItemSelected(ModelOptions item) {
        switch (type) {
          case ActivityLiveSettings.ITEM_CATEGORY: {
            int selectedCategoryId = item.getId();
            binding.itemCategorySetting.setValue(item.getTitle());
            if (updateLiveInterface != null) {
              updateLiveInterface.updateCategoryId(selectedCategoryId);
            }
            break;
          }
          case ActivityLiveSettings.ITEM_SET_LOCK: {
            int setMaxUser = Integer.parseInt(item.getTitle());
            binding.itemSetLock.setValue(item.getTitle());
            if (updateLiveInterface != null) {
              updateLiveInterface.updateLiveMember(setMaxUser);
            }
            break;
          }
          case ActivityLiveSettings.ITEM_BROADCAST: {
            binding.itemBroadcastQuality.setValue(item.getTitle());
            if (updateLiveInterface != null) {
              updateLiveInterface.updateResolution(item.getTitle());
            }
            break;
          }

          case ActivityLiveSettings.ITEM_DISTRIBUTE: {
            DialogHelper.showAlertDialog(getActivity(), "", getString(R.string.mesh_type_confirm), getString(R.string.confirm_yes),
                new DialogInterface.OnClickListener() {
                  @Override public void onClick(DialogInterface dialog, int which) {
                    binding.itemLiveDistribute.setValue(item.getTitle());
                    if (updateLiveInterface != null) {
                      updateLiveInterface.updateMeshType(item.getTitle());
                    }
                  }
                }, getString(R.string.confirm_no), new DialogInterface.OnClickListener() {
                  @Override public void onClick(DialogInterface dialog, int which) {

                  }
                });
            break;
          }
        }
        closeBottomSheet();
      }

      @Override public void close() {
        closeBottomSheet();
      }
    });

    fragmentTransaction.replace(R.id.bottom_sheet, bottomSheetOptions);
    fragmentTransaction.commit();

    new Handler().postDelayed(() -> getActivity().runOnUiThread(() -> {
      sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
      if (binding.dim.getVisibility() != View.VISIBLE) {
        binding.dim.setVisibility(View.VISIBLE);
      }
    }), 100);
  }

  private void closeBottomSheet() {
    hideKeyboard();
    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
  }

  private void hideKeyboard() {
    InputMethodManager imm =
        (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
    Objects.requireNonNull(imm).hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
  }

  private void showVerifiedDialog() {
    SuccessDialog successDialog = new SuccessDialog(getActivity());
    successDialog.showDialog();
  }

  interface OnItemSelectedListener {
    void onItemSelected(ModelOptions item);

    void close();
  }

  interface UpdateLiveInterface {
    void updateLiveLock(String pin);

    void updateChatLock(boolean enabled);

    void updateRestricted(boolean enabled);

    void updateCategoryId(int mediaCategoryId);

    void updateLiveMember(int count);

    void updateResolution(String resolution);

    void updateMeshType(String meshType);
  }
}
