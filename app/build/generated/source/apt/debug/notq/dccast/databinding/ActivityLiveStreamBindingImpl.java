package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityLiveStreamBindingImpl extends ActivityLiveStreamBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.camera_preview, 1);
        sViewsWithIds.put(R.id.layoutThumbnail, 2);
        sViewsWithIds.put(R.id.ivMediaThumbnail, 3);
        sViewsWithIds.put(R.id.live_chat_recycler_view, 4);
        sViewsWithIds.put(R.id.layout_settings, 5);
        sViewsWithIds.put(R.id.iv_settings, 6);
        sViewsWithIds.put(R.id.layout_volume, 7);
        sViewsWithIds.put(R.id.iv_volume, 8);
        sViewsWithIds.put(R.id.layout_switch, 9);
        sViewsWithIds.put(R.id.btn_switch, 10);
        sViewsWithIds.put(R.id.layout_flash, 11);
        sViewsWithIds.put(R.id.iv_flash, 12);
        sViewsWithIds.put(R.id.iv_share, 13);
        sViewsWithIds.put(R.id.btn_start_live, 14);
        sViewsWithIds.put(R.id.live_timer, 15);
        sViewsWithIds.put(R.id.memberCount, 16);
        sViewsWithIds.put(R.id.manduCount, 17);
        sViewsWithIds.put(R.id.btn_close, 18);
        sViewsWithIds.put(R.id.live_title, 19);
        sViewsWithIds.put(R.id.container, 20);
        sViewsWithIds.put(R.id.counter_dim, 21);
        sViewsWithIds.put(R.id.orientation_chooser_layout, 22);
        sViewsWithIds.put(R.id.container_portrait, 23);
        sViewsWithIds.put(R.id.img_portrait, 24);
        sViewsWithIds.put(R.id.text_portrait, 25);
        sViewsWithIds.put(R.id.container_landscape, 26);
        sViewsWithIds.put(R.id.img_landscape, 27);
        sViewsWithIds.put(R.id.text_landscape, 28);
        sViewsWithIds.put(R.id.counter, 29);
        sViewsWithIds.put(R.id.dim, 30);
        sViewsWithIds.put(R.id.bottom_sheet, 31);
        sViewsWithIds.put(R.id.bottom_sheet_block, 32);
    }
    // views
    @NonNull
    private final androidx.coordinatorlayout.widget.CoordinatorLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityLiveStreamBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 33, sIncludes, sViewsWithIds));
    }
    private ActivityLiveStreamBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.FrameLayout) bindings[31]
            , (android.widget.FrameLayout) bindings[32]
            , (android.widget.FrameLayout) bindings[18]
            , (android.widget.Button) bindings[14]
            , (android.widget.ImageView) bindings[10]
            , (com.wowza.gocoder.sdk.api.devices.WOWZCameraView) bindings[1]
            , (android.widget.FrameLayout) bindings[20]
            , (android.widget.LinearLayout) bindings[26]
            , (android.widget.LinearLayout) bindings[23]
            , (android.widget.TextView) bindings[29]
            , (android.view.View) bindings[21]
            , (android.view.View) bindings[30]
            , (android.widget.ImageView) bindings[27]
            , (android.widget.ImageView) bindings[24]
            , (android.widget.ImageView) bindings[12]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.FrameLayout) bindings[11]
            , (android.widget.FrameLayout) bindings[5]
            , (android.widget.FrameLayout) bindings[9]
            , (android.widget.FrameLayout) bindings[2]
            , (android.widget.FrameLayout) bindings[7]
            , (androidx.recyclerview.widget.RecyclerView) bindings[4]
            , (notq.dccast.screens.navigation_menu.live.TimerView) bindings[15]
            , (android.widget.TextView) bindings[19]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[16]
            , (android.widget.LinearLayout) bindings[22]
            , (android.widget.TextView) bindings[28]
            , (android.widget.TextView) bindings[25]
            );
        this.mboundView0 = (androidx.coordinatorlayout.widget.CoordinatorLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
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
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}