package nz.ben.flitter.message;

import nz.ben.flitter.user.User;
import org.joda.time.DateTime;

import java.util.Objects;

/**
 * Created by bengilbert on 23/04/15.
 */
public class Message {

    private String message;
    private User user;
    private DateTime posted;

    public Message(final User user, final String message, final DateTime dateTimePosted) {
        this.message = message;
        this.user = user;
        this.posted = dateTimePosted;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public DateTime getDateTime() {
        return posted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(message, message1.message) &&
                Objects.equals(user, message1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, user);
    }
}
