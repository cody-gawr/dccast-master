package notq.dccast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.realm.annotations.Ignore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.model.category.CategoryItem;
import notq.dccast.model.user.ModelUser;
import notq.dccast.util.LoginService;
import notq.dccast.util.Url;

public class ModelVideo implements Serializable {
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
  @SerializedName("is_vip") @Expose public boolean isVip;
  @SerializedName("is_vip_active") @Expose public boolean isVipActive;
  @SerializedName("hit_created") @Expose public transient Object hitCreated;
  @SerializedName("is_hit_active") @Expose public boolean isHitActive;
  @SerializedName("hit_completed") @Expose public transient Object hitCompleted;
  @SerializedName("is_complete") @Expose public boolean isComplete;
  @SerializedName("complete_created") @Expose public transient Object completeCreated;
  @SerializedName("is_popular") @Expose public boolean isPopular;
  @SerializedName("popular_created") @Expose public transient Object popularCreated;
  @Ignore public boolean needFetchUser;
  @SerializedName("user") @Expose public ModelUser user;
  @SerializedName("media_category") @Expose public CategoryItem mediaCategory;
  @SerializedName("views") @Expose public int views;
  @SerializedName("media_id") @Expose public String mediaId;
  @SerializedName("media_thumbnail") @Expose public String mediaThumbnail;
  @SerializedName("live_password") @Expose public String livePassword;
  @SerializedName("alive") @Expose public boolean alive;
  @SerializedName("forced_offs") @Expose public transient List<Object> forcedOffs = null;
  @SerializedName("live_last_update") @Expose public String liveLastUpdate;
  @SerializedName("live_vod_id") @Expose public String liveVodId;
  @SerializedName("live_start_datetime") @Expose public Object liveStartDatetime;
  @SerializedName("like") @Expose public int like;
  @SerializedName("dis_like") @Expose public int disLike;
  @SerializedName("live_resolution") @Expose public String liveResolution;
  @SerializedName("is_converted") @Expose public boolean isConverted;
  @SerializedName("dc_gallery") @Expose public String dcGallery;
  @SerializedName("dc_gallery_name") @Expose public String dcGalleryName;
  @SerializedName("orientation") @Expose public String orientation;
  @Ignore public boolean needFetchById;
  @Ignore public int userId;
  private boolean isLiveMedia = false;
  private boolean isLiked;
  private boolean isDisliked;

  public ModelVideo(ModelUploadVideoResponse response) {
    this.id = response.getId();
    this.category = response.getCategory();
    this.created = response.getCreated();
    this.title = response.getTitle();
    this.explanation = response.getExplanation();
    this.mediaType = response.getMediaType();
    this.duration = response.getDuration();
    this.kinds = response.getKinds();
    this.liveDeploy = response.getLiveDeploy();
    this.liveMember = response.getLiveMember();
    this.liveChatDisable = response.isLiveChatDisable();
    this.liveSetpass = response.isLiveSetpass();
    this.isHit = response.isHit();
    this.hitCreated = response.getHitCreated();
    this.isHitActive = response.isHitActive();
    this.hitCompleted = response.getHitCompleted();
    this.isComplete = response.isComplete();
    this.completeCreated = response.getCompleteCreated();
    this.isPopular = response.isPopular();
    this.popularCreated = response.getPopularCreated();
    this.mediaCategory = response.getMediaCategory();
    this.views = response.getViews();
    this.mediaId = response.getMediaId();
    this.mediaThumbnail = response.getMediaThumbnail();
    this.livePassword = response.getLivePassword();
    this.alive = response.isAlive();
    this.forcedOffs = new ArrayList<>();
    this.liveLastUpdate = response.getLiveLastUpdate();
    this.liveVodId = response.getLiveVodId();
    this.liveStartDatetime = response.getLiveStartDatetime();
    this.like = response.getLike();
    this.disLike = response.getDisLike();
    this.liveResolution = response.getLiveResolution();
    this.isConverted = response.isConverted();
    this.dcGallery = response.getDcGallery();
    this.dcGalleryName = response.getDcGalleryName();
    this.user = new ModelUser();
    this.user.setId(response.getUser());
    this.needFetchUser = true;
    this.isVip = response.isVip();
    this.isVipActive = response.isVipActive();
    if (response.getOrientation() != null) {
      this.orientation = getOrientation();
    }
  }

  public ModelVideo(ModelLiveHistoryVideoResponse response) {
    this.id = response.getId();
    this.category = response.getCategory();
    this.created = response.getCreated();
    this.title = response.getTitle();
    this.explanation = response.getExplanation();
    this.mediaType = response.getMediaType();
    this.duration = response.getDuration();
    this.kinds = response.getKinds();
    this.liveDeploy = response.getLiveDeploy();
    this.liveMember = response.getLiveMember();
    this.liveChatDisable = response.isLiveChatDisable();
    this.liveSetpass = response.isLiveSetpass();
    this.isHit = response.isHit();
    this.hitCreated = response.getHitCreated();
    this.isHitActive = response.isHitActive();
    this.hitCompleted = response.getHitCompleted();
    this.isComplete = response.isComplete();
    this.completeCreated = response.getCompleteCreated();
    this.isPopular = response.isPopular();
    this.popularCreated = response.getPopularCreated();
    this.mediaCategory = response.getMediaCategory();
    this.views = response.getViews();
    this.mediaId = response.getMediaId();
    this.mediaThumbnail = response.getMediaThumbnail();
    this.livePassword = response.getLivePassword();
    this.alive = response.isAlive();
    this.forcedOffs = new ArrayList<>();
    this.liveLastUpdate = response.getLiveLastUpdate();
    this.liveVodId = response.getLiveVodId();
    this.liveStartDatetime = response.getLiveStartDatetime();
    this.like = response.getLike();
    this.disLike = response.getDisLike();
    this.liveResolution = response.getLiveResolution();
    this.isConverted = response.isConverted();
    this.dcGallery = response.getDcGallery();
    this.dcGalleryName = response.getDcGalleryName();
    this.user = response.getUser();
    this.needFetchUser = true;
    this.isVip = response.isVip();
    this.isVipActive = response.isVipActive();
    if (response.getOrientation() != null) {
      this.orientation = response.getOrientation();
    }
  }

  public ModelVideo(ModelListHeader response) {
    this.id = response.getId();
    this.category = response.getCategory();
    this.created = response.getCreated();
    this.title = response.getTitle();
    this.explanation = response.getExplanation();
    this.mediaType = response.getMediaType();
    this.duration = response.getDuration();
    this.kinds = response.getKinds();
    this.liveDeploy = response.getLiveDeploy();
    this.liveMember = response.getLiveMember();
    this.hitCreated = response.getHitCreated();
    this.hitCompleted = response.getHitCompleted();
    this.completeCreated = response.getCompleteCreated();
    this.popularCreated = response.getPopularCreated();
    this.mediaCategory = response.getMediaCategory();
    this.views = response.getViews();
    this.mediaId = response.getMediaId();
    this.mediaThumbnail = response.getMediaThumbnail();
    this.livePassword = response.getLivePassword();
    this.forcedOffs = new ArrayList<>();
    this.liveLastUpdate = response.getLiveLastUpdate();
    this.liveVodId = response.getLiveVodId();
    this.liveStartDatetime = response.getLiveStartDatetime();
    this.like = response.getLike();
    this.disLike = response.getDisLike();
    this.liveResolution = response.getLiveResolution();
    this.user = response.getUser();
    this.isVip = response.isVip();
    this.isVipActive = response.isVipActive();
    if (response.getOrientation() != null) {
      this.orientation = response.getOrientation();
    }
  }

  public String getOrientation() {
    if (orientation == null || !orientation.equalsIgnoreCase("portrait")) {
      return "landscape";
    }
    return orientation;
  }

  public void setOrientation(String orientation) {
    this.orientation = orientation;
  }

  public boolean isPortraitVideo() {
    return orientation != null && orientation.equalsIgnoreCase("portrait");
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

  public boolean isNeedFetchUser() {
    return needFetchUser;
  }

  public void setNeedFetchUser(boolean needFetchUser) {
    this.needFetchUser = needFetchUser;
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

  public ModelUser getUser() {
    ModelUser loginUser = LoginService.getLoginUser();
    if (user.getUser() == null
        && loginUser != null
        && loginUser.getId() == user.getId()) {
      return loginUser;
    }
    return user;
  }

  public void setUser(ModelUser user) {
    this.user = user;
  }

  public int getUserId() {
    if (user == null) {
      return userId;
    }

    return user.getId();
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public CategoryItem getMediaCategory() {
    return mediaCategory;
  }

  public int getViews() {
    return views;
  }

  public String getMediaId() {
    return mediaId;
  }

  public String getMediaThumbnail() {
    return mediaThumbnail;
  }

  public String getLivePassword() {
    if (livePassword == null || livePassword.equalsIgnoreCase("null")) {
      return "";
    }
    return livePassword;
  }

  public boolean isAlive() {
    return alive;
  }

  public List<Object> getForcedOffs() {
    return forcedOffs;
  }

  public Object getLiveLastUpdate() {
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

  public void setLike(int like) {
    this.like = like;
  }

  public int getDisLike() {
    return disLike;
  }

  public void setDisLike(int disLike) {
    this.disLike = disLike;
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

  public String getVideoUrl(String quality) {
    if (quality == null || quality.isEmpty()) {
      String mediaIdFormatted = mediaId + "";
      mediaIdFormatted = mediaIdFormatted.replaceAll(".mp4", "");
      if (category.equals("LIVE")) {
        return "http://"
            + Url.wowzaUrl
            + ":1935/live/ngrp:"
            + mediaIdFormatted + "_all"
            + "/playlist.m3u8";
      } else {
        return "http://"
            + Url.wowzaUrl
            + ":1935/vod/_definst_/smil:"
            + mediaIdFormatted + ".smil"
            + "/playlist.m3u8";
      }
    } else {
      if (category.equals("LIVE")) {
        return "http://"
            + Url.wowzaUrl
            + ":1935/live/"
            + getFormattedMedia(quality)
            + "/playlist.m3u8";
      } else {
        return "http://"
            + Url.wowzaUrl
            + ":1935/vod/_definst_/"
            + getFormattedMedia(quality)
            + "/playlist.m3u8";
      }
    }
  }

  public String getFormattedMedia(String quality) {
    if (quality == null || quality.isEmpty()) {
      return mediaId;
    }

    String formatted = mediaId;

    return formatted.replaceAll(".mp4", "_" + quality + ".mp4");
  }
}
