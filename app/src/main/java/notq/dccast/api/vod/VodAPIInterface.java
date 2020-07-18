package notq.dccast.api.vod;

import com.google.gson.JsonObject;
import notq.dccast.model.ModelGroupVideo;
import notq.dccast.model.vod.VodResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface VodAPIInterface {
  @Multipart @POST("/upload")
  Call<VodResponse> uploadVOD(@Part MultipartBody.Part file);

  @POST("api/group/{groupId}/vod_upload/") Call<ModelGroupVideo> uploadToGroup(
      @Path("groupId") int groupId, @Body RequestBody body);

  @POST("api/group/{groupId}/live/") Call<ModelGroupVideo> uploadToGroupLive(
      @Path("groupId") int groupId, @Body RequestBody body);

  @Multipart @POST("upload_thumbnail")
  Call<JsonObject> uploadThumbnail(@Part MultipartBody.Part file);

  @POST("/media/media_list/") Call<JsonObject> createMedia(@Body RequestBody body);

  @PUT("/media/media_list/{id}/") Call<JsonObject> updateMedia(@Path("id") int id,
      @Body RequestBody body);

  @DELETE("media/media_list/{id}/") Call<Void> deleteMedia(@Path("id") int mediaId);

  @Headers({
      "Content-Type: application/x-www-form-urlencoded",
      "User-Agent: dcinside.castapp"
  })
  @FormUrlEncoded
  @POST("/api/_total_search.php") Call<JsonObject> searchGallery(@Field("app_id") String appId,
      @Field("keyword") String keyword, @Field("search_type") String searchType);
}