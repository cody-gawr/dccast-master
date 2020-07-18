package notq.dccast.api.notification;

import notq.dccast.model.ModelNotificationNew;
import notq.dccast.model.notification.ModelNotificationWrapper;
import notq.dccast.model.user.ModelResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NotificationAPIInterface {
  @GET("/api/notifications/{type}/")
  Call<ModelNotificationWrapper> getNotificationList(@Path("type") String path,
      @Query("user_id") int userId, @Query("page") int page);

  @GET("/api/notifications/has_new")
  Call<ModelNotificationNew> getBadgeNew(@Query("user_id") int userId);

  @GET("/api/notifications/read")
  Call<ModelResult> setBadgeRead(@Query("user_id") int userId, @Query("category") String category);
}