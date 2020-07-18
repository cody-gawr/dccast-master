package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityLiveSettingsBindingImpl extends ActivityLiveSettingsBinding  {

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
        sViewsWithIds.put(R.id.item_live_title, 3);
        sViewsWithIds.put(R.id.title, 4);
        sViewsWithIds.put(R.id.item_live_distribute, 5);
        sViewsWithIds.put(R.id.item_live_type, 6);
        sViewsWithIds.put(R.id.item_broadcast_orientation, 7);
        sViewsWithIds.put(R.id.item_category_setting, 8);
        sViewsWithIds.put(R.id.item_set_lock, 9);
        sViewsWithIds.put(R.id.item_broadcast_quality, 10);
        sViewsWithIds.put(R.id.item_restricted, 11);
        sViewsWithIds.put(R.id.item_chat_lock, 12);
        sViewsWithIds.put(R.id.item_live_lock, 13);
        sViewsWithIds.put(R.id.start_live_btn, 14);
        sViewsWithIds.put(R.id.dim, 15);
        sViewsWithIds.put(R.id.bottom_sheet, 16);
    }
    // views
    @NonNull
    private final androidx.coordinatorlayout.widget.CoordinatorLayout mboundView0;
    @NonNull
    private final com.google.android.material.appbar.AppBarLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityLiveSettingsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private ActivityLiveSettingsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.FrameLayout) bindings[16]
            , (android.view.View) bindings[15]
            , (notq.dccast.databinding.LayoutBackHeaderBinding) bindings[2]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[7]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[10]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[8]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[12]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[5]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[13]
            , (android.widget.LinearLayout) bindings[3]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[6]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[11]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[9]
            , (android.widget.Button) bindings[14]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[4]
            );
        this.mboundView0 = (androidx.coordinatorlayout.widget.CoordinatorLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (com.google.android.material.appbar.AppBarLayout) bindings[1];
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