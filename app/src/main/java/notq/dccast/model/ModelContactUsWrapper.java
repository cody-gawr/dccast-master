package notq.dccast.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class ModelContactUsWrapper implements Serializable {
  @SerializedName("results")
  public List<ModelContactUs> contactList;
  @SerializedName("next")
  public String next;
  @SerializedName("count")
  public int count;

  public List<ModelContactUs> getContactList() {
    return contactList;
  }

  public void setContactList(List<ModelContactUs> contactList) {
    this.contactList = contactList;
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
