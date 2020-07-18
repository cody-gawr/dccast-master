package notq.dccast.model.live;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ModelBlockResultWrapper {
  @SerializedName("results") public List<ModelBlockResult> blockResults;

  public List<ModelBlockResult> getBlockResults() {
    return blockResults;
  }

  public void setBlockResults(List<ModelBlockResult> blockResults) {
    this.blockResults = blockResults;
  }
}
