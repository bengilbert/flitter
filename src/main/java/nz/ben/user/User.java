package nz.ben.user;

import nz.ben.message.Message;
import nz.ben.message.PostOffice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by bengilbert on 23/04/15.
 */
@Configurable
//@Service
public class User {

    @Autowired
    private PostOffice postOffice;

    private String userName;

    public User(final String userName) {
        this.userName = userName;
    }

//    public User() {
//        userName = "alice";
//    }


    public void postMessage(final String message) {
        postOffice.postMessage(this, new Message(message));
    }

    public Collection<Message> timeline() {
        return postOffice.getMessagesForUser(this);
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
