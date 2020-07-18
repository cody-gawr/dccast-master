package notq.dccast.databinding;
import notq.dccast.R;
import notq.dccast.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentBottomCommentBindingImpl extends FragmentBottomCommentBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.total_comments, 1);
        sViewsWithIds.put(R.id.btn_refresh, 2);
        sViewsWithIds.put(R.id.btn_comment_close, 3);
        sViewsWithIds.put(R.id.comments_recycler_view, 4);
        sViewsWithIds.put(R.id.progress_bar_vod, 5);
        sViewsWithIds.put(R.id.reply_bottom_sheet, 6);
        sViewsWithIds.put(R.id.total_replies, 7);
        sViewsWithIds.put(R.id.btn_reply_close, 8);
        sViewsWithIds.put(R.id.reply_recycler_view, 9);
        sViewsWithIds.put(R.id.dim, 10);
        sViewsWithIds.put(R.id.report_bottom_sheet, 11);
        sViewsWithIds.put(R.id.btn_reply, 12);
        sViewsWithIds.put(R.id.btn_edit, 13);
        sViewsWithIds.put(R.id.btn_report, 14);
        sViewsWithIds.put(R.id.btn_delete, 15);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentBottomCommentBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }
    private FragmentBottomCommentBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.FrameLayout) bindings[3]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[13]
            , (android.widget.FrameLayout) bindings[2]
            , (android.widget.TextView) bindings[12]
            , (android.widget.FrameLayout) bindings[8]
            , (android.widget.TextView) bindings[14]
            , (androidx.recyclerview.widget.RecyclerView) bindings[4]
            , (android.view.View) bindings[10]
            , (androidx.coordinatorlayout.widget.CoordinatorLayout) bindings[0]
            , (android.widget.ProgressBar) bindings[5]
            , (android.widget.FrameLayout) bindings[6]
            , (androidx.recyclerview.widget.RecyclerView) bindings[9]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[7]
            );
        this.mainLayout.setTag(null);
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