package notq.dccast.model.ads;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ModelAffil implements Serializable {
  @SerializedName("id")
  @Expose
  public int id;

  @SerializedName("company_name")
  @Expose
  public String companyName;

  @SerializedName("adver_name")
  @Expose
  public String adverName;

  @SerializedName("user")
  @Expose
  public int user;

  @SerializedName("manager_name")
  @Expose
  public String managerName;

  @SerializedName("contact")
  @Expose
  public String contact;

  @SerializedName("phone")
  @Expose
  public String phone;

  @SerializedName("email")
  @Expose
  public String email;

  @SerializedName("site")
  @Expose
  public String site;

  @SerializedName("remarks")
  @Expose
  public String remarks;

  @SerializedName("create_date")
  @Expose
  public String create_date;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUser() {
    return user;
  }

  public void setUser(int user) {
    this.user = user;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getAdverName() {
    return adverName;
  }

  public void setAdverName(String adverName) {
    this.adverName = adverName;
  }

  public String getManagerName() {
    return managerName;
  }

  public void setManagerName(String managerName) {
    this.managerName = managerName;
  }

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getCreate_date() {
    return create_date;
  }

  public void setCreate_date(String create_date) {
    this.create_date = create_date;
  }
}
