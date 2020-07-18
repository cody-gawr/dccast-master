package notq.dccast.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ModelDCInsideLoginResult implements Serializable {
  @SerializedName("result")
  @Expose
  public boolean result;

  @SerializedName("user_id")
  @Expose
  public String userId;

  @SerializedName("user_no")
  @Expose
  public String userNo;

  @SerializedName("name")
  @Expose
  public String name;

  @SerializedName("stype")
  @Expose
  public String sType;

  @SerializedName("cause")
  @Expose
  public String cause;
  @SerializedName("is_adult")
  @Expose
  public int isAdult;
  @SerializedName("is_dormancy")
  @Expose
  public int isDormancy;

  public String getCause() {
    return cause;
  }

  public void setCause(String cause) {
    this.cause = cause;
  }

  public boolean isResult() {
    return result;
  }

  public void setResult(boolean result) {
    this.result = result;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserNo() {
    return userNo;
  }

  public void setUserNo(String userNo) {
    this.userNo = userNo;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getsType() {
    return sType;
  }

  public void setsType(String sType) {
    this.sType = sType;
  }

  public int getIsAdult() {
    return isAdult;
  }

  public void setIsAdult(int isAdult) {
    this.isAdult = isAdult;
  }

  public int getIsDormancy() {
    return isDormancy;
  }

  public void setIsDormancy(int isDormancy) {
    this.isDormancy = isDormancy;
  }
}
