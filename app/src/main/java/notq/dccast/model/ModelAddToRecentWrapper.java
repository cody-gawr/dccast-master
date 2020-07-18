package notq.dccast.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ModelAddToRecentWrapper {
  @SerializedName("results") public List<ModelAddToRecent> recentMedias;

  public List<ModelAddToRecent> getRecentMedias() {
    return recentMedias;
  }

  public void setRecentMedias(List<ModelAddToRecent> recentMedias) {
    this.recentMedias = recentMedias;
  }
}
