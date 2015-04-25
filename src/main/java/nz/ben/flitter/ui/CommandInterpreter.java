package nz.ben.flitter.ui;

import nz.ben.flitter.message.Message;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;


/**
 * Created by bengilbert on 25/04/15.
 */
@Service
public class CommandInterpreter {


    public String interpretCommand(final String commandText) {

        Command command = new CommandBuilder().forString(commandText).build();
        String response = null;
        switch (command.getCommandType()) {
            case POST:
                command.getUser().postMessage(command.getCommandDetail());
                break;
            case VIEW_WALL:
                command.getUser().timeline();
                break;
            case VIEW_TIMELINE:
                Collection<Message> timeline = command.getUser().timeline();
                if (!timeline.isEmpty()) {
                    response = timeline.stream().map(Message::getMessage).collect(Collectors.joining("\n"));
                }
                break;
            case FOLLOW:
                break;
            default:
                break;
        }

        return response;
    }
}
