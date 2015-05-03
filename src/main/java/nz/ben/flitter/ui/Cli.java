package nz.ben.flitter.ui;

import nz.ben.flitter.command.Response;
import nz.ben.flitter.ui.interpreter.CommandInterpreter;
import nz.ben.flitter.ui.render.MessageRenderer;
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
    private MessageRenderer messageRenderer;

    public void start() {
        if (System.console() == null) {
            System.out.println("Command interpreter requires a console to operate.");
            return;
        }

        String command;
        do {
            System.out.print("> ");
            command = System.console().readLine();

            Response response = interpreter.interpretCommand(command);

            if (response.hasMessages()) {
                System.out.println(messageRenderer.render(response.getMessages()));
            }
        } while (!command.isEmpty());

    }
}
