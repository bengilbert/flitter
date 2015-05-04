package nz.ben.flitter.ui;

import nz.ben.flitter.command.CommandResponse;
import nz.ben.flitter.ui.interpreter.CommandInterpreter;
import nz.ben.flitter.ui.render.ResponseRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by bengilbert on 25/04/15.
 */
@Service
public class Cli {

    @Autowired
    private CommandInterpreter interpreter;

    @Autowired
    private ResponseRenderer responseRenderer;

    public void start() {
        if (System.console() == null) {
            System.out.println("Command interpreter requires a console to operate.");
            return;
        }

        String command;
        do {
            System.out.print("> ");
            command = System.console().readLine();

            CommandResponse commandResponse = interpreter.interpretCommand(command);

            if (commandResponse.hasMessages()) {
                System.out.println(responseRenderer.render(commandResponse));
            }
        } while (!command.isEmpty());

    }
}
