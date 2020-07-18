package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class VhHomeVideoBindingImpl extends VhHomeVideoBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.live_layout, 1);
        sViewsWithIds.put(R.id.live_thumbnail, 2);
        sViewsWithIds.put(R.id.live_status, 3);
        sViewsWithIds.put(R.id.live_18, 4);
        sViewsWithIds.put(R.id.vod_lock_inlive, 5);
        sViewsWithIds.put(R.id.live_crown, 6);
        sViewsWithIds.put(R.id.live_duration_container, 7);
        sViewsWithIds.put(R.id.live_duration, 8);
        sViewsWithIds.put(R.id.live_horizontal_title, 9);
        sViewsWithIds.put(R.id.live_content, 10);
        sViewsWithIds.put(R.id.live_title, 11);
        sViewsWithIds.put(R.id.live_category, 12);
        sViewsWithIds.put(R.id.live_channel, 13);
        sViewsWithIds.put(R.id.btn_live_more, 14);
        sViewsWithIds.put(R.id.vod_layout, 15);
        sViewsWithIds.put(R.id.vod_thumbnail, 16);
        sViewsWithIds.put(R.id.vod_duration_container, 17);
        sViewsWithIds.put(R.id.vod_duration, 18);
        sViewsWithIds.put(R.id.vod_18, 19);
        sViewsWithIds.put(R.id.vod_lock, 20);
        sViewsWithIds.put(R.id.vod_crown, 21);
        sViewsWithIds.put(R.id.vod_progress, 22);
        sViewsWithIds.put(R.id.vod_title, 23);
        sViewsWithIds.put(R.id.vod_category, 24);
        sViewsWithIds.put(R.id.vod_channel, 25);
        sViewsWithIds.put(R.id.vod_view_count, 26);
        sViewsWithIds.put(R.id.btn_vod_more, 27);
        sViewsWithIds.put(R.id.layout_load, 28);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public VhHomeVideoBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 29, sIncludes, sViewsWithIds));
    }
    private VhHomeVideoBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.FrameLayout) bindings[14]
            , (android.widget.FrameLayout) bindings[27]
            , (android.widget.FrameLayout) bindings[28]
            , (androidx.cardview.widget.CardView) bindings[4]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[13]
            , (android.widget.LinearLayout) bindings[10]
            , (androidx.cardview.widget.CardView) bindings[6]
            , (android.widget.TextView) bindings[8]
            , (android.widget.FrameLayout) bindings[7]
            , (android.widget.TextView) bindings[9]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.TextView) bindings[3]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.TextView) bindings[11]
            , (android.widget.FrameLayout) bindings[0]
            , (androidx.cardview.widget.CardView) bindings[19]
            , (android.widget.TextView) bindings[24]
            , (android.widget.TextView) bindings[25]
            , (androidx.cardview.widget.CardView) bindings[21]
            , (android.widget.TextView) bindings[18]
            , (android.widget.FrameLayout) bindings[17]
            , (android.widget.LinearLayout) bindings[15]
            , (androidx.cardview.widget.CardView) bindings[20]
            , (androidx.cardview.widget.CardView) bindings[5]
            , (android.widget.ProgressBar) bindings[22]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.TextView) bindings[23]
            , (android.widget.TextView) bindings[26]
            );
        this.rootLayout.setTag(null);
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