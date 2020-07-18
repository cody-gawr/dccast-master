package notq.dccast.screens.home.video_detail;

import android.app.Activity;
import android.content.Context;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import notq.dccast.model.LiveChatModel;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.home.adapter.LiveChatAdapter;
import notq.dccast.util.LoginService;
import notq.dccast.util.Url;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.Timber;

public class LiveChatHelper {
  private final int TIMER_DURATION = 5000;
  boolean isJoined = false;
  private Socket mSocket;
  private int videoId;
  private int userId;
  private Context context;
  private LiveChatAdapter liveChatAdapter;
  private boolean timerStared = false;
  private boolean isHost = false;
  private Timer getMembersTimer;
  private onReceivedMemberCount onReceivedMemberCount;
  private onUserMessageBlocked onUserMessageBlocked;
  private Emitter.Listener onConnect = new Emitter.Listener() {
    @Override public void call(Object... args) {
      JSONObject params = new JSONObject();
      try {
        params.put("room", videoId);
        params.put("userId", userId);
      } catch (JSONException ignored) {
      }
      mSocket.emit("join", params);
    }
  };
  private Emitter.Listener onNewMessage = new Emitter.Listener() {
    @Override
    public void call(final Object... args) {
      JSONObject data = (JSONObject) args[0];
      ((Activity) context).runOnUiThread(() -> {

        try {
          if (data != null) {
            Timber.e("NEW MESSAGE: " + data.toString());
            if (data.has("message")) {
              LiveChatModel chatModel = new LiveChatModel(data.getJSONObject("message"));

              if (!chatModel.getType().isEmpty() && !chatModel.getUserNickname().isEmpty()) {

                ModelUser loginUser = LoginService.getLoginUser();
                if (loginUser != null) {
                  if (chatModel.getType().equalsIgnoreCase("warning") && loginUser.getNickName()
                      .equalsIgnoreCase(chatModel.getUserNickname())) {
                    if (onUserMessageBlocked != null) {
                      onUserMessageBlocked.onUserMessageBlocked(false, 0);
                    }
                  }

                  if (chatModel.getType().equalsIgnoreCase("force") && loginUser.getNickName()
                      .equalsIgnoreCase(chatModel.getUserNickname())) {
                    onUserMessageBlocked.onUserMessageBlocked(true, 0);
                  }
                }
                return;
              }

              if (chatModel.getText() != null || chatModel.getStickerUrl() != null) {
                liveChatAdapter.addMessage(chatModel);
              }
            }
          } else {
            Timber.e("DATA NULL");
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      });
    }
  };
  private Emitter.Listener allMembersCount = new Emitter.Listener() {
    @Override
    public void call(final Object... args) {
      if (args.length > 0) {
        JSONObject data = (JSONObject) args[0];

        int memberCount = 0;

        if (data.has("members")) {
          try {
            memberCount = data.getInt("members");
          } catch (JSONException e) {
            e.printStackTrace();
          } finally {
            if (onReceivedMemberCount != null) {
              onReceivedMemberCount.onReceivedMemberCount(memberCount);
            }
          }
        }
      }
    }
  };
  private Emitter.Listener onDisconnect = new Emitter.Listener() {
    @Override public void call(Object... args) {
      isJoined = false;
      cancelTimer();
    }
  };
  private Emitter.Listener onConnectError = new Emitter.Listener() {
    @Override public void call(Object... args) {
      isJoined = false;
    }
  };
  private Emitter.Listener onLogin = new Emitter.Listener() {
    @Override public void call(Object... args) {
      isJoined = true;

      //if (!isHost) {
      //  return;
      //}
      if (timerStared) {
        return;
      }
      timerStared = true;
      getMembersTimer = new Timer();
      getMembersTimer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
          getMemberCount();
        }
      }, TIMER_DURATION, TIMER_DURATION);
    }
  };

  public LiveChatHelper(Context context, int videoId,
      LiveChatAdapter liveChatAdapter) {
    this.videoId = videoId;
    this.context = context;
    this.liveChatAdapter = liveChatAdapter;

    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser != null) {
      userId = loginUser.getId();
    }

    isHost = true;
  }

  public LiveChatHelper(Context context, ModelVideo modelVideo,
      LiveChatAdapter liveChatAdapter) {
    this.videoId = modelVideo.getId();
    this.context = context;
    this.liveChatAdapter = liveChatAdapter;

    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser != null) {
      userId = loginUser.getId();
    }
  }

  public LiveChatHelper(Context context, ModelListHeader modelHeaderVideo,
      LiveChatAdapter liveChatAdapter) {
    this.videoId = modelHeaderVideo.getId();
    this.context = context;
    this.liveChatAdapter = liveChatAdapter;

    ModelUser loginUser = LoginService.getLoginUser();
    if (loginUser != null) {
      userId = loginUser.getId();
    }
  }

  public void setOnReceivedMemberCount(
      LiveChatHelper.onReceivedMemberCount onReceivedMemberCount) {
    this.onReceivedMemberCount = onReceivedMemberCount;
  }

  public void setOnUserMessageBlocked(
      LiveChatHelper.onUserMessageBlocked onUserMessageBlocked) {
    this.onUserMessageBlocked = onUserMessageBlocked;
  }

  private void cancelTimer() {
    timerStared = false;
    if (getMembersTimer != null) {
      getMembersTimer.cancel();
    }
  }

  public void stopGetMembersTimer() {
    if (getMembersTimer != null) {
      getMembersTimer.cancel();
    }
  }

  public void onSocket() {
    try {
      mSocket = IO.socket(Url.hostUrl);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }

    mSocket.on(Socket.EVENT_CONNECT, onConnect);
    mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
    mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
    mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
    mSocket.on("new message", onNewMessage);
    mSocket.on("user_joined", onLogin);
    mSocket.on("all_members", allMembersCount);
    mSocket.connect();
  }

  public void offSocket() {
    if (mSocket != null) {
      mSocket.disconnect();
      mSocket.off(Socket.EVENT_CONNECT, onConnect);
      mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
      mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
      mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
      mSocket.off("new message", onNewMessage);
      mSocket.off("user_joined", onLogin);
      mSocket.off("all_members", allMembersCount);
    }
  }

  public void getMemberCount() {
    if (LoginService.getLoginUser() == null) {
      return;
    }

    //JSONObject params = new JSONObject();
    //try {
    //  params.put("room", String.valueOf(videoId));
    //} catch (JSONException ignored) {
    //}
    mSocket.emit("members count", String.valueOf(videoId));
  }

  public void sendMessage(String message) {
    JSONObject params = new JSONObject();
    if (LoginService.getLoginUser() != null) {
      ModelUser loginUser = LoginService.getLoginUser();
      try {
        params.put("text", message);
        params.put("media", videoId);
        params.put("room", videoId);
        params.put("type", "");
        params.put("username", loginUser.getNickName());
        //params.put("user", loginUser.getNickName());
        params.put("userId", loginUser.getId());
        params.put("user", String.valueOf(loginUser.getId()));
        String date = (new Date()).toString();
        params.put("created", date);
      } catch (JSONException ignored) {
        Timber.e("ERROR: " + ignored.getMessage());
      }

      Timber.e("SEND MESSAGE: " + params.toString());

      mSocket.emit("new message", params);
    }
  }

  public void sendSticker(String stickerUrl) {
    JSONObject params = new JSONObject();
    if (LoginService.getLoginUser() != null) {
      ModelUser loginUser = LoginService.getLoginUser();
      try {
        params.put("dcconUrl", stickerUrl);
        params.put("media", videoId);
        params.put("room", videoId);
        params.put("type", "");
        params.put("username", loginUser.getNickName());
        //params.put("user", loginUser.getNickName());
        params.put("user", String.valueOf(loginUser.getId()));
        params.put("userId", loginUser.getId());
        String date = (new Date()).toString();
        params.put("created", date);
      } catch (JSONException ignored) {
      }

      mSocket.emit("new message", params);
    }
  }

  public void sendMessageBlocked(String blockUserNickname) {
    JSONObject params = new JSONObject();
    if (LoginService.getLoginUser() != null) {
      ModelUser loginUser = LoginService.getLoginUser();
      try {
        params.put("text", blockUserNickname + " 님이 경고를 받아 채팅이 일시 중지 되었습니다.-");
        params.put("media", videoId);
        params.put("room", videoId);
        params.put("type", "warning");
        params.put("username", loginUser.getNickName());
        params.put("user", blockUserNickname);
        params.put("userId", loginUser.getId());
        String date = (new Date()).toString();
        params.put("created", date);
      } catch (JSONException ignored) {
      }

      mSocket.emit("new message", params);
    }
  }

  public void sendUserBlocked(String blockUserNickname) {
    JSONObject params = new JSONObject();
    if (LoginService.getLoginUser() != null) {
      ModelUser loginUser = LoginService.getLoginUser();
      try {
        params.put("text", "님이 강제 퇴장 되었습니다.-");
        params.put("media", videoId);
        params.put("room", videoId);
        params.put("type", "force");
        params.put("username", loginUser.getNickName());
        params.put("user", blockUserNickname);
        params.put("userId", loginUser.getId());
        String date = (new Date()).toString();
        params.put("created", date);
      } catch (JSONException ignored) {
      }

      mSocket.emit("new message", params);
    }
  }

  public interface onReceivedMemberCount {
    void onReceivedMemberCount(int memberCount);
  }

  public interface onUserMessageBlocked {
    void onUserMessageBlocked(boolean permanent, long duration);
  }
}
