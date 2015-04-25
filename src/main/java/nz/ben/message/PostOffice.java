package nz.ben.message;

import nz.ben.user.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by bengilbert on 23/04/15.
 */
@Service
public class PostOffice {

    MultiValueMap<User, Message> messages = new MultiValueMap<>();

    public void postMessage(final User user, final Message message) {
        messages.put(user, message);
    }

    public Collection<Message> getMessagesForUser(final User user) {
        return CollectionUtils.emptyIfNull(messages.getCollection(user));
    }
}
