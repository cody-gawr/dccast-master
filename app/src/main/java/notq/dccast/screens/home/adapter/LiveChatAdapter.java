package notq.dccast.screens.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.DCCastApplication;
import notq.dccast.R;
import notq.dccast.databinding.VhLiveChatBinding;
import notq.dccast.model.LiveChatModel;
import notq.dccast.util.Util;

public class LiveChatAdapter extends RecyclerView.Adapter<LiveChatAdapter.ViewHolderLiveChat> {
  private List<LiveChatModel> messages;
  private Context context;
  private RecyclerView chatRecyclerView;
  private RecyclerView chatRecyclerViewFullScreen;
  private boolean isFullScreen;
  private OnBlockUserMessage onBlockUserMessage;
  private int textColor = 0;

  public LiveChatAdapter(Context context, RecyclerView chatRecyclerView) {
    this.context = context;
    this.chatRecyclerView = chatRecyclerView;
    messages = new ArrayList<>();
  }

  public LiveChatAdapter(Context context, RecyclerView chatRecyclerView,
      RecyclerView chatRecyclerViewFullScreen) {
    this.context = context;
    this.chatRecyclerView = chatRecyclerView;
    this.chatRecyclerViewFullScreen = chatRecyclerViewFullScreen;
    messages = new ArrayList<>();
  }

  public void setOnBlockUserMessage(
      OnBlockUserMessage onBlockUserMessage) {
    this.onBlockUserMessage = onBlockUserMessage;
  }

  public void setTextColor(int color) {
    this.textColor = color;
    notifyDataSetChanged();
  }

  public void setFullScreen(boolean isFullScreen) {
    this.isFullScreen = isFullScreen;
    notifyDataSetChanged();
  }

  public void addMessage(LiveChatModel chatModel) {
    messages.add(chatModel);
    notifyItemInserted(getItemCount() - 1);

    if (chatRecyclerViewFullScreen != null) {
      chatRecyclerViewFullScreen.smoothScrollToPosition(getItemCount() - 1);
    } else if (chatRecyclerView != null) {
      chatRecyclerView.smoothScrollToPosition(getItemCount() - 1);
    }
  }

  @NonNull @Override
  public ViewHolderLiveChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    return new ViewHolderLiveChat(
        DataBindingUtil.inflate(layoutInflater, R.layout.vh_live_chat, parent, false));
  }

  @Override public void onBindViewHolder(@NonNull ViewHolderLiveChat holder, int position) {
    holder.setMessage(messages.get(position));
  }

  @Override public int getItemCount() {
    return messages == null ? 0 : messages.size();
  }

  public interface OnBlockUserMessage {
    void showBlockOptions(int userId, String userNickName);
  }

  class ViewHolderLiveChat extends RecyclerView.ViewHolder {
    private VhLiveChatBinding chatBinding;

    ViewHolderLiveChat(@NonNull VhLiveChatBinding chatBinding) {
      super(chatBinding.getRoot());
      this.chatBinding = chatBinding;

      if (chatRecyclerViewFullScreen != null) {
        chatBinding.wrapper.setBackgroundResource(R.drawable.chat_text_background_default);
      } else {
        chatBinding.wrapper.setBackgroundResource(R.drawable.chat_text_background);
      }
    }

    @SuppressLint("SetTextI18n")
    void setMessage(LiveChatModel message) {
      chatBinding.user.setText(message.getUserName() + ":");

      chatBinding.wrapper.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (onBlockUserMessage != null) {
            onBlockUserMessage.showBlockOptions(message.getUserId(), message.getUserName());
          }
        }
      });

      if (message.getText() != null) {
        chatBinding.sticker.setVisibility(View.GONE);
        chatBinding.text.setVisibility(View.VISIBLE);
        chatBinding.text.setText(message.getText());
      } else {
        if (message.getStickerUrl() != null) {
          chatBinding.sticker.setVisibility(View.VISIBLE);
          chatBinding.text.setVisibility(View.GONE);

          int size = DCCastApplication.utils.pxFromDp(100);
          Glide.with(context).load(Util.getValidateUrl(message.getStickerUrl()))
              .apply(new RequestOptions().override(size, size).centerCrop())
              .into(chatBinding.sticker);
        } else {
          chatBinding.sticker.setVisibility(View.GONE);
          chatBinding.text.setVisibility(View.GONE);
        }
      }

      if (isFullScreen) {
        chatBinding.wrapper.post(new Runnable() {
          @Override public void run() {
            chatBinding.wrapper.setBackgroundResource(R.drawable.chat_text_background);
          }
        });

        chatBinding.user.post(new Runnable() {
          @Override public void run() {
            chatBinding.user.setTextColor(Color.WHITE);
          }
        });

        chatBinding.text.post(new Runnable() {
          @Override public void run() {
            chatBinding.text.setTextColor(Color.WHITE);
          }
        });
      } else {
        chatBinding.wrapper.post(new Runnable() {
          @Override public void run() {
            if (chatRecyclerViewFullScreen != null) {
              chatBinding.wrapper.setBackgroundResource(R.drawable.chat_text_background_default);
            } else {
              chatBinding.wrapper.setBackgroundResource(R.drawable.chat_text_background);
            }
          }
        });

        if (textColor != 0) {
          chatBinding.user.post(new Runnable() {
            @Override public void run() {
              chatBinding.user.setTextColor(textColor);
            }
          });

          chatBinding.text.post(new Runnable() {
            @Override public void run() {
              chatBinding.text.setTextColor(textColor);
            }
          });
        } else {
          chatBinding.user.post(new Runnable() {
            @Override public void run() {
              chatBinding.user.setTextColor(Color.BLACK);
            }
          });

          chatBinding.text.post(new Runnable() {
            @Override public void run() {
              chatBinding.text.setTextColor(Color.BLACK);
            }
          });
        }
      }
    }
  }
}
