package notq.dccast.model;

import com.google.gson.annotations.SerializedName;

public class ModelPublicKeyResult {
    @SerializedName("result")
    public boolean result;
    @SerializedName("app_id")
    public String appId;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
