package notq.dccast.screens.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.R;
import notq.dccast.databinding.VhSearchHistoryBinding;
import notq.dccast.model.ModelSearchHistory;
import notq.dccast.screens.home.interfaces.SearchAdapterListener;

public class AdapterSearchHistory
    extends RecyclerView.Adapter<AdapterSearchHistory.ViewHolderSearchHistory> {

  private List<ModelSearchHistory> modelSearchHistories;
  private Context context;
  private SearchAdapterListener adapterListener;

  public AdapterSearchHistory(Context context, SearchAdapterListener adapterListener) {
    this.context = context;
    this.adapterListener = adapterListener;
    modelSearchHistories = new ArrayList<>();
  }

  public void setModelSearchHistories(List<ModelSearchHistory> modelSearchHistories) {
    this.modelSearchHistories = modelSearchHistories;
  }

  @NonNull @Override
  public ViewHolderSearchHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);

    VhSearchHistoryBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.vh_search_history, parent, false);

    return new ViewHolderSearchHistory(binding);
  }

  @Override public void onBindViewHolder(@NonNull ViewHolderSearchHistory holder, int position) {
    holder.setData(modelSearchHistories.get(position), position);
  }

  @Override public int getItemCount() {
    return modelSearchHistories.size();
  }

  class ViewHolderSearchHistory extends RecyclerView.ViewHolder implements View.OnClickListener {
    private VhSearchHistoryBinding binding;
    private int position;
    private ModelSearchHistory modelSearchHistory;

    ViewHolderSearchHistory(@NonNull VhSearchHistoryBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(ModelSearchHistory modelSearchHistory, int position) {
      this.position = position;
      this.modelSearchHistory = modelSearchHistory;
      binding.keyword.setText(modelSearchHistory.getKeyword());
      binding.keyword.setOnClickListener(this);
      binding.remove.setOnClickListener(this);
    }

    @Override public void onClick(View view) {
      if (view.getId() == R.id.keyword) {
        adapterListener.onKeywordSelected(modelSearchHistory.getKeyword());
      } else if (view.getId() == R.id.remove) {
        adapterListener.onKeywordRemoveClicked(modelSearchHistory);
        notifyItemRemoved(position);
      }
    }
  }
}
