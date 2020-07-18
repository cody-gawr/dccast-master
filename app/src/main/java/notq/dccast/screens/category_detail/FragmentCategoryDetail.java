package notq.dccast.screens.category_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import notq.dccast.R;

public class FragmentCategoryDetail extends Fragment {
  public FragmentCategoryDetail() {
    // Required empty public constructor
  }

  private static FragmentCategoryDetail newInstance() {
    FragmentCategoryDetail fragment = new FragmentCategoryDetail();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {

    }
  }

  @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_category_detail, container, false);
  }
}
