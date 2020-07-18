package notq.dccast.model.live;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LiveResponse {
  @SerializedName("id") @Expose private Integer id;
  @SerializedName("category") @Expose private String category;
  @SerializedName("created") @Expose private String created;
  @SerializedName("title") @Expose private String title;
  @SerializedName("explanation") @Expose private Object explanation;
  @SerializedName("media_type") @Expose private Object mediaType;
  @SerializedName("duration") @Expose private Integer duration;
  @SerializedName("kinds") @Expose private Object kinds;
  @SerializedName("live_deploy") @Expose private String liveDeploy;
  @SerializedName("live_member") @Expose private String liveMember;
  @SerializedName("live_chat_disable") @Expose private Boolean liveChatDisable;
  @SerializedName("live_setpass") @Expose private Boolean liveSetpass;
  @SerializedName("is_hit") @Expose private Boolean isHit;
  @SerializedName("hit_created") @Expose private Object hitCreated;
  @SerializedName("is_hit_active") @Expose private Boolean isHitActive;
  @SerializedName("hit_completed") @Expose private Object hitCompleted;
  @SerializedName("is_complete") @Expose private Boolean isComplete;
  @SerializedName("complete_created") @Expose private Object completeCreated;
  @SerializedName("is_popular") @Expose private Boolean isPopular;
  @SerializedName("popular_created") @Expose private Object popularCreated;
  @SerializedName("user") @Expose private Integer user;
  @SerializedName("media_category") @Expose private Integer mediaCategory;
  @SerializedName("views") @Expose private Integer views;
  @SerializedName("media_id") @Expose private String mediaId;
  @SerializedName("media_thumbnail") @Expose private String mediaThumbnail;
  @SerializedName("live_password") @Expose private String livePassword;

  @SerializedName("orientation") @Expose private String orientation;

  public String getOrientation() {
    if (orientation == null) {
      return "";
    }
    return orientation;
  }
  @SerializedName("alive") @Expose private Boolean alive;
  @SerializedName("forced_offs") @Expose private List<Object> forcedOffs = null;
  @SerializedName("live_last_update") @Expose private Object liveLastUpdate;
  @SerializedName("live_vod_id") @Expose private String liveVodId;
  @SerializedName("live_start_datetime") @Expose private Object liveStartDatetime;
  @SerializedName("like") @Expose private Integer like;
  @SerializedName("dis_like") @Expose private Integer disLike;
  @SerializedName("live_resolution") @Expose private Object liveResolution;
  @SerializedName("is_converted") @Expose private Boolean isConverted;
  @SerializedName("dc_gallery") @Expose private Object dcGallery;
  @SerializedName("dc_gallery_name") @Expose private Object dcGalleryName;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Object getExplanation() {
    return explanation;
  }

  public void setExplanation(Object explanation) {
    this.explanation = explanation;
  }

  public Object getMediaType() {
    return mediaType;
  }

  public void setMediaType(Object mediaType) {
    this.mediaType = mediaType;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public Object getKinds() {
    return kinds;
  }

  public void setKinds(Object kinds) {
    this.kinds = kinds;
  }

  public String getLiveDeploy() {
    return liveDeploy;
  }

  public void setLiveDeploy(String liveDeploy) {
    this.liveDeploy = liveDeploy;
  }

  public String getLiveMember() {
    return liveMember;
  }

  public void setLiveMember(String liveMember) {
    this.liveMember = liveMember;
  }

  public Boolean getLiveChatDisable() {
    return liveChatDisable;
  }

  public void setLiveChatDisable(Boolean liveChatDisable) {
    this.liveChatDisable = liveChatDisable;
  }

  public Boolean getLiveSetpass() {
    return liveSetpass;
  }

  public void setLiveSetpass(Boolean liveSetpass) {
    this.liveSetpass = liveSetpass;
  }

  public Boolean getIsHit() {
    return isHit;
  }

  public void setIsHit(Boolean isHit) {
    this.isHit = isHit;
  }

  public Object getHitCreated() {
    return hitCreated;
  }

  public void setHitCreated(Object hitCreated) {
    this.hitCreated = hitCreated;
  }

  public Boolean getIsHitActive() {
    return isHitActive;
  }

  public void setIsHitActive(Boolean isHitActive) {
    this.isHitActive = isHitActive;
  }

  public Object getHitCompleted() {
    return hitCompleted;
  }

  public void setHitCompleted(Object hitCompleted) {
    this.hitCompleted = hitCompleted;
  }

  public Boolean getIsComplete() {
    return isComplete;
  }

  public void setIsComplete(Boolean isComplete) {
    this.isComplete = isComplete;
  }

  public Object getCompleteCreated() {
    return completeCreated;
  }

  public void setCompleteCreated(Object completeCreated) {
    this.completeCreated = completeCreated;
  }

  public Boolean getIsPopular() {
    return isPopular;
  }

  public void setIsPopular(Boolean isPopular) {
    this.isPopular = isPopular;
  }

  public Object getPopularCreated() {
    return popularCreated;
  }

  public void setPopularCreated(Object popularCreated) {
    this.popularCreated = popularCreated;
  }

  public Integer getUser() {
    return user;
  }

  public void setUser(Integer user) {
    this.user = user;
  }

  public Integer getMediaCategory() {
    return mediaCategory;
  }

  public void setMediaCategory(Integer mediaCategory) {
    this.mediaCategory = mediaCategory;
  }

  public Integer getViews() {
    return views;
  }

  public void setViews(Integer views) {
    this.views = views;
  }

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
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

  public void setMediaThumbnail(String mediaThumbnail) {
    this.mediaThumbnail = mediaThumbnail;
  }

  public String getLivePassword() {
    return livePassword;
  }

  public void setLivePassword(String livePassword) {
    this.livePassword = livePassword;
  }

  public Boolean getAlive() {
    return alive;
  }

  public void setAlive(Boolean alive) {
    this.alive = alive;
  }

  public List<Object> getForcedOffs() {
    return forcedOffs;
  }

  public void setForcedOffs(List<Object> forcedOffs) {
    this.forcedOffs = forcedOffs;
  }

  public Object getLiveLastUpdate() {
    return liveLastUpdate;
  }

  public void setLiveLastUpdate(Object liveLastUpdate) {
    this.liveLastUpdate = liveLastUpdate;
  }

  public String getLiveVodId() {
    return liveVodId;
  }

  public void setLiveVodId(String liveVodId) {
    this.liveVodId = liveVodId;
  }

  public Object getLiveStartDatetime() {
    return liveStartDatetime;
  }

  public void setLiveStartDatetime(Object liveStartDatetime) {
    this.liveStartDatetime = liveStartDatetime;
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

  public Object getLiveResolution() {
    return liveResolution;
  }

  public void setLiveResolution(Object liveResolution) {
    this.liveResolution = liveResolution;
  }

  public Boolean getIsConverted() {
    return isConverted;
  }

  public void setIsConverted(Boolean isConverted) {
    this.isConverted = isConverted;
  }

  public Object getDcGallery() {
    return dcGallery;
  }

  public void setDcGallery(Object dcGallery) {
    this.dcGallery = dcGallery;
  }

  public Object getDcGalleryName() {
    return dcGalleryName;
  }

  public void setDcGalleryName(Object dcGalleryName) {
    this.dcGalleryName = dcGalleryName;
  }
}