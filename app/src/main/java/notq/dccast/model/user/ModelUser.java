package notq.dccast.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import java.io.Serializable;
import notq.dccast.model.user.profile.ModelProfile;
import notq.dccast.model.user.profile.ModelProfileStat;

public class ModelUser extends RealmObject implements Serializable {
  @PrimaryKey
  @SerializedName("id")
  @Expose
  public int id;
  @SerializedName("user")
  @Expose
  public ModelChildUser user;
  @SerializedName("user_no")
  @Expose
  public String userNo;
  @SerializedName("name_certification")
  @Expose
  public Boolean nameCertification;
  @SerializedName("auto_login")
  @Expose
  public Boolean autoLogin;
  @SerializedName("nick_name")
  @Expose
  public String nickName;
  @SerializedName("state_message")
  @Expose
  public String stateMessage;
  @SerializedName("notice_live")
  @Expose
  public Boolean noticeLive;
  @SerializedName("notice_vod")
  @Expose
  public Boolean noticeVod;
  @SerializedName("notice_chat")
  @Expose
  public Boolean noticeChat;
  @SerializedName("notice_notice")
  @Expose
  public Boolean noticeNotice;
  @SerializedName("notice_sound")
  @Expose
  public Boolean noticeSound;
  @SerializedName("notice_vibration")
  @Expose
  public Boolean noticeVibration;
  @SerializedName("phone")
  @Expose
  public String phone;
  @SerializedName("phone_certification")
  @Expose
  public Boolean phoneCertification;
  @SerializedName("limit_mobile_data")
  @Expose
  public Boolean limitMobileData;
  @SerializedName("stop_recent_view")
  @Expose
  public Boolean stopRecentView;
  @SerializedName("stop_recent_search")
  @Expose
  public Boolean stopRecentSearch;
  @SerializedName("profile_image")
  @Expose
  public String profileImage;
  @SerializedName("phone_certification_date")
  @Expose
  public String phoneCertificationDate;
  @SerializedName("adult_certification")
  @Expose
  public Boolean adultCertification;
  @SerializedName("set_pass")
  @Expose
  public Boolean setPass;
  @SerializedName("pass_string")
  @Expose
  public String passString;
  @SerializedName("adult_certification_date")
  @Expose
  public String adultCertificationDate;
  @SerializedName("last_name_certification")
  @Expose
  public String lastNameCertification;
  @SerializedName("email_certification")
  @Expose
  public String emailCertification;
  @SerializedName("is_vip")
  @Expose
  public Boolean isVip;
  @SerializedName("is_vip_active")
  @Expose
  public Boolean isVipActive;
  @SerializedName("vip_create_date")
  @Expose
  public String vipCreateDate;
  @SerializedName("profile_original_image")
  @Expose
  public String profileOriginalImage;
  @SerializedName("client_token")
  @Expose
  public String clientToken;
  @SerializedName("on_air")
  @Expose
  public boolean onAir;
  @SerializedName("on_air_media")
  @Expose
  @Ignore
  public int onAirMedia;
  @Ignore
  public ModelProfileStat stat;
  @Ignore
  public boolean following = false;
  @Ignore
  public boolean isFriend = false;
  @Ignore
  public boolean isFriendRequestSend = false;
  @Ignore
  public boolean selected = false;
  @Ignore
  boolean needFetchProfile;
  @Ignore
  boolean needFetchFriend;

  public int getFollowingId() {
    return followingId;
  }

  public void setFollowingId(int followingId) {
    this.followingId = followingId;
  }

  @Ignore
  private int followingId;
  private String token;
  public ModelUser() {
  }
  public ModelUser(int id) {
    this.id = id;
  }

  public ModelUser(ModelUser profile) {
    this.id = profile.getId();
    this.user = profile.getUser();
    this.userNo = profile.getUserNo();
    this.nameCertification = profile.getNameCertification();
    this.autoLogin = profile.getAutoLogin();
    this.nickName = profile.getNickName();
    this.stateMessage = profile.getStateMessage();
    this.noticeLive = profile.getNoticeLive();
    this.noticeVod = profile.getNoticeVod();
    this.noticeChat = profile.getNoticeChat();
    this.noticeNotice = profile.getNoticeNotice();
    this.noticeSound = profile.getNoticeSound();
    this.noticeVibration = profile.getNoticeVibration();
    this.phone = profile.getPhone();
    this.phoneCertification = profile.getPhoneCertification();
    this.limitMobileData = profile.getLimitMobileData();
    this.stopRecentView = profile.getStopRecentView();
    this.stopRecentSearch = profile.getStopRecentSearch();
    this.profileImage = profile.getProfileImage();
    this.phoneCertificationDate = profile.getPhoneCertificationDate();
    this.adultCertification = profile.getAdultCertification();
    this.setPass = profile.getSetPass();
    this.passString = profile.getPassString();
    this.adultCertificationDate = profile.getAdultCertificationDate();
    this.lastNameCertification = profile.getLastNameCertification();
    this.emailCertification = profile.getEmailCertification();
    this.isVip = profile.getVip();
    this.isVipActive = profile.getVipActive();
    this.vipCreateDate = profile.getVipCreateDate();
    this.profileOriginalImage = profile.getProfileOriginalImage();
    this.clientToken = profile.getClientToken();
    this.onAir = profile.isOnAir();
    this.onAirMedia = profile.getOnAirMedia();
    this.followingId = profile.getFollowingId();
  }

  public int getOnAirMedia() {
    return onAirMedia;
  }

  public void setOnAirMedia(int onAirMedia) {
    this.onAirMedia = onAirMedia;
  }

  public boolean isOnAir() {
    return onAir;
  }

  public void setOnAir(boolean onAir) {
    this.onAir = onAir;
  }

  public boolean isFriendRequestSend() {
    return isFriendRequestSend;
  }

  public void setFriendRequestSend(boolean friendRequestSend) {
    needFetchFriend = false;
    isFriendRequestSend = friendRequestSend;
  }

  public boolean isNeedFetchFriend() {
    return needFetchFriend;
  }

  public void setNeedFetchFriend(boolean needFetchFriend) {
    this.needFetchFriend = needFetchFriend;
  }

  public boolean isFriend() {
    return isFriend;
  }

  public void setFriend(boolean friend) {
    isFriend = friend;
    isFriendRequestSend = false;
    needFetchFriend = false;
  }

  public boolean isNeedFetchProfile() {
    return needFetchProfile;
  }

  public void setNeedFetchProfile(boolean needFetchProfile) {
    this.needFetchProfile = needFetchProfile;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public boolean isFollowing() {
    return following;
  }

  public void setFollowing(boolean following) {
    this.following = following;
  }

  public void setProfile(ModelProfile profile) {
    this.userNo = profile.getUserNo();
    this.nameCertification = profile.getNameCertification();
    this.autoLogin = profile.getAutoLogin();
    this.nickName = profile.getNickName();
    this.stateMessage = profile.getStateMessage();
    this.noticeLive = profile.getNoticeLive();
    this.noticeVod = profile.getNoticeVod();
    this.noticeChat = profile.getNoticeChat();
    this.noticeNotice = profile.getNoticeNotice();
    this.noticeSound = profile.getNoticeSound();
    this.noticeVibration = profile.getNoticeVibration();
    this.phone = profile.getPhone();
    this.phoneCertification = profile.getPhoneCertification();
    this.limitMobileData = profile.getLimitMobileData();
    this.stopRecentView = profile.getStopRecentView();
    this.stopRecentSearch = profile.getStopRecentSearch();
    this.profileImage = profile.getProfileImage();
    this.phoneCertificationDate = profile.getPhoneCertificationDate();
    this.adultCertification = profile.getAdultCertification();
    this.setPass = profile.getSetPass();
    this.passString = profile.getPassString();
    this.adultCertificationDate = profile.getAdultCertificationDate();
    this.lastNameCertification = profile.getLastNameCertification();
    this.emailCertification = profile.getEmailCertification();
    this.isVip = profile.getVip();
    this.isVipActive = profile.getVipActive();
    this.vipCreateDate = profile.getVipCreateDate();
    this.profileOriginalImage = profile.getProfileOriginalImage();
    this.clientToken = profile.getClientToken();
    this.onAir = profile.isOnAir();
    this.followingId = profile.getFollowingId();
  }

  public ModelProfileStat getStat() {
    return stat;
  }

  public void setStat(ModelProfileStat stat) {
    this.stat = stat;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ModelChildUser getUser() {
    return user;
  }

  public void setUser(ModelChildUser user) {
    this.user = user;
  }

  public String getUserNo() {
    return userNo;
  }

  public void setUserNo(String userNo) {
    this.userNo = userNo;
  }

  public Boolean getNameCertification() {
    return nameCertification;
  }

  public void setNameCertification(Boolean nameCertification) {
    this.nameCertification = nameCertification;
  }

  public Boolean getAutoLogin() {
    return autoLogin;
  }

  public void setAutoLogin(Boolean autoLogin) {
    this.autoLogin = autoLogin;
  }

  public String getNickName() {
    if (nickName == null) {
      return "";
    }
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getStateMessage() {
    return stateMessage;
  }

  public void setStateMessage(String stateMessage) {
    this.stateMessage = stateMessage;
  }

  public Boolean getNoticeLive() {
    return noticeLive;
  }

  public void setNoticeLive(Boolean noticeLive) {
    this.noticeLive = noticeLive;
  }

  public Boolean getNoticeVod() {
    return noticeVod;
  }

  public void setNoticeVod(Boolean noticeVod) {
    this.noticeVod = noticeVod;
  }

  public Boolean getNoticeChat() {
    return noticeChat;
  }

  public void setNoticeChat(Boolean noticeChat) {
    this.noticeChat = noticeChat;
  }

  public Boolean getNoticeNotice() {
    return noticeNotice;
  }

  public void setNoticeNotice(Boolean noticeNotice) {
    this.noticeNotice = noticeNotice;
  }

  public Boolean getNoticeSound() {
    return noticeSound;
  }

  public void setNoticeSound(Boolean noticeSound) {
    this.noticeSound = noticeSound;
  }

  public Boolean getNoticeVibration() {
    return noticeVibration;
  }

  public void setNoticeVibration(Boolean noticeVibration) {
    this.noticeVibration = noticeVibration;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Boolean getPhoneCertification() {
    return phoneCertification;
  }

  public void setPhoneCertification(Boolean phoneCertification) {
    this.phoneCertification = phoneCertification;
  }

  public Boolean getLimitMobileData() {
    return limitMobileData;
  }

  public void setLimitMobileData(Boolean limitMobileData) {
    this.limitMobileData = limitMobileData;
  }

  public Boolean getStopRecentView() {
    return stopRecentView;
  }

  public void setStopRecentView(Boolean stopRecentView) {
    this.stopRecentView = stopRecentView;
  }

  public Boolean getStopRecentSearch() {
    return stopRecentSearch;
  }

  public void setStopRecentSearch(Boolean stopRecentSearch) {
    this.stopRecentSearch = stopRecentSearch;
  }

  public String getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(String profileImage) {
    this.profileImage = profileImage;
  }

  public String getPhoneCertificationDate() {
    return phoneCertificationDate;
  }

  public void setPhoneCertificationDate(String phoneCertificationDate) {
    this.phoneCertificationDate = phoneCertificationDate;
  }

  public Boolean getAdultCertification() {
    return adultCertification;
  }

  public void setAdultCertification(Boolean adultCertification) {
    this.adultCertification = adultCertification;
  }

  public Boolean getSetPass() {
    if(setPass == null) {
      return false;
    }
    return setPass;
  }

  public void setSetPass(Boolean setPass) {
    this.setPass = setPass;
  }

  public String getPassString() {
    return passString;
  }

  public void setPassString(String passString) {
    this.passString = passString;
  }

  public String getAdultCertificationDate() {
    return adultCertificationDate;
  }

  public void setAdultCertificationDate(String adultCertificationDate) {
    this.adultCertificationDate = adultCertificationDate;
  }

  public String getLastNameCertification() {
    return lastNameCertification;
  }

  public void setLastNameCertification(String lastNameCertification) {
    this.lastNameCertification = lastNameCertification;
  }

  public String getEmailCertification() {
    return emailCertification;
  }

  public void setEmailCertification(String emailCertification) {
    this.emailCertification = emailCertification;
  }

  public Boolean getVip() {
    return isVip;
  }

  public void setVip(Boolean vip) {
    isVip = vip;
  }

  public Boolean getVipActive() {
    return isVipActive;
  }

  public void setVipActive(Boolean vipActive) {
    isVipActive = vipActive;
  }

  public String getVipCreateDate() {
    return vipCreateDate;
  }

  public void setVipCreateDate(String vipCreateDate) {
    this.vipCreateDate = vipCreateDate;
  }

  public String getProfileOriginalImage() {
    return profileOriginalImage;
  }

  public void setProfileOriginalImage(String profileOriginalImage) {
    this.profileOriginalImage = profileOriginalImage;
  }

  public String getClientToken() {
    return clientToken;
  }

  public void setClientToken(String clientToken) {
    this.clientToken = clientToken;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
