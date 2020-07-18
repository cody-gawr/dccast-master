package notq.dccast.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelUserWrapper implements Serializable {
    @SerializedName("results")
    @Expose
    public List<ModelUser> users;
    @SerializedName("next")
    @Expose
    public String next;

    @SerializedName("count")
    @Expose
    public int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ModelUser> getUsers() {
        return users;
    }

    public void setUsers(List<ModelUser> users) {
        this.users = users;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
