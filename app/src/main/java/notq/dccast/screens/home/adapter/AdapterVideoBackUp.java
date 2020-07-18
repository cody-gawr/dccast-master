package notq.dccast.screens.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import io.realm.RealmList;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.R;
import notq.dccast.databinding.VhHomeCategoryBinding;
import notq.dccast.databinding.VhHomeLoadMoreBinding;
import notq.dccast.databinding.VhHomeMainHeaderBinding;
import notq.dccast.databinding.VhHomeNoDataBinding;
import notq.dccast.databinding.VhHomeSubHeaderBinding;
import notq.dccast.databinding.VhHomeVideoBinding;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.category.CategoryItem;
import notq.dccast.screens.home.view_holders.VHCategory;
import notq.dccast.screens.home.view_holders.VHLoadMore;
import notq.dccast.screens.home.view_holders.VHMainHeader;
import notq.dccast.screens.home.view_holders.VHNoData;
import notq.dccast.screens.home.view_holders.VHSubHeader;
import notq.dccast.screens.home.view_holders.VHVideo;

public class AdapterVideoBackUp extends RecyclerView.Adapter {
  public static final int VIEW_TYPE_MAIN_HEADER = 0;
  public static final int VIEW_TYPE_SUB_HEADER = 2;
  public static final int VIEW_TYPE_CATEGORY = 1;
  public static final int VIEW_TYPE_NO_DATA = 3;
  public static final int VIEW_TYPE_ITEM = 4;
  public static final int VIEW_TYPE_LOAD_MORE = 5;
  public List<CategoryItem> categories;
  private boolean isLive;
  private boolean isNeedShowLoader = true;
  private boolean isNeedShowHeaderPager = true;
  private List<ModelListHeader> headers;
  private ArrayList<ModelVideo> videos;
  private Fragment fragment;
  private TabLayout.OnTabSelectedListener categoryTabListener;
  private View.OnClickListener onClickListener;
  private VHLoadMore loadMore;
  private VHCategory category;
  private int type;

  public AdapterVideoBackUp(Fragment fragment, TabLayout.OnTabSelectedListener tabSelectedListener,
      View.OnClickListener onClickListener) {
    this.fragment = fragment;
    this.categoryTabListener = tabSelectedListener;
    this.onClickListener = onClickListener;

    videos = new ArrayList<>();
    headers = new ArrayList<>();
    categories = new RealmList<>();
  }

  public AdapterVideoBackUp(Fragment fragment, boolean isLive) {
    this.isLive = isLive;
    this.fragment = fragment;
    videos = new ArrayList<>();
  }

  public void updateVideo(ModelVideo modelVideo) {
    int position = -1;
    if (videos != null) {
      for (int i = 0; i < videos.size(); i++) {
        ModelVideo video = videos.get(i);
        if (video != null && video.getId() == modelVideo.getId()) {
          position = i;
          break;
        }
      }

      if (position != -1) {
        videos.set(position, modelVideo);
        position += (type == VHVideo.TYPE_HOME ? 4 : 0);
        notifyItemChanged(position);
      }
    }
  }

  /**
   * no header viewpager, no categories, no video header
   */
  public void setViewType(int type) {
    this.type = type;
  }

  public void toggleLiveVod(boolean isLive) {
    this.isLive = isLive;
    notifyItemChanged(VIEW_TYPE_SUB_HEADER);
  }

  public void removeVideoItems() {
    if (type != VHVideo.TYPE_HOME) {
      if (!videos.isEmpty()) {
        notifyItemRangeRemoved(0, videos.size());
        videos.clear();
      }
    } else {
      if (!videos.isEmpty()) {
        notifyItemRangeRemoved(VIEW_TYPE_ITEM, videos.size());
        videos.clear();
      }
    }
  }

  public void removeVideo(ModelVideo modelVideo) {
    for (int i = videos.size() - 1; i >= 0; i--) {
      if (videos.get(i).id == modelVideo.id) {
        videos.remove(i);
        int removeIndex = type == VHVideo.TYPE_HOME ? i + 4 : i;
        notifyItemRemoved(removeIndex);
      }
    }
  }

  public void addVideo(ModelVideo modelVideo) {
    videos.add(modelVideo);
    notifyItemInserted(getItemCount() - 1);
  }

  public int getVideoCount() {
    return videos == null ? 0 : videos.size();
  }

  public void resetHeaderDatas() {
    if (!headers.isEmpty()) {
      headers.clear();
    }

    showHeaderPager();

    notifyItemChanged(VIEW_TYPE_MAIN_HEADER);
  }

  public List<ModelListHeader> getHeaders() {
    return headers;
  }

  public void setHeaders(List<ModelListHeader> headers) {
    this.headers = headers;
    notifyItemChanged(VIEW_TYPE_MAIN_HEADER);
  }

  public ArrayList<ModelVideo> getVideos() {
    return videos;
  }

  public void setVideos(ArrayList<ModelVideo> videos) {
    this.videos = videos;
    notifyDataSetChanged();
  }

  public void moveCurrentTab(int position) {
    category.setCurrentPosition(position);
  }

  public void setHeaderDatas(List<ModelListHeader> listHeaderItems) {
    this.headers = listHeaderItems;
    notifyItemChanged(VIEW_TYPE_MAIN_HEADER);
    notifyItemChanged(VIEW_TYPE_SUB_HEADER);
  }

  public void setCategoryDatas(List<CategoryItem> listCategoryItems) {
    this.categories = listCategoryItems;
    notifyItemInserted(VIEW_TYPE_CATEGORY);
  }

  public void showLoadMoreLoader() {
    if (loadMore != null) {
      isNeedShowLoader = true;
      notifyItemChanged(getItemCount() - 1);
    }
  }

  public void setNeedShowLoader(boolean needShowLoader) {
    isNeedShowLoader = needShowLoader;
  }

  public void hideLoadMoreLoader() {
    if (loadMore != null) {
      isNeedShowLoader = false;
      notifyItemChanged(getItemCount() - 1);
    }
  }

  public void hideHeaderPager() {
    isNeedShowHeaderPager = false;
    notifyItemChanged(VIEW_TYPE_MAIN_HEADER);
  }

  public void showHeaderPager() {
    isNeedShowHeaderPager = true;
    notifyItemChanged(VIEW_TYPE_MAIN_HEADER);
  }

  public void hideVideosHeader() {
    isNeedShowHeaderPager = false;
    notifyItemChanged(VIEW_TYPE_SUB_HEADER);
  }

  public void showVideosHeader() {
    isNeedShowHeaderPager = true;
    notifyItemChanged(VIEW_TYPE_SUB_HEADER);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(fragment.getContext());

    switch (viewType) {
      case VIEW_TYPE_MAIN_HEADER: {
        VhHomeMainHeaderBinding headerBinding =
            DataBindingUtil.inflate(inflater, R.layout.vh_home_main_header, parent, false);
        return new VHMainHeader(fragment, headerBinding);
      }

      case VIEW_TYPE_CATEGORY: {
        VhHomeCategoryBinding categoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.vh_home_category, parent, false);
        category = new VHCategory(fragment, categoryTabListener, categoryBinding);
        return category;
      }

      case VIEW_TYPE_SUB_HEADER: {
        VhHomeSubHeaderBinding videoHeaderBinding =
            DataBindingUtil.inflate(inflater, R.layout.vh_home_sub_header, parent, false);
        return new VHSubHeader(fragment.getContext(), videoHeaderBinding, onClickListener);
      }

      case VIEW_TYPE_ITEM: {
        VhHomeVideoBinding liveBinding =
            DataBindingUtil.inflate(inflater, R.layout.vh_home_video, parent, false);
        return new VHVideo(fragment.getContext(), fragment.getFragmentManager(),
            liveBinding);
      }

      case VIEW_TYPE_LOAD_MORE: {
        VhHomeLoadMoreBinding loadMoreBinding =
            DataBindingUtil.inflate(inflater, R.layout.vh_home_load_more, parent, false);
        loadMore = new VHLoadMore(loadMoreBinding);
        loadMore.setIsHorizontal();

        return loadMore;
      }

      case VIEW_TYPE_NO_DATA: {
        VhHomeNoDataBinding noDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.vh_home_no_data, parent, false);
        return new VHNoData(noDataBinding);
      }
    }

    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    switch (getItemViewType(position)) {
      case VIEW_TYPE_MAIN_HEADER: {
        if (headers == null) {
          headers = new ArrayList<>();
        }
        ((VHMainHeader) holder).setHeaders(headers);

        if (headers.isEmpty()) {
          ((VHMainHeader) holder).hide();
        } else {
          ((VHMainHeader) holder).show();
        }

        //if (isNeedShowHeaderPager) {
        //  ((VHMainHeader) holder).show();
        //} else {
        //  ((VHMainHeader) holder).hide();
        //}

        break;
      }

      case VIEW_TYPE_CATEGORY: {
        if (categories != null) {
          if (((VHCategory) holder).getCategoryItems() == null) {
            ((VHCategory) holder).setCategories(categories);
          }
        }
        break;
      }

      case VIEW_TYPE_SUB_HEADER: {
        ((VHSubHeader) holder).setIsLive(isLive);

        if (isNeedShowHeaderPager) {
          ((VHSubHeader) holder).show();
        } else {
          ((VHSubHeader) holder).hide();
        }

        break;
      }

      case VIEW_TYPE_ITEM: {
        if (videos != null) {
          if (type == VHVideo.TYPE_GROUP) {
            ((VHVideo) holder).setVideoItem(type, videos.get(position),
                (VHVideo.ItemFetchedListener) video -> {
                  videos.set(position, video);
                  notifyItemChanged(position);
                });
          } else {
            ((VHVideo) holder).setVideoItem(type,
                videos.get(position - (type == VHVideo.TYPE_HOME ? 4 : 0)));
          }
        }

        break;
      }

      case VIEW_TYPE_LOAD_MORE: {
        ((VHLoadMore) holder).notifyLoaderStatusChanged(isNeedShowLoader);
        break;
      }

      case VIEW_TYPE_NO_DATA: {
        if (videos.isEmpty()) {
          ((VHNoData) holder).show();
        } else {
          ((VHNoData) holder).hide();
        }

        break;
      }
    }
  }

  @Override
  public int getItemCount() {
    return (videos == null ? 0 : videos.size()) + (type == VHVideo.TYPE_HOME ? 5 : 1);
  }

  @Override
  public int getItemViewType(int position) {
    if (type == VHVideo.TYPE_HOME) {
      if (position == 0) {
        return VIEW_TYPE_MAIN_HEADER;
      } else if (position == 1) {
        return VIEW_TYPE_CATEGORY;
      } else if (position == 2) {
        return VIEW_TYPE_SUB_HEADER;
      } else if (position == 3) {
        return VIEW_TYPE_NO_DATA;
      } else if (position == getItemCount() - 1) {
        return VIEW_TYPE_LOAD_MORE;
      } else {
        return VIEW_TYPE_ITEM;
      }
    } else {
      if (position == getItemCount() - 1) {
        return VIEW_TYPE_LOAD_MORE;
      } else {
        return VIEW_TYPE_ITEM;
      }
    }
  }
}

