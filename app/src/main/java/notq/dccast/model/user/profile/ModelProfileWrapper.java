package notq.dccast.model.user.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelProfileWrapper implements Serializable {
    @SerializedName("results")
    @Expose
    public List<ModelProfile> profiles;
    @SerializedName("next")
    public String next;

    public List<ModelProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ModelProfile> profiles) {
        this.profiles = profiles;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
