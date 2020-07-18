package notq.dccast.model.user;

import com.google.gson.annotations.SerializedName;

public class ModelResult {
    @SerializedName("results")
    public boolean result;
    @SerializedName("success")
    public boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
