package notq.dccast.screens.home.video_detail;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.api.APIClient;
import notq.dccast.api.home.CommentAPIInterface;
import notq.dccast.databinding.DialogVideoReportBinding;
import notq.dccast.databinding.FragmentBottomCommentBinding;
import notq.dccast.model.comment.ModelComment;
import notq.dccast.model.comment.ModelCommentActionResponse;
import notq.dccast.model.comment.ModelCommentActionWrapper;
import notq.dccast.model.comment.ModelCreateCommentResponse;
import notq.dccast.model.comment.ModelSticker;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.adapter.AdapterComments;
import notq.dccast.screens.home.interfaces.CommentListener;
import notq.dccast.screens.home.interfaces.ReplyButtonListener;
import notq.dccast.util.DialogHelper;
import notq.dccast.util.LoginService;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class BottomSheetComment extends BottomSheetDialogFragment
    implements View.OnClickListener, ReplyButtonListener, CommentListener {
  private static int videoId;
  private FragmentBottomCommentBinding binding;
  private BottomSheetBehavior sheetBehaviorReply;
  private BottomSheetBehavior sheetBehaviorMore;
  private View.OnClickListener onClickListenerForParent;
  private AdapterComments adapterComments;
  private AdapterComments adapterReplies;
  private RecyclerView.SmoothScroller smoothScroller;
  private AlertDialog reportVodLive;
  private DialogVideoReportBinding reportDialogBinding;

  private OnLayoutAddComment onLayoutAddComment;
  private ModelComment editModelComment;

  private OnStickerSelectedListenerForComment stickerSelectedListenerForComment;
  private OnCommentAddListenerForComment onCommentAddListenerForComment;

  static synchronized BottomSheetComment getInstance(int videoId) {
    BottomSheetComment instance = new BottomSheetComment();
    Bundle bundle = new Bundle();
    bundle.putInt("videoId", videoId);
    instance.setArguments(bundle);
    return instance;
  }

  public OnStickerSelectedListenerForComment getStickerSelectedListenerForComment() {
    return stickerSelectedListenerForComment;
  }

  public OnCommentAddListenerForComment getOnCommentAddListenerForComment() {
    return onCommentAddListenerForComment;
  }

  public void setOnLayoutAddComment(
      OnLayoutAddComment onLayoutAddComment) {
    this.onLayoutAddComment = onLayoutAddComment;
  }

  void setOnClickListenerForParent(View.OnClickListener onClickListenerForParent) {
    this.onClickListenerForParent = onClickListenerForParent;
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_comment, container, false);
    videoId = Objects.requireNonNull(getArguments()).getInt("videoId");

    stickerSelectedListenerForComment = new OnStickerSelectedListenerForComment() {
      @Override
      public void onStickerSelected(boolean isEdit, ModelSticker sticker) {
        boolean isReply = sheetBehaviorReply.getState() == BottomSheetBehavior.STATE_EXPANDED;
        int parentId = (isReply) ? adapterReplies.getParentId() : adapterComments.getParentId();
        boolean isComment = !isReply;

        if (isEdit) {
          if (editModelComment == null) {
            Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
            return;
          }

          editModelComment.setDcconUrl(sticker.getImg());
          editModelComment.setText(null);
          updateCommentRequest(isComment, editModelComment);
        } else {
          createCommentStickerRequest(
              isComment, sticker.getImg(), parentId);
        }
      }
    };

    onCommentAddListenerForComment = new OnCommentAddListenerForComment() {
      @Override public void onCommentSelected(boolean isEdit, String comment) {
        boolean isReply = sheetBehaviorReply.getState() == BottomSheetBehavior.STATE_EXPANDED;
        int parentId = (isReply) ? adapterReplies.getParentId() : adapterComments.getParentId();
        boolean isComment = !isReply;

        if (isEdit) {
          if (editModelComment == null) {
            Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
            return;
          }

          editModelComment.setText(comment);
          editModelComment.setDcconUrl(null);
          updateCommentRequest(isComment, editModelComment);
        } else {
          createCommentRequest(
              isComment, comment, parentId);
        }
      }
    };

    initCommentsRecyclerView();
    initReportDialog();
    getCommentListRequest(videoId);

    return binding.getRoot();
  }

  @Override public void onDestroy() {
    if (reportVodLive != null && reportVodLive.isShowing()) {
      reportVodLive.dismiss();
    }

    super.onDestroy();
  }

  @SuppressLint("ClickableViewAccessibility") private void initCommentsRecyclerView() {
    sheetBehaviorReply = BottomSheetBehavior.from(binding.replyBottomSheet);
    sheetBehaviorMore = BottomSheetBehavior.from(binding.reportBottomSheet);
    sheetBehaviorMore.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override public void onStateChanged(@NonNull View bottomSheet, int newState) {
        if (newState == BottomSheetBehavior.STATE_EXPANDED) {
          binding.dim.setVisibility(View.VISIBLE);
        } else {
          //if (!toggleDim()) {
          //  binding.dim.setVisibility(View.GONE);
          //}
          binding.dim.setVisibility(View.GONE);
        }
      }

      @Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {

      }
    });

    sheetBehaviorReply.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override public void onStateChanged(@NonNull View bottomSheet, int newState) {
        //if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
        //  binding.dim.setVisibility(View.VISIBLE);
        //} else {
        //  if (!toggleDim()) {
        //    binding.dim.setVisibility(View.GONE);
        //  }
        //}
      }

      @Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {

      }
    });

    smoothScroller = new LinearSmoothScroller(getContext()) {
      @Override protected int getVerticalSnapPreference() {
        return LinearSmoothScroller.SNAP_TO_START;
      }
    };

    binding.dim.setOnClickListener(v -> {
      if (sheetBehaviorMore.getState() == BottomSheetBehavior.STATE_EXPANDED) {
        sheetBehaviorMore.setState(BottomSheetBehavior.STATE_COLLAPSED);
        return;
      }

      if (sheetBehaviorReply.getState() == BottomSheetBehavior.STATE_EXPANDED) {
        sheetBehaviorReply.setState(BottomSheetBehavior.STATE_COLLAPSED);
      }
    });

    ViewCompat.postOnAnimation(binding.replyBottomSheet,
        () -> ViewCompat.postInvalidateOnAnimation(binding.replyBottomSheet));

    ViewCompat.postOnAnimation(binding.replyBottomSheet,
        () -> ViewCompat.postInvalidateOnAnimation(binding.reportBottomSheet));

    adapterComments = new AdapterComments(getActivity(), getContext(), this, this, true);
    adapterReplies = new AdapterComments(getActivity(), getContext(), this, this, false);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
    linearLayoutManager2.setOrientation(RecyclerView.VERTICAL);

    binding.commentsRecyclerView.setLayoutManager(linearLayoutManager);
    binding.commentsRecyclerView.setAdapter(adapterComments);
    binding.replyRecyclerView.setLayoutManager(linearLayoutManager2);
    binding.replyRecyclerView.setAdapter(adapterReplies);

    DisplayMetrics displayMetrics = new DisplayMetrics();
    Objects.requireNonNull(getActivity())
        .getWindowManager()
        .getDefaultDisplay()
        .getMetrics(displayMetrics);
    int displayHeight = displayMetrics.heightPixels;

    binding.replyBottomSheet.getLayoutParams().height =
        displayHeight - DCCastApplication.utils.pxFromDp(203);

    binding.btnCommentClose.setOnClickListener(onClickListenerForParent);
    binding.btnRefresh.setOnClickListener(this);
    binding.btnReplyClose.setOnClickListener(this);
  }

  private boolean toggleDim() {
    if (sheetBehaviorMore.getState() == BottomSheetBehavior.STATE_EXPANDED) {
      return true;
    }

    if (sheetBehaviorReply.getState() == BottomSheetBehavior.STATE_EXPANDED) {
      return true;
    }

    return false;
  }

  private void initReportDialog() {
    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
    reportDialogBinding =
        DataBindingUtil.inflate(layoutInflater, R.layout.dialog_video_report, null, false);

    reportVodLive = new AlertDialog.Builder(getContext())
        .setView(reportDialogBinding.getRoot()).create();

    reportDialogBinding.report1.setSelected(true);

    reportDialogBinding.cancelReport.setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_reply_close: {
        sheetBehaviorReply.setState(BottomSheetBehavior.STATE_COLLAPSED);
        break;
      }

      case R.id.btn_refresh: {
        getCommentListRequest(videoId);
        break;
      }

      case R.id.cancel_report: {
        dismissReport();
        break;
      }
    }
  }

  @Override public void onClickReply(int position, ModelComment modelComment) {
    adapterReplies.setParentId(modelComment.getId());
    binding.totalReplies.setText(String.valueOf(modelComment.getReplies().size()));
    adapterReplies.setComments(modelComment.getReplies());
    sheetBehaviorReply.setState(BottomSheetBehavior.STATE_EXPANDED);
  }

  @Override public void onClickMore(boolean isComment, ModelComment modelComment) {
    int bottomSheetHeight;
    bottomSheetHeight = 192;
    if (modelComment.getUser().getId() == LoginService.getLoginUser().getId()) {
      binding.btnDelete.setVisibility(View.VISIBLE);
      binding.btnDelete.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          DialogHelper.showAlertDialog(getActivity(), getString(R.string.confirm_delete_comment),
              null, getString(R.string.confirm_yes),
              new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                  deleteCommentRequest(isComment, modelComment);
                }
              }, getString(R.string.confirm_no), null);
        }
      });
    } else {
      bottomSheetHeight -= 48;
      binding.btnDelete.setVisibility(View.GONE);
    }

    if (isComment) {
      binding.btnReply.setVisibility(View.VISIBLE);
      binding.btnReply.setOnClickListener(v -> {
        if (modelComment.getReplies() != null) {
          binding.totalReplies.setText(String.valueOf(modelComment.getReplies().size()));
          adapterReplies.setComments(modelComment.getReplies());
        } else {
          adapterReplies.setComments(new ArrayList<>());
          binding.totalReplies.setText("0");
        }

        adapterReplies.setParentId(modelComment.getId());
        sheetBehaviorReply.setState(BottomSheetBehavior.STATE_EXPANDED);
        sheetBehaviorMore.setState(BottomSheetBehavior.STATE_COLLAPSED);
      });
    } else {
      binding.btnReply.setVisibility(View.GONE);
      bottomSheetHeight -= 48;
    }

    binding.btnEdit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        editModelComment = modelComment;
        onLayoutAddComment.onLayoutEditCommentClicked(isComment, modelComment);

        sheetBehaviorMore.setState(BottomSheetBehavior.STATE_COLLAPSED);
      }
    });

    binding.btnReport.setOnClickListener(v -> checkIsCommentReportedRequest(modelComment));

    if (LoginService.getLoginUser() != null
        && modelComment.getUser() != null
        && modelComment.getUser().getId() == LoginService.getLoginUser().getId()) {
      binding.btnEdit.setVisibility(View.VISIBLE);
    } else {
      binding.btnEdit.setVisibility(View.GONE);
      bottomSheetHeight -= 48;
    }

    binding.reportBottomSheet.getLayoutParams().height =
        DCCastApplication.utils.pxFromDp(bottomSheetHeight);
    binding.reportBottomSheet.requestLayout();
    sheetBehaviorMore.onLayoutChild(binding.mainLayout, binding.reportBottomSheet,
        ViewCompat.LAYOUT_DIRECTION_LTR);

    binding.dim.setVisibility(View.VISIBLE);
    sheetBehaviorMore.setState(BottomSheetBehavior.STATE_EXPANDED);
  }

  @Override public void onCommentLikeClicked(ModelComment modelComment) {
    if (modelComment.isLiked()) {
      createCommentLikeRequest(modelComment.getId());
    }
  }

  @Override public void onCommentDislikeClicked(ModelComment modelComment) {
    if (modelComment.isDisliked()) {
      createCommentDislikeRequest(modelComment.getId());
    }
  }

  private void deleteCommentRequest(boolean isComment, ModelComment modelComment) {
    Call<Void> call
        = APIClient.getClient().create(CommentAPIInterface.class)
        .deleteComment(modelComment.getId());
    call.enqueue(new Callback<Void>() {
      @Override
      public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
        if (isComment) {
          adapterComments.deleteComment(modelComment);

          sheetBehaviorMore.setState(BottomSheetBehavior.STATE_COLLAPSED);
          binding.totalComments.setText(
              String.valueOf(Integer.parseInt(binding.totalComments.getText().toString()) - 1));
        } else {

          for (int i = adapterReplies.comments.size() - 1; i >= 0; i--) {
            if (modelComment.getId() == adapterReplies.comments.get(i).getId()) {
              adapterReplies.comments.remove(i);
              adapterReplies.notifyDataSetChanged();
              break;
            }
          }

          sheetBehaviorMore.setState(BottomSheetBehavior.STATE_COLLAPSED);
          binding.totalReplies.setText(
              String.valueOf(Integer.parseInt(binding.totalReplies.getText().toString()) - 1));

          binding.totalComments.setText(
              String.valueOf(Integer.parseInt(binding.totalComments.getText().toString()) - 1));

          for (int i = 0; i < adapterComments.comments.size(); i++) {
            if (modelComment.getParent() == adapterComments.comments.get(i).getId()) {
              adapterComments.comments.get(i).setReplies(adapterReplies.comments);
              adapterComments.notifyDataSetChanged();
            }
          }
        }
      }

      @Override public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void createCommentLikeRequest(int commentId) {
    JSONObject commentBody = new JSONObject();
    try {
      commentBody.put("user", LoginService.getLoginUser().id);
      commentBody.put("comment", commentId);
      commentBody.put("is_dislike", false);
      commentBody.put("is_like", true);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    RequestBody commentRequestBody =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            (commentBody).toString());

    Call<ModelCommentActionResponse> call =
        APIClient.getClient()
            .create(CommentAPIInterface.class)
            .createCommentLike(commentRequestBody);

    call.enqueue(new Callback<ModelCommentActionResponse>() {
      @Override public void onResponse(@NonNull Call<ModelCommentActionResponse> call,
          @NonNull Response<ModelCommentActionResponse> response) {
        if (response != null) {
          Timber.e("CREATE COMMENT LIKE");
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelCommentActionResponse> call, @NonNull Throwable t) {
        call.cancel();
      }
    });
  }

  private void createCommentDislikeRequest(int commentId) {
    JSONObject commentBody = new JSONObject();
    try {
      commentBody.put("user", LoginService.getLoginUser().id);
      commentBody.put("comment", commentId);
      commentBody.put("is_dislike", true);
      commentBody.put("is_like", false);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    RequestBody commentRequestBody =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            (commentBody).toString());

    Call<ModelCommentActionResponse> call =
        APIClient.getClient()
            .create(CommentAPIInterface.class)
            .createCommentLike(commentRequestBody);

    call.enqueue(new Callback<ModelCommentActionResponse>() {
      @Override public void onResponse(@NonNull Call<ModelCommentActionResponse> call,
          @NonNull Response<ModelCommentActionResponse> response) {
        if (response != null) {
          Timber.e("CREATE COMMENT DISLIKE");
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelCommentActionResponse> call, @NonNull Throwable t) {
        call.cancel();
      }
    });
  }

  private void createCommentRequest(boolean isComment, String message, int parentId) {
    JSONObject commentJson = new JSONObject();

    try {
      commentJson.put("media", videoId);
      commentJson.put("user", LoginService.getLoginUser().id);
      commentJson.put("text", message);

      if (parentId != -1) {
        commentJson.put("parent", parentId);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    if (isComment) {
      adapterComments.showLoadFirst();
    } else {
      adapterReplies.showLoadFirst();
    }

    RequestBody commentRequestBody =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            (commentJson).toString());

    Call<ModelCreateCommentResponse> call =
        APIClient.getClient()
            .create(CommentAPIInterface.class)
            .createComment(commentRequestBody);

    call.enqueue(new Callback<ModelCreateCommentResponse>() {
      @Override public void onResponse(@NonNull Call<ModelCreateCommentResponse> call,
          @NonNull Response<ModelCreateCommentResponse> response) {
        ModelCreateCommentResponse commentResponse = response.body();

        if (commentResponse != null) {
          ModelComment modelComment = new ModelComment();
          modelComment.setId(commentResponse.getId());
          modelComment.setMedia(commentResponse.getMedia());
          modelComment.setText(commentResponse.getText());

          if (commentResponse.getParent() != null) {
            modelComment.setParent(commentResponse.getParent());
            modelComment.setLevel(1);
          } else {
            modelComment.setParent(-1);
            modelComment.setLevel(0);
          }

          modelComment.setUser(LoginService.getLoginUser());
          modelComment.setLike(0);
          modelComment.setDisLike(0);
          Calendar now = Calendar.getInstance();
          int year = now.get(Calendar.YEAR);
          int month = now.get(Calendar.MONTH) + 1;
          int day = now.get(Calendar.DAY_OF_MONTH);
          modelComment.setCreated(
              year + "-" + (month < 10 ? ("0" + month) : month) + "-" + (day < 10 ? ("0" + day)
                  : day));
          modelComment.setDcconUrl(commentResponse.getDcconUrl());

          if (isComment) {
            adapterComments.addComment(modelComment);
            adapterComments.hideLoadFirst();
            binding.totalComments.setText(
                String.valueOf(Integer.parseInt(binding.totalComments.getText().toString()) + 1));
            smoothScroller.setTargetPosition(0);
            binding.commentsRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
          } else {
            adapterReplies.addComment(modelComment);
            adapterReplies.hideLoadFirst();
            binding.totalReplies.setText(
                String.valueOf(Integer.parseInt(binding.totalReplies.getText().toString()) + 1));
            binding.totalComments.setText(
                String.valueOf(Integer.parseInt(binding.totalComments.getText().toString()) + 1));
            smoothScroller.setTargetPosition(0);
            binding.replyRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);

            for (int i = 0; i < adapterComments.comments.size(); i++) {
              ModelComment modelComment_ = adapterComments.comments.get(i);
              if (modelComment_.getId() == parentId) {
                modelComment_.setReplies(adapterReplies.comments);
                adapterComments.notifyDataSetChanged();
              }
            }
          }
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelCreateCommentResponse> call, @NonNull Throwable t) {
        call.cancel();
        if (isComment) {
          adapterComments.hideLoadFirst();
        } else {
          adapterReplies.hideLoadFirst();
        }
      }
    });
  }

  private void updateCommentRequest(boolean isComment, ModelComment comment) {
    JsonObject commentJson = new JsonObject();

    commentJson.addProperty("media", videoId);
    commentJson.addProperty("user", LoginService.getLoginUser().id);
    if (comment.getDcconUrl() != null) {
      commentJson.addProperty("dccon_url", comment.getDcconUrl());
      commentJson.add("text", null);
    } else {
      commentJson.addProperty("text", comment.getText());
      commentJson.add("dccon_url", null);
    }

    if (comment.getParent() != -1) {
      commentJson.addProperty("parent", comment.getParent());
    }

    if (comment.getReplies() != null) {
      commentJson.addProperty("replies", comment.getReplies().toString());
    }

    RequestBody commentRequestBody =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            (commentJson).toString());

    Call<ModelCreateCommentResponse> call =
        APIClient.getClient()
            .create(CommentAPIInterface.class)
            .updateComment(comment.getId(), commentRequestBody);

    call.enqueue(new Callback<ModelCreateCommentResponse>() {
      @Override public void onResponse(@NonNull Call<ModelCreateCommentResponse> call,
          @NonNull Response<ModelCreateCommentResponse> response) {
        ModelCreateCommentResponse commentResponse = response.body();

        if (commentResponse != null) {
          ModelComment modelComment = new ModelComment();
          modelComment.setId(commentResponse.getId());
          modelComment.setMedia(commentResponse.getMedia());
          modelComment.setText(commentResponse.getText());

          if (commentResponse.getParent() != null) {
            modelComment.setParent(commentResponse.getParent());
            modelComment.setLevel(1);
          } else {
            modelComment.setParent(-1);
            modelComment.setLevel(0);
          }

          modelComment.setUser(LoginService.getLoginUser());
          modelComment.setLike(commentResponse.getLike());
          modelComment.setDisLike(commentResponse.getDisLike());
          Calendar now = Calendar.getInstance();
          int year = now.get(Calendar.YEAR);
          int month = now.get(Calendar.MONTH) + 1;
          int day = now.get(Calendar.DAY_OF_MONTH);
          modelComment.setCreated(
              year + "-" + (month < 10 ? ("0" + month) : month) + "-" + (day < 10 ? ("0" + day)
                  : day));
          modelComment.setDcconUrl(commentResponse.getDcconUrl());

          if (editModelComment != null && editModelComment.getId() == modelComment.getId()) {
            modelComment.setReplies(editModelComment.getReplies());

            modelComment.setLiked(editModelComment.isLiked());
            modelComment.setDisliked(editModelComment.isDisliked());
          }

          new Handler().postDelayed(() -> getActivity().runOnUiThread(() -> {
            if (isComment) {
              adapterComments.updateComment(modelComment);
              adapterComments.hideLoadFirst();
            } else {
              adapterReplies.updateComment(modelComment);
              adapterReplies.hideLoadFirst();
            }
          }), 200);
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelCreateCommentResponse> call, @NonNull Throwable t) {
        call.cancel();
        if (isComment) {
          adapterComments.hideLoadFirst();
        } else {
          adapterReplies.hideLoadFirst();
        }
      }
    });
  }

  private void createCommentStickerRequest(boolean isComment, String dcconUrl, int parentId) {
    JSONObject commentJson = new JSONObject();

    try {
      commentJson.put("media", videoId);
      commentJson.put("user", LoginService.getLoginUser().id);
      commentJson.put("dccon_url", dcconUrl);
      commentJson.put("text", null);

      if (parentId != -1) {
        commentJson.put("parent", parentId);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    Log.e("json", commentJson.toString());

    RequestBody commentRequestBody =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
            (commentJson).toString());

    Call<ModelCreateCommentResponse> call =
        APIClient.getClient()
            .create(CommentAPIInterface.class)
            .createComment(commentRequestBody);

    call.enqueue(new Callback<ModelCreateCommentResponse>() {
      @Override public void onResponse(@NonNull Call<ModelCreateCommentResponse> call,
          @NonNull Response<ModelCreateCommentResponse> response) {
        ModelCreateCommentResponse commentResponse = response.body();

        if (commentResponse != null) {
          ModelComment modelComment = new ModelComment();
          modelComment.setId(commentResponse.getId());
          modelComment.setMedia(commentResponse.getMedia());
          modelComment.setText(commentResponse.getText());

          if (commentResponse.getParent() != null) {
            modelComment.setParent(commentResponse.getParent());
            modelComment.setLevel(1);
          } else {
            modelComment.setParent(-1);
            modelComment.setLevel(0);
          }

          modelComment.setUser(LoginService.getLoginUser());
          modelComment.setLike(0);
          modelComment.setDisLike(0);
          Calendar now = Calendar.getInstance();
          int year = now.get(Calendar.YEAR);
          int month = now.get(Calendar.MONTH) + 1;
          int day = now.get(Calendar.DAY_OF_MONTH);
          modelComment.setCreated(
              year + "-" + (month < 10 ? ("0" + month) : month) + "-" + (day < 10 ? ("0" + day)
                  : day));
          modelComment.setDcconUrl(commentResponse.getDcconUrl());

          if (isComment) {
            adapterComments.addComment(modelComment);
            adapterComments.hideLoadFirst();
            binding.totalComments.setText(
                String.valueOf(Integer.parseInt(binding.totalComments.getText().toString()) + 1));
            smoothScroller.setTargetPosition(0);
            binding.commentsRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
          } else {
            adapterReplies.addComment(modelComment);
            adapterReplies.hideLoadFirst();
            binding.totalComments.setText(
                String.valueOf(Integer.parseInt(binding.totalComments.getText().toString()) + 1));
            binding.totalReplies.setText(
                String.valueOf(Integer.parseInt(binding.totalReplies.getText().toString()) + 1));
            smoothScroller.setTargetPosition(0);
            binding.replyRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);

            for (int i = 0; i < adapterComments.comments.size(); i++) {
              ModelComment modelComment_ = adapterComments.comments.get(i);
              if (modelComment_.getId() == parentId) {
                modelComment_.setReplies(adapterReplies.comments);
                adapterComments.notifyDataSetChanged();
              }
            }
          }

          //sheetBehaviorSticker.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
      }

      @Override
      public void onFailure(@NonNull Call<ModelCreateCommentResponse> call, @NonNull Throwable t) {
        call.cancel();
        if (isComment) {
          adapterComments.hideLoadFirst();
        } else {
          adapterReplies.hideLoadFirst();
        }
      }
    });
  }

  private void getCommentListRequest(int id) {
    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser == null) {
      return;
    }
    adapterComments.clearComments();
    binding.progressBarVod.setVisibility(View.VISIBLE);

    Call<ModelCommentActionWrapper> call = APIClient.getClient()
        .create(CommentAPIInterface.class)
        .getCommentList(id, loginUser.id);

    call.enqueue(new Callback<ModelCommentActionWrapper>() {
      @Override public void onResponse(@NonNull Call<ModelCommentActionWrapper> call,
          @NonNull Response<ModelCommentActionWrapper> response) {
        ModelCommentActionWrapper commentWrapper = response.body();
        adapterComments.setComments(Objects.requireNonNull(commentWrapper).commentList);

        if (commentWrapper.commentList.isEmpty()) {
          adapterComments.showNoDataViewHolder();
        } else {
          adapterComments.hideNoDataViewHolder();
        }

        binding.progressBarVod.setVisibility(View.GONE);
        binding.totalComments.setText(String.valueOf(commentWrapper.getTotalCommentCount()));
      }

      @Override
      public void onFailure(@NonNull Call<ModelCommentActionWrapper> call, @NonNull Throwable t) {
        adapterComments.showNoDataViewHolder();
        call.cancel();
        binding.progressBarVod.setVisibility(View.GONE);
      }
    });
  }

  private void checkIsCommentReportedRequest(ModelComment modelComment) {
    Call<JsonObject> checkIsCommentReported =
        APIClient.getClient().create(CommentAPIInterface.class)
            .checkIsCommentReported(modelComment.getId());
    checkIsCommentReported.enqueue(new Callback<JsonObject>() {
      @Override public void onResponse(@NonNull Call<JsonObject> call,
          @NonNull Response<JsonObject> response) {
        JsonObject jsonObject = response.body();
        if (jsonObject.has("count")) {
          int count = jsonObject.get("count").getAsInt();
          if (count == 0) {
            sheetBehaviorMore.setState(BottomSheetBehavior.STATE_COLLAPSED);

            reportDialogBinding.reportTitle.setText(getString(R.string.report_comment_title));

            reportDialogBinding.sendReport.setOnClickListener(v -> {
              if (reportDialogBinding.reportField.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), getString(R.string.validate_report_reason),
                    Toast.LENGTH_SHORT).show();
              } else {
                int userId = LoginService.getLoginUser().id;
                String division = DCCastApplication.utils.VIDEO_CHOOSE_TYPE;
                int comment = modelComment.getId();

                String kind = "";
                if (reportDialogBinding.report1.isChecked()) {
                  kind = reportDialogBinding.report1.getText().toString();
                }

                if (reportDialogBinding.report2.isChecked()) {
                  kind = reportDialogBinding.report2.getText().toString();
                }

                if (reportDialogBinding.report3.isChecked()) {
                  kind = reportDialogBinding.report3.getText().toString();
                }

                if (reportDialogBinding.report4.isChecked()) {
                  kind = reportDialogBinding.report4.getText().toString();
                }

                int media = videoId;
                JSONObject reportJson = new JSONObject();
                try {
                  reportJson.put("user", userId);
                  reportJson.put("division", division);
                  reportJson.put("comment", comment);
                  reportJson.put("kind", kind);
                  reportJson.put("media", media);
                } catch (JSONException e) {
                  e.printStackTrace();
                }

                RequestBody report =
                    RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                        (reportJson).toString());

                Call<JsonObject> callCreateCommentReport =
                    APIClient.getClient()
                        .create(CommentAPIInterface.class)
                        .createCommentReport(report);
                callCreateCommentReport.enqueue(new Callback<JsonObject>() {
                  @Override
                  public void onResponse(@NonNull Call<JsonObject> call,
                      @NonNull Response<JsonObject> response) {
                    Toast.makeText(getContext(), getString(R.string.report_comment_success),
                        Toast.LENGTH_SHORT)
                        .show();
                    dismissReport();
                  }

                  @Override
                  public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    dismissReport();
                  }
                });
              }
            });

            reportVodLive.show();
          } else {
            sheetBehaviorMore.setState(BottomSheetBehavior.STATE_COLLAPSED);
            Toast.makeText(getContext(), getString(R.string.already_reported), Toast.LENGTH_SHORT)
                .show();
          }
        }
      }

      @Override public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void dismissReport() {
    if (reportVodLive != null && reportVodLive.isShowing()) {
      reportVodLive.dismiss();
    }
  }

  @Override public void onCommentLayoutClicked() {
    if (onLayoutAddComment != null) {
      boolean isComment = !(sheetBehaviorReply.getState() == BottomSheetBehavior.STATE_EXPANDED);
      onLayoutAddComment.onLayoutAddCommentClicked(isComment);
    }
  }

  public interface OnLayoutAddComment {
    void onLayoutAddCommentClicked(boolean isComment);

    void onLayoutEditCommentClicked(boolean isComment, ModelComment comment);
  }

  public interface OnStickerSelectedListenerForComment {
    void onStickerSelected(boolean isEdit, ModelSticker sticker);
  }

  public interface OnCommentAddListenerForComment {
    void onCommentSelected(boolean isEdit, String comment);
  }
}
