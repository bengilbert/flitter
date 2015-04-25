package nz.ben.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nz.ben.config.FlitterConfig;
import nz.ben.message.Message;
import nz.ben.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Collection;

/**
 * Created by bengilbert on 22/04/15.
 */
@ContextConfiguration(classes = FlitterConfig.class)
public class UserSteps {

    //@Autowired
    private User user;

    @Given("^a user$")
    public void a_user() throws Throwable {
        user = new User("Alice");
    }

    @When("^the user posts a message$")
    public void the_user_posts_a_message() throws Throwable {
        user.postMessage("I love the weather today");
    }


    @Then("^the message will be on the users personal timeline$")
    public void the_message_will_be_on_the_users_personal_timeline() throws Throwable {
        Collection<Message> timeline = user.timeline();
        assertThat("timeline should contain one message", timeline, hasSize(1));
    }
}


