package notq.dccast.model.user;

import com.google.gson.annotations.SerializedName;

public class ModelShareResult {
  @SerializedName("result")
  public boolean result;

  public boolean isResult() {
    return result;
  }

  public void setResult(boolean result) {
    this.result = result;
  }
}
