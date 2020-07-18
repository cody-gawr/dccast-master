package notq.dccast.api.login;

import com.google.gson.JsonObject;
import java.util.List;
import notq.dccast.model.ModelPublicKeyResult;
import notq.dccast.model.user.ModelAdultCertification;
import notq.dccast.model.user.ModelDCInsideLoginResult;
import notq.dccast.model.user.ModelLoginUserWrapper;
import notq.dccast.model.user.ModelSignUpResult;
import notq.dccast.model.user.ModelUserWrapper;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface LoginAPIInterface {
  @FormUrlEncoded
  @POST("/api/auth/login")
  Call<ModelLoginUserWrapper> login(@Field("username") String username,
      @Field("password") String password);

  @FormUrlEncoded
  @POST("/join/mobile_app_login.php/")
  Call<List<ModelDCInsideLoginResult>> dcInsideLogin(@Field("user_id") String username,
      @Field("user_pw") String password);

  @Headers({
      "Content-Type: application/x-www-form-urlencoded",
      "User-Agent: dcinside.castapp"
  })
  @FormUrlEncoded
  @POST("/api/dccast/adult_certification")
  Call<ModelAdultCertification> checkAdultCertification(
      @Field("company_code") String companyCode,
      @Field("userCode") String userCode);

  @GET("/api/profiles")
  Call<ModelUserWrapper> checkUserExists(@Query("user__username") String username,
      @Query("password") String password);

  @FormUrlEncoded
  @POST("/api/auth/signup")
  Call<ModelSignUpResult> signUp(@Field("username") String username,
      @Field("password") String password, @Field("email") String email);

  @PUT("/api/auth/logout")
  Call<JsonObject> logout();

  @Headers({
      "Content-Type: application/x-www-form-urlencoded",
      "User-Agent: dcinside.castapp"
  })
  @FormUrlEncoded
  @POST("/join/mobile_app_key_verification_3rd.php/")
  Call<List<ModelPublicKeyResult>> getPublicKey(@Field("value_token") String valueToken,
      @Field("signature") String signature, @Field("pkg") String pkg, @Field("vName") String vName);
}