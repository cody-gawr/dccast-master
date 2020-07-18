package notq.dccast.screens.home.interfaces;

import notq.dccast.model.comment.ModelSticker;

public interface StickerListener {
  void onStickerSelected(boolean isEdit, ModelSticker sticker);

  void onStickerLongClick(ModelSticker sticker);

  void onStickerLongClickRelease();
}
