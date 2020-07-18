package notq.dccast.model;

import org.json.JSONException;
import org.json.JSONObject;

public class LiveChatModel {
  private int mediaId;
  private int userId;
  private String text;
  private String type;
  private String userNickname;
  private String userName;
  private String stickerUrl;
  private String createdDate;

  public LiveChatModel(JSONObject jsonObject) {
    try {
      userName = jsonObject.getString("username");

      mediaId = jsonObject.getInt("media");

      if (jsonObject.has("text")) {
        if (!jsonObject.getString("text").isEmpty()) {
          text = jsonObject.getString("text");
        }
      }

      if (jsonObject.has("user")) {
        if (!jsonObject.getString("user").isEmpty()) {
          userNickname = jsonObject.getString("user");
        }
      }

      if (jsonObject.has("userId")) {
        userId = jsonObject.getInt("userId");
      }

      if (jsonObject.has("type")) {
        if (!jsonObject.getString("type").isEmpty()) {
          type = jsonObject.getString("type");
        } else {
          userId = jsonObject.getInt("user");
        }
      } else {
        userId = jsonObject.getInt("user");
      }

      if (jsonObject.has("dcconUrl")) {
        if (!jsonObject.getString("dcconUrl").isEmpty()) {
          stickerUrl = jsonObject.getString("dcconUrl");
        }
      }

      createdDate = jsonObject.getString("created");
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public String getUserNickname() {
    if (userNickname == null) {
      return "";
    }
    return userNickname;
  }

  public int getMediaId() {
    return mediaId;
  }

  public int getUserId() {
    return userId;
  }

  public String getText() {
    return text;
  }

  public String getType() {
    if (type == null) {
      return "";
    }
    return type;
  }

  public String getUserName() {
    return userName;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public String getStickerUrl() {
    return stickerUrl;
  }
}
