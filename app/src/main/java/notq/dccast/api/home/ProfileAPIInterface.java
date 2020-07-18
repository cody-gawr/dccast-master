package notq.dccast.api.home;

import com.google.gson.JsonObject;
import java.util.HashMap;
import notq.dccast.model.ModelContactUsWrapper;
import notq.dccast.model.user.ModelResult;
import notq.dccast.model.user.profile.ModelProfile;
import notq.dccast.model.user.profile.ModelUserProfileWrapper;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProfileAPIInterface {
  @FormUrlEncoded
  @PUT("/api/profile_list/{id}/")
  Call<ModelProfile> updateProfile(@Path("id") int userId, @FieldMap HashMap<String, Object> data);

  @GET("/api/profiles")
  Call<ModelUserProfileWrapper> getProfile(@Query("id") int userId);

  @DELETE("/api/recent/")
  Call<ModelResult> deleteRecentHistory(@Query("user") int userId,
      @Query("media__category") String category);

  @GET("/config/version")
  Call<JsonObject> getAppVersion();

  @GET("/property/question/")
  Call<ModelContactUsWrapper> getContactUsList(@Query("user") int userId, @Query("limit") int limit,
      @Query("page") int page);

  @FormUrlEncoded
  @POST("/property/question_list/")
  Call<JsonObject> sendContact(@Field("title") String title,
      @Field("kinds") String kinds,
      @Field("question") String question,
      @Field("user") int user,
      @Field("file_name") String file_name,
      @Field("img_path") String img_path);
}