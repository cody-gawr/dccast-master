package notq.dccast.screens.navigation_menu.settings;

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

public class SettingsItem extends LinearLayout {
  private TextView lblTitle, lblDescription;
  private SwitchButton switchItem;
  private ImageView iconRight;
  private TextView updateRight;

  private LayoutInflater mInflater;
  private Context context;

  private boolean isSwitch = false;
  private boolean isUpdate = false;
  private boolean checked = false;
  private String title;
  private String description;

  private SwitchButton.OnCheckedChangeListener listener;

  public SettingsItem(Context context) {
    super(context);
    this.context = context;
    init(null);
  }

  public SettingsItem(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    init(attrs);
  }

  public SettingsItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.context = context;
    init(attrs);
  }

  public void init(AttributeSet attrs) {
    mInflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View v = mInflater.inflate(R.layout.vh_settings_item, this, true);
    lblTitle = v.findViewById(R.id.lbl_title);
    lblDescription = v.findViewById(R.id.lbl_description);
    switchItem = v.findViewById(R.id.switch_item);
    iconRight = v.findViewById(R.id.icon_right);
    updateRight = v.findViewById(R.id.update_right);

    TypedArray typedArray = null;
    if (attrs != null) {
      typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingsItem);

      isSwitch = typedArray.getBoolean(R.styleable.SettingsItem_si_ischeck, false);
      isUpdate = typedArray.getBoolean(R.styleable.SettingsItem_si_update, false);
      checked = typedArray.getBoolean(R.styleable.SettingsItem_si_checked, false);
      title = typedArray.getString(R.styleable.SettingsItem_si_title);
      description = typedArray.getString(R.styleable.SettingsItem_si_description);
    }

    if (typedArray != null) {
      typedArray.recycle();
    }

    iconRight.setVisibility(isSwitch ? View.GONE : View.VISIBLE);
    switchItem.setVisibility(isSwitch ? View.VISIBLE : View.GONE);
    switchItem.setChecked(checked);

    if (isUpdate) {
      iconRight.setVisibility(View.GONE);
    }

    if (title != null) {
      lblTitle.setText(title);
    }

    if (description != null && !description.isEmpty()) {
      lblDescription.setText(description);
      lblDescription.setVisibility(View.VISIBLE);
    } else {
      lblDescription.setVisibility(View.GONE);
    }

    setOrientation(LinearLayout.HORIZONTAL);
    setGravity(Gravity.CENTER);
  }

  public void showUpdate(UpdateClickListener clickListener) {
    updateRight.setVisibility(View.VISIBLE);
    updateRight.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (clickListener != null) {
          clickListener.updateClick();
        }
      }
    });
  }

  public void hideUpdate() {
    updateRight.setVisibility(View.GONE);
  }

  public void toggleChecked() {
    if (switchItem != null) {
      boolean checked = switchItem.isChecked();
      switchItem.setChecked(!checked);
    }
  }

  public void setTitle(String title) {
    if (lblTitle != null) {
      lblTitle.setText(title);
    }
  }

  public boolean isChecked() {
    if (switchItem != null) {
      return switchItem.isChecked();
    }

    return false;
  }

  public void setChecked(boolean checked) {
    if (switchItem != null) {
      switchItem.setChecked(checked);
    }
  }

  public SwitchButton getSwitchItem() {
    return switchItem;
  }

  public void disableSwitch() {
    if (switchItem != null) {
      switchItem.setEnabled(false);
      switchItem.setClickable(false);
    }
  }

  public void disableClickSwitch() {
    if (switchItem != null) {
      switchItem.setEnabled(true);
      switchItem.setFocusable(false);
      switchItem.setFocusableInTouchMode(false);
      switchItem.setClickable(false);
    }
  }

  public void enableSwitch() {
    if (switchItem != null) {
      switchItem.setEnabled(true);
      switchItem.setClickable(true);
    }
  }

  public SwitchButton.OnCheckedChangeListener getCheckedChangeListener() {
    return listener;
  }

  public void setCheckedChangeListener(SwitchButton.OnCheckedChangeListener listener) {
    if (switchItem != null) {
      this.listener = listener;
      switchItem.setOnCheckedChangeListener(listener);
    }
  }

  public interface UpdateClickListener {
    void updateClick();
  }
}
