package notq.dccast.screens.home.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.model.comment.ModelSticker;
import notq.dccast.screens.home.interfaces.StickerListener;
import notq.dccast.screens.home.video_detail.StickerPaginationItem;

public class AdapterFragmentSticker extends FragmentStatePagerAdapter {
  private final int PAGINATION = 8;
  private List<ModelSticker> items;

  private boolean isEdit = false;
  private boolean isFullScreen = false;

  private StickerListener stickerListener;

  public AdapterFragmentSticker(@NonNull FragmentManager fm, boolean isEdit, boolean isFullScreen,
      StickerListener stickerListener) {
    super(fm);
    this.isEdit = isEdit;
    this.isFullScreen = isFullScreen;
    this.stickerListener = stickerListener;
  }

  public void setStickerListener(StickerListener stickerListener) {
    this.stickerListener = stickerListener;
  }

  public void setStickerItems(List<ModelSticker> items) {
    this.items = items;
    notifyDataSetChanged();
  }

  @NonNull @Override public Fragment getItem(int position) {
    ArrayList<ModelSticker> loadList = new ArrayList<>();
    int startIndex = position * PAGINATION;
    int endIndex = ((position + 1) * PAGINATION);
    if (endIndex > items.size()) {
      endIndex = items.size();
    }

    for (int i = startIndex; i < endIndex; i++) {
      loadList.add(items.get(i));
    }
    StickerPaginationItem item = StickerPaginationItem.getInstance(isEdit, isFullScreen, loadList);
    item.setStickerItemSelected(new StickerListener() {
      @Override
      public void onStickerSelected(boolean isEdit, ModelSticker sticker) {
        stickerListener.onStickerSelected(isEdit, sticker);
      }

      @Override public void onStickerLongClick(ModelSticker sticker) {
        stickerListener.onStickerLongClick(sticker);
      }

      @Override public void onStickerLongClickRelease() {
        stickerListener.onStickerLongClickRelease();
      }
    });
    return item;
  }

  @Override public int getCount() {
    if (items == null || items.isEmpty()) {
      return 0;
    }

    int pageCount = items.size() / PAGINATION;
    if (items.size() - pageCount * PAGINATION > 0) {
      pageCount++;
    }
    return pageCount;
  }
}