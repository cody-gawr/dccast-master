package notq.dccast.model.notification;

public class ModelNotificationNotice {
    private String username;
    private int watchingCount;
    private boolean hasCheck = false;

    public ModelNotificationNotice(String username, int watchingCount) {
        this.username = username;
        this.watchingCount = watchingCount;
        this.hasCheck = false;
    }

    public ModelNotificationNotice(String username, boolean hasCheck) {
        this.username = username;
        this.hasCheck = hasCheck;
    }

    public boolean isHasCheck() {
        return hasCheck;
    }

    public void setHasCheck(boolean hasCheck) {
        this.hasCheck = hasCheck;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWatchingCount() {
        return watchingCount;
    }

    public void setWatchingCount(int watchingCount) {
        this.watchingCount = watchingCount;
    }
}
