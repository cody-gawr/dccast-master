package notq.dccast.screens.home.video_detail;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.databinding.FragmentBottomAddCommentBinding;
import notq.dccast.model.comment.ModelComment;
import notq.dccast.util.LoginService;
import notq.dccast.util.Util;

public class BottomSheetAddComment extends BottomSheetDialogFragment
    implements View.OnClickListener {
  private FragmentBottomAddCommentBinding binding;

  private View.OnClickListener onClickListenerForParent;
  private OnStickerSelect onStickerSelect;
  private BottomSheetComment.OnCommentAddListenerForComment onCommentAddListenerForComment;
  private boolean isComment = true;

  private boolean isEdit = false;
  private ModelComment editComment;
  private InputMethodManager keyboard;

  static synchronized BottomSheetAddComment getInstance(boolean isComment, boolean isEdit,
      ModelComment editComment) {
    BottomSheetAddComment instance = new BottomSheetAddComment();
    Bundle bundle = new Bundle();
    bundle.putBoolean("isComment", isComment);
    bundle.putBoolean("isEdit", isEdit);
    bundle.putSerializable("editComment", editComment);
    instance.setArguments(bundle);
    return instance;
  }

  public void setOnStickerSelect(
      OnStickerSelect onStickerSelect) {
    this.onStickerSelect = onStickerSelect;
  }

  public void setOnCommentAddListenerForComment(
      BottomSheetComment.OnCommentAddListenerForComment onCommentAddListenerForComment) {
    this.onCommentAddListenerForComment = onCommentAddListenerForComment;
  }

  void setOnClickListenerForParent(View.OnClickListener onClickListenerForParent) {
    this.onClickListenerForParent = onClickListenerForParent;
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_add_comment, container, false);

    keyboard =
        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

    if (getArguments() != null) {
      isComment = getArguments().getBoolean("isComment", true);
      isEdit = getArguments().getBoolean("isEdit", false);

      editComment = (ModelComment) getArguments().getSerializable("editComment");
    }

    initView();

    return binding.getRoot();
  }

  public void focusKeyboard() {
    if (binding.addComment != null) {
      binding.addComment.requestFocus();
      binding.addComment.postDelayed(new Runnable() {
                                       @Override public void run() {
                                         InputMethodManager keyboard =
                                             (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                         keyboard.showSoftInput(binding.addComment, InputMethodManager.SHOW_IMPLICIT);
                                       }
                                     }
          , 200);
    }
  }

  private void initView() {
    binding.btnEditCommentClose.setOnClickListener(onClickListenerForParent);

    if (isEdit) {
      binding.lblTitle.setText(isComment ? getString(R.string.edit_public_comment)
          : getString(R.string.edit_public_reply));
      binding.addComment.setHint(isComment ? getString(R.string.edit_public_comment)
          : getString(R.string.edit_public_reply));

      binding.addComment.postDelayed(new Runnable() {
                                       @Override public void run() {
                                         binding.addComment.requestFocus();
                                         if (keyboard == null) {
                                           return;
                                         }
                                         keyboard.showSoftInput(binding.addComment, InputMethodManager.SHOW_IMPLICIT);
                                       }
                                     }
          , 300);

      if (editComment != null) {
        if (editComment.getDcconUrl() == null || editComment.getDcconUrl().isEmpty()) {
          if (editComment.getText() != null && !editComment.getText().isEmpty()) {
            binding.addComment.setText(editComment.getText());
            binding.addComment.setSelection(binding.addComment.getText().length());
          }
          binding.btnSendSticker.setVisibility(View.GONE);
          binding.btnSendComment.setVisibility(View.VISIBLE);
          binding.addComment.requestFocus();
          showKeyboard();
        } else {
          binding.addComment.setVisibility(View.INVISIBLE);
          binding.btnSendSticker.setVisibility(View.VISIBLE);
          binding.btnSendComment.setVisibility(View.GONE);
        }
      }
    } else {
      binding.lblTitle.setText(isComment ? getString(R.string.add_public_comment)
          : getString(R.string.add_public_reply));
      binding.addComment.setHint(isComment ? getString(R.string.add_public_comment)
          : getString(R.string.add_public_reply));

      binding.addComment.postDelayed(new Runnable() {
                                       @Override public void run() {
                                         binding.addComment.requestFocus();
                                         if (keyboard == null) {
                                           return;
                                         }
                                         keyboard.showSoftInput(binding.addComment, InputMethodManager.SHOW_IMPLICIT);
                                       }
                                     }
          , 300);
    }

    binding.btnSendSticker.setOnClickListener(view -> {
      if (onStickerSelect != null) {
        onStickerSelect.onStickerSelect();
      }
    });

    binding.addComment.setOnTouchListener(new View.OnTouchListener() {
      public boolean onTouch(View view, MotionEvent event) {
        if (view.getId() == R.id.add_comment) {
          view.getParent().requestDisallowInterceptTouchEvent(true);
          switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
              view.getParent().requestDisallowInterceptTouchEvent(false);
              break;
          }
        }
        return false;
      }
    });

    if (LoginService.getLoginUser() != null) {
      Glide.with(getActivity())
          .load(Util.getValidateUrl(LoginService.getLoginUser().getProfileImage()))
          .into(binding.ivEditUser);
    }

    binding.btnSendComment.setOnClickListener(view -> {
      if (binding.addComment.getText().length() > 0) {
        String comment = Objects.requireNonNull(binding.addComment.getText()).toString();
        if (onCommentAddListenerForComment != null) {
          onCommentAddListenerForComment.onCommentSelected(isEdit, comment);
          binding.addComment.getText().clear();

          binding.btnEditCommentClose.performClick();
        }
      } else {
        Toast.makeText(getActivity(), getString(R.string.validate_comment),
            Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }

  @Override public void onClick(View v) {

  }

  private void showKeyboard() {
    if (getActivity() == null) {
      return;
    }
    InputMethodManager imm =
        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
  }

  private void hideKeyboard() {
    InputMethodManager imm =
        (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
    Objects.requireNonNull(imm).hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
  }

  public interface OnStickerSelect {
    void onStickerSelect();
  }
}
