package notq.dccast.model.mandu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelManduDCInside {
  @SerializedName("result")
  @Expose
  public String result;
  @SerializedName("cause")
  @Expose
  public String cause;

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getCause() {
    return cause;
  }

  public void setCause(String cause) {
    this.cause = cause;
  }

  public double getManduCount() {
    if (cause == null || cause.isEmpty()) {
      return 0;
    }
    double manduCount = 0;
    try {
      manduCount = Double.parseDouble(cause);
    } catch (Exception ex) {
      return 0;
    }

    return manduCount;
  }
}
