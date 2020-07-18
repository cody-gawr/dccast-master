package notq.dccast.model.user.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import notq.dccast.model.user.ModelUser;

public class ModelUserProfileWrapper implements Serializable {
    @SerializedName("results")
    @Expose
    public List<ModelUser> users;
    @SerializedName("next")
    public String next;
}
