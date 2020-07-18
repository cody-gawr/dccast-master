package notq.dccast.screens.navigation_menu.live;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.R;
import notq.dccast.databinding.VhBottomSheetOptionsBinding;
import notq.dccast.model.live.ModelOptions;

public class AdapterBottomSheetOptions
    extends RecyclerView.Adapter<AdapterBottomSheetOptions.ViewHolderBottomSheetOptions> {

  private List<ModelOptions> listOptions;
  private Context context;
  private OnItemSelectedListener adapterListener;
  private int beforeSelectedPosition = -1;

  public AdapterBottomSheetOptions(Context context, OnItemSelectedListener adapterListener) {
    this.context = context;
    this.adapterListener = adapterListener;
    listOptions = new ArrayList<>();
  }

  public void setListOptions(List<ModelOptions> listOptions) {
    this.listOptions = listOptions;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ViewHolderBottomSheetOptions onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);

    VhBottomSheetOptionsBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.vh_bottom_sheet_options, parent, false);

    return new ViewHolderBottomSheetOptions(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolderBottomSheetOptions holder, int position) {
    holder.setData(listOptions.get(position), position);
  }

  @Override
  public int getItemCount() {
    return listOptions.size();
  }

  public ModelOptions getItem(int position) {
    return listOptions.get(position);
  }

  private void updateSelected(int position) {
    if (beforeSelectedPosition != position) {
      if (beforeSelectedPosition >= 0) {
        getItem(beforeSelectedPosition).setSelected(false);
        notifyItemChanged(beforeSelectedPosition);
      }
    }

    getItem(position).setSelected(true);
    notifyItemChanged(position);

    beforeSelectedPosition = position;
    adapterListener.onItemSelected(position);
  }

  public interface OnItemSelectedListener {
    void onItemSelected(int position);
  }

  class ViewHolderBottomSheetOptions extends RecyclerView.ViewHolder
      implements View.OnClickListener {
    private VhBottomSheetOptionsBinding binding;
    private int position;
    private ModelOptions item;

    ViewHolderBottomSheetOptions(@NonNull VhBottomSheetOptionsBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(ModelOptions item, int position) {
      this.position = position;
      this.item = item;
      binding.layoutItem.setOnClickListener(this);

      binding.lblItem.setText(item.getTitle());
      if (item.isSelected()) {
        binding.ivSelected.setColorFilter(
            ContextCompat.getColor(context, R.color.bottom_sheet_selected),
            PorterDuff.Mode.SRC_ATOP);
      } else {
        binding.ivSelected.setColorFilter(
            ContextCompat.getColor(context, R.color.group_live_cancel_border),
            PorterDuff.Mode.SRC_ATOP);
      }

      if (item.isSelected()) {
        beforeSelectedPosition = position;
      }
    }

    @Override
    public void onClick(View view) {
      switch (view.getId()) {
        case R.id.layout_item: {
          updateSelected(position);
          break;
        }
      }
    }
  }
}
