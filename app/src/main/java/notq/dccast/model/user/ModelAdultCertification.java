package notq.dccast.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ModelAdultCertification implements Serializable {
  @SerializedName("result")
  @Expose
  public String result;

  @SerializedName("userKey")
  @Expose
  public String userKey;

  @SerializedName("adult_cert")
  @Expose
  public String adultCert;

  @SerializedName("exp_date")
  @Expose
  public String expireDate;

  @SerializedName("info")
  @Expose
  public String info;

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getUserKey() {
    return userKey;
  }

  public void setUserKey(String userKey) {
    this.userKey = userKey;
  }

  public String getAdultCert() {
    return adultCert;
  }

  public void setAdultCert(String adultCert) {
    this.adultCert = adultCert;
  }

  public String getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(String expireDate) {
    this.expireDate = expireDate;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }
}
