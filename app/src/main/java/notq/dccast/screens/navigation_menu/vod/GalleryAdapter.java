package notq.dccast.screens.navigation_menu.vod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import notq.dccast.R;
import notq.dccast.databinding.VhVodGalleyItemBinding;
import notq.dccast.databinding.VhVodGalleySectionBinding;
import notq.dccast.model.vod.GalleryResponse;

public class GalleryAdapter extends RecyclerView.Adapter {
  private static final int VIEW_TYPE_MAIN_SECTION = 0;
  private static final int VIEW_TYPE_MAIN_ITEM = 1;
  private static final int VIEW_TYPE_MINOR_SECTION = 2;
  private static final int VIEW_TYPE_MINOR_ITEM = 3;

  private Context context;
  private GalleryItemWrapper mainGalleryItemWrapper;
  private GalleryItemWrapper minorGalleryItemWrapper;

  GalleryAdapter(Context context,
      GalleryItemWrapper mainGalleryItemWrapper,
      GalleryItemWrapper minorGalleryItemWrapper) {
    this.context = context;
    this.mainGalleryItemWrapper = mainGalleryItemWrapper;
    this.minorGalleryItemWrapper = minorGalleryItemWrapper;
  }

  @NonNull @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);

    switch (viewType) {
      case VIEW_TYPE_MAIN_SECTION: {
        VhVodGalleySectionBinding sectionBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.vh_vod_galley_section, parent, false);
        return new ViewHolderSection(sectionBinding);
      }

      case VIEW_TYPE_MAIN_ITEM: {
        VhVodGalleyItemBinding itemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.vh_vod_galley_item, parent, false);
        return new ViewHolderItem(itemBinding);
      }

      case VIEW_TYPE_MINOR_SECTION: {
        VhVodGalleySectionBinding sectionBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.vh_vod_galley_section, parent, false);
        return new ViewHolderSection(sectionBinding);
      }

      case VIEW_TYPE_MINOR_ITEM: {
        VhVodGalleyItemBinding itemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.vh_vod_galley_item, parent, false);
        return new ViewHolderItem(itemBinding);
      }
    }

    return null;
  }

  @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    switch (getItemViewType(position)) {
      case VIEW_TYPE_MAIN_SECTION: {
        ((ViewHolderSection) holder).sectionBinding.section.setText(
            mainGalleryItemWrapper.getSectionTitle());
        break;
      }

      case VIEW_TYPE_MAIN_ITEM: {
        GalleryResponse galleryResponse = mainGalleryItemWrapper.getItemList().get(position - 1);
        ((ViewHolderItem) holder).galleryBinding.itemTitle.setOnClickListener(
            view -> ((GalleryCallback) context).onGalleryItemClicked(galleryResponse));
        ((ViewHolderItem) holder).galleryBinding.itemTitle.setText(
            galleryResponse.getTitle());
        break;
      }

      case VIEW_TYPE_MINOR_SECTION: {
        ((ViewHolderSection) holder).sectionBinding.section.setText(
            minorGalleryItemWrapper.getSectionTitle());
        break;
      }

      case VIEW_TYPE_MINOR_ITEM: {
        int minusCount = 0;

        if (mainGalleryItemWrapper != null) {
          minusCount = mainGalleryItemWrapper.getItemList().size() + 1;
        }

        if (minorGalleryItemWrapper != null) {
          minusCount = minusCount + 1;
        }

        int realPosition = position - minusCount;
        GalleryResponse galleryResponse = minorGalleryItemWrapper.getItemList().get(realPosition);
        ((ViewHolderItem) holder).galleryBinding.itemTitle.setOnClickListener(
            view -> ((GalleryCallback) context).onGalleryItemClicked(galleryResponse));
        ((ViewHolderItem) holder).galleryBinding.itemTitle.setText(
            galleryResponse.getTitle());
        break;
      }
    }
  }

  @Override public int getItemViewType(int position) {
    if (mainGalleryItemWrapper != null) {
      if (position == 0) {
        return VIEW_TYPE_MAIN_SECTION;
      } else {
        if (position <= mainGalleryItemWrapper.getItemList().size()) {
          return VIEW_TYPE_MAIN_ITEM;
        } else if (minorGalleryItemWrapper != null) {
          if (position == getItemCount() - (minorGalleryItemWrapper.getItemList().size() + 1)) {
            return VIEW_TYPE_MINOR_SECTION;
          } else {
            return VIEW_TYPE_MINOR_ITEM;
          }
        }
      }
    } else if (minorGalleryItemWrapper != null) {
      if (position == 0) {
        return VIEW_TYPE_MINOR_SECTION;
      } else {
        if (position <= minorGalleryItemWrapper.getItemList().size()) {
          return VIEW_TYPE_MINOR_ITEM;
        }
      }
    }

    return super.getItemViewType(position);
  }

  @Override public int getItemCount() {
    int size = 0;

    if (mainGalleryItemWrapper != null) {
      size = mainGalleryItemWrapper.getItemList().size() + 1;
    }

    if (minorGalleryItemWrapper != null) {
      size = size + minorGalleryItemWrapper.getItemList().size() + 1;
    }

    return size;
  }

  private class ViewHolderSection extends RecyclerView.ViewHolder {
    VhVodGalleySectionBinding sectionBinding;

    ViewHolderSection(@NonNull VhVodGalleySectionBinding sectionBinding) {
      super(sectionBinding.getRoot());
      this.sectionBinding = sectionBinding;
    }
  }

  private class ViewHolderItem extends RecyclerView.ViewHolder {
    VhVodGalleyItemBinding galleryBinding;

    ViewHolderItem(VhVodGalleyItemBinding galleryBinding) {
      super(galleryBinding.getRoot());
      this.galleryBinding = galleryBinding;
    }
  }
}
