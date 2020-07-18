package notq.dccast.model.mandu;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ModelManduWrapper {
  @SerializedName("count")
  public int count;
  @SerializedName("results")
  public List<ModelMandu> manduList;
  @SerializedName("next")
  public String next;
  @SerializedName("total_pages")
  public int totalPages;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public List<ModelMandu> getManduList() {
    return manduList;
  }

  public void setManduList(List<ModelMandu> manduList) {
    this.manduList = manduList;
  }

  public String getNext() {
    return next;
  }

  public void setNext(String next) {
    this.next = next;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }
}
