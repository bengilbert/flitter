package nz.ben.flitter.command;

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
        Pattern p = Pattern.compile("(.*)(->|follows|wall)(.*)");
        Matcher m = p.matcher(command);

        String userName = null;
        Command.CommandType commandType = Command.CommandType.UNKNOWN;
        String commandDetail = null;

        if (!m.matches() && !command.isEmpty()) {
            //assume viewing posts for a user when the regex mtches nothing.
            userName = command;
            commandType = Command.CommandType.VIEW_TIMELINE;
            commandDetail = "";
        } else if (!m.matches() && command.isEmpty()) {
            userName = "";
            commandType = Command.CommandType.UNKNOWN;
            commandDetail = "";
        } else {

            userName = m.group(1);

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
                    commandType = Command.CommandType.UNKNOWN;
            }

            commandDetail = "";
            if (!m.group(3).isEmpty()) {
                commandDetail = m.group(3);
            }

        }

        return new Command(userName.trim(), commandType, commandDetail.trim());
    }


}
