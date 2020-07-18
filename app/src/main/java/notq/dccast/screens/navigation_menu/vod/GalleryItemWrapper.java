package notq.dccast.screens.navigation_menu.vod;

import java.util.List;
import notq.dccast.model.vod.GalleryResponse;

public class GalleryItemWrapper {
  private String sectionTitle;
  private List<GalleryResponse> itemList;

  public String getSectionTitle() {
    return sectionTitle;
  }

  public void setSectionTitle(String sectionTitle) {
    this.sectionTitle = sectionTitle;
  }

  public List<GalleryResponse> getItemList() {
    return itemList;
  }

  public void setItemList(List<GalleryResponse> itemList) {
    this.itemList = itemList;
  }
}
