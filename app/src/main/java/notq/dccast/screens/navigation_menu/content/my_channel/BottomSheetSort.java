package notq.dccast.screens.navigation_menu.content.my_channel;

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
import notq.dccast.R;
import notq.dccast.databinding.FragmentBottomSortBinding;
import notq.dccast.model.live.ModelOptions;
import notq.dccast.screens.navigation_menu.live.AdapterBottomSheetOptions;

public class BottomSheetSort extends Fragment {
  public static final int TYPE_MY_CHANNEL = 123;
  public static final int TYPE_SUBSCRIBE = 124;
  private static BottomSheetSort instance;
  @SuppressLint("StaticFieldLeak") private FragmentBottomSortBinding binding;
  private AdapterBottomSheetOptions adapter;
  private OnItemSelectedListener onItemSelectedListener;
  private ModelOptions selectedSort;
  private int type = TYPE_MY_CHANNEL;

  public static synchronized BottomSheetSort getInstance(int type) {
    if (instance == null) {
      instance = new BottomSheetSort();
    }

    Bundle args = new Bundle();
    args.putInt("type", type);
    instance.setArguments(args);

    return instance;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      type = getArguments().getInt("type");
    }
  }

  public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
    this.onItemSelectedListener = onItemSelectedListener;
  }

  @SuppressLint("SetTextI18n") @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sort, container, false);

    initRecyclerView();
    return binding.getRoot();
  }

  private void initRecyclerView() {
    adapter = new AdapterBottomSheetOptions(getActivity(), position -> {
      ModelOptions selectedItem = adapter.getItem(position);

      selectedSort = selectedItem;

      if (onItemSelectedListener != null) {
        onItemSelectedListener.onItemSelected(selectedItem);
      }
    });

    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.setAdapter(adapter);

    ArrayList<ModelOptions> items = new ArrayList<>();

    if (type == TYPE_MY_CHANNEL) {
      ModelOptions recent = new ModelOptions(0, getString(R.string.my_channel_sort_recent),
          selectedSort != null && (selectedSort.getTitle()
              .equalsIgnoreCase(getString(R.string.my_channel_sort_recent))));
      //ModelOptions favorite = new ModelOptions(1, getString(R.string.my_channel_sort_favorite),
      //    selectedSort != null && (selectedSort.getTitle()
      //        .equalsIgnoreCase(getString(R.string.my_channel_sort_favorite))));
      ModelOptions views = new ModelOptions(2, getString(R.string.my_channel_sort_views),
          selectedSort != null && (selectedSort.getTitle()
              .equalsIgnoreCase(getString(R.string.my_channel_sort_views))));

      if (selectedSort == null) {
        recent.setSelected(true);
      }
      items.add(recent);
      //items.add(favorite);
      items.add(views);
    } else {
      ModelOptions recent = new ModelOptions(0, getString(R.string.subscribe_sort_recent),
          selectedSort != null && (selectedSort.getTitle()
              .equalsIgnoreCase(getString(R.string.subscribe_sort_recent))));
      ModelOptions onAir = new ModelOptions(1, getString(R.string.subscribe_sort_on_air),
          selectedSort != null && (selectedSort.getTitle()
              .equalsIgnoreCase(getString(R.string.subscribe_sort_on_air))));
      ModelOptions title = new ModelOptions(2, getString(R.string.subscribe_sort_title),
          selectedSort != null && (selectedSort.getTitle()
              .equalsIgnoreCase(getString(R.string.subscribe_sort_title))));

      if (selectedSort == null) {
        recent.setSelected(true);
      }
      items.add(recent);
      items.add(onAir);
      items.add(title);
    }

    adapter.setListOptions(items);
  }

  public interface OnItemSelectedListener {
    void onItemSelected(ModelOptions item);
  }
}
