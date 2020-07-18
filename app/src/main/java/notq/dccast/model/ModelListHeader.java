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

public class ModelListHeader implements Serializable {
  @SerializedName("id") @Expose public Integer id;
  @SerializedName("category") @Expose public String category;
  @SerializedName("created") @Expose public String created;
  @SerializedName("title") @Expose public String title;
  @SerializedName("explanation") @Expose public String explanation;
  @SerializedName("media_type") @Expose public String mediaType;
  @SerializedName("duration") @Expose public Integer duration;
  @SerializedName("kinds") @Expose public String kinds;
  @SerializedName("live_deploy") @Expose public String liveDeploy;
  @SerializedName("live_member") @Expose public String liveMember;
  @SerializedName("live_chat_disable") @Expose public Boolean liveChatDisable;
  @SerializedName("live_setpass") @Expose public Boolean liveSetpass;
  @SerializedName("is_hit") @Expose public Boolean isHit;
  @SerializedName("hit_created") @Expose public String hitCreated;
  @SerializedName("is_hit_active") @Expose public Boolean isHitActive;
  @SerializedName("hit_completed") @Expose public Object hitCompleted;
  @SerializedName("is_complete") @Expose public Boolean isComplete;
  @SerializedName("complete_created") @Expose public Object completeCreated;
  @SerializedName("is_popular") @Expose public Boolean isPopular;
  @SerializedName("popular_created") @Expose public Object popularCreated;
  @SerializedName("user") @Expose public ModelUser user;
  @SerializedName("media_category") @Expose public CategoryItem mediaCategory;
  @SerializedName("views") @Expose public Integer views;
  @SerializedName("media_id") @Expose public String mediaId;
  @SerializedName("media_thumbnail") @Expose public String mediaThumbnail;
  @SerializedName("live_password") @Expose public String livePassword;
  @SerializedName("alive") @Expose public Boolean alive;
  @SerializedName("forced_offs") @Expose public List<Object> forcedOffs = null;
  @SerializedName("live_last_update") @Expose public String liveLastUpdate;
  @SerializedName("is_vip") @Expose public boolean isVip;
  @SerializedName("is_vip_active") @Expose public boolean isVipActive;
  @SerializedName("live_vod_id") @Expose public String liveVodId;
  @SerializedName("live_start_datetime") @Expose public String liveStartDatetime;
  @SerializedName("like") @Expose public Integer like;
  @SerializedName("dis_like") @Expose public Integer disLike;
  @SerializedName("live_resolution") @Expose public String liveResolution;
  @SerializedName("is_converted") @Expose public Boolean isConverted;
  @SerializedName("dc_gallery") @Expose public Object dcGallery;
  @SerializedName("dc_gallery_name") @Expose public Object dcGalleryName;
  @SerializedName("orientation") @Expose public String orientation;
  @Ignore public boolean needFetchUser;
  @Ignore public int userId;
  private boolean isLiked;
  private boolean isDisliked;

  public ModelListHeader(ModelVideo response) {
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
    if (response.getHitCreated() != null) {
      this.hitCreated = response.getHitCreated().toString();
    }
    this.hitCompleted = response.getHitCompleted();
    this.completeCreated = response.getCompleteCreated();
    this.popularCreated = response.getPopularCreated();
    this.mediaCategory = response.getMediaCategory();
    this.views = response.getViews();
    this.mediaId = response.getMediaId();
    this.mediaThumbnail = response.getMediaThumbnail();
    this.livePassword = response.getLivePassword();
    this.forcedOffs = new ArrayList<>();
    if (response.getLiveLastUpdate() != null) {
      this.liveLastUpdate = response.getLiveLastUpdate().toString();
    }
    if (response.getLiveVodId() != null) {
      this.liveVodId = response.getLiveVodId().toString();
    }
    if (response.getLiveStartDatetime() != null) {
      this.liveStartDatetime = response.getLiveStartDatetime().toString();
    }
    this.like = response.getLike();
    this.disLike = response.getDisLike();
    this.liveResolution = response.getLiveResolution();
    this.user = response.getUser();
    this.isVip = response.isVip();
    this.isVipActive = response.isVipActive();
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

  public int getId() {
    return id;
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

  public Integer getDuration() {
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

  public Boolean getLiveChatDisable() {
    return liveChatDisable;
  }

  public Boolean getLiveSetpass() {
    return liveSetpass;
  }

  public Boolean getHit() {
    return isHit;
  }

  public String getHitCreated() {
    return hitCreated;
  }

  public Boolean getHitActive() {
    return isHitActive;
  }

  public Object getHitCompleted() {
    return hitCompleted;
  }

  public Boolean getComplete() {
    return isComplete;
  }

  public Object getCompleteCreated() {
    return completeCreated;
  }

  public Boolean getPopular() {
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

  public Integer getViews() {
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

  public Boolean getAlive() {
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

  public String getLiveStartDatetime() {
    return liveStartDatetime;
  }

  public Integer getLike() {
    return like;
  }

  public void setLike(Integer like) {
    this.like = like;
  }

  public Integer getDisLike() {
    return disLike;
  }

  public void setDisLike(Integer disLike) {
    this.disLike = disLike;
  }

  public String getLiveResolution() {
    return liveResolution;
  }

  public Boolean getConverted() {
    return isConverted;
  }

  public Object getDcGallery() {
    return dcGallery;
  }

  public Object getDcGalleryName() {
    return dcGalleryName;
  }

  public String getVideoUrl(String quality) {
    if (quality == null || quality.isEmpty()) {
      if (category.equals("LIVE")) {
        return "http://"
            + Url.wowzaUrl
            + ":1935/live/ngrp:"
            + mediaId + "_all"
            + "/playlist.m3u8";
      } else {
        return "http://"
            + Url.wowzaUrl
            + ":1935/vod/_definst_/smil:"
            + mediaId + ".smil"
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
