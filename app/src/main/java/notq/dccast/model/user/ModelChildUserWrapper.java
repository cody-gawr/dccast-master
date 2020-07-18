package notq.dccast.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelChildUserWrapper {
    @SerializedName("results")
    public List<ModelChildUser> users;
    @SerializedName("next")
    public String next;

    public List<ModelChildUser> getUsers() {
        return users;
    }

    public void setUsers(List<ModelChildUser> users) {
        this.users = users;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
