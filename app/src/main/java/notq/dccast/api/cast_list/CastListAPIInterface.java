package notq.dccast.api.cast_list;

import java.util.ArrayList;
import notq.dccast.model.ModelGroupVideoWrapper;
import notq.dccast.model.ModelMyLiveHistoryWrapper;
import notq.dccast.model.ModelVideoWrapper;
import notq.dccast.model.friends.ModelFriendRequestResult;
import notq.dccast.model.friends.ModelFriendRequestWrapper;
import notq.dccast.model.friends.ModelFriendResult;
import notq.dccast.model.group.ModelGroup;
import notq.dccast.model.group.ModelGroupWrapper;
import notq.dccast.model.user.ModelChildUserWrapper;
import notq.dccast.model.user.ModelResult;
import notq.dccast.model.user.ModelUserWrapper;
import notq.dccast.model.user.follower.ModelFollowResult;
import notq.dccast.model.user.follower.ModelFollowerWrapper;
import notq.dccast.model.user.follower.ModelRecentWrapper;
import notq.dccast.model.user.profile.ModelProfileWrapper;
import notq.dccast.model.user.profile.ModelUserProfileWrapper;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CastListAPIInterface {
  @GET("/api/follow/following")
  Call<ModelFollowerWrapper> getFollowing(@Query("user_id") int userId, @Query("page") int page);

  @GET("/api/follow/unfollowing")
  Call<ModelFollowerWrapper> getUnfollowing(@Query("user_id") int userId, @Query("page") int page);

  @GET("/api/follow/followers")
  Call<ModelFollowerWrapper> getFollowers(@Query("user_id") int userId, @Query("page") int page);

  @GET("/api/follow/followers_viewed/")
  Call<ModelFollowerWrapper> getMyLiveHistory(@Query("user_id") int userId,
      @Query("page") int page);

  @GET("/api/follow/followers_viewed_medias/")
  Call<ModelMyLiveHistoryWrapper> getMyLiveHistoryMedia(@Query("user_id") int userId,
      @Query("follower_id") int followerId, @Query("page") int page);

  @GET("/api/groups_list")
  Call<ModelGroupWrapper> getOwnGroups(@Query("contact_person") int userId,
      @Query("page") int page);

  @GET("/api/group")
  Call<ModelGroupWrapper> getGroups(@Query("user_id") int userId, @Query("page") int page);

  @GET("/api/profiles")
  Call<ModelUserProfileWrapper> getProfile(@Query("id") int userId);

  @GET("/api/profiles")
  Call<ModelUserWrapper> search(@Query("search") String keyword, @Query("page") int page);

  @FormUrlEncoded
  @POST("/api/follow/followers_search/")
  Call<ModelUserWrapper> searchFromFollowers(@Field("user_id") int userId,
      @Field("keyword") String keyword, @Query("page") int page);

  @FormUrlEncoded
  @POST("/api/friends/search/")
  Call<ModelUserWrapper> searchFromFriends(@Field("user_id") int userId,
      @Field("keyword") String keyword, @Query("page") int page);

  @FormUrlEncoded
  @POST("/api/group/search/")
  Call<ModelGroupWrapper> searchFromGroups(@Field("user") int userId,
      @Field("keyword") String keyword, @Query("page") int page);

  @GET("/api/users")
  Call<ModelChildUserWrapper> getAllMembers(@Query("page") int page);

  @FormUrlEncoded
  @POST("/api/follow/")
  Call<ModelFollowResult> follow(@Field("user_id") int userId,
      @Field("follower_id") int followerId);

  @DELETE("/api/follow/delete/")
  Call<ModelResult> unFollow(@Query("user_id") int userId, @Query("follower_id") int followerId);

  @FormUrlEncoded
  @POST("/api/update")
  Call<ModelResult> checkFollow(@Field("kind") String kind, @Field("follower") int follower,
      @Field("user") int user);

  @Multipart
  @POST("/api/group/")
  Call<ModelGroup> createGroup(@Part("name") RequestBody name, @Part("message") RequestBody message,
      @Part("contact_person") RequestBody contactPerson, @Part("members") ArrayList<Integer> ids,
      @Part MultipartBody.Part file);

  @Multipart
  @PUT("/api/group/{group_id}/")
  Call<ModelGroup> updateGroup(@Path("group_id") int group_id, @Part("name") RequestBody name,
      @Part("message") RequestBody message, @Part("contact_person") RequestBody contactPerson,
      @Part("members") ArrayList<Integer> ids, @Part MultipartBody.Part file);

  @Multipart
  @PUT("/api/group/{group_id}/")
  Call<ModelGroup> updateGroupMembers(@Path("group_id") int group_id,
      @Part("members") ArrayList<Integer> ids);

  @GET("/api/group/{group_id}/{type}/")
  Call<ModelGroupVideoWrapper> getGroupVideos(@Path("group_id") int group_id,
      @Path("type") String type, @Query("user_id") int userId, @Query("page") int page);

  @GET("/media/media")
  Call<ModelVideoWrapper> getVideoById(@Query("id") int id);

  @GET("/api/group/{group_id}/members")
  Call<ModelProfileWrapper> getGroupMembers(@Path("group_id") int group_id,
      @Query("page") int page);

  @DELETE("/api/group/{group_id}/members/")
  Call<ModelResult> leaveGroup(@Path("group_id") int group_id, @Query("member_id") int memberId);

  @GET("/api/friends")
  Call<ModelFriendRequestWrapper> getReceivedFriendRequest(@Query("to_user") int toUser,
      @Query("accepted") boolean accepted);

  @FormUrlEncoded
  @POST("/api/friends_request/")
  Call<ModelFriendResult> sendFriendRequest(@Field("from_user") int fromUser,
      @Field("to_user") int toUser);

  @FormUrlEncoded
  @POST("/api/friends_request/accept/")
  Call<ModelFriendRequestResult> acceptFriendRequest(@Field("from_user") int fromUser,
      @Field("to_user") int toUser);

  @DELETE("/api/friends_request/delete/")
  Call<ModelFriendRequestResult> deleteFriendRequest(@Query("from_user") int fromUser,
      @Query("to_user") int toUser);

  @DELETE("/api/friends_request/delete/")
  Call<ModelFriendRequestResult> deleteFriend(@Query("from_user") int fromUser,
      @Query("to_user") int toUser);

  @DELETE("/api/follow/delete_follower/")
  Call<ModelFollowResult> deleteFollower(@Query("user_id") int userId,
      @Query("follower_id") int followerId);

  @DELETE("/api/follow/{follow_id}/")
  Call<ModelFollowResult> deleteUnfollow(@Path("follow_id") int followingId);

  @GET("/api/follow/friends_list/recent")
  Call<ModelRecentWrapper> getRecentFriends(@Query("user_id") int userId, @Query("page") int page);

  @GET("/api/friends")
  Call<ModelFriendRequestWrapper> getFriends(@Query("id") int userId, @Query("page") int page);

  @GET("/api/friends/is_friends")
  Call<ModelResult> checkIsFriend(@Query("user1") int user1, @Query("user2") int user2);
}