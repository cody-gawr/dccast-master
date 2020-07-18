package notq.dccast.model.category;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class ModelCategory implements Serializable {
  @SerializedName("results") public List<CategoryItem> categoryItems;
}
