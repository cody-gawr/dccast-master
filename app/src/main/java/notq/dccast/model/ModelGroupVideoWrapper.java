package notq.dccast.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelGroupVideoWrapper implements Serializable {
    @SerializedName("results")
    public List<ModelGroupVideo> mediaList;
    @SerializedName("next")
    public String next;
}
