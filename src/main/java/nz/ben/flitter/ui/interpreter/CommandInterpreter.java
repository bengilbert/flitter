package nz.ben.flitter.ui.interpreter;

import nz.ben.flitter.command.Command;
import nz.ben.flitter.ui.command.CommandBuilder;
import nz.ben.flitter.command.CommandResponse;
import org.springframework.stereotype.Service;


/**
 * Created by bengilbert on 25/04/15.
 */
@Service
public class CommandInterpreter {

    public CommandResponse interpretCommand(final String commandText) {
        Command command = new CommandBuilder().forString(commandText).build();
        CommandResponse commandResponse = command.execute();

        return commandResponse;
    }
}
