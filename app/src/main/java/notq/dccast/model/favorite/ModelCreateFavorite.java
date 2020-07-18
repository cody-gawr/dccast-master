package notq.dccast.model.favorite;

import com.google.gson.annotations.SerializedName;

public class ModelCreateFavorite {
  /*
  {
    "id": 130,
    "user": 40,
    "media": 1033,
    "created": "2019-02-21T03:31:47.136950Z"
  }
  */

  @SerializedName("id") public int id;
  @SerializedName("user") public int userId;
  @SerializedName("media") public int mediaId;
  @SerializedName("created") public String createdDate;
}
