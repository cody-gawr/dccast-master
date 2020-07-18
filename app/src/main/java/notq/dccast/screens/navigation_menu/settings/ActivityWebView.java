package notq.dccast.screens.navigation_menu.settings;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import notq.dccast.R;
import notq.dccast.databinding.ActivityWebViewBinding;
import notq.dccast.screens.BaseActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityWebView extends BaseActivity {

  private Context mContext = this;
  private ActivityWebViewBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);

    String url = getIntent().getStringExtra("url");
    String title = getIntent().getStringExtra("title");
    if (url == null || url.isEmpty()) {
      Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
      onBackPressed();
      return;
    }

    if (title != null) {
      binding.header.lblHeader.setText(title);
    }

    initToolbar();
    init();

    binding.webView.loadUrl(url);
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.backButton.setOnClickListener(v -> {
      finish();
    });
  }

  private void init() {
    binding.webView.getSettings().setJavaScriptEnabled(true);
    binding.webView.setWebViewClient(new WebViewClient() {

      public void onPageFinished(WebView view, String url) {
        binding.header.layoutLoading.setVisibility(View.GONE);
      }

      @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

        binding.header.layoutLoading.setVisibility(View.VISIBLE);
      }
    });
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
