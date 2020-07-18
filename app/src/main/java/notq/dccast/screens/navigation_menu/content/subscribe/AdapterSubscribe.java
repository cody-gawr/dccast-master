package notq.dccast.screens.navigation_menu.content.subscribe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.my_content.MyContentAPIInterface;
import notq.dccast.databinding.VhHomeLoadMoreBinding;
import notq.dccast.databinding.VhSubscribeBinding;
import notq.dccast.model.user.profile.ModelProfileStat;
import notq.dccast.model.user.subscribe.ModelSubscribeUser;
import notq.dccast.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterSubscribe
    extends RecyclerView.Adapter {

  private static final int VIEW_TYPE_ITEM = 4;
  private static final int VIEW_TYPE_LOAD_MORE = 5;
  private List<ModelSubscribeUser> subscribers;
  private Context context;
  private SubscribeListener adapterListener;
  private VHLoadMore loadMore;
  private boolean isNeedShowLoader = true;

  AdapterSubscribe(Context context, SubscribeListener adapterListener) {
    this.context = context;
    this.adapterListener = adapterListener;
    subscribers = new ArrayList<>();
  }

  List<ModelSubscribeUser> getSubscribers() {
    return subscribers;
  }

  void setSubscribers(List<ModelSubscribeUser> notificationLives) {
    this.subscribers = notificationLives;
    notifyDataSetChanged();
  }

  int getSubscriptionsCount() {
    return subscribers == null ? 0 : subscribers.size();
  }

  void showLoadMoreLoader() {
    if (loadMore != null) {
      isNeedShowLoader = true;
      notifyItemChanged(getItemCount() - 1);
    }
  }

  void hideLoadMoreLoader() {
    if (loadMore != null) {
      isNeedShowLoader = false;
      notifyItemChanged(getItemCount() - 1);
    }
  }

  void removeSubscribers() {
    if (!subscribers.isEmpty()) {
      notifyItemRangeRemoved(0, subscribers.size());
      subscribers.clear();
    }
  }

  void addSubscribe(ModelSubscribeUser item) {
    subscribers.add(item);
    notifyItemInserted(getItemCount() - 1);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    switch (viewType) {
      case VIEW_TYPE_ITEM: {
        VhSubscribeBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.vh_subscribe, parent, false);
        return new ViewHolderSubscribe(binding);
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
        ((ViewHolderSubscribe) holder).setData(subscribers.get(position), position);
        break;
      }

      case VIEW_TYPE_LOAD_MORE: {
        ((VHLoadMore) holder).notifyLoaderStatusChanged(isNeedShowLoader);
        break;
      }
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (position == getItemCount() - 1) {
      return VIEW_TYPE_LOAD_MORE;
    } else {
      return VIEW_TYPE_ITEM;
    }
  }

  @Override
  public int getItemCount() {
    return subscribers.size() + 1;
  }

  class ViewHolderSubscribe extends RecyclerView.ViewHolder implements View.OnClickListener {
    private VhSubscribeBinding binding;
    private int position;
    private ModelSubscribeUser modelSubscriber;

    ViewHolderSubscribe(@NonNull VhSubscribeBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(ModelSubscribeUser item, int position) {
      this.position = position;
      this.modelSubscriber = item;
      binding.layoutItem.setOnClickListener(this);
      binding.lblOnAir.setOnClickListener(this);

      binding.layoutLoad.setVisibility(View.GONE);
      displayUser();
    }

    void getProfileStat() {
      binding.layoutStatLoad.setVisibility(View.VISIBLE);
      Call<ModelProfileStat> call =
          APIClient.getClient()
              .create(MyContentAPIInterface.class)
              .getProfileStat(modelSubscriber.getToUser().getId());

      call.enqueue(new Callback<ModelProfileStat>() {
        @Override
        public void onResponse(@NonNull Call<ModelProfileStat> call,
            @NonNull Response<ModelProfileStat> response) {
          ModelProfileStat stat = response.body();

          if (stat != null) {
            modelSubscriber.getToUser().setStat(stat);
          }
          subscribers.set(position, modelSubscriber);
          displayStat();
        }

        @Override
        public void onFailure(@NonNull Call<ModelProfileStat> call, @NonNull Throwable t) {
          call.cancel();

          binding.layoutStatLoad.setVisibility(View.GONE);
        }
      });
    }

    void displayUser() {
      binding.lblItemSubscribeUsername.setText(modelSubscriber.getToUser().getNickName());

      int width =
          context.getResources().getDimensionPixelSize(R.dimen.notification_notice_user_image_size);
      Glide.with(context).load(Util.getValidateUrl(modelSubscriber.getToUser().getProfileImage()))
          .apply(
              new RequestOptions()
                  .override(width, width)
                  .placeholder(R.drawable.ic_profile_placeholder)
                  .centerCrop()).into(binding.ivItemSubscribeUser);

      if (modelSubscriber.getToUser().isOnAir()) {
        binding.lblOnAir.setVisibility(View.VISIBLE);
      } else {
        binding.lblOnAir.setVisibility(View.GONE);
      }

      if (modelSubscriber.getToUser().getStat() == null) {
        getProfileStat();
      } else {
        displayStat();
      }
    }

    void displayStat() {
      binding.layoutStatLoad.setVisibility(View.GONE);
      if (modelSubscriber.getToUser() == null || modelSubscriber.getToUser().getStat() == null) {
        return;
      }
      //String stat = context.getString(R.string.subscriptions_stat,
      //    Util.getFormattedNumber(modelSubscriber.getToUser().getStat().subscribers));
      //binding.lblItemSubscribeDescription.setText(stat);
      binding.lblItemSubscribeDescription.setText(modelSubscriber.getToUser().getStateMessage());
    }

    @Override
    public void onClick(View view) {
      if (view.getId() == R.id.layout_item) {
        adapterListener.onUserSelected(modelSubscriber);
      }
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
