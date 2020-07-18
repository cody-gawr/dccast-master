package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentVideoBindingImpl extends FragmentVideoBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(94);
        sIncludes.setIncludes(1, 
            new String[] {"layout_ads"},
            new int[] {2},
            new int[] {notq.dccast.R.layout.layout_ads});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.videoMotionLayout, 3);
        sViewsWithIds.put(R.id.videoViewContainer, 4);
        sViewsWithIds.put(R.id.videoView, 5);
        sViewsWithIds.put(R.id.progress_bar_vod, 6);
        sViewsWithIds.put(R.id.controllers, 7);
        sViewsWithIds.put(R.id.btnPre, 8);
        sViewsWithIds.put(R.id.btnPlayBig, 9);
        sViewsWithIds.put(R.id.btnNext, 10);
        sViewsWithIds.put(R.id.btn_collapse, 11);
        sViewsWithIds.put(R.id.live_screen_title, 12);
        sViewsWithIds.put(R.id.live_18, 13);
        sViewsWithIds.put(R.id.btn_settings, 14);
        sViewsWithIds.put(R.id.btn_close_1, 15);
        sViewsWithIds.put(R.id.start, 16);
        sViewsWithIds.put(R.id.player_seek_bar, 17);
        sViewsWithIds.put(R.id.duration, 18);
        sViewsWithIds.put(R.id.btn_volume_toggle, 19);
        sViewsWithIds.put(R.id.not_muted, 20);
        sViewsWithIds.put(R.id.muted, 21);
        sViewsWithIds.put(R.id.btn_screen_size, 22);
        sViewsWithIds.put(R.id.layout_video_ads, 23);
        sViewsWithIds.put(R.id.videoViewAds, 24);
        sViewsWithIds.put(R.id.skip_ads, 25);
        sViewsWithIds.put(R.id.skip_timer, 26);
        sViewsWithIds.put(R.id.collapsed_divider, 27);
        sViewsWithIds.put(R.id.collapsedControlLayout, 28);
        sViewsWithIds.put(R.id.collapsed_title, 29);
        sViewsWithIds.put(R.id.collapsed_channel, 30);
        sViewsWithIds.put(R.id.btnPlay, 31);
        sViewsWithIds.put(R.id.btnClose, 32);
        sViewsWithIds.put(R.id.content_container, 33);
        sViewsWithIds.put(R.id.content, 34);
        sViewsWithIds.put(R.id.container_recyclerview, 35);
        sViewsWithIds.put(R.id.live_header, 36);
        sViewsWithIds.put(R.id.live_follow_layout, 37);
        sViewsWithIds.put(R.id.iv_live_user, 38);
        sViewsWithIds.put(R.id.tv_live_user_name, 39);
        sViewsWithIds.put(R.id.tv_live_view_count, 40);
        sViewsWithIds.put(R.id.btn_live_follow, 41);
        sViewsWithIds.put(R.id.related_layout, 42);
        sViewsWithIds.put(R.id.related_recyclerview, 43);
        sViewsWithIds.put(R.id.dc_loader, 44);
        sViewsWithIds.put(R.id.app_bar, 45);
        sViewsWithIds.put(R.id.scroll_view, 46);
        sViewsWithIds.put(R.id.btn_chat, 47);
        sViewsWithIds.put(R.id.chat_layout, 48);
        sViewsWithIds.put(R.id.chat_message_field, 49);
        sViewsWithIds.put(R.id.chat_message_sticker, 50);
        sViewsWithIds.put(R.id.chat_message_send, 51);
        sViewsWithIds.put(R.id.btn_buuz, 52);
        sViewsWithIds.put(R.id.btn_fav, 53);
        sViewsWithIds.put(R.id.btn_fav_image, 54);
        sViewsWithIds.put(R.id.btn_fav_image_selected, 55);
        sViewsWithIds.put(R.id.btn_share, 56);
        sViewsWithIds.put(R.id.btn_subscribe, 57);
        sViewsWithIds.put(R.id.subscribe_wrapper, 58);
        sViewsWithIds.put(R.id.subscribe, 59);
        sViewsWithIds.put(R.id.bottom_sheet, 60);
        sViewsWithIds.put(R.id.dim, 61);
        sViewsWithIds.put(R.id.bottom_sheet_add_comment, 62);
        sViewsWithIds.put(R.id.bottom_sheet_sticker, 63);
        sViewsWithIds.put(R.id.bottom_sheet_buuz, 64);
        sViewsWithIds.put(R.id.state_detector, 65);
        sViewsWithIds.put(R.id.live_full_screen_controllers, 66);
        sViewsWithIds.put(R.id.live_full_screen_title, 67);
        sViewsWithIds.put(R.id.live_18_full, 68);
        sViewsWithIds.put(R.id.btn_volume_toggle_full, 69);
        sViewsWithIds.put(R.id.not_muted_full, 70);
        sViewsWithIds.put(R.id.muted_full, 71);
        sViewsWithIds.put(R.id.btn_screen_size_full, 72);
        sViewsWithIds.put(R.id.live_btn_settings, 73);
        sViewsWithIds.put(R.id.live_btn_close_1, 74);
        sViewsWithIds.put(R.id.iv_live_user_full, 75);
        sViewsWithIds.put(R.id.tv_live_user_name_full, 76);
        sViewsWithIds.put(R.id.tv_live_view_count_full, 77);
        sViewsWithIds.put(R.id.btn_live_follow_full, 78);
        sViewsWithIds.put(R.id.live_chat_recyclerview, 79);
        sViewsWithIds.put(R.id.chat_layout_full, 80);
        sViewsWithIds.put(R.id.chat_message_field_full, 81);
        sViewsWithIds.put(R.id.chat_message_sticker_full, 82);
        sViewsWithIds.put(R.id.chat_message_send_full, 83);
        sViewsWithIds.put(R.id.btn_fav_full, 84);
        sViewsWithIds.put(R.id.btn_fav_image_full, 85);
        sViewsWithIds.put(R.id.btn_fav_image_selected_full, 86);
        sViewsWithIds.put(R.id.btn_buuz_full, 87);
        sViewsWithIds.put(R.id.btn_share_full, 88);
        sViewsWithIds.put(R.id.btn_subscribe_full, 89);
        sViewsWithIds.put(R.id.subscribe_wrapper_full, 90);
        sViewsWithIds.put(R.id.subscribe_full, 91);
        sViewsWithIds.put(R.id.bottom_sheet_sticker_full, 92);
        sViewsWithIds.put(R.id.bottom_sheet_buuz_full, 93);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentVideoBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 94, sIncludes, sViewsWithIds));
    }
    private FragmentVideoBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (notq.dccast.databinding.LayoutAdsBinding) bindings[2]
            , (com.google.android.material.appbar.AppBarLayout) bindings[45]
            , (android.widget.FrameLayout) bindings[60]
            , (android.widget.FrameLayout) bindings[62]
            , (android.widget.FrameLayout) bindings[64]
            , (android.widget.FrameLayout) bindings[93]
            , (android.widget.FrameLayout) bindings[63]
            , (android.widget.FrameLayout) bindings[92]
            , (android.widget.FrameLayout) bindings[52]
            , (android.widget.FrameLayout) bindings[87]
            , (android.widget.FrameLayout) bindings[47]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[32]
            , (android.widget.FrameLayout) bindings[15]
            , (android.widget.FrameLayout) bindings[11]
            , (android.widget.FrameLayout) bindings[53]
            , (android.widget.FrameLayout) bindings[84]
            , (android.widget.ImageView) bindings[54]
            , (android.widget.ImageView) bindings[85]
            , (android.widget.ImageView) bindings[55]
            , (android.widget.ImageView) bindings[86]
            , (android.widget.TextView) bindings[41]
            , (android.widget.TextView) bindings[78]
            , (android.widget.ImageView) bindings[10]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[31]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.FrameLayout) bindings[22]
            , (android.widget.FrameLayout) bindings[72]
            , (android.widget.FrameLayout) bindings[14]
            , (android.widget.FrameLayout) bindings[56]
            , (android.widget.FrameLayout) bindings[88]
            , (android.widget.FrameLayout) bindings[57]
            , (android.widget.FrameLayout) bindings[89]
            , (android.widget.FrameLayout) bindings[19]
            , (android.widget.FrameLayout) bindings[69]
            , (android.widget.LinearLayout) bindings[48]
            , (android.widget.LinearLayout) bindings[80]
            , (android.widget.TextView) bindings[49]
            , (android.widget.EditText) bindings[81]
            , (android.widget.FrameLayout) bindings[51]
            , (android.widget.FrameLayout) bindings[83]
            , (android.widget.FrameLayout) bindings[50]
            , (android.widget.FrameLayout) bindings[82]
            , (android.widget.TextView) bindings[30]
            , (android.widget.LinearLayout) bindings[28]
            , (android.view.View) bindings[27]
            , (android.widget.TextView) bindings[29]
            , (android.widget.LinearLayout) bindings[35]
            , (androidx.coordinatorlayout.widget.CoordinatorLayout) bindings[34]
            , (android.widget.FrameLayout) bindings[33]
            , (notq.dccast.custom_view.CustomFrameLayout) bindings[7]
            , (com.airbnb.lottie.LottieAnimationView) bindings[44]
            , (android.view.View) bindings[61]
            , (android.widget.TextView) bindings[18]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[38]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[75]
            , (android.widget.FrameLayout) bindings[23]
            , (androidx.cardview.widget.CardView) bindings[13]
            , (androidx.cardview.widget.CardView) bindings[68]
            , (android.widget.FrameLayout) bindings[74]
            , (android.widget.FrameLayout) bindings[73]
            , (androidx.recyclerview.widget.RecyclerView) bindings[79]
            , (android.widget.LinearLayout) bindings[37]
            , (androidx.coordinatorlayout.widget.CoordinatorLayout) bindings[66]
            , (android.widget.TextView) bindings[67]
            , (android.widget.LinearLayout) bindings[36]
            , (android.widget.TextView) bindings[12]
            , (android.widget.ImageView) bindings[21]
            , (android.widget.ImageView) bindings[71]
            , (android.widget.ImageView) bindings[20]
            , (android.widget.ImageView) bindings[70]
            , (androidx.appcompat.widget.AppCompatSeekBar) bindings[17]
            , (notq.dccast.custom_view.CustomProgressView) bindings[6]
            , (android.widget.FrameLayout) bindings[42]
            , (androidx.recyclerview.widget.RecyclerView) bindings[43]
            , (android.widget.FrameLayout) bindings[0]
            , (android.widget.HorizontalScrollView) bindings[46]
            , (android.widget.LinearLayout) bindings[25]
            , (android.widget.TextView) bindings[26]
            , (android.widget.TextView) bindings[16]
            , (android.view.View) bindings[65]
            , (android.widget.TextView) bindings[59]
            , (android.widget.TextView) bindings[91]
            , (android.widget.FrameLayout) bindings[58]
            , (android.widget.FrameLayout) bindings[90]
            , (android.widget.TextView) bindings[39]
            , (android.widget.TextView) bindings[76]
            , (android.widget.TextView) bindings[40]
            , (android.widget.TextView) bindings[77]
            , (notq.dccast.custom_view.DMotionLayout) bindings[3]
            , (notq.dccast.custom_view.CustomPlayerView) bindings[5]
            , (notq.dccast.custom_view.CustomPlayerView) bindings[24]
            , (androidx.cardview.widget.CardView) bindings[4]
            );
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.rootLayout.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        ads.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (ads.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        ads.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeAds((notq.dccast.databinding.LayoutAdsBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeAds(notq.dccast.databinding.LayoutAdsBinding Ads, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
        executeBindingsOn(ads);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): ads
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}