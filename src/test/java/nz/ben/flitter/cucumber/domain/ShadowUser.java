package nz.ben.flitter.cucumber.domain;

import nz.ben.flitter.message.Message;
import nz.ben.flitter.ui.CommandInterpreter;
import nz.ben.flitter.user.User;
import nz.ben.flitter.user.UserRepository;
import org.apache.commons.collections4.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.*;

/**
 * Created by bengilbert on 2/05/15.
 */
@Configurable
public class ShadowUser {

    @Autowired
    private CommandInterpreter interpreter;

    @Autowired
    private UserRepository userRepository;

    private String userName;

    private List<String> messages = new ArrayList<>();
    private Set<ShadowUser> following = new HashSet<>();

    public ShadowUser(final String userName) {
        this.userName = userName;
    }

    public void postMessage(final String message) {
        messages.add(message);
        interpreter.interpretCommand(userName + " -> " + message);
    }

    public Collection<Message> viewTimeline() {
        return interpreter.interpretCommand(userName);
    }

    public void follow(final ShadowUser otherShadowUser) {
        //TODO add result code to response as we only want to follow users that exist
        //TODO extract this information from the response
        interpreter.interpretCommand(userName + " follows " + otherShadowUser.getUserName());
        following.add(otherShadowUser);
    }

    public Collection<Message> viewWall() {
        return interpreter.interpretCommand(userName + " wall");
    }


    public Set<ShadowUser> getFollowing() {
        return SetUtils.unmodifiableSet(this.following);
    }

    public String getUserName() {
        return this.userName;
    }

    public User getUser() {
        //Assuming that the user always exists
        return userRepository.findByName(getUserName()).orElseThrow(RuntimeException::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShadowUser that = (ShadowUser) o;
        return Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}
