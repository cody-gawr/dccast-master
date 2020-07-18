package notq.dccast.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelLoginUserWrapper implements Serializable {
    @SerializedName("profile")
    @Expose
    public ModelUser user;

    public ModelUser getUser() {
        return user;
    }

    public void setUser(ModelUser user) {
        this.user = user;
    }
}
