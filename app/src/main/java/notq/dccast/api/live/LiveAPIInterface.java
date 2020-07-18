package notq.dccast.api.live;

import com.google.gson.JsonObject;
import notq.dccast.model.ModelVideoWrapper;
import notq.dccast.model.live.FollowerStatResponse;
import notq.dccast.model.live.LiveResponse;
import notq.dccast.model.live.ModelBlockResult;
import notq.dccast.model.live.ModelBlockResultWrapper;
import notq.dccast.model.live.StatResponse;
import notq.dccast.model.user.ModelResult;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LiveAPIInterface {
  @Headers("Content-Type: application/json") @POST("/media/media_list/")
  Call<LiveResponse> createLive(
      @Body RequestBody body);

  @GET("/api/profiles/stat")
  Call<StatResponse> getStat(@Query("user_id") int userId);

  @GET("/api/follow/followers_stat")
  Call<FollowerStatResponse> getFollowerStat(@Query("user_id") int userId);

  @Multipart @POST("upload_thumbnail")
  Call<JsonObject> uploadThumbnail(@Part MultipartBody.Part file);

  @FormUrlEncoded @POST("/media/update")
  Call<JsonObject> updateLive(@Field("kind") String kind, @Field("media_id") int mediaId,
      @Field("is_stop") boolean isStop, @Field("duration") long duration,
      @Field("views") int views);

  @PUT("/media/media_list/{media_id}/")
  Call<JsonObject> updateLive(@Path("media_id") int mediaId, @Body RequestBody body);

  @FormUrlEncoded @POST("/media/update")
  Call<JsonObject> updateLive(@Field("kind") String kind, @Field("media_id") int mediaId,
      @Field("is_stop") boolean isStop);

  @FormUrlEncoded @POST("/api/update")
  Call<ModelResult> checkNickname(@Field("kind") String kind, @Field("user") int userId,
      @Field("nick_name") String nickname);

  @FormUrlEncoded @POST("/media/forcedoff_list/")
  Call<ModelBlockResult> sendBlockRequest(@Field("type") String type,
      @Field("category") String category,
      @Field("media") int media, @Field("forced_user") int forced_user,
      @Field("forcing_user") int forcing_user);

  @GET("/media/forcedoff_list/")
  Call<ModelBlockResultWrapper> checkBlockRequest(
      @Query("media") int media, @Query("forced_user") int forced_user);

  @GET("/media/media") Call<ModelVideoWrapper> getMediaById(@Query("id") int videoId);
}