package notq.dccast.screens.navigation_menu.notification.adapter;

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
import notq.dccast.databinding.VhHomeLoadMoreBinding;
import notq.dccast.databinding.VhNotificationVodItemBinding;
import notq.dccast.model.notification.ModelNotification;
import notq.dccast.screens.navigation_menu.notification.interfaces.NotificationNoticeListener;
import notq.dccast.util.Util;

public class AdapterNotificationVOD
    extends RecyclerView.Adapter {

  private static final int VIEW_TYPE_ITEM = 4;
  private static final int VIEW_TYPE_LOAD_MORE = 5;
  private List<ModelNotification> notifications;
  private Context context;
  private NotificationNoticeListener adapterListener;
  private VHLoadMore loadMore;
  private boolean isNeedShowLoader = true;

  public AdapterNotificationVOD(Context context, NotificationNoticeListener adapterListener) {
    this.context = context;
    this.adapterListener = adapterListener;
    notifications = new ArrayList<>();
  }

  public void addNotification(ModelNotification modelNotification) {
    notifications.add(modelNotification);
    notifyItemInserted(getItemCount() - 1);
  }

  public void showLoadMoreLoader() {
    if (loadMore != null) {
      isNeedShowLoader = true;
      notifyItemChanged(getItemCount() - 1);
    }
  }

  public void hideLoadMoreLoader() {
    if (loadMore != null) {
      isNeedShowLoader = false;
      notifyItemChanged(getItemCount() - 1);
    }
  }

  public List<ModelNotification> getNotifications() {
    return notifications == null ? new ArrayList<>() : notifications;
  }

  public void setNotifications(List<ModelNotification> notifications) {
    this.notifications = notifications;
    notifyDataSetChanged();
  }

  public void removeNotifications() {
    if (!notifications.isEmpty()) {
      notifyItemRangeRemoved(0, notifications.size());
      notifications.clear();
    }
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    switch (viewType) {
      case VIEW_TYPE_ITEM: {
        VhNotificationVodItemBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.vh_notification_vod_item, parent, false);
        return new ViewHolderNotificationVod(binding);
      }
      case VIEW_TYPE_LOAD_MORE: {
        VhHomeLoadMoreBinding loadMoreBinding =
            DataBindingUtil.inflate(inflater, R.layout.vh_home_load_more, parent, false);
        loadMore = new VHLoadMore(loadMoreBinding);

        return loadMore;
      }
    }

    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    switch (getItemViewType(position)) {
      case VIEW_TYPE_ITEM: {
        ((ViewHolderNotificationVod) holder).setData(notifications.get(position), position);
        break;
      }

      case VIEW_TYPE_LOAD_MORE: {
        ((VHLoadMore) holder).notifyLoaderStatusChanged(isNeedShowLoader);
        break;
      }
    }
  }

  @Override
  public int getItemCount() {
    return notifications.size() + 1;
  }

  @Override
  public int getItemViewType(int position) {
    if (position == getItemCount() - 1) {
      return VIEW_TYPE_LOAD_MORE;
    } else {
      return VIEW_TYPE_ITEM;
    }
  }

  public ModelNotification getItem(int position) {
    return notifications.get(position);
  }

  class ViewHolderNotificationVod extends RecyclerView.ViewHolder
      implements View.OnClickListener {
    private VhNotificationVodItemBinding binding;
    private int position;
    private ModelNotification modelNotification;

    ViewHolderNotificationVod(@NonNull VhNotificationVodItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(ModelNotification notificationLive, int position) {
      this.position = position;
      this.modelNotification = notificationLive;
      binding.layoutItem.setOnClickListener(this);

      binding.lblTitle.setText(modelNotification.getTitle());
      binding.lblBody.setText(modelNotification.getText());

      binding.lblDate.setText(Util.getDateString(modelNotification.getSendDateTime()));
    }

    @Override
    public void onClick(View view) {

    }
  }

  class VHLoadMore extends RecyclerView.ViewHolder {
    private VhHomeLoadMoreBinding binding;

    VHLoadMore(VhHomeLoadMoreBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void notifyLoaderStatusChanged(boolean isNeedShowLoadMore) {
      binding.progressBar.setVisibility(isNeedShowLoadMore ? View.VISIBLE : View.GONE);
    }
  }
}
