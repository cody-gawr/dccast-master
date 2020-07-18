package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityGroupDetailBindingImpl extends ActivityGroupDetailBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(17);
        sIncludes.setIncludes(1, 
            new String[] {"layout_back_header"},
            new int[] {2},
            new int[] {notq.dccast.R.layout.layout_back_header});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.mainMotionLayout, 3);
        sViewsWithIds.put(R.id.backgroundDim, 4);
        sViewsWithIds.put(R.id.container_for_group_video, 5);
        sViewsWithIds.put(R.id.layout_header, 6);
        sViewsWithIds.put(R.id.layout_start_live, 7);
        sViewsWithIds.put(R.id.layout_upload_vod, 8);
        sViewsWithIds.put(R.id.iv_group_profile_img, 9);
        sViewsWithIds.put(R.id.description_layout, 10);
        sViewsWithIds.put(R.id.lbl_group_name, 11);
        sViewsWithIds.put(R.id.lbl_group_description, 12);
        sViewsWithIds.put(R.id.tab_layout, 13);
        sViewsWithIds.put(R.id.view_pager, 14);
        sViewsWithIds.put(R.id.dim, 15);
        sViewsWithIds.put(R.id.bottom_sheet, 16);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityGroupDetailBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private ActivityGroupDetailBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (com.google.android.material.appbar.AppBarLayout) bindings[1]
            , (android.view.View) bindings[4]
            , (android.widget.FrameLayout) bindings[16]
            , (android.widget.FrameLayout) bindings[5]
            , (android.widget.LinearLayout) bindings[10]
            , (android.view.View) bindings[15]
            , (notq.dccast.databinding.LayoutBackHeaderBinding) bindings[2]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[9]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[11]
            , (notq.dccast.custom_view.DMotionLayout) bindings[3]
            , (com.google.android.material.tabs.TabLayout) bindings[13]
            , (androidx.viewpager.widget.ViewPager) bindings[14]
            );
        this.appBar.setTag(null);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
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
                return onChangeHeader((notq.dccast.databinding.LayoutBackHeaderBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeHeader(notq.dccast.databinding.LayoutBackHeaderBinding Header, int fieldId) {
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