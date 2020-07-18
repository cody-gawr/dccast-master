package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentBottomLiveSettingsBindingImpl extends FragmentBottomLiveSettingsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.btn_settings_close, 1);
        sViewsWithIds.put(R.id.item_live_distribute, 2);
        sViewsWithIds.put(R.id.item_category_setting, 3);
        sViewsWithIds.put(R.id.item_set_lock, 4);
        sViewsWithIds.put(R.id.item_broadcast_quality, 5);
        sViewsWithIds.put(R.id.item_restricted, 6);
        sViewsWithIds.put(R.id.item_chat_lock, 7);
        sViewsWithIds.put(R.id.item_live_lock, 8);
        sViewsWithIds.put(R.id.dim, 9);
        sViewsWithIds.put(R.id.bottom_sheet, 10);
    }
    // views
    @NonNull
    private final androidx.coordinatorlayout.widget.CoordinatorLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentBottomLiveSettingsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private FragmentBottomLiveSettingsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.FrameLayout) bindings[10]
            , (android.widget.FrameLayout) bindings[1]
            , (android.view.View) bindings[9]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[5]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[3]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[7]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[2]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[8]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[6]
            , (notq.dccast.screens.navigation_menu.live.LiveSettingsItem) bindings[4]
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