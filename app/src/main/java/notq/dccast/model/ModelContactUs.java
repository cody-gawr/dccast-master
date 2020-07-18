package notq.dccast.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import notq.dccast.model.user.ModelUser;

public class ModelContactUs implements Serializable {
  @SerializedName("id")
  public int id;
  @SerializedName("title")
  public String title;
  @SerializedName("question")
  public String question;
  @SerializedName("answer")
  public String answer;
  @SerializedName("answer_date")
  public String answer_date;
  @SerializedName("created")
  public String created;
  @SerializedName("user")
  public ModelUser user;
  @SerializedName("state")
  public String state;
  @SerializedName("kinds")
  public String kinds;
  @SerializedName("img_path")
  public String imgPath;
  @SerializedName("file_name")
  public String fileName;

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
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

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public String getAnswer_date() {
    return answer_date;
  }

  public void setAnswer_date(String answer_date) {
    this.answer_date = answer_date;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getKinds() {
    return kinds;
  }

  public void setKinds(String kinds) {
    this.kinds = kinds;
  }

  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public ModelUser getUser() {
    return user;
  }

  public void setUser(ModelUser user) {
    this.user = user;
  }
}
