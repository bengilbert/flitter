package nz.ben.flitter.ui;

import nz.ben.flitter.command.Command;
import nz.ben.flitter.command.CommandBuilder;
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
        Collection<Message> responses = command.execute();
        if (!responses.isEmpty()) {
            return responses.stream().map(Message::getMessage).collect(Collectors.joining("\n"));
        }

        return "";
    }
}
