package notq.dccast.screens.home.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.CommentAPIInterface;
import notq.dccast.databinding.VhStickerTabBinding;
import notq.dccast.model.comment.ModelTab;
import notq.dccast.screens.home.interfaces.StickerTabListener;
import notq.dccast.util.Util;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AdapterStickerTabs
    extends RecyclerView.Adapter<AdapterStickerTabs.ViewHolderStickerTab> {

  private List<ModelTab> tabs = new ArrayList<>();
  private Context context;

  private StickerTabListener adapterListener;

  private int selectedIndex = 0;

  public AdapterStickerTabs(Context context, StickerTabListener adapterListener) {
    this.context = context;
    this.adapterListener = adapterListener;
    tabs = new ArrayList<>();
  }

  public void setTabs(List<ModelTab> tabs) {
    this.tabs = tabs;
    notifyDataSetChanged();
  }

  public void updateSelected(int position) {
    if (position == selectedIndex) {
      return;
    }

    tabs.get(selectedIndex).setSelected(false);
    notifyItemChanged(selectedIndex);

    tabs.get(position).setSelected(true);
    notifyItemChanged(position);

    selectedIndex = position;
  }

  public void updateTab(ModelTab item, int position) {
    tabs.set(position, item);
    notifyItemChanged(position);
  }

  public void addTab(ModelTab item) {
    tabs.add(item);
    notifyItemInserted(getItemCount() - 1);
  }

  @NonNull
  @Override
  public ViewHolderStickerTab onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    VhStickerTabBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.vh_sticker_tab, parent, false);
    return new ViewHolderStickerTab(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolderStickerTab holder, int position) {
    holder.setData(tabs.get(position), position);
  }

  public ModelTab getItem(int position) {
    return tabs.get(position);
  }

  @Override
  public int getItemCount() {
    return tabs.size();
  }

  class ViewHolderStickerTab extends RecyclerView.ViewHolder implements View.OnClickListener {
    private VhStickerTabBinding binding;
    private int position;
    private ModelTab item;

    ViewHolderStickerTab(@NonNull VhStickerTabBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(ModelTab item, int position) {
      this.position = position;
      this.item = item;
      binding.layoutItem.setOnClickListener(this);

      if (item.isSelected()) {
        binding.layoutItem.setAlpha(1f);
      } else {
        binding.layoutItem.setAlpha(0.3f);
      }

      binding.layoutLoad.setVisibility(View.VISIBLE);
      Glide.with(context).load(Util.getValidateUrl(item.getImg())).listener(new RequestListener<Drawable>() {
        @Override public boolean onLoadFailed(@Nullable GlideException e, Object model,
            Target<Drawable> target, boolean isFirstResource) {
          binding.layoutLoad.setVisibility(View.GONE);
          return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
            DataSource dataSource, boolean isFirstResource) {
          binding.layoutLoad.setVisibility(View.GONE);
          return false;
        }
      }).into(binding.ivStickerTab);
    }

    @Override
    public void onClick(View view) {
      switch (view.getId()) {
        case R.id.layout_item: {
          adapterListener.onStickerTabSelected(position);
          break;
        }
      }
    }
  }
}
