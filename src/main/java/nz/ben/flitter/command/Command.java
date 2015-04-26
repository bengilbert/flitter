package nz.ben.flitter.command;

import nz.ben.flitter.message.Message;
import nz.ben.flitter.user.User;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by bengilbert on 25/04/15.
 */
public class Command {

    public enum CommandType {POST, FOLLOW, VIEW_TIMELINE, VIEW_WALL, UNKNOWN};

    private User user;
    private CommandType commandType;
    private String commandDetail;

    public Command(final User user, final CommandType type, final String commandDetail) {
        this.user = user;
        this.commandType = type;
        this.commandDetail = commandDetail;
    }

    public User getUser() {
        return this.user;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public String getCommandDetail() {
        return this.commandDetail;
    }

    //TODO : consider response object - not needed for now
    public Collection<Message> execute() {
        Collection<Message> response = Collections.emptyList();
        switch (getCommandType()) {
            case POST:
                getUser().postMessage(getCommandDetail());
                break;
            case VIEW_WALL:
                response = getUser().timeline();
                break;
            case VIEW_TIMELINE:
                response = getUser().timeline();
                break;
            case FOLLOW:
                break;
            default:
                break;
        }

        return response;
    }


}
