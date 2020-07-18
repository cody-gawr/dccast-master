package notq.dccast.api.home;

import com.google.gson.JsonObject;
import notq.dccast.model.comment.ModelCommentActionResponse;
import notq.dccast.model.comment.ModelCommentActionWrapper;
import notq.dccast.model.comment.ModelCreateCommentResponse;
import notq.dccast.model.comment.ModelStickerWrapper;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommentAPIInterface {
  @Headers("Content-Type: application/json") @GET("/media/comment/")
  Call<ModelCommentActionWrapper> getCommentList(@Query("media") int mediaId,
      @Query("user_id") int userId);

  @Headers("Content-Type: application/json") @POST("/media/commentlike_list/")
  Call<ModelCommentActionResponse> createCommentLike(@Body RequestBody body);

  @Headers("Content-Type: application/json") @POST("/media/comment_list/")
  Call<ModelCreateCommentResponse> createComment(@Body RequestBody body);

  @Headers("Content-Type: application/json") @PUT("/media/comment_list/{id}/")
  Call<ModelCreateCommentResponse> updateComment(@Path("id") int commentId, @Body RequestBody body);

  @DELETE("/media/comment/{id}/") Call<Void> deleteComment(@Path("id") int commentId);

  @FormUrlEncoded
  @POST("/media/comment/update")
  Call<JsonObject> checkRequestSend(@Field("kind") String kind, @Field("user") int userId,
      @Field("comment") int commentId);

  @GET("/property/declaration/") Call<JsonObject> checkIsCommentReported(
      @Query("comment") int commentId);

  @POST("/property/declaration_list/") Call<JsonObject> createCommentReport(@Body RequestBody body);

  @Headers({
      "Referer: http://dccon.dcinside.com/",
      "User-Agent: dcinside.castapp"
  })
  @FormUrlEncoded
  @POST("/api/dccon.php")
  Call<ModelStickerWrapper> getStickers(@Field("type") String type, @Field("app_id") String appId,
      @Field("user_id") String userId);
}
