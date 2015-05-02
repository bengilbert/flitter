package nz.ben.flitter.user;

import nz.ben.flitter.message.Message;
import nz.ben.flitter.message.PostOffice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bengilbert on 23/04/15.
 */
@Configurable
public class User {

    @Autowired
    private PostOffice postOffice;

    private Set<User> follows = new HashSet<>();

    private String userName;

    /* package */ User(final String userName) {
        this.userName = userName;
    }

    public void postMessage(final String message) {
        postOffice.postMessage(new Message(this, message));
    }

    public Collection<Message> timeline() {
        return postOffice.getMessagesForUser(this);
    }

    public Collection<Message> wall() {
        Collection<Message> allMessages = new ArrayList<>();
        allMessages.addAll(timeline());

        follows.forEach(u -> allMessages.addAll(u.timeline()));

        return allMessages;
    }

    public void follow(final User user) {
        follows.add(user);
    }

    public String getUserName() {
        return this.userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!userName.equals(user.userName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }
}
