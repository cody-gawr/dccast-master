package notq.dccast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ModelNotificationNew implements Serializable {
  @SerializedName("live") @Expose public boolean live;
  @SerializedName("notice") @Expose public boolean notice;
  @SerializedName("vod") @Expose public boolean vod;

  public boolean isLive() {
    return live;
  }

  public void setLive(boolean live) {
    this.live = live;
  }

  public boolean isNotice() {
    return notice;
  }

  public void setNotice(boolean notice) {
    this.notice = notice;
  }

  public boolean isVod() {
    return vod;
  }

  public void setVod(boolean vod) {
    this.vod = vod;
  }
}
