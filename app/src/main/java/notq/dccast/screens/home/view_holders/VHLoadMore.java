package notq.dccast.screens.home.view_holders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import notq.dccast.databinding.VhHomeLoadMoreBinding;

public class VHLoadMore extends RecyclerView.ViewHolder {
  private VhHomeLoadMoreBinding binding;

  public VHLoadMore(VhHomeLoadMoreBinding binding) {
    super(binding.getRoot());
    this.binding = binding;

    FrameLayout.LayoutParams params =
        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);

    binding.progressLayout.setLayoutParams(params);
  }

  public void hide() {
    binding.progressLayout.setVisibility(View.GONE);
  }

  public void show() {
    binding.progressLayout.setVisibility(View.VISIBLE);
  }

  public void setIsHorizontal() {
    FrameLayout.LayoutParams params =
        new FrameLayout.LayoutParams(0,
            ViewGroup.LayoutParams.WRAP_CONTENT);

    binding.progressLayout.setLayoutParams(params);
  }

  public void notifyLoaderStatusChanged(boolean isNeedShowLoadMore) {
    binding.progressBar.setVisibility(isNeedShowLoadMore ? View.VISIBLE : View.GONE);
  }
}

