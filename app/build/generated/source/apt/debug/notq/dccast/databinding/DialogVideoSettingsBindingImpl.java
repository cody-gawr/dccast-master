package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DialogVideoSettingsBindingImpl extends DialogVideoSettingsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.resolution_title, 1);
        sViewsWithIds.put(R.id.resolution_group, 2);
        sViewsWithIds.put(R.id.resolution_0, 3);
        sViewsWithIds.put(R.id.resolution_1, 4);
        sViewsWithIds.put(R.id.resolution_2, 5);
        sViewsWithIds.put(R.id.resolution_3, 6);
        sViewsWithIds.put(R.id.resolution_4, 7);
        sViewsWithIds.put(R.id.report, 8);
        sViewsWithIds.put(R.id.pop_up, 9);
        sViewsWithIds.put(R.id.view_as_radio, 10);
        sViewsWithIds.put(R.id.view_as_pop_up, 11);
        sViewsWithIds.put(R.id.change_nickname, 12);
    }
    // views
    @NonNull
    private final androidx.core.widget.NestedScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DialogVideoSettingsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }
    private DialogVideoSettingsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.TextView) bindings[8]
            , (com.google.android.material.radiobutton.MaterialRadioButton) bindings[3]
            , (com.google.android.material.radiobutton.MaterialRadioButton) bindings[4]
            , (com.google.android.material.radiobutton.MaterialRadioButton) bindings[5]
            , (com.google.android.material.radiobutton.MaterialRadioButton) bindings[6]
            , (com.google.android.material.radiobutton.MaterialRadioButton) bindings[7]
            , (android.widget.RadioGroup) bindings[2]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[10]
            );
        this.mboundView0 = (androidx.core.widget.NestedScrollView) bindings[0];
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