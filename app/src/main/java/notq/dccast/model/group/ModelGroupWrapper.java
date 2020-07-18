package notq.dccast.model.group;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class ModelGroupWrapper implements Serializable {
  @SerializedName("results")
  public List<ModelGroup> groups;
  @SerializedName("next")
  public String next;
  @SerializedName("count")
  public int count;

  public List<ModelGroup> getGroups() {
    return groups;
  }

  public void setGroups(List<ModelGroup> groups) {
    this.groups = groups;
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

