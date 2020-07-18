package notq.dccast.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import java.io.Serializable;

public class ModelChildUser extends RealmObject implements Serializable {
  @PrimaryKey
  @SerializedName("id")
  @Expose
  public Integer id;
  @SerializedName("email")
  @Expose
  public String email;
  @SerializedName("username")
  @Expose
  public String username;
  @SerializedName("is_superuser")
  @Expose
  public Boolean isSuperuser;
  @SerializedName("is_staff")
  @Expose
  public Boolean isStaff;
  @SerializedName("last_login")
  @Expose
  public String lastLogin;
  @SerializedName("date_joined")
  @Expose
  public String dateJoined;
  @SerializedName("first_name")
  @Expose
  public String firstName;
  @SerializedName("last_name")
  @Expose
  public String lastName;

  @Ignore
  public boolean selected = false;

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Boolean getSuperuser() {
    return isSuperuser;
  }

  public void setSuperuser(Boolean superuser) {
    isSuperuser = superuser;
  }

  public Boolean getStaff() {
    return isStaff;
  }

  public void setStaff(Boolean staff) {
    isStaff = staff;
  }

  public String getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(String lastLogin) {
    this.lastLogin = lastLogin;
  }

  public String getDateJoined() {
    return dateJoined;
  }

  public void setDateJoined(String dateJoined) {
    this.dateJoined = dateJoined;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
