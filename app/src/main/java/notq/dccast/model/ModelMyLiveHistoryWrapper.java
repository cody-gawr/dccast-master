package notq.dccast.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class ModelMyLiveHistoryWrapper implements Serializable {
  @SerializedName("results")
  public List<ModelLiveHistoryVideoResponse> videoWrappers;
  @SerializedName("next")
  public String next;
  @SerializedName("count")
  public int count;

  public List<ModelLiveHistoryVideoResponse> getVideoWrappers() {
    return videoWrappers;
  }

  public void setVideoWrappers(List<ModelLiveHistoryVideoResponse> videoWrappers) {
    this.videoWrappers = videoWrappers;
  }

  public String getNext() {
    return next;
  }

  public void setNext(String next) {
    this.next = next;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
