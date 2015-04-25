package nz.ben.flitter.ui;

import nz.ben.flitter.user.User;

/**
 * Created by bengilbert on 25/04/15.
 */
public class Command {

    public enum CommandType {POST, FOLLOW, VIEW_TIMELINE, VIEW_WALL, UNKNOWN}

    ;

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


}
