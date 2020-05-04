package Wade.botti;

import java.io.Serializable;

public class User implements Serializable {

    public long userId;
    public int messagePoints;

    public User(long userId, int messagePoints) {
        super();
        this.userId = userId;
        this.messagePoints = messagePoints;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId='" + userId + '\'' +
                ", messagePoints=" + messagePoints +
                '}';
    }
}
