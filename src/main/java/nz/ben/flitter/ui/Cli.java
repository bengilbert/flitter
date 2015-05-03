package nz.ben.flitter.ui;

import nz.ben.flitter.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
            Collection<Message> messages = interpreter.interpretCommand(command);

            //TODO order messages earliest first

            System.out.println(messageRenderer.render(messages));
        } while (!command.isEmpty());

    }
}
