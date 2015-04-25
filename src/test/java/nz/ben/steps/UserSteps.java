package nz.ben.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nz.ben.flitter.config.FlitterConfig;
import nz.ben.flitter.ui.CommandInterpreter;
import nz.ben.flitter.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by bengilbert on 22/04/15.
 */
@ContextConfiguration(classes = FlitterConfig.class)
public class UserSteps {

    //@Autowired
    private User user;

    @Autowired
    private CommandInterpreter interpreter;

    private String userName;

    @Given("^a user$")
    public void a_user() throws Throwable {
        //user = new User("Alice");
        userName = "Alice";
    }

    @When("^the user posts a message$")
    public void the_user_posts_a_message() throws Throwable {
        //user.postMessage("I love the weather today");
        interpreter.interpretCommand(userName + " -> message");
    }


    @Then("^the message will be on the users personal timeline$")
    public void the_message_will_be_on_the_users_personal_timeline() throws Throwable {
        //Collection<Message> timeline = user.timeline();
        String response = interpreter.interpretCommand(userName);
        assertThat(response, not(nullValue()));
        List<String> responseLines = CollectionUtils.arrayToList(response.split("\n"));
        assertThat("timeline should contain one message", responseLines, hasSize(1));
    }
}


