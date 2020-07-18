package notq.dccast.model.friends;

import com.google.gson.annotations.SerializedName;

public class ModelFriendRequestResult {
    @SerializedName("result")
    public boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
