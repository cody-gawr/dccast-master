package notq.dccast.screens.navigation_menu.settings.contact;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import notq.dccast.R;
import notq.dccast.databinding.VhContactUsBinding;
import notq.dccast.model.ModelContactUs;
import timber.log.Timber;

public class ContactUsAdapter extends RecyclerView.Adapter<ContactUsAdapter.ViewHolderContactUs> {
  private List<ModelContactUs> contactUs;
  private Context context;
  private ContactUsInterface contactUsInterface;

  public ContactUsAdapter(Context context, ContactUsInterface contactUsInterface) {
    this.context = context;
    contactUs = new ArrayList<>();
    this.contactUsInterface = contactUsInterface;
  }

  public void setContactUs(List<ModelContactUs> contactUs) {
    this.contactUs = contactUs;
    notifyDataSetChanged();
  }

  public int getContactUsCount() {
    return contactUs == null ? 0 : contactUs.size();
  }

  public void addContactUs(ModelContactUs contactUs) {
    this.contactUs.add(contactUs);
    notifyItemInserted(getItemCount() - 1);
  }

  public void removeContactUs() {
    this.contactUs = new ArrayList<>();
    notifyDataSetChanged();
  }

  @NonNull @Override
  public ViewHolderContactUs onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    return new ViewHolderContactUs(
        DataBindingUtil.inflate(layoutInflater, R.layout.vh_contact_us, parent, false));
  }

  @Override public void onBindViewHolder(@NonNull ViewHolderContactUs holder, int position) {
    holder.setMessage(contactUs.get(position));
  }

  @Override public int getItemCount() {
    return contactUs == null ? 0 : contactUs.size();
  }

  public interface ContactUsInterface {
    void contactUsClicked(ModelContactUs contactUs);
  }

  class ViewHolderContactUs extends RecyclerView.ViewHolder {
    private VhContactUsBinding binding;

    ViewHolderContactUs(@NonNull VhContactUsBinding chatBinding) {
      super(chatBinding.getRoot());
      this.binding = chatBinding;
    }

    @SuppressLint("SetTextI18n")
    void setMessage(ModelContactUs contactUs) {
      this.binding.title.setText(contactUs.getTitle());
      this.binding.state.setText(contactUs.getState());
      this.binding.layoutItem.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (contactUsInterface != null) {
            contactUsInterface.contactUsClicked(contactUs);
          }
        }
      });

      try {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format1.parse(contactUs.getCreated());
        binding.date.setText(format2.format(date));
      } catch (Exception ex) {
        Timber.e("Exception: " + ex.getMessage());
      }
    }
  }
}
