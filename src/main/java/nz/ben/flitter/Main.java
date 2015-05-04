package nz.ben.flitter;

import nz.ben.flitter.config.FlitterConfig;
import nz.ben.flitter.ui.Cli;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by bengilbert on 25/04/15.
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext();
        ctx.register(FlitterConfig.class);
        ctx.refresh();

        Cli cli = ctx.getBean(Cli.class);

        cli.start();
    }
}
