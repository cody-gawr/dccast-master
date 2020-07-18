package notq.dccast.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import notq.dccast.model.ads.ModelAds;

public class ModelAdsWrapper implements Serializable {
  @SerializedName("results")
  public List<ModelAds> ads;
  @SerializedName("next")
  public String next;
  @SerializedName("count")
  public int count;

  public List<ModelAds> getAds() {
    return ads;
  }

  public void setAds(List<ModelAds> ads) {
    this.ads = ads;
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
