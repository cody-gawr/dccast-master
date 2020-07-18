package notq.dccast.api.home;

import com.google.gson.JsonObject;
import java.util.HashMap;
import notq.dccast.model.ModelAddToRecentResult;
import notq.dccast.model.ModelAddToRecentWrapper;
import notq.dccast.model.ModelVideoWrapper;
import notq.dccast.model.favorite.ModelCreateFavorite;
import notq.dccast.model.favorite.ModelFavoriteWrapper;
import notq.dccast.model.friends.ModelFriendRequestWrapper;
import notq.dccast.model.subscribe.ResponseCreateSubscribe;
import notq.dccast.model.subscribe.ResponseSubscribeWrapper;
import notq.dccast.model.user.ModelShareResult;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VideoDetailAPIInterface {
  @GET("/media/media/") Call<ModelVideoWrapper> getRelatedVideoList(@Query("user_id") int userId,
      @Query("category") String category, @Query("limit") int limit);

  @GET("/api/favorite/") Call<ModelFavoriteWrapper> getFavorite(@Query("user") int userId,
      @Query("media") int mediaId);

  @POST("/api/favorite_list/") Call<ModelCreateFavorite> createFavorite(@Body RequestBody body);

  @FormUrlEncoded
  @POST("/api/recent_list/") Call<ModelAddToRecentResult> createRecent(@Field("user") int user,
      @Field("media") int media);

  @GET("/api/recent") Call<ModelAddToRecentWrapper> getRecent(@Query("user") int user,
      @Query("media") int media);

  @DELETE("/api/favorite/{id}/") Call<Void> removeFavorite(@Path("id") int favId);

  @GET("/api/friends")
  Call<ModelFriendRequestWrapper> getFriends(@Query("id") int userId, @Query("page") int page);

  @POST("/api/friends/share/")
  Call<ModelShareResult> shareToFriends(@Body HashMap<String, Object> body);

  @GET("/media/like_list/") Call<JsonObject> getLikeAndDislikeStatus(@Query("media") int mediaId,
      @Query("user") int id);

  @POST("/media/like_list/") Call<JsonObject> createLike(@Body RequestBody body);

  @DELETE("/media/like_list/{id}/") Call<Void> deleteLike(@Path("id") int mediaId);

  @DELETE("/media/like_list/{id}/") Call<Void> deleteDislike(@Path("id") int mediaId);

  @POST("/api/subscribe_list/") Call<ResponseCreateSubscribe> createSubscribe(
      @Body RequestBody body);

  @DELETE("/api/subscribe_list/{id}/") Call<Void> deleteSubscribe(
      @Path("id") int subscribeId);

  @GET("/api/subscribe/")
  Call<ResponseSubscribeWrapper> getSubscribe(@Query("from_user") int fromUserId,
      @Query("to_user") int toUserId);

  @GET("/property/declaration/") Call<JsonObject> checkIsLiveVodReported(
      @Query("media") int mediaId, @Query("user") int userId);

  @POST("/property/declaration_list/") Call<JsonObject> reportVideo(@Body RequestBody body);
}
