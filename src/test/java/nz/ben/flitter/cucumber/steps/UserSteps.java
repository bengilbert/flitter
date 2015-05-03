package nz.ben.flitter.cucumber.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nz.ben.flitter.config.FlitterConfig;
import nz.ben.flitter.cucumber.domain.ShadowUser;
import nz.ben.flitter.cucumber.domain.World;
import nz.ben.flitter.message.Message;
import nz.ben.flitter.ui.interpreter.CommandInterpreter;
import nz.ben.flitter.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by bengilbert on 22/04/15.
 */
@ContextConfiguration(classes = FlitterConfig.class)
public class UserSteps {

    @Autowired
    private World world;

    @Autowired
    private CommandInterpreter interpreter;

    @Given("^a user$")
    public void a_user() throws Throwable {
        world.setShadowUser(new ShadowUser("Alice"));
    }

    @When("^the user posts a message$")
    public void the_user_posts_a_message() throws Throwable {
        world.getShadowUser().postMessage("message");
    }

    @Then("^the message will be on the users personal timeline$")
    public void the_message_will_be_on_the_users_personal_timeline() throws Throwable {
        Collection<Message> responses = world.getShadowUser().viewTimeline();
        assertThat("timeline should contain one message", responses, hasSize(1));
    }

    @Given("^a user with posts on their timeline$")
    public void a_user_with_posts_on_their_timeline() throws Throwable {
        world.setShadowUser(new ShadowUser("Alice"));
        world.getShadowUser().postMessage("message 1");
        world.getShadowUser().postMessage("message 2");
        world.getShadowUser().postMessage("message 3");
    }

    @And("^another user with posts on their timeline$")
    public void another_user_with_posts_on_their_timeline() throws Throwable {
        world.setOtherShadowUser(new ShadowUser("Charlie"));
        world.getOtherShadowUser().postMessage("message 1");
        world.getOtherShadowUser().postMessage("message 2");
    }

    @And("^the user follows the other user$")
    public void the_user_follows_the_other_user() throws Throwable {
        world.getShadowUser().follow(world.getOtherShadowUser());
    }

    @When("^the user views their wall they see posts for everyone they are following$")
    public void the_user_views_their_wall_they_see_posts_for_everyone_they_are_following() throws Throwable {
        Set<User> following = world.getShadowUser().getFollowing().stream().map(s -> s.getUser()).collect(Collectors.toSet());

        Collection<Message> messages = world.getShadowUser().viewWall();
        assertThat(messages, is(not(empty())));
        messages.forEach(m -> following.remove(m.getUser()));
        assertThat("There should be messages from each followed user", following, is(empty()));
    }

    @And("^the user views their wall they see their own posts$")
    public void the_user_views_their_wall_they_see_their_own_posts() throws Throwable {
        Collection<Message> wall = world.getShadowUser().viewWall();
        Collection<Message> timeline = world.getShadowUser().viewTimeline();

        Collection<Message> intersect = wall.stream().filter((m) -> timeline.contains(m)).collect(Collectors.toList());
        assertThat(intersect, is(not(empty())));
        assertThat(intersect, hasSize(timeline.size()));
    }
}


