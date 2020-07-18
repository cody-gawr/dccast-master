package notq.dccast.model.comment;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSticker implements Parcelable {
  public static final Parcelable.Creator<ModelSticker> CREATOR =
      new Parcelable.Creator<ModelSticker>() {
        @Override public ModelSticker createFromParcel(Parcel source) {
          return new ModelSticker(source);
        }

        @Override public ModelSticker[] newArray(int size) {
          return new ModelSticker[size];
        }
      };
  @SerializedName("detail_idx") @Expose private String detailIdx;
  @SerializedName("package_idx") @Expose private String packageIdx;
  @SerializedName("title") @Expose private String title;
  @SerializedName("img") @Expose private String img;

  public ModelSticker() {
  }

  protected ModelSticker(Parcel in) {
    this.detailIdx = in.readString();
    this.packageIdx = in.readString();
    this.title = in.readString();
    this.img = in.readString();
  }

  public String getDetailIdx() {
    return detailIdx;
  }

  public void setDetailIdx(String detailIdx) {
    this.detailIdx = detailIdx;
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

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.detailIdx);
    dest.writeString(this.packageIdx);
    dest.writeString(this.title);
    dest.writeString(this.img);
  }
}
