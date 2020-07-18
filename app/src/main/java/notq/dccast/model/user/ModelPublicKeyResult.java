package notq.dccast.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelPublicKeyResult implements Serializable {
    @SerializedName("result")
    @Expose
    public boolean result;

    @SerializedName("app_id")
    @Expose
    public String appId;

    @SerializedName("cause")
    @Expose
    public String cause;

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

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
