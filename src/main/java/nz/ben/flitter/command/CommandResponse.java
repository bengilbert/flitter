package nz.ben.flitter.command;

import nz.ben.flitter.message.Message;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

/**
 * Created by bengilbert on 3/05/15.
 */
public class CommandResponse {

    private CommandType commandType;
    private Collection<Message> messages;

    public CommandResponse(CommandType commandType) {
        this.commandType = commandType;
        messages = CollectionUtils.emptyCollection();
    }

    public void setMessages(final Collection<Message> messages) {
        this.messages = messages;
    }

    public Collection<Message> getMessages() {
        return CollectionUtils.unmodifiableCollection(messages);
    }

    public boolean hasMessages() {
        return commandType.isHasMessages();
    }

    public boolean hasMessagesForOtherUsers() {
        return commandType.isHasMessagesForOtherUsers();
    }
}
