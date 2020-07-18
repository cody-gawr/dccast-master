package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentHomeListBindingImpl extends FragmentHomeListBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(8);
        sIncludes.setIncludes(1, 
            new String[] {"layout_ads"},
            new int[] {2},
            new int[] {notq.dccast.R.layout.layout_ads});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.dc_loader, 3);
        sViewsWithIds.put(R.id.backgroundDim, 4);
        sViewsWithIds.put(R.id.container_for_video, 5);
        sViewsWithIds.put(R.id.refresher, 6);
        sViewsWithIds.put(R.id.recyclerView, 7);
    }
    // views
    @NonNull
    private final android.widget.FrameLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentHomeListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private FragmentHomeListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (notq.dccast.databinding.LayoutAdsBinding) bindings[2]
            , (android.view.View) bindings[4]
            , (android.widget.FrameLayout) bindings[5]
            , (com.airbnb.lottie.LottieAnimationView) bindings[3]
            , (androidx.constraintlayout.motion.widget.MotionLayout) bindings[0]
            , (androidx.recyclerview.widget.RecyclerView) bindings[7]
            , (androidx.swiperefreshlayout.widget.SwipeRefreshLayout) bindings[6]
            );
        this.mainMotionLayout.setTag(null);
        this.mboundView1 = (android.widget.FrameLayout) bindings[1];
        this.mboundView1.setTag(null);
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