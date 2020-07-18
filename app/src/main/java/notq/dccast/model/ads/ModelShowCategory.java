package notq.dccast.model.ads;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import notq.dccast.model.user.ModelUser;

public class ModelShowCategory implements Serializable {
  @SerializedName("id")
  @Expose
  public int id;

  @SerializedName("name")
  @Expose
  public String name;

  @SerializedName("create_date")
  @Expose
  public String createDate;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }
}
