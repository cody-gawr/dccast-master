package notq.dccast.model.comment;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ModelStickerWrapper {
  @SerializedName("tab") public List<ModelTab> tabList;
  @SerializedName("list") public List<List<ModelSticker>> stickerList;

  public List<ModelTab> getTabList() {
    return tabList;
  }

  public void setTabList(List<ModelTab> tabList) {
    this.tabList = tabList;
  }

  public List<List<ModelSticker>> getStickerList() {
    return stickerList;
  }

  public void setStickerList(
      List<List<ModelSticker>> stickerList) {
    this.stickerList = stickerList;
  }
}
