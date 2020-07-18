package notq.dccast.screens.home.mandu;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import notq.dccast.R;
import notq.dccast.databinding.ActivityManduHistoryWebViewBinding;
import notq.dccast.screens.BaseActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityManduHistoryWebView extends BaseActivity {

  private Context mContext = this;
  private ActivityManduHistoryWebViewBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_mandu_history_web_view);

    initToolbar();
    init();

    showLoader();
    ManduService service = new ManduService(mContext, new ManduService.ManduHistoryCallback() {
      @Override public void onError(String error) {
        hideAllLoaders();

        if (error != null) {
          Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
        } else {
          Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_LONG).show();
        }
      }

      @Override public void onComplete(String content) {
        hideAllLoaders();

        binding.webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
      }
    });
    service.getManduHistory();
  }

  private void initToolbar() {
    setSupportActionBar(binding.header.toolbar);
    binding.header.lblHeader.setText(getString(R.string.activity_mandu_history));
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

  private void showLoader() {
    if (!binding.dcLoader.isAnimating() && !binding.dcLoader.isShown()) {
      binding.dcLoader.playAnimation();
      binding.dcLoader.setVisibility(View.VISIBLE);
    }
  }

  private void hideLoaders() {
    if (binding.dcLoader.isShown() && binding.dcLoader.isAnimating()) {
      binding.dcLoader.setVisibility(View.GONE);
      binding.dcLoader.cancelAnimation();
    }
  }

  private void hideAllLoaders() {
    new CountDownTimer(1500, 1500) {
      @Override
      public void onTick(long l) {

      }

      @Override
      public void onFinish() {
        if (binding.dcLoader.isShown() && binding.dcLoader.isAnimating()) {
          binding.dcLoader.cancelAnimation();
        }

        binding.dcLoader.setVisibility(View.GONE);
      }
    }.start();

    hideLoaders();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
