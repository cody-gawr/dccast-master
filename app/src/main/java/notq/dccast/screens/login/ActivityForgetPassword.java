package notq.dccast.screens.login;

import android.content.Context;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import notq.dccast.R;
import notq.dccast.databinding.ActivityForgetPasswordBinding;
import notq.dccast.screens.BaseActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityForgetPassword extends BaseActivity {
    private Context mContext = this;
    private ActivityForgetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password);

        initToolbar();

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.loadUrl("https://dcid.dcinside.com/join_mobile/member_find_id.php");
    }

    private void initToolbar() {
        setSupportActionBar(binding.header.toolbar);
        binding.header.lblHeader.setText(getString(R.string.activity_forget_password));
        binding.header.backButton.setOnClickListener(v -> {
            onBackPressed();
        });
    }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
