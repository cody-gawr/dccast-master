package notq.dccast.screens.navigation_menu.live;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.databinding.FragmentBottomOptionsBinding;
import notq.dccast.model.category.CategoryItem;
import notq.dccast.model.live.ModelOptions;

public class BottomSheetLiveSettingsItem extends Fragment {
  @SuppressLint("StaticFieldLeak") private FragmentBottomOptionsBinding binding;
  private AdapterBottomSheetOptions adapter;
  private int TYPE;

  private ModelOptions selectedCategory;
  private ModelOptions selectedSetLock;
  private ModelOptions selectedBroadcast;

  private OnItemSelectedListener onItemSelectedListener;

  public static synchronized BottomSheetLiveSettingsItem getInstance(int type) {
    BottomSheetLiveSettingsItem instance = new BottomSheetLiveSettingsItem();

    Bundle args = new Bundle();
    args.putInt("type", type);
    instance.setArguments(args);

    return instance;
  }

  void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
    this.onItemSelectedListener = onItemSelectedListener;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      TYPE = getArguments().getInt("type", 0);
    }
  }

  @SuppressLint("SetTextI18n") @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_options, container, false);

    initRecyclerView();
    return binding.getRoot();
  }

  private void initRecyclerView() {
    adapter = new AdapterBottomSheetOptions(getActivity(), position -> {
      ModelOptions selectedItem = adapter.getItem(position);

      switch (TYPE) {
        case ActivityLiveSettings.ITEM_CATEGORY: {
          selectedCategory = selectedItem;
          break;
        }
        case ActivityLiveSettings.ITEM_SET_LOCK: {
          selectedSetLock = selectedItem;
          break;
        }
        case ActivityLiveSettings.ITEM_BROADCAST: {
          selectedBroadcast = selectedItem;
          break;
        }
      }

      if (onItemSelectedListener != null) {
        onItemSelectedListener.onItemSelected(selectedItem);
      }
    });

    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.setAdapter(adapter);

    ArrayList<ModelOptions> items = new ArrayList<>();

    switch (TYPE) {
      case ActivityLiveSettings.ITEM_CATEGORY: {
        List<CategoryItem> listCategoryItems = DCCastApplication.listCategoryItems;

        for (int i = 0; i < listCategoryItems.size(); i++) {
          String selectedCategoryName = listCategoryItems.get(i).name;
          boolean isSelected = selectedCategory != null && (selectedCategory.getTitle()
              .equalsIgnoreCase(selectedCategoryName));
          ModelOptions modelOptions =
              new ModelOptions(listCategoryItems.get(i).getId(), selectedCategoryName, isSelected);
          items.add(modelOptions);
        }
        break;
      }

      case ActivityLiveSettings.ITEM_SET_LOCK: {

        for (int i = 100; i < 700; i += 100) {
          String selectedCategoryName = String.valueOf(i);
          if (selectedSetLock == null && i == 100) {
            selectedSetLock = new ModelOptions(String.valueOf(i), true);
          }
          boolean isSelected = selectedSetLock != null && (selectedSetLock.getTitle()
              .equalsIgnoreCase(selectedCategoryName));
          ModelOptions modelOptions = new ModelOptions(selectedCategoryName, isSelected);
          items.add(modelOptions);
        }

        break;
      }

      case ActivityLiveSettings.ITEM_BROADCAST: {

        if (selectedBroadcast == null) {
          items.add(new ModelOptions(getString(R.string.broadcast_high_quality), true));
        } else {
          items.add(new ModelOptions(getString(R.string.broadcast_high_quality),
              selectedBroadcast != null && (selectedBroadcast.getTitle()
                  .equalsIgnoreCase(getString(R.string.broadcast_high_quality)))));
        }
        items.add(new ModelOptions(getString(R.string.broadcast_medium_quality),
            selectedBroadcast != null && (selectedBroadcast
                .getTitle()
                .equalsIgnoreCase(getString(R.string.broadcast_medium_quality)))));
        items.add(new ModelOptions(getString(R.string.broadcast_low_quality),
            selectedBroadcast != null && (selectedBroadcast.getTitle()
                .equalsIgnoreCase(getString(R.string.broadcast_low_quality)))));
        break;
      }

      case ActivityLiveSettings.ITEM_BLOCK_USER: {
        items.add(new ModelOptions(0, getString(R.string.broadcast_block_3),
            false));
        items.add(new ModelOptions(1, getString(R.string.broadcast_block_permanent),
            false));
        break;
      }
    }

    adapter.setListOptions(items);
  }

  interface OnItemSelectedListener {
    void onItemSelected(ModelOptions item);
  }
}
