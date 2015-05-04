package nz.ben.flitter.command;

/**
 * Created by bengilbert on 3/05/15.
 */
public enum CommandType {
    POST(false, false), FOLLOW(false, false), VIEW_TIMELINE(true, false), VIEW_WALL(true, true), UNKNOWN(false, false);

    private boolean hasMessages;
    private boolean hasMessagesForOtherUsers;

    CommandType(boolean hasMessages, boolean hasMessagesForOtherUsers) {
        this.hasMessages = hasMessages;
        this.hasMessagesForOtherUsers = hasMessagesForOtherUsers;
    }

    public boolean isHasMessages() {
        return hasMessages;
    }

    public boolean isHasMessagesForOtherUsers() {
        return hasMessagesForOtherUsers;
    }
};
