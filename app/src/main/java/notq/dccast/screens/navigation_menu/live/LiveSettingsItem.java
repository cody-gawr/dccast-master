package notq.dccast.screens.navigation_menu.live;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import notq.dccast.R;
import notq.dccast.custom_view.SwitchButton;
import notq.dccast.util.Util;

public class LiveSettingsItem extends LinearLayout {
  private TextView lblTitle, lblValue;
  private SwitchButton switchItem;
  private ImageView ivArrow, ivIcon, ivDescriptionArrow;
  private LinearLayout layoutText;
  private View layoutItem;
  private View layoutSubscriber, layoutCastLine;
  private TextView lblFollowers, lblSecondFollowers, lblThirdFollowers, lblSubscriber;

  private LayoutInflater mInflater;
  private Context context;

  private boolean isSwitch = false;
  private boolean checked = false;
  private boolean hasArrow = false;
  private int icon;
  private String title;
  private String value;
  private int rotation = 0;

  public LiveSettingsItem(Context context) {
    super(context);
    this.context = context;
    init(null);
  }

  public LiveSettingsItem(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    init(attrs);
  }

  public LiveSettingsItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.context = context;
    init(attrs);
  }

  public SwitchButton getSwitchItem() {
    return switchItem;
  }

  public void init(AttributeSet attrs) {
    mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View v = mInflater.inflate(R.layout.vh_live_settings_item, this, true);
    ivIcon = v.findViewById(R.id.iv_icon);
    layoutText = v.findViewById(R.id.layout_text);
    lblTitle = v.findViewById(R.id.lbl_title);
    lblValue = v.findViewById(R.id.lbl_value);
    switchItem = v.findViewById(R.id.switch_item);
    ivArrow = v.findViewById(R.id.iv_arrow);
    layoutItem = v.findViewById(R.id.layout_item);

    ivDescriptionArrow = v.findViewById(R.id.iv_description_arrow);
    layoutSubscriber = v.findViewById(R.id.layout_subscriber);
    layoutCastLine = v.findViewById(R.id.layout_cast_line);

    lblSubscriber = v.findViewById(R.id.lbl_value_subscriber);
    lblFollowers = v.findViewById(R.id.lbl_value_followers);
    lblSecondFollowers = v.findViewById(R.id.lbl_value_second_followers);
    lblThirdFollowers = v.findViewById(R.id.lbl_value_third_followers);

    TypedArray typedArray = null;
    if (attrs != null) {
      typedArray = context.obtainStyledAttributes(attrs, R.styleable.LiveSettingsItem);
      icon = typedArray.getResourceId(R.styleable.LiveSettingsItem_lsi_icon, -1);
      isSwitch = typedArray.getBoolean(R.styleable.LiveSettingsItem_lsi_ischeck, false);
      checked = typedArray.getBoolean(R.styleable.LiveSettingsItem_lsi_checked, false);
      hasArrow = typedArray.getBoolean(R.styleable.LiveSettingsItem_lsi_has_arrow, false);
      title = typedArray.getString(R.styleable.LiveSettingsItem_lsi_title);
      value = typedArray.getString(R.styleable.LiveSettingsItem_lsi_value);
    }

    if (typedArray != null) {
      typedArray.recycle();
    }

    if (isSwitch) {
      switchItem.setVisibility(View.VISIBLE);
      layoutText.setVisibility(View.GONE);
      switchItem.setChecked(checked);

      ivArrow.setVisibility(View.GONE);
    } else {
      switchItem.setVisibility(View.GONE);
      layoutText.setVisibility(View.VISIBLE);

      ivArrow.setVisibility(isSwitch || !hasArrow ? View.GONE : View.VISIBLE);

      if (hasArrow) {
        ivArrow.setVisibility(View.VISIBLE);
      } else {
        ivArrow.setVisibility(View.GONE);
      }

      if (hasArrow) {
        layoutItem.setOnClickListener(v1 -> ivArrow.performClick());
      }

      if (value != null && !value.isEmpty()) {
        lblValue.setText(value);
        lblValue.setVisibility(View.VISIBLE);
      } else {
        lblValue.setVisibility(View.GONE);
      }
    }

    if (title != null) {
      lblTitle.setText(title);
    }

    if (icon > 0) {
      ivIcon.setImageResource(icon);
    }

    setOrientation(LinearLayout.HORIZONTAL);
    setGravity(Gravity.CENTER);
  }

  public void setItemClickListener(ItemClickListener itemClickListener) {
    if (!isSwitch) {
      if (hasArrow) {
        layoutItem.setOnClickListener(v -> itemClickListener.rightArrowClicked());
        ivArrow.setOnClickListener(v -> itemClickListener.rightArrowClicked());
      }
      lblValue.setOnClickListener(v -> itemClickListener.valueItemClicked());
    }
  }

  public void showDescriptionLayout(int followers, int secondFollowers, int thirdFollowers) {
    ivDescriptionArrow.setVisibility(View.VISIBLE);
    layoutCastLine.setVisibility(View.VISIBLE);

    lblFollowers.setText(Util.getFormattedNumber(followers));
    lblSecondFollowers.setText(Util.getFormattedNumber(secondFollowers));
    lblThirdFollowers.setText(Util.getFormattedNumber(thirdFollowers));

    rotateImage(270);
  }

  public void showDescriptionLayout(int subscriber) {
    ivDescriptionArrow.setVisibility(View.VISIBLE);
    layoutSubscriber.setVisibility(View.VISIBLE);

    lblSubscriber.setText(Util.getFormattedNumber(subscriber));

    rotateImage(270);
  }

  public boolean isDescriptionShowing() {
    return ivDescriptionArrow.getVisibility() == View.VISIBLE;
  }

  public void hideDescriptionLayout() {
    ivDescriptionArrow.setVisibility(View.GONE);
    layoutSubscriber.setVisibility(View.GONE);
    layoutCastLine.setVisibility(View.GONE);

    rotateImage(90);
  }

  private void rotateImage(int toDegree) {
    ivArrow.animate().rotation(toDegree);
  }

  public String getValueText() {
    return lblValue.getText().toString();
  }

  public void setValue(String value) {
    if (value != null && !value.isEmpty()) {
      lblValue.setText(value);
      lblValue.setVisibility(View.VISIBLE);
    } else {
      lblValue.setVisibility(View.GONE);
    }
  }

  public boolean isSubscriber() {
    return lblValue.getText()
        .toString()
        .equalsIgnoreCase(context.getString(R.string.item_distribute_subscriber));
  }

  public void toggleChecked() {
    if (switchItem != null) {
      boolean checked = !switchItem.isChecked();
      switchItem.setChecked(checked);
    }
  }

  public void setChecked(boolean checked) {
    if (switchItem != null) {
      switchItem.setChecked(checked);
    }
  }

  public void setCheckedChangeListener(SwitchButton.OnCheckedChangeListener listener) {
    if (switchItem != null) {
      switchItem.setOnCheckedChangeListener(listener);
    }
  }

  interface ItemClickListener {
    void rightArrowClicked();

    void valueItemClicked();
  }
}
