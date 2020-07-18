package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentMyChannelBindingImpl extends FragmentMyChannelBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.refresher, 1);
        sViewsWithIds.put(R.id.layout_live, 2);
        sViewsWithIds.put(R.id.live_video, 3);
        sViewsWithIds.put(R.id.container, 4);
        sViewsWithIds.put(R.id.thumbnail, 5);
        sViewsWithIds.put(R.id.status_container, 6);
        sViewsWithIds.put(R.id.status, 7);
        sViewsWithIds.put(R.id.live_title, 8);
        sViewsWithIds.put(R.id.profile_image, 9);
        sViewsWithIds.put(R.id.profile_name, 10);
        sViewsWithIds.put(R.id.lbl_no_video, 11);
        sViewsWithIds.put(R.id.lbl_vod_count, 12);
        sViewsWithIds.put(R.id.recyclerView, 13);
        sViewsWithIds.put(R.id.layout_no_data, 14);
        sViewsWithIds.put(R.id.no_data, 15);
        sViewsWithIds.put(R.id.dc_loader, 16);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentMyChannelBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private FragmentMyChannelBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.FrameLayout) bindings[4]
            , (com.airbnb.lottie.LottieAnimationView) bindings[16]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[8]
            , (android.widget.FrameLayout) bindings[3]
            , (android.widget.TextView) bindings[15]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.TextView) bindings[10]
            , (androidx.recyclerview.widget.RecyclerView) bindings[13]
            , (androidx.swiperefreshlayout.widget.SwipeRefreshLayout) bindings[1]
            , (android.widget.TextView) bindings[7]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.ImageView) bindings[5]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
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