package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class VhNavMenuBindingImpl extends VhNavMenuBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.item_home_container, 1);
        sViewsWithIds.put(R.id.item_home, 2);
        sViewsWithIds.put(R.id.img_home, 3);
        sViewsWithIds.put(R.id.img_home_selected, 4);
        sViewsWithIds.put(R.id.home_text, 5);
        sViewsWithIds.put(R.id.item_my_content_container, 6);
        sViewsWithIds.put(R.id.item_my_content, 7);
        sViewsWithIds.put(R.id.img_my_content, 8);
        sViewsWithIds.put(R.id.img_my_content_selected, 9);
        sViewsWithIds.put(R.id.my_content_text, 10);
        sViewsWithIds.put(R.id.item_favorite_container, 11);
        sViewsWithIds.put(R.id.item_favorite, 12);
        sViewsWithIds.put(R.id.img_favorite, 13);
        sViewsWithIds.put(R.id.img_favorite_selected, 14);
        sViewsWithIds.put(R.id.favorite_text, 15);
        sViewsWithIds.put(R.id.item_cast_container, 16);
        sViewsWithIds.put(R.id.item_cast, 17);
        sViewsWithIds.put(R.id.img_cast, 18);
        sViewsWithIds.put(R.id.img_cast_selected, 19);
        sViewsWithIds.put(R.id.cast_list_text, 20);
    }
    // views
    @NonNull
    private final androidx.cardview.widget.CardView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public VhNavMenuBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 21, sIncludes, sViewsWithIds));
    }
    private VhNavMenuBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[5]
            , (android.widget.ImageView) bindings[18]
            , (android.widget.ImageView) bindings[19]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.ImageView) bindings[14]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.LinearLayout) bindings[17]
            , (android.widget.FrameLayout) bindings[16]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.FrameLayout) bindings[11]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.FrameLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.FrameLayout) bindings[6]
            , (android.widget.TextView) bindings[10]
            );
        this.mboundView0 = (androidx.cardview.widget.CardView) bindings[0];
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