package notq.dccast.api.my_content;

import notq.dccast.model.ModelRecentVideoWrapper;
import notq.dccast.model.ModelVideoWrapper;
import notq.dccast.model.user.profile.ModelProfileStat;
import notq.dccast.model.user.profile.ModelUserProfileWrapper;
import notq.dccast.model.user.subscribe.ModelSubscribeUserWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyContentAPIInterface {
  @GET("/api/recent")
  Call<ModelRecentVideoWrapper> getRecentWithType(@Query("user") int userId,
      @Query("media__category") String type, @Query("page") int page);

  @GET("/api/recent")
  Call<ModelRecentVideoWrapper> getRecent(@Query("user") int userId, @Query("page") int page);

  @GET("/api/favorite")
  Call<ModelRecentVideoWrapper> getFavorite(@Query("user") int userId, @Query("limit") int limit,
      @Query("page") int page);

  @GET("/media/media")
  Call<ModelVideoWrapper> getMyChannelVideo(@Query("user") int userId, @Query("category") String category, @Query("limit") int limit, @Query("page") int page);

  @GET("/media/media")
  Call<ModelVideoWrapper> searchRequest(@Query("user") int userId, @Query("search") String keyword,
      @Query("ordering") String ordering, @Query("category") String category, @Query("limit") int limit, @Query("page") int page);

  @GET("/media/media")
  Call<ModelVideoWrapper> getMyChannelLastLive(@Query("user") int userId,
      @Query("ordering") String ordering, @Query("category") String category,
      @Query("limit") int limit);

  @GET("/api/profiles/stat")
  Call<ModelProfileStat> getProfileStat(@Query("user_id") int userId);

  @GET("/api/profiles")
  Call<ModelUserProfileWrapper> getProfile(@Query("id") int userId);

  @GET("/api/subscribe/")
  Call<ModelSubscribeUserWrapper> getSubscribeList(@Query("from_user") int userId, @Query("page") int page);

  @GET("/api/subscribe/")
  Call<ModelSubscribeUserWrapper> getSubscribeList(@Query("from_user") int userId,
      @Query("search") String keyword, @Query("ordering") String ordering, @Query("page") int page);
}