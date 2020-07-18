package notq.dccast.model.subscribe;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ResponseSubscribeWrapper {
  @SerializedName("results")
  public List<ResponseSubscribe> subscribeList;
}
