package notq.dccast.model.favorite;

import com.google.gson.annotations.SerializedName;

public class ModelGetFavorite {
  @SerializedName("status") boolean isFavorite;
  @SerializedName("id") int id;

  public boolean isFavorite() {
    return isFavorite;
  }

  public void setFavorite(boolean favorite) {
    isFavorite = favorite;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
