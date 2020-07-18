package notq.dccast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.realm.annotations.Ignore;
import java.io.Serializable;
import java.util.List;
import notq.dccast.model.category.CategoryItem;
import notq.dccast.util.Url;

public class ModelUploadVideoResponse implements Serializable {
  @SerializedName("id") @Expose public int id;
  @SerializedName("category") @Expose public String category;
  @SerializedName("created") @Expose public String created;
  @SerializedName("title") @Expose public String title;
  @SerializedName("explanation") @Expose public String explanation;
  @SerializedName("media_type") @Expose public String mediaType;
  @SerializedName("duration") @Expose public int duration;
  @SerializedName("kinds") @Expose public String kinds;
  @SerializedName("live_deploy") @Expose public String liveDeploy;
  @SerializedName("live_member") @Expose public String liveMember;
  @SerializedName("live_chat_disable") @Expose public boolean liveChatDisable;
  @SerializedName("live_setpass") @Expose public boolean liveSetpass;
  @SerializedName("is_hit") @Expose public boolean isHit;
  @SerializedName("hit_created") @Expose public Object hitCreated;
  @SerializedName("is_hit_active") @Expose public boolean isHitActive;
  @SerializedName("hit_completed") @Expose public Object hitCompleted;
  @SerializedName("is_complete") @Expose public boolean isComplete;
  @SerializedName("complete_created") @Expose public Object completeCreated;
  @SerializedName("is_popular") @Expose public boolean isPopular;
  @SerializedName("popular_created") @Expose public Object popularCreated;
  @SerializedName("user") @Expose public int user;
  @SerializedName("media_category") @Expose public int mediaCategory;
  @SerializedName("views") @Expose public int views;
  @SerializedName("media_id") @Expose public String mediaId;
  @SerializedName("media_thumbnail") @Expose public String mediaThumbnail;
  @SerializedName("live_password") @Expose public String livePassword;
  @SerializedName("alive") @Expose public boolean alive;
  @SerializedName("forced_offs") @Expose public List<Object> forcedOffs = null;
  @SerializedName("live_last_update") @Expose public String liveLastUpdate;
  @SerializedName("live_vod_id") @Expose public String liveVodId;
  @SerializedName("live_start_datetime") @Expose public Object liveStartDatetime;
  @SerializedName("like") @Expose public int like;
  @SerializedName("dis_like") @Expose public int disLike;
  @SerializedName("live_resolution") @Expose public String liveResolution;
  @SerializedName("is_converted") @Expose public boolean isConverted;
  @SerializedName("dc_gallery") @Expose public String dcGallery;
  @SerializedName("dc_gallery_name") @Expose public String dcGalleryName;
  @SerializedName("is_vip") @Expose public boolean isVip;
  @SerializedName("is_vip_active") @Expose public boolean isVipActive;
  @SerializedName("orientation") @Expose public String orientation;
  @Ignore public boolean needFetchById;
  private boolean isLiveMedia = false;
  private boolean isLiked;
  private boolean isDisliked;

  public String getOrientation() {
    if (orientation == null) {
      return "landscape";
    }
    return orientation;
  }

  public void setOrientation(String orientation) {
    this.orientation = orientation;
  }

  public boolean isVip() {
    return isVip;
  }

  public void setVip(boolean vip) {
    isVip = vip;
  }

  public boolean isVipActive() {
    return isVipActive;
  }

  public void setVipActive(boolean vipActive) {
    isVipActive = vipActive;
  }

  public boolean isLiveMedia() {
    return isLiveMedia;
  }

  public void setLiveMedia(boolean liveMedia) {
    isLiveMedia = liveMedia;
  }

  public boolean isLiked() {
    return isLiked;
  }

  public void setLiked(boolean liked) {
    isLiked = liked;
  }

  public boolean isDisliked() {
    return isDisliked;
  }

  public void setDisliked(boolean disliked) {
    isDisliked = disliked;
  }

  public boolean isNeedFetchById() {
    return needFetchById;
  }

  public void setNeedFetchById(boolean needFetchById) {
    this.needFetchById = needFetchById;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCategory() {
    return category;
  }

  public String getCreated() {
    return created;
  }

  public String getTitle() {
    return title;
  }

  public String getExplanation() {
    return explanation;
  }

  public String getMediaType() {
    return mediaType;
  }

  public int getDuration() {
    return duration;
  }

  public String getKinds() {
    return kinds;
  }

  public String getLiveDeploy() {
    return liveDeploy;
  }

  public String getLiveMember() {
    return liveMember;
  }

  public boolean isLiveChatDisable() {
    return liveChatDisable;
  }

  public boolean isLiveSetpass() {
    return liveSetpass;
  }

  public boolean isHit() {
    return isHit;
  }

  public Object getHitCreated() {
    return hitCreated;
  }

  public boolean isHitActive() {
    return isHitActive;
  }

  public Object getHitCompleted() {
    return hitCompleted;
  }

  public boolean isComplete() {
    return isComplete;
  }

  public Object getCompleteCreated() {
    return completeCreated;
  }

  public boolean isPopular() {
    return isPopular;
  }

  public Object getPopularCreated() {
    return popularCreated;
  }

  public int getUser() {
    return user;
  }

  public CategoryItem getMediaCategory() {
    CategoryItem categoryItem = new CategoryItem();
    categoryItem.setId(mediaCategory);
    return categoryItem;
  }

  public int getViews() {
    return views;
  }

  public String getMediaId() {
    return mediaId;
  }

  public String getMediaThumbnail() {
    String returnMediaThumbnail = mediaThumbnail;
    if (returnMediaThumbnail != null) {
      returnMediaThumbnail = returnMediaThumbnail.replaceAll("%20", "");
      returnMediaThumbnail = returnMediaThumbnail.replaceAll(" ", "");
      returnMediaThumbnail = returnMediaThumbnail.trim();
    }
    return returnMediaThumbnail;
  }

  public String getLivePassword() {
    return livePassword;
  }

  public boolean isAlive() {
    return alive;
  }

  public List<Object> getForcedOffs() {
    return forcedOffs;
  }

  public String getLiveLastUpdate() {
    return liveLastUpdate;
  }

  public String getLiveVodId() {
    return liveVodId;
  }

  public Object getLiveStartDatetime() {
    return liveStartDatetime;
  }

  public int getLike() {
    return like;
  }

  public int getDisLike() {
    return disLike;
  }

  public String getLiveResolution() {
    return liveResolution;
  }

  public boolean isConverted() {
    return isConverted;
  }

  public String getDcGallery() {
    return dcGallery;
  }

  public String getDcGalleryName() {
    return dcGalleryName;
  }

  public String getVideoUrl() {
    if (category.equals("LIVE")) {
      return "http://" + Url.wowzaUrl + ":1935/live/" + mediaId + "/playlist.m3u8";
    } else {
      return "http://" + Url.wowzaUrl + ":1935/vod/_definst_/" + mediaId + "/playlist.m3u8";
    }
  }
}
