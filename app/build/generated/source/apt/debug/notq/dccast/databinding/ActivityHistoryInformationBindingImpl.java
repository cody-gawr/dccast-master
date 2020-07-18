package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityHistoryInformationBindingImpl extends ActivityHistoryInformationBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(7);
        sIncludes.setIncludes(0, 
            new String[] {"layout_back_header"},
            new int[] {1},
            new int[] {notq.dccast.R.layout.layout_back_header});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.clear_live_history, 2);
        sViewsWithIds.put(R.id.clear_vod_history, 3);
        sViewsWithIds.put(R.id.clear_search_history, 4);
        sViewsWithIds.put(R.id.switch_recent_view, 5);
        sViewsWithIds.put(R.id.switch_recent_history, 6);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityHistoryInformationBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }
    private ActivityHistoryInformationBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (notq.dccast.screens.navigation_menu.settings.SettingsItem) bindings[2]
            , (notq.dccast.screens.navigation_menu.settings.SettingsItem) bindings[4]
            , (notq.dccast.screens.navigation_menu.settings.SettingsItem) bindings[3]
            , (notq.dccast.databinding.LayoutBackHeaderBinding) bindings[1]
            , (notq.dccast.screens.navigation_menu.settings.SettingsItem) bindings[6]
            , (notq.dccast.screens.navigation_menu.settings.SettingsItem) bindings[5]
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