package notq.dccast.model.notification;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelNotificationWrapper implements Serializable {
    @SerializedName("results")
    public List<ModelNotification> notifications;
    @SerializedName("next")
    public String next;

    public List<ModelNotification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<ModelNotification> notifications) {
        this.notifications = notifications;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
