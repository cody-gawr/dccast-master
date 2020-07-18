package notq.dccast.screens.navigation_menu.cast.group.add_people;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.R;
import notq.dccast.databinding.VhGroupAddPeopleBinding;
import notq.dccast.databinding.VhHomeLoadMoreBinding;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.navigation_menu.cast.interfaces.AddPeopleListener;
import notq.dccast.util.Util;

public class AdapterAddPeople
    extends RecyclerView.Adapter {

  private static final int VIEW_TYPE_ITEM = 4;
  private static final int VIEW_TYPE_LOAD_MORE = 5;
  private List<ModelUser> users;
  private Context context;
  private AddPeopleListener adapterListener;

  private VHLoadMore loadMore;
  private boolean isNeedShowLoader = true;

  public AdapterAddPeople(Context context, AddPeopleListener adapterListener) {
    this.context = context;
    this.adapterListener = adapterListener;
    users = new ArrayList<>();
  }

  public void setUsers(List<ModelUser> users) {
    this.users = users;
    notifyDataSetChanged();
  }

  public ArrayList<ModelUser> getSelectedPeople() {
    ArrayList<ModelUser> selectedList = new ArrayList<>();
    for (ModelUser listPerson : users) {
      if (listPerson.isSelected()) {
        selectedList.add(listPerson);
      }
    }
    return selectedList;
  }

  public ArrayList<ModelUser> getAllPeopleWithSelectedStatus() {
    if (users == null) {
      return new ArrayList<>();
    }
    return new ArrayList<>(users);
  }

  public int getUsersCount() {
    return users == null ? 0 : users.size();
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

  public void removeUser() {
    if (!users.isEmpty()) {
      notifyItemRangeRemoved(0, users.size());
      users.clear();
    }
  }

  public void addUser(ModelUser item) {
    users.add(item);
    notifyItemInserted(getItemCount() - 1);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    switch (viewType) {
      case VIEW_TYPE_ITEM: {
        VhGroupAddPeopleBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.vh_group_add_people, parent, false);
        return new ViewHolderAddPeople(binding);
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
        ((ViewHolderAddPeople) holder).setData(users.get(position), position);
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
    return users.size() + 1;
  }

  public ModelUser getItem(int position) {
    return users.get(position);
  }

  public void updateItem(int position, ModelUser item) {
    users.set(position, item);
    notifyItemChanged(position);
  }

  public void updateItemSelected(int id, boolean selected) {
    int position = -1;
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getId() == id) {
        position = i;
      }
    }

    if (position == -1) {
      return;
    }
    users.get(position).setSelected(selected);
    notifyItemChanged(position);
  }

  class ViewHolderAddPeople extends RecyclerView.ViewHolder implements View.OnClickListener {
    private VhGroupAddPeopleBinding binding;
    private int position;
    private ModelUser item;

    ViewHolderAddPeople(@NonNull VhGroupAddPeopleBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void setData(ModelUser item, int position) {
      this.position = position;
      this.item = item;
      binding.layoutItem.setOnClickListener(this);

      binding.lblUsername.setText(item.getNickName());
      Glide.with(context).load(Util.getValidateUrl(item.getProfileImage()))
          .placeholder(R.drawable.ic_notification_placeholder).into(binding.ivUser);

      if (item.isSelected()) {
        binding.viewSelected.setBackgroundResource(R.drawable.group_add_people_chosen_bg);
      } else {
        binding.viewSelected.setBackgroundResource(R.drawable.group_add_people_choose_bg);
      }
    }

    @Override
    public void onClick(View view) {
      switch (view.getId()) {
        case R.id.layout_item: {
          adapterListener.onItemSelected(position);
          break;
        }
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
