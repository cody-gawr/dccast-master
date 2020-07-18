package notq.dccast.screens.navigation_menu.settings.contact;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import notq.dccast.R;
import notq.dccast.databinding.ActivityContactUsDetailBinding;
import notq.dccast.model.ModelContactUs;
import notq.dccast.screens.BaseActivity;
import timber.log.Timber;

public class ActivityContactUsDetail extends BaseActivity {

  private Context mContext = this;
  private ActivityContactUsDetailBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us_detail);

    ModelContactUs contactUs = (ModelContactUs) getIntent().getSerializableExtra("contactUs");

    if (contactUs == null) {
      Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
      onBackPressed();
      return;
    }

    initToolbar();

    binding.title.setText(contactUs.getTitle());
    binding.type.setText(contactUs.getKinds());
    binding.contactDate.setText(getDateString(contactUs.getCreated()));
    binding.status.setText(contactUs.getState());
    binding.information.setText(contactUs.getQuestion());
    String text = contactUs.getAnswer();
    if (contactUs.getState() != null && contactUs.getState()
        .equalsIgnoreCase(getString(R.string.state_answered))) {
      text = getString(R.string.contact_us_answer_date)
          + getDateString(contactUs.getAnswer_date())
          + "\n\n"
          + contactUs.getAnswer();
    }
    binding.answer.setText(text);
  }

  private String getDateString(String dateStr) {
    if (dateStr == null) {
      return "";
    }

    try {
      SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
      SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      Date date = format1.parse(dateStr);
      return format2.format(date);
    } catch (Exception ex) {
      Timber.e("Exception: " + ex.getMessage());
    }

    return "";
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_contact_us_detail));
    binding.header.backButton.setOnClickListener(v -> {
      finish();
    });
  }
}
