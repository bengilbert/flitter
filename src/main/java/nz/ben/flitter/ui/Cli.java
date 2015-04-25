package nz.ben.flitter.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by bengilbert on 25/04/15.
 */
@Service
public class Cli {

    @Autowired
    private CommandInterpreter interpreter;

    public void start() {
        //TODO read from stdin
        interpreter.interpretCommand("");
    }
}
