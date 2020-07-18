package notq.dccast.model.comment;

import android.graphics.Bitmap;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.realm.annotations.Ignore;

public class ModelTab {
  @SerializedName("package_idx") @Expose private String packageIdx;
  @SerializedName("title") @Expose private String title;
  @SerializedName("img") @Expose private String img;

  @Ignore
  private boolean selected = false;
  private Bitmap imageContent;

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public Bitmap getImageContent() {
    return imageContent;
  }

  public void setImageContent(Bitmap imageContent) {
    this.imageContent = imageContent;
  }

  public String getPackageIdx() {
    return packageIdx;
  }

  public void setPackageIdx(String packageIdx) {
    this.packageIdx = packageIdx;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }
}
