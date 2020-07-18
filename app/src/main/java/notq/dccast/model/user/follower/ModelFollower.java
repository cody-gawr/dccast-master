package notq.dccast.model.user.follower;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.annotations.Ignore;
import notq.dccast.model.user.ModelUser;

public class ModelFollower {
    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("user_id")
    @Expose
    public int userId;

    @SerializedName("follower_id")
    @Expose
    public int followerId;

    @SerializedName("follow_date")
    @Expose
    public String followerDate;

    @Ignore
    public boolean following;

    @SerializedName("follower")
    @Expose
    public ModelUser follower;

    @SerializedName("user")
    @Expose
    public ModelUser user;

    public ModelUser getUser() {
        return user;
    }

    public void setUser(ModelUser user) {
        this.user = user;
    }

    public ModelUser getFollower() {
        return follower;
    }

    public void setFollower(ModelUser follower) {
        this.follower = follower;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public String getFollowerDate() {
        return followerDate;
    }

    public void setFollowerDate(String followerDate) {
        this.followerDate = followerDate;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }
}
