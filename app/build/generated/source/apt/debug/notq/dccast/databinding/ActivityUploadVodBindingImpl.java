package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityUploadVodBindingImpl extends ActivityUploadVodBinding  {

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
        sViewsWithIds.put(R.id.layout_title, 3);
        sViewsWithIds.put(R.id.title_input_layout, 4);
        sViewsWithIds.put(R.id.title_input_text, 5);
        sViewsWithIds.put(R.id.lbl_character_count, 6);
        sViewsWithIds.put(R.id.description_input_layout, 7);
        sViewsWithIds.put(R.id.description_input_text, 8);
        sViewsWithIds.put(R.id.thumbnail, 9);
        sViewsWithIds.put(R.id.drop_down_layout, 10);
        sViewsWithIds.put(R.id.spinner_category, 11);
        sViewsWithIds.put(R.id.spinner_share, 12);
        sViewsWithIds.put(R.id.layout_gallery, 13);
        sViewsWithIds.put(R.id.btn_select_gallery, 14);
        sViewsWithIds.put(R.id.gallery, 15);
        sViewsWithIds.put(R.id.post_btn, 16);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final com.google.android.material.appbar.AppBarLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityUploadVodBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private ActivityUploadVodBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.LinearLayout) bindings[14]
            , (com.google.android.material.textfield.TextInputLayout) bindings[7]
            , (com.google.android.material.textfield.TextInputEditText) bindings[8]
            , (android.widget.LinearLayout) bindings[10]
            , (android.widget.TextView) bindings[15]
            , (notq.dccast.databinding.LayoutBackHeaderBinding) bindings[2]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.TextView) bindings[6]
            , (android.widget.Button) bindings[16]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[11]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[12]
            , (android.widget.ImageView) bindings[9]
            , (com.google.android.material.textfield.TextInputLayout) bindings[4]
            , (com.google.android.material.textfield.TextInputEditText) bindings[5]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
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