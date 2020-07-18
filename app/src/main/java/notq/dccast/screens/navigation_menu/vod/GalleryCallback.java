package notq.dccast.screens.navigation_menu.vod;

import notq.dccast.model.vod.GalleryResponse;

public interface GalleryCallback {
  void onGalleryItemClicked(GalleryResponse gallery);
}
