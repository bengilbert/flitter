package nz.ben.flitter.ui;

import nz.ben.flitter.user.User;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bengilbert on 25/04/15.
 */
@Configurable
public class CommandBuilder {

    private String command;

    public CommandBuilder forString(final String command) {
        this.command = command;
        return this;
    }

    public Command build() {
        Pattern p = Pattern.compile("(\\S+)(?: *)(\\S*)(?: *)(\\S*)");
        Matcher m = p.matcher(command);

        User user = null;
        Command.CommandType commandType = Command.CommandType.UNKNOWN;
        String commandDetail = null;

        if (m.matches()) {
            String userName = m.group(1);

            if (!userName.isEmpty()) {
                user = new User(userName);
            }

            switch (m.group(2)) {
                case "->":
                    commandType = Command.CommandType.POST;
                    break;
                case "follows":
                    commandType = Command.CommandType.FOLLOW;
                    break;
                case "wall":
                    commandType = Command.CommandType.VIEW_WALL;
                    break;
                default:
                    commandType = Command.CommandType.VIEW_TIMELINE;
            }

            if (!m.group(3).isEmpty()) {
                commandDetail = m.group(3);
            }

        }

        return new Command(user, commandType, commandDetail);
    }


}
