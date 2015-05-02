package nz.ben.flitter.ui;

import nz.ben.flitter.command.Command;
import nz.ben.flitter.command.CommandBuilder;
import nz.ben.flitter.message.Message;
import org.springframework.stereotype.Service;

import java.util.Collection;


/**
 * Created by bengilbert on 25/04/15.
 */
@Service
public class CommandInterpreter {

    public Collection<Message> interpretCommand(final String commandText) {
        Command command = new CommandBuilder().forString(commandText).build();
        Collection<Message> responses = command.execute();

        return responses;

    }
}
