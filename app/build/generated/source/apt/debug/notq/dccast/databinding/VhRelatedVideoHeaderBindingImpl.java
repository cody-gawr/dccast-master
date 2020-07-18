package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class VhRelatedVideoHeaderBindingImpl extends VhRelatedVideoHeaderBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.title, 1);
        sViewsWithIds.put(R.id.view_count, 2);
        sViewsWithIds.put(R.id.btn_expand_description, 3);
        sViewsWithIds.put(R.id.arrow, 4);
        sViewsWithIds.put(R.id.profile_image, 5);
        sViewsWithIds.put(R.id.item_crown, 6);
        sViewsWithIds.put(R.id.profile_name, 7);
        sViewsWithIds.put(R.id.lbl_follow, 8);
        sViewsWithIds.put(R.id.btn_like, 9);
        sViewsWithIds.put(R.id.btn_like_image, 10);
        sViewsWithIds.put(R.id.like_count, 11);
        sViewsWithIds.put(R.id.btn_dislike, 12);
        sViewsWithIds.put(R.id.btn_dislike_image, 13);
        sViewsWithIds.put(R.id.dislike_count, 14);
        sViewsWithIds.put(R.id.description_container, 15);
        sViewsWithIds.put(R.id.category, 16);
        sViewsWithIds.put(R.id.date, 17);
        sViewsWithIds.put(R.id.description, 18);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public VhRelatedVideoHeaderBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private VhRelatedVideoHeaderBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[4]
            , (android.widget.FrameLayout) bindings[12]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.FrameLayout) bindings[3]
            , (android.widget.FrameLayout) bindings[9]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[18]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.TextView) bindings[14]
            , (androidx.cardview.widget.CardView) bindings[6]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[11]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[5]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[2]
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