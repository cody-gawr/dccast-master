package notq.dccast.api.home;

import com.google.gson.JsonObject;
import java.util.List;
import notq.dccast.model.mandu.ModelManduDCInside;
import notq.dccast.model.user.ModelResult;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ManduAPIInterface {

  @Headers({
      "Content-Type: application/x-www-form-urlencoded",
      "User-Agent: dcinside.castapp"
  })
  @FormUrlEncoded
  @POST("/api/dccast/cash_total")
  Call<List<ModelManduDCInside>> getManduFromDcInside(@Field("user_id") String userId,
      @Field("app_id") String appId);

  @Headers({
      "Content-Type: application/x-www-form-urlencoded",
      "User-Agent: dcinside.castapp"
  })
  @FormUrlEncoded
  @POST("/api/dccast/webview")
  Call<ResponseBody> getManduHistoryWebView(@Field("user_id") String userId,
      @Field("app_id") String appId);

  @FormUrlEncoded
  @POST("/api/dccast/cash_gift")
    //TODO CHANGE
    //List<ModelManduDCInside>
  Call<JsonObject> sendManduToDcInside(@Field("company_code") String companyCode,
      @Field("user_id") String userId,
      @Field("app_id") String appId, @Field("present_cash") int manduCount,
      @Field("r_no") String userNo);

  @FormUrlEncoded
  @POST("/api/update")
  Call<ModelResult> checkManduSent(@Field("kind") String kind, @Field("from_user") int fromUser,
      @Field("to_user") int toUser, @Field("transaction_type") String transactionType,
      @Field("quantity") int quantity);
}
