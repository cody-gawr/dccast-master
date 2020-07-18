package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class VhCommentBodyBindingImpl extends VhCommentBodyBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.profile_image, 1);
        sViewsWithIds.put(R.id.profile_name, 2);
        sViewsWithIds.put(R.id.time, 3);
        sViewsWithIds.put(R.id.comment_body, 4);
        sViewsWithIds.put(R.id.layout_sticker, 5);
        sViewsWithIds.put(R.id.iv_sticker, 6);
        sViewsWithIds.put(R.id.report, 7);
        sViewsWithIds.put(R.id.comment_like_btn, 8);
        sViewsWithIds.put(R.id.comment_like_image, 9);
        sViewsWithIds.put(R.id.like_count, 10);
        sViewsWithIds.put(R.id.comment_dislike_btn, 11);
        sViewsWithIds.put(R.id.comment_dislike_image, 12);
        sViewsWithIds.put(R.id.dislike_count, 13);
        sViewsWithIds.put(R.id.layout_reply, 14);
        sViewsWithIds.put(R.id.reply_count, 15);
        sViewsWithIds.put(R.id.reply_recycler_view, 16);
        sViewsWithIds.put(R.id.view_reply, 17);
        sViewsWithIds.put(R.id.space, 18);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public VhCommentBodyBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private VhCommentBodyBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[4]
            , (android.widget.FrameLayout) bindings[11]
            , (android.widget.ImageView) bindings[12]
            , (android.widget.FrameLayout) bindings[8]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.TextView) bindings[13]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.TextView) bindings[10]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[15]
            , (androidx.recyclerview.widget.RecyclerView) bindings[16]
            , (android.widget.FrameLayout) bindings[7]
            , (android.widget.Space) bindings[18]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[17]
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