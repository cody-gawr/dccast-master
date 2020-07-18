package notq.dccast.screens.home.video_detail;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.List;
import java.util.Objects;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.CommentAPIInterface;
import notq.dccast.databinding.FragmentBottomStickerBinding;
import notq.dccast.model.comment.ModelSticker;
import notq.dccast.model.comment.ModelStickerWrapper;
import notq.dccast.model.comment.ModelTab;
import notq.dccast.screens.home.adapter.AdapterFragmentSticker;
import notq.dccast.screens.home.adapter.AdapterStickerTabs;
import notq.dccast.screens.home.adapter.VerticalSpaceItemDecoration;
import notq.dccast.screens.home.interfaces.StickerListener;
import notq.dccast.screens.home.interfaces.StickerTabListener;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetSticker extends BottomSheetDialogFragment
    implements StickerTabListener, StickerListener {

  private AdapterStickerTabs adapterStickerTabs;
  private List<List<ModelSticker>> stickerList;
  private FragmentBottomStickerBinding binding;
  private LiveChatHelper liveChatHelper;
  private View.OnClickListener onClickListener;
  private BottomSheetComment.OnStickerSelectedListenerForComment stickerListener;
  private AdapterFragmentSticker adapterViewPagerSticker;
  private Dialog stickerPreview;
  private OnLiveStickerSelected onLiveStickerSelected;
  private OnDismissListener onDismissListener;
  private boolean isEdit = false;
  private boolean isFullScreen = false;
  private boolean isPortrait = false;

  static synchronized BottomSheetSticker getInstance(boolean isEdit, boolean isFullScreen,
      boolean isPortrait) {
    BottomSheetSticker instance = new BottomSheetSticker();
    Bundle bundle = new Bundle();
    bundle.putBoolean("isEdit", isEdit);
    bundle.putBoolean("isFullScreen", isFullScreen);
    bundle.putBoolean("isPortrait", isPortrait);
    instance.setArguments(bundle);
    return instance;
  }

  public void setOnDismissListener(
      BottomSheetSticker.OnDismissListener onDismissListener) {
    this.onDismissListener = onDismissListener;
  }

  public void setOnLiveStickerSelected(
      OnLiveStickerSelected onLiveStickerSelected) {
    this.onLiveStickerSelected = onLiveStickerSelected;
  }

  public void setOnClickListener(View.OnClickListener onClickListener) {
    this.onClickListener = onClickListener;
  }

  public void setStickerListener(
      BottomSheetComment.OnStickerSelectedListenerForComment stickerListener) {
    this.stickerListener = stickerListener;
  }

  public void setLiveChatHelper(LiveChatHelper liveChatHelper) {
    this.liveChatHelper = liveChatHelper;
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sticker, container, false);
    if (getArguments() != null) {
      isEdit = getArguments().getBoolean("isEdit", false);
      isFullScreen = getArguments().getBoolean("isFullScreen", false);
      isPortrait = getArguments().getBoolean("isPortrait", false);
    }
    init();
    return binding.getRoot();
  }

  private void init() {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    Objects.requireNonNull(getActivity())
        .getWindowManager()
        .getDefaultDisplay()
        .getMetrics(displayMetrics);
    int displayHeight = displayMetrics.heightPixels;
    adapterStickerTabs = new AdapterStickerTabs(getContext(), this);
    LinearLayoutManager tabLayoutManager =
        new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    binding.tabRecyclerView.setLayoutManager(tabLayoutManager);
    VerticalSpaceItemDecoration dividerItemDecoration = new VerticalSpaceItemDecoration(20);
    binding.tabRecyclerView.addItemDecoration(dividerItemDecoration);
    binding.tabRecyclerView.setAdapter(adapterStickerTabs);

    adapterViewPagerSticker =
        new AdapterFragmentSticker(getChildFragmentManager(), isEdit, isFullScreen, this);
    binding.viewPager.setAdapter(adapterViewPagerSticker);
    binding.viewPager.setOffscreenPageLimit(1);

    if (onClickListener != null) {
      binding.btnStickerClose.setOnClickListener(onClickListener);
    } else {
      binding.btnStickerClose.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          dismiss();
        }
      });
    }

    DCCastApplication.utils.hideKeyboard(getActivity());
    getStickers();
  }

  private void getStickers() {
    if (DCCastApplication.appId == null || DCCastApplication.appId.isEmpty()) {
      return;
    }
    //TODO CHANGE USER ID
    //DEBUG
    //String userId = "Q0c0OGtmSEgzUzVHUXBTa0lYRTRZUT09";
    //PROD
    String userId = DCCastApplication.userId;
    binding.progressBarSticker.setVisibility(View.VISIBLE);
    Call<ModelStickerWrapper> call =
        APIClient.getMDCIdClient()
            .create(CommentAPIInterface.class)
            .getStickers("list", DCCastApplication.appId, userId);

    call.enqueue(new Callback<ModelStickerWrapper>() {
      @Override public void onResponse(@NonNull Call<ModelStickerWrapper> call,
          @NonNull Response<ModelStickerWrapper> response) {
        binding.progressBarSticker.setVisibility(View.GONE);

        ModelStickerWrapper stickerWrapper = response.body();

        if (stickerWrapper == null
            || stickerWrapper.getTabList() == null
            || stickerWrapper.getStickerList() == null) {
          return;
        }

        List<ModelTab> tabList = stickerWrapper.getTabList();
        if (tabList != null && tabList.size() > 0) {
          tabList.get(0).setSelected(true);
        }

        stickerList = stickerWrapper.getStickerList();

        adapterStickerTabs.setTabs(tabList);
        if (stickerList != null && stickerList.size() > 0) {
          adapterViewPagerSticker.setStickerItems(stickerList.get(0));
        }

        binding.totalStickers.setText(String.valueOf(tabList.size()));
      }

      @Override
      public void onFailure(@NonNull Call<ModelStickerWrapper> call, @NonNull Throwable t) {
        call.cancel();
        binding.progressBarSticker.setVisibility(View.GONE);
      }
    });
  }

  @Override public void onStickerSelected(boolean isEdit, ModelSticker sticker) {
    if (liveChatHelper != null) {
      if (liveChatHelper.isJoined) {
        liveChatHelper.sendSticker(sticker.getImg());
        dismiss();

        if (onLiveStickerSelected != null) {
          onLiveStickerSelected.onLiveStickerSelected();
        }
      } else {
        Toast.makeText(getActivity(), getString(R.string.live_need_to_join), Toast.LENGTH_SHORT)
            .show();
      }
    } else {
      if (stickerListener != null) {
        stickerListener.onStickerSelected(isEdit, sticker);
        if (onDismissListener != null) {
          onDismissListener.onDismiss();
        }
        dismiss();
      }
    }
  }

  @Override public void onStickerLongClick(ModelSticker sticker) {
    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
    View rootView =
        layoutInflater.inflate(R.layout.dialog_sticker_preview, null, false);

    ImageView ivStickerPreview = rootView.findViewById(R.id.iv_sticker_preview);
    View layoutLoad = rootView.findViewById(R.id.layout_load);

    stickerPreview = new Dialog(getContext());
    stickerPreview.setContentView(rootView);
    stickerPreview.setCancelable(true);

    Glide.with(getActivity())
        .load(Util.getValidateUrl(sticker.getImg()))
        .listener(new RequestListener<Drawable>() {
          @Override
          public boolean onLoadFailed(@Nullable GlideException e, Object model,
              Target<Drawable> target,
              boolean isFirstResource) {
            layoutLoad.setVisibility(View.GONE);
            return false;
          }

          @Override
          public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
              DataSource dataSource, boolean isFirstResource) {
            layoutLoad.setVisibility(View.GONE);
            return false;
          }
        })
        .into(ivStickerPreview);

    Window window = stickerPreview.getWindow();
    if (window != null) {
      int previewSize = getResources().getDimensionPixelSize(R.dimen.dialog_preview_height);
      window.setLayout(previewSize, previewSize);
    }

    stickerPreview.show();
  }

  @Override public void onStickerLongClickRelease() {
    if (stickerPreview != null && stickerPreview.isShowing()) {
      stickerPreview.dismiss();
    }
  }

  @Override public void onStickerTabSelected(int position) {
    if (getActivity() == null) {
      return;
    }

    new Handler().postDelayed(() -> getActivity().runOnUiThread(() -> {
      binding.viewPager.setAdapter(null);
      adapterStickerTabs.updateSelected(position);
      if (stickerList != null && stickerList.size() >= position) {
        adapterViewPagerSticker.setStickerItems(stickerList.get(position));
        binding.viewPager.setAdapter(adapterViewPagerSticker);
      }
    }), 200);
  }

  public interface OnLiveStickerSelected {
    void onLiveStickerSelected();
  }

  public interface OnDismissListener {
    void onDismiss();
  }
}
