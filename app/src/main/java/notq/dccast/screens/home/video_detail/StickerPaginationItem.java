package notq.dccast.screens.home.video_detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.R;
import notq.dccast.databinding.FragmentStickerPaginationBinding;
import notq.dccast.model.comment.ModelSticker;
import notq.dccast.screens.home.adapter.AdapterStickerPagination;
import notq.dccast.screens.home.interfaces.StickerListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class StickerPaginationItem extends Fragment {
  private List<ModelSticker> stickerList;
  private FragmentStickerPaginationBinding binding;
  private AdapterStickerPagination adapterStickerPagination;
  private StickerListener stickerListener;
  private boolean isEdit = false;
  private boolean isFullScreen = false;

  public StickerPaginationItem() {
    // Required empty public constructor
  }

  public static StickerPaginationItem getInstance(boolean isEdit, boolean isFullScreen,
      ArrayList<ModelSticker> stickerList) {
    StickerPaginationItem instance = new StickerPaginationItem();
    Bundle headerData = new Bundle();
    headerData.putBoolean("isEdit", isEdit);
    headerData.putBoolean("isFullScreen", isFullScreen);
    headerData.putParcelableArrayList("stickerList", stickerList);
    instance.setArguments(headerData);
    return instance;
  }

  public void setStickerItemSelected(
      StickerListener stickerListener) {
    this.stickerListener = stickerListener;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      Bundle bundle = getArguments();
      stickerList = bundle.getParcelableArrayList("stickerList");
      isEdit = bundle.getBoolean("isEdit", false);
      isFullScreen = bundle.getBoolean("isFullScreen", false);
    }
  }

  @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_sticker_pagination, container, false);

    binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));

    adapterStickerPagination =
        new AdapterStickerPagination(getActivity(), isEdit, new StickerListener() {
          @Override
          public void onStickerSelected(boolean isEdit, ModelSticker sticker) {
            if (stickerListener != null) {
              stickerListener.onStickerSelected(isEdit, sticker);
            }
          }

          @Override public void onStickerLongClick(ModelSticker sticker) {
            if (stickerListener != null) {
              stickerListener.onStickerLongClick(sticker);
            }
          }

          @Override public void onStickerLongClickRelease() {
            if (stickerListener != null) {
              stickerListener.onStickerLongClickRelease();
            }
          }
        });
    binding.recyclerView.setAdapter(adapterStickerPagination);
    setDatas();
    return binding.getRoot();
  }

  @SuppressLint("SetTextI18n") private void setDatas() {
    adapterStickerPagination.setStickers(stickerList);
  }
}
