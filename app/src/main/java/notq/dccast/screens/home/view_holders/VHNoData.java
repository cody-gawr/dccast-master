package notq.dccast.screens.home.view_holders;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import notq.dccast.databinding.VhHomeNoDataBinding;

public class VHNoData extends RecyclerView.ViewHolder {
  private VhHomeNoDataBinding binding;

  public VHNoData(@NonNull VhHomeNoDataBinding binding) {
    super(binding.getRoot());
    this.binding = binding;
  }

  public void hide() {
    binding.placeholderNoData.setVisibility(View.GONE);
  }

  public void showWithText(String text) {
    binding.lblText.setText(text);
    binding.placeholderNoData.setVisibility(View.VISIBLE);
  }

  public void show() {
    binding.placeholderNoData.setVisibility(View.VISIBLE);
  }

  public void show(String text) {
    binding.lblText.setText(text);
    binding.placeholderNoData.setVisibility(View.VISIBLE);
  }

}
