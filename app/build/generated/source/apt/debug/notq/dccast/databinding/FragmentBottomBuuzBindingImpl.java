package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentBottomBuuzBindingImpl extends FragmentBottomBuuzBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.btn_close_bs, 1);
        sViewsWithIds.put(R.id.layout_mandu_count, 2);
        sViewsWithIds.put(R.id.mandu_your_dumpling, 3);
        sViewsWithIds.put(R.id.buuz_point, 4);
        sViewsWithIds.put(R.id.mandu_loading, 5);
        sViewsWithIds.put(R.id.lbl_buuz_buy, 6);
        sViewsWithIds.put(R.id.btn_minus, 7);
        sViewsWithIds.put(R.id.buuz_animation, 8);
        sViewsWithIds.put(R.id.btn_add, 9);
        sViewsWithIds.put(R.id.buuz_count_field, 10);
        sViewsWithIds.put(R.id.btn_send_buuz, 11);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentBottomBuuzBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private FragmentBottomBuuzBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.FrameLayout) bindings[9]
            , (android.widget.FrameLayout) bindings[1]
            , (android.widget.FrameLayout) bindings[7]
            , (android.widget.Button) bindings[11]
            , (com.airbnb.lottie.LottieAnimationView) bindings[8]
            , (android.widget.EditText) bindings[10]
            , (android.widget.TextView) bindings[4]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.TextView) bindings[6]
            , (android.widget.ProgressBar) bindings[5]
            , (android.widget.TextView) bindings[3]
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