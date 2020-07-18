package notq.dccast.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class ModelVideoWrapper implements Serializable {
  @SerializedName("results")
  public List<ModelVideo> videoList;
  @SerializedName("next")
  public String next;

  @SerializedName("count")
  public int count;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public List<ModelVideo> getVideoList() {
    return videoList;
  }

  public void setVideoList(List<ModelVideo> videoList) {
    this.videoList = videoList;
  }

  public String getNext() {
    return next;
  }

  public void setNext(String next) {
    this.next = next;
  }
}
