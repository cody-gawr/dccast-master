package notq.dccast.model.mandu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import notq.dccast.model.user.ModelUser;

public class ModelMandu {
  @SerializedName("id")
  @Expose
  public int id;
  @SerializedName("user")
  @Expose
  public ModelUser user;
  @SerializedName("point")
  @Expose
  public int point;
  @SerializedName("last_update")
  @Expose
  public String lastUpdate;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public ModelUser getUser() {
    return user;
  }

  public void setUser(ModelUser user) {
    this.user = user;
  }

  public int getPoint() {
    return point;
  }

  public void setPoint(int point) {
    this.point = point;
  }

  public String getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(String lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
