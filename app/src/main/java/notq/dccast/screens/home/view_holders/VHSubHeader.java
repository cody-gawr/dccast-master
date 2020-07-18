package notq.dccast.screens.home.view_holders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import notq.dccast.R;
import notq.dccast.databinding.VhHomeSubHeaderBinding;

public class VHSubHeader extends RecyclerView.ViewHolder {
  private VhHomeSubHeaderBinding binding;
  private View.OnClickListener onClickListener;
  private Context context;

  public VHSubHeader(Context context, @NonNull VhHomeSubHeaderBinding binding,
      View.OnClickListener onClickListener) {
    super(binding.getRoot());
    this.binding = binding;
    this.context = context;
    this.onClickListener = onClickListener;

    init();
  }

  @SuppressLint("SetTextI18n") public void setIsLive(boolean isLive) {
    binding.videosType.setText(
        context.getString(R.string.recent) + " " + (isLive ? context.getString(R.string.tab_live)
            : context.getString(R.string.tab_vod)));
  }

  @SuppressLint("SetTextI18n") private void init() {
    binding.btnSeeAll.setVisibility(View.GONE);
    //binding.btnSeeAll.setOnClickListener(onClickListener);
  }

  public void show() {
    binding.container.setVisibility(View.VISIBLE);
  }

  public void hide() {
    binding.container.setVisibility(View.GONE);
  }
}