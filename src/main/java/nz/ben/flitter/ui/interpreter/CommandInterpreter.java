package nz.ben.flitter.ui.interpreter;

import nz.ben.flitter.command.Command;
import nz.ben.flitter.command.CommandBuilder;
import nz.ben.flitter.command.Response;
import org.springframework.stereotype.Service;


/**
 * Created by bengilbert on 25/04/15.
 */
@Service
public class CommandInterpreter {

    public Response interpretCommand(final String commandText) {
        Command command = new CommandBuilder().forString(commandText).build();
        Response response = command.execute();

        return response;
    }
}
