package notq.dccast.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ModelListHeaderWrapper {
  @SerializedName("results") public List<ModelListHeader> headerList;

  public List<ModelListHeader> getHeaderList() {
    return headerList;
  }

  public void setHeaderList(List<ModelListHeader> headerList) {
    this.headerList = headerList;
  }
}
