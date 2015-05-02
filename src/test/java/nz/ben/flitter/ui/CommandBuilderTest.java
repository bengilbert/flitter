package nz.ben.flitter.ui;

import nz.ben.flitter.command.Command;
import nz.ben.flitter.command.CommandBuilder;
import nz.ben.flitter.config.FlitterConfig;
import nz.ben.flitter.user.User;
import nz.ben.flitter.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEmptyString.isEmptyString;

/**
 * Created by bengilbert on 25/04/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FlitterConfig.class)
public class CommandBuilderTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        userRepository.reset();
    }

    @Test()
    public void testBuild_blankString_isUnknown() {
        Command command = new CommandBuilder().forString("").build();
        assertThat(command.getCommandType(), is(Command.CommandType.UNKNOWN));
        assertThat(command.getCommandDetail(), isEmptyString());
    }

    @Test
    public void testBuild_userName_viewsWall() throws Exception {
        User alice = userRepository.createUser("Alice");

        Command command = new CommandBuilder().forString("Alice").build();
        assertThat(command.getCommandType(), is(Command.CommandType.VIEW_TIMELINE));
        assertThat(command.getUser(), is(alice));
        assertThat(command.getCommandDetail(), isEmptyString());
    }

    @Test
    public void testBuild_userFollowsOtherUser_bothUsersExist() throws Exception {
        User alice = userRepository.createUser("Alice");
        User charles = userRepository.createUser("Charles");

        Command command = new CommandBuilder().forString("Alice follows Charles").build();

        assertThat(command.getCommandType(), is(Command.CommandType.FOLLOW));
        assertThat(command.getUser(), is(alice));
        assertThat(command.getCommandDetail(), is(charles.getUserName()));
    }

    @Test
    public void testBuild_postingMessage_validCommand() throws Exception {
        User alice = userRepository.createUser("Alice");

        Command command = new CommandBuilder().forString("Alice -> message").build();

        assertThat(command.getCommandType(), is(Command.CommandType.POST));
        assertThat(command.getUser(), is(alice));
        assertThat(command.getCommandDetail(), is("message"));
    }

    @Test
    public void testBuild_postingMultiWordMessage_validCommand() throws Exception {
        User alice = userRepository.createUser("Alice");

        Command command = new CommandBuilder().forString("Alice -> message with spaces").build();

        assertThat(command.getCommandType(), is(Command.CommandType.POST));
        assertThat(command.getUser(), is(alice));
        assertThat(command.getCommandDetail(), is("message with spaces"));
    }

    @Test
    public void testBuild_postingMultiWordName_validCommand() throws Exception {
        User alice = userRepository.createUser("Alice April");

        Command command = new CommandBuilder().forString("Alice April -> message").build();

        assertThat(command.getCommandType(), is(Command.CommandType.POST));
        assertThat(command.getUser(), is(alice));
        assertThat(command.getCommandDetail(), is("message"));
    }

    @Test
    public void testBuild_postingMultiWordNameWithMultiWordMessage_validCommand() throws Exception {
        User alice = userRepository.createUser("Alice April");

        Command command = new CommandBuilder().forString("Alice April -> message with spaces").build();

        assertThat(command.getCommandType(), is(Command.CommandType.POST));
        assertThat(command.getUser(), is(alice));
        assertThat(command.getCommandDetail(), is("message with spaces"));
    }


    @Test
    public void testBuild_postingEmptyMessage_validCommand() throws Exception {
        User alice = userRepository.createUser("Alice");

        Command command = new CommandBuilder().forString("Alice ->").build();
        assertThat(command.getCommandType(), is(Command.CommandType.POST));
        assertThat(command.getUser(), is(alice));
        assertThat(command.getCommandDetail(), isEmptyString());
    }


}