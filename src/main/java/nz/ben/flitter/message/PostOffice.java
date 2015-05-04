package nz.ben.flitter.message;

import nz.ben.flitter.user.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.MultiValueMap;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by bengilbert on 23/04/15.
 */
@Service
public class PostOffice {

    MultiValueMap<User, Message> messages = new MultiValueMap<>();

    public void postMessage(final Message message) {
        messages.put(message.getUser(), message);
    }

    public Collection<Message> getMessagesForUser(final User user) {
        return CollectionUtils.emptyIfNull(messages.getCollection(user));
    }

    public void reset() {
        messages.clear();
    }
}
