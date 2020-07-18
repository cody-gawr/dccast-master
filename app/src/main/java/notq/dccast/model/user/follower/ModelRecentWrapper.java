package notq.dccast.model.user.follower;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import notq.dccast.model.user.profile.ModelProfile;

public class ModelRecentWrapper {
    @SerializedName("results")
    public List<ModelProfile> recents;
    @SerializedName("next")


    public String next;

    public List<ModelProfile> getRecents() {
        return recents;
    }

    public void setRecents(List<ModelProfile> recents) {
        this.recents = recents;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
