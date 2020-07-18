package notq.dccast.model.favorite;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import notq.dccast.model.comment.ModelComment;

public class ModelFavoriteWrapper {
  @SerializedName("results") public List<ModelFavorite> favoriteList;
}
