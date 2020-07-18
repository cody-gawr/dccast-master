package notq.dccast;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import notq.dccast.databinding.ActivityAddFriendBindingImpl;
import notq.dccast.databinding.ActivityAddPeopleBindingImpl;
import notq.dccast.databinding.ActivityContactUsBindingImpl;
import notq.dccast.databinding.ActivityContactUsDetailBindingImpl;
import notq.dccast.databinding.ActivityCreateGroupBindingImpl;
import notq.dccast.databinding.ActivityEditProfileBindingImpl;
import notq.dccast.databinding.ActivityFavoriteBindingImpl;
import notq.dccast.databinding.ActivityForgetPasswordBindingImpl;
import notq.dccast.databinding.ActivityGroupDetailBindingImpl;
import notq.dccast.databinding.ActivityHistoryInformationBindingImpl;
import notq.dccast.databinding.ActivityHomeBindingImpl;
import notq.dccast.databinding.ActivityHomeToolbarBindingImpl;
import notq.dccast.databinding.ActivityLiveSettingsBindingImpl;
import notq.dccast.databinding.ActivityLiveStreamBindingImpl;
import notq.dccast.databinding.ActivityLoginBindingImpl;
import notq.dccast.databinding.ActivityManduHistoryWebViewBindingImpl;
import notq.dccast.databinding.ActivityMyChannelBindingImpl;
import notq.dccast.databinding.ActivityMyContentBindingImpl;
import notq.dccast.databinding.ActivityMyLiveHistoryBindingImpl;
import notq.dccast.databinding.ActivityNotificationBindingImpl;
import notq.dccast.databinding.ActivityNotificationSettingsBindingImpl;
import notq.dccast.databinding.ActivityRecentBindingImpl;
import notq.dccast.databinding.ActivitySearchBindingImpl;
import notq.dccast.databinding.ActivitySearchFollowersBindingImpl;
import notq.dccast.databinding.ActivitySearchFriendBindingImpl;
import notq.dccast.databinding.ActivitySearchGroupsBindingImpl;
import notq.dccast.databinding.ActivitySearchToolbarBindingImpl;
import notq.dccast.databinding.ActivitySelectGalleryBindingImpl;
import notq.dccast.databinding.ActivitySettingsBindingImpl;
import notq.dccast.databinding.ActivitySignUpBindingImpl;
import notq.dccast.databinding.ActivitySplashBindingImpl;
import notq.dccast.databinding.ActivitySubscribeBindingImpl;
import notq.dccast.databinding.ActivityThumbnailChooserBindingImpl;
import notq.dccast.databinding.ActivityTrimmerBindingImpl;
import notq.dccast.databinding.ActivityUploadVodBindingImpl;
import notq.dccast.databinding.ActivityUserFollowBindingImpl;
import notq.dccast.databinding.ActivityWebViewBindingImpl;
import notq.dccast.databinding.DialogAgeConfirmBindingImpl;
import notq.dccast.databinding.DialogChangeNicknameBindingImpl;
import notq.dccast.databinding.DialogManduPasswordBindingImpl;
import notq.dccast.databinding.DialogShareBindingImpl;
import notq.dccast.databinding.DialogVideoReportBindingImpl;
import notq.dccast.databinding.DialogVideoSettingsBindingImpl;
import notq.dccast.databinding.FragmentBottomAddCommentBindingImpl;
import notq.dccast.databinding.FragmentBottomBuuzBindingImpl;
import notq.dccast.databinding.FragmentBottomCommentBindingImpl;
import notq.dccast.databinding.FragmentBottomLiveSettingsBindingImpl;
import notq.dccast.databinding.FragmentBottomLiveTitleBindingImpl;
import notq.dccast.databinding.FragmentBottomLockBindingImpl;
import notq.dccast.databinding.FragmentBottomOptionsBindingImpl;
import notq.dccast.databinding.FragmentBottomSetLockBindingImpl;
import notq.dccast.databinding.FragmentBottomSortBindingImpl;
import notq.dccast.databinding.FragmentBottomStickerBindingImpl;
import notq.dccast.databinding.FragmentCastBindingImpl;
import notq.dccast.databinding.FragmentContactBindingImpl;
import notq.dccast.databinding.FragmentContactListBindingImpl;
import notq.dccast.databinding.FragmentContentRecentBindingImpl;
import notq.dccast.databinding.FragmentFavoriteBindingImpl;
import notq.dccast.databinding.FragmentFollowersBindingImpl;
import notq.dccast.databinding.FragmentFollowingBindingImpl;
import notq.dccast.databinding.FragmentFriendsBindingImpl;
import notq.dccast.databinding.FragmentGroupMemberBindingImpl;
import notq.dccast.databinding.FragmentGroupVideoBindingImpl;
import notq.dccast.databinding.FragmentGroupsBindingImpl;
import notq.dccast.databinding.FragmentHomeHeaderBindingImpl;
import notq.dccast.databinding.FragmentHomeListBindingImpl;
import notq.dccast.databinding.FragmentManduHistoryBindingImpl;
import notq.dccast.databinding.FragmentMyChannelBindingImpl;
import notq.dccast.databinding.FragmentMyLiveHistoryBindingImpl;
import notq.dccast.databinding.FragmentNotificationLiveBindingImpl;
import notq.dccast.databinding.FragmentNotificationNoticeBindingImpl;
import notq.dccast.databinding.FragmentNotificationVodBindingImpl;
import notq.dccast.databinding.FragmentRecentBindingImpl;
import notq.dccast.databinding.FragmentSearchResultPageBindingImpl;
import notq.dccast.databinding.FragmentStickerPaginationBindingImpl;
import notq.dccast.databinding.FragmentVideoBindingImpl;
import notq.dccast.databinding.LayoutAdsBindingImpl;
import notq.dccast.databinding.LayoutBackHeaderBindingImpl;
import notq.dccast.databinding.LayoutBackHeaderSubscribeBindingImpl;
import notq.dccast.databinding.LayoutHeaderLoadingBindingImpl;
import notq.dccast.databinding.TestBindingImpl;
import notq.dccast.databinding.VhBottomSheetOptionsBindingImpl;
import notq.dccast.databinding.VhCastAddFriendBindingImpl;
import notq.dccast.databinding.VhCastFollowerBindingImpl;
import notq.dccast.databinding.VhCastFriendBindingImpl;
import notq.dccast.databinding.VhCastGroupBindingImpl;
import notq.dccast.databinding.VhCastRecentFollowerBindingImpl;
import notq.dccast.databinding.VhCastSearchFriendBindingImpl;
import notq.dccast.databinding.VhCommentBodyBindingImpl;
import notq.dccast.databinding.VhCommentNoDataBindingImpl;
import notq.dccast.databinding.VhCommentWriteBindingImpl;
import notq.dccast.databinding.VhContactUsBindingImpl;
import notq.dccast.databinding.VhGroupAddPeopleBindingImpl;
import notq.dccast.databinding.VhGroupMemberBindingImpl;
import notq.dccast.databinding.VhHomeCategoryBindingImpl;
import notq.dccast.databinding.VhHomeLoadMoreBindingImpl;
import notq.dccast.databinding.VhHomeMainHeaderBindingImpl;
import notq.dccast.databinding.VhHomeNoDataBindingImpl;
import notq.dccast.databinding.VhHomePopularHeaderBindingImpl;
import notq.dccast.databinding.VhHomeSubHeaderBindingImpl;
import notq.dccast.databinding.VhHomeVideoBindingImpl;
import notq.dccast.databinding.VhLiveChatBindingImpl;
import notq.dccast.databinding.VhNavCategoryBindingImpl;
import notq.dccast.databinding.VhNavCategoryItemBindingImpl;
import notq.dccast.databinding.VhNavMenu2BindingImpl;
import notq.dccast.databinding.VhNavMenuBindingImpl;
import notq.dccast.databinding.VhNavProfileBindingImpl;
import notq.dccast.databinding.VhNotificationLiveItemBindingImpl;
import notq.dccast.databinding.VhNotificationNoticeItemBindingImpl;
import notq.dccast.databinding.VhNotificationVodItemBindingImpl;
import notq.dccast.databinding.VhPopularItemBindingImpl;
import notq.dccast.databinding.VhRelatedVideoHeaderBindingImpl;
import notq.dccast.databinding.VhSearchHistoryBindingImpl;
import notq.dccast.databinding.VhShareAndFriendsBindingImpl;
import notq.dccast.databinding.VhStickerTabBindingImpl;
import notq.dccast.databinding.VhStickersBindingImpl;
import notq.dccast.databinding.VhSubscribeBindingImpl;
import notq.dccast.databinding.VhVodGalleyItemBindingImpl;
import notq.dccast.databinding.VhVodGalleySectionBindingImpl;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYADDFRIEND = 1;

  private static final int LAYOUT_ACTIVITYADDPEOPLE = 2;

  private static final int LAYOUT_ACTIVITYCONTACTUS = 3;

  private static final int LAYOUT_ACTIVITYCONTACTUSDETAIL = 4;

  private static final int LAYOUT_ACTIVITYCREATEGROUP = 5;

  private static final int LAYOUT_ACTIVITYEDITPROFILE = 6;

  private static final int LAYOUT_ACTIVITYFAVORITE = 7;

  private static final int LAYOUT_ACTIVITYFORGETPASSWORD = 8;

  private static final int LAYOUT_ACTIVITYGROUPDETAIL = 9;

  private static final int LAYOUT_ACTIVITYHISTORYINFORMATION = 10;

  private static final int LAYOUT_ACTIVITYHOME = 11;

  private static final int LAYOUT_ACTIVITYHOMETOOLBAR = 12;

  private static final int LAYOUT_ACTIVITYLIVESETTINGS = 13;

  private static final int LAYOUT_ACTIVITYLIVESTREAM = 14;

  private static final int LAYOUT_ACTIVITYLOGIN = 15;

  private static final int LAYOUT_ACTIVITYMANDUHISTORYWEBVIEW = 16;

  private static final int LAYOUT_ACTIVITYMYCHANNEL = 17;

  private static final int LAYOUT_ACTIVITYMYCONTENT = 18;

  private static final int LAYOUT_ACTIVITYMYLIVEHISTORY = 19;

  private static final int LAYOUT_ACTIVITYNOTIFICATION = 20;

  private static final int LAYOUT_ACTIVITYNOTIFICATIONSETTINGS = 21;

  private static final int LAYOUT_ACTIVITYRECENT = 22;

  private static final int LAYOUT_ACTIVITYSEARCH = 23;

  private static final int LAYOUT_ACTIVITYSEARCHFOLLOWERS = 24;

  private static final int LAYOUT_ACTIVITYSEARCHFRIEND = 25;

  private static final int LAYOUT_ACTIVITYSEARCHGROUPS = 26;

  private static final int LAYOUT_ACTIVITYSEARCHTOOLBAR = 27;

  private static final int LAYOUT_ACTIVITYSELECTGALLERY = 28;

  private static final int LAYOUT_ACTIVITYSETTINGS = 29;

  private static final int LAYOUT_ACTIVITYSIGNUP = 30;

  private static final int LAYOUT_ACTIVITYSPLASH = 31;

  private static final int LAYOUT_ACTIVITYSUBSCRIBE = 32;

  private static final int LAYOUT_ACTIVITYTHUMBNAILCHOOSER = 33;

  private static final int LAYOUT_ACTIVITYTRIMMER = 34;

  private static final int LAYOUT_ACTIVITYUPLOADVOD = 35;

  private static final int LAYOUT_ACTIVITYUSERFOLLOW = 36;

  private static final int LAYOUT_ACTIVITYWEBVIEW = 37;

  private static final int LAYOUT_DIALOGAGECONFIRM = 38;

  private static final int LAYOUT_DIALOGCHANGENICKNAME = 39;

  private static final int LAYOUT_DIALOGMANDUPASSWORD = 40;

  private static final int LAYOUT_DIALOGSHARE = 41;

  private static final int LAYOUT_DIALOGVIDEOREPORT = 42;

  private static final int LAYOUT_DIALOGVIDEOSETTINGS = 43;

  private static final int LAYOUT_FRAGMENTBOTTOMADDCOMMENT = 44;

  private static final int LAYOUT_FRAGMENTBOTTOMBUUZ = 45;

  private static final int LAYOUT_FRAGMENTBOTTOMCOMMENT = 46;

  private static final int LAYOUT_FRAGMENTBOTTOMLIVESETTINGS = 47;

  private static final int LAYOUT_FRAGMENTBOTTOMLIVETITLE = 48;

  private static final int LAYOUT_FRAGMENTBOTTOMLOCK = 49;

  private static final int LAYOUT_FRAGMENTBOTTOMOPTIONS = 50;

  private static final int LAYOUT_FRAGMENTBOTTOMSETLOCK = 51;

  private static final int LAYOUT_FRAGMENTBOTTOMSORT = 52;

  private static final int LAYOUT_FRAGMENTBOTTOMSTICKER = 53;

  private static final int LAYOUT_FRAGMENTCAST = 54;

  private static final int LAYOUT_FRAGMENTCONTACT = 55;

  private static final int LAYOUT_FRAGMENTCONTACTLIST = 56;

  private static final int LAYOUT_FRAGMENTCONTENTRECENT = 57;

  private static final int LAYOUT_FRAGMENTFAVORITE = 58;

  private static final int LAYOUT_FRAGMENTFOLLOWERS = 59;

  private static final int LAYOUT_FRAGMENTFOLLOWING = 60;

  private static final int LAYOUT_FRAGMENTFRIENDS = 61;

  private static final int LAYOUT_FRAGMENTGROUPMEMBER = 62;

  private static final int LAYOUT_FRAGMENTGROUPVIDEO = 63;

  private static final int LAYOUT_FRAGMENTGROUPS = 64;

  private static final int LAYOUT_FRAGMENTHOMEHEADER = 65;

  private static final int LAYOUT_FRAGMENTHOMELIST = 66;

  private static final int LAYOUT_FRAGMENTMANDUHISTORY = 67;

  private static final int LAYOUT_FRAGMENTMYCHANNEL = 68;

  private static final int LAYOUT_FRAGMENTMYLIVEHISTORY = 69;

  private static final int LAYOUT_FRAGMENTNOTIFICATIONLIVE = 70;

  private static final int LAYOUT_FRAGMENTNOTIFICATIONNOTICE = 71;

  private static final int LAYOUT_FRAGMENTNOTIFICATIONVOD = 72;

  private static final int LAYOUT_FRAGMENTRECENT = 73;

  private static final int LAYOUT_FRAGMENTSEARCHRESULTPAGE = 74;

  private static final int LAYOUT_FRAGMENTSTICKERPAGINATION = 75;

  private static final int LAYOUT_FRAGMENTVIDEO = 76;

  private static final int LAYOUT_LAYOUTADS = 77;

  private static final int LAYOUT_LAYOUTBACKHEADER = 78;

  private static final int LAYOUT_LAYOUTBACKHEADERSUBSCRIBE = 79;

  private static final int LAYOUT_LAYOUTHEADERLOADING = 80;

  private static final int LAYOUT_TEST = 81;

  private static final int LAYOUT_VHBOTTOMSHEETOPTIONS = 82;

  private static final int LAYOUT_VHCASTADDFRIEND = 83;

  private static final int LAYOUT_VHCASTFOLLOWER = 84;

  private static final int LAYOUT_VHCASTFRIEND = 85;

  private static final int LAYOUT_VHCASTGROUP = 86;

  private static final int LAYOUT_VHCASTRECENTFOLLOWER = 87;

  private static final int LAYOUT_VHCASTSEARCHFRIEND = 88;

  private static final int LAYOUT_VHCOMMENTBODY = 89;

  private static final int LAYOUT_VHCOMMENTNODATA = 90;

  private static final int LAYOUT_VHCOMMENTWRITE = 91;

  private static final int LAYOUT_VHCONTACTUS = 92;

  private static final int LAYOUT_VHGROUPADDPEOPLE = 93;

  private static final int LAYOUT_VHGROUPMEMBER = 94;

  private static final int LAYOUT_VHHOMECATEGORY = 95;

  private static final int LAYOUT_VHHOMELOADMORE = 96;

  private static final int LAYOUT_VHHOMEMAINHEADER = 97;

  private static final int LAYOUT_VHHOMENODATA = 98;

  private static final int LAYOUT_VHHOMEPOPULARHEADER = 99;

  private static final int LAYOUT_VHHOMESUBHEADER = 100;

  private static final int LAYOUT_VHHOMEVIDEO = 101;

  private static final int LAYOUT_VHLIVECHAT = 102;

  private static final int LAYOUT_VHNAVCATEGORY = 103;

  private static final int LAYOUT_VHNAVCATEGORYITEM = 104;

  private static final int LAYOUT_VHNAVMENU = 105;

  private static final int LAYOUT_VHNAVMENU2 = 106;

  private static final int LAYOUT_VHNAVPROFILE = 107;

  private static final int LAYOUT_VHNOTIFICATIONLIVEITEM = 108;

  private static final int LAYOUT_VHNOTIFICATIONNOTICEITEM = 109;

  private static final int LAYOUT_VHNOTIFICATIONVODITEM = 110;

  private static final int LAYOUT_VHPOPULARITEM = 111;

  private static final int LAYOUT_VHRELATEDVIDEOHEADER = 112;

  private static final int LAYOUT_VHSEARCHHISTORY = 113;

  private static final int LAYOUT_VHSHAREANDFRIENDS = 114;

  private static final int LAYOUT_VHSTICKERTAB = 115;

  private static final int LAYOUT_VHSTICKERS = 116;

  private static final int LAYOUT_VHSUBSCRIBE = 117;

  private static final int LAYOUT_VHVODGALLEYITEM = 118;

  private static final int LAYOUT_VHVODGALLEYSECTION = 119;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(119);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_add_friend, LAYOUT_ACTIVITYADDFRIEND);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_add_people, LAYOUT_ACTIVITYADDPEOPLE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_contact_us, LAYOUT_ACTIVITYCONTACTUS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_contact_us_detail, LAYOUT_ACTIVITYCONTACTUSDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_create_group, LAYOUT_ACTIVITYCREATEGROUP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_edit_profile, LAYOUT_ACTIVITYEDITPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_favorite, LAYOUT_ACTIVITYFAVORITE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_forget_password, LAYOUT_ACTIVITYFORGETPASSWORD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_group_detail, LAYOUT_ACTIVITYGROUPDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_history_information, LAYOUT_ACTIVITYHISTORYINFORMATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_home, LAYOUT_ACTIVITYHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_home_toolbar, LAYOUT_ACTIVITYHOMETOOLBAR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_live_settings, LAYOUT_ACTIVITYLIVESETTINGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_live_stream, LAYOUT_ACTIVITYLIVESTREAM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_mandu_history_web_view, LAYOUT_ACTIVITYMANDUHISTORYWEBVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_my_channel, LAYOUT_ACTIVITYMYCHANNEL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_my_content, LAYOUT_ACTIVITYMYCONTENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_my_live_history, LAYOUT_ACTIVITYMYLIVEHISTORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_notification, LAYOUT_ACTIVITYNOTIFICATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_notification_settings, LAYOUT_ACTIVITYNOTIFICATIONSETTINGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_recent, LAYOUT_ACTIVITYRECENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_search, LAYOUT_ACTIVITYSEARCH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_search_followers, LAYOUT_ACTIVITYSEARCHFOLLOWERS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_search_friend, LAYOUT_ACTIVITYSEARCHFRIEND);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_search_groups, LAYOUT_ACTIVITYSEARCHGROUPS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_search_toolbar, LAYOUT_ACTIVITYSEARCHTOOLBAR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_select_gallery, LAYOUT_ACTIVITYSELECTGALLERY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_settings, LAYOUT_ACTIVITYSETTINGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_sign_up, LAYOUT_ACTIVITYSIGNUP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_splash, LAYOUT_ACTIVITYSPLASH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_subscribe, LAYOUT_ACTIVITYSUBSCRIBE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_thumbnail_chooser, LAYOUT_ACTIVITYTHUMBNAILCHOOSER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_trimmer, LAYOUT_ACTIVITYTRIMMER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_upload_vod, LAYOUT_ACTIVITYUPLOADVOD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_user_follow, LAYOUT_ACTIVITYUSERFOLLOW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.activity_web_view, LAYOUT_ACTIVITYWEBVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.dialog_age_confirm, LAYOUT_DIALOGAGECONFIRM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.dialog_change_nickname, LAYOUT_DIALOGCHANGENICKNAME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.dialog_mandu_password, LAYOUT_DIALOGMANDUPASSWORD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.dialog_share, LAYOUT_DIALOGSHARE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.dialog_video_report, LAYOUT_DIALOGVIDEOREPORT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.dialog_video_settings, LAYOUT_DIALOGVIDEOSETTINGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_bottom_add_comment, LAYOUT_FRAGMENTBOTTOMADDCOMMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_bottom_buuz, LAYOUT_FRAGMENTBOTTOMBUUZ);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_bottom_comment, LAYOUT_FRAGMENTBOTTOMCOMMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_bottom_live_settings, LAYOUT_FRAGMENTBOTTOMLIVESETTINGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_bottom_live_title, LAYOUT_FRAGMENTBOTTOMLIVETITLE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_bottom_lock, LAYOUT_FRAGMENTBOTTOMLOCK);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_bottom_options, LAYOUT_FRAGMENTBOTTOMOPTIONS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_bottom_set_lock, LAYOUT_FRAGMENTBOTTOMSETLOCK);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_bottom_sort, LAYOUT_FRAGMENTBOTTOMSORT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_bottom_sticker, LAYOUT_FRAGMENTBOTTOMSTICKER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_cast, LAYOUT_FRAGMENTCAST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_contact, LAYOUT_FRAGMENTCONTACT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_contact_list, LAYOUT_FRAGMENTCONTACTLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_content_recent, LAYOUT_FRAGMENTCONTENTRECENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_favorite, LAYOUT_FRAGMENTFAVORITE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_followers, LAYOUT_FRAGMENTFOLLOWERS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_following, LAYOUT_FRAGMENTFOLLOWING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_friends, LAYOUT_FRAGMENTFRIENDS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_group_member, LAYOUT_FRAGMENTGROUPMEMBER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_group_video, LAYOUT_FRAGMENTGROUPVIDEO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_groups, LAYOUT_FRAGMENTGROUPS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_home_header, LAYOUT_FRAGMENTHOMEHEADER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_home_list, LAYOUT_FRAGMENTHOMELIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_mandu_history, LAYOUT_FRAGMENTMANDUHISTORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_my_channel, LAYOUT_FRAGMENTMYCHANNEL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_my_live_history, LAYOUT_FRAGMENTMYLIVEHISTORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_notification_live, LAYOUT_FRAGMENTNOTIFICATIONLIVE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_notification_notice, LAYOUT_FRAGMENTNOTIFICATIONNOTICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_notification_vod, LAYOUT_FRAGMENTNOTIFICATIONVOD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_recent, LAYOUT_FRAGMENTRECENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_search_result_page, LAYOUT_FRAGMENTSEARCHRESULTPAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_sticker_pagination, LAYOUT_FRAGMENTSTICKERPAGINATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.fragment_video, LAYOUT_FRAGMENTVIDEO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.layout_ads, LAYOUT_LAYOUTADS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.layout_back_header, LAYOUT_LAYOUTBACKHEADER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.layout_back_header_subscribe, LAYOUT_LAYOUTBACKHEADERSUBSCRIBE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.layout_header_loading, LAYOUT_LAYOUTHEADERLOADING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.test, LAYOUT_TEST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_bottom_sheet_options, LAYOUT_VHBOTTOMSHEETOPTIONS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_cast_add_friend, LAYOUT_VHCASTADDFRIEND);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_cast_follower, LAYOUT_VHCASTFOLLOWER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_cast_friend, LAYOUT_VHCASTFRIEND);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_cast_group, LAYOUT_VHCASTGROUP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_cast_recent_follower, LAYOUT_VHCASTRECENTFOLLOWER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_cast_search_friend, LAYOUT_VHCASTSEARCHFRIEND);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_comment_body, LAYOUT_VHCOMMENTBODY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_comment_no_data, LAYOUT_VHCOMMENTNODATA);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_comment_write, LAYOUT_VHCOMMENTWRITE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_contact_us, LAYOUT_VHCONTACTUS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_group_add_people, LAYOUT_VHGROUPADDPEOPLE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_group_member, LAYOUT_VHGROUPMEMBER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_home_category, LAYOUT_VHHOMECATEGORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_home_load_more, LAYOUT_VHHOMELOADMORE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_home_main_header, LAYOUT_VHHOMEMAINHEADER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_home_no_data, LAYOUT_VHHOMENODATA);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_home_popular_header, LAYOUT_VHHOMEPOPULARHEADER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_home_sub_header, LAYOUT_VHHOMESUBHEADER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_home_video, LAYOUT_VHHOMEVIDEO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_live_chat, LAYOUT_VHLIVECHAT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_nav_category, LAYOUT_VHNAVCATEGORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_nav_category_item, LAYOUT_VHNAVCATEGORYITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_nav_menu, LAYOUT_VHNAVMENU);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_nav_menu_2, LAYOUT_VHNAVMENU2);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_nav_profile, LAYOUT_VHNAVPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_notification_live_item, LAYOUT_VHNOTIFICATIONLIVEITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_notification_notice_item, LAYOUT_VHNOTIFICATIONNOTICEITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_notification_vod_item, LAYOUT_VHNOTIFICATIONVODITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_popular_item, LAYOUT_VHPOPULARITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_related_video_header, LAYOUT_VHRELATEDVIDEOHEADER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_search_history, LAYOUT_VHSEARCHHISTORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_share_and_friends, LAYOUT_VHSHAREANDFRIENDS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_sticker_tab, LAYOUT_VHSTICKERTAB);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_stickers, LAYOUT_VHSTICKERS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_subscribe, LAYOUT_VHSUBSCRIBE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_vod_galley_item, LAYOUT_VHVODGALLEYITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(notq.dccast.R.layout.vh_vod_galley_section, LAYOUT_VHVODGALLEYSECTION);
  }

  private final ViewDataBinding internalGetViewDataBinding0(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_ACTIVITYADDFRIEND: {
        if ("layout/activity_add_friend_0".equals(tag)) {
          return new ActivityAddFriendBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_add_friend is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYADDPEOPLE: {
        if ("layout/activity_add_people_0".equals(tag)) {
          return new ActivityAddPeopleBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_add_people is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCONTACTUS: {
        if ("layout/activity_contact_us_0".equals(tag)) {
          return new ActivityContactUsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_contact_us is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCONTACTUSDETAIL: {
        if ("layout/activity_contact_us_detail_0".equals(tag)) {
          return new ActivityContactUsDetailBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_contact_us_detail is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCREATEGROUP: {
        if ("layout/activity_create_group_0".equals(tag)) {
          return new ActivityCreateGroupBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_create_group is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYEDITPROFILE: {
        if ("layout/activity_edit_profile_0".equals(tag)) {
          return new ActivityEditProfileBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_edit_profile is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYFAVORITE: {
        if ("layout/activity_favorite_0".equals(tag)) {
          return new ActivityFavoriteBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_favorite is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYFORGETPASSWORD: {
        if ("layout/activity_forget_password_0".equals(tag)) {
          return new ActivityForgetPasswordBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_forget_password is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYGROUPDETAIL: {
        if ("layout/activity_group_detail_0".equals(tag)) {
          return new ActivityGroupDetailBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_group_detail is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYHISTORYINFORMATION: {
        if ("layout/activity_history_information_0".equals(tag)) {
          return new ActivityHistoryInformationBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_history_information is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYHOME: {
        if ("layout/activity_home_0".equals(tag)) {
          return new ActivityHomeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_home is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYHOMETOOLBAR: {
        if ("layout/activity_home_toolbar_0".equals(tag)) {
          return new ActivityHomeToolbarBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_home_toolbar is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYLIVESETTINGS: {
        if ("layout/activity_live_settings_0".equals(tag)) {
          return new ActivityLiveSettingsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_live_settings is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYLIVESTREAM: {
        if ("layout/activity_live_stream_0".equals(tag)) {
          return new ActivityLiveStreamBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_live_stream is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYLOGIN: {
        if ("layout/activity_login_0".equals(tag)) {
          return new ActivityLoginBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYMANDUHISTORYWEBVIEW: {
        if ("layout/activity_mandu_history_web_view_0".equals(tag)) {
          return new ActivityManduHistoryWebViewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_mandu_history_web_view is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYMYCHANNEL: {
        if ("layout/activity_my_channel_0".equals(tag)) {
          return new ActivityMyChannelBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_my_channel is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYMYCONTENT: {
        if ("layout/activity_my_content_0".equals(tag)) {
          return new ActivityMyContentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_my_content is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYMYLIVEHISTORY: {
        if ("layout/activity_my_live_history_0".equals(tag)) {
          return new ActivityMyLiveHistoryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_my_live_history is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYNOTIFICATION: {
        if ("layout/activity_notification_0".equals(tag)) {
          return new ActivityNotificationBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_notification is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYNOTIFICATIONSETTINGS: {
        if ("layout/activity_notification_settings_0".equals(tag)) {
          return new ActivityNotificationSettingsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_notification_settings is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYRECENT: {
        if ("layout/activity_recent_0".equals(tag)) {
          return new ActivityRecentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_recent is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSEARCH: {
        if ("layout/activity_search_0".equals(tag)) {
          return new ActivitySearchBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_search is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSEARCHFOLLOWERS: {
        if ("layout/activity_search_followers_0".equals(tag)) {
          return new ActivitySearchFollowersBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_search_followers is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSEARCHFRIEND: {
        if ("layout/activity_search_friend_0".equals(tag)) {
          return new ActivitySearchFriendBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_search_friend is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSEARCHGROUPS: {
        if ("layout/activity_search_groups_0".equals(tag)) {
          return new ActivitySearchGroupsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_search_groups is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSEARCHTOOLBAR: {
        if ("layout/activity_search_toolbar_0".equals(tag)) {
          return new ActivitySearchToolbarBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_search_toolbar is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSELECTGALLERY: {
        if ("layout/activity_select_gallery_0".equals(tag)) {
          return new ActivitySelectGalleryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_select_gallery is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSETTINGS: {
        if ("layout/activity_settings_0".equals(tag)) {
          return new ActivitySettingsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_settings is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSIGNUP: {
        if ("layout/activity_sign_up_0".equals(tag)) {
          return new ActivitySignUpBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_sign_up is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSPLASH: {
        if ("layout/activity_splash_0".equals(tag)) {
          return new ActivitySplashBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_splash is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSUBSCRIBE: {
        if ("layout/activity_subscribe_0".equals(tag)) {
          return new ActivitySubscribeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_subscribe is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYTHUMBNAILCHOOSER: {
        if ("layout/activity_thumbnail_chooser_0".equals(tag)) {
          return new ActivityThumbnailChooserBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_thumbnail_chooser is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYTRIMMER: {
        if ("layout/activity_trimmer_0".equals(tag)) {
          return new ActivityTrimmerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_trimmer is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYUPLOADVOD: {
        if ("layout/activity_upload_vod_0".equals(tag)) {
          return new ActivityUploadVodBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_upload_vod is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYUSERFOLLOW: {
        if ("layout/activity_user_follow_0".equals(tag)) {
          return new ActivityUserFollowBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_user_follow is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYWEBVIEW: {
        if ("layout/activity_web_view_0".equals(tag)) {
          return new ActivityWebViewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_web_view is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGAGECONFIRM: {
        if ("layout/dialog_age_confirm_0".equals(tag)) {
          return new DialogAgeConfirmBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_age_confirm is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGCHANGENICKNAME: {
        if ("layout/dialog_change_nickname_0".equals(tag)) {
          return new DialogChangeNicknameBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_change_nickname is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGMANDUPASSWORD: {
        if ("layout/dialog_mandu_password_0".equals(tag)) {
          return new DialogManduPasswordBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_mandu_password is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGSHARE: {
        if ("layout/dialog_share_0".equals(tag)) {
          return new DialogShareBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_share is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGVIDEOREPORT: {
        if ("layout/dialog_video_report_0".equals(tag)) {
          return new DialogVideoReportBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_video_report is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGVIDEOSETTINGS: {
        if ("layout/dialog_video_settings_0".equals(tag)) {
          return new DialogVideoSettingsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_video_settings is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTBOTTOMADDCOMMENT: {
        if ("layout/fragment_bottom_add_comment_0".equals(tag)) {
          return new FragmentBottomAddCommentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_bottom_add_comment is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTBOTTOMBUUZ: {
        if ("layout/fragment_bottom_buuz_0".equals(tag)) {
          return new FragmentBottomBuuzBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_bottom_buuz is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTBOTTOMCOMMENT: {
        if ("layout/fragment_bottom_comment_0".equals(tag)) {
          return new FragmentBottomCommentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_bottom_comment is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTBOTTOMLIVESETTINGS: {
        if ("layout/fragment_bottom_live_settings_0".equals(tag)) {
          return new FragmentBottomLiveSettingsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_bottom_live_settings is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTBOTTOMLIVETITLE: {
        if ("layout/fragment_bottom_live_title_0".equals(tag)) {
          return new FragmentBottomLiveTitleBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_bottom_live_title is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTBOTTOMLOCK: {
        if ("layout/fragment_bottom_lock_0".equals(tag)) {
          return new FragmentBottomLockBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_bottom_lock is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTBOTTOMOPTIONS: {
        if ("layout/fragment_bottom_options_0".equals(tag)) {
          return new FragmentBottomOptionsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_bottom_options is invalid. Received: " + tag);
      }
    }
    return null;
  }

  private final ViewDataBinding internalGetViewDataBinding1(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_FRAGMENTBOTTOMSETLOCK: {
        if ("layout/fragment_bottom_set_lock_0".equals(tag)) {
          return new FragmentBottomSetLockBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_bottom_set_lock is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTBOTTOMSORT: {
        if ("layout/fragment_bottom_sort_0".equals(tag)) {
          return new FragmentBottomSortBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_bottom_sort is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTBOTTOMSTICKER: {
        if ("layout/fragment_bottom_sticker_0".equals(tag)) {
          return new FragmentBottomStickerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_bottom_sticker is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTCAST: {
        if ("layout/fragment_cast_0".equals(tag)) {
          return new FragmentCastBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_cast is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTCONTACT: {
        if ("layout/fragment_contact_0".equals(tag)) {
          return new FragmentContactBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_contact is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTCONTACTLIST: {
        if ("layout/fragment_contact_list_0".equals(tag)) {
          return new FragmentContactListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_contact_list is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTCONTENTRECENT: {
        if ("layout/fragment_content_recent_0".equals(tag)) {
          return new FragmentContentRecentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_content_recent is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTFAVORITE: {
        if ("layout/fragment_favorite_0".equals(tag)) {
          return new FragmentFavoriteBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_favorite is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTFOLLOWERS: {
        if ("layout/fragment_followers_0".equals(tag)) {
          return new FragmentFollowersBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_followers is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTFOLLOWING: {
        if ("layout/fragment_following_0".equals(tag)) {
          return new FragmentFollowingBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_following is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTFRIENDS: {
        if ("layout/fragment_friends_0".equals(tag)) {
          return new FragmentFriendsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_friends is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTGROUPMEMBER: {
        if ("layout/fragment_group_member_0".equals(tag)) {
          return new FragmentGroupMemberBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_group_member is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTGROUPVIDEO: {
        if ("layout/fragment_group_video_0".equals(tag)) {
          return new FragmentGroupVideoBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_group_video is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTGROUPS: {
        if ("layout/fragment_groups_0".equals(tag)) {
          return new FragmentGroupsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_groups is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTHOMEHEADER: {
        if ("layout/fragment_home_header_0".equals(tag)) {
          return new FragmentHomeHeaderBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_home_header is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTHOMELIST: {
        if ("layout/fragment_home_list_0".equals(tag)) {
          return new FragmentHomeListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_home_list is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTMANDUHISTORY: {
        if ("layout/fragment_mandu_history_0".equals(tag)) {
          return new FragmentManduHistoryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_mandu_history is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTMYCHANNEL: {
        if ("layout/fragment_my_channel_0".equals(tag)) {
          return new FragmentMyChannelBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_my_channel is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTMYLIVEHISTORY: {
        if ("layout/fragment_my_live_history_0".equals(tag)) {
          return new FragmentMyLiveHistoryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_my_live_history is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTNOTIFICATIONLIVE: {
        if ("layout/fragment_notification_live_0".equals(tag)) {
          return new FragmentNotificationLiveBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_notification_live is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTNOTIFICATIONNOTICE: {
        if ("layout/fragment_notification_notice_0".equals(tag)) {
          return new FragmentNotificationNoticeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_notification_notice is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTNOTIFICATIONVOD: {
        if ("layout/fragment_notification_vod_0".equals(tag)) {
          return new FragmentNotificationVodBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_notification_vod is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTRECENT: {
        if ("layout/fragment_recent_0".equals(tag)) {
          return new FragmentRecentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_recent is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTSEARCHRESULTPAGE: {
        if ("layout/fragment_search_result_page_0".equals(tag)) {
          return new FragmentSearchResultPageBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_search_result_page is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTSTICKERPAGINATION: {
        if ("layout/fragment_sticker_pagination_0".equals(tag)) {
          return new FragmentStickerPaginationBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_sticker_pagination is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTVIDEO: {
        if ("layout/fragment_video_0".equals(tag)) {
          return new FragmentVideoBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_video is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTADS: {
        if ("layout/layout_ads_0".equals(tag)) {
          return new LayoutAdsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_ads is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTBACKHEADER: {
        if ("layout/layout_back_header_0".equals(tag)) {
          return new LayoutBackHeaderBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_back_header is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTBACKHEADERSUBSCRIBE: {
        if ("layout/layout_back_header_subscribe_0".equals(tag)) {
          return new LayoutBackHeaderSubscribeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_back_header_subscribe is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTHEADERLOADING: {
        if ("layout/layout_header_loading_0".equals(tag)) {
          return new LayoutHeaderLoadingBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_header_loading is invalid. Received: " + tag);
      }
      case  LAYOUT_TEST: {
        if ("layout/test_0".equals(tag)) {
          return new TestBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for test is invalid. Received: " + tag);
      }
      case  LAYOUT_VHBOTTOMSHEETOPTIONS: {
        if ("layout/vh_bottom_sheet_options_0".equals(tag)) {
          return new VhBottomSheetOptionsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_bottom_sheet_options is invalid. Received: " + tag);
      }
      case  LAYOUT_VHCASTADDFRIEND: {
        if ("layout/vh_cast_add_friend_0".equals(tag)) {
          return new VhCastAddFriendBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_cast_add_friend is invalid. Received: " + tag);
      }
      case  LAYOUT_VHCASTFOLLOWER: {
        if ("layout/vh_cast_follower_0".equals(tag)) {
          return new VhCastFollowerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_cast_follower is invalid. Received: " + tag);
      }
      case  LAYOUT_VHCASTFRIEND: {
        if ("layout/vh_cast_friend_0".equals(tag)) {
          return new VhCastFriendBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_cast_friend is invalid. Received: " + tag);
      }
      case  LAYOUT_VHCASTGROUP: {
        if ("layout/vh_cast_group_0".equals(tag)) {
          return new VhCastGroupBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_cast_group is invalid. Received: " + tag);
      }
      case  LAYOUT_VHCASTRECENTFOLLOWER: {
        if ("layout/vh_cast_recent_follower_0".equals(tag)) {
          return new VhCastRecentFollowerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_cast_recent_follower is invalid. Received: " + tag);
      }
      case  LAYOUT_VHCASTSEARCHFRIEND: {
        if ("layout/vh_cast_search_friend_0".equals(tag)) {
          return new VhCastSearchFriendBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_cast_search_friend is invalid. Received: " + tag);
      }
      case  LAYOUT_VHCOMMENTBODY: {
        if ("layout/vh_comment_body_0".equals(tag)) {
          return new VhCommentBodyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_comment_body is invalid. Received: " + tag);
      }
      case  LAYOUT_VHCOMMENTNODATA: {
        if ("layout/vh_comment_no_data_0".equals(tag)) {
          return new VhCommentNoDataBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_comment_no_data is invalid. Received: " + tag);
      }
      case  LAYOUT_VHCOMMENTWRITE: {
        if ("layout/vh_comment_write_0".equals(tag)) {
          return new VhCommentWriteBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_comment_write is invalid. Received: " + tag);
      }
      case  LAYOUT_VHCONTACTUS: {
        if ("layout/vh_contact_us_0".equals(tag)) {
          return new VhContactUsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_contact_us is invalid. Received: " + tag);
      }
      case  LAYOUT_VHGROUPADDPEOPLE: {
        if ("layout/vh_group_add_people_0".equals(tag)) {
          return new VhGroupAddPeopleBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_group_add_people is invalid. Received: " + tag);
      }
      case  LAYOUT_VHGROUPMEMBER: {
        if ("layout/vh_group_member_0".equals(tag)) {
          return new VhGroupMemberBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_group_member is invalid. Received: " + tag);
      }
      case  LAYOUT_VHHOMECATEGORY: {
        if ("layout/vh_home_category_0".equals(tag)) {
          return new VhHomeCategoryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_home_category is invalid. Received: " + tag);
      }
      case  LAYOUT_VHHOMELOADMORE: {
        if ("layout/vh_home_load_more_0".equals(tag)) {
          return new VhHomeLoadMoreBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_home_load_more is invalid. Received: " + tag);
      }
      case  LAYOUT_VHHOMEMAINHEADER: {
        if ("layout/vh_home_main_header_0".equals(tag)) {
          return new VhHomeMainHeaderBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_home_main_header is invalid. Received: " + tag);
      }
      case  LAYOUT_VHHOMENODATA: {
        if ("layout/vh_home_no_data_0".equals(tag)) {
          return new VhHomeNoDataBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_home_no_data is invalid. Received: " + tag);
      }
      case  LAYOUT_VHHOMEPOPULARHEADER: {
        if ("layout/vh_home_popular_header_0".equals(tag)) {
          return new VhHomePopularHeaderBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_home_popular_header is invalid. Received: " + tag);
      }
      case  LAYOUT_VHHOMESUBHEADER: {
        if ("layout/vh_home_sub_header_0".equals(tag)) {
          return new VhHomeSubHeaderBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_home_sub_header is invalid. Received: " + tag);
      }
    }
    return null;
  }

  private final ViewDataBinding internalGetViewDataBinding2(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_VHHOMEVIDEO: {
        if ("layout/vh_home_video_0".equals(tag)) {
          return new VhHomeVideoBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_home_video is invalid. Received: " + tag);
      }
      case  LAYOUT_VHLIVECHAT: {
        if ("layout/vh_live_chat_0".equals(tag)) {
          return new VhLiveChatBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_live_chat is invalid. Received: " + tag);
      }
      case  LAYOUT_VHNAVCATEGORY: {
        if ("layout/vh_nav_category_0".equals(tag)) {
          return new VhNavCategoryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_nav_category is invalid. Received: " + tag);
      }
      case  LAYOUT_VHNAVCATEGORYITEM: {
        if ("layout/vh_nav_category_item_0".equals(tag)) {
          return new VhNavCategoryItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_nav_category_item is invalid. Received: " + tag);
      }
      case  LAYOUT_VHNAVMENU: {
        if ("layout/vh_nav_menu_0".equals(tag)) {
          return new VhNavMenuBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_nav_menu is invalid. Received: " + tag);
      }
      case  LAYOUT_VHNAVMENU2: {
        if ("layout/vh_nav_menu_2_0".equals(tag)) {
          return new VhNavMenu2BindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_nav_menu_2 is invalid. Received: " + tag);
      }
      case  LAYOUT_VHNAVPROFILE: {
        if ("layout/vh_nav_profile_0".equals(tag)) {
          return new VhNavProfileBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_nav_profile is invalid. Received: " + tag);
      }
      case  LAYOUT_VHNOTIFICATIONLIVEITEM: {
        if ("layout/vh_notification_live_item_0".equals(tag)) {
          return new VhNotificationLiveItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_notification_live_item is invalid. Received: " + tag);
      }
      case  LAYOUT_VHNOTIFICATIONNOTICEITEM: {
        if ("layout/vh_notification_notice_item_0".equals(tag)) {
          return new VhNotificationNoticeItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_notification_notice_item is invalid. Received: " + tag);
      }
      case  LAYOUT_VHNOTIFICATIONVODITEM: {
        if ("layout/vh_notification_vod_item_0".equals(tag)) {
          return new VhNotificationVodItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_notification_vod_item is invalid. Received: " + tag);
      }
      case  LAYOUT_VHPOPULARITEM: {
        if ("layout/vh_popular_item_0".equals(tag)) {
          return new VhPopularItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_popular_item is invalid. Received: " + tag);
      }
      case  LAYOUT_VHRELATEDVIDEOHEADER: {
        if ("layout/vh_related_video_header_0".equals(tag)) {
          return new VhRelatedVideoHeaderBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_related_video_header is invalid. Received: " + tag);
      }
      case  LAYOUT_VHSEARCHHISTORY: {
        if ("layout/vh_search_history_0".equals(tag)) {
          return new VhSearchHistoryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_search_history is invalid. Received: " + tag);
      }
      case  LAYOUT_VHSHAREANDFRIENDS: {
        if ("layout/vh_share_and_friends_0".equals(tag)) {
          return new VhShareAndFriendsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_share_and_friends is invalid. Received: " + tag);
      }
      case  LAYOUT_VHSTICKERTAB: {
        if ("layout/vh_sticker_tab_0".equals(tag)) {
          return new VhStickerTabBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_sticker_tab is invalid. Received: " + tag);
      }
      case  LAYOUT_VHSTICKERS: {
        if ("layout/vh_stickers_0".equals(tag)) {
          return new VhStickersBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_stickers is invalid. Received: " + tag);
      }
      case  LAYOUT_VHSUBSCRIBE: {
        if ("layout/vh_subscribe_0".equals(tag)) {
          return new VhSubscribeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_subscribe is invalid. Received: " + tag);
      }
      case  LAYOUT_VHVODGALLEYITEM: {
        if ("layout/vh_vod_galley_item_0".equals(tag)) {
          return new VhVodGalleyItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_vod_galley_item is invalid. Received: " + tag);
      }
      case  LAYOUT_VHVODGALLEYSECTION: {
        if ("layout/vh_vod_galley_section_0".equals(tag)) {
          return new VhVodGalleySectionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vh_vod_galley_section is invalid. Received: " + tag);
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      // find which method will have it. -1 is necessary becausefirst id starts with 1;
      int methodIndex = (localizedLayoutId - 1) / 50;
      switch(methodIndex) {
        case 0: {
          return internalGetViewDataBinding0(component, view, localizedLayoutId, tag);
        }
        case 1: {
          return internalGetViewDataBinding1(component, view, localizedLayoutId, tag);
        }
        case 2: {
          return internalGetViewDataBinding2(component, view, localizedLayoutId, tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(3);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "category");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(119);

    static {
      sKeys.put("layout/activity_add_friend_0", notq.dccast.R.layout.activity_add_friend);
      sKeys.put("layout/activity_add_people_0", notq.dccast.R.layout.activity_add_people);
      sKeys.put("layout/activity_contact_us_0", notq.dccast.R.layout.activity_contact_us);
      sKeys.put("layout/activity_contact_us_detail_0", notq.dccast.R.layout.activity_contact_us_detail);
      sKeys.put("layout/activity_create_group_0", notq.dccast.R.layout.activity_create_group);
      sKeys.put("layout/activity_edit_profile_0", notq.dccast.R.layout.activity_edit_profile);
      sKeys.put("layout/activity_favorite_0", notq.dccast.R.layout.activity_favorite);
      sKeys.put("layout/activity_forget_password_0", notq.dccast.R.layout.activity_forget_password);
      sKeys.put("layout/activity_group_detail_0", notq.dccast.R.layout.activity_group_detail);
      sKeys.put("layout/activity_history_information_0", notq.dccast.R.layout.activity_history_information);
      sKeys.put("layout/activity_home_0", notq.dccast.R.layout.activity_home);
      sKeys.put("layout/activity_home_toolbar_0", notq.dccast.R.layout.activity_home_toolbar);
      sKeys.put("layout/activity_live_settings_0", notq.dccast.R.layout.activity_live_settings);
      sKeys.put("layout/activity_live_stream_0", notq.dccast.R.layout.activity_live_stream);
      sKeys.put("layout/activity_login_0", notq.dccast.R.layout.activity_login);
      sKeys.put("layout/activity_mandu_history_web_view_0", notq.dccast.R.layout.activity_mandu_history_web_view);
      sKeys.put("layout/activity_my_channel_0", notq.dccast.R.layout.activity_my_channel);
      sKeys.put("layout/activity_my_content_0", notq.dccast.R.layout.activity_my_content);
      sKeys.put("layout/activity_my_live_history_0", notq.dccast.R.layout.activity_my_live_history);
      sKeys.put("layout/activity_notification_0", notq.dccast.R.layout.activity_notification);
      sKeys.put("layout/activity_notification_settings_0", notq.dccast.R.layout.activity_notification_settings);
      sKeys.put("layout/activity_recent_0", notq.dccast.R.layout.activity_recent);
      sKeys.put("layout/activity_search_0", notq.dccast.R.layout.activity_search);
      sKeys.put("layout/activity_search_followers_0", notq.dccast.R.layout.activity_search_followers);
      sKeys.put("layout/activity_search_friend_0", notq.dccast.R.layout.activity_search_friend);
      sKeys.put("layout/activity_search_groups_0", notq.dccast.R.layout.activity_search_groups);
      sKeys.put("layout/activity_search_toolbar_0", notq.dccast.R.layout.activity_search_toolbar);
      sKeys.put("layout/activity_select_gallery_0", notq.dccast.R.layout.activity_select_gallery);
      sKeys.put("layout/activity_settings_0", notq.dccast.R.layout.activity_settings);
      sKeys.put("layout/activity_sign_up_0", notq.dccast.R.layout.activity_sign_up);
      sKeys.put("layout/activity_splash_0", notq.dccast.R.layout.activity_splash);
      sKeys.put("layout/activity_subscribe_0", notq.dccast.R.layout.activity_subscribe);
      sKeys.put("layout/activity_thumbnail_chooser_0", notq.dccast.R.layout.activity_thumbnail_chooser);
      sKeys.put("layout/activity_trimmer_0", notq.dccast.R.layout.activity_trimmer);
      sKeys.put("layout/activity_upload_vod_0", notq.dccast.R.layout.activity_upload_vod);
      sKeys.put("layout/activity_user_follow_0", notq.dccast.R.layout.activity_user_follow);
      sKeys.put("layout/activity_web_view_0", notq.dccast.R.layout.activity_web_view);
      sKeys.put("layout/dialog_age_confirm_0", notq.dccast.R.layout.dialog_age_confirm);
      sKeys.put("layout/dialog_change_nickname_0", notq.dccast.R.layout.dialog_change_nickname);
      sKeys.put("layout/dialog_mandu_password_0", notq.dccast.R.layout.dialog_mandu_password);
      sKeys.put("layout/dialog_share_0", notq.dccast.R.layout.dialog_share);
      sKeys.put("layout/dialog_video_report_0", notq.dccast.R.layout.dialog_video_report);
      sKeys.put("layout/dialog_video_settings_0", notq.dccast.R.layout.dialog_video_settings);
      sKeys.put("layout/fragment_bottom_add_comment_0", notq.dccast.R.layout.fragment_bottom_add_comment);
      sKeys.put("layout/fragment_bottom_buuz_0", notq.dccast.R.layout.fragment_bottom_buuz);
      sKeys.put("layout/fragment_bottom_comment_0", notq.dccast.R.layout.fragment_bottom_comment);
      sKeys.put("layout/fragment_bottom_live_settings_0", notq.dccast.R.layout.fragment_bottom_live_settings);
      sKeys.put("layout/fragment_bottom_live_title_0", notq.dccast.R.layout.fragment_bottom_live_title);
      sKeys.put("layout/fragment_bottom_lock_0", notq.dccast.R.layout.fragment_bottom_lock);
      sKeys.put("layout/fragment_bottom_options_0", notq.dccast.R.layout.fragment_bottom_options);
      sKeys.put("layout/fragment_bottom_set_lock_0", notq.dccast.R.layout.fragment_bottom_set_lock);
      sKeys.put("layout/fragment_bottom_sort_0", notq.dccast.R.layout.fragment_bottom_sort);
      sKeys.put("layout/fragment_bottom_sticker_0", notq.dccast.R.layout.fragment_bottom_sticker);
      sKeys.put("layout/fragment_cast_0", notq.dccast.R.layout.fragment_cast);
      sKeys.put("layout/fragment_contact_0", notq.dccast.R.layout.fragment_contact);
      sKeys.put("layout/fragment_contact_list_0", notq.dccast.R.layout.fragment_contact_list);
      sKeys.put("layout/fragment_content_recent_0", notq.dccast.R.layout.fragment_content_recent);
      sKeys.put("layout/fragment_favorite_0", notq.dccast.R.layout.fragment_favorite);
      sKeys.put("layout/fragment_followers_0", notq.dccast.R.layout.fragment_followers);
      sKeys.put("layout/fragment_following_0", notq.dccast.R.layout.fragment_following);
      sKeys.put("layout/fragment_friends_0", notq.dccast.R.layout.fragment_friends);
      sKeys.put("layout/fragment_group_member_0", notq.dccast.R.layout.fragment_group_member);
      sKeys.put("layout/fragment_group_video_0", notq.dccast.R.layout.fragment_group_video);
      sKeys.put("layout/fragment_groups_0", notq.dccast.R.layout.fragment_groups);
      sKeys.put("layout/fragment_home_header_0", notq.dccast.R.layout.fragment_home_header);
      sKeys.put("layout/fragment_home_list_0", notq.dccast.R.layout.fragment_home_list);
      sKeys.put("layout/fragment_mandu_history_0", notq.dccast.R.layout.fragment_mandu_history);
      sKeys.put("layout/fragment_my_channel_0", notq.dccast.R.layout.fragment_my_channel);
      sKeys.put("layout/fragment_my_live_history_0", notq.dccast.R.layout.fragment_my_live_history);
      sKeys.put("layout/fragment_notification_live_0", notq.dccast.R.layout.fragment_notification_live);
      sKeys.put("layout/fragment_notification_notice_0", notq.dccast.R.layout.fragment_notification_notice);
      sKeys.put("layout/fragment_notification_vod_0", notq.dccast.R.layout.fragment_notification_vod);
      sKeys.put("layout/fragment_recent_0", notq.dccast.R.layout.fragment_recent);
      sKeys.put("layout/fragment_search_result_page_0", notq.dccast.R.layout.fragment_search_result_page);
      sKeys.put("layout/fragment_sticker_pagination_0", notq.dccast.R.layout.fragment_sticker_pagination);
      sKeys.put("layout/fragment_video_0", notq.dccast.R.layout.fragment_video);
      sKeys.put("layout/layout_ads_0", notq.dccast.R.layout.layout_ads);
      sKeys.put("layout/layout_back_header_0", notq.dccast.R.layout.layout_back_header);
      sKeys.put("layout/layout_back_header_subscribe_0", notq.dccast.R.layout.layout_back_header_subscribe);
      sKeys.put("layout/layout_header_loading_0", notq.dccast.R.layout.layout_header_loading);
      sKeys.put("layout/test_0", notq.dccast.R.layout.test);
      sKeys.put("layout/vh_bottom_sheet_options_0", notq.dccast.R.layout.vh_bottom_sheet_options);
      sKeys.put("layout/vh_cast_add_friend_0", notq.dccast.R.layout.vh_cast_add_friend);
      sKeys.put("layout/vh_cast_follower_0", notq.dccast.R.layout.vh_cast_follower);
      sKeys.put("layout/vh_cast_friend_0", notq.dccast.R.layout.vh_cast_friend);
      sKeys.put("layout/vh_cast_group_0", notq.dccast.R.layout.vh_cast_group);
      sKeys.put("layout/vh_cast_recent_follower_0", notq.dccast.R.layout.vh_cast_recent_follower);
      sKeys.put("layout/vh_cast_search_friend_0", notq.dccast.R.layout.vh_cast_search_friend);
      sKeys.put("layout/vh_comment_body_0", notq.dccast.R.layout.vh_comment_body);
      sKeys.put("layout/vh_comment_no_data_0", notq.dccast.R.layout.vh_comment_no_data);
      sKeys.put("layout/vh_comment_write_0", notq.dccast.R.layout.vh_comment_write);
      sKeys.put("layout/vh_contact_us_0", notq.dccast.R.layout.vh_contact_us);
      sKeys.put("layout/vh_group_add_people_0", notq.dccast.R.layout.vh_group_add_people);
      sKeys.put("layout/vh_group_member_0", notq.dccast.R.layout.vh_group_member);
      sKeys.put("layout/vh_home_category_0", notq.dccast.R.layout.vh_home_category);
      sKeys.put("layout/vh_home_load_more_0", notq.dccast.R.layout.vh_home_load_more);
      sKeys.put("layout/vh_home_main_header_0", notq.dccast.R.layout.vh_home_main_header);
      sKeys.put("layout/vh_home_no_data_0", notq.dccast.R.layout.vh_home_no_data);
      sKeys.put("layout/vh_home_popular_header_0", notq.dccast.R.layout.vh_home_popular_header);
      sKeys.put("layout/vh_home_sub_header_0", notq.dccast.R.layout.vh_home_sub_header);
      sKeys.put("layout/vh_home_video_0", notq.dccast.R.layout.vh_home_video);
      sKeys.put("layout/vh_live_chat_0", notq.dccast.R.layout.vh_live_chat);
      sKeys.put("layout/vh_nav_category_0", notq.dccast.R.layout.vh_nav_category);
      sKeys.put("layout/vh_nav_category_item_0", notq.dccast.R.layout.vh_nav_category_item);
      sKeys.put("layout/vh_nav_menu_0", notq.dccast.R.layout.vh_nav_menu);
      sKeys.put("layout/vh_nav_menu_2_0", notq.dccast.R.layout.vh_nav_menu_2);
      sKeys.put("layout/vh_nav_profile_0", notq.dccast.R.layout.vh_nav_profile);
      sKeys.put("layout/vh_notification_live_item_0", notq.dccast.R.layout.vh_notification_live_item);
      sKeys.put("layout/vh_notification_notice_item_0", notq.dccast.R.layout.vh_notification_notice_item);
      sKeys.put("layout/vh_notification_vod_item_0", notq.dccast.R.layout.vh_notification_vod_item);
      sKeys.put("layout/vh_popular_item_0", notq.dccast.R.layout.vh_popular_item);
      sKeys.put("layout/vh_related_video_header_0", notq.dccast.R.layout.vh_related_video_header);
      sKeys.put("layout/vh_search_history_0", notq.dccast.R.layout.vh_search_history);
      sKeys.put("layout/vh_share_and_friends_0", notq.dccast.R.layout.vh_share_and_friends);
      sKeys.put("layout/vh_sticker_tab_0", notq.dccast.R.layout.vh_sticker_tab);
      sKeys.put("layout/vh_stickers_0", notq.dccast.R.layout.vh_stickers);
      sKeys.put("layout/vh_subscribe_0", notq.dccast.R.layout.vh_subscribe);
      sKeys.put("layout/vh_vod_galley_item_0", notq.dccast.R.layout.vh_vod_galley_item);
      sKeys.put("layout/vh_vod_galley_section_0", notq.dccast.R.layout.vh_vod_galley_section);
    }
  }
}
