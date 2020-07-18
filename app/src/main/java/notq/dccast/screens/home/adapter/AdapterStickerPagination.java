package notq.dccast.screens.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import notq.dccast.R;
import notq.dccast.databinding.VhStickersBinding;
import notq.dccast.model.comment.ModelSticker;
import notq.dccast.screens.home.interfaces.StickerListener;
import notq.dccast.util.Util;
import timber.log.Timber;

public class AdapterStickerPagination
    extends RecyclerView.Adapter<AdapterStickerPagination.ViewHolderSticker> {

  private List<ModelSticker> stickers = new ArrayList<>();
  private Context context;
  private StickerListener adapterListener;

  private boolean isEdit = false;

  public AdapterStickerPagination(Context context, boolean isEdit,
      StickerListener adapterListener) {
    this.context = context;
    this.isEdit = isEdit;
    this.adapterListener = adapterListener;
    stickers = new ArrayList<>();
  }

  public void setStickers(List<ModelSticker> stickers) {
    this.stickers = stickers;
    notifyDataSetChanged();
  }

  public void removeStickers() {
    this.stickers = new ArrayList<>();
    notifyDataSetChanged();
  }

  public void updateSticker(ModelSticker item, int position) {
    stickers.set(position, item);
    notifyItemChanged(position);
  }

  public void addSticker(ModelSticker item) {
    stickers.add(item);
    notifyItemInserted(getItemCount() - 1);
  }

  @NonNull
  @Override
  public ViewHolderSticker onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    VhStickersBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.vh_stickers, parent, false);
    return new ViewHolderSticker(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolderSticker holder, int position) {
    holder.setData(stickers.get(position), position);
  }

  public ModelSticker getItem(int position) {
    return stickers.get(position);
  }

  @Override
  public int getItemCount() {
    return stickers == null ? 0 : stickers.size();
  }

  class ViewHolderSticker extends RecyclerView.ViewHolder {
    private VhStickersBinding binding;
    private boolean isButtonLongPressed = false;
    private boolean isClicked = false;
    private boolean isSent = false;
    private Timer timer;

    ViewHolderSticker(@NonNull VhStickersBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    @SuppressLint("ClickableViewAccessibility") void setData(ModelSticker item, int position) {
      binding.layoutItem.setOnLongClickListener(v -> {
        if (isButtonLongPressed) {
          return true;
        }
        isButtonLongPressed = true;
        adapterListener.onStickerLongClick(item);
        binding.layoutItem.getParent().requestDisallowInterceptTouchEvent(true);
        return true;
      });
      binding.layoutItem.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (isSent) {
            return;
          }

          isSent = true;
          adapterListener.onStickerSelected(isEdit, item);
          Timber.e("onClick");

          new Handler().postDelayed(new Runnable() {
            @Override public void run() {
              isSent = false;
            }
          }, 200);
        }
      });
      binding.layoutItem.setOnTouchListener(new View.OnTouchListener() {
        @Override public boolean onTouch(View view, MotionEvent event) {
          if (event.getAction() == MotionEvent.ACTION_MOVE) {
            isClicked = false;
            return false;
          }
          if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isClicked = true;
          }
          view.getParent().requestDisallowInterceptTouchEvent(false);
          view.onTouchEvent(event);
          if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isButtonLongPressed) {
              adapterListener.onStickerLongClickRelease();
              isButtonLongPressed = false;
            } else {
              if (isClicked) {
                Timber.e("onClick with onTouch");
                //  isClicked = false;
                //  adapterListener.onStickerSelected(isEdit, item);
                return true;
              }
            }
          }
          return false;
        }
      });

      binding.layoutLoad.setVisibility(View.GONE);
      Glide.with(context).load(Util.getValidateUrl(item.getImg())).into(binding.ivStickerTab);
    }
  }
}
