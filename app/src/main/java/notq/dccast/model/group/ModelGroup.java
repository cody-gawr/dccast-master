package notq.dccast.model.group;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelGroup implements Serializable {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("message")
    public String message;
    @SerializedName("contact_person")
    public int contactPerson;
    @SerializedName("profile_img")
    @Expose
    public String profileImg;
    @SerializedName("members")
    public List<Integer> members;
    @SerializedName("media_list")
    public List<Integer> medias;

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(int contactPerson) {
        this.contactPerson = contactPerson;
    }

    public List<Integer> getMembers() {
        return members;
    }

    public void setMembers(List<Integer> members) {
        this.members = members;
    }

    public List<Integer> getMedias() {
        return medias;
    }

    public void setMedias(List<Integer> medias) {
        this.medias = medias;
    }

}
