package notq.dccast.screens.navigation_menu.settings;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.R;
import notq.dccast.screens.BaseActivity;

public class ActivityOpenSource extends BaseActivity {

  private LinearLayout layoutContainer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_open_source);

    View header = findViewById(R.id.header);
    Toolbar toolbar = header.findViewById(R.id.toolbar);
    TextView lblHeader = header.findViewById(R.id.lbl_header);
    View backButton = header.findViewById(R.id.back_button);

    setSupportActionBar(toolbar);
    lblHeader.setText(getString(R.string.settings_license));
    backButton.setOnClickListener(v -> {
      finish();
    });

    layoutContainer = findViewById(R.id.layout_container);

    List<String> licenses = new ArrayList<>();
    licenses.add("Glide (4.9.0)");
    licenses.add("Retrofit (2.5.0)");
    licenses.add("Timber (4.7.1)");
    licenses.add("Lottie (3.0.0)");
    licenses.add("GSON (2.8.2)");
    licenses.add("Exo Player (2.9.3)");
    licenses.add("Line SDK (4.0.8)");
    licenses.add("Kakao SDK (1.11.1)");
    licenses.add("Socket.io-client (1.0.0");

    if (licenses != null) {
      for (String license : licenses) {
        addToLayout(license);
      }
    }
  }

  private void addToLayout(String text) {
    TextView tv = new TextView(this);
    LinearLayout.LayoutParams params =
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    tv.setLayoutParams(params);
    tv.setText(text);
    tv.setTextColor(Color.BLACK);
    tv.setPadding(35, 30, 35, 30);

    View line = new View(this);
    LinearLayout.LayoutParams paramsLine =
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            3);
    line.setLayoutParams(paramsLine);
    line.setBackgroundColor(ContextCompat.getColor(this, R.color.tab_separator));
    layoutContainer.addView(tv);
    layoutContainer.addView(line);
  }
}
