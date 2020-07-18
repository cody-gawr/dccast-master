package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentFollowersBindingImpl extends FragmentFollowersBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.dc_loader, 1);
        sViewsWithIds.put(R.id.backgroundDim, 2);
        sViewsWithIds.put(R.id.container_for_cast_list_video, 3);
        sViewsWithIds.put(R.id.refresher, 4);
        sViewsWithIds.put(R.id.layout_my_live_history, 5);
        sViewsWithIds.put(R.id.lbl_my_live_history, 6);
        sViewsWithIds.put(R.id.recyclerViewMyLive, 7);
        sViewsWithIds.put(R.id.layout_item, 8);
        sViewsWithIds.put(R.id.lbl_follower_count, 9);
        sViewsWithIds.put(R.id.recyclerView, 10);
        sViewsWithIds.put(R.id.layout_no_data, 11);
        sViewsWithIds.put(R.id.no_data, 12);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentFollowersBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }
    private FragmentFollowersBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.view.View) bindings[2]
            , (android.widget.FrameLayout) bindings[3]
            , (com.airbnb.lottie.LottieAnimationView) bindings[1]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[6]
            , (androidx.constraintlayout.motion.widget.MotionLayout) bindings[0]
            , (android.widget.TextView) bindings[12]
            , (com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView) bindings[10]
            , (androidx.recyclerview.widget.RecyclerView) bindings[7]
            , (androidx.swiperefreshlayout.widget.SwipeRefreshLayout) bindings[4]
            );
        this.mainMotionLayout.setTag(null);
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