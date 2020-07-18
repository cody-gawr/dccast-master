package notq.dccast.model.notification;

public class ModelNotificationVOD {
    private String username;
    private int watchingCount;

    public ModelNotificationVOD(String username, int watchingCount) {
        this.username = username;
        this.watchingCount = watchingCount;
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
