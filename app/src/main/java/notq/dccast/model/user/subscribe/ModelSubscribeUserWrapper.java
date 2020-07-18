package notq.dccast.model.user.subscribe;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelSubscribeUserWrapper {
    public List<ModelSubscribeUser> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(
        List<ModelSubscribeUser> subscribers) {
        this.subscribers = subscribers;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    @SerializedName("results")
    public List<ModelSubscribeUser> subscribers;
    @SerializedName("next")
    public String next;
}
