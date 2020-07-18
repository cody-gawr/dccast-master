package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentFollowingBindingImpl extends FragmentFollowingBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.dc_loader, 1);
        sViewsWithIds.put(R.id.backgroundDim, 2);
        sViewsWithIds.put(R.id.container_for_following_video, 3);
        sViewsWithIds.put(R.id.refresher, 4);
        sViewsWithIds.put(R.id.layout_recent, 5);
        sViewsWithIds.put(R.id.lbl_following_count, 6);
        sViewsWithIds.put(R.id.recyclerViewRecent, 7);
        sViewsWithIds.put(R.id.layout_item, 8);
        sViewsWithIds.put(R.id.lbl_unfollowing_count, 9);
        sViewsWithIds.put(R.id.recyclerView, 10);
        sViewsWithIds.put(R.id.layout_no_data, 11);
        sViewsWithIds.put(R.id.dc_no_data, 12);
        sViewsWithIds.put(R.id.layout_fab, 13);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentFollowingBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }
    private FragmentFollowingBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.view.View) bindings[2]
            , (android.widget.FrameLayout) bindings[3]
            , (com.airbnb.lottie.LottieAnimationView) bindings[1]
            , (com.airbnb.lottie.LottieAnimationView) bindings[12]
            , (androidx.cardview.widget.CardView) bindings[13]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[9]
            , (androidx.constraintlayout.motion.widget.MotionLayout) bindings[0]
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