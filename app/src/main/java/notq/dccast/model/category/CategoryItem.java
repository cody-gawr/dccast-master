package notq.dccast.model.category;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.io.Serializable;
import notq.dccast.DCCastApplication;

public class CategoryItem extends RealmObject implements Serializable {
  @PrimaryKey @SerializedName("id") public int id;
  @SerializedName("name") public String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    if (name == null) {
      name = "";
      if (DCCastApplication.listCategoryItems != null) {
        for (CategoryItem listCategoryItem : DCCastApplication.listCategoryItems) {
          if (listCategoryItem.getId() == id) {
            return listCategoryItem.getNameDirect();
          }
        }
      }
    }
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNameDirect() {
    return name == null ? "" : name;
  }

  @NonNull @Override public String toString() {
    return name;
  }

  public CategoryItem() {
  }
}
