package notq.dccast.api.home;

import notq.dccast.model.ModelAdsWrapper;
import notq.dccast.model.ModelBlockDivisionResult;
import notq.dccast.model.ModelListHeaderWrapper;
import notq.dccast.model.ModelVideoWrapper;
import notq.dccast.model.category.ModelCategory;
import notq.dccast.model.user.follower.ModelFollowResult;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HomeAPIInterface {
  @GET("/property/mediacategory/") Call<ModelCategory> getCategoryList();

  @GET("/media/media") Call<ModelListHeaderWrapper> getHeaderListWithUserId(
      @Query("ordering") String ordering, @Query("is_hit_active") boolean active,
      @Query("category") String category, @Query("limit") int limit, @Query("user_id") int userId);

  @GET("/media/media") Call<ModelListHeaderWrapper> getHeaderList(
      @Query("ordering") String ordering, @Query("is_hit_active") boolean active,
      @Query("category") String category, @Query("limit") int limit);

  @GET("/media/media/hit_vod_list") Call<ModelListHeaderWrapper> getHeaderVodList();

  @GET("/media/media/hit_live_list") Call<ModelListHeaderWrapper> getHeaderLiveList();

  @GET("/media/media/?category=LIVE&limit=10&ordering=-views")
  Call<ModelVideoWrapper> getPopularVideo(@Query("category") String category,
      @Query("limit") int limit, @Query("ordering") String ordering);

  @GET("/media/media") Call<ModelVideoWrapper> getVideoList(@Query("category") String category,
      @Query("media_category") int category_id, @Query("ordering") String ordering,
      @Query("limit") int limit, @Query("page") int page, @Query("is_hit_active") int active,
      @Query("is_popular") int popular);

  @GET("/media/media") Call<ModelVideoWrapper> getAllVideoList(@Query("category") String category,
      @Query("ordering") String ordering,
      @Query("limit") int limit, @Query("page") int page, @Query("is_hit_active") int active,
      @Query("is_popular") int popular);

  @GET("/media/media") Call<ModelVideoWrapper> getVideoListWithUserId(@Query("user_id") int userId,
      @Query("category") String category,
      @Query("media_category") int category_id, @Query("ordering") String ordering,
      @Query("limit") int limit, @Query("page") int page, @Query("is_hit_active") int active,
      @Query("is_popular") int popular);

  @GET("/media/media") Call<ModelVideoWrapper> getAllVideoListWithUserId(
      @Query("user_id") int userId, @Query("category") String category,
      @Query("ordering") String ordering,
      @Query("limit") int limit, @Query("page") int page, @Query("is_hit_active") int active,
      @Query("is_popular") int popular);

  @Headers("Content-Type: application/x-www-form-urlencoded") @POST("/media/search")
  Call<ModelVideoWrapper> search(@Body RequestBody body);

  @GET("/media/media") Call<ModelVideoWrapper> getMediaById(@Query("id") int videoId);

  @GET("/adver/adver/?state=배포") Call<ModelAdsWrapper> getAds();

  @FormUrlEncoded
  @POST("/api/update")
  Call<ModelBlockDivisionResult> checkBlockedFromAdmin(@Field("kind") String kind,
      @Field("profile_id") int user_id, @Field("block_kind") String block_kind);
}