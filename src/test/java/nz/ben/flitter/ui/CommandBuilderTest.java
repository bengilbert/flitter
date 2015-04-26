package nz.ben.flitter.ui;

import junit.framework.TestCase;
import nz.ben.flitter.command.Command;
import nz.ben.flitter.command.CommandBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by bengilbert on 25/04/15.
 */
public class CommandBuilderTest extends TestCase {

    public void testBuild_blankString_isUnknown() {
        Command command = new CommandBuilder().forString("").build();
        assertThat(command.getCommandType(), is(Command.CommandType.UNKNOWN));
        assertThat(command.getUser(), is(nullValue()));
        assertThat(command.getCommandDetail(), is(nullValue()));
    }

    public void testBuild_userName_viewsWall() throws Exception {
        Command command = new CommandBuilder().forString("Alice").build();
        assertThat(command.getCommandType(), is(Command.CommandType.VIEW_TIMELINE));
        assertThat(command.getUser(), not(nullValue()));
        assertThat(command.getCommandDetail(), is(nullValue()));
    }

    public void testBuild_postingMessage_validCommand() throws Exception {
        Command command = new CommandBuilder().forString("Alice -> message").build();
        assertThat(command.getCommandType(), is(Command.CommandType.POST));
        assertThat(command.getUser(), not(nullValue()));
        assertThat(command.getCommandDetail(), is("message"));
    }

    public void testBuild_postingEmptyMessage_validCommand() throws Exception {
        Command command = new CommandBuilder().forString("Alice ->").build();
        assertThat(command.getCommandType(), is(Command.CommandType.POST));
        assertThat(command.getUser(), not(nullValue()));
        assertThat(command.getCommandDetail(), is(nullValue()));
    }


}