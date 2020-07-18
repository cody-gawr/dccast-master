package notq.dccast.model.ads;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import notq.dccast.model.user.ModelUser;
import notq.dccast.util.Url;

public class ModelAds implements Serializable {
  @SerializedName("id")
  @Expose
  public int id;

  @SerializedName("title")
  @Expose
  public String title;

  @SerializedName("adver_type")
  @Expose
  public String adverType;

  @SerializedName("content")
  @Expose
  public String content;

  @SerializedName("full_show")
  @Expose
  public boolean fullShow;

  @SerializedName("file")
  @Expose
  public String file;

  @SerializedName("user_rate")
  @Expose
  public String userRate;

  @SerializedName("number_of_click")
  @Expose
  public int numberOfClick;

  @SerializedName("state")
  @Expose
  public String state;

  @SerializedName("deploy_date")
  @Expose
  public String deployDate;

  @SerializedName("user")
  @Expose
  public ModelUser user;

  @SerializedName("create_date")
  @Expose
  public String createDate;

  @SerializedName("locate")
  @Expose
  public ModelLocate locate;
  @SerializedName("affil")
  @Expose
  public ModelAffil affil;
  @SerializedName("show_category")
  public List<ModelShowCategory> showCategories;

  public ModelAffil getAffil() {
    return affil;
  }

  public void setAffil(ModelAffil affil) {
    this.affil = affil;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAdverType() {
    return adverType;
  }

  public void setAdverType(String adverType) {
    this.adverType = adverType;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean isFullShow() {
    return fullShow;
  }

  public void setFullShow(boolean fullShow) {
    this.fullShow = fullShow;
  }

  public String getFile() {
    return Url.baseUrl + "/" + file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public String getUserRate() {
    return userRate;
  }

  public void setUserRate(String userRate) {
    this.userRate = userRate;
  }

  public int getNumberOfClick() {
    return numberOfClick;
  }

  public void setNumberOfClick(int numberOfClick) {
    this.numberOfClick = numberOfClick;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getDeployDate() {
    return deployDate;
  }

  public void setDeployDate(String deployDate) {
    this.deployDate = deployDate;
  }

  public ModelUser getUser() {
    return user;
  }

  public void setUser(ModelUser user) {
    this.user = user;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  public ModelLocate getLocate() {
    return locate;
  }

  public void setLocate(ModelLocate locate) {
    this.locate = locate;
  }

  public List<ModelShowCategory> getShowCategories() {
    return showCategories;
  }

  public void setShowCategories(List<ModelShowCategory> showCategories) {
    this.showCategories = showCategories;
  }
}
