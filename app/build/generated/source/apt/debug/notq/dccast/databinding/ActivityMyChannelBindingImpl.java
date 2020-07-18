package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMyChannelBindingImpl extends ActivityMyChannelBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(21);
        sIncludes.setIncludes(1, 
            new String[] {"layout_back_header_subscribe"},
            new int[] {2},
            new int[] {notq.dccast.R.layout.layout_back_header_subscribe});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.mainMotionLayout, 3);
        sViewsWithIds.put(R.id.backgroundDim, 4);
        sViewsWithIds.put(R.id.container_for_channel_video, 5);
        sViewsWithIds.put(R.id.app_bar, 6);
        sViewsWithIds.put(R.id.layout_user_info, 7);
        sViewsWithIds.put(R.id.iv_channel_user, 8);
        sViewsWithIds.put(R.id.lbl_channel_user, 9);
        sViewsWithIds.put(R.id.lbl_channel_user_description, 10);
        sViewsWithIds.put(R.id.lbl_subscriber, 11);
        sViewsWithIds.put(R.id.stat_loader, 12);
        sViewsWithIds.put(R.id.lbl_followers, 13);
        sViewsWithIds.put(R.id.lbl_following, 14);
        sViewsWithIds.put(R.id.et_search, 15);
        sViewsWithIds.put(R.id.layout_sort, 16);
        sViewsWithIds.put(R.id.lbl_sort_value, 17);
        sViewsWithIds.put(R.id.view_pager, 18);
        sViewsWithIds.put(R.id.dim, 19);
        sViewsWithIds.put(R.id.bottom_sheet, 20);
    }
    // views
    @NonNull
    private final androidx.coordinatorlayout.widget.CoordinatorLayout mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityMyChannelBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 21, sIncludes, sViewsWithIds));
    }
    private ActivityMyChannelBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (com.google.android.material.appbar.AppBarLayout) bindings[6]
            , (android.view.View) bindings[4]
            , (android.widget.FrameLayout) bindings[20]
            , (android.widget.FrameLayout) bindings[5]
            , (android.view.View) bindings[19]
            , (android.widget.EditText) bindings[15]
            , (notq.dccast.databinding.LayoutBackHeaderSubscribeBinding) bindings[2]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.LinearLayout) bindings[16]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[11]
            , (notq.dccast.custom_view.DMotionLayout) bindings[3]
            , (android.widget.ProgressBar) bindings[12]
            , (androidx.viewpager.widget.ViewPager) bindings[18]
            );
        this.mboundView0 = (androidx.coordinatorlayout.widget.CoordinatorLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
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
        header.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (header.hasPendingBindings()) {
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
        header.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeHeader((notq.dccast.databinding.LayoutBackHeaderSubscribeBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeHeader(notq.dccast.databinding.LayoutBackHeaderSubscribeBinding Header, int fieldId) {
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
        executeBindingsOn(header);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): header
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}