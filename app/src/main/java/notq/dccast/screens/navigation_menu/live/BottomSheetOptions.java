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
import notq.dccast.model.ModelVideo;
import notq.dccast.model.category.CategoryItem;
import notq.dccast.model.live.ModelOptions;

public class BottomSheetOptions extends Fragment {
  @SuppressLint("StaticFieldLeak") private FragmentBottomOptionsBinding binding;
  private AdapterBottomSheetOptions adapter;
  private int TYPE;

  private ModelOptions selectedDistribute;
  private ModelOptions selectedLiveType;
  private ModelOptions selectedCategory;
  private ModelOptions selectedSetLock;
  private ModelOptions selectedBroadcast;
  private ModelOptions selectedOrientation;
  private ModelOptions selectedBlock;

  private ModelVideo liveVideo;

  private OnItemSelectedListener onItemSelectedListener;

  public static synchronized BottomSheetOptions getInstance(int type) {
    BottomSheetOptions instance = new BottomSheetOptions();

    Bundle args = new Bundle();
    args.putInt("type", type);
    instance.setArguments(args);

    return instance;
  }

  public void setLiveVideo(ModelVideo liveVideo) {
    this.liveVideo = liveVideo;
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

    init();
    initRecyclerView();
    return binding.getRoot();
  }

  private void init() {
    binding.btnOptionsClose.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (onItemSelectedListener != null) {
          onItemSelectedListener.close();
        }
      }
    });
  }

  private void initRecyclerView() {
    adapter = new AdapterBottomSheetOptions(getActivity(), position -> {
      ModelOptions selectedItem = adapter.getItem(position);

      switch (TYPE) {
        case ActivityLiveSettings.ITEM_DISTRIBUTE: {
          selectedDistribute = selectedItem;
          break;
        }
        case ActivityLiveSettings.ITEM_LIVE_TYPE: {
          selectedLiveType = selectedItem;
          break;
        }
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
        case ActivityLiveSettings.ITEM_ORIENTATION: {
          selectedOrientation = selectedItem;
        }
        case ActivityLiveSettings.ITEM_BLOCK_USER: {
          selectedBlock = selectedItem;
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
      case ActivityLiveSettings.ITEM_DISTRIBUTE: {
        ModelOptions castLine = new ModelOptions(getString(R.string.item_distribute_cast_line),
            selectedDistribute != null && (selectedDistribute.getTitle()
                .equalsIgnoreCase(getString(R.string.item_distribute_cast_line))));

        ModelOptions meshType = new ModelOptions(getString(R.string.item_distribute_mesh_type),
            selectedDistribute != null && (selectedDistribute.getTitle()
                .equalsIgnoreCase(getString(R.string.item_distribute_mesh_type))));

        ModelOptions subscriber = new ModelOptions(getString(R.string.item_distribute_subscriber),
            selectedDistribute != null && (selectedDistribute.getTitle()
                .equalsIgnoreCase(getString(R.string.item_distribute_subscriber))));

        if (selectedDistribute == null) {
          subscriber.setSelected(true);
        }
        items.add(castLine);
        items.add(meshType);
        items.add(subscriber);
        break;
      }

      case ActivityLiveSettings.ITEM_LIVE_TYPE: {
        if (selectedLiveType == null) {
          items.add(new ModelOptions(1, getString(R.string.item_live_type_camera), true));
        } else {
          items.add(new ModelOptions(1, getString(R.string.item_live_type_camera),
              selectedLiveType != null && (selectedLiveType.getTitle()
                  .equalsIgnoreCase(getString(R.string.item_live_type_camera)))));
        }
        items.add(new ModelOptions(2, getString(R.string.item_live_type_album),
            selectedLiveType != null && (selectedLiveType.getTitle()
                .equalsIgnoreCase(getString(R.string.item_live_type_album)))));
        items.add(new ModelOptions(3, getString(R.string.item_live_type_radio_mode),
            selectedLiveType != null && (selectedLiveType.getTitle()
                .equalsIgnoreCase(getString(R.string.item_live_type_radio_mode)))));
        break;
      }

      case ActivityLiveSettings.ITEM_CATEGORY: {
        int categoryId = -100;
        if(liveVideo != null && liveVideo.getMediaCategory() != null) {
          categoryId = liveVideo.getMediaCategory().getId();
        }
        List<CategoryItem> listCategoryItems = DCCastApplication.listCategoryItems;

        for (int i = 0; i < listCategoryItems.size(); i++) {
          String selectedCategoryName = listCategoryItems.get(i).name;
          boolean isSelected = selectedCategory != null && (selectedCategory.getTitle()
              .equalsIgnoreCase(selectedCategoryName));
          if(selectedCategory == null && listCategoryItems.get(i).id == categoryId) {
            isSelected = true;
          }
          ModelOptions modelOptions =
              new ModelOptions(listCategoryItems.get(i).getId(), selectedCategoryName, isSelected);
          items.add(modelOptions);
        }
        break;
      }

      case ActivityLiveSettings.ITEM_SET_LOCK: {

        int liveMemberCount = 0;
        if (liveVideo != null && liveVideo.getLiveMember() != null) {
          liveMemberCount = Integer.parseInt(liveVideo.getLiveMember());
        }

        for (int i = 100; i < 5000; i += 100) {
          String selectedCategoryName = String.valueOf(i);
          if (selectedSetLock == null) {
            if (liveMemberCount > 0) {
              if (i == liveMemberCount) {
                selectedSetLock = new ModelOptions(String.valueOf(i), true);
              }
            } else {
              if (i == 100) {
                selectedSetLock = new ModelOptions(String.valueOf(i), true);
              }
            }
          }
          boolean isSelected = selectedSetLock != null && (selectedSetLock.getTitle()
              .equalsIgnoreCase(selectedCategoryName));
          ModelOptions modelOptions = new ModelOptions(selectedCategoryName, isSelected);
          items.add(modelOptions);
        }

        break;
      }

      case ActivityLiveSettings.ITEM_BROADCAST: {
        String broadcast = "";
        if(liveVideo != null && liveVideo.getLiveResolution() != null) {
          broadcast = liveVideo.getLiveResolution();
        }
        if(broadcast != null && !broadcast.isEmpty()) {
          selectedBroadcast = new ModelOptions(broadcast, true);
        }
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

      case ActivityLiveSettings.ITEM_ORIENTATION: {

        if (selectedOrientation == null) {
          items.add(new ModelOptions(0, getString(R.string.broadcast_portrait), true));
        } else {
          items.add(new ModelOptions(0, getString(R.string.broadcast_portrait),
              selectedOrientation != null && (selectedOrientation.getTitle()
                  .equalsIgnoreCase(getString(R.string.broadcast_portrait)))));
        }
        items.add(new ModelOptions(1, getString(R.string.broadcast_landscape),
            selectedOrientation != null && (selectedOrientation
                .getTitle()
                .equalsIgnoreCase(getString(R.string.broadcast_landscape)))));
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

    void close();
  }
}
