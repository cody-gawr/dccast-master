package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DialogVideoReportBindingImpl extends DialogVideoReportBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.report_title, 1);
        sViewsWithIds.put(R.id.report_types, 2);
        sViewsWithIds.put(R.id.report_1, 3);
        sViewsWithIds.put(R.id.report_2, 4);
        sViewsWithIds.put(R.id.report_3, 5);
        sViewsWithIds.put(R.id.report_4, 6);
        sViewsWithIds.put(R.id.report_field, 7);
        sViewsWithIds.put(R.id.cancel_report, 8);
        sViewsWithIds.put(R.id.send_report, 9);
    }
    // views
    @NonNull
    private final androidx.core.widget.NestedScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DialogVideoReportBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private DialogVideoReportBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[8]
            , (androidx.appcompat.widget.AppCompatRadioButton) bindings[3]
            , (androidx.appcompat.widget.AppCompatRadioButton) bindings[4]
            , (androidx.appcompat.widget.AppCompatRadioButton) bindings[5]
            , (androidx.appcompat.widget.AppCompatRadioButton) bindings[6]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[7]
            , (android.widget.TextView) bindings[1]
            , (android.widget.RadioGroup) bindings[2]
            , (android.widget.Button) bindings[9]
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