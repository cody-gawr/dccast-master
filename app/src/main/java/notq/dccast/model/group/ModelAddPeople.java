package notq.dccast.model.group;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelAddPeople implements Parcelable {
    public static final Parcelable.Creator<ModelAddPeople> CREATOR = new Parcelable.Creator<ModelAddPeople>() {
        @Override
        public ModelAddPeople createFromParcel(Parcel source) {
            return new ModelAddPeople(source);
        }

        @Override
        public ModelAddPeople[] newArray(int size) {
            return new ModelAddPeople[size];
        }
    };
    private int userId;
    private String name;
    private boolean selected;

    public ModelAddPeople(int userId, String name, boolean selected) {
        this.userId = userId;
        this.name = name;
        this.selected = selected;
    }

    protected ModelAddPeople(Parcel in) {
        this.userId = in.readInt();
        this.name = in.readString();
        this.selected = in.readByte() != 0;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.name);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }
}
