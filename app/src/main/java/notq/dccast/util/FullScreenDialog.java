package notq.dccast.util;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.DialogFragment;
import com.bumptech.glide.Glide;
import notq.dccast.R;

public class FullScreenDialog extends DialogFragment {

  public static final String TAG = "FullScreenDialog";
  private Dialog dialog;
  private String imgUrl = "";
  private boolean defaultImage;

  public static FullScreenDialog newInstance(String imgUrl) {
    FullScreenDialog myFragment = new FullScreenDialog();

    Bundle args = new Bundle();
    args.putString("imgUrl", imgUrl);
    myFragment.setArguments(args);

    return myFragment;
  }

  public static FullScreenDialog newInstanceDefault() {
    FullScreenDialog myFragment = new FullScreenDialog();

    Bundle args = new Bundle();
    args.putBoolean("defaultImage", true);
    myFragment.setArguments(args);

    return myFragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);

    if (getArguments() != null) {
      imgUrl = getArguments().getString("imgUrl");
      defaultImage = getArguments().getBoolean("defaultImage");
    }
  }

  @Override
  public void onStart() {
    super.onStart();

    dialog = getDialog();
    if (dialog != null) {
      int width = ViewGroup.LayoutParams.MATCH_PARENT;
      int height = ViewGroup.LayoutParams.MATCH_PARENT;
      dialog.getWindow().setLayout(width, height);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state) {
    super.onCreateView(inflater, parent, state);

    View view =
        getActivity().getLayoutInflater().inflate(R.layout.dialog_full_screen, parent, false);

    View close = view.findViewById(R.id.full_screen_image_close);
    ImageView ivImage = view.findViewById(R.id.full_screen_image);
    View container = view.findViewById(R.id.full_screen_image_container);

    if (imgUrl != null && !imgUrl.isEmpty()) {
      Glide.with(getActivity()).load(Util.getValidateUrl(imgUrl)).into(ivImage);
    }

    close.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (dialog != null) {
          dialog.dismiss();
        }
      }
    });

    container.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (dialog != null) {
          dialog.dismiss();
        }
      }
    });
    return view;
  }
}